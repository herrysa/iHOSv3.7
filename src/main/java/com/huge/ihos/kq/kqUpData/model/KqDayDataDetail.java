package com.huge.ihos.kq.kqUpData.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name="kq_dayDataDetail")
public class KqDayDataDetail extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4455408465742266891L;
	private String detailId; //ID
	private KqDayData kqDayData; //考勤ID
	private String kqColumn; //考勤列
	private String kqItem;//考勤项
	private Double kqValue;//考勤数量
	
	@Id
	@Column(name = "detailId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kqId",nullable = true)
	public KqDayData getKqDayData() {
		return kqDayData;
	}
	public void setKqDayData(KqDayData kqDayData) {
		this.kqDayData = kqDayData;
	}
	
	@Column(name = "kqColumn", length = 50)
	public String getKqColumn() {
		return kqColumn;
	}
	public void setKqColumn(String kqColumn) {
		this.kqColumn = kqColumn;
	}
	
	@Column(name = "kqItem", length = 50)
	public String getKqItem() {
		return kqItem;
	}
	public void setKqItem(String kqItem) {
		this.kqItem = kqItem;
	}
	
	@Column(name = "kqValue")
	public Double getKqValue() {
		return kqValue;
	}
	public void setKqValue(Double kqValue) {
		this.kqValue = kqValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kqDayData == null) ? 0 : kqDayData.hashCode());
		result = prime * result + ((detailId == null) ? 0 : detailId.hashCode());
		result = prime * result + ((kqColumn == null) ? 0 : kqColumn.hashCode());
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
		KqDayDataDetail other = (KqDayDataDetail) obj;
		if (kqDayData == null) {
			if (other.kqDayData != null)
				return false;
		} else if (!kqDayData.equals(other.kqDayData))
			return false;
		if (detailId == null) {
			if (other.detailId != null)
				return false;
		} else if (!detailId.equals(other.detailId))
			return false;
		if (kqColumn == null) {
			if (other.kqColumn != null)
				return false;
		} else if (!kqColumn.equals(other.kqColumn))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "KqDayDataDetail [detailId=" + detailId + ", kqDayData=" + kqDayData + ", kqColumn=" + kqColumn + "]";
	}
}
