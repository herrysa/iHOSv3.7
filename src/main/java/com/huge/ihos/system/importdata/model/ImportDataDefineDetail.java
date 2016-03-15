package com.huge.ihos.system.importdata.model;

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
@Table(name = "sy_importdata_define_detail")
public class ImportDataDefineDetail extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3190859379029881592L;
	//主键
	private String detailId;
	//名称
	private String detailName;
	//数据类型
	private Integer detailType; //0:数字型;1:字符型;2:日期型;3:整型
	//是否为主键约束（查询语句中的约束条件）
	private Boolean isConstraint;
	//实体名称
	private String entityName;
	//是否更新
	private Boolean isUpdate;
	//排序保存
	private Integer sn; 

	private ImportDataDefine importDataDefine;

	@Id
	@Column(name = "detail_id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "detail_name", length = 50)
	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	@Column(name = "data_type", length = 2)
	public Integer getDetailType() {
		return detailType;
	}

	public void setDetailType(Integer detailType) {
		this.detailType = detailType;
	}

	@Column(name = "isconstraint")
	public Boolean getIsConstraint() {
		return isConstraint;
	}

	public void setIsConstraint(Boolean isConstraint) {
		this.isConstraint = isConstraint;
	}

	@Column(name = "detail_entityname",length=50)
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name="isupdate")
	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Column(name="sn",length=5,nullable=false)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detail_interface_id", nullable = true)
	public ImportDataDefine getImportDataDefine() {
		return importDataDefine;
	}

	public void setImportDataDefine(ImportDataDefine importDataDefine) {
		this.importDataDefine = importDataDefine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detailId == null) ? 0 : detailId.hashCode());
		result = prime * result + ((detailName == null) ? 0 : detailName.hashCode());
		result = prime * result + ((detailType == null) ? 0 : detailType.hashCode());
		result = prime * result + ((entityName == null) ? 0 : entityName.hashCode());
		result = prime * result + ((importDataDefine == null) ? 0 : importDataDefine.hashCode());
		result = prime * result + ((isConstraint == null) ? 0 : isConstraint.hashCode());
		result = prime * result + ((isUpdate == null) ? 0 : isUpdate.hashCode());
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
		ImportDataDefineDetail other = (ImportDataDefineDetail) obj;
		if (detailId == null) {
			if (other.detailId != null)
				return false;
		} else if (!detailId.equals(other.detailId))
			return false;
		if (detailName == null) {
			if (other.detailName != null)
				return false;
		} else if (!detailName.equals(other.detailName))
			return false;
		if (detailType == null) {
			if (other.detailType != null)
				return false;
		} else if (!detailType.equals(other.detailType))
			return false;
		if (entityName == null) {
			if (other.entityName != null)
				return false;
		} else if (!entityName.equals(other.entityName))
			return false;
		if (importDataDefine == null) {
			if (other.importDataDefine != null)
				return false;
		} else if (!importDataDefine.equals(other.importDataDefine))
			return false;
		if (isConstraint == null) {
			if (other.isConstraint != null)
				return false;
		} else if (!isConstraint.equals(other.isConstraint))
			return false;
		if (isUpdate == null) {
			if (other.isUpdate != null)
				return false;
		} else if (!isUpdate.equals(other.isUpdate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImportDataDefineDetail [detailId=" + detailId + ", detailName=" + detailName + ", detailType=" + detailType + ", isConstraint=" + isConstraint + ", isUpdate=" + isUpdate + ", entityName=" + entityName + ", importDataDefine=" + importDataDefine + "]";
	}

}
