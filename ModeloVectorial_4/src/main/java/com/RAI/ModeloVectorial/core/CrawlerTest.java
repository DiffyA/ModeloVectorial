package com.RAI.ModeloVectorial.core;

import java.util.ArrayList;

import com.RAI.ModeloVectorial.crawler.Crawler;

public class CrawlerTest {

	public static void main(String[] args) {
		ArrayList<Query> queries = Crawler.getQueries("src/main/resources/2010-topics.xml");
	
		for (Query query : queries) {
			System.out.println(query.getCleanContent() + " " + query.getId());
		}
	}

}
