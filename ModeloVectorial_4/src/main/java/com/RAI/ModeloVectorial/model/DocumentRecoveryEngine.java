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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
	
	/**
	 * Calculates the similarities between the provided queries and document vectors.
	 * Stores those occurrences that have a similarity greater than 0 into their corresponding
	 * table in the database. This set of documents with similarities > 0 is known as the recovered set of documents.
	 * @param queries
	 * @param docVectors
	 */
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
		
				// Iterate through each document vector in DB, obtaining its similarity with respect to the query.
				for (DocVector docVector : docVectors) {
					double similarity = this.vectorSpaceModel.similitudCos.calculate(docVector, queryVector);
					
					// If similarity is greater than 0, add to the recovered documents set in the DB for that query.
					if (similarity > 0) {
						// Gets document ID without file extension for joining with relevance table later.
						String docName = docVector.getId().substring(0, docVector.getId().lastIndexOf("."));
						DatabaseManager.saveSimilitud(query.getId(), docName, similarity);
						
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
	
	public static double[] calcEficacia (ArrayList<String> recs, Set<String> rels, int corte){	//Para hacer los cortes (5, 10) se le pasan solo los 5/10 primeros docs recuperados. 
		DecimalFormat df = new DecimalFormat("0.0###");
		double[] calcs = new double[5];
		int Nrels = 0;
		double precision;
		double recall;
		double valorF;
		//int rank = 0;
		double rrank;
		ArrayList<Double> ranks = new ArrayList<Double>();
		double suma_rranks = 0;
		double ap;
		
		//Iterator<String> itrecs = recs.iterator();
		
		//boolean flag = false;
		for(int i=0; i<recs.size() && i<corte; i++){
			//String rec = itrecs.next();
			Iterator<String> itrels = rels.iterator();
			for(int j=0; itrels.hasNext(); j++){
				String rel = itrels.next();
				if(rel.equals(recs.get(i))){	//En el union.trel los docs no tienen .html ?
					Nrels++;
					//if(!flag){
						//rank = i;
						//flag = true;
					//}
					ranks.add((double) (i+1));
				}
			}
		}
		
		for(int h=0; h<Nrels && h<100; h++){	//Av.Precision @100
			suma_rranks += (h+1)/ranks.get(h);
		}
		
		if(Nrels!=0 && ranks.size()!=0){
			precision = (double) Nrels/(recs.size());
			recall = (double) Nrels/rels.size();
			valorF = (double) (2*precision*recall)/(precision+recall);
			rrank = (double) 1/ranks.get(0);
			ap = (double) suma_rranks/Nrels;
		}else{		//Esto es para que no salga NaN: x/0 = 0/x = 0
			precision = 0;
			recall = 0;
			valorF = 0;
			rrank = 0;
			ap = 0;
		}
		
		calcs[0] = precision;
		calcs[1] = recall;
		calcs[2] = valorF;
		calcs[3] = rrank;
		calcs[4] = ap;
		
		/*System.out.println("");
		System.out.println(" - M�TRICAS CON CORTE "+corte+" - ");
		System.out.println("Precision: "+df.format(precision));
		System.out.println("Recall: "+df.format(recall));
		System.out.println("Valor F: "+df.format(valorF));
		System.out.println("1st RRank: "+df.format(rrank));
		System.out.println("Average Precision: "+df.format(ap));
		System.out.println("-------------------------");*/
		
		return calcs;	//Es mejor calcular y devolver todos los valores de una vez para ahorrar bucles.
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
		engine.vectorSpaceModel.index(Arrays.copyOfRange(documents, 0, 3), false);
		
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
		// This will be done using JSoup to retrieve the relevant information from the 2010-topics.xml and 2010.union.trel files
		ArrayList<Query> queries = Crawler.getQueries("src/main/resources/2010-topics.xml");
		
		// ---------- OBTAINING THE SIMILARITIES BETWEEN QUERIES AND DOCUMENTS IN THE DB --------------------------
		// Obtain TFIDF vectors of all indexed documents in the DB
//		ArrayList<DocVector> vectors = Controller.obtainAllTFIDFVectors();
		
		// Obtain similarities between queries and document vectors
//		engine.obtainSimilarities(queries, vectors);
		
		// Clean Database
//		DatabaseManager.dropQueryTables();
//		DatabaseManager.createQueryTables();
		
		// ---------- OBTAINING THE SET OF RECOVERED DOCUMENTS AND RELEVANT DOCUMENTS --------------------------
		// Obtain the relevances from queries and documents from the union.trel file
//		DatabaseManager.dropTable("Relevancias");
//		DatabaseManager.createTable("Relevancias");
//		DatabaseManager.storeRelevance("src/main/resources/2010.union.trel");
		
		// The set of recovered documents are those stored in each query table (T2010001 ...).
//		ArrayList<String> recoveredDocumentsQuery001 = DatabaseManager.obtainRecoveredDocumentSet("2010-001");
//		System.out.println(recoveredDocumentsQuery001.size());
		
		// The set of relevant documents is obtained from here: 
		// First parameter is query id, second is minimum relevance
//		Set<String> relevantDocumentsQuery001 = DatabaseManager.obtainRelevantDocumentSet("2010-001", 0);
//			  				  		
//		System.out.println(relevantDocumentsQuery001.size());
//		System.out.println(relevantDocumentsQuery001);
//		System.out.println(recoveredDocumentsQuery001);
		
		
		// ---------- MEASUREMENTS --------------------------
		//Arrays donde guardaremos las metricas calculadas (rel.min. 1 y 2, cortes 5 y 10)
		//Guardamos todos los datos por is queremos probar calculos adicionales
		double[][] metricas15 = new double[5][20];
		double[][] metricas110 = new double[5][20];
		double[][] metricas25 = new double[5][20];
		double[][] metricas210 = new double[5][20];
		double[][] ndcg10 = new double[10][20];
		double[][] ndcg100 = new double[100][20];
		
		//Calculamos las metricas para cada consulta
		for(int i=1; i<=20; i++){
			String query = "2010-0";
			if(i<10){
				query += "0" + i;
			}else{
				query += i;
			}
			
			//System.out.println("*** Query: "+query);
			
			ArrayList<String> recDocs = DatabaseManager.obtainRecoveredDocumentSet(query);
			
			// The set of relevant documents is obtained from here: 
			// First parameter is query id, second is minimum relevance
			Set<String> relDocs1 = DatabaseManager.obtainRelevantDocumentSet(query, 1);
			Set<String> relDocs2 = DatabaseManager.obtainRelevantDocumentSet(query, 2);
			
			double[] m15 = calcEficacia(recDocs, relDocs1, 5);	//El int indica el corte
			double[] m110 = calcEficacia(recDocs, relDocs1, 10);
			double[] m1100 = calcEficacia(recDocs, relDocs1, 100); //Con 100 es la misma que con 1000 
			
			
			double[] m25 = calcEficacia(recDocs, relDocs2, 5);
			double[] m210 = calcEficacia(recDocs, relDocs2, 10);
			double[] m2100 = calcEficacia(recDocs, relDocs2, 100); //Con 100 es la misma que con 1000 
			
			double[] n10 = DatabaseManager.calcularNDCG(query, recDocs, relDocs1, 10);	//El int indica el corte
			double[] n100 = DatabaseManager.calcularNDCG(query, recDocs, relDocs1, 100);
			
			//Aqu� guardamos las metricas
			for (int j=0; j<5; j++){
				metricas15[j][i-1] = m15[j];
				metricas25[j][i-1] = m25[j];
				metricas110[j][i-1] = m110[j];
				metricas210[j][i-1] = m210[j];
			}
			for (int j=0; j<10; j++){
				ndcg10[j][i-1] = n10[j];
			}
			for (int j=0; j<100; j++){
				ndcg100[j][i-1] = n100[j];
			}
			
		}
		
		//Aqu� imprimimos las m�tricas de cada consulta
		System.out.println("");
		System.out.println("*** Metricas ***");
		System.out.println("(Todas calculadas con relevancia m�nima 1 salvo indicaci�n)");
		for(int i=0; i<20; i++){
			DecimalFormat df = new DecimalFormat("0.0###");
			System.out.println("");
			String query = " ** CONSULTA 2010-0";
			if(i<9){
				query += "0" + (i+1);
			}else{
				query += (i+1);
			}
			System.out.println(query);
			System.out.println("  * Precision en corte 5 = "+df.format(metricas15[0][i]));
			System.out.println("  * Precision en corte 10 = "+df.format(metricas110[0][i]));
			System.out.println("  * Exhaustividad en corte 5 = "+df.format(metricas15[1][i]));
			System.out.println("  * Exhaustividad en corte 10 = "+df.format(metricas110[1][i]));
			System.out.println("  * Valor F en corte 5 = "+df.format(metricas15[2][i]));
			System.out.println("  * Valor F en corte 10 = "+df.format(metricas110[2][i]));
			System.out.println("  * Reciprocal Rank  = "+df.format(metricas15[3][i]));
			System.out.println("  * Reciprocal Rank con relevancia 2 = "+df.format(metricas25[3][i]));
			System.out.println("  * Average precision @100 en corte 5 = "+df.format(metricas15[4][i]));
			System.out.println("  * Average precision @100 en corte 10 = "+df.format(metricas110[4][i]));
			
			df = new DecimalFormat("0.0000");
			System.out.println("  * NDCG en corte 10 = "+df.format(ndcg10[9][i]));
			System.out.print("    ndcg: ");
			for(int j=0; j<10; j++){
				System.out.print(df.format(ndcg10[j][i])+" ");
			}
			System.out.println();
			System.out.print("    docs:      ");
			for(int j=0; j<10; j++){
				System.out.print((j+1)+"     ");
				if(j<8)System.out.print(" ");
			}
			System.out.println();
			System.out.println("  * NDCG en corte 100 = "+df.format(ndcg100[99][i]));
			System.out.print("    ndcg: ");
			for(int j=0; j<100; j++){
				System.out.print(df.format(ndcg100[j][i])+" ");
			}
			System.out.println();
			System.out.print("    docs:      ");
			for(int j=0; j<100; j++){
				System.out.print((j+1)+"     ");
				if(j<8)System.out.print(" ");
			}
			System.out.println();
		}
		
		//Para calcular las medias
		double[] medias = new double[12];
		for(int i=0; i<20; i++){
			medias[0] += metricas15[0][i];
			medias[1] += metricas110[0][i];
			medias[2] += metricas15[1][i];
			medias[3] += metricas110[1][i];
			medias[4] += metricas15[2][i];
			medias[5] += metricas110[2][i];
			medias[6] += metricas15[3][i];
			medias[7] += metricas25[3][i];
			medias[8] += metricas15[4][i];
			medias[9] += metricas110[4][i];
			medias[10] += ndcg100[9][i];
			medias[11] += ndcg100[99][i];
		}
		for(int i=0; i<12; i++){
			medias[i] = medias[i] / 20;
		}
		
		DecimalFormat df = new DecimalFormat("0.0###");
		System.out.println("");
		System.out.println(" ** METRICAS MEDIAS entre las 20 consultas");
		System.out.println("  * Precision en corte 5 = "+df.format(medias[0]));
		System.out.println("  * Precision en corte 10 = "+df.format(medias[1]));
		System.out.println("  * Exhaustividad en corte 5 = "+df.format(medias[2]));
		System.out.println("  * Exhaustividad en corte 10 = "+df.format(medias[3]));
		System.out.println("  * Valor F en corte 5 = "+df.format(medias[4]));
		System.out.println("  * Valor F en corte 10 = "+df.format(medias[5]));
		System.out.println("  * Reciprocal Rank  = "+df.format(medias[6]));
		System.out.println("  * Reciprocal Rank con relevancia 2 = "+df.format(medias[7]));
		System.out.println("  * Average precision @100 en corte 5 = "+df.format(medias[8]));
		System.out.println("  * Average precision @100 en corte 10 = "+df.format(medias[9]));
		System.out.println("  * NDCG en corte 10 = "+df.format(medias[10]));
		System.out.println("  * NDCG en corte 100 = "+df.format(medias[11]));
		
		DatabaseManager.close();
	}


}