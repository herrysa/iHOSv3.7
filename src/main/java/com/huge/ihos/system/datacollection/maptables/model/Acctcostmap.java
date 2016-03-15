package com.huge.ihos.system.datacollection.maptables.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_acctcostmap" )
public class Acctcostmap
    extends BaseObject
    implements Serializable {
    private Long acctMapId;

    private String acctcode;

    private String acctname;

    private String costitemid;

    private String costitem;

    @Id
    @GeneratedValue
    public Long getAcctMapId() {
        return acctMapId;
    }

    public void setAcctMapId( Long acctMapId ) {
        this.acctMapId = acctMapId;
    }

    @Column( name = "acctcode", length = 100 )
    public String getAcctcode() {
        return this.acctcode;
    }

    public void setAcctcode( String acctcode ) {
        this.acctcode = acctcode;
    }

    @Column( name = "acctname", length = 100 )
    public String getAcctname() {
        return this.acctname;
    }

    public void setAcctname( String acctname ) {
        this.acctname = acctname;
    }

    @Column( name = "costitemid", length = 50 )
    public String getCostitemid() {
        return this.costitemid;
    }

    public void setCostitemid( String costitemid ) {
        this.costitemid = costitemid;
    }

    @Column( name = "costitem", length = 100 )
    public String getCostitem() {
        return this.costitem;
    }

    public void setCostitem( String costitem ) {
        this.costitem = costitem;
    }

    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( !( o instanceof Acctcostmap ) )
            return false;

        Acctcostmap pojo = (Acctcostmap) o;

        if ( this.getAcctMapId() == null ) {
            if ( pojo.getAcctMapId() != null )
                return false;
        }
        else if ( !this.getAcctMapId().equals( pojo.getAcctMapId() ) )
            return false;

        return true;

    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( this.getAcctMapId() == null ) ? 0 : this.getAcctMapId().hashCode() );
        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "acctMapId" ).append( "='" ).append( getAcctMapId() ).append( "', " );
        sb.append( "acctcode" ).append( "='" ).append( getAcctcode() ).append( "', " );
        sb.append( "acctname" ).append( "='" ).append( getAcctname() ).append( "', " );
        sb.append( "costitemid" ).append( "='" ).append( getCostitemid() ).append( "', " );
        sb.append( "costitem" ).append( "='" ).append( getCostitem() ).append( "'" );
        sb.append( "]" );

        return sb.toString();
    }

}
