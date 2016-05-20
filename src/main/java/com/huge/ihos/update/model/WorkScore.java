package com.huge.ihos.update.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.huge.model.BaseObject;

@Entity
@Table( name = "jj_dict_workScore" )
public class WorkScore extends BaseObject{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8352408260255639399L;
	private String id;
	private String workName;
	private int clevel;
	private WorkScore parentid;
	private int workScore;
	private String workunit;
	private Boolean disabled;
	private Boolean leaf;
	private String remark;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	@Column
	public int getClevel() {
		return clevel;
	}
	public void setClevel(int clevel) {
		this.clevel = clevel;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public WorkScore getParentid() {
		return parentid;
	}
	public void setParentid(WorkScore parentid) {
		this.parentid = parentid;
	}
	
	@Column
	public int getWorkScore() {
		return workScore;
	}
	public void setWorkScore(int workScore) {
		this.workScore = workScore;
	}
	
	@Column
	public String getWorkunit() {
		if(workunit==null){
			workunit = "分";
		}
		return workunit;
	}
	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}
	
	@Column
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clevel;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((workName == null) ? 0 : workName.hashCode());
		result = prime * result + workScore;
		result = prime * result
				+ ((workunit == null) ? 0 : workunit.hashCode());
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
		WorkScore other = (WorkScore) obj;
		if (clevel != other.clevel)
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
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
		if (workName == null) {
			if (other.workName != null)
				return false;
		} else if (!workName.equals(other.workName))
			return false;
		if (workScore != other.workScore)
			return false;
		if (workunit == null) {
			if (other.workunit != null)
				return false;
		} else if (!workunit.equals(other.workunit))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WorkScore [id=" + id + ", workName=" + workName + ", clevel="
				+ clevel + ", workScore="
				+ workScore + ", workunit=" + workunit + ", disabled="
				+ disabled + ", remark=" + remark + "]";
	}
	
	
}
