package com.zcreate.salary.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SalaryProperties extends Properties {
	private static final long serialVersionUID = -7593343036491560483L;
	
	private static String propertiesPath="./config/salary.properties";
	
	private static SalaryProperties instance;

	public SalaryProperties() {
		try {
			InputStream is=SalaryProperties.class.getClassLoader().getResource(propertiesPath).openStream();
			load(is);
			
		} catch (IOException e) {
			e.printStackTrace();
			//SalaryMainFrame.getDefaultMainFrame().setStatuInfo(e.getMessage());
		}
	}
	
	public static SalaryProperties getDefault(){
		if(instance==null){
			instance=new SalaryProperties();
		}
		return instance;
	}
	
	public static String getSalaryProperty(String key){
		return getSalaryProperty(key,null);
	}
	
	public static String getSalaryProperty(String key,String defaultValue){
		return getDefault().getProperty(key, defaultValue);
	}
}
