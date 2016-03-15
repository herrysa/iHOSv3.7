package com.huge.ihos.accounting.account.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.model.BaseObject;

@Entity
@Table(name = "GL_accountType")
//@IdClass(OrgCopyPk.class)
public class AccountType extends BaseObject implements Serializable { 

	//private OrgCopyAtCodePk orgCopyAtCodePk;
	
	private String orgCode;
	private String copyCode;
	private String accouttypecode;
	private String accounttype;
	private String accttypeId;
	

	/*@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "orgId",
		column = @Column(name="OrgId")),
		@AttributeOverride(name = "copyCode",
		column = @Column(name="CopyCode")),
		@AttributeOverride(name = "accouttypecode",
		column = @Column(name="Accouttypecode"))
		})*/
	/*public OrgCopyAtCodePk getOrgCopyAtCodePk() {
		return orgCopyAtCodePk;
	}

	public void setOrgCopyAtCodePk(OrgCopyAtCodePk orgCopyAtCodePk) {
		this.orgCopyAtCodePk = orgCopyAtCodePk;
	}*/
	
	@Id
	@Column(name="AccttypeId")
	public String getAccttypeId() {
		return accttypeId;
	}

	public void setAccttypeId(String accttypeId) {
		this.accttypeId = accttypeId;
	}

	
	@Column(name="OrgCode")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="CopyCode")
	public String getCopyCode() {
		return this.copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name="Accttypecode")
	public String getAccouttypecode() {
		return this.accouttypecode ;
	}

	public void setAccouttypecode(String accouttypecode) {
		this.accouttypecode = accouttypecode;
	}

	@Column(name = "Accttype")
	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public AccountType(){}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accounttype == null) ? 0 : accounttype.hashCode());
		result = prime * result
				+ ((accouttypecode == null) ? 0 : accouttypecode.hashCode());
		result = prime * result
				+ ((accttypeId == null) ? 0 : accttypeId.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
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
		AccountType other = (AccountType) obj;
		if (accounttype == null) {
			if (other.accounttype != null)
				return false;
		} else if (!accounttype.equals(other.accounttype))
			return false;
		if (accouttypecode == null) {
			if (other.accouttypecode != null)
				return false;
		} else if (!accouttypecode.equals(other.accouttypecode))
			return false;
		if (accttypeId == null) {
			if (other.accttypeId != null)
				return false;
		} else if (!accttypeId.equals(other.accttypeId))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccountType [orgCode=" + orgCode + ", copyCode=" + copyCode
				+ ", accouttypecode=" + accouttypecode + ", accounttype="
				+ accounttype + ", accttypeId=" + accttypeId + "]";
	}

	
}
