package com.wdz.receiver;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;

public class Receiver {

	private static Logger logger = Logger.getLogger(Receiver.class);

	public List<Message> receiveMails(Properties properties) {
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

			logger.info("email in inbox " + emails.length);

			currentFolder.close(false);
			currentStore.close();

			return Arrays.asList(emails);
		} catch (Throwable throwable) {
			logger.error("error on server connection", throwable);
			throwable.printStackTrace();
		}
		return null;
	}
}