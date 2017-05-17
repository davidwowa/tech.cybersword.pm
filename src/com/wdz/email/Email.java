package com.wdz.email;

import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.Flags;

public class Email implements Serializable {

	private static final long serialVersionUID = 8901938651116173870L;

	private String uuid = UUID.randomUUID().toString();

	private Enumeration<?> allHeaders;
	private Address[] recipients;
	private Object content;
	private String contentyType;
	private String description;
	private Flags flags;
	private Address[] from;
	private Date receivedDate;

	private String sourceEmail;
	private String targetEmail;

	private String response;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Enumeration<?> getAllHeaders() {
		return allHeaders;
	}

	public void setAllHeaders(Enumeration<?> allHeaders) {
		this.allHeaders = allHeaders;
	}

	public Address[] getRecipients() {
		return recipients;
	}

	public void setRecipients(Address[] recipients) {
		this.recipients = recipients;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getContentyType() {
		return contentyType;
	}

	public void setContentyType(String contentyType) {
		this.contentyType = contentyType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Flags getFlags() {
		return flags;
	}

	public void setFlags(Flags flags) {
		this.flags = flags;
	}

	public Address[] getFrom() {
		return from;
	}

	public void setFrom(Address[] from) {
		this.from = from;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getSourceEmail() {
		return sourceEmail;
	}

	public void setSourceEmail(String sourceEmail) {
		this.sourceEmail = sourceEmail;
	}

	public String getTargetEmail() {
		return targetEmail;
	}

	public void setTargetEmail(String targetEmail) {
		this.targetEmail = targetEmail;
	}
}