package com.huge.ihos.system.reportManager.chart.model;

import java.math.BigDecimal;
import java.util.Comparator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table( name = "T_MccKeyDetail" )
public class MccKeyDetail
    implements Comparator {
    /* 表盘明细ID */
    private String mccKeyDetailId;

    /* 显示顺序 */
    private int dispNo;

    /* 区域最小值 */
    private BigDecimal minValue;

    /* 区域最大值 */
    private BigDecimal maxValue;

    /* 状态 */
    private String state;

    /* 主表ID */
    private String mccKeyId;

    /* 区域颜色 */
    private String color;

    /* 报警 */
    private boolean warning = false;

    private MccKey mccKey;

    @Id
    @Column( name = "mccKeyDetailId", length = 10 )
    public String getMccKeyDetailId() {
        return mccKeyDetailId;
    }

    public void setMccKeyDetailId( String mccKeyDetailId ) {
        this.mccKeyDetailId = mccKeyDetailId;
    }

    @Column( name = "dispNo", length = 3 )
    public int getDispNo() {
        return dispNo;
    }

    public void setDispNo( int dispNo ) {
        this.dispNo = dispNo;
    }

    @Column( name = "min_Value", length = 7 )
    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue( BigDecimal minValue ) {
        this.minValue = minValue;
    }

    @Column( name = "max_Value", length = 7 )
    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue( BigDecimal maxValue ) {
        this.maxValue = maxValue;
    }

    @Column( name = "state", length = 20 )
    public String getState() {
        return state;
    }

    public void setState( String state ) {
        this.state = state;
    }

    @Column( name = "mccKeyId", length = 10 )
    public String getMccKeyId() {
        return mccKeyId;
    }

    public void setMccKeyId( String mccKeyId ) {
        this.mccKeyId = mccKeyId;
    }

    @Override
    public int compare( Object arg0, Object arg1 ) {
        MccKeyDetail c1 = (MccKeyDetail) arg0;
        MccKeyDetail c2 = (MccKeyDetail) arg1;
        if ( c1.getMinValue().intValue() > c2.getMinValue().intValue() ) {
            return c1.getMinValue().intValue();
        }
        else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( maxValue == null ) ? 0 : maxValue.hashCode() );
        /*result = prime * result + ((mccKey == null) ? 0 : mccKey.hashCode());*/
        result = prime * result + ( ( mccKeyDetailId == null ) ? 0 : mccKeyDetailId.hashCode() );
        result = prime * result + ( ( minValue == null ) ? 0 : minValue.hashCode() );
        result = prime * result + ( ( state == null ) ? 0 : state.hashCode() );
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
        MccKeyDetail other = (MccKeyDetail) obj;
        if ( maxValue == null ) {
            if ( other.maxValue != null )
                return false;
        }
        else if ( !maxValue.equals( other.maxValue ) )
            return false;
        if ( mccKey == null ) {
            if ( other.mccKey != null )
                return false;
        }
        else if ( !mccKey.equals( other.mccKey ) )
            return false;
        if ( mccKeyDetailId == null ) {
            if ( other.mccKeyDetailId != null )
                return false;
        }
        else if ( !mccKeyDetailId.equals( other.mccKeyDetailId ) )
            return false;
        if ( minValue == null ) {
            if ( other.minValue != null )
                return false;
        }
        else if ( !minValue.equals( other.minValue ) )
            return false;
        if ( state == null ) {
            if ( other.state != null )
                return false;
        }
        else if ( !state.equals( other.state ) )
            return false;
        return true;
    }

    @JSON( serialize = true )
    @ManyToOne( cascade = CascadeType.REFRESH, fetch = FetchType.LAZY )
    @JoinColumn( name = "mccKeyId", insertable = false, updatable = false )
    public MccKey getMccKey() {
        return mccKey;
    }

    public void setMccKey( MccKey mccKey ) {
        this.mccKey = mccKey;
    }

    @Column( name = "color", length = 7 )
    public String getColor() {
        return color;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    public boolean getWarning() {
        return warning;
    }

    public void setWarning( boolean warning ) {
        this.warning = warning;
    }

}
