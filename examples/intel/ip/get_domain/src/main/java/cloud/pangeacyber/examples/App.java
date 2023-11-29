package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.requests.IPDomainRequest;
import cloud.pangeacyber.pangea.intel.responses.IPDomainResponse;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(IPIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        IPIntelClient client = new IPIntelClient.Builder(cfg).build();
        IPDomainResponse response = null;
        try {
            response = client.getDomain(
	    		new IPDomainRequest.Builder("24.235.114.61").provider("digitalelement").verbose(true).raw(true).build()
    		);
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("Get domain success");
        System.out.println("Domain name: " + response.getResult().getData().getDomain());
        System.out.println("Domain raw data: " + response.getResult().getRawData());
    }
}
