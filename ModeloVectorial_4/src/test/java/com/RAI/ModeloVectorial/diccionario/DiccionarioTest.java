package com.RAI.ModeloVectorial.diccionario;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Termino;

public class DiccionarioTest {
	private Diccionario dicc = new Diccionario();
	private Documento doc1 = new Documento("src/test/resources/test.html");
	
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
		String testString = "These terms should go in the term vector";
		
		
		
		dicc.addDictionaryEntry(doc1, testString);
		
		Vector<Termino> vector = dicc.getTermVector(doc1);
		
		for (Termino t : vector) {
			assertTrue(testString.contains(t.getTermino()));
		}
		
	}

}
