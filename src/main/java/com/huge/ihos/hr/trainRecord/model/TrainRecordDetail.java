package com.huge.ihos.hr.trainRecord.model;
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

import com.huge.ihos.hr.trainCourse.model.TrainCourse;
import com.huge.model.BaseObject;
/**
 * 培训记录
 */
@Entity
@Table(name = "hr_train_record_detail")
public class TrainRecordDetail extends BaseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5865569456778494194L;
	private String id;//主键
	private TrainRecord trainRecord;//
	private TrainCourse trainCourse;//
	private Date trainDate;//培训日期
	private Double trainHour;//学时
	private String personIds;
	private String personNames;
	
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainRecord",nullable = true)
	public TrainRecord getTrainRecord() {
		return trainRecord;
	}
	public void setTrainRecord(TrainRecord trainRecord) {
		this.trainRecord = trainRecord;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trainDate", length = 19, nullable = true)
	public Date getTrainDate() {
		return trainDate;
	}
	public void setTrainDate(Date trainDate) {
		this.trainDate = trainDate;
	}
	@Column( name = "trainHour", nullable = true,precision=12,scale=2)
	public Double getTrainHour() {
		return trainHour;
	}
	public void setTrainHour(Double trainHour) {
		this.trainHour = trainHour;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainCourse",nullable = true)
	public TrainCourse getTrainCourse() {
		return trainCourse;
	}
	public void setTrainCourse(TrainCourse trainCourse) {
		this.trainCourse = trainCourse;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trainHour == null) ? 0 : trainHour.hashCode());
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
		TrainRecordDetail other = (TrainRecordDetail) obj;
		if (trainHour == null) {
			if (other.trainHour != null)
				return false;
		} else if (!trainHour.equals(other.trainHour))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainRecordDetail [trainHour=" + trainHour+"]";
	}
}

