package com.RAI.ModeloVectorial.model;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.database.DatabaseManager;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.queryExpansion.QueryExpander;
import com.RAI.ModeloVectorial.similarities.CosineCalculator;
import com.RAI.ModeloVectorial.similarities.ScalarProductCalculator;

import java.sql.SQLException;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.transformation.Indexer;
import com.RAI.ModeloVectorial.vector.Vector;
import com.RAI.ModeloVectorial.vector.Vectorizer;
import com.RAI.ModeloVectorial.weightCalculator.CalculatorTF;
import com.RAI.ModeloVectorial.weightCalculator.CalculatorTFIDF;

/**
 * Entry-point for the project, given its main method, which represents the functionality
 * of the developed vector-space model.
 * 
 * @author vdegou
 *
 */
public class VectorSpaceModel {
	// Create the vectorizer
	Vectorizer vectorizer = new Vectorizer();
	
	// Create the weight calculators.
	CalculatorTF calcTF = new CalculatorTF();
	CalculatorTFIDF calcTFIDF = new CalculatorTFIDF();
	
	// Create the relevance metric calculators.
	CosineCalculator similitudCos = new CosineCalculator();
	ScalarProductCalculator similitudScalar = new ScalarProductCalculator();

	// Create expander
	QueryExpander queryExpander = new QueryExpander();
	
	
	// Create the dictionary
	Dictionary dicc = new Dictionary();
	
	// Create the documents.
	Documento document1 = new Documento("src/main/resources/2010-22-100.html");
	Documento document2 = new Documento("src/main/resources/2010-42-103.html");
	Documento document3 = new Documento("src/main/resources/2010-58-044.html");
	Documento document4 = new Documento("src/main/resources/2010-76-088.html");
	Documento document5 = new Documento("src/main/resources/2010-99-086.html");
	
	// Create the document array.
	Documento[] docArray = {document1, document2, document3, document4, document5};
//	Documento[] docArray = {document1};

	// Create the queries.
	Query query1 = new Query("What video game won Spike's best driving game award in year 2006?");
	Query query2 = new Query("What is the default combination of Kensington cables?");
	Query query3 = new Query("Who won the first ACM Gerard Salton prize?");

	// Create the query array.
	Query[] queryArray = {query1, query2, query3};

	/**
 	 * Indexes the documents found in the docArray structure.
	 * By setting the parameter to True, the method will store the indexed documents 
	 * and relevant information in the database, in which case the dictionary structures will be cleared
	 * afterwards to free up main memory. Otherwise, it won't be sent to the database
	 * and all data will be kept in memory inside of the Dictionary object.  
	 * 
	 * NOTE: if toDatabase is set to true, this method will populate the DocTerm (Document, Term, TF) and
	 * Term(Term, IDF) tables with the indexed information.
	 * 
	 * @param toDatabase Boolean value which will determine whether or not to store
	 * @param prettyPrint Boolean value which determine if the currently indexed term is printed to the console.
	 * the index in the database or not.
	 */
	public void index(boolean toDatabase, boolean prettyPrint) {
		/* Index the documents in the dictionary.
		 * We also configure SQLite to make a single transaction after everything
		 * has been committed in order to save time.
		 */
		long startTime = 0;
		long estimatedTime = 0;
		
		try {
			DatabaseManager.connect.setAutoCommit(false);
			
			startTime = System.currentTimeMillis();
			Indexer.indizar(docArray, dicc, toDatabase, prettyPrint);
			estimatedTime = System.currentTimeMillis() - startTime;
			
			DatabaseManager.connect.setAutoCommit(true);
			
		} catch (SQLException e) {
			System.out.println("Error indexing the documents.");
			e.printStackTrace();
		}
		System.out.println("\n\n\n*** END OF INDEXING ***");
		System.out.println("Estimated time taken to index " + docArray.length + 
				" documents and around " + dicc.getAllTerms().size() + 
				" terms: " + estimatedTime + "ms \n\n\n");
	}
	
	public void similarityFunctions() {
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
		VectorSpaceModel model = new VectorSpaceModel();
		
		// Connect to the DB.
		DatabaseManager.connect();
		
		// Drop pre-existing tables
		DatabaseManager.dropTable("DocTerm");
		DatabaseManager.dropTable("Term");
		
		// Create table
		DatabaseManager.createTable("DocTerm"); // (Doc, Term, TF)
		DatabaseManager.createTable("Term"); // (Term, IDF)

		//Expand queries
		//for (Query q : model.queryArray){
		//	model.queryExpander.expandQuery(q);
		//}
		
		// Indexes the documents in the docArray, making sure they go to the database.
		// First boolean determines whether or not to store information in database.
		// Second boolean determines whether or not to pretty print current term to console.
		model.index(true, true);
		
		// Executes the similarity functions between the 5 documents and 3 queries.
		model.similarityFunctions();
				
		// Selects all from the DocTerms table (Doc, Term, TF).
//		DatabaseManager.mostrarDocTerms();
//		DatabaseManager.mostrarTerms();
		
		// Close database connection.
		DatabaseManager.close();
		
	}
}
