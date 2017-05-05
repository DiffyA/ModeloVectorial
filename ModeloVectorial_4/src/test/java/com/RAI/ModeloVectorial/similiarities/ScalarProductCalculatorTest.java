package com.RAI.ModeloVectorial.similiarities;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.logic.DocumentVector;
import com.RAI.ModeloVectorial.logic.Vectorizer;
import com.RAI.ModeloVectorial.pesos.CalculatorTFIDF;
import com.RAI.ModeloVectorial.transformacion.Indizador;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * Created by kgeetz on 5/2/17.
 */
public class ScalarProductCalculatorTest {
    ScalarProductCalculator calculator = new ScalarProductCalculator();

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
        CalculatorTFIDF calculatorTFIDF = new CalculatorTFIDF();
        Vectorizer vectorizer = new Vectorizer();
        Dictionary dicc = new Dictionary();
        Documento doc1 = new Documento("src/test/resources/testCalculatorTFIDFtest/testDocument1.txt");
        Documento doc2 = new Documento("src/test/resources/testCalculatorTFIDFtest/testDocument2.txt");
        Query query = new Query("red car red");

        // Add the documents to the dictionary
        Documento[] documentsToAdd = {doc1, doc2};
        Indizador.indizar(documentsToAdd, dicc);

        // Print the term list stored in the dictionary index
        System.out.println("Terms in dictionary index:" + dicc.getTermList() + "\n");

        // Vectorize all documents, as well as the query
        DocumentVector docVector1 = vectorizer.toVector(doc1, dicc, calculatorTFIDF);
        DocumentVector docVector2 = vectorizer.toVector(doc2, dicc, calculatorTFIDF);
        DocumentVector queryVector = vectorizer.toVector(query, dicc, calculatorTFIDF);

        // Check the similarity between docVector1 and queryVector
        // The following expected results have been calculated by hand:
        double expected = 0.1812381165789131;
        
        assertEquals(expected, calculator.calculate(docVector1, queryVector), 0);
        
        // Check the similarity between docVector2 and queryVector
        // The following expected results have been calculated by hand:
        expected = 0;
        
        assertEquals(expected, calculator.calculate(docVector2, queryVector), 0);
    }

}

