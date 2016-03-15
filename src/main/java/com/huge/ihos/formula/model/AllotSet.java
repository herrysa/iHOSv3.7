package com.huge.ihos.formula.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_allotSet" )
public class AllotSet
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5394430797553587806L;

    String allotSetId;

    String allotSetName;

    String remark;

    boolean disabled;

    @Id
    //30
    @Column( length = 30 )
    public String getAllotSetId() {
        return allotSetId;
    }

    public void setAllotSetId( String allotSetId ) {
        this.allotSetId = allotSetId;
    }

    @Column( name = "allotSetName", nullable = false, length = 100 )
    public String getAllotSetName() {
        return allotSetName;
    }

    public void setAllotSetName( String allotSetName ) {
        this.allotSetName = allotSetName;
    }

    @Column( name = "remark", nullable = true, length = 200 )
    public String getRemark() {
        return remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @Column( name = "disabled", nullable = false )
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( allotSetId == null ) ? 0 : allotSetId.hashCode() );
        result = prime * result + ( ( allotSetName == null ) ? 0 : allotSetName.hashCode() );
        result = prime * result + ( disabled ? 1231 : 1237 );
        result = prime * result + ( ( remark == null ) ? 0 : remark.hashCode() );
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
        AllotSet other = (AllotSet) obj;
        if ( allotSetId == null ) {
            if ( other.allotSetId != null )
                return false;
        }
        else if ( !allotSetId.equals( other.allotSetId ) )
            return false;
        if ( allotSetName == null ) {
            if ( other.allotSetName != null )
                return false;
        }
        else if ( !allotSetName.equals( other.allotSetName ) )
            return false;
        if ( disabled != other.disabled )
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
    public String toString() {
        return "AllotSet [allotSetId=" + allotSetId + ", allotSetName=" + allotSetName + ", remark=" + remark + ", disabled=" + disabled + "]";
    }

}
