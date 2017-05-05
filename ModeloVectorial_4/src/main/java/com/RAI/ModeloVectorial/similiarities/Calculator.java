package com.RAI.ModeloVectorial.similiarities;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.vector.Vector;
import com.RAI.ModeloVectorial.core.Documento;

/**
 * Created by kgeetz on 3/29/17.
 */
public interface Calculator {

    public double calculate(Vector docVec, Vector queryVec);
}
