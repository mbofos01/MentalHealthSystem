package Tools;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This tool is used to send an email to someone.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class EmailService {
	/*
	 * Sender's email ID needs to be mentioned
	 */
	private static String from = "epl441.mentalhealth@gmail.com";

	/*
	 * Assuming you are sending email from through gmail's SMTP
	 */
	private static String host = "smtp.gmail.com";

	/**
	 * This function sends the actual email to someone.
	 * 
	 * @param to      String email address of receiver
	 * @param subject String subject of the email
	 * @param body    String email body
	 * @param verbose Boolean flag to print SMTP messages
	 * @return true if successful otherwise false
	 */
	public static boolean sendEmail(String to, String subject, String body, boolean verbose) {
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("*@gmail.com", "*");

			}

		});
		if (verbose)
			// Used to debug SMTP issues
			session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(body);

			// Send message
			Transport.send(message);
		} catch (MessagingException mex) {
			return false;
		}

		return true;
	}

	/**
	 * Example usage of mail sending tool.
	 * 
	 * @param args No arguments needed
	 */
	public static void main(String[] args) {
		sendEmail("*@gmail.com", "test", "as", true);
	}

}
