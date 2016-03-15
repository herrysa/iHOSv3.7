package com.huge.ihos.system.reportManager.chart.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "T_MccKey" )
public class MccKey {
    /* 图形编号 */
    private String mccKeyId;

    /* 图形编号 */
    private String ckey;

    /* 主显示字段 */
    private String keyField;

    /* 图形类型 */
    private String chartType;

    /* 是否显示3D图 */
    private boolean c3d = false;

    /* 图形外 标签*/
    private String label;

    /* sql语句 */
    private String mysql;

    /* 标题 */
    private String caption;

    /* 副标题 */
    private String subCaption;

    /* 设置是否在柱型图或饼型图上显示数据的值1/0 默认不显示 */
    private boolean showValues = false;

    /* 前缀 */
    private String numberPrefix;

    /* 后缀 */
    private String numberSuffix;

    /* 设置是否格式化数据 */
    private boolean formatNumber = false;

    /* 设置是否用“K”来代表千，“M”来代表百万 */
    private boolean formatNumberScale = false;

    /* 用指定的字符来代替千位分隔符 */
    private String thousandSeparator;

    /*  X轴的名字 */
    private String xAxisName;

    /* y轴的名字 */
    private String yAxisName;

    /* 设置字体样式 */
    private String baseFont;

    /* 设置字体大小 */
    private String baseFontSize = "12";

    /* 设置字体颜色 */
    private String baseFontColor;

    /* 设置十进制的精度 */
    private String decimalPrecision;

    /* 整数 x轴坐标显示间隔数，默认1所有坐标都显示 */
    private String labelStep;

    /* 设置y轴数值的小数位数 */
    private String pieRadius;

    /* 图背景颜色 */
    private String bgColor;

    /**
     * 饼图专有
     */

    /* 超过设定值颜色(饼图专用) */
    private String color;

    /* 是否显示百分比(饼图专用) */
    private boolean showPercentValues = false;

    /* 设置坎值(饼图专用)-大于这个百分比的才可以显示 */
    private double kanZhi = 0;

    /* 设定值 */
    private double sheDingZhi = 0;

    /* 显示数值 */
    private boolean disShowValue = false;

    /**
     * 折线柱状图专有
     */
    /* 折线条数 */
    private int zheXianCount = 1;

    /* 折线上显示的值字大小 */
    private String zxZhiBaseFontSize = "8";

    /* 背景透明度 */
    private String bgAlpha = "10,10";

    /* 非图形数据内部的图内其他标题的字体大小 */
    private String outCnvbaseFontSize = "12";

    /* 折线线的粗细 */
    private String lineThickness = "2";

    /* 折线上节点的大小 */
    private String anchorRadius = "4";

    /* 设置水平分隔线的透明度 */
    private String divLineAlpha = "100";

    /* 设置水平分隔线的颜色 */
    private String divLineColor = "CC3300";

    /* 纵向分割线条数*/
    private String numvdivlines = "8";

    /* 设置水平分隔线是否是虚线 */
    private boolean divLineIsDashed = false;

    /* 是否将背景色显示在图形数据内部 */
    private boolean showAlternateHGridColor = true;

    /* 图形数据内部底色透明度 */
    private String alternateHGridAlpha = "30";

    /* 背景色(渐变时使用) */
    private String bgColor2;

    /* Y轴名称是否显示 */
    private boolean rotateYAxisName = false;

    /* Y轴外围宽度 */
    private String yAxisNameWidth = "20";

    /* X轴名称是否垂直显示 */
    private boolean rotateNames = false;

    /* 设置图表左边距 */
    private String chartLeftMargin = "25";

    /* 设置图表右边距 */
    private String chartRightMargin = "50";

    /**
     * 雷达图专有
     */
    /* 设置雷达图填充区域浓度 */
    private String plotFillAlpha="5";
    /* 设置雷达图线粗细 */
    private String plotBorderThickness="2";
    /* 雷达图区域线条数 */
    private String numDivLines="3";
    /* 标注显示位置 */
    private String legendPosition="left";
    /**
     * 表盘专有
     */
    /* 表盘半径 */
    private String gaugeOuterRadius;

    /* 表针半径 */
    private String radius;

    /* 表针宽度 */
    private String baseWidth;

    /* 值与表盘外围距离 */
    private String displayValueDistance;

    /* 表针根部圆心半径 */
    private String pivotRadius;

    /* 表盘坐标X */
    private String gaugeOriginX;

