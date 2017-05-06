package com.RAI.ModeloVectorial.similarities;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.vector.Vector;
import com.RAI.ModeloVectorial.core.Documento;

/**
 * Interface representing the different similarity functions used to evaluate
 * queries and documents in their vector form. 
 * 
 * @author kgeetz
 *
 */
public interface SimilarityFunction {

	/**
	 * Calculates the similarity between two vectors.
	 * @param docVec
	 * @param queryVec
	 * @return the similarity value
	 */
    public double calculate(Vector docVec, Vector queryVec);
}
