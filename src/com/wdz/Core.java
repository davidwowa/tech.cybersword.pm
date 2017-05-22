package com.wdz;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wdz.db.DBConnector;
import com.wdz.db.converter.EmailConverter;
import com.wdz.db.entity.MailEntity;
import com.wdz.email.Email;
import com.wdz.email.generator.EmailGenerator;
import com.wdz.io.conf.reader.PropertiesReader;
import com.wdz.receiver.Receiver;
import com.wdz.sender.Sender;

public class Core {

	private static Logger logger = Logger.getLogger(Core.class);

	public static void main(String[] args) {

		String config_in_email = "resources/config_in_email.properties";
		String config_out_email = "resources/config_out_email.properties";

		PropertiesReader propertiesReader = new PropertiesReader();

		logger.info("read email properties");
		Properties targetProperties = propertiesReader.getProperties(config_in_email);
		Properties sourceProperties = propertiesReader.getProperties(config_out_email);

		logger.info("create sender and receiver objects");
		Receiver emailReceiver = new Receiver();
		Sender emailSender = new Sender();
		EmailGenerator generator = new EmailGenerator();

		// ---------------------------------------------- SEND
		logger.info("create email/s");
		List<Email> mailsToSend = generator.generate("source@email.em", targetProperties.getProperty("email"), 2);

		logger.info("send email/s");
		for (Email email : mailsToSend) {
			emailSender.send(email, sourceProperties);
		}

		DBConnector connector = DBConnector.getInstance();
		EmailConverter converter = EmailConverter.getInstance();

		List<MailEntity> emailsForDB = new ArrayList<>();
		for (Email currentEmail : mailsToSend) {
			emailsForDB.add(converter.createFrom(currentEmail));
		}

		connector.insertEmail(emailsForDB);

		// ---------------------------------------------- RECIEVE
		logger.info("wait");

		logger.info("receive emails");
		List<Email> emails = emailReceiver.receiveMails(targetProperties);

		emailsForDB = new ArrayList<>();
		for (Email currentEmail : emails) {
			emailsForDB.add(converter.createFrom(currentEmail));
		}

		logger.info("save loaded emails in db");
		connector.insertEmail(emailsForDB);
	}
}