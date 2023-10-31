package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.*;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class Agreements extends AuthNBaseClient {

	public Agreements(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Create an agreement
	 * @pangea.description Create an agreement.
	 * @pangea.operationId authn_post_v2_agreements_create
	 * @param request
	 * @return AgreementCreateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * AgreementCreateRequest request = new AgreementCreateRequest.Builder(
	 * 	AgreementType.EULA,
	 * 	"EULA_V1",
	 * 	"You agree to behave yourself while logged in.").build();
	 *
	 * AgreementCreateResponse response = client.agreements().create(request);
	 * }
	 */
	public AgreementCreateResponse create(AgreementCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/agreements/create", request, AgreementCreateResponse.class);
	}

	/**
	 * Delete an agreement
	 * @pangea.description Delete an agreement.
	 * @pangea.operationId authn_post_v2_agreements_delete
	 * @param request
	 * @return AgreementDeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * AgreementDeleteRequest request = new AgreementDeleteRequest.Builder(
	 * 	AgreementType.EULA,
	 * 	"peu_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a").build();
	 *
	 * AgreementDeleteResponse response = client.agreements().delete(request);
	 * }
	 */
	public AgreementDeleteResponse delete(AgreementDeleteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/agreements/delete", request, AgreementDeleteResponse.class);
	}

	/**
	 * Update agreement
	 * @pangea.description Update agreement.
	 * @pangea.operationId authn_post_v2_agreements_update
	 * @param request
	 * @return AgreementUpdateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * AgreementUpdateRequest request = new AgreementUpdateRequest.Builder(
	 * 	AgreementType.EULA,
	 * 	"peu_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a"
	 * ).setText("You agree to behave yourself while logged in. Don't be evil.").build();
	 *
	 * AgreementUpdateResponse response = client.agreements().update(request);
	 * }
	 */
	public AgreementUpdateResponse update(AgreementUpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/agreements/update", request, AgreementUpdateResponse.class);
	}

	/**
	 * List agreements
	 * @pangea.description List agreements.
	 * @pangea.operationId authn_post_v2_agreements_list
	 * @param request
	 * @return AgreementListResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * AgreementListRequest request = new AgreementListRequest.Builder().build();
	 *
	 * AgreementListResponse response = client.agreements().list();
	 * }
	 */
	public AgreementListResponse list(AgreementListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/agreements/list", request, AgreementListResponse.class);
	}
}
