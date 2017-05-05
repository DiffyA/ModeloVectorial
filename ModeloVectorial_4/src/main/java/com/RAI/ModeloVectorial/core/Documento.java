package com.RAI.ModeloVectorial.core;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Provided a file path, an object of class Documento holds the contents 
 * of a file and allows for further operations with the Jsoup external library.
 * 
 * @author vdegou
 *
 */
public class Documento implements IText {
	private String filePath;
	
	public Documento(String filePath) {
		this.filePath = filePath;
	}

	public String getCleanContent() {
		// Retrieve htmlFile from filePath
		Document htmlFile = null;
		try {
			htmlFile = Jsoup.parse(new File(this.getFilePath()), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Obtain the parsed (clean) text from the htmlFile
		return htmlFile.text();
	}

	public String getFilePath() {
		return filePath;
	}
	
	@Override
	public String toString() {
		return this.getFilePath();
	}
}
