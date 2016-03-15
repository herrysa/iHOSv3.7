package com.huge.ihos.accounting.voucher.model;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.accounting.AssistType.model.AssistType;
import com.huge.model.BaseObject;

@Entity
@Table( name = "GL_voucherDetail_assist" )
public class VoucherDetailAssist extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String VoucherDetailAssistId;
	private VoucherDetail voucherDetail;
	/*private String voucherType;
	private Integer voucherNo;
	private Integer detailNo;*/
	private Integer assistNo;
	private String abstractStr;
	private AssistType assistType;
	private String assistValue;
	private String assistName;
	private Double money;
	private HashMap<String,Object> assist;
	
	@Id
	@Column(length = 32,name="voucherDetailAssistId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@JSON(serialize=false)
	public String getVoucherDetailAssistId() {
		return VoucherDetailAssistId;
	}
	public void setVoucherDetailAssistId(String voucherDetailAssistId) {
		VoucherDetailAssistId = voucherDetailAssistId;
	}
	
	/*@JSON(serialize=false)
	@Column(name="voucherType")
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	
	@JSON(serialize=false)
	@Column(name="voucherNo")
	public Integer getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(Integer voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	@JSON(serialize=false)
	@Column(name="detailNo")
	public Integer getDetailNo() {
		return detailNo;
	}
	public void setDetailNo(Integer detailNo) {
		this.detailNo = detailNo;
	}*/
	
	@Column(name="assistNo")
	public Integer getAssistNo() {
		return assistNo;
	}
	public void setAssistNo(Integer assistNo) {
		this.assistNo = assistNo;
	}
	
	@Column(name="abstract")
	public String getAbstractStr() {
		return abstractStr;
	}
	public void setAbstractStr(String abstractStr) {
		this.abstractStr = abstractStr;
	}
	
	@JSON(serialize=false)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assistCode")
	public AssistType getAssistType() {
		return assistType;
	}
	public void setAssistType(AssistType assistType) {
		this.assistType = assistType;
	}
	
	@Column(name="assistValue")
	public String getAssistValue() {
		return assistValue;
	}
	public void setAssistValue(String assistValue) {
		this.assistValue = assistValue;
	}
	
	@Column(name="assistName")
	public String getAssistName() {
		return assistName;
	}
	public void setAssistName(String assistName) {
		this.assistName = assistName;
	}
	
	@JSON(serialize=false)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="voucherDetailId")
	public VoucherDetail getVoucherDetail() {
		return voucherDetail;
	}
	public void setVoucherDetail(VoucherDetail voucherDetail) {
		this.voucherDetail = voucherDetail;
	}
	
	@JSON(serialize=false)
	@Column(name="money")
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@JSON(serialize=false)
	@Transient
	public HashMap<String, Object> getAssist() {
		return assist;
	}
	public void setAssist(HashMap<String, Object> assist) {
		this.assist = assist;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((VoucherDetailAssistId == null) ? 0 : VoucherDetailAssistId
						.hashCode());
		result = prime * result
				+ ((abstractStr == null) ? 0 : abstractStr.hashCode());
		result = prime * result + ((assist == null) ? 0 : assist.hashCode());
		result = prime * result
				+ ((assistName == null) ? 0 : assistName.hashCode());
		result = prime * result
				+ ((assistNo == null) ? 0 : assistNo.hashCode());
		result = prime * result
				+ ((assistType == null) ? 0 : assistType.hashCode());
		result = prime * result
				+ ((assistValue == null) ? 0 : assistValue.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
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
		VoucherDetailAssist other = (VoucherDetailAssist) obj;
		if (VoucherDetailAssistId == null) {
			if (other.VoucherDetailAssistId != null)
				return false;
		} else if (!VoucherDetailAssistId.equals(other.VoucherDetailAssistId))
			return false;
		if (abstractStr == null) {
			if (other.abstractStr != null)
				return false;
		} else if (!abstractStr.equals(other.abstractStr))
			return false;
		if (assist == null) {
			if (other.assist != null)
				return false;
		} else if (!assist.equals(other.assist))
			return false;
		if (assistName == null) {
			if (other.assistName != null)
				return false;
		} else if (!assistName.equals(other.assistName))
			return false;
		if (assistNo == null) {
			if (other.assistNo != null)
				return false;
		} else if (!assistNo.equals(other.assistNo))
			return false;
		if (assistType == null) {
			if (other.assistType != null)
				return false;
		} else if (!assistType.equals(other.assistType))
			return false;
		if (assistValue == null) {
			if (other.assistValue != null)
				return false;
		} else if (!assistValue.equals(other.assistValue))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "VoucherDetailAssist [VoucherDetailAssistId="
				+ VoucherDetailAssistId + ", voucherDetail=" + voucherDetail
				+ ", assistNo=" + assistNo + ", abstractStr=" + abstractStr
				+ ", assistType=" + assistType + ", assistValue=" + assistValue
				+ ", assistName=" + assistName + ", money=" + money
				+ ", assist=" + assist + "]";
	}
	
}
