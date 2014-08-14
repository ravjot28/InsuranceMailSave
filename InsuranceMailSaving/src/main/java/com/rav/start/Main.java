package com.rav.start;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.rav.bean.MessageInfo;
import com.rav.dao.SaveMailDAO;
import com.rav.mail.ReadMails;

public class Main {

	private static final String FILE_NAME = "id.val";

	public static void main(String[] args) {
		int messageNumber = getMessageNumber();
		ReadMails mails = new ReadMails("imap.google.com", "993", "testingpurpo@gmail.com", "", "", messageNumber);
		List<MessageInfo> messageInfo = mails.process();
		SaveMailDAO dao = new SaveMailDAO();
		dao.saveMessages(messageInfo);
		setMessageNumber(mails.getMessageNumber());
	}

	public static void setMessageNumber(int number) {
		BufferedWriter b = null;
		try {
			File f = new File(FILE_NAME);
			b = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					f)));
			b.write(number);
		} catch (Exception e) {

		} finally {
			if (b != null)
				try {
					b.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static int getMessageNumber() {
		BufferedReader b = null;
		try {
			File f = new File(FILE_NAME);
			if (f.exists()) {
				b = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(FILE_NAME))));
				String line = b.readLine();
				if (line != null) {
					return Integer.parseInt(line);
				} else {
					return -1;
				}
			} else {
				return -1;
			}

		} catch (Exception e) {

		} finally {
			if (b != null)
				try {
					b.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return -1;
	}

}
