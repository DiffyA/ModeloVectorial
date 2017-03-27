package com.RAI.ModeloVectorial.diccionario;

import com.RAI.ModeloVectorial.core.Termino;

import java.util.Vector;

public class Diccionario {

    private Vector<Termino> terminos;

    public void agregarTermino(String toAdd){

        for (Termino t : terminos){
            if (t.getTermino().equals(toAdd)){
                t.increaseCount();
                return;
            }
        }
        terminos.add(new Termino(toAdd));
    }

    public int buscarTermino(String termino){

        for (Termino t : terminos) {
            if (t.getTermino().equals(termino)){
                return t.getCount();
            }
        }
        return 0;
    }
}
