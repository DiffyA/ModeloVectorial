package com.RAI.ModeloVectorial.model;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.crawler.Crawler;
import com.RAI.ModeloVectorial.database.Controller;
import com.RAI.ModeloVectorial.database.DatabaseManager;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.similarities.CosineCalculator;
import com.RAI.ModeloVectorial.similarities.ScalarProductCalculator;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.transformation.Indexer;
import com.RAI.ModeloVectorial.vector.DocVector;
import com.RAI.ModeloVectorial.vector.Vectorizer;
import com.RAI.ModeloVectorial.weightCalculator.CalculatorTF;
import com.RAI.ModeloVectorial.weightCalculator.CalculatorTFIDF;

/**
 * 
 * 
 * @author vdegou
 *
 */
public class DocumentRecoveryEngine {
	public VectorSpaceModel vectorSpaceModel = new VectorSpaceModel();
	
	/**
	 * Takes the indexed terms in memory and persists them in the database.
	 * @param allTerms
	 */
	public void storeInDatabase(HashMap<String, Term> allTerms) {
		long startTime = 0;
		long estimatedTime= 0;
		
		// Store in the Term(Term, IDF) table
		startTime = System.currentTimeMillis();
		Controller.storeTerms(allTerms);

		// Store in the DocTerm(Document, Term, TF) table
		Controller.storeDocTerms(allTerms);
		estimatedTime = System.currentTimeMillis() - startTime;
		
		// Report time statistics
		System.out.println("Time taken to persist data to database: " + estimatedTime + " ms.");
	}
	
	public static void main(String[] args) {
		// Make an instance of the DocumentRecoveryEngine
		DocumentRecoveryEngine engine = new DocumentRecoveryEngine();	
		
		// ---------- CREATING THE DOCUMENT ARRAY FROM THE EIREX DIRECTORY ---------
		// Get the EIREX directory path
		File EIREX = new File("src/main/resources/2010-documents.biased");
		
		// Mock list needed for createDocArray() method.
		ArrayList<Documento> mockList = new ArrayList<Documento>();
		
		// Get an array of Document objects from the EIREX directory.
		Documento[] documents = Controller.createDocArray(EIREX, mockList);	
		
		// Index all the files into main memory (vector space model's dictionary structure) and time it
//		engine.vectorSpaceModel.index(documents, true);
		engine.vectorSpaceModel.index(Arrays.copyOfRange(documents, 0, 3), false);
		
		
		
		// ---------- STORE INFORMATION IN THE DATABASE ---------------------------
		// Connect to the DB.
		DatabaseManager.connect();
		
		// Clean the database
		engine.vectorSpaceModel.cleanDatabase();
		
		// Persist data in the database
		engine.storeInDatabase(engine.vectorSpaceModel.dicc.getAllTerms());

		// Check that info was indeed stored
//		DatabaseManager.mostrarDocTerms();
//		DatabaseManager.mostrarTerms();
		
		// Close the database
		DatabaseManager.close();
		
		
		// ---------- OBTAINING THE QUERIES FROM THE FILE --------------------------
		// TODO: Obtain the queries from the file
		// This will be done using JSoup to retrieve the relevant information from the 2010-topics.xml and 2010.union.trel files
		
		ArrayList<Query> queries = Crawler.getQueries("src/main/resources/2010-topics.xml");
		
		
		
	}
}