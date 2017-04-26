package com.RAI.ModeloVectorial.diccionario;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Occurrences;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.transformacion.Indizador;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * The Diccionario class is in charge of indexing terms and documents.
 * It keeps a set of all the documents and a HashMap of all the terms
 * that have been indexed.
 * 
 * The Diccionario class is also in charge of updating the IDF of the
 * terms it has currently indexed, given that the IDF depends on 
 * the structure containing all terms from all documents, which only this class
 * has.
 * 
 * @author vdegou
 *
 */
public class Diccionario {
	/* The key for the HashMap will be the String representation of the term. 
	 * TODO: make it so the actual key is a stemmed and filtered version of the term.
	 * Probably have to add more attributes to the Term class.
	 */
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
    		
    		// If a new document is added, the IDF of ALL terms in the dictionary must be updated.
			// updateIdfOfAllTerms();
    	}
    	
    	// If the term already exists in the dictionary, retrieve it and operate with it
    	if (allTerms.containsKey(term.getFilteredTerm())) {
    		
    		// Get the term and add an occurrence in the provided document.
    		Term updatedTerm = allTerms.get(term.getFilteredTerm());
    		updatedTerm.addOccurrenceInDocument(doc);

    		// Update the term in the dictionary.
    		allTerms.put(updatedTerm.getFilteredTerm(), updatedTerm);
    	}
    	// If the term doesn't exist in the dictionary, add it.
    	else {
    		term.addOccurrenceInDocument(doc);
    		allTerms.put(term.getFilteredTerm(), term);
    	}
    	
    	/* After everything is done, update the IDF of the term that was added. 
    	 * The IDF of this term must be updated even though all IDF's have been updated 
    	 * since the occurrences of this term have been modified. */
//    	term.updateIDF(getAllDocuments().size(), term.getListOfDocuments().size());
    	updateIdfOfAllTerms();
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
     * Adds a document specified as a parameter to the structure containing all documents in the dictionary.
     * @param doc
     */
    public void addDocument(Documento doc) {
    	allDocuments.add(doc);
    }
    
    /**
     * Returns a set of documents which contain the specified term.
     * @param term
     * @return
     */
    public Set<Documento> getDocumentsContainingTerm(Term term) {
    	
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
    public Set<Documento> getDocumentList() {
    	Set<Documento> resultSet = new HashSet<Documento>();
    	
    	// Iterate through all the terms of the dictionary. This only iterates through the values!
    	for (Term t : allTerms.values()) {
    		resultSet.addAll(t.getListOfDocuments());
    	}
    	
    	return resultSet;
    }
    
    public Set<Documento> getAllDocuments() {
    	return allDocuments;
    }
}