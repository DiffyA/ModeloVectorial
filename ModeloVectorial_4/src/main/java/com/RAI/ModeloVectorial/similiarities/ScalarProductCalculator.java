package com.RAI.ModeloVectorial.similiarities;

import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.vector.Vector;

import java.util.*;

/**
 * Created by kgeetz on 3/29/17.
 */
public class ScalarProductCalculator implements Calculator {
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
