package com.huge.ihos.system.reportManager.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_report_define")
public class ReportDefine extends BaseObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1013732417225389432L;

	private String defineId; // 帐表定义id

	private String defineName; // 帐表定义名称

	private String noItems; // 不可用项拼串
	/*
	 * a1:期间 a2:单位 a3:部门 a4:人员类别 a5:人员姓名 a6:人员编码 a7:部门层级 a8:部门明细 a9:类别选择 a10:人员
	 * a11:含未审核 a12:汇总项目 c1:期间范围 b1:部门小计 b2:人员类别小计 b3:隐藏无数据员工 b4:隐藏无效小数 b5:零不显示
	 */
	private String filters; // 可用条件拼串(a1:)

	private String remark; // 帐表定义备注

	private String groups;// 分组

	private String reverseColumn;// 反转列

	private String requiredFilter;// 必填条件

	private String displayType; // 展示形式

	private Boolean configurable = true;// 可配置

	private String requiredItems;// 必选项

	private String subSystemCode;// 子系统代码(KQ,GZ,MM,HR)

	private String subSystem;// 子系统

	private String curType;// 当前类别（定义后类别下拉框只读）

	private String tableName;// 表名

	private String tableTypeField;// 表中类别字段

	private String attachField;// 附加字段

	private String attachFieldName;// 附加字段名称

	private String unCheckStatus;// 未审核状态

	private String groupSelectItems;// 可选的分组项

	private String groupIds;// 分组字段(固定分组字段)

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

	@Column(name = "noItems", length = 1000)
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

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "groups", length = 200)
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

	@Column(name = "subSystem", nullable = true, length = 50)
	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	@Column(name = "subSystemCode", nullable = false, length = 10)
	public String getSubSystemCode() {
		return subSystemCode;
	}

	public void setSubSystemCode(String subSystemCode) {
		this.subSystemCode = subSystemCode;
	}

	@Column(name = "curType", nullable = true, length = 32)
	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	@Column(name = "tableName", nullable = false, length = 50)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "attachField", nullable = true, length = 200)
	public String getAttachField() {
		return attachField;
	}

	public void setAttachField(String attachField) {
		this.attachField = attachField;
	}

	@Column(name = "attachFieldName", nullable = true, length = 200)
	public String getAttachFieldName() {
		return attachFieldName;
	}

	public void setAttachFieldName(String attachFieldName) {
		this.attachFieldName = attachFieldName;
	}

	@Column(name = "tableTypeField", nullable = false, length = 50)
	public String getTableTypeField() {
		return tableTypeField;
	}

	public void setTableTypeField(String tableTypeField) {
		this.tableTypeField = tableTypeField;
	}

	@Column(name = "unCheckStatus", nullable = true, length = 10)
	public String getUnCheckStatus() {
		return unCheckStatus;
	}

	public void setUnCheckStatus(String unCheckStatus) {
		this.unCheckStatus = unCheckStatus;
	}

	@Column(name = "groupSelectItems", nullable = true, length = 1000)
	public String getGroupSelectItems() {
		return groupSelectItems;
	}

	public void setGroupSelectItems(String groupSelectItems) {
		this.groupSelectItems = groupSelectItems;
	}

	@Column(name = "groupIds", nullable = true, length = 100)
	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
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
		ReportDefine other = (ReportDefine) obj;
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
		return "ReportDefine [defineId=" + defineId + ", defineName="
				+ defineName + ", noItems=" + noItems + ", filters=" + filters
				+ ", remark=" + remark + "]";
	}
	@Override
	public ReportDefine clone() {
		ReportDefine o = null;
		try {
			o = (ReportDefine) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
