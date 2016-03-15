package com.huge.ihos.hr.changesInPersonnel.model;

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

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.model.BaseObject;

@Entity
@Table(name = "hr_person_entry")
public class PersonEntry extends BaseObject implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8352208208144650135L;

	private String id; // 入职表ID
	private String code;
	private String personCode;//人员编号
	// String
	private String name; // 姓名
	// req
	private String sex; // 性别
	// dic
	private Date birthday; // 出生日期
	private String idNumber; // 身份证号
	private String educationalLevel; // 学历
	private String postType; // 岗位类别
	private Post duty;      //岗位职务
	private String jobTitle; // 职称
	private String remark; // 备注
	private HrDepartmentCurrent hrDept;
	private HrDepartmentHis hrDeptHis;
	private String deptSnapCode;
	private String mobilePhone;// 手机号码
	private String personPolCode;// 政治面貌
	private String imagePath;// 照片
	private String people;// 民族
	private String school;// 毕业学校
	private String email;// Email
	private String professional;// 专业
	private String degree;// 学位
	private Date graduateDay;// 毕业时间
	private Person maker;// 修改人
	private Date makeDate;// 修改时间
	private Person checker;// 审核人
	private Date checkDate;// 审核时间
	private String state;// 状态0:新建;1:已审核;2:已执行;
	private Date entryDate;//入职时间
	private Person doner;//执行人
	private Date doneDate;//执行时间


	private String maritalStatus;// 婚姻状况
	private String nativePlace;// 籍贯
	private String domicilePlace;// 户口所在地
	private String homeTelephone;// 家庭电话
	private String houseAddress;// 家庭住址
	private PersonType empType; // 职工类别
	private Integer age;// 年龄
	// 教育背景
	// 第一份
	private Date eduStartDateFirst;// 开始时间
	private Date eduEndDateFirst;// 结束时间
	private String schoolFirst;// 学校名称
	private String professionFirst;// 专业名称
	private String eduLevelFirst;// 学历
	// 第二份
	private Date eduStartDateSecond;// 开始时间
	private Date eduEndDateSecond;// 结束时间
	private String schoolSecond;// 学校名称
	private String professionSecond;// 专业名称
	private String eduLevelSecond;// 学历
	// 第三份
	private Date eduStartDateThird;// 开始时间
	private Date eduEndDateThird;// 结束时间
	private String schoolThird;// 学校名称
	private String professionThird;// 专业名称
	private String eduLevelThird;// 学历

	// 工作经历
	// 第一份
	private Date workStartDateFirst;// 开始时间
	private Date workEndDateFirst;// 结束时间
	private String workUnitFirst;// 工作单位
	private String workPostFirst;// 工作岗位
	private String payLevelFirst;// 薪资水平
	private String leavingReasonFirst;// 离职原因
	private String certifierFirst;// 证明人
	private String certifierPhoneFirst;// 联系方式
	// 第二份
	private Date workStartDateSecond;// 开始时间
	private Date workEndDateSecond;// 结束时间
	private String workUnitSecond;// 工作单位
	private String workPostSecond;// 工作岗位
	private String payLevelSecond;// 薪资水平
	private String leavingReasonSecond;// 离职原因
	private String certifierSecond;// 证明人
	private String certifierPhoneSecond;// 联系方式
	// 第三份
	private Date workStartDateThird;// 开始时间
	private Date workEndDateThird;// 结束时间
	private String workUnitThird;// 工作单位
	private String workPostThird;// 工作岗位
	private String payLevelThird;// 薪资水平
	private String leavingReasonThird;// 离职原因
	private String certifierThird;// 证明人
	private String certifierPhoneThird;// 联系方式

	// 家庭成员
	// 第一份
	private String familyTiesFirst;// 关系
	private String familyNameFirst;// 姓名
	private String familyWorkUnitFirst;// 工作单位
	private String familyPhoneFirst;// 联系方式
	// 第二份
	private String familyTiesSecond;// 关系
	private String familyNameSecond;// 姓名
	private String familyWorkUnitSecond;// 工作单位
	private String familyPhoneSecond;// 联系方式
	// 第三份
	private String familyTiesThird;// 关系
	private String familyNameThird;// 姓名
	private String familyWorkUnitThird;// 工作单位
	private String familyPhoneThird;// 联系方式
	// 紧急联系人
	private String emergencyContact;// 紧急联系人
	private String emergencyTies;// 关系
	private String emergencyPhone;// 联系方式
	private String yearMonth;
	private String personFrom;//入职人员来源
	private String personId;
	

	@Column(name = "code", length = 50, nullable = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	 @Column(name = "yearMonth", nullable = true, length = 6)
	  public String getYearMonth() {
	   return yearMonth;
	  }

	  public void setYearMonth(String yearMonth) {
	   this.yearMonth = yearMonth;
	  }
	@Column(name = "personCode", length = 20, nullable = false)
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	
	// snap
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = false)
	public HrDepartmentCurrent getHrDept() {
		return hrDept;
	}

	public void setHrDept(HrDepartmentCurrent hrDept) {
		this.hrDept = hrDept;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "deptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "dept_snapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getHrDeptHis() {
		return hrDeptHis;
	}

	public void setHrDeptHis(HrDepartmentHis hrDeptHis) {
		this.hrDeptHis = hrDeptHis;
	}

	@Column(name = "dept_snapCode", nullable = true, length = 14)
	public String getDeptSnapCode() {
		return deptSnapCode;
	}

	public void setDeptSnapCode(String deptSnapCode) {
		this.deptSnapCode = deptSnapCode;
	}

	// snap end

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doner", nullable = true)
	public Person getDoner() {
		return doner;
	}

	public void setDoner(Person doner) {
		this.doner = doner;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "doneDate", length = 19, nullable = true)
	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
	
	@Column(name = "idNumber", nullable = true, length = 20)
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "postType", nullable = true, length = 20)
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "duty", nullable = true)
	public Post getDuty() {
		return duty;
	}

	public void setDuty(Post duty) {
		this.duty = duty;
	}
	
	@Column(name = "jobTitle", nullable = true, length = 20)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday", length = 19, nullable = true)
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

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "mobile_phone", nullable = true, length = 20)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "email", nullable = true, length = 40)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "person_pol_code", nullable = true, length = 20)
	public String getPersonPolCode() {
		return personPolCode;
	}

	public void setPersonPolCode(String personPolCode) {
		this.personPolCode = personPolCode;
	}

	@Column(name = "image_path", nullable = true, length = 100)
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "school", nullable = true, length = 100)
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "professional", nullable = true, length = 20)
	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	@Column(name = "degree", nullable = true, length = 20)
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "graduate_day", length = 19, nullable = true)
	public Date getGraduateDay() {
		return graduateDay;
	}

	public void setGraduateDay(Date graduateDay) {
		this.graduateDay = graduateDay;
	}

	@Column(name = "people", nullable = true, length = 20)
	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = true)
	public Person getMaker() {
		return maker;
	}

	public void setMaker(Person maker) {
		this.maker = maker;
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
	public Person getChecker() {
		return checker;
	}

	public void setChecker(Person checker) {
		this.checker = checker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "state", length = 2, nullable = true)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime
				* result
				+ ((educationalLevel == null) ? 0 : educationalLevel.hashCode());
		result = prime * result
				+ ((idNumber == null) ? 0 : idNumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((postType == null) ? 0 : postType.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result
				+ ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((personPolCode == null) ? 0 : personPolCode.hashCode());
		result = prime * result
				+ ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		result = prime * result
				+ ((professional == null) ? 0 : professional.hashCode());
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result
				+ ((graduateDay == null) ? 0 : graduateDay.hashCode());
		result = prime * result + ((people == null) ? 0 : people.hashCode());
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
		PersonEntry other = (PersonEntry) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
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
		if (postType == null) {
			if (other.postType != null)
				return false;
		} else if (!postType.equals(other.postType))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (mobilePhone == null) {
			if (other.mobilePhone != null)
				return false;
		} else if (!mobilePhone.equals(other.mobilePhone))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (personPolCode == null) {
			if (other.personPolCode != null)
				return false;
		} else if (!personPolCode.equals(other.personPolCode))
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		if (professional == null) {
			if (other.professional != null)
				return false;
		} else if (!professional.equals(other.professional))
			return false;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (graduateDay == null) {
			if (other.graduateDay != null)
				return false;
		} else if (!graduateDay.equals(other.graduateDay))
			return false;
		if (people == null) {
			if (other.people != null)
				return false;
		} else if (!people.equals(other.people))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrPerson [name=" + name + ", sex=" + sex + ", birthday="
				+ birthday + ", educationalLevel=" + educationalLevel
				+ ", idNumber=" + idNumber + ", postType=" + postType
				+ ", remark=" + remark + ", mobilePhone=" + mobilePhone
				+ ", email=" + email + ", personPolCode=" + personPolCode
				+ ", imagePath=" + imagePath + ", school=" + school
				+ ", professional=" + professional + ", degree=" + degree
				+ ", graduateDay=" + graduateDay + ", people=" + people + "]";
	}

	@Column(name = "marital_status", nullable = true, length = 10)
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name = "native_place", nullable = true, length = 30)
	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	@Column(name = "domicile_place", nullable = true, length = 100)
	public String getDomicilePlace() {
		return domicilePlace;
	}

	public void setDomicilePlace(String domicilePlace) {
		this.domicilePlace = domicilePlace;
	}

	@Column(name = "home_telephone", nullable = true, length = 20)
	public String getHomeTelephone() {
		return homeTelephone;
	}

	public void setHomeTelephone(String homeTelephone) {
		this.homeTelephone = homeTelephone;
	}

	@Column(name = "house_address", nullable = true, length = 100)
	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edu_start_date_first", length = 19, nullable = true)
	public Date getEduStartDateFirst() {
		return eduStartDateFirst;
	}

	public void setEduStartDateFirst(Date eduStartDateFirst) {
		this.eduStartDateFirst = eduStartDateFirst;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edu_end_date_first", length = 19, nullable = true)
	public Date getEduEndDateFirst() {
		return eduEndDateFirst;
	}

	public void setEduEndDateFirst(Date eduEndDateFirst) {
		this.eduEndDateFirst = eduEndDateFirst;
	}

	@Column(name = "school_first", nullable = true, length = 30)
	public String getSchoolFirst() {
		return schoolFirst;
	}

	public void setSchoolFirst(String schoolFirst) {
		this.schoolFirst = schoolFirst;
	}

	@Column(name = "profession_first", nullable = true, length = 20)
	public String getProfessionFirst() {
		return professionFirst;
	}

	public void setProfessionFirst(String professionFirst) {
		this.professionFirst = professionFirst;
	}

	@Column(name = "edu_level_first", nullable = true, length = 10)
	public String getEduLevelFirst() {
		return eduLevelFirst;
	}

	public void setEduLevelFirst(String eduLevelFirst) {
		this.eduLevelFirst = eduLevelFirst;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edu_start_date_second", length = 19, nullable = true)
	public Date getEduStartDateSecond() {
		return eduStartDateSecond;
	}

	public void setEduStartDateSecond(Date eduStartDateSecond) {
		this.eduStartDateSecond = eduStartDateSecond;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edu_end_date_second", length = 19, nullable = true)
	public Date getEduEndDateSecond() {
		return eduEndDateSecond;
	}

	public void setEduEndDateSecond(Date eduEndDateSecond) {
		this.eduEndDateSecond = eduEndDateSecond;
	}

	@Column(name = "school_second", nullable = true, length = 30)
	public String getSchoolSecond() {
		return schoolSecond;
	}

	public void setSchoolSecond(String schoolSecond) {
		this.schoolSecond = schoolSecond;
	}

	@Column(name = "profession_second", nullable = true, length = 20)
	public String getProfessionSecond() {
		return professionSecond;
	}

	public void setProfessionSecond(String professionSecond) {
		this.professionSecond = professionSecond;
	}

	@Column(name = "edu_level_second", nullable = true, length = 10)
	public String getEduLevelSecond() {
		return eduLevelSecond;
	}

	public void setEduLevelSecond(String eduLevelSecond) {
		this.eduLevelSecond = eduLevelSecond;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edu_start_date_third", length = 19, nullable = true)
	public Date getEduStartDateThird() {
		return eduStartDateThird;
	}

	public void setEduStartDateThird(Date eduStartDateThird) {
		this.eduStartDateThird = eduStartDateThird;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edu_end_date_third", length = 19, nullable = true)
	public Date getEduEndDateThird() {
		return eduEndDateThird;
	}

	public void setEduEndDateThird(Date eduEndDateThird) {
		this.eduEndDateThird = eduEndDateThird;
	}

	@Column(name = "school_third", nullable = true, length = 30)
	public String getSchoolThird() {
		return schoolThird;
	}

	public void setSchoolThird(String schoolThird) {
		this.schoolThird = schoolThird;
	}

	@Column(name = "profession_third", nullable = true, length = 20)
	public String getProfessionThird() {
		return professionThird;
	}

	public void setProfessionThird(String professionThird) {
		this.professionThird = professionThird;
	}

	@Column(name = "edu_level_third", nullable = true, length = 10)
	public String getEduLevelThird() {
		return eduLevelThird;
	}

	public void setEduLevelThird(String eduLevelThird) {
		this.eduLevelThird = eduLevelThird;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "workStartDateFirst", length = 19, nullable = true)
	public Date getWorkStartDateFirst() {
		return workStartDateFirst;
	}

	public void setWorkStartDateFirst(Date workStartDateFirst) {
		this.workStartDateFirst = workStartDateFirst;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "workEndDateFirst", length = 19, nullable = true)
	public Date getWorkEndDateFirst() {
		return workEndDateFirst;
	}

	public void setWorkEndDateFirst(Date workEndDateFirst) {
		this.workEndDateFirst = workEndDateFirst;
	}

	@Column(name = "workUnitFirst", nullable = true, length = 30)
	public String getWorkUnitFirst() {
		return workUnitFirst;
	}

	public void setWorkUnitFirst(String workUnitFirst) {
		this.workUnitFirst = workUnitFirst;
	}

	@Column(name = "workPostFirst", nullable = true, length = 20)
	public String getWorkPostFirst() {
		return workPostFirst;
	}

	public void setWorkPostFirst(String workPostFirst) {
		this.workPostFirst = workPostFirst;
	}

	@Column(name = "payLevelFirst", nullable = true, length = 20)
	public String getPayLevelFirst() {
		return payLevelFirst;
	}

	public void setPayLevelFirst(String payLevelFirst) {
		this.payLevelFirst = payLevelFirst;
	}

	@Column(name = "leavingReasonFirst", nullable = true, length = 50)
	public String getLeavingReasonFirst() {
		return leavingReasonFirst;
	}

	public void setLeavingReasonFirst(String leavingReasonFirst) {
		this.leavingReasonFirst = leavingReasonFirst;
	}

	@Column(name = "certifierFirst", nullable = true, length = 20)
	public String getCertifierFirst() {
		return certifierFirst;
	}

	public void setCertifierFirst(String certifierFirst) {
		this.certifierFirst = certifierFirst;
	}

	@Column(name = "certifierPhoneFirst", nullable = true, length = 20)
	public String getCertifierPhoneFirst() {
		return certifierPhoneFirst;
	}

	public void setCertifierPhoneFirst(String certifierPhoneFirst) {
		this.certifierPhoneFirst = certifierPhoneFirst;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "workStartDateSecond", length = 19, nullable = true)
	public Date getWorkStartDateSecond() {
		return workStartDateSecond;
	}

	public void setWorkStartDateSecond(Date workStartDateSecond) {
		this.workStartDateSecond = workStartDateSecond;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "workEndDateSecond", length = 19, nullable = true)
	public Date getWorkEndDateSecond() {
		return workEndDateSecond;
	}

	public void setWorkEndDateSecond(Date workEndDateSecond) {
		this.workEndDateSecond = workEndDateSecond;
	}

	@Column(name = "workUnitSecond", nullable = true, length = 30)
	public String getWorkUnitSecond() {
		return workUnitSecond;
	}

	public void setWorkUnitSecond(String workUnitSecond) {
		this.workUnitSecond = workUnitSecond;
	}

	@Column(name = "workPostSecond", nullable = true, length = 20)
	public String getWorkPostSecond() {
		return workPostSecond;
	}

	public void setWorkPostSecond(String workPostSecond) {
		this.workPostSecond = workPostSecond;
	}

	@Column(name = "payLevelSecond", nullable = true, length = 20)
	public String getPayLevelSecond() {
		return payLevelSecond;
	}

	public void setPayLevelSecond(String payLevelSecond) {
		this.payLevelSecond = payLevelSecond;
	}

	@Column(name = "leavingReasonSecond", nullable = true, length = 50)
	public String getLeavingReasonSecond() {
		return leavingReasonSecond;
	}

	public void setLeavingReasonSecond(String leavingReasonSecond) {
		this.leavingReasonSecond = leavingReasonSecond;
	}

	@Column(name = "certifierSecond", nullable = true, length = 20)
	public String getCertifierSecond() {
		return certifierSecond;
	}

	public void setCertifierSecond(String certifierSecond) {
		this.certifierSecond = certifierSecond;
	}

	@Column(name = "certifierPhoneSecond", nullable = true, length = 20)
	public String getCertifierPhoneSecond() {
		return certifierPhoneSecond;
	}

	public void setCertifierPhoneSecond(String certifierPhoneSecond) {
		this.certifierPhoneSecond = certifierPhoneSecond;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "workStartDateThird", length = 19, nullable = true)
	public Date getWorkStartDateThird() {
		return workStartDateThird;
	}

	public void setWorkStartDateThird(Date workStartDateThird) {
		this.workStartDateThird = workStartDateThird;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "workEndDateThird", length = 19, nullable = true)
	public Date getWorkEndDateThird() {
		return workEndDateThird;
	}

	public void setWorkEndDateThird(Date workEndDateThird) {
		this.workEndDateThird = workEndDateThird;
	}

	@Column(name = "workUnitThird", nullable = true, length = 30)
	public String getWorkUnitThird() {
		return workUnitThird;
	}

	public void setWorkUnitThird(String workUnitThird) {
		this.workUnitThird = workUnitThird;
	}

	@Column(name = "workPostThird", nullable = true, length = 20)
	public String getWorkPostThird() {
		return workPostThird;
	}

	public void setWorkPostThird(String workPostThird) {
		this.workPostThird = workPostThird;
	}

	@Column(name = "payLevelThird", nullable = true, length = 20)
	public String getPayLevelThird() {
		return payLevelThird;
	}

	public void setPayLevelThird(String payLevelThird) {
		this.payLevelThird = payLevelThird;
	}

	@Column(name = "leavingReasonThird", nullable = true, length = 50)
	public String getLeavingReasonThird() {
		return leavingReasonThird;
	}

	public void setLeavingReasonThird(String leavingReasonThird) {
		this.leavingReasonThird = leavingReasonThird;
	}

	@Column(name = "certifierThird", nullable = true, length = 20)
	public String getCertifierThird() {
		return certifierThird;
	}

	public void setCertifierThird(String certifierThird) {
		this.certifierThird = certifierThird;
	}

	@Column(name = "certifierPhoneThird", nullable = true, length = 20)
	public String getCertifierPhoneThird() {
		return certifierPhoneThird;
	}

	public void setCertifierPhoneThird(String certifierPhoneThird) {
		this.certifierPhoneThird = certifierPhoneThird;
	}

	@Column(name = "familyTiesFirst", nullable = true, length = 20)
	public String getFamilyTiesFirst() {
		return familyTiesFirst;
	}

	public void setFamilyTiesFirst(String familyTiesFirst) {
		this.familyTiesFirst = familyTiesFirst;
	}

	@Column(name = "familyNameFirst", nullable = true, length = 20)
	public String getFamilyNameFirst() {
		return familyNameFirst;
	}

	public void setFamilyNameFirst(String familyNameFirst) {
		this.familyNameFirst = familyNameFirst;
	}

	@Column(name = "familyWorkUnitFirst", nullable = true, length = 30)
	public String getFamilyWorkUnitFirst() {
		return familyWorkUnitFirst;
	}

	public void setFamilyWorkUnitFirst(String familyWorkUnitFirst) {
		this.familyWorkUnitFirst = familyWorkUnitFirst;
	}

	@Column(name = "familyPhoneFirst", nullable = true, length = 20)
	public String getFamilyPhoneFirst() {
		return familyPhoneFirst;
	}

	public void setFamilyPhoneFirst(String familyPhoneFirst) {
		this.familyPhoneFirst = familyPhoneFirst;
	}

	@Column(name = "familyTiesSecond", nullable = true, length = 20)
	public String getFamilyTiesSecond() {
		return familyTiesSecond;
	}

	public void setFamilyTiesSecond(String familyTiesSecond) {
		this.familyTiesSecond = familyTiesSecond;
	}

	@Column(name = "familyNameSecond", nullable = true, length = 20)
	public String getFamilyNameSecond() {
		return familyNameSecond;
	}

	public void setFamilyNameSecond(String familyNameSecond) {
		this.familyNameSecond = familyNameSecond;
	}

	@Column(name = "familyWorkUnitSecond", nullable = true, length = 30)
	public String getFamilyWorkUnitSecond() {
		return familyWorkUnitSecond;
	}

	public void setFamilyWorkUnitSecond(String familyWorkUnitSecond) {
		this.familyWorkUnitSecond = familyWorkUnitSecond;
	}

	@Column(name = "familyPhoneSecond", nullable = true, length = 20)
	public String getFamilyPhoneSecond() {
		return familyPhoneSecond;
	}

	public void setFamilyPhoneSecond(String familyPhoneSecond) {
		this.familyPhoneSecond = familyPhoneSecond;
	}

	@Column(name = "familyTiesThird", nullable = true, length = 20)
	public String getFamilyTiesThird() {
		return familyTiesThird;
	}

	public void setFamilyTiesThird(String familyTiesThird) {
		this.familyTiesThird = familyTiesThird;
	}

	@Column(name = "familyNameThird", nullable = true, length = 20)
	public String getFamilyNameThird() {
		return familyNameThird;
	}

	public void setFamilyNameThird(String familyNameThird) {
		this.familyNameThird = familyNameThird;
	}

	@Column(name = "familyWorkUnitThird", nullable = true, length = 30)
	public String getFamilyWorkUnitThird() {
		return familyWorkUnitThird;
	}

	public void setFamilyWorkUnitThird(String familyWorkUnitThird) {
		this.familyWorkUnitThird = familyWorkUnitThird;
	}

	@Column(name = "familyPhoneThird", nullable = true, length = 20)
	public String getFamilyPhoneThird() {
		return familyPhoneThird;
	}

	public void setFamilyPhoneThird(String familyPhoneThird) {
		this.familyPhoneThird = familyPhoneThird;
	}

	@Column(name = "emergencyContact", nullable = true, length = 20)
	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	@Column(name = "EmergencyTies", nullable = true, length = 20)
	public String getEmergencyTies() {
		return emergencyTies;
	}

	public void setEmergencyTies(String emergencyTies) {
		this.emergencyTies = emergencyTies;
	}

	@Column(name = "emergencyPhone", nullable = true, length = 20)
	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entryDate", length = 19, nullable = true)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empType", nullable = true)
	public PersonType getEmpType() {
		return empType;
	}

	public void setEmpType(PersonType empType) {
		this.empType = empType;
	}
	@Column(name = "age", nullable = true)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public PersonEntry clone() {
		PersonEntry o = null;
		try {
			o = (PersonEntry) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	@Column(name = "personFrom", nullable = true, length = 20)
	public String getPersonFrom() {
		return personFrom;
	}

	public void setPersonFrom(String personFrom) {
		this.personFrom = personFrom;
	}
	@Column(name = "personId", length = 50, nullable = true)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
}
