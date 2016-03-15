package com.huge.ihos.system.configuration.colsetting.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_colshow" )
public class ColShow extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1085707374698443224L;
	private String id;
	private String col;
	private String label;
	private Integer order;
	private Boolean show;
	private String templetName;
	private String entityName;
	private Long editTime;
	private String userId;
	private String colWidth;
	private String colForId;
	private Boolean templetToDept;
	private Boolean templetToRole;
	private Boolean templetToPublic;
	private String customLayout;
	private String colshowType;//类型


	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="col",length=20)
	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	@Column(name="label",length=50)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name="colOrder")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name="show")
	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
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
	
	@Column(name="colWidth")
	public String getColWidth() {
		return colWidth;
	}

	public void setColWidth(String colWidth) {
		this.colWidth = colWidth;
	}
	
	@Column(name="templetToDept")
	public Boolean getTempletToDept() {
		return templetToDept;
	}

	public void setTempletToDept(Boolean templetToDept) {
		this.templetToDept = templetToDept;
	}

	@Column(name="templetToRole")
	public Boolean getTempletToRole() {
		return templetToRole;
	}

	public void setTempletToRole(Boolean templetToRole) {
		this.templetToRole = templetToRole;
	}

	@Column(name="templetToPublic")
	public Boolean getTempletToPublic() {
		return templetToPublic;
	}

	public void setTempletToPublic(Boolean templetToPublic) {
		this.templetToPublic = templetToPublic;
	}
	
	@Transient
	public String getColForId() {
		if(col!=null&&col.contains(".")){
			colForId = col.replaceAll("\\.", "_");
		}else{
			colForId = col;
		}
		
		return colForId;
	}

	public void setColForId(String colForId) {
		this.colForId = colForId;
	}
	
	@Column(name="custom_layout")
	public String getCustomLayout() {
		return customLayout;
	}

	public void setCustomLayout(String customLayout) {
		this.customLayout = customLayout;
	}
	@Column(name="colshowType",length = 10,nullable = true)
	public String getColshowType() {
		return colshowType;
	}

	public void setColshowType(String colshowType) {
		this.colshowType = colshowType;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((col == null) ? 0 : col.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((show == null) ? 0 : show.hashCode());
		result = prime * result
				+ ((templetName == null) ? 0 : templetName.hashCode());
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
		ColShow other = (ColShow) obj;
		if (col == null) {
			if (other.col != null)
				return false;
		} else if (!col.equals(other.col))
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
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (show == null) {
			if (other.show != null)
				return false;
		} else if (!show.equals(other.show))
			return false;
		if (templetName == null) {
			if (other.templetName != null)
				return false;
		} else if (!templetName.equals(other.templetName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ColShow [id=" + id + ", col=" + col + ", label=" + label
				+ ", order=" + order + ", show=" + show + ", templetName="
				+ templetName + "]";
	}
}
