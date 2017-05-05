package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.IText;
import com.RAI.ModeloVectorial.core.Term;

/**
 * Created by kgeetz on 4/26/17.
 */
public interface IWeightCalculator {
    public double calculate(Term term, IText doc);
}
