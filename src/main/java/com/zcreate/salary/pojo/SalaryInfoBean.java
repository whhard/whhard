package com.zcreate.salary.pojo;

public class SalaryInfoBean {
	private String excelTitle;
	private String excelDate;
	private double excelDayCount;
	
	private String name;
	private String depart;
	private String emial;
	// 工资
	private double basic;// 基本工资
	private double station;// 岗位工资
	private double performance;// 绩效
	private double tutor;//导师工资
	private double salaryTotal;// 工资小计

	// 减项
	private double leave;// 请假
	private double notSignIn;// 未签到
	private double beLate;// 迟到
	private double absenteeism;// 旷工
	private double deductionTotal;// 减项小计

	// 增项
	private double subsidy;// 补助
	private double computerSubsidy;// 电脑补贴
	private double overtime;// 加班
	private double increaseTotal;// 加班

	
	private double companySB;// 单位社保
	private double companyGJJ;// 单位公积金
	private double paymentTotal;// 薪酬总额

	
	// 社保减项
	private double companySBDeduction;//单位社保
	private double personalSBDeduction;//个人社保
	
	// 公积金减项
	private double companyGJJDeduction;//单位公积金
	private double personalGJJDeduction;//个人公积金

	// 个人所得税减项
	private double basicTax;//计税基础
	private double paymentDeduction;//应缴金额

	// 个人减项
	private double personalDeduction;

	// 本月实际发放
	private double netPayroll;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBasic() {
		return basic;
	}

	public void setBasic(double basic) {
		this.basic = basic;
	}

	public double getStation() {
		return station;
	}

	public void setStation(double station) {
		this.station = station;
	}

	public double getPerformance() {
		return performance;
	}

	public void setPerformance(double performance) {
		this.performance = performance;
	}

	public double getSalaryTotal() {
		return salaryTotal;
	}

	public void setSalaryTotal(double salaryTotal) {
		this.salaryTotal = salaryTotal;
	}

	public double getLeave() {
		return leave;
	}

	public void setLeave(double leave) {
		this.leave = leave;
	}

	public double getNotSignIn() {
		return notSignIn;
	}

	public void setNotSignIn(double notSignIn) {
		this.notSignIn = notSignIn;
	}

	public double getBeLate() {
		return beLate;
	}

	public void setBeLate(double beLate) {
		this.beLate = beLate;
	}

	public double getAbsenteeism() {
		return absenteeism;
	}

	public void setAbsenteeism(double absenteeism) {
		this.absenteeism = absenteeism;
	}

	public double getDeductionTotal() {
		return deductionTotal;
	}

	public void setDeductionTotal(double deductionTotal) {
		this.deductionTotal = deductionTotal;
	}

	public double getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(double subsidy) {
		this.subsidy = subsidy;
	}

	public double getComputerSubsidy() {
		return computerSubsidy;
	}

	public void setComputerSubsidy(double computerSubsidy) {
		this.computerSubsidy = computerSubsidy;
	}

	public double getOvertime() {
		return overtime;
	}

	public void setOvertime(double overtime) {
		this.overtime = overtime;
	}

	public double getIncreaseTotal() {
		return increaseTotal;
	}

	public void setIncreaseTotal(double increaseTotal) {
		this.increaseTotal = increaseTotal;
	}

	public double getCompanySB() {
		return companySB;
	}

	public void setCompanySB(double companySB) {
		this.companySB = companySB;
	}

	public double getCompanyGJJ() {
		return companyGJJ;
	}

	public void setCompanyGJJ(double companyGJJ) {
		this.companyGJJ = companyGJJ;
	}

	public double getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(double paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	public double getCompanySBDeduction() {
		return companySBDeduction;
	}

	public void setCompanySBDeduction(double companySBDeduction) {
		this.companySBDeduction = companySBDeduction;
	}

	public double getPersonalSBDeduction() {
		return personalSBDeduction;
	}

	public void setPersonalSBDeduction(double personalSBDeduction) {
		this.personalSBDeduction = personalSBDeduction;
	}

	public double getCompanyGJJDeduction() {
		return companyGJJDeduction;
	}

	public void setCompanyGJJDeduction(double companyGJJDeduction) {
		this.companyGJJDeduction = companyGJJDeduction;
	}

	public double getPersonalGJJDeduction() {
		return personalGJJDeduction;
	}

	public void setPersonalGJJDeduction(double personalGJJDeduction) {
		this.personalGJJDeduction = personalGJJDeduction;
	}

	public double getBasicTax() {
		return basicTax;
	}

	public void setBasicTax(double basicTax) {
		this.basicTax = basicTax;
	}

	public double getPaymentDeduction() {
		return paymentDeduction;
	}

	public void setPaymentDeduction(double paymentDeduction) {
		this.paymentDeduction = paymentDeduction;
	}

	public double getPersonalDeduction() {
		return personalDeduction;
	}

	public void setPersonalDeduction(double personalDeduction) {
		this.personalDeduction = personalDeduction;
	}

	public double getNetPayroll() {
		return netPayroll;
	}

	public void setNetPayroll(double netPayroll) {
		this.netPayroll = netPayroll;
	}

	public String getEmial() {
		return emial;
	}

	public void setEmial(String emial) {
		this.emial = emial;
	}

	public double getTutor() {
		return tutor;
	}

	public void setTutor(double tutor) {
		this.tutor = tutor;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getExcelTitle() {
		return excelTitle;
	}

	public void setExcelTitle(String excelTitle) {
		this.excelTitle = excelTitle;
	}

	public String getExcelDate() {
		return excelDate;
	}

	public void setExcelDate(String excelDate) {
		this.excelDate = excelDate;
	}

	public double getExcelDayCount() {
		return excelDayCount;
	}

	public void setExcelDayCount(double excelDayCount) {
		this.excelDayCount = excelDayCount;
	}
	
}
