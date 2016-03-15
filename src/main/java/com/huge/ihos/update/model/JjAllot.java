package com.huge.ihos.update.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;

@Entity
@Table(name = "jj_t_jj2")
public class JjAllot extends BaseObject implements Serializable {

	private Integer id;
	private String checkPeriod;
	private Department jjDeptId;
	private Department allotDeptId;
	private BigDecimal amount;
	private String remark;
	private String itemName;
	private BigDecimal a=new BigDecimal(0);
	private BigDecimal b=new BigDecimal(0);
	private BigDecimal c=new BigDecimal(0);

	@Id
    @Column( name = "id")
    @GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column( name = "checkPeriod", length = 6)
	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "jjDeptId")
	public Department getJjDeptId() {
		return jjDeptId;
	}

	public void setJjDeptId(Department jjDeptId) {
		this.jjDeptId = jjDeptId;
	}

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "allotDeptId")
	public Department getAllotDeptId() {
		return allotDeptId;
	}

	public void setAllotDeptId(Department allotDeptId) {
		this.allotDeptId = allotDeptId;
	}

	@Column( name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column( name = "remark", length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column( name = "itemName", length = 30)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column( name = "a")
	public BigDecimal getA() {
		return a;
	}

	public void setA(BigDecimal a) {
		this.a = a;
	}

	@Column( name = "b")
	public BigDecimal getB() {
		return b;
	}

	public void setB(BigDecimal b) {
		this.b = b;
	}

	@Column( name = "c")
	public BigDecimal getC() {
		return c;
	}

	public void setC(BigDecimal c) {
		this.c = c;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result
				+ ((allotDeptId == null) ? 0 : allotDeptId.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result
				+ ((checkPeriod == null) ? 0 : checkPeriod.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result
				+ ((jjDeptId == null) ? 0 : jjDeptId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		JjAllot other = (JjAllot) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (allotDeptId == null) {
			if (other.allotDeptId != null)
				return false;
		} else if (!allotDeptId.equals(other.allotDeptId))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		if (checkPeriod == null) {
			if (other.checkPeriod != null)
				return false;
		} else if (!checkPeriod.equals(other.checkPeriod))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (jjDeptId == null) {
			if (other.jjDeptId != null)
				return false;
		} else if (!jjDeptId.equals(other.jjDeptId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JjAllot [id=" + id + ", checkPeriod=" + checkPeriod
				+ ", jjDeptId=" + jjDeptId + ", allotDeptId=" + allotDeptId
				+ ", amount=" + amount + ", remark=" + remark + ", itemName="
				+ itemName + ", a=" + a + ", b=" + b + ", c=" + c + "]";
	}

	
}
