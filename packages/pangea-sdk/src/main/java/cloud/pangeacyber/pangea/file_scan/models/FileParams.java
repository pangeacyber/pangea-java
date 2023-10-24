package cloud.pangeacyber.pangea.file_scan.models;

public class FileParams {

	int size;
	String sha256;
	String crc32c;

	public FileParams(int size, String sha256, String crc32c) {
		this.size = size;
		this.sha256 = sha256;
		this.crc32c = crc32c;
	}

	public int getSize() {
		return size;
	}

	public String getSHA256() {
		return sha256;
	}

	public String getCRC32C() {
		return crc32c;
	}
}
