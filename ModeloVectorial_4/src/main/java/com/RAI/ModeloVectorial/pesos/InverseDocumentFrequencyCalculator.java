package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Termino;
import com.RAI.ModeloVectorial.diccionario.Diccionario;

import java.util.Vector;

public class InverseDocumentFrequencyCalculator implements Calculator {

    public double calculate(Diccionario dic, Consulta consulta) {

        //Formula = log( N /n_i )

        int numDocs = 0;
        for (Vector<Termino> v : dic.getTerminosDocumento().values()) {
            numDocs += 1;
        }



        return 0;
    }
}
