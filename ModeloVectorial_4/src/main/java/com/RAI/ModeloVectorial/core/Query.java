package com.RAI.ModeloVectorial.core;

import java.util.HashSet;
import java.util.Set;

import com.RAI.ModeloVectorial.transformation.Indexer;

/**
 * Represents a query. Queries are used to search for documents 
 * indexed inside of a dictionary. Queries contain a set of terms.
 * The amount of repetitions of a certain term inside the query affect
 * the weight of that term, and thus affects the results obtained from searching
 * the dictionary with said query.
 * 
 * @author vdegou
 * @see com.RAI.ModeloVectorial.core.Term
 */
public class Query implements IText{
	private String content;
	private String id;
	private Set<Term> terms = new HashSet<Term>();

	public Query(String consulta) {
		this.content = consulta;
		Indexer.processQuery(this);
	}
	
	public Query(String id, String query) {
		this.id = id;
		this.content = query;
		Indexer.processQuery(this);
	}
	
	public String getCleanContent() {
		return content;
	}
	
	public Set<Term> getTerms() {
		return terms;
	}
	
	public String getId() {
		return id;
	}
	
}
