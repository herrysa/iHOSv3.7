package com.huge.ihos.pz.businesstype.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.model.BaseObject;
@Entity
@Table(name="pz_businessType_D")
public class BusinessTypeD extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;					//ID
	private BusinessType businessType;	//外键，关联业务类型
	private String colName;				//显示名称
	private String dataSourceType;		//0:本地,1:中间库,2:财务库
	private String sourceTable;			//表名
	private String fieldName;			//字段名
	private Boolean rowToCol;			//是否行变列
	private Boolean accCol;				//是否财务凭证列
	private Boolean disabled;			//停用
	private Integer sn;					//序号
	private String remark;				//备注
	
	private String sourceId;
	private String sourceName;
	
	@Id
	@Column(name="dId",length=30,nullable=false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="businessId",nullable=false,insertable=true,updatable=false)
	public BusinessType getBusinessType() {
		return businessType;
	}
	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}
	@Column(name="colName",length=50,nullable=false)
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	@Column(name="dataSourceType",length=1,nullable=false)
	public String getDataSourceType() {
		return dataSourceType;
	}
	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}
	@Column(name="sourceTable",length=50,nullable=false)
	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	@Column(name="fieldName",length=30,nullable=false)
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	@Column(name="rowToCol",nullable=false)
	public Boolean getRowToCol() {
		return rowToCol;
	}
	public void setRowToCol(Boolean rowToCol) {
		this.rowToCol = rowToCol;
	}
	@Column(name="accCol",nullable=false)
	public Boolean getAccCol() {
		return accCol;
	}
	public void setAccCol(Boolean accCol) {
		this.accCol = accCol;
	}
	@Column(name="disabled",nullable=false)
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Column(name="sn",nullable=false)
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	@Column(name="remark",length=150,nullable=true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="sourceId",length=1000,nullable=true)
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	@Column(name="sourceName",length=1000,nullable=true)
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accCol == null) ? 0 : accCol.hashCode());
		result = prime * result + ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result + ((colName == null) ? 0 : colName.hashCode());
		result = prime * result + ((dataSourceType == null) ? 0 : dataSourceType.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((rowToCol == null) ? 0 : rowToCol.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((sourceTable == null) ? 0 : sourceTable.hashCode());
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
		BusinessTypeD other = (BusinessTypeD) obj;
		if (accCol == null) {
			if (other.accCol != null)
				return false;
		} else if (!accCol.equals(other.accCol))
			return false;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		if (colName == null) {
			if (other.colName != null)
				return false;
		} else if (!colName.equals(other.colName))
			return false;
		if (dataSourceType == null) {
			if (other.dataSourceType != null)
				return false;
		} else if (!dataSourceType.equals(other.dataSourceType))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (rowToCol == null) {
			if (other.rowToCol != null)
				return false;
		} else if (!rowToCol.equals(other.rowToCol))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (sourceTable == null) {
			if (other.sourceTable != null)
				return false;
		} else if (!sourceTable.equals(other.sourceTable))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BusinessTypeJ [id=" + id + ", businessType=" + businessType + ", colName=" + colName + ", dataSourceType=" + dataSourceType + ", sourceTable=" + sourceTable + ", fieldName=" + fieldName + ", rowToCol=" + rowToCol + ", accCol=" + accCol + ", disabled=" + disabled + ", sn=" + sn + ", remark=" + remark + "]";
	}
	
}
