package com.RAI.ModeloVectorial.diccionario;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Occurrences;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.transformacion.Indizador;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Diccionario {
	/* The key for the HashMap will be the hash of the value itself. Sort of an ID for the Term in question.
	 * We use a HashMap because a HashSet will not allow us to retrieve the element without iterating through
	 * the whole structure.
	 */
    private HashMap<Integer, Term> allTerms = new HashMap<Integer,Term>();

    public void addDictionaryEntry(Term term, Documento doc){

//        //Ya existe el termino
//        if (allTerms.containsKey(term)){
//            allTerms.get(term).add(toAdd);
//        }
//        //No existe el termino
//        else {
//            Vector<Occurrences> newTermVector = new Vector<Occurrences>();
//            newTermVector.add(toAdd);
//            allTerms.put(term, newTermVector);
//        }
    	// If the term already exists in the dictionary, retrieve it and operate with it
    	if (allTerms.containsKey(term.hashCode())) {
    		// Get the term and add an occurrence in the provided document.
    		Term updatedTerm = allTerms.get(term.hashCode());
    		updatedTerm.addOccurrenceInDocument(doc);
//    		allTerms.get(term.hashCode()).addOccurrenceInDocument(doc);
    		allTerms.put(updatedTerm.hashCode(), updatedTerm);
    	}
    	// If the term doesn't exist in the dictionary, add it.
    	else {
    		term.addOccurrenceInDocument(doc);
    		allTerms.put(term.hashCode(), term);
    	}
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
    /*
    private void addTerm(Documento doc, String toAdd){

    	// If the string is empty (to filter stopwords), jump out of this method.
    	if (toAdd.equals("")) {
    		return;
    	}
    	
        //Ya existe el termino
        if (allTerms.containsKey(toAdd)){

//        	 Consultar con Kevin
            Vector<Entry> entryList = allTerms.get(toAdd);
            //El termino existe, pero es la primera ocurrencia en este documento
            for (Entry e : entryList){
                if (e.getDocument() == doc) {
                    return;
                }
            }
            entryList.add(new Entry(doc, Indizador.getTermOccurrence(toAdd, doc)));
            return;
            
            //El termino existe, y no es la primera ocurrencia
			
        	
    		/* Finds the entry which references the document provided as a parameter.
    		 * First, obtain all the entries in the dictionary that reference the term.
    		 * Then, iterate through those entries and check which one of them references
    		 * the document provided as parameter.
    		 * Add 1 to the occurrences of the term in that document once found.
    		 */
        	
        	/*
        	Vector<Entry> entryList = allTerms.get(toAdd);
        	for (Entry e : entryList) {
        		if (e.getDocument() == doc) {
        			e.increaseCount();
        		}
        	}

        }
        
        //No existe el termino
        else {
//            Entry entry = new Entry(doc, Indizador.getTermOccurrence(toAdd, doc));
            Entry entry = new Entry(doc, Indizador.getTermOccurrence(toAdd, doc));
            
            /* It is possible that getTermOccurrence changes the Term (mainly because it calls the
             * method which removes stopwords) to "". We do not want the empty string in our dictionary
             * because other terms unassociated to the original one may be reduced to the empty string as well
             * and we would get false data. 
             * For example: "These" is reduced to "" because of the stopword filter. Another word such as "an"
             * may also be reduced to "", increasing the count of that Entry (which should not exist in the first
             * place). For this reason, we only add strings that have not been classified as stopwords.

              
            Vector <Entry> newEntry = new Vector<Entry>();
            newEntry.add(entry);
            allTerms.put(toAdd, newEntry);
        }
    }
    */

    /**
     * Returns the whole dictionary structure.
     * @return
     */
    public HashMap<Integer, Term> getAllTerms() {
		return allTerms;
	}
//    
//    /**
//     * Returns all the terms in the dictionary as a set of strings.
//     * @return
//     */
//    public Set<String> getTermList() {
//    	return allTerms.keySet();
//    }
// 
//    /**
//     * Returns the amount of occurrences of a given term in a given document.
//     * @param term
//     * @param document
//     * @return
//     */
//    public int getTermOccurrenceInDocument(String term, Documento document) {
//    	
//    	// If the term doesn't exist in the dictionary, return -1.
//    	if (!allTerms.containsKey(term)) {
//    		return -1;
//    	}
//    	
//    	// Obtain all the entries for the term
//    	Vector<Occurrences> entries= allTerms.get(term);
//    	
//    	/* Iterate through all the entries, looking for the one 
//    	 * which matches the document sent as a parameter.
//    	 */
//    	for (Occurrences e : entries) {
//    		// Once found, return the amount of occurrences in that document.
//    		if (e.getDocument() == document) {
//    			return e.getCount();
//    		}
//    	}
//    	
//    	// If no term-document combination is found in the dictionary, return -1.
//    	return 0;
//    }
//
//    public Vector<Documento> getDocuments(){
//
//        Vector<Documento> docs = new Vector<Documento>();
//        for (String s : allTerms.keySet()){
//            Vector<Occurrences> entries = allTerms.get(s);
//            for (Occurrences e : entries) {
//                if (!docs.contains(e.getDocument())){
//                    docs.add(e.getDocument());
//                }
//            }
//        }
//        return docs;
//    }
//
//    public int getNumDocuments(){
//        return getDocuments().size();
//    }
}


