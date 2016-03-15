package com.huge.ihos.system.configuration.colsetting.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_colsearch" )
public class ColSearch extends BaseObject implements Serializable {

	private String id;
	private String col;
	private String label;
	private String filedType;
	private String operator;
	private String value;
	private String templetName;
	private String entityName;
	private Long editTime;
	private String userId;
	
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="col",length=20,nullable=false)
	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	@Transient
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name="operator",length=10,nullable=false)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name="value",length=20,nullable=false)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name="templetName",length=30,nullable=false)
	public String getTempletName() {
		return templetName;
	}

	public void setTempletName(String templetName) {
		this.templetName = templetName;
	}
	
	@Column(name="entityName")
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	@Transient
	public String getFiledType() {
		return filedType;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}
	
	@Column(name="editTime")
	public Long getEditTime() {
		return editTime;
	}

	public void setEditTime(Long editTime) {
		this.editTime = editTime;
	}
	
	@Column(name="userId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((col == null) ? 0 : col.hashCode());
		result = prime * result
				+ ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result
				+ ((entityName == null) ? 0 : entityName.hashCode());
		result = prime * result
				+ ((filedType == null) ? 0 : filedType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result
				+ ((templetName == null) ? 0 : templetName.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ColSearch other = (ColSearch) obj;
		if (col == null) {
			if (other.col != null)
				return false;
		} else if (!col.equals(other.col))
			return false;
		if (editTime == null) {
			if (other.editTime != null)
				return false;
		} else if (!editTime.equals(other.editTime))
			return false;
		if (entityName == null) {
			if (other.entityName != null)
				return false;
		} else if (!entityName.equals(other.entityName))
			return false;
		if (filedType == null) {
			if (other.filedType != null)
				return false;
		} else if (!filedType.equals(other.filedType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (templetName == null) {
			if (other.templetName != null)
				return false;
		} else if (!templetName.equals(other.templetName))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ColSearch [id=" + id + ", col=" + col + ", label=" + label
				+ ", filedType=" + filedType + ", operator=" + operator
				+ ", value=" + value + ", templetName=" + templetName
				+ ", entityName=" + entityName + ", editTime=" + editTime + "]";
	}

	

}
