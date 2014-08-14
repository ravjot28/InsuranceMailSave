package com.rav.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

import com.rav.bean.MessageInfo;

public class ReadMails {
	static String messagess = ",";
	Session session;
	Store store;
	boolean exception = false;
	private String subject;

	private int messageNumber;

	public static void main(String[] args) {
		List<MessageInfo> l = new ReadMails("imap.gmail.com", "993", "", "", "")
				.process();

		for (MessageInfo s : l) {
			System.out.println(s.getMessage());
		}
	}

	public ReadMails(String host, String port, String email, String password,
			String subject) {

		try {

			Properties properties = new Properties();

			// server setting
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);

			// SSL setting
			properties.setProperty("mail.imap.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			properties.setProperty("mail.imap.socketFactory.fallback", "false");
			properties.setProperty("mail.imap.socketFactory.port",
					String.valueOf(port));

			Session session = Session.getDefaultInstance(properties);
			store = session.getStore("imap");
			store.connect(email, password);
			this.subject = subject;

		} catch (Exception adfasf) {
			System.out.println("Constructor Catch: " + adfasf);
		}
		if (messageNumber <= 0)
			messageNumber = -1;
	}

	public ReadMails(String host, String port, String email, String password,
			String subject, int maxMessageNumber) {
		this(host, port, email, password, subject);
		messageNumber = maxMessageNumber;
	}

	public int getMessageNumber() {
		return messageNumber;
	}

	public List<MessageInfo> process() {
		try {
			Folder inbox = store.getFolder("Inbox");

			inbox.open(Folder.READ_ONLY);
			SearchTerm term = new SearchTerm() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean match(Message arg0) {
					try {
						if (arg0 != null && arg0.getSubject() != null
								&& arg0.getSubject().contains(subject)
								&& arg0.getMessageNumber() > messageNumber)
							return true;
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					return false;
				}
			};

			Message messages[] = inbox.search(term);
			System.out.println(messages.length);
			List<MessageInfo> messagesList = new ArrayList<MessageInfo>();
			int messageNumber = -1;
			for (int i = messages.length - 1; i >= 0; i--) {
				if (messages[i].getMessageNumber() > messageNumber)
					messageNumber = messages[i].getMessageNumber();
				String subject = messages[i].getSubject();

				Object message = messages[i].getContent();
				String content = "";
				if (message instanceof Multipart) {

					Multipart multipart = (Multipart) message;

					System.out.println("BodyPart " + "MultiPartCount: "
							+ multipart.getCount());

					for (int j = 0; j < multipart.getCount(); j++) {

						BodyPart bodyPart = multipart.getBodyPart(j);

						String disposition = bodyPart.getDisposition();

						if (disposition != null
								&& (disposition.equalsIgnoreCase("ATTACHMENT"))) {
							System.out.println("Mail have some attachment");

							DataHandler handler = bodyPart.getDataHandler();
							System.out.println("file name : "
									+ handler.getName());
						} else {
							System.out.println(bodyPart.getContent());
							content += bodyPart.getContent().toString() + "\n";

						}
					}
				} else {
					content = messages[i].getContent().toString();
				}

				String from = "";
				for (Address add : messages[i].getFrom())
					from = from + add.toString() + ",";

				String allRecipients = "";
				for (Address add : messages[i].getAllRecipients())
					allRecipients = allRecipients + add.toString() + ",";

				Date sentDate = messages[i].getSentDate();

				Date receivedDate = messages[i].getReceivedDate();

				MessageInfo m = new MessageInfo();
				m.setFrom(from);
				m.setMessage(content);
				m.setReceivedDate(receivedDate);
				m.setRecepients(allRecipients);
				m.setSentDate(sentDate);
				m.setSubject(subject);
				messagesList.add(m);
			}
			return messagesList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception 1: " + e);
		}
		return null;
	}
}
