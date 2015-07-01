package com.simple.pinyin;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

/**
 * Created by qindongliang on 2015/6/30.
 */
public class StopCnFilterFactory extends TokenFilterFactory {



    public StopCnFilterFactory(Map<String, String> args) {
        super(args);

    }

    @Override
    public TokenStream create(TokenStream tokenStream) {

        return new StopCnFilter(tokenStream);
    }
}
