package com.RAI.ModeloVectorial.similarities;

import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.vector.Vector;

import static java.lang.Math.pow;

/**
 * Class used to calculate the similarity function between two vectors
 * using the Cosine formula.
 * 
 * @author kgeetz
 *
 */
public class CosineCalculator implements ISimilarityFunction {
	
    public double calculate(Vector docVec, Vector queryVec) {

        ISimilarityFunction calc = new ScalarProductCalculator();
        double scalarProduct = calc.calculate(docVec,queryVec);

        double divisor = 0;
        double queryDivisor = 0;

        for (Term t : docVec.getVector().keySet()){
            divisor += pow(docVec.getVector().get(t), 2);
        }


        for (Term t : queryVec.getVector().keySet()){
            queryDivisor += pow(queryVec.getVector().get(t), 2);
        }

        divisor = Math.sqrt(divisor);
        divisor *= Math.sqrt(queryDivisor);

        return scalarProduct/divisor;
    }
}
