package com.RAI.ModeloVectorial.Core;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

	static String readFile(String path, Charset encoding) throws IOException {
		  byte[] encoded = Files.readAllBytes(Paths.get(path));
		  return new String(encoded, encoding);
	}
	
	@Test
	public void testHtmlContent() {
		Documento documento = new Documento("src/test/resources/test.html");		
		
		// Obtain page title and compare with values in file.
		String expectedTitle = documento.getHtmlFile().title();
		assertEquals(expectedTitle, "Page Title");
		
		// Obtain h1 element and compare with values in file.
		String expectedH1 = documento.getHtmlFile().getElementsByTag("h1").text();
		assertEquals(expectedH1, "Heading");
		
		// Obtain p element and compare with values in file.
		String expectedP = documento.getHtmlFile().getElementsByTag("p").text();
		assertEquals(expectedP, "Paragraph");	
	}
	
	@Test
	public void testCleanContent() {
		Documento documento = new Documento("src/test/resources/test.html");
		
		// Check that clean content equals the text in the html file without the tags
		String expectedCleanContent = documento.getCleanContent();
		assertEquals(expectedCleanContent, "Page Title Heading Paragraph");
	}

}
