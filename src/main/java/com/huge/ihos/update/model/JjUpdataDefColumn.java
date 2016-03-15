package com.huge.ihos.update.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.huge.model.BaseObject;

@Entity
@Table( name = "jj_t_updata_defcolumn" )
@FilterDef( name = "disableFilter", parameters = { @ParamDef( name = "disabled", type = "boolean" ) } )
@Filters( { @Filter( name = "disableFilter", condition = "disabled <> :disabled" ) } )

public class JjUpdataDefColumn extends BaseObject implements Serializable {

	private Long columnId;
	private String columnName;
	private String columnType;
	private String title;
	private String type;
	private String formula;
	private boolean disabled;
	private Integer  order;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	
    @Column( name = "columnName")
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
    @Column( name = "columnType")
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	@Column( name = "title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column( name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column( name = "formula")
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	@Column( name = "disabled")
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	@Column( name = "disOrder")
	@OrderBy("order asc")
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((columnType == null) ? 0 : columnType.hashCode());
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + ((columnId == null) ? 0 : columnId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		JjUpdataDefColumn other = (JjUpdataDefColumn) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (columnType == null) {
			if (other.columnType != null)
				return false;
		} else if (!columnType.equals(other.columnType))
			return false;
		if (disabled != other.disabled)
			return false;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		if (columnId == null) {
			if (other.columnId != null)
				return false;
		} else if (!columnId.equals(other.columnId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JjUpdataDefColum [id=" + columnId + ", columnName=" + columnName
				+ ", columnType=" + columnType + ", title=" + title + ", type="
				+ type + ", formula=" + formula + ", disable=" + disabled + "]";
	}
	
	
}