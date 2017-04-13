package com.RAI.ModeloVectorial.transformacion;

import java.util.Arrays;
import java.util.HashSet;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Occurrences;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.diccionario.Diccionario;

public class Indizador {


    public static void indizar(Documento[] documentos, Diccionario dic) {
        // Iterate through all documents
    	for (Documento doc : documentos){

    		// Clean up the text
            String docText = doc.getCleanContent();
//            docText = tokenizarTerminos(docText);
//            docText = stemTerminos(docText);
            
            // 
//			HashSet<String> docTextNoDuplicates = new HashSet<String>(Arrays.asList(docText.split(" ")));
			
//			for (String s : docTextNoDuplicates) {
//				Occurrences newEntry = new Occurrences(doc, Indizador.getTermOccurrence(s, doc));
//				dic.addDictionaryEntry(s, newEntry);
//			}
            
            String[] docTextSplit = docText.split(" ");
            for (String s : docTextSplit) {
            	/* First, we create the Term object and add to it the stemmed and tokenized version of the string
            	 * it represents.
            	 */
            	Term termToAdd;
            	String term = s;
            	String filteredTerm = tokenizarTerminos(term);
            	filteredTerm = stemTerminos(filteredTerm);
            	
            	/* Only create the Term if it has not been reduced to "" or " " because of the filters.
            	 * Normally terms get reduced to "" by the tokenizarTerminos method which removes Stop Words,
            	 * but then a " " gets added in stemTerminos because it is needed in order to split long strings
            	 * if a larger (more than one word) text is being filtered.
            	 */
            	if (!filteredTerm.equals(" ")) {
            		termToAdd = new Term(term, filteredTerm);
            		
            		// Add the term to the dictionary, telling it also which document it comes from.
                	dic.addDictionaryEntry(termToAdd, doc);
            	}
            }
        }
    }

    public static String tokenizarTerminos(String toTokenize){

            try {
                return Tokenizador.removeStopWords(toTokenize);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    public static String stemTerminos(String textToStem){ 
    	String[] terms = textToStem.split(" ");
    	StringBuilder stemmedText = new StringBuilder();
    	
    	/* Iterate through each term and stem them individually. We add a space at the end
    	 * because the Porter stemmer automatically removes all trailing whitespaces.
    	 * We need these spaces so that the terms can be split later on. 
    	 */
    	for (String t : terms) {
    		stemmedText.append(Tokenizador.stemTerm(new Term(t))).append(" ");
    	}
    	
    	return stemmedText.toString();

	}
    
//    public static int getTermOccurrence(String term, Documento doc) {
//    	// Document must be cleaned before processing
//    	String docClean = tokenizarTerminos(doc.getCleanContent());
//    	docClean = stemTerminos(docClean);
//    	
//    	// Term must also be cleaned before processing
//    	String termClean = tokenizarTerminos(term);
//    	termClean = stemTerminos(termClean);
//    	
//    	// Term must be trimmed because one of the previous functions adds a trailing whitespace.
//    	termClean = termClean.trim();
//
//    	// Split the terms up into an array
//    	String[] termsInDocument = docClean.split(" ");
//    	int counter = 0;
//    	
//    	for (String t : termsInDocument) {
//    		if (termClean.equals(t)) {
//    			counter++;
//    		}
//    	}
//    	
//    	return counter;
//    }

//    public static int getTermOccurrence(String term, Consulta consulta){
//
//		// Split the terms up into an array
//		String[] termsInDocument = consulta.getCleanContent().split(" ");
//		int counter = 0;
//
//		for (String t : termsInDocument) {
//			if (term.equals(t)) {
//				counter++;
//			}
//		}
//		return counter;
//	}
}