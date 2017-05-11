package com.RAI.ModeloVectorial.database;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.IText;
import com.RAI.ModeloVectorial.core.Term;

public class Controller {
	
	
	public static void main(String[] args) {
		
		final File folder = new File("C:/Users/l04sd205.AIG/Downloads");	//Ruta donde buscar los documentos.
		
		/*resetTabla("Documentos");	//Para borrar los docs guardados antes de meter los nuevos.
		guardarDirArchivos(folder);
		//mostrar("Documentos");
		obtenerArrayDocs();
		
		resetTabla("DocTerm");
		resetTabla("Term");
		guardarDocsyTerminos("Doc123", "patata", 88);
		guardarDocsyTerminos("Doc456", "coche", 23);
		guardarDocsyTerminos("Doc789", "coche", 66);
		guardarDocsyTerminos("Doc234", "coche", 49);
		guardarDocsyTerminos("Doc345", "coche", 3);
		guardarTerminos("patata", 5);
		guardarTerminos("coche", 1);
		mostrar("DocTerm");
		mostrar("Term");
		guardarDocsyTerminos("Doc123", "patata", 10);
		guardarTerminos("patata", 2);
		mostrar("DocTerm");
		mostrar("Term");
		//consultar("coche");
		Vector<String> pru = new Vector<String>();
		pru.add("coche");
		pru.add("patata");
		consultar(pru);*/
		
		
		//ESTRUCTURA TEORICA
		/*
		 * Recorrer documentos en carpeta
		 * Para cada documento, analizarlo e indizarlo
		 * Y guardar sus datos en la bbdd
		 */
		
		/*
		 * NECESITAREMOS
		 * 3 tablas en teoria
		 * Un metodo por cada campo que haya que guardar en cada tabla ?
		 * O un metodo para guardar cada tipo de datos y otros para cambiar de tabla donde insertar
		 * Pero seguramente haya que hacer al menos 1 metodo para insertar por cada tabla
		 * (1 metodo por tabla si se insertan todos los datos a la vez, varios metodos por tabla si se insertan los datos por separado)
		 */
		
	}
	
	
	public static void guardarDocsyTerminos(String doc, String term, double termFrec) {	//Cambiar por un vector ?
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		con.saveDocTerm(doc, term, termFrec);
	    con.close();
	}
	
