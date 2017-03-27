package com.RAI.ModeloVectorial.transformacion;

import com.RAI.ModeloVectorial.core.Documento;

public class Indizador {

    public void tokenizarTerminos(Documento[] documentos){
        for (Documento doc : documentos){
            String tokenizedText = null;

            try {
                tokenizedText = Tokenizador.removeStopWords(doc.getCleanContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            doc.setCleanContent(tokenizedText);
        }
    }

    public void stemTerminos(Documento[] documentos){

        for (Documento doc : documentos){
            String stemmedContent = Tokenizador.stemTerm(doc.getCleanContent());
            doc.setCleanContent(stemmedContent);
        }
    }
}