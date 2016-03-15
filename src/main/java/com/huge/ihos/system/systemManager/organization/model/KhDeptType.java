package com.huge.ihos.system.systemManager.organization.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.huge.model.BaseObject;

@Entity
@Table( name = "KH_Dict_JjDeptType" )
@FilterDef( name = "disableFilter", parameters = { @ParamDef( name = "disabled", type = "boolean" ) } )
@Filters( { @Filter( name = "disableFilter", condition = "disabled <> :disabled" ) } )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class KhDeptType
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6678636096296157716L;

    private String khDeptTypeId;

    private String khDeptTypeName;

    private Boolean disabled = false;

    @Override
    public String toString() {
        return "KhDeptType [khDeptTypeId=" + khDeptTypeId + ", khDeptTypeName=" + khDeptTypeName + ", disabled=" + disabled + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( khDeptTypeId == null ) ? 0 : khDeptTypeId.hashCode() );
        result = prime * result + ( ( khDeptTypeName == null ) ? 0 : khDeptTypeName.hashCode() );
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
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
        KhDeptType other = (KhDeptType) obj;
        if ( khDeptTypeId == null ) {
            if ( other.khDeptTypeId != null )
                return false;
        }
        else if ( !khDeptTypeId.equals( other.khDeptTypeId ) )
            return false;
        if ( khDeptTypeName == null ) {
            if ( other.khDeptTypeName != null )
                return false;
        }
        else if ( !khDeptTypeName.equals( other.khDeptTypeName ) )
            return false;
        if ( disabled == null ) {
            if ( other.disabled != null )
                return false;
        }
        else if ( !disabled.equals( other.disabled ) )
            return false;
        return true;
    }

    @Id
    @Column( name = "jjDeptTypeId", nullable = false, length = 20 )
    public String getKhDeptTypeId() {
        return this.khDeptTypeId;
    }

    public void setKhDeptTypeId( String khDeptTypeId ) {
        this.khDeptTypeId = khDeptTypeId;
    }

    @Column( name = "jjDeptTypeName", nullable = false, length = 20 )
    public String getKhDeptTypeName() {
        return this.khDeptTypeName;
    }

    public void setKhDeptTypeName( String khDeptTypeName ) {
        this.khDeptTypeName = khDeptTypeName;
    }

    @Column( name = "disabled", nullable = false )
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

}
