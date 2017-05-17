package com.wdz.sender;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.sun.mail.smtp.SMTPTransport;
import com.wdz.email.Email;

public class Sender {

	private Logger logger = Logger.getLogger(Sender.class);

	public void send(Email email, Properties properties) {
		try {
			Session session = Session.getInstance(properties, null);
			logger.info("build new email for sending " + email.getUuid());
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email.getSourceEmail()));

			// !!!
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTargetEmail(), false));
			// !!!

			message.setSubject("Email " + email.getUuid());
			message.setText("----////\\\\----\n" + email.getUuid());
			logger.info("change headers");
			// !!!
			message.setHeader("Received", "haha");
			message.setHeader("Return-Path", "haha");
			// !!!
			message.setSentDate(new Date());
			SMTPTransport transport = (SMTPTransport) session.getTransport(properties.getProperty("store"));
			logger.info("connect to sender host");
			transport.connect(properties.getProperty("host"), properties.getProperty("email"),
					properties.getProperty("password"));
			logger.info("send email " + email.getUuid());
			transport.sendMessage(message, message.getAllRecipients());
			logger.info("Response: " + transport.getLastServerResponse());
			email.setResponse(transport.getLastServerResponse());
			transport.close();
		} catch (Throwable throwable) {
			logger.error("error on email send", throwable);
			throwable.printStackTrace();
		}
	}
}