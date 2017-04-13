package com.RAI.ModeloVectorial.core;

import java.util.HashMap;
import java.util.Set;

public class Occurrences {
	private HashMap<Documento, Integer> occurrences = new HashMap<Documento, Integer>();

	
	/**
	 * Returns the amount of occurrences in a given document.
	 * @param doc
	 * @return
	 */
    public int getTFInDocument(Documento doc) {
    	if (!occurrences.containsKey(doc)) {
    		return 0;
    	}
    	
    	return occurrences.get(doc);
	}
    
    /**
     * Method used to increment the amount of occurrences
     * for a given document. If there is no key in the hashmap
     * for the specified document, the document will be added
     * to the hashmap, and one occurrence will be specified.
     * @param doc
     */
    public void addOccurrenceInDocument(Documento doc){
    	if (!occurrences.containsKey(doc)) {
    		occurrences.put(doc, 1);
    	}
    	else {
    		int currentOccurrences = this.getOccurrences().get(doc);
    		occurrences.put(doc, currentOccurrences + 1);
    	}
	}
    
    /**
     * Returns a set of all the documents in which the term appears
     * at least once.
     * @return
     */
    public Set<Documento> getDocuments() {
    	return occurrences.keySet();
    }
    
    /**
     * Returns the hashmap data structure containing the occurrences.
     * @return
     */
    public HashMap<Documento, Integer> getOccurrences() {
    	return occurrences;
    }
}
