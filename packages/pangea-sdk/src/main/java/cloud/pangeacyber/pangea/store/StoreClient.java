package cloud.pangeacyber.pangea.store;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.store.requests.*;
import cloud.pangeacyber.pangea.store.responses.*;
import java.io.File;

/// TODO: Docs
public class StoreClient extends BaseClient {

	public static final String serviceName = "store";

	public StoreClient(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public StoreClient build() {
			return new StoreClient(this);
		}
	}

	public DeleteResponse delete(DeleteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/delete", request, DeleteResponse.class);
	}

	public FolderCreateResponse folderCreate(FolderCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/folder/create", request, FolderCreateResponse.class);
	}

	public GetResponse get(GetRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/get", request, GetResponse.class);
	}

	public GetArchiveResponse getArchive(GetArchiveRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/get_archive", request, GetArchiveResponse.class);
	}

	public UpdateResponse update(UpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/update", request, UpdateResponse.class);
	}

	public ListResponse list(ListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/list", request, ListResponse.class);
	}

	public PutResponse put(PutRequest request, File file) throws PangeaException, PangeaAPIException {
		TransferMethod tm = request.getTransferMethod();

		if (tm == TransferMethod.PUT_URL) {
			throw new PangeaException(String.format("%s not supported. Use requestUploadURL() instead", tm), null);
		}

		if (tm == TransferMethod.POST_URL) {
			FileParams fileParams = Utils.getFileUploadParams(file);
			request.setCRC32c(fileParams.getCRC32C());
			request.setSHA256(fileParams.getSHA256());
			request.setSize(fileParams.getSize());
		}

		FileData fileData = new FileData(file, "file");
		return post("/v1beta/put", request, fileData, PutResponse.class);
	}

	public AcceptedResponse requestUploadURL(PutRequest request) throws PangeaException, PangeaAPIException {
		TransferMethod tm = request.getTransferMethod();
		if (tm == null) {
			tm = TransferMethod.PUT_URL;
		}

		if (tm == TransferMethod.MULTIPART) {
			throw new PangeaException(String.format("%s not supported. Use scan() instead", tm), null);
		}

		if (
			tm == TransferMethod.POST_URL &&
			(request.getCRC32c() == null || request.getSHA256() == null || request.getSize() == null)
		) {
			throw new PangeaException(
				String.format("Should set SHA256, CRC32C and size in order to use %s transfer method", tm),
				null
			);
		}

		return requestPresignedURL("/v1beta/put", request);
	}

	public ShareLinkCreateResponse shareLinkCreate(ShareLinkCreateRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/share/link/create", request, ShareLinkCreateResponse.class);
	}

	public ShareLinkGetResponse shareLinkGet(ShareLinkGetRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/share/link/get", request, ShareLinkGetResponse.class);
	}

	public ShareLinkListResponse shareLinkList(ShareLinkListRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/share/link/list", request, ShareLinkListResponse.class);
	}

	public ShareLinkDeleteResponse shareLinkDelete(ShareLinkDeleteRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/share/link/delete", request, ShareLinkDeleteResponse.class);
	}

	public ShareLinkSendResponse shareLinkSend(ShareLinkSendRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/share/link/send", request, ShareLinkSendResponse.class);
	}

}
