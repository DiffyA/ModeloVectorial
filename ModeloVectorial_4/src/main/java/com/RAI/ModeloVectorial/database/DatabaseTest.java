package com.RAI.ModeloVectorial.database;

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
		
		
		DatabaseManager.obtainAllTFIDFVectors();
		
		
		DatabaseManager.close();
	}
}
