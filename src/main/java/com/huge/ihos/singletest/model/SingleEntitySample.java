package com.huge.ihos.singletest.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "singleEntitySample" )
public class SingleEntitySample
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5661301529292968023L;

    private Long pkid;

    private String stringField;

    private Integer intField;

    private Double doubleField;

    private Float floatField;

    private BigDecimal bigDecimalField;

    private Date dateField;

    private Boolean booleanField;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getPkid() {
        return pkid;
    }

    public void setPkid( Long pkid ) {
        this.pkid = pkid;
    }

    @Column( nullable = false, length = 50, unique = false )
    public String getStringField() {
        return stringField;
    }

    public void setStringField( String stringField ) {
        this.stringField = stringField;
    }

    @Column( nullable = false )
    public Integer getIntField() {
        return intField;
    }

    public void setIntField( Integer intField ) {
        this.intField = intField;
    }

    @Column( nullable = false )
    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField( Double doubleField ) {
        this.doubleField = doubleField;
    }

    @Column( nullable = false )
    public Float getFloatField() {
        return floatField;
    }

    public void setFloatField( Float floatField ) {
        this.floatField = floatField;
    }

    @Column( nullable = false )
    public BigDecimal getBigDecimalField() {
        return bigDecimalField;
    }

    public void setBigDecimalField( BigDecimal bigDecimalField ) {
        this.bigDecimalField = bigDecimalField;
    }

    @Column( nullable = false )
    public Date getDateField() {
        return dateField;
    }

    public void setDateField( Date dateField ) {
        this.dateField = dateField;
    }

    @Column( nullable = false )
    public Boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField( Boolean booleanField ) {
        this.booleanField = booleanField;
    }

    @Override
    public String toString() {
        return "SingleEntitySample [pkid=" + pkid + ", stringField=" + stringField + ", intField=" + intField + ", doubleField=" + doubleField
            + ", floatField=" + floatField + ", bigDecimalField=" + bigDecimalField + ", dateField=" + dateField + ", booleanField=" + booleanField
            + "]";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        SingleEntitySample other = (SingleEntitySample) obj;
        if ( bigDecimalField == null ) {
            if ( other.bigDecimalField != null )
                return false;
        }
        else if ( !bigDecimalField.equals( other.bigDecimalField ) )
            return false;
        if ( booleanField == null ) {
            if ( other.booleanField != null )
                return false;
        }
        else if ( !booleanField.equals( other.booleanField ) )
            return false;
        if ( dateField == null ) {
            if ( other.dateField != null )
                return false;
        }
        else if ( !dateField.equals( other.dateField ) )
            return false;
        if ( doubleField == null ) {
            if ( other.doubleField != null )
                return false;
        }
        else if ( !doubleField.equals( other.doubleField ) )
            return false;
        if ( floatField == null ) {
            if ( other.floatField != null )
                return false;
        }
        else if ( !floatField.equals( other.floatField ) )
            return false;
        if ( intField == null ) {
            if ( other.intField != null )
                return false;
        }
        else if ( !intField.equals( other.intField ) )
            return false;
        if ( pkid == null ) {
            if ( other.pkid != null )
                return false;
        }
        else if ( !pkid.equals( other.pkid ) )
            return false;
        if ( stringField == null ) {
            if ( other.stringField != null )
                return false;
        }
        else if ( !stringField.equals( other.stringField ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( bigDecimalField == null ) ? 0 : bigDecimalField.hashCode() );
        result = prime * result + ( ( booleanField == null ) ? 0 : booleanField.hashCode() );
        result = prime * result + ( ( dateField == null ) ? 0 : dateField.hashCode() );
        result = prime * result + ( ( doubleField == null ) ? 0 : doubleField.hashCode() );
        result = prime * result + ( ( floatField == null ) ? 0 : floatField.hashCode() );
        result = prime * result + ( ( intField == null ) ? 0 : intField.hashCode() );
        result = prime * result + ( ( pkid == null ) ? 0 : pkid.hashCode() );
        result = prime * result + ( ( stringField == null ) ? 0 : stringField.hashCode() );
        return result;
    }

}
