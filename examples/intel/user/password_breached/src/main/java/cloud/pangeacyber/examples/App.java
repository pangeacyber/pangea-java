package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.UserIntelClient;
import cloud.pangeacyber.pangea.intel.models.*;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Utils;

public class App
{
    public static void main( String[] args ) throws PangeaException
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(UserIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        UserIntelClient client = new UserIntelClient(cfg);
        UserPasswordBreachedResponse response = null;
        // Set the password you would like to check
        String password = "mypassword";
        // Calculate its hash, it could be sha256 or sha1
        String hash = Utils.hashSHA256(password);
        // get the hash prefix, right know it should be just 5 characters
        String hashPrefix = Utils.getHashPrefix(hash, 5);

        try {
          response = client.breached(
            // should setup right hash_type here, sha256 or sha1
            new UserPasswordBreachedRequest.UserPasswordBreachedRequestBuilder(HashType.SHA256, hashPrefix)
            .setVerbose(true)
            .setRaw(true)
            .build()
            );
        } catch (Exception e){
          System.out.println("Fail to perfom request: " + e);
          System.exit(1);
        }

        System.out.println("Request success");
        System.out.println("User password found in breach?: " + response.getResult().getData().getFoundInBreach());

        // This auxiliary function analyze service provider raw data to search for full hash in their registers
        PasswordStatus status = UserIntelClient.isPasswordBreached(response, hash);
        if(status == PasswordStatus.BREACHED){
          System.out.println(String.format("Password '%s' has been breached", password));
        }
        else if(status == PasswordStatus.UNBREACHED){
          System.out.println(String.format("Password '%s' has not been breached", password));
        }
        else if(status == PasswordStatus.INCONCLUSIVE){
          System.out.println(String.format("Not enough information to confirm if password '%s' has been or has not been breached.", password));
        }
        else {
          System.out.println(String.format("Unknown status: %d", status));
        }
    }
}
