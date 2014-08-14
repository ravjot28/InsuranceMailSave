package com.rav.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGES")
public class MessageInfo {
	@Id
	@GeneratedValue(generator = "messageID_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1001, sequenceName = "messageID_seq", name = "messageID_seq")
	private int id;

	private Date sentDate;
	private Date receivedDate;
	private String from;
	private String recepients;
	private String message;
	private String subject;

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getRecepients() {
		return recepients;
	}

	public void setRecepients(String recepients) {
		this.recepients = recepients;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
