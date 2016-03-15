package com.huge.ihos.kq.kqItem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name="kq_kqItem")
public class KqItem extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3502451275392319981L;
	
	private String kqItemId; //ID
	private String kqItemCode; //项目编码
	private String kqItemName; //项目名称
	private String shortName; //简称
	private Boolean isDefault; //是否缺省
	private Boolean isYearHoliday; //是否年假
	private Boolean isCoveroffGeneralHoliday; //是否覆盖公休
	private Integer frequency; //频次 	(0:天数;1:次数;2:小时)
	private String itemIcon;
	private String parentId; //父亲节点ID
	private Boolean isUsed = false;//被使用
	
	@Id
	@Column(name="kqItemId",length=32)
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator = "uuid")
	public String getKqItemId() {
		return kqItemId;
	}
	public void setKqItemId(String kqItemId) {
		this.kqItemId = kqItemId;
	}
	@Column(name="kqItemCode",nullable=false,length=50)
	public String getKqItemCode() {
		return kqItemCode;
	}
	public void setKqItemCode(String kqItemCode) {
		this.kqItemCode = kqItemCode;
	}
	@Column(name="kqItemName",nullable=false,length=50)
	public String getKqItemName() {
		return kqItemName;
	}
	public void setKqItemName(String kqItemName) {
		this.kqItemName = kqItemName;
	}
	@Column(name="shortName",nullable=false,length=20)
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Column(name="isDefault")
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Column(name="isYearHoliday")
	public Boolean getIsYearHoliday() {
		return isYearHoliday;
	}
	public void setIsYearHoliday(Boolean isYearHoliday) {
		this.isYearHoliday = isYearHoliday;
	}
	@Column(name="isCoveroffGeneralHoliday")
	public Boolean getIsCoveroffGeneralHoliday() {
		return isCoveroffGeneralHoliday;
	}
	public void setIsCoveroffGeneralHoliday(Boolean isCoveroffGeneralHoliday) {
		this.isCoveroffGeneralHoliday = isCoveroffGeneralHoliday;
	}
	@Column(name="frequency",nullable=false)
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	@Column(name="itemIcon",length=100)
	public String getItemIcon() {
		return itemIcon;
	}
	public void setItemIcon(String itemIcon) {
		this.itemIcon = itemIcon;
	}
	@Column(name="parentId",nullable=false,length=32)
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name="isUsed")
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((isCoveroffGeneralHoliday == null) ? 0 : isCoveroffGeneralHoliday.hashCode());
		result = prime * result + ((isDefault == null) ? 0 : isDefault.hashCode());
		result = prime * result + ((isYearHoliday == null) ? 0 : isYearHoliday.hashCode());
		result = prime * result + ((kqItemCode == null) ? 0 : kqItemCode.hashCode());
		result = prime * result + ((kqItemId == null) ? 0 : kqItemId.hashCode());
		result = prime * result + ((kqItemName == null) ? 0 : kqItemName.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result + ((itemIcon == null) ? 0 : itemIcon.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
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
		KqItem other = (KqItem) obj;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (isCoveroffGeneralHoliday == null) {
			if (other.isCoveroffGeneralHoliday != null)
				return false;
		} else if (!isCoveroffGeneralHoliday.equals(other.isCoveroffGeneralHoliday))
			return false;
		if (isDefault == null) {
			if (other.isDefault != null)
				return false;
		} else if (!isDefault.equals(other.isDefault))
			return false;
		if (isYearHoliday == null) {
			if (other.isYearHoliday != null)
				return false;
		} else if (!isYearHoliday.equals(other.isYearHoliday))
			return false;
		if (kqItemCode == null) {
			if (other.kqItemCode != null)
				return false;
		} else if (!kqItemCode.equals(other.kqItemCode))
			return false;
		if (kqItemId == null) {
			if (other.kqItemId != null)
				return false;
		} else if (!kqItemId.equals(other.kqItemId))
			return false;
		if (kqItemName == null) {
			if (other.kqItemName != null)
				return false;
		} else if (!kqItemName.equals(other.kqItemName))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (itemIcon == null) {
			if (other.itemIcon != null)
				return false;
		} else if (!itemIcon.equals(other.itemIcon))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		return true;
	}
	
	public KqItem() {
		super();
	}
	@Override
	public String toString() {
		return "KqItem [kqItemId=" + kqItemId + ", kqItemCode=" + kqItemCode + ", kqItemName=" + kqItemName + ", shortName=" + shortName + ", isDefault=" + isDefault + ", isYearHoliday=" + isYearHoliday + ", isCoveroffGeneralHoliday=" + isCoveroffGeneralHoliday + ", frequency=" + frequency + ",itemIcon=" + itemIcon + ", parentId=" + parentId + "]";
	}
}
