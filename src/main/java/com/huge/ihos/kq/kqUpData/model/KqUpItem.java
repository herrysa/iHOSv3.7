package com.huge.ihos.kq.kqUpData.model;

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

import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.model.BaseObject;

@Entity
@Table(name = "kq_kqUpItem")
public class KqUpItem extends BaseObject implements Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3042927930220337681L;

	private String itemId ;  //考勤项的id
	
	private String itemName ;  //考勤项名称
	
	private KqType kqType ; //考勤类别的id
	
	private Integer sn;            //序号
	
	private String itemType ;      //项目类型 0:数字型;1:字符型;2:日期型;3:整型
	
	private Integer itemLength ;    //项目长度
	
	private Integer scale;  //小数位数
	
	private Boolean isInherit = false;          //继承项 
	
	private String calculateType ;       //计算类型 （0，：手填，1：公式计算）
	
	private String showType;//day,kqItem,XT
	
	//private Boolean warning = false;            //限额提醒 
	
	//private String warningType; //1:大于;2:大于等于;3:小于;4:小于等于
	
	//private Double warningValue ;        //限额提醒值
	
	private String remark;              //备注
	
	private Boolean disabled = false ;            //停用
	
	private String itemCode ;           //考勤项编码
	
	private String colName;
	
	private String colWidth;
	
	private Boolean  sysField = false;//系统
	
	private Boolean kqUpDataHide = false;//考勤上报时隐藏
	
	private String isThousandSeparat;
	    
	private String headerFontIndex;
	    
	private String fontIndex;
	    
	private String headerTextColor;
	
	private String frequency;//单位
	
	private String shortName;//简称

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
	@Column(name = "itemName", length = 50,nullable=false)
	public String getItemName() {
		return itemName;
	}
   
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kqTypeId",nullable = true)
	public KqType getKqType() {
		return kqType;
	}

	public void setKqType(KqType kqType) {
		this.kqType = kqType;
	}

	@Column(name = "sn")
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	@Column(name = "itemType",length=1,nullable=false)
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
	@Column(name = "calculateType",length=1)
	public String getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(String calculateType) {
		this.calculateType = calculateType;
	}
	/*@Column(name = "warning")
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
	}*/
	@Column(name = "remark",length=200)
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
	@Column(name="itemCode",length = 20)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	@Column(name="sysField")
	public Boolean getSysField() {
		return sysField;
	}

	public void setSysField(Boolean sysField) {
		this.sysField = sysField;
	}
	@Column(name="kqUpDataHide")
	public Boolean getKqUpDataHide() {
		return kqUpDataHide;
	}

	public void setKqUpDataHide(Boolean kqUpDataHide) {
		this.kqUpDataHide = kqUpDataHide;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((calculateType == null) ? 0 : calculateType.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((kqType == null) ? 0 : kqType.hashCode());
		result = prime * result
				+ ((isInherit == null) ? 0 : isInherit.hashCode());
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + itemLength;
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result
				+ ((itemType == null) ? 0 : itemType.hashCode());
//		result = prime * result
//				+ ((necessary == null) ? 0 : necessary.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((scale == null) ? 0 : scale.hashCode());
		result = prime * result + sn;
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
		KqUpItem other = (KqUpItem) obj;
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
		if (kqType == null) {
			if (other.kqType != null)
				return false;
		} else if (!kqType.equals(other.kqType))
			return false;
		if (isInherit == null) {
			if (other.isInherit != null)
				return false;
		} else if (!isInherit.equals(other.isInherit))
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
		return true;
	}

	@Override
	public String toString() {
		return "KqUpItem [itemId=" + itemId + ", itemName=" + itemName
				+ ", kqType=" + kqType + ", sn=" + sn + ", itemType="
				+ itemType + ", itemLength=" + itemLength + ", scale=" + scale
				+ ", isInherit="
				+ isInherit + ", calculateType="
				+ calculateType +  ", remark=" + remark + ", disabled="
				+ disabled + ", itemCode=" + itemCode + ", kqUpDataHide="
				+ kqUpDataHide + "]";
	}

	@Override
	public KqUpItem clone() {
		KqUpItem o = null;
		try {
			o = (KqUpItem) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
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
//	@Column(name="warningType",length=2)
//	public String getWarningType() {
//		return warningType;
//	}
//
//	public void setWarningType(String warningType) {
//		this.warningType = warningType;
//	}
	@Column(name="showType",length=50)
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	@Transient
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	@Transient
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
