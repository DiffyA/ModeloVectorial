package com.RAI.ModeloVectorial.Core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Term;

public class ConsultaTest {

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
	public void test() {
		Query query = new Query("search query with a duplicate duplicate term");
		
		System.out.println(query.getTerms());
		
		for (Term t : query.getTerms()) {
			System.out.println("TF of " + t.getTerm() + ": " + t.getTFInDocument(query));
		}
	}

}
