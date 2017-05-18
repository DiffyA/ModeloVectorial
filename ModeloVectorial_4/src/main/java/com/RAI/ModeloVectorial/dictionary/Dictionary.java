package com.RAI.ModeloVectorial.dictionary;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.IText;
import com.RAI.ModeloVectorial.core.Occurrences;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.transformation.Indexer;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * The Dictionary class is in charge of indexing terms and documents.
 * It keeps a set of all the documents and a HashMap of all the terms
 * that have been indexed.
 * 
 * The Dictionary class is also in charge of updating the IDF of the
 * terms it has currently indexed, given that the IDF depends on 
 * the structure containing all terms from all documents, which only this class
 * has.
 * 
 * @author vdegou
 *
 */
public class Dictionary {
	// The key for the HashMap will be the filtered String representation of the term. 
    private HashMap<String, Term> allTerms = new HashMap<String, Term>();
    private Set<Documento> allDocuments = new HashSet<Documento>();

    /**
     * Adds an entry to the dictionary, provided the term and the document it comes from.
     * 
     * @param term
     * @param doc
     */
    public void addDictionaryEntry(Term term, Documento doc){
    	
    	// If the document does not exist in the dictionary, add it.
    	if (!allDocuments.contains(doc)) {
    		addDocument(doc);
    		
    		/* If a new document is added, the IDF of ALL terms in the dictionary must be updated, because
    		 * the amount of documents in the dictionary increases which affects the IDF formula.
    		 */
			 updateIdfOfAllTerms();
    	}
    	
    	// If the term already exists in the dictionary, retrieve it and operate with it
    	if (allTerms.containsKey(term.getFilteredTerm())) {
    		
    		// Get the term.
    		Term updatedTerm = allTerms.get(term.getFilteredTerm());
    		
    		// Update term reference to updatedTerm object and add an occurrence in the document.
    		term = updatedTerm;
    		term.addOccurrenceInDocument(doc);
    	}
    	// If the term doesn't exist in the dictionary, add it.
    	else {
    		term.addOccurrenceInDocument(doc);
    		allTerms.put(term.getFilteredTerm(), term);
    	}
    	
    	/* After everything is done, update the IDF of the term that was added. 
    	 * The IDF of this term must be updated even though all IDF's have been updated 
    	 * since the occurrences of this term have been modified. */
    	term.updateIDF(getAllDocuments().size(), term.getListOfDocuments().size());
//    	updateIdfOfAllTerms();
    }
    
    /**
     * Iterates through the terms in the dictionary and updates the IDF value of each one.
     */
    public void updateIdfOfAllTerms() {
		for (String s : allTerms.keySet()) {
			Term currentTerm = allTerms.get(s);
			currentTerm.updateIDF(getAllDocuments().size(), currentTerm.getListOfDocuments().size());
		}
    }
    
    /**
     * Returns a set of term object references stored in the dictionary index.
     * The point of this method is to pass it the terms in a query so it may take these
     * terms, and via their filteredTerm attribute, return the actual occurrences of those
     * terms if they exist within the dictionary.
     * @param terms
     * @return
     */
    public Set<Term> findTerms(Set<Term> terms) {
    	Set<Term> foundTerms = new HashSet<Term>();
    	
    	// Iterate through all the given terms trying to find a match within the dictionary index
    	for (Term t : terms) {
    		Term foundTerm;
    		String termIdentifier = t.getFilteredTerm();
    		
    		// If the index contains a key matching that of the provided term, get the reference
    		if (allTerms.containsKey(termIdentifier)) {
    			foundTerms.add(allTerms.get(termIdentifier));
    		}
    	}
    	
    	return foundTerms;
    }
    
    /**
     * Adds a document specified as a parameter to the structure containing all documents in the dictionary.
     * @param doc
     */
    public void addDocument(Documento doc) {
    	allDocuments.add(doc);
    }
    
    /**
     * Clears the structures containing information.
     */
    public void clearAll() {
    	this.getAllDocuments().clear();
    	this.getAllTerms().clear();
    }
    
    /**
     * Returns a set of documents which contain the specified term.
     * @param term
     * @return
     */
    public Set<IText> getDocumentsContainingTerm(Term term) {
    	
    	// If the term doesn't exist in the dictionary, return an empty set.
    	if (!allTerms.containsKey(term.getFilteredTerm())) {
    		return Collections.emptySet();
    	}
    	
    	return allTerms.get(term.getFilteredTerm()).getListOfDocuments();
    }
    
    /**
     * Returns the whole dictionary structure.
     * @return
     */
    public HashMap<String, Term> getAllTerms() {
		return allTerms;
	}
    
    /**
     * This returns the keys used in the inner dictionary structure, so the term identifier.
     * @return
     */
    public Set<String> getTermList() {
    	return allTerms.keySet();
    }
 
    /**
     * Returns the amount of occurrences of a given term in a given document.
     * @param term
     * @param document
     * @return
     */
    public int getTFInDocument(Term term, Documento doc) {
    	
    	// If the dictionary does not contain a key with the specified term, return 0.
    	if (!allTerms.containsKey(term.getFilteredTerm())) {
    		return 0;
    	}

    	return allTerms.get(term.getFilteredTerm()).getTFInDocument(doc);
    }

    /**
     * Get a set of all the documents in the dictionary.
     * @return
     */
    public Set<IText> getDocumentList() {
    	Set<IText> resultSet = new HashSet<IText>();
    	
    	// Iterate through all the terms of the dictionary. This only iterates through the values!
    	for (Term t : allTerms.values()) {
    		resultSet.addAll(t.getListOfDocuments());
    	}
    	
    	return resultSet;
    }
    
    /**
     * Returns the set containing all the documents which have been indexed
     * in the dictionary.
     * @return
     */
    public Set<Documento> getAllDocuments() {
    	return allDocuments;
    }
}