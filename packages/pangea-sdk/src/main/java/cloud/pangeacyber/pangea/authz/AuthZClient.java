package cloud.pangeacyber.pangea.authz;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authz.requests.*;
import cloud.pangeacyber.pangea.authz.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

/**
 * AuthZ API client.
 *
 * Note that this service is in Beta and is subject to change.
 */
public class AuthZClient extends BaseClient {

	/** Service name. */
	public static final String serviceName = "authz";

	public AuthZClient(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public AuthZClient build() {
			return new AuthZClient(this);
		}
	}

	/**
	 * Create tuple (Beta)
	 * @pangea.description Create tuples in the AuthZ Service. The request will
	 * fail if there is no schema or the tuples do not validate against the
	 * schema.
	 * How to install a <a href="https://pangea.cloud/docs/sdk/java/#beta-releases">Beta release</a>.
	 * @pangea.operationId tuple_create_post_v1_tuple_create
	 * @param request {@link TupleCreateRequest} containing the list of tuples to be created.
	 * @return A {@link TupleCreateResponse} with an empty result.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the tuple creation process.
	 * @pangea.code
	 * {@code
	 * client.tupleCreate(
	 *     new TupleCreateRequest.Builder(
	 *         new Tuple[] {
	 *             new Tuple(
	 *                 new Resource.Builder("folder").setId("folder_1").build(),
	 *                 "owner",
	 *                 new Subject.Builder("user").setId("user_1").build()
	 *             ),
	 *         }
	 *     ).build()
	 * );
	 * }
	 */
	public TupleCreateResponse tupleCreate(TupleCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/tuple/create", request, TupleCreateResponse.class);
	}

	/**
	 * List tuples (Beta)
	 * @pangea.description Return a paginated list of filtered tuples. The
	 * filter is given in terms of a tuple. Fill out the fields that you want to
	 * filter. If the filter is empty it will return all the tuples.
	 * How to install a <a href="https://pangea.cloud/docs/sdk/java/#beta-releases">Beta release</a>.
	 * @pangea.operationId tuple_list_post_v1_tuple_list
	 * @param request The {@link TupleListRequest} containing the filter criteria.
	 * @return A {@link TupleListResponse} with the list of tuples and additional information.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the tuple listing process.
	 * @pangea.code
	 * {@code
	 * var filter = new FilterTupleList();
	 * filter.resourceType().set("user");
	 * filter.resourceID().set("user_1");
	 * var response = client.tupleList(new TupleListRequest.Builder().setFilter(filter).build());
	 * }
	 */
	public TupleListResponse tupleList(TupleListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/tuple/list", request, TupleListResponse.class);
	}

	/**
	 * Delete tuples (Beta)
	 * @pangea.description Delete tuples in the AuthZ Service.
	 * How to install a <a href="https://pangea.cloud/docs/sdk/java/#beta-releases">Beta release</a>.
	 * @pangea.operationId tuple_delete_post_v1_tuple_delete
	 * @param request The {@link TupleDeleteRequest} containing the tuples to be deleted.
	 * @return A {@link TupleDeleteResponse} with information about the deleted tuples.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the tuple deletion process.
	 * @pangea.code
	 * {@code
	 * client.tupleDelete(
	 *     new TupleDeleteRequest.Builder(
	 *         new Tuple[] {
	 *             new Tuple(
	 *                 new Resource.Builder("folder").setId("folder_1").build(),
	 *                 "owner",
	 *                 new Subject.Builder("user").setId("user_1").build()
	 *             ),
	 *         }
	 *     ).build()
	 * );
	 * }
	 */
	public TupleDeleteResponse tupleDelete(TupleDeleteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/tuple/delete", request, TupleDeleteResponse.class);
	}

	/**
	 * Check authorization (Beta)
	 * @pangea.description Check if a subject has permission to perform an
	 * action on the resource.
	 * How to install a <a href="https://pangea.cloud/docs/sdk/java/#beta-releases">Beta release</a>.
	 * @pangea.operationId check_post_v1_check
	 * @param request The {@link CheckRequest} containing details for the authorization check.
	 * @return A {@link CheckResponse} indicating whether the action is allowed or not.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the authorization check process.
	 * @pangea.code
	 * {@code
	 * var response = client.check(
	 *     new CheckRequest.Builder()
	 *         .setResource(new Resource.Builder("folder").setId("folder_1").build())
	 *         .setSubject(new Subject.Builder("user").setId("user_1").build())
	 *         .setAction("update")
	 *         .build()
	 * );
	 * }
	 */
	public CheckResponse check(CheckRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/check", request, CheckResponse.class);
	}

	/**
	 * List resources (Beta)
	 * @pangea.description Given a type, action, and subject, list all the
	 * resources of the type that the subject has access to the action with.
	 * How to install a <a href="https://pangea.cloud/docs/sdk/java/#beta-releases">Beta release</a>.
	 * @pangea.operationId list_resources_post_v1_list_resources
	 * @param request The {@link ListResourcesRequest} containing criteria for listing resources.
	 * @return A {@link ListResourcesResponse} with the IDs of resources that match the criteria.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the resource listing process.
	 * @pangea.code
	 * {@code
	 * var response = client.listResources(
	 *     new ListResourcesRequest.Builder(
	 *         "folder",
	 *         "edit",
	 *         new Subject.Builder("user").setId("user_1").build()
	 *     ).build()
	 * );
	 * }
	 */
	public ListResourcesResponse listResources(ListResourcesRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/list-resources", request, ListResourcesResponse.class);
	}

	/**
	 * List subjects (Beta)
	 * @pangea.description Given a resource and an action, return the list of
	 * subjects who have access to the action for the given resource.
	 * How to install a <a href="https://pangea.cloud/docs/sdk/java/#beta-releases">Beta release</a>.
	 * @pangea.operationId list_subjects_post_v1_list_subjects
	 * @param request The {@link ListSubjectsRequest} containing criteria for listing subjects.
	 * @return A {@link ListSubjectsResponse} with the subjects that match the criteria.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the subject listing process.
	 * @pangea.code
	 * {@code
	 * var response = client.listSubjects(
	 *     new ListSubjectsRequest.Builder(
	 *         new Resource.Builder("folder").setId("folder_1").build(),
	 *         "update"
	 *     ).build()
	 * );
	 * }
	 */
	public ListSubjectsResponse listSubjects(ListSubjectsRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/list-subjects", request, ListSubjectsResponse.class);
	}
}
