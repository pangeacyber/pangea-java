package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.DomainIntelClient;
import cloud.pangeacyber.pangea.intel.UrlIntelClient;
import cloud.pangeacyber.pangea.intel.models.DomainReputationResponse;
import cloud.pangeacyber.pangea.intel.models.URLReputationResponse;
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
            cfg = Config.fromEnvironment(UrlIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        String url = "http://113.235.101.11:54384";
        UrlIntelClient clientURL = new UrlIntelClient(cfg);
        DomainIntelClient clientDomain = new DomainIntelClient(cfg);

        try {
            URLReputationResponse responseURL = clientURL.reputation(url, "crowdstrike", true, true);

            if(responseURL.getResult().getData().getVerdict().equalsIgnoreCase("malicious")){
                System.out.println(String.format("Defanged URL: %s", App.defangURL(url)));
            } else {
                String domain = App.getDomain(url);
                DomainReputationResponse responseDomain = clientDomain.reputation(domain, "domaintools", true, true);
                if(responseDomain.getResult().getData().getVerdict().equalsIgnoreCase("malicious")){
                    System.out.println(String.format("Defanged URL: %s", App.defangURL(url)));
                } else {
                    System.out.println(String.format("URL: %s seems to be safe", url));
                }
            }
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
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
