package com.zcreate.salary.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;

import com.zcreate.salary.email.EmailBuilder;
import com.zcreate.salary.email.EmailSender;
import com.zcreate.salary.email.ExcelBuilder;
import com.zcreate.salary.pojo.SalaryInfoBean;
import com.zcreate.salary.ui.SalaryMainFrame;

public class SalaryEmailSendService {
	private static SalaryEmailSendService instance;
	private static ExcelBuilder excelBuilder = new ExcelBuilder();
	private static EmailBuilder emailBuilder = new EmailBuilder();
	private static EmailSender emailSender = new EmailSender();

	public static SalaryEmailSendService getDefaultInstance() {
		if (instance == null) {
			instance = new SalaryEmailSendService();
		}
		return instance;
	}

	public void sendEmail(List<SalaryInfoBean> list,String msg) {
		Message message;
		ByteArrayOutputStream baos;
		int count=list.size();
		int sendCount=0;
		for (int i=0;i<list.size();i++) {
			try {
				if(SalaryMainFrame.getDefaultMainFrame().isPause()){
					do{
						Thread.sleep(500);
					}while(SalaryMainFrame.getDefaultMainFrame().isPause());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			SalaryInfoBean salaryInfoBean=list.get(i);
			
			String targetUserName=salaryInfoBean.getName();
			String userEmail=salaryInfoBean.getEmial();
			
			SalaryMainFrame.getDefaultMainFrame().updateTableRow(count,i,targetUserName,userEmail);

			try {//�����������ļ�
				baos = excelBuilder.build(salaryInfoBean);
			} catch (Exception e) {
				sendFailure(count,i,targetUserName,userEmail,"�������ļ�����ʧ��");
				continue;
			}
			try {//�����ʼ�����
				message = emailBuilder.build(salaryInfoBean, baos,msg);
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail, false));
			} catch (Exception e) {
				sendFailure(count,i,targetUserName,userEmail,"�ʼ�����ʧ��");
				continue;
			}
			
			try {//ִ���ʼ�����
				emailSender.send(message,(i==list.size()-1));
				sendCount++;
				sendSucceed(count,i,targetUserName,userEmail);
			} catch (Exception e) {
				sendFailure(count,i,targetUserName,userEmail,"�ʼ�����ʧ��");
				continue;
			}
		}
		JOptionPane.showMessageDialog(SalaryMainFrame.getDefaultMainFrame(),"�ʼ�����ִ�����,�����ʼ�:"+sendCount+"/"+list.size(),"��ʾ", JOptionPane.INFORMATION_MESSAGE);
		SalaryMainFrame.getDefaultMainFrame().operationActivate();
	}

	
	private void sendFailure(int count,int row,String targetUserName,String userEmail,String message){
		SalaryMainFrame.getDefaultMainFrame().updateMassage((row+1)+"/"+count+"|"+targetUserName+"|"+userEmail+"|"+message);
		SalaryMainFrame.getDefaultMainFrame().setFailureRow(row);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void sendSucceed(int count,int row,String targetUserName,String userEmail){
		SalaryMainFrame.getDefaultMainFrame().updateMassage((row+1)+"/"+count+"|"+targetUserName+"|"+userEmail+"|�ʼ����ͳɹ�");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��֤����
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * ��֤�ֻ�����
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
