package com.RAI.ModeloVectorial.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.*;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.vector.DocVector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class DatabaseManager {
	
	static String url = "mydb.db";
	public static Connection connect;
	
	
	public static void connect(){
		
		 try {
		     connect = DriverManager.getConnection("jdbc:sqlite:"+url);
		     if (connect!=null) {
		         System.out.println("*** Conectado");
		     }
		 }catch (SQLException ex) {
		     System.err.println("Error connecting to the database.\n"+ex.getMessage());
		 }
	}
	
	
	public static void close(){
		
		 try {
		     connect.close();
		 } catch (SQLException ex) {
		     Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		 }
	}
	
	
	public static void createTable(String table){
		String statement = "";
		if(table.equals("Documentos")){
			statement = "CREATE TABLE Documentos (name VARCHAR2(20) NULL, filepath VARCHAR2(80) NOT NULL, PRIMARY KEY (filepath))";
		}
		else if(table.equals("DocTerm")){
			statement = "CREATE TABLE DocTerm (doc VARCHAR2(80) NOT NULL, term VARCHAR2(20) NOT NULL, termFrec NUMBER(6) NOT NULL, PRIMARY KEY (doc, term))";
		}
		else if(table.equals("Term")){
			statement = "CREATE TABLE Term (term VARCHAR2(20) NOT NULL, idf NUMBER(6) NOT NULL, PRIMARY KEY (term))";
		}
		else if(table.equals("Relevancias")){
			statement = "CREATE TABLE Relevancias (consulta VARCHAR2(20) NOT NULL, documento VARCHAR2(80) NOT NULL, relevancia NUMBER(1) NOT NULL, PRIMARY KEY (consulta, documento))";
		}
		else if(table.equals("TermQuery")){
			statement = "CREATE TABLE TermQuery (id VARCHAR2(20) NOT NULL, term VARCHAR2(20) NOT NULL, PRIMARY KEY (id, term))";
		}
		/*else{
			statement = "CREATE TABLE "+table+" (doc VARCHAR2(80) NOT NULL, cos NUMBER(10) NOT NULL, PRIMARY KEY (doc))";
			System.out.println(statement);
		}*/
        try {
        	PreparedStatement st = connect.prepareStatement(statement.toString());	
        	st.execute();
            System.out.println("*** Tabla '"+table+"' creada");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	public static void dropTable(String table){
		String statement = "";
		if(table.equals("Documentos")){
			statement = "DROP TABLE IF EXISTS Documentos";
		}
		if(table.equals("DocTerm")){
			statement = "DROP TABLE IF EXISTS DocTerm";
		}
		if(table.equals("Term")){
			statement = "DROP TABLE IF EXISTS Term";
		}
		if(table.equals("Relevancias")){
			statement = "DROP TABLE IF EXISTS Relevancias";
		}
		if(table.equals("TermQuery")){
			statement = "DROP TABLE IF EXISTS TermQuery";
		}
		try {
        	PreparedStatement st = connect.prepareStatement(statement);	
            st.execute();
            System.out.println("*** Tabla "+table+" borrada");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	public static void storeRelevance(String filepath){
		File file = new File(filepath);	//Ruta donde buscar las relevancias.
		
		try{
			Scanner scanner = new Scanner(file);
			connect.setAutoCommit(false);
		    while(scanner.hasNext()){
		    	String[] line = scanner.nextLine().split("\t");
		    	//System.out.println("line "+line[1]+" "+line[2]+" "+line[3]);
				//																		(query, doc, rel)
		    	PreparedStatement st = connect.prepareStatement("insert into Relevancias values (?,?,?)");
	        	//for(int i=0; i<line.length; i++){
	        		st.setString(1, line[1]);
	        		st.setString(2, line[2]);
	        		st.setString(3, line[3]);
	        		st.execute();
	        		System.out.println("insertada relevancia: "+line[1]+" "+line[2]+" "+line[3]);
	        	//}
		    }
		    scanner.close();
		    System.out.println("*** Relevancias guardadas");
		    connect.setAutoCommit(true);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public static ArrayList<String> consultarDocsRelevantes(String query, int rel){
        ResultSet result = null;
        try {
        	PreparedStatement st = connect.prepareStatement("SELECT documento FROM Relevancias WHERE consulta=? AND relevancia>=?");
        	st.setString(1, query);
        	st.setInt(2, rel);
            result = st.executeQuery();
            
            ArrayList<String> relevantes = new ArrayList<String>();
            
			while(result.next()){
				relevantes.add(result.getObject(1).toString());
			}
			
			//Aqui no hace falta hacer el BUBBLE SORT
            
            return relevantes;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
	
	public static void saveDocTerm(String d, String t, double tf){
        try{
        	PreparedStatement st = connect.prepareStatement("SELECT termFrec FROM DocTerm WHERE doc=? AND term=?");	//Miramos si ya existe el termino
            st.setString(1, d);
            st.setString(2, t);
            ResultSet result = st.executeQuery();
            
            if (!result.next() ) {		//Si no existe, lo metemos
            	st = connect.prepareStatement("insert into DocTerm values (?,?,?)");
	            st.setString(1, d);
	            st.setString(2, t);
	            st.setDouble(3, tf);
	            st.execute();
	            //System.out.println("*** Termino guardado");
            	
            }else{		//Si ya existe, lo actualizamos
                double rtf = result.getDouble("termFrec");
                
                st = connect.prepareStatement("UPDATE DocTerm SET termFrec=? WHERE doc=? AND term=?");
                st.setDouble(1, tf);
                st.setString(2, d);
                st.setString(3, t);
                st.execute();
                //System.out.println("*** Termino actualizado");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	public static void saveTerm(String t, double i){
        try{
        	PreparedStatement st = connect.prepareStatement("SELECT idf FROM Term WHERE term=?");	//Miramos si ya existe el termino
            st.setString(1, t);
            ResultSet result = st.executeQuery();

            if (!result.next() ) {		//Si no existe, lo metemos
            	st = connect.prepareStatement("insert into Term values (?,?)");
	            st.setString(1, t);
	            st.setDouble(2, i);
	            st.execute();
	            //System.out.println("*** Termino guardado");
            	
            }else{		//Si ya existe, lo actualizamos
                double ri = (double) result.getDouble("idf");
                
                st = connect.prepareStatement("UPDATE Term SET idf=? WHERE term=?");
                st.setDouble(1, ri + i);
                st.setString(2, t);
                st.execute();
                //System.out.println("*** Termino actualizado");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	public void saveDoc(String nom, String dir){
        try {
            PreparedStatement st = connect.prepareStatement("insert into Documentos values (?,?)");
            st.setString(1, nom);
            st.setString(2, dir);
            st.execute();
            //System.out.println("*** Documento guardado");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	/*public void updateTerm(String d, String t, double w){
        try {
            //PreparedStatement st = connect.prepareStatement("SELECT x FROM DocTerm x WHERE x.doc=:d AND x.term=:t");
        	PreparedStatement st = connect.prepareStatement("SELECT weight FROM DocTerm WHERE doc=? AND term=?");
            st.setString(1, d);
            st.setString(2, t);
            //st.execute();
            //ResultSet result = st.getResultSet();
            
            double result = st.executeQuery().getDouble("weight");
            
            st = connect.prepareStatement("UPDATE DocTerm SET weight=? WHERE doc=? AND term=?");
            st.setDouble(1, result + w);
            st.setString(2, d);
            st.setString(3, t);
            st.execute();
            //System.out.println("*** Termino actualizado");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }*/
	
	
	public void consultarDocTerms(String term){
        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select doc,termFrec from DocTerm where term=?");
			st.setString(1,term);
            result = st.executeQuery();
            
            ArrayList<String> docs = new ArrayList<String>();
            ArrayList<Integer> tfs = new ArrayList<Integer>();
            
			for(int i=0; result.next(); i++){
				/*System.out.println("* * * * * row " +  result.getObject(result.getRow()) );
				System.out.println("* * * * * 1 " +  result.getObject(1) );
				System.out.println("* * * * * 2 " +  result.getObject(2) );*/
				docs.add(result.getObject(1).toString());
				tfs.add((Integer) (result.getObject(2)));
			}
			
			//BUBBLE SORT
			int j;
			boolean flag = true;   // set flag to true to begin first pass
			while (flag){
				flag = false;    //set flag to false awaiting a possible swap
				for(j=0; j<tfs.size()-1; j++){
					if ( tfs.get(j) < tfs.get(j+1) ){   // change to > for ascending sort
						tfs.add(j+1, tfs.remove(j));	//swap elements
						docs.add(j+1, docs.remove(j));
						flag = true;              //shows a swap occurred  
					}
				}
			}
			
            System.out.println("--- Mostrar Docs con '"+term+"' segun TF (10 mejores resultados) ---");
            for (int i=0; i<10 && i<docs.size(); i++) {
                System.out.print("Doc: ");
                System.out.println(docs.get(i));
                System.out.print("TF: ");
                System.out.println(tfs.get(i));
                System.out.println("=======================");
            }
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	public void consultarDocTerms(Vector<String> terms){
        ResultSet result = null;
        try {
        	String consulta = "select doc,termFrec from DocTerm where term=?";
        	for (int i=1; i<terms.size(); i++){
        		consulta += " or term=?";
        	}
            PreparedStatement st = connect.prepareStatement(consulta);
			for (int i=0; i<terms.size(); i++){
				st.setString(i+1, terms.get(i));
				//System.out.println("* * * * * 2 " +  terms.get(i) );
        	}
            result = st.executeQuery();
            
            ArrayList<String> docs = new ArrayList<String>();
            ArrayList<Integer> tfs = new ArrayList<Integer>();
            
			for(int i=0; result.next(); i++){
				/*System.out.println("* * * * * row " +  result.getObject(result.getRow()) );
				System.out.println("* * * * * 1 " +  result.getObject(1) );
				System.out.println("* * * * * 2 " +  result.getObject(2) );*/
				docs.add(result.getObject(1).toString());
				tfs.add((Integer) (result.getObject(2)));
			}
			
			//BUBBLE SORT
			int j;
			boolean flag = true;   // set flag to true to begin first pass
			while (flag){
				flag = false;    //set flag to false awaiting a possible swap
				for(j=0; j<tfs.size()-1; j++){
					if ( tfs.get(j) < tfs.get(j+1) ){   // change to > for ascending sort
						tfs.add(j+1, tfs.remove(j));	//swap elements
						docs.add(j+1, docs.remove(j));
						flag = true;              //shows a swap occurred  
					}
				}
			}
			
            System.out.print("--- Mostrar Docs con '"+terms.get(0)+"'");
            for (int i=1; i<terms.size(); i++){
            	System.out.print(", '"+terms.get(i)+"'");
        	}
            System.out.println(" segun TF (10 mejores resultados) ---");
            for (int i=0; i<10 && i<docs.size(); i++) {
                System.out.print("Doc: ");
                System.out.println(docs.get(i));
                System.out.print("TF: ");
                System.out.println(tfs.get(i));
                System.out.println("=======================");
            }
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	public ArrayList<Double> consultarDocTerms2(Documento doc, ArrayList<String> terms){
		//System.out.println("***PROBANDO 2***: "+terms.get(0));
		//System.out.println("***PROBANDO 2***: "+doc);
        ResultSet result = null;
        try {
        	/*String consulta = "select term,termFrec from DocTerm where doc=? and term=?";
        	for (int i=1; i<terms.size(); i++){
        		consulta += " or term=?";
        	}*/
        	
        	String consulta = "select term,termFrec from DocTerm where doc=?";
            PreparedStatement st = connect.prepareStatement(consulta);
            st.setString(1, doc.toString());
            result = st.executeQuery();
            
            ArrayList<Double> tfs = new ArrayList<Double>();
            
			for(int i=0; result.next(); i++){
				//System.out.println(result.getObject(1));
				//System.out.println(result.getObject(2));
				for(int j=0; j<terms.size(); j++){
					/*System.out.println(result.getObject(1));
					System.out.println(result.getObject(2));
					System.out.println(result.getObject("term"));
					System.out.println(terms.get(j));*/
					if(result.getObject("term").equals(terms.get(j))){		//Se pasan con espacios al final
						//System.out.println("***PROBANDO 3***: "+result.getObject("termFrec"));
						tfs.add(Double.parseDouble((result.getObject("termFrec").toString())));
					}
				}
			}
			
			//BUBBLE SORT
			/*int j;
			boolean flag = true;   // set flag to true to begin first pass
			while (flag){
				flag = false;    //set flag to false awaiting a possible swap
				for(j=0; j<tfs.size()-1; j++){
					if ( tfs.get(j) < tfs.get(j+1) ){   // change to > for ascending sort
						tfs.add(j+1, tfs.remove(j));	//swap elements
						docs.add(j+1, docs.remove(j));
						flag = true;              //shows a swap occurred  
					}
				}
			}*/
			
            /*System.out.print("--- Mostrar Docs con '"+terms.get(0)+"'");
            for (int i=1; i<terms.size(); i++){
            	System.out.print(", '"+terms.get(i)+"'");
        	}
            System.out.println(" segun TF (10 mejores resultados) ---");
            for (int i=0; i<10 && i<docs.size(); i++) {
                System.out.print("Doc: ");
                System.out.println(docs.get(i));
                System.out.print("TF: ");
                System.out.println(tfs.get(i));
                System.out.println("=======================");
            }
            System.out.println("");*/
			//System.out.println("***PROBANDO 4***: "+tfs.get(0));
            return tfs;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
	
	
	public ArrayList<Double> consultarTerms_old(ArrayList<String> terms){
        ResultSet result = null;
        try {
        	String consulta = "select idf from Term where term=?";
        	int i;
        	for (i=1; i<terms.size(); i++){
        		consulta += " or term=?";
        	}
            PreparedStatement st = connect.prepareStatement(consulta);
            
			for (i=0; i<terms.size(); i++){
				st.setString(i+1, terms.get(i));
        	}
            result = st.executeQuery();
            
            ArrayList<Double> idfs = new ArrayList<Double>();
            
			for(i=0; result.next(); i++){
				idfs.add(Double.parseDouble((result.getObject(1).toString())));
			}
			
			//BUBBLE SORT
            
			System.out.println("");
			System.out.println(" idfs.size="+idfs.size());
            return idfs;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
	
	public ArrayList<Double> consultarTerms(ArrayList<String> terms){
        ResultSet result = null;
        System.out.println("");
        try {
        	String consulta = "select term,idf from Term where term=?";
        	int i;
        	for (i=1; i<terms.size(); i++){
        		consulta += " or term=?";
        	}
        	//System.out.println(" i1="+i);
            PreparedStatement st = connect.prepareStatement(consulta);
            
            int x=0;
			for (i=0; i<terms.size(); i++){
				st.setString(i+1, terms.get(i));
				for (int j=i+1; j<terms.size(); j++){
					if(terms.get(i).equals(terms.get(j))){
						x++;
					}
				}
        	}
			//System.out.println(" x="+x);
			//System.out.println(" i2="+i);
            result = st.executeQuery();
            
            ArrayList<Double> idfs = new ArrayList<Double>();
            
			for(i=0; result.next(); i++){
				idfs.add(Double.parseDouble((result.getObject(2).toString())));
			}
			//System.out.println(" i3="+i);
			
			//BUBBLE SORT
            
			//System.out.println(" idfs.size="+idfs.size());
            return idfs;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
	
	public static void mostrarRelevancias() {
        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Relevancias");
            result = st.executeQuery();
            System.out.println("--- Mostrar Relevancias ---");
            int i=0;
            while (result.next()) {
            	System.out.print(result.getString("consulta"));
                System.out.print("\t"+result.getString("documento"));
                System.out.println("\t"+result.getString("relevancia"));
                if(i<10){
                	i++;
                }else{
                	i=0;
                }
            }
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}
	
	public static void mostrarDocTerms(){
        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from DocTerm");
            result = st.executeQuery();
            System.out.println("--- Mostrar Docs y Terminos ---");
            int i=0;
            while (result.next()) {
            	System.out.print(result.getString("doc"));
                System.out.print("\t"+result.getString("term"));
                System.out.println("\t"+result.getString("termFrec"));
                if(i<10){
                	i++;
                }else{
                	System.out.println(" DOC   *  *  *  *  *  *  *  *  *  *  *  *   TERM  * * *  TF ");
                	i=0;
                }
            }
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	public static void mostrarTerms(){
        ResultSet result = null;
        try {
        	PreparedStatement st = connect.prepareStatement("SELECT COUNT(*) FROM Term");
        	result = st.executeQuery();
        	System.out.println("--- Mostrar "+result.getInt(1)+" Terminos ---");
            st = connect.prepareStatement("select * from Term");
            result = st.executeQuery();
            int i=0;
            while (result.next()) {
                System.out.println(result.getString("term")+"\t"+result.getString("idf"));
                if(i<10){
                	i++;
                }else{
                	System.out.println(" TERM  *  IDF  *");
                	i=0;
                }
            }
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	public void mostrarDocs(){
        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Documentos");
            result = st.executeQuery();
            System.out.println("--- Mostrar Documentos ---");
            while (result.next()) {
                System.out.print("Name: ");
                System.out.println(result.getString("name"));
                System.out.print("File path: ");
                System.out.println(result.getString("filepath"));
                System.out.println("=======================");
            }
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	
	public Documento[] sacarArrayDocs(){
		Documento[] arrayDocs;
        ResultSet result = null;
        try {
        	PreparedStatement st = connect.prepareStatement("SELECT COUNT(*) FROM Documentos");
        	result = st.executeQuery();
        	int rows = result.getInt(1);
        	System.out.println("rows: "+rows);
        	
        	arrayDocs = new Documento[rows];
            st = connect.prepareStatement("select * from Documentos");
            result = st.executeQuery();
            for (int i=0; result.next(); i++) {
            	Documento doc = new Documento(result.getString("filepath"));
            	arrayDocs[i] = doc;
                /*System.out.print("File path: ");
                System.out.println(doc.getFilePath());
                System.out.println("=======================");*/
            }
            //System.out.println("");
            return arrayDocs;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
	
	
	public double[] sacarRelevancias (String query, Vector<String> recs, Vector<String> rels){
        ResultSet result = null;
        try {
        	PreparedStatement st = connect.prepareStatement("SELECT documento,relevancia FROM Relevancias WHERE consulta=?");
        	st.setString(1, query);
        	result = st.executeQuery();
        	
        	ArrayList<String> d = new ArrayList<String>();
            ArrayList<Integer> r = new ArrayList<Integer>();	//necesario ?
            
            while(result.next()){
            	for(int j=0; j<recs.size(); j++){
    				if(result.getObject(1).toString().equals(recs.get(j))){
    					d.add(j, result.getObject(1).toString());
        				r.add(j, (Integer) (result.getObject(2)));
    				}
    			}
            }
			
            int[] cg = new int[r.size()];
            int x = 0;
        	for(int i=0; i<cg.length; i++){
        		x += r.get(i);
        		cg[i] = x;
        	}
        	
        	double[] dcg = new double[cg.length];
        	int y = 0;
        	for(int i=0; i<dcg.length; i++){
        		if(r.get(i) >= 2){				// b es 2 ???
        			y += r.get(i) / (Math.log(i+1)/Math.log(2));
            		dcg[i] = y;
        		}else{
        			dcg[i] = y;
        		}
        	}
        	
        	//BUBBLE SORT
			int j;
			boolean flag = true;   // set flag to true to begin first pass
			while (flag){
				flag = false;    //set flag to false awaiting a possible swap
				for(j=0; j<r.size()-1; j++){
					if ( r.get(j) < r.get(j+1) ){   // change to > for ascending sort
						r.add(j+1, r.remove(j));	//swap elements
						flag = true;              //shows a swap occurred  
					}
				}
			}
			
			int[] icg = new int[d.size()];
            x = 0;
        	for(int i=0; i<icg.length; i++){
        		x += r.get(i);
        		icg[i] = x;
        	}
        	
        	double[] idcg = new double[icg.length];
        	y = 0;
        	for(int i=0; i<idcg.length; i++){
        		if(r.get(i) >= 2){				// b es 2 ???
        			y += r.get(i) / (Math.log(i+1)/Math.log(2));
            		idcg[i] = y;
        		}else{
        			idcg[i] = y;
        		}
        	}
			
        	double[] ndcg = new double[cg.length];
        	System.out.print("nDCG:");
        	for(int i=0; i<idcg.length; i++){
        		ndcg[i] = dcg[i]/idcg[i];
        		System.out.print(" "+ndcg[i]);
        	}
        	System.out.println("-------------------------------------------");
        	
        	return ndcg;
        	
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return null;
    }
	
	/**
	 * Returns a list of all documents in the DB expressed as TFIDF vectors.
	 */
	public static void obtainAllTFIDFVectors() {
		ArrayList<DocVector> vectors = new ArrayList<DocVector>();
		
		 ResultSet result = null;
	        try {
	        	PreparedStatement st = connect.prepareStatement("SELECT * FROM Documentos");
	        	result = st.executeQuery();
	        	int rows = result.getInt(1);
	        	System.out.println("rows: "+rows);
	        	
	            for (int i=0; result.next(); i++) {
	            	Documento doc = new Documento(result.getString("filepath"));
	                System.out.print("File path: ");
	                System.out.println(doc.getFilePath());
//	                System.out.println("=======================");
	            }
	            //System.out.println("");
	        } catch (SQLException ex) {
	            System.err.println(ex.getMessage());
	        }
	        
	}
	
//	public void saveAllDocuments(Set<Documento> sd){
//        try{
//        	Documento aux;
//        	PreparedStatement st = connect.prepareStatement("insert into DocTerm values (?,?,?)");
//        	for(int i=0; i<sd.size(); i++){
//        		aux = sd.iterator().next();
//        		st.setString(1, aux.getDoc());
//        		st.setString(2, aux.getTerm());
//        		st.setDouble(3, aux.getTF());
//        		st.executeQuery();
//        	}
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
	
	/**
	 * Method used to take a dictionary index into the database
	 * @param hat
	 */
//	public void saveAllTerms(HashMap<String, Term> allTerms){
//		
//		try {
//			// To enable multiple operations per transaction
//			connect.setAutoCommit(false);
//			PreparedStatement st = connect.prepareStatement("insert into DocTerm values (?,?,?)");
//            st.setString(1, d);
//            st.setString(2, t);
//            st.setDouble(3, tf);
//            st.execute();
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//        try{
//        	
//        	for(int i=0; i<hat.size(); i++){
//        		st.setString(1, hat.get(i).getTerm());
//        		st.setDouble(2, hat.get(i).getIDF());
//        		st.executeQuery();
//        	}
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
	
	
	/*public static void BubbleSort( int [ ] num ){
		int j;
		boolean flag = true;   // set flag to true to begin first pass
		int temp;   //holding variable

		while (flag){
			flag= false;    //set flag to false awaiting a possible swap
			for( j=0;  j < num.length -1;  j++ ){
				if ( num[ j ] < num[j+1] ){   // change to > for ascending sort
					temp = num[ j ];                //swap elements
					num[ j ] = num[ j+1 ];
					num[ j+1 ] = temp;
					flag = true;              //shows a swap occurred  
				}
			}
		}
	}*/
	
}
