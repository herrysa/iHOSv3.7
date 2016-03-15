package com.huge.ihos.hr.pact.model;

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

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 解除合同
 * 
 * @author Gaozhengyang
 * @date 2014年11月24日
 */
@Entity
@Table(name = "hr_pact_relieve")
public class PactRelieve extends BaseObject implements Serializable {
	private static final long serialVersionUID = -964000151297815134L;
	private String id;
	private String relieveNo;
	private Pact pact;
	private Date relieveDate;// 解除日期
	private String relieveReason;// 解除原因
	private Integer state;// 1:新建；2：审核;3:解除成功
	private String remark;
	private Person makePerson;
	private Person checkPerson;
	private Person confirmPerson;
	private Date makeDate;
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

	@Column(name = "relieveNo", nullable = false, length = 30)
	public String getRelieveNo() {
		return relieveNo;
	}

	public void setRelieveNo(String relieveNo) {
		this.relieveNo = relieveNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pactId", nullable = false)
	public Pact getPact() {
		return pact;
	}

	public void setPact(Pact pact) {
		this.pact = pact;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "relieveDate", length = 19, nullable = true)
	public Date getRelieveDate() {
		return relieveDate;
	}

	public void setRelieveDate(Date relieveDate) {
		this.relieveDate = relieveDate;
	}

	@Column(name = "relieveReason", nullable = true, length = 200)
	public String getRelieveReason() {
		return relieveReason;
	}

	public void setRelieveReason(String relieveReason) {
		this.relieveReason = relieveReason;
	}

	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = true)
	public Person getMakePerson() {
		return makePerson;
	}

	public void setMakePerson(Person makePerson) {
		this.makePerson = makePerson;
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
	@Column(name = "makeDate", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result
				+ ((makePerson == null) ? 0 : makePerson.hashCode());
		result = prime * result + ((pact == null) ? 0 : pact.hashCode());
		result = prime * result
				+ ((relieveDate == null) ? 0 : relieveDate.hashCode());
		result = prime * result
				+ ((relieveNo == null) ? 0 : relieveNo.hashCode());
		result = prime * result
				+ ((relieveReason == null) ? 0 : relieveReason.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		PactRelieve other = (PactRelieve) obj;
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
		if (makePerson == null) {
			if (other.makePerson != null)
				return false;
		} else if (!makePerson.equals(other.makePerson))
			return false;
		if (pact == null) {
			if (other.pact != null)
				return false;
		} else if (!pact.equals(other.pact))
			return false;
		if (relieveDate == null) {
			if (other.relieveDate != null)
				return false;
		} else if (!relieveDate.equals(other.relieveDate))
			return false;
		if (relieveNo == null) {
			if (other.relieveNo != null)
				return false;
		} else if (!relieveNo.equals(other.relieveNo))
			return false;
		if (relieveReason == null) {
			if (other.relieveReason != null)
				return false;
		} else if (!relieveReason.equals(other.relieveReason))
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
		return true;
	}

	@Override
	public String toString() {
		return "PactRelieve [id=" + id + ", relieveNo=" + relieveNo + ", pact="
				+ pact + ", relieveDate=" + relieveDate + ", relieveReason="
				+ relieveReason + ", state=" + state + ", remark=" + remark
				+ ", makePerson=" + makePerson + ", checkPerson=" + checkPerson
				+ ", confirmPerson=" + confirmPerson + ", makeDate=" + makeDate
				+ ", checkDate=" + checkDate + ", confirmDate=" + confirmDate
				+ "]";
	}

}
