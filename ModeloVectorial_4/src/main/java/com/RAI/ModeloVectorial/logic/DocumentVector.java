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
	private HashMap<Term, Float> vector = new HashMap<Term, Float>();
}
