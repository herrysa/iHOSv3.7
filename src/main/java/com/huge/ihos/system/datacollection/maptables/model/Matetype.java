package com.huge.ihos.system.datacollection.maptables.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_matetype" )
public class Matetype
    extends BaseObject
    implements Serializable {
    private Long mateMapId;

    private String mateTypeId;

    private String mateType;

    private String costItemId;

    private String costitem;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getMateMapId() {
        return mateMapId;
    }

    public void setMateMapId( Long mateMapId ) {
        this.mateMapId = mateMapId;
    }

    @Column( name = "mateTypeId", nullable = false, length = 60 )
    public String getMateTypeId() {
        return this.mateTypeId;
    }

    public void setMateTypeId( String mateTypeId ) {
        this.mateTypeId = mateTypeId;
    }

    @Column( name = "MateType", nullable = false, length = 60 )
    public String getMateType() {
        return this.mateType;
    }

    public void setMateType( String mateType ) {
        this.mateType = mateType;
    }

    @Column( name = "costItemId", length = 40 )
    public String getCostItemId() {
        return this.costItemId;
    }

    public void setCostItemId( String costItemId ) {
        this.costItemId = costItemId;
    }

    @Column( name = "costitem", length = 40 )
    public String getCostitem() {
        return this.costitem;
    }

    public void setCostitem( String costitem ) {
        this.costitem = costitem;
    }

    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Matetype pojo = (Matetype) o;

        if ( mateMapId != null ? !mateMapId.equals( pojo.mateMapId ) : pojo.mateMapId != null )
            return false;
        if ( mateType != null ? !mateType.equals( pojo.mateType ) : pojo.mateType != null )
            return false;
        if ( mateTypeId != null ? !mateTypeId.equals( pojo.mateTypeId ) : pojo.mateTypeId != null )
            return false;
        if ( costItemId != null ? !costItemId.equals( pojo.costItemId ) : pojo.costItemId != null )
            return false;
        if ( costitem != null ? !costitem.equals( pojo.costitem ) : pojo.costitem != null )
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = ( mateType != null ? mateType.hashCode() : 0 );
        result = 31 * result + ( costItemId != null ? costItemId.hashCode() : 0 );
        result = 31 * result + ( costitem != null ? costitem.hashCode() : 0 );

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "mateMapId" ).append( "='" ).append( getMateMapId() ).append( "', " );
        sb.append( "mateTypeId" ).append( "='" ).append( getMateTypeId() ).append( "', " );
        sb.append( "mateType" ).append( "='" ).append( getMateType() ).append( "', " );
        sb.append( "costItemId" ).append( "='" ).append( getCostItemId() ).append( "', " );
        sb.append( "costitem" ).append( "='" ).append( getCostitem() ).append( "'" );
        sb.append( "]" );

        return sb.toString();
    }

}
