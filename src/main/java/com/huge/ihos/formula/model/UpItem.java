package com.huge.ihos.formula.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table( name = "T_UpItem" )
public class UpItem extends BaseObject
implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String itemName;
	
	private String remark;
	
	private String itemClass;
	
	private CostItem costItemId;
	
	private Department sbdeptId;
	
	private Department auditDeptId;
	
	private boolean disabled;
	
	private String upItemType ;
	
	private String taskName ;
	
	private Boolean isUpOtherDept = false;//是否上报他科人员
	
	private Person sbPersonId;
	
	//private Org costOrg;
	@Id
    @GeneratedValue
    @Column( name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column( name = "itemName")
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Column( name = "remark", length = 100 )
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column( name = "itemClass",  length = 10 )
	public String getItemClass() {
		return itemClass;
	}
	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}
	
	@ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "costItemId" )
	public CostItem getCostItemId() {
		return costItemId;
	}
	public void setCostItemId(CostItem costItemId) {
		this.costItemId = costItemId;
	}
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "sbdeptId", nullable = true )
	public Department getSbdeptId() {
		return sbdeptId;
	}
	public void setSbdeptId(Department sbdeptId) {
		this.sbdeptId = sbdeptId;
	}
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "auditDeptId", nullable = true )
	public Department getAuditDeptId() {
		return auditDeptId;
	}
	public void setAuditDeptId(Department auditDeptId) {
		this.auditDeptId = auditDeptId;
	}
	
	@Column( name = "disabled")
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column( name = "checkSpName")
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	@Column( name = "isUpOtherDept", nullable = false, columnDefinition = "bit default 0")
	public boolean getIsUpOtherDept() {
		return isUpOtherDept;
	}
	public void setIsUpOtherDept(boolean isUpOtherDept) {
		this.isUpOtherDept = isUpOtherDept;
	}
	@Transient
	public String getUpItemType() {
		if(this.itemClass!=null){
			if(this.itemClass.equals("本科室")){
				upItemType = "1";
			}else{
				upItemType = "0";
			}
		}
		return upItemType;
	}
	public void setUpItemType(String upItemType) {
		this.upItemType = upItemType;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sbPersonId")
	public Person getSbPersonId() {
		return sbPersonId;
	}
	public void setSbPersonId(Person sbPersonId) {
		this.sbPersonId = sbPersonId;
	}
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="costOrgCode")
	public Org getCostOrg() {
		return costOrg;
	}
	public void setCostOrg(Org costOrg) {
		this.costOrg = costOrg;
	}*/
	
	@Override
	public String toString() {
		return "UpItem [id=" + id + ", itemName=" + itemName + ", remark="
				+ remark + ", itemClass=" + itemClass + ", costItemId="
				+ costItemId + ", sbdeptId=" + sbdeptId + ", auditDeptId="
				+ auditDeptId + ", disabled=" + disabled + ",isUpOtherDept="+isUpOtherDept+"]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditDeptId == null) ? 0 : auditDeptId.hashCode());
		result = prime * result
				+ ((costItemId == null) ? 0 : costItemId.hashCode());
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((itemClass == null) ? 0 : itemClass.hashCode());
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((isUpOtherDept == null) ? 0 : isUpOtherDept.hashCode());
		result = prime * result
				+ ((sbdeptId == null) ? 0 : sbdeptId.hashCode());
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
		UpItem other = (UpItem) obj;
		if (auditDeptId == null) {
			if (other.auditDeptId != null)
				return false;
		} else if (!auditDeptId.equals(other.auditDeptId))
			return false;
		if (costItemId == null) {
			if (other.costItemId != null)
				return false;
		} else if (!costItemId.equals(other.costItemId))
			return false;
		if (disabled != other.disabled)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemClass == null) {
			if (other.itemClass != null)
				return false;
		} else if (!itemClass.equals(other.itemClass))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (isUpOtherDept == null) {
			if (other.isUpOtherDept != null)
				return false;
		} else if (!isUpOtherDept.equals(other.isUpOtherDept))
			return false;
		if (sbdeptId == null) {
			if (other.sbdeptId != null)
				return false;
		} else if (!sbdeptId.equals(other.sbdeptId))
			return false;
		return true;
	}
	
}
