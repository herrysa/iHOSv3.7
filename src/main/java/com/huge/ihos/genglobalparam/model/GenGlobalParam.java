package com.huge.ihos.genglobalparam.model;

/*
 com.huge.ihos.genglobalparam.model.GenGlobalParam
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_gen_globalParam", uniqueConstraints = { @UniqueConstraint( columnNames = { "paramKey" } ),
    @UniqueConstraint( columnNames = { "paramValue" } ) } )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class GenGlobalParam
    extends BaseObject
    implements Serializable {
    private Long paramId;

    private String paramKey;

    private String paramValue;

    private String subSystemId;

    private String description;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getParamId() {
        return paramId;
    }

    public void setParamId( Long paramId ) {
        this.paramId = paramId;
    }

    @Column( name = "paramKey", nullable = false, length = 50 )
    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey( String paramKey ) {
        this.paramKey = paramKey;
    }

    @Column( name = "paramValue", nullable = false, length = 50 )
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue( String paramValue ) {
        this.paramValue = paramValue;
    }

    @Column( name = "subSystemId", length = 50 )
    public String getSubSystemId() {
        return subSystemId;
    }

    public void setSubSystemId( String subSystemId ) {
        this.subSystemId = subSystemId;
    }

    @Column( name = "description", length = 100 )
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( description == null ) ? 0 : description.hashCode() );
        result = prime * result + ( ( paramId == null ) ? 0 : paramId.hashCode() );
        result = prime * result + ( ( paramKey == null ) ? 0 : paramKey.hashCode() );
        result = prime * result + ( ( paramValue == null ) ? 0 : paramValue.hashCode() );
        result = prime * result + ( ( subSystemId == null ) ? 0 : subSystemId.hashCode() );
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
        GenGlobalParam other = (GenGlobalParam) obj;
        if ( description == null ) {
            if ( other.description != null )
                return false;
        }
        else if ( !description.equals( other.description ) )
            return false;
        if ( paramId == null ) {
            if ( other.paramId != null )
                return false;
        }
        else if ( !paramId.equals( other.paramId ) )
            return false;
        if ( paramKey == null ) {
            if ( other.paramKey != null )
                return false;
        }
        else if ( !paramKey.equals( other.paramKey ) )
            return false;
        if ( paramValue == null ) {
            if ( other.paramValue != null )
                return false;
        }
        else if ( !paramValue.equals( other.paramValue ) )
            return false;
        if ( subSystemId == null ) {
            if ( other.subSystemId != null )
                return false;
        }
        else if ( !subSystemId.equals( other.subSystemId ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GlobalParam [paramId=" + paramId + ", paramKey=" + paramKey + ", paramValue=" + paramValue + ", subSystemId=" + subSystemId
            + ", description=" + description + "]";
    }

}
