package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.redact.RedactClient;
import cloud.pangeacyber.pangea.redact.RedactTextResponse;
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

        RedactClient client = new RedactClient(cfg);
        String text = "Hello, my phone number is 123-456-7890";
        RedactTextResponse response = null;
        try {
            response = client.redactText(text);
        } catch (Exception e){
            System.out.println("Fail to perfom redact: " + e);
            System.exit(1);
        }

        System.out.println("Redact success");
        System.out.println("Redacted text: " + response.getResult().getRedactedText());
    }
}
