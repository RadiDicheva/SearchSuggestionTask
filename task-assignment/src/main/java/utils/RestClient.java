package utils;

import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient {

	private String getResponseBody(String url, String param) {
		Client client = Client.create();

		WebResource webResource = client.resource(url + param);

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		return response.getEntity(String.class);
	}

	public List<String> getSearchTermList(String url, String searchTerm) {

		String responseBody = getResponseBody(url, searchTerm);

		Object document = Configuration.defaultConfiguration().jsonProvider().parse(responseBody);

		List<String> searchTermList = JsonPath.read(document, "$..searchterm");

		return searchTermList;
	}
}
