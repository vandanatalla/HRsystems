package com.example.android.helloactivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {
	private String stmpHost = "smtp.gmail.com";
	private String mailServer = "imap.gmail.com";
	private EmailAccount account;
	private Session smtpSession;
	private Session imapSession;

	private Folder inbox;
	private Store store;

	public EmailManager(String username, String password, String urlServer,
			String stmpHost, String mailServer) {
		account = new EmailAccount(username, password, urlServer);
		this.stmpHost = stmpHost;
		this.mailServer = mailServer;
		initProtocol();
	}

	private void initProtocol() {
		EmailAuthenticator authenticator = new EmailAuthenticator(account);

		Properties props1 = new Properties();
		props1.setProperty("mail.transport.protocol", "smtps");
		props1.setProperty("mail.host", stmpHost);
		props1.put("mail.smtp.auth", "true");
		props1.put("mail.smtp.port", "465");
		props1.put("mail.smtp.socketFactory.port", "465");
		props1.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props1.put("mail.smtp.socketFactory.fallback", "false");
		props1.setProperty("mail.smtp.quitwait", "false");
		smtpSession = Session.getDefaultInstance(props1, authenticator);

		Properties props2 = new Properties();
		props2.setProperty("mail.store.protocol", "imaps");
		props2.setProperty("mail.imaps.host", mailServer);
		props2.setProperty("mail.imaps.port", "993");
		props2.setProperty("mail.imaps.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props2.setProperty("mail.imaps.socketFactory.fallback", "false");
		imapSession = Session.getInstance(props2);
	}

	public HashMap getMails() throws MessagingException, IOException {
		store = imapSession.getStore("imaps");
		store.connect(mailServer, account.username, account.password);
		inbox = store.getFolder("Inbox");
		inbox.open(Folder.READ_ONLY);
		Message[] result = inbox.getMessages(1, 10);

		/*
		 * while (headers.hasMoreElements()) { Header h = (Header)
		 * headers.nextElement(); //System.out.println(h.getName() + ":" +
		 * h.getValue()); //System.out.println(""); String mID = h.getName();
		 * if(mID.contains("Message-ID")){ h.getValue(); } }
		 */
		try {
			result[1].getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, String> hm = new HashMap<String, String>();
		for (int i = 0; i < 10; i++) {
			if (result[i].getSubject() != null
					|| result[i].getContent() != null) {
				hm.put(result[i].getSubject(), result[i].getContent()
						.toString());
			}
		}

		return hm;
	}

	public void close() {
		// Close connection
		try {
			inbox.close(false);
			store.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void sendMail(String subject, String body,
			String sender, String recipients) throws Exception {
		MimeMessage message = new MimeMessage(smtpSession);
		DataHandler handler = new DataHandler(new ByteArrayDataSource(
				body.getBytes(), "text/plain"));
		message.setSender(new InternetAddress(sender));
		message.setSubject(subject);
		message.setDataHandler(handler);
		if (recipients.indexOf(',') > 0)
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipients));
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					recipients));
		Transport.send(message);
	}
}
