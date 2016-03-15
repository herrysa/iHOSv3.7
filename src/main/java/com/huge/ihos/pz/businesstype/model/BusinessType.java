package com.huge.ihos.pz.businesstype.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "pz_businessType")
public class BusinessType extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String businessId;
	private String businessName;
	private BusinessType parentType;
	private String searchName;
	private String icon;
	private Boolean leaf;
	private Boolean disabled;
	private Integer sn;
	private String remark;
	private String dataSourceType; //数据源类型(0:本地,1:中间库)
	private String contrastTable; //对照表
	private String collectTempTable; //汇总临时表

	private Set<BusinessTypeJ> businessTypeJs;
	private Set<BusinessTypeD> businessTypeDs;
	private Set<BusinessTypeParam> businessTypeParams;
	@Id
	@Column(name = "businessId", length = 30, nullable = false)
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	@Column(name = "businessName", length = 30, nullable = false)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId", nullable = true)
	public BusinessType getParentType() {
		return parentType;
	}

	public void setParentType(BusinessType parentType) {
		this.parentType = parentType;
	}

	@Column(name = "searchName", length = 100, nullable = true)
	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	@Column(name = "icon", length = 255, nullable = true)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "leaf", nullable = false)
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "disabled", nullable = false)
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "sn", nullable = false)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	@Column(name = "remark", length = 150, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="dataSourceType",length=1)
	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}
	@Column(name="contrastTable",length=50)
	public String getContrastTable() {
		return contrastTable;
	}

	public void setContrastTable(String contrastTable) {
		this.contrastTable = contrastTable;
	}

	@OneToMany(mappedBy="businessType")
	@OrderBy("sn asc")
	public Set<BusinessTypeJ> getBusinessTypeJs() {
		return businessTypeJs;
	}

	public void setBusinessTypeJs(Set<BusinessTypeJ> businessTypeJs) {
		this.businessTypeJs = businessTypeJs;
	}
	@OneToMany(mappedBy="businessType")
	@OrderBy("sn asc")
	public Set<BusinessTypeD> getBusinessTypeDs() {
		return businessTypeDs;
	}

	public void setBusinessTypeDs(Set<BusinessTypeD> businessTypeDs) {
		this.businessTypeDs = businessTypeDs;
	}

	@OneToMany(mappedBy="businessType")
	@OrderBy("sn asc")
	public Set<BusinessTypeParam> getBusinessTypeParams() {
		return businessTypeParams;
	}

	public void setBusinessTypeParams(Set<BusinessTypeParam> businessTypeParams) {
		this.businessTypeParams = businessTypeParams;
	}

	
	@Column(name="collectTempTable",nullable=true,length=50)
	public String getCollectTempTable() {
		return collectTempTable;
	}

	public void setCollectTempTable(String collectTempTable) {
		this.collectTempTable = collectTempTable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessId == null) ? 0 : businessId.hashCode());
		result = prime * result + ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((parentType == null) ? 0 : parentType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((searchName == null) ? 0 : searchName.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
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
		BusinessType other = (BusinessType) obj;
		if (businessId == null) {
			if (other.businessId != null)
				return false;
		} else if (!businessId.equals(other.businessId))
			return false;
		if (businessName == null) {
			if (other.businessName != null)
				return false;
		} else if (!businessName.equals(other.businessName))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
			return false;
		if (parentType == null) {
			if (other.parentType != null)
				return false;
		} else if (!parentType.equals(other.parentType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (searchName == null) {
			if (other.searchName != null)
				return false;
		} else if (!searchName.equals(other.searchName))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BusinessType [businessId=" + businessId + ", businessName=" + businessName + ", parentType=" + parentType + ", searchName=" + searchName + ", icon=" + icon + ", leaf=" + leaf + ", disabled=" + disabled + ", sn=" + sn + ", remark=" + remark + "]";
	}

}
