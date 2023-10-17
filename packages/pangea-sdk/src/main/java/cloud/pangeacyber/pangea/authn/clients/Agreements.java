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

	// TODO: Docs
	public AgreementCreateResponse create(AgreementCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/agreements/create", request, AgreementCreateResponse.class);
	}

	// TODO: Docs
	public AgreementDeleteResponse delete(AgreementDeleteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/agreements/delete", request, AgreementDeleteResponse.class);
	}

	// TODO: Docs
	public AgreementUpdateResponse update(AgreementUpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/agreements/update", request, AgreementUpdateResponse.class);
	}

	// TODO: Docs
	public AgreementListResponse list(AgreementListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/agreements/list", request, AgreementListResponse.class);
	}
}
