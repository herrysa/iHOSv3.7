package com.huge.ihos.period.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_period" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class Period
    extends BaseObject
    implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4255084857477706032L;

    private Long periodId;

    private String periodCode;

    private String cyear;

    private String cmonth;

    private Date startDate;

    private Date endDate;

    private Boolean cpFlag = false;

    private Boolean cdpFlag = false;

    private String note;

    @Column( name = "cp_flag", nullable = false )
    public Boolean getCpFlag() {
        return cpFlag;
    }

    public void setCpFlag( Boolean cpFlag ) {
        this.cpFlag = cpFlag;
    }

    @Column( name = "cdp_flag", nullable = false )
    public Boolean getCdpFlag() {
        return cdpFlag;
    }

    public void setCdpFlag( Boolean cdpFlag ) {
        this.cdpFlag = cdpFlag;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId( Long periodId ) {
        this.periodId = periodId;
    }

    @Column( name = "periodCode", unique = true, nullable = false, length = 6 )
    public String getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode( String periodCode ) {
        this.periodCode = periodCode;
    }

    // TODO @Column(name="startDate", unique=true,nullable=false)
    @Column( name = "startDate", nullable = false )
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate( Date startDate ) {
        this.startDate = startDate;
    }

    // TODO @Column(name="endDate", unique=true,nullable=false)
    @Column( name = "endDate", nullable = false )
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate( Date endDate ) {
        this.endDate = endDate;
    }

    @Column( name = "cyear", unique = false, nullable = false, length = 4 )
    public String getCyear() {
        return cyear;
    }

    public void setCyear( String cyear ) {
        this.cyear = cyear;
    }

    @Column( name = "cmonth", unique = false, nullable = false, length = 2 )
    public String getCmonth() {
        return cmonth;
    }

    public void setCmonth( String cmonth ) {
        this.cmonth = cmonth;
    }

    @Column( name = "note", unique = false, nullable = true, length = 200 )
    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( cdpFlag == null ) ? 0 : cdpFlag.hashCode() );
        result = prime * result + ( ( cmonth == null ) ? 0 : cmonth.hashCode() );
        result = prime * result + ( ( cpFlag == null ) ? 0 : cpFlag.hashCode() );
        result = prime * result + ( ( cyear == null ) ? 0 : cyear.hashCode() );
        result = prime * result + ( ( endDate == null ) ? 0 : endDate.hashCode() );
        result = prime * result + ( ( note == null ) ? 0 : note.hashCode() );
        result = prime * result + ( ( periodCode == null ) ? 0 : periodCode.hashCode() );
        result = prime * result + ( ( periodId == null ) ? 0 : periodId.hashCode() );
        result = prime * result + ( ( startDate == null ) ? 0 : startDate.hashCode() );
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
        Period other = (Period) obj;
        if ( cdpFlag == null ) {
            if ( other.cdpFlag != null )
                return false;
        }
        else if ( !cdpFlag.equals( other.cdpFlag ) )
            return false;
        if ( cmonth == null ) {
            if ( other.cmonth != null )
                return false;
        }
        else if ( !cmonth.equals( other.cmonth ) )
            return false;
        if ( cpFlag == null ) {
            if ( other.cpFlag != null )
                return false;
        }
        else if ( !cpFlag.equals( other.cpFlag ) )
            return false;

        if ( cyear == null ) {
            if ( other.cyear != null )
                return false;
        }
        else if ( !cyear.equals( other.cyear ) )
            return false;
        if ( endDate == null ) {
            if ( other.endDate != null )
                return false;
        }
        else if ( !endDate.equals( other.endDate ) )
            return false;
        if ( note == null ) {
            if ( other.note != null )
                return false;
        }
        else if ( !note.equals( other.note ) )
            return false;
        if ( periodCode == null ) {
            if ( other.periodCode != null )
                return false;
        }
        else if ( !periodCode.equals( other.periodCode ) )
            return false;
        if ( periodId == null ) {
            if ( other.periodId != null )
                return false;
        }
        else if ( !periodId.equals( other.periodId ) )
            return false;
        if ( startDate == null ) {
            if ( other.startDate != null )
                return false;
        }
        else if ( !startDate.equals( other.startDate ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Period [periodId=" + periodId + ", periodCode=" + periodCode + ", startDate=" + startDate + ", endDate=" + endDate + ", cyear="
            + cyear + ", cmonth=" + cmonth + ", cpFlag=" + cpFlag + ", cdpClodedFlag=" + cdpFlag + ", note=" + note + "]";
    }

}
