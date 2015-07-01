package simple.pinyin.test;

import com.pinyin.utils.PinyinTools;
import com.simple.pinyin.PYAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
* Created by qindongliang on 2015/6/30.
*/
public class PinyinToolsTest {

    public static void main1(String[] args) {

        String kw="银行";
        System.out.println(PinyinTools.jp(kw));
        System.out.println(PinyinTools.jpone(kw));
        System.out.println(PinyinTools.qp(kw));
        System.out.println(PinyinTools.all(kw));
    }


    public void testExec()throws Exception{
        PYAnalyzer p=new PYAnalyzer("jp");

          TokenStream ts = p.tokenStream("aa","中国银行");

         CharTermAttribute term=ts.addAttribute(CharTermAttribute.class);

        ts.reset();
        while(ts.incrementToken()){
            System.out.println(term.toString());
        }
        ts.end();
        ts.close();
    }



}
