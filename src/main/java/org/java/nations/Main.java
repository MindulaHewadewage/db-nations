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
		
		try (Scanner sc = new Scanner(System.in);
				Connection con = DriverManager.getConnection(url, user, password)){
			
			System.out.print("Ricerca passeggeri per cognome: ");
			String searchLastname = sc.nextLine();
		    
			String sql = "SELECT countries.name,countries.country_id,regions.name,continents.name"
					+"FROM countries"
					+"JOIN regions"
					+"ON countries.region_id =regions.region_id" 
					+"JOIN continents"
					+"on regions.continent_id =continents.continent_id"
					+"ORDER BY countries.name"; 

			

			
			try (PreparedStatement  ps = con.prepareStatement(sql)) {
				
				ps.setString(1, searchLastname);
				
				try (ResultSet  rs = ps.executeQuery()) {
					
					while(rs.next()) {
						
						final int id = rs.getInt(1);
						final String name = rs.getString(2);
						final String lastname = rs.getString(3);
						final Date dateOfBirth = rs.getDate(4);
						
						System.out.println(id + " - " + name + " - " 
								+ lastname + " - " + dateOfBirth);
					}
				}				
			} catch (SQLException ex) {
				System.err.println("Query not well formed");
			}
		} catch (SQLException ex) {
			System.err.println("Error during connection to db");
		}
	}
	
	public static LocalDateTime  getLocalDateTime(Timestamp time) {
		
		return LocalDateTime
				.ofInstant(Instant.ofEpochMilli(time.getTime()), 
						TimeZone .getDefault().toZoneId());  
	}


}
