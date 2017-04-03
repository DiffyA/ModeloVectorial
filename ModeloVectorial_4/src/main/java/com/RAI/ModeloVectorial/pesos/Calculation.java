package com.RAI.ModeloVectorial.pesos;

import com.RAI.ModeloVectorial.core.Documento;

/**
 * Created by kgeetz on 4/3/17.
 */
public class Calculation {

    private Documento doc;
    private String term;
    private double calculation;

    public Calculation(Documento doc, String term, double calculation){
        this.calculation = calculation;
        this.doc = doc;
        this.term = term;
    }

    public Calculation(){

    }

    public Documento getDoc() {
        return doc;
    }

    public void setDoc(Documento doc) {
        this.doc = doc;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getCalculation() {
        return calculation;
    }

    public void setCalculation(double calculation) {
        this.calculation = calculation;
    }
}
