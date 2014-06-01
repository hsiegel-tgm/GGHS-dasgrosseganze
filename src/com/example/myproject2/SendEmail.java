package com.example.myproject2;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Hannah
 *
 *
 *http://www.coderanch.com/t/439043/java/java/sending-mail-JavaMail
 */
public class SendEmail {
	private static final String SMTP_HOST_NAME = "smtp.door2solution.at";
	private static final String SMTP_AUTH_USER = "doodle@door2solution.at";
	private static final String SMTP_AUTH_PWD = "28D8zDB6NbS5x26e";
	private static final String SMTP_FROM = SMTP_AUTH_USER;

	public static void send(String to, String subject, String content) throws AddressException, MessagingException {

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session mailSession = Session.getDefaultInstance(props, auth);

		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		message.setContent(content, "text/html");
		message.setFrom(new InternetAddress(SMTP_FROM));
		message.setSubject(subject); //TODO test
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		transport.connect();
		transport.sendMessage(message,
				message.getRecipients(Message.RecipientType.TO));
		transport.close();

	}

	private static class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}
