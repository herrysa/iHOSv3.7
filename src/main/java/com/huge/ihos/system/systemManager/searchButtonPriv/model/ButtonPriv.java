package com.huge.ihos.system.systemManager.searchButtonPriv.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
@Entity
@Table(name="t_buttonPriv")
public class ButtonPriv extends BaseObject implements Serializable {

	private String privId;
	//private Search search;
	private String searchOrMenuId;
	private String searchName;
	private String label;
	private String roleId;
	private String buttonType;//1:search;2:menu
//	private String searchUrl;
	private Set<ButtonPrivDetail> buttonPrivDetails;
	
	@OneToMany(fetch=FetchType.LAZY,orphanRemoval=true,mappedBy="buttonPriv",cascade=CascadeType.ALL)
	public Set<ButtonPrivDetail> getButtonPrivDetails() {
		return buttonPrivDetails;
	}
	public void setButtonPrivDetails(
			Set<ButtonPrivDetail> buttonPrivDetails) {
		this.buttonPrivDetails = buttonPrivDetails;
	}
	@Id
	@GeneratedValue(generator = "uuid")     
	@GenericGenerator(name = "uuid", strategy = "uuid")  
	public String getPrivId() {
		return privId;
	}
	public void setPrivId(String privId) {
		this.privId = privId;
	}
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="searchId")
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}*/
	
	@Column(name = "searchOrMenuId", length = 20)
	public String getSearchOrMenuId() {
		return searchOrMenuId;
	}
	public void setSearchOrMenuId(String searchOrMenuId) {
		this.searchOrMenuId = searchOrMenuId;
	}
	@Column(name = "label",length=100)
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Column(name = "buttonType",length=2)
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	//	public String getSearchUrl() {
//		return searchUrl;
//	}
//	public void setSearchUrl(String searchUrl) {
//		this.searchUrl = searchUrl;
//	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((privId == null) ? 0 : privId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result
				+ ((searchOrMenuId == null) ? 0 : searchOrMenuId.hashCode());
		result = prime * result
				+ ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((searchName == null) ? 0 : searchName.hashCode());
		result = prime * result
				+ ((buttonType == null) ? 0 : buttonType.hashCode());
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
		ButtonPriv other = (ButtonPriv) obj;
		if (privId == null) {
			if (other.privId != null)
				return false;
		} else if (!privId.equals(other.privId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (searchOrMenuId == null) {
			if (other.searchOrMenuId != null)
				return false;
		} else if (!searchOrMenuId.equals(other.searchOrMenuId))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (searchName == null) {
			if (other.searchName != null)
				return false;
		} else if (!searchName.equals(other.searchName))
			return false;
		if (buttonType == null) {
			if (other.buttonType != null)
				return false;
		} else if (!buttonType.equals(other.buttonType))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SearchButtonPriv [privId=" + privId + ", searchOrMenuId=" + searchOrMenuId
				+ ", searchName=" + searchName + ", label=" + label
				+ ", roleId=" + roleId + ", buttonType="+buttonType+"]";
	}
	
}
