package com.huge.ihos.hr.recruitPlan.model;

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

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "hr_recruit_plan")
public class RecruitPlan extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5964736336114424045L;
	private String id;//主键
	private String orgCode;//单位
	private HrOrg hrOrg;
	private HrOrgHis hrOrgHis;
	private String orgSnapCode;
	private String code;//编码
	private String name;//名称
	private String channel;//招聘渠道
	private String post;//需求岗位
	private Date makeDate;//创建日期
	private Person maker;//创建人
	private Date checkDate;//审核日期
	private Person checker;//审核人
	private Integer recruitNumber;//需求人数
	private String sex;//性别要求
	private Integer ageStart;//最低年龄要求
	private Integer ageEnd;//最高年龄要求
	private String politicsStatus;//政治面貌
	private String jobRequirements;//工作职责
	private String maritalStatus;//婚姻要求
	private String educationLevel;//学历要求
	private String profession;//专业要求
	private String workExperience;//工作经验
	private String salaryLevel;//薪资水平
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String workplace;//工作地点
	private String recruitTarget;//招聘对象
	private Boolean hotPost;//热点职位
	private String postResponsibility;//岗位要求
	private String remark;//备注
	private String state;//状态
	private Date releasedDate;//发布日期
	private Person releaseder;//发布人
	private String otherRequirements;//其他要求
	private Date breakDate;// 终止日期
	private Person breaker;//终止人
	private String needIds;//需求号
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
	@Column( name = "code", nullable = false ,length=50)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "make_date", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	
	@Column( name = "state", nullable = true ,length=2)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Column( name = "remark", nullable = true ,length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column( name = "name", nullable = true ,length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	@Column( name = "orgCode", nullable = true ,length=20)
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Column( name = "salary_level", nullable = true ,length=20)
	public String getSalaryLevel() {
		return salaryLevel;
	}
	public void setSalaryLevel(String salaryLevel) {
		this.salaryLevel = salaryLevel;
	}
	
	
	@Column( name = "workplace", nullable = true,length=20)
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
		
	@Column( name = "recruit_target", nullable = true ,length=40)
	public String getRecruitTarget() {
		return recruitTarget;
	}
	public void setRecruitTarget(String recruitTarget) {
		this.recruitTarget = recruitTarget;
	}
	
	@Column( name = "hot_post", nullable = true,columnDefinition = "bit default 0")
	public Boolean getHotPost() {
		return hotPost;
	}
	public void setHotPost(Boolean hotPost) {
		this.hotPost = hotPost;
	}
	
	@Column( name = "recruit_number", nullable = true)
	public Integer getRecruitNumber() {
		return recruitNumber;
	}
	public void setRecruitNumber(Integer recruitNumber) {
		this.recruitNumber = recruitNumber;
	}
	
	@Column( name = "work_experience", nullable = true,length=30)
	public String getWorkExperience() {
		return workExperience;
	}
	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "check_date", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker",nullable = true)
	public Person getChecker() {
		return checker;
	}
	public void setChecker(Person checker) {
		this.checker = checker;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "released_date", length = 19, nullable = true)
	public Date getReleasedDate() {
		return releasedDate;
	}
	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "releaseder",nullable = true)
	public Person getReleaseder() {
		return releaseder;
	}
	public void setReleaseder(Person releaseder) {
		this.releaseder = releaseder;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker",nullable = true)
	public Person getMaker() {
		return maker;
	}
	public void setMaker(Person maker) {
		this.maker = maker;
	}
	
	@Column( name = "channel", nullable = true ,length=20)
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Column( name = "sex", nullable = true ,length=10)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	@Column( name = "politics_status", nullable = true ,length=20)
	public String getPoliticsStatus() {
		return politicsStatus;
	}
	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}
	
	@Column( name = "job_requirements", nullable = true ,length=4000)
	public String getJobRequirements() {
		return jobRequirements;
	}
	public void setJobRequirements(String jobRequirements) {
		this.jobRequirements = jobRequirements;
	}
	
	@Column( name = "marital_status", nullable = true ,length=10)
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	@Column( name = "education_level", nullable = true ,length=10)
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	
	@Column( name = "profession", nullable = true ,length=30)
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	@Column( name = "ageStart", nullable = true)
	public Integer getAgeStart() {
		return ageStart;
	}
	public void setAgeStart(Integer ageStart) {
		this.ageStart = ageStart;
	}
	
	@Column( name = "ageEnd", nullable = true)
	public Integer getAgeEnd() {
		return ageEnd;
	}
	public void setAgeEnd(Integer ageEnd) {
		this.ageEnd = ageEnd;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19, nullable = true)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19, nullable = true)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column( name = "post_responsibility", nullable = true ,length=4000)
	public String getPostResponsibility() {
		return postResponsibility;
	}
	public void setPostResponsibility(String postResponsibility) {
		this.postResponsibility = postResponsibility;
	}
	
	@Column( name = "post", nullable = true ,length=50)
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	@Column( name = "otherRequirements", nullable = true ,length=4000)
	public String getOtherRequirements() {
		return otherRequirements;
	}
	public void setOtherRequirements(String otherRequirements) {
		this.otherRequirements = otherRequirements;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "breakDate", length = 19, nullable = true)
	public Date getBreakDate() {
		return breakDate;
	}
	public void setBreakDate(Date breakDate) {
		this.breakDate = breakDate;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "breaker",nullable = true)
	public Person getBreaker() {
		return breaker;
	}
	public void setBreaker(Person breaker) {
		this.breaker = breaker;
	}
	@Column( name = "needIds", nullable = true ,length=2000)
	public String getNeedIds() {
		return needIds;
	}
	public void setNeedIds(String needIds) {
		this.needIds = needIds;
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((salaryLevel == null) ? 0 : salaryLevel.hashCode());
		result = prime * result + ((workplace == null) ? 0 : workplace.hashCode());
		result = prime * result + ((recruitTarget == null) ? 0 : recruitTarget.hashCode());
		result = prime * result + ((hotPost == null) ? 0 : hotPost.hashCode());
		result = prime * result + ((recruitNumber == null) ? 0 : recruitNumber.hashCode());
		result = prime * result + ((workExperience == null) ? 0 : workExperience.hashCode());
		result = prime * result + ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result + ((checker == null) ? 0 : checker.hashCode());
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
		RecruitPlan other = (RecruitPlan) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (salaryLevel == null) {
			if (other.salaryLevel != null)
				return false;
		} else if (!salaryLevel.equals(other.salaryLevel))
			return false;
		if (workplace == null) {
			if (other.workplace != null)
				return false;
		} else if (!workplace.equals(other.workplace))
			return false;
		if (recruitTarget == null) {
			if (other.recruitTarget != null)
				return false;
		} else if (!recruitTarget.equals(other.recruitTarget))
			return false;
		if (hotPost == null) {
			if (other.hotPost != null)
				return false;
		} else if (!hotPost.equals(other.hotPost))
			return false;
		if (recruitNumber == null) {
			if (other.recruitNumber != null)
				return false;
		} else if (!recruitNumber.equals(other.recruitNumber))
			return false;
		if (workExperience == null) {
			if (other.workExperience != null)
				return false;
		} else if (!workExperience.equals(other.workExperience))
			return false;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (checker == null) {
			if (other.checker != null)
				return false;
		} else if (!checker.equals(other.checker))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RecruitPlan [code=" + code+ ", makeDate=" + makeDate+ ", state=" + state+ ", remark=" + remark
				+ ", orgCode=" + orgCode+ ", name=" + name+ ", salaryLevel=" + salaryLevel+ ", workplace=" + workplace+
				 ", recruitTarget=" + recruitTarget+ 
				", hotPost=" + hotPost+ ", recruitNumber=" + recruitNumber+ ", workExperience=" + workExperience
				+ ", checkDate=" + checkDate+ ", checker=" + checker+ "]";
	}
}

