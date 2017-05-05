package com.RAI.ModeloVectorial.vector;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.transformation.Indexer;
import com.RAI.ModeloVectorial.vector.Vectorizer;
import com.RAI.ModeloVectorial.weightCalculator.CalculatorTFIDF;

public class VectorizerTest {

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
	public void testToVector() {
		Vectorizer vectorizer = new Vectorizer();
		CalculatorTFIDF calc = new CalculatorTFIDF();
		Dictionary dicc = new Dictionary();
		Documento doc1 = new Documento("src/test/resources/testVectorizer/testDocument1.txt");
		Documento doc2 = new Documento("src/test/resources/testVectorizer/testDocument2.txt");
		
		Documento[] docsToIndex = {doc1, doc2};
		
		Indexer.indizar(docsToIndex, dicc);
		
		System.out.println("Dicc document list: ");
		System.out.println(dicc.getDocumentList());
		
		System.out.println("Dicc term list: ");
		System.out.println(dicc.getTermList());
		
		System.out.println("Doc1");
		System.out.println(vectorizer.toVector(doc1, dicc, calc));
		
		System.out.println("doc2");
		System.out.println(vectorizer.toVector(doc2, dicc, calc));
		
	}

}
