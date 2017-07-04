package com.zcreate.salary.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zcreate.salary.pojo.SalaryInfoBean;
import com.zcreate.salary.util.SalaryProperties;

public class SalaryDataLoadService {
	private static SalaryDataLoadService instance;
	
	public static SalaryDataLoadService getDefaultService(){
		if(instance==null){
			instance=new SalaryDataLoadService();
		}
		return instance;
	}
	
	public List<SalaryInfoBean> getSalarysInfoList(File excelFile){
		Workbook wb=null;
		InputStream is=null;
		try {
			is=new FileInputStream(excelFile);
			POIFSFileSystem fs=new POIFSFileSystem(is); 
			wb=new HSSFWorkbook(fs);
		} catch (OfficeXmlFileException e) {
			 try {
				is=new FileInputStream(excelFile);
				wb = new XSSFWorkbook(is);
			} catch (IOException e1) {
				wb=null;
			}  
		}catch (Exception e) {
			wb=null;
		}
		if(wb!=null){
			Sheet sheet = wb.getSheetAt(getSheetIndex());
			return buildSalary(sheet);
		}
		return new ArrayList<SalaryInfoBean>();
	}
	
	private List<SalaryInfoBean> buildSalary(Sheet sheet){
		List<SalaryInfoBean> ret=new ArrayList<SalaryInfoBean>();
		int startRowNum=getStartRowNum();
		int lastRowNum=sheet.getLastRowNum();
		
		Row row;
		SalaryInfoBean bean;
		for(int i=startRowNum;i<(lastRowNum-1);i++){
			row=sheet.getRow(i);
			if(row==null){
				continue;
			}
			bean=buildSalaryInfo(row);
			if(bean!=null){
				bean.setExcelTitle(sheet.getRow(0).getCell(0).getStringCellValue());
				bean.setExcelDate(sheet.getRow(2).getCell(0).getStringCellValue());
				bean.setExcelDayCount(sheet.getRow(3).getCell(3).getNumericCellValue());
				ret.add(bean);
			}
		}
		
		return ret;
	}
	
	private SalaryInfoBean buildSalaryInfo(Row row){
		SalaryInfoBean salary=new SalaryInfoBean();
		int startCellNum=getStartCell();
		int lastCellNum=getEndCell();
		
		String depart=row.getCell(startCellNum++).getStringCellValue();
		salary.setDepart(depart);
		
		String name=row.getCell(startCellNum++).getStringCellValue();
		if(name==null||name.trim().length()==0){
			return null;
		}
		
		salary.setName(name.trim());
		
		
		double basic=row.getCell(startCellNum++).getNumericCellValue();
		double station=row.getCell(startCellNum++).getNumericCellValue();
		double performance=row.getCell(startCellNum++).getNumericCellValue();
		double tutor=row.getCell(startCellNum++).getNumericCellValue();
		double salaryTotal=row.getCell(startCellNum++).getNumericCellValue();
		
		salary.setBasic(basic);
		salary.setStation(station);
		salary.setPerformance(performance);
		salary.setTutor(tutor);
		salary.setSalaryTotal(salaryTotal);
		
		double leave=row.getCell(startCellNum++).getNumericCellValue();
		double notSignIn=row.getCell(startCellNum++).getNumericCellValue();
		double beLate=row.getCell(startCellNum++).getNumericCellValue();
		double absenteeism=row.getCell(startCellNum++).getNumericCellValue();
		double deductionTotal=row.getCell(startCellNum++).getNumericCellValue();
		
		salary.setLeave(leave);
		salary.setNotSignIn(notSignIn);
		salary.setBeLate(beLate);
		salary.setAbsenteeism(absenteeism);
		salary.setDeductionTotal(deductionTotal);
		
		
		double subsidy=row.getCell(startCellNum++).getNumericCellValue();
		double computerSubsidy=row.getCell(startCellNum++).getNumericCellValue();
		double overtime=row.getCell(startCellNum++).getNumericCellValue();
		double increaseTotal=row.getCell(startCellNum++).getNumericCellValue();
		
		salary.setSubsidy(subsidy);
		salary.setComputerSubsidy(computerSubsidy);
		salary.setOvertime(overtime);
		salary.setIncreaseTotal(increaseTotal);
		
		double companySB=row.getCell(startCellNum++).getNumericCellValue();
		double companyGJJ=row.getCell(startCellNum++).getNumericCellValue();
		double paymentTotal=row.getCell(startCellNum++).getNumericCellValue();
		
		salary.setCompanySB(companySB);
		salary.setCompanyGJJ(companyGJJ);
		salary.setPaymentTotal(paymentTotal);
		
		double companySBDeduction=row.getCell(startCellNum++).getNumericCellValue();
		double personalSBDeduction=row.getCell(startCellNum++).getNumericCellValue();
		
		salary.setCompanySBDeduction(companySBDeduction);
		salary.setPersonalSBDeduction(personalSBDeduction);
		
		double companyGJJDeduction=row.getCell(startCellNum++).getNumericCellValue();
		double personalGJJDeduction=row.getCell(startCellNum++).getNumericCellValue();
		
		salary.setCompanyGJJDeduction(companyGJJDeduction);
		salary.setPersonalGJJDeduction(personalGJJDeduction);
		
		double basicTax=row.getCell(startCellNum++).getNumericCellValue();
		double paymentDeduction=row.getCell(startCellNum++).getNumericCellValue();
		
		salary.setBasicTax(basicTax);
		salary.setPaymentDeduction(paymentDeduction);
		
		double personalDeduction=row.getCell(startCellNum++).getNumericCellValue();
		
		salary.setPersonalDeduction(personalDeduction);
		
		double netPayroll=row.getCell(startCellNum++).getNumericCellValue();
		salary.setNetPayroll(netPayroll);
		
		String emial=row.getCell(startCellNum++).getStringCellValue();
		salary.setEmial(emial);
		
		return salary;
	}
	
	private int getSheetIndex(){
		return Integer.parseInt(SalaryProperties.getSalaryProperty("excel.datasource.sheetindex").toString());
	}
	private int getStartRowNum(){
		return Integer.parseInt(SalaryProperties.getSalaryProperty("excel.datasource.startrow").toString());
	}
	private int getStartCell(){
		return Integer.parseInt(SalaryProperties.getSalaryProperty("excel.datasource.startcell").toString());
	}
	private int getEndCell(){
		return Integer.parseInt(SalaryProperties.getSalaryProperty("excel.datasource.endcell").toString());
	}
	
}
