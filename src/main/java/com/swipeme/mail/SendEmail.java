package com.swipeme.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.swipeme.domain.SendEmailDomain;

public class SendEmail {
	public static void main123(String[] args) {
		// Recipient's email ID needs to be mentioned.
		String to = "bchetluri@osius.com";

		// Sender's email ID needs to be mentioned
		String from = "bchetluri@gmail.com";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	//////////////////////

	public static void sendEmail(SendEmailDomain domain) {
	
		

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth.mechanisms", "login");
		props.put("mail.smtp.quitwait", "false");
		props.put("mail.debug ", "false");
		props.put("mail.username", "kasim.ch786@gmail.com");
		props.put("mail.password", "Thameema@3");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.protocol", "smtps");

		// Get the Session object.

		try {
			// Create a default MimeMessage object.
			SmtpAuthenticator authentication = new SmtpAuthenticator();
			Session session = Session.getInstance(props, authentication);

			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(domain.getFromAddress()));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(domain.getToAddress()));

			// Set Subject: header field
			message.setSubject("EMP ID:12345 , Swipe Details Issue On (10/27/2016)");

			// Now set the actual message
			/*StringWriter writer = new StringWriter();
			IOUtils.copy(new FileInputStream(new File("home.html")), writer);
*/
			message.setContent(getMessage(domain), "text/html");

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static String getMessage(SendEmailDomain emilObj){
		
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<script>"); 
		sb.append("function testEmp(id,name){alert(id);alert(name);}");
		sb.append("</script>");
		sb.append("</head>");
		sb.append("<body>");
		
		sb.append("<b>Emp Name         : KASIM<b><br>");
		sb.append("<b>Date             : 10/27/2016</b><br>");
		sb.append("<b>No Hours Present : 0</b><br>");
		sb.append("<b><input type='button' value='APPROVED1' name='approved1' onclick='testEmp(1111,1122djsssjddj)'/></b><br>");
		sb.append("<b><a href='http://localhost:8084/time/approved?empId=123&date=10-20-2016&reason=test&approvedBy="+emilObj.getUserName()+"'/>APPROVED</a></b><br>");
		sb.append("</body>");
		sb.append("</html>");
		
		
        return sb.toString();
	}

	public static class SmtpAuthenticator extends Authenticator {
		public SmtpAuthenticator() {

			super();
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			String username = "kasim.ch786@gmail.com";
			String password = "Thameema@3";
			if ((username != null) && (username.length() > 0) && (password != null) && (password.length() > 0)) {

				return new PasswordAuthentication(username, password);
			}

			return null;
		}
	}
}
