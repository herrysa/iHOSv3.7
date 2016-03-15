package com.huge.ihos.hr.changesInPersonnel.model;

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

import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "hr_person_leave")
public class SysPersonLeave extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4911617320835829405L;

	private String id;// 主键ID

	private String code;// 申请编码

	private String name;// 申请名称

	private HrPersonCurrent hrPerson;// 员工
	private HrPersonHis hrPersonHis;
	private String personSnapCode;

	/*private String orgCode;// 单位

	private HrDepartmentCurrent hrDept;//部门
	private HrDepartmentHis hrDeptHis;
	private String deptSnapCode;
*/
	private Person maker;// 申请人

	private Date makeDate;// 申请时间

	private Person checker;// 审核人

	private Date checkDate;// 审核时间
	
	private Person doner;//执行人
	
	private Date doneDate;//执行时间

	private String remark;// 备注

	private String state;// 状态

	private String reason;// 离职原因

	private String type;// 离职类别

	private Date leaveDate;// 离职日期
	private String yearMonth;
	
	private String changedSnapCode;
	
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

	@Column(name = "code", nullable = true, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = true, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId", nullable = false)
	public HrPersonCurrent getHrPerson() {
		return hrPerson;
	}

	public void setHrPerson(HrPersonCurrent hrPerson) {
		this.hrPerson = hrPerson;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "personId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "personSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrPersonHis getHrPersonHis() {
		return hrPersonHis;
	}

	public void setHrPersonHis(HrPersonHis hrPersonHis) {
		this.hrPersonHis = hrPersonHis;
	}

	@Column(name = "personSnapCode", nullable = true, length = 14)
	public String getPersonSnapCode() {
		return personSnapCode;
	}

	public void setPersonSnapCode(String personSnapCode) {
		this.personSnapCode = personSnapCode;
	}

	@Column(name = "changedSnapCode", nullable = true, length = 14)
	public String getChangedSnapCode() {
		return changedSnapCode;
	}

	public void setChangedSnapCode(String changedSnapCode) {
		this.changedSnapCode = changedSnapCode;
	}
	
	/*// snap
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

	@Column(name = "orgCode", nullable = true, length = 20)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}*/

	@Column(name = "state", length = 1, nullable = true)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = true)
	public Person getMaker() {
		return maker;
	}

	public void setMaker(Person maker) {
		this.maker = maker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker", nullable = true)
	public Person getChecker() {
		return checker;
	}

	public void setChecker(Person checker) {
		this.checker = checker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "reason", length = 200, nullable = true)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "type", length = 20, nullable = true)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "leaveDate", length = 19, nullable = true)
	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doner", nullable = true)
	public Person getDoner() {
		return doner;
	}

	public void setDoner(Person doner) {
		this.doner = doner;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "doneDate", length = 19, nullable = true)
	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result + ((checker == null) ? 0 : checker.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((doneDate == null) ? 0 : doneDate.hashCode());
		result = prime * result + ((doner == null) ? 0 : doner.hashCode());
		result = prime * result
				+ ((hrPerson == null) ? 0 : hrPerson.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((leaveDate == null) ? 0 : leaveDate.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result + ((maker == null) ? 0 : maker.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((personSnapCode == null) ? 0 : personSnapCode.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		SysPersonLeave other = (SysPersonLeave) obj;
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
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (doneDate == null) {
			if (other.doneDate != null)
				return false;
		} else if (!doneDate.equals(other.doneDate))
			return false;
		if (doner == null) {
			if (other.doner != null)
				return false;
		} else if (!doner.equals(other.doner))
			return false;
		if (hrPerson == null) {
			if (other.hrPerson != null)
				return false;
		} else if (!hrPerson.equals(other.hrPerson))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (leaveDate == null) {
			if (other.leaveDate != null)
				return false;
		} else if (!leaveDate.equals(other.leaveDate))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (maker == null) {
			if (other.maker != null)
				return false;
		} else if (!maker.equals(other.maker))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (personSnapCode == null) {
			if (other.personSnapCode != null)
				return false;
		} else if (!personSnapCode.equals(other.personSnapCode))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SysPersonLeave [id=" + id + ", code=" + code + ", name=" + name
				+ ", hrPerson=" + hrPerson + ", hrPersonHis=" + hrPersonHis
				+ ", personSnapCode=" + personSnapCode + ", maker=" + maker
				+ ", makeDate=" + makeDate + ", checker=" + checker
				+ ", checkDate=" + checkDate + ", doner=" + doner
				+ ", doneDate=" + doneDate + ", remark=" + remark + ", state="
				+ state + ", reason=" + reason + ", type=" + type
				+ ", leaveDate=" + leaveDate + "]";
	}
}
