package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class hospital {

	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospital", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertHospitals(String name, String address, String contactno, String capacity, String units) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "insert into hospitals(`hosId`,`hosName`,`hosAddress`,`hosContactNo`,`hosCapacity`,`hosUnits`)"
					+ "values ( ?,  ?,  ?,  ?,  ?,  ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, contactno);
			preparedStmt.setDouble(5, Double.parseDouble(capacity));
			preparedStmt.setString(6, units);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
			
		} catch (Exception e) {
			
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the hospital.\"}";
			System.err.println(e.getMessage());
		}
		return output;

	}
	
	public String readHospitals() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Hospital Name</th><th>Hospital Address</th><th>Hospital ContactNo</th><th>Hospital Capacity</th><th>Hospital Units</th><th>Update</th><th>Remove</th></tr>"; 
			
			String query = "select * from hospitals";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String hosId = Integer.toString(rs.getInt("hosId"));
				String hosName = rs.getString("hosName");
				String hosAddress = rs.getString("hosAddress");
				String hosContactNo = rs.getString("hosContactNo");
				String hosCapacity = Double.toString(rs.getDouble("hosCapacity"));
				String hosUnits = rs.getString("hosUnits");
				
				// Add into the html table
				
				
				
				output += "<tr><td><input id='hidHospitalIDUpdate' name='hidHospitalIDUpdate' type='hidden' value='"+ hosId + "'>" + hosName + "</td>";
				output += "<td>" + hosAddress + "</td>";
				output += "<td>" + hosContactNo + "</td>";
				output += "<td>" + hosCapacity + "</td>";
				output += "<td>" + hosUnits + "</td>";
				
				// buttons
				
				   output += "<td><input name='btnUpdate' type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
	                        + "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger' data-hosId='"
	                        + hosId + "'>" + "</td></tr>";
				
				
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
		} 
		catch (Exception e) {
			output = "Error while reading the hospitals.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateHospitals(String hosId, String name, String address, String contactno, String capacity, String units) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE hospitals SET hosName=?,hosAddress=?,hosContactNo=?,hosCapacity=?,hosUnits=? WHERE hosId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, contactno);
			preparedStmt.setDouble(4, Double.parseDouble(capacity));
			preparedStmt.setString(5, units);
			preparedStmt.setInt(6, Integer.parseInt(hosId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the hospital.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteHospitals(String hosId) {
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from hospitals where hosId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(hosId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the hospital.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}

}
