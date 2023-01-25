package cloud.pangeacyber.pangea.audit.arweave;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GraphqlOutput {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("data")
	Data data;

	public Data getData() {
		return data;
	}
}

final class Data {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transactions")
	Transactions transactions;

	public Transactions getTransactions() {
		return transactions;
	}
}

final class Transactions {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("edges")
	Edge[] edges;

	public Edge[] getEdges() {
		return edges;
	}
}

final class Edge {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("node")
	Node node;

	public Node getNode() {
		return node;
	}
}

final class Node {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tag[] tags;

	public String getId() {
		return id;
	}

	public Tag[] getTags() {
		return tags;
	}
}

final class Tag {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("value")
	Object value;

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}
