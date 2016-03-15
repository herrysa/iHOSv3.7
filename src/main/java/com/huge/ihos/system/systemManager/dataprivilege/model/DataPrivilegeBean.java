package com.huge.ihos.system.systemManager.dataprivilege.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zzh
 * 存储数据权限的bean，包括配置的dataprivilege和menu里的dataprivilege
 */
public class DataPrivilegeBean implements Serializable{

	/**
	 * 存放菜单数据权限
	 */
	private List<MenuDataPrivilege> tableList;

	/**
	 * 存放数据权限类型数据，map以classcode为key
	 */
	private Map<String,PrivilegeClass> joinTableMap;
	
	/**
	 * 存放数据权限值，map以classcode为key
	 */
	private Map<String, String> valueMap;
	
	public List<MenuDataPrivilege> getTableList() {
		return tableList;
	}
	
	public void setTableList(List<MenuDataPrivilege> tableList) {
		this.tableList = tableList;
	}

	public Map<String, PrivilegeClass> getJoinTableMap() {
		return joinTableMap;
	}

	public void setJoinTableMap(Map<String, PrivilegeClass> joinTableMap) {
		this.joinTableMap = joinTableMap;
	}

	public Map<String, String> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}



	public class MenuDataPrivilege{
		private String table;
		private String column;
		private String classCode;
		
		public String getClassCode() {
			return classCode;
		}
		public void setClassCode(String classCode) {
			this.classCode = classCode;
		}
		public String getTable() {
			return table;
		}
		public void setTable(String table) {
			this.table = table;
		}
		public String getColumn() {
			return column;
		}
		public void setColumn(String column) {
			this.column = column;
		}
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((joinTableMap == null) ? 0 : joinTableMap.hashCode());
		result = prime * result
				+ ((tableList == null) ? 0 : tableList.hashCode());
		result = prime * result
				+ ((valueMap == null) ? 0 : valueMap.hashCode());
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
		DataPrivilegeBean other = (DataPrivilegeBean) obj;
		if (joinTableMap == null) {
			if (other.joinTableMap != null)
				return false;
		} else if (!joinTableMap.equals(other.joinTableMap))
			return false;
		if (tableList == null) {
			if (other.tableList != null)
				return false;
		} else if (!tableList.equals(other.tableList))
			return false;
		if (valueMap == null) {
			if (other.valueMap != null)
				return false;
		} else if (!valueMap.equals(other.valueMap))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataPrivilegeBean [tableList=" + tableList + ", joinTableMap="
				+ joinTableMap + ", valueMap=" + valueMap + "]";
	}

	
}
