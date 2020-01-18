package com.java.swing;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnect {
	public Connection con = null;
	public Connection dbConnect() {
		try {
			System.out.println("Connecting...");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
			System.out.println("Connected!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void main(String[] args) {
		DBConnect db = new DBConnect();
		db.dbConnect();

	}

}
