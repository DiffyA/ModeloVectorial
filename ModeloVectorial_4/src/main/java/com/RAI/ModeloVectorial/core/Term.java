package com.RAI.ModeloVectorial.core;

import java.util.Set;

public class Term {
	private String term;
	private String filteredTerm;
	private Occurrences occurrences = new Occurrences(); 
	
	public Term(String term) {
		this.term = term.trim();
	}
	
	public Term(String term, String filteredTerm) {
		this.term = term.trim();
		this.filteredTerm = filteredTerm.trim();
	}
	
	/**
	 * Returns the list of documents in which the term appears at least once.
	 * @param doc
	 * @return
	 */
	public Set<Documento> getListOfDocuments() {
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
	
	public int getTFInDocument(Documento doc) {
		return occurrences.getTFInDocument(doc);
	}
	
	public String getTerm() {
		return term;
	}
	
	public String getFilteredTerm() {
		return filteredTerm;
	}
	
	@Override
	public String toString() {
		return this.getTerm();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Term other = (Term) obj;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
	
	
}
