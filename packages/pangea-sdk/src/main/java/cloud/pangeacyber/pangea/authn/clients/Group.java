package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.models.GroupInfo;
import cloud.pangeacyber.pangea.authn.models.GroupList;
import cloud.pangeacyber.pangea.authn.models.GroupUserList;
import cloud.pangeacyber.pangea.authn.requests.CreateGroupRequest;
import cloud.pangeacyber.pangea.authn.requests.DeleteGroupRequest;
import cloud.pangeacyber.pangea.authn.requests.GetGroupRequest;
import cloud.pangeacyber.pangea.authn.requests.ListGroupUsersRequest;
import cloud.pangeacyber.pangea.authn.requests.ListGroupsRequest;
import cloud.pangeacyber.pangea.authn.requests.UpdateGroupRequest;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.core.type.TypeReference;

public final class Group extends AuthNBaseClient {

	/** @hidden */
	public Group(final AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Create a new group
	 * @pangea.description Create a new group
	 * @pangea.operationId authn_post_v2_group_create
	 * @pangea.code
	 * {@code
	 * final var request = CreateGroupRequest.builder()
	 * 	.name("my-group")
	 * 	.type("my-type")
	 * 	.description("my-description")
	 * 	.attributes(Map.of("key", "value"))
	 * 	.build();
	 *
	 * final var response = client.group().create(request);
	 * }
	 */
	public Response<GroupInfo> create(final CreateGroupRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/group/create", request, new TypeReference<Response<GroupInfo>>() {});
	}

	/**
	 * Delete a group
	 * @pangea.description Delete a group
	 * @pangea.operationId authn_post_v2_group_delete
	 * @pangea.code
	 * {@code
	 * final var request = DeleteGroupRequest.builder()
	 * 	.id("my-group")
	 * 	.build();
	 *
	 * final var response = client.group().delete(request);
	 * }
	 */
	public Response<Void> delete(final DeleteGroupRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/group/delete", request, new TypeReference<Response<Void>>() {});
	}

	/**
	 * Get group information
	 * @pangea.description Look up a group by ID and return its information
	 * @pangea.operationId authn_post_v2_group_get
	 * @pangea.code
	 * {@code
	 * final var request = GetGroupRequest.builder()
	 * 	.id("my-group")
	 * 	.build();
	 *
	 * final var response = client.group().get(request);
	 * }
	 */
	public Response<GroupInfo> get(final GetGroupRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/group/get", request, new TypeReference<Response<GroupInfo>>() {});
	}

	/**
	 * List groups
	 * @pangea.description List groups
	 * @pangea.operationId authn_post_v2_group_list
	 * @pangea.code
	 * {@code
	 * final var request = ListGroupsRequest.builder()
	 * 	.filter(GroupsFilter.builder()
	 * 		.name("my-group")
	 * 		.build())
	 * 	.build();
	 *
	 * final var response = client.group().list(request);
	 * }
	 */
	public Response<GroupList> list(final ListGroupsRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/group/list", request, new TypeReference<Response<GroupList>>() {});
	}

	/**
	 * List of users assigned to a group
	 * @pangea.description Return a list of ids for users assigned to a group
	 * @pangea.operationId authn_post_v2_group_user_list
	 * @pangea.code
	 * {@code
	 * final var request = ListGroupUsersRequest.builder()
	 * 	.id("my-group")
	 * 	.build();
	 *
	 * final var response = client.group().listUsers(request);
	 * }
	 */
	public Response<GroupUserList> listUsers(final ListGroupUsersRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/group/user/list", request, new TypeReference<Response<GroupUserList>>() {});
	}

	/**
	 * Update group information
	 * @pangea.description Update group information
	 * @pangea.operationId authn_post_v2_group_update
	 * @pangea.code
	 * {@code
	 * final var request = UpdateGroupRequest.builder()
	 * 	.id("my-group")
	 * 	.name("my-new-name")
	 * 	.description("my-new-description")
	 * 	.type("my-new-type")
	 * 	.attributes(Map.of("key", "value"))
	 * 	.build();
	 *
	 * final var response = client.group().update(request);
	 * }
	 */
	public Response<GroupInfo> update(final UpdateGroupRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/group/update", request, new TypeReference<Response<GroupInfo>>() {});
	}
}
