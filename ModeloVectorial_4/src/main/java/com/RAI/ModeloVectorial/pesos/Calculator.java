package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;

/**
 * Created by kgeetz on 3/29/17.
 */
public interface Calculator {

    public double calculate(Diccionario dic, Documento doc, Consulta consulta, String term);
}
