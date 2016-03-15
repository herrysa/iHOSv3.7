package com.huge.ihos.kaohe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table( name = "KH_InspectBSCColumn")
public class InspectBSCColumn {

	private Long columnId;
	private String columnName ;
	private boolean status = true;
	private Long disOrder;
	private String width;
	

	private InspectTempl inspectTempl;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	@Column(name="columnName")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	@Column(name="status")
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	

	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="inspectModelId")
	public InspectTempl getInspectTempl() {
		return inspectTempl;
	}

	public void setInspectTempl(InspectTempl inspectTempl) {
		this.inspectTempl = inspectTempl;
	}

	@Column(name="disOrder")
	public Long getDisOrder() {
		return disOrder;
	}

	public void setDisOrder(Long disOrder) {
		this.disOrder = disOrder;
	}
	
	@Column(name="width")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnId == null) ? 0 : columnId.hashCode());
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((inspectTempl == null) ? 0 : inspectTempl.hashCode());
		result = prime * result + (status ? 1231 : 1237);
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
		InspectBSCColumn other = (InspectBSCColumn) obj;
		if (columnId == null) {
			if (other.columnId != null)
				return false;
		} else if (!columnId.equals(other.columnId))
			return false;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (inspectTempl == null) {
			if (other.inspectTempl != null)
				return false;
		} else if (!inspectTempl.equals(other.inspectTempl))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	
}
