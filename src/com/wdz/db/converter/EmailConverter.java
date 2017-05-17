package com.wdz.db.converter;

import java.util.Enumeration;

import javax.mail.Address;
import javax.mail.Header;

import org.apache.log4j.Logger;

import com.wdz.db.entity.MailEntity;
import com.wdz.email.Email;

public class EmailConverter {

	private Logger logger = Logger.getLogger(EmailConverter.class);

	private static EmailConverter instance;

	public static EmailConverter getInstance() {
		if (instance == null) {
			instance = new EmailConverter();
		}
		return instance;
	}

	private EmailConverter() {
		logger.info("create instance of email converter");
	}

	public MailEntity createFrom(Email email) {
		MailEntity mailEntity = new MailEntity();

		mailEntity.setUuid(email.getUuid());
		mailEntity.setTargetEmail(email.getTargetEmail());
		mailEntity.setSourceEmail(email.getSourceEmail());
		mailEntity.setResponse(email.getResponse());
		if (email.getReceivedDate() != null) {
			mailEntity.setReceivedDate(email.getReceivedDate().toString());
		} else {
			logger.warn("receive date is null");
		}
		mailEntity.setDescription(email.getDescription());
		mailEntity.setContent(email.getContent().toString());
		mailEntity.setContentyType(email.getContentyType());

		mailEntity.setFrom(getFrom(email.getFrom()));
		mailEntity.setAllHeaders(getHeaders(email.getAllHeaders()));

		return mailEntity;
	}

	private String getHeaders(Enumeration<?> enumeration) {
		if (enumeration == null) {
			logger.error("email headers are null");
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		while (enumeration.hasMoreElements()) {
			Header currentHeader = (Header) enumeration.nextElement();
			stringBuilder.append(currentHeader.getName());
			stringBuilder.append(":");
			stringBuilder.append(currentHeader.getValue());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	private String getFrom(Address[] addressesArray) {
		if (addressesArray == null || addressesArray.length == 0) {
			logger.warn("from addresses are null or empty!!!");
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (Address currentAddress : addressesArray) {
			stringBuilder.append(currentAddress.toString());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}