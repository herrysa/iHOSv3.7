package com.huge.ihos.system.systemManager.organization.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.huge.model.BaseObject;

@Entity
@Table(name = "t_person")
@FilterDef(name = "exceptPersonFilter", parameters = { @ParamDef(name = "exceptPerson", type = "string") })
@Filters({ @Filter(name = "exceptPersonFilter", condition = "personId <> :exceptPerson") })
public class Person extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3222641174081493073L;

	private String personId; // 人员ID

	// String
	private String name; // 姓名

	// req
	private String sex; // 性别

	// dic
	private Date birthday; // 出生日期

	private String duty; // 职务

	// dic
	private String personCode; // 人员编码

	// 唯一键
	// req
	private String educationalLevel; // 学历

	// dic
	private Boolean disable = false; // 是否有效

	// req
	private String salaryNumber; // 工资号

	private String idNumber; // 身份证号

	private String jobTitle; // 职称

	// dic
	private String postType; // 岗位

	// dic
	// req
	private Double ratio; // 系数

	private String status; // 职工类别

	// rename
	// to
	// empType
	// req

	private Date workBegin; // 参加工作时间

	private String note; // 备注

	private Department department; // req

	private Boolean jjmark = true;

	private String orgCode;//单位

	private Org org;

	private String snapCode = "0";

	private String branchCode;
	
	private Branch branch; 
	
	//工资附加字段
	//private PersonType personType;
	private Boolean stopSalary = false;
	private String stopReason;
	private String taxType;
	private String bank1;
	private String bank2;
	private String salaryNumber2;
	private String gzType;
	private String gzType2;
	//考勤
	private String kqType;//考勤类别
	private Boolean stopKq = false;
	private String stopKqReason;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dept_id", nullable = false)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "salaryNumber", nullable = true, length = 20)
	public String getSalaryNumber() {
		return salaryNumber;
	}

	public void setSalaryNumber(String salaryNumber) {
		this.salaryNumber = salaryNumber;
	}

	@Column(name = "idNumber", nullable = true, length = 20)
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "jobTitle", nullable = true, length = 20)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name = "postType", nullable = true, length = 20)
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	@Column(name = "workBegin", nullable = true)
	public Date getWorkBegin() {
		return workBegin;
	}

	public void setWorkBegin(Date workBegin) {
		this.workBegin = workBegin;
	}

	/*
	 * @Column(name="prizeFlag", nullable=true) public Boolean getPrizeFlag() {
	 * return prizeFlag; }
	 * 
	 * public void setPrizeFlag(Boolean prizeFlag) { this.prizeFlag = prizeFlag;
	 * }
	 * 
	 * @Column(name="prescriptionFlag", nullable=true) public Boolean
	 * getPrescriptionFlag() { return prescriptionFlag; }
	 * 
	 * public void setPrescriptionFlag(Boolean prescriptionFlag) {
	 * this.prescriptionFlag = prescriptionFlag; }
	 * 
	 * @Column(name="nurseFlag", nullable=true) public Boolean getNurseFlag() {
	 * return nurseFlag; }
	 * 
	 * public void setNurseFlag(Boolean nurseFlag) { this.nurseFlag = nurseFlag;
	 * }
	 * 
	 * @Column(name="expertFlag", nullable=true) public Boolean getExpertFlag()
	 * { return expertFlag; }
	 * 
	 * public void setExpertFlag(Boolean expertFlag) { this.expertFlag =
	 * expertFlag; }
	 */

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "personId", nullable = false, length = 50)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "sex", nullable = false, length = 10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "birthday", nullable = true)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "educationalLevel", nullable = true, length = 20)
	public String getEducationalLevel() {
		return educationalLevel;
	}

	public void setEducationalLevel(String educationalLevel) {
		this.educationalLevel = educationalLevel;
	}

	/*
	 * @Column(name="email", nullable=true, length=50) public String getEmail()
	 * { return email; }
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 * 
	 * @Column(name="phone", nullable=true, length=20) public String getPhone()
	 * { return phone; }
	 * 
	 * public void setPhone(String phone) { this.phone = phone; }
	 * 
	 * @Column(name="mobile", nullable=true, length=20) public String
	 * getMobile() { return mobile; }
	 * 
	 * public void setMobile(String mobile) { this.mobile = mobile; }
	 */
	@Column(name = "disabled", nullable = false)
	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	@Column(name = "duty", nullable = true, length = 20)
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "personCode", nullable = false, length = 20)
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	@Column(name = "note", nullable = true, length = 200)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "ratio", nullable = true)
	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	@Column(name = "empType", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "jjmark")
	public Boolean getJjmark() {
		return jjmark;
	}

	public void setJjmark(Boolean jjmark) {
		this.jjmark = jjmark;
	}
	@Column(name="branchCode",length=30,nullable=true)
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	@Column(name = "orgCode", nullable = true, length = 20)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgCode", nullable = true, insertable = false, updatable = false)
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	@Column(name = "snapCode", length = 14, nullable = true)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	/*@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "person_type")
	public PersonType getPersonType() {
		return personType;
	}

	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}*/

	@Column(name = "stopSalary", nullable = true)
	public Boolean getStopSalary() {
		return stopSalary;
	}

	public void setStopSalary(Boolean stopSalary) {
		this.stopSalary = stopSalary;
	}

	@Column(name = "taxType", length = 20, nullable = true)
	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	@Column(name = "stopReason", length = 200, nullable = true)
	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	@Column(name = "bank1", length = 10, nullable = true)
	public String getBank1() {
		return bank1;
	}

	public void setBank1(String bank1) {
		this.bank1 = bank1;
	}

	@Column(name = "bank2", length = 10, nullable = true)
	public String getBank2() {
		return bank2;
	}

	public void setBank2(String bank2) {
		this.bank2 = bank2;
	}

	@Column(name = "salaryNumber2", length = 20, nullable = true)
	public String getSalaryNumber2() {
		return salaryNumber2;
	}

	public void setSalaryNumber2(String salaryNumber2) {
		this.salaryNumber2 = salaryNumber2;
	}

	@Column(name = "gzTypeId", length = 20, nullable = true)
	public String getGzType() {
		return gzType;
	}

	public void setGzType(String gzType) {
		this.gzType = gzType;
	}
	
	@Column(name = "gzTypeId2", length = 32, nullable = true)
	//@Transient
	public String getGzType2() {
		return gzType2;
	}

	public void setGzType2(String gzType2) {
		this.gzType2 = gzType2;
	}

	@Column(name = "kqTypeId", length = 32, nullable = true)
	public String getKqType() {
		return kqType;
	}

	public void setKqType(String kqType) {
		this.kqType = kqType;
	}

	@Column(name = "stopKq", nullable = true)
	public Boolean getStopKq() {
		return stopKq;
	}

	public void setStopKq(Boolean stopKq) {
		this.stopKq = stopKq;
	}

	@Column(name = "stopKqReason", length = 200, nullable = true)
	public String getStopKqReason() {
		return stopKqReason;
	}

	public void setStopKqReason(String stopKqReason) {
		this.stopKqReason = stopKqReason;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branchCode", nullable = true, insertable = false, updatable = false)
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((duty == null) ? 0 : duty.hashCode());
		result = prime * result + ((educationalLevel == null) ? 0 : educationalLevel.hashCode());
		result = prime * result + ((disable == null) ? 0 : disable.hashCode());
		result = prime * result + ((idNumber == null) ? 0 : idNumber.hashCode());
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((personCode == null) ? 0 : personCode.hashCode());
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + ((postType == null) ? 0 : postType.hashCode());
		result = prime * result + ((ratio == null) ? 0 : ratio.hashCode());
		result = prime * result + ((salaryNumber == null) ? 0 : salaryNumber.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((workBegin == null) ? 0 : workBegin.hashCode());
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
		Person other = (Person) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (duty == null) {
			if (other.duty != null)
				return false;
		} else if (!duty.equals(other.duty))
			return false;
		if (educationalLevel == null) {
			if (other.educationalLevel != null)
				return false;
		} else if (!educationalLevel.equals(other.educationalLevel))
			return false;
		if (disable == null) {
			if (other.disable != null)
				return false;
		} else if (!disable.equals(other.disable))
			return false;
		if (idNumber == null) {
			if (other.idNumber != null)
				return false;
		} else if (!idNumber.equals(other.idNumber))
			return false;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
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
		if (personCode == null) {
			if (other.personCode != null)
				return false;
		} else if (!personCode.equals(other.personCode))
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (postType == null) {
			if (other.postType != null)
				return false;
		} else if (!postType.equals(other.postType))
			return false;
		if (ratio == null) {
			if (other.ratio != null)
				return false;
		} else if (!ratio.equals(other.ratio))
			return false;
		if (salaryNumber == null) {
			if (other.salaryNumber != null)
				return false;
		} else if (!salaryNumber.equals(other.salaryNumber))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (workBegin == null) {
			if (other.workBegin != null)
				return false;
		} else if (!workBegin.equals(other.workBegin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", duty=" + duty + ", personCode=" + personCode + ", educationalLevel=" + educationalLevel + ", enabled=" + disable + ", salaryNumber=" + salaryNumber + ", idNumber=" + idNumber + ", jobTitle=" + jobTitle + ", postType=" + postType + ", ratio=" + ratio + ", status=" + status + ", workBegin=" + workBegin + ", note=" + note + ", department=" + department + "]";
	}

}
