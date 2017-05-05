package com.RAI.ModeloVectorial.pesos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.weightCalculator.CalculatorTFIDF;

public class CalculatorTFIDFtest {
	CalculatorTFIDF calculator = new CalculatorTFIDF();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * We have to involve a dictionary object in this test case because the dictionary
	 * is the object in charge of updating a term's IDF, since it is the class with knowledge
	 * of the collection of terms gathered from all the documents.
	 */
	@Test
	public void testCalculate() {
		Dictionary dicc = new Dictionary();
		Term term1 = new Term("term1", "term1");
		Term term2 = new Term("term2", "term2");
		Documento doc1 = new Documento("doc1");
		Documento doc2 = new Documento("doc2");
				
		double result = calculator.calculate(term1, doc1);
		double expected = 0;
		
		// Verify cleanliness.
		assertEquals(expected, result, 0);
		
		/* Add an occurrence of the term1 in the document
		 * Doing so, changes the values as follows:
		 * TF of term1 in doc1 = 1
		 * IDF of term1 = log ( N / Ni) where N = 1 and Ni = 1. IDF = 0
		 * TF * IDF = 0
		 */
		dicc.addDictionaryEntry(term1, doc1);
		result = calculator.calculate(term1, doc1);
		assertEquals(expected, result, 0);
		
		/* Now we add another document and term to the dictionary.
		 * Doing so, changes the values as follows:
		 * TF of term1 in doc1 = 1
		 * IDF of term1 = log (N / Ni) where N = 2 and Ni = 1. IDF = 0.30102...
		 * TF*IDF = 1 * 0.30102
		 */
		dicc.addDictionaryEntry(term2, doc2);
		expected = 1 * Math.log10(2/1);
		result = calculator.calculate(term1, doc1);
		assertEquals(expected, result, 0);
		
		/* Repeat the previous operation to make sure adding occurrences of a term
		 * in the same document does not mess with the calculations.
		 */
		dicc.addDictionaryEntry(term2, doc2);
		expected = 1 * Math.log10(2/1);
		result = calculator.calculate(term1, doc1);
		assertEquals(expected, result, 0);
		
		/* Make sure the IDF of term2 is also correctly calculated.
		 * TF of term2 in doc2 = 2
		 * IDF of term2 = log (N/ Ni) where N = 2 and Ni = 1. IDF = 0.30102...
		 * TF*IDF = 2 * 0.30102...
		 */
		expected = 2 * Math.log10(2/1);
		result = calculator.calculate(term2, doc2);
		assertEquals(expected, result, 0);
		
		// Assert that the TF*IDF of a term in a document where it does not appear is 0.
		result = calculator.calculate(term1, doc2);
		assertEquals(0, result, 0);
	}

}
