package cloud.pangeacyber.pangea;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import cloud.pangeacyber.pangea.exceptions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Client {
    Config config;
    HttpClient httpClient;
    Builder httpRequestBuilder;
    String serviceName;

    protected Client(Config config, String serviceName) {
        this.serviceName = serviceName;
        this.config = config;
        this.httpClient = buildClient();
    }

    protected HttpClient buildClient() {
        java.net.http.HttpClient.Builder builder = HttpClient.newBuilder();
        if (config.connectionTimeout != null) {
            builder.connectTimeout(config.connectionTimeout);
        }
        return builder.build();
    }

    protected HttpRequest buildPostRequest(String path, String body) {
        Builder builder = HttpRequest.newBuilder();
        fillPostRequestBuilder(builder, path, body);
        return builder.build();
    }

    protected void fillPostRequestBuilder(HttpRequest.Builder builder, String path, String body) {
        builder
            .uri(config.getServiceUrl(serviceName, path))
            .header("Authorization", "Bearer " + config.getToken())
            .header("User-Agent", "pangea-java/" + Version.VERSION)
            .POST(HttpRequest.BodyPublishers.ofString(body));
    }

    public <Req, ResponseType extends Response<?>> ResponseType doPost(String path, Req request, Class<ResponseType> responseClass) throws PangeaException, PangeaAPIException {
        ObjectMapper mapper = new ObjectMapper();
        String body;
        try{
            body = mapper.writeValueAsString(request);
        } catch(JsonProcessingException e){
            throw new PangeaException("Failed to write request", e);
        }

        HttpRequest httpRequest = buildPostRequest(path, body);
        HttpResponse<String> httpResponse;

        try{
            httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
        } catch(Exception e){
            throw new PangeaException("Failed to send request", e);
        }

        return checkResponse(httpResponse, responseClass);
    }

    private <ResponseType extends Response<?>> ResponseType checkResponse(HttpResponse<String> httpResponse, Class<ResponseType> responseClass) throws PangeaException, PangeaAPIException {
        String body = httpResponse.body();
        ObjectMapper mapper = new ObjectMapper();
        ResponseHeader header;

        try{
            header =  mapper.readValue(body, ResponseHeader.class);
        } catch(Exception e){
            throw new PangeaException("Failed to parse response header", e);
        }

        ResponseType resultResponse;

        if(header.isOk()){
            try{
                resultResponse =  mapper.readValue(body, responseClass);
            } catch(Exception e) {
                throw new ParseResultFailed("Failed to parse response result", e, header, body);
            }
            resultResponse.setHttpResponse(httpResponse);
            return resultResponse;
        }

        // Process error
        String summary = header.getSummary();
        String status = header.getStatus();
        ResponseError response;
        try{
            response =  mapper.readValue(body, ResponseError.class);
        } catch(Exception e){
            throw new ParseResultFailed("Failed to parse response errors", e, header, body);
        }

        response.setHttpResponse(httpResponse);

        if( status.equals(ResponseStatus.VALIDATION_ERR.toString())){
            throw new ValidationException(summary, response);
        } else if( status.equals(ResponseStatus.TOO_MANY_REQUESTS.toString())) {
            throw new RateLimitException(summary, response);
        } else if( status.equals(ResponseStatus.NO_CREDIT.toString())) {
            throw new NoCreditException(summary, response);
        } else if( status.equals(ResponseStatus.UNAUTHORIZED.toString())) {
            throw new UnauthorizedException(this.serviceName, response);
        } else if( status.equals(ResponseStatus.NOT_FOUND.toString())) {
            throw new NotFound(httpResponse.uri().getHost() + httpResponse.uri().getPath(), response);
        } else if( status.equals(ResponseStatus.SERVICE_NOT_ENABLED.toString())) {
            throw new ServiceNotEnabledException(this.serviceName, response);
        } else if( status.equals(ResponseStatus.PROVIDER_ERR.toString())) {
            throw new ProviderErrorException(summary, response);
        } else if( status.equals(ResponseStatus.MISSING_CONFIG_ID_SCOPE.toString()) || status.equals(ResponseStatus.MISSING_CONFIG_ID.toString())) {
            throw new MissingConfigID(this.serviceName, response);
        } else if( status.equals(ResponseStatus.SERVICE_NOT_AVAILABLE.toString())) {
            throw new ServiceNotAvailableException(summary, response);
        } else if( status.equals(ResponseStatus.IP_NOT_FOUND.toString())) {
            throw new EmbargoIPNotFoundException(summary, response);
        } else {
            throw new PangeaAPIException(String.format("%s: %s", status, summary), response);
        }
    }

}
