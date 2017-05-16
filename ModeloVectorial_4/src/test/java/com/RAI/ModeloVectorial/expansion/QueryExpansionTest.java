package com.RAI.ModeloVectorial.expansion;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by kgeetz on 5/16/17.
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
        try {
            JWNL.initialize(new FileInputStream("/home/kgeetz/Programming/information_access_retrieval/ModeloVectorial/ModeloVectorial_4/src/main/resources/properties.xml"));

            final Dictionary dictionary = Dictionary.getInstance();
            final IndexWord indexWord = dictionary.lookupIndexWord(POS.NOUN, "test");

            Synset[] senses = indexWord.getSenses();
            //Synset[] synsets = dictionary.getSynsets("your word", SynsetType.NOUN);

            for (Synset set : senses) {
                for (Word w: set.getWords()){
                    System.out.println(w.getLemma());
                }
            }
        } catch (JWNLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
