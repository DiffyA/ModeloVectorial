package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.logic.DocumentVector;
import com.RAI.ModeloVectorial.transformacion.Indizador;

import java.util.*;

/**
 * Created by kgeetz on 3/29/17.
 */
public class ScalarProductTFCalculator implements Calculator{
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
