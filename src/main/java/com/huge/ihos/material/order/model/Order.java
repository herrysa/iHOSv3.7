package com.huge.ihos.material.order.model;

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

import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 采购订单
 * 
 * @author Gaozhengyang
 * @date 2014年5月12日
 */
@Entity
@Table(name = "mm_order_main")
public class Order extends BaseObject implements Serializable,Cloneable {
	private static final long serialVersionUID = -4781636845294758030L;
	private String orderId;
	private String orgCode;
	private String copyCode;
	private String periodMonth;
	private String orderNo;
	private ProcType procType;// 采购类型
	private Person makePerson;
	private Date makeDate;
	private Person checkPerson;
	private Date checkDate;
	private String state;
	private String remark;
	private Vendor vendor;
	private Department dept;
	private Person buyPerson;// 采购员
	private String arrAddr;// 到货地址
	private String transType;// 运输方式1:航空运输,2:河海运输,3:汽车运输,4:铁路运输,5:邮件运输
	private PayCondition payCon;// 付款条件
	private Double taxRate;// 税率
	private Double bargain;// 定金
	private Double cost;// 费用
//	private Boolean isDiff;// 是否中止订单
	private Boolean isNotify = false;// 是否通知[供货商]
	private Date notifyDate;// 通知日期
//	private Boolean isExec;// 是否执行
//	private Date execDate;// 执行日期
//	private Person closePerson;
//	private Date closeDate;
	private String docTemId;

	@Column(name = "docTemId", length = 32, nullable = true)
	public String getDocTemId() {
		return docTemId;
	}

	public void setDocTemId(String docTemId) {
		this.docTemId = docTemId;
	}

	private Set<OrderDetail> orderDetails;
	@OneToMany(fetch=FetchType.LAZY,orphanRemoval=true,mappedBy="order",cascade=CascadeType.ALL)
	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Id
	@Column(name = "orderId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	@Column(name = "orderNo", length = 20, nullable = false)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procTypeId", nullable = false)
	public ProcType getProcType() {
		return procType;
	}

	public void setProcType(ProcType procType) {
		this.procType = procType;
	}

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

	@Column(name = "state", length = 1, nullable = false)
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
	@JoinColumn(name = "vendorId", nullable = true)
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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
	@JoinColumn(name = "buyer", nullable = true)
	public Person getBuyPerson() {
		return buyPerson;
	}

	public void setBuyPerson(Person buyPerson) {
		this.buyPerson = buyPerson;
	}

	@Column(name = "arrAddr", length = 100, nullable = true)
	public String getArrAddr() {
		return arrAddr;
	}

	public void setArrAddr(String arrAddr) {
		this.arrAddr = arrAddr;
	}

	@Column(name = "transType", length = 2, nullable = true)
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payConId", nullable = true)
	public PayCondition getPayCon() {
		return payCon;
	}

	public void setPayCon(PayCondition payCon) {
		this.payCon = payCon;
	}

	@Column(name = "taxRate", nullable = true)
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name = "bargain", nullable = true)
	public Double getBargain() {
		return bargain;
	}

	public void setBargain(Double bargain) {
		this.bargain = bargain;
	}

	@Column(name = "cost", nullable = true)
	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

