package com.huge.ihos.system.systemManager.searchButtonPriv.model;

import java.io.Serializable;
import java.util.Arrays;

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
@Table(name="t_buttonPrivUser_detail")
public class ButtonPrivUserDetail extends BaseObject implements Serializable,Comparable<ButtonPrivUserDetail> {
	private String bpuDetailId;
	private ButtonPrivUser buttonPrivUser;
	private String buttonId;
	private String buttonLabel;
	private String buttonURL;
	private Integer order;
//	private Boolean right;
	@Id
	@GeneratedValue(generator = "uuid")     
	@GenericGenerator(name = "uuid", strategy = "uuid")  
	public String getBpuDetailId() {
		return bpuDetailId;
	}
	public void setBpuDetailId(String bpuDetailId) {
		this.bpuDetailId = bpuDetailId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="buttonPrivUserId")
	public ButtonPrivUser getButtonPrivUser() {
		return buttonPrivUser;
	}
	public void setButtonPrivUser(ButtonPrivUser buttonPrivUser) {
		this.buttonPrivUser = buttonPrivUser;
	}
	public String getButtonId() {
		return buttonId;
	}
	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
	public String getButtonLabel() {
		return buttonLabel;
	}
	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}
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
		result = prime * result + ((order == null) ? 0 : order.hashCode());
//		result = prime * result + ((right == null) ? 0 : right.hashCode());
//		result = prime * result
//				+ ((bpuDetailId == null) ? 0 : bpuDetailId.hashCode());
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
		ButtonPrivUserDetail other = (ButtonPrivUserDetail) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
//		if (right == null) {
//			if (other.right != null)
//				return false;
//		} else if (!right.equals(other.right))
//			return false;
		if (bpuDetailId == null) {
			if (other.bpuDetailId != null)
				return false;
		} else if (!bpuDetailId.equals(other.bpuDetailId))
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
		return "SearchButtonPrivUserDetail [bpuDetailId=" + bpuDetailId
				+ ", buttonId=" + buttonId + ", buttonLabel="
				+ buttonLabel + ", buttonURL=" + buttonURL + ", order="
				+ order +  "]";
	}
	@Override
	public int compareTo(ButtonPrivUserDetail obj) {
		if(obj.buttonId==null){
			return 0;
		}
		String[] twoSearchURLId = {buttonId,obj.buttonId};
		Arrays.sort(twoSearchURLId);
		if(buttonId.equals(obj.buttonId))
			return 0;
		else if(!twoSearchURLId[0].equals(buttonId))
            return 1;
		else
	        return -1;
	}

	
}
