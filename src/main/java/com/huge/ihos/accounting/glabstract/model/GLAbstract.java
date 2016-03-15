package com.huge.ihos.accounting.glabstract.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;


@Entity
@Table(name = "GL_abstract")
public class GLAbstract extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String abstractId;
	private String orgCode;
	private String copycode;
	private String kjYear;
	private String voucher_abstract;
	private String cnCode;
	private String acctcode;
	private Boolean disabled;

	
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator="uuid")
	public String getAbstractId() {
		return abstractId;
	}

	public void setAbstractId(String abstractId) {
		this.abstractId = abstractId;
	}
	@Column(name="OrgCode")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Column(name="copycode")
	public String getCopycode() {
		return copycode;
	}

	public void setCopycode(String copycode) {
		this.copycode = copycode;
	}
	@Column(name="kjYear")
	public String getKjYear() {
		return kjYear;
	}

	public void setKjYear(String kjYear) {
		this.kjYear = kjYear;
	}
	@Column(name="abstract")
	public String getVoucher_abstract() {
		return voucher_abstract;
	}

	public void setVoucher_abstract(String voucher_abstract) {
		this.voucher_abstract = voucher_abstract;
	}
	@Column(name="cnCode")
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}
	@Column(name="acctcode")
	public String getAcctcode() {
		return acctcode;
	}

	public void setAcctcode(String acctcode) {
		this.acctcode = acctcode;
	}
	
	@Column(name="disabled", nullable=false)
	public Boolean getDisabled() {
		return disabled;
	}
	
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Override
	public String toString() {
		return "Abstract [abstractId=" + abstractId + ", orgCode=" + orgCode
		+ ", kjYear=" + kjYear + ", copycode=" + copycode
		+ ", abstract=" + voucher_abstract + ", cnCode=" + cnCode + "acctcode="+acctcode+"disabled="+disabled+"]";
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GLAbstract other = (GLAbstract) obj;
		if (abstractId == null) {
			if (other.abstractId != null)
				return false;
		} else if (!abstractId.equals(other.abstractId))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (copycode == null) {
			if (other.copycode != null)
				return false;
		} else if (!copycode.equals(other.copycode))
			return false;
		if (kjYear == null) {
			if (other.kjYear != null)
				return false;
		} else if (!kjYear.equals(other.kjYear))
			return false;
		if (voucher_abstract == null) {
			if (other.voucher_abstract != null)
				return false;
		} else if (!voucher_abstract.equals(other.voucher_abstract))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (acctcode == null) {
			if (other.acctcode != null)
				return false;
		} else if (!acctcode.equals(other.acctcode))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abstractId == null) ? 0 : abstractId.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((copycode == null) ? 0 : copycode.hashCode());
		result = prime * result + ((kjYear == null) ? 0 : kjYear.hashCode());
		result = prime * result	+ ((voucher_abstract == null) ? 0 : voucher_abstract.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result + ((acctcode == null) ? 0 : acctcode.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		return result;
	}
}
