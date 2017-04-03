package com.RAI.ModeloVectorial.transformacion;

import java.util.Arrays;
import java.util.HashSet;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.diccionario.Entry;

public class Indizador {


    public static void indizar(Documento[] documentos, Diccionario dic) {
        for (Documento doc : documentos){

            String docText = doc.getCleanContent();
            docText = tokenizarTerminos(docText);
            docText = stemTerminos(docText);
            
			HashSet<String> docTextNoDuplicates = new HashSet<String>(Arrays.asList(docText.split(" ")));
			for (String s : docTextNoDuplicates) {
				Entry newEntry = new Entry(doc, Indizador.getTermOccurrence(s, doc));
				dic.addDictionaryEntry(s, newEntry);
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
    		stemmedText.append(Tokenizador.stemTerm(t)).append(" ");
    	}
    	
    	return stemmedText.toString();

	}
    
    public static int getTermOccurrence(String term, Documento doc) {
    	// Document must be cleaned before processing
    	String docClean = tokenizarTerminos(doc.getCleanContent());
    	docClean = stemTerminos(docClean);
    	
    	// Term must also be cleaned before processing
    	String termClean = tokenizarTerminos(term);
    	termClean = stemTerminos(termClean);
    	
    	// Term must be trimmed because one of the previous functions adds a trailing whitespace.
    	termClean = termClean.trim();

    	// Split the terms up into an array
    	String[] termsInDocument = docClean.split(" ");
    	int counter = 0;
    	
    	for (String t : termsInDocument) {
    		if (termClean.equals(t)) {
    			counter++;
    		}
    	}
    	
    	return counter;
    }

    public static int getTermOccurrence(String term, Consulta consulta){

		// Split the terms up into an array
		String[] termsInDocument = consulta.getCleanContent().split(" ");
		int counter = 0;

		for (String t : termsInDocument) {
			if (term.equals(t)) {
				counter++;
			}
		}
		return counter;
	}
}