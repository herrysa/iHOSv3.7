package com.huge.ihos.gz.gzItem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.model.BaseObject;
import com.huge.util.OtherUtil;

@Entity
@Table(name = "gz_gzItem")
public class GzItem extends BaseObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1301395763349914124L;

	private String itemId; // 工资项的id

	private String itemName; // 工资项名称

	private String itemShowName; // 显示名称

	private GzType gzType; // 工资类别的id

	private Integer sn; // 工资项的序号

	private String itemType; // 项目类型 0:数字型;1:字符型;2:日期型;3:整型

	private Integer itemLength; // 项目长度

	private Integer scale; // 小数位数

	private Boolean isInherit = false; // 继承项

	private Boolean isTax = false; // 扣税项

	private String calculateType; // 计算类型 （0，：手填，1：公式计算）

	private Boolean warning = false; // 限额提醒

	private String warningType; // 1:大于;2:大于等于;3:小于;4:小于等于

	private Double warningValue; // 限额提醒值

	private String remark; // 备注

	private Boolean disabled = false; // 停用

	private String itemCode; // 工资项编码

	private String colName;

	private String colWidth;

	private Boolean statistics = false;// 统计

	private Boolean sysField = false;// 系统

	private Boolean gzContentHide = false;// 工资编辑时隐藏
	
	private Boolean isUsed = false;//被使用

	private String isThousandSeparat;

	private String headerFontIndex;

	private String fontIndex;

	private String headerTextColor;

	@Id
	@Column(name = "itemId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name = "itemName", length = 50, nullable = false)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gzTypeId", nullable = true)
	public GzType getGzType() {
		return gzType;
	}

	public void setGzType(GzType gzType) {
		this.gzType = gzType;
	}

	@Column(name = "sn")
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	@Column(name = "itemType", length = 1, nullable = false)
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Column(name = "itemLength")
	public Integer getItemLength() {
		return itemLength;
	}

	public void setItemLength(Integer itemLength) {
		this.itemLength = itemLength;
	}

	@Column(name = "scale")
	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	@Column(name = "isInherit")
	public Boolean getIsInherit() {
		return isInherit;
	}

	public void setIsInherit(Boolean isInherit) {
		this.isInherit = isInherit;
	}

	@Column(name = "isTax")
	public Boolean getIsTax() {
		return isTax;
	}

	public void setIsTax(Boolean isTax) {
		this.isTax = isTax;
	}

	@Column(name = "calculateType", length = 1)
	public String getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(String calculateType) {
		this.calculateType = calculateType;
	}

	@Column(name = "warning")
	public Boolean getWarning() {
		return warning;
	}

	public void setWarning(Boolean warning) {
		this.warning = warning;
	}

	@Column(name = "warningValue")
	public Double getWarningValue() {
		return warningValue;
	}

	public void setWarningValue(Double warningValue) {
		this.warningValue = warningValue;
	}

	@Column(name = "remark", length = 500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "itemCode", length = 20)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "sysField")
	public Boolean getSysField() {
		return sysField;
	}

	public void setSysField(Boolean sysField) {
		this.sysField = sysField;
	}

	@Column(name = "gzContentHide")
	public Boolean getGzContentHide() {
		return gzContentHide;
	}

	public void setGzContentHide(Boolean gzContentHide) {
		this.gzContentHide = gzContentHide;
	}

	@Column(name = "warningType", length = 2)
	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	@Column(name = "itemShowName", length = 50, nullable = false)
	public String getItemShowName() {
		return itemShowName;
	}

	public void setItemShowName(String itemShowName) {
		this.itemShowName = itemShowName;
	}

	@Column(name = "statisticsFlag")
	public Boolean getStatistics() {
		return statistics;
	}

	public void setStatistics(Boolean statistics) {
		this.statistics = statistics;
	}
	@Column(name = "isUsed")
	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	@Transient
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@Transient
	public String getColWidth() {
		return colWidth;
	}

	public void setColWidth(String colWidth) {
		this.colWidth = colWidth;
	}

	@Transient
	public String getIsThousandSeparat() {
		return isThousandSeparat;
	}

	public void setIsThousandSeparat(String isThousandSeparat) {
		this.isThousandSeparat = isThousandSeparat;
	}

	@Transient
	public String getHeaderFontIndex() {
		return headerFontIndex;
	}

	public void setHeaderFontIndex(String headerFontIndex) {
		this.headerFontIndex = headerFontIndex;
	}

	@Transient
	public String getFontIndex() {
		return fontIndex;
	}

	public void setFontIndex(String fontIndex) {
		this.fontIndex = fontIndex;
	}

	@Transient
	public String getHeaderTextColor() {
		return headerTextColor;
	}

	public void setHeaderTextColor(String headerTextColor) {
		this.headerTextColor = headerTextColor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((calculateType == null) ? 0 : calculateType.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((gzType == null) ? 0 : gzType.hashCode());
		result = prime * result
				+ ((isInherit == null) ? 0 : isInherit.hashCode());
		result = prime * result + ((isTax == null) ? 0 : isTax.hashCode());
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + itemLength;
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result
				+ ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((scale == null) ? 0 : scale.hashCode());
		result = prime * result + sn;
		result = prime * result + ((warning == null) ? 0 : warning.hashCode());
		result = prime * result
				+ ((warningValue == null) ? 0 : warningValue.hashCode());
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
		GzItem other = (GzItem) obj;
		if (calculateType == null) {
			if (other.calculateType != null)
				return false;
		} else if (!calculateType.equals(other.calculateType))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (gzType == null) {
			if (other.gzType != null)
				return false;
		} else if (!gzType.equals(other.gzType))
			return false;
		if (isInherit == null) {
			if (other.isInherit != null)
				return false;
		} else if (!isInherit.equals(other.isInherit))
			return false;
		if (isTax == null) {
			if (other.isTax != null)
				return false;
		} else if (!isTax.equals(other.isTax))
			return false;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (itemLength != other.itemLength)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (itemType == null) {
			if (other.itemType != null)
				return false;
		} else if (!itemType.equals(other.itemType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (scale == null) {
			if (other.scale != null)
				return false;
		} else if (!scale.equals(other.scale))
			return false;
		if (sn != other.sn)
			return false;
		if (warning == null) {
			if (other.warning != null)
				return false;
		} else if (!warning.equals(other.warning))
			return false;
		if (warningValue == null) {
			if (other.warningValue != null)
				return false;
		} else if (!warningValue.equals(other.warningValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GzItem [itemId=" + itemId + ", itemName=" + itemName
				+ ", gzType=" + gzType + ", sn=" + sn + ", itemType="
				+ itemType + ", itemLength=" + itemLength + ", scale=" + scale
				+ ", isInherit=" + isInherit + ", isTax=" + isTax
				+ ", calculateType=" + calculateType + ", warning=" + warning
				+ ", warningValue=" + warningValue + ", remark=" + remark
				+ ", disabled=" + disabled + ", itemCode=" + itemCode
				+ ", gzContentHide=" + gzContentHide + "]";
	}

	@Override
	public GzItem clone() {
		GzItem o = null;
		try {
			o = (GzItem) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public String toInsertSql(){
		String sqlString = " INSERT INTO gz_gzItem ";
		String sqlHead = "";
		String sqlEnd = "";
		sqlHead += "itemId,";
		sqlEnd += "'"+itemId+"',";
		sqlHead += "itemName,";
		sqlEnd += "'"+itemName+"',";
		sqlHead += "isUsed,";
		sqlEnd += "'0',";
		sqlHead += "itemShowName,";
		if(OtherUtil.measureNotNull(itemShowName)){
			sqlEnd += "'"+itemShowName+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "gzTypeId,";
		sqlEnd += "'"+gzType.getGzTypeId()+"',";
		sqlHead += "sn,";
		sqlEnd += "'"+sn+"',";
		sqlHead += "itemType,";
		sqlEnd += "'"+itemType+"',";
		sqlHead += "itemLength,";
		if(OtherUtil.measureNotNull(itemLength)){
			sqlEnd += "'"+itemLength+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "scale,";
		if(OtherUtil.measureNotNull(scale)){
			sqlEnd += "'"+scale+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "isInherit,";
		if(OtherUtil.measureNotNull(isInherit)){
			if(isInherit == true){
				sqlEnd += "'1',";
			}else{
				sqlEnd += "'0',";
			}
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "isTax,";
		if(OtherUtil.measureNotNull(isTax)){
			if(isTax == true){
				sqlEnd += "'1',";
			}else{
				sqlEnd += "'0',";
			}
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "calculateType,";
		if(OtherUtil.measureNotNull(calculateType)){
			sqlEnd += "'"+calculateType+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "warning,";
		if(OtherUtil.measureNotNull(warning)){
			if(warning == true){
				sqlEnd += "'1',";
			}else{
				sqlEnd += "'0',";
			}
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "warningType,";
		if(OtherUtil.measureNotNull(warningType)){
			sqlEnd += "'"+warningType+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "warningValue,";
		if(OtherUtil.measureNotNull(warningValue)){
			sqlEnd += "'"+warningValue+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "remark,";
		if(OtherUtil.measureNotNull(remark)){
			sqlEnd += "'"+remark+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "disabled,";
		if(OtherUtil.measureNotNull(disabled)){
			if(disabled == true){
				sqlEnd += "'1',";
			}else{
				sqlEnd += "'0',";
			}
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "itemCode,";
		if(OtherUtil.measureNotNull(itemCode)){
			sqlEnd += "'"+itemCode+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "statisticsFlag,";
		if(OtherUtil.measureNotNull(statistics)){
			if(statistics == true){
				sqlEnd += "'1',";
			}else{
				sqlEnd += "'0',";
			}
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "sysField,";
		if(OtherUtil.measureNotNull(sysField)){
			if(sysField == true){
				sqlEnd += "'1',";
			}else{
				sqlEnd += "'0',";
			}
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "gzContentHide";
		if(OtherUtil.measureNotNull(gzContentHide)){
			if(gzContentHide == true){
				sqlEnd += "'1'";
			}else{
				sqlEnd += "'0'";
			}
		}else{
			sqlEnd += "NULL";
		}
		sqlString += "(" + sqlHead + ") VALUES (" + sqlEnd + ")";
		return sqlString;
	}
}
