package com.test.assignment;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;
import utils.RestClient;

@Slf4j
public class SuggestionTest {

	private String url;
	RestClient restClient = new RestClient();

	@DataProvider(name = "searchTerms")
	public static Object[][] dataProviderName() {
		return new Object[][] { { "jacket" }, { "shoes" }, { "dress" } };
	}

	@BeforeClass
	public void readConfigurationProperties() {
		try (FileInputStream configurationFile = new FileInputStream("src/test/resources/config.properties")) {
			Properties configuration = new Properties();
			configuration.load(configurationFile);
			url = configuration.getProperty("url");
		} catch (IOException ioe) {
			log.error("Problem with reading the configuration file.");
		}
	}

	@Test(dataProvider = "searchTerms")
	public void suggestionTest(String searchTerm) {
		List<String> response = new ArrayList<String>();
		response.addAll(restClient.getSearchTermList(url, searchTerm));
		for (String string : response) {
			assertTrue(string.contains(searchTerm), string + " does not contain the search term: " + searchTerm);
		}
	}
}