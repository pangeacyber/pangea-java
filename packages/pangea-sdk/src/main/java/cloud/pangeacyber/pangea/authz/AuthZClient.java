package cloud.pangeacyber.pangea.authz;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authz.requests.*;
import cloud.pangeacyber.pangea.authz.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class AuthZClient extends BaseClient {

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
	 * Create tuple
	 * @pangea.description This operation creates tuples in the AuthZ Service. The request will fail
	 * if there is no schema or if the tuples do not validate against the schema.
	 * @pangea.operationId tuple_create_post_v1beta_tuple_create
	 * @param request {@link TupleCreateRequest} containing the list of tuples to be created.
	 * @return A {@link TupleCreateResponse} with an empty result.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the tuple creation process.
	 * @pangea.code
	 * {@code
		// TODO: Complete
	 * }
	 */
	public TupleCreateResponse tupleCreate(TupleCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/tuple/create", request, TupleCreateResponse.class);
	}

	/**
	 * Lists tuples
	 * @pangea.description This operation lists tuples in the AuthZ Service based on the provided filter criteria.
	 * @pangea.operationId tuple_list_post_v1beta_tuple_list
	 * @param request The {@link TupleListRequest} containing the filter criteria.
	 * @return A {@link TupleListResponse} with the list of tuples and additional information.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the tuple listing process.
	 * @pangea.code
	 * {@code
		// TODO:
	 * }
	 */
	public TupleListResponse tupleList(TupleListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/tuple/list", request, TupleListResponse.class);
	}

	/**
	 * Deletes tuples
	 * @pangea.description This operation deletes tuples in the AuthZ Service based on the provided deletion request.
	 * @pangea.operationId tuple_delete_post_v1beta_tuple_delete
	 * @param request The {@link TupleDeleteRequest} containing the tuples to be deleted.
	 * @return A {@link TupleDeleteResponse} with information about the deleted tuples.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the tuple deletion process.
	 * @pangea.code
	 * {@code
		// TODO:
	 * }
	 */
	public TupleDeleteResponse tupleDelete(TupleDeleteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/tuple/delete", request, TupleDeleteResponse.class);
	}

	/**
	 * Checks authorization
	 * @pangea.description This operation checks authorization for a specific action on a resource
	 *                      with respect to the provided subject.
	 * @pangea.operationId check_post_v1beta_check
	 * @param request The {@link CheckRequest} containing details for the authorization check.
	 * @return A {@link CheckResponse} indicating whether the action is allowed or not.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the authorization check process.
	 * @pangea.code
	 * {@code
		// TODO:
	 * }
	 */
	public CheckResponse check(CheckRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/check", request, CheckResponse.class);
	}

	/**
	 * Lists resources
	 * @pangea.description This operation lists resources based on the specified criteria.
	 * @pangea.operationId list_resources_post_v1beta_list_resources
	 * @param request The {@link ListResourcesRequest} containing criteria for listing resources.
	 * @return A {@link ListResourcesResponse} with the IDs of resources that match the criteria.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the resource listing process.
	 * @pangea.code
	 * {@code
		// TODO:
	 * }
	 */
	public ListResourcesResponse listResources(ListResourcesRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/list-resources", request, ListResourcesResponse.class);
	}

	/**
	 * Lists subjects
	 * @pangea.description This operation lists subjects based on the specified criteria.
	 * @pangea.operationId list_subjects_post_v1beta_list_subjects
	 * @param request The {@link ListSubjectsRequest} containing criteria for listing subjects.
	 * @return A {@link ListSubjectsResponse} with the subjects that match the criteria.
	 * @throws PangeaException If a general Pangea-related exception occurs.
	 * @throws PangeaAPIException If an API error occurs during the subject listing process.
	 * @pangea.code
	 * {@code
		// TODO:
	 * }
	 */
	public ListSubjectsResponse listSubjects(ListSubjectsRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/list-subjects", request, ListSubjectsResponse.class);
	}
}
