package com.wdz;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

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

		logger.info("read properties");
		Properties targetProperties = propertiesReader.getProperties(config_in_email);
		Properties sourceProperties = propertiesReader.getProperties(config_out_email);

		logger.info("create sender and receiver objects");
		Receiver emailReceiver = new Receiver();
		Sender emailSender = new Sender();
		EmailGenerator generator = new EmailGenerator();

		logger.info("create email/s");
		List<Email> mailsToSend = generator.generate("bankX@bankX.de", "malwrans@web.de", 7);

		logger.info("send email/s");
		for (Email email : mailsToSend) {
			emailSender.send(email, sourceProperties);
		}

		logger.info("save sended email in db");
		// todo

		logger.info("wait");

		logger.info("load emails");
		List<Email> emails = emailReceiver.receiveMails(targetProperties);

		logger.info("save loaded emails in db");
		// todo -> db
	}
}