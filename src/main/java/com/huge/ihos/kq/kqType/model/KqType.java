package com.huge.ihos.kq.kqType.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
@Entity
@Table(name="kq_kqType")
public class KqType extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3902848298922162626L;
	
	private String kqTypeId; //ID
	private String kqTypeCode; //考勤类别编码
	private String kqTypeName; //考勤类别名称
	private Boolean disabled = false; //考勤类别是否禁用
	
	@Id
	@Column(name = "kqTypeId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getKqTypeId() {
		return kqTypeId;
	}
	public void setKqTypeId(String kqTypeId) {
		this.kqTypeId = kqTypeId;
	}
	@Column(name="kqTypeCode",nullable=false,length=50)
	public String getKqTypeCode() {
		return kqTypeCode;
	}
	public void setKqTypeCode(String kqTypeCode) {
		this.kqTypeCode = kqTypeCode;
	}
	@Column(name="kqTypeName",nullable=false,length=50)
	public String getKqTypeName() {
		return kqTypeName;
	}
	public void setKqTypeName(String kqTypeName) {
		this.kqTypeName = kqTypeName;
	}
	@Column(name="disabled",nullable=false)
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((kqTypeCode == null) ? 0 : kqTypeCode.hashCode());
		result = prime * result + ((kqTypeId == null) ? 0 : kqTypeId.hashCode());
		result = prime * result + ((kqTypeName == null) ? 0 : kqTypeName.hashCode());
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
		KqType other = (KqType) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (kqTypeCode == null) {
			if (other.kqTypeCode != null)
				return false;
		} else if (!kqTypeCode.equals(other.kqTypeCode))
			return false;
		if (kqTypeId == null) {
			if (other.kqTypeId != null)
				return false;
		} else if (!kqTypeId.equals(other.kqTypeId))
			return false;
		if (kqTypeName == null) {
			if (other.kqTypeName != null)
				return false;
		} else if (!kqTypeName.equals(other.kqTypeName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "KqType [kqTypeId=" + kqTypeId + ", kqTypeCode=" + kqTypeCode + ", kqTypeName=" + kqTypeName + ", disabled=" + disabled + "]";
	}
	public KqType() {
		super();
	}
	
}
