package com.huge.ihos.system.systemManager.searchButtonPriv.model;

import java.io.Serializable;

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
@Table(name="t_buttonPriv_detail")
public class ButtonPrivDetail extends BaseObject implements Serializable {
	private String bpDetailId;
	private ButtonPriv buttonPriv;
	private String buttonId;
	private String buttonLabel;
	private String buttonURL;
	private Integer order;
//	private Boolean right;
	
	@Id
	@Column(name="bpDetailId")
	@GeneratedValue(generator = "uuid")     
	@GenericGenerator(name = "uuid", strategy = "uuid")  
	public String getBpDetailId() {
		return bpDetailId;
	}
	public void setBpDetailId(String bpDetailId) {
		this.bpDetailId = bpDetailId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="buttonPrivId")
	public ButtonPriv getButtonPriv() {
		return buttonPriv;
	}
	public void setButtonPriv(ButtonPriv buttonPriv) {
		this.buttonPriv = buttonPriv;
	}
	@Column(name="buttonId",length=20)
	public String getButtonId() {
		return buttonId;
	}
	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
	@Column(name="buttonLabel",length=50)
	public String getButtonLabel() {
		return buttonLabel;
	}
	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}
	@Column(name="buttonURL",length=500)
	public String getButtonURL() {
		return buttonURL;
	}
	public void setButtonURL(String buttonURL) {
		this.buttonURL = buttonURL;
	}
	@Column(name="[order]")
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
//	@Column(name="[right]")
//	public Boolean getRight() {
//		return right;
//	}
//	public void setRight(Boolean right) {
//		this.right = right;
//	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + order;
//		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result
				+ ((bpDetailId == null) ? 0 : bpDetailId.hashCode());
		result = prime * result
				+ ((buttonURL == null) ? 0 : buttonURL.hashCode());
		result = prime * result
				+ ((buttonId == null) ? 0 : buttonId.hashCode());
		result = prime * result
				+ ((buttonLabel == null) ? 0 : buttonLabel.hashCode());
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
		ButtonPrivDetail other = (ButtonPrivDetail) obj;
		if (order != other.order)
			return false;
//		if (right == null) {
//			if (other.right != null)
//				return false;
//		} else if (!right.equals(other.right))
//			return false;
		if (bpDetailId == null) {
			if (other.bpDetailId != null)
				return false;
		} else if (!bpDetailId.equals(other.bpDetailId))
			return false;
		if (buttonURL == null) {
			if (other.buttonURL != null)
				return false;
		} else if (!buttonURL.equals(other.buttonURL))
			return false;
		if (buttonId == null) {
			if (other.buttonId != null)
				return false;
		} else if (!buttonId.equals(other.buttonId))
			return false;
		if (buttonLabel == null) {
			if (other.buttonLabel != null)
				return false;
		} else if (!buttonLabel.equals(other.buttonLabel))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SearchButtoPrivDetail [bpDetailId=" + bpDetailId
				+ ", buttonId="
				+ buttonId + ", buttonLabel=" + buttonLabel
				+ ", buttonURL=" + buttonURL + ", order=" + order + 
				 "]";
	}
	
}
