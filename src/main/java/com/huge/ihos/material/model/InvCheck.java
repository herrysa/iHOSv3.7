package com.huge.ihos.material.model;

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

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "mm_check")
public class InvCheck extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2230561210208634319L;
	/**
	 * 
	 */

	// check_id Char 32 Uuid
	private String checkId;
	// OrgCode 单位编码
	private String orgCode;
	// copyCode 账套编码
	private String copyCode;
	// yearMonth char 6 所属期间
	private String yearMonth;
	// Check_No 盘点单号：根据规则生成 PD-201311001
	private String checkNo;
	// StoreId 库房id
	private Store store;
	// Deptid 盘点科室
	private Department dept;
	// Personid 经办人
	private Person person;
	// Makeid 制单人
	private Person makePerson;
	// Checkid 审核人
	private Person checkPerson;
	// makedate 盘点日期 （精确到天即可）
	private Date checkDate;
	private Date makeDate;
	private Person confirmPerson;
	private Date confirmDate;
	// State 0 新建 1 已审核 2 已生成出入库单
	private String state;
	// Remark 摘要
	private String remark;
	// In_id 盘盈入库单id
	private InvMain invMainIn;
	// Out_id 盘盈出库单号id
	private InvMain invMainOut;
	private Double totalDiffMoney = 0.0;
	private String docTemId;

	@Column(name = "docTemId", length = 32, nullable = true)
	public String getDocTemId() {
		return docTemId;
	}

	public void setDocTemId(String docTemId) {
		this.docTemId = docTemId;
	}

	@Override
	public String toString() {
		return "InvCheck [checkId=" + checkId + ", orgCode=" + orgCode + ", copyCode=" + copyCode + ", yearMonth=" + yearMonth + ", checkNo=" + checkNo + ", store=" + store + ", dept=" + dept
				+ ", person=" + person + ", makePerson=" + makePerson + ", checkPerson=" + checkPerson + ", checkDate=" + checkDate + ", state=" + state + ", remark=" + remark + ", inId=" + this.getInvMainIn().getIoBillNumber()
				+ ", makeDate=" + makeDate + ", confirmPerson=" + confirmPerson + ", confirmDate=" + confirmDate +",docTemId="+docTemId
				+ ",totalDiffMoney="+totalDiffMoney+", outId=" + this.getInvMainOut().getIoBillNumber() + "]";
	}

	@Id
	@Column(name = "checkId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "confirmPersonId", nullable = true)
	public Person getConfirmPerson() {
		return confirmPerson;
	}

	public void setConfirmPerson(Person confirmPerson) {
		this.confirmPerson = confirmPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirmDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name = "orgCode", length = 20, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "copyCode", length = 20, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name = "yearMonth", length = 6, nullable = false)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Column(name = "checkNo", length = 20, nullable = false)
	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = true)
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId", nullable = true)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "makeId", nullable = true)
	public Person getMakePerson() {
		return makePerson;
	}

	public void setMakePerson(Person makePerson) {
		this.makePerson = makePerson;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chkPersonId", nullable = true)
	public Person getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Person checkPerson) {
		this.checkPerson = checkPerson;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "state", length = 1, nullable = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "remark", length = 100, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inId", nullable = true)
	public InvMain getInvMainIn() {
		return invMainIn;
	}

	public void setInvMainIn(InvMain invMainIn) {
		this.invMainIn = invMainIn;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "outId", nullable = true)
	public InvMain getInvMainOut() {
		return invMainOut;
	}

	public void setInvMainOut(InvMain invMainOut) {
		this.invMainOut = invMainOut;
	}
	
	

//	@Column(name = "inId", length = 20, nullable = true)
//	public String getInId() {
//		return inId;
//	}
//
//	public void setInId(String inId) {
//		this.inId = inId;
//	}
	@Column(name = "totalDiffMoney",nullable = false)
	public Double getTotalDiffMoney() {
		return totalDiffMoney;
	}

	public void setTotalDiffMoney(Double totalDiffMoney) {
		this.totalDiffMoney = totalDiffMoney;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvCheck other = (InvCheck) obj;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (checkId == null) {
			if (other.checkId != null)
				return false;
		} else if (!checkId.equals(other.checkId))
			return false;
		if (checkNo == null) {
			if (other.checkNo != null)
				return false;
		} else if (!checkNo.equals(other.checkNo))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
//		if (inId == null) {
//			if (other.inId != null)
//				return false;
//		} else if (!inId.equals(other.inId))
//			return false;
//		if (orgCode == null) {
//			if (other.orgCode != null)
//				return false;
//		} else if (!orgCode.equals(other.orgCode))
//			return false;
//		if (outId == null) {
//			if (other.outId != null)
//				return false;
//		} else if (!outId.equals(other.outId))
//			return false;
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
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		if (totalDiffMoney == null) {
			if (other.totalDiffMoney != null)
				return false;
		} else if (!totalDiffMoney.equals(other.totalDiffMoney))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (confirmPerson == null) {
			if (other.confirmPerson != null)
				return false;
		} else if (!confirmPerson.equals(other.confirmPerson))
			return false;
		if (confirmDate == null) {
			if (other.confirmDate != null)
				return false;
		} else if (!confirmDate.equals(other.confirmDate))
			return false;
		if (docTemId == null) {
			if (other.docTemId != null)
				return false;
		} else if (!docTemId.equals(other.docTemId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result + ((checkId == null) ? 0 : checkId.hashCode());
		result = prime * result + ((checkNo == null) ? 0 : checkNo.hashCode());
		result = prime * result + ((copyCode == null) ? 0 : copyCode.hashCode());
//		result = prime * result + ((inId == null) ? 0 : inId.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
//		result = prime * result + ((outId == null) ? 0 : outId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result + ((yearMonth == null) ? 0 : yearMonth.hashCode());
		result = prime * result + ((totalDiffMoney == null) ? 0 : totalDiffMoney.hashCode());
		result = prime * result + ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result + ((confirmPerson == null) ? 0 : confirmPerson.hashCode());
		result = prime * result + ((confirmDate == null) ? 0 : confirmDate.hashCode());
		result = prime * result + ((docTemId == null) ? 0 : docTemId.hashCode());
		return result;
	}

}
