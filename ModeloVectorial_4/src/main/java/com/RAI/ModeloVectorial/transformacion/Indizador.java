package com.RAI.ModeloVectorial.transformacion;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;

public class Indizador {


    public static void indizar(Documento[] documentos, Diccionario dic) {
        for (Documento doc : documentos){

            String docText = doc.getCleanContent();
            docText = tokenizarTerminos(docText);
            docText = stemTerminos(docText);
            dic.addDictionaryEntry(doc, docText);
        }
    }

    public static String tokenizarTerminos(String toTokenize){

            try {
                return Tokenizador.removeStopWords(toTokenize);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    public static String stemTerminos(String textToStem){ return Tokenizador.stemTerm(textToStem); }
}