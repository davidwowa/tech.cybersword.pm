package com.wdz.rm;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;

public class ReceiveEmail {

	private static Logger logger = Logger.getLogger(ReceiveEmail.class);

	public static void main(String[] args) {
		logger.info("Start email receive service");
		String hostval = "pop3.web.de";
		String mailStrProt = "995";
		String uname = "email";
		String pwd = "password";
		checkMail(hostval, mailStrProt, uname, pwd);
	}

	public static void checkMail(String hostval, String mailStrProt, String uname, String pwd) {
		try {
			// Set property values
			Properties propvals = new Properties();
			propvals.put("mail.pop3.host", hostval);
			propvals.put("mail.pop3.port", "995");
			propvals.put("mail.pop3.starttls.enable", "true");
			logger.info("build session obj");
			Session emailSessionObj = Session.getDefaultInstance(propvals);
			// Create POP3 store object and connect with the server
			Store storeObj = emailSessionObj.getStore("pop3s");
			logger.info("connect with " + emailSessionObj.getProperty("mail.pop3.host"));
			storeObj.connect(hostval, uname, pwd);
			logger.info(storeObj.isConnected());

			// Create folder object and open it in read-only mode
			Folder emailFolderObj = storeObj.getFolder("INBOX");
			emailFolderObj.open(Folder.READ_ONLY);
			// Fetch messages from the folder and print in a loop
			Message[] messageobjs = emailFolderObj.getMessages();

			logger.info("email in inbox " + messageobjs.length);

			for (int i = 0, n = messageobjs.length; i < n; i++) {
				Message indvidualmsg = messageobjs[i];
				System.out.println("Printing individual messages");
				System.out.println("No# " + (i + 1));
				System.out.println("Email Subject: " + indvidualmsg.getSubject());
				System.out.println("Sender: " + indvidualmsg.getFrom()[0]);
				System.out.println("Content: " + indvidualmsg.getContent().toString());
			}
			// Now close all the objects
			emailFolderObj.close(false);
			storeObj.close();
		} catch (Throwable exp) {
			logger.error("error on server connection", exp);
		}
	}

}
