package com.huge.ihos.bm.bugetSubj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_bugetAcctMap" )
public class BugetAcctMap extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825941499036724067L;
	private String mapId;
	private BugetSubj bmSubjId;
	private String acctId;
	private String acctCode;
	private String acctName;
	private String subDirect;
	private String addDirect;
	private String remark;
	
	@Id
	@Column(name = "mapId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bmSubjId")
	public BugetSubj getBmSubjId() {
		return bmSubjId;
	}
	public void setBmSubjId(BugetSubj bmSubjId) {
		this.bmSubjId = bmSubjId;
	}
	
	@Column
	public String getSubDirect() {
		return subDirect;
	}
	public void setSubDirect(String subDirect) {
		this.subDirect = subDirect;
	}
	
	@Column
	public String getAddDirect() {
		return addDirect;
	}
	public void setAddDirect(String addDirect) {
		this.addDirect = addDirect;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column
	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	
	@Column
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	
	@Column
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acctCode == null) ? 0 : acctCode.hashCode());
		result = prime * result + ((acctId == null) ? 0 : acctId.hashCode());
		result = prime * result
				+ ((acctName == null) ? 0 : acctName.hashCode());
		result = prime * result
				+ ((addDirect == null) ? 0 : addDirect.hashCode());
		result = prime * result + ((mapId == null) ? 0 : mapId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((subDirect == null) ? 0 : subDirect.hashCode());
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
		BugetAcctMap other = (BugetAcctMap) obj;
		if (acctCode == null) {
			if (other.acctCode != null)
				return false;
		} else if (!acctCode.equals(other.acctCode))
			return false;
		if (acctId == null) {
			if (other.acctId != null)
				return false;
		} else if (!acctId.equals(other.acctId))
			return false;
		if (acctName == null) {
			if (other.acctName != null)
				return false;
		} else if (!acctName.equals(other.acctName))
			return false;
		if (addDirect == null) {
			if (other.addDirect != null)
				return false;
		} else if (!addDirect.equals(other.addDirect))
			return false;
		if (mapId == null) {
			if (other.mapId != null)
				return false;
		} else if (!mapId.equals(other.mapId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (subDirect == null) {
			if (other.subDirect != null)
				return false;
		} else if (!subDirect.equals(other.subDirect))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BugetAcctMap [mapId=" + mapId + ", acctId=" + acctId
				+ ", acctCode=" + acctCode + ", acctName=" + acctName
				+ ", subDirect=" + subDirect + ", addDirect=" + addDirect
				+ ", remark=" + remark + "]";
	}
	
	
}
