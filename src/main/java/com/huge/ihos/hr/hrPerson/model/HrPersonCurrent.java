package com.huge.ihos.hr.hrPerson.model;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.model.BaseObject;

@Entity
@Table(name = "v_hr_person_current")
public class HrPersonCurrent extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8227507865652305886L;
	private String personId;
	private String name; // 姓名
	private String sex; // 性别
	private Date birthday;
	private Post duty;

	private String personCode; // 人员编码
	private String educationalLevel;// 学历
	private Boolean disable = false; // 是否有效
	private String salaryNumber; // 工资号
	private String idNumber; // 身份证号
	private String jobTitle; // 职称
	private String postType; // 岗位
	private Double ratio; // 系数
	private PersonType status;// 职工类别
	private Date workBegin; // 参加工作时间
	private String note;
	private String nation;// 民族
	private String politicalCode;// 政治面貌
	private String workPhone;// 工作电话
	private String mobilePhone;// 手机号码
	private String email;// Email
	private Date entryDate; // 进单位时间
	private String school;// 毕业学校
	private String profession;// 专业
	private String degree;// 学位
	private Date graduateDate;// 毕业时间
	private Integer age;// 年龄
	private Integer nurseAge;// 护龄
	private Integer workAge;// 工龄
	private Integer softWorkAge;// 软工龄
	private HrDepartmentCurrent department; // req
	private HrDepartmentHis departmentHis;
	private String departmentSnapCode;
	private Boolean jjmark = true;

	private String orgCode;// 单位
	private HrOrg hrOrg;
	private HrOrgHis hrOrgHis;
	private String orgSnapCode;
	private Boolean deleted = false;
	private String snapCode;
	private String imagePath;

	// lock
	private String lockState;// 锁状态
	/* 工资 */
	private Boolean stopSalary;
	private String stopReason;
	private String taxType;
	private String bank1;
	private String bank2;
	private String salaryNumber2;
	private String gzType;
	//考勤附加字段
	private String kqType;//考勤类别
	private Boolean stopKq;
    private String stopKqReason;
    
    private Branch branch;
	private String branchCode;

	@Id
	@Column(name = "personId", length = 50, nullable = false)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "name", length = 20, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "nation", nullable = true, length = 20)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "political_code", nullable = true, length = 20)
	public String getPoliticalCode() {
		return politicalCode;
	}

	public void setPoliticalCode(String politicalCode) {
		this.politicalCode = politicalCode;
	}

	@Column(name = "work_phone", nullable = true, length = 15)
	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	@Column(name = "mobile_phone", nullable = true, length = 15)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "email", nullable = true, length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entry_date", length = 19, nullable = true)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Column(name = "school", nullable = true, length = 50)
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "profession", nullable = true, length = 20)
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Column(name = "degree", nullable = true, length = 20)
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "graduate_date", length = 19, nullable = true)
	public Date getGraduateDate() {
		return graduateDate;
	}

	public void setGraduateDate(Date graduateDate) {
		this.graduateDate = graduateDate;
	}

	@Column(name = "sex", length = 10, nullable = false)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday", length = 19, nullable = true)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "duty", nullable = true)
	public Post getDuty() {
		return duty;
	}

	public void setDuty(Post duty) {
		this.duty = duty;
	}

	@Column(name = "personCode", length = 20, nullable = false)
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	@Column(name = "educationalLevel", nullable = true, length = 20)
	public String getEducationalLevel() {
		return educationalLevel;
	}

	public void setEducationalLevel(String educationalLevel) {
		this.educationalLevel = educationalLevel;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
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

	// @JSON(serialize = false)
	@Column(name = "postType", nullable = true, length = 20)
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	@Column(name = "ratio", nullable = true)
	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empType", nullable = true)
	public PersonType getStatus() {
		return status;
	}

	public void setStatus(PersonType status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "workBegin", length = 19, nullable = true)
	public Date getWorkBegin() {
		return workBegin;
	}

	public void setWorkBegin(Date workBegin) {
		this.workBegin = workBegin;
	}

	@Column(name = "note", nullable = true, length = 200)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", nullable = false)
	public HrDepartmentCurrent getDepartment() {
		return department;
	}

	public void setDepartment(HrDepartmentCurrent department) {
		this.department = department;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "dept_id", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "departmentSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getDepartmentHis() {
		return departmentHis;
	}

	public void setDepartmentHis(HrDepartmentHis departmentHis) {
		this.departmentHis = departmentHis;
	}

	@Column(name = "departmentSnapCode", nullable = true, length = 14)
	public String getDepartmentSnapCode() {
		return departmentSnapCode;
	}

	public void setDepartmentSnapCode(String departmentSnapCode) {
		this.departmentSnapCode = departmentSnapCode;
	}

	@Column(name = "jjmark", nullable = true, columnDefinition = "bit default 0")
	public Boolean getJjmark() {
		return jjmark;
	}

	public void setJjmark(Boolean jjmark) {
		this.jjmark = jjmark;
	}

	@Column(name = "orgCode", nullable = true, length = 10)
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

	@Column(name = "snapCode", length = 14, nullable = false)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	@Column(name = "deleted", nullable = true, columnDefinition = "bit default 0")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "imagePath", length = 100, nullable = true)
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "age", nullable = true)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "nurse_age", nullable = true)
	public Integer getNurseAge() {
		return nurseAge;
	}

	public void setNurseAge(Integer nurseAge) {
		this.nurseAge = nurseAge;
	}

	@Column(name = "work_age", nullable = true)
	public Integer getWorkAge() {
		return workAge;
	}

	public void setWorkAge(Integer workAge) {
		this.workAge = workAge;
	}

	@Column(name = "soft_work_age", nullable = true)
	public Integer getSoftWorkAge() {
		return softWorkAge;
	}

	public void setSoftWorkAge(Integer softWorkAge) {
		this.softWorkAge = softWorkAge;
	}

	@Column(name = "lock_state", nullable = true, length = 200)
	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}

	/*工资*/
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
	@Column(name = "stopKqReason",length = 200, nullable = true)
	public String getStopKqReason() {
		return stopKqReason;
	}
	public void setStopKqReason(String stopKqReason) {
		this.stopKqReason = stopKqReason;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="branchCode",nullable=true,insertable=false,updatable=false)
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	@Column(name="branchCode",length=30,nullable=true)
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result
				+ ((department == null) ? 0 : department.hashCode());
		result = prime * result
				+ ((departmentHis == null) ? 0 : departmentHis.hashCode());
		result = prime
				* result
				+ ((departmentSnapCode == null) ? 0 : departmentSnapCode
						.hashCode());
		result = prime * result + ((disable == null) ? 0 : disable.hashCode());
		result = prime * result + ((duty == null) ? 0 : duty.hashCode());
		result = prime
				* result
				+ ((educationalLevel == null) ? 0 : educationalLevel.hashCode());
		result = prime * result
				+ ((idNumber == null) ? 0 : idNumber.hashCode());
		result = prime * result + ((jjmark == null) ? 0 : jjmark.hashCode());
		result = prime * result
				+ ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((personCode == null) ? 0 : personCode.hashCode());
		result = prime * result
				+ ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + ((ratio == null) ? 0 : ratio.hashCode());
		result = prime * result
				+ ((salaryNumber == null) ? 0 : salaryNumber.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result
				+ ((workBegin == null) ? 0 : workBegin.hashCode());
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
		HrPersonCurrent other = (HrPersonCurrent) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (departmentHis == null) {
			if (other.departmentHis != null)
				return false;
		} else if (!departmentHis.equals(other.departmentHis))
			return false;
		if (departmentSnapCode == null) {
			if (other.departmentSnapCode != null)
				return false;
		} else if (!departmentSnapCode.equals(other.departmentSnapCode))
			return false;
		if (disable == null) {
			if (other.disable != null)
				return false;
		} else if (!disable.equals(other.disable))
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
		if (idNumber == null) {
			if (other.idNumber != null)
				return false;
		} else if (!idNumber.equals(other.idNumber))
			return false;
		if (jjmark == null) {
			if (other.jjmark != null)
				return false;
		} else if (!jjmark.equals(other.jjmark))
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
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
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
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
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
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrPersonCurrent [personId=" + personId + ", name=" + name
				+ ", sex=" + sex + ", birthday=" + birthday + ", duty=" + duty
				+ ", personCode=" + personCode + ", educationalLevel="
				+ educationalLevel + ", disable=" + disable + ", salaryNumber="
				+ salaryNumber + ", idNumber=" + idNumber + ", jobTitle="
				+ jobTitle + ", ratio=" + ratio + ", status=" + status
				+ ", workBegin=" + workBegin + ", note=" + note
				+ ", department=" + department + ", departmentHis="
				+ departmentHis + ", departmentSnapCode=" + departmentSnapCode
				+ ", jjmark=" + jjmark + ", orgCode=" + orgCode + ", deleted="
				+ deleted + ", snapCode=" + snapCode + ", imagePath="
				+ imagePath + "]";
	}

}
