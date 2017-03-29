package com.RAI.ModeloVectorial.transformacion;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Documento;

public class InidizadorTest {
	
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
	public void testGetTermOccurrence() {
		Documento doc = new Documento("src/test/resources/wordOccurrenceTest.txt");
		
		int occurrencesHello = 3;
		int occurrencesTimes = 2;
		int occurrencesWords = 2;
		int occurrencesRepeated = 3;
		
		int resultOccurrencesHello = Indizador.getTermOccurrence("Hello", doc);
		int resultOccurrencesTimes = Indizador.getTermOccurrence("times", doc);
		int resultOccurrencesWords = Indizador.getTermOccurrence("Words", doc);
		int resultOccurrencesRepeated = Indizador.getTermOccurrence("Repeated", doc);
		
		assertEquals(occurrencesHello, resultOccurrencesHello);
		assertEquals(occurrencesTimes, resultOccurrencesWords);
		assertEquals(occurrencesWords, resultOccurrencesTimes);
		assertEquals(occurrencesRepeated, resultOccurrencesRepeated);
		
	}

}
