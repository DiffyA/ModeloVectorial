package com.RAI.ModeloVectorial.similiarities;

import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.logic.DocumentVector;

import java.util.HashMap;

/**
 * Created by kgeetz on 4/26/17.
 */
public class ScalarProductTFIDFCalculator implements Calculator {
    public double calculate(DocumentVector docVec, DocumentVector queryVec){

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
