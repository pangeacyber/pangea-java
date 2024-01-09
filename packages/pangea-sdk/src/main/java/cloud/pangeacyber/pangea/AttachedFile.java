package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.PangeaException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AttachedFile {

	String filename;
	String contentType;
	byte[] fileContent;

	public AttachedFile(String filename, String contentType, byte[] fileContent) {
		this.filename = filename;
		this.contentType = contentType;
		this.fileContent = fileContent;
	}

	public String getFilename() {
		return filename;
	}

	public String getContentType() {
		return contentType;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void save(Path path) throws PangeaException {
		Path parent = path.getParent().toAbsolutePath();
		if (!Files.exists(parent)) {
			try {
				Files.createDirectories(parent);
			} catch (IOException e) {
				throw new PangeaException("Failed to create file path", e);
			}
		}

		try {
			Files.write(path, this.fileContent);
		} catch (IOException e) {
			throw new PangeaException("Failed to write file", e);
		}
	}
}
