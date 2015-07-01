# lucene-simple-pinyin
支持，Lucene，Solr5.x拼音分词插件

这个分词插件，主要是为了解决有些中文的名称，需要一个对应的拼音名称而开发的，对拼音支持简拼，全拼，还可以过滤中文，对多音字可以选择
只取一个，或者全部都保存。

应用场景：

1： 按拼音排序
2： 中文网站一般都有suggest功能，是支持拼音的suggest的



用到的技术：

	1,开源的Pinyin4j
	2,开源的lucene
	3,开源的solr

如何在Lucene里使用？


		 public static void testExec()throws Exception{
	   	//PYAnalyzer支持参数，代表5中拼音模式，
	    	//jp所有简拼，如果有多音字的话
	   	//qp所有全拼，如果有多音字的话
	   	//jpone简拼中第一个，不管是否有多音字
	  	//qpone全拼中第一个，不管是否有多音字
	  	//all所有简拼+全拼去重后的集合
	   	PYAnalyzer p=new PYAnalyzer("jp");
	  	TokenStream ts = p.tokenStream("name","中国银行");
	 	CharTermAttribute term=ts.addAttribute(CharTermAttribute.class);
        	ts.reset();
	 	while(ts.incrementToken()){
	     	System.out.println(term.toString());
	 	}
	 	ts.end();
	   	ts.close();
		  }



如何在solr里面使用？

very easy，只需在Solr的schaml.xml里面配置 ，如下分词器即可！


	<!-- 配置一个拼音分词器 -->
	  <fieldType name="pinyin" class="solr.TextField" positionIncrementGap="100">
      <analyzer>
        <tokenizer class="solr.KeywordTokenizerFactory"/>
	    <!-- 配置拼音的filter -->
		<filter class="com.simple.pinyin.PinYinFilterFactory" type="all" />
		<!-- 过滤原始汉字输入，只保留拼音输出 -->
		<filter class="com.simple.pinyin.StopCnFilterFactory"  />
      </analyzer>
    </fieldType>
	



























