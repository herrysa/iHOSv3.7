package com.huge.ihos.system.context;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilegeBean;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.DateUtil;

public class UserContext implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7133544307860410486L;
	//时间类型
	private String periodPlanCode ="";		//登录期间[方案]
	private String periodYear = "";			//登录期间[年度]eg.2015
	private String periodMonth = "";		//登录期间[月度]eg.201501
	private String periodQuarter = "";		//登录期间[季度]
	private Date businessDate;				//登录期间[日期]-日期型
	private String businessDateStr;			//登录期间[日期]-字符串
	private Date periodBeginDate;			//登录期间[开始日期]-日期型 req:
	private String periodBeginDateStr;		//登录期间[开始日期]-字符串 req:
	private Date periodEndDate;				//登录期间[结束日期]-日期型 req:
	private String periodEndDateStr;		//登录期间[结束日期]-字符串 req:
	private Date periodYearBeginDate;		//登录期间[年度开始日期]-日期型 req:
	private String periodYearBeginDateStr;	//登录期间[年度开始日期]-字符串 req:
	private Date periodYearEndDate;			//登录期间[年度结束日期]-日期型 req:
	private String periodYearEndDateStr;	//登录期间[年度结束日期]-字符串 req:
	
	private Date sysDate;					//登录系统[日期]
	private String sysDateStr;				//登录系统[日期]-字符串
	private String sysYear;					//登录系统[年]
	private String sysMonth;				//登录系统[月]
	private String sysQuarter = "";			//登录系统[季]
	private Date monthBeginDate;			//登录系统[月开始日期]
	private String monthBeginDateStr;
	private Date monthEndDate;				//登录系统[月结束日期]
	private String monthEndDateStr;			//登录系统[月结束日期]-字符串
	private Date yearBeginDate;				//登录系统[年开始日期]
	private String yearBeginDateStr;		//登录系统[年开始日期]
	private Date yearEndDate;				//登录系统[年结束日期]
	private String yearEndDateStr;			//登录系统[年结束日期]
	
	//组织结构
	private String orgCode; 				//操作环境[单位ID]
	private String orgName = "";			//操作环境[单位名称]	
	private String branchCode = "";			//操作环境[院区ID]	
	private String branchName = "";			//操作环境[院区名称]	
	private String copyCode; 				//登录[账套ID]
	private String copyName = "";			//登录[账套名称]
	private String loginOrgId;				//登录[单位ID]
	private String loginOrgName;			//登录[单位名称]
	private String loginUserId ;			//登录[用户ID]
	private String loginUserName ;			//登录[用户名称]
	private String loginDeptId;				//登录[部门ID]
	private String loginDeptCode;			//登录[部门编码]
	private String loginDeptName;			//登录[部门姓名]
	private String loginPersonId;			//登录[人员ID]
	private String loginPersonCode;			//登录[人员编码]
	private String loginPersonName;			//登录[人员姓名]
	private String loginPersonTypeId;		//登录[人员类别ID]
	private String loginPersonTypeCode;		//登录[人员类别编码]
	private String loginPersonTypeName;		//登录[人员类别姓名]
	/*private User user;
	private Set<Role> roles;
	private Person person;*/
	
	//其他
	private boolean sysVariableInited = false;
	private String dataBaseName;    //登录数据库名称
	private String currentRootMenu ;//当前子系统
	private String currentRootMenuCode ;//当前子系统代码 hr、gz
	private String currentOrgModel;		//当前单位模式
	private String currentCopyModel;    //当前账套模式
	private String subSysMainPage = "";//当前子系统主页URL
	
	
	//权限
	//private Map<String, String[]> userMenuPrivileges; //用户菜单权限
	private TreeSet<Menu> userSysRootMenus;
	private TreeSet<Menu> userBusRootMenus;
	private Map<String ,TreeSet<Menu>> userMenuPrivileges; //用户菜单权限
	private Map<String, String[]> userButtonPrivileges; //用户按钮权限
	private Map<String, Set<UserDataPrivilegeBean>> userDataPrivilegeMap; //用户数据权限
	//private Map<String, String> systemModelStatus;//子系统结账状态  key：hr_月_orgCode_copyCode_201501 value:1 另外还存储每个子系统的工作期间 key：hr_月_orgCode_copyCode value：201501
	private Map<String, Object> sysVars;	//系统变量 %HSQJ% 
	private Map<String ,Map<String,String>>  globalParamMap;//按子系统存储的参数map，如：hr:Map<key,value>,如配置时不选子系统，则为全局参数：all
	
	

	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public String getPeriodBeginDateStr() {
		if(periodBeginDate!=null){
			periodBeginDateStr = DateUtil.convertDateToString(periodBeginDate);
		}
		return periodBeginDateStr;
	}
	public void setPeriodBeginDateStr(String periodBeginDateStr) {
		this.periodBeginDateStr = periodBeginDateStr;
	}
	public String getPeriodEndDateStr() {
		if(periodEndDate!=null){
			periodEndDateStr = DateUtil.convertDateToString(periodEndDate);
		}
		return periodEndDateStr;
	}
	public void setPeriodEndDateStr(String periodEndDateStr) {
		this.periodEndDateStr = periodEndDateStr;
	}
	public Date getPeriodYearBeginDate() {
		return periodYearBeginDate;
	}
	public void setPeriodYearBeginDate(Date periodYearBeginDate) {
		this.periodYearBeginDate = periodYearBeginDate;
	}
	public String getPeriodYearBeginDateStr() {
		if(periodYearBeginDate!=null){
			periodYearBeginDateStr = DateUtil.convertDateToString(periodYearBeginDate);
		}
		return periodYearBeginDateStr;
	}
	public void setPeriodYearBeginDateStr(String periodYearBeginDateStr) {
		this.periodYearBeginDateStr = periodYearBeginDateStr;
	}
	public Date getPeriodYearEndDate() {
		return periodYearEndDate;
	}
	public void setPeriodYearEndDate(Date periodYearEndDate) {
		this.periodYearEndDate = periodYearEndDate;
	}
	public String getPeriodYearEndDateStr() {
		if(periodYearEndDate!=null){
			periodYearEndDateStr = DateUtil.convertDateToString(periodYearEndDate);
		}
		return periodYearEndDateStr;
	}
	public void setPeriodYearEndDateStr(String periodYearEndDateStr) {
		this.periodYearEndDateStr = periodYearEndDateStr;
	}
	
	public String getMonthBeginDateStr() {
		if(monthBeginDate!=null){
			monthBeginDateStr = DateUtil.convertDateToString(monthBeginDate);
		}
		return monthBeginDateStr;
	}
	public void setMonthBeginDateStr(String monthBeginDateStr) {
		this.monthBeginDateStr = monthBeginDateStr;
	}
	public String getMonthEndDateStr() {
		if(monthEndDate!=null){
			monthEndDateStr = DateUtil.convertDateToString(monthEndDate);
		}
		return monthEndDateStr;
	}
	public void setMonthEndDateStr(String monthEndDateStr) {
		this.monthEndDateStr = monthEndDateStr;
	}
	public String getYearBeginDateStr() {
		if(yearBeginDate!=null){
			yearBeginDateStr = DateUtil.convertDateToString(yearBeginDate);
		}
		return yearBeginDateStr;
	}
	public void setYearBeginDateStr(String yearBeginDateStr) {
		this.yearBeginDateStr = yearBeginDateStr;
	}
	public String getYearEndDateStr() {
		if(yearEndDate!=null){
			yearEndDateStr = DateUtil.convertDateToString(yearEndDate);
		}
		return yearEndDateStr;
	}
	public void setYearEndDateStr(String yearEndDateStr) {
		this.yearEndDateStr = yearEndDateStr;
	}

	public String getSysYear() {
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(Calendar.YEAR);
		return year.toString();
	}
	public void setSysYear(String sysYear) {
		this.sysYear = sysYear;
	}

	public String getSysMonth() {
		Calendar calendar = Calendar.getInstance();
		Integer month = calendar.get(Calendar.MONTH) + 1;
		return month.toString();
	}
	public void setSysMonth(String sysMonth) {
		this.sysMonth = sysMonth;
	}

	public String getSysQuarter() {
		int month = Integer.parseInt(this.getSysMonth());
		if(month==1||month==2||month==3){
			sysQuarter = "1";
		}else if(month==4||month==5||month==6){
			sysQuarter = "2";
		}else if(month==7||month==8||month==9){
			sysQuarter = "3";
		}else if(month==10||month==11||month==12){
			sysQuarter = "4";
		}
		return sysQuarter;
	}
	public void setSysQuarter(String sysQuarter) {
		this.sysQuarter = sysQuarter;
	}

	public Date getYearBeginDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, this.getYear());
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		yearBeginDate = calendar.getTime();
		return yearBeginDate;
	}
	public void setYearBeginDate(Date yearBeginDate) {
		this.yearBeginDate = yearBeginDate;
	}

	public Date getYearEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, this.getYear() + 1);
		calendar.set(Calendar.DAY_OF_YEAR, 0);
		yearEndDate = calendar.getTime();
		return yearEndDate;
	}
	public void setYearEndDate(Date yearEndDate) {
		this.yearEndDate = yearEndDate;
	}
	public Date getMonthBeginDate() {
		return monthBeginDate;
	}
	public void setMonthBeginDate(Date monthBeginDate) {
		this.monthBeginDate = monthBeginDate;
	}
	public Date getMonthEndDate() {
		return monthEndDate;
	}
	public void setMonthEndDate(Date monthEndDate) {
		this.monthEndDate = monthEndDate;
	}
	
	public String getPeriodYear() {
		return periodYear;
	}
	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}
	
	public Date getSysDate() {
		return sysDate;
	}
	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}
	
	public String getSysDateStr() {
		if(sysDate!=null){
			sysDateStr = DateUtil.convertDateToString(sysDate);
		}
		return sysDateStr;
	}
	public void setSysDateStr(String sysDateStr) {
		this.sysDateStr = sysDateStr;
	}
	public String getPeriodQuarter() {
		int month = getMonth();
		if(month==1||month==2||month==3){
			periodQuarter = "1";
		}else if(month==4||month==5||month==6){
			periodQuarter = "2";
		}else if(month==7||month==8||month==9){
			periodQuarter = "3";
		}else if(month==10||month==11||month==12){
			periodQuarter = "4";
		}
		return periodQuarter;
	}
	public void setPeriodQuarter(String periodQuarter) {
		this.periodQuarter = periodQuarter;
	}
	public boolean isSysVariableInited() {
		return sysVariableInited;
	}
	public void setSysVariableInited(boolean sysVariableInited) {
		this.sysVariableInited = sysVariableInited;
	}
	/*public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}*/
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	public String getPeriodMonth() {
		return periodMonth;
	}
	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}
	public String getBusinessDateStr() {
		return businessDateStr;
	}
	public void setBusinessDateStr(String businessDateStr) {
		this.businessDateStr = businessDateStr;
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
	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}
	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}
	public Date getPeriodEndDate() {
		return periodEndDate;
	}
	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}
	
	public int getYear() {
		int year = 0;
		if(periodYear!=null&&!"".equals(periodYear)){
			year = Integer.parseInt(periodYear);
		}
		return year;
	}
	public int getMonth() {
		int month = 0;
		if(periodYear!=null&&!"".equals(periodYear)){
			month = Integer.parseInt(periodMonth.replace(periodYear, ""));
		}
		return month;
	}
	
	
	public String getCurrentRootMenuCode() {
		return currentRootMenuCode;
	}
	public void setCurrentRootMenuCode(String currentRootMenuCode) {
		this.currentRootMenuCode = currentRootMenuCode;
	}
	
	public Map<String, Set<UserDataPrivilegeBean>> getUserDataPrivilegeMap() {
		return userDataPrivilegeMap;
	}
	public void setUserDataPrivilegeMap(
			Map<String, Set<UserDataPrivilegeBean>> userDataPrivilegeMap) {
		this.userDataPrivilegeMap = userDataPrivilegeMap;
	}
	public Map<String, Object> getSysVars() {
		return sysVars;
	}
	public void setSysVars(Map<String, Object> sysVars) {
		this.sysVars = sysVars;
	}
	
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	public String getLoginPersonId() {
		return loginPersonId;
	}
	public void setLoginPersonId(String loginPersonId) {
		this.loginPersonId = loginPersonId;
	}
	public String getLoginPersonName() {
		return loginPersonName;
	}
	public void setLoginPersonName(String loginPersonName) {
		this.loginPersonName = loginPersonName;
	}
	public String getLoginDeptId() {
		return loginDeptId;
	}
	public void setLoginDeptId(String loginDeptId) {
		this.loginDeptId = loginDeptId;
	}
	public String getLoginDeptCode() {
		return loginDeptCode;
	}
	public void setLoginDeptCode(String loginDeptCode) {
		this.loginDeptCode = loginDeptCode;
	}
	public String getLoginDeptName() {
		return loginDeptName;
	}
	public void setLoginDeptName(String loginDeptName) {
		this.loginDeptName = loginDeptName;
	}
	public String getLoginPersonCode() {
		return loginPersonCode;
	}
	public void setLoginPersonCode(String loginPersonCode) {
		this.loginPersonCode = loginPersonCode;
	}
	public String getLoginPersonTypeId() {
		return loginPersonTypeId;
	}
	public void setLoginPersonTypeId(String loginPersonTypeId) {
		this.loginPersonTypeId = loginPersonTypeId;
	}
	public String getLoginPersonTypeCode() {
		return loginPersonTypeCode;
	}
	public void setLoginPersonTypeCode(String loginPersonTypeCode) {
		this.loginPersonTypeCode = loginPersonTypeCode;
	}
	public String getLoginPersonTypeName() {
		return loginPersonTypeName;
	}
	public void setLoginPersonTypeName(String loginPersonTypeName) {
		this.loginPersonTypeName = loginPersonTypeName;
	}
	public TreeSet<Menu> getUserSysRootMenus() {
		return userSysRootMenus;
	}
	public void setUserSysRootMenus(TreeSet<Menu> userSysRootMenus) {
		this.userSysRootMenus = userSysRootMenus;
	}
	public TreeSet<Menu> getUserBusRootMenus() {
		return userBusRootMenus;
	}
	public void setUserBusRootMenus(TreeSet<Menu> userBusRootMenus) {
		this.userBusRootMenus = userBusRootMenus;
	}
	public Map<String ,TreeSet<Menu>> getUserMenuPrivileges() {
		return userMenuPrivileges;
	}
	public void setUserMenuPrivileges(Map<String ,TreeSet<Menu>> userMenuPrivileges) {
		this.userMenuPrivileges = userMenuPrivileges;
	}
	public Map<String, String[]> getUserButtonPrivileges() {
		return userButtonPrivileges;
	}
	public void setUserButtonPrivileges(Map<String, String[]> userButtonPrivileges) {
		this.userButtonPrivileges = userButtonPrivileges;
	}
	public Map<String, Map<String, String>> getGlobalParamMap() {
		return globalParamMap;
	}
	public void setGlobalParamMap(Map<String, Map<String, String>> globalParamMap) {
		this.globalParamMap = globalParamMap;
	}
	public String getCurrentOrgModel() {
		return currentOrgModel;
	}
	public void setCurrentOrgModel(String currentOrgModel) {
		this.currentOrgModel = currentOrgModel;
	}
	public String getCurrentCopyModel() {
		return currentCopyModel;
	}
	public void setCurrentCopyModel(String currentCopyModel) {
		this.currentCopyModel = currentCopyModel;
	}
	public String getLoginOrgId() {
		return loginOrgId;
	}
	public void setLoginOrgId(String loginOrgId) {
		this.loginOrgId = loginOrgId;
	}
	public String getLoginOrgName() {
		return loginOrgName;
	}
	public void setLoginOrgName(String loginOrgName) {
		this.loginOrgName = loginOrgName;
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
		result = prime
				* result
				+ ((currentRootMenuCode == null) ? 0 : currentRootMenuCode
						.hashCode());
		result = prime * result
				+ ((dataBaseName == null) ? 0 : dataBaseName.hashCode());
		result = prime * result
				+ ((loginPersonId == null) ? 0 : loginPersonId.hashCode());
		result = prime * result
				+ ((loginPersonName == null) ? 0 : loginPersonName.hashCode());
		result = prime * result
				+ ((loginUserId == null) ? 0 : loginUserId.hashCode());
		result = prime * result
				+ ((loginUserName == null) ? 0 : loginUserName.hashCode());
		result = prime * result
				+ ((monthBeginDate == null) ? 0 : monthBeginDate.hashCode());
		result = prime * result
				+ ((monthEndDate == null) ? 0 : monthEndDate.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result + ((periodMonth == null) ? 0 : periodMonth.hashCode());
		result = prime * result
				+ ((periodBeginDate == null) ? 0 : periodBeginDate.hashCode());
		result = prime * result
				+ ((periodEndDate == null) ? 0 : periodEndDate.hashCode());
		result = prime * result
				+ ((periodPlanCode == null) ? 0 : periodPlanCode.hashCode());
		result = prime * result
				+ ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result
				+ ((subSysMainPage == null) ? 0 : subSysMainPage.hashCode());
		result = prime * result + ((sysDate == null) ? 0 : sysDate.hashCode());
		result = prime * result
				+ ((sysDateStr == null) ? 0 : sysDateStr.hashCode());
		result = prime * result + ((sysVars == null) ? 0 : sysVars.hashCode());
		result = prime
				* result
				+ ((userButtonPrivileges == null) ? 0 : userButtonPrivileges
						.hashCode());
		result = prime
				* result
				+ ((userDataPrivilegeMap == null) ? 0 : userDataPrivilegeMap
						.hashCode());
		result = prime
				* result
				+ ((userMenuPrivileges == null) ? 0 : userMenuPrivileges
						.hashCode());
		result = prime * result
				+ ((periodQuarter == null) ? 0 : periodQuarter.hashCode());
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
		UserContext other = (UserContext) obj;
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
		if (currentRootMenuCode == null) {
			if (other.currentRootMenuCode != null)
				return false;
		} else if (!currentRootMenuCode.equals(other.currentRootMenuCode))
			return false;
		if (dataBaseName == null) {
			if (other.dataBaseName != null)
				return false;
		} else if (!dataBaseName.equals(other.dataBaseName))
			return false;
		if (loginPersonId == null) {
			if (other.loginPersonId != null)
				return false;
		} else if (!loginPersonId.equals(other.loginPersonId))
			return false;
		if (loginPersonName == null) {
			if (other.loginPersonName != null)
				return false;
		} else if (!loginPersonName.equals(other.loginPersonName))
			return false;
		if (loginUserId == null) {
			if (other.loginUserId != null)
				return false;
		} else if (!loginUserId.equals(other.loginUserId))
			return false;
		if (loginUserName == null) {
			if (other.loginUserName != null)
				return false;
		} else if (!loginUserName.equals(other.loginUserName))
			return false;
		if (monthBeginDate == null) {
			if (other.monthBeginDate != null)
				return false;
		} else if (!monthBeginDate.equals(other.monthBeginDate))
			return false;
		if (monthEndDate == null) {
			if (other.monthEndDate != null)
				return false;
		} else if (!monthEndDate.equals(other.monthEndDate))
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
		if (periodMonth == null) {
			if (other.periodMonth != null)
				return false;
		} else if (!periodMonth.equals(other.periodMonth))
			return false;
		if (periodBeginDate == null) {
			if (other.periodBeginDate != null)
				return false;
		} else if (!periodBeginDate.equals(other.periodBeginDate))
			return false;
		if (periodEndDate == null) {
			if (other.periodEndDate != null)
				return false;
		} else if (!periodEndDate.equals(other.periodEndDate))
			return false;
		if (periodPlanCode == null) {
			if (other.periodPlanCode != null)
				return false;
		} else if (!periodPlanCode.equals(other.periodPlanCode))
			return false;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (subSysMainPage == null) {
			if (other.subSysMainPage != null)
				return false;
		} else if (!subSysMainPage.equals(other.subSysMainPage))
			return false;
		if (sysDate == null) {
			if (other.sysDate != null)
				return false;
		} else if (!sysDate.equals(other.sysDate))
			return false;
		if (sysDateStr == null) {
			if (other.sysDateStr != null)
				return false;
		} else if (!sysDateStr.equals(other.sysDateStr))
			return false;
		if (sysVars == null) {
			if (other.sysVars != null)
				return false;
		} else if (!sysVars.equals(other.sysVars))
			return false;
		if (userButtonPrivileges == null) {
			if (other.userButtonPrivileges != null)
				return false;
		} else if (!userButtonPrivileges.equals(other.userButtonPrivileges))
			return false;
		if (userDataPrivilegeMap == null) {
			if (other.userDataPrivilegeMap != null)
				return false;
		} else if (!userDataPrivilegeMap.equals(other.userDataPrivilegeMap))
			return false;
		if (userMenuPrivileges == null) {
			if (other.userMenuPrivileges != null)
				return false;
		} else if (!userMenuPrivileges.equals(other.userMenuPrivileges))
			return false;
		if (periodQuarter == null) {
			if (other.periodQuarter != null)
				return false;
		} else if (!periodQuarter.equals(other.periodQuarter))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserContext [orgCode=" + orgCode + ", orgName=" + orgName
				+ ", copyCode=" + copyCode + ", copyName=" + copyName
				+ ", periodPlanCode=" + periodPlanCode + ", periodYear="
				+ periodYear + ", period=" + periodMonth + ", yearQuarter="
				+ periodQuarter + ", businessDate=" + businessDate + ", sysDate="
				+ sysDate + ", sysDateStr=" + sysDateStr + ", dataBaseName="
				+ dataBaseName + ", currentRootMenu=" + currentRootMenu
				+ ", currentRootMenuCode=" + currentRootMenuCode
				+ ", subSysMainPage=" + subSysMainPage + ", periodBeginDate="
				+ periodBeginDate + ", periodEndDate=" + periodEndDate
				+ ", monthBeginDate=" + monthBeginDate + ", monthEndDate="
				+ monthEndDate 
				+ ", loginUserId=" + loginUserId + ", loginUserName="
				+ loginUserName + ", loginPersonId=" + loginPersonId
				+ ", loginPersonName=" + loginPersonName
				+ ", userMenuPrivileges=" + userMenuPrivileges
				+ ", userButtonPrivileges=" + userButtonPrivileges
				+ ", userDataPrivileges=" + userDataPrivilegeMap
				+ ", sysVars="+ sysVars + "]";
	}
	
}

