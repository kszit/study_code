package org.stu_dbneo4j;

import java.util.Iterator;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.cypher.internal.compiler.v2_2.parser.CypherParser;
import org.neo4j.cypher.internal.compiler.v2_2.parser.Query;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       
    	final GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("h://neo4jTest");
    
    	Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				graphDb.shutdown();
			}
    		
    	});
    	
    	
    	
    	App app = new App(graphDb);
    	
//    	app.insesrtData();
    	app.queryData();
    	
    	
    }
    
    String indexCol = "name";
    
    GraphDatabaseService graphDb = null;
    Index<Node> nodeIndex = null;
    public App(GraphDatabaseService graphDb){
    	this.graphDb = graphDb;
    	
    }
    
    /**
     * 通过cyper查询数据
     */
    public void queryDataByCyper(){
    	
    	
    	ExecutionEngine engine = new ExecutionEngine(this.graphDb);  
    	Query query = parser.parse( "start n=(0) where 1=1 return n" );  
    	ExecutionResult result = engine.execute( query );  
    	assertThat( result.columns(), hasItem( "n" ) );  
    	Iterator<Node> n_column = result.columnAs( "n" );  
    	assertThat( asIterable( n_column ), hasItem(db.getNodeById(0)) );  
    	assertThat( result.toString(), containsString("Node[0]") );  
//    	
//    	TraversalDescription td = this.graphDb.traversalDescription();
//    	td.breadthFirst().relationships(RelType.KNOWS, Direction.OUTGOING).filter();
    	
    }
    
    
    /**
     * 通过索引查询数据
     */
    public void queryDataByIndex(){
    	
    	
    	Transaction tx = graphDb.beginTx();
    	try{
    		
    		nodeIndex = this.graphDb.index().forNodes("nodes");

        	Node qNode = nodeIndex.get(indexCol, "msg5").getSingle();
        	
        	System.out.println(qNode.getProperty("name"));
        	System.out.println(qNode.getProperty("time"));
        	System.out.println(System.currentTimeMillis());
    		tx.success();
    	}finally{
    		tx.finish();
    	}
    }
    
    /**
     * 添加用户
     */
    public void insertData(){
    	Transaction tx = graphDb.beginTx();
    	try{
    		
    		nodeIndex = this.graphDb.index().forNodes("nodes");
    		
    		
    		Node firstNode = graphDb.createNode();
    		firstNode.setProperty("msg","msg5");
    		firstNode.setProperty("name","lisi");
    		firstNode.setProperty("time",System.currentTimeMillis());
    		
    		nodeIndex.add(firstNode, indexCol, "msg5");
    		
    		Node secondNode = graphDb.createNode();
    		secondNode.setProperty("msg", "msg6");
    		nodeIndex.add(secondNode, indexCol, "msg6");
    		
    		
    		Relationship relationShip = firstNode.createRelationshipTo(secondNode, RelType.KNOWS);
    		relationShip.setProperty("msg", "msg7");
    		
    		tx.success();
    	}finally{
    		tx.finish();
    	}
    	
    }
    
    private static enum RelType implements RelationshipType{
    	KNOWS
    }
    
    
    
}
