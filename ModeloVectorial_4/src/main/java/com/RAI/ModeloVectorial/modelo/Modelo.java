package com.RAI.ModeloVectorial.modelo;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.logic.DocumentVector;
import com.RAI.ModeloVectorial.logic.Vectorizer;
import com.RAI.ModeloVectorial.pesos.CalculatorTF;
import com.RAI.ModeloVectorial.pesos.CalculatorTFIDF;
import com.RAI.ModeloVectorial.similiarities.CosineTFCalculator;
import com.RAI.ModeloVectorial.similiarities.CosineTFIDFCalculator;
import com.RAI.ModeloVectorial.similiarities.ScalarProductTFCalculator;
import com.RAI.ModeloVectorial.similiarities.ScalarProductTFIDFCalculator;
import com.RAI.ModeloVectorial.transformacion.Indizador;

public class Modelo {
	
	public static void main(String[] args) {
		// Create the vectorizer
		Vectorizer vectorizer = new Vectorizer();
		
		// Create the weight calculators.
		CalculatorTF calcTF = new CalculatorTF();
		CalculatorTFIDF calcTFIDF = new CalculatorTFIDF();
		
		// Create the relevance metric calculators.
		CosineTFCalculator similitudCosTF = new CosineTFCalculator();
		CosineTFIDFCalculator similitudCosTFIDF = new CosineTFIDFCalculator();
		ScalarProductTFCalculator similitudScalarTF = new ScalarProductTFCalculator();
		ScalarProductTFIDFCalculator similitudScalarTFIDF = new ScalarProductTFIDFCalculator();
		
		
		// Create the dictionary
		Diccionario dicc = new Diccionario();
		
		// Create the documents.
		Documento document1 = new Documento("src/main/resources/2010-22-100.html");
		Documento document2 = new Documento("src/main/resources/2010-42-103.html");
		Documento document3 = new Documento("src/main/resources/2010-58-044.html");
		Documento document4 = new Documento("src/main/resources/2010-76-088.html");
		Documento document5 = new Documento("src/main/resources/2010-99-086.html");
		
		// Create the document array.
		Documento[] docArray = {document1, document2, document3, document4, document5};

		// Create the queries.
		Consulta query1 = new Consulta("What video game won Spike's best driving game award in 2006?");
		Consulta query2 = new Consulta("What is the default combination of Kensington cables?");
		Consulta query3 = new Consulta("Who won the first ACM Gerard Salton prize?");

		// Create the query array.
		Consulta[] queryArray = {query1, query2, query3};
		
		// Index the documents in the dictionary.
		Indizador.indizar(docArray, dicc);
		
		// Obtain document vectors from the documents.
		// Vectors from TF
		DocumentVector[] docsTF = {vectorizer.toVector(document1, dicc, calcTF),
				vectorizer.toVector(document2, dicc, calcTF),
				vectorizer.toVector(document3, dicc, calcTF),
				vectorizer.toVector(document4, dicc, calcTF),
				vectorizer.toVector(document5, dicc, calcTF)};

//		DocumentVector doc1TF = vectorizer.toVector(document1, dicc, calcTF);
//		DocumentVector doc2TF = vectorizer.toVector(document2, dicc, calcTF);
//		DocumentVector doc3TF = vectorizer.toVector(document3, dicc, calcTF);
//		DocumentVector doc4TF = vectorizer.toVector(document4, dicc, calcTF);
//		DocumentVector doc5TF = vectorizer.toVector(document5, dicc, calcTF);
		
		// Vectors from TFIDF
		DocumentVector[] docsTFIDF = {vectorizer.toVector(document1, dicc, calcTFIDF),
				vectorizer.toVector(document2, dicc, calcTFIDF),
				vectorizer.toVector(document3, dicc, calcTFIDF),
				vectorizer.toVector(document4, dicc, calcTFIDF),
				vectorizer.toVector(document5, dicc, calcTFIDF)};
		
//		DocumentVector doc1TFIDF = vectorizer.toVector(document1, dicc, calcTFIDF);
//		DocumentVector doc2TFIDF = vectorizer.toVector(document2, dicc, calcTFIDF);
//		DocumentVector doc3TFIDF = vectorizer.toVector(document3, dicc, calcTFIDF);
//		DocumentVector doc4TFIDF = vectorizer.toVector(document4, dicc, calcTFIDF);
//		DocumentVector doc5TFIDF = vectorizer.toVector(document5, dicc, calcTFIDF);		
		
		// Obtain document vectors from the queries.
		// Vectors from TF
		DocumentVector[] queriesTF = {vectorizer.toVector(query1, dicc, calcTF),
				vectorizer.toVector(query2, dicc, calcTF),
				vectorizer.toVector(query3, dicc, calcTF)
		};
		
//		DocumentVector query1TF =  vectorizer.toVector(query1, dicc, calcTF);
//		DocumentVector query2TF =  vectorizer.toVector(query2, dicc, calcTF);
//		DocumentVector query3TF =  vectorizer.toVector(query3, dicc, calcTF);
		
		// Vectors from TFIDF
		DocumentVector[] queriesTFIDF = {vectorizer.toVector(query1, dicc, calcTFIDF),
				vectorizer.toVector(query2, dicc, calcTFIDF),
				vectorizer.toVector(query3, dicc, calcTFIDF)
		};
		
//		DocumentVector query1TFIDF =  vectorizer.toVector(query1, dicc, calcTFIDF);
//		DocumentVector query2TFIDF =  vectorizer.toVector(query1, dicc, calcTFIDF);
//		DocumentVector query3TFIDF =  vectorizer.toVector(query1, dicc, calcTFIDF);
		
//		System.out.println(query1TF);
		
		// Calculating similarity with Scalar Product using TF weights.
		System.out.println("* RELEVANCIA: ProductoEscalarTF");
		System.out.println("Nombre del doc \t \t Q1 \t Q2 \t Q3");
		
		for (Documento doc : docArray) {
			
			// Print document name
			String documentName = doc.toString().substring(doc.toString().lastIndexOf("/")+1);
			System.out.print(documentName + "\t");
			
			String stringToPrint = "";
			
			for (Consulta query : queryArray) {
				
				// Obtain vectors to compare
				DocumentVector docVector = vectorizer.toVector(doc, dicc, calcTF);
				DocumentVector queryVector = vectorizer.toVector(query, dicc, calcTF);
				
				// Obtain similarity
				double similarity = similitudScalarTF.calculate(docVector, queryVector);
				
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
			
			for (Consulta query : queryArray) {
				
				// Obtain vectors to compare
				DocumentVector docVector = vectorizer.toVector(doc, dicc, calcTFIDF);
				DocumentVector queryVector = vectorizer.toVector(query, dicc, calcTFIDF);
				
				// Obtain similarity
				double similarity = similitudScalarTFIDF.calculate(docVector, queryVector);
				
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
			
			for (Consulta query : queryArray) {
				
				// Obtain vectors to compare
				DocumentVector docVector = vectorizer.toVector(doc, dicc, calcTF);
				DocumentVector queryVector = vectorizer.toVector(query, dicc, calcTF);
				
				// Obtain similarity
				double similarity = similitudCosTF.calculate(docVector, queryVector);
				
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
			
			for (Consulta query : queryArray) {
				
				// Obtain vectors to compare
				DocumentVector docVector = vectorizer.toVector(doc, dicc, calcTFIDF);
				DocumentVector queryVector = vectorizer.toVector(query, dicc, calcTFIDF);
				
				// Obtain similarity
				double similarity = similitudCosTFIDF.calculate(docVector, queryVector);
				
				// Add content to stringToPrint
				stringToPrint += similarity + "\t";
			}
			System.out.println(stringToPrint);
		}

	}

}
