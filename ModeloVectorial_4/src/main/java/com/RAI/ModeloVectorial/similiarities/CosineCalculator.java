package com.RAI.ModeloVectorial.similiarities;

import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.vector.Vector;

import static java.lang.Math.pow;

/**
 * Created by kgeetz on 4/26/17.
 */
public class CosineCalculator implements Calculator {
    public double calculate(Vector docVec, Vector queryVec) {

        Calculator calc = new ScalarProductCalculator();
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
