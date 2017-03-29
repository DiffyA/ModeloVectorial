package com.RAI.ModeloVectorial.diccionario;

import com.RAI.ModeloVectorial.core.Documento;

/**
 * Created by kgeetz on 3/30/17.
 */
public class Entry {

    Documento document;
    int count;

    public Entry(Documento doc, int count){
        this.document = doc;
        this.count = count;
    }

    public int getCount(){return count;}
    public Documento getDocument() {return document;}

    public void increaseCount(){count++;}
}
