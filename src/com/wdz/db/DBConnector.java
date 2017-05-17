package com.wdz.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wdz.db.entity.MailEntity;
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

			Boolean create_table_email = new Boolean(properties.getProperty("create_email_table"));
			if (create_table_email) {

				logger.info("drop table emails");
				statement = connection.createStatement();
				statement.execute("DROP TABLE EMAILS");
				statement.close();

				logger.info("create table emails");
				statement = connection.createStatement();
				StringBuilder create_table_emails = new StringBuilder(
						"CREATE TABLE IF NOT EXISTS EMAILS(ID SERIAL NOT NULL PRIMARY KEY,");
				create_table_emails.append(" UUID VARCHAR(50),");
				create_table_emails.append(" ALLHEADERS VARCHAR(7000),");
				create_table_emails.append(" RECIPIENTS VARCHAR(7000),");
				create_table_emails.append(" EMAIL_CONTENT VARCHAR(10000),");
				create_table_emails.append(" EMAIL_CONTENTTYPE VARCHAR(5000),");
				create_table_emails.append(" EMAIL_DESCRIPTION VARCHAR(5000),");
				create_table_emails.append(" EMAIL_FROM VARCHAR(5000),");
				create_table_emails.append(" RECEIVEDDATE VARCHAR(500),");
				create_table_emails.append(" SOURCEMAIL VARCHAR(500),");
				create_table_emails.append(" TARGETMEAIL VARCHAR(500),");
				create_table_emails.append(" RESPONSE VARCHAR(500))");

				// statement.executeQuery(create_table_emails.toString());
				statement.execute(create_table_emails.toString());
				// statement.executeUpdate(create_table_emails.toString());
				statement.close();
				logger.info("table emails created");
			} else {
				logger.info("table email exists, really ???");
			}

			logger.info("close connection");
			connection.close();
			logger.info("connection closed");
		} catch (Throwable throwable) {
			logger.error("error on database configuration", throwable);
			throwable.printStackTrace();
		}
	}

	public void insertEmail(List<MailEntity> mailsToInsert) {
		if (mailsToInsert == null || mailsToInsert.isEmpty()) {
			logger.warn("nothing to save !!!");
			return;
		}
		try {
			logger.info("save emails");
			Connection connection = DriverManager.getConnection(properties.getProperty("host"),
					properties.getProperty("user"), properties.getProperty("password"));

			for (MailEntity currentEmail : mailsToInsert) {
				logger.info("save email with uuid " + currentEmail.getUuid());
				Statement statement = connection.createStatement();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(
						"INSERT INTO EMAILS (UUID, ALLHEADERS, RECIPIENTS, EMAIL_CONTENT, EMAIL_CONTENTTYPE, EMAIL_DESCRIPTION, EMAIL_FROM, RECEIVEDDATE, SOURCEMAIL, TARGETMEAIL, RESPONSE) VALUES ('");

				stringBuilder.append(currentEmail.getUuid());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getAllHeaders());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getRecipients());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getContent());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getContentyType());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getDescription());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getFrom());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getReceivedDate());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getSourceEmail());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getTargetEmail());
				stringBuilder.append("', '");

				stringBuilder.append(currentEmail.getResponse());

				stringBuilder.append("')");

				statement.execute(stringBuilder.toString());
				statement.close();
			}

			logger.info("emails are in database");
			connection.close();
		} catch (Throwable throwable) {
			logger.error("error on email db insert", throwable);
			throwable.printStackTrace();
		}
	}
}