package cloud.pangeacyber.pangea.audit.arweave;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class ArweaveRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("query")
	String query = null;

	public ArweaveRequest(String query) {
		this.query = query;
	}
}

public class Arweave {

	public static String baseURL = "https://arweave.net";
	String treeName;

	public Arweave(String treeName) {
		this.treeName = treeName;
	}

	private String getTransactionURL(String transactionID) {
		return String.format("%s/%s/", baseURL, transactionID);
	}

	private String getGraphqlURL() {
		return String.format("%s/graphql", baseURL);
	}

	private String getQuery(Integer[] treeSizes) {
		List<Integer> list = Arrays.asList(treeSizes);
		String sizes = list.stream().map(n -> String.format("\"%d\"", n)).collect(Collectors.joining(","));

		String query =
			"""
            {
            transactions(
                tags: [
                        {
                            name: \"tree_size\"
                            values: [{tree_sizes}]
                        },
                        {
                            name: \"tree_name\"
                            values: [\"{tree_name}\"]
                        }
                    ]
                ) {
                    edges {
                        node {
                            id
                            tags {
                                name
                                value
                            }
                        }
                    }
                }
            }
            """.replace(
					"{tree_sizes}",
					sizes
				)
				.replace("{tree_name}", this.treeName);

		return query;
	}

	private GraphqlOutput doPostGraphql(Integer[] treeSizes) {
		ObjectMapper mapper = new ObjectMapper();
		ArweaveRequest request = new ArweaveRequest(getQuery(treeSizes));
		String body;

		try {
			body = mapper.writeValueAsString(request);
		} catch (Exception e) {
			return null;
		}

		HttpRequest.Builder builder = HttpRequest.newBuilder();
		builder
			.uri(URI.create(getGraphqlURL()))
			.setHeader("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(body));

		HttpRequest httpRequest = builder.build();
		HttpClient httpClient = buildClient();
		HttpResponse<String> httpResponse;

		try {
			httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
		} catch (Exception e) {
			return null;
		}

		if (httpResponse.statusCode() != 200) {
			return null;
		}

		body = httpResponse.body();
		GraphqlOutput response;
		try {
			response = mapper.readValue(body, GraphqlOutput.class);
		} catch (Exception e) {
			return null;
		}
		return response;
	}

	private HttpResponse<String> doGet(String url) {
		HttpRequest.Builder builder = HttpRequest.newBuilder();
		builder.uri(URI.create(url)).GET();

		HttpRequest httpRequest = builder.build();
		HttpClient httpClient = buildClient();
		HttpResponse<String> httpResponse;
		try {
			httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
		} catch (Exception e) {
			return null;
		}

		if (httpResponse.statusCode() == 302) {
			String newUrl = httpResponse.headers().map().get("Location").get(0);
			return doGet(newUrl);
		}

		if (httpResponse.statusCode() != 200) {
			return null;
		}

		return httpResponse;
	}

	private PublishedRoot doGetRoot(String nodeID) {
		HttpResponse<String> httpResponse = doGet(getTransactionURL(nodeID));
		String body = httpResponse.body();
		ObjectMapper mapper = new ObjectMapper();
		PublishedRoot root;
		try {
			root = mapper.readValue(body, PublishedRoot.class);
		} catch (Exception e) {
			return null;
		}

		return root;
	}

	public Map<Integer, PublishedRoot> getPublishedRoots(Integer[] treeSizes) {
		Map<Integer, PublishedRoot> publishedRoots = new HashMap<Integer, PublishedRoot>();
		if (treeSizes.length == 0) {
			return publishedRoots;
		}

		GraphqlOutput response = doPostGraphql(treeSizes);
		if (response == null) {
			return publishedRoots;
		}

		for (Edge edge : response.getData().getTransactions().getEdges()) {
			String nodeID = edge.getNode().getId();
			Object value = null;
			for (Tag tag : edge.getNode().getTags()) {
				if (tag.getName().equals("tree_size")) {
					value = tag.getValue();
					break;
				}
			}

			if (value != null) {
				PublishedRoot root = doGetRoot(nodeID);
				if (root != null) {
					try {
						publishedRoots.put(Integer.valueOf((String) value), root);
					} catch (Exception e) {}
				}
			}
		}

		return publishedRoots;
	}

	protected HttpClient buildClient() {
		java.net.http.HttpClient.Builder builder = HttpClient.newBuilder();
		builder.connectTimeout(Duration.ofSeconds(20));
		return builder.build();
	}
}
