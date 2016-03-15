package com.huge.ihos.update.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

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
import com.huge.ihos.system.systemManager.organization.model.PersonJj;
@Entity
@Table( name = "jj_t_Updata")
public class JjUpdata {

	private Long updataId;
	//private String KPIId;
	private String checkperiod = "";
	private String itemName = "";
	private BigDecimal amount = new BigDecimal(0) ;
	
	private BigDecimal zjj = new BigDecimal(0);
	private String remark = "";
	//private String remark2 = "";
	//private String remark3 = "";
	private Integer state = 0;
	
	private Department department;
	private Department ownerdept;
	
	private PersonJj person;
	
	private Person operator;
	private String operatorName;
	
	private Person operator1;
	private Person operator2;
	
	private Date operateDate;
	private Date operateDate1;
	private Date operateDate2;
	
	private String operatorInfo;
	private String operator1Info;
	private String operator2Info;
	
	//@Formula("(select * from jj_t_Updata)")
	private Map sqlDatamap;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public Long getUpdataId() {
		return updataId;
	}
	public void setUpdataId(Long updataId) {
		this.updataId = updataId;
	}
	
	@Column(name="checkperiod")
	public String getCheckperiod() {
		return checkperiod;
	}
	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}
	
	@Column(name="itemName")
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Column(name="amount")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(name="zjj")
	public BigDecimal getZjj() {
		return zjj;
	}
	public void setZjj(BigDecimal zjj) {
		this.zjj = zjj;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="deptId")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="ownerdept")
	public Department getOwnerdept() {
		return ownerdept;
	}
	public void setOwnerdept(Department ownerdept) {
		this.ownerdept = ownerdept;
	}
	
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="personId")
	public PersonJj getPerson() {
		return person;
	}
	public void setPerson(PersonJj person) {
		this.person = person;
	}
	
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="operatorId")
	public Person getOperator() {
		return operator;
	}
	public void setOperator(Person operator) {
		this.operator = operator;
	}
	@Column(name="operatorName")
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="operatorId1")
	public Person getOperator1() {
		return operator1;
	}
	public void setOperator1(Person operator1) {
		this.operator1 = operator1;
	}
	
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="operatorId2")
	public Person getOperator2() {
		return operator2;
	}
	public void setOperator2(Person operator2) {
		this.operator2 = operator2;
	}
	
	@Column(name="operateDate")
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	
	@Column(name="operateDate1")
	public Date getOperateDate1() {
		return operateDate1;
	}
	public void setOperateDate1(Date operateDate1) {
		this.operateDate1 = operateDate1;
	}
	
	@Column(name="operateDate2")
	public Date getOperateDate2() {
		return operateDate2;
	}
	public void setOperateDate2(Date operateDate2) {
		this.operateDate2 = operateDate2;
	}
	@Transient
	public String getOperatorInfo() {
		return operatorInfo;
	}
	public void setOperatorInfo(String operatorInfo) {
		this.operatorInfo = operatorInfo;
	}
	@Transient
	public String getOperator1Info() {
		return operator1Info;
	}
	public void setOperator1Info(String operator1Info) {
		this.operator1Info = operator1Info;
	}
	@Transient
	public String getOperator2Info() {
		return operator2Info;
	}
	public void setOperator2Info(String operator2Info) {
		this.operator2Info = operator2Info;
	}
	
	@Transient
	public Map getSqlDatamap() {
		return sqlDatamap;
	}
	public void setSqlDatamap(Map sqlDatamap) {
		this.sqlDatamap = sqlDatamap;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((checkperiod == null) ? 0 : checkperiod.hashCode());
		result = prime * result
				+ ((department == null) ? 0 : department.hashCode());
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result
				+ ((operateDate == null) ? 0 : operateDate.hashCode());
		result = prime * result
				+ ((operateDate1 == null) ? 0 : operateDate1.hashCode());
		result = prime * result
				+ ((operateDate2 == null) ? 0 : operateDate2.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result
				+ ((operator1 == null) ? 0 : operator1.hashCode());
		result = prime * result
				+ ((operator1Info == null) ? 0 : operator1Info.hashCode());
		result = prime * result
				+ ((operator2 == null) ? 0 : operator2.hashCode());
		result = prime * result
				+ ((operator2Info == null) ? 0 : operator2Info.hashCode());
		result = prime * result
				+ ((operatorInfo == null) ? 0 : operatorInfo.hashCode());
		result = prime * result
				+ ((ownerdept == null) ? 0 : ownerdept.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((updataId == null) ? 0 : updataId.hashCode());
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
		JjUpdata other = (JjUpdata) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (checkperiod == null) {
			if (other.checkperiod != null)
				return false;
		} else if (!checkperiod.equals(other.checkperiod))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (operateDate == null) {
			if (other.operateDate != null)
				return false;
		} else if (!operateDate.equals(other.operateDate))
			return false;
		if (operateDate1 == null) {
			if (other.operateDate1 != null)
				return false;
		} else if (!operateDate1.equals(other.operateDate1))
			return false;
		if (operateDate2 == null) {
			if (other.operateDate2 != null)
				return false;
		} else if (!operateDate2.equals(other.operateDate2))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (operator1 == null) {
			if (other.operator1 != null)
				return false;
		} else if (!operator1.equals(other.operator1))
			return false;
		if (operator1Info == null) {
			if (other.operator1Info != null)
				return false;
		} else if (!operator1Info.equals(other.operator1Info))
			return false;
		if (operator2 == null) {
			if (other.operator2 != null)
				return false;
		} else if (!operator2.equals(other.operator2))
			return false;
		if (operator2Info == null) {
			if (other.operator2Info != null)
				return false;
		} else if (!operator2Info.equals(other.operator2Info))
			return false;
		if (operatorInfo == null) {
			if (other.operatorInfo != null)
				return false;
		} else if (!operatorInfo.equals(other.operatorInfo))
			return false;
		if (ownerdept == null) {
			if (other.ownerdept != null)
				return false;
		} else if (!ownerdept.equals(other.ownerdept))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
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
		if (updataId == null) {
			if (other.updataId != null)
				return false;
		} else if (!updataId.equals(other.updataId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JjUpdate [updataId=" + updataId + ", checkperiod="
				+ checkperiod + ", itemName=" + itemName + ", amount=" + amount
				+ ", remark=" + remark + ", state=" + state + ", department="
				+ department + ", ownerdept=" + ownerdept + ", person="
				+ person + ", operator=" + operator + ", operator1="
				+ operator1 + ", operator2=" + operator2 + ", operateDate="
				+ operateDate + ", operateDate1=" + operateDate1
				+ ", operateDate2=" + operateDate2 + ", operatorInfo="
				+ operatorInfo + ", operator1Info=" + operator1Info
				+ ", operator2Info=" + operator2Info + "]";
	}
	
	
	
}
