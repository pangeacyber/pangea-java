package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.models.GroupList;
import cloud.pangeacyber.pangea.authn.requests.AssignUserGroupRequest;
import cloud.pangeacyber.pangea.authn.requests.ListUserGroupsRequest;
import cloud.pangeacyber.pangea.authn.requests.RemoveUserGroupRequest;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.core.type.TypeReference;

public final class UserGroup extends AuthNBaseClient {

	/** @hidden */
	public UserGroup(final AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Assign groups to a user
	 * @pangea.description Add a list of groups to a specified user
	 * @pangea.operationId authn_post_v2_user_group_assign
	 * @pangea.code
	 * {@code
	 * final var request = AssignUserGroupRequest.builder()
	 * 	.id("my-user")
	 * 	.groupsIds(List.of("my-group-1", "my-group-2"))
	 * 	.build();
	 *
	 * final var response = client.userGroup().assign(request);
	 * }
	 */
	public Response<Void> assign(final AssignUserGroupRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/user/group/assign", request, new TypeReference<Response<Void>>() {});
	}

	/**
	 * Remove a group assigned to a user
	 * @pangea.description Remove a group assigned to a user
	 * @pangea.operationId authn_post_v2_user_group_remove
	 * @pangea.code
	 * {@code
	 * final var request = RemoveUserGroupRequest.builder()
	 * 	.id("my-user")
	 * 	.groupId("my-group")
	 * 	.build();
	 *
	 * final var response = client.userGroup().remove(request);
	 * }
	 */
	public Response<Void> remove(final RemoveUserGroupRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/user/group/remove", request, new TypeReference<Response<Void>>() {});
	}

	/**
	 * List of groups assigned to a user
	 * @pangea.description Return a list of ids for groups assigned to a user
	 * @pangea.operationId authn_post_v2_user_group_list
	 * @pangea.code
	 * {@code
	 * final var request = ListUserGroupsRequest.builder()
	 * 	.id("my-user")
	 * 	.build();
	 *
	 * final var response = client.userGroup().list(request);
	 * }
	 */
	public Response<GroupList> list(final ListUserGroupsRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/user/group/list", request, new TypeReference<Response<GroupList>>() {});
	}
}
