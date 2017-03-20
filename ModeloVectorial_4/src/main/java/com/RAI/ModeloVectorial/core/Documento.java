package com.RAI.ModeloVectorial.core;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.RAI.ModeloVectorial.Interface.ITexto;

/**
 * Provided a file path, an object of class Documento holds the contents 
 * of a file and allows for further operations with the Jsoup external library.
 * @author vdegou
 *
 */
public class Documento implements ITexto {
	private Document htmlFile;
	private String htmlContent;
	private String cleanContent;
	private Termino[] terminos;
	
	public Documento(String filePath) {
		// Retrieve htmlFile from filePath
		this.htmlFile = null;
		try {
			this.htmlFile = Jsoup.parse(new File(filePath), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Turn the htmlFile to a string.
		htmlContent = getHtmlFile().toString();
		
		// Obtain the parsed text from the htmlFile
		cleanContent = htmlFile.text();
	}

	public Document getHtmlFile() {
		return htmlFile;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public String getCleanContent() {
		return cleanContent;
	}

	public Termino[] getTerminos() {
		return terminos;
	}
}
