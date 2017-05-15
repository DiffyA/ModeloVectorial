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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public void storeInDatabase(HashMap<String, Term> allTerms, Set<Documento> allDocuments) {
		long startTime = 0;
		long estimatedTime= 0;
		
		// Store in the Documents(name, filepath) table
		startTime = System.currentTimeMillis();
		Controller.storeDocs(allDocuments);
		
		// Store in the Term(Term, IDF) table
		Controller.storeTerms(allTerms);

		// Store in the DocTerm(Document, Term, TF) table
		Controller.storeDocTerms(allTerms);
		estimatedTime = System.currentTimeMillis() - startTime;
		
		
		// Report time statistics
		System.out.println("Time taken to persist data to database: " + estimatedTime + " ms.");
	}
	
	public void cleanDatabase() {
		DatabaseManager.dropTable("DocTerm");
		DatabaseManager.dropTable("Term");
		DatabaseManager.dropTable("Documentos");
		
		DatabaseManager.createTable("DocTerm");
		DatabaseManager.createTable("Term");
		DatabaseManager.createTable("Documentos");
	}
	
	public void obtainSimilarities(List<Query> queries, List<DocVector> docVectors) {
		long startTime = 0;
		long estimatedTime = 0;
		long cumulativeTime = 0;
		int recoveredDocuments = 0;
		
		try {
			DatabaseManager.connect.setAutoCommit(false);
		
			// Obtain similarity between all queries and all vectors
			for (Query query : queries) {
				startTime = System.currentTimeMillis();
				
				// Obtain the query vector
				DocVector queryVector = this.vectorSpaceModel.vectorizer.toVector(query);
//				System.out.println(queryVector.getSoftVector());
//				System.out.println(queryVector.getVector());
		
				// Iterate through each document vector in DB, obtaining its similarity with respect to the query.
				for (DocVector docVector : docVectors) {
	//				System.out.println(docVector.getSoftVector());
	//				System.out.println(docVector.getVector());
					double similarity = this.vectorSpaceModel.similitudCos.calculate(docVector, queryVector);
//					System.out.println(similarity);
					
					// If similarity is greater than 0, add to the recovered documents set in the DB for that query.
					if (similarity > 0) {
//						System.out.println("Storing queryID: " + query.getId() + ", DocVectorID: " + docVector.getId() + ", similarity: " + similarity);
						
						DatabaseManager.saveSimilitud(query.getId(), docVector.getId(), similarity);
						
						// Increase counter
						recoveredDocuments++;
					}
				}
				
				
				estimatedTime = System.currentTimeMillis() - startTime;
				cumulativeTime += estimatedTime;
				System.out.println("--- For query with ID: " + query.getId() + ", the engine stored " + recoveredDocuments + " documents in the DB and took " + estimatedTime +" ms.");
				
				// Reset counters
				recoveredDocuments = 0;
				estimatedTime = 0;
				startTime = 0;
			}
			
			// Persist info in database.
			DatabaseManager.connect.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("--- Calculated similarities between " + queries.size() + " queries and " + docVectors.size() + " documents and took " + cumulativeTime + " ms.");
	}
	
	public static void main(String[] args) {
		
		// Make an instance of the DocumentRecoveryEngine
		DocumentRecoveryEngine engine = new DocumentRecoveryEngine();	
		/*
		// ---------- CREATING THE DOCUMENT ARRAY FROM THE EIREX DIRECTORY ---------
		// Get the EIREX directory path
		File EIREX = new File("src/main/resources/2010-documents.biased");
		
		// Mock list needed for createDocArray() method.
		ArrayList<Documento> mockList = new ArrayList<Documento>();
		
		// Get an array of Document objects from the EIREX directory.
		Documento[] documents = Controller.createDocArray(EIREX, mockList);	
		
		// Index all the files into main memory (vector space model's dictionary structure) and time it
		engine.vectorSpaceModel.index(documents, true);
//		engine.vectorSpaceModel.index(Arrays.copyOfRange(documents, 0, 3), false);
		
		// Make references to the loaded structures.
		HashMap<String, Term> allTerms = engine.vectorSpaceModel.dicc.getAllTerms();
		Set<Documento> allDocuments = engine.vectorSpaceModel.dicc.getAllDocuments();
		*/
		// ---------- STORE INFORMATION IN THE DATABASE ---------------------------
		// Connect to the DB.
		DatabaseManager.connect();
		
		// Clean the database
//		engine.cleanDatabase();
		
		// Persist data in the database
//		engine.storeInDatabase(allTerms, allDocuments);

		// Check that info was indeed stored
//		DatabaseManager.mostrarDocTerms();
//		DatabaseManager.mostrarTerms();
		
		// Close the database
//		DatabaseManager.close();
		
		
		// ---------- OBTAINING THE QUERIES FROM THE FILE --------------------------
		// TODO: Obtain the queries from the file
		// This will be done using JSoup to retrieve the relevant information from the 2010-topics.xml and 2010.union.trel files
		ArrayList<Query> queries = Crawler.getQueries("src/main/resources/2010-topics.xml");
		
		// Obtain TFIDF vectors of all indexed documents in the DB
		ArrayList<DocVector> vectors = Controller.obtainAllTFIDFVectors();
		
		// Clean Database
//		DatabaseManager.dropQueryTables();
//		DatabaseManager.createQueryTables();
		
		// Obtain similarities between queries and document vectors
		engine.obtainSimilarities(queries, vectors);
		
		
		
		DatabaseManager.close();

	}


}