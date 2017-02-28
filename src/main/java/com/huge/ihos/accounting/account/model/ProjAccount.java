package com.huge.ihos.accounting.account.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.configuration.proj.model.Proj;
import com.huge.model.BaseObject;

@Entity
@Table(name="GL_project_account")
public class ProjAccount extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projAcctId;
	private Account account;
	private Proj proj;
	
	@Id
	@Column(length = 32,name="periodId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getProjAcctId() {
		return projAcctId;
	}
	public void setProjAcctId(String projAcctId) {
		this.projAcctId = projAcctId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="acctId")
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="projId")
	public Proj getProj() {
		return proj;
	}
	public void setProj(Proj proj) {
		this.proj = proj;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((proj == null) ? 0 : proj.hashCode());
		result = prime * result
				+ ((projAcctId == null) ? 0 : projAcctId.hashCode());
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
		ProjAccount other = (ProjAccount) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (proj == null) {
			if (other.proj != null)
				return false;
		} else if (!proj.equals(other.proj))
			return false;
		if (projAcctId == null) {
			if (other.projAcctId != null)
				return false;
		} else if (!projAcctId.equals(other.projAcctId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProjAccount [projAcctId=" + projAcctId + ", account=" + account
				+ ", proj=" + proj + "]";
	}
	
	
	
	
}
