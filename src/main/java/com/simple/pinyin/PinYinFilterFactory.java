package com.simple.pinyin;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

/**
 * Created by qindongliang on 2015/6/30.
 */
public class PinYinFilterFactory extends TokenFilterFactory {
    //分词拼音类型
    private String type;

    public PinYinFilterFactory(Map<String, String> args) {
        super(args);
        this.type = get(args, "type");
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new PinYinFilter(tokenStream,type);
    }
}
