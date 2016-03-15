package com.huge.ihos.strategy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;
@Entity
@Table( name = "kh_strategy" )
public class Strategy extends BaseObject
implements Serializable {
	/* id号 */
	private Integer strategyId;
	/* 开始期间 */
	private String periodBegin;
	/* 结束期间 */
	private String periodEnd;
	/* 战略主题 */
	private String strategyTitle;
	/* 启用/停用 */
	private boolean disabled = false;
	/* 摘要 */
	private String remark;
	/* 附件 */
	private String attachment;
	/* 附件路径 */
	private String attachmentUrl;
	
    @Id
    @GeneratedValue
    @Column( name = "strategyId")
	public Integer getStrategyId() {
		return strategyId;
	}
	public void setStrategyId(Integer strategyId) {
		this.strategyId = strategyId;
	}
	
	 @Column( name = "periodBegin", length = 6 )
	public String getPeriodBegin() {
		return periodBegin;
	}
	public void setPeriodBegin(String periodBegin) {
		this.periodBegin = periodBegin;
	}
	
	 @Column( name = "periodEnd", length = 6 )
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
	
	@Column( name = "strategyTitle", length = 50 )
	public String getStrategyTitle() {
		return strategyTitle;
	}
	public void setStrategyTitle(String strategyTitle) {
		this.strategyTitle = strategyTitle;
	}
	
	@Column( name = "disabled" )
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column( name = "remark", length = 1024)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column( name = "attachment", length = 50 )
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@Override
	public String toString() {
		return "Strategy [strategyId=" + strategyId + ", periodBegin="
				+ periodBegin + ", periodEnd=" + periodEnd + ", strategyTitle="
				+ strategyTitle + ", disabled=" + disabled + ", remark="
				+ remark + ", attachment=" + attachment + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attachment == null) ? 0 : attachment.hashCode());
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result
				+ ((periodBegin == null) ? 0 : periodBegin.hashCode());
		result = prime * result
				+ ((periodEnd == null) ? 0 : periodEnd.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((strategyTitle == null) ? 0 : strategyTitle.hashCode());
		result = prime * result + strategyId;
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
		Strategy other = (Strategy) obj;
		if (attachment == null) {
			if (other.attachment != null)
				return false;
		} else if (!attachment.equals(other.attachment))
			return false;
		if (disabled != other.disabled)
			return false;
		if (periodBegin == null) {
			if (other.periodBegin != null)
				return false;
		} else if (!periodBegin.equals(other.periodBegin))
			return false;
		if (periodEnd == null) {
			if (other.periodEnd != null)
				return false;
		} else if (!periodEnd.equals(other.periodEnd))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (strategyTitle == null) {
			if (other.strategyTitle != null)
				return false;
		} else if (!strategyTitle.equals(other.strategyTitle))
			return false;
		if (strategyId != other.strategyId)
			return false;
		return true;
	}
	@Column( name = "attachmentUrl", length = 128 )
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	

}
