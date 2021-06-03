package com.test.assignment;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.RestClient;

public class SuggestionTest {

	private static final String REST_URL = "http://query.published.live1.suggest.eu1.fredhopperservices.com/asos_uk/json?scope=//catalog01/en_GB&search=";

	@DataProvider(name = "searchTerms")
	public static Object[][] dataProviderName() {
		return new Object[][] { { "jacket" }, { "shoes" }, { "dress" } };
	}

	@Test(dataProvider = "searchTerms")
	public void suggestionTest(String searchTerm) {
		RestClient restClient = new RestClient();
		List<String> response = new ArrayList<String>();
		response.addAll(restClient.getSearchTermList(REST_URL, searchTerm));

		for (String string : response) {
			assertTrue(string.contains(searchTerm), string + " does not contain the search term: " + searchTerm);
		}
	}
}
