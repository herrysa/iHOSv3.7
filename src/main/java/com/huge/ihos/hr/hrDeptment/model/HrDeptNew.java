package com.huge.ihos.hr.hrDeptment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 部门新增
 */
@Entity
@Table(name = "hr_department_new")
public class HrDeptNew extends BaseObject implements Serializable,Cloneable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3717408693888530015L;
	private String id;
	private String newNo;// 新增流水号
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

	private String remark; // 备注

	private Boolean leaf = true; // 是否为叶子

	private Integer clevel = 1; // 层次

	private HrDepartmentCurrent parentDept;
	private HrDepartmentHis parentDeptHis;
	private String parentDeptSnapCode;

	private String parentDeptName;

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
	
	private HrOrg hrOrg;
	private HrOrgHis hrOrgHis;
	private String orgSnapCode;
	private String kindCode;// 科室类型
	private String attrCode;// 科室性质

	private Integer state;//1:新建;2:已审核;3:已执行
	private Person makePerson;
	private Date makeDate;
	private Person checkPerson;
	private Date checkDate;
	private Person confirmPerson;
	private Date confirmDate;
	private String yearMonth;
	private Integer planCount;// 编制人数
	private Integer realCount=0;// 实有人数
	private Integer diffCount;// 编缺人数
	
	@Column(name = "yearMonth", nullable = true, length = 6)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	
	@Id
	@Column(name = "id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "newNo", nullable = false, length = 30)
	public String getNewNo() {
		return newNo;
	}

	public void setNewNo(String newNo) {
		this.newNo = newNo;
	}

	
	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Transient
	public String getDeptCodeName() {
		return this.deptCode+"("+this.name+")";
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
			@JoinColumn(name = "parentDept_id", nullable = true, insertable = false, updatable = false),
			@JoinColumn(name = "parentDeptSnapCode", nullable = true, insertable = false, updatable = false) })
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

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "invalidDate", nullable = true)
	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
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
			@JoinColumn(name = "jjDeptId", nullable = true, insertable = false, updatable = false),
			@JoinColumn(name = "jjDeptSnapCode", nullable = true, insertable = false, updatable = false) })
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
			@JoinColumn(name = "ysDeptId", nullable = true, insertable = false, updatable = false),
			@JoinColumn(name = "ysDeptSnapCode", nullable = true, insertable = false, updatable = false) })
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = true)
	public Person getMakePerson() {
		return makePerson;
	}

	public void setMakePerson(Person makePerson) {
		this.makePerson = makePerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker", nullable = true)
	public Person getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Person checkPerson) {
		this.checkPerson = checkPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "confirmer", nullable = true)
	public Person getConfirmPerson() {
		return confirmPerson;
	}

	public void setConfirmPerson(Person confirmPerson) {
		this.confirmPerson = confirmPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirmDate", length = 19, nullable = true)
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	@Column(name = "kind_code", nullable = true, length = 32)
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	@Column(name = "attr_code", nullable = true, length = 32)
	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}
	@Column(name = "plan_count", nullable = true)
	public Integer getPlanCount() {
		return planCount;
	}

	public void setPlanCount(Integer planCount) {
		this.planCount = planCount;
	}

	@Column(name = "real_count", nullable = true)
	public Integer getRealCount() {
//		return realCount;
		return 0;
	}

	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}

	@Column(name = "diff_count", nullable = true)
	public Integer getDiffCount() {
		if(this.planCount==null){
			diffCount =  0;
		}else{
//			diffCount = this.planCount - this.realCount;
			diffCount = this.planCount - 0;
			if(diffCount < 0){
				diffCount =  0;
			}
		}
		return diffCount;
	}
	public void setDiffCount(Integer diffCount) {
		this.diffCount = diffCount;
	}
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgCode", nullable = false, insertable = false, updatable = false)
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}*/
	
	
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
				+ ((deptClass == null) ? 0 : deptClass.hashCode());
		result = prime * result
				+ ((deptCode == null) ? 0 : deptCode.hashCode());
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
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		HrDeptNew other = (HrDeptNew) obj;
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
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
		return "HrDeptNew [name="
				+ name + ", deptCode=" + deptCode + ", shortnName="
				+ shortnName + ", internalCode=" + internalCode + ", outin="
				+ outin + ", deptClass=" + deptClass + ", subClass=" + subClass
				+ ", phone=" + phone + ", contact=" + contact
				+ ", contactPhone=" + contactPhone + ", manager=" + manager
				+ ", remark=" + remark + ", leaf=" + leaf + ", clevel=" + clevel
				+ ", parentDept=" + parentDept + ", parentDeptHis="
				+ parentDeptHis + ", parentDeptSnapCode=" + parentDeptSnapCode
				+ ", parentDeptName=" + parentDeptName + ", invalidDate=" + invalidDate + ", cnCode="
				+ cnCode + ", jjLeaf=" + jjLeaf + ", jjDept=" + jjDept
				+ ", jjDeptHis=" + jjDeptHis + ", jjDeptSnapCode="
				+ jjDeptSnapCode + ", ysLeaf=" + ysLeaf + ", ysDept=" + ysDept
				+ ", ysDeptHis=" + ysDeptHis + ", ysDeptSnapCode="
				+ ysDeptSnapCode + ", jjDeptType=" + jjDeptType + ", orgCode="
				+ orgCode + ", ysPurchasingDepartment="
				+ ysPurchasingDepartment + "]";
	}
	@Override
	public HrDeptNew clone() {
		HrDeptNew o = null;
		try {
			o = (HrDeptNew) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
