package com.huge.ihos.system.systemManager.menu.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.model.BaseObject;

@Entity
@Table(name="t_menuButton")
public class MenuButton extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8218562317518387817L;
	
	private String id;
	private String menuId;
	private String buttonLabel;
	private String buttonUrl;
	private String className;
	//private String buttonType;
	private Long oorder;
	private boolean show = true;
	private boolean enable = true;
	private String random;
	
	@Id
	@Column(name="id",length=60,nullable = false )
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="menuId",length=30,nullable = false )
	public String getMenuId() {
		return menuId;
	}
	
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name="buttonLabel",length=50)
	public String getButtonLabel() {
		return buttonLabel;
	}

	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}

	@Column(name="buttonUrl",length=500)
	public String getButtonUrl() {
		return buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}
	
	@Column(name="className")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name="oorder", nullable = false )
	public Long getOorder() {
		return oorder;
	}

	public void setOorder(Long oorder) {
		this.oorder = oorder;
	}
	
	/*@Column(name="buttonType")
	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}*/
	
	@Transient
	public boolean getShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
	@Transient
	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	@Transient
	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((buttonLabel == null) ? 0 : buttonLabel.hashCode());
		result = prime * result
				+ ((buttonUrl == null) ? 0 : buttonUrl.hashCode());
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result + ((oorder == null) ? 0 : oorder.hashCode());
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
		MenuButton other = (MenuButton) obj;
		if (buttonLabel == null) {
			if (other.buttonLabel != null)
				return false;
		} else if (!buttonLabel.equals(other.buttonLabel))
			return false;
		if (buttonUrl == null) {
			if (other.buttonUrl != null)
				return false;
		} else if (!buttonUrl.equals(other.buttonUrl))
			return false;
		if (menuId == null) {
			if (other.menuId != null)
				return false;
		} else if (!menuId.equals(other.menuId))
			return false;
		if (oorder == null) {
			if (other.oorder != null)
				return false;
		} else if (!oorder.equals(other.oorder))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuButton [menuId=" + menuId 
				+ ", buttonLabel=" + buttonLabel + ", buttonUrl=" + buttonUrl
				+ ", oorder=" + oorder + "]";
	}

}
