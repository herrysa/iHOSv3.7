package com.huge.ihos.system.systemManager.organization.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.huge.model.BaseObject;

@Entity
@Table(name = "T_Org")
@FilterDef( name = "disableFilter", parameters = { @ParamDef( name = "disabled", type = "boolean" ) } )
@Filters( { @Filter( name = "disableFilter", condition = "disabled <> :disabled" ) } )
public class Org extends BaseObject {
	private static final long serialVersionUID = 5662286137136292988L;
	private String orgCode;
	private String orgname;
	private String shortName;
	private String internal;
	private String ownerOrg;
	private Boolean disabled;
	private Date invalidDate;
	private String address;
	private String phone;
	private String fax;
	private String contact;
	private String contactPhone;
	private String email;
	private String homePage;
	private String note;
	private String type;
	private Org parentOrgCode;
	private int personCount;//包含停用部门；包含停用人员
	private int personCountD;//不包含停用部门；包含停用人员
	private int personCountP;//包含停用部门；不包含停用人员
	private int personCountDP;//不包含停用部门；不包含停用人员
	private String postCode;
	private String snapCode="0";
	
	@Column(name="postCode",length=6)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Id
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="orgname",length=50,nullable=false)
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	@Column(name="shortName",length=30)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name="internal",length=20)
	public String getInternal() {
		return internal;
	}

	public void setInternal(String internal) {
		this.internal = internal;
	}

	@Column(name="ownerOrg",length=50)
	public String getOwnerOrg() {
		return ownerOrg;
	}

	public void setOwnerOrg(String ownerOrg) {
		this.ownerOrg = ownerOrg;
	}

	@Column(name="disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name="invalidDate")
	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name="address",length=100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="phone",length=20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="fax",length=20)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name="contact",length=20)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name="contactPhone",length=20)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name="email",length=20)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="homePage",length=30)
	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	@Column(name="note",length=100)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name="type",length=10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name="parentOrgCode")
	public Org getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(Org parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}
	
	@Column(name = "snapCode", length = 14, nullable = true)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}
	@Transient
	public int getPersonCountD() {
		return personCountD;
	}

	public void setPersonCountD(int personCountD) {
		this.personCountD = personCountD;
	}
	@Transient
	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}
	@Transient
	public int getPersonCountP() {
		return personCountP;
	}

	public void setPersonCountP(int personCountP) {
		this.personCountP = personCountP;
	}
	@Transient
	public int getPersonCountDP() {
		return personCountDP;
	}

	public void setPersonCountDP(int personCountDP) {
		this.personCountDP = personCountDP;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result
				+ ((contactPhone == null) ? 0 : contactPhone.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result
				+ ((homePage == null) ? 0 : homePage.hashCode());
		result = prime * result
				+ ((internal == null) ? 0 : internal.hashCode());
		result = prime * result
				+ ((invalidDate == null) ? 0 : invalidDate.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((orgname == null) ? 0 : orgname.hashCode());
		result = prime * result
				+ ((ownerOrg == null) ? 0 : ownerOrg.hashCode());
		//result = prime * result + ((parentOrgCode == null) ? 0 : parentOrgCode.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
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
		Org other = (Org) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (contactPhone == null) {
			if (other.contactPhone != null)
				return false;
		} else if (!contactPhone.equals(other.contactPhone))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (homePage == null) {
			if (other.homePage != null)
				return false;
		} else if (!homePage.equals(other.homePage))
			return false;
		if (internal == null) {
			if (other.internal != null)
				return false;
		} else if (!internal.equals(other.internal))
			return false;
		if (invalidDate == null) {
			if (other.invalidDate != null)
				return false;
		} else if (!invalidDate.equals(other.invalidDate))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (orgname == null) {
			if (other.orgname != null)
				return false;
		} else if (!orgname.equals(other.orgname))
			return false;
		if (ownerOrg == null) {
			if (other.ownerOrg != null)
				return false;
		} else if (!ownerOrg.equals(other.ownerOrg))
			return false;
		if (parentOrgCode == null) {
			if (other.parentOrgCode != null)
				return false;
		} else if (!parentOrgCode.equals(other.parentOrgCode))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Org [orgCode=" + orgCode + ", orgname=" + orgname
				+ ", shortName=" + shortName + ", internal=" + internal
				+ ", ownerOrg=" + ownerOrg + ", disabled=" + disabled
				+ ", invalidDate=" + invalidDate + ", address=" + address
				+ ", phone=" + phone + ", fax=" + fax + ", contact=" + contact
				+ ", contactPhone=" + contactPhone + ", email=" + email
				+ ", homePage=" + homePage + ", note=" + note + ", type="
				+ type + ", parentOrgCode=" + parentOrgCode + ", postCode="+postCode+"]";
	}
}
