package com.RAI.ModeloVectorial.logic;

import java.util.HashMap;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.pesos.CalculatorTFIDF;

/**
 * The Vectorizer class takes a document, a dictionary, and a calculator
 * to calculate the vector representation of a document, in the form of a
 * DocumentVector object. Refer to the DocumentVector class for furter information.
 * 
 * It requires a Diccionario object because this is the object that allows us to obtain
 * the IDF values for the terms, which is necessary in order to calculate the weight of the 
 * term. 
 * 
 * @author vdegou
 * @see com.RAI.ModeloVectorial.logic.DocumentVector
 *
 */
public class Vectorizer {
	
	public DocumentVector toVector(Documento doc, Diccionario dicc, CalculatorTFIDF calc) {
		return null;
	}
}
