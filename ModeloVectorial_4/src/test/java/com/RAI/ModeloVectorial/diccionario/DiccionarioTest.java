package com.RAI.ModeloVectorial.diccionario;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
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
		dicc.getAllTerms().clear();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddDictionaryEntry() {
		Term term1 = new Term("term1");
		Documento doc1 = new Documento("doc1");
		
		// Check that the dictionary does not contain the term's string representation as key.
		assertFalse(dicc.getAllTerms().containsKey(term1.getTerm()));
		
		// Add the term found in the given document
		dicc.addDictionaryEntry(term1, doc1);
		
		// Now check that the dictionary in fact contains the term's hashcode as key.
		assertTrue(dicc.getAllTerms().containsKey(term1.getTerm()));
		
		// Check the amount of occurrences of term1 in the dictionary is equal to 1.
		Term termInDictionary = dicc.getAllTerms().get(term1.getTerm());
		int occurrencesInDoc1 = termInDictionary.getTFInDocument(doc1);

		assertEquals(1, occurrencesInDoc1);
		
		// Now we check if it works when adding the same term in the same document.
		
		// Once more, add the term found in the given document
		dicc.addDictionaryEntry(term1, doc1);
		
		// Update the occurrences in doc1.
		occurrencesInDoc1 = termInDictionary.getTFInDocument(doc1);
		
		assertEquals(2, occurrencesInDoc1);
		
		// Now we will check the same for another term, to make sure everything is ok.
		Term term2 = new Term("term2");
		Documento doc2 = new Documento("doc2");
		
		assertFalse(dicc.getAllTerms().containsKey(term2.getTerm()));
		
		dicc.addDictionaryEntry(term2, doc1);
		
		assertTrue(dicc.getAllTerms().containsKey(term2.getTerm()));
		assertEquals(1, dicc.getAllTerms().get(term2.getTerm()).getTFInDocument(doc1));
		
		// Now check for a different document.
		dicc.addDictionaryEntry(term2, doc2);
		dicc.addDictionaryEntry(term2, doc2);
		
		assertEquals(2, dicc.getAllTerms().get(term2.getTerm()).getTFInDocument(doc2));
	}
	
	@Test
	public void testGetTFInDocument() {
		Documento doc1 = new Documento("doc1");
		Documento doc2 = new Documento("doc2");
		Documento doc3 = new Documento("doc3");
		Term term1 = new Term("term1");
		
		// Assert that the occurrence is 0 since nothing has been added yet.
		assertEquals(0, dicc.getTFInDocument(term1, doc1));
		
		// Add an entry
		dicc.addDictionaryEntry(term1, doc1);
		
		// Now check that the value has updated
		assertEquals(1, dicc.getTFInDocument(term1, doc1));
		
		// Add the same entry multiple times and for different documents.
		dicc.addDictionaryEntry(term1, doc1);
		dicc.addDictionaryEntry(term1, doc1);
		dicc.addDictionaryEntry(term1, doc2);
		dicc.addDictionaryEntry(term1, doc3);
		dicc.addDictionaryEntry(term1, doc3);
		
		// Check the values
		assertEquals(3, dicc.getTFInDocument(term1, doc1));
		assertEquals(1, dicc.getTFInDocument(term1, doc2));
		assertEquals(2, dicc.getTFInDocument(term1, doc3));
	}
	
	@Test
	public void testGetDocuments() {
		Documento doc1 = new Documento("doc1");
		Documento doc2 = new Documento("doc2");
		Documento doc3 = new Documento("doc3");
		
		Term term1 = new Term("term1");
		Term term2 = new Term("term2");
		Term term3 = new Term("term3");
		
		Set<Documento> expectedSet = new HashSet<Documento>();
		
		// Check that both sets are equal in size and content
		assertEquals(expectedSet.size(), dicc.getDocumentList().size());
		assertTrue(expectedSet.containsAll(dicc.getDocumentList()));
		
		// Start adding documents to the dictionary
		dicc.addDictionaryEntry(term1, doc1);
		
		// Check the sets
		expectedSet.add(doc1);
		
		assertEquals(expectedSet.size(), dicc.getDocumentList().size());
		assertTrue(expectedSet.containsAll(dicc.getDocumentList()));
		
		// Add more documents
		dicc.addDictionaryEntry(term1, doc2);
		dicc.addDictionaryEntry(term2, doc3);

		// Check the sets
		expectedSet.add(doc2);
		expectedSet.add(doc3);
		
		assertEquals(expectedSet.size(), dicc.getDocumentList().size());
		assertTrue(expectedSet.containsAll(dicc.getDocumentList()));
		
		// Add all terms to all documents and perform final check
		dicc.addDictionaryEntry(term1, doc1);
		dicc.addDictionaryEntry(term1, doc2);
		dicc.addDictionaryEntry(term1, doc3);
		dicc.addDictionaryEntry(term2, doc1);
		dicc.addDictionaryEntry(term2, doc2);
		dicc.addDictionaryEntry(term2, doc3);
		dicc.addDictionaryEntry(term3, doc1);
		dicc.addDictionaryEntry(term3, doc2);
		dicc.addDictionaryEntry(term3, doc3);
		
		assertEquals(expectedSet.size(), dicc.getDocumentList().size());
		assertTrue(expectedSet.containsAll(dicc.getDocumentList()));
	}
	
	@Test
	public void testGetTermList() {
		Documento doc1 = new Documento("doc1");
		
		Term term1 = new Term("term1");
		Term term2 = new Term("term2");
		Term term3 = new Term("term3");
		
		Set<String> expectedSet = new HashSet<String>();
		
		// Initial safety check.
		assertTrue(expectedSet.containsAll(dicc.getTermList()));
		
		// Start adding terms to the dictionary.
		dicc.addDictionaryEntry(term1, doc1);
		dicc.addDictionaryEntry(term2, doc1);
		dicc.addDictionaryEntry(term3, doc1);
		
		// Check the sets.
		expectedSet.add(term1.getTerm());
		expectedSet.add(term2.getTerm());
		expectedSet.add(term3.getTerm());
		
		assertEquals(expectedSet.size(), dicc.getTermList().size());
		assertTrue(expectedSet.containsAll(dicc.getTermList()));
		
		
		
		
	}
	
//	@Test
//	public void testAddDictionaryEntry() {
//		Documento doc1 = new Documento("src/test/resources/testDiccionario/testAddDictionaryEntry.txt");
//		String testString = "These These terms should should go in the term vector";
//		
//		/* Since we are just testing this method by itself, we are introducing a dummy string.
//		 * The proper functionality of this method should be tested in an integration test,
//		 * where the class Indizador is the one to call it, after cleaning up the terms.
//		 */
////		dicc.addDictionaryEntry(doc1, testString);
//
//		Set<String> expected = new HashSet<String>();
//		expected.add("These");
//		expected.add("terms");
//		expected.add("should");
//		expected.add("go");
//		expected.add("in");
//		expected.add("the");
//		expected.add("term");
//		expected.add("vector");
//		
//		Set<String> result = dicc.getAllTerms().keySet();
//		
//		assertEquals(expected, result);
//	}
	
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
