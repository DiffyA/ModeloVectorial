package com.RAI.ModeloVectorial.expansion;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Term;
import com.RAI.ModeloVectorial.queryExpansion.QueryExpander;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by kgeetz on 5/18/17.
 */
public class QueryExpansionTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testDictionaryInitialization(){

        //Create expander
        QueryExpander qe = new QueryExpander();

        //Create query
        Query query = new Query("red car apple thing ice award");

        System.out.println("The query terms before expansion are: ");
        for (Term t : query.getTerms()){
            System.out.println(t + ": " + t.getWeight());
        }
        System.out.println();

        qe.expandQuery(query);
        System.out.println("The query terms after expansion are: ");
        for (Term t : query.getTerms()){
            System.out.println(t + ": " + t.getWeight());
        }
    }
}
