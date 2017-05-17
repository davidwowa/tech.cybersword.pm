package com.wdz.email.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wdz.email.Email;

public class EmailGenerator {

	private Logger logger = Logger.getLogger(Logger.class);

	public List<Email> generate(String sourceEmail, String targetEMail, int count) {
		logger.info("generate " + count + " emails");
		List<Email> emailList = new ArrayList<Email>();
		for (int i = 0; i <= count; i++) {
			Email email = new Email();
			email.setSourceEmail(sourceEmail);
			email.setContent(email.getUuid() + " " + sourceEmail + "->" + targetEMail);
			email.setTargetEmail(targetEMail);
			emailList.add(email);
		}
		return emailList;
	}
}