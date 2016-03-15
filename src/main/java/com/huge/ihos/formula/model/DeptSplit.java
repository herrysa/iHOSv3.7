package com.huge.ihos.formula.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_deptsplit" )
public class DeptSplit
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6476907065301488354L;

    private Long deptSplitId;

    private String pubdeptid;

    private String pubdeptname;

    private String deptid;

    private String deptname;

    private BigDecimal costratio;

    private BigDecimal payinrattio;

    private String remark;

    private boolean disabled;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getDeptSplitId() {
        return deptSplitId;
    }

    public void setDeptSplitId( Long deptSplitId ) {
        this.deptSplitId = deptSplitId;
    }

    @Column( name = "pubdeptid", nullable = false, length = 20 )
    public String getPubdeptid() {
        return this.pubdeptid;
    }

    public void setPubdeptid( String pubdeptid ) {
        this.pubdeptid = pubdeptid;
    }

    @Column( name = "pubdeptname", nullable = false, length = 50 )
    public String getPubdeptname() {
        return this.pubdeptname;
    }

    public void setPubdeptname( String pubdeptname ) {
        this.pubdeptname = pubdeptname;
    }

    @Column( name = "deptid", nullable = false, length = 20 )
    public String getDeptid() {
        return this.deptid;
    }

    public void setDeptid( String deptid ) {
        this.deptid = deptid;
    }

    @Column( name = "deptname", nullable = false, length = 50 )
    public String getDeptname() {
        return this.deptname;
    }

    public void setDeptname( String deptname ) {
        this.deptname = deptname;
    }

    @Column( name = "costratio", nullable = false, precision = 18, scale = 4 )
    public BigDecimal getCostratio() {
        return this.costratio;
    }

    public void setCostratio( BigDecimal costratio ) {
        this.costratio = costratio;
    }

    @Column( name = "payinrattio", precision = 18, scale = 4 )
    public BigDecimal getPayinrattio() {
        return this.payinrattio;
    }

    public void setPayinrattio( BigDecimal payinrattio ) {
        this.payinrattio = payinrattio;
    }

    @Column( name = "remark", length = 200 )
    public String getRemark() {
        return this.remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @Column( name = "disabled", nullable = false )
    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        DeptSplit other = (DeptSplit) obj;
        if ( costratio == null ) {
            if ( other.costratio != null )
                return false;
        }
        else if ( !costratio.equals( other.costratio ) )
            return false;
        if ( deptSplitId == null ) {
            if ( other.deptSplitId != null )
                return false;
        }
        else if ( !deptSplitId.equals( other.deptSplitId ) )
            return false;
        if ( deptid == null ) {
            if ( other.deptid != null )
                return false;
        }
        else if ( !deptid.equals( other.deptid ) )
            return false;
        if ( deptname == null ) {
            if ( other.deptname != null )
                return false;
        }
        else if ( !deptname.equals( other.deptname ) )
            return false;
        if ( disabled != other.disabled )
            return false;
        if ( payinrattio == null ) {
            if ( other.payinrattio != null )
                return false;
        }
        else if ( !payinrattio.equals( other.payinrattio ) )
            return false;
        if ( pubdeptid == null ) {
            if ( other.pubdeptid != null )
                return false;
        }
        else if ( !pubdeptid.equals( other.pubdeptid ) )
            return false;
        if ( pubdeptname == null ) {
            if ( other.pubdeptname != null )
                return false;
        }
        else if ( !pubdeptname.equals( other.pubdeptname ) )
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
        result = prime * result + ( ( costratio == null ) ? 0 : costratio.hashCode() );
        result = prime * result + ( ( deptSplitId == null ) ? 0 : deptSplitId.hashCode() );
        result = prime * result + ( ( deptid == null ) ? 0 : deptid.hashCode() );
        result = prime * result + ( ( deptname == null ) ? 0 : deptname.hashCode() );
        result = prime * result + ( disabled ? 1231 : 1237 );
        result = prime * result + ( ( payinrattio == null ) ? 0 : payinrattio.hashCode() );
        result = prime * result + ( ( pubdeptid == null ) ? 0 : pubdeptid.hashCode() );
        result = prime * result + ( ( pubdeptname == null ) ? 0 : pubdeptname.hashCode() );
        result = prime * result + ( ( remark == null ) ? 0 : remark.hashCode() );
        return result;
    }

    @Override
    public String toString() {
        return "DeptSplit [deptSplitId=" + deptSplitId + ", pubdeptid=" + pubdeptid + ", pubdeptname=" + pubdeptname + ", deptid=" + deptid
            + ", deptname=" + deptname + ", costratio=" + costratio + ", payinrattio=" + payinrattio + ", remark=" + remark + ", disabled="
            + disabled + "]";
    }

}
