package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.redact.RedactClient;
import cloud.pangeacyber.pangea.redact.requests.RedactTextRequest;
import cloud.pangeacyber.pangea.redact.responses.RedactTextResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(RedactClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        RedactClient client = new RedactClient.Builder(cfg).build();
        String text = "Hello, my phone number is 123-456-7890";
        RedactTextResponse response = null;
        try {
            response = client.redactText(new RedactTextRequest.Builder(text).build());
        } catch (Exception e){
            System.out.println("Fail to perfom redact: " + e);
            System.exit(1);
        }

        System.out.println(String.format("Redacting PII from: '%s'", text));
        System.out.println(String.format("Redacted text: '%s'", response.getResult().getRedactedText()));
    }
}
