package com.huge.ihos.hr.hrPerson.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.hibernate.annotations.Formula;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.model.BaseObject;
import com.huge.util.annotation.AProperty;
@Entity
@Table( name = "hr_person_snap")
public class HrPersonSnap extends BaseObject implements Serializable ,Cloneable{
	private static final long serialVersionUID = 9073082388683015474L;
	private String snapId;
	private String snapCode;
	@AProperty(label="单位编码")
	private String orgCode;// 单位
	
	@AProperty(label="人员ID")
	private String personId;
	@AProperty(label="人员编码")
	private String personCode; // 人员编码
	@AProperty(label="姓名")
	private String name; // 姓名
	@AProperty(label="所在部门")
	private String deptId;
	@AProperty(label="职工类别")
	private String personType;
	private HrDepartmentCurrent hrDept;
	private HrDepartmentHis hrDeptHis;
	private String deptSnapCode;
	private PersonType empType; // 职工类别
	
	@AProperty(label="性别",diccode="sex")
	private String sex; // 性别
	private Boolean jjmark = true;
	private Boolean disabled = false; // 是否有效

	
	
	private HrOrg hrOrg;
	private HrOrgHis hrOrgHis;
	private String orgSnapCode;
	private Boolean deleted = false;//是否被删除

	@AProperty(label="出生日期")
	private Date birthday; // 出生日期
	@AProperty(label="年龄")
	private Integer age;// 年龄
	@AProperty(label="民族",diccode="nation")
	private String nation;// 民族
	@AProperty(label="政治面貌",diccode="personPol")
	private String politicalCode;// 政治面貌
	
	@AProperty(label="身份证号")
	private String idNumber; // 身份证号
	
	
	@AProperty(label="进单位时间")
	private Date entryDate; // 进单位时间
	@AProperty(label="岗位类别",diccode="postType")
	private String postType; // 岗位类别
	@AProperty(label="岗位职务")
	private Post duty; // 岗位职务
	@AProperty(label="职称",diccode="jobTitle")
	private String jobTitle; // 职称
	@AProperty(label="参加工作时间")
	private Date workBegin; // 参加工作时间
	@AProperty(label="现有职称")
	private String nowTitleCode;// 现有职称
	@AProperty(label="现有职称获得时间")
	private Date nowTitleTime;// 现有职称获得时间
	
	
	@AProperty(label="学历",diccode="education")
	private String educationalLevel; // 学历
	
	
	/* add for hr */
	// education info
	@AProperty(label="毕业学校")
	private String school;// 毕业学校
	@AProperty(label="专业",diccode="professional")
	private String profession;// 专业
	@AProperty(label="学位",diccode="degree")
	private String degree;// 学位
	
	@AProperty(label="毕业时间")
	private Date graduateDate;// 毕业时间
	@AProperty(label="最初学历")
	private String zcEduLevel;// 最初学历
	@AProperty(label="最初毕业学校")
	private String zcGraduateSchool;// 最初毕业学校
	@AProperty(label="最初毕业日期")
	private Date zcGraduateDate;// 最初毕业日期

	// person info
	@AProperty(label="工作电话")
	private String workPhone;// 工作电话
	@AProperty(label="手机号")
	private String mobilePhone;// 手机号码
	@AProperty(label="是否外籍")
	private Boolean foreigner;// 是否外籍
	@AProperty(label="邮箱")
	private String email;// Email
	
	private String cnCode;// 助记码
	
	@AProperty(label="系数")
	private Double ratio; // 系数
	@AProperty(label="备注")
	private String remark; // 备注

	// work info
	
	
	private String salaryWay;// 工资发放方式
	// other info
	
	
	private String salaryNumber; // 工资号
	

	private Boolean purchaser;// 是否采购员
	private Boolean payoff;// 是否发工资
	private String natureCode;// 职工属性
	private String imagePath;// 照片
	private String passWord;// 密码
	private String attenceCode;// 考勤人员类别
	private Date titleDate;// 职称评聘时间
	private Date dutyDate;// 职位任职时间
	private String station;// 人员身份
	private Integer nurseAge;// 护龄
	private Integer workAge;// 工龄
	private Integer softWorkAge;// 软工龄
	private String comeFrom;// 人员来源
	private String insuCode;// 社会保险号
	private Boolean formal;// 是否在编
	private String jobLevel;// wh
	private Double jobSalary;// wh
	private Double salary;// wh
	private Boolean teacherNurse;// 教护工标识
	private Date dealDate;// 办理时间
	private Date salaryBDate;// 起薪时间
	private String salaryLevel;// 薪级
	
