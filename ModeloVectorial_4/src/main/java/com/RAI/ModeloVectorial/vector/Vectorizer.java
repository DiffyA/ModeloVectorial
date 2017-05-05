package com.RAI.ModeloVectorial.vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.pesos.IWeightCalculator;
import com.RAI.ModeloVectorial.transformacion.Indizador;

/**
 * The Vectorizer class takes a document, a dictionary, and a calculator
 * to calculate the vector representation of a document, in the form of a
 * DocumentVector object. Refer to the {@link com.RAI.ModeloVectorial.vector.Vector} class for further information.
 * 
 * It requires a Dictionary object because this is the object that allows us to obtain
 * the IDF values for the terms, which is necessary in order to calculate the weight of the 
 * term. 
 * 
 * @author vdegou
 * @see com.RAI.ModeloVectorial.vector.Vector
 *
 */
public class Vectorizer {
	
	/**
	 * Converts a Query into a Vector object, given a specific dictionary and a weight calculator instance.
	 * @param query
	 * @param dicc
	 * @param calc
	 * @return
	 */
	public Vector toVector(Query query, Dictionary dicc, IWeightCalculator calc) {
		HashMap<Term, Double> vector = new HashMap<Term, Double>();
		
		// Obtain filtered terms of the query
		Set<Term> termsInQuery =  query.getTerms();
		
		// For each term in the query, we have to find its equivalent in the dictionary to read its actual IDF
		for (Term queryTerm : termsInQuery) {
			
			// Update the IDF if dictionary index contains the term
			if (dicc.getAllTerms().containsKey(queryTerm.getFilteredTerm())) {
				queryTerm.setIDF(dicc.getAllTerms().get(queryTerm.getFilteredTerm()).getIDF());
			}
			// Put it in the vector
			vector.put(queryTerm, calc.calculate(queryTerm, query));
		}
		
		// Create the DocumentVector object
		Vector queryVector = new Vector(vector);
		
		return queryVector;
	}
	
	/**
	 * Converts a Document into a Vector object, given a specific dictionary and a weight calculator instance.
	 * 
	 * @param doc
	 * @param dicc
	 * @param calc
	 * @return
	 */
	public Vector toVector(Documento doc, Dictionary dicc, IWeightCalculator calc) {
		HashMap<Term, Double> vector = new HashMap<Term, Double>();
		
		// Obtain filtered terms of a document
		Set<Term> termsInDocument =  Indizador.filterDocument(doc);
		
		// For each term in the document, we have to find the equivalent Term object stored in the dictionary.
		Set<Term> termsInDictionary = new HashSet<Term>();
		
		// This only gets the Term objects stored in the dictionary that also belong in the document.
		for (Term t : termsInDocument) {
			Term termToAdd = dicc.getAllTerms().get(t.getFilteredTerm());
			termsInDictionary.add(termToAdd);
		}
		
		// For each term, calculate it's weight in the document 
		for (Term t : termsInDictionary) {
			vector.put(t, calc.calculate(t, doc));
		}
		
		Vector docVector = new Vector(vector);
		
		return docVector;
	}
}
