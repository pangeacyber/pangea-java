package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPGeolocateData;
import cloud.pangeacyber.pangea.intel.models.IPGeolocateBulkData;
import cloud.pangeacyber.pangea.intel.requests.IPGeolocateBulkRequest;
import cloud.pangeacyber.pangea.intel.responses.IPGeolocateBulkResponse;

import cloud.pangeacyber.pangea.Config;
import java.util.Map;

public class App
{

    public static void printData(String indicator, IPGeolocateData data) {
        System.out.printf("\t Indicator: %s\n", indicator);
        System.out.printf("\t\t Country: %s\n", data.getCountry());
        System.out.printf("\t\t City: %s\n", data.getCity());
        System.out.printf("\t\t Latitud: %f\n", data.getLatitude());
        System.out.printf("\t\t Longitud: %f\n", data.getLongitude());
        System.out.printf("\t\t Country code: %s\n", data.getCountryCode());
        System.out.printf("\t\t Postal code: %s\n", data.getPostalCode());
    }

    private static void printBulkData(IPGeolocateBulkData data) {
        for (Map.Entry<String, IPGeolocateData> entry : data.entrySet()) {
            printData(entry.getKey(), entry.getValue());
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
        IPGeolocateBulkResponse response = null;
        try {
            String[] ips = { "93.231.182.110", "190.28.74.251" };
            response = client.geolocateBulk(
                new IPGeolocateBulkRequest.Builder(ips).verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printBulkData(response.getResult().getData());
    }
}
