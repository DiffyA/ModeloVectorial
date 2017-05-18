package com.RAI.ModeloVectorial.model;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.similarities.CosineCalculator;
import com.RAI.ModeloVectorial.similarities.ISimilarityFunction;
import com.RAI.ModeloVectorial.similarities.ScalarProductCalculator;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.omg.Messaging.SyncScopeHelper;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.transformation.Indexer;
import com.RAI.ModeloVectorial.vector.Vector;
import com.RAI.ModeloVectorial.vector.Vectorizer;
import com.RAI.ModeloVectorial.weightCalculator.CalculatorTF;
import com.RAI.ModeloVectorial.weightCalculator.CalculatorTFIDF;
import com.RAI.ModeloVectorial.weightCalculator.IWeightCalculator;

/**
 * Entry-point for the project, given its main method, which represents the functionality
 * of the developed vector-space model.
 * 
 * @author vdegou
 *
 */
public class VectorSpaceModel {
	// Create the dictionary
	public Dictionary dicc = new Dictionary();
	
	// Create the vectorizer
	public Vectorizer vectorizer = new Vectorizer();
	
	// Create the weight calculators.
	public IWeightCalculator calcTF = new CalculatorTF();
	public IWeightCalculator calcTFIDF = new CalculatorTFIDF();
	
	// Create the relevance metric calculators.
	public ISimilarityFunction similitudCos = new CosineCalculator();
	public ISimilarityFunction similitudScalar = new ScalarProductCalculator();
	
	/**
	 * Wrapper method to index an array of documents into the model's dictionary structure
	 * and pretty print to console if desired.
	 * @param prettyPrint Boolean value which determine if the currently indexed term is printed to the console.
	 * the index in the database or not.
	 */
	public void index(Documento[] documentsToIndex, boolean prettyPrint) {
		long startTime = 0;
		long estimatedTime = 0;
		
		startTime = System.currentTimeMillis();
		Indexer.indizar(documentsToIndex, dicc, prettyPrint);
		estimatedTime = System.currentTimeMillis() - startTime;
			
		System.out.println("\n\n\n*** END OF INDEXING ***");
		System.out.println("Estimated time taken to index " + documentsToIndex.length + 
				" documents and around " + dicc.getAllTerms().size() + 
				" terms: " + estimatedTime + "ms \n\n\n");
	}
		
	/**
	 * Calculates and prints the similarities between all the queries in the queryArray and 
	 * all the documents in the docArray.
	 */
	public void printSimilarityFunctions(Documento[] docArray, Query[] queryArray) {
		System.out.println("*** SIMILARITY FUNCTIONS ***");
		// Calculating similarity with Scalar Product using TF weights.
		System.out.println("* RELEVANCIA: ProductoEscalarTF");
		System.out.println("Nombre del doc \t \t Q1 \t Q2 \t Q3");
		
		for (Documento doc : docArray) {
			
			// Print document name
			String documentName = doc.toString().substring(doc.toString().lastIndexOf("/")+1);
			System.out.print(documentName + "\t");
			
			String stringToPrint = "";
			
			for (Query query : queryArray) {
				
				// Obtain vectors to compare
				Vector docVector = vectorizer.toVector(doc, dicc, calcTF);
				Vector queryVector = vectorizer.toVector(query, dicc, calcTF);
				
				// Obtain similarity
				double similarity = similitudScalar.calculate(docVector, queryVector);
				
				// Add content to stringToPrint
				stringToPrint += similarity + "\t";
			}
			System.out.println(stringToPrint);
		}
		
		System.out.println();
		
		// Calculating similarity with Scalar Product using TFIDF weights.
		System.out.println("* RELEVANCIA: ProductoEscalarTFIDF");
		System.out.println("Nombre del doc \t \t Q1 \t Q2 \t Q3");
		
		for (Documento doc : docArray) {
			
			// Print document name
			String documentName = doc.toString().substring(doc.toString().lastIndexOf("/")+1);
			System.out.print(documentName + "\t");
			
			String stringToPrint = "";
			
			for (Query query : queryArray) {
				
				// Obtain vectors to compare
				Vector docVector = vectorizer.toVector(doc, dicc, calcTFIDF);
				Vector queryVector = vectorizer.toVector(query, dicc, calcTFIDF);
				
				// Obtain similarity
				double similarity = similitudScalar.calculate(docVector, queryVector);
				
				// Add content to stringToPrint
				stringToPrint += similarity + "\t";
			}
			System.out.println(stringToPrint);
		}
		
		System.out.println();
		
		// Calculating similarity with Scalar Product using TFIDF weights.
		System.out.println("* RELEVANCIA: CosenoTF");
		System.out.println("Nombre del doc \t \t Q1 \t Q2 \t Q3");
		
		for (Documento doc : docArray) {
			
			// Print document name
			String documentName = doc.toString().substring(doc.toString().lastIndexOf("/")+1);
			System.out.print(documentName + "\t");
			
			String stringToPrint = "";
			
			for (Query query : queryArray) {
				
				// Obtain vectors to compare
				Vector docVector = vectorizer.toVector(doc, dicc, calcTF);
				Vector queryVector = vectorizer.toVector(query, dicc, calcTF);
				
				// Obtain similarity
				double similarity = similitudCos.calculate(docVector, queryVector);
				
				// Add content to stringToPrint
				stringToPrint += similarity + "\t";
			}
			System.out.println(stringToPrint);
		}
		
		System.out.println();
		
		// Calculating similarity with Scalar Product using TFIDF weights.
		System.out.println("* RELEVANCIA: CosenoTFIDF");
		System.out.println("Nombre del doc \t \t Q1 \t Q2 \t Q3");
		
		for (Documento doc : docArray) {
			
			// Print document name
			String documentName = doc.toString().substring(doc.toString().lastIndexOf("/")+1);
			System.out.print(documentName + "\t");
			
			String stringToPrint = "";
			
			for (Query query : queryArray) {
				
				// Obtain vectors to compare
				Vector docVector = vectorizer.toVector(doc, dicc, calcTFIDF);
				Vector queryVector = vectorizer.toVector(query, dicc, calcTFIDF);
				
				// Obtain similarity
				double similarity = similitudCos.calculate(docVector, queryVector);
				
				// Add content to stringToPrint
				stringToPrint += similarity + "\t";
			}
			System.out.println(stringToPrint);
		}
	}
		
	public static void main(String[] args) {
		// Make an instance of the VectorSpaceModel
		VectorSpaceModel model = new VectorSpaceModel();
		
		// Create the documents.
		Documento document1 = new Documento("src/main/resources/2010-22-100.html");
		Documento document2 = new Documento("src/main/resources/2010-42-103.html");
		Documento document3 = new Documento("src/main/resources/2010-58-044.html");
		Documento document4 = new Documento("src/main/resources/2010-76-088.html");
		Documento document5 = new Documento("src/main/resources/2010-99-086.html");
		
		// Create the document array.
		Documento[] docArray = {document1, document2, document3, document4, document5};
		
		// Create the queries.
		Query query1 = new Query("What video game won Spike's best driving game award in 2006?");
		Query query2 = new Query("What is the default combination of Kensington cables?");
		Query query3 = new Query("Who won the first ACM Gerard Salton prize?");

		// Create the query array.
		Query[] queryArray = {query1, query2, query3};
		
		// Indexes the documents in the docArray. Boolean tells us whether to print to console or not.
		model.index(docArray, true);
		
		// Executes the similarity functions between the 5 documents and 3 queries.
		model.printSimilarityFunctions(docArray, queryArray);
	}
}
