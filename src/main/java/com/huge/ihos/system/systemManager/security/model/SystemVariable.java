package com.huge.ihos.system.systemManager.security.model;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.user.model.User;

public class SystemVariable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7133544307860410486L;
	private String orgCode; 	//登陆的单位
	private String orgName = "";//登陆单位名称	
	private String copyCode; 	//登陆的账套
	private String copyName = "";//登陆账套名称
	private String periodPlanCode ="";//登陆的期间方案;
	private String periodYear = ""; //登陆的年度
	private String period = "";     //登陆的期间（月度）
	private String businessDate;    //登陆业务日期
	private String dataBaseName;    //登陆数据库名称
	private String currentRootMenu ;//当前子系统
	private String currentRootMenuCode ;//当前子系统代码 hr、gz
	private String subSysMainPage = "";//当前子系统主页URL
	
	private User loginUser ;	//登陆用户
	private Person loginPerson ;		//登陆人
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCopyName() {
		return copyName;
	}
	public void setCopyName(String copyName) {
		this.copyName = copyName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getCopyCode() {
		return copyCode;
	}
	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getDataBaseName() {
		return dataBaseName;
	}
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}
	public String getCurrentRootMenu() {
		return currentRootMenu;
	}
	public void setCurrentRootMenu(String currentRootMenu) {
		this.currentRootMenu = currentRootMenu;
	}
	public String getSubSysMainPage() {
		return subSysMainPage;
	}
	public void setSubSysMainPage(String subSysMainPage) {
		this.subSysMainPage = subSysMainPage;
	}
	
	public String getPeriodPlanCode() {
		return periodPlanCode;
	}
	public void setPeriodPlanCode(String periodPlanCode) {
		this.periodPlanCode = periodPlanCode;
	}
	public String getPeriodYear() {
		return periodYear;
	}
	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}
	
	public String getCurrentRootMenuCode() {
		return currentRootMenuCode;
	}
	public void setCurrentRootMenuCode(String currentRootMenuCode) {
		this.currentRootMenuCode = currentRootMenuCode;
	}
	
	@JSON(serialize=false)
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	
	@JSON(serialize=false)
	public Person getLoginPerson() {
		return loginPerson;
	}
	public void setLoginPerson(Person loginPerson) {
		this.loginPerson = loginPerson;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((businessDate == null) ? 0 : businessDate.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((copyName == null) ? 0 : copyName.hashCode());
		result = prime * result
				+ ((currentRootMenu == null) ? 0 : currentRootMenu.hashCode());
		result = prime * result
				+ ((dataBaseName == null) ? 0 : dataBaseName.hashCode());
		result = prime * result + ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemVariable other = (SystemVariable) obj;
		if (businessDate == null) {
			if (other.businessDate != null)
				return false;
		} else if (!businessDate.equals(other.businessDate))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (copyName == null) {
			if (other.copyName != null)
				return false;
		} else if (!copyName.equals(other.copyName))
			return false;
		if (currentRootMenu == null) {
			if (other.currentRootMenu != null)
				return false;
		} else if (!currentRootMenu.equals(other.currentRootMenu))
			return false;
		if (dataBaseName == null) {
			if (other.dataBaseName != null)
				return false;
		} else if (!dataBaseName.equals(other.dataBaseName))
			return false;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SystemVariable [orgCode=" + orgCode + ", copyCode=" + copyCode
				+ ", kjYear=" + periodYear + ", period=" + period
				+ ", businessDate=" + businessDate + ", dataBaseName="
				+ dataBaseName + ", orgName=" + orgName + ", copyName="
				+ copyName + ", currentRootMenu=" + currentRootMenu
				+ "]";
	}

}
