package org.stu_solrj;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 * Hello world!
 *
 */
public class App 
{
	
	static String urlString = "http://172.31.131.205:8983/solr/myfirstcore";
    public static void main( String[] args ) throws SolrServerException, IOException
    {
    	
//    	insertDoc();
    	query();
    	
    }

    
 // 删除索引
 	// 据查询结果删除：
 	public void DeleteByQuery() {
 		SolrClient solr = new HttpSolrClient(urlString);
 		try {
 			// 删除所有的索引
 			solr.deleteByQuery("jobsName:高级技术支持");
 			//solr.deleteById("515792");
 			solr.commit();
 			solr.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 	}
    public static void insertDoc() throws SolrServerException, IOException{
    	SolrClient solr = new HttpSolrClient(urlString);
    	
    	SolrInputDocument inDoc = new SolrInputDocument();
    	inDoc.addField("name22", "这个没有光泽");
    	
    	solr.add(inDoc);
    	
    	solr.commit();
    	
    	solr.close();
    }
    
    public static void query() throws SolrServerException, IOException{    	SolrClient solr = new HttpSolrClient(urlString);

    	SolrQuery query = new SolrQuery();
    	query.set("q", "Samsung");
//    	query.setQuery("features:GB18030");
    	query.setParam("hl", "true");
    	query.setParam("hl.fl", "name");  
    	
    	query.setHighlight(true); // 开启高亮组件
		query.addHighlightField("name");// 高亮字段
		query.setHighlightSimplePre("<font color=\"red\">");// 标记
		query.setHighlightSimplePost("</font>");
		query.setHighlightSnippets(1);// 结果分片数，默认为1
		query.setHighlightFragsize(100);// 每个分片的最大长度，默认为100
		query.setHighlightRequireFieldMatch(true);

    	query.setFacet(true);
    	
    	query.setStart(0);
    	query.setRows(19);
    	
    	QueryResponse response = solr.query(query);
    	
    	Map<String, Map<String, List<String>>> map = response.getHighlighting();  
    	
    	SolrDocumentList rList = response.getResults();
    	for(SolrDocument d:rList){
    		System.out.println("================================================");
    		for(String fName:d.getFieldNames()){
    			System.out.println(fName+":"+d.getFieldValue(fName));
    			
    		}
    	}
    	
    	
    	
    	   
    	   
    	   
    	
    	   
    	
    	
    	solr.close();
    }
}
