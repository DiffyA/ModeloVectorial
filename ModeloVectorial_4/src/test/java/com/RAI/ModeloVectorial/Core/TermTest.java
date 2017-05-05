package com.RAI.ModeloVectorial.Core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;

public class TermTest {

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
	 * Tests that the dictionary object is properly updating the IDF of the term.
	 * This has to be tested through the dictionary object because it is the class that has
	 * information as to how many documents are being analyzed. It can happen that the Term class
	 * has a different amount of documents stored in its Occurrence object, but the Dictionary object
	 * will always have the correct amount.
	 * 
	 * The formula for the IDF is:
	 * 
	 * idf of "i" = log (N / Ni) 
	 * 
	 * where 
	 * N: total number of documents present in the dictionary
	 * Ni: number of documents in which the term "i" appears.
	 */
	@Test
	public void testUpdateIDF() {
		Dictionary dicc = new Dictionary();
		Term term1 = new Term("term1", "term1");
		Term term2 = new Term("term2", "term2");
		Documento doc1 = new Documento("doc1");
		Documento doc2 = new Documento("doc2");
		
		double expected = 0;
		
		// Check that IDF for term is initialized properly to 0.
		assertEquals(expected, term1.getIDF(), 0);
		
		// A term from one document is added to the dictionary. 
		dicc.addDictionaryEntry(term1, doc1);
		
		// Total of 1 document in dictionary, Term1 appears in 1 of them.
		expected = Math.log10(1/1);
		
		assertEquals(expected, term1.getIDF(), 0);
		
		// Another term from another document is added to the dictionary.
		dicc.addDictionaryEntry(term2, doc2);
		
		// Total of 2 documents in dictionary, Term1 appears in 1 of them.
		expected = Math.log10(2/1);
		
//		assertEquals(expected, term1.getIDF(), 0);
		
		System.out.println(term1.getListOfDocuments());
		
	}

}
