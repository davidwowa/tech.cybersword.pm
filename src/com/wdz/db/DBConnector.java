package com.wdz.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432", "emailuser", "wowawowa");
		connection.close();
	}
}