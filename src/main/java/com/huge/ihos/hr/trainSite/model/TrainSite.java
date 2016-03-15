package com.huge.ihos.hr.trainSite.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 培训场所
 */
@Entity
@Table(name = "hr_train_site")
public class TrainSite extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6770443350829691171L;
	private String id;// 主键
	private String code;// 场所编号
	private String name;// 场所名称
	private String scale;// 场所规模
	private String equipment;// 场所设备
	private String remark;// 场所说明
	private String address;// 场所地址
	private Double rentCharge;// 租借费用
	private String contacts;// 场所联系人
	private String contactNumber;// 场所联系电话
	private String evaluationGrade;// 场所测评等级
	private Boolean disabled = false; // 是否可用

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

	@Column(name = "contact_number", nullable = true, length = 20)
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "disabled", nullable = true)
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
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

	@Column(name = "contacts", nullable = true, length = 20)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "scale", nullable = true, length = 20)
	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	@Column(name = "equipment", nullable = true, length = 50)
	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	@Column(name = "address", nullable = true, length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "rent_charge", nullable = true, precision = 12, scale = 2)
	public Double getRentCharge() {
		return rentCharge;
	}

	public void setRentCharge(Double rentCharge) {
		this.rentCharge = rentCharge;
	}

	@Column(name = "evaluation_grade", nullable = true, length = 20)
	public String getEvaluationGrade() {
		return evaluationGrade;
	}

	public void setEvaluationGrade(String evaluationGrade) {
		this.evaluationGrade = evaluationGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result
				+ ((contactNumber == null) ? 0 : contactNumber.hashCode());
		result = prime * result + ((scale == null) ? 0 : scale.hashCode());
		result = prime * result
				+ ((equipment == null) ? 0 : equipment.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((rentCharge == null) ? 0 : rentCharge.hashCode());
		result = prime * result
				+ ((evaluationGrade == null) ? 0 : evaluationGrade.hashCode());
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
		TrainSite other = (TrainSite) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		if (contactNumber == null) {
			if (other.contactNumber != null)
				return false;
		} else if (!contactNumber.equals(other.contactNumber))
			return false;
		if (scale == null) {
			if (other.scale != null)
				return false;
		} else if (!scale.equals(other.scale))
			return false;
		if (equipment == null) {
			if (other.equipment != null)
				return false;
		} else if (!equipment.equals(other.equipment))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (rentCharge == null) {
			if (other.rentCharge != null)
				return false;
		} else if (!rentCharge.equals(other.rentCharge))
			return false;
		if (evaluationGrade == null) {
			if (other.evaluationGrade != null)
				return false;
		} else if (!evaluationGrade.equals(other.evaluationGrade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainSite [code=" + code + ", remark=" + remark + ", name="
				+ name + ", contacts=" + contacts + ", contactNumber="
				+ contactNumber + ", scale=" + scale + ", compSignPerson="
				+ ", equipment=" + equipment + ", address=" + address
				+ ", rentCharge=" + rentCharge + ", evaluationGrade="
				+ evaluationGrade + "]";
	}
}