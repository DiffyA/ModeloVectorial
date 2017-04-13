package com.RAI.ModeloVectorial.core;

import java.util.Set;

public class Term {
	private String term;
	private Occurrences occurrences; 
	
	public Term(String term) {
		this.term = term;
	}
	
	public String getTerm() {
		return term;
	}
	
	/**
	 * Returns the list of documents in which the term appears at least once.
	 * @param doc
	 * @return
	 */
	public Set<Documento> getListOfDocuments(Documento doc) {
		return occurrences.getDocuments();
	}
	
	/**
	 * Adds an occurrence of the current term to the specified document.
	 * Also increases the IDF of the current document.
	 * @param doc
	 */
	public void addOccurrenceInDocument(Documento doc) {
		occurrences.addOccurrenceInDocument(doc);
	}
	
	@Override
	public String toString() {
		return this.getTerm();
	}
}
