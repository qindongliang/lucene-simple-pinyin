package com.simple.pinyin;

import com.pinyin.utils.PinyinTools;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by qindongliang on 2015/6/30.
 */
public class PinYinFilter extends TokenFilter {

    // 存储分词数据
    private final CharTermAttribute cta ;
    // 存储语汇单元的位置信息
    private final PositionIncrementAttribute pia ;
    // 添加是否有同义词的判断变量属性,保存当前元素的状态信息
    private  AttributeSource.State current;
    // 栈存储
    private Stack<String> sames = null;
    //拼音分词类型
    //jp（简拼）qp(全拼) jpone(简拼一个),all(简拼+全拼)
    private String type;

    protected PinYinFilter( TokenStream input, String type) {
        super(input);
        this.type=type;
        cta = this.addAttribute(CharTermAttribute.class);
        pia = this.addAttribute(PositionIncrementAttribute.class);
        sames = new Stack<String>();
    }

    @Override
    public final boolean incrementToken() throws IOException {
        // 保存上一个语汇的同义词
        while (sames.size() > 0) {
            // 出栈,并获取同义词
            String str = sames.pop();
            // 还原上一个语汇的状态
            restoreState(current);
            // 在上一个语汇上保存元素
            cta.setEmpty();
            cta.append(str);
            // 设置同义词位置为0
            pia.setPositionIncrement(0);
            return true;
        }

        // 跳到下个cta
        if (!this.input.incrementToken())
            // 没有元素返回false
            return false;


        if (getSameWordsPinYin(cta.toString())) {
            // 如果有同义词,改变词汇的current状态信息,把当前状态保存(捕获当前状态)
            current = captureState();
        }


        return true;
    }



    /*
     *
     * 获取同义词
     */
    private Boolean getSameWordsPinYin(String name) {

        List<String> sws=null;
        if(type==null) {
            return  false;
        }
        if(type.equals("jp")){
            sws=PinyinTools.jp(name);//所有简拼
        }else if(type.equals("qp")){
            sws=PinyinTools.qp(name);//所有全拼
        }else if (type.equals("jpone")){
            sws=PinyinTools.jpone(name);//获取一个简拼
        }else if(type.equals("qpone")){//获取一个全拼
            sws=PinyinTools.qpone(name);
        } else {
            sws=PinyinTools.all(name);//获取所有拼音
        }

        if (sws != null) {
            // 添加进栈中
            for (String str : sws) {
                sames.push(str);
            }
            return true;
        }
        return false;
    }


}
