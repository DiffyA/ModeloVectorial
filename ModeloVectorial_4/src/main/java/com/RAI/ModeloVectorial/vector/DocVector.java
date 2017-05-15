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
public class DocVector {
	private HashMap<Term, Double> vector = new HashMap<Term, Double>();
	private String id;
	
	private HashMap<String, Double> softVector = new HashMap<String, Double>();
	
	public DocVector(HashMap<Term, Double> vector) {
		this.vector = vector;
	}
	
	public DocVector(HashMap<String, Double> vector, String id) {
		this.softVector = vector;
		this.id = id;
	}
	
	/**
	 * Int I does nothing, it's to fix a problem.
	 * @param vector
	 * @param id
	 * @param i
	 */
	public DocVector(HashMap<Term, Double> vector, String id, int i) {
		this.vector = vector;
		this.id = id;
	}
	
	public String getId() {
		return this.id;
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
	
	public HashMap<String, Double> getSoftVector() {
		return this.softVector;
	}
}
