package com.huge.ihos.hr.hrDeptment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.model.BaseObject;

@Entity
@Table(name = "hr_department_snap")
public class HrDepartmentSnap extends BaseObject implements Serializable,
		Cloneable {
	private static final long serialVersionUID = -1055266851780437513L;
	private String snapId;
	private String snapCode;
	private String deptId;
	private String name; // 科室名称
	private String shortName; // 科室简称
	private String deptCode; // 科室编码
	private String internalCode; // 科室内部码
	private String outin; // 住院门诊
	private String deptType; // 部门类别
	private String subClass; // 专业系别
	private String cnCode;// 助记码
	private String remark; // 备注
	private Boolean disabled = false; // 是否可用
	private Date invalidDate; // 失效日期

	private String manager; // 科室主管姓名
	private String phone; // 电话
	private String contact; // 联系人
	private String contactPhone; // 联系电话
	private String orgCode;// 单位
	private HrOrg hrOrg;
	private HrOrgHis hrOrgHis;
	private String orgSnapCode;
	
	private Boolean leaf = true; // 是否为叶子
	private Integer clevel = 1; // 层次
	private HrDepartmentCurrent parentDept;
	private HrDepartmentHis hisParentDept;
	private String parentDeptId;
	private String hisPDSnapCode;

	private Boolean jjLeaf = true; // 是否为叶子(绩效)
	private HrDepartmentCurrent jjDept;// 奖金
	private HrDepartmentHis hisJjDept;// 奖金
	private String jjDeptId;
	private String hisJjDSnapCode;
	private Boolean ysLeaf = true; // 是否为叶子（预算）
	private HrDepartmentCurrent ysDept;// 预算
	private HrDepartmentHis hisYsDept;// 预算
	private String ysDeptId;
	private String hisYsDSnapCode;
	private KhDeptType jjDeptType;
	private Boolean purchaseDept = false; // 是否采购科室
	/* ADD FOR HR */
	private String kindCode;// 科室类型
	private String attrCode;// 科室性质
	private Boolean funcDept = false;// 是否职能科室
	private Boolean budget = false;// 是否预算
	private Boolean outer = false;// 是否外部单位
	private Integer planCount;// 编制人数
	private Integer realCount;// 实有人数
	private Integer realCountAll;//实有人数（带停用）
	private Integer diffCount;// 编缺人数
	private Integer realZbCount;//实有在编人数
	private Integer overCount;//超编人数

	private String parentDeptName;
	private Boolean deleted = false;// 是否被删除
	private Integer state;// 1:新建；2：已审核；3：新增成功；4：撤销 
	private int personCount = 0;
	private int personCountD = 0;
	private int personCountP = 0;
	private int personCountDP = 0;
	private int personCountWithOutDisabled = 0;
	
	private String branchCode; //院区
	private Branch branch;
	private String dgroup;
	private Boolean cbLeaf = false; // 是否为叶子(成本)
	private Boolean zcLeaf = false; // 是否为叶子(诊次)
	private Boolean crLeaf = false; // 是否为叶子(床日)
	private Boolean xmLeaf = false; // 是否为叶子(项目)
	private HrDepartmentCurrent yjDept;
	
	//lock
		private String lockState;//锁状态
	
	@Transient
	public int getPersonCountD() {
		return personCountD;
	}

	public void setPersonCountD(int personCountD) {
		this.personCountD = personCountD;
	}
	@Transient
	public int getPersonCountP() {
		return personCountP;
	}

	public void setPersonCountP(int personCountP) {
		this.personCountP = personCountP;
	}
	@Transient
	public int getPersonCountDP() {
		return personCountDP;
	}

	public void setPersonCountDP(int personCountDP) {
		this.personCountDP = personCountDP;
	}
	
	@Transient
	//@Formula("(select count(*) from hr_person_snap hpso where hpso.deptId = deptId and hpso.deleted = 0 and hpso.snapId in (select MAX(hpsi.snapId) FROM hr_person_snap hpsi where hpsi.dept_snapCode <=snapCode GROUP BY hpsi.personId) ) ")
	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}
	
	@Transient
	//@Formula("(select count(*) from hr_person_snap hpso where hpso.deptId = deptId and hpso.disabled = 0 and hpso.deleted = 0 and hpso.snapId in (select MAX(hpsi.snapId) FROM hr_person_snap hpsi where hpsi.dept_snapCode <=snapCode GROUP BY hpsi.personId) ) ")
	public int getPersonCountWithOutDisabled() {
		return personCountWithOutDisabled;
	}

	public void setPersonCountWithOutDisabled(int personCountWithOutDisabled) {
		this.personCountWithOutDisabled = personCountWithOutDisabled;
	}
	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Id
	@Column(name = "snapId", length = 65, nullable = false)
	public String getSnapId() {
		return snapId;
	}

	public void setSnapId(String snapId) {
		this.snapId = snapId;
	}

	@Column(name = "snapCode", length = 14, nullable = false)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	@Column(name = "deptId", length = 50, nullable = false)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "name", unique = true, nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "shortName", nullable = true, length = 30)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "deptCode", nullable = false, length = 20)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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

	@Column(name = "deptType", nullable = false, length = 32)
	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	@Column(name = "subClass", nullable = true, length = 50)
	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	@Column(name = "cnCode", nullable = true, length = 50)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "disabled", nullable = true, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invalidDate", length = 19, nullable = true)
	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "manager", nullable = true, length = 20)
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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

	@Column(name = "orgCode", length = 10, nullable = true)
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

	@Column(name = "leaf", nullable = true, columnDefinition = "bit default 0")
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

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentDept_id", nullable = false, insertable = false, updatable = false)
	public HrDepartmentCurrent getParentDept() {
		return parentDept;
	}

	public void setParentDept(HrDepartmentCurrent parentDept) {
		this.parentDept = parentDept;
	}
	
	@Column(name = "parentDept_id", nullable = true, length = 50)
	public String getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	@Column(name = "hisPD_snapCode", nullable = true, length = 14)
	public String getHisPDSnapCode() {
		return hisPDSnapCode;
	}

	public void setHisPDSnapCode(String hisPDSnapCode) {
		this.hisPDSnapCode = hisPDSnapCode;
	}

