package com.RAI.ModeloVectorial.modelo;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.logic.DocumentVector;
import com.RAI.ModeloVectorial.logic.Vectorizer;
import com.RAI.ModeloVectorial.pesos.CalculatorTF;
import com.RAI.ModeloVectorial.pesos.CalculatorTFIDF;
import com.RAI.ModeloVectorial.similiarities.CosineCalculator;
import com.RAI.ModeloVectorial.similiarities.ScalarProductCalculator;
import com.RAI.ModeloVectorial.transformacion.Indizador;

public class Modelo {
	
	public static void main(String[] args) {
		// Create the vectorizer
		Vectorizer vectorizer = new Vectorizer();
		
		// Create the weight calculators.
		CalculatorTF calcTF = new CalculatorTF();
		CalculatorTFIDF calcTFIDF = new CalculatorTFIDF();
		
		// Create the relevance metric calculators.
		CosineCalculator similitudCos = new CosineCalculator();
		ScalarProductCalculator similitudScalar = new ScalarProductCalculator();
		
		
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
		Query query1 = new Query("What video game won Spike's best driving game award in 2006?");
		Query query2 = new Query("What is the default combination of Kensington cables?");
		Query query3 = new Query("Who won the first ACM Gerard Salton prize?");

		// Create the query array.
		Query[] queryArray = {query1, query2, query3};
		
		// Index the documents in the dictionary.
		Indizador.indizar(docArray, dicc);
		
		// Obtain document vectors from the documents.
		// Vectors from TF
		DocumentVector[] docsTF = {vectorizer.toVector(document1, dicc, calcTF),
				vectorizer.toVector(document2, dicc, calcTF),
				vectorizer.toVector(document3, dicc, calcTF),
				vectorizer.toVector(document4, dicc, calcTF),
				vectorizer.toVector(document5, dicc, calcTF)};

		// Vectors from TFIDF
		DocumentVector[] docsTFIDF = {vectorizer.toVector(document1, dicc, calcTFIDF),
				vectorizer.toVector(document2, dicc, calcTFIDF),
				vectorizer.toVector(document3, dicc, calcTFIDF),
				vectorizer.toVector(document4, dicc, calcTFIDF),
				vectorizer.toVector(document5, dicc, calcTFIDF)};
		
		// Obtain document vectors from the queries.
		// Vectors from TF
		DocumentVector[] queriesTF = {vectorizer.toVector(query1, dicc, calcTF),
				vectorizer.toVector(query2, dicc, calcTF),
				vectorizer.toVector(query3, dicc, calcTF)
		};
		
		// Vectors from TFIDF
		DocumentVector[] queriesTFIDF = {vectorizer.toVector(query1, dicc, calcTFIDF),
				vectorizer.toVector(query2, dicc, calcTFIDF),
				vectorizer.toVector(query3, dicc, calcTFIDF)
		};
		
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
				DocumentVector docVector = vectorizer.toVector(doc, dicc, calcTF);
				DocumentVector queryVector = vectorizer.toVector(query, dicc, calcTF);
				
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
				DocumentVector docVector = vectorizer.toVector(doc, dicc, calcTFIDF);
				DocumentVector queryVector = vectorizer.toVector(query, dicc, calcTFIDF);
				
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
				DocumentVector docVector = vectorizer.toVector(doc, dicc, calcTF);
				DocumentVector queryVector = vectorizer.toVector(query, dicc, calcTF);
				
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
				DocumentVector docVector = vectorizer.toVector(doc, dicc, calcTFIDF);
				DocumentVector queryVector = vectorizer.toVector(query, dicc, calcTFIDF);
				
				// Obtain similarity
				double similarity = similitudCos.calculate(docVector, queryVector);
				
				// Add content to stringToPrint
				stringToPrint += similarity + "\t";
			}
			System.out.println(stringToPrint);
		}

	}

}
