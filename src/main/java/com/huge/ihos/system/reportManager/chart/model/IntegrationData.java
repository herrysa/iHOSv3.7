package com.huge.ihos.system.reportManager.chart.model;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * 此类专为饼图整理显示数据
 * @author zhangMing
 *
 */
public class IntegrationData
    implements Comparator {

    private Object dataValue;

    private String dataStringValue;

    private Object dataName;

    private Object percent;

    @Override
    public int compare( Object arg0, Object arg1 ) {
        IntegrationData c1 = (IntegrationData) arg0;
        IntegrationData c2 = (IntegrationData) arg1;
        if ( ( (BigDecimal) c1.getDataValue() ).intValue() < ( (BigDecimal) c2.getDataValue() ).intValue() ) {
            return ( (BigDecimal) c1.getDataValue() ).intValue();
        }
        else {
            return 0;
        }
    }

    public Object getDataValue() {
        return dataValue;
    }

    public void setDataValue( Object dataValue ) {
        this.dataValue = dataValue;
    }

    public Object getDataName() {
        return dataName;
    }

    public void setDataName( Object dataName ) {
        this.dataName = dataName;
    }

    public Object getPercent() {
        return percent;
    }

    public void setPercent( Object percent ) {
        this.percent = percent;
    }

    public String getDataStringValue() {
        return dataStringValue;
    }

    public void setDataStringValue( String dataStringValue ) {
        this.dataStringValue = dataStringValue;
    }

}
