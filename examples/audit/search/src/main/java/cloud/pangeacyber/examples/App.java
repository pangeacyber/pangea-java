package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.SearchEvent;
import cloud.pangeacyber.pangea.audit.models.Event;
import cloud.pangeacyber.pangea.audit.models.SearchConfig;
import cloud.pangeacyber.pangea.audit.requests.ResultRequest;
import cloud.pangeacyber.pangea.audit.requests.SearchRequest;
import cloud.pangeacyber.pangea.audit.responses.ResultsResponse;
import cloud.pangeacyber.pangea.audit.responses.SearchResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    private static void printSearchEvent(SearchEvent e){
        Event event = (Event) e.getEventEnvelope().getEvent();
        System.out.println(e.getEventEnvelope().getReceivedAt() + "\t| "
        + e.getSignatureVerification() + "\t| "
        + e.getConsistencyVerification() + "\t| "
        + e.getMembershipVerification() + "\t| "
        + event.getMessage());
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
        SearchRequest searchRequest = new SearchRequest.Builder("message:\"\"")
                                        .limit(10)
                                        .build();
        SearchResponse searchResponse = null;

        // Perform search
        try {
            searchResponse = client.search(searchRequest, Event.class, new SearchConfig.Builder().build());
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
        ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
                                        .limit(resultsLimit)
                                        .offset(0)
                                        .build();
        try {
            resultsResponse = client.results(resultRequest, Event.class, new SearchConfig.Builder().build());
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
        resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
                                        .limit(resultsLimit)
                                        .offset(resultsLimit)
                                        .build();
        try {
            resultsResponse = client.results(resultRequest, Event.class, new SearchConfig.Builder().build());
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
