package com.aklc.ed.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {


	public static Connection connect() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite://C:/Practice/database/ed.db");
		return con;
	}

	
}
