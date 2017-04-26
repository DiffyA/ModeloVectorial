package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.Interface.ITexto;
import com.RAI.ModeloVectorial.core.Term;

public class CalculatorTFIDF implements IWeightCalculator{

	/**
	 * Obtains the TF * IDF weight of a term in a given document.
	 * @param term
	 * @param doc
	 * @return
	 */
	public double calculate(Term term, ITexto doc) {
		int TF = term.getTFInDocument(doc);
		double IDF = term.getIDF(); 
		
		return TF * IDF;
	}
	
}
