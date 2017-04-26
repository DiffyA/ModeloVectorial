package com.RAI.ModeloVectorial.similiarities;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.logic.DocumentVector;

/**
 * Created by kgeetz on 3/29/17.
 */
public interface Calculator {

    public double calculate(DocumentVector docVec, DocumentVector queryVec);
}
