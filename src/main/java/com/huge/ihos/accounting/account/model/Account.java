package com.huge.ihos.accounting.account.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "GL_account")
public class Account extends BaseObject implements Serializable {

	//private OrgCopyAccCodePk orgCopyAccCodePk;
	private String orgCode;
	private String copyCode;
	private String kjYear;
	private String acctCode;
	private String acctname;
	private String acctFullname;
	private String cnCode;
	private AccountType accttype;
	private String mxtypecode;
	private Boolean leaf = false;
	private String direction;
	private String acctNature;
	private String AssistTypes;
	private String build_id;
	private String build_date;
	private String modi_id;
	private String modi_date;
	private Boolean isCash = false;
	private String LossDirection;
	private Boolean IsProfitLoss = false;
	private Boolean isUsed = false;
	private Boolean disabled = false;
	//private Balance balance;
	

	private String acctId;
	
	
	/*@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "orgId",
		column = @Column(name="OrgId")),
		@AttributeOverride(name = "copyCode",
		column = @Column(name="CopyCode")),
		@AttributeOverride(name = "accoutCode",
		column = @Column(name="acctcode")),
		@AttributeOverride(name = "kjYear",
		column = @Column(name="kjYear"))
		})
	public OrgCopyAccCodePk getOrgCopyAccCodePk() {
		return orgCopyAccCodePk;
	}

	public void setOrgCopyAccCodePk(OrgCopyAccCodePk orgCopyAccCodePk) {
		this.orgCopyAccCodePk = orgCopyAccCodePk;
	}*/

	@Id
	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	/*@Transient
	public String getId() {
		return this.orgCopyAccCodePk.getOrgId()+"PKFENGE"+this.orgCopyAccCodePk.getCopyCode()+"PKFENGE"+this.orgCopyAccCodePk.getKjYear()+"PKFENGE"+this.orgCopyAccCodePk.getAccoutCode().trim();
	}

	public void setId(String id) {
		this.id = id;
	}*/
	
	@Column(name="orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="copycode")
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	
	@Column(name="kjYear")
	public String getKjYear() {
		return kjYear;
	}

	public void setKjYear(String kjYear) {
		this.kjYear = kjYear;
	}
	
	@Column(name="acctcode")
	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	
	@Column(name="acctname")
	public String getAcctname() {
		return acctname;
	}

	public void setAcctname(String acctname) {
		this.acctname = acctname;
	}
	
	@Column(name="acctFullname")
	public String getAcctFullname() {
		return acctFullname;
	}

	public void setAcctFullname(String acctFullname) {
		this.acctFullname = acctFullname;
	}
	
	@Column(name="cnCode")
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="accttypeId")
	public AccountType getAccttype() {
		return accttype;
	}

	public void setAccttype(AccountType accttype) {
		this.accttype = accttype;
	}
	

	/*@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.REMOVE, mappedBy = "account")
	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}*/

	@Column(name="mxtypeId")
	public String getMxtypecode() {
		return mxtypecode;
	}

