package utils;

import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient {

	public List<String> getSearchTermList(String url, String searchTerm) {
		String responseBody = getResponseBody(url, searchTerm);
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(responseBody);
		List<String> searchTermList = JsonPath.read(document, "$..searchterm");
		return searchTermList;
	}

	private String getResponseBody(String url, String param) {
		Client client = Client.create();
		WebResource webResource = client.resource(String.format(url, param));
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
			throw new IllegalStateException(String.format("The response status is: [%s]", response.getStatus()));
		}

		return response.getEntity(String.class);
	}
}
