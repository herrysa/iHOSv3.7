package com.huge.ihos.hr.trainRecord.model;

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

import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;
/**
 * 培训记录
 */
@Entity
@Table(name = "hr_train_record")
public class TrainRecord extends BaseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3214168015655692533L;
	private String id;//主键
	private String code;//编码
	private String name;//名称
	private String goal;//培训目标
	private String content;//培训内容
	private TrainNeed trainNeed;//培训班
	private Date makeDate;//编制日期
	private Person maker;//编制人
	private Date checkDate;//审核日期
	private Person checker;//审核人
	private Date doneDate;//记入档案日期
	private Person doner;//记入档案人
	private String state;//状态
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
	
	@Column( name = "name", nullable = true ,length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column( name = "goal", nullable = true ,length=30)
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	@Column( name = "content", nullable = true ,length=200)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker",nullable = true)
	public Person getChecker() {
		return checker;
	}
	public void setChecker(Person checker) {
		this.checker = checker;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker",nullable = true)
	public Person getMaker() {
		return maker;
	}
	public void setMaker(Person maker) {
		this.maker = maker;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainNeed",nullable = true)
	public TrainNeed getTrainNeed() {
		return trainNeed;
	}
	public void setTrainNeed(TrainNeed trainNeed) {
		this.trainNeed = trainNeed;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "check_date", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "done_date", length = 19, nullable = true)
	public Date getDoneDate() {
		return doneDate;
	}
	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doner",nullable = true)
	public Person getDoner() {
		return doner;
	}
	public void setDoner(Person doner) {
		this.doner = doner;
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TrainRecord other = (TrainRecord) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainRecord [code=" + code+"]";
	}
}

