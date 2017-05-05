package com.RAI.ModeloVectorial.similiarities;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Documento;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.dictionary.Dictionary;
import com.RAI.ModeloVectorial.pesos.CalculatorTFIDF;
import com.RAI.ModeloVectorial.transformacion.Indizador;
import com.RAI.ModeloVectorial.vector.Vector;
import com.RAI.ModeloVectorial.vector.Vectorizer;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * Created by kgeetz on 5/2/17.
 */
public class CosineCalculatorTest {
    CosineCalculator cosCalculator = new CosineCalculator();

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
        Vector docVector1 = vectorizer.toVector(doc1, dicc, calculator);
        Vector docVector2 = vectorizer.toVector(doc2, dicc, calculator);
        Vector queryVector = vectorizer.toVector(query, dicc, calculator);

        // Check the similarity between docVector1 and queryVector
        // The following expected results have been calculated by hand:
        double scalarProduct = 0.1812381165789131;
        double divisor = 0.6534633223937912;
        double expected = scalarProduct / divisor; 
        
        assertEquals(expected, cosCalculator.calculate(docVector1, queryVector), 0);
        
        // Check the similarity between docVector2 and queryVector
        // The following expected results have been calculated by hand:
        scalarProduct = 0; // This value is 0 because doc2 and the query do not share any terms.
        divisor = 0.3624762331578262;
        expected = scalarProduct / divisor; 
        
        assertEquals(expected, cosCalculator.calculate(docVector2, queryVector), 0);
    }

}