    /* 表盘坐标Y */
    private String gaugeOriginY;


    @Id
    @Column( name = "mccKeyId", length = 10 )
    public String getMccKeyId() {
        return mccKeyId;
    }

    public void setMccKeyId( String mccKeyId ) {
        this.mccKeyId = mccKeyId;
    }

    @Column( name = "ckey", length = 20 )
    public String getCkey() {
        return ckey;
    }

    public void setCkey( String ckey ) {
        this.ckey = ckey;
    }

    @Column( name = "keyField", length = 128 )
    public String getKeyField() {
        return keyField;
    }

    public void setKeyField( String keyField ) {
        this.keyField = keyField;
    }

    @Column( name = "chartType", length = 10 )
    public String getChartType() {
        return chartType;
    }

    public void setChartType( String chartType ) {
        this.chartType = chartType;
    }

    @Column( name = "c3d" )
    public boolean getC3d() {
        return c3d;
    }

    public void setC3d( boolean c3d ) {
        this.c3d = c3d;
    }

    @Column( name = "label", length = 100 )
    public String getLabel() {
        return label;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    @Column( name = "mysql", length = 1024 )
    public String getMysql() {
        return mysql;
    }

    public void setMysql( String mysql ) {
        this.mysql = mysql;
    }

    @Column( name = "caption", length = 30 )
    public String getCaption() {
        return caption;
    }

    public void setCaption( String caption ) {
        this.caption = caption;
    }

    @Column( name = "subCaption", length = 30 )
    public String getSubCaption() {
        return subCaption;
    }

    public void setSubCaption( String subCaption ) {
        this.subCaption = subCaption;
    }

    @Column( name = "showValues" )
    public boolean getShowValues() {
        return showValues;
    }

    public void setShowValues( boolean showValues ) {
        this.showValues = showValues;
    }

    @Column( name = "numberPrefix", length = 2 )
    public String getNumberPrefix() {
        return numberPrefix;
    }

    public void setNumberPrefix( String numberPrefix ) {
        this.numberPrefix = numberPrefix;
    }

    @Column( name = "numberSuffix", length = 2 )
    public String getNumberSuffix() {
        return numberSuffix;
    }

    public void setNumberSuffix( String numberSuffix ) {
        this.numberSuffix = numberSuffix;
    }

    @Column( name = "formatNumber" )
    public boolean getFormatNumber() {
        return formatNumber;
    }

    public void setFormatNumber( boolean formatNumber ) {
        this.formatNumber = formatNumber;
    }

    @Column( name = "formatNumberScale" )
    public boolean getFormatNumberScale() {
        return formatNumberScale;
    }

    public void setFormatNumberScale( boolean formatNumberScale ) {
        this.formatNumberScale = formatNumberScale;
    }

    @Column( name = "thousandSeparator", length = 2 )
    public String getThousandSeparator() {
        return thousandSeparator;
    }

    public void setThousandSeparator( String thousandSeparator ) {
        this.thousandSeparator = thousandSeparator;
    }

    @Column( name = "xAxisName", length = 30 )
    public String getxAxisName() {
        return xAxisName;
    }

    public void setxAxisName( String xAxisName ) {
        this.xAxisName = xAxisName;
    }

    @Column( name = "yAxisName", length = 30 )
    public String getYAxisName() {
        return yAxisName;
    }

    public void setYAxisName( String yAxisName ) {
        this.yAxisName = yAxisName;
    }

    @Column( name = "labelStep", length = 3 )
    public String getLabelStep() {
        return labelStep;
    }

    public void setLabelStep( String labelStep ) {
        this.labelStep = labelStep;
    }

    @Column( name = "baseFont", length = 10 )
    public String getBaseFont() {
        return baseFont;
    }

    public void setBaseFont( String baseFont ) {
        this.baseFont = baseFont;
    }

    @Column( name = "baseFontSize", length = 3 )
    public String getBaseFontSize() {
        return baseFontSize;
    }

    public void setBaseFontSize( String baseFontSize ) {
        this.baseFontSize = baseFontSize;
    }

    @Column( name = "baseFontColor", length = 7 )
    public String getBaseFontColor() {
        return baseFontColor;
    }

    public void setBaseFontColor( String baseFontColor ) {
        this.baseFontColor = baseFontColor;
    }

    @Column( name = "decimalPrecision", length = 5 )
    public String getDecimalPrecision() {
        return decimalPrecision;
    }

    public void setDecimalPrecision( String decimalPrecision ) {
        this.decimalPrecision = decimalPrecision;
    }

    @Column( name = "pieRadius", length = 5 )
    public String getPieRadius() {
        return pieRadius;
    }

    public void setPieRadius( String pieRadius ) {
        this.pieRadius = pieRadius;
    }

    @Column( name = "bgColor", length = 7 )
    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor( String bgColor ) {
        this.bgColor = bgColor;
    }

    @Column( name = "color", length = 7 )
    public String getColor() {
        return color;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    @Column( name = "cZhi" )
    public double getSheDingZhi() {
        return sheDingZhi;
    }

    public void setSheDingZhi( double cZhi ) {
        this.sheDingZhi = cZhi;
    }

    @Column( name = "showPercentValues" )
    public boolean getShowPercentValues() {
        return showPercentValues;
    }

    public void setShowPercentValues( boolean showPercentValues ) {
        this.showPercentValues = showPercentValues;
    }

    @Column( name = "kanZhi" )
    public double getKanZhi() {
        return kanZhi;
    }

    public void setKanZhi( double kanZhi ) {
        this.kanZhi = kanZhi;
    }

	@Column( name = "zheXianCount" )
    public int getZheXianCount() {
        return zheXianCount;
    }

    public void setZheXianCount( int zheXianCount ) {
        this.zheXianCount = zheXianCount;
    }

    @Column( name = "zxZhiBaseFontSize", length = 3 )
    public String getZxZhiBaseFontSize() {
        return zxZhiBaseFontSize;
    }

    public void setZxZhiBaseFontSize( String zxZhiBaseFontSize ) {
        this.zxZhiBaseFontSize = zxZhiBaseFontSize;
    }

    @Column( name = "bgAlpha", length = 7 )
    public String getBgAlpha() {
        return bgAlpha;
    }

    public void setBgAlpha( String bgAlpha ) {
        this.bgAlpha = bgAlpha;
    }

    @Column( name = "outCnvbaseFontSize", length = 3 )
    public String getOutCnvbaseFontSize() {
        return outCnvbaseFontSize;
    }

    public void setOutCnvbaseFontSize( String outCnvbaseFontSize ) {
        this.outCnvbaseFontSize = outCnvbaseFontSize;
    }

    @Column( name = "lineThickness", length = 3 )
    public String getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness( String lineThickness ) {
        this.lineThickness = lineThickness;
    }

    @Column( name = "anchorRadius", length = 3 )
    public String getAnchorRadius() {
        return anchorRadius;
    }

    public void setAnchorRadius( String anchorRadius ) {
        this.anchorRadius = anchorRadius;
    }

    @Column( name = "divLineAlpha", length = 3 )
    public String getDivLineAlpha() {
        return divLineAlpha;
    }

    public void setDivLineAlpha( String divLineAlpha ) {
        this.divLineAlpha = divLineAlpha;
    }

    @Column( name = "divLineColor", length = 7 )
    public String getDivLineColor() {
        return divLineColor;
    }

    public void setDivLineColor( String divLineColor ) {
        this.divLineColor = divLineColor;
    }

    @Column( name = "numvdivlines", length = 3 )
    public String getNumvdivlines() {
        return numvdivlines;
    }

    public void setNumvdivlines( String numvdivlines ) {
        this.numvdivlines = numvdivlines;
    }

    @Column( name = "divLineIsDashed" )
    public boolean getDivLineIsDashed() {
        return divLineIsDashed;
    }

    public void setDivLineIsDashed( boolean divLineIsDashed ) {
        this.divLineIsDashed = divLineIsDashed;
    }

    @Column( name = "showAlternateHGridColor" )
    public boolean getShowAlternateHGridColor() {
        return showAlternateHGridColor;
    }

    public void setShowAlternateHGridColor( boolean showAlternateHGridColor ) {
        this.showAlternateHGridColor = showAlternateHGridColor;
    }

    @Column( name = "alternateHGridAlpha", length = 3 )
    public String getAlternateHGridAlpha() {
        return alternateHGridAlpha;
    }

    public void setAlternateHGridAlpha( String alternateHGridAlpha ) {
        this.alternateHGridAlpha = alternateHGridAlpha;
    }

    @Column( name = "bgColor2", length = 7 )
    public String getBgColor2() {
        return bgColor2;
    }

    public void setBgColor2( String bgColor2 ) {
        this.bgColor2 = bgColor2;
    }

    @Column( name = "rotateYAxisName" )
    public boolean getRotateYAxisName() {
        return rotateYAxisName;
    }

    public void setRotateYAxisName( boolean rotateYAxisName ) {
        this.rotateYAxisName = rotateYAxisName;
    }

    @Column( name = "yAxisNameWidth", length = 30 )
    public String getYAxisNameWidth() {
        return yAxisNameWidth;
    }

    public void setYAxisNameWidth( String yAxisNameWidth ) {
        this.yAxisNameWidth = yAxisNameWidth;
    }

    @Column( name = "gaugeOuterRadius", length = 4 )
    public String getGaugeOuterRadius() {
        return gaugeOuterRadius;
    }

    public void setGaugeOuterRadius( String gaugeOuterRadius ) {
        this.gaugeOuterRadius = gaugeOuterRadius;
    }

    @Column( name = "radius", length = 4 )
    public String getRadius() {
        return radius;
    }

    public void setRadius( String radius ) {
        this.radius = radius;
    }

    @Column( name = "baseWidth", length = 3 )
    public String getBaseWidth() {
        return baseWidth;
    }

    public void setBaseWidth( String baseWidth ) {
        this.baseWidth = baseWidth;
    }

    @Column( name = "displayValueDistance", length = 4 )
    public String getDisplayValueDistance() {
        return displayValueDistance;
    }

    public void setDisplayValueDistance( String displayValueDistance ) {
        this.displayValueDistance = displayValueDistance;
    }

    @Column( name = "pivotRadius", length = 3 )
    public String getPivotRadius() {
        return pivotRadius;
    }

    public void setPivotRadius( String pivotRadius ) {
        this.pivotRadius = pivotRadius;
    }

    @Column( name = "gaugeOriginX", length = 4 )
    public String getGaugeOriginX() {
        return gaugeOriginX;
    }

    public void setGaugeOriginX( String gaugeOriginX ) {
        this.gaugeOriginX = gaugeOriginX;
    }

    @Column( name = "gaugeOriginY", length = 4 )
    public String getGaugeOriginY() {
        return gaugeOriginY;
    }

    public void setGaugeOriginY( String gaugeOriginY ) {
        this.gaugeOriginY = gaugeOriginY;
    }

    @Column( name = "rotateNames" )
    public boolean getRotateNames() {
        return rotateNames;
    }

    public void setRotateNames( boolean rotateNames ) {
        this.rotateNames = rotateNames;
    }

    @Column( name = "chartLeftMargin", length = 3 )
    public String getChartLeftMargin() {
        return chartLeftMargin;
    }

    public void setChartLeftMargin( String chartLeftMargin ) {
        this.chartLeftMargin = chartLeftMargin;
    }

    @Column( name = "chartRightMargin", length = 3 )
    public String getChartRightMargin() {
        return chartRightMargin;
    }

    public void setChartRightMargin( String chartRightMargin ) {
        this.chartRightMargin = chartRightMargin;
    }

    @Column( name = "disValue" )
    public boolean isDisShowValue() {
        return disShowValue;
    }

    public void setDisShowValue( boolean disShowValue ) {
        this.disShowValue = disShowValue;
    }
    @Column( name = "plotFillAlpha", length = 3 )
	public String getPlotFillAlpha() {
		return plotFillAlpha;
	}

	public void setPlotFillAlpha(String plotFillAlpha) {
		this.plotFillAlpha = plotFillAlpha;
	}
	@Column( name = "plotBorderThickness", length = 3 )
	public String getPlotBorderThickness() {
		return plotBorderThickness;
	}

	public void setPlotBorderThickness(String plotBorderThickness) {
		this.plotBorderThickness = plotBorderThickness;
	}

	@Column( name = "numDivLines", length = 3 )
	public String getNumDivLines() {
		return numDivLines;
	}

	public void setNumDivLines(String numDivLines) {
		this.numDivLines = numDivLines;
	}
	@Column( name = "legendPosition", length = 5 )
	public String getLegendPosition() {
		return legendPosition;
	}

	public void setLegendPosition(String legendPosition) {
		this.legendPosition = legendPosition;
	}
	

    @Override
	public String toString() {
		return "MccKey [mccKeyId=" + mccKeyId + ", ckey=" + ckey+ ", keyField=" + keyField + ", chartType=" + chartType+ ", c3d=" + c3d + ", label=" + label + ", mysql=" + mysql+ ", caption=" + caption + ", subCaption=" + subCaption
				+ ", showValues=" + showValues + ", numberPrefix="+ numberPrefix + ", numberSuffix=" + numberSuffix+ ", formatNumber=" + formatNumber + ", formatNumberScale="+ formatNumberScale + ", thousandSeparator="
				+ thousandSeparator + ", xAxisName=" + xAxisName+ ", yAxisName=" + yAxisName + ", baseFont=" + baseFont+ ", baseFontSize=" + baseFontSize + ", baseFontColor="+ baseFontColor + ", decimalPrecision=" + decimalPrecision
				+ ", labelStep=" + labelStep + ", pieRadius=" + pieRadius+ ", bgColor=" + bgColor + ", color=" + color+ ", showPercentValues=" + showPercentValues + ", kanZhi="+ kanZhi + ", sheDingZhi=" + sheDingZhi + ", disShowValue="
				+ disShowValue + ", zheXianCount=" + zheXianCount+ ", zxZhiBaseFontSize=" + zxZhiBaseFontSize + ", bgAlpha="+ bgAlpha + ", outCnvbaseFontSize=" + outCnvbaseFontSize+ ", lineThickness=" + lineThickness + ", anchorRadius="
				+ anchorRadius + ", divLineAlpha=" + divLineAlpha+ ", divLineColor=" + divLineColor + ", numvdivlines="+ numvdivlines + ", divLineIsDashed=" + divLineIsDashed+ ", showAlternateHGridColor=" + showAlternateHGridColor
				+ ", alternateHGridAlpha=" + alternateHGridAlpha+ ", bgColor2=" + bgColor2 + ", rotateYAxisName="+ rotateYAxisName + ", yAxisNameWidth=" + yAxisNameWidth+ ", rotateNames=" + rotateNames + ", chartLeftMargin="
				+ chartLeftMargin + ", chartRightMargin=" + chartRightMargin+ ", plotFillAlpha=" + plotFillAlpha + ", plotBorderThickness="+ plotBorderThickness + ", numDivLines=" + numDivLines+ ", legendPosition=" + legendPosition + ", gaugeOuterRadius="
				+ gaugeOuterRadius + ", radius=" + radius + ", baseWidth="+ baseWidth + ", displayValueDistance=" + displayValueDistance+ ", pivotRadius=" + pivotRadius + ", gaugeOriginX="+ gaugeOriginX + ", gaugeOriginY=" + gaugeOriginY + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((baseFont == null) ? 0 : baseFont.hashCode());
		result = prime * result
				+ ((baseFontColor == null) ? 0 : baseFontColor.hashCode());
		result = prime * result
				+ ((baseWidth == null) ? 0 : baseWidth.hashCode());
		result = prime * result + ((bgColor == null) ? 0 : bgColor.hashCode());
		result = prime * result
				+ ((bgColor2 == null) ? 0 : bgColor2.hashCode());
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result
				+ ((chartType == null) ? 0 : chartType.hashCode());
		result = prime * result + ((ckey == null) ? 0 : ckey.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime
				* result
				+ ((decimalPrecision == null) ? 0 : decimalPrecision.hashCode());
		result = prime
				* result
				+ ((displayValueDistance == null) ? 0 : displayValueDistance
						.hashCode());
		result = prime * result
				+ ((gaugeOriginX == null) ? 0 : gaugeOriginX.hashCode());
		result = prime * result
				+ ((gaugeOriginY == null) ? 0 : gaugeOriginY.hashCode());
		result = prime
				* result
				+ ((gaugeOuterRadius == null) ? 0 : gaugeOuterRadius.hashCode());
		long temp;
		temp = Double.doubleToLongBits(kanZhi);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((keyField == null) ? 0 : keyField.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((labelStep == null) ? 0 : labelStep.hashCode());
		result = prime * result
				+ ((mccKeyId == null) ? 0 : mccKeyId.hashCode());
		result = prime * result + ((mysql == null) ? 0 : mysql.hashCode());
		result = prime * result
				+ ((numberPrefix == null) ? 0 : numberPrefix.hashCode());
		result = prime * result
				+ ((numberSuffix == null) ? 0 : numberSuffix.hashCode());
		result = prime * result
				+ ((pieRadius == null) ? 0 : pieRadius.hashCode());
		result = prime * result
				+ ((pivotRadius == null) ? 0 : pivotRadius.hashCode());
		result = prime * result + ((radius == null) ? 0 : radius.hashCode());
		temp = Double.doubleToLongBits(sheDingZhi);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((subCaption == null) ? 0 : subCaption.hashCode());
		result = prime
				* result
				+ ((thousandSeparator == null) ? 0 : thousandSeparator
						.hashCode());
		result = prime * result
				+ ((xAxisName == null) ? 0 : xAxisName.hashCode());
		result = prime * result
				+ ((yAxisName == null) ? 0 : yAxisName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MccKey other = (MccKey) obj;
		if (baseFont == null) {
			if (other.baseFont != null)
				return false;
		} else if (!baseFont.equals(other.baseFont))
			return false;
		if (baseFontColor == null) {
			if (other.baseFontColor != null)
				return false;
		} else if (!baseFontColor.equals(other.baseFontColor))
			return false;
		if (baseWidth == null) {
			if (other.baseWidth != null)
				return false;
		} else if (!baseWidth.equals(other.baseWidth))
			return false;
		if (bgColor == null) {
			if (other.bgColor != null)
				return false;
		} else if (!bgColor.equals(other.bgColor))
			return false;
		if (bgColor2 == null) {
			if (other.bgColor2 != null)
				return false;
		} else if (!bgColor2.equals(other.bgColor2))
			return false;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
		if (chartType == null) {
			if (other.chartType != null)
				return false;
		} else if (!chartType.equals(other.chartType))
			return false;
		if (ckey == null) {
			if (other.ckey != null)
				return false;
		} else if (!ckey.equals(other.ckey))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (decimalPrecision == null) {
			if (other.decimalPrecision != null)
				return false;
		} else if (!decimalPrecision.equals(other.decimalPrecision))
			return false;
		if (displayValueDistance == null) {
			if (other.displayValueDistance != null)
				return false;
		} else if (!displayValueDistance.equals(other.displayValueDistance))
			return false;
		if (gaugeOriginX == null) {
			if (other.gaugeOriginX != null)
				return false;
		} else if (!gaugeOriginX.equals(other.gaugeOriginX))
			return false;
		if (gaugeOriginY == null) {
			if (other.gaugeOriginY != null)
				return false;
		} else if (!gaugeOriginY.equals(other.gaugeOriginY))
			return false;
		if (gaugeOuterRadius == null) {
			if (other.gaugeOuterRadius != null)
				return false;
		} else if (!gaugeOuterRadius.equals(other.gaugeOuterRadius))
			return false;
		if (Double.doubleToLongBits(kanZhi) != Double
				.doubleToLongBits(other.kanZhi))
			return false;
		if (keyField == null) {
			if (other.keyField != null)
				return false;
		} else if (!keyField.equals(other.keyField))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (labelStep == null) {
			if (other.labelStep != null)
				return false;
		} else if (!labelStep.equals(other.labelStep))
			return false;
		if (mccKeyId == null) {
			if (other.mccKeyId != null)
				return false;
		} else if (!mccKeyId.equals(other.mccKeyId))
			return false;
		if (mysql == null) {
			if (other.mysql != null)
				return false;
		} else if (!mysql.equals(other.mysql))
			return false;
		if (numberPrefix == null) {
			if (other.numberPrefix != null)
				return false;
		} else if (!numberPrefix.equals(other.numberPrefix))
			return false;
		if (numberSuffix == null) {
			if (other.numberSuffix != null)
				return false;
		} else if (!numberSuffix.equals(other.numberSuffix))
			return false;
		if (pieRadius == null) {
			if (other.pieRadius != null)
				return false;
		} else if (!pieRadius.equals(other.pieRadius))
			return false;
		if (pivotRadius == null) {
			if (other.pivotRadius != null)
				return false;
		} else if (!pivotRadius.equals(other.pivotRadius))
			return false;
		if (radius == null) {
			if (other.radius != null)
				return false;
		} else if (!radius.equals(other.radius))
			return false;
		if (Double.doubleToLongBits(sheDingZhi) != Double
				.doubleToLongBits(other.sheDingZhi))
			return false;
		if (subCaption == null) {
			if (other.subCaption != null)
				return false;
		} else if (!subCaption.equals(other.subCaption))
			return false;
		if (thousandSeparator == null) {
			if (other.thousandSeparator != null)
				return false;
		} else if (!thousandSeparator.equals(other.thousandSeparator))
			return false;
		if (xAxisName == null) {
			if (other.xAxisName != null)
				return false;
		} else if (!xAxisName.equals(other.xAxisName))
			return false;
		if (yAxisName == null) {
			if (other.yAxisName != null)
				return false;
		} else if (!yAxisName.equals(other.yAxisName))
			return false;
		return true;
	}
}
