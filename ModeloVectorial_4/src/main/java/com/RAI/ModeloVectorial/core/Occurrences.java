package com.RAI.ModeloVectorial.core;

import java.util.HashMap;
import java.util.Set;

/**
 * Represents the occurrences of a term inside of an object implementing the IText interface.
 * In other words, this class is used to count the amount of times a specific term shows up
 * in either a Query or a Document object.
 * 
 * @author vdegou
 * @see com.RAI.ModeloVectorial.Term
 */
public class Occurrences {
	/* The HashMap is composed of a Document and an Integer which represents the times that the term
	 * appears in that document. In other words, that integer represents the TF (term frequency) of the term
	 * in the document which appears as the key.
	 */
	private HashMap<IText, Integer> occurrences = new HashMap<IText, Integer>();
	
	/**
	 * Returns the amount of occurrences in a given document.
	 * @param doc
	 * @return
	 */
    public int getTFInDocument(IText doc) {
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
    public void addOccurrenceInDocument(IText doc){
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
    public Set<IText> getDocuments() {
    	return occurrences.keySet();
    }
    
    /**
     * Returns the hashmap data structure containing the occurrences.
     * @return
     */
    public HashMap<IText, Integer> getOccurrences() {
    	return occurrences;
    }
}
