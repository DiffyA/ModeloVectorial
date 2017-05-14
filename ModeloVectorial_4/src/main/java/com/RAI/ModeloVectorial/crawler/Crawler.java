package com.RAI.ModeloVectorial.crawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.RAI.ModeloVectorial.core.Query;

public class Crawler {

	/**
	 * Obtains the queries from a file. Queries must be enveloped in XML "title" tags, as follows:
	 * <title>This is the query</title>
	 * 
	 * @param filepath
	 */
	public static ArrayList<Query> getQueries(String filepath) {
		ArrayList<Query> queries = new ArrayList<Query>();
		
		Document file = null; 
		try {
			file = Jsoup.parse(new File(filepath), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Obtain the elements where the tag is "topic".
		Elements queryTopics = file.getElementsByTag("topic");
		
		// Run through each "topic" element in the file
		for (Element topic : queryTopics) {
			// Get the "title" element, which contains the actual query
			Elements queryContent = topic.getElementsByTag("title");
			
			// Create the query with the "topic" id and the query content
			Query queryToAdd = new Query(topic.attr("id"), queryContent.text());
			
			// Add it to the list
			queries.add(queryToAdd);
		}
		
		return queries;
	}

	
}