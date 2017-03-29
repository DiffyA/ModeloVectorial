package com.RAI.ModeloVectorial.diccionario;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.transformacion.Indizador;

import java.util.HashMap;
import java.util.Vector;

public class Diccionario {

    private HashMap<String, Vector<Entry>> allTerms;

    public Diccionario() {
    	allTerms = new HashMap<String, Vector<Entry>>();
    }

    public void addDictionaryEntry(Documento toAdd, String docText){
        String[] docContent = docText.split("\\s");

        for (String term : docContent){
            addTerm(toAdd, term);
        }
    }

    public Vector<Documento> searchDocumentsContainingTerm(String term){

        Vector<Documento> docList = new Vector<Documento>();
        if (!allTerms.containsKey(term)){
            return docList;
        } else {

           for (Entry e : allTerms.get(term)) { docList.add(e.getDocument()); }
           return docList;
        }
    }

    public int searchOccurencesTerm(String term, Documento doc){

        Vector<Entry> docs = allTerms.get(term);
        if (docs == null) return 0;

        for (Entry e : docs) {
            if (e.getDocument() == doc) {
                return e.getCount();
            }
        }
        return 0;
    }

    private void addTerm(Documento doc, String toAdd){

        //Ya existe el termino
        if (allTerms.containsKey(toAdd)){

            Vector<Entry> entryList = allTerms.get(toAdd);
            //El termino existe, pero es la primera ocurrencia en este documento
            for (Entry e : entryList){
                if (e.getDocument() == doc) {
                    return;
                }
            }
            entryList.add(new Entry(doc, Indizador.getTermOccurrence(toAdd, doc)));
            return;
            //El termino existe, y no es la primera ocurrencia

        }
        //No existe el termino
        else {
            Entry entry = new Entry(doc, Indizador.getTermOccurrence(toAdd, doc));
            Vector <Entry> newEntry = new Vector<Entry>();
            newEntry.add(entry);
            allTerms.put(toAdd, newEntry);
        }
    }

}