	public void setMxtypecode(String mxtypecode) {
		this.mxtypecode = mxtypecode;
	}
	@Column(name="leaf")
	public Boolean isLeaf(){
		return leaf;
	}
	
//原来是isLeaf
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name="direction")
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name="acctNature")
	public String getAcctNature() {
		return acctNature;
	}

	public void setAcctNature(String acctNature) {
		this.acctNature = acctNature;
	}

	@Column(name="AssistTypes")
	public String getAssistTypes() {
		return AssistTypes;
	}

	public void setAssistTypes(String assistTypes) {
		AssistTypes = assistTypes;
	}

	@Column(name="build_id")
	public String getBuild_id() {
		return build_id;
	}

	public void setBuild_id(String build_id) {
		this.build_id = build_id;
	}

	@Column(name="build_date")
	public String getBuild_date() {
		return build_date;
	}

	public void setBuild_date(String build_date) {
		this.build_date = build_date;
	}

	@Column(name="modi_id")
	public String getModi_id() {
		return modi_id;
	}

	public void setModi_id(String modi_id) {
		this.modi_id = modi_id;
	}

	@Column(name="modi_date")
	public String getModi_date() {
		return modi_date;
	}

	public void setModi_date(String modi_date) {
		this.modi_date = modi_date;
	}

	@Column(name="isCash")
	public Boolean isCash() {
		return isCash;
	}

	public void setCash(Boolean isCash) {
		this.isCash = isCash;
	}

	@Column(name="LossDirection")
	public String getLossDirection() {
		return LossDirection;
	}

	public void setLossDirection(String lossDirection) {
		LossDirection = lossDirection;
	}

	@Column(name="IsProfitLoss")
	public Boolean isIsProfitLoss() {
		return IsProfitLoss;
	}

	public void setIsProfitLoss(Boolean isProfitLoss) {
		IsProfitLoss = isProfitLoss;
	}

	@Column(name="isUsed")
	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	@Column(name="disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((AssistTypes == null) ? 0 : AssistTypes.hashCode());
		result = prime * result
				+ ((IsProfitLoss == null) ? 0 : IsProfitLoss.hashCode());
		result = prime * result
				+ ((LossDirection == null) ? 0 : LossDirection.hashCode());
		result = prime * result
				+ ((acctCode == null) ? 0 : acctCode.hashCode());
		result = prime * result
				+ ((acctFullname == null) ? 0 : acctFullname.hashCode());
		result = prime * result + ((acctId == null) ? 0 : acctId.hashCode());
		result = prime * result
				+ ((acctNature == null) ? 0 : acctNature.hashCode());
		result = prime * result
				+ ((acctname == null) ? 0 : acctname.hashCode());
		result = prime * result
				+ ((accttype == null) ? 0 : accttype.hashCode());
		result = prime * result
				+ ((build_date == null) ? 0 : build_date.hashCode());
		result = prime * result
				+ ((build_id == null) ? 0 : build_id.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((isCash == null) ? 0 : isCash.hashCode());
		result = prime * result + ((isUsed == null) ? 0 : isUsed.hashCode());
		result = prime * result + ((kjYear == null) ? 0 : kjYear.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result
				+ ((modi_date == null) ? 0 : modi_date.hashCode());
		result = prime * result + ((modi_id == null) ? 0 : modi_id.hashCode());
		result = prime * result
				+ ((mxtypecode == null) ? 0 : mxtypecode.hashCode());
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
		Account other = (Account) obj;
		if (AssistTypes == null) {
			if (other.AssistTypes != null)
				return false;
		} else if (!AssistTypes.equals(other.AssistTypes))
			return false;
		if (IsProfitLoss == null) {
			if (other.IsProfitLoss != null)
				return false;
		} else if (!IsProfitLoss.equals(other.IsProfitLoss))
			return false;
		if (LossDirection == null) {
			if (other.LossDirection != null)
				return false;
		} else if (!LossDirection.equals(other.LossDirection))
			return false;
		if (acctCode == null) {
			if (other.acctCode != null)
				return false;
		} else if (!acctCode.equals(other.acctCode))
			return false;
		if (acctFullname == null) {
			if (other.acctFullname != null)
				return false;
		} else if (!acctFullname.equals(other.acctFullname))
			return false;
		if (acctId == null) {
			if (other.acctId != null)
				return false;
		} else if (!acctId.equals(other.acctId))
			return false;
		if (acctNature == null) {
			if (other.acctNature != null)
				return false;
		} else if (!acctNature.equals(other.acctNature))
			return false;
		if (acctname == null) {
			if (other.acctname != null)
				return false;
		} else if (!acctname.equals(other.acctname))
			return false;
		if (accttype == null) {
			if (other.accttype != null)
				return false;
		} else if (!accttype.equals(other.accttype))
			return false;
		if (build_date == null) {
			if (other.build_date != null)
				return false;
		} else if (!build_date.equals(other.build_date))
			return false;
		if (build_id == null) {
			if (other.build_id != null)
				return false;
		} else if (!build_id.equals(other.build_id))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (isCash == null) {
			if (other.isCash != null)
				return false;
		} else if (!isCash.equals(other.isCash))
			return false;
		if (isUsed == null) {
			if (other.isUsed != null)
				return false;
		} else if (!isUsed.equals(other.isUsed))
			return false;
		if (kjYear == null) {
			if (other.kjYear != null)
				return false;
		} else if (!kjYear.equals(other.kjYear))
			return false;
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
			return false;
		if (modi_date == null) {
			if (other.modi_date != null)
				return false;
		} else if (!modi_date.equals(other.modi_date))
			return false;
		if (modi_id == null) {
			if (other.modi_id != null)
				return false;
		} else if (!modi_id.equals(other.modi_id))
			return false;
		if (mxtypecode == null) {
			if (other.mxtypecode != null)
				return false;
		} else if (!mxtypecode.equals(other.mxtypecode))
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
		return "Account [orgCode=" + orgCode + ", copyCode=" + copyCode
				+ ", kjYear=" + kjYear + ", acctCode=" + acctCode
				+ ", acctname=" + acctname + ", acctFullname=" + acctFullname
				+ ", cnCode=" + cnCode + ", accttype=" + accttype
				+ ", mxtypecode=" + mxtypecode + ", leaf=" + leaf
				+ ", direction=" + direction + ", acctNature=" + acctNature
				+ ", AssistTypes=" + AssistTypes + ", build_id=" + build_id
				+ ", build_date=" + build_date + ", modi_id=" + modi_id
				+ ", modi_date=" + modi_date + ", isCash=" + isCash
				+ ", LossDirection=" + LossDirection + ", IsProfitLoss="
				+ IsProfitLoss + ", isUsed=" + isUsed + ", disabled="
				+ disabled + ", acctId=" + acctId + "]";
	}

	

	
	
}
