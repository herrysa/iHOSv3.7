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
@Table( name = "t_deptType" )
@FilterDef( name = "disableFilter", parameters = { @ParamDef( name = "disabled", type = "boolean" ) } )
@Filters( { @Filter( name = "disableFilter", condition = "disabled <> :disabled" ) } )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class DeptType
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6678636096296157716L;

    private String deptTypeId;

    private String deptTypeName;

    private Boolean disabled = false;

    @Override
    public String toString() {
        return "DeptType [deptTypeId=" + deptTypeId + ", deptTypeName=" + deptTypeName + ", disabled=" + disabled + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( deptTypeId == null ) ? 0 : deptTypeId.hashCode() );
        result = prime * result + ( ( deptTypeName == null ) ? 0 : deptTypeName.hashCode() );
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
        DeptType other = (DeptType) obj;
        if ( deptTypeId == null ) {
            if ( other.deptTypeId != null )
                return false;
        }
        else if ( !deptTypeId.equals( other.deptTypeId ) )
            return false;
        if ( deptTypeName == null ) {
            if ( other.deptTypeName != null )
                return false;
        }
        else if ( !deptTypeName.equals( other.deptTypeName ) )
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
    public String getDeptTypeId() {
        return this.deptTypeId;
    }

    public void setDeptTypeId( String checkLevelId ) {
        this.deptTypeId = checkLevelId;
    }

    @Column( name = "deptTypeName", nullable = false, length = 20 )
    public String getDeptTypeName() {
        return this.deptTypeName;
    }

    public void setDeptTypeName( String checkLevel ) {
        this.deptTypeName = checkLevel;
    }

    @Column( name = "disabled", nullable = false )
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

}