//	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "parentDept_id", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "hisPD_snapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getHisParentDept() {
		return hisParentDept;
	}

	public void setHisParentDept(HrDepartmentHis hisParentDept) {
		this.hisParentDept = hisParentDept;
	}

	@Column(name = "jjLeaf", nullable = true, columnDefinition = "bit default 0")
	public Boolean getJjLeaf() {
		return jjLeaf;
	}

	public void setJjLeaf(Boolean jjLeaf) {
		this.jjLeaf = jjLeaf;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jjDeptId", nullable = false, insertable = false, updatable = false)
	public HrDepartmentCurrent getJjDept() {
		return jjDept;
	}

	public void setJjDept(HrDepartmentCurrent jjDept) {
		this.jjDept = jjDept;
	}
	
	@Column(name = "jjDeptId", nullable = true, length = 50)
	public String getJjDeptId() {
		return jjDeptId;
	}

	public void setJjDeptId(String jjDeptId) {
		this.jjDeptId = jjDeptId;
	}

	@Column(name = "hisJjD_snapCode", nullable = true, length = 14)
	public String getHisJjDSnapCode() {
		return hisJjDSnapCode;
	}

	public void setHisJjDSnapCode(String hisJjDSnapCode) {
		this.hisJjDSnapCode = hisJjDSnapCode;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "jjDeptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "hisJjD_snapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getHisJjDept() {
		return hisJjDept;
	}

	public void setHisJjDept(HrDepartmentHis hisJjDept) {
		this.hisJjDept = hisJjDept;
	}

	@Column(name = "ysLeaf", nullable = true, columnDefinition = "bit default 0")
	public Boolean getYsLeaf() {
		return ysLeaf;
	}

	public void setYsLeaf(Boolean ysLeaf) {
		this.ysLeaf = ysLeaf;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ysDeptId", nullable = false, insertable = false, updatable = false)
	public HrDepartmentCurrent getYsDept() {
		return ysDept;
	}

	public void setYsDept(HrDepartmentCurrent ysDept) {
		this.ysDept = ysDept;
	}
	
	@Column(name = "ysDeptId", nullable = true, length = 50)
	public String getYsDeptId() {
		return ysDeptId;
	}

	public void setYsDeptId(String ysDeptId) {
		this.ysDeptId = ysDeptId;
	}

	@Column(name = "hisYsD_snapCode", nullable = true, length = 14)
	public String getHisYsDSnapCode() {
		return hisYsDSnapCode;
	}

	public void setHisYsDSnapCode(String hisYsDSnapCode) {
		this.hisYsDSnapCode = hisYsDSnapCode;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "ysDeptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "hisYsD_snapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getHisYsDept() {
		return hisYsDept;
	}

	public void setHisYsDept(HrDepartmentHis hisYsDept) {
		this.hisYsDept = hisYsDept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jjdepttype", nullable = true)
	public KhDeptType getJjDeptType() {
		return jjDeptType;
	}

	public void setJjDeptType(KhDeptType jjDeptType) {
		this.jjDeptType = jjDeptType;
	}

	@Column(name = "is_purchase", nullable = true, columnDefinition = "bit default 0")
	public Boolean getPurchaseDept() {
		return purchaseDept;
	}

	public void setPurchaseDept(Boolean purchaseDept) {
		this.purchaseDept = purchaseDept;
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

	@Column(name = "is_func", nullable = true, columnDefinition = "bit default 0")
	public Boolean getFuncDept() {
		return funcDept;
	}

	public void setFuncDept(Boolean funcDept) {
		this.funcDept = funcDept;
	}

	@Column(name = "is_budg", nullable = true, columnDefinition = "bit default 0")
	public Boolean getBudget() {
		return budget;
	}

	public void setBudget(Boolean budget) {
		this.budget = budget;
	}

	@Column(name = "is_outer", nullable = true, columnDefinition = "bit default 0")
	public Boolean getOuter() {
		return outer;
	}

	public void setOuter(Boolean outer) {
		this.outer = outer;
	}

	@Column(name = "plan_count", nullable = true)
	public Integer getPlanCount() {
		if(planCount==null){
			planCount =  0;
		}
		return planCount;
	}

	public void setPlanCount(Integer planCount) {
		this.planCount = planCount;
	}

	@Column(name = "real_count", nullable = true)
	public Integer getRealCount() {
		if(realCount==null){
			realCount =  0;
		}
		return realCount;
		//return personCount;
	}

	public void setRealCount(Integer realCount) {
		if(realCount==null){
			realCount =  0;
		}
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
	
	@Column(name = "diff_count", nullable = true)
	public Integer getDiffCount() {
		if(diffCount==null){
			diffCount =  0;
		}
		/*else{
//			diffCount = this.planCount - this.realCount;
			diffCount = this.planCount - this.personCount;
			if(diffCount < 0){
				diffCount =  0;
			}
		}*/
		return diffCount;
	}

	public void setDiffCount(Integer diffCount) {
		this.diffCount = diffCount;
	}
	
	@Column(name = "real_zb_count", nullable = true)
	public Integer getRealZbCount() {
		if(realZbCount==null){
			realZbCount =  0;
		}
		return realZbCount;
	}

	public void setRealZbCount(Integer realZbCount) {
		this.realZbCount = realZbCount;
	}
	@Column(name = "over_count", nullable = true)
	public Integer getOverCount() {
		return overCount;
	}

	public void setOverCount(Integer overCount) {
		this.overCount = overCount;
	}

	@Column(name = "deleted", nullable = true, columnDefinition = "bit default 0")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	@Column(name = "lock_state", nullable = true, length = 200)
	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
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
	public String toString() {
		return "HrDepartmentSnap [snapId=" + snapId + ", snapCode=" + snapCode
				+ ", deptId=" + deptId + ", name=" + name + ", shortName="
				+ shortName + ", deptCode=" + deptCode + ", internalCode="
				+ internalCode + ", outin=" + outin + ", deptType=" + deptType
				+ ", subClass=" + subClass + ", cnCode=" + cnCode + ", remark="
				+ remark + ", disabled=" + disabled + ", invalidDate="
				+ invalidDate + ", manager=" + manager + ", phone=" + phone
				+ ", contact=" + contact + ", contactPhone=" + contactPhone
				+ ", orgCode=" + orgCode + ", leaf=" + leaf + ", clevel="
				+ clevel + ", parentDept=" + parentDept + ", hisParentDept="
				+ hisParentDept + ", hisPDSnapCode=" + hisPDSnapCode
				+ ", jjLeaf=" + jjLeaf + ", jjDept=" + jjDept + ", hisJjDept="
				+ hisJjDept + ", hisJjDSnapCode=" + hisJjDSnapCode
				+ ", ysLeaf=" + ysLeaf + ", ysDept=" + ysDept + ", hisYsDept="
				+ hisYsDept + ", hisYsDSnapCode=" + hisYsDSnapCode
				+ ", jjDeptType=" + jjDeptType + ", purchaseDept="
				+ purchaseDept + ", kindCode=" + kindCode + ", attrCode="
				+ attrCode + ", funcDept=" + funcDept + ", budget=" + budget
				+ ", outer=" + outer + ", planCount=" + planCount
				+ ", realCount=" + realCount + ", diffCount=" + diffCount
				+ ", parentDeptName=" + parentDeptName + ", deleted=" + deleted
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attrCode == null) ? 0 : attrCode.hashCode());
		result = prime * result + ((budget == null) ? 0 : budget.hashCode());
		result = prime * result + ((clevel == null) ? 0 : clevel.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result
				+ ((contactPhone == null) ? 0 : contactPhone.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result
				+ ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result
				+ ((deptType == null) ? 0 : deptType.hashCode());
		result = prime * result
				+ ((diffCount == null) ? 0 : diffCount.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((funcDept == null) ? 0 : funcDept.hashCode());
		result = prime * result
				+ ((hisJjDSnapCode == null) ? 0 : hisJjDSnapCode.hashCode());
		result = prime * result
				+ ((hisJjDept == null) ? 0 : hisJjDept.hashCode());
		result = prime * result
				+ ((hisPDSnapCode == null) ? 0 : hisPDSnapCode.hashCode());
		result = prime * result
				+ ((hisParentDept == null) ? 0 : hisParentDept.hashCode());
		result = prime * result
				+ ((hisYsDSnapCode == null) ? 0 : hisYsDSnapCode.hashCode());
		result = prime * result
				+ ((hisYsDept == null) ? 0 : hisYsDept.hashCode());
		result = prime * result
				+ ((internalCode == null) ? 0 : internalCode.hashCode());
		result = prime * result
				+ ((invalidDate == null) ? 0 : invalidDate.hashCode());
		result = prime * result + ((jjDept == null) ? 0 : jjDept.hashCode());
		result = prime * result
				+ ((jjDeptType == null) ? 0 : jjDeptType.hashCode());
		result = prime * result + ((jjLeaf == null) ? 0 : jjLeaf.hashCode());
		result = prime * result
				+ ((kindCode == null) ? 0 : kindCode.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((outer == null) ? 0 : outer.hashCode());
		result = prime * result + ((outin == null) ? 0 : outin.hashCode());
		result = prime * result
				+ ((parentDept == null) ? 0 : parentDept.hashCode());
		result = prime * result
				+ ((parentDeptName == null) ? 0 : parentDeptName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((planCount == null) ? 0 : planCount.hashCode());
		result = prime * result
				+ ((purchaseDept == null) ? 0 : purchaseDept.hashCode());
		result = prime * result
				+ ((realCount == null) ? 0 : realCount.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
		result = prime * result + ((snapId == null) ? 0 : snapId.hashCode());
		result = prime * result
				+ ((subClass == null) ? 0 : subClass.hashCode());
		result = prime * result + ((ysDept == null) ? 0 : ysDept.hashCode());
		result = prime * result + ((ysLeaf == null) ? 0 : ysLeaf.hashCode());
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
		HrDepartmentSnap other = (HrDepartmentSnap) obj;
		if (attrCode == null) {
			if (other.attrCode != null)
				return false;
		} else if (!attrCode.equals(other.attrCode))
			return false;
		if (budget == null) {
			if (other.budget != null)
				return false;
		} else if (!budget.equals(other.budget))
			return false;
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
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (deptType == null) {
			if (other.deptType != null)
				return false;
		} else if (!deptType.equals(other.deptType))
			return false;
		if (diffCount == null) {
			if (other.diffCount != null)
				return false;
		} else if (!diffCount.equals(other.diffCount))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (funcDept == null) {
			if (other.funcDept != null)
				return false;
		} else if (!funcDept.equals(other.funcDept))
			return false;
		if (hisJjDSnapCode == null) {
			if (other.hisJjDSnapCode != null)
				return false;
		} else if (!hisJjDSnapCode.equals(other.hisJjDSnapCode))
			return false;
		if (hisJjDept == null) {
			if (other.hisJjDept != null)
				return false;
		} else if (!hisJjDept.equals(other.hisJjDept))
			return false;
		if (hisPDSnapCode == null) {
			if (other.hisPDSnapCode != null)
				return false;
		} else if (!hisPDSnapCode.equals(other.hisPDSnapCode))
			return false;
		if (hisParentDept == null) {
			if (other.hisParentDept != null)
				return false;
		} else if (!hisParentDept.equals(other.hisParentDept))
			return false;
		if (hisYsDSnapCode == null) {
			if (other.hisYsDSnapCode != null)
				return false;
		} else if (!hisYsDSnapCode.equals(other.hisYsDSnapCode))
			return false;
		if (hisYsDept == null) {
			if (other.hisYsDept != null)
				return false;
		} else if (!hisYsDept.equals(other.hisYsDept))
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
		if (kindCode == null) {
			if (other.kindCode != null)
				return false;
		} else if (!kindCode.equals(other.kindCode))
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
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (outer == null) {
			if (other.outer != null)
				return false;
		} else if (!outer.equals(other.outer))
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
		if (parentDeptName == null) {
			if (other.parentDeptName != null)
				return false;
		} else if (!parentDeptName.equals(other.parentDeptName))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (planCount == null) {
			if (other.planCount != null)
				return false;
		} else if (!planCount.equals(other.planCount))
			return false;
		if (purchaseDept == null) {
			if (other.purchaseDept != null)
				return false;
		} else if (!purchaseDept.equals(other.purchaseDept))
			return false;
		if (realCount == null) {
			if (other.realCount != null)
				return false;
		} else if (!realCount.equals(other.realCount))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
			return false;
		if (snapId == null) {
			if (other.snapId != null)
				return false;
		} else if (!snapId.equals(other.snapId))
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
		if (ysLeaf == null) {
			if (other.ysLeaf != null)
				return false;
		} else if (!ysLeaf.equals(other.ysLeaf))
			return false;
		return true;
	}
	
	@Transient
	public Map<String,Object> getMapEntity(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("snapId", this.snapId);
		map.put("snapCode", this.snapCode);
		map.put("deptId", this.deptId);
		map.put("name", this.name);
		map.put("shortName", this.shortName);
		map.put("deptCode", this.deptCode);
		map.put("deptType", this.deptType);
		map.put("clevel", this.clevel);
		map.put("outin", this.outin);
		map.put("leaf", this.leaf);
		map.put("cnCode", this.cnCode);
		map.put("subClass", this.subClass);
		map.put("internalCode", this.internalCode);
		map.put("disabled", this.disabled);
		map.put("contact", this.contact);
		map.put("contactPhone", this.contactPhone);
		map.put("invalidDate", this.invalidDate);
		map.put("manager", this.manager);
		map.put("phone", this.phone);
		map.put("remark", this.remark);
		map.put("parentDept_id", this.parentDeptId);
		map.put("jjLeaf", this.jjLeaf);
		map.put("jjDeptId", this.jjDeptId);
		map.put("ysLeaf", this.ysLeaf);
		map.put("ysDeptId", this.ysDeptId);
		map.put("jjdepttype", jjDeptType==null?null:this.jjDeptType.getKhDeptTypeId());
		map.put("orgCode", this.orgCode);
		map.put("orgSnapCode", this.orgSnapCode);
		map.put("is_purchase", this.purchaseDept);
		map.put("deleted", this.deleted);
		map.put("is_func", this.funcDept);
		map.put("is_budg", this.budget);
		map.put("is_outer", this.outer);
		map.put("plan_count", this.getPlanCount());
		map.put("real_count", this.getRealCount());
		map.put("real_zb_count", this.getRealZbCount());
		map.put("diff_count", this.getDiffCount());
		map.put("attr_code", this.attrCode);
		map.put("kind_code", this.kindCode);
		map.put("state", this.state);
		map.put("hisPD_snapCode", this.hisPDSnapCode);
		map.put("hisJjD_snapCode", this.hisJjDSnapCode);
		map.put("hisYsD_snapCode", this.hisYsDSnapCode);
		return map;
	}
	

	@Override
	public HrDepartmentSnap clone() {
		HrDepartmentSnap o = null;
		try {
			o = (HrDepartmentSnap) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
