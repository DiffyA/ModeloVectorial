package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by kgeetz on 3/29/17.
 */
public class WeightCalculator implements Calculator {
    public double calculate(Diccionario dic, Documento doc, Consulta consulta, String term) {

        //Formula = tF * IDF

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


        return idf * dic.getTermOccurrenceInDocument(term, doc);

    }
}
