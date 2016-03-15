package com.huge.ihos.hr.recruitResume.model;

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
import com.huge.ihos.hr.recruitPlan.model.RecruitPlan;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "hr_recruit_resume")
public class RecruitResume extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6986500793797603909L;
	private String id;// 主键
	private HrDepartmentCurrent hrDept;//部门
	private HrDepartmentHis hrDeptHis;
	private String deptSnapCode;
	private Post post;
	private String code;// 编号
	private String viewState;//浏览状态
	private Integer age;
	

	// 个人信息
	private String name;// 姓名
	private String remark;// 备注
	private String sex;// 性别
	private Date birthday;// 出生日期
	private Date workStartDate;// 参加工作时间
	private String domicilePlace;// 户口所在地
	private String presentResidence;// 现居住地
	private String contactWay;// 联系方式
	private String email;// 电子邮箱
	private String maritalStatus;// 婚姻状况
	private String typeOfId;// 证件类型
	private String idNumber;// 证件号码
	private Boolean overseaAssignment;// 海外工作/学习经历
	private String nation;// 民族
	private String politicsStatus;// 政治面貌
	private String photo;// 照片
	private String foreignLanguage;// 外语语种
	private String foreignLanguageLevel;// 外语水平
	private String interests;// 兴趣爱好
	private String selfAssessment;// 自我评价

	// 求职意向
	private String expectJobCategory;// 期望工作性质
	private String expectWorkplace;// 期望工作地点
	private String expectOccupation;// 期望从事职业
	private String expectIndustry;// 期望从事行业
	private String expectMonthlyPay;// 期望月薪
	private String workingCondition;// 工作状态

	// 工作经验
	// 第一份
	private String companyNameFirst;// 企业名称
	private String industryCategoryFirst;// 行业类别
	private String positionCategoryFirst;// 职位类别
	private String positionNameFirst;// 职位名称
	private Date workStartDateFirst;// 开始时间
	private Date workEndDateFirst;// 结束时间
	private String workMonthlyPayFirst;// 职位月薪
	private String workSpecificationFirst;// 工作描述
	// 第二份
	private String companyNameSecond;// 企业名称
	private String industryCategorySecond;// 行业类别
	private String positionCategorySecond;// 职位类别
	private String positionNameSecond;// 职位名称
	private Date workStartDateSecond;// 开始时间
	private Date workEndDateSecond;// 结束时间
	private String workMonthlyPaySecond;// 职位月薪
	private String workSpecificationSecond;// 工作描述
	// 第三份
	private String companyNameThird;// 企业名称
	private String industryCategoryThird;// 行业类别
	private String positionCategoryThird;// 职位类别
	private String positionNameThird;// 职位名称
	private Date workStartDateThird;// 开始时间
	private Date workEndDateThird;// 结束时间
	private String workMonthlyPayThird;// 职位月薪
	private String workSpecificationThird;// 工作描述

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

	// 描述
	private String projectExperience;// 项目经验描述
	private String professionalSkill;// 专业技能描述

	// 操作
	private RecruitPlan recruitPlan;// 应聘岗位
	private Date recruitDate;// 应聘时间
	private String favoriteState;// 收藏夹/人才库   0：；1：收藏夹；2：人才库
	private Person favoriteName;// 收藏人
	private Date favoriteDate;// 收藏时间
	private Person talentPoolName;// 入库人
	private Date talentPoolDate;// 入库时间
	private String state;// 状态   0:新建;1:审查合格;2:拟录用;3:已录用;4:面试不合格
	private Date qualifiedDate;// 审核合格日期
	private Person qualifieder;// 审核合格人
	private Date disqualificationDate;// 审核不合格日期
	private Person disqualificationer;// 审核不合格人

	// 面试与录用
	private String interviewState;// 面试通知状态   0:待通知;1:已通知;2:联系不上;3:已有工作;4:个人放弃
	private String interviewSpace;// 面试地点
	private String professionalExaminer;// 专业考官
	private String foreignLanguageExaminer;// 外语考官
	private Date examinerDate;// 面试时间
	private String interviewContacts;// 面试联系人
	private String interviewContactWay;// 面试联系方式
	private Double examineGrade;// 考评总分
	private String examineEvaluate;// 考评评价
	private Date reportDate;// 报到时间
	private String reportContacts;// 报到联系人
	private String reportContactWay;// 报到联系方式
	private String yearMonth;
	 
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
	 @Column(name = "yearMonth", nullable = true, length = 6)
	 public String getYearMonth() {
	  return yearMonth;
	 }

	 public void setYearMonth(String yearMonth) {
	  this.yearMonth = yearMonth;
	 }
	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "state", nullable = true, length = 2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Column(name = "viewState", nullable = true, length = 2)
	public String getViewState() {
		return viewState;
	}

	public void setViewState(String viewState) {
		this.viewState = viewState;
	}
	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "name", nullable = true, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	 // snap end
	 @ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "post", nullable = true)
		public Post getPost() {
			return post;
		}

		public void setPost(Post post) {
			this.post = post;
		}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday", length = 19, nullable = true)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "work_start_date", length = 19, nullable = true)
	public Date getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	@Column(name = "domicile_place", nullable = true, length = 100)
	public String getDomicilePlace() {
		return domicilePlace;
	}

	public void setDomicilePlace(String domicilePlace) {
		this.domicilePlace = domicilePlace;
	}

	@Column(name = "present_residence", nullable = true, length = 50)
	public String getPresentResidence() {
		return presentResidence;
	}

	public void setPresentResidence(String presentResidence) {
		this.presentResidence = presentResidence;
	}

	@Column(name = "marital_status", nullable = true, length = 10)
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name = "email", nullable = true, length = 20)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "contact_way", nullable = true, length = 20)
	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	@Column(name = "type_of_id", nullable = true, length = 20)
	public String getTypeOfId() {
		return typeOfId;
	}

	public void setTypeOfId(String typeOfId) {
		this.typeOfId = typeOfId;
	}

	@Column(name = "id_number", nullable = true, length = 20)
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "oversea_assignment", nullable = true, columnDefinition = "bit default 0")
	public Boolean getOverseaAssignment() {
		return overseaAssignment;
	}

	public void setOverseaAssignment(Boolean overseaAssignment) {
		this.overseaAssignment = overseaAssignment;
	}

	@Column(name = "nation", nullable = true, length = 10)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "politics_status", nullable = true, length = 20)
	public String getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	@Column(name = "photo", nullable = true, length = 100)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "foreign_language_level", nullable = true, length = 20)
	public String getForeignLanguageLevel() {
		return foreignLanguageLevel;
	}

	public void setForeignLanguageLevel(String foreignLanguageLevel) {
		this.foreignLanguageLevel = foreignLanguageLevel;
	}

	@Column(name = "foreign_language", nullable = true, length = 20)
	public String getForeignLanguage() {
		return foreignLanguage;
	}

	public void setForeignLanguage(String foreignLanguage) {
		this.foreignLanguage = foreignLanguage;
	}

	@Column(name = "interests", nullable = true, length = 200)
	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	@Column(name = "self_assessment", nullable = true, length = 200)
	public String getSelfAssessment() {
		return selfAssessment;
	}

	public void setSelfAssessment(String selfAssessment) {
		this.selfAssessment = selfAssessment;
	}

	@Column(name = "expect_job_category", nullable = true, length = 20)
	public String getExpectJobCategory() {
		return expectJobCategory;
	}

	public void setExpectJobCategory(String expectJobCategory) {
		this.expectJobCategory = expectJobCategory;
	}

	@Column(name = "expect_workplace", nullable = true, length = 20)
	public String getExpectWorkplace() {
		return expectWorkplace;
	}

	public void setExpectWorkplace(String expectWorkplace) {
		this.expectWorkplace = expectWorkplace;
	}

	@Column(name = "expect_occupation", nullable = true, length = 20)
	public String getExpectOccupation() {
		return expectOccupation;
	}

	public void setExpectOccupation(String expectOccupation) {
		this.expectOccupation = expectOccupation;
	}

	@Column(name = "expect_industry", nullable = true, length = 20)
	public String getExpectIndustry() {
		return expectIndustry;
	}

	public void setExpectIndustry(String expectIndustry) {
		this.expectIndustry = expectIndustry;
	}

	@Column(name = "expect_monthly_pay", nullable = true, length = 20)
	public String getExpectMonthlyPay() {
		return expectMonthlyPay;
	}

	public void setExpectMonthlyPay(String expectMonthlyPay) {
		this.expectMonthlyPay = expectMonthlyPay;
	}

	@Column(name = "working_condition", nullable = true, length = 30)
	public String getWorkingCondition() {
		return workingCondition;
	}

	public void setWorkingCondition(String workingCondition) {
		this.workingCondition = workingCondition;
	}

	@Column(name = "company_name_first", nullable = true, length = 30)
	public String getCompanyNameFirst() {
		return companyNameFirst;
	}

	public void setCompanyNameFirst(String companyNameFirst) {
		this.companyNameFirst = companyNameFirst;
	}

	@Column(name = "industry_category_first", nullable = true, length = 20)
	public String getIndustryCategoryFirst() {
		return industryCategoryFirst;
	}

	public void setIndustryCategoryFirst(String industryCategoryFirst) {
		this.industryCategoryFirst = industryCategoryFirst;
	}

	@Column(name = "position_category_first", nullable = true, length = 20)
	public String getPositionCategoryFirst() {
		return positionCategoryFirst;
	}

	public void setPositionCategoryFirst(String positionCategoryFirst) {
		this.positionCategoryFirst = positionCategoryFirst;
	}

	@Column(name = "position_name_first", nullable = true, length = 20)
	public String getPositionNameFirst() {
		return positionNameFirst;
	}

	public void setPositionNameFirst(String positionNameFirst) {
		this.positionNameFirst = positionNameFirst;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "work_start_date_first", length = 19, nullable = true)
	public Date getWorkStartDateFirst() {
		return workStartDateFirst;
	}

	public void setWorkStartDateFirst(Date workStartDateFirst) {
		this.workStartDateFirst = workStartDateFirst;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "work_end_date_first", length = 19, nullable = true)
	public Date getWorkEndDateFirst() {
		return workEndDateFirst;
	}

	public void setWorkEndDateFirst(Date workEndDateFirst) {
		this.workEndDateFirst = workEndDateFirst;
	}

	@Column(name = "work_monthly_pay_first", nullable = true, length = 20)
	public String getWorkMonthlyPayFirst() {
		return workMonthlyPayFirst;
	}

	public void setWorkMonthlyPayFirst(String workMonthlyPayFirst) {
		this.workMonthlyPayFirst = workMonthlyPayFirst;
	}

	@Column(name = "work_specification_first", nullable = true, length = 200)
	public String getWorkSpecificationFirst() {
		return workSpecificationFirst;
	}

	public void setWorkSpecificationFirst(String workSpecificationFirst) {
		this.workSpecificationFirst = workSpecificationFirst;
	}

	@Column(name = "company_name_second", nullable = true, length = 30)
	public String getCompanyNameSecond() {
		return companyNameSecond;
	}

	public void setCompanyNameSecond(String companyNameSecond) {
		this.companyNameSecond = companyNameSecond;
	}

	@Column(name = "industry_category_second", nullable = true, length = 20)
	public String getIndustryCategorySecond() {
		return industryCategorySecond;
	}

	public void setIndustryCategorySecond(String industryCategorySecond) {
		this.industryCategorySecond = industryCategorySecond;
	}

	@Column(name = "position_category_second", nullable = true, length = 20)
	public String getPositionCategorySecond() {
		return positionCategorySecond;
	}

	public void setPositionCategorySecond(String positionCategorySecond) {
		this.positionCategorySecond = positionCategorySecond;
	}

	@Column(name = "position_name_second", nullable = true, length = 20)
	public String getPositionNameSecond() {
		return positionNameSecond;
	}

	public void setPositionNameSecond(String positionNameSecond) {
		this.positionNameSecond = positionNameSecond;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "work_start_date_second", length = 19, nullable = true)
	public Date getWorkStartDateSecond() {
		return workStartDateSecond;
	}

	public void setWorkStartDateSecond(Date workStartDateSecond) {
		this.workStartDateSecond = workStartDateSecond;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "work_end_date_second", length = 19, nullable = true)
	public Date getWorkEndDateSecond() {
		return workEndDateSecond;
	}

	public void setWorkEndDateSecond(Date workEndDateSecond) {
		this.workEndDateSecond = workEndDateSecond;
	}

	@Column(name = "work_monthly_pay_second", nullable = true, length = 20)
	public String getWorkMonthlyPaySecond() {
		return workMonthlyPaySecond;
	}

	public void setWorkMonthlyPaySecond(String workMonthlyPaySecond) {
		this.workMonthlyPaySecond = workMonthlyPaySecond;
	}

	@Column(name = "work_specification_second", nullable = true, length = 200)
	public String getWorkSpecificationSecond() {
		return workSpecificationSecond;
	}

	public void setWorkSpecificationSecond(String workSpecificationSecond) {
		this.workSpecificationSecond = workSpecificationSecond;
	}

	@Column(name = "company_name_third", nullable = true, length = 30)
	public String getCompanyNameThird() {
		return companyNameThird;
	}

	public void setCompanyNameThird(String companyNameThird) {
		this.companyNameThird = companyNameThird;
	}

	@Column(name = "industry_category_third", nullable = true, length = 20)
	public String getIndustryCategoryThird() {
		return industryCategoryThird;
	}

	public void setIndustryCategoryThird(String industryCategoryThird) {
		this.industryCategoryThird = industryCategoryThird;
	}

	@Column(name = "position_category_third", nullable = true, length = 20)
	public String getPositionCategoryThird() {
		return positionCategoryThird;
	}

	public void setPositionCategoryThird(String positionCategoryThird) {
		this.positionCategoryThird = positionCategoryThird;
	}

	@Column(name = "position_name_third", nullable = true, length = 20)
	public String getPositionNameThird() {
		return positionNameThird;
	}

	public void setPositionNameThird(String positionNameThird) {
		this.positionNameThird = positionNameThird;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "work_start_date_third", length = 19, nullable = true)
	public Date getWorkStartDateThird() {
		return workStartDateThird;
	}

	public void setWorkStartDateThird(Date workStartDateThird) {
		this.workStartDateThird = workStartDateThird;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "work_end_date_third", length = 19, nullable = true)
	public Date getWorkEndDateThird() {
		return workEndDateThird;
	}

	public void setWorkEndDateThird(Date workEndDateThird) {
		this.workEndDateThird = workEndDateThird;
	}

	@Column(name = "work_monthly_pay_third", nullable = true, length = 20)
	public String getWorkMonthlyPayThird() {
		return workMonthlyPayThird;
	}

	public void setWorkMonthlyPayThird(String workMonthlyPayThird) {
		this.workMonthlyPayThird = workMonthlyPayThird;
	}

	@Column(name = "work_specification_third", nullable = true, length = 200)
	public String getWorkSpecificationThird() {
		return workSpecificationThird;
	}

	public void setWorkSpecificationThird(String workSpecificationThird) {
		this.workSpecificationThird = workSpecificationThird;
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

	@Column(name = "project_experience", nullable = true, length = 200)
	public String getProjectExperience() {
		return projectExperience;
	}

	public void setProjectExperience(String projectExperience) {
		this.projectExperience = projectExperience;
	}

	@Column(name = "professional_skill", nullable = true, length = 200)
	public String getProfessionalSkill() {
		return professionalSkill;
	}

	public void setProfessionalSkill(String professionalSkill) {
		this.professionalSkill = professionalSkill;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recruit_plan", nullable = true)
	public RecruitPlan getRecruitPlan() {
		return recruitPlan;
	}

	public void setRecruitPlan(RecruitPlan recruitPlan) {
		this.recruitPlan = recruitPlan;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recruit_date", length = 19, nullable = true)
	public Date getRecruitDate() {
		return recruitDate;
	}

	public void setRecruitDate(Date recruitDate) {
		this.recruitDate = recruitDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "favorite_name", nullable = true)
	public Person getFavoriteName() {
		return favoriteName;
	}

	public void setFavoriteName(Person favoriteName) {
		this.favoriteName = favoriteName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "favorite_date", length = 19, nullable = true)
	public Date getFavoriteDate() {
		return favoriteDate;
	}

	public void setFavoriteDate(Date favoriteDate) {
		this.favoriteDate = favoriteDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "talent_pool_name", nullable = true)
	public Person getTalentPoolName() {
		return talentPoolName;
	}

	public void setTalentPoolName(Person talentPoolName) {
		this.talentPoolName = talentPoolName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "talent_pool_date", length = 19, nullable = true)
	public Date getTalentPoolDate() {
		return talentPoolDate;
	}

	public void setTalentPoolDate(Date talentPoolDate) {
		this.talentPoolDate = talentPoolDate;
	}

	@Column(name = "interview_state", nullable = true, length = 2)
	public String getInterviewState() {
		return interviewState;
	}

	public void setInterviewState(String interviewState) {
		this.interviewState = interviewState;
	}

	@Column(name = "professional_examiner", nullable = true, length = 20)
	public String getProfessionalExaminer() {
		return professionalExaminer;
	}

	public void setProfessionalExaminer(String professionalExaminer) {
		this.professionalExaminer = professionalExaminer;
	}

	@Column(name = "interview_space", nullable = true, length = 30)
	public String getInterviewSpace() {
		return interviewSpace;
	}

	public void setInterviewSpace(String interviewSpace) {
		this.interviewSpace = interviewSpace;
	}

	@Column(name = "foreign_language_examiner", nullable = true, length = 30)
	public String getForeignLanguageExaminer() {
		return foreignLanguageExaminer;
	}

	public void setForeignLanguageExaminer(String foreignLanguageExaminer) {
		this.foreignLanguageExaminer = foreignLanguageExaminer;
	}

	@Column(name = "interview_contact_way", nullable = true, length = 20)
	public String getInterviewContactWay() {
		return interviewContactWay;
	}

	public void setInterviewContactWay(String interviewContactWay) {
		this.interviewContactWay = interviewContactWay;
	}

	@Column(name = "interview_contacts", nullable = true, length = 20)
	public String getInterviewContacts() {
		return interviewContacts;
	}

	public void setInterviewContacts(String interviewContacts) {
		this.interviewContacts = interviewContacts;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "examiner_Date", length = 19, nullable = true)
	public Date getExaminerDate() {
		return examinerDate;
	}

	public void setExaminerDate(Date examinerDate) {
		this.examinerDate = examinerDate;
	}

	@Column(name = "examine_grade", nullable = true)
	public Double getExamineGrade() {
		return examineGrade;
	}

	public void setExamineGrade(Double examineGrade) {
		this.examineGrade = examineGrade;
	}

	@Column(name = "examine_evaluate", nullable = true, length = 200)
	public String getExamineEvaluate() {
		return examineEvaluate;
	}

	public void setExamineEvaluate(String examineEvaluate) {
		this.examineEvaluate = examineEvaluate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "report_date", length = 19, nullable = true)
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name = "report_contacts", nullable = true, length = 20)
	public String getReportContacts() {
		return reportContacts;
	}

	public void setReportContacts(String reportContacts) {
		this.reportContacts = reportContacts;
	}

	@Column(name = "report_contact_way", nullable = true, length = 20)
	public String getReportContactWay() {
		return reportContactWay;
	}

	public void setReportContactWay(String reportContactWay) {
		this.reportContactWay = reportContactWay;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "qualified_date", length = 19, nullable = true)
	public Date getQualifiedDate() {
		return qualifiedDate;
	}

	public void setQualifiedDate(Date qualifiedDate) {
		this.qualifiedDate = qualifiedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualifieder", nullable = true)
	public Person getQualifieder() {
		return qualifieder;
	}

	public void setQualifieder(Person qualifieder) {
		this.qualifieder = qualifieder;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "disqualification_date", length = 19, nullable = true)
	public Date getDisqualificationDate() {
		return disqualificationDate;
	}

	public void setDisqualificationDate(Date disqualificationDate) {
		this.disqualificationDate = disqualificationDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disqualificationer", nullable = true)
	public Person getDisqualificationer() {
		return disqualificationer;
	}

	public void setDisqualificationer(Person disqualificationer) {
		this.disqualificationer = disqualificationer;
	}

	@Column(name = "favorite_state", nullable = true, length = 2)
	public String getFavoriteState() {
		return favoriteState;
	}

	public void setFavoriteState(String favoriteState) {
		this.favoriteState = favoriteState;
	}

	@Column(name = "sex", nullable = true, length = 10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name = "age", nullable = true)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime
				* result
				+ ((companyNameFirst == null) ? 0 : companyNameFirst.hashCode());
		result = prime
				* result
				+ ((companyNameSecond == null) ? 0 : companyNameSecond
						.hashCode());
		result = prime
				* result
				+ ((companyNameThird == null) ? 0 : companyNameThird.hashCode());
		result = prime * result
				+ ((contactWay == null) ? 0 : contactWay.hashCode());
		result = prime
				* result
				+ ((disqualificationDate == null) ? 0 : disqualificationDate
						.hashCode());
		result = prime
				* result
				+ ((disqualificationer == null) ? 0 : disqualificationer
						.hashCode());
		result = prime * result
				+ ((domicilePlace == null) ? 0 : domicilePlace.hashCode());
		result = prime * result
				+ ((eduEndDateFirst == null) ? 0 : eduEndDateFirst.hashCode());
		result = prime
				* result
				+ ((eduEndDateSecond == null) ? 0 : eduEndDateSecond.hashCode());
		result = prime * result
				+ ((eduEndDateThird == null) ? 0 : eduEndDateThird.hashCode());
		result = prime * result
				+ ((eduLevelFirst == null) ? 0 : eduLevelFirst.hashCode());
		result = prime * result
				+ ((eduLevelSecond == null) ? 0 : eduLevelSecond.hashCode());
		result = prime * result
				+ ((eduLevelThird == null) ? 0 : eduLevelThird.hashCode());
		result = prime
				* result
				+ ((eduStartDateFirst == null) ? 0 : eduStartDateFirst
						.hashCode());
		result = prime
				* result
				+ ((eduStartDateSecond == null) ? 0 : eduStartDateSecond
						.hashCode());
		result = prime
				* result
				+ ((eduStartDateThird == null) ? 0 : eduStartDateThird
						.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((examineEvaluate == null) ? 0 : examineEvaluate.hashCode());
		result = prime * result
				+ ((examineGrade == null) ? 0 : examineGrade.hashCode());
		result = prime * result
				+ ((examinerDate == null) ? 0 : examinerDate.hashCode());
		result = prime * result
				+ ((expectIndustry == null) ? 0 : expectIndustry.hashCode());
		result = prime
				* result
				+ ((expectJobCategory == null) ? 0 : expectJobCategory
						.hashCode());
		result = prime
				* result
				+ ((expectMonthlyPay == null) ? 0 : expectMonthlyPay.hashCode());
		result = prime
				* result
				+ ((expectOccupation == null) ? 0 : expectOccupation.hashCode());
		result = prime * result
				+ ((expectWorkplace == null) ? 0 : expectWorkplace.hashCode());
		result = prime * result
				+ ((favoriteDate == null) ? 0 : favoriteDate.hashCode());
		result = prime * result
				+ ((favoriteName == null) ? 0 : favoriteName.hashCode());
		result = prime * result
				+ ((favoriteState == null) ? 0 : favoriteState.hashCode());
		result = prime * result
				+ ((foreignLanguage == null) ? 0 : foreignLanguage.hashCode());
		result = prime
				* result
				+ ((foreignLanguageExaminer == null) ? 0
						: foreignLanguageExaminer.hashCode());
		result = prime
				* result
				+ ((foreignLanguageLevel == null) ? 0 : foreignLanguageLevel
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idNumber == null) ? 0 : idNumber.hashCode());
		result = prime
				* result
				+ ((industryCategoryFirst == null) ? 0 : industryCategoryFirst
						.hashCode());
		result = prime
				* result
				+ ((industryCategorySecond == null) ? 0
						: industryCategorySecond.hashCode());
		result = prime
				* result
				+ ((industryCategoryThird == null) ? 0 : industryCategoryThird
						.hashCode());
		result = prime * result
				+ ((interests == null) ? 0 : interests.hashCode());
		result = prime
				* result
				+ ((interviewContactWay == null) ? 0 : interviewContactWay
						.hashCode());
		result = prime
				* result
				+ ((interviewContacts == null) ? 0 : interviewContacts
						.hashCode());
		result = prime * result
				+ ((interviewSpace == null) ? 0 : interviewSpace.hashCode());
		result = prime * result
				+ ((interviewState == null) ? 0 : interviewState.hashCode());
		result = prime * result
				+ ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nation == null) ? 0 : nation.hashCode());
		result = prime
				* result
				+ ((overseaAssignment == null) ? 0 : overseaAssignment
						.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result
				+ ((politicsStatus == null) ? 0 : politicsStatus.hashCode());
		result = prime
				* result
				+ ((positionCategoryFirst == null) ? 0 : positionCategoryFirst
						.hashCode());
		result = prime
				* result
				+ ((positionCategorySecond == null) ? 0
						: positionCategorySecond.hashCode());
		result = prime
				* result
				+ ((positionCategoryThird == null) ? 0 : positionCategoryThird
						.hashCode());
		result = prime
				* result
				+ ((positionNameFirst == null) ? 0 : positionNameFirst
						.hashCode());
		result = prime
				* result
				+ ((positionNameSecond == null) ? 0 : positionNameSecond
						.hashCode());
		result = prime
				* result
				+ ((positionNameThird == null) ? 0 : positionNameThird
						.hashCode());
		result = prime
				* result
				+ ((presentResidence == null) ? 0 : presentResidence.hashCode());
		result = prime * result
				+ ((professionFirst == null) ? 0 : professionFirst.hashCode());
		result = prime
				* result
				+ ((professionSecond == null) ? 0 : professionSecond.hashCode());
		result = prime * result
				+ ((professionThird == null) ? 0 : professionThird.hashCode());
		result = prime
				* result
				+ ((professionalExaminer == null) ? 0 : professionalExaminer
						.hashCode());
		result = prime
				* result
				+ ((professionalSkill == null) ? 0 : professionalSkill
						.hashCode());
		result = prime
				* result
				+ ((projectExperience == null) ? 0 : projectExperience
						.hashCode());
		result = prime * result
				+ ((qualifiedDate == null) ? 0 : qualifiedDate.hashCode());
		result = prime * result
				+ ((qualifieder == null) ? 0 : qualifieder.hashCode());
		result = prime * result
				+ ((recruitDate == null) ? 0 : recruitDate.hashCode());
		result = prime * result
				+ ((recruitPlan == null) ? 0 : recruitPlan.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime
				* result
				+ ((reportContactWay == null) ? 0 : reportContactWay.hashCode());
		result = prime * result
				+ ((reportContacts == null) ? 0 : reportContacts.hashCode());
		result = prime * result
				+ ((reportDate == null) ? 0 : reportDate.hashCode());
		result = prime * result
				+ ((schoolFirst == null) ? 0 : schoolFirst.hashCode());
		result = prime * result
				+ ((schoolSecond == null) ? 0 : schoolSecond.hashCode());
		result = prime * result
				+ ((schoolThird == null) ? 0 : schoolThird.hashCode());
		result = prime * result
				+ ((selfAssessment == null) ? 0 : selfAssessment.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((talentPoolDate == null) ? 0 : talentPoolDate.hashCode());
		result = prime * result
				+ ((talentPoolName == null) ? 0 : talentPoolName.hashCode());
		result = prime * result
				+ ((typeOfId == null) ? 0 : typeOfId.hashCode());
		result = prime
				* result
				+ ((workEndDateFirst == null) ? 0 : workEndDateFirst.hashCode());
		result = prime
				* result
				+ ((workEndDateSecond == null) ? 0 : workEndDateSecond
						.hashCode());
		result = prime
				* result
				+ ((workEndDateThird == null) ? 0 : workEndDateThird.hashCode());
		result = prime
				* result
				+ ((workMonthlyPayFirst == null) ? 0 : workMonthlyPayFirst
						.hashCode());
		result = prime
				* result
				+ ((workMonthlyPaySecond == null) ? 0 : workMonthlyPaySecond
						.hashCode());
		result = prime
				* result
				+ ((workMonthlyPayThird == null) ? 0 : workMonthlyPayThird
						.hashCode());
		result = prime
				* result
				+ ((workSpecificationFirst == null) ? 0
						: workSpecificationFirst.hashCode());
		result = prime
				* result
				+ ((workSpecificationSecond == null) ? 0
						: workSpecificationSecond.hashCode());
		result = prime
				* result
				+ ((workSpecificationThird == null) ? 0
						: workSpecificationThird.hashCode());
		result = prime * result
				+ ((workStartDate == null) ? 0 : workStartDate.hashCode());
		result = prime
				* result
				+ ((workStartDateFirst == null) ? 0 : workStartDateFirst
						.hashCode());
		result = prime
				* result
				+ ((workStartDateSecond == null) ? 0 : workStartDateSecond
						.hashCode());
		result = prime
				* result
				+ ((workStartDateThird == null) ? 0 : workStartDateThird
						.hashCode());
		result = prime
				* result
				+ ((workingCondition == null) ? 0 : workingCondition.hashCode());
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
		RecruitResume other = (RecruitResume) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (companyNameFirst == null) {
			if (other.companyNameFirst != null)
				return false;
		} else if (!companyNameFirst.equals(other.companyNameFirst))
			return false;
		if (companyNameSecond == null) {
			if (other.companyNameSecond != null)
				return false;
		} else if (!companyNameSecond.equals(other.companyNameSecond))
			return false;
		if (companyNameThird == null) {
			if (other.companyNameThird != null)
				return false;
		} else if (!companyNameThird.equals(other.companyNameThird))
			return false;
		if (contactWay == null) {
			if (other.contactWay != null)
				return false;
		} else if (!contactWay.equals(other.contactWay))
			return false;
		if (disqualificationDate == null) {
			if (other.disqualificationDate != null)
				return false;
		} else if (!disqualificationDate.equals(other.disqualificationDate))
			return false;
		if (disqualificationer == null) {
			if (other.disqualificationer != null)
				return false;
		} else if (!disqualificationer.equals(other.disqualificationer))
			return false;
		if (domicilePlace == null) {
			if (other.domicilePlace != null)
				return false;
		} else if (!domicilePlace.equals(other.domicilePlace))
			return false;
		if (eduEndDateFirst == null) {
			if (other.eduEndDateFirst != null)
				return false;
		} else if (!eduEndDateFirst.equals(other.eduEndDateFirst))
			return false;
		if (eduEndDateSecond == null) {
			if (other.eduEndDateSecond != null)
				return false;
		} else if (!eduEndDateSecond.equals(other.eduEndDateSecond))
			return false;
		if (eduEndDateThird == null) {
			if (other.eduEndDateThird != null)
				return false;
		} else if (!eduEndDateThird.equals(other.eduEndDateThird))
			return false;
		if (eduLevelFirst == null) {
			if (other.eduLevelFirst != null)
				return false;
		} else if (!eduLevelFirst.equals(other.eduLevelFirst))
			return false;
		if (eduLevelSecond == null) {
			if (other.eduLevelSecond != null)
				return false;
		} else if (!eduLevelSecond.equals(other.eduLevelSecond))
			return false;
		if (eduLevelThird == null) {
			if (other.eduLevelThird != null)
				return false;
		} else if (!eduLevelThird.equals(other.eduLevelThird))
			return false;
		if (eduStartDateFirst == null) {
			if (other.eduStartDateFirst != null)
				return false;
		} else if (!eduStartDateFirst.equals(other.eduStartDateFirst))
			return false;
		if (eduStartDateSecond == null) {
			if (other.eduStartDateSecond != null)
				return false;
		} else if (!eduStartDateSecond.equals(other.eduStartDateSecond))
			return false;
		if (eduStartDateThird == null) {
			if (other.eduStartDateThird != null)
				return false;
		} else if (!eduStartDateThird.equals(other.eduStartDateThird))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (examineEvaluate == null) {
			if (other.examineEvaluate != null)
				return false;
		} else if (!examineEvaluate.equals(other.examineEvaluate))
			return false;
		if (examineGrade == null) {
			if (other.examineGrade != null)
				return false;
		} else if (!examineGrade.equals(other.examineGrade))
			return false;
		if (examinerDate == null) {
			if (other.examinerDate != null)
				return false;
		} else if (!examinerDate.equals(other.examinerDate))
			return false;
		if (expectIndustry == null) {
			if (other.expectIndustry != null)
				return false;
		} else if (!expectIndustry.equals(other.expectIndustry))
			return false;
		if (expectJobCategory == null) {
			if (other.expectJobCategory != null)
				return false;
		} else if (!expectJobCategory.equals(other.expectJobCategory))
			return false;
		if (expectMonthlyPay == null) {
			if (other.expectMonthlyPay != null)
				return false;
		} else if (!expectMonthlyPay.equals(other.expectMonthlyPay))
			return false;
		if (expectOccupation == null) {
			if (other.expectOccupation != null)
				return false;
		} else if (!expectOccupation.equals(other.expectOccupation))
			return false;
		if (expectWorkplace == null) {
			if (other.expectWorkplace != null)
				return false;
		} else if (!expectWorkplace.equals(other.expectWorkplace))
			return false;
		if (favoriteDate == null) {
			if (other.favoriteDate != null)
				return false;
		} else if (!favoriteDate.equals(other.favoriteDate))
			return false;
		if (favoriteName == null) {
			if (other.favoriteName != null)
				return false;
		} else if (!favoriteName.equals(other.favoriteName))
			return false;
		if (favoriteState == null) {
			if (other.favoriteState != null)
				return false;
		} else if (!favoriteState.equals(other.favoriteState))
			return false;
		if (foreignLanguage == null) {
			if (other.foreignLanguage != null)
				return false;
		} else if (!foreignLanguage.equals(other.foreignLanguage))
			return false;
		if (foreignLanguageExaminer == null) {
			if (other.foreignLanguageExaminer != null)
				return false;
		} else if (!foreignLanguageExaminer
				.equals(other.foreignLanguageExaminer))
			return false;
		if (foreignLanguageLevel == null) {
			if (other.foreignLanguageLevel != null)
				return false;
		} else if (!foreignLanguageLevel.equals(other.foreignLanguageLevel))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idNumber == null) {
			if (other.idNumber != null)
				return false;
		} else if (!idNumber.equals(other.idNumber))
			return false;
		if (industryCategoryFirst == null) {
			if (other.industryCategoryFirst != null)
				return false;
		} else if (!industryCategoryFirst.equals(other.industryCategoryFirst))
			return false;
		if (industryCategorySecond == null) {
			if (other.industryCategorySecond != null)
				return false;
		} else if (!industryCategorySecond.equals(other.industryCategorySecond))
			return false;
		if (industryCategoryThird == null) {
			if (other.industryCategoryThird != null)
				return false;
		} else if (!industryCategoryThird.equals(other.industryCategoryThird))
			return false;
		if (interests == null) {
			if (other.interests != null)
				return false;
		} else if (!interests.equals(other.interests))
			return false;
		if (interviewContactWay == null) {
			if (other.interviewContactWay != null)
				return false;
		} else if (!interviewContactWay.equals(other.interviewContactWay))
			return false;
		if (interviewContacts == null) {
			if (other.interviewContacts != null)
				return false;
		} else if (!interviewContacts.equals(other.interviewContacts))
			return false;
		if (interviewSpace == null) {
			if (other.interviewSpace != null)
				return false;
		} else if (!interviewSpace.equals(other.interviewSpace))
			return false;
		if (interviewState == null) {
			if (other.interviewState != null)
				return false;
		} else if (!interviewState.equals(other.interviewState))
			return false;
		if (maritalStatus == null) {
			if (other.maritalStatus != null)
				return false;
		} else if (!maritalStatus.equals(other.maritalStatus))
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
		if (overseaAssignment == null) {
			if (other.overseaAssignment != null)
				return false;
		} else if (!overseaAssignment.equals(other.overseaAssignment))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (politicsStatus == null) {
			if (other.politicsStatus != null)
				return false;
		} else if (!politicsStatus.equals(other.politicsStatus))
			return false;
		if (positionCategoryFirst == null) {
			if (other.positionCategoryFirst != null)
				return false;
		} else if (!positionCategoryFirst.equals(other.positionCategoryFirst))
			return false;
		if (positionCategorySecond == null) {
			if (other.positionCategorySecond != null)
				return false;
		} else if (!positionCategorySecond.equals(other.positionCategorySecond))
			return false;
		if (positionCategoryThird == null) {
			if (other.positionCategoryThird != null)
				return false;
		} else if (!positionCategoryThird.equals(other.positionCategoryThird))
			return false;
		if (positionNameFirst == null) {
			if (other.positionNameFirst != null)
				return false;
		} else if (!positionNameFirst.equals(other.positionNameFirst))
			return false;
		if (positionNameSecond == null) {
			if (other.positionNameSecond != null)
				return false;
		} else if (!positionNameSecond.equals(other.positionNameSecond))
			return false;
		if (positionNameThird == null) {
			if (other.positionNameThird != null)
				return false;
		} else if (!positionNameThird.equals(other.positionNameThird))
			return false;
		if (presentResidence == null) {
			if (other.presentResidence != null)
				return false;
		} else if (!presentResidence.equals(other.presentResidence))
			return false;
		if (professionFirst == null) {
			if (other.professionFirst != null)
				return false;
		} else if (!professionFirst.equals(other.professionFirst))
			return false;
		if (professionSecond == null) {
			if (other.professionSecond != null)
				return false;
		} else if (!professionSecond.equals(other.professionSecond))
			return false;
		if (professionThird == null) {
			if (other.professionThird != null)
				return false;
		} else if (!professionThird.equals(other.professionThird))
			return false;
		if (professionalExaminer == null) {
			if (other.professionalExaminer != null)
				return false;
		} else if (!professionalExaminer.equals(other.professionalExaminer))
			return false;
		if (professionalSkill == null) {
			if (other.professionalSkill != null)
				return false;
		} else if (!professionalSkill.equals(other.professionalSkill))
			return false;
		if (projectExperience == null) {
			if (other.projectExperience != null)
				return false;
		} else if (!projectExperience.equals(other.projectExperience))
			return false;
		if (qualifiedDate == null) {
			if (other.qualifiedDate != null)
				return false;
		} else if (!qualifiedDate.equals(other.qualifiedDate))
			return false;
		if (qualifieder == null) {
			if (other.qualifieder != null)
				return false;
		} else if (!qualifieder.equals(other.qualifieder))
			return false;
		if (recruitDate == null) {
			if (other.recruitDate != null)
				return false;
		} else if (!recruitDate.equals(other.recruitDate))
			return false;
		if (recruitPlan == null) {
			if (other.recruitPlan != null)
				return false;
		} else if (!recruitPlan.equals(other.recruitPlan))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (reportContactWay == null) {
			if (other.reportContactWay != null)
				return false;
		} else if (!reportContactWay.equals(other.reportContactWay))
			return false;
		if (reportContacts == null) {
			if (other.reportContacts != null)
				return false;
		} else if (!reportContacts.equals(other.reportContacts))
			return false;
		if (reportDate == null) {
			if (other.reportDate != null)
				return false;
		} else if (!reportDate.equals(other.reportDate))
			return false;
		if (schoolFirst == null) {
			if (other.schoolFirst != null)
				return false;
		} else if (!schoolFirst.equals(other.schoolFirst))
			return false;
		if (schoolSecond == null) {
			if (other.schoolSecond != null)
				return false;
		} else if (!schoolSecond.equals(other.schoolSecond))
			return false;
		if (schoolThird == null) {
			if (other.schoolThird != null)
				return false;
		} else if (!schoolThird.equals(other.schoolThird))
			return false;
		if (selfAssessment == null) {
			if (other.selfAssessment != null)
				return false;
		} else if (!selfAssessment.equals(other.selfAssessment))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (talentPoolDate == null) {
			if (other.talentPoolDate != null)
				return false;
		} else if (!talentPoolDate.equals(other.talentPoolDate))
			return false;
		if (talentPoolName == null) {
			if (other.talentPoolName != null)
				return false;
		} else if (!talentPoolName.equals(other.talentPoolName))
			return false;
		if (typeOfId == null) {
			if (other.typeOfId != null)
				return false;
		} else if (!typeOfId.equals(other.typeOfId))
			return false;
		if (workEndDateFirst == null) {
			if (other.workEndDateFirst != null)
				return false;
		} else if (!workEndDateFirst.equals(other.workEndDateFirst))
			return false;
		if (workEndDateSecond == null) {
			if (other.workEndDateSecond != null)
				return false;
		} else if (!workEndDateSecond.equals(other.workEndDateSecond))
			return false;
		if (workEndDateThird == null) {
			if (other.workEndDateThird != null)
				return false;
		} else if (!workEndDateThird.equals(other.workEndDateThird))
			return false;
		if (workMonthlyPayFirst == null) {
			if (other.workMonthlyPayFirst != null)
				return false;
		} else if (!workMonthlyPayFirst.equals(other.workMonthlyPayFirst))
			return false;
		if (workMonthlyPaySecond == null) {
			if (other.workMonthlyPaySecond != null)
				return false;
		} else if (!workMonthlyPaySecond.equals(other.workMonthlyPaySecond))
			return false;
		if (workMonthlyPayThird == null) {
			if (other.workMonthlyPayThird != null)
				return false;
		} else if (!workMonthlyPayThird.equals(other.workMonthlyPayThird))
			return false;
		if (workSpecificationFirst == null) {
			if (other.workSpecificationFirst != null)
				return false;
		} else if (!workSpecificationFirst.equals(other.workSpecificationFirst))
			return false;
		if (workSpecificationSecond == null) {
			if (other.workSpecificationSecond != null)
				return false;
		} else if (!workSpecificationSecond
				.equals(other.workSpecificationSecond))
			return false;
		if (workSpecificationThird == null) {
			if (other.workSpecificationThird != null)
				return false;
		} else if (!workSpecificationThird.equals(other.workSpecificationThird))
			return false;
		if (workStartDate == null) {
			if (other.workStartDate != null)
				return false;
		} else if (!workStartDate.equals(other.workStartDate))
			return false;
		if (workStartDateFirst == null) {
			if (other.workStartDateFirst != null)
				return false;
		} else if (!workStartDateFirst.equals(other.workStartDateFirst))
			return false;
		if (workStartDateSecond == null) {
			if (other.workStartDateSecond != null)
				return false;
		} else if (!workStartDateSecond.equals(other.workStartDateSecond))
			return false;
		if (workStartDateThird == null) {
			if (other.workStartDateThird != null)
				return false;
		} else if (!workStartDateThird.equals(other.workStartDateThird))
			return false;
		if (workingCondition == null) {
			if (other.workingCondition != null)
				return false;
		} else if (!workingCondition.equals(other.workingCondition))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RecruitResume [id=" + id + ", code="
				+ code + ", name=" + name + ", remark=" + remark + ", sex="
				+ sex + ", birthday=" + birthday + ", workStartDate="
				+ workStartDate + ", domicilePlace=" + domicilePlace
				+ ", presentResidence=" + presentResidence + ", contactWay="
				+ contactWay + ", email=" + email + ", maritalStatus="
				+ maritalStatus + ", typeOfId=" + typeOfId + ", idNumber="
				+ idNumber + ", overseaAssignment=" + overseaAssignment
				+ ", nation=" + nation + ", politicsStatus=" + politicsStatus
				+ ", photo=" + photo + ", foreignLanguage=" + foreignLanguage
				+ ", foreignLanguageLevel=" + foreignLanguageLevel
				+ ", interests=" + interests + ", selfAssessment="
				+ selfAssessment + ", expectJobCategory=" + expectJobCategory
				+ ", expectWorkplace=" + expectWorkplace
				+ ", expectOccupation=" + expectOccupation
				+ ", expectIndustry=" + expectIndustry + ", expectMonthlyPay="
				+ expectMonthlyPay + ", workingCondition=" + workingCondition
				+ ", companyNameFirst=" + companyNameFirst
				+ ", industryCategoryFirst=" + industryCategoryFirst
				+ ", positionCategoryFirst=" + positionCategoryFirst
				+ ", positionNameFirst=" + positionNameFirst
				+ ", workStartDateFirst=" + workStartDateFirst
				+ ", workEndDateFirst=" + workEndDateFirst
				+ ", workMonthlyPayFirst=" + workMonthlyPayFirst
				+ ", workSpecificationFirst=" + workSpecificationFirst
				+ ", companyNameSecond=" + companyNameSecond
				+ ", industryCategorySecond=" + industryCategorySecond
				+ ", positionCategorySecond=" + positionCategorySecond
				+ ", positionNameSecond=" + positionNameSecond
				+ ", workStartDateSecond=" + workStartDateSecond
				+ ", workEndDateSecond=" + workEndDateSecond
				+ ", workMonthlyPaySecond=" + workMonthlyPaySecond
				+ ", workSpecificationSecond=" + workSpecificationSecond
				+ ", companyNameThird=" + companyNameThird
				+ ", industryCategoryThird=" + industryCategoryThird
				+ ", positionCategoryThird=" + positionCategoryThird
				+ ", positionNameThird=" + positionNameThird
				+ ", workStartDateThird=" + workStartDateThird
				+ ", workEndDateThird=" + workEndDateThird
				+ ", workMonthlyPayThird=" + workMonthlyPayThird
				+ ", workSpecificationThird=" + workSpecificationThird
				+ ", eduStartDateFirst=" + eduStartDateFirst
				+ ", eduEndDateFirst=" + eduEndDateFirst + ", schoolFirst="
				+ schoolFirst + ", professionFirst=" + professionFirst
				+ ", eduLevelFirst=" + eduLevelFirst + ", eduStartDateSecond="
				+ eduStartDateSecond + ", eduEndDateSecond=" + eduEndDateSecond
				+ ", schoolSecond=" + schoolSecond + ", professionSecond="
				+ professionSecond + ", eduLevelSecond=" + eduLevelSecond
				+ ", eduStartDateThird=" + eduStartDateThird
				+ ", eduEndDateThird=" + eduEndDateThird + ", schoolThird="
				+ schoolThird + ", professionThird=" + professionThird
				+ ", eduLevelThird=" + eduLevelThird + ", projectExperience="
				+ projectExperience + ", professionalSkill="
				+ professionalSkill + ", recruitPlan=" + recruitPlan
				+ ", recruitDate=" + recruitDate + ", favoriteState="
				+ favoriteState + ", favoriteName=" + favoriteName
				+ ", favoriteDate=" + favoriteDate + ", talentPoolName="
				+ talentPoolName + ", talentPoolDate=" + talentPoolDate
				+ ", state=" + state + ", qualifiedDate=" + qualifiedDate
				+ ", qualifieder=" + qualifieder + ", disqualificationDate="
				+ disqualificationDate + ", disqualificationer="
				+ disqualificationer + ", interviewState=" + interviewState
				+ ", interviewSpace=" + interviewSpace
				+ ", professionalExaminer=" + professionalExaminer
				+ ", foreignLanguageExaminer=" + foreignLanguageExaminer
				+ ", examinerDate=" + examinerDate + ", interviewContacts="
				+ interviewContacts + ", interviewContactWay="
				+ interviewContactWay + ", examineGrade=" + examineGrade
				+ ", examineEvaluate=" + examineEvaluate + ", reportDate="
				+ reportDate + ", reportContacts=" + reportContacts
				+ ", reportContactWay=" + reportContactWay + "]";
	}
}
