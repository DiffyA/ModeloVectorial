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

public class CalculatorTFtest {
	CalculatorTF calculator = new CalculatorTF();

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
		
		/*
		 * Add an entry to the dictionary. TF should equal 1.
		 */
		dicc.addDictionaryEntry(term1, doc1);
		expected = 1;
		result = calculator.calculate(term1, doc1);
		assertEquals(expected, result, 0);
		
		/* Now we add another document and term to the dictionary.
		 * We check that both terms have the correct TF.
		 */
		dicc.addDictionaryEntry(term2, doc2);
		expected = 1;
		result = calculator.calculate(term2, doc2);
		assertEquals(expected, result, 0);
		
		result = calculator.calculate(term1, doc1);
		assertEquals(expected, result, 0);
		
		/*
		 * We add the term multiple times through the dictionary and check once more.
		 */
		dicc.addDictionaryEntry(term2, doc2);
		dicc.addDictionaryEntry(term2, doc2);
		dicc.addDictionaryEntry(term2, doc2);

		expected = 4;
		result = calculator.calculate(term2, doc2);
		assertEquals(expected, result, 0);
		
		dicc.addDictionaryEntry(term1, doc1);
		dicc.addDictionaryEntry(term1, doc1);
		dicc.addDictionaryEntry(term1, doc1);

		result = calculator.calculate(term2, doc2);
		assertEquals(expected, result, 0);
		
		/*
		 * Now we add the terms to other documents and check those.
		 */
		dicc.addDictionaryEntry(term1, doc2);
		expected = 1;
		result = calculator.calculate(term1, doc2);
		assertEquals(expected, result, 0);
		
		dicc.addDictionaryEntry(term1, doc2);
		dicc.addDictionaryEntry(term1, doc2);
		expected = 3;
		result = calculator.calculate(term1, doc2);
		assertEquals(expected, result, 0);
		
		dicc.addDictionaryEntry(term2, doc1);
		expected = 1;
		result = calculator.calculate(term2, doc1);
		assertEquals(expected, result, 0);
		
		dicc.addDictionaryEntry(term2, doc1);
		dicc.addDictionaryEntry(term2, doc1);
		expected = 3;
		result = calculator.calculate(term2, doc1);
		assertEquals(expected, result, 0);
		
	}


}
