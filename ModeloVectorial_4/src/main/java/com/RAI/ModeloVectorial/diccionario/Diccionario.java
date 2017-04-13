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

public class Diccionario {
	/* The key for the HashMap will be the String representation of the term. 
	 * TODO: make it so the actual key is a stemmed and filtered version of the term.
	 * Probably have to add more attributes to the Term class.
	 */
    private HashMap<String, Term> allTerms = new HashMap<String, Term>();

    /**
     * Adds an entry to the dictionary, provided the term and the document it comes from.
     * 
     * TODO: Find a better key for the terms than the actual hashcode. Maybe make it so the Term object
     * stores the stemmed and cleaned version of the word as well, and use that as a key.
     * @param term
     * @param doc
     */
    public void addDictionaryEntry(Term term, Documento doc){
    	// If the term already exists in the dictionary, retrieve it and operate with it
    	if (allTerms.containsKey(term.getTerm())) {
    		
    		// Get the term and add an occurrence in the provided document.
    		Term updatedTerm = allTerms.get(term.getTerm());
    		updatedTerm.addOccurrenceInDocument(doc);

    		// Update the term in the dictionary.
    		allTerms.put(updatedTerm.getTerm(), updatedTerm);
    	}
    	// If the term doesn't exist in the dictionary, add it.
    	else {
    		term.addOccurrenceInDocument(doc);
    		allTerms.put(term.getTerm(), term);
    	}
    }

    /**
     * Returns a set of documents which contain the specified term.
     * @param term
     * @return
     */
    public Set<Documento> getDocumentsContainingTerm(Term term) {
    	
    	// If the term doesn't exist in the dictionary, return an empty set.
    	if (!allTerms.containsKey(term.getTerm())) {
    		
    		return Collections.emptySet();
    	}
    	
    	return allTerms.get(term.getTerm()).getListOfDocuments();
    }

//    public Vector<Documento> searchDocumentsContainingTerm(String term){
//
//        Vector<Documento> docList = new Vector<Documento>();
//        if (!allTerms.containsKey(term)){
//            return docList;
//        } else {
//
//           for (Occurrences e : allTerms.get(term)) { docList.add(e.getDocument()); }
//           return docList;
//        }
//    }
//
//    public int searchOccurencesTerm(String term, Documento doc){
//
//        Vector<Occurrences> docs = allTerms.get(term);
//        if (docs == null) return 0;
//
//        for (Occurrences e : docs) {
//            if (e.getDocument() == doc) {
//                return e.getCount();
//            }
//        }
//        return 0;
//    }
    
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
    	if (!allTerms.containsKey(term.toString())) {
    		return 0;
    	}

    	return allTerms.get(term.getTerm()).getTFInDocument(doc);
    	
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
}