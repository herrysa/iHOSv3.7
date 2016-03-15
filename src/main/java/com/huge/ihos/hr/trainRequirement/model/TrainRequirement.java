package com.huge.ihos.hr.trainRequirement.model;

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
import com.huge.ihos.hr.trainCategory.model.TrainCategory;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 培训需求
 */
@Entity
@Table(name = "hr_train_requirement")
public class TrainRequirement extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -405752773885346338L;
	private String id;// 主键
	private HrDepartmentCurrent hrDept;// 部门
	private HrDepartmentHis hrDeptHis;
	private String deptSnapCode;
	private String code;// 编码
	private String name;// 名称
	private String goal;// 培训目标
	private String content;// 培训内容
	private Integer peopleNumber;// 培训人数
	private String remark;// 备注
	private Date makeDate;// 编制日期
	private Person maker;// 编制人
	private Date checkDate;// 审核日期
	private Person checker;// 审核人
	private String state;// 培训班状态
	private TrainCategory trainCategory;// 培训类别
	private String periodMonth;
	private String personIds;
	private String personNames;
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

	@Column(name = "people_number", nullable = true)
	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
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
	@JoinColumn(name = "maker", nullable = true)
	public Person getMaker() {
		return maker;
	}

	public void setMaker(Person maker) {
		this.maker = maker;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_category_id", nullable = true)
	public TrainCategory getTrainCategory() {
		return trainCategory;
	}

	public void setTrainCategory(TrainCategory trainCategory) {
		this.trainCategory = trainCategory;
	}

	@Column(name = "periodMonth", length = 6, nullable = true)
	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	@Column(name = "personIds", length = 8000)
	public String getPersonIds() {
		return personIds;
	}

	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}

	@Column(name = "personNames", length = 8000)
	public String getPersonNames() {
		return personNames;
	}

	public void setPersonNames(String personNames) {
		this.personNames = personNames;
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
		result = prime * result
				+ ((peopleNumber == null) ? 0 : peopleNumber.hashCode());
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
		TrainRequirement other = (TrainRequirement) obj;
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
		if (peopleNumber == null) {
			if (other.peopleNumber != null)
				return false;
		} else if (!peopleNumber.equals(other.peopleNumber))
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
		return "TrainRequirement [code=" + code + ", makeDate=" + makeDate
				+ ", state=" + state + ", remark=" + remark + ", name=" + name
				+ ", peopleNumber=" + peopleNumber + ", checkDate=" + checkDate
				+ ", checker=" + checker + "]";
	}

}
