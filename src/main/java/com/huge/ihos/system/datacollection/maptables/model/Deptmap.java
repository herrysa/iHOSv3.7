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
@Table( name = "t_deptmap" )
public class Deptmap
    extends BaseObject
    implements Serializable {
    private Long deptMapId;

    private String marktype;

    private String sourcecode;

    private String sourcename;

    private String targetcode;

    private String targetname;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getDeptMapId() {
        return deptMapId;
    }

    public void setDeptMapId( Long deptMapId ) {
        this.deptMapId = deptMapId;
    }

    @Column( name = "marktype", nullable = false, length = 60 )
    public String getMarktype() {
        return this.marktype;
    }

    public void setMarktype( String marktype ) {
        this.marktype = marktype;
    }

    @Column( name = "sourcecode", nullable = false, length = 50 )
    public String getSourcecode() {
        return this.sourcecode;
    }

    public void setSourcecode( String sourcecode ) {
        this.sourcecode = sourcecode;
    }

    @Column( name = "sourcename", nullable = false, length = 100 )
    public String getSourcename() {
        return this.sourcename;
    }

    public void setSourcename( String sourcename ) {
        this.sourcename = sourcename;
    }

    @Column( name = "targetcode", length = 50 )
    public String getTargetcode() {
        return this.targetcode;
    }

    public void setTargetcode( String targetcode ) {
        this.targetcode = targetcode;
    }

    @Column( name = "targetname", length = 100 )
    public String getTargetname() {
        return this.targetname;
    }

    public void setTargetname( String targetname ) {
        this.targetname = targetname;
    }

    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Deptmap pojo = (Deptmap) o;

        if ( marktype != null ? !marktype.equals( pojo.marktype ) : pojo.marktype != null )
            return false;
        if ( sourcecode != null ? !sourcecode.equals( pojo.sourcecode ) : pojo.sourcecode != null )
            return false;
        if ( sourcename != null ? !sourcename.equals( pojo.sourcename ) : pojo.sourcename != null )
            return false;
        if ( targetcode != null ? !targetcode.equals( pojo.targetcode ) : pojo.targetcode != null )
            return false;
        if ( targetname != null ? !targetname.equals( pojo.targetname ) : pojo.targetname != null )
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = ( marktype != null ? marktype.hashCode() : 0 );
        result = 31 * result + ( sourcecode != null ? sourcecode.hashCode() : 0 );
        result = 31 * result + ( sourcename != null ? sourcename.hashCode() : 0 );
        result = 31 * result + ( targetcode != null ? targetcode.hashCode() : 0 );
        result = 31 * result + ( targetname != null ? targetname.hashCode() : 0 );

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "deptMapId" ).append( "='" ).append( getDeptMapId() ).append( "', " );
        sb.append( "marktype" ).append( "='" ).append( getMarktype() ).append( "', " );
        sb.append( "sourcecode" ).append( "='" ).append( getSourcecode() ).append( "', " );
        sb.append( "sourcename" ).append( "='" ).append( getSourcename() ).append( "', " );
        sb.append( "targetcode" ).append( "='" ).append( getTargetcode() ).append( "', " );
        sb.append( "targetname" ).append( "='" ).append( getTargetname() ).append( "'" );
        sb.append( "]" );

        return sb.toString();
    }

}
