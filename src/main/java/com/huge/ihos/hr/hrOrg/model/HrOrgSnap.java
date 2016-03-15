package com.huge.ihos.hr.hrOrg.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.huge.model.BaseObject;

@Entity
@Table(name = "hr_org_snap")
public class HrOrgSnap extends BaseObject implements Serializable ,Cloneable {
	private static final long serialVersionUID = -8367310882845892276L;
	private String snapId;
	private String snapCode;
	private String orgCode;
	private String orgname;
	private String shortName;
	private String internal;
	private String ownerOrg;
	private Boolean disabled = false;
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
	private String parentOrgCode;
	private HrOrg parentOrg;
	private HrOrgHis parentOrgHis;
	private String parentSnapCode;
	private Boolean deleted = false;
	private String postCode;
	private int personCount = 0;//包含停用部门，包含停用人员
	private int personCountD = 0;//不包含停用部门，包含停用人员
	private int personCountP = 0;//包含停用部门，不包含停用人员
	private int personCountDP = 0;//不包含停用部门，不包含停用人员
	
	@Column(name = "postCode", length = 6, nullable = true)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
	@Id
	@Column(name = "snapId", length = 25, nullable = false)
	public String getSnapId() {
		return snapId;
	}

	public void setSnapId(String snapId) {
		this.snapId = snapId;
	}

	@Column(name = "snapCode", length = 14, nullable = false)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "orgname", length = 50, nullable = false)
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	@Column(name = "shortName", length = 30, nullable = true)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "internal", length = 20, nullable = true)
	public String getInternal() {
		return internal;
	}

	public void setInternal(String internal) {
		this.internal = internal;
	}

	@Column(name = "ownerOrg", length = 50, nullable = true)
	public String getOwnerOrg() {
		return ownerOrg;
	}

	public void setOwnerOrg(String ownerOrg) {
		this.ownerOrg = ownerOrg;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invalidDate", length = 19, nullable = true)
	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "address", length = 100, nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone", length = 20, nullable = true)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "fax", length = 20, nullable = true)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "contact", length = 20, nullable = true)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "contactPhone", length = 20, nullable = true)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name = "email", length = 20, nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "homePage", length = 30, nullable = true)
	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	@Column(name = "note", length = 200, nullable = true)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "type", length = 10, nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentOrgCode", nullable = true, insertable = false, updatable = false)
	public HrOrg getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(HrOrg parentOrg) {
		this.parentOrg = parentOrg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "parentOrgCode", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "parentSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrOrgHis getParentOrgHis() {
		return parentOrgHis;
	}

	public void setParentOrgHis(HrOrgHis parentOrgHis) {
		this.parentOrgHis = parentOrgHis;
	}

	@Column(name = "parentSnapCode", nullable = true, length = 14)
	public String getParentSnapCode() {
		return parentSnapCode;
	}

	public void setParentSnapCode(String parentSnapCode) {
		this.parentSnapCode = parentSnapCode;
	}
	
	@Column(name = "parentOrgCode", nullable = true, length = 10)
	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	@Column(name = "deleted", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result
				+ ((contactPhone == null) ? 0 : contactPhone.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
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
		result = prime * result
				+ ((parentOrg == null) ? 0 : parentOrg.hashCode());
		result = prime * result
				+ ((parentSnapCode == null) ? 0 : parentSnapCode.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		HrOrgSnap other = (HrOrgSnap) obj;
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
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
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
		if (parentOrg == null) {
			if (other.parentOrg != null)
				return false;
		} else if (!parentOrg.equals(other.parentOrg))
			return false;
		if (parentSnapCode == null) {
			if (other.parentSnapCode != null)
				return false;
		} else if (!parentSnapCode.equals(other.parentSnapCode))
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
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrOrgSnap [snapId=" + snapId + ", snapCode=" + snapCode
				+ ", orgCode=" + orgCode + ", orgname=" + orgname
				+ ", shortName=" + shortName + ", internal=" + internal
				+ ", ownerOrg=" + ownerOrg + ", disabled=" + disabled
				+ ", invalidDate=" + invalidDate + ", address=" + address
				+ ", phone=" + phone + ", fax=" + fax + ", contact=" + contact
				+ ", contactPhone=" + contactPhone + ", email=" + email
				+ ", homePage=" + homePage + ", note=" + note + ", type="
				+ type + ", parentOrg=" + parentOrg + ", parentOrgHis="
				+ parentOrgHis + ", parentSnapCode=" + parentSnapCode
				+ ", deleted=" + deleted + "]";
	}
	
	@Transient
	public Map<String,Object> getMapEntity(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("snapId", this.snapId);
		map.put("snapCode", this.snapCode);
		map.put("orgCode", this.orgCode);
		map.put("orgname", this.orgname);
		map.put("shortName", this.shortName);
		map.put("internal", this.internal);
		map.put("ownerOrg", this.ownerOrg);
		map.put("disabled", this.disabled);
		map.put("invalidDate", this.invalidDate);
		map.put("address", this.address);
		map.put("phone", this.phone);
		map.put("fax", this.fax);
		map.put("contact", this.contact);
		map.put("contactPhone", this.contactPhone);
		map.put("email", this.email);
		map.put("homePage", this.homePage);
		map.put("note", this.note);
		map.put("type", this.type);
		map.put("deleted", this.deleted);
		map.put("parentOrgCode", this.parentOrgCode);
		map.put("parentSnapCode", this.parentSnapCode);
		map.put("postCode", this.postCode);
		return map;
	}

	@Override
	public HrOrgSnap clone() {
		HrOrgSnap o = null;
		try {
			o = (HrOrgSnap) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
