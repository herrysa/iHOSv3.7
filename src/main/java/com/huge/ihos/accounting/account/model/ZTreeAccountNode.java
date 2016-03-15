package com.huge.ihos.accounting.account.model;

import com.huge.webapp.ztree.ZTreeSimpleNode;

public class ZTreeAccountNode extends ZTreeSimpleNode{

	private String assistTypes;
	private String acctFullname;
	private String acctId;

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getAssistTypes() {
		return assistTypes;
	}

	public void setAssistTypes(String assistTypes) {
		this.assistTypes = assistTypes;
	}

	public String getAcctFullname() {
		return acctFullname;
	}

	public void setAcctFullname(String acctFullname) {
		this.acctFullname = acctFullname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((acctFullname == null) ? 0 : acctFullname.hashCode());
		result = prime * result + ((acctId == null) ? 0 : acctId.hashCode());
		result = prime * result
				+ ((assistTypes == null) ? 0 : assistTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZTreeAccountNode other = (ZTreeAccountNode) obj;
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
		if (assistTypes == null) {
			if (other.assistTypes != null)
				return false;
		} else if (!assistTypes.equals(other.assistTypes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ZTreeAccountNode [assistTypes=" + assistTypes
				+ ", acctFullname=" + acctFullname + ", acctId=" + acctId + "]";
	}

	
	
	
}
