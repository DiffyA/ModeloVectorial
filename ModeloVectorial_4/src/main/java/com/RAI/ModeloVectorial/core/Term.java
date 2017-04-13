package com.RAI.ModeloVectorial.core;

public class Term {
	private String term;
	private int IDF;
	
	public Term(String term) {
		this.term = term;
		this.IDF = 0;
	}
	
	public void increaseIDF() {
		this.IDF++;
	}
}
