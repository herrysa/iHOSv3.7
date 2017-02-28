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
@Table( name = "sy_projNature" )
public class ProjNature  extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String natureId;
	private String natureName;
	private Boolean isSys;
	private Boolean disabled;
	
	@Id
	@Column(name="nature_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getNatureId() {
		return natureId;
	}
	public void setNatureId(String natureId) {
		this.natureId = natureId;
	}
	
	@Column(name="nature_name",length=50)
	public String getNatureName() {
		return natureName;
	}
	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	
	@Column(name="is_sys")
	public Boolean getIsSys() {
		return isSys;
	}
	public void setIsSys(Boolean isSys) {
		this.isSys = isSys;
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
		result = prime * result + ((isSys == null) ? 0 : isSys.hashCode());
		result = prime * result
				+ ((natureId == null) ? 0 : natureId.hashCode());
		result = prime * result
				+ ((natureName == null) ? 0 : natureName.hashCode());
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
		ProjNature other = (ProjNature) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (isSys == null) {
			if (other.isSys != null)
				return false;
		} else if (!isSys.equals(other.isSys))
			return false;
		if (natureId == null) {
			if (other.natureId != null)
				return false;
		} else if (!natureId.equals(other.natureId))
			return false;
		if (natureName == null) {
			if (other.natureName != null)
				return false;
		} else if (!natureName.equals(other.natureName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProjNature [natureId=" + natureId + ", natureName="
				+ natureName + ", isSys=" + isSys + ", disabled=" + disabled
				+ "]";
	}
}
