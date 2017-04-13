package com.RAI.ModeloVectorial.core;

public class Occurrence {
    private Documento document;
    private Term term;
    int count;

    public Occurrence(Documento doc, Term term, int count){
        this.document = doc;
        this.term = term;
        this.count = count;
    }

    public Documento getDocument() {
    	return document;
	}
    
    public Term getTerm() {
    	return term;
    }
    
    public int getCount() {
    	return count;
	}

    public void increaseCount(){
    	count++;
	}
    
    @Override
    public String toString() {
    	return "The term " + this.getTerm() + " appears in document " + this.getDocument() + " a total of " + this.getCount() + " times.";
    }
}
