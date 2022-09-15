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
    HttpClient client;
    String serviceName;

    protected Client(Config config, String serviceName) {
        this.config = config;
        this.client = getClient();
        this.serviceName = serviceName;
    }

    private HttpClient getClient() {
        
        HttpClient client = HttpClient.newBuilder()
            //.connectTimeout(Duration.ofSeconds(config.connectionTimeout)) // TODO
            .build();
        return client;
    }

    public <Req, ResponseType extends Response<?>> ResponseType doPost(String path, Req request, Class<ResponseType> responseClass) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(request);
                
        Builder builder = HttpRequest.newBuilder()
            .uri(config.getServiceUrl(serviceName, path))
            .header("Authorization", "Bearer " + config.getToken())
            .POST(HttpRequest.BodyPublishers.ofString(body));

        if (config.getConfigId() != "") {
            builder = builder.header(config.getServiceIdHeaderName(serviceName), config.getConfigId());
        }

        HttpRequest httpRequest = builder.build();
        
        HttpResponse<String> httpResponse = client.send(httpRequest, BodyHandlers.ofString());
        body = httpResponse.body();
        ResponseType response =  mapper.readValue(body, responseClass);
        response.setHttpResponse(httpResponse);
        return response;
    }
}
