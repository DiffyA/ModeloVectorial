package com.RAI.ModeloVectorial.vector;

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
public class Vector {
	private HashMap<Term, Double> vector = new HashMap<Term, Double>();
	
	public Vector(HashMap<Term, Double> vector) {
		this.vector = vector;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		
		for (Term t : vector.keySet()) {
			output.append(t.getFilteredTerm()).append(": ").append(vector.get(t)).append("\n");
		}
		
		return output.toString();
	}

	public HashMap<Term, Double> getVector(){
		return vector;
	}
}
