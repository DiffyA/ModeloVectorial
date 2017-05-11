package com.RAI.ModeloVectorial.database;

public class DatabaseTest {
	
	public static void main(String args[]) {
		DatabaseManager.connect();
//		DatabaseManager.dropTable("DocTerm");
//		DatabaseManager.dropTable("Term");
		DatabaseManager.mostrarDocTerms();
//		DatabaseManager.mostrarTerms();
		DatabaseManager.close();
	}
}
