package br.com.projetoIndividual.db.connection;

import java.sql.Connection;

public class Connec {
	
	private Connection connec;
	
	public Connection openConnection(){
	    try{
	    	Class.forName("org.gjt.mm.mysql.Driver");
	    	connec = java.sql.DriverManager
	    			.getConnection(
	    			"jdbc:mysql://localhost:3306/projeto?autoReconnect=true&useSSL=false",
	    			"root", "1234");
	    	}
	    	catch (Exception e){
	    		e.printStackTrace();
	    	}
	    	return connec;
	    }
		
		public void closeConnection(){
			try{
				connec.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

