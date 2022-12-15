package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.redact.RedactClient;
import cloud.pangeacyber.pangea.redact.RedactStructuredResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.util.HashMap;

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
        RedactStructuredResponse response = null;
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("Name", "My name is Andres");
        data.put("Contact", "My phone number is 123-456-7890");
        try {
            response = client.redactStructured(data);
        } catch (Exception e){
            System.out.println("Fail to perfom redact: " + e);
            System.exit(1);
        }

        System.out.println("Redact success");
        System.out.println("Redacted data: " + response.getResult().getRedactedData());
    }
}
