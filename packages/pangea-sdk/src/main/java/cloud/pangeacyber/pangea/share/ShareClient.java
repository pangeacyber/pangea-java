package cloud.pangeacyber.pangea.share;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.share.requests.*;
import cloud.pangeacyber.pangea.share.responses.*;
import java.io.File;

/** Secure Share client. */
public class ShareClient extends BaseClient {

	/** Service name. */
	public static final String serviceName = "share";

	/**
	 * Constructor.
	 *
	 * @param builder Secure Share client builder.
	 */
	public ShareClient(Builder builder) {
		super(builder, serviceName);
	}

	/** Secure Share client builder. */
	public static class Builder extends BaseClient.Builder<Builder> {

		/**
		 * Constructor.
		 *
		 * @param config Configuration.
		 */
		public Builder(Config config) {
			super(config);
		}

		/** Build a Secure Share client. */
		public ShareClient build() {
			return new ShareClient(this);
		}
	}

	/**
	 * Buckets
	 * @pangea.description Get information on the accessible buckets.
	 * @pangea.operationId share_post_v1_buckets
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * final var response = client.buckets();
	 * }
	 */
	public BucketsResponse buckets() throws PangeaException, PangeaAPIException {
		return post("/v1/buckets", new BaseRequest(), BucketsResponse.class);
	}

	/**
	 * Delete
	 * @pangea.description Delete object by ID.
	 * @pangea.operationId share_post_v1_delete
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.delete(new DeleteRequest.Builder().id("pos_[...]").build());
	 * }
	 */
	public DeleteResponse delete(DeleteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/delete", request, DeleteResponse.class);
	}

	/**
	 * Create a folder
	 * @pangea.description Create a folder, either by name or path and
	 * parent_id.
	 * @pangea.operationId share_post_v1_folder_create
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.folderCreate(
	 *     new FolderCreateRequest.Builder().folder("/path/to/new/folder").build()
	 * );
	 * }
	 */
	public FolderCreateResponse folderCreate(FolderCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/folder/create", request, FolderCreateResponse.class);
	}

	/**
	 * Get an object
	 * @pangea.description Get object. If both ID and path are supplied, the
	 * call will fail if the target object doesn't match both properties.
	 * @pangea.operationId share_post_v1_folder_create
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.get(
	 *     new GetRequest.Builder()
	 *     .id("pos_[...]")
	 *     .transferMethod(TransferMethod.DEST_URL)
	 *     .build()
	 * );
	 * }
	 */
	public GetResponse get(GetRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/get", request, GetResponse.class);
	}

	/**
	 * Get archive
	 * @pangea.description Get an archive file of multiple objects.
	 * @pangea.operationId share_post_v1_get_archive
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.getArchive(
	 *     new GetArchiveRequest.Builder(Arrays.asList("pos_[...]"))
	 *         .format(ArchiveFormat.TAR)
	 *         .transferMethod(TransferMethod.DEST_URL)
	 *         .build()
	 * );
	 * }
	 */
	public GetArchiveResponse getArchive(GetArchiveRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/get_archive", request, GetArchiveResponse.class);
	}

	/**
	 * Update a file
	 * @pangea.description Update a file.
	 * @pangea.operationId share_post_v1_update
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var metadata = new Metadata();
	 * metadata.put("key", "value");
	 * var response = client.update(
	 *     new UpdateRequest.Builder("pos_[...]")
	 *         .metadata(metadata)
	 *         .build()
	 * );
	 * }
	 */
	public UpdateResponse update(UpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/update", request, UpdateResponse.class);
	}

	/**
	 * List
	 * @pangea.description List or filter/search records.
	 * @pangea.operationId share_post_v1_list
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var filterList = new FilterList();
	 * filterList.folder().set("/path/to/folder");
	 * var response = client.list(new ListRequest.Builder().filter(filterList).build());
	 * }
	 */
	public ListResponse list(ListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/list", request, ListResponse.class);
	}

	/**
	 * Upload a file
	 * @pangea.description Upload a file.
	 * @pangea.operationId share_post_v1_put
	 * @param request Request parameters.
	 * @param file File to upload.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.put(
	 *     new PutRequest.Builder()
	 *         .name("foobar")
	 *         .transferMethod(TransferMethod.MULTIPART)
	 *         .build(),
	 *     file
	 * );
	 * }
	 */
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
		} else if (Utils.getFileSize(file) == 0) {
			request.setSize(0);
		}

		FileData fileData = new FileData(file, "file");
		return post("/v1/put", request, fileData, PutResponse.class);
	}

	/**
	 * Request upload URL
	 * @pangea.description Request an upload URL.
	 * @pangea.operationId share_post_v1_put 2
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.requestUploadURL(
	 *     new PutRequest.Builder()
	 *     .name("foobar")
	 *     .transferMethod(TransferMethod.PUT_URL)
	 *     .build()
	 * );
	 * }
	 */
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

		return requestPresignedURL("/v1/put", request);
	}

	/**
	 * Create share links
	 * @pangea.description Create a share link.
	 * @pangea.operationId share_post_v1_share_link_create
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var authenticators = Arrays.asList(
	 *     new Authenticator(AuthenticatorType.PASSWORD, "somepassword")
	 * );
	 * var linkList = Arrays.asList(
	 *     new ShareLinkCreateItem.Builder()
	 *         .targets(Arrays.asList("pos_[...]"))
	 *         .linkType(LinkType.EDITOR)
	 *         .maxAccessCount(3)
	 *         .authenticators(authenticators)
	 *         .build()
	 * );
	 * var response = client.shareLinkCreate(
	 *     new ShareLinkCreateRequest.Builder().links(linkList).build()
	 * );
	 * }
	 */
	public ShareLinkCreateResponse shareLinkCreate(ShareLinkCreateRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/share/link/create", request, ShareLinkCreateResponse.class);
	}

	/**
	 * Get share link
	 * @pangea.description Get a share link.
	 * @pangea.operationId share_post_v1_share_link_get
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.shareLinkGet(new ShareLinkGetRequest.Builder("psl_[...]").build());
	 * }
	 */
	public ShareLinkGetResponse shareLinkGet(ShareLinkGetRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/share/link/get", request, ShareLinkGetResponse.class);
	}

	/**
	 * List share links
	 * @pangea.description Look up share links by filter options.
	 * @pangea.operationId share_post_v1_share_link_list
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.shareLinkList(new ShareLinkListRequest.Builder().build());
	 * }
	 */
	public ShareLinkListResponse shareLinkList(ShareLinkListRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/share/link/list", request, ShareLinkListResponse.class);
	}

	/**
	 * Delete share links
	 * @pangea.description Delete share links.
	 * @pangea.operationId share_post_v1_share_link_delete
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.shareLinkDelete(
	 *     new ShareLinkDeleteRequest.Builder(Arrays.asList("psl_[...]")).build()
	 * );
	 * }
	 */
	public ShareLinkDeleteResponse shareLinkDelete(ShareLinkDeleteRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/share/link/delete", request, ShareLinkDeleteResponse.class);
	}

	/**
	 * Send share links
	 * @pangea.description Send share links.
	 * @pangea.operationId share_post_v1_share_link_send
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.shareLinkSend(
	 *     new ShareLinkSendRequest.Builder(
	 *         Arrays.asList(new ShareLinkSendItem("psl_[...]", "alice@example.org")),
	 *         "bob@example.org"
	 *     ).build()
	 * );
	 * }
	 */
	public ShareLinkSendResponse shareLinkSend(ShareLinkSendRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/share/link/send", request, ShareLinkSendResponse.class);
	}
}
