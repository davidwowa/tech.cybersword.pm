package com.wdz;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;

import org.apache.log4j.Logger;

import com.wdz.io.conf.reader.PropertiesReader;
import com.wdz.receiver.Receiver;

public class Core {

	private static Logger logger = Logger.getLogger(Core.class);

	public static void main(String[] args) {

		String config_in_email = "resources/config_in_email.properties";
		String config_out_email = "resources/config_out_email.properties";

		PropertiesReader propertiesReader = new PropertiesReader();
		Properties properties = propertiesReader.getProperties(config_in_email);

		Receiver emailReceiver = new Receiver();

		List<Message> emails = emailReceiver.receiveMails(properties);
		try {
			int i = 0;
			for (Message curretnEmail : emails) {
				logger.info("\tPrinting individual messages");
				logger.info("\tNo# " + (i + 1));
				logger.info("\tEmail Subject: " + curretnEmail.getSubject());
				logger.info("\tSender: " + curretnEmail.getFrom()[0]);
				logger.info("\tContent: " + curretnEmail.getContent().toString());
				i++;
			}
		} catch (Throwable throwable) {
			logger.error("error on displaying emails", throwable);
			throwable.printStackTrace();
		}
	}
}