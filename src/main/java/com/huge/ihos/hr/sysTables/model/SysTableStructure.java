package com.huge.ihos.hr.sysTables.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "sys_hr_table_structure")
public class SysTableStructure extends BaseObject implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -4945992938623226656L;
	
	private String id;//主键ID
	
	private FieldInfo fieldInfo;//字段表

	private SysTableContent tableContent;				// 表名
		
	private String name;						// 字段名
	
	private Integer sn;           //字段序号
						        
    private Boolean disabled = false;           //是否停用
        
    private String remark;//备注
    
    private Boolean sumFlag = false;//汇总标记
    
    private Person changeUser;//修改人
    
    private Date changeDate;//修改时间
    
    private Integer orderBySn;//排序
    
    private String ascDesc;//升降序
    
    private Integer groupSn;//分组
        
    private Boolean batchEdit = false;//是否批量修改
    
    private Integer gridShowLength;//jqgrid展示长度
    
    private Boolean isView = false;//视图
        
    
	
	@Id
	@Column(name = "structureId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contentId",nullable = false)
	public SysTableContent getTableContent() {
		return tableContent;
	}

	public void setTableContent(SysTableContent tableContent) {
		this.tableContent = tableContent;
	}
	
	@Column(name = "name", length = 50, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	@Column(name = "sn", nullable = true)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
	
	@Column(name = "disabled", nullable = true,columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "sum_flag", nullable = true,columnDefinition = "bit default 0")
	public Boolean getSumFlag() {
		return sumFlag;
	}

	public void setSumFlag(Boolean sumFlag) {
		this.sumFlag = sumFlag;
	}
	
	@Column(name = "batchEdit", nullable = true,columnDefinition = "bit default 0")
	public Boolean getBatchEdit() {
		return batchEdit;
	}

	public void setBatchEdit(Boolean batchEdit) {
		this.batchEdit = batchEdit;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId",nullable = true)
	public Person getChangeUser() {
		return changeUser;
	}

	public void setChangeUser(Person changeUser) {
		this.changeUser = changeUser;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "changeDate", length = 19, nullable = true)
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	@Column(name = "orderby_sn", nullable = true)
	public Integer getOrderBySn() {
		return orderBySn;
	}

	public void setOrderBySn(Integer orderBySn) {
		this.orderBySn = orderBySn;
	}
	@Column(name = "asc_desc", length = 10, nullable = true)
	public String getAscDesc() {
		return ascDesc;
	}

	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}
	@Column(name = "group_sn", nullable = true)
	public Integer getGroupSn() {
		return groupSn;
	}

	public void setGroupSn(Integer groupSn) {
		this.groupSn = groupSn;
	}
	@Column(name = "gridShowLength", nullable = true)
	public Integer getGridShowLength() {
		return gridShowLength;
	}

	public void setGridShowLength(Integer gridShowLength) {
		this.gridShowLength = gridShowLength;
	}	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fieldInfo",nullable = true)
	public FieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(FieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	
	@Column(name = "isView", nullable = true,columnDefinition = "bit default 0")
	public Boolean getIsView() {
		return isView;
	}

	public void setIsView(Boolean isView) {
		this.isView = isView;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tableContent == null) ? 0 : tableContent.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());		
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((sumFlag == null) ? 0 : sumFlag.hashCode());
		result = prime * result + ((changeUser == null) ? 0 : changeUser.hashCode());
		result = prime * result + ((changeDate == null) ? 0 : changeDate.hashCode());
		result = prime * result + ((orderBySn == null) ? 0 : orderBySn.hashCode());
		result = prime * result + ((ascDesc == null) ? 0 : ascDesc.hashCode());
		result = prime * result + ((groupSn == null) ? 0 : groupSn.hashCode());
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
		SysTableStructure other = (SysTableStructure) obj;
		if (tableContent == null) {
			if (other.tableContent != null)
				return false;
		} else if (!tableContent.equals(other.tableContent))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (changeUser == null) {
			if (other.changeUser != null)
				return false;
		} else if (!changeUser.equals(other.changeUser))
			return false;
		if (changeDate == null) {
			if (other.changeDate != null)
				return false;
		} else if (!changeDate.equals(other.changeDate))
			return false;
		if (orderBySn == null) {
			if (other.orderBySn != null)
				return false;
		} else if (!orderBySn.equals(other.orderBySn))
			return false;
		if (ascDesc == null) {
			if (other.ascDesc != null)
				return false;
		} else if (!ascDesc.equals(other.ascDesc))
			return false;
		if (groupSn == null) {
			if (other.groupSn != null)
				return false;
		} else if (!groupSn.equals(other.groupSn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SysTableStructure [tableContent=" +tableContent +  ", name=" + name + ", sn=" + sn + 
				", remark=" + remark +", sumFlag=" + sumFlag +", changeUser=" + changeUser +
				", changeDate=" + changeDate +", orderBySn=" + orderBySn +", ascDesc=" + ascDesc +", groupSn=" + groupSn +"]";
	}
}
