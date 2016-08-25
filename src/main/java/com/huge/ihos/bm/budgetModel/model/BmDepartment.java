package com.huge.ihos.bm.budgetModel.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.model.BaseObject;

@Entity
@Table( name = "v_bm_department" )
public class BmDepartment
    extends BaseObject
    implements Serializable,Comparable<BmDepartment> {
    /**
     * 
     */
    private static final long serialVersionUID = -4949654284990681948L;

    private String departmentId; // ID

    // String
    private String name; // 科室名称

    // req
    private String deptCode; // 科室编码

    // req
    private String shortnName; // 科室简称

    private String internalCode; // 科室内部码

    private String outin; // 住院门诊

    private String deptClass; // 科室类别

    // rename
    // to
    // dept
    // type
    // req
    private String subClass; // 专业系别

    private String phone; // 电话

    private String contact; // 联系人

    private String contactPhone; // 联系电话

    private String manager; // 科室主管姓名

    private String note; // 备注

    private Boolean leaf = true; // 是否为叶子 

    private Integer clevel = 1; // 层次

	private Boolean disabled = false; // 是否可用

    private Date invalidDate; // 失效日期

    private String cnCode;
    
    private Boolean ysLeaf = false; // 是否为叶子（预算）
    
    //private String orgCode;//单位
    
    //private Org org;
    
    //private String branchCode; //院区
    
    //private String branchName; //院区
    
    /*@Transient
    public String getBranchName() {
    	if(branch!=null){
    		branchName = branch.getBranchName();
    	}else{
    		branchName = "";
    	}
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}*/


	//private Branch branch;
    
    private String dgroup;
    
    private Boolean cbLeaf = false; // 是否为叶子(成本)
    
    private Boolean zcLeaf = false; // 是否为叶子(诊次)
    
    private Boolean crLeaf = false; // 是否为叶子(床日)
    
    private Boolean xmLeaf = false; // 是否为叶子(项目)
    
    private String snapCode="0";
    
    private String ysDeptName;

	private String jjDeptName;
    private String yjDeptName;
    

	@Id
    @Column( name = "deptId",length=50)
    public String getDepartmentId() {
        return departmentId;
    }
    
    @Column( name = "cnCode", length = 50 )
    public String getCnCode() {
        return cnCode;
    }

    public void setCnCode( String cnCode ) {
        this.cnCode = cnCode;
    }


    public void setDepartmentId( String departmentId ) {
        this.departmentId = departmentId;
    }

    @Column( name = "name", nullable = false, length = 50 )
    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Column( name = "deptCode", nullable = false, length = 20 )
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode( String deptCode ) {
        this.deptCode = deptCode;
    }

    @Column( name = "shortnName", nullable = true, length = 30 )
    public String getShortnName() {
        return shortnName;
    }

    public void setShortnName( String shortnName ) {
        this.shortnName = shortnName;
    }

    @Column( name = "internalCode", nullable = true, length = 20 )
    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode( String internalCode ) {
        this.internalCode = internalCode;
    }

    @Column( name = "outin", nullable = true, length = 50 )
    public String getOutin() {
        return outin;
    }

    public void setOutin( String outin ) {
        this.outin = outin;
    }

    @Column( name = "deptType", nullable = false, length = 50 )
    public String getDeptClass() {
        return deptClass;
    }

    public void setDeptClass( String deptClass ) {
        this.deptClass = deptClass;
    }

    @Column( name = "subClass", nullable = true, length = 50 )
    public String getSubClass() {
        return subClass;
    }

    public void setSubClass( String subClass ) {
        this.subClass = subClass;
    }

    @Column( name = "phone", nullable = true, length = 20 )
    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    @Column( name = "contact", nullable = true, length = 20 )
    public String getContact() {
        return contact;
    }

    public void setContact( String contact ) {
        this.contact = contact;
    }

    @Column( name = "contactPhone", nullable = true, length = 20 )
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone( String contactPhone ) {
        this.contactPhone = contactPhone;
    }

    @Column( name = "manager", nullable = true, length = 20 )
    public String getManager() {
        return manager;
    }

    public void setManager( String manager ) {
        this.manager = manager;
    }

    @Column( name = "note", nullable = true, length = 120 )
    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    @Column( name = "leaf", nullable = true )
    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf( Boolean leaf ) {
        this.leaf = leaf;
    }

    @Column( name = "clevel", nullable = true )
    public Integer getClevel() {
        return clevel;
    }

    public void setClevel( Integer clevel ) {
        this.clevel = clevel;
    }

    @Column( name = "disabled", nullable = true )
    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

    public void setInvalidDate( Date invalidDate ) {
        this.invalidDate = invalidDate;
    }

    @Column( name = "invalidDate", nullable = true )
    public Date getInvalidDate() {
        return invalidDate;
    }

    public Boolean getDisabled() {
        return disabled;
    }


    @Column( name = "ysLeaf", nullable = true )
	public Boolean getYsLeaf() {
		return ysLeaf;
	}

	public void setYsLeaf(Boolean ysLeaf) {
		this.ysLeaf = ysLeaf;
	}
	
	/*@Column( name = "orgCode", nullable = true ,length=20)
	public String getOrgCode() {
		return orgCode;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}*/
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgCode", nullable = true, insertable = false, updatable = false)
	public Org getOrg() {
		return org;
	}


	public void setOrg(Org org) {
		this.org = org;
	}*/

	@Column(name = "snapCode", length = 14, nullable = true)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}
	/*@Column(name="branchCode",length=30,nullable=true)
	public String getBranchCode() {
		return branchCode;
	}


	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="branchCode",nullable=true,insertable=false,updatable=false)
	public Branch getBranch() {
		return branch;
	}


	public void setBranch(Branch branch) {
		this.branch = branch;
	}*/

    @Column( name = "cbleaf", nullable = true )
	public Boolean getCbLeaf() {
		return cbLeaf;
	}


	public void setCbLeaf(Boolean cbLeaf) {
		this.cbLeaf = cbLeaf;
	}

	@Column( name = "zcleaf", nullable = true )
	public Boolean getZcLeaf() {
		return zcLeaf;
	}

	public void setZcLeaf(Boolean zcLeaf) {
		this.zcLeaf = zcLeaf;
	}


	@Column(name="crleaf",nullable=true)
	public Boolean getCrLeaf() {
		return crLeaf;
	}


	public void setCrLeaf(Boolean crLeaf) {
		this.crLeaf = crLeaf;
	}

	@Column(name="xmleaf",nullable=true)
	public Boolean getXmLeaf() {
		return xmLeaf;
	}


	public void setXmLeaf(Boolean xmLeaf) {
		this.xmLeaf = xmLeaf;
	}

	@Column(name="dGroup",length=30,nullable=true)
	public String getDgroup() {
		return dgroup;
	}


	public void setDgroup(String dgroup) {
		this.dgroup = dgroup;
	}

	@Override
	public int compareTo(BmDepartment arg0) {
		if(arg0.getDeptCode()==null){
			return 0;
		}
		String[] twoDeptCode = {deptCode,arg0.getDeptCode()};
		Arrays.sort(twoDeptCode);
		if(deptCode.equals(arg0.getDeptCode()))
			return 0;
		else if(!twoDeptCode[0].equals(deptCode))
            return 1;
		else
	        return -1;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cbLeaf == null) ? 0 : cbLeaf.hashCode());
		result = prime * result + ((clevel == null) ? 0 : clevel.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result
				+ ((contactPhone == null) ? 0 : contactPhone.hashCode());
		result = prime * result + ((crLeaf == null) ? 0 : crLeaf.hashCode());
		result = prime * result
				+ ((departmentId == null) ? 0 : departmentId.hashCode());
		result = prime * result
				+ ((deptClass == null) ? 0 : deptClass.hashCode());
		result = prime * result
				+ ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result + ((dgroup == null) ? 0 : dgroup.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((internalCode == null) ? 0 : internalCode.hashCode());
		result = prime * result
				+ ((invalidDate == null) ? 0 : invalidDate.hashCode());
		result = prime * result
				+ ((jjDeptName == null) ? 0 : jjDeptName.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((outin == null) ? 0 : outin.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((shortnName == null) ? 0 : shortnName.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
		result = prime * result
				+ ((subClass == null) ? 0 : subClass.hashCode());
		result = prime * result + ((xmLeaf == null) ? 0 : xmLeaf.hashCode());
		result = prime * result
				+ ((yjDeptName == null) ? 0 : yjDeptName.hashCode());
		result = prime * result
				+ ((ysDeptName == null) ? 0 : ysDeptName.hashCode());
		result = prime * result + ((ysLeaf == null) ? 0 : ysLeaf.hashCode());
		result = prime * result + ((zcLeaf == null) ? 0 : zcLeaf.hashCode());
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
		BmDepartment other = (BmDepartment) obj;
		if (cbLeaf == null) {
			if (other.cbLeaf != null)
				return false;
		} else if (!cbLeaf.equals(other.cbLeaf))
			return false;
		if (clevel == null) {
			if (other.clevel != null)
				return false;
		} else if (!clevel.equals(other.clevel))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (contactPhone == null) {
			if (other.contactPhone != null)
				return false;
		} else if (!contactPhone.equals(other.contactPhone))
			return false;
		if (crLeaf == null) {
			if (other.crLeaf != null)
				return false;
		} else if (!crLeaf.equals(other.crLeaf))
			return false;
		if (departmentId == null) {
			if (other.departmentId != null)
				return false;
		} else if (!departmentId.equals(other.departmentId))
			return false;
		if (deptClass == null) {
			if (other.deptClass != null)
				return false;
		} else if (!deptClass.equals(other.deptClass))
			return false;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (dgroup == null) {
			if (other.dgroup != null)
				return false;
		} else if (!dgroup.equals(other.dgroup))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (internalCode == null) {
			if (other.internalCode != null)
				return false;
		} else if (!internalCode.equals(other.internalCode))
			return false;
		if (invalidDate == null) {
			if (other.invalidDate != null)
				return false;
		} else if (!invalidDate.equals(other.invalidDate))
			return false;
		if (jjDeptName == null) {
			if (other.jjDeptName != null)
				return false;
		} else if (!jjDeptName.equals(other.jjDeptName))
			return false;
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (outin == null) {
			if (other.outin != null)
				return false;
		} else if (!outin.equals(other.outin))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (shortnName == null) {
			if (other.shortnName != null)
				return false;
		} else if (!shortnName.equals(other.shortnName))
			return false;
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
			return false;
		if (subClass == null) {
			if (other.subClass != null)
				return false;
		} else if (!subClass.equals(other.subClass))
			return false;
		if (xmLeaf == null) {
			if (other.xmLeaf != null)
				return false;
		} else if (!xmLeaf.equals(other.xmLeaf))
			return false;
		if (yjDeptName == null) {
			if (other.yjDeptName != null)
				return false;
		} else if (!yjDeptName.equals(other.yjDeptName))
			return false;
		if (ysDeptName == null) {
			if (other.ysDeptName != null)
				return false;
		} else if (!ysDeptName.equals(other.ysDeptName))
			return false;
		if (ysLeaf == null) {
			if (other.ysLeaf != null)
				return false;
		} else if (!ysLeaf.equals(other.ysLeaf))
			return false;
		if (zcLeaf == null) {
			if (other.zcLeaf != null)
				return false;
		} else if (!zcLeaf.equals(other.zcLeaf))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "BmDepartment [departmentId=" + departmentId + ", name=" + name
				+ ", deptCode=" + deptCode + ", shortnName=" + shortnName
				+ ", internalCode=" + internalCode + ", outin=" + outin
				+ ", deptClass=" + deptClass + ", subClass=" + subClass
				+ ", phone=" + phone + ", contact=" + contact
				+ ", contactPhone=" + contactPhone + ", manager=" + manager
				+ ", note=" + note + ", leaf=" + leaf + ", clevel=" + clevel
				+ ", disabled=" + disabled + ", invalidDate=" + invalidDate
				+ ", cnCode=" + cnCode + ", ysLeaf=" + ysLeaf + ", orgCode="
				+ ", dgroup=" + dgroup + ", cbLeaf=" + cbLeaf + ", zcLeaf="
				+ zcLeaf + ", crLeaf=" + crLeaf + ", xmLeaf=" + xmLeaf
				+ ", snapCode=" + snapCode + ", ysDeptName=" + ysDeptName
				+ ", jjDeptName=" + jjDeptName + ", yjDeptName=" + yjDeptName
				+ "]";
	}
	
	
}
