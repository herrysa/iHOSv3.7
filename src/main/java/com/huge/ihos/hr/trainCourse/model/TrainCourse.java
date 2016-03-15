package com.huge.ihos.hr.trainCourse.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.ihos.hr.trainTeacher.model.TrainTeacher;
import com.huge.model.BaseObject;

/**
 * 培训课程
 */
@Entity
@Table(name = "hr_train_course")
public class TrainCourse extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1056324055358455644L;
	private String id;// 主键
	private String code;// 编码
	private String name;// 名称
	private TrainNeed trainNeed;// 培训班
	private TrainTeacher trainTeacher;// 培训教师
	private Date startDate;// 开始时间
	private Date endDate;// 结束时间
	private Double hour;// 课时
	private String trainInformationIds;
	private String trainInformationNames;// 培训资料
	private Double expense;// 费用
	private String remark;// 备注
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

	@Column(name = "expense", nullable = true, precision = 12, scale = 2)
	public Double getExpense() {
		return expense;
	}

	public void setExpense(Double expense) {
		this.expense = expense;
	}

	@Column(name = "course_hour", nullable = true, precision = 12, scale = 2)
	public Double getHour() {
		return hour;
	}

	public void setHour(Double hour) {
		this.hour = hour;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_need", nullable = true)
	public TrainNeed getTrainNeed() {
		return trainNeed;
	}

	public void setTrainNeed(TrainNeed trainNeed) {
		this.trainNeed = trainNeed;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_teacher", nullable = true)
	public TrainTeacher getTrainTeacher() {
		return trainTeacher;
	}

	public void setTrainTeacher(TrainTeacher trainTeacher) {
		this.trainTeacher = trainTeacher;
	}

	@Column(name = "trainInformationIds", length = 2000, nullable = true)
	public String getTrainInformationIds() {
		return trainInformationIds;
	}

	public void setTrainInformationIds(String trainInformationIds) {
		this.trainInformationIds = trainInformationIds;
	}

	@Column(name = "trainInformationNames", length = 2000, nullable = true)
	public String getTrainInformationNames() {
		return trainInformationNames;
	}

	public void setTrainInformationNames(String trainInformationNames) {
		this.trainInformationNames = trainInformationNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((expense == null) ? 0 : expense.hashCode());
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
		TrainCourse other = (TrainCourse) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		if (expense == null) {
			if (other.expense != null)
				return false;
		} else if (!expense.equals(other.expense))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainCourse [code=" + code + ", remark=" + remark + ", name="
				+ name + ", expense=" + expense + "]";
	}

}
