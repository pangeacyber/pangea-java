package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.ResultsResponse;
import cloud.pangeacyber.pangea.audit.SearchEvent;
import cloud.pangeacyber.pangea.audit.SearchResponse;
import cloud.pangeacyber.pangea.audit.SearchInput;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    private static void printSearchEvent(SearchEvent e){
        System.out.println(e.getEventEnvelope().getReceivedAt() + "\t| "
        + e.getSignatureVerification() + "\t| "
        + e.getConsistencyVerification() + "\t| "
        + e.getMembershipVerification() + "\t| "
        +  e.getEventEnvelope().getEvent().getMessage());
    }

    private static void printSearchEventHeader(){
        System.out.println("\t\treceived_at\t| Signature\t| Consistency\t| Membership\t| \t Message \t\t" );
        System.out.println("--".repeat(60));
    }

    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(AuditClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        // Create audit client
        AuditClient client = new AuditClient.Builder(cfg).build();

        // Set up search params
        SearchInput input = new SearchInput("message:");
        input.setLimit(10);
        SearchResponse searchResponse = null;

        // Perform search
        try {
            searchResponse = client.search(input);
        } catch (Exception e){
            System.out.println("Fail to perfom log: " + e);
            System.exit(1);
        }

        // Print search events
        System.out.println("Search success");
        System.out.println("Events found: " + searchResponse.getResult().getCount());
        App.printSearchEventHeader();
        for(SearchEvent e: searchResponse.getResult().getEvents()){
            App.printSearchEvent(e);
        }

        // Perform search pagination. Page 1
        int resultsLimit = 3;
        ResultsResponse resultsResponse = null;
        try {
            resultsResponse = client.results(searchResponse.getResult().getId(), resultsLimit, 0);
        } catch (Exception e){
            System.out.println("Fail to perfom log: " + e);
            System.exit(1);
        }

        // Print results events
        System.out.println("\n\nResults success. Print results page 1");
        System.out.println("Events found: " + resultsResponse.getResult().getCount());
        App.printSearchEventHeader();
        for(SearchEvent e: resultsResponse.getResult().getEvents()){
            App.printSearchEvent(e);
        }

        // Perform search pagination. Page 2
        try {
            resultsResponse = client.results(searchResponse.getResult().getId(), resultsLimit, resultsLimit);
        } catch (Exception e){
            System.out.println("Fail to perfom log: " + e);
            System.exit(1);
        }

        // Print results events
        System.out.println("\n\nResults success. Print results page 2");
        System.out.println("Events found: " + resultsResponse.getResult().getCount());
        App.printSearchEventHeader();
        for(SearchEvent e: resultsResponse.getResult().getEvents()){
            App.printSearchEvent(e);
        }

    }
}
