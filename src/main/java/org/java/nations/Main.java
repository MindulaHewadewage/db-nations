package org.java.nations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {

public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/db-aereoporto";
		String user = "root";
		String password = "code";
		
		try (Connection con = DriverManager.getConnection(url, user, password)){
			
		    
			String sql = "SELECT countries.name,countries.country_id,regions.name,continents.name"
					+"FROM countries"
					+"JOIN regions"
					+"ON countries.region_id =regions.region_id" 
					+"JOIN continents"
					+"on regions.continent_id =continents.continent_id"
					+"ORDER BY countries.name"; 

			

			
			try (PreparedStatement  ps = con.prepareStatement(sql)) {
				
				try (ResultSet  rs = ps.executeQuery()) {
					
					while(rs.next()) {
						
						final String name = rs.getString(1);
						final int id = rs.getInt(2);
						final String region = rs.getString(3);
						final String continent= rs.getString(4);
						
						System.out.println(name + "-"+id+"-"+region+"-"+continent);
					}
						
				} catch (SQLException ex) {
					System.err.println("ERROR");
				}

			} catch (SQLException ex) {
				System.err.println("Query Error");
			}

		}catch (SQLException ex){
			System.err.println("Connection error");
		}
}
}
	  



