package com.RAI.ModeloVectorial.similarities;

import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.vector.Vector;

import java.util.*;

/**
 * Class used to calculate the similarity function between two vectors
 * using the Scalar Product formula.
 * 
 * @author kgeetz
 *
 */
public class ScalarProductCalculator implements ISimilarityFunction {
    
	public double calculate(Vector docVec, Vector queryVec){

        HashMap<Term, Double> docTerms = docVec.getVector();
        HashMap<Term, Double> queryTerms = queryVec.getVector();

        double sum = 0;
        for (Term t : docTerms.keySet()){
            for ( Term tq : queryTerms.keySet()){
                if (t.equals(tq)){
                    sum += docTerms.get(t) * queryTerms.get(tq);
                }
            }
        }

        return sum;
    }
}
