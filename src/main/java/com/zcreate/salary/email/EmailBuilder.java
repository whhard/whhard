package com.zcreate.salary.email;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.zcreate.salary.pojo.SalaryInfoBean;

public class EmailBuilder {
	private EmailSender  sender=new EmailSender();
	public Message build(SalaryInfoBean salaryInfoBean, ByteArrayOutputStream baos,String msg)throws Exception{
		String targetUserName=salaryInfoBean.getName();
		
		Message message=sender.createMessage();
		message.setFrom(sender.getDefaultFromAddress());
		
		message.setSubject(getSubject(targetUserName));
		message.setSentDate(new Date());
		
		Multipart multipart = new MimeMultipart();
		BodyPart contentPart = new MimeBodyPart();
        contentPart.setText(targetUserName+"，您好,附件为您"+salaryInfoBean.getExcelTitle()+",请查收."+(msg==null?"":"__"+msg));
        
        multipart.addBodyPart(contentPart);
        
        BodyPart messageBodyPart= new MimeBodyPart();
        messageBodyPart.setFileName(getAttachmentName(targetUserName));
        messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(baos.toByteArray(),"application/x-xlsx")));
        multipart.addBodyPart(messageBodyPart);
        
        message.setContent(multipart);
        
        message.saveChanges();
		return message;
	}
	
	String getSubject(String userName){
		return new StringBuilder("智诚科技工资单-").append(userName).append("-").append(getCurrDate("yyyy-MM-dd")).append(")").toString();
	}
	
	String getAttachmentName(String userName){
		
		return new StringBuilder("智诚科技工资-").append(userName).append("-").append(getCurrDate("yyyy-MM-dd")).append(").xlsx").toString();
	}
	
	String getCurrDate(String format){
		 DateFormat sdf = new SimpleDateFormat(format);
		 return sdf.format(new Date());
	}

}
