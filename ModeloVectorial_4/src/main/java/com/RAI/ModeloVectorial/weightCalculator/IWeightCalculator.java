package com.RAI.ModeloVectorial.weightCalculator;

import com.RAI.ModeloVectorial.core.IText;
import com.RAI.ModeloVectorial.core.Term;

/**
 * Interface used to represent the different weight calculator classes.
 * @author kgeetz
 *
 */
public interface IWeightCalculator {
	
	/**
	 * Calculates the weight of a term inside a given IText object.
	 * @param term
	 * @param doc
	 * @return
	 */
    public double calculate(Term term, IText doc);
}
