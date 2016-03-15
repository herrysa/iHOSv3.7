package com.huge.ihos.formula.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_allotPrinciple" )
public class AllotPrinciple
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2669767824451057309L;

    private String allotPrincipleId;

    private String allotPrincipleName;

    private boolean disabled;

    private boolean paramed;

    private String param1 = "";

    @Id
    //30
    @Column( length = 30 )
    public String getAllotPrincipleId() {
        return allotPrincipleId;
    }

    public void setAllotPrincipleId( String allotPrincipleId ) {
        this.allotPrincipleId = allotPrincipleId;
    }

    @Column( name = "allotPrincipleName", nullable = false, length = 100 )
    public String getAllotPrincipleName() {
        return allotPrincipleName;
    }

    public void setAllotPrincipleName( String allotPrincipleName ) {
        this.allotPrincipleName = allotPrincipleName;
    }

    @Column( name = "disabled", nullable = false )
    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }

    @Column( name = "paramed", nullable = false )
    public boolean isParamed() {
        return this.paramed;
    }

    public void setParamed( boolean paramed ) {
        this.paramed = paramed;
    }

    @Column( name = "param1", length = 100, nullable = false )
    public String getParam1() {
        return this.param1;
    }

    public void setParam1( String param1 ) {
        this.param1 = param1;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        AllotPrinciple other = (AllotPrinciple) obj;
        if ( allotPrincipleId == null ) {
            if ( other.allotPrincipleId != null )
                return false;
        }
        else if ( !allotPrincipleId.equals( other.allotPrincipleId ) )
            return false;
        if ( allotPrincipleName == null ) {
            if ( other.allotPrincipleName != null )
                return false;
        }
        else if ( !allotPrincipleName.equals( other.allotPrincipleName ) )
            return false;
        if ( disabled != other.disabled )
            return false;
        if ( param1 == null ) {
            if ( other.param1 != null )
                return false;
        }
        else if ( !param1.equals( other.param1 ) )
            return false;
        if ( paramed != other.paramed )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( allotPrincipleId == null ) ? 0 : allotPrincipleId.hashCode() );
        result = prime * result + ( ( allotPrincipleName == null ) ? 0 : allotPrincipleName.hashCode() );
        result = prime * result + ( disabled ? 1231 : 1237 );
        result = prime * result + ( ( param1 == null ) ? 0 : param1.hashCode() );
        result = prime * result + ( paramed ? 1231 : 1237 );
        return result;
    }

    @Override
    public String toString() {
        return "AllotPrinciple [allotPrincipleId=" + allotPrincipleId + ", allotPrincipleName=" + allotPrincipleName + ", disabled=" + disabled
            + ", paramed=" + paramed + ", param1=" + param1 + "]";
    }

}
