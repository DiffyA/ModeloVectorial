package com.RAI.ModeloVectorial.transformacion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.IText;
import com.RAI.ModeloVectorial.core.Occurrences;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;

public class Indizador {
	
	/**
	 * Processes each term in the query, making the necessary changes to the query's set of terms
	 * and each term's TF inside of the query.
	 * @param query
	 * @return
	 */
	public static void processQuery(Query query) {
		String queryString = query.getCleanContent();
		String[] splitString = queryString.split(" ");
		
		// Runs through each term in the query
		for (String s : splitString) {
        	Term termToAdd;
        	String term = s;
        	String filteredTerm = filterStopWords(term);
        	filteredTerm = stemTerminos(filteredTerm);
        	
        	// Continue if the term passed the filters
        	if (!filteredTerm.equals(" ")) {
        		
        		// If it already exists in the query, update that term's TF
        		for (Term t : query.getTerms()) {
        			if (t.getFilteredTerm().equals(filteredTerm.trim())) {
        				t.addOccurrenceInDocument(query);
        			}
        		}
        		
        		// Add the term to the set of terms in the query. If it's repeated it won't matter since it's a Set.
        		termToAdd = new Term(term.trim(), filteredTerm.trim());
            	termToAdd.addOccurrenceInDocument(query);
            	query.getTerms().add(termToAdd);
    		}
		}
		
	}
	
	public static Set<Term> filterDocument(IText doc) {
		Set<Term> termsInDocument = new HashSet<Term>();
		
		String docText = doc.getCleanContent();
		String[] docTextSplit = docText.split(" ");
		
		for (String s : docTextSplit) {
        	Term termToAdd;
        	String term = s;
        	String filteredTerm = filterStopWords(term);
        	filteredTerm = stemTerminos(filteredTerm);
        	
        	if (!filteredTerm.equals(" ")) {
            	termToAdd = new Term(term.trim(), filteredTerm.trim());
            	termToAdd.addOccurrenceInDocument(doc);
        		termsInDocument.add(termToAdd);
    		}
		}
		
		return termsInDocument;
	}
	
	/**
	 * Takes an array of documents and indexes each term of each document 
	 * in the chosen dictionary object.
	 * @param documentos
	 * @param dic
	 */
    public static void indizar(Documento[] documentos, Dictionary dic) {
        // Iterate through all documents
    	for (Documento doc : documentos){

    		// Get the document text without HTML tags and split it
            String docText = doc.getCleanContent();
            String[] docTextSplit = docText.split(" ");
            
            for (String s : docTextSplit) {
            	Term termToAdd;
            	String term = s;
            	String filteredTerm = filterStopWords(term);
            	filteredTerm = stemTerminos(filteredTerm);
            	
            	// If the term has passed the filters
            	if (!filteredTerm.equals(" ")) {
            		// Create the term object
            		termToAdd = new Term(term, filteredTerm);
            		
            		// Add the term to the dictionary, telling it also which document it comes from.
                	dic.addDictionaryEntry(termToAdd, doc);
            	}
            }
        }
    }

    /**
     * Filters any stop words from the string passed as parameter.
     * This method should receive a single word, not a String of words.
     * If the word is a stop word, " " will be returned. Otherwise, its
     * filtered version according to the method used is returned.
     * 
     * @param toTokenize
     * @return
     * @see com.RAI.ModeloVectorial.transformacion.Tokenizador#removeStopWords()
     */
    public static String filterStopWords(String toTokenize){

            try {
                return Tokenizador.removeStopWords(toTokenize);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    
    /**
     * Returns a String of words that have been stemmed.
     * This method can take a String of multiple words.
     * @param textToStem
     * @return
     * @see com.RAI.ModeloVectorial.transformacion.Tokenizador#stemTerm()
     */
    public static String stemTerminos(String textToStem){ 
    	String[] terms = textToStem.split(" ");
    	StringBuilder stemmedText = new StringBuilder();
    	
    	/* Iterate through each term and stem them individually. We add a space at the end
    	 * because the Porter stemmer automatically removes all trailing whitespaces.
    	 * We need these spaces so it doesn't return a single long word.
    	 */
    	for (String t : terms) {
    		stemmedText.append(Tokenizador.stemTerm(new Term(t))).append(" ");
    	}
    	
    	return stemmedText.toString();
	}
    
}