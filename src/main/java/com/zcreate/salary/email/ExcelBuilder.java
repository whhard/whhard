package com.zcreate.salary.email;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zcreate.salary.pojo.SalaryInfoBean;
import com.zcreate.salary.util.SalaryProperties;

public class ExcelBuilder {
	private String templFile;
	private String excelTitleInfo;
	private String excelDateInfo;
	private String sheetName;
	
	int getIntProperties(String str){
		str=SalaryProperties.getSalaryProperty(str);
		return coventInt(str);
	}
	int coventInt(Object obj){
		return Integer.parseInt(obj.toString());
	}
	String getExcelTitleInfo(){
		return excelTitleInfo==null?getCurrDate("yyyy年MM月")+"份工资明细表":excelTitleInfo;
	}
	
	String getExcelDateInfo(){
		return excelDateInfo==null?getCurrDate("yyyy年MM月dd日"):excelDateInfo;
	}
	
	String getSheetName(){
		return sheetName==null?"工资条":sheetName;
	}
	
	String getTemplFile() {
		if(templFile==null){
			templFile=SalaryProperties.getSalaryProperty("excel.templ.filepath");
		}
		return templFile;
	}

	void setTemplFile(String templFile) {
		this.templFile = templFile;
	}

	void setExcelTitleInfo(String excelTitleInfo) {
		this.excelTitleInfo = excelTitleInfo;
	}

	void setExcelDateInfo(String excelDateInfo) {
		this.excelDateInfo = excelDateInfo;
	}

	void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	String getCurrDate(String format){
		 DateFormat sdf = new SimpleDateFormat(format);
		 return sdf.format(new Date());
	}

	//生成工资条Excel文件的输出流
	public ByteArrayOutputStream build(SalaryInfoBean salaryInfoBean)throws Exception{
		InputStream is=null;
		Workbook wb=null;
		
		try {
			is=SalaryProperties.class.getClassLoader().getResource(getTemplFile()).openStream();
			POIFSFileSystem fs=new POIFSFileSystem(is); 
			wb=new HSSFWorkbook(fs);
		} catch (OfficeXmlFileException e) {
			 try {
				 is=SalaryProperties.class.getClassLoader().getResource(getTemplFile()).openStream();
				wb = new XSSFWorkbook(is);
			} catch (IOException e1) {
				wb=null;
				throw e;
			}  
		}catch (Exception e) {
			wb=null;
			throw e;
		}
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		
		Sheet sheet;
		try {
			int sheetIndex=getIntProperties("excel.templ.title.row");
			sheet = wb.getSheetAt(sheetIndex);
			wb.setSheetName(wb.getSheetIndex(sheet), getSheetName());
		
		
		//设置标题
		int titleRowIndex=getIntProperties("excel.templ.title.row");
		int titleCellIndex=getIntProperties("excel.templ.title.cell");
		Cell titleCell = sheet.getRow(titleRowIndex).getCell(titleCellIndex);
		titleCell.setCellValue(salaryInfoBean.getExcelTitle()); 
        
		//设置时间
        int dateRowIndex=getIntProperties("excel.templ.date.row");
		int dateCellIndex=getIntProperties("excel.templ.date.cell");
        Cell dateCell = sheet.getRow(dateRowIndex).getCell(dateCellIndex);  
        dateCell.setCellValue(salaryInfoBean.getExcelDate());
        
        //设置天数
        int dayCountRowIndex=getIntProperties("excel.templ.daycount.row");
		int dayCountCellIndex=getIntProperties("excel.templ.daycount.cell");
        Cell dayCountCell = sheet.getRow(dayCountRowIndex).getCell(dayCountCellIndex);  
        dayCountCell.setCellValue(salaryInfoBean.getExcelDayCount());
        
        int salaryInfoRowIndex=getIntProperties("excel.templ.salaryinfo.row");
        Row newrow=sheet.getRow(salaryInfoRowIndex);
        if(newrow==null){
        	newrow=sheet.createRow(salaryInfoRowIndex);
        }
        
        //设置员工部门
        int userDepatCellIndex=1;
        Cell cellDepat=newrow.getCell(userDepatCellIndex);
        cellDepat=cellDepat==null?newrow.createCell(userDepatCellIndex):cellDepat;
        cellDepat.setCellValue(salaryInfoBean.getDepart());
        
        //设置员工名称
        int userNameCellIndex=2;
        Cell cell=newrow.getCell(userNameCellIndex);
        cell=cell==null?newrow.createCell(userNameCellIndex):cell;
        cell.setCellValue(salaryInfoBean.getName());
       
        
        buildSalaryInfo(newrow,salaryInfoBean);

        wb.write(baos);
        
		} catch (Exception e) {
			baos=null;
			throw new Exception("工资条模板文件错误", e);
		}finally{
			if(wb!=null){
				wb=null;
			}
		}
        return baos;
	}
	
	private void buildSalaryInfo(Row row,SalaryInfoBean salary){
		int startCellNum=3;
				
		setCellValue(row.getCell(startCellNum++),salary.getBasic());
		setCellValue(row.getCell(startCellNum++),salary.getStation());
		setCellValue(row.getCell(startCellNum++),salary.getPerformance());
		setCellValue(row.getCell(startCellNum++),salary.getTutor());
		setCellValue(row.getCell(startCellNum++),salary.getSalaryTotal());
		
		setCellValue(row.getCell(startCellNum++),salary.getLeave());
		setCellValue(row.getCell(startCellNum++),salary.getNotSignIn());
		setCellValue(row.getCell(startCellNum++),salary.getBeLate());
		setCellValue(row.getCell(startCellNum++),salary.getAbsenteeism());
		setCellValue(row.getCell(startCellNum++),salary.getDeductionTotal());
		
		setCellValue(row.getCell(startCellNum++),salary.getSubsidy());
		setCellValue(row.getCell(startCellNum++),salary.getComputerSubsidy());
		setCellValue(row.getCell(startCellNum++),salary.getOvertime());
		setCellValue(row.getCell(startCellNum++),salary.getIncreaseTotal());
		
		setCellValue(row.getCell(startCellNum++),salary.getCompanySB());
		setCellValue(row.getCell(startCellNum++),salary.getCompanyGJJ());
		setCellValue(row.getCell(startCellNum++),salary.getPaymentTotal());
		
		setCellValue(row.getCell(startCellNum++),salary.getCompanySBDeduction());
		setCellValue(row.getCell(startCellNum++),salary.getPersonalSBDeduction());
		
		setCellValue(row.getCell(startCellNum++),salary.getCompanyGJJDeduction());
		setCellValue(row.getCell(startCellNum++),salary.getPersonalGJJDeduction());
		
		
		setCellValue(row.getCell(startCellNum++),salary.getBasicTax());
		setCellValue(row.getCell(startCellNum++),salary.getPaymentDeduction());
		
		setCellValue(row.getCell(startCellNum++),salary.getPersonalDeduction());
		
		setCellValue(row.getCell(startCellNum++),salary.getNetPayroll());
	}
	
	private void setCellValue(Cell cell,double value){
		if(value==0){
			cell.setCellValue("");
		}else{
			cell.setCellValue(value);
		}
	}
}
