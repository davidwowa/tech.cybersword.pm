package com.wdz.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;

import com.wdz.email.Email;

public class Receiver {

	private Logger logger = Logger.getLogger(Receiver.class);

	public List<Email> receiveMails(Properties properties) {
		try {
			logger.info("get emails from " + properties.getProperty("host"));
			Session currentSession = Session.getDefaultInstance(properties);
			Store currentStore = currentSession.getStore(properties.getProperty("store"));
			logger.info("connetct to " + properties.getProperty("host"));
			currentStore.connect(properties.getProperty("host"), properties.getProperty("email"),
					properties.getProperty("password"));
			logger.info("is connected = " + currentStore.isConnected());

			Folder currentFolder = currentStore.getFolder(properties.getProperty("folder"));
			currentFolder.open(Folder.READ_ONLY);
			Message[] emails = currentFolder.getMessages();

			logger.info("emails in inbox " + emails.length);

			try {
				List<Email> emailList = new ArrayList<Email>();
				int i = 0;
				for (Message curretnEmail : emails) {

					Email currentEmaiL = new Email();

					currentEmaiL.setUuid(UUID.randomUUID().toString());

					currentEmaiL.setAllHeaders(curretnEmail.getAllHeaders());
					currentEmaiL.setContent(curretnEmail.getContent());
					currentEmaiL.setRecipients(curretnEmail.getAllRecipients());
					currentEmaiL.setContentyType(curretnEmail.getContentType());
					currentEmaiL.setDescription(curretnEmail.getDescription());
					currentEmaiL.setFlags(curretnEmail.getFlags());
					currentEmaiL.setFrom(curretnEmail.getFrom());
					currentEmaiL.setReceivedDate(curretnEmail.getReceivedDate());

					emailList.add(currentEmaiL);
					logger.info("\tIndividual email");
					logger.info("\tNo# " + (i + 1));
					logger.info("\tEmail Subject: " + curretnEmail.getSubject());
					if (curretnEmail.getFrom() != null) {
						logger.info("\tSender: " + curretnEmail.getFrom()[0]);
					}
					i++;
				}
				return emailList;
			} catch (Throwable throwable) {
				logger.error("error on displaying emails", throwable);
				throwable.printStackTrace();
			}

			currentFolder.close(false);
			currentStore.close();
		} catch (Throwable throwable) {
			logger.error("error on server connection", throwable);
			throwable.printStackTrace();
		}
		return null;
	}
}