package com.huge.ihos.inout.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_chargetype" )
public class ChargeType
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4720846193048878267L;

    private String chargeTypeId;

    private String chargeTypeName;

    private String medOrLee;

    private Boolean disabled = false;

    private Boolean leaf;

    private Long clevel;

    private String displayMark;

    //private String	parentId;
    private ChargeType parent;

    //private String	payinItemId;

    private PayinItem payinItem;

    private String cnCode;

    //private String	bigtype;
    private String reportmark = "";

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "payinItemId", nullable = true )
    public PayinItem getPayinItem() {
        return payinItem;
    }

    public void setPayinItem( PayinItem payinItem ) {
        this.payinItem = payinItem;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "parentId", nullable = true )
    public ChargeType getParent() {
        return parent;
    }

    public void setParent( ChargeType parent ) {
        this.parent = parent;
    }

    @Id
    public String getChargeTypeId() {
        return this.chargeTypeId;
    }

    public void setChargeTypeId( String chargeTypeId ) {
        this.chargeTypeId = chargeTypeId;
    }

    @Column( name = "chargeType", nullable = false, length = 30 )
    public String getChargeTypeName() {
        return this.chargeTypeName;
    }

    public void setChargeTypeName( String chargeType ) {
        this.chargeTypeName = chargeType;
    }

    @Column( name = "medOrLee", length = 8 )
    public String getMedOrLee() {
        return this.medOrLee;
    }

    public void setMedOrLee( String medOrLee ) {
        this.medOrLee = medOrLee;
    }

    @Column( name = "disabled", nullable = false )
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

    @Column( name = "leaf", nullable = false )
    public Boolean getLeaf() {
        return this.leaf;
    }

    public void setLeaf( Boolean leaf ) {
        this.leaf = leaf;
    }

    @Column( name = "clevel", nullable = false )
    public Long getClevel() {
        return this.clevel;
    }

    public void setClevel( Long clevel ) {
        this.clevel = clevel;
    }

    @Column( name = "displayMark", length = 12 )
    public String getDisplayMark() {
        return this.displayMark;
    }

    public void setDisplayMark( String displayMark ) {
        this.displayMark = displayMark;
    }

    //	@Column(name = "parentId", length = 20)
    //
    //	public String getParentId() {
    //		return this.parentId;
    //	}
    //	
    //	public void setParentId(String parentId) {
    //		this.parentId = parentId;
    //	}

    //	@Column(name = "payinItemId", length = 10)
    //
    //	public String getPayinItemId() {
    //		return this.payinItemId;
    //	}
    //	
    //	public void setPayinItemId(String payinItemId) {
    //		this.payinItemId = payinItemId;
    //	}

    @Column( name = "cnCode", length = 12 )
    public String getCnCode() {
        return this.cnCode;
    }

    public void setCnCode( String cnCode ) {
        this.cnCode = cnCode;
    }

    //	@Column(name = "bigtype", length = 50)
    //
    //	public String getBigtype() {
    //		return this.bigtype;
    //	}
    //	
    //	public void setBigtype(String bigtype) {
    //		this.bigtype = bigtype;
    //	}

    @Column( name = "reportmark", length = 50 )
    public String getReportmark() {
        return this.reportmark;
    }

    public void setReportmark( String reportmark ) {
        this.reportmark = reportmark;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        ChargeType other = (ChargeType) obj;
        //		if (bigtype == null) {
        //			if (other.bigtype != null)
        //				return false;
        //		} else if (!bigtype.equals(other.bigtype))
        //			return false;
        if ( chargeTypeId == null ) {
            if ( other.chargeTypeId != null )
                return false;
        }
        else if ( !chargeTypeId.equals( other.chargeTypeId ) )
            return false;
        if ( chargeTypeName == null ) {
            if ( other.chargeTypeName != null )
                return false;
        }
        else if ( !chargeTypeName.equals( other.chargeTypeName ) )
            return false;
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
        if ( disabled == null ) {
            if ( other.disabled != null )
                return false;
        }
        else if ( !disabled.equals( other.disabled ) )
            return false;
        if ( displayMark == null ) {
            if ( other.displayMark != null )
                return false;
        }
        else if ( !displayMark.equals( other.displayMark ) )
            return false;
        if ( leaf == null ) {
            if ( other.leaf != null )
                return false;
        }
        else if ( !leaf.equals( other.leaf ) )
            return false;
        if ( medOrLee == null ) {
            if ( other.medOrLee != null )
                return false;
        }
        else if ( !medOrLee.equals( other.medOrLee ) )
            return false;
        if ( parent == null ) {
            if ( other.parent != null )
                return false;
        }
        else if ( !parent.equals( other.parent ) )
            return false;
        if ( payinItem == null ) {
            if ( other.payinItem != null )
                return false;
        }
        else if ( !payinItem.equals( other.payinItem ) )
            return false;
        if ( reportmark == null ) {
            if ( other.reportmark != null )
                return false;
        }
        else if ( !reportmark.equals( other.reportmark ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        //result = prime * result + ((bigtype == null) ? 0 : bigtype.hashCode());
        result = prime * result + ( ( chargeTypeId == null ) ? 0 : chargeTypeId.hashCode() );
        result = prime * result + ( ( chargeTypeName == null ) ? 0 : chargeTypeName.hashCode() );
        result = prime * result + ( ( clevel == null ) ? 0 : clevel.hashCode() );
        result = prime * result + ( ( cnCode == null ) ? 0 : cnCode.hashCode() );
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
        result = prime * result + ( ( displayMark == null ) ? 0 : displayMark.hashCode() );
        result = prime * result + ( ( leaf == null ) ? 0 : leaf.hashCode() );
        result = prime * result + ( ( medOrLee == null ) ? 0 : medOrLee.hashCode() );
        //result = prime * result + ( ( parent == null ) ? 0 : parent.hashCode() );
        result = prime * result + ( ( payinItem == null ) ? 0 : payinItem.hashCode() );
        result = prime * result + ( ( reportmark == null ) ? 0 : reportmark.hashCode() );
        return result;
    }

    @Override
    public String toString() {
        return "ChargeType [chargeTypeId=" + chargeTypeId + ", chargeTypeName=" + chargeTypeName + ", medOrLee=" + medOrLee + ", disabled="
            + disabled + ", leaf=" + leaf + ", clevel=" + clevel + ", displayMark=" + displayMark + ", parent=" + parent + ", payinItem=" + payinItem
            + ", cnCode=" + cnCode + ", reportmark=" + reportmark + "]";
    }

}
