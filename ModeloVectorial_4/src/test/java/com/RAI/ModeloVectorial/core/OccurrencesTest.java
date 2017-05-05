package com.RAI.ModeloVectorial.core;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Occurrences;

public class OccurrencesTest {
	Occurrences occurrences = new Occurrences();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		occurrences.getOccurrences().clear();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddOccurrenceInDocument() {
		Documento doc1 = new Documento("");
		
		// First check that the document does not exist inside the hashmap as a key.
		assertFalse(occurrences.getOccurrences().containsKey(doc1));
		
		// Now we add it
		occurrences.addOccurrenceInDocument(doc1);
		
		// Check that the HashMap now contains doc1 as a key.
		assertTrue(occurrences.getOccurrences().containsKey(doc1));
		
		// We must also check that the occurrence count increased to 1.
		assertEquals(1, (int) occurrences.getOccurrences().get(doc1));
	}
	
	@Test
	public void testGetDocuments() {
		Documento doc1 = new Documento("");
		Documento doc2 = new Documento("");
		Documento doc3 = new Documento("");
		
		Set<Documento> expected = new HashSet<Documento>();
		
		//Add first document to both sets
		occurrences.addOccurrenceInDocument(doc1);
		expected.add(doc1);
		
		assertEquals(expected, occurrences.getDocuments());
		
		// Add second document to both sets
		occurrences.addOccurrenceInDocument(doc2);
		expected.add(doc2);
		
		assertEquals(expected, occurrences.getDocuments());
		
		//Try adding again the first document to the occurrences
		occurrences.addOccurrenceInDocument(doc1);
		
		assertEquals(expected, occurrences.getDocuments());
		
		//Add the last document to both sets
		occurrences.addOccurrenceInDocument(doc3);
		expected.add(doc3);
		
		assertEquals(expected, occurrences.getDocuments());
	}
	
	@Test
	public void testGetOccurrencesInDocument() {
		Documento doc1 = new Documento("");
		Documento doc2 = new Documento("");
		
		// Check that for a non-existing document, the occurrences are 0.
		assertEquals(0, occurrences.getTFInDocument(doc1));
		assertEquals(0, occurrences.getTFInDocument(doc2));
		
		// Now, start adding occurrences and check.
		occurrences.addOccurrenceInDocument(doc1);
		assertEquals(1, occurrences.getTFInDocument(doc1));
		
		occurrences.addOccurrenceInDocument(doc2);
		assertEquals(1, occurrences.getTFInDocument(doc2));
		
		occurrences.addOccurrenceInDocument(doc2);
		assertEquals(2, occurrences.getTFInDocument(doc2));
		
		occurrences.addOccurrenceInDocument(doc1);
		assertEquals(2, occurrences.getTFInDocument(doc1));
	}

}
