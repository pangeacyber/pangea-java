package cloud.pangeacyber.pangea;

import java.io.File;
import java.util.Map;

public class FileData {

	File file;
	String name;
	Map<String, Object> details;

	public FileData(File file, String name, Map<String, Object> details) {
		this.file = file;
		this.name = name;
		this.details = details;
	}

	public FileData(File file, String name) {
		this.file = file;
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public String getName() {
		return name;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}
}
