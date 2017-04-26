package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.logic.DocumentVector;

import java.util.Arrays;
import java.util.Set;

public class InverseDocumentFrequencyCalculator implements Calculator {

    public double calculate(DocumentVector docVec, DocumentVector queryVec){

        //Formula = log( N /n_i )

        int n_i = dic.getNumDocuments();
        Set<String> terms = dic.getTermList();
        String[] queryTerms = consulta.getCleanContent().toLowerCase().split("//s");
        terms.addAll(Arrays.asList(queryTerms));

        double idf = 0;
        int N = 0;
        if (dic.getAllTerms().get(term) != null)
            N = dic.getAllTerms().get(term).size();
        if (Arrays.asList(queryTerms).contains(term)) { N++; }
        idf = Math.log10(N/n_i);


        return idf;
    }
}
