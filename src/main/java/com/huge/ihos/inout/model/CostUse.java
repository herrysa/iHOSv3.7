package com.huge.ihos.inout.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_costuse" )
public class CostUse
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -9082399712482596075L;

    private String costUseId;

    private String costUseName;

    private Boolean disabled = false;

    @Id
    public String getCostUseId() {
        return this.costUseId;
    }

    public void setCostUseId( String costUseId ) {
        this.costUseId = costUseId;
    }

    @Column( name = "costUse", nullable = false, length = 20 )
    public String getCostUseName() {
        return this.costUseName;
    }

    public void setCostUseName( String costUse ) {
        this.costUseName = costUse;
    }

    @Column( name = "disabled", nullable = false )
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled( Boolean disabled ) {
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
        CostUse other = (CostUse) obj;
        if ( costUseName == null ) {
            if ( other.costUseName != null )
                return false;
        }
        else if ( !costUseName.equals( other.costUseName ) )
            return false;
        if ( costUseId == null ) {
            if ( other.costUseId != null )
                return false;
        }
        else if ( !costUseId.equals( other.costUseId ) )
            return false;
        if ( disabled == null ) {
            if ( other.disabled != null )
                return false;
        }
        else if ( !disabled.equals( other.disabled ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( costUseName == null ) ? 0 : costUseName.hashCode() );
        result = prime * result + ( ( costUseId == null ) ? 0 : costUseId.hashCode() );
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
        return result;
    }

    @Override
    public String toString() {
        return "TCostuse [costUseId=" + costUseId + ", costUse=" + costUseName + ", disabled=" + disabled + "]";
    }

}
