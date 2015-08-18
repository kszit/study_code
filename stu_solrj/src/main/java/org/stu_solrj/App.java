package org.stu_solrj;

import java.io.IOException;

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

    public static void insertDoc() throws SolrServerException, IOException{
    	SolrClient solr = new HttpSolrClient(urlString);
    	
    	SolrInputDocument inDoc = new SolrInputDocument();
    	inDoc.addField("name22", "这个没有光泽");
    	
    	solr.add(inDoc);
    	
    	solr.commit();
    	
    }
    
    public static void query() throws SolrServerException, IOException{
    	SolrClient solr = new HttpSolrClient(urlString);

    	SolrQuery query = new SolrQuery();
    	query.set("q", "光泽");
    	QueryResponse response = solr.query(query);
    	
    	SolrDocumentList rList = response.getResults();
    	for(SolrDocument d:rList){
    		for(String fName:d.getFieldNames()){
    			System.out.println(fName+":"+d.getFieldValue(fName));
    			
    		}
    	}
    }
}
