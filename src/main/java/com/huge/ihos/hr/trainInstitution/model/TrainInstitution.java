package com.huge.ihos.hr.trainInstitution.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 培训机构
 * 
 * @date 2014年12月17日
 */
@Entity
@Table(name = "hr_train_institution")
public class TrainInstitution extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9209469136550765482L;
	private String id;// 主键
	private String code;// 培训机构编号
	private String name;// 培训机构名称
	private String remark;// 培训机构简介
	private String contacts;// 联系人
	private String contactNumber;// 联系电话
	private String contactAddress;// 联系地址
	private String postCode;// 邮编
	private String webSite;// WEB网址
	private String email;// Email信箱
	private String evaluationGrade;// 测评等级
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

	@Column(name = "disabled", nullable = true)
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "evaluation_grade", nullable = false, length = 20)
	public String getEvaluationGrade() {
		return evaluationGrade;
	}

	public void setEvaluationGrade(String evaluationGrade) {
		this.evaluationGrade = evaluationGrade;
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

	@Column(name = "contact_number", nullable = true, length = 50)
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name = "contact_address", nullable = true, length = 100)
	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	@Column(name = "postCode", nullable = true, length = 10)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "webSite", nullable = true, length = 50)
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Column(name = "email", nullable = true, length = 30)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
				+ ((evaluationGrade == null) ? 0 : evaluationGrade.hashCode());
		result = prime * result
				+ ((contactNumber == null) ? 0 : contactNumber.hashCode());
		result = prime * result
				+ ((contactAddress == null) ? 0 : contactAddress.hashCode());
		result = prime * result
				+ ((postCode == null) ? 0 : postCode.hashCode());
		result = prime * result + ((webSite == null) ? 0 : webSite.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		TrainInstitution other = (TrainInstitution) obj;
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
		if (evaluationGrade == null) {
			if (other.evaluationGrade != null)
				return false;
		} else if (!evaluationGrade.equals(other.evaluationGrade))
			return false;
		if (contactNumber == null) {
			if (other.contactNumber != null)
				return false;
		} else if (!contactNumber.equals(other.contactNumber))
			if (contactAddress == null) {
				if (other.contactAddress != null)
					return false;
			} else if (!contactAddress.equals(other.contactAddress))
				return false;
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		if (webSite == null) {
			if (other.webSite != null)
				return false;
		} else if (!webSite.equals(other.webSite))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainInstitution [code=" + code + ", remark=" + remark
				+ ", name=" + name + ", contacts=" + contacts
				+ ", evaluationGrade=" + evaluationGrade + ", contactNumber="
				+ contactNumber + ", contactAddress=" + contactAddress
				+ ", postCode=" + postCode + ", webSite=" + webSite
				+ ", email=" + email + "]";
	}
}
