package com.RAI.ModeloVectorial.logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.RAI.ModeloVectorial.Interface.ITexto;
import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.pesos.CalculatorTFIDF;
import com.RAI.ModeloVectorial.pesos.IWeightCalculator;
import com.RAI.ModeloVectorial.transformacion.Indizador;

/**
 * The Vectorizer class takes a document, a dictionary, and a calculator
 * to calculate the vector representation of a document, in the form of a
 * DocumentVector object. Refer to the DocumentVector class for furter information.
 * 
 * It requires a Diccionario object because this is the object that allows us to obtain
 * the IDF values for the terms, which is necessary in order to calculate the weight of the 
 * term. 
 * 
 * @author vdegou
 * @see com.RAI.ModeloVectorial.logic.DocumentVector
 *
 */
public class Vectorizer {
	
	public DocumentVector toVector(Consulta query, Diccionario dicc, IWeightCalculator calc) {
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
		DocumentVector queryVector = new DocumentVector(vector);
		
		return queryVector;
	}
	
	public DocumentVector toVector(Documento doc, Diccionario dicc, IWeightCalculator calc) {
		HashMap<Term, Double> vector = new HashMap<Term, Double>();
		
		// Obtain filtered terms of a document
		Set<Term> termsInDocument =  Indizador.filterDocument(doc);
		
		// For each term in the document, we have to find the equivalent Term object stored in the dictionary.
		Set<Term> termsInDictionary = new HashSet<Term>();
		
		// This only gets the Term objects stored in the dictionary that also belong in the document.
		for (Term t : termsInDocument) {
			Term termToAdd = dicc.getAllTerms().get(t.getFilteredTerm());
			// TODO: Make a distinction here with class consulta and copy the IDF from the diccionario class to the term
			termsInDictionary.add(termToAdd);
		}
		
		// For each term, calculate it's weight in the document 
		for (Term t : termsInDictionary) {
			vector.put(t, calc.calculate(t, doc));
		}
		
		DocumentVector docVector = new DocumentVector(vector);
		
		return docVector;
	}
}
