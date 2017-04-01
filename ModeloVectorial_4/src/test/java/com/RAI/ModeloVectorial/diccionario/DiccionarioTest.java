package com.RAI.ModeloVectorial.diccionario;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.index.Term;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;

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
	 * TODO: The method in the dictionary to GetTermOccurence depends heavily on the filters applied by the indexer.
	 * This should probably be tested in an integration test with both classes interacting or think of a better
	 * way to uncouple the design of these two classes. 
	 */
	@Test
	public void testGetTermOccurrenceInDocument() {
		Documento doc1 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument1.txt");
		Documento doc2 = new Documento("src/test/resources/testDiccionario/testGetTermOccurrenceInDocument2.txt");
		
		String testString1 = "These These terms should should go in the term vector";
		String testString2 = "This is is another another another term string to test";
		
		
		
		dicc.addDictionaryEntry(doc1, testString1);
		dicc.addDictionaryEntry(doc2, testString2);
		
//		Entry entry = dicc.getAllTerms().get("These").get(0);
//		System.out.println(entry);
		
		assertEquals(0, dicc.getTermOccurrenceInDocument("These", doc1));
		assertEquals(2, dicc.getTermOccurrenceInDocument("terms", doc1));
		assertEquals(2, dicc.getTermOccurrenceInDocument("should", doc1));
		
		assertEquals(0, dicc.getTermOccurrenceInDocument("This", doc2));
		assertEquals(2, dicc.getTermOccurrenceInDocument("is", doc2));
		assertEquals(3, dicc.getTermOccurrenceInDocument("another", doc2));
//		assertEquals(-1, dicc.getTermOccurrenceInDocument("Non-existant", doc2));
	}

}
