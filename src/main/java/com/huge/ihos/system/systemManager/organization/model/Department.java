package com.huge.ihos.system.systemManager.organization.model;

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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.ParamDef;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_department" )
@JsonIgnoreProperties( { "parentDept" } )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
@FilterDef( name = "exceptDepartmentFilter", parameters = { @ParamDef( name = "exceptDepartment", type = "string" ) } )
@Filters( { @Filter( name = "exceptDepartmentFilter", condition = "deptId <> :exceptDepartment" ) } )
public class Department
    extends BaseObject
    implements Serializable,Comparable<Department> {
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

    private Department parentDept;
    
    private String parentDeptName;

	private Boolean disabled = false; // 是否可用

    private Date invalidDate; // 失效日期

    private String cnCode;
    
    private Boolean jjLeaf = false; // 是否为叶子(绩效)
    
    private Department jjDept;//奖金
    
    private Boolean ysLeaf = false; // 是否为叶子（预算）
    
    private Department ysDept;//预算
    
    private KhDeptType jjDeptType;
    
    private String orgCode;//单位
    
    private Org org;
    
    private String branchCode; //院区
    
    private String branchName; //院区
    
    @Transient
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
	}


	private Branch branch;
    
    private String dgroup;
    
    private Boolean cbLeaf = false; // 是否为叶子(成本)
    
    private Boolean zcLeaf = false; // 是否为叶子(诊次)
    
    private Boolean crLeaf = false; // 是否为叶子(床日)
    
    private Boolean xmLeaf = false; // 是否为叶子(项目)
    
    private Department yjDept;
    
    private String snapCode="0";
    
    private String ysDeptName;

	private String jjDeptName;
    private String yjDeptName;
    
    private int personCount = 0;//包含停用部门；包含停用人员
    private int personCountD = 0;//不包含停用部门；包含停用人员
    private int personCountP = 0;//包含停用部门；不包含停用人员
    private int personCountDP = 0;//不包含停用部门；不包含停用人员
    private int personCountWithOutDisabled = 0;
    
    /*工资帐表临时字段*/
    private List<Double> numberList;
    private List<String> stringList;
    private Boolean showFlag;
    //private Boolean ysPurchasingDepartment = false; //是否采购科室

	@Id
    @Column( name = "deptId",length=50)
    public String getDepartmentId() {
        return departmentId;
    }
    
   
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "parentDept_id", nullable = true )
    public Department getParentDept() {
        return parentDept;
    }
    
    public void setParentDept( Department parentDept ) {
    	this.parentDept = parentDept;
    }

    @Formula("(select count(*) from t_person as cp where cp.dept_id = deptId)")
	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}
	@Transient
	public int getPersonCountD() {
		return personCountD;
	}


	public void setPersonCountD(int personCountD) {
		this.personCountD = personCountD;
	}

	@Transient
	public int getPersonCountDP() {
		return personCountDP;
	}


	public void setPersonCountDP(int personCountDP) {
		this.personCountDP = personCountDP;
	}

	@Transient
	public int getPersonCountP() {
		return personCountP;
	}


	public void setPersonCountP(int personCountP) {
		this.personCountP = personCountP;
	}
	@Formula("(select count(*) from t_person as cp where cp.dept_id = deptId and cp.disabled =0)")
	public int getPersonCountWithOutDisabled() {
		return personCountWithOutDisabled;
	}

	public void setPersonCountWithOutDisabled(int personCountWithOutDisabled) {
		this.personCountWithOutDisabled = personCountWithOutDisabled;
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

    @Column( name = "jjLeaf", nullable = true )
    public Boolean getJjLeaf() {
		return jjLeaf;
	}

	public void setJjLeaf(Boolean jjLeaf) {
		this.jjLeaf = jjLeaf;
	}


    @Column( name = "ysLeaf", nullable = true )
	public Boolean getYsLeaf() {
		return ysLeaf;
	}

	public void setYsLeaf(Boolean ysLeaf) {
		this.ysLeaf = ysLeaf;
	}
	
	/*@Column( name = "ysPurchasingDepartment", nullable = true )
	public Boolean getYsPurchasingDepartment() {
		return ysPurchasingDepartment;
	}


	public void setYsPurchasingDepartment(Boolean ysPurchasingDepartment) {
		this.ysPurchasingDepartment = ysPurchasingDepartment;
	}*/

	@JSON(serialize=false)
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "jjDeptId", nullable = true )
	public Department getJjDept() {
		return jjDept;
	}

	public void setJjDept(Department jjDept) {
		this.jjDept = jjDept;
	}

	@JSON(serialize=false)
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "ysDeptId", nullable = true  )
	public Department getYsDept() {
		return ysDept;
	}

	public void setYsDept(Department ysDept) {
		this.ysDept = ysDept;
	}

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "jjdepttype", nullable = true  )
	//@Column( name = "jjdepttype", length = 50 )
    public KhDeptType getJjDeptType() {
		return jjDeptType;
	}

	public void setJjDeptType(KhDeptType jjDeptType) {
		this.jjDeptType = jjDeptType;
	}
	
	@Transient
	public String getParentDeptName() {
		if(parentDept!=null){
			parentDeptName = parentDept.getName();
		}else{
			parentDeptName = "";
		}
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
	
	@Column( name = "orgCode", nullable = true ,length=20)
	public String getOrgCode() {
		return orgCode;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgCode", nullable = true, insertable = false, updatable = false)
	public Org getOrg() {
		return org;
	}


	public void setOrg(Org org) {
		this.org = org;
	}

	@Column(name = "snapCode", length = 14, nullable = true)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}
	@Column(name="branchCode",length=30,nullable=true)
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
	}

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


	@JSON(serialize=false)
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "yjDeptId", nullable = true )
	public Department getYjDept() {
		return yjDept;
	}


	public void setYjDept(Department yjDept) {
		this.yjDept = yjDept;
	}
	
	@Transient
    public String getYsDeptName() {
		if(ysDept!=null){
			ysDeptName = ysDept.getName();
		}
		return ysDeptName;
	}


	public void setYsDeptName(String ysDeptName) {
		this.ysDeptName = ysDeptName;
	}


	@Transient
	public String getJjDeptName() {
		if(jjDept!=null){
			jjDeptName = jjDept.getName();
		}
		return jjDeptName;
	}


	public void setJjDeptName(String jjDeptName) {
		this.jjDeptName = jjDeptName;
	}


	@Transient
	public String getYjDeptName() {
		if(yjDept!=null){
			yjDeptName = yjDept.getName();
		}
		return yjDeptName;
	}


	public void setYjDeptName(String yjDeptName) {
		this.yjDeptName = yjDeptName;
	}


	@Override
    public String toString() {
        return "Department [departmentId=" + departmentId + ", name=" + name + ", deptCode=" + deptCode + ", shortnName=" + shortnName
            + ", internalCode=" + internalCode + ", outin=" + outin + ", deptClass=" + deptClass + ", subClass=" + subClass + ", phone=" + phone
            + ", contact=" + contact + ", contactPhone=" + contactPhone + ", manager=" + manager + ", note=" + note + ", leaf=" + leaf + ", clevel="
            + clevel + ", parentDept=" + parentDept + ", disabled=" + disabled + ", invalidDate=" + invalidDate + ", cnCode=" + cnCode+"]";
    }



	@Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Department other = (Department) obj;
        if ( clevel == null ) {
            if ( other.clevel != null )
                return false;
        }
        else if ( !clevel.equals( other.clevel ) )
            return false;
        if ( cnCode == null ) {
            if ( other.cnCode != null )
                return false;
        }
        else if ( !cnCode.equals( other.cnCode ) )
            return false;
        if ( contact == null ) {
            if ( other.contact != null )
                return false;
        }
        else if ( !contact.equals( other.contact ) )
            return false;
        if ( contactPhone == null ) {
            if ( other.contactPhone != null )
                return false;
        }
        else if ( !contactPhone.equals( other.contactPhone ) )
            return false;
        if ( departmentId == null ) {
            if ( other.departmentId != null )
                return false;
        }
        else if ( !departmentId.equals( other.departmentId ) )
            return false;
        if ( deptClass == null ) {
            if ( other.deptClass != null )
                return false;
        }
        else if ( !deptClass.equals( other.deptClass ) )
            return false;
        if ( deptCode == null ) {
            if ( other.deptCode != null )
                return false;
        }
        else if ( !deptCode.equals( other.deptCode ) )
            return false;
        if ( disabled == null ) {
            if ( other.disabled != null )
                return false;
        }
        else if ( !disabled.equals( other.disabled ) )
            return false;
        /*if(ysPurchasingDepartment==null){
        	if(other.ysPurchasingDepartment !=null)
        		return false;
        }
        else if(!ysPurchasingDepartment.equals(other.ysPurchasingDepartment))
        	return false;*/
        if ( internalCode == null ) {
            if ( other.internalCode != null )
                return false;
        }
        else if ( !internalCode.equals( other.internalCode ) )
            return false;
        if ( invalidDate == null ) {
            if ( other.invalidDate != null )
                return false;
        }
        else if ( !invalidDate.equals( other.invalidDate ) )
            return false;
        if ( leaf == null ) {
            if ( other.leaf != null )
                return false;
        }
        else if ( !leaf.equals( other.leaf ) )
            return false;
        if ( manager == null ) {
            if ( other.manager != null )
                return false;
        }
        else if ( !manager.equals( other.manager ) )
            return false;
        if ( name == null ) {
            if ( other.name != null )
                return false;
        }
        else if ( !name.equals( other.name ) )
            return false;
        if ( note == null ) {
            if ( other.note != null )
                return false;
        }
        else if ( !note.equals( other.note ) )
            return false;
        if ( outin == null ) {
            if ( other.outin != null )
                return false;
        }
        else if ( !outin.equals( other.outin ) )
            return false;
        if ( parentDept == null ) {
            if ( other.parentDept != null )
                return false;
        }
        else if ( !parentDept.equals( other.parentDept ) )
            return false;
        if ( phone == null ) {
            if ( other.phone != null )
                return false;
        }
        else if ( !phone.equals( other.phone ) )
            return false;
        if ( shortnName == null ) {
            if ( other.shortnName != null )
                return false;
        }
        else if ( !shortnName.equals( other.shortnName ) )
            return false;
        if ( subClass == null ) {
            if ( other.subClass != null )
                return false;
        }
        else if ( !subClass.equals( other.subClass ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( clevel == null ) ? 0 : clevel.hashCode() );
        result = prime * result + ( ( cnCode == null ) ? 0 : cnCode.hashCode() );
        result = prime * result + ( ( contact == null ) ? 0 : contact.hashCode() );
        result = prime * result + ( ( contactPhone == null ) ? 0 : contactPhone.hashCode() );
        result = prime * result + ( ( departmentId == null ) ? 0 : departmentId.hashCode() );
        result = prime * result + ( ( deptClass == null ) ? 0 : deptClass.hashCode() );
        result = prime * result + ( ( deptCode == null ) ? 0 : deptCode.hashCode() );
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
        result = prime * result + ( ( internalCode == null ) ? 0 : internalCode.hashCode() );
        result = prime * result + ( ( invalidDate == null ) ? 0 : invalidDate.hashCode() );
        result = prime * result + ( ( leaf == null ) ? 0 : leaf.hashCode() );
        result = prime * result + ( ( manager == null ) ? 0 : manager.hashCode() );
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + ( ( note == null ) ? 0 : note.hashCode() );
        result = prime * result + ( ( outin == null ) ? 0 : outin.hashCode() );
        //result = prime * result + ( ( parentDept == null ) ? 0 : parentDept.hashCode() );
        result = prime * result + ( ( phone == null ) ? 0 : phone.hashCode() );
        result = prime * result + ( ( shortnName == null ) ? 0 : shortnName.hashCode() );
        result = prime * result + ( ( subClass == null ) ? 0 : subClass.hashCode() );
        //result = prime * result + ( (ysPurchasingDepartment == null) ? 0 : ysPurchasingDepartment.hashCode());
        return result;
    }

    @Transient
	public List<Double> getNumberList() {
		return numberList;
	}

	public void setNumberList(List<Double> numberList) {
		this.numberList = numberList;
	}

	@Transient
	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	@Transient
	public Boolean getShowFlag() {
		return showFlag;
	}


	public void setShowFlag(Boolean showFlag) {
		this.showFlag = showFlag;
	}


	@Override
	public int compareTo(Department arg0) {
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
}
