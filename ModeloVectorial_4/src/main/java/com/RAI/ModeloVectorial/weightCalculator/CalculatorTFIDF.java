package com.RAI.ModeloVectorial.weightCalculator;

import com.RAI.ModeloVectorial.core.IText;
import com.RAI.ModeloVectorial.core.Term;

/**
 * Calculator sub-class used when implementing a vector-space model with weights
 * based on the TF*IDF of the terms in the documents.
 * 
 * @author vdegou
 *
 */
public class CalculatorTFIDF implements IWeightCalculator{

	/**
	 * Obtains the TF * IDF weight of a term in a given IText object.
	 * @param term
	 * @param doc
	 * @return
	 */
	public double calculate(Term term, IText doc) {
		int TF = term.getTFInDocument(doc);
		double IDF = term.getIDF(); 
		
		return TF * IDF;
	}
	
}
