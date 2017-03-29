package com.RAI.ModeloVectorial.diccionario;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Termino;

import java.util.HashMap;
import java.util.Vector;

public class Diccionario {

    private HashMap<Documento,Vector<Termino> > terminosDocumento;
    private Vector<Termino> terminosConsulta;

    public Diccionario() {
    	terminosDocumento = new HashMap<Documento, Vector<Termino>>();
    	terminosConsulta = new Vector<Termino>();
    }

    public void addDictionaryEntry(Documento toAdd, String docText){
        String[] docContent = docText.split("\\s");
        terminosDocumento.put(toAdd, new Vector<Termino>());
        Vector<Termino> terms = terminosDocumento.get(toAdd);
        for (String term : docContent){
            addTerm(terms, term);
        }
    }

    public int buscarTermino(Documento doc, String termino){

        Vector<Termino> terminos = terminosDocumento.get(doc);
        for (Termino t : terminos) {
            if (t.getTermino().equals(termino)){
                return t.getCount();
            }
        }
        return 0;
    }

    private void addTerm(Vector<Termino> list, String toAdd){
        for (Termino t : list){
            if (t.getTermino().equals(toAdd)){
                t.increaseCount();
                return;
            }
        }
        list.add(new Termino(toAdd));
    }

    public HashMap<Documento, Vector<Termino>> getTerminosDocumento() {
        return terminosDocumento;
    }

    public Vector<Termino> getTermVector(Documento doc) {return terminosDocumento.get(doc);}

}
