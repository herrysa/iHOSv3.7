package com.huge.ihos.hr.trainNeed.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.hr.trainInstitution.model.TrainInstitution;
import com.huge.ihos.hr.trainPlan.model.TrainPlan;
import com.huge.ihos.hr.trainSite.model.TrainSite;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 培训班
 */
@Entity
@Table(name = "hr_train_class")
public class TrainNeed extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1260981517130518056L;
	private String id;// 主键
	private String code;// 编码
	private String name;// 名称
	private String trainMethod;// 培训方式
	private String trainType;// 培训类型
	private String sponsor;// 主办单位
	private String principal;// 负责人
	private String goal;// 培训目标
	private String content;// 培训内容
	private String target;// 培训对象
	private Integer peopleNumber;// 培训人数
	private Double expense;// 费用
	private String studyPeriod;// 学时
	private Date applyStartDate;// 报名开始时间
	private Date applyEndDate;// 报名结束时间
	private Date startDate;// 培训班开始时间
	private Date endDate;// 培训班结束时间
	private String remark;// 活动说明
	private Date makeDate;// 编制日期
	private Person maker;// 编制人
	private Date checkDate;// 审核日期
	private Person checker;// 审核人
	private String state;// 培训班状态
	private String approvalOpinion;// 审核意见
	private String year;// 培训班年度
	private String quarter;// 培训班季度
	private String month;// 培训班举办月份
	private String courseArrangement;// 课程安排
	private String syllabus;// 课程表
	private TrainSite trainSite;// 培训场所
	private TrainInstitution trainInstitution;// 培训机构
	private TrainPlan trainPlan;// 培训计划
	private String personIds;
	private String personNames;
	private String type;// 计划类型
	private String trainEquipmentIds;
	private String trainEquipmentNames;// 培训设施
	private Date tempPlanStartDate;// 临时计划开始时间
	private Date tempPlanEndDate;// 临时计划结束时间
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "make_date", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@Column(name = "state", nullable = true, length = 2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	@Column(name = "year", nullable = true, length = 20)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "quarter", nullable = true, length = 20)
	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	@Column(name = "month", nullable = true, length = 10)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "expense", nullable = true, precision = 12, scale = 2)
	public Double getExpense() {
		return expense;
	}

	public void setExpense(Double expense) {
		this.expense = expense;
	}

	@Column(name = "people_number", nullable = true)
	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	@Column(name = "approval_opinion", nullable = true, length = 50)
	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
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
	@JoinColumn(name = "checker", nullable = true)
	public Person getChecker() {
		return checker;
	}

	public void setChecker(Person checker) {
		this.checker = checker;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = true)
	public Person getMaker() {
		return maker;
	}

	public void setMaker(Person maker) {
		this.maker = maker;
	}

	@Column(name = "train_method", nullable = true, length = 30)
	public String getTrainMethod() {
		return trainMethod;
	}

	public void setTrainMethod(String trainMethod) {
		this.trainMethod = trainMethod;
	}

	@Column(name = "train_type", nullable = true, length = 30)
	public String getTrainType() {
		return trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}

	@Column(name = "sponsor", nullable = true, length = 30)
	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	@Column(name = "principal", nullable = true, length = 20)
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Column(name = "goal", nullable = true, length = 30)
	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	@Column(name = "content", nullable = true, length = 200)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "target", nullable = true, length = 30)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "study_period", nullable = true, length = 20)
	public String getStudyPeriod() {
		return studyPeriod;
	}

	public void setStudyPeriod(String studyPeriod) {
		this.studyPeriod = studyPeriod;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apply_start_date", length = 19, nullable = true)
	public Date getApplyStartDate() {
		return applyStartDate;
	}

	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apply_end_date", length = 19, nullable = true)
	public Date getApplyEndDate() {
		return applyEndDate;
	}

	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
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

	@Column(name = "course_arrangement", nullable = true, length = 200)
	public String getCourseArrangement() {
		return courseArrangement;
	}

	public void setCourseArrangement(String courseArrangement) {
		this.courseArrangement = courseArrangement;
	}

	@Column(name = "syllabus", nullable = true, length = 200)
	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_site", nullable = true)
	public TrainSite getTrainSite() {
		return trainSite;
	}

	public void setTrainSite(TrainSite trainSite) {
		this.trainSite = trainSite;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_plan", nullable = true)
	public TrainPlan getTrainPlan() {
		return trainPlan;
	}

	public void setTrainPlan(TrainPlan trainPlan) {
		this.trainPlan = trainPlan;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_institution", nullable = true)
	public TrainInstitution getTrainInstitution() {
		return trainInstitution;
	}

	public void setTrainInstitution(TrainInstitution trainInstitution) {
		this.trainInstitution = trainInstitution;
	}

	@Column( name = "personIds", length = 8000 )
	public String getPersonIds() {
		return personIds;
	}

	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}
	
	@Column( name = "personNames", length = 8000 )
	public String getPersonNames() {
		return personNames;
	}

	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}

	@Column(name = "trainEquipmentIds", length = 1000)
	public String getTrainEquipmentIds() {
		return trainEquipmentIds;
	}

	public void setTrainEquipmentIds(String trainEquipmentIds) {
		this.trainEquipmentIds = trainEquipmentIds;
	}

	@Column(name = "trainEquipmentNames", length = 1000)
	public String getTrainEquipmentNames() {
		return trainEquipmentNames;
	}

	public void setTrainEquipmentNames(String trainEquipmentNames) {
		this.trainEquipmentNames = trainEquipmentNames;
	}

	@Column(name = "type", nullable = true, length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tempPlanStartDate", length = 19, nullable = true)
	public Date getTempPlanStartDate() {
		return tempPlanStartDate;
	}

	public void setTempPlanStartDate(Date tempPlanStartDate) {
		this.tempPlanStartDate = tempPlanStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tempPlanEndDate", length = 19, nullable = true)
	public Date getTempPlanEndDate() {
		return tempPlanEndDate;
	}

	public void setTempPlanEndDate(Date tempPlanEndDate) {
		this.tempPlanEndDate = tempPlanEndDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		result = prime * result + ((quarter == null) ? 0 : quarter.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((expense == null) ? 0 : expense.hashCode());
		result = prime * result
				+ ((peopleNumber == null) ? 0 : peopleNumber.hashCode());
		result = prime * result
				+ ((approvalOpinion == null) ? 0 : approvalOpinion.hashCode());
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
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
		TrainNeed other = (TrainNeed) obj;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		if (quarter == null) {
			if (other.quarter != null)
				return false;
		} else if (!quarter.equals(other.quarter))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (expense == null) {
			if (other.expense != null)
				return false;
		} else if (!expense.equals(other.expense))
			return false;
		if (peopleNumber == null) {
			if (other.peopleNumber != null)
				return false;
		} else if (!peopleNumber.equals(other.peopleNumber))
			return false;
		if (approvalOpinion == null) {
			if (other.approvalOpinion != null)
				return false;
		} else if (!approvalOpinion.equals(other.approvalOpinion))
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
		return "TrainNeed [code=" + code + ", makeDate=" + makeDate
				+ ", state=" + state + ", remark=" + remark + ", name=" + name
				+ ", year=" + year + ", quarter=" + quarter + ", month="
				+ month + ", expense=" + expense + ", peopleNumber="
				+ peopleNumber + ", approvalOpinion=" + approvalOpinion
				+ ", checkDate=" + checkDate + ", checker=" + checker + "]";
	}

}
