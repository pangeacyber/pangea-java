package cloud.pangeacyber.pangea.vault.responses;

import java.util.Map;

import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.vault.results.EncryptStructuredResult;

/**
 * Full response of an encrypt/decrypt structured request.
 *
 * @param <T> Structured data type.
 */
public final class EncryptStructuredResponse<K, V, T extends Map<K, V>> extends Response<EncryptStructuredResult<K, V, T>> {}
