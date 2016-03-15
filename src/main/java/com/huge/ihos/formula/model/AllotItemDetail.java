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

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_allotItemDetail" )
public class AllotItemDetail
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3284357863863293314L;

    private Long allotItemDetailId;

    private BigDecimal costRatio;

    private String remark;

    private AllotItem allotItem;

    private AllotPrinciple principle;

    private AllotPrinciple bakPrinciple;

    private AllotPrinciple realPrinciple;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getAllotItemDetailId() {
        return allotItemDetailId;
    }

    public void setAllotItemDetailId( Long allotItemDetailId ) {
        this.allotItemDetailId = allotItemDetailId;
    }

    @Column( name = "costRatio", nullable = false, precision = 18, scale = 6 )
    public BigDecimal getCostRatio() {
        return this.costRatio;
    }

    public void setCostRatio( BigDecimal costRatio ) {
        this.costRatio = costRatio;
    }

    @Column( name = "remark", length = 100 )
    public String getRemark() {
        return this.remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "allotItemId", nullable = false )
    public AllotItem getAllotItem() {
        return allotItem;
    }

    public void setAllotItem( AllotItem allotItem ) {
        this.allotItem = allotItem;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "principleId", nullable = false )
    public AllotPrinciple getPrinciple() {
        return principle;
    }

    public void setPrinciple( AllotPrinciple principle ) {
        this.principle = principle;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "bakPrincipleId", nullable = false )
    public AllotPrinciple getBakPrinciple() {
        return bakPrinciple;
    }

    public void setBakPrinciple( AllotPrinciple bakPrinciple ) {
        this.bakPrinciple = bakPrinciple;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "realPrincipleId", nullable = true )
    public AllotPrinciple getRealPrinciple() {
        return realPrinciple;
    }

    public void setRealPrinciple( AllotPrinciple realPrinciple ) {
        this.realPrinciple = realPrinciple;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( allotItem == null ) ? 0 : allotItem.hashCode() );
        result = prime * result + ( ( allotItemDetailId == null ) ? 0 : allotItemDetailId.hashCode() );
        result = prime * result + ( ( bakPrinciple == null ) ? 0 : bakPrinciple.hashCode() );
        result = prime * result + ( ( costRatio == null ) ? 0 : costRatio.hashCode() );
        result = prime * result + ( ( principle == null ) ? 0 : principle.hashCode() );
        result = prime * result + ( ( realPrinciple == null ) ? 0 : realPrinciple.hashCode() );
        result = prime * result + ( ( remark == null ) ? 0 : remark.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        AllotItemDetail other = (AllotItemDetail) obj;
        if ( allotItem == null ) {
            if ( other.allotItem != null )
                return false;
        }
        else if ( !allotItem.equals( other.allotItem ) )
            return false;
        if ( allotItemDetailId == null ) {
            if ( other.allotItemDetailId != null )
                return false;
        }
        else if ( !allotItemDetailId.equals( other.allotItemDetailId ) )
            return false;
        if ( bakPrinciple == null ) {
            if ( other.bakPrinciple != null )
                return false;
        }
        else if ( !bakPrinciple.equals( other.bakPrinciple ) )
            return false;
        if ( costRatio == null ) {
            if ( other.costRatio != null )
                return false;
        }
        else if ( !costRatio.equals( other.costRatio ) )
            return false;
        if ( principle == null ) {
            if ( other.principle != null )
                return false;
        }
        else if ( !principle.equals( other.principle ) )
            return false;
        if ( realPrinciple == null ) {
            if ( other.realPrinciple != null )
                return false;
        }
        else if ( !realPrinciple.equals( other.realPrinciple ) )
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
    public String toString() {
        return "AllotItemDetail [allotItemDetailId=" + allotItemDetailId + ", costRatio=" + costRatio + ", remark=" + remark + ", allotItem="
            + allotItem + ", principle=" + principle + ", bakPrinciple=" + bakPrinciple + ", realPrinciple=" + realPrinciple + "]";
    }

}
