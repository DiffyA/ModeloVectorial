package com.RAI.ModeloVectorial.database;

import java.util.ArrayList;

import com.RAI.ModeloVectorial.vector.DocVector;

public class DatabaseTest {
	
	public static void main(String args[]) {
		DatabaseManager.connect();
//		DatabaseManager.dropTable("DocTerm");
//		DatabaseManager.dropTable("Term");
//		DatabaseManager.mostrarDocTerms();
//		DatabaseManager.mostrarTerms();
		
//		DatabaseManager.createTable("Relevancias");
//		DatabaseManager.dropTable("Relevancias");
		
//		DatabaseManager.storeRelevance("src/main/resources/2010.union.trel");
		
//		DatabaseManager.mostrarRelevancias();
		
		
//		System.out.println(DatabaseManager.obtainTFIDFVector("2010-60-020.html").getSoftVector());
		
//		DocVector vector = DatabaseManager.obtainTFIDFVector("2010-60-020.html");
		
//		System.out.println(vector.getId() + vector.getSoftVector());
		
//		ArrayList<DocVector> vectors = Controller.obtainAllTFIDFVectors();
		
//		System.out.println(vectors.size());
		
//		for (DocVector vector : vectors) {
//			System.out.println(vector.getSoftVector());
//		}

//		DatabaseManager.mostrarDocumentos();
		
//		for (DocVector vector : vectors) {
//			System.out.println(vector.getId() + vector.getSoftVector());
//		}
		
		// Creamos query tables
		DatabaseManager.dropQueryTables();
		DatabaseManager.createQueryTables();
		
//		System.out.println(DatabaseManager.obtainIDFofTerm("taggart"));
		
		
		DatabaseManager.close();
	}
}
