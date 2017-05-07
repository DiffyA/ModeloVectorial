package com.RAI.ModeloVectorial.transformation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Occurrences;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.transformation.Indexer;

public class IndexerTest {
	
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

//	@Test
//	public void testGetTermOccurrence() {
//		Documento doc = new Documento("src/test/resources/wordOccurrenceTest.txt");
//		
//		int occurrencesHello = 3;
//		int occurrencesTimes = 2;
//		int occurrencesWords = 2;
//		int occurrencesRepeated = 3;
//		
//		int resultOccurrencesHello = Indizador.getTermOccurrence("Hello", doc);
//		int resultOccurrencesTimes = Indizador.getTermOccurrence("times", doc);
//		int resultOccurrencesWords = Indizador.getTermOccurrence("Words", doc);
//		int resultOccurrencesRepeated = Indizador.getTermOccurrence("Repeated", doc);
//		
//		assertEquals(occurrencesHello, resultOccurrencesHello);
//		assertEquals(occurrencesTimes, resultOccurrencesWords);
//		assertEquals(occurrencesWords, resultOccurrencesTimes);
//		assertEquals(occurrencesRepeated, resultOccurrencesRepeated);
//		
//	}
	
	/**
	 * Checks to see that an array of documents is indexed properly by the Indizador class into a Diccionario object.
	 */
	@Test
	public void integrationTestIndizar01() {
		Dictionary dicc = new Dictionary();

		Documento doc1 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument1.txt");
		Documento doc2 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument2.txt");
		Documento[] arrayDocs = {doc1, doc2};
		
		// Contents of the documents as Strings.
		String testString1 = "These These terms should should go in the term vector";
		String testString2 = "This is is another another another term string to test";
		
		// Index all the documents into the array
		Indexer.indizar(arrayDocs, dicc, false, false);
		
		// Create duplicate strings that go through the same filtering process as the document in the "indizar" method.
		String cleanString1 = Indexer.filterStopWords(testString1);
		cleanString1 = Indexer.stemTerminos(cleanString1);
		
		String cleanString2 = Indexer.filterStopWords(testString2);
		cleanString2 = Indexer.stemTerminos(cleanString2);
		
		// Create a set of strings containing the expected terms of each document.
		Set<String> expectedTerms1 = new HashSet<String>(Arrays.asList(cleanString1.split(" ")));
		Set<String> expectedTerms2 = new HashSet<String>(Arrays.asList(cleanString2.split(" ")));
		
		System.out.println("expectedTerms1: " + expectedTerms1);
		System.out.println("expectedTerms2: " + expectedTerms2);
		
		// Create a set which will contain all results of both previous sets.
		Set<String> expectedTermsTotal = new HashSet<String>();
		expectedTermsTotal.addAll(expectedTerms1); 
		expectedTermsTotal.addAll(expectedTerms2); 
		
		System.out.println("expectedTermsTotal: " + expectedTermsTotal);
		System.out.println("dicc.getTermList(): " + dicc.getTermList());
//		Set<String> resultTerms1 = dicc.getTermList();
		
		assertEquals(expectedTermsTotal, dicc.getTermList());
	}
	
	@Test
	public void integrationTestIndizar02() {
		Dictionary dicc = new Dictionary();

		Documento doc1 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument1.txt");
		Documento doc2 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument2.txt");
		Documento[] arrayDocs = {doc1, doc2};
		
		// Contents of the documents as Strings.
		String testString1 = "These These terms should should go in the term vector";
		String testString2 = "This is is another another another term string to test";
		
		// Index all the documents into the array
		Indexer.indizar(arrayDocs, dicc, false, false);
		
		// Now we must check if the occurrences of each term in different documents are stored properly.
		
		// In the first document
		int occurrenceTerm = 2;
		int occurrenceVector = 1;
		int occurrenceNull = -1;
		
		// In the second document
		int occurrenceAnother = 3;
		int occurrenceTest = 1;
		int occurrenceNone = -1;
		
		assertEquals(occurrenceTerm, dicc.getTFInDocument(new Term("term", "term"), doc1));
	}

	/**
	 * Checks that the indizar method is working properly.
	 */
	@Test
	public void integrationTestIndizar03() {
		System.out.println("TestIndizar03");
		Dictionary dicc = new Dictionary();
		Indexer indexer = new Indexer();
		Documento doc1 = new Documento("src/test/resources/testIndizador/testDocument1.txt");
//		Documento doc2 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument2.txt");
		
		Documento[] docsToAdd = {doc1};
		
		System.out.println("Term list before adding: ");
		System.out.println(dicc.getTermList());
		
		indexer.indizar(docsToAdd, dicc, false, false);
		
		System.out.println("Term list after adding: ");
		System.out.println(dicc.getTermList());
	}
	
//	@Test
//	public void testFilterDocument() {
//		Documento doc1 = new Documento("src/test/resources/testIndizador/testDocument1.txt");
//		
//		Set<String> result = Indizador.filterDocument(doc1);
//		Set<String> expected = new HashSet<String>();
//		
//		expected.add("fall");
//		expected.add("happi");
//		expected.add("should");
//		expected.add("go");
//		expected.add("term");
//		expected.add("vector");
//		expected.add("conflat");
//		expected.add("word");
//		
//		assertEquals(expected, result);
//	}
	
}
