package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.UserIntelClient;
import cloud.pangeacyber.pangea.intel.requests.UserBreachedRequest;
import cloud.pangeacyber.pangea.intel.responses.UserBreachedResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(UserIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        UserIntelClient client = new UserIntelClient.Builder(cfg).build();
        UserBreachedResponse response = null;
        try {
            response = client.breached(
                new UserBreachedRequest.Builder()
                .ip("192.168.140.37")
                .verbose(true)
                .raw(true)
                .build()
            );
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Request success");
        System.out.println("User breached?: " + response.getResult().getData().getFoundInBreach());
        System.out.println("Breached raw data: " + response.getResult().getRawData());
    }
}
