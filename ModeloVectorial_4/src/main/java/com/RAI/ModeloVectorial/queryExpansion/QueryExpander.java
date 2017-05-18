package com.RAI.ModeloVectorial.queryExpansion;

import com.RAI.ModeloVectorial.core.Query;
import com.RAI.ModeloVectorial.core.Term;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import static java.lang.Math.pow;

/**
 * Created by kgeetz on 5/16/17.
 */
public class QueryExpander {

    private Dictionary dictionary = null;

    public QueryExpander(){
        //Initialize dictionary
        try {
            JWNL.initialize(ClassLoader.getSystemResourceAsStream("properties.xml"));
            dictionary = Dictionary.getInstance();
        } catch (JWNLException e) {
            e.printStackTrace();
        }


    }

    public void expandQuery(Query toExpand) {
        //Generate additional terms for every term in the query
        Set<Term> queryTerms = toExpand.getTerms();
        Set<Term> expandedTerms = new HashSet<>();

        //Set of all starting terms
        Set<String> newTerms = new HashSet<>();
        for (Term t: queryTerms){newTerms.add(t.getTerm());}

        // New terms get added to a buffer set
        for (Term t : queryTerms) {
            Set<Term> additional = expandTerm(t);
            for(Term et: additional){
                if(!newTerms.contains(et.getTerm())){
                    newTerms.add(et.getTerm());
                    expandedTerms.add(et);
                }
            }
        }

        //Add all terms in the buffer to the original query
        queryTerms.addAll(expandedTerms);


    }

    private Set<Term> expandTerm(Term t) {

        HashSet<Term> expandedTerms = null;
        try {
            Set<String> newWords = new HashSet<>();
            expandedTerms = new HashSet<Term>();

            Vector<IndexWord> indexWords = new Vector<>();
            indexWords.add(dictionary.lookupIndexWord(POS.NOUN, t.toString()));
            indexWords.add(dictionary.lookupIndexWord(POS.VERB, t.toString()));

            //Add all new words to the expanded set, with reduced weight
            for (IndexWord iw : indexWords){
                if(iw!= null){
                    Synset[] synsets = iw.getSenses();
                    for (int i = 0; i < synsets.length; i++){
                        for (Word w : synsets[i].getWords()){
                            if(!newWords.contains(w.getLemma().toLowerCase())){
                                double weight = pow(0.5, i+1);
                                if(weight >= 0.0625)
                                expandedTerms.add(new Term(w.getLemma().toLowerCase(), weight));
                            }
                        }
                    }
                }
            }


        } catch (JWNLException e) {
            e.printStackTrace();
        }
        return expandedTerms;
    }
}
