package cloud.pangeacyber.pangea.vault.responses;

import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.vault.results.EncryptStructuredResult;
import java.util.Map;

/**
 * Full response of an encrypt/decrypt structured request.
 *
 * @param <T> Structured data type.
 */
public final class EncryptStructuredResponse<K, V, T extends Map<K, V>>
	extends Response<EncryptStructuredResult<K, V, T>> {}
