package com.huge.ihos.inout.model;

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
@Table( name = "t_chargeitem" )
public class ChargeItem
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3633069920054374891L;

    private Long chargeItemNo;

    private String chargeItemId;

    private String chargeItemName;

    private String unit;

    private BigDecimal price;

    private String remark;

    private String pyCode;

    private String wbCode;

    private String userDefine1;

    private String userDefine2;

    private String userDefine3;

    private Boolean disabled = false;

    //   private String chargeTypeId;
    private ChargeType chargeType;

    private String specification;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "chargeTypeId", nullable = true )
    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType( ChargeType chargeType ) {
        this.chargeType = chargeType;
    }

//    @Id
//    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "chargeItemNo", nullable = true, length = 30 )
    public Long getChargeItemNo() {
        return this.chargeItemNo;
    }

    public void setChargeItemNo( Long chargeItemNo ) {
        this.chargeItemNo = chargeItemNo;
    }
    
    @Id
    @Column( name = "chargeItemId", nullable = false, length = 50 )
    public String getChargeItemId() {
        return this.chargeItemId;
    }

    public void setChargeItemId( String chargeItemId ) {
        this.chargeItemId = chargeItemId;
    }

    @Column( name = "chargeItem", length = 100 )
    public String getChargeItemName() {
        return this.chargeItemName;
    }

    public void setChargeItemName( String chargeItem ) {
        this.chargeItemName = chargeItem;
    }

    @Column( name = "unit", length = 15 )
    public String getUnit() {
        return this.unit;
    }

    public void setUnit( String unit ) {
        this.unit = unit;
    }

    @Column( name = "price", precision = 18, scale = 4 )
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice( BigDecimal price ) {
        this.price = price;
    }

    @Column( name = "remark", length = 100 )
    public String getRemark() {
        return this.remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @Column( name = "pyCode", length = 100 )
    public String getPyCode() {
        return this.pyCode;
    }

    public void setPyCode( String pyCode ) {
        this.pyCode = pyCode;
    }

    @Column( name = "wbCode", length = 10 )
    public String getWbCode() {
        return this.wbCode;
    }

    public void setWbCode( String wbCode ) {
        this.wbCode = wbCode;
    }

    @Column( name = "userDefine1", length = 20 )
    public String getUserDefine1() {
        return this.userDefine1;
    }

    public void setUserDefine1( String b1 ) {
        this.userDefine1 = b1;
    }

    @Column( name = "userDefine2", length = 20 )
    public String getUserDefine2() {
        return this.userDefine2;
    }

    public void setUserDefine2( String b2 ) {
        this.userDefine2 = b2;
    }

    @Column( name = "userDefine3", length = 20 )
    public String getUserDefine3() {
        return this.userDefine3;
    }

    public void setUserDefine3( String b3 ) {
        this.userDefine3 = b3;
    }

    @Column( name = "disabled", nullable = false )
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

    @Column( name = "specification", length = 64 )
    public String getSpecification() {
        return this.specification;
    }

    public void setSpecification( String gg ) {
        this.specification = gg;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( chargeItemId == null ) ? 0 : chargeItemId.hashCode() );
        result = prime * result + ( ( chargeItemName == null ) ? 0 : chargeItemName.hashCode() );
        result = prime * result + ( ( chargeItemNo == null ) ? 0 : chargeItemNo.hashCode() );
        result = prime * result + ( ( chargeType == null ) ? 0 : chargeType.hashCode() );
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
        result = prime * result + ( ( price == null ) ? 0 : price.hashCode() );
        result = prime * result + ( ( pyCode == null ) ? 0 : pyCode.hashCode() );
        result = prime * result + ( ( remark == null ) ? 0 : remark.hashCode() );
        result = prime * result + ( ( specification == null ) ? 0 : specification.hashCode() );
        result = prime * result + ( ( unit == null ) ? 0 : unit.hashCode() );
        result = prime * result + ( ( userDefine1 == null ) ? 0 : userDefine1.hashCode() );
        result = prime * result + ( ( userDefine2 == null ) ? 0 : userDefine2.hashCode() );
        result = prime * result + ( ( userDefine3 == null ) ? 0 : userDefine3.hashCode() );
        result = prime * result + ( ( wbCode == null ) ? 0 : wbCode.hashCode() );
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
        ChargeItem other = (ChargeItem) obj;
        if ( chargeItemId == null ) {
            if ( other.chargeItemId != null )
                return false;
        }
        else if ( !chargeItemId.equals( other.chargeItemId ) )
            return false;
        if ( chargeItemName == null ) {
            if ( other.chargeItemName != null )
                return false;
        }
        else if ( !chargeItemName.equals( other.chargeItemName ) )
            return false;
        if ( chargeItemNo == null ) {
            if ( other.chargeItemNo != null )
                return false;
        }
        else if ( !chargeItemNo.equals( other.chargeItemNo ) )
            return false;
        if ( chargeType == null ) {
            if ( other.chargeType != null )
                return false;
        }
        else if ( !chargeType.equals( other.chargeType ) )
            return false;
        if ( disabled == null ) {
            if ( other.disabled != null )
                return false;
        }
        else if ( !disabled.equals( other.disabled ) )
            return false;
        if ( price == null ) {
            if ( other.price != null )
                return false;
        }
        else if ( !price.equals( other.price ) )
            return false;
        if ( pyCode == null ) {
            if ( other.pyCode != null )
                return false;
        }
        else if ( !pyCode.equals( other.pyCode ) )
            return false;
        if ( remark == null ) {
            if ( other.remark != null )
                return false;
        }
        else if ( !remark.equals( other.remark ) )
            return false;
        if ( specification == null ) {
            if ( other.specification != null )
                return false;
        }
        else if ( !specification.equals( other.specification ) )
            return false;
        if ( unit == null ) {
            if ( other.unit != null )
                return false;
        }
        else if ( !unit.equals( other.unit ) )
            return false;
        if ( userDefine1 == null ) {
            if ( other.userDefine1 != null )
                return false;
        }
        else if ( !userDefine1.equals( other.userDefine1 ) )
            return false;
        if ( userDefine2 == null ) {
            if ( other.userDefine2 != null )
                return false;
        }
        else if ( !userDefine2.equals( other.userDefine2 ) )
            return false;
        if ( userDefine3 == null ) {
            if ( other.userDefine3 != null )
                return false;
        }
        else if ( !userDefine3.equals( other.userDefine3 ) )
            return false;
        if ( wbCode == null ) {
            if ( other.wbCode != null )
                return false;
        }
        else if ( !wbCode.equals( other.wbCode ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ChargeItem [chargeItemNo=" + chargeItemNo + ", chargeItemId=" + chargeItemId + ", chargeItemName=" + chargeItemName + ", unit="
            + unit + ", price=" + price + ", remark=" + remark + ", pyCode=" + pyCode + ", wbCode=" + wbCode + ", userDefine1=" + userDefine1
            + ", userDefine2=" + userDefine2 + ", userDefine3=" + userDefine3 + ", disabled=" + disabled + ", chargeType=" + chargeType
            + ", specification=" + specification + "]";
    }

}
