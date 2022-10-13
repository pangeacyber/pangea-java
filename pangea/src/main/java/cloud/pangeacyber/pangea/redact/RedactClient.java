package cloud.pangeacyber.pangea.redact;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;

final class TextRequest {
    @JsonProperty("text")
    String text;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("debug")
    Boolean debug;

    public TextRequest(String text, Boolean debug) {
        this.text = text;
        this.debug = debug;
    }
}

final class StructuredRequest {
    @JsonProperty("data")
    Map<String, Object> data;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("jsonp")
    String[] jsonp;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("format")
    String format;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("debug")
    Boolean debug;

    StructuredRequest(Map<String, Object> data, String[] jsonp, String format, Boolean debug){
        this.data = data;
        this.jsonp = jsonp;
        this.format = format;
        this.debug = debug;
    }
}

final class RedactTextResponse extends Response<RedactTextOutput> {}
final class RedactStructuredResponse extends Response<RedactStructuredOutput> {}

public class RedactClient extends Client {
    public static String serviceName = "redact";

    public RedactClient(Config config) {
        super(config, serviceName);
    }

    private RedactTextResponse redactPost(String text, Boolean debug)  throws IOException, InterruptedException, PangeaAPIException{
        TextRequest request = new TextRequest(text, debug);
        RedactTextResponse resp = doPost("/v1/redact", request, RedactTextResponse.class);
        return resp;
    }

    private RedactStructuredResponse structuredPost(Map<String, Object> data, String format, Boolean debug, String[] jsonp)  throws IOException, InterruptedException, PangeaAPIException{
        StructuredRequest request = new StructuredRequest(data, jsonp, null, debug);
        RedactStructuredResponse resp = doPost("/v1/redact_structured", request, RedactStructuredResponse.class);
        return resp;
    }

    public RedactTextResponse redactText(String text) throws IOException, InterruptedException, PangeaAPIException{
        return redactPost(text, null);
    }

    public RedactTextResponse redactText(String text, boolean debug) throws IOException, InterruptedException, PangeaAPIException {
        return redactPost(text, debug);
    }

    public RedactStructuredResponse redactStructured(Map<String, Object> data) throws IOException, InterruptedException, PangeaAPIException{
        return structuredPost(data, null, null, null);
    }

    public RedactStructuredResponse redactStructured(Map<String, Object> data, String format) throws IOException, InterruptedException, PangeaAPIException{
        return structuredPost(data, format, null, null);
    }

    public RedactStructuredResponse redactStructured(Map<String, Object> data, boolean debug) throws IOException, InterruptedException, PangeaAPIException{
        return structuredPost(data, null, debug, null);
    }

    public RedactStructuredResponse redactStructured(Map<String, Object> data, String format, boolean debug) throws IOException, InterruptedException, PangeaAPIException{
        return structuredPost(data, format, debug, null);
    }

    public RedactStructuredResponse redactStructured(Map<String, Object> data, boolean debug, String[] jsonp) throws IOException, InterruptedException, PangeaAPIException{
        return structuredPost(data, null, debug, jsonp);
    }

}