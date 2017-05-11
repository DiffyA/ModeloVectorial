package com.RAI.ModeloVectorial.transformation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.IText;
import com.RAI.ModeloVectorial.core.Occurrences;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;

/**
 * Class in charge of indexing Documents term by term into Dictionaries.
 * 
 * @author vdegou
 *
 */
public class Indexer {
	
	/**
	 * Processes each term in the Query, making the necessary changes to the query's set of terms
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
	
	/**
	 * Processes each term in the Document, making the necessary changes to the Document's set of terms
	 * and each term's TF inside of the Document.
	 * @param doc
	 * @return
	 */
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
	 * If the boolean value toDatabase is set to false, the information
	 * indexed will not be stored in the integrated database. If it is set to true,
	 * the information will be stored in the database and cleared from main memory once
	 * done.
	 * The pretty print parameter allows for printing of the current term and document to the console.
	 * @param documentos
	 * @param dic
	 */
    public static void indizar(Documento[] documentos, Dictionary dic, boolean prettyPrint) {
        // Iterate through all documents
    	int amountOfDocs = documentos.length;
    	int currentDoc = 0;
    	
    	for (Documento doc : documentos){
    		currentDoc++; 
    		
        	String documentName = doc.toString().substring(doc.toString().lastIndexOf("/")+1);

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
            		
            		// Add it to the dictionary
        			dic.addDictionaryEntry(termToAdd, doc);
            	}
            }
            
            if (prettyPrint)
    			System.out.println("*** Indexed doc: " + currentDoc + " of " + amountOfDocs + " total documents.");
            
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
     * @see com.RAI.ModeloVectorial.transformation.Tokenizer#removeStopWords()
     */
    public static String filterStopWords(String toTokenize){

            try {
                return Tokenizer.removeStopWords(toTokenize);
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
     * @see com.RAI.ModeloVectorial.transformation.Tokenizer#stemTerm()
     */
    public static String stemTerminos(String textToStem){ 
    	String[] terms = textToStem.split(" ");
    	StringBuilder stemmedText = new StringBuilder();
    	
    	/* Iterate through each term and stem them individually. We add a space at the end
    	 * because the Porter stemmer automatically removes all trailing whitespaces.
    	 * We need these spaces so it doesn't return a single long word.
    	 */
    	for (String t : terms) {
    		stemmedText.append(Tokenizer.stemTerm(new Term(t))).append(" ");
    	}
    	
    	return stemmedText.toString();
	}
    
}