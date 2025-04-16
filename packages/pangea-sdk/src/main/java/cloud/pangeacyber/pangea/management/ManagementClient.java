package cloud.pangeacyber.pangea.management;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.management.models.AccessClientCreateInfo;
import cloud.pangeacyber.pangea.management.models.AccessClientInfo;
import cloud.pangeacyber.pangea.management.models.AccessClientListResult;
import cloud.pangeacyber.pangea.management.models.AccessClientSecretInfo;
import cloud.pangeacyber.pangea.management.models.AccessClientSecretInfoListResult;
import cloud.pangeacyber.pangea.management.models.AccessRolesListResult;
import cloud.pangeacyber.pangea.management.models.ListProjectsResult;
import cloud.pangeacyber.pangea.management.models.Organization;
import cloud.pangeacyber.pangea.management.models.Project;
import cloud.pangeacyber.pangea.management.requests.CreateClientRequest;
import cloud.pangeacyber.pangea.management.requests.CreateClientSecretRequest;
import cloud.pangeacyber.pangea.management.requests.CreateProjectRequest;
import cloud.pangeacyber.pangea.management.requests.DeleteProjectRequest;
import cloud.pangeacyber.pangea.management.requests.GetOrgRequest;
import cloud.pangeacyber.pangea.management.requests.GetProjectRequest;
import cloud.pangeacyber.pangea.management.requests.GrantClientAccessRequest;
import cloud.pangeacyber.pangea.management.requests.ListClientRolesRequest;
import cloud.pangeacyber.pangea.management.requests.ListClientSecretMetadataRequest;
import cloud.pangeacyber.pangea.management.requests.ListClientsRequest;
import cloud.pangeacyber.pangea.management.requests.ListProjectsRequest;
import cloud.pangeacyber.pangea.management.requests.RevokeClientAccessRequest;
import cloud.pangeacyber.pangea.management.requests.UpdateClientRequest;
import cloud.pangeacyber.pangea.management.requests.UpdateClientSecretRequest;
import cloud.pangeacyber.pangea.management.requests.UpdateOrgRequest;
import cloud.pangeacyber.pangea.management.requests.UpdateProjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;

final class AuthorizationClient extends BaseClient {

	static final String serviceName = "authorization.access";

	public AuthorizationClient(final Builder builder) {
		super(builder, serviceName);
	}

	public AccessClientCreateInfo createClient(final CreateClientRequest request)
		throws PangeaException, PangeaAPIException {
		return this.postNonPangeaResponse("/v1beta/oauth/clients/register", request);
	}

	public AccessClientListResult listClients(final ListClientsRequest request)
		throws PangeaException, PangeaAPIException {
		return this.getNonPangeaResponse(
				"/v1beta/oauth/clients",
				AuthorizationClient.objectMapper.convertValue(request, new TypeReference<Map<String, String>>() {}),
				new TypeReference<AccessClientListResult>() {}
			);
	}

	public AccessClientInfo getClient(final String clientId) throws PangeaException, PangeaAPIException {
		return this.getNonPangeaResponse(
				String.format("/v1beta/oauth/clients/%s", clientId),
				null,
				new TypeReference<AccessClientInfo>() {}
			);
	}

	public AccessClientInfo updateClient(final String clientId, final UpdateClientRequest options)
		throws PangeaException, PangeaAPIException {
		return this.postNonPangeaResponse(String.format("/v1beta/oauth/clients/%s", clientId), options);
	}

	public void deleteClient(final String clientId) throws PangeaException, PangeaAPIException {
		this.deleteRequest(String.format("/v1beta/oauth/clients/%s", clientId));
	}

	public AccessClientSecretInfo createClientSecret(final CreateClientSecretRequest request)
		throws PangeaException, PangeaAPIException {
		return this.postNonPangeaResponse(
				String.format("/v1beta/oauth/clients/%s/secrets", request.getClientId()),
				request
			);
	}

