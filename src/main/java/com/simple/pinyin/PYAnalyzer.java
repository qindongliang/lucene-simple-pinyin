package com.simple.pinyin;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Created by qindongliang on 2015/6/30.
 */
public class PYAnalyzer extends Analyzer {

    private String type;

    public PYAnalyzer(String type) {
        this.type = type;
    }

    @Override
    protected TokenStreamComponents createComponents(String s){
        Tokenizer tokenizer=new KeywordTokenizer();
        TokenStream stream=new PinYinFilter(tokenizer,type);
        stream=new StopCnFilter(stream);
        return new TokenStreamComponents(tokenizer,stream);
    }
}
