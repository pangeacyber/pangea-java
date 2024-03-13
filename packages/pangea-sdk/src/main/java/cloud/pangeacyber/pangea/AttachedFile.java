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
		if (filename == null || filename.isBlank()) {
			filename = "defaultName";
		}
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
		if (path == null) {
			throw new PangeaException("path param is null", null);
		}

		try {
			path = AttachedFile.findAvailableFile(path);
		} catch (IOException e) {}

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

	private static Path findAvailableFile(Path filePath) throws IOException {
		Path baseName = filePath.getFileName().toString().contains(".")
			? Paths.get(
				filePath.getFileName().toString().substring(0, filePath.getFileName().toString().lastIndexOf('.'))
			)
			: filePath.getFileName();

		String extension = filePath.getFileName().toString().contains(".")
			? filePath.getFileName().toString().substring(filePath.getFileName().toString().lastIndexOf('.'))
			: "";

		int counter = 1;
		Path availableFilePath = filePath;

		while (Files.exists(availableFilePath)) {
			String newFileName = baseName + "_" + counter + extension;
			availableFilePath = filePath.resolveSibling(newFileName);
			counter++;
		}

		return availableFilePath;
	}
}
