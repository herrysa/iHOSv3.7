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

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "sys_hr_table_content")
public class SysTableContent extends BaseObject implements Serializable{



		
	/**
	 * 
	 */
	private static final long serialVersionUID = 8095065614195010291L;

	private SysTableKind tableKind;				// 表类型Id
	
	private BdInfo bdinfo;				// 表名
	
	private String id;                   //主键ID
		
	private String name;						// 表名字
												
	private Integer sn;					// 表序号
	
	private String remark;                 //表描述
	
	private Boolean sumFlag = false;            //是否汇总标记
	
	private Person changeUser;              //修改人
	
	private Date changeDate;                 //修改时间
	
	private Boolean disabled = false;			//停用
	
	private Boolean isView = false;//视图
	


	
	@Id
	@Column(name = "contentId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bdinfoId",nullable = false)
	public BdInfo getBdinfo() {
		return bdinfo;
	}

	public void setBdinfo(BdInfo bdinfo) {
		this.bdinfo = bdinfo;
	}
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kindId",nullable = false)
	public SysTableKind getTableKind() {
		return tableKind;
	}

	public void setTableKind(SysTableKind tableKind) {
		this.tableKind = tableKind;
	}
	
	@Column(name = "name", length = 50, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tableKind == null) ? 0 : tableKind.hashCode());
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
		SysTableContent other = (SysTableContent) obj;
		if (tableKind == null) {
			if (other.tableKind != null)
				return false;
		} else if (!tableKind.equals(other.tableKind))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SysTableContent [tableKind=" +tableKind  + ", name=" + name + "]";
	}

	@Column(name = "sn", nullable = true)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
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
	@Column(name = "disabled", nullable = true,columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Column(name = "isView", nullable = true,columnDefinition = "bit default 0")
	public Boolean getIsView() {
		return isView;
	}

	public void setIsView(Boolean isView) {
		this.isView = isView;
	}
}
