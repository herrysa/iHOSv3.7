package com.huge.ihos.gz.gzAccount.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "gz_account_define")
public class GzAccountDefine extends BaseObject {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5848733103486885120L;

	private String defineId ;  // 帐表定义id
	
	private String defineName ;  //帐表定义名称
	
	private String noItems ;   //不可用工资项拼串
	
	private String filters ;     //可用条件拼串
	
	private String remark;       //帐表定义备注
	
	private String groups;//分组
	
	private String reverseColumn;//反转列
	
	private String requiredFilter;//必填条件
	
	private String displayType; //展示形式
	
	private Boolean configurable = true;//可配置
	
	private String requiredItems;//必选项
	
	@Id
	@Column(name = "defineId", length = 20)
	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}
	@Column(name = "defineName", length = 100)
	public String getDefineName() {
		return defineName;
	}

	public void setDefineName(String defineName) {
		this.defineName = defineName;
	}
	@Column(name = "noItems", length = 200)
	public String getNoItems() {
		return noItems;
	}

	public void setNoItems(String noItems) {
		this.noItems = noItems;
	}
	@Column(name = "filters", length = 200)
	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}
	@Column(name = "remark", length = 50)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "groups", length = 100)
	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}
	
	@Column(name = "reverseColumn", length = 100)
	public String getReverseColumn() {
		return reverseColumn;
	}

	public void setReverseColumn(String reverseColumn) {
		this.reverseColumn = reverseColumn;
	}
	@Column(name = "requiredFilter", length = 100)
	public String getRequiredFilter() {
		return requiredFilter;
	}

	public void setRequiredFilter(String requiredFilter) {
		this.requiredFilter = requiredFilter;
	}
	@Column(name = "displayType", length = 20)
	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	
	@Column(name = "configurable")
	public Boolean getConfigurable() {
		return configurable;
	}

	public void setConfigurable(Boolean configurable) {
		this.configurable = configurable;
	}
	@Column(name = "requiredItems", length = 2000)
	public String getRequiredItems() {
		return requiredItems;
	}

	public void setRequiredItems(String requiredItems) {
		this.requiredItems = requiredItems;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((defineId == null) ? 0 : defineId.hashCode());
		result = prime * result
				+ ((defineName == null) ? 0 : defineName.hashCode());
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
		result = prime * result + ((noItems == null) ? 0 : noItems.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		GzAccountDefine other = (GzAccountDefine) obj;
		if (defineId == null) {
			if (other.defineId != null)
				return false;
		} else if (!defineId.equals(other.defineId))
			return false;
		if (defineName == null) {
			if (other.defineName != null)
				return false;
		} else if (!defineName.equals(other.defineName))
			return false;
		if (filters == null) {
			if (other.filters != null)
				return false;
		} else if (!filters.equals(other.filters))
			return false;
		if (noItems == null) {
			if (other.noItems != null)
				return false;
		} else if (!noItems.equals(other.noItems))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GzAccountDefine [defineId=" + defineId + ", defineName="
				+ defineName + ", noItems=" + noItems + ", filters=" + filters
				+ ", remark=" + remark + "]";
	}
}
