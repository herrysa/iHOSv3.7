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

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;

@Entity
@Table( name = "jj_t_NurseDayScore")
public class NurseDayScore {
	
	private Long dayScoreID;
	private String billno;
	private String checkperiod;
	private Date   scoredate;
	private Department groupid;
	private String groupname;
	private Person operatorid ;
	private String operatorname = "";
	private Date operatedate ;
	private Person auditid ;
	private String auditname;
	private Date auditdate;
	private Double days ;
	private Double dayscore ;
	private Integer state ;
	private String remark = "";
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public Long getDayScoreID() {
		return dayScoreID;
	}
	public void setDayScoreID(Long dayScoreID) {
		this.dayScoreID = dayScoreID;
	}
	@Column(name="billno")
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
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
	@JoinColumn(name="operatorid")
	public Person getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(Person operatorid) {
		this.operatorid = operatorid;
	}
	@Column(name="operatorname")
	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	@Column(name="operatedate")
	public Date getOperatedate() {
		return operatedate;
	}
	public void setOperatedate(Date operatedate) {
		this.operatedate = operatedate;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="auditid")
	public Person getAuditid() {
		return auditid;
	}
	public void setAuditid(Person auditid) {
		this.auditid = auditid;
	}
	@Column(name="auditname")
	public String getAuditname() {
		if(auditid!=null){
			auditname = auditid.getName();
			
		}
		return auditname;
	}
	public void setAuditname(String auditname) {
		this.auditname = auditname;
	}
	@Column(name="auditdate")
	public Date getAuditdate() {
		return auditdate;
	}
	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}
	@Column(name="days")
	public Double getDays() {
		return days;
	}
	public void setDays(Double days) {
		this.days = days;
	}
	@Column(name="dayscore")
	public Double getDayscore() {
		return dayscore;
	}
	public void setDayscore(Double dayscore) {
		this.dayscore = dayscore;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dayScoreID == null) ? 0 : dayScoreID.hashCode());
		result = prime * result
				+ ((auditdate == null) ? 0 : auditdate.hashCode());
		result = prime * result + ((auditid == null) ? 0 : auditid.hashCode());
		result = prime * result
				+ ((auditname == null) ? 0 : auditname.hashCode());
		result = prime * result + ((billno == null) ? 0 : billno.hashCode());
		result = prime * result
				+ ((checkperiod == null) ? 0 : checkperiod.hashCode());
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result
				+ ((dayscore == null) ? 0 : dayscore.hashCode());
		result = prime * result + ((groupid == null) ? 0 : groupid.hashCode());
		result = prime * result
				+ ((groupname == null) ? 0 : groupname.hashCode());
		result = prime * result
				+ ((operatedate == null) ? 0 : operatedate.hashCode());
		result = prime * result
				+ ((operatorid == null) ? 0 : operatorid.hashCode());
		result = prime * result
				+ ((operatorname == null) ? 0 : operatorname.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((scoredate == null) ? 0 : scoredate.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		NurseDayScore other = (NurseDayScore) obj;
		if (dayScoreID == null) {
			if (other.dayScoreID != null)
				return false;
		} else if (!dayScoreID.equals(other.dayScoreID))
			return false;
		if (auditdate == null) {
			if (other.auditdate != null)
				return false;
		} else if (!auditdate.equals(other.auditdate))
			return false;
		if (auditid == null) {
			if (other.auditid != null)
				return false;
		} else if (!auditid.equals(other.auditid))
			return false;
		if (auditname == null) {
			if (other.auditname != null)
				return false;
		} else if (!auditname.equals(other.auditname))
			return false;
		if (billno == null) {
			if (other.billno != null)
				return false;
		} else if (!billno.equals(other.billno))
			return false;
		if (checkperiod == null) {
			if (other.checkperiod != null)
				return false;
		} else if (!checkperiod.equals(other.checkperiod))
			return false;
		if (days == null) {
			if (other.days != null)
				return false;
		} else if (!days.equals(other.days))
			return false;
		if (dayscore == null) {
			if (other.dayscore != null)
				return false;
		} else if (!dayscore.equals(other.dayscore))
			return false;
		if (groupid == null) {
			if (other.groupid != null)
				return false;
		} else if (!groupid.equals(other.groupid))
			return false;
		if (groupname == null) {
			if (other.groupname != null)
				return false;
		} else if (!groupname.equals(other.groupname))
			return false;
		if (operatedate == null) {
			if (other.operatedate != null)
				return false;
		} else if (!operatedate.equals(other.operatedate))
			return false;
		if (operatorid == null) {
			if (other.operatorid != null)
				return false;
		} else if (!operatorid.equals(other.operatorid))
			return false;
		if (operatorname == null) {
			if (other.operatorname != null)
				return false;
		} else if (!operatorname.equals(other.operatorname))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (scoredate == null) {
			if (other.scoredate != null)
				return false;
		} else if (!scoredate.equals(other.scoredate))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NurseDayScore [DayScoreID=" + dayScoreID + ", billno=" + billno
				+ ", checkperiod=" + checkperiod + ", scoredate=" + scoredate
				+ ", groupid=" + groupid + ", groupname=" + groupname
				+ ", operatorid=" + operatorid + ", operatorname="
				+ operatorname + ", operatedate=" + operatedate + ", auditid="
				+ auditid + ", auditname=" + auditname + ", auditdate="
				+ auditdate + ", days=" + days + ", dayscore=" + dayscore
				+ ", state=" + state + ", remark=" + remark + "]";
	}
	
	
	
	
	
}