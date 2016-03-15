package com.huge.ihos.nursescore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;

@Entity
@Table( name = "jj_t_NurseDayScoreDetail")
public class NurseDayScoreDetail {
	
	private Long dayScoreDetailID;
	private String checkperiod;
	private Date   scoredate;
	private Department groupid;
	private String groupname;
	private Person personid ;
	private String code = "";
	private String name = "";
	private NurseYearRate yearcode ;
	private String yearname = "";
	private NursePostRate postcode ;
	private String postname = "";
	private NurseShiftRate shiftcode ;
	private String shiftname = "";
	private boolean isWork = true;
	private NurseDayScore dayScoreID;
	private Double days =new Double(1);
	private Double score =new Double(0);
	private Integer state ;
	private String remark = "";
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public Long getDayScoreDetailID() {
		return dayScoreDetailID;
	}
	public void setDayScoreDetailID(Long dayScoreDetailID) {
		this.dayScoreDetailID = dayScoreDetailID;
	}
	@Column(name="checkperiod")
	public String getCheckperiod() {
		return checkperiod;
	}
	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}
	@Column(name="scoredate")
	public Date getScoredate() {
		return scoredate;
	}
	public void setScoredate(Date scoredate) {
		this.scoredate = scoredate;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="groupid")
	public Department getGroupid() {
		return groupid;
	}
	public void setGroupid(Department groupid) {
		this.groupid = groupid;
	}
	@Column(name="groupname")
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="personid")
	public Person getPersonid() {
		return personid;
	}
	public void setPersonid(Person personid) {
		this.personid = personid;
	}
	@Column(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="yearcode")
	public NurseYearRate getYearcode() {
		return yearcode;
	}
	public void setYearcode(NurseYearRate yearcode) {
		this.yearcode = yearcode;
	}
	@Column(name="days")
	public Double getDays() {
		return days;
	}
	public void setDays(Double days) {
		this.days = days;
	}
	@Column(name="score")
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	@Column(name="state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public String getYearname() {
		yearname = yearcode.getTitle();
		return yearname;
	}
	public void setYearname(String yearname) {
		this.yearname = yearname;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="postcode")
	public NursePostRate getPostcode() {
		return postcode;
	}
	public void setPostcode(NursePostRate postcode) {
		this.postcode = postcode;
	}
	@Transient
	public String getPostname() {
		return postname;
	}
	public void setPostname(String postname) {
		this.postname = postname;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="shiftcode")
	public NurseShiftRate getShiftcode() {
		return shiftcode;
	}
	public void setShiftcode(NurseShiftRate shiftcode) {
		this.shiftcode = shiftcode;
	}
	@Transient
	public String getShiftname() {
		return shiftname;
	}
	public void setShiftname(String shiftname) {
		this.shiftname = shiftname;
	}
	@Column(name="isWork")
	public boolean getIsWork() {
		return isWork;
	}
	public void setIsWork(boolean isWork) {
		this.isWork = isWork;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="dayScoreID")
	public NurseDayScore getDayScoreID() {
		return dayScoreID;
	}
	public void setDayScoreID(NurseDayScore dayScoreID) {
		this.dayScoreID = dayScoreID;
	}
	
}