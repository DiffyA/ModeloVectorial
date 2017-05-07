package com.RAI.ModeloVectorial.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.*;

import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class DatabaseManager {
	
	static String url = "mydb.db";
	public static Connection connect;
	
	/**
	 * Establishes a connection to the database.
	 */
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
	
	/**
	 * Closes the connection to the database.
	 */
	public static void close(){
		
		 try {
		     connect.close();
		 } catch (SQLException ex) {
		     Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		 }
	}
	
	/**
	 * Creates a table with a predefined format if the name matches.
	 * @param table
	 */
	public static void createTable(String table){
		String statement = "";
		if(table.equals("Documentos")){
			statement = "CREATE TABLE Documentos (name VARCHAR2(20) NULL, filepath VARCHAR2(80) NOT NULL, PRIMARY KEY (filepath))";
		}
		if(table.equals("DocTerm")){
			statement = "CREATE TABLE DocTerm (doc VARCHAR2(20) NOT NULL, term VARCHAR2(20) NOT NULL, termFrec NUMBER(6) NOT NULL, PRIMARY KEY (doc, term))";
		}
		if(table.equals("Term")){
			statement = "CREATE TABLE Term (term VARCHAR2(20) NOT NULL, idf NUMBER(6) NOT NULL, PRIMARY KEY (term))";
		}
        try {
        	PreparedStatement st = connect.prepareStatement(statement);	
        	st.execute();
            System.out.println("*** Tabla creada");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	/**
	 * Drops a table.
	 * @param table
	 */
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
		try {
        	PreparedStatement st = connect.prepareStatement(statement);	
            st.execute();
            //System.out.println("*** Tabla Documentos borrada");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	/**
	 * Creates a row in the DocTerms table (Doc, Term, TF). Updates the row if if the same term
	 * is inserted more than once.
	 * @param d
	 * @param t
	 * @param tf
	 */
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
	
	/**
	 * Creates a row in the Term table (Term, IDF). Updates the row if if the same term
	 * is inserted more than once.
	 * @param t
	 * @param i
	 */
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
	
	/**
	 * Shows the rows in the DocTerms table (Document, Term, TF).
	 */
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
                if(i<20){
                	i++;
                }else{
                	System.out.println("DOC \t\t\t TERM \t TF ");
                	i=0;
                }
            }
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
	
	/**
	 * Shows the rows in the Terms table (Term, IDF).
	 */
	public static void mostrarTerms(){
        ResultSet result = null;
        try {
        	PreparedStatement st = connect.prepareStatement("SELECT COUNT(*) FROM Term");
        	result = st.executeQuery();
        	System.out.println("--- Showing IDF of  "+result.getInt(1)+" Terms---");
            st = connect.prepareStatement("select * from Term");
            result = st.executeQuery();
            int i=0;
            
            System.out.println("\n");
            while (result.next()) {
                System.out.println(result.getString("term")+"\t\t\t\t"+result.getString("idf"));
                if(i<20){
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
}
	
	