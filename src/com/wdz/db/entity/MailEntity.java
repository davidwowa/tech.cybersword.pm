package com.wdz.db.entity;

import java.io.Serializable;

public class MailEntity implements Serializable {

	private static final long serialVersionUID = -3444019204775746682L;

	private String uuid;

	private String allHeaders;
	private String recipients;
	private String content;
	private String contentyType;
	private String description;
	private String from;
	private String receivedDate;

	private String sourceEmail;
	private String targetEmail;

	private String response;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAllHeaders() {
		return allHeaders;
	}

	public void setAllHeaders(String allHeaders) {
		this.allHeaders = allHeaders;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
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

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}