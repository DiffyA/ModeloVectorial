package com.RAI.ModeloVectorial.similiarities;

import com.RAI.ModeloVectorial.core.Consulta;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.diccionario.Diccionario;
import com.RAI.ModeloVectorial.logic.DocumentVector;
import com.RAI.ModeloVectorial.logic.Vectorizer;
import com.RAI.ModeloVectorial.pesos.CalculatorTFIDF;
import com.RAI.ModeloVectorial.transformacion.Indizador;
import org.junit.*;

/**
 * Created by kgeetz on 5/2/17.
 */
public class CalculatorTFTest {
    CosineTFCalculator cosCalculator = new CosineTFCalculator();
    ScalarProductTFCalculator SPCalculator = new ScalarProductTFCalculator();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCalculate() {
        CalculatorTFIDF calculator = new CalculatorTFIDF();
        Vectorizer vectorizer = new Vectorizer();
        CalculatorTFIDF calc = new CalculatorTFIDF();
        Diccionario dicc = new Diccionario();
        Documento doc1 = new Documento("src/test/resources/testCalculatorTFIDFtest/testDocument1.txt");
        Documento doc2 = new Documento("src/test/resources/testCalculatorTFIDFtest/testDocument2.txt");
        Consulta query = new Consulta("red car red");

        // Double check the terms inside the query
        System.out.println("Terms in the query: " + query.getTerms() + "\n");

        // Iterate through the terms in the query to check that their TF is correct
        // It should be 2 for RED, and 1 for CAR.
        for (Term t : query.getTerms()) {
            System.out.println("TF of " + t.getTerm() + ": " + t.getTFInDocument(query));
        }
        System.out.println("\n");

        // Add the documents to the dictionary
        Documento[] documentsToAdd = {doc1, doc2};
        Indizador.indizar(documentsToAdd, dicc);

        // Print the term list stored in the dictionary index
        System.out.println("Terms in dictionary index:" + dicc.getTermList() + "\n");

        // Vectorize all documents, as well as the query
        DocumentVector docVector1 = vectorizer.toVector(doc1, dicc, calculator);
        DocumentVector docVector2 = vectorizer.toVector(doc2, dicc, calculator);
        DocumentVector queryVector = vectorizer.toVector(query, dicc, calculator);

        // Print them out to check their contents.
        System.out.println("Docvector1: \n" + docVector1);
        System.out.println("Docvector2: \n" + docVector2);
        System.out.println("Queryvector: \n" + queryVector);

        //Print out Vector comparisons
        System.out.println("Cos, Docvector1 + query: " + cosCalculator.calculate(docVector1, queryVector));
        System.out.println("SP, Docvector1 + query: " + SPCalculator.calculate(docVector1, queryVector));
        System.out.println("Cos, Docvector2 + query: " + cosCalculator.calculate(docVector2, queryVector));
        System.out.println("SP, Docvector2 + query: " + SPCalculator.calculate(docVector2, queryVector));

    }

}

