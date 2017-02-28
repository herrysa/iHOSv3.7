package com.huge.ihos.material.purchaseplan.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;
/**
 * 采购计划
 * @author Gaozhengyang
 * @date 2014年5月8日
 */
@Entity
@Table(name = "mm_purchase_plan_main")
public class PurchasePlan extends BaseObject implements Serializable,Cloneable{
	private static final long serialVersionUID = 8255626535629364756L;

	private String purchaseId;
	private String purchaseNo;
	private String orgCode;
	private String copyCode;
	private String periodMonth;
	private Department dept;
	private Store store;
//	private Boolean isDiff = false;// 是否终止计划
	private Person makePerson;
	private Date makeDate;
	private Person checkPerson;
	private Date checkDate;
	private String remark;
	private String state;
	private String docTemId;
	private String orderNo;

	@Column(name = "docTemId", length = 32, nullable = true)
	public String getDocTemId() {
		return docTemId;
	}

	public void setDocTemId(String docTemId) {
		this.docTemId = docTemId;
	}

	private Set<PurchasePlanDetail> purchasePlanDetails;
	
	@OneToMany(fetch=FetchType.LAZY,orphanRemoval=true,mappedBy="purchasePlan",cascade=CascadeType.ALL)
	public Set<PurchasePlanDetail> getPurchasePlanDetails() {
		return purchasePlanDetails;
	}

	public void setPurchasePlanDetails(Set<PurchasePlanDetail> purchasePlanDetails) {
		this.purchasePlanDetails = purchasePlanDetails;
	}

	@Id
	@Column(name = "purchaseId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	@Column(name = "purchaseNo", length = 20, nullable = true)
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "copyCode", length = 10, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name = "periodMonth", length = 6, nullable = false)
	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = false)
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
//	@Column(name = "isDiff", nullable = false, columnDefinition = "bit default 0")
//	public Boolean getIsDiff() {
//		return isDiff;
//	}
//
//	public void setIsDiff(Boolean isDiff) {
//		this.isDiff = isDiff;
//	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = false)
	public Person getMakePerson() {
		return makePerson;
	}

	public void setMakePerson(Person makePerson) {
		this.makePerson = makePerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = false)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker", nullable = true)
	public Person getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Person checkPerson) {
		this.checkPerson = checkPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "state", length = 1, nullable = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name = "orderNo", length = 20, nullable = true)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
//		result = prime * result + ((isDiff == null) ? 0 : isDiff.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result
				+ ((makePerson == null) ? 0 : makePerson.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((periodMonth == null) ? 0 : periodMonth.hashCode());
		result = prime * result
				+ ((purchaseNo == null) ? 0 : purchaseNo.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result + ((docTemId == null) ? 0 : docTemId.hashCode());
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
		PurchasePlan other = (PurchasePlan) obj;
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
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
//		if (isDiff == null) {
//			if (other.isDiff != null)
//				return false;
//		} else if (!isDiff.equals(other.isDiff))
//			return false;
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
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (periodMonth == null) {
			if (other.periodMonth != null)
				return false;
		} else if (!periodMonth.equals(other.periodMonth))
			return false;
		if (purchaseNo == null) {
			if (other.purchaseNo != null)
				return false;
		} else if (!purchaseNo.equals(other.purchaseNo))
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
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		if (docTemId == null) {
			if (other.docTemId != null)
				return false;
		} else if (!docTemId.equals(other.docTemId))
			return false;
		
		if(orderNo ==null){
			if(other.orderNo !=null)
				return false;
		}else if(!orderNo.equals(other.orderNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PurchasePlan [purchaseId=" + purchaseId + ", purchaseNo="
				+ purchaseNo + ", orgCode=" + orgCode + ", copyCode="
				+ copyCode + ", periodMonth=" + periodMonth + ", dept=" + dept
				+ ", store=" + store + ", makePerson="
				+ makePerson + ", makeDate=" + makeDate + ", checkPerson="
				+ checkPerson + ", checkDate=" + checkDate + ", remark="
				+ remark + ", state=" + state + ",docTemId="+docTemId+
				",orderNo="+orderNo+"]";
	}
	//Clone
	public PurchasePlan clone() {  
		PurchasePlan o = null;  
        try {  
            o = (PurchasePlan) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }  

}
