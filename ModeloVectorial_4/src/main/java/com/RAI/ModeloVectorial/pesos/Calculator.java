package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;

/**
 * Created by kgeetz on 3/29/17.
 */
public interface Calculator {

    public double calculate(Documento doc, Consulta consulta);
}
