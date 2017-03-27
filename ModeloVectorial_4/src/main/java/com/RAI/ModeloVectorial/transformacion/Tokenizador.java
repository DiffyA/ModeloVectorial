package com.RAI.ModeloVectorial.transformacion;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.StringReader;

public class Tokenizador {

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

    public static String stemTerm (String term) {
        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(term);
        stemmer.stem();
        return stemmer.getCurrent();
    }

}