	public AccessClientSecretInfoListResult listClientSecretMetadata(final ListClientSecretMetadataRequest request)
		throws PangeaException, PangeaAPIException {
		return this.postNonPangeaResponse(
				String.format("/v1beta/oauth/clients/%s/secrets/metadata", request.getId()),
				request
			);
	}

	public void revokeClientSecret(final String id, final String clientSecretId)
		throws PangeaException, PangeaAPIException {
		this.deleteRequest(String.format("/v1beta/oauth/clients/%s/secrets/%s", id, clientSecretId));
	}

	public AccessClientSecretInfo updateClientSecret(
		final String id,
		final String clientSecretId,
		final UpdateClientSecretRequest options
	) throws PangeaException, PangeaAPIException {
		return this.postNonPangeaResponse(
				String.format("/v1beta/oauth/clients/%s/secrets/%s", id, clientSecretId),
				options
			);
	}

	public AccessRolesListResult listClientRoles(final String id, final ListClientRolesRequest request)
		throws PangeaException, PangeaAPIException {
		return this.postNonPangeaResponse(String.format("/v1beta/oauth/clients/%s/roles", id), request);
	}

	public void grantClientAccess(final String id, final GrantClientAccessRequest request)
		throws PangeaException, PangeaAPIException {
		this.postNonPangeaResponse(String.format("/v1beta/oauth/clients/%s/grant", id), request);
	}

	public void revokeClientAccess(final String id, final RevokeClientAccessRequest request)
		throws PangeaException, PangeaAPIException {
		this.postNonPangeaResponse(String.format("/v1beta/oauth/clients/%s/revoke", id), request);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(final Config config) {
			super(config);
		}

		public AuthorizationClient build() {
			return new AuthorizationClient(this);
		}
	}

	public static Builder builder(final Config config) {
		return new Builder(config);
	}

	private <Res> Res getNonPangeaResponse(
		final String path,
		final Map<String, String> params,
		final TypeReference<Res> responseTypeRef
	) throws PangeaException, PangeaAPIException {
		final String responseBody = this.get(path, params);
		try {
			return AuthorizationClient.objectMapper.readValue(responseBody, responseTypeRef);
		} catch (Exception e) {
			throw new PangeaException("Failed to parse response", e);
		}
	}

	private <Req extends BaseRequest, Res> Res postNonPangeaResponse(final String path, final Req request)
		throws PangeaException, PangeaAPIException {
		final var responseBody = this.post(path, request);
		try {
			return AuthorizationClient.objectMapper.readValue(responseBody, new TypeReference<Res>() {});
		} catch (Exception e) {
			throw new PangeaException("Failed to parse response", e);
		}
	}
}

final class ConsoleClient extends BaseClient {

	static final String serviceName = "api.console";

	public ConsoleClient(final Builder builder) {
		super(builder, serviceName);
	}

	public Response<Organization> getOrg(final String id) throws PangeaException, PangeaAPIException {
		return this.post(
				"/v1beta/platform/org/get",
				GetOrgRequest.builder().id(id).build(),
				new TypeReference<Response<Organization>>() {}
			);
	}

	public Response<Organization> updateOrg(final String id, final String name)
		throws PangeaException, PangeaAPIException {
		return this.post(
				"/v1beta/platform/org/update",
				UpdateOrgRequest.builder().id(id).name(name).build(),
				new TypeReference<Response<Organization>>() {}
			);
	}

	public Response<Project> getProject(final String id) throws PangeaException, PangeaAPIException {
		return this.post(
				"/v1beta/platform/project/get",
				GetProjectRequest.builder().id(id).build(),
				new TypeReference<Response<Project>>() {}
			);
	}

	public Response<ListProjectsResult> listProjects(final ListProjectsRequest request)
		throws PangeaException, PangeaAPIException {
		return this.post(
				"/v1beta/platform/project/list",
				request,
				new TypeReference<Response<ListProjectsResult>>() {}
			);
	}

	public Response<Project> createProject(final CreateProjectRequest request)
		throws PangeaException, PangeaAPIException {
		return this.post("/v1beta/platform/project/create", request, new TypeReference<Response<Project>>() {});
	}

