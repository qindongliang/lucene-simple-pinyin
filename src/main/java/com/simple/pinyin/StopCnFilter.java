package com.simple.pinyin;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by qindongliang on 2015/6/30.
 */
public class StopCnFilter extends FilteringTokenFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    public StopCnFilter(TokenStream in) {
        super(in);
    }

    @Override
    protected boolean accept() throws IOException {
        return !Pattern.matches("[\\u4e00-\\u9fa5]*",this.termAtt);
    }
}
