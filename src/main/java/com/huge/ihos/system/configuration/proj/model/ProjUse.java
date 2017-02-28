package com.huge.ihos.system.configuration.proj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Entity;
import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;


@Entity
@Table( name = "sy_projUse" )
public class ProjUse extends BaseObject implements Serializable {
	private String useCode;
	private String useName;
	private Boolean disabled;
	
	@Id
	@Column(name="use_code")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getUseCode() {
		return useCode;
	}
	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}
	@Column(name="use_name",length=50)
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	@Column(name="disabled")
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
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((useCode == null) ? 0 : useCode.hashCode());
		result = prime * result + ((useName == null) ? 0 : useName.hashCode());
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
		ProjUse other = (ProjUse) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (useCode == null) {
			if (other.useCode != null)
				return false;
		} else if (!useCode.equals(other.useCode))
			return false;
		if (useName == null) {
			if (other.useName != null)
				return false;
		} else if (!useName.equals(other.useName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProjUse [useCode=" + useCode + ", useName=" + useName
				+ ", disabled=" + disabled + "]";
	}
}
