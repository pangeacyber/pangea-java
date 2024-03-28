package cloud.pangeacyber.pangea.authz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.authz.models.FilterTupleList;
import cloud.pangeacyber.pangea.authz.models.Resource;
import cloud.pangeacyber.pangea.authz.models.Subject;
import cloud.pangeacyber.pangea.authz.models.Tuple;
import cloud.pangeacyber.pangea.authz.requests.CheckRequest;
import cloud.pangeacyber.pangea.authz.requests.ListResourcesRequest;
import cloud.pangeacyber.pangea.authz.requests.ListSubjectsRequest;
import cloud.pangeacyber.pangea.authz.requests.TupleCreateRequest;
import cloud.pangeacyber.pangea.authz.requests.TupleDeleteRequest;
import cloud.pangeacyber.pangea.authz.requests.TupleListRequest;
import cloud.pangeacyber.pangea.authz.responses.CheckResponse;
import cloud.pangeacyber.pangea.authz.responses.ListResourcesResponse;
import cloud.pangeacyber.pangea.authz.responses.ListSubjectsResponse;
import cloud.pangeacyber.pangea.authz.responses.TupleCreateResponse;
import cloud.pangeacyber.pangea.authz.responses.TupleDeleteResponse;
import cloud.pangeacyber.pangea.authz.responses.TupleListResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITAuthZTest {

	AuthZClient client;
	static TestEnvironment environment;
	String time;

	String folder1;
	String folder2;
	String user1;
	String user2;

	String namespace_folder;
	String namespace_user;
	String relation_owner;
	String relation_editor;
	String relation_reader;

	@BeforeClass
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("authz", TestEnvironment.LIVE);
	}

	@Before
	public void setUp() throws Exception {
		client = new AuthZClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		time = dtf.format(LocalDateTime.now());

		folder1 = "folder_1_" + time;
		folder2 = "folder_2_" + time;
		user1 = "user_1_" + time;
		user2 = "user_2_" + time;

		namespace_folder = "folder";
		namespace_user = "user";
		relation_owner = "owner";
		relation_editor = "editor";
		relation_reader = "reader";
	}

	@Test
	public void testIntegration() throws PangeaException, PangeaAPIException {
		// Create tuples
		Resource resource1 = new Resource.Builder(namespace_folder).setId(folder1).build();
		Resource resource2 = new Resource.Builder(namespace_folder).setId(folder2).build();
		Subject subject1 = new Subject.Builder(namespace_user).setId(user1).build();
		Subject subject2 = new Subject.Builder(namespace_user).setId(user2).build();

		Tuple tuple1 = new Tuple(resource1, relation_reader, subject1);
		Tuple tuple2 = new Tuple(resource1, relation_editor, subject2);
		Tuple tuple3 = new Tuple(resource2, relation_editor, subject1);
		Tuple tuple4 = new Tuple(resource2, relation_owner, subject2);

		TupleCreateResponse createResp = client.tupleCreate(
			new TupleCreateRequest.Builder(new Tuple[] { tuple1, tuple2, tuple3, tuple4 }).build()
		);

		assertNull(createResp.getResult());

		// Tuple list with resource
		FilterTupleList filter = new FilterTupleList();
		filter.resourceNamespace().set(namespace_folder);
		filter.resourceID().set(folder1);

		TupleListResponse listResp = client.tupleList(new TupleListRequest.Builder().setFilter(filter).build());

		assertNotNull(listResp.getResult());
		assertEquals(2, listResp.getResult().getTuples().length);

		// Tuple list with subject
		filter = new FilterTupleList();
		filter.subjectNamespace().set(namespace_user);
		filter.subjectID().set(user1);

		listResp = client.tupleList(new TupleListRequest.Builder().setFilter(filter).build());

		assertNotNull(listResp.getResult());
		assertEquals(2, listResp.getResult().getTuples().length);

		// Tuple delete
		TupleDeleteResponse deleteResp = client.tupleDelete(
			new TupleDeleteRequest.Builder(new Tuple[] { tuple1 }).build()
		);

		assertNull(deleteResp.getResult());

		// Check no debug
		CheckResponse checkResp = client.check(
			new CheckRequest.Builder().setResource(resource1).setSubject(subject2).setAction(relation_editor).build()
		);

		assertNotNull(checkResp.getResult());
		assertTrue(checkResp.getResult().isAllowed());
		assertNull(checkResp.getResult().getDebug());
		assertNotNull(checkResp.getResult().getSchemaID());
		assertNotNull(checkResp.getResult().getSchemaVersion());

		// Check debug
		checkResp =
			client.check(
				new CheckRequest.Builder()
					.setResource(resource1)
					.setSubject(subject2)
					.setAction(relation_editor)
					.setDebug(true)
					.build()
			);

		assertNotNull(checkResp.getResult());
		assertTrue(checkResp.getResult().isAllowed());
		assertNotNull(checkResp.getResult().getDebug());
		assertNotNull(checkResp.getResult().getSchemaID());
		assertNotNull(checkResp.getResult().getSchemaVersion());

		// List resources
		ListResourcesResponse listResourcesResp = client.listResources(
			new ListResourcesRequest.Builder(namespace_folder, relation_editor, subject2).build()
		);

		assertNotNull(listResourcesResp.getResult());
		assertEquals(1, listResourcesResp.getResult().getIds().length);

		// List subjects
		ListSubjectsResponse listSubjectsResponse = client.listSubjects(
			new ListSubjectsRequest.Builder(resource2, relation_editor).build()
		);

		assertNotNull(listSubjectsResponse.getResult());
		assertEquals(1, listSubjectsResponse.getResult().getSubjects().length);
	}
}