//	@Column(name = "isDiff", nullable = false, columnDefinition = "bit default 0")
//	public Boolean getIsDiff() {
//		return isDiff;
//	}
//
//	public void setIsDiff(Boolean isDiff) {
//		this.isDiff = isDiff;
//	}

	@Column(name = "isNotify", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsNotify() {
		return isNotify;
	}

	public void setIsNotify(Boolean isNotify) {
		this.isNotify = isNotify;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notifyDate", length = 19, nullable = true)
	public Date getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(Date notifyDate) {
		this.notifyDate = notifyDate;
	}

//	@Column(name = "isExec", nullable = false, columnDefinition = "bit default 0")
//	public Boolean getIsExec() {
//		return isExec;
//	}
//
//	public void setIsExec(Boolean isExec) {
//		this.isExec = isExec;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "execDate", length = 19, nullable = true)
//	public Date getExecDate() {
//		return execDate;
//	}
//
//	public void setExecDate(Date execDate) {
//		this.execDate = execDate;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrAddr == null) ? 0 : arrAddr.hashCode());
		result = prime * result + ((bargain == null) ? 0 : bargain.hashCode());
		result = prime * result
				+ ((buyPerson == null) ? 0 : buyPerson.hashCode());
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result
				+ ((checkPerson == null) ? 0 : checkPerson.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
//		result = prime * result
//				+ ((execDate == null) ? 0 : execDate.hashCode());
//		result = prime * result + ((isDiff == null) ? 0 : isDiff.hashCode());
//		result = prime * result + ((isExec == null) ? 0 : isExec.hashCode());
		result = prime * result
				+ ((isNotify == null) ? 0 : isNotify.hashCode());
		result = prime * result
				+ ((makePerson == null) ? 0 : makePerson.hashCode());
		result = prime * result
				+ ((notifyDate == null) ? 0 : notifyDate.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((payCon == null) ? 0 : payCon.hashCode());
		result = prime * result
				+ ((periodMonth == null) ? 0 : periodMonth.hashCode());
		result = prime * result
				+ ((procType == null) ? 0 : procType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((taxRate == null) ? 0 : taxRate.hashCode());
		result = prime * result
				+ ((transType == null) ? 0 : transType.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
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
		Order other = (Order) obj;
		if (arrAddr == null) {
			if (other.arrAddr != null)
				return false;
		} else if (!arrAddr.equals(other.arrAddr))
			return false;
		if (bargain == null) {
			if (other.bargain != null)
				return false;
		} else if (!bargain.equals(other.bargain))
			return false;
		if (buyPerson == null) {
			if (other.buyPerson != null)
				return false;
		} else if (!buyPerson.equals(other.buyPerson))
			return false;
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
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
//		if (execDate == null) {
//			if (other.execDate != null)
//				return false;
//		} else if (!execDate.equals(other.execDate))
//			return false;
//		if (isDiff == null) {
//			if (other.isDiff != null)
//				return false;
//		} else if (!isDiff.equals(other.isDiff))
//			return false;
//		if (isExec == null) {
//			if (other.isExec != null)
//				return false;
//		} else if (!isExec.equals(other.isExec))
//			return false;
		if (isNotify == null) {
			if (other.isNotify != null)
				return false;
		} else if (!isNotify.equals(other.isNotify))
			return false;
		if (makePerson == null) {
			if (other.makePerson != null)
				return false;
		} else if (!makePerson.equals(other.makePerson))
			return false;
		if (notifyDate == null) {
			if (other.notifyDate != null)
				return false;
		} else if (!notifyDate.equals(other.notifyDate))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (payCon == null) {
			if (other.payCon != null)
				return false;
		} else if (!payCon.equals(other.payCon))
			return false;
		if (periodMonth == null) {
			if (other.periodMonth != null)
				return false;
		} else if (!periodMonth.equals(other.periodMonth))
			return false;
		if (procType == null) {
			if (other.procType != null)
				return false;
		} else if (!procType.equals(other.procType))
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
		if (taxRate == null) {
			if (other.taxRate != null)
				return false;
		} else if (!taxRate.equals(other.taxRate))
			return false;
		if (transType == null) {
			if (other.transType != null)
				return false;
		} else if (!transType.equals(other.transType))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		if (docTemId == null) {
			if (other.docTemId != null)
				return false;
		} else if (!docTemId.equals(other.docTemId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", periodMonth=" + periodMonth
				+ ", orderNo=" + orderNo + ", procType=" + procType
				+ ", makePerson=" + makePerson + ", makeDate=" + makeDate
				+ ", checkPerson=" + checkPerson + ", checkDate=" + checkDate
				+ ", state=" + state + ", remark=" + remark + ", vendor="
				+ vendor + ", dept=" + dept + ", buyPerson=" + buyPerson
				+ ", arrAddr=" + arrAddr + ", transType=" + transType
				+ ", payCon=" + payCon + ", taxRate=" + taxRate + ", bargain="
				+ bargain + ", cost=" + cost +", docTemId="+docTemId
				+ ", isNotify=" + isNotify + ", notifyDate=" + notifyDate + "]";
	}
    //Cloneable接口的实现
	@Override
	public Order clone() {  
		Order o = null;  
        try {  
            o = (Order) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }  
}
