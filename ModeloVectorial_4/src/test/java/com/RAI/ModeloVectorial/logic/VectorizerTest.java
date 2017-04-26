package com.RAI.ModeloVectorial.logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.pesos.CalculatorTFIDF;
import com.RAI.ModeloVectorial.transformacion.Indizador;

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
		Diccionario dicc = new Diccionario();
		Documento doc1 = new Documento("src/test/resources/testVectorizer/testDocument1.txt");
		Documento doc2 = new Documento("src/test/resources/testVectorizer/testDocument2.txt");
		
		Documento[] docsToIndex = {doc1, doc2};
		
		Indizador.indizar(docsToIndex, dicc);
		
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