package com.huge.ihos.hr.hrDeptment.model;

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

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 撤销部门
 * 
 * @author Gaozhengyang
 * @date 2014年12月1日
 */
@Entity
@Table(name = "hr_department_rescind")
public class HrDeptRescind extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8739297702584235371L;
	private String id;
	private String rescindNo;
	private HrDepartmentCurrent hrDept;
	private HrDepartmentHis hrDeptHis;
	private HrDepartmentCurrent moveToDept;
	private HrDepartmentHis moveToDeptHis;
	private String moveToSnapCode;
	private String snapCode;
	private Integer state;
	private String rescindReason;
	private Person rescindPerson;
	private Date rescindDate;
	private Date makeDate;
	private Person checkPerson;
	private Person confirmPerson;
	private Date checkDate;
	private Date confirmDate;
	private String yearMonth;
	
	@Column(name = "yearMonth", nullable = true, length = 6)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
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

	@Column(name = "rescindNo", nullable = false, length = 30)
	public String getRescindNo() {
		return rescindNo;
	}

	public void setRescindNo(String rescindNo) {
		this.rescindNo = rescindNo;
	}
	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = true)
	public HrDepartmentCurrent getHrDept() {
		return hrDept;
	}

	public void setHrDept(HrDepartmentCurrent hrDept) {
		this.hrDept = hrDept;
	}

//	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "deptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "snapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getHrDeptHis() {
		return hrDeptHis;
	}

	public void setHrDeptHis(HrDepartmentHis hrDeptHis) {
		this.hrDeptHis = hrDeptHis;
	}

	@Column(name = "snapCode", nullable = false, length = 14)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}
	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moveTo_deptId", nullable = true)
	public HrDepartmentCurrent getMoveToDept() {
		return moveToDept;
	}

	public void setMoveToDept(HrDepartmentCurrent moveToDept) {
		this.moveToDept = moveToDept;
	}

//	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "moveTo_deptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "moveTo_snapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getMoveToDeptHis() {
		return moveToDeptHis;
	}

	public void setMoveToDeptHis(HrDepartmentHis moveToDeptHis) {
		this.moveToDeptHis = moveToDeptHis;
	}

	@Column(name = "moveTo_snapCode", nullable = false, length = 14)
	public String getMoveToSnapCode() {
		return moveToSnapCode;
	}

	public void setMoveToSnapCode(String moveToSnapCode) {
		this.moveToSnapCode = moveToSnapCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rescindDate", length = 19, nullable = true)
	public Date getRescindDate() {
		return rescindDate;
	}

	public void setRescindDate(Date rescindDate) {
		this.rescindDate = rescindDate;
	}

	@Column(name = "rescindReason", nullable = true, length = 200)
	public String getRescindReason() {
		return rescindReason;
	}

	public void setRescindReason(String rescindReason) {
		this.rescindReason = rescindReason;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = true)
	public Person getRescindPerson() {
		return rescindPerson;
	}

	public void setRescindPerson(Person rescindPerson) {
		this.rescindPerson = rescindPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker", nullable = true)
	public Person getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Person checkPerson) {
		this.checkPerson = checkPerson;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "confirmer", nullable = true)
	public Person getConfirmPerson() {
		return confirmPerson;
	}

	public void setConfirmPerson(Person confirmPerson) {
		this.confirmPerson = confirmPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirmDate", length = 19, nullable = true)
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result
				+ ((checkPerson == null) ? 0 : checkPerson.hashCode());
		result = prime * result
				+ ((confirmDate == null) ? 0 : confirmDate.hashCode());
		result = prime * result
				+ ((confirmPerson == null) ? 0 : confirmPerson.hashCode());
		result = prime * result + ((hrDept == null) ? 0 : hrDept.hashCode());
		result = prime * result
				+ ((hrDeptHis == null) ? 0 : hrDeptHis.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result
				+ ((moveToDept == null) ? 0 : moveToDept.hashCode());
		result = prime * result
				+ ((moveToDeptHis == null) ? 0 : moveToDeptHis.hashCode());
		result = prime * result
				+ ((moveToSnapCode == null) ? 0 : moveToSnapCode.hashCode());
		result = prime * result
				+ ((rescindDate == null) ? 0 : rescindDate.hashCode());
		result = prime * result
				+ ((rescindNo == null) ? 0 : rescindNo.hashCode());
		result = prime * result
				+ ((rescindPerson == null) ? 0 : rescindPerson.hashCode());
		result = prime * result
				+ ((rescindReason == null) ? 0 : rescindReason.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
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
		HrDeptRescind other = (HrDeptRescind) obj;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (checkPerson == null) {
			if (other.checkPerson != null)
				return false;
		} else if (!checkPerson.equals(other.checkPerson))
			return false;
		if (confirmDate == null) {
			if (other.confirmDate != null)
				return false;
		} else if (!confirmDate.equals(other.confirmDate))
			return false;
		if (confirmPerson == null) {
			if (other.confirmPerson != null)
				return false;
		} else if (!confirmPerson.equals(other.confirmPerson))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (moveToDept == null) {
			if (other.moveToDept != null)
				return false;
		} else if (!moveToDept.equals(other.moveToDept))
			return false;
		if (moveToDeptHis == null) {
			if (other.moveToDeptHis != null)
				return false;
		} else if (!moveToDeptHis.equals(other.moveToDeptHis))
			return false;
		if (moveToSnapCode == null) {
			if (other.moveToSnapCode != null)
				return false;
		} else if (!moveToSnapCode.equals(other.moveToSnapCode))
			return false;
		if (rescindDate == null) {
			if (other.rescindDate != null)
				return false;
		} else if (!rescindDate.equals(other.rescindDate))
			return false;
		if (rescindNo == null) {
			if (other.rescindNo != null)
				return false;
		} else if (!rescindNo.equals(other.rescindNo))
			return false;
		if (rescindPerson == null) {
			if (other.rescindPerson != null)
				return false;
		} else if (!rescindPerson.equals(other.rescindPerson))
			return false;
		if (rescindReason == null) {
			if (other.rescindReason != null)
				return false;
		} else if (!rescindReason.equals(other.rescindReason))
			return false;
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
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
		return "HrDeptRescind [id=" + id + ", rescindNo=" + rescindNo
				+ ", hrDept=" + hrDept + ", hrDeptHis=" + hrDeptHis
				+ ", moveToDept=" + moveToDept + ", moveToDeptHis="
				+ moveToDeptHis + ", moveToSnapCode=" + moveToSnapCode
				+ ", snapCode=" + snapCode + ", state=" + state
				+ ", rescindReason=" + rescindReason + ", rescindPerson="
				+ rescindPerson + ", rescindDate=" + rescindDate
				+ ", makeDate=" + makeDate + ", checkPerson=" + checkPerson
				+ ", confirmPerson=" + confirmPerson + ", checkDate="
				+ checkDate + ", confirmDate=" + confirmDate + "]";
	}

}
