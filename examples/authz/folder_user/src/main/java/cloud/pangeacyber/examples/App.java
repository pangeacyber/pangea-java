package cloud.pangeacyber.examples;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authz.AuthZClient;
import cloud.pangeacyber.pangea.authz.models.FilterTupleList;
import cloud.pangeacyber.pangea.authz.models.Resource;
import cloud.pangeacyber.pangea.authz.models.Subject;
import cloud.pangeacyber.pangea.authz.models.Tuple;
import cloud.pangeacyber.pangea.authz.requests.CheckRequest;
import cloud.pangeacyber.pangea.authz.requests.TupleCreateRequest;
import cloud.pangeacyber.pangea.authz.requests.TupleDeleteRequest;
import cloud.pangeacyber.pangea.authz.requests.TupleListRequest;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class App {
    public static void main(String[] args) {
		var token = System.getenv("PANGEA_AUTHZ_TOKEN");
		var domain = System.getenv("PANGEA_DOMAIN");

		var config = new Config.Builder(token, domain).build();
        var client = new AuthZClient.Builder(config).build();

		// Mock data.
		var dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		var time = dtf.format(OffsetDateTime.now());
		var folderId = String.format("folder_%s", time);
		var userId = String.format("user_%s", time);

		// Set up resources, subjects, and tuples.
		var folder = new Resource.Builder("folder").setId(folderId).build();
		var user = new Subject.Builder("user").setId(userId).build();
		var tuple = new Tuple(folder, "reader", user);

		try {
			// Create a tuple.
			client.tupleCreate(new TupleCreateRequest.Builder(new Tuple[] { tuple }).build());
			System.out.printf("User '%s' is a 'reader' for folder '%s'.\n", userId, folderId);

			// Find the tuple that was just created.
			var filter = new FilterTupleList();
			filter.resourceType().set("folder");
			filter.resourceID().set(folderId);
			var listResponse = client.tupleList(new TupleListRequest.Builder().setFilter(filter).build());
			System.out.println("Tuples:");
			for (var tupleResult : listResponse.getResult().getTuples()) {
				System.out.printf(
					"- subject '%s' has relation '%s' with resource '%s'.\n",
					tupleResult.getSubject().getId(),
					tupleResult.getRelation(),
					tupleResult.getResource().getId()
				);
			}
			System.out.println();

			// Check if the user is an editor of the folder.
			var checkResponse = client.check(
				new CheckRequest.Builder()
					.setResource(folder)
					.setAction("editor")
					.setSubject(user)
					.build()
			);
			System.out.printf("Is editor? %s.\n", checkResponse.getResult().isAllowed());
			// ⇒ false

			// They're not an editor, but they are a reader.
			checkResponse = client.check(
				new CheckRequest.Builder()
					.setResource(folder)
					.setAction("reader")
					.setSubject(user)
					.build()
			);
			System.out.printf("Is reader? %s.\n", checkResponse.getResult().isAllowed());
			// ⇒ true

			// Delete the tuple.
			client.tupleDelete(new TupleDeleteRequest.Builder(new Tuple[] { tuple }).build());
		} catch (PangeaException error) {
			System.err.println("Operation error.");
			System.err.println(error);
			System.exit(1);
		} catch (PangeaAPIException error) {
			System.err.println("API error.");
			System.err.println(error);
			System.exit(1);
		}
    }
}
