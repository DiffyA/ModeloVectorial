package com.RAI.ModeloVectorial.transformacion;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TokenizadorTest {

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
	public void testRemoveStopWords() throws Exception {
		String originalString = "The quick brown fox jumps over a dog.";
		
		String expected = "quick brown fox jumps over dog ";
		String result = Tokenizador.removeStopWords(originalString);
		
		assertEquals(expected, result);
	}
	
	@Test
	public void testStemTerm() {
		String example1 = "catty";
		String example2 = "conflated";
		String example3 = "falling";
		String example4 = "sized";
		String example5 = "happy";
		String example6 = "words";
		String example7 = "word";
		
		String expected1 = "catti";
		String expected2 = "conflat";
		String expected3 = "fall";
		String expected4 = "size";
		String expected5 = "happi";
		String expected6 = "word";
		String expected7 = "word";
		
		String result1 = Tokenizador.stemTerm(example1);
		String result2 = Tokenizador.stemTerm(example2);
		String result3 = Tokenizador.stemTerm(example3);
		String result4 = Tokenizador.stemTerm(example4);
		String result5 = Tokenizador.stemTerm(example5);
		String result6 = Tokenizador.stemTerm(example6);
		String result7= Tokenizador.stemTerm(example7);
		
		assertEquals(expected1, result1);
		assertEquals(expected2, result2);
		assertEquals(expected3, result3);
		assertEquals(expected4, result4);
		assertEquals(expected5, result5);
		assertEquals(expected6, result6);
		assertEquals(expected7, result7);
	}

}