	public static void guardarDocsyTerminos2(String doc, String term, double frec) {
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		con.saveDocTerm(doc, term, frec);
		con.saveTerm(term, frec);
	    con.close();
	}
	
	
	public static void guardarTerminos(String term, int idf) {
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		con.saveTerm(term, idf);
	    con.close();
	}
	
	
	public static void guardarDirArchivos(File folder) {	//Al no haber clave primaria, se guardan duplicados si se recorre el mismo fichero varias veces.
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	guardarDirArchivos(fileEntry);	//Para incluir subcarpetas.
	        } else {
	            System.out.println(fileEntry.getName());
//	            con.saveDoc(fileEntry.getName(),fileEntry.getPath());
	        }
	    }
	    
	    //con.mostrarDocs();		//Para listar los documentos guardados.
	    con.close();
	}
	
	/**
	 * Method which creates an array of documents by crawling an entrypoint directory.
	 * 
	 * This method takes an empty ArrayList because since it is recursive it would always
	 * overwrite it's own list, so by taking an external reference it is able to keep on adding
	 * document objects without overriding when recursive calls are made.
	 * @param folder
	 * @return
	 */
	public static Documento[] createDocArray(File folder, ArrayList<Documento> emptyList) {	//Al no haber clave primaria, se guardan duplicados si se recorre el mismo fichero varias veces.
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	createDocArray(fileEntry, emptyList);	//Para incluir subcarpetas.
	        } 
	        else {
	        	Documento toAdd = new Documento(fileEntry.getPath());
	        	
	        	emptyList.add(toAdd);
	        }
	    }
	    
	    Documento[] docArray = emptyList.toArray(new Documento[emptyList.size()]);
	    
	    return docArray;
	}
	
	/**
	 * Takes the structure created in the dictionary and stores it in the database.
	 * Term(term, IDF)
	 * @param allTerms
	 */
	public static void storeTerms(HashMap<String, Term> allTerms) {
		int termsToStore = allTerms.values().size();
		int currentTerm = 0;
		long startTime = 0;
		long estimatedTime = 0;
		
		System.out.println("*** Indexing Term(term, idf).");
		
		try {
			DatabaseManager.connect.setAutoCommit(false);
			startTime = System.currentTimeMillis();
			
			
			for (Term term : allTerms.values()) {
				currentTerm++;
				DatabaseManager.saveTerm(term.getFilteredTerm(), term.getIDF());
//				System.out.println("Indexed term " + currentTerm + " of " + termsToStore);
			}
			
			
			DatabaseManager.connect.setAutoCommit(true);
			estimatedTime = System.currentTimeMillis() - startTime;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("--- Stored " + termsToStore + " terms in the DB.");
		System.out.println("--- Time taken to persist Term table: " + estimatedTime + "ms.");
	}
	
	/**
	 * Takes the structure created in the dictionary and stores it in the database.
	 * DocTerm(Doc, Term, Tf)
	 * @param allTerms
	 */
	public static void storeDocTerms(HashMap<String, Term> allTerms) {
		int termsToStore = allTerms.values().size();
		int currentTerm = 0;
		long startTime = 0;
		long estimatedTime = 0;
		
		System.out.println("*** Indexing DocTerm(Document, Term, TF).");
		
		try {
			DatabaseManager.connect.setAutoCommit(false);
			startTime = System.currentTimeMillis();
			
			
			for (Term term : allTerms.values()) {
				currentTerm++;
				DatabaseManager.saveTerm(term.getFilteredTerm(), term.getIDF());
				
//				Documento doc = (Documento) term.getListOfDocuments().iterator().next();
				
				for (IText doc : term.getListOfDocuments()) {
					Documento currentDoc = (Documento) doc;
					String documentName = currentDoc.toString().substring(currentDoc.toString().lastIndexOf("\\")+1);
					
//					System.out.println(documentName);
					
					DatabaseManager.saveDocTerm(documentName, term.getFilteredTerm(), term.getTFInDocument(doc));
				}
				
//				System.out.println("Indexed term " + currentTerm + " of " + termsToStore);
			}
			
			
			DatabaseManager.connect.setAutoCommit(true);
			estimatedTime = System.currentTimeMillis() - startTime;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("--- Time taken to persist DocTerm table: " + estimatedTime + "ms.");
	}
	
	
	/*public static void actualizarTerminos(String doc, String term, double weight) {
		
		Conector con = new Conector();
		con.connect();
		
		con.updateTerm(doc, term, weight);
	    con.close();
	}*/
	
	
	public static void consultar(String term) {
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		con.consultarDocTerms(term);
	    con.close();
	}
	
	public static void consultar(Vector<String> terms) {
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		con.consultarDocTerms(terms);
	    con.close();
	}
	
	public static ArrayList<Double> consultar2(Documento doc, ArrayList<String> terms, String calc) {
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		if(calc.equals("calcTF")){
			ArrayList<Double> tf = con.consultarDocTerms2(doc, terms);
			con.close();
			return tf;
		}
		if(calc.equals("calcTFIDF")){
			ArrayList<Double> idf = con.consultarTerms(terms);
			con.close();
			return idf;
		}
		
	    con.close();
	    return null;
	}
	
	
	public static void mostrar(String table) {
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		if(table.equals("Documentos")){
			con.mostrarDocs();
		}
		if(table.equals("DocTerm")){
			con.mostrarDocTerms();
		}
		if(table.equals("Term")){
			con.mostrarTerms();
		}
		
	    con.close();
	}
	
	
	public static Documento[] obtenerArrayDocs() {
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		Documento[] d = con.sacarArrayDocs();
		
	    con.close();
	    return d;
	}
	
	
	public static void resetTabla(String table) {
		
		DatabaseManager con = new DatabaseManager();
		con.connect();
		
		con.dropTable(table);
		con.createTable(table);
		
	    con.close();
	}
	
	
//	public static void saveAll(HashMap<String, Term> hat, Set<Documento> sd){
//		DatabaseManager con = new DatabaseManager();
//		con.connect();
//		
//		con.saveAllTerms(hat);
//		con.saveAllDocuments(sd);
//		
//	    con.close();
//	}
		
}
