package com.huge.ihos.hr.trainEquipment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 培训设备
 */
@Entity
@Table(name = "hr_train_equipment")
public class TrainEquipment extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1682078149032120013L;
	private String id;// 主键
	private String code;// 设备编号
	private String name;// 设备名称
	private String category;// 设备类型
	private String state;// 设备状态
	private Date acquisitionDate;// 购置日期
	private Double acquisitonExpenses;// 购置费用
	private String acquisitionAmount;// 购置数量
	private String remark;// 设施说明

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

	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "acquisition_date", length = 19, nullable = true)
	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "acquisiton_expenses", nullable = true, precision = 12, scale = 2)
	public Double getAcquisitonExpenses() {
		return acquisitonExpenses;
	}

	public void setAcquisitonExpenses(Double acquisitonExpenses) {
		this.acquisitonExpenses = acquisitonExpenses;
	}

	@Column(name = "category", nullable = true, length = 20)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "state", nullable = true, length = 20)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "acquisition_amount", nullable = true, length = 20)
	public String getAcquisitionAmount() {
		return acquisitionAmount;
	}

	public void setAcquisitionAmount(String acquisitionAmount) {
		this.acquisitionAmount = acquisitionAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((acquisitionDate == null) ? 0 : acquisitionDate.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((acquisitonExpenses == null) ? 0 : acquisitonExpenses
						.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime
				* result
				+ ((acquisitionAmount == null) ? 0 : acquisitionAmount
						.hashCode());
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
		TrainEquipment other = (TrainEquipment) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (acquisitionDate == null) {
			if (other.acquisitionDate != null)
				return false;
		} else if (!acquisitionDate.equals(other.acquisitionDate))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (acquisitonExpenses == null) {
			if (other.acquisitonExpenses != null)
				return false;
		} else if (!acquisitonExpenses.equals(other.acquisitonExpenses))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (acquisitionAmount == null) {
			if (other.acquisitionAmount != null)
				return false;
		} else if (!acquisitionAmount.equals(other.acquisitionAmount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainEquipment [code=" + code + ", acquisitionDate="
				+ acquisitionDate + ", remark=" + remark + ", name=" + name
				+ ", acquisitonExpenses=" + acquisitonExpenses + ", category="
				+ category + ", state=" + state + ", acquisitionAmount="
				+ acquisitionAmount + "]";
	}
}