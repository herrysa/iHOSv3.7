package com.huge.ihos.formula.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.model.BaseObject;

@Entity
@Table( name = "t_allotItem" )
public class AllotItem
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2029292948364597715L;

    private Long allotItemId;

    private String checkPeriod;

    private BigDecimal allotCost=new BigDecimal(0);

    private String remark;

    private CostItem costItem; //非必填

    private String calcType; // 计算、分摊

    private Department department;

    private boolean cjflag; // 冲减标志

    private String costType; //直接、管理、医辅、医技

    private String freeMark;

    private boolean dxflag; // 定向标志

    private String dxDeptIds; // 逗号分开

    private String dxDeptNames; // 仅供显示
    
    private Org org;
    
    private Branch branch;

    /**
     * 加入对set的引用（parent）
     */
    private AllotSet allotSet;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "allotSetId", nullable = false )
    public AllotSet getAllotSet() {
        return allotSet;
    }

    public void setAllotSet( AllotSet allotSet ) {
        this.allotSet = allotSet;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getAllotItemId() {
        return allotItemId;
    }

    public void setAllotItemId( Long allotItemId ) {
        this.allotItemId = allotItemId;
    }

    @Column( name = "checkPeriod", nullable = false, length = 6 )
    public String getCheckPeriod() {
        return this.checkPeriod;
    }

    public void setCheckPeriod( String checkPeriod ) {
        this.checkPeriod = checkPeriod;
    }

    @Column( name = "allotCost", nullable = false, precision = 18, scale = 6 )
    public BigDecimal getAllotCost() {
        return allotCost;
    }

    public void setAllotCost( BigDecimal allotCost ) {
        this.allotCost = allotCost;
    }

    @Column( name = "remark", length = 500 )
    public String getRemark() {
        return this.remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "costItemId", nullable = true )
    public CostItem getCostItem() {
        return costItem;
    }

    public void setCostItem( CostItem costItem ) {
        this.costItem = costItem;
    }

    @Column( name = "calcType", length = 10 )
    public String getCalcType() {
        return this.calcType;
    }

    public void setCalcType( String calcType ) {
        this.calcType = calcType;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "deptId", nullable = true )
    public Department getDepartment() {
        return department;
    }

    public void setDepartment( Department department ) {
        this.department = department;
    }

    @Column( name = "cjflag" )
    public boolean isCjflag() {
        return this.cjflag;
    }

    public void setCjflag( boolean cjflag ) {
        this.cjflag = cjflag;
    }

    @Column( name = "costType", length = 10 )
    public String getCostType() {
        return this.costType;
    }

    public void setCostType( String costType ) {
        this.costType = costType;
    }

    @Column( name = "freeMark", length = 50 )
    public String getFreeMark() {
        return freeMark;
    }

    public void setFreeMark( String freeMark ) {
        this.freeMark = freeMark;
    }

    @Column( name = "dxflag" )
    public boolean isDxflag() {
        return this.dxflag;
    }

    public void setDxflag( boolean dxflag ) {
        this.dxflag = dxflag;
    }

    @Column( name = "dxDeptIds", length = 8000 )
    public String getDxDeptIds() {
        return this.dxDeptIds;
    }

    public void setDxDeptIds( String dxDeptIds ) {
        this.dxDeptIds = dxDeptIds;
    }

    @Column( name = "dxDeptNames", length = 8000 )
    public String getDxDeptNames() {
        return this.dxDeptNames;
    }

    public void setDxDeptNames( String dxDeptNames ) {
        this.dxDeptNames = dxDeptNames;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orgCode",nullable=true)
    public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="branchCode",nullable=true)
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        AllotItem other = (AllotItem) obj;
        if ( allotCost == null ) {
            if ( other.allotCost != null )
                return false;
        }
        else if ( !allotCost.equals( other.allotCost ) )
            return false;
        if ( allotItemId == null ) {
            if ( other.allotItemId != null )
                return false;
        }
        else if ( !allotItemId.equals( other.allotItemId ) )
            return false;
        if ( allotSet == null ) {
            if ( other.allotSet != null )
                return false;
        }
        else if ( !allotSet.equals( other.allotSet ) )
            return false;
        if ( calcType == null ) {
            if ( other.calcType != null )
                return false;
        }
        else if ( !calcType.equals( other.calcType ) )
            return false;
        if ( checkPeriod == null ) {
            if ( other.checkPeriod != null )
                return false;
        }
        else if ( !checkPeriod.equals( other.checkPeriod ) )
            return false;
        if ( cjflag != other.cjflag )
            return false;
        if ( costItem == null ) {
            if ( other.costItem != null )
                return false;
        }
        else if ( !costItem.equals( other.costItem ) )
            return false;
        if ( costType == null ) {
            if ( other.costType != null )
                return false;
        }
        else if ( !costType.equals( other.costType ) )
            return false;
        if ( department == null ) {
            if ( other.department != null )
                return false;
        }
        else if ( !department.equals( other.department ) )
            return false;
        if ( dxDeptIds == null ) {
            if ( other.dxDeptIds != null )
                return false;
        }
        else if ( !dxDeptIds.equals( other.dxDeptIds ) )
            return false;
        if ( dxDeptNames == null ) {
            if ( other.dxDeptNames != null )
                return false;
        }
        else if ( !dxDeptNames.equals( other.dxDeptNames ) )
            return false;
        if ( dxflag != other.dxflag )
            return false;
        if ( freeMark == null ) {
            if ( other.freeMark != null )
                return false;
        }
        else if ( !freeMark.equals( other.freeMark ) )
            return false;
        if ( remark == null ) {
            if ( other.remark != null )
                return false;
        }
        else if ( !remark.equals( other.remark ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( allotCost == null ) ? 0 : allotCost.hashCode() );
        result = prime * result + ( ( allotItemId == null ) ? 0 : allotItemId.hashCode() );
        result = prime * result + ( ( allotSet == null ) ? 0 : allotSet.hashCode() );
        result = prime * result + ( ( calcType == null ) ? 0 : calcType.hashCode() );
        result = prime * result + ( ( checkPeriod == null ) ? 0 : checkPeriod.hashCode() );
        result = prime * result + ( cjflag ? 1231 : 1237 );
        result = prime * result + ( ( costItem == null ) ? 0 : costItem.hashCode() );
        result = prime * result + ( ( costType == null ) ? 0 : costType.hashCode() );
        result = prime * result + ( ( department == null ) ? 0 : department.hashCode() );
        result = prime * result + ( ( dxDeptIds == null ) ? 0 : dxDeptIds.hashCode() );
        result = prime * result + ( ( dxDeptNames == null ) ? 0 : dxDeptNames.hashCode() );
        result = prime * result + ( dxflag ? 1231 : 1237 );
        result = prime * result + ( ( freeMark == null ) ? 0 : freeMark.hashCode() );
        result = prime * result + ( ( remark == null ) ? 0 : remark.hashCode() );
        return result;
    }

    @Override
    public String toString() {
        return "AllotItem [allotItemId=" + allotItemId + ", checkPeriod=" + checkPeriod + ", allotCost=" + allotCost + ", remark=" + remark
            + ", costItem=" + costItem + ", calcType=" + calcType + ", department=" + department + ", cjflag=" + cjflag + ", costType=" + costType
            + ", freeMark=" + freeMark + ", dxflag=" + dxflag + ", dxDeptIds=" + dxDeptIds + ", dxDeptNames=" + dxDeptNames + ", allotSet="
            + allotSet + "]";
    }

}
