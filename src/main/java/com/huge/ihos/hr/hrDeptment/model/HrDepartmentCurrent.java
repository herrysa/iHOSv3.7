package com.huge.ihos.hr.hrDeptment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.model.BaseObject;

/**
 * 抽取HrDepartmentSnap的最新纪录形成的视图
 * 
 * @author Gaozhengyang
 * @date 2014年10月17日
 */
@Entity
@Table(name = "v_hr_department_current")
public class HrDepartmentCurrent extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8176864934889858641L;
	private String departmentId; // ID
	// String
	private String name; // 科室名称
	// req
	private String deptCode; // 科室编码
	// req
	private String shortnName; // 科室简称
	
	private String internalCode; // 科室内部码
	
	private String outin; // 住院门诊
	// req
	private String deptClass; // 科室类别

	private String subClass; // 专业系别

	private String phone; // 电话

	private String contact; // 联系人

	private String contactPhone; // 联系电话

	private String manager; // 科室主管姓名

	private String note; // 备注

	private Boolean leaf = true; // 是否为叶子

	private Integer clevel = 1; // 层次

	private HrDepartmentCurrent parentDept;
	private HrDepartmentHis parentDeptHis;
	private String parentDeptSnapCode;

	private String parentDeptName;

	private Boolean disabled = false; // 是否可用

	private Date invalidDate; // 失效日期

	private String cnCode;

	private Boolean jjLeaf = true; // 是否为叶子(绩效)

	private HrDepartmentCurrent jjDept;// 奖金
	private HrDepartmentHis jjDeptHis;
	private String jjDeptSnapCode;

	private Boolean ysLeaf = true; // 是否为叶子（预算）

	private HrDepartmentCurrent ysDept;// 预算
	private HrDepartmentHis ysDeptHis;
	private String ysDeptSnapCode;

	private KhDeptType jjDeptType;

	private String orgCode;// 单位
	private Boolean ysPurchasingDepartment = false; // 是否采购科室
	
	private String branchCode; //院区

	private Branch branch;

	private String dgroup;

	private Boolean cbLeaf = false; // 是否为叶子(成本)

	private Boolean zcLeaf = false; // 是否为叶子(诊次)

	private Boolean crLeaf = false; // 是否为叶子(床日)

	private Boolean xmLeaf = false; // 是否为叶子(项目)

	private HrDepartmentCurrent yjDept;
	
	private HrOrg hrOrg;
	private HrOrgHis hrOrgHis;
	private String orgSnapCode;
	
