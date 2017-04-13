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
		Term term1 = new Term("term1", "term1");
		Documento doc1 = new Documento("doc1");
		
		// Check that the dictionary does not contain the term's string representation as key.
		assertFalse(dicc.getAllTerms().containsKey(term1.getFilteredTerm()));
		
		// Add the term found in the given document
		dicc.addDictionaryEntry(term1, doc1);
		
		// Now check that the dictionary in fact contains the term's hashcode as key.
		assertTrue(dicc.getAllTerms().containsKey(term1.getFilteredTerm()));
		
		// Check the amount of occurrences of term1 in the dictionary is equal to 1.
		Term termInDictionary = dicc.getAllTerms().get(term1.getFilteredTerm());
		int occurrencesInDoc1 = termInDictionary.getTFInDocument(doc1);

		assertEquals(1, occurrencesInDoc1);
		
		// Now we check if it works when adding the same term in the same document.
		
		// Once more, add the term found in the given document
		dicc.addDictionaryEntry(term1, doc1);
		
		// Update the occurrences in doc1.
		occurrencesInDoc1 = termInDictionary.getTFInDocument(doc1);
		
		assertEquals(2, occurrencesInDoc1);
		
		// Now we will check the same for another term, to make sure everything is ok.
		Term term2 = new Term("term2", "term2");
		Documento doc2 = new Documento("doc2");
		
		assertFalse(dicc.getAllTerms().containsKey(term2.getFilteredTerm()));
		
		dicc.addDictionaryEntry(term2, doc1);
		
		assertTrue(dicc.getAllTerms().containsKey(term2.getFilteredTerm()));
		assertEquals(1, dicc.getAllTerms().get(term2.getFilteredTerm()).getTFInDocument(doc1));
		
		// Now check for a different document.
		dicc.addDictionaryEntry(term2, doc2);
		dicc.addDictionaryEntry(term2, doc2);
		
		assertEquals(2, dicc.getAllTerms().get(term2.getFilteredTerm()).getTFInDocument(doc2));
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
		expectedSet.add(term1.getFilteredTerm());
		expectedSet.add(term2.getFilteredTerm());
		expectedSet.add(term3.getFilteredTerm());
		
		assertEquals(expectedSet.size(), dicc.getTermList().size());
		assertTrue(expectedSet.containsAll(dicc.getTermList()));
	}
	
	@Test
	public void testGetDocumentsContainingTerm() {
		Documento doc1 = new Documento("doc1");
		Documento doc2 = new Documento("doc2");
		Documento doc3 = new Documento("doc3");
		
		Term term1 = new Term("term1");
		
		Set<Documento> expectedSet = new HashSet<Documento>();
		
		// Initial safety check.
		assertTrue(expectedSet.containsAll(dicc.getDocumentsContainingTerm(term1)));
		
		// Start adding terms and documents.
		dicc.addDictionaryEntry(term1, doc1);
		
		expectedSet.add(doc1);
		
		assertEquals(expectedSet.size(), dicc.getDocumentsContainingTerm(term1).size());
		assertTrue(expectedSet.containsAll(dicc.getDocumentsContainingTerm(term1)));
		
		// Add more terms and documents.
		dicc.addDictionaryEntry(term1, doc2);
		dicc.addDictionaryEntry(term1, doc3);
		
		expectedSet.add(doc2);
		expectedSet.add(doc3);
		
		assertEquals(expectedSet.size(), dicc.getDocumentsContainingTerm(term1).size());
		assertTrue(expectedSet.containsAll(dicc.getDocumentsContainingTerm(term1)));
	}
}
