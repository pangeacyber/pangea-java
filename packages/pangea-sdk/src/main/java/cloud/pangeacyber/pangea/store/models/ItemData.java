package cloud.pangeacyber.pangea.store.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {

	@JsonProperty("id")
	String id;

	@JsonProperty("type")
	String type;

	@JsonProperty("name")
	String name;

	@JsonProperty("created_at")
	String createdAt;

	@JsonProperty("updated_at")
	String updatedAt;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("billable_size")
	Integer billableSize;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("location")
	String location;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("md5")
	String md5;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha256")
	String sha256;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha512")
	String sha512;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parent_id")
	String parentId;

	public ItemData() {}

	public String getID() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public Integer getSize() {
		return size;
	}

	public Integer getBillableSize() {
		return billableSize;
	}

	public String getLocation() {
		return location;
	}

	public Tags getTags() {
		return tags;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public String getMD5() {
		return md5;
	}

	public String getSHA256() {
		return sha256;
	}

	public String getSHA512() {
		return sha512;
	}

	public String getParentID() {
		return parentId;
	}
}
