package com.zcreate.salary.email;

import java.security.Security;
import java.util.List;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import com.zcreate.salary.ui.SalaryMainFrame;
import com.zcreate.salary.util.SalaryProperties;

public class EmailSender {
	private static Session session;
	private Transport transport; 
	static {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		session = Session.getDefaultInstance(SalaryProperties.getDefault(), new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
						SalaryProperties.getSalaryProperty("mail.connect.username"),
						SalaryProperties.getSalaryProperty("mail.connect.password"));
			}
		});
	}
	
	
	public void send(Message message,boolean connectClose) throws Exception{
			Transport transport=getTransport();
			transport.send(message);
			if(connectClose){
				transport.close();
			}
	}
	
	private Transport getTransport()throws NoSuchProviderException,MessagingException{
		if(transport==null){
			transport=session.getTransport();
		}
		if(!transport.isConnected()){
			transport.connect(
					SalaryProperties.getSalaryProperty("mail.smtp.host"),
					SalaryProperties.getSalaryProperty("mail.connect.username"),
					SalaryProperties.getSalaryProperty("mail.connect.password"));
		}
		return transport;
	}
	
	InternetAddress getDefaultFromAddress()throws AddressException{
		return new InternetAddress(SalaryProperties.getSalaryProperty("mail.default.fromaddress"));
	}
	
	Message createMessage(){
		return new MimeMessage(session);
	}
}