	public Response<Project> updateProject(final String id, final String name)
		throws PangeaException, PangeaAPIException {
		return this.post(
				"/v1beta/platform/project/update",
				UpdateProjectRequest.builder().id(id).name(name).build(),
				new TypeReference<Response<Project>>() {}
			);
	}

	public Response<Project> deleteProject(final String id) throws PangeaException, PangeaAPIException {
		return this.post(
				"/v1beta/platform/project/delete",
				DeleteProjectRequest.builder().id(id).build(),
				new TypeReference<Response<Project>>() {}
			);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(final Config config) {
			super(config);
		}

		public ConsoleClient build() {
			return new ConsoleClient(this);
		}
	}

	public static Builder builder(final Config config) {
		return new Builder(config);
	}
}

public final class ManagementClient {

	private final AuthorizationClient authorizationClient;
	private final ConsoleClient consoleClient;

	public ManagementClient(final Config config) {
		this.authorizationClient = AuthorizationClient.builder(config).build();
		this.consoleClient = ConsoleClient.builder(config).build();
	}

	/**
	 * Retrieve an organization
	 * @pangea.description Retrieve an organization
	 * @pangea.operationId api.console_post_v1beta_platform_org_get
	 * @param id An Organization Pangea ID
	 */
	public Response<Organization> getOrg(final String id) throws PangeaException, PangeaAPIException {
		return this.consoleClient.getOrg(id);
	}

	/**
	 * Update an organization
	 * @pangea.description Update an organization
	 * @pangea.operationId api.console_post_v1beta_platform_org_update
	 * @param id An Organization Pangea ID
	 * @param name The new name for the organization
	 */
	public Response<Organization> updateOrg(final String id, final String name)
		throws PangeaException, PangeaAPIException {
		return this.consoleClient.updateOrg(id, name);
	}

	/**
	 * Retrieve a project
	 * @pangea.description Retrieve a project
	 * @pangea.operationId api.console_post_v1beta_platform_project_get
	 * @param id A Project Pangea ID
	 */
	public Response<Project> getProject(final String id) throws PangeaException, PangeaAPIException {
		return this.consoleClient.getProject(id);
	}

	/**
	 * List projects
	 * @pangea.description List projects
	 * @pangea.operationId api.console_post_v1beta_platform_project_list
	 * @param request Request parameters
	 */
	public Response<ListProjectsResult> listProjects(final ListProjectsRequest request)
		throws PangeaException, PangeaAPIException {
		return this.consoleClient.listProjects(request);
	}

	/**
	 * Create a project
	 * @pangea.description Create a project
	 * @pangea.operationId api.console_post_v1beta_platform_project_create
	 * @param request Request parameters
	 */
	public Response<Project> createProject(final CreateProjectRequest request)
		throws PangeaException, PangeaAPIException {
		return this.consoleClient.createProject(request);
	}

	/**
	 * Update a project
	 * @pangea.description Update a project
	 * @pangea.operationId api.console_post_v1beta_platform_project_update
	 * @param id A Project Pangea ID
	 * @param name The new name for the project
	 */
	public Response<Project> updateProject(final String id, final String name)
		throws PangeaException, PangeaAPIException {
		return this.consoleClient.updateProject(id, name);
	}

	/**
	 * Delete a project
	 * @pangea.description Delete a project
	 * @pangea.operationId api.console_post_v1beta_platform_project_delete
	 * @param id A Project Pangea ID
	 */
	public Response<Project> deleteProject(final String id) throws PangeaException, PangeaAPIException {
		return this.consoleClient.deleteProject(id);
	}

	/**
	 * Create a client
	 * @pangea.description Create a client
	 * @pangea.operationId createPlatformClient
	 * @param request Request parameters
	 */
	public AccessClientCreateInfo createClient(final CreateClientRequest request)
		throws PangeaException, PangeaAPIException {
		return this.authorizationClient.createClient(request);
	}

