package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.IText;
import com.RAI.ModeloVectorial.core.Term;

/**
 * Calculator sub-class used when implementing a vector-space model with weights
 * based on the TF of the terms in the documents.
 * @author vdegou
 *
 */
public class CalculatorTF implements IWeightCalculator{

	/**
	 * Obtains the TF weight of a term in a given document.
	 * @param term
	 * @param doc
	 * @return
	 */
	public double calculate(Term term, IText doc) {		
		return term.getTFInDocument(doc);
	}
	
}
