package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.DomainIntelClient;
import cloud.pangeacyber.pangea.intel.URLIntelClient;
import cloud.pangeacyber.pangea.intel.requests.DomainReputationRequest;
import cloud.pangeacyber.pangea.intel.requests.URLReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.DomainReputationResponse;
import cloud.pangeacyber.pangea.intel.responses.URLReputationResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(URLIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        String url = "http://113.235.101.11:54384";
        URLIntelClient clientURL = new URLIntelClient.Builder(cfg).build();
        DomainIntelClient clientDomain = new DomainIntelClient.Builder(cfg).build();

        try {
            URLReputationResponse responseURL = clientURL.reputation(
                new URLReputationRequest.Builder(url)
                    .provider("crowdstrike")
                    .raw(true)
                    .verbose(true)
                    .build()
            );

            if(responseURL.getResult().getData().getVerdict().equalsIgnoreCase("malicious")){
                System.out.println(String.format("Defanged URL: %s", App.defangURL(url)));
            } else {
                String domain = App.getDomain(url);
                DomainReputationResponse responseDomain = clientDomain.reputation(
                    new DomainReputationRequest.Builder(domain)
                        .provider("domaintools")
                        .verbose(true)
                        .raw(true)
                        .build()
                );
                if(responseDomain.getResult().getData().getVerdict().equalsIgnoreCase("malicious")){
                    System.out.println(String.format("Defanged URL: %s", App.defangURL(url)));
                } else {
                    System.out.println(String.format("URL: %s seems to be safe", url));
                }
            }
        } catch (Exception e){
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }
    }

    public static String defangURL(String url){
        Map<String, String> defangedProtocols = new HashMap<String, String>();
        defangedProtocols.put("http", "hxxp");
        defangedProtocols.put("https", "hxxps");

        URL aURL = null;
        try {
            aURL = new URL(url);
        } catch (MalformedURLException e) {
            return url;
        }
        String protocol = aURL.getProtocol();
        String defangedProtocol = defangedProtocols.get(protocol);
        if(defangedProtocol != null){
            return url.replace(protocol, defangedProtocol);
        }
        return url;
    }

    public static String getDomain(String url){
        URL aURL = null;
        try {
            aURL = new URL(url);
        } catch (MalformedURLException e) {
            return url;
        }
        return aURL.getHost();
    }
}