	/**
	 * List platform clients
	 * @pangea.description List platform clients
	 * @pangea.operationId listPlatformClients
	 * @param request Request parameters
	 */
	public AccessClientListResult listClients(final ListClientsRequest request)
		throws PangeaException, PangeaAPIException {
		return this.authorizationClient.listClients(request);
	}

	/**
	 * Get a platform client
	 * @pangea.description Get a platform client
	 * @pangea.operationId getPlatformClient
	 */
	public AccessClientInfo getClient(final String clientId) throws PangeaException, PangeaAPIException {
		return this.authorizationClient.getClient(clientId);
	}

	/**
	 * Update a platform client
	 * @pangea.description Update a platform client
	 * @pangea.operationId updatePlatformClient
	 * @param clientId A Client Pangea ID
	 * @param options Request parameters
	 */
	public AccessClientInfo updateClient(final String clientId, final UpdateClientRequest options)
		throws PangeaException, PangeaAPIException {
		return this.authorizationClient.updateClient(clientId, options);
	}

	/**
	 * Delete a platform client
	 * @pangea.description Delete a platform client
	 * @pangea.operationId deletePlatformClient
	 * @param clientId A Client Pangea ID
	 */
	public void deleteClient(final String clientId) throws PangeaException, PangeaAPIException {
		this.authorizationClient.deleteClient(clientId);
	}

	/**
	 * Create client secret
	 * @pangea.description Create client secret
	 * @pangea.operationId createClientSecret
	 * @param request Request parameters
	 */
	public AccessClientSecretInfo createClientSecret(final CreateClientSecretRequest request)
		throws PangeaException, PangeaAPIException {
		return this.authorizationClient.createClientSecret(request);
	}

	/**
	 * List client secret metadata
	 * @pangea.description List client secret metadata
	 * @pangea.operationId listClientSecretMetadata
	 * @param request Request parameters
	 */
	public AccessClientSecretInfoListResult listClientSecretMetadata(final ListClientSecretMetadataRequest request)
		throws PangeaException, PangeaAPIException {
		return this.authorizationClient.listClientSecretMetadata(request);
	}

	/**
	 * Revoke client secret
	 * @pangea.description Revoke client secret
	 * @pangea.operationId revokeClientSecret
	 * @param id Client ID
	 * @param clientSecretId Client secret ID to revoke
	 */
	public void revokeClientSecret(final String id, final String clientSecretId)
		throws PangeaException, PangeaAPIException {
		this.authorizationClient.revokeClientSecret(id, clientSecretId);
	}

	/**
	 * Update client secret
	 * @pangea.description Update client secret
	 * @pangea.operationId updateClientSecret
	 * @param id Client ID
	 * @param clientSecretId Client secret ID to update
	 * @param options Request parameters
	 */
	public AccessClientSecretInfo updateClientSecret(
		final String id,
		final String clientSecretId,
		final UpdateClientSecretRequest options
	) throws PangeaException, PangeaAPIException {
		return this.authorizationClient.updateClientSecret(id, clientSecretId, options);
	}

	/**
	 * List client roles
	 * @pangea.description List client roles
	 * @pangea.operationId listClientRoles
	 * @param id Client ID
	 * @param request Request parameters
	 */
	public AccessRolesListResult listClientRoles(final String id, final ListClientRolesRequest request)
		throws PangeaException, PangeaAPIException {
		return this.authorizationClient.listClientRoles(id, request);
	}

	/**
	 * Grant client access
	 * @pangea.description Grant client access
	 * @pangea.operationId grantClientRoles
	 * @param id Client ID
	 * @param request Request parameters
	 */
	public void grantClientAccess(final String id, final GrantClientAccessRequest request)
		throws PangeaException, PangeaAPIException {
		this.authorizationClient.grantClientAccess(id, request);
	}

	/**
	 * Revoke client access
	 * @pangea.description Revoke client access
	 * @pangea.operationId revokeClientRoles
	 * @param id Client ID
	 * @param request Request parameters
	 */
	public void revokeClientAccess(final String id, final RevokeClientAccessRequest request)
		throws PangeaException, PangeaAPIException {
		this.authorizationClient.revokeClientAccess(id, request);
	}
}
