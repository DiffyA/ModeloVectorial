package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.transformacion.Indizador;

import java.util.Arrays;
import java.util.Set;
import java.util.Vector;

/**
 * Created by kgeetz on 3/29/17.
 */
public class ScalarProductCalculator implements Calculator{
    public double calculate(Diccionario dic, Documento doc, Consulta consulta, String term) {

        int n_i = dic.getNumDocuments();
        Set<String> terms = dic.getTermList();
        String[] queryTerms = consulta.getCleanContent().toLowerCase().split("//s");
        terms.addAll(Arrays.asList(queryTerms));

        Vector<Double> queryWeights = new Vector<Double>();
        Vector<Double> docWeights = new Vector<Double>();

        for (String s : terms){
            int N = 0;
            if (dic.getAllTerms().get(s) != null)
                N = dic.getAllTerms().get(s).size();
            if (Arrays.asList(queryTerms).contains(s)) { N++; }
            double idf = Math.log10(N/n_i);

            queryWeights.add(idf * Indizador.getTermOccurrence(s, consulta));
            int termOccurrence = dic.getTermOccurrenceInDocument(s, doc);
            if (termOccurrence == -1) termOccurrence = 0;
            docWeights.add(idf * termOccurrence);
        }

        double sum = 0;
        for (int i = 0; i < queryWeights.size(); i++){
            sum += queryWeights.get(i) * docWeights.get(i);
        }

        return sum;
    }
}
