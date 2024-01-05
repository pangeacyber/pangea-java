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

	public void save(String folder, String name) throws PangeaException {
		if (folder == null) {
			folder = "./";
		}
		Path path = Paths.get(folder);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				throw new PangeaException("Failed to create file path", e);
			}
			System.out.println("Directory created: " + path);
		}

		if (name == null) {
			name = filename != null ? filename : "defaultFilename";
		}

		this.save(folder + name);
	}

	public void save(String filePath) throws PangeaException {
		// Convert byte array to Path
		Path path = Paths.get(filePath);

		try {
			Files.write(path, this.fileContent);
		} catch (IOException e) {
			System.out.println(e.toString());
			throw new PangeaException("Failed to write file", e);
		}
	}
}
