package com.RAI.ModeloVectorial.transformation;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;

import com.RAI.ModeloVectorial.core.Term;

import java.io.StringReader;

/**
 * Class in charge of transforming, filtering, and tokenizing Strings
 * into a normalized and stemmed version of themselves.
 * 
 * @author vdegou
 *
 */
public class Tokenizer {

	/**
	 * Removes the stop words from a String.
	 * 
	 * @param textFile
	 * @return
	 * @throws Exception
	 */
    public static String removeStopWords(String textFile) throws Exception {
        StandardTokenizer stdToken = new StandardTokenizer();
        stdToken.setReader(new StringReader(textFile));
        TokenStream tokenStream;

        tokenStream = new StopFilter(new ASCIIFoldingFilter(new ClassicFilter(new LowerCaseFilter(stdToken))), EnglishAnalyzer.getDefaultStopSet());
        tokenStream.reset();

        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
        StringBuilder sb = new StringBuilder();
        while (tokenStream.incrementToken()) {
            sb.append(token.toString()).append(" ");
        }
        tokenStream.close();
        return sb.toString();
    }

    /**
     * Stems a term and normalizes it.
     * @param term
     * @return
     */
    public static String stemTerm (Term term) {
        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(term.getTerm());
        stemmer.stem();
        return stemmer.getCurrent();
    }

}
