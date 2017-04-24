package com.RAI.ModeloVectorial.logic;

import java.util.HashMap;

import com.RAI.ModeloVectorial.core.Term;

/**
 * This class is used to represent a document or query in the form of
 * a vector. 
 * 
 * The vector representation of a document is composed of a set of terms
 * that make up the document or query, and their associated weights with
 * respect to the current working dictionary.
 * 
 * @author vdegou
 *
 */
public class DocumentVector {
	private HashMap<Term, Double> vector = new HashMap<Term, Double>();
	
	public DocumentVector(HashMap<Term, Double> vector) {
		this.vector = vector;
	}
	
	@Override
	public String toString() {
		String output = "";
		
		for (Term t : vector.keySet()) {
			output += t.getFilteredTerm() + ": " + vector.get(t) + "\n";
		}
		
		return output;
	}
}
