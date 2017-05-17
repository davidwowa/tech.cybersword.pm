package com.wdz.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wdz.io.conf.reader.PropertiesReader;

public class DBConnector {

	private static Logger logger = Logger.getLogger(DBConnector.class);

	private Properties properties;
	private String conf_properties = "resources/database.properties";

	public static DBConnector instance;

	public static DBConnector getInstance() {
		if (instance == null) {
			instance = new DBConnector();
		}
		return instance;
	}

	private DBConnector() {
		loadProperties();
		init();
	}

	private void loadProperties() {
		PropertiesReader propertiesReader = new PropertiesReader();
		logger.info("read database properties");
		properties = propertiesReader.getProperties(conf_properties);
	}

	private void init() {
		try {
			logger.info("load jdbc driver");
			Class.forName(properties.getProperty("driver"));
			logger.info("get connection");
			Connection connection = DriverManager.getConnection(properties.getProperty("host"),
					properties.getProperty("user"), properties.getProperty("password"));

			logger.info("create statement");
			Statement statement = connection.createStatement();
			logger.info("run version query / only postgres db");
			ResultSet resultSet = statement.executeQuery("SELECT VERSION()");

			logger.info("host -> \t\t\t " + properties.getProperty("host"));
			logger.info("port -> \t\t\t " + properties.getProperty("port"));
			logger.info("driver -> \t\t " + properties.getProperty("driver"));
			if (resultSet.next()) {
				logger.info("postgresql version -> \t " + resultSet.getString(1));
			} else {
				logger.error("something is wrong");
				throw new Exception("check database connection parameters, or you use other database as postgresql");
			}
			resultSet.close();
			statement.close();

			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM PG_TABLES WHERE SCHEMANAME = 'INFORMATION_SCHEMA'");

			int count = 1;
			while (resultSet.next()) {
				logger.info("schema information -> \t " + resultSet.getString(count++));
			}

			resultSet.close();
			statement.close();

			logger.info("close connection");
			connection.close();
			logger.info("connection closed");
		} catch (Throwable throwable) {
			logger.error("error on database configuration", throwable);
			throwable.printStackTrace();
		}
	}
}