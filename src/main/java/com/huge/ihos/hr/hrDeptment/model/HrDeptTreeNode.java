package com.huge.ihos.hr.hrDeptment.model;


import com.huge.util.OtherUtil;
import com.huge.webapp.ztree.ZTreeSimpleNode;

public class HrDeptTreeNode extends ZTreeSimpleNode implements Cloneable{
	private String nameWithoutPerson;
	private String postCount = "0";
	private String code;
	private String deptCode;
	private String orgCode;
	private String personCount = "0";
	private String snapCode;
	private String state;
	private Integer displayOrder;
	private String personCountD = "0";
	private String postCountD = "0";
	private String personCountP = "0";
	private String personCountDP = "0";
	private String nameType;
	private String sn;
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPersonCountP() {
		return personCountP;
	}

	public void setPersonCountP(String personCountP) {
		this.personCountP = personCountP;
	}

	public String getPersonCountDP() {
		return personCountDP;
	}

	public void setPersonCountDP(String personCountDP) {
		this.personCountDP = personCountDP;
	}

	public String getPostCountD() {
		return postCountD;
	}

	public void setPostCountD(String postCountD) {
		this.postCountD = postCountD;
	}

	public String getPersonCountD() {
		return personCountD;
	}

	public void setPersonCountD(String personCountD) {
		this.personCountD = personCountD;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	public String getPersonCount() {
		return personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPostCount() {
		return postCount;
	}

	public void setPostCount(String postCount) {
		this.postCount = postCount;
	}

	public String getNameWithoutPerson() {
		return nameWithoutPerson;
	}

	public void setNameWithoutPerson(String nameWithoutPerson) {
		this.nameWithoutPerson = nameWithoutPerson;
	}
	
	public String getNameType() {
		return nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}
	
	public HrDeptTreeNode(){
		
	}
	public HrDeptTreeNode(HrDepartmentCurrent hrDepartment,String contextPath){
		String iconPath = contextPath + "/scripts/zTree/css/zTreeStyle/img/diy/";
		this.setCode(hrDepartment.getDeptCode());
		this.setId(hrDepartment.getDepartmentId());
		this.setSnapCode(hrDepartment.getSnapCode());
		this.setState(hrDepartment.getState()>=3?"real":"temp");
		this.setName(hrDepartment.getName());
		this.setNameWithoutPerson(hrDepartment.getName());
		String pId=null;
		if(OtherUtil.measureNull(hrDepartment.getParentDept())||OtherUtil.measureNull(hrDepartment.getParentDept().getDepartmentId())){
			pId = hrDepartment.getOrgCode();
		}else{
			pId = hrDepartment.getParentDept().getDepartmentId();
		}
		this.setpId(pId);
		this.setIsParent(!hrDepartment.getLeaf());
		this.setSubSysTem("DEPT");
		this.setActionUrl(hrDepartment.getDisabled()?"1":"0");
		this.setIcon(iconPath+"dept.gif");
		this.setPersonCount(""+hrDepartment.getPersonCount());
		this.setPersonCountP(""+hrDepartment.getPersonCountWithoutDisabled());
		if(hrDepartment.getDisabled()){
			this.setPersonCountD("0");
			this.setPersonCountDP("0");
		}else{
			this.setPersonCountD(""+hrDepartment.getPersonCount());
			this.setPersonCountDP(""+hrDepartment.getPersonCountWithoutDisabled());
		}
		this.setDisplayOrder(3);
	}
	@Override
	public HrDeptTreeNode clone() {
		HrDeptTreeNode o = null;
		try {
			o = (HrDeptTreeNode) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}	
}
