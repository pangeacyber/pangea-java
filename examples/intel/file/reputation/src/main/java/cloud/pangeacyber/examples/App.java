package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.FileIntelClient;
import cloud.pangeacyber.pangea.intel.models.FileReputationResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(FileIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        FileIntelClient client = new FileIntelClient.Builder(cfg).build();
        FileReputationResponse response = null;
        try {
            response = client.reputation("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Reputation success");
        System.out.println("Reputation verdict: " + response.getResult().getData().getVerdict());
        System.out.println("Reputation raw data: " + response.getResult().getRawData());
    }
}
