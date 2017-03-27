package com.RAI.ModeloVectorial.core;

public class Termino {
	private String termino;
	private int count;

	public Termino(String termino) {
		this.termino = termino;
		this.count = 0;
	}
	
	public String getTermino() {
		return termino;
	}

	public int getCount() {return count;}
	public void increaseCount() { count ++; }
}
