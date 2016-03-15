package com.huge.ihos.system.systemManager.searchButtonPriv.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
@Entity
@Table(name="t_buttonPrivUser")
public class ButtonPrivUser extends BaseObject implements Serializable,Comparable<ButtonPrivUser> {

	private String privId;
	private String searchName;
	private String label;
	private String userId;
	private String searchOrMenuId;
	private String buttonType;//1:search;2:menu
	private Set<ButtonPrivUserDetail> buttonPrivUserDetails;
	
	@OneToMany(fetch=FetchType.LAZY,orphanRemoval=true,mappedBy="buttonPrivUser",cascade=CascadeType.ALL)
	public Set<ButtonPrivUserDetail> getButtonPrivUserDetails() {
		return buttonPrivUserDetails;
	}
	public void setButtonPrivUserDetails(
			Set<ButtonPrivUserDetail> buttonPrivUserDetails) {
		this.buttonPrivUserDetails = buttonPrivUserDetails;
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
	public String getSearchName() {
		return searchName;
	}  
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSearchOrMenuId() {
		return searchOrMenuId;
	}
	public void setSearchOrMenuId(String searchOrMenuId) {
		this.searchOrMenuId = searchOrMenuId;
	}
	
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((privId == null) ? 0 : privId.hashCode());
		result = prime * result
				+ ((searchOrMenuId == null) ? 0 : searchOrMenuId.hashCode());
		result = prime * result
				+ ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((searchName == null) ? 0 : searchName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((buttonType == null) ? 0 : buttonType.hashCode());
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
		ButtonPrivUser other = (ButtonPrivUser) obj;
		if (privId == null) {
			if (other.privId != null)
				return false;
		} else if (!privId.equals(other.privId))
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		return "SearchButtonPrivUser [privId=" + privId + ", searchName="
				+ searchName + ", label=" + label + ", userId="
				+ userId + ", searchOrMenuId=" + searchOrMenuId + ", buttonType="+buttonType+"]";
	}
	@Override
	public int compareTo(ButtonPrivUser obj) {
		if(obj.searchOrMenuId==null){
			return 0;
		}
		String[] twoSearchId = {searchOrMenuId,obj.searchOrMenuId};
		Arrays.sort(twoSearchId);
		if(searchOrMenuId.equals(obj.searchOrMenuId))
			return 0;
		else if(!twoSearchId[0].equals(searchOrMenuId))
            return 1;
		else
	        return -1;
	}

	
}
