package com.student.DAOs;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import static org.neo4j.driver.v1.Values.parameters;


public class Neo4jDAO {

	//Constructor
	public Neo4jDAO() {
		super();
	}
	
	//Create node to add to Neo4j database
	public void createNode(String name, String address) {
		//connect to Neo4j database
	    Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4jdb"));
	    Session session = driver.session();
	    
	    session.writeTransaction(new TransactionWork<String>() {
	        @Override
	        public String execute(Transaction tx) {
	        	//run Neo4j query to add Student to database
	            tx.run("CREATE (:STUDENT{name: {name},address: {address}})",
	                parameters("name", name , "address",address));
	        return "manage_students.xhtml";
	        }
	    });
	}
	
	//Delete node from Neo4j Database
	public void deleteNode(String name) {
		//connect to neo4j database
	    Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4jdb"));
	    Session session = driver.session();
	    
	    session.writeTransaction(new TransactionWork<String>() {
	        @Override
	        public String execute(Transaction tx) {
	        	//match student via name parameter and delete that node
	            tx.run("MATCH (n:STUDENT {name: {name}}) DELETE n",
	                parameters("name", name));
	        return "manage_students.xhtml";
	        }
	    });
	}
	
}
