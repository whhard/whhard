package com.zcreate.salary.ui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.zcreate.salary.pojo.SalaryInfoBean;

public class SalaryTableModel implements TableModel {
	private static String[] columnNames={
			"序号","邮箱","姓名","基本工资","岗位工资","绩效考核","导师工资","工资小计",
			"请假","未打卡","迟到","旷工","小计",
			"补助","电脑补贴","加班","小计",
			"单位社保","单位公积金","薪酬总额",
			"单位社保(-)","个人社保(-)",
			"单位公积金(-)","个人公积金(-)",
			"计税基础","应缴金额",
			"个人应扣","本月实际发放"
	};
	
	private static String[] columnMethods={
			null,"getEmial","getName","getBasic","getStation","getPerformance","getTutor","getSalaryTotal",
			"getLeave","getNotSignIn","getBeLate","getAbsenteeism","getDeductionTotal",
			"getSubsidy","getComputerSubsidy","getOvertime","getIncreaseTotal",
			"getCompanySB","getCompanyGJJ","getPaymentTotal",
			"getCompanySBDeduction","getPersonalSBDeduction",
			"getCompanyGJJDeduction","getPersonalGJJDeduction",
			"getBasicTax","getPaymentDeduction",
			"getPersonalDeduction",
			"getNetPayroll"
	};
	
	private List<SalaryInfoBean>  list;
	
	public SalaryTableModel() {
		this.list = new ArrayList<SalaryInfoBean>();
	}

	public List<SalaryInfoBean> getList() {
		return list;
	}

	public void setList(List<SalaryInfoBean> list) {
		this.list = list;
	}

	@Override
	public Class<?> getColumnClass(int i) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int i) {
		return columnNames[i];
	}

	@Override
	public int getRowCount() {
		int size=list.size();
		return size==0?1:size;
	}

	@Override
	public Object getValueAt(int i, int j) {
		if(j==0){
			return i+1;
		}
		if(i>list.size()-1){
			return "";
		}
		
		SalaryInfoBean bean=list.get(i);
		String methodStr=columnMethods[j];
		if(methodStr!=null){
			try {
				Method method=SalaryInfoBean.class.getMethod(methodStr);
				Object value=method.invoke(bean);
				if(value instanceof Double){
					value=((double)value)==0?"":value;
				}
				return value;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "-";
	}

	@Override
	public boolean isCellEditable(int i, int j) {
		return false;
	}
	
	@Override
	public void setValueAt(Object obj, int i, int j) {}

	@Override
	public void removeTableModelListener(TableModelListener tablemodellistener) {}
	
	@Override
	public void addTableModelListener(TableModelListener tablemodellistener) {}
}
