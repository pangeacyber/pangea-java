package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPDomainData;
import cloud.pangeacyber.pangea.intel.requests.IPDomainRequest;
import cloud.pangeacyber.pangea.intel.responses.IPDomainResponse;

import cloud.pangeacyber.pangea.Config;

public class App
{

    public static void printData(String ip, IPDomainData data) {
        if(data.isDomainFound()) {
            System.out.printf("\t IP %s domain is %s\n", ip, data.getDomain());
        } else {
            System.out.printf("\t IP %s domain not found\n", ip);
        }
    }

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
        String ip = "24.235.114.61";
        try {
            response = client.getDomain(
	    		new IPDomainRequest.Builder(ip).provider("digitalelement").verbose(true).raw(true).build()
    		);
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printData(ip,response.getResult().getData());
    }
}
