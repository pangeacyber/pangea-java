package cloud.pangeacyber.pangea.embargo;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class IsoCheckRequest {

	@JsonProperty("iso_code")
	String isoCode;

	public IsoCheckRequest(String isoCode) {
		this.isoCode = isoCode;
	}
}

final class IpCheckRequest {

	@JsonProperty("ip")
	String ip;

	public IpCheckRequest(String ip) {
		this.ip = ip;
	}
}

public class EmbargoClient extends Client {

	public static String serviceName = "embargo";

	public EmbargoClient(Config config) {
		super(config, serviceName);
	}

	/**
	 * ISO Code Check
	 * @pangea.description Check a country code against known sanction and trade embargo lists.
	 * @pangea.operationId embargo_post_v1_iso_check
	 * @param isoCode Check the country against code the enabled embargo lists.
	 * @return IsoCheckResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IsoCheckResponse response = client.isoCheck("CU");
	 * }
	 */
	public IsoCheckResponse isoCheck(String isoCode) throws PangeaException, PangeaAPIException {
		IsoCheckRequest request = new IsoCheckRequest(isoCode);
		IsoCheckResponse resp = doPost("/v1/iso/check", request, IsoCheckResponse.class);
		return resp;
	}

	/**
	 * Check IP
	 * @pangea.description Check an IP against known sanction and trade embargo lists.
	 * @pangea.operationId embargo_post_v1_ip_check
	 * @param ip Geolocate this IP and check the corresponding country against the enabled embargo lists.
	 * @return IpCheckResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpCheckResponse response = client.ipCheck("213.24.238.26");
	 * }
	 */
	public IpCheckResponse ipCheck(String ip) throws PangeaException, PangeaAPIException {
		IpCheckRequest request = new IpCheckRequest(ip);
		IpCheckResponse resp = doPost("/v1/ip/check", request, IpCheckResponse.class);
		return resp;
	}
}