	private String maritalStatus;// 婚姻状况
	private String nativePlace;// 籍贯
	private String domicilePlace;// 户口所在地
	private String homeTelephone;// 家庭电话
	private String houseAddress;// 家庭住址
	
	//lock
	private String lockState;//锁状态
	
	/*工资*/
	private Boolean stopSalary;
    private String stopReason;
    private String taxType;
	private String bank1;
    private String bank2;
    private String salaryNumber2;
	private String gzType;
	private String gzType2;

	//考勤附加字段
	private String kqType;//考勤类别
	private Boolean stopKq;
    private String stopKqReason;
    
    private Branch branch;
    private String branchCode;
	
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

	@Column(name = "personId", length = 50, nullable = false)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
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

	
	@Column(name = "personCode", length = 20, nullable = false)
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	@Column(name = "name", length = 20, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "sex", length = 10, nullable = false)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "deptId", nullable = false, insertable = false, updatable = false)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = true)
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
	
	@Column(name = "empType", nullable = false, insertable = false, updatable = false)
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empType", nullable = true)
	public PersonType getEmpType() {
		return empType;
	}

	public void setEmpType(PersonType empType) {
		this.empType = empType;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postType", nullable = true)*/
	@Column(name = "postType", nullable = true, length = 20)
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	@Column(name = "jobTitle", nullable = true, length = 20)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name = "ratio", nullable = true,precision=12,scale=4)
	public Double getRatio() {
		if(ratio != null){
			 BigDecimal bg = new BigDecimal(ratio);  
			 ratio = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
		}
		return ratio;
	}

	public void setRatio(Double ratio) {
		if(ratio != null){
			 BigDecimal bg = new BigDecimal(ratio);  
			 ratio = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
		}
		this.ratio = ratio;
	}

	@Column(name = "jjmark", nullable = true, columnDefinition = "bit default 0")
	public Boolean getJjmark() {
		return jjmark;
	}

	public void setJjmark(Boolean jjmark) {
		this.jjmark = jjmark;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "workBegin", length = 19, nullable = true)
	public Date getWorkBegin() {
		return workBegin;
	}

	public void setWorkBegin(Date workBegin) {
		this.workBegin = workBegin;
	}

	@Column(name = "orgCode", nullable = true, length = 10)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgCode", nullable = true, insertable = false, updatable = false)
	public HrOrg getHrOrg() {
		return hrOrg;
	}

	public void setHrOrg(HrOrg hrOrg) {
		this.hrOrg = hrOrg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "orgCode", nullable = true, insertable = false, updatable = false),
			@JoinColumn(name = "orgSnapCode", nullable = true, insertable = false, updatable = false) })
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

	@Column(name = "deleted", nullable = true, columnDefinition = "bit default 0")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	@Column(name = "zc_edu_level", nullable = true, length = 20)
	public String getZcEduLevel() {
		return zcEduLevel;
	}

	public void setZcEduLevel(String zcEduLevel) {
		this.zcEduLevel = zcEduLevel;
	}

	@Column(name = "zc_graduate_school", nullable = true, length = 50)
	public String getZcGraduateSchool() {
		return zcGraduateSchool;
	}

	public void setZcGraduateSchool(String zcGraduateSchool) {
		this.zcGraduateSchool = zcGraduateSchool;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "zc_graduate_date", length = 19, nullable = true)
	public Date getZcGraduateDate() {
		return zcGraduateDate;
	}

	public void setZcGraduateDate(Date zcGraduateDate) {
		this.zcGraduateDate = zcGraduateDate;
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

	@Column(name = "is_foreigner", nullable = true, columnDefinition = "bit default 0")
	public Boolean getForeigner() {
		return foreigner;
	}

	public void setForeigner(Boolean foreigner) {
		this.foreigner = foreigner;
	}

	@Column(name = "email", nullable = true, length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "political_code", nullable = true, length = 20)
	public String getPoliticalCode() {
		return politicalCode;
	}

	public void setPoliticalCode(String politicalCode) {
		this.politicalCode = politicalCode;
	}
	
	@Column(name = "cnCode", nullable = true, length = 20)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@Column(name = "nation", nullable = true, length = 20)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "age", nullable = true)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entry_date", length = 19, nullable = true)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "duty", nullable = true)
	public Post getDuty() {
		return duty;
	}

	public void setDuty(Post duty) {
		this.duty = duty;
	}

	@Column(name = "salary_way", nullable = true, length = 20)
	public String getSalaryWay() {
		return salaryWay;
	}

	public void setSalaryWay(String salaryWay) {
		this.salaryWay = salaryWay;
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

	@Column(name = "is_purchaser", nullable = true, columnDefinition = "bit default 0")
	public Boolean getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(Boolean purchaser) {
		this.purchaser = purchaser;
	}

	@Column(name = "is_payoff", nullable = true, columnDefinition = "bit default 0")
	public Boolean getPayoff() {
		return payoff;
	}

	public void setPayoff(Boolean payoff) {
		this.payoff = payoff;
	}

	@Column(name = "nature_code", nullable = true, length = 20)
	public String getNatureCode() {
		return natureCode;
	}

	public void setNatureCode(String natureCode) {
		this.natureCode = natureCode;
	}

	@Column(name = "image_path", nullable = true, length = 100)
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "password", nullable = true, length = 50)
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Column(name = "attence_code", nullable = true, length = 20)
	public String getAttenceCode() {
		return attenceCode;
	}

	public void setAttenceCode(String attenceCode) {
		this.attenceCode = attenceCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "title_date", length = 19, nullable = true)
	public Date getTitleDate() {
		return titleDate;
	}

	public void setTitleDate(Date titleDate) {
		this.titleDate = titleDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "duty_date", length = 19, nullable = true)
	public Date getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}

	@Column(name = "station", nullable = true, length = 20)
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
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

	@Column(name = "come_from", nullable = true, length = 20)
	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	@Column(name = "insu_code", nullable = true, length = 20)
	public String getInsuCode() {
		return insuCode;
	}

	public void setInsuCode(String insuCode) {
		this.insuCode = insuCode;
	}

	@Column(name = "is_formal", nullable = true, columnDefinition = "bit default 0")
	public Boolean getFormal() {
		return formal;
	}

	public void setFormal(Boolean formal) {
		this.formal = formal;
	}

	@Column(name = "job_level", nullable = true, length = 20)
	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	@Column(name = "job_salary", nullable = true, precision = 12, scale = 2)
	public Double getJobSalary() {
		return jobSalary;
	}

	public void setJobSalary(Double jobSalary) {
		this.jobSalary = jobSalary;
	}

	@Column(name = "salary", nullable = true, precision = 12, scale = 2)
	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Column(name = "teacher_nurse", nullable = true, columnDefinition = "bit default 0")
	public Boolean getTeacherNurse() {
		return teacherNurse;
	}

	public void setTeacherNurse(Boolean teacherNurse) {
		this.teacherNurse = teacherNurse;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deal_date", length = 19, nullable = true)
	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "salary_bdate", length = 19, nullable = true)
	public Date getSalaryBDate() {
		return salaryBDate;
	}

	public void setSalaryBDate(Date salaryBDate) {
		this.salaryBDate = salaryBDate;
	}

	@Column(name = "salary_level", nullable = true, length = 10)
	public String getSalaryLevel() {
		return salaryLevel;
	}

	public void setSalaryLevel(String salaryLevel) {
		this.salaryLevel = salaryLevel;
	}

	@Column(name = "now_title_code", nullable = true, length = 20)
	public String getNowTitleCode() {
		return nowTitleCode;
	}

	public void setNowTitleCode(String nowTitleCode) {
		this.nowTitleCode = nowTitleCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "now_title_time", length = 19, nullable = true)
	public Date getNowTitleTime() {
		return nowTitleTime;
	}

	public void setNowTitleTime(Date nowTitleTime) {
		this.nowTitleTime = nowTitleTime;
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

	@Column(name = "gzTypeId", length = 32, nullable = true)
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
		result = prime
				* result
				+ ((deptSnapCode == null) ? 0 : deptSnapCode
						.hashCode());
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result
				+ ((attenceCode == null) ? 0 : attenceCode.hashCode());
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((comeFrom == null) ? 0 : comeFrom.hashCode());
		result = prime * result
				+ ((dealDate == null) ? 0 : dealDate.hashCode());
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((duty == null) ? 0 : duty.hashCode());
		result = prime * result
				+ ((dutyDate == null) ? 0 : dutyDate.hashCode());
		result = prime
				* result
				+ ((educationalLevel == null) ? 0 : educationalLevel.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((empType == null) ? 0 : empType.hashCode());
		result = prime * result
				+ ((entryDate == null) ? 0 : entryDate.hashCode());
		result = prime * result
				+ ((foreigner == null) ? 0 : foreigner.hashCode());
		result = prime * result + ((formal == null) ? 0 : formal.hashCode());
		result = prime * result
				+ ((graduateDate == null) ? 0 : graduateDate.hashCode());
		result = prime * result
				+ ((hrDept == null) ? 0 : hrDept.hashCode());
		result = prime * result
				+ ((hrDeptHis == null) ? 0 : hrDeptHis.hashCode());
		result = prime * result
				+ ((idNumber == null) ? 0 : idNumber.hashCode());
		result = prime * result
				+ ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result
				+ ((insuCode == null) ? 0 : insuCode.hashCode());
		result = prime * result + ((jjmark == null) ? 0 : jjmark.hashCode());
		result = prime * result
				+ ((jobLevel == null) ? 0 : jobLevel.hashCode());
		result = prime * result
				+ ((jobSalary == null) ? 0 : jobSalary.hashCode());
		result = prime * result
				+ ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result
				+ ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nation == null) ? 0 : nation.hashCode());
		result = prime * result
				+ ((natureCode == null) ? 0 : natureCode.hashCode());
		result = prime * result
				+ ((nowTitleCode == null) ? 0 : nowTitleCode.hashCode());
		result = prime * result
				+ ((nowTitleTime == null) ? 0 : nowTitleTime.hashCode());
		result = prime * result
				+ ((nurseAge == null) ? 0 : nurseAge.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((passWord == null) ? 0 : passWord.hashCode());
		result = prime * result + ((payoff == null) ? 0 : payoff.hashCode());
		result = prime * result
				+ ((personCode == null) ? 0 : personCode.hashCode());
		result = prime * result
				+ ((personId == null) ? 0 : personId.hashCode());
		result = prime * result
				+ ((politicalCode == null) ? 0 : politicalCode.hashCode());
		result = prime * result
				+ ((postType == null) ? 0 : postType.hashCode());
		result = prime * result
				+ ((profession == null) ? 0 : profession.hashCode());
		result = prime * result
				+ ((purchaser == null) ? 0 : purchaser.hashCode());
		result = prime * result + ((ratio == null) ? 0 : ratio.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result
				+ ((salaryBDate == null) ? 0 : salaryBDate.hashCode());
		result = prime * result
				+ ((salaryLevel == null) ? 0 : salaryLevel.hashCode());
		result = prime * result
				+ ((salaryNumber == null) ? 0 : salaryNumber.hashCode());
		result = prime * result
				+ ((salaryWay == null) ? 0 : salaryWay.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
		result = prime * result + ((snapId == null) ? 0 : snapId.hashCode());
		result = prime * result
				+ ((softWorkAge == null) ? 0 : softWorkAge.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		result = prime * result
				+ ((teacherNurse == null) ? 0 : teacherNurse.hashCode());
		result = prime * result
				+ ((titleDate == null) ? 0 : titleDate.hashCode());
		result = prime * result + ((workAge == null) ? 0 : workAge.hashCode());
		result = prime * result
				+ ((workBegin == null) ? 0 : workBegin.hashCode());
		result = prime * result
				+ ((workPhone == null) ? 0 : workPhone.hashCode());
		result = prime * result
				+ ((zcEduLevel == null) ? 0 : zcEduLevel.hashCode());
		result = prime * result
				+ ((zcGraduateDate == null) ? 0 : zcGraduateDate.hashCode());
		result = prime
				* result
				+ ((zcGraduateSchool == null) ? 0 : zcGraduateSchool.hashCode());
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
		HrPersonSnap other = (HrPersonSnap) obj;
		if (deptSnapCode == null) {
			if (other.deptSnapCode != null)
				return false;
		} else if (!deptSnapCode.equals(other.deptSnapCode))
			return false;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (attenceCode == null) {
			if (other.attenceCode != null)
				return false;
		} else if (!attenceCode.equals(other.attenceCode))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (comeFrom == null) {
			if (other.comeFrom != null)
				return false;
		} else if (!comeFrom.equals(other.comeFrom))
			return false;
		if (dealDate == null) {
			if (other.dealDate != null)
				return false;
		} else if (!dealDate.equals(other.dealDate))
			return false;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (duty == null) {
			if (other.duty != null)
				return false;
		} else if (!duty.equals(other.duty))
			return false;
		if (dutyDate == null) {
			if (other.dutyDate != null)
				return false;
		} else if (!dutyDate.equals(other.dutyDate))
			return false;
		if (educationalLevel == null) {
			if (other.educationalLevel != null)
				return false;
		} else if (!educationalLevel.equals(other.educationalLevel))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (empType == null) {
			if (other.empType != null)
				return false;
		} else if (!empType.equals(other.empType))
			return false;
		if (entryDate == null) {
			if (other.entryDate != null)
				return false;
		} else if (!entryDate.equals(other.entryDate))
			return false;
		if (foreigner == null) {
			if (other.foreigner != null)
				return false;
		} else if (!foreigner.equals(other.foreigner))
			return false;
		if (formal == null) {
			if (other.formal != null)
				return false;
		} else if (!formal.equals(other.formal))
			return false;
		if (graduateDate == null) {
			if (other.graduateDate != null)
				return false;
		} else if (!graduateDate.equals(other.graduateDate))
			return false;
		if (hrDept == null) {
			if (other.hrDept != null)
				return false;
		} else if (!hrDept.equals(other.hrDept))
			return false;
		if (hrDeptHis == null) {
			if (other.hrDeptHis != null)
				return false;
		} else if (!hrDeptHis.equals(other.hrDeptHis))
			return false;
		if (idNumber == null) {
			if (other.idNumber != null)
				return false;
		} else if (!idNumber.equals(other.idNumber))
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (insuCode == null) {
			if (other.insuCode != null)
				return false;
		} else if (!insuCode.equals(other.insuCode))
			return false;
		if (jjmark == null) {
			if (other.jjmark != null)
				return false;
		} else if (!jjmark.equals(other.jjmark))
			return false;
		if (jobLevel == null) {
			if (other.jobLevel != null)
				return false;
		} else if (!jobLevel.equals(other.jobLevel))
			return false;
		if (jobSalary == null) {
			if (other.jobSalary != null)
				return false;
		} else if (!jobSalary.equals(other.jobSalary))
			return false;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
			return false;
		if (mobilePhone == null) {
			if (other.mobilePhone != null)
				return false;
		} else if (!mobilePhone.equals(other.mobilePhone))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nation == null) {
			if (other.nation != null)
				return false;
		} else if (!nation.equals(other.nation))
			return false;
		if (natureCode == null) {
			if (other.natureCode != null)
				return false;
		} else if (!natureCode.equals(other.natureCode))
			return false;
		if (nowTitleCode == null) {
			if (other.nowTitleCode != null)
				return false;
		} else if (!nowTitleCode.equals(other.nowTitleCode))
			return false;
		if (nowTitleTime == null) {
			if (other.nowTitleTime != null)
				return false;
		} else if (!nowTitleTime.equals(other.nowTitleTime))
			return false;
		if (nurseAge == null) {
			if (other.nurseAge != null)
				return false;
		} else if (!nurseAge.equals(other.nurseAge))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (passWord == null) {
			if (other.passWord != null)
				return false;
		} else if (!passWord.equals(other.passWord))
			return false;
		if (payoff == null) {
			if (other.payoff != null)
				return false;
		} else if (!payoff.equals(other.payoff))
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
		if (politicalCode == null) {
			if (other.politicalCode != null)
				return false;
		} else if (!politicalCode.equals(other.politicalCode))
			return false;
		if (postType == null) {
			if (other.postType != null)
				return false;
		} else if (!postType.equals(other.postType))
			return false;
		if (profession == null) {
			if (other.profession != null)
				return false;
		} else if (!profession.equals(other.profession))
			return false;
		if (purchaser == null) {
			if (other.purchaser != null)
				return false;
		} else if (!purchaser.equals(other.purchaser))
			return false;
		if (ratio == null) {
			if (other.ratio != null)
				return false;
		} else if (!ratio.equals(other.ratio))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		if (salaryBDate == null) {
			if (other.salaryBDate != null)
				return false;
		} else if (!salaryBDate.equals(other.salaryBDate))
			return false;
		if (salaryLevel == null) {
			if (other.salaryLevel != null)
				return false;
		} else if (!salaryLevel.equals(other.salaryLevel))
			return false;
		if (salaryNumber == null) {
			if (other.salaryNumber != null)
				return false;
		} else if (!salaryNumber.equals(other.salaryNumber))
			return false;
		if (salaryWay == null) {
			if (other.salaryWay != null)
				return false;
		} else if (!salaryWay.equals(other.salaryWay))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
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
		if (snapId == null) {
			if (other.snapId != null)
				return false;
		} else if (!snapId.equals(other.snapId))
			return false;
		if (softWorkAge == null) {
			if (other.softWorkAge != null)
				return false;
		} else if (!softWorkAge.equals(other.softWorkAge))
			return false;
		if (station == null) {
			if (other.station != null)
				return false;
		} else if (!station.equals(other.station))
			return false;
		if (teacherNurse == null) {
			if (other.teacherNurse != null)
				return false;
		} else if (!teacherNurse.equals(other.teacherNurse))
			return false;
		if (titleDate == null) {
			if (other.titleDate != null)
				return false;
		} else if (!titleDate.equals(other.titleDate))
			return false;
		if (workAge == null) {
			if (other.workAge != null)
				return false;
		} else if (!workAge.equals(other.workAge))
			return false;
		if (workBegin == null) {
			if (other.workBegin != null)
				return false;
		} else if (!workBegin.equals(other.workBegin))
			return false;
		if (workPhone == null) {
			if (other.workPhone != null)
				return false;
		} else if (!workPhone.equals(other.workPhone))
			return false;
		if (zcEduLevel == null) {
			if (other.zcEduLevel != null)
				return false;
		} else if (!zcEduLevel.equals(other.zcEduLevel))
			return false;
		if (zcGraduateDate == null) {
			if (other.zcGraduateDate != null)
				return false;
		} else if (!zcGraduateDate.equals(other.zcGraduateDate))
			return false;
		if (zcGraduateSchool == null) {
			if (other.zcGraduateSchool != null)
				return false;
		} else if (!zcGraduateSchool.equals(other.zcGraduateSchool))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrPersonSnap [snapId=" + snapId + ", snapCode=" + snapCode
				+ ", personId=" + personId + ", personCode=" + personCode
				+ ", name=" + name + ", sex=" + sex + ", hrDept="
				+ hrDept + ", hrDeptHis=" + hrDeptHis
				+ ", deptSnapCode=" + deptSnapCode + ", empType="
				+ empType + ", postType=" + postType + ", jobTitle=" + jobTitle
				+ ", ratio=" + ratio + ", jjmark=" + jjmark + ", disabled="
				+ disabled + ", workBegin=" + workBegin + ", orgCode="
				+ orgCode + ", school=" + school + ", profession=" + profession
				+ ", degree=" + degree + ", graduateDate=" + graduateDate
				+ ", zcEduLevel=" + zcEduLevel + ", zcGraduateSchool="
				+ zcGraduateSchool + ", zcGraduateDate=" + zcGraduateDate
				+ ", workPhone=" + workPhone + ", mobilePhone=" + mobilePhone
				+ ", foreigner=" + foreigner + ", email=" + email
				+ ", politicalCode=" + politicalCode + ", cnCode=" + cnCode
				+ ", nation=" + nation + ", age=" + age + ", remark=" + remark
				+ ", entryDate=" + entryDate + ", duty=" + duty
				+ ", salaryWay=" + salaryWay + ", birthday=" + birthday
				+ ", educationalLevel=" + educationalLevel + ", salaryNumber="
				+ salaryNumber + ", idNumber=" + idNumber + ", purchaser="
				+ purchaser + ", payoff=" + payoff + ", natureCode="
				+ natureCode + ", imagePath=" + imagePath + ", passWord="
				+ passWord + ", attenceCode=" + attenceCode + ", titleDate="
				+ titleDate + ", dutyDate=" + dutyDate + ", station=" + station
				+ ", nurseAge=" + nurseAge + ", workAge=" + workAge
				+ ", softWorkAge=" + softWorkAge + ", comeFrom=" + comeFrom
				+ ", insuCode=" + insuCode + ", formal=" + formal
				+ ", jobLevel=" + jobLevel + ", jobSalary=" + jobSalary
				+ ", salary=" + salary + ", teacherNurse=" + teacherNurse
				+ ", dealDate=" + dealDate + ", salaryBDate=" + salaryBDate
				+ ", salaryLevel=" + salaryLevel + ", nowTitleCode="
				+ nowTitleCode + ", nowTitleTime=" + nowTitleTime + "]";
	}
	@Transient
	public Map<String,Object> getMapEntity(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("snapId", this.snapId);
		map.put("snapCode", this.snapCode);
		map.put("personId", this.personId);
		map.put("personCode", this.personCode);
		map.put("name", this.name);
		map.put("sex", this.sex);
		Object deptId = null;
		if(this.hrDept==null){
			if(this.hrDeptHis==null){
				deptId = null;
			}else{
				deptId = this.hrDeptHis.getHrDeptPk().getDeptId();
			}
		}else{
			deptId = this.hrDept.getDepartmentId();
		}
		map.put("deptId", deptId);
		map.put("dept_snapCode", this.deptSnapCode);
		map.put("empType", empType==null?null:this.empType.getId());
		map.put("postType", this.postType);
		map.put("jobTitle", this.jobTitle);
		map.put("ratio", this.ratio);
		map.put("jjmark", this.jjmark);
		map.put("disabled", this.disabled);
		map.put("workBegin", this.workBegin);
		map.put("orgCode", this.orgCode);
		map.put("orgSnapCode", this.orgSnapCode);
		map.put("deleted", this.deleted);
		map.put("school", this.school);
		map.put("profession", this.profession);
		map.put("degree", this.degree);
		map.put("graduate_date", this.graduateDate);
		map.put("zc_edu_level", this.zcEduLevel);
		map.put("zc_graduate_school", this.zcGraduateSchool);
		map.put("zc_graduate_date", this.zcGraduateDate);
		map.put("work_phone", this.workPhone);
		map.put("mobile_phone", this.mobilePhone);
		map.put("is_foreigner", this.foreigner);
		map.put("email", this.email);
		map.put("political_code", this.politicalCode);
		map.put("cnCode", this.cnCode);
		map.put("nation", this.nation);
		map.put("age", this.age);
		map.put("remark", this.remark);
		map.put("entry_date", this.entryDate);
		map.put("duty", duty==null?null:this.duty.getId());
		map.put("salary_way", this.salaryWay);
		map.put("birthday", this.birthday);
		map.put("educationalLevel", this.educationalLevel);
		map.put("salaryNumber", this.salaryNumber);
		map.put("idNumber", this.idNumber);
		map.put("is_purchaser", this.purchaser);
		//map.put("is_payoff", this.payoff);
		map.put("nature_code", this.natureCode);
		map.put("image_path", this.imagePath);
		map.put("password", this.passWord);
		map.put("attence_code", this.attenceCode);
		map.put("title_date", this.titleDate);
		map.put("duty_date", this.dutyDate);
		map.put("station", this.station);
		map.put("nurse_age", this.nurseAge);
		map.put("work_age", this.workAge);
		map.put("soft_work_age", this.softWorkAge);
		map.put("come_from", this.comeFrom);
		map.put("insu_code", this.insuCode);
		map.put("is_formal", this.formal);
		map.put("job_level", this.jobLevel);
		map.put("job_salary", this.jobSalary);
		map.put("salary", this.salary);
		map.put("teacher_nurse", this.teacherNurse);
		map.put("deal_date", this.dealDate);
		map.put("salary_bdate", this.salaryBDate);
		map.put("salary_level", this.salaryLevel);
		map.put("now_title_code", this.nowTitleCode);
		map.put("now_title_time", this.nowTitleTime);
		map.put("marital_status", this.maritalStatus);
		map.put("native_place", this.nativePlace);
		map.put("domicile_place", this.domicilePlace);
		map.put("home_telephone", this.homeTelephone);
		map.put("house_address", this.houseAddress);
		//工资
		map.put("stopSalary", this.stopSalary);
		map.put("stopReason", this.stopReason);
		map.put("taxType", this.taxType);
		map.put("bank1", this.bank1);
		map.put("bank2", this.bank2);
		map.put("salaryNumber2", this.salaryNumber2);
		map.put("gzTypeId", this.gzType);
		//考勤
		map.put("kqTypeId", this.kqType);
		map.put("stopKq", this.stopKq);
		map.put("stopKqReason", this.stopKqReason);
		return map;
	}
	
	@Override
	public HrPersonSnap clone() {
		HrPersonSnap o = null;
		try {
			o = (HrPersonSnap) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}	
}
