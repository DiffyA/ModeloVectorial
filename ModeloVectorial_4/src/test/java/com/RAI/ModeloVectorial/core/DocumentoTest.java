package com.RAI.ModeloVectorial.core;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
//import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.Files;

import com.RAI.ModeloVectorial.core.Documento;

public class DocumentoTest {
	
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
	public void testGetCleanContent() throws IOException {
		Documento documento = new Documento("src/test/resources/test.html");		
		
		String expected = "Page Title Heading Paragraph";
		String result = documento.getCleanContent();
		
		assertEquals(expected, result);
	}
	
}
