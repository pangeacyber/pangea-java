package cloud.pangeacyber.pangea;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;

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
            .POST(HttpRequest.BodyPublishers.ofString(body));

        if (config.getConfigId() != "") {
            builder = builder.header(config.getServiceIdHeaderName(serviceName), config.getConfigId());
        }
    }

    public <Req, ResponseType extends Response<?>> ResponseType doPost(String path, Req request, Class<ResponseType> responseClass) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(request);

        HttpRequest httpRequest = buildPostRequest(path, body);
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
        body = httpResponse.body();
        ResponseType response =  mapper.readValue(body, responseClass);
        response.setHttpResponse(httpResponse);
        return response;
    }
}