//	private Org org;
	private String snapCode;
	private int personCount = 0;
	private int personCountWithoutDisabled = 0;
	private int zbPersonCountNotDisabled = 0;
	
	private Integer planCount = 0;
	private Integer realCount = 0;//实有人数
	private Integer realCountAll = 0;//实有人数（带停用）
	private Integer realZbCount = 0;//实有在编人数
	private Integer diffCount = 0;//缺编人数
	private Integer overCount = 0;//超编人数

	
	private int postCount = 0;
	private int postCountD = 0;
	private int personCountD = 0;
	private int personCountP = 0;
	private int personCountDP = 0;
	
	//lock
	private String lockState;//锁状态
	
	@Transient
	public int getPersonCountDP() {
		return personCountDP;
	}

	public void setPersonCountDP(int personCountDP) {
		this.personCountDP = personCountDP;
	}

	@Transient
	public int getPersonCountP() {
		return personCountP;
	}

	public void setPersonCountP(int personCountP) {
		this.personCountP = personCountP;
	}

	@Transient
	public int getPersonCountD() {
		return personCountD;
	}

	public void setPersonCountD(int personCountD) {
		this.personCountD = personCountD;
	}

	@Transient
	public int getPostCountD() {
		return postCountD;
	}

	public void setPostCountD(int postCountD) {
		this.postCountD = postCountD;
	}

	private Integer state;
	
	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Formula("(select count(*) from hr_post as post where post.deptId = deptId)")
	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}
	
	@Transient
	//@Formula("(select count(*) from v_hr_person_current as cp where cp.dept_id = deptId and cp.disabled = 0)")
	public int getPersonCountWithoutDisabled() {
		return personCountWithoutDisabled;
	}

	public void setPersonCountWithoutDisabled(int personCountWithoutDisabled) {
		this.personCountWithoutDisabled = personCountWithoutDisabled;
	}
	
	@Transient
	//@Formula("(select count(*) from v_hr_person_current as cp where cp.dept_id = deptId and cp.empType=(select tp.id from t_personType tp where tp.code='PT0101' ) and cp.disabled = 0)")
	public int getZbPersonCountNotDisabled() {
		return zbPersonCountNotDisabled;
	}

	public void setZbPersonCountNotDisabled(int zbPersonCountNotDisabled) {
		this.zbPersonCountNotDisabled = zbPersonCountNotDisabled;
	}

	@Transient
	//@Formula("(select count(*) from v_hr_person_current as cp where cp.dept_id = deptId)")
	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}
	
	@Column(name = "plan_count", nullable = true)
	public Integer getPlanCount() {
		if(planCount==null){
			planCount = 0;
		}
		return planCount;
	}

	public void setPlanCount(Integer planCount) {
		this.planCount = planCount;
	}

	@Column(name = "real_count", nullable = true)
	public Integer getRealCount() {
		if(realCount==null){
			realCount = 0;
		}
		return realCount;
	}

	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}

	@Column(name = "real_CountAll", nullable = true)
	public Integer getRealCountAll() {
		if(realCountAll==null){
			realCountAll =  0;
		}
		return realCountAll;
	}

	public void setRealCountAll(Integer realCountAll) {
		if(realCountAll==null){
			realCountAll =  0;
		}
		this.realCountAll = realCountAll;
	}
	
	@Column(name = "real_zb_count", nullable = true)
	public Integer getRealZbCount() {
		if(realZbCount==null){
			realZbCount = 0;
		}
		return realZbCount;
	}

	public void setRealZbCount(Integer realZbCount) {
		this.realZbCount = realZbCount;
	}

	@Column(name = "diff_count", nullable = true)
	public Integer getDiffCount() {
		if(diffCount==null){
			diffCount = 0;
		}
		return diffCount;
	}

	public void setDiffCount(Integer diffCount) {
		this.diffCount = diffCount;
	}
	
	
	@Column(name = "over_count", nullable = true)
	public Integer getOverCount() {
		if(overCount==null){
			overCount = 0;
		}
		return overCount;
	}

	public void setOverCount(Integer overCount) {
		this.overCount = overCount;
	}

	@Transient
	public String getDeptCodeName() {
		return this.deptCode+"("+this.name+")";
	}

	@Id
	@Column(name = "deptId", length = 50, nullable = false)
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "snapCode", length = 14, nullable = false)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentDept_id", nullable = true)
	public HrDepartmentCurrent getParentDept() {
		return parentDept;
	}

	public void setParentDept(HrDepartmentCurrent parentDept) {
		this.parentDept = parentDept;
	}
	
	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "parentDept_id", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "parentDeptSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getParentDeptHis() {
		return parentDeptHis;
	}

	public void setParentDeptHis(HrDepartmentHis parentDeptHis) {
		this.parentDeptHis = parentDeptHis;
	}

	@Column(name = "parentDeptSnapCode", nullable = true, length = 14)
	public String getParentDeptSnapCode() {
		return parentDeptSnapCode;
	}

	public void setParentDeptSnapCode(String parentDeptSnapCode) {
		this.parentDeptSnapCode = parentDeptSnapCode;
	}

	@Column(name = "cnCode", length = 50)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@Column(name = "name", unique = true, nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "deptCode", nullable = false, length = 20)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "shortnName", nullable = true, length = 30)
	public String getShortnName() {
		return shortnName;
	}

	public void setShortnName(String shortnName) {
		this.shortnName = shortnName;
	}

	@Column(name = "internalCode", nullable = true, length = 20)
	public String getInternalCode() {
		return internalCode;
	}

	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
	}

	@Column(name = "outin", nullable = true, length = 50)
	public String getOutin() {
		return outin;
	}

	public void setOutin(String outin) {
		this.outin = outin;
	}

	@Column(name = "deptType", nullable = false, length = 50)
	public String getDeptClass() {
		return deptClass;
	}

	public void setDeptClass(String deptClass) {
		this.deptClass = deptClass;
	}

	@Column(name = "subClass", nullable = true, length = 50)
	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	@Column(name = "phone", nullable = true, length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "contact", nullable = true, length = 20)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "contactPhone", nullable = true, length = 20)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name = "manager", nullable = true, length = 20)
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Column(name = "note", nullable = true, length = 120)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "leaf", nullable = true)
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "clevel", nullable = true)
	public Integer getClevel() {
		return clevel;
	}

	public void setClevel(Integer clevel) {
		this.clevel = clevel;
	}

	@Column(name = "disabled", nullable = true)
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "invalidDate", nullable = true)
	public Date getInvalidDate() {
		return invalidDate;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	@Column(name = "jjLeaf", nullable = true)
	public Boolean getJjLeaf() {
		return jjLeaf;
	}

	public void setJjLeaf(Boolean jjLeaf) {
		this.jjLeaf = jjLeaf;
	}

	@Column(name = "ysLeaf", nullable = true)
	public Boolean getYsLeaf() {
		return ysLeaf;
	}

	public void setYsLeaf(Boolean ysLeaf) {
		this.ysLeaf = ysLeaf;
	}

	@Column(name = "purchasingDepartment", nullable = true)
	public Boolean getYsPurchasingDepartment() {
		return ysPurchasingDepartment;
	}

	public void setYsPurchasingDepartment(Boolean ysPurchasingDepartment) {
		this.ysPurchasingDepartment = ysPurchasingDepartment;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jjDeptId", nullable = true)
	public HrDepartmentCurrent getJjDept() {
		return jjDept;
	}

	public void setJjDept(HrDepartmentCurrent jjDept) {
		this.jjDept = jjDept;
	}
	
	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "jjDeptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "jjDeptSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getJjDeptHis() {
		return jjDeptHis;
	}

	public void setJjDeptHis(HrDepartmentHis jjDeptHis) {
		this.jjDeptHis = jjDeptHis;
	}
	@Column(name = "jjDeptSnapCode", nullable = true, length = 14)
	public String getJjDeptSnapCode() {
		return jjDeptSnapCode;
	}

	public void setJjDeptSnapCode(String jjDeptSnapCode) {
		this.jjDeptSnapCode = jjDeptSnapCode;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ysDeptId", nullable = true)
	public HrDepartmentCurrent getYsDept() {
		return ysDept;
	}

	public void setYsDept(HrDepartmentCurrent ysDept) {
		this.ysDept = ysDept;
	}
	
	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "ysDeptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "ysDeptSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getYsDeptHis() {
		return ysDeptHis;
	}

	public void setYsDeptHis(HrDepartmentHis ysDeptHis) {
		this.ysDeptHis = ysDeptHis;
	}
	
	@Column(name = "ysDeptSnapCode", nullable = true, length = 14)
	public String getYsDeptSnapCode() {
		return ysDeptSnapCode;
	}

	public void setYsDeptSnapCode(String ysDeptSnapCode) {
		this.ysDeptSnapCode = ysDeptSnapCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jjdepttype", nullable = true)
	public KhDeptType getJjDeptType() {
		return jjDeptType;
	}

	public void setJjDeptType(KhDeptType jjDeptType) {
		this.jjDeptType = jjDeptType;
	}

	@Transient
	public String getParentDeptName() {
		if (parentDept != null) {
			parentDeptName = parentDept.getName();
		} else {
			parentDeptName = "";
		}
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

	@Column(name = "orgCode", nullable = true, length = 20)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgCode", nullable = false, insertable = false, updatable = false)
	public HrOrg getHrOrg() {
		return hrOrg;
	}

	public void setHrOrg(HrOrg hrOrg) {
		this.hrOrg = hrOrg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "orgCode", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "orgSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrOrgHis getHrOrgHis() {
		return hrOrgHis;
	}

	public void setHrOrgHis(HrOrgHis hrOrgHis) {
		this.hrOrgHis = hrOrgHis;
	}
	
	@Column(name = "orgSnapCode", nullable = true, length = 14)
	public String getOrgSnapCode() {
		return orgSnapCode;
	}

	public void setOrgSnapCode(String orgSnapCode) {
		this.orgSnapCode = orgSnapCode;
	}
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgCode", nullable = false, insertable = false, updatable = false)
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}*/
	@Column(name = "lock_state", nullable = true, length = 200)
	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}
	
	@Column(name="branchCode",nullable=true,length=30)
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="branchCode",nullable=true,insertable=false,updatable=false)
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	@Column(name="dGroup",length=30,nullable=true)
	public String getDgroup() {
		return dgroup;
	}

	public void setDgroup(String dgroup) {
		this.dgroup = dgroup;
	}
	@Column( name = "cbleaf", nullable = true )
	public Boolean getCbLeaf() {
		return cbLeaf;
	}

	public void setCbLeaf(Boolean cbLeaf) {
		this.cbLeaf = cbLeaf;
	}
	 @Column( name = "zcleaf", nullable = true )
	public Boolean getZcLeaf() {
		return zcLeaf;
	}

	public void setZcLeaf(Boolean zcLeaf) {
		this.zcLeaf = zcLeaf;
	}
	@Column( name = "crleaf", nullable = true )
	public Boolean getCrLeaf() {
		return crLeaf;
	}

	public void setCrLeaf(Boolean crLeaf) {
		this.crLeaf = crLeaf;
	}
	@Column( name = "xmleaf", nullable = true )
	public Boolean getXmLeaf() {
		return xmLeaf;
	}

	public void setXmLeaf(Boolean xmLeaf) {
		this.xmLeaf = xmLeaf;
	}
	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ysDeptId", nullable = true,insertable=false,updatable=false)
	public HrDepartmentCurrent getYjDept() {
		return yjDept;
	}

	public void setYjDept(HrDepartmentCurrent yjDept) {
		this.yjDept = yjDept;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clevel == null) ? 0 : clevel.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result
				+ ((contactPhone == null) ? 0 : contactPhone.hashCode());
		result = prime * result
				+ ((departmentId == null) ? 0 : departmentId.hashCode());
		result = prime * result
				+ ((deptClass == null) ? 0 : deptClass.hashCode());
		result = prime * result
				+ ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((internalCode == null) ? 0 : internalCode.hashCode());
		result = prime * result
				+ ((invalidDate == null) ? 0 : invalidDate.hashCode());
		result = prime * result + ((jjDept == null) ? 0 : jjDept.hashCode());
		result = prime * result
				+ ((jjDeptHis == null) ? 0 : jjDeptHis.hashCode());
		result = prime * result
				+ ((jjDeptSnapCode == null) ? 0 : jjDeptSnapCode.hashCode());
		result = prime * result
				+ ((jjDeptType == null) ? 0 : jjDeptType.hashCode());
		result = prime * result + ((jjLeaf == null) ? 0 : jjLeaf.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
//		result = prime * result + ((org == null) ? 0 : org.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((outin == null) ? 0 : outin.hashCode());
		result = prime * result
				+ ((parentDept == null) ? 0 : parentDept.hashCode());
		result = prime * result
				+ ((parentDeptHis == null) ? 0 : parentDeptHis.hashCode());
		result = prime * result
				+ ((parentDeptName == null) ? 0 : parentDeptName.hashCode());
		result = prime
				* result
				+ ((parentDeptSnapCode == null) ? 0 : parentDeptSnapCode
						.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((shortnName == null) ? 0 : shortnName.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
		result = prime * result
				+ ((subClass == null) ? 0 : subClass.hashCode());
		result = prime * result + ((ysDept == null) ? 0 : ysDept.hashCode());
		result = prime * result
				+ ((ysDeptHis == null) ? 0 : ysDeptHis.hashCode());
		result = prime * result
				+ ((ysDeptSnapCode == null) ? 0 : ysDeptSnapCode.hashCode());
		result = prime * result + ((ysLeaf == null) ? 0 : ysLeaf.hashCode());
		result = prime
				* result
				+ ((ysPurchasingDepartment == null) ? 0
						: ysPurchasingDepartment.hashCode());
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
		HrDepartmentCurrent other = (HrDepartmentCurrent) obj;
		if (clevel == null) {
			if (other.clevel != null)
				return false;
		} else if (!clevel.equals(other.clevel))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (contactPhone == null) {
			if (other.contactPhone != null)
				return false;
		} else if (!contactPhone.equals(other.contactPhone))
			return false;
		if (departmentId == null) {
			if (other.departmentId != null)
				return false;
		} else if (!departmentId.equals(other.departmentId))
			return false;
		if (deptClass == null) {
			if (other.deptClass != null)
				return false;
		} else if (!deptClass.equals(other.deptClass))
			return false;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (internalCode == null) {
			if (other.internalCode != null)
				return false;
		} else if (!internalCode.equals(other.internalCode))
			return false;
		if (invalidDate == null) {
			if (other.invalidDate != null)
				return false;
		} else if (!invalidDate.equals(other.invalidDate))
			return false;
		if (jjDept == null) {
			if (other.jjDept != null)
				return false;
		} else if (!jjDept.equals(other.jjDept))
			return false;
		if (jjDeptHis == null) {
			if (other.jjDeptHis != null)
				return false;
		} else if (!jjDeptHis.equals(other.jjDeptHis))
			return false;
		if (jjDeptSnapCode == null) {
			if (other.jjDeptSnapCode != null)
				return false;
		} else if (!jjDeptSnapCode.equals(other.jjDeptSnapCode))
			return false;
		if (jjDeptType == null) {
			if (other.jjDeptType != null)
				return false;
		} else if (!jjDeptType.equals(other.jjDeptType))
			return false;
		if (jjLeaf == null) {
			if (other.jjLeaf != null)
				return false;
		} else if (!jjLeaf.equals(other.jjLeaf))
			return false;
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
//		if (org == null) {
//			if (other.org != null)
//				return false;
//		} else if (!org.equals(other.org))
//			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (outin == null) {
			if (other.outin != null)
				return false;
		} else if (!outin.equals(other.outin))
			return false;
		if (parentDept == null) {
			if (other.parentDept != null)
				return false;
		} else if (!parentDept.equals(other.parentDept))
			return false;
		if (parentDeptHis == null) {
			if (other.parentDeptHis != null)
				return false;
		} else if (!parentDeptHis.equals(other.parentDeptHis))
			return false;
		if (parentDeptName == null) {
			if (other.parentDeptName != null)
				return false;
		} else if (!parentDeptName.equals(other.parentDeptName))
			return false;
		if (parentDeptSnapCode == null) {
			if (other.parentDeptSnapCode != null)
				return false;
		} else if (!parentDeptSnapCode.equals(other.parentDeptSnapCode))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (shortnName == null) {
			if (other.shortnName != null)
				return false;
		} else if (!shortnName.equals(other.shortnName))
			return false;
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
			return false;
		if (subClass == null) {
			if (other.subClass != null)
				return false;
		} else if (!subClass.equals(other.subClass))
			return false;
		if (ysDept == null) {
			if (other.ysDept != null)
				return false;
		} else if (!ysDept.equals(other.ysDept))
			return false;
		if (ysDeptHis == null) {
			if (other.ysDeptHis != null)
				return false;
		} else if (!ysDeptHis.equals(other.ysDeptHis))
			return false;
		if (ysDeptSnapCode == null) {
			if (other.ysDeptSnapCode != null)
				return false;
		} else if (!ysDeptSnapCode.equals(other.ysDeptSnapCode))
			return false;
		if (ysLeaf == null) {
			if (other.ysLeaf != null)
				return false;
		} else if (!ysLeaf.equals(other.ysLeaf))
			return false;
		if (ysPurchasingDepartment == null) {
			if (other.ysPurchasingDepartment != null)
				return false;
		} else if (!ysPurchasingDepartment.equals(other.ysPurchasingDepartment))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrDepartmentCurrent [departmentId=" + departmentId + ", name="
				+ name + ", deptCode=" + deptCode + ", shortnName="
				+ shortnName + ", internalCode=" + internalCode + ", outin="
				+ outin + ", deptClass=" + deptClass + ", subClass=" + subClass
				+ ", phone=" + phone + ", contact=" + contact
				+ ", contactPhone=" + contactPhone + ", manager=" + manager
				+ ", note=" + note + ", leaf=" + leaf + ", clevel=" + clevel
				+ ", parentDept=" + parentDept + ", parentDeptHis="
				+ parentDeptHis + ", parentDeptSnapCode=" + parentDeptSnapCode
				+ ", parentDeptName=" + parentDeptName + ", disabled="
				+ disabled + ", invalidDate=" + invalidDate + ", cnCode="
				+ cnCode + ", jjLeaf=" + jjLeaf + ", jjDept=" + jjDept
				+ ", jjDeptHis=" + jjDeptHis + ", jjDeptSnapCode="
				+ jjDeptSnapCode + ", ysLeaf=" + ysLeaf + ", ysDept=" + ysDept
				+ ", ysDeptHis=" + ysDeptHis + ", ysDeptSnapCode="
				+ ysDeptSnapCode + ", jjDeptType=" + jjDeptType + ", orgCode="
				+ orgCode + ", ysPurchasingDepartment="
				+ ysPurchasingDepartment + ", snapCode=" + snapCode + "]";
	}
}
