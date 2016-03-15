package com.huge.ihos.inout.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_payinitem" )
public class PayinItem
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -897911796708639885L;

    private String payinItemId;

    private String payinItemName;

    private String remark;

    private Boolean disabled = false;

    private String cnCode;

    @Id
    @Column( name = "payinItemId", nullable = false, length = 50 )
    public String getPayinItemId() {
        return this.payinItemId;
    }

    public void setPayinItemId( String payinItemId ) {
        this.payinItemId = payinItemId;
    }

    @Column( name = "payinItem", nullable = false, length = 30 )
    public String getPayinItemName() {
        return this.payinItemName;
    }

    public void setPayinItemName( String payinItem ) {
        this.payinItemName = payinItem;
    }

    @Column( name = "remark", length = 100 )
    public String getRemark() {
        return this.remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @Column( name = "disabled", nullable = false )
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

    @Column( name = "cnCode", length = 12 )
    public String getCnCode() {
        return this.cnCode;
    }

    public void setCnCode( String cnCode ) {
        this.cnCode = cnCode;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        PayinItem other = (PayinItem) obj;
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
        if ( payinItemName == null ) {
            if ( other.payinItemName != null )
                return false;
        }
        else if ( !payinItemName.equals( other.payinItemName ) )
            return false;
        if ( payinItemId == null ) {
            if ( other.payinItemId != null )
                return false;
        }
        else if ( !payinItemId.equals( other.payinItemId ) )
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
        result = prime * result + ( ( cnCode == null ) ? 0 : cnCode.hashCode() );
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
        result = prime * result + ( ( payinItemName == null ) ? 0 : payinItemName.hashCode() );
        result = prime * result + ( ( payinItemId == null ) ? 0 : payinItemId.hashCode() );
        result = prime * result + ( ( remark == null ) ? 0 : remark.hashCode() );
        return result;
    }

    @Override
    public String toString() {
        return "TPayinitem [payinItemId=" + payinItemId + ", payinItem=" + payinItemName + ", remark=" + remark + ", disabled=" + disabled
            + ", cnCode=" + cnCode + "]";
    }

}
