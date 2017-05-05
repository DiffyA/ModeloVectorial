package com.RAI.ModeloVectorial.core;

import java.util.Set;

public class Term {
	private String term;
	private String filteredTerm;
	private Occurrences occurrences = new Occurrences();
	private double IDF; 
	
	public Term(String term) {
		this.term = term.trim();
	}
	
	public Term(String term, String filteredTerm) {
		this.term = term.trim();
		this.filteredTerm = filteredTerm.trim();
	}
	
//	/**
//	 * Gets the number of times this term appears at least once across the set of documents 
//	 * stored in the occurrences.
//	 * @return
//	 */
//	public int getUniqueAppearances() {
//		return occurrences.getUniqueAppearances();
//	}
	
	/**
	 * Updates the IDF value for this term by calculating the logarithm of the division between
	 * the total amount of documents and the amount of documents in which this term appears in.
	 * @param numberOfDocuments
	 * @param numberOfAppearances
	 */
	public void updateIDF(int numberOfDocuments, int numberOfAppearances) {
		setIDF(Math.log10(numberOfDocuments/numberOfAppearances));
	}
	
	/**
	 * Returns the list of documents in which the term appears at least once.
	 * @param doc
	 * @return
	 */
	public Set<IText> getListOfDocuments() {
		return occurrences.getDocuments();
	}
	
	/**
	 * Adds an occurrence of the current term to the specified document.
	 * Also increases the IDF of the current document.
	 * @param doc
	 */
	public void addOccurrenceInDocument(IText doc) {
		occurrences.addOccurrenceInDocument(doc);
	}
	
	public int getTFInDocument(IText doc) {
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

	public double getIDF() {
		return IDF;
	}

	public void setIDF(double iDF) {
		IDF = iDF;
	}
	
	
}
