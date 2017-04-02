package com.RAI.ModeloVectorial.diccionario;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.transformacion.Indizador;

public class DiccionarioTest {
	private Diccionario dicc = new Diccionario();

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

	@Test
	public void testAddDictionaryEntry() {
		Documento doc1 = new Documento("src/test/resources/testDiccionario/testAddDictionaryEntry.txt");
		String testString = "These These terms should should go in the term vector";
		
		/* Since we are just testing this method by itself, we are introducing a dummy string.
		 * The proper functionality of this method should be tested in an integration test,
		 * where the class Indizador is the one to call it, after cleaning up the terms.
		 */
		dicc.addDictionaryEntry(doc1, testString);

		Set<String> expected = new HashSet<String>();
		expected.add("These");
		expected.add("terms");
		expected.add("should");
		expected.add("go");
		expected.add("in");
		expected.add("the");
		expected.add("term");
		expected.add("vector");
		
		Set<String> result = dicc.getAllTerms().keySet();
		
		assertEquals(expected, result);
	}
	
	/**
	 * TODO: Test the method AddTerm() before continuing with termOccurrence. There is a filtering issue.
	 */
	
	/**
	 * TODO: The method in the dictionary to GetTermOccurence depends heavily on the filters applied by the indexer.
	 * This should probably be tested in an integration test with both classes interacting or think of a better
	 * way to uncouple the design of these two classes. 
	 */
	
	/*
	@Test
	public void testGetTermOccurrenceInDocument() {
		Documento doc1 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument1.txt");
		Documento doc2 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument2.txt");
		
		String testString1 = "These These terms should should go in the term vector";
		String testString2 = "This is is another another another term string to test";
		
		// We have to put the test strings through the same filtering process and check if we get the same results
//		String testString1 = Indizador.tokenizarTerminos(testString1);
//		testString1 = Indizador.stemTerminos(testString1);
		
		// Store the occurrences of each term in the first document.
		int occurrencesThese = StringUtils.countMatches(testString1, "These");
		int occurrencesTerms = StringUtils.countMatches(testString1, "terms");
		int occurrencesShould = StringUtils.countMatches(testString1, "should");
		int occurrencesGo = StringUtils.countMatches(testString1, "go");
		int occurrencesIn = StringUtils.countMatches(testString1, "in");
		int occurrencesThe = StringUtils.countMatches(testString1, "the");
		int occurrencesTerm = StringUtils.countMatches(testString1, "term");
		int occurrencesVector = StringUtils.countMatches(testString1, "vector");
		
		
		System.out.println("testString1: " + testString1);
		
		// Add the documents to the dictionary
		dicc.addDictionaryEntry(doc1, testString1);
		dicc.addDictionaryEntry(doc2, testString2);
		
		
		int occTerms = dicc.getTermOccurrenceInDocument("terms", doc1);
		int occShould = dicc.getTermOccurrenceInDocument("should", doc1);
		int occGo = dicc.getTermOccurrenceInDocument("go", doc1);
		
		System.out.println("occurrencesTerms: " + occurrencesTerms + "occTerms: " + occTerms);
		System.out.println("occurrencesShould: " + occurrencesShould + "occShould: " + occShould);
		System.out.println("occurrencesGo: " + occurrencesGo+ "occGo: " + occGo);
		
		System.out.println(dicc.getAllTerms().keySet());
		
		if (occurrencesTerms == dicc.getTermOccurrenceInDocument("terms", doc1)) {
			System.out.println("Success");
		}
		
		assertEquals(occurrencesThese, dicc.getTermOccurrenceInDocument("These", doc1));
		assertEquals(occurrencesTerms, dicc.getTermOccurrenceInDocument("terms", doc1));
		assertEquals(occurrencesShould, dicc.getTermOccurrenceInDocument("should", doc1));
		assertEquals(occurrencesGo, dicc.getTermOccurrenceInDocument("go", doc1));
		assertEquals(occurrencesIn, dicc.getTermOccurrenceInDocument("in", doc1));
		assertEquals(occurrencesThe, dicc.getTermOccurrenceInDocument("the", doc1));
		assertEquals(occurrencesTerm, dicc.getTermOccurrenceInDocument("term", doc1));
		assertEquals(occurrencesVector, dicc.getTermOccurrenceInDocument("vector", doc1));
		
		
		
//		assertEquals(0, dicc.getTermOccurrenceInDocument("This", doc2));
//		assertEquals(2, dicc.getTermOccurrenceInDocument("is", doc2));
//		assertEquals(3, dicc.getTermOccurrenceInDocument("another", doc2));
//		assertEquals(-1, dicc.getTermOccurrenceInDocument("Non-existant", doc2));
	}
	*/

}
