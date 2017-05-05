package com.RAI.ModeloVectorial.transformacion;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.transformation.Tokenizer;

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
		String result = Tokenizer.removeStopWords(originalString);
		
		assertEquals(expected, result);
	}
	
	@Test
	public void testStemTerm() {
		Term example1 = new Term("catty");
		Term example2 = new Term("conflated");
		Term example3 = new Term("falling");
		Term example4 = new Term("sized");
		Term example5 = new Term("happy");
		Term example6 = new Term("words");
		Term example7 = new Term("word");
		
		String expected1 = "catti";
		String expected2 = "conflat";
		String expected3 = "fall";
		String expected4 = "size";
		String expected5 = "happi";
		String expected6 = "word";
		String expected7 = "word";
		
		String result1 = Tokenizer.stemTerm(example1);
		String result2 = Tokenizer.stemTerm(example2);
		String result3 = Tokenizer.stemTerm(example3);
		String result4 = Tokenizer.stemTerm(example4);
		String result5 = Tokenizer.stemTerm(example5);
		String result6 = Tokenizer.stemTerm(example6);
		String result7= Tokenizer.stemTerm(example7);
		
		assertEquals(expected1, result1);
		assertEquals(expected2, result2);
		assertEquals(expected3, result3);
		assertEquals(expected4, result4);
		assertEquals(expected5, result5);
		assertEquals(expected6, result6);
		assertEquals(expected7, result7);
	}

}
