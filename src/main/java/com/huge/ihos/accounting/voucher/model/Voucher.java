package com.huge.ihos.accounting.voucher.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;

import com.huge.model.BaseObject;
import com.huge.util.DateUtil;

@Entity
@Table(name="GL_voucher")
@FilterDef( name = "sysVariable", parameters = { @ParamDef( name = "orgCode", type = "string" ),
		                                     @ParamDef( name = "copyCode", type = "string" ),
		                                     @ParamDef( name = "periodYear", type = "string" ),
		                                     @ParamDef( name = "periodMonth", type = "string" ) } )
@Filters( { @Filter( name = "sysVariable", condition = "orgCode = :orgCode and copyCode = :copyCode and periodMonth = :periodMonth" )} )
public class Voucher extends BaseObject {

	private String voucherId;
	private String orgCode;
	private String copyCode;
	private String periodYear;
	private String periodMonth;
	private String voucherFromCode;
	private String voucherType;
	private Integer voucherNo;
	private String voucherDate;
	private Integer attachNum;
	private String billId;
	private String billName;
	private String billDate;
	private String checkId;
	private String checkName;
	private String checkDate;
	private String auditId;
	private String auditName;
	private String auditDate;
	private String businessPerson;
	private String pzhzkmdy;
	private String pzhzbz;
	private String abstractStr;
	private String remark;
	private String kjManager;
	private Integer status;
	private BigDecimal money;
	private Set<VoucherDetail> voucherDetails;

	private Map<String, VoucherDetail> voucherDetailData;
	
	private String voucherNum;
	private Date ts;
	private String tsStr;
	

	@Id
	@GeneratedValue(generator = "uuid")     
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name="voucherId")
	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	@Column(name="orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="copyCode")
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name="periodYear",length=4)
	public String getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}
	
	@Column(name="periodMonth",length=6)
	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	@Column(name="voucherFromCode")
	public String getVoucherFromCode() {
		return voucherFromCode;
	}

	public void setVoucherFromCode(String voucherFromCode) {
		this.voucherFromCode = voucherFromCode;
	}

	@Column(name="voucherType")
	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	
	@Column(name="voucherNo")
	public Integer getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(Integer voucherNo) {
		this.voucherNo = voucherNo;
	}

	@Column(name="voucherDate")
	public String getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}

	@Column(name="attachNum")
	public Integer getAttachNum() {
		return attachNum;
	}

	public void setAttachNum(Integer attachNum) {
		this.attachNum = attachNum;
	}

	@Column(name="bill_ID")
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	@Column(name="bill_name")
	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	@Column(name="bill_date")
	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	@Column(name="check_id")
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@Column(name="check_name")
	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	@Column(name="check_date")
	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name="account_id")
	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	@Column(name="account_name")
	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	@Column(name="account_date")
	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	@Column(name="businessPerson")
	public String getBusinessPerson() {
		return businessPerson;
	}

	public void setBusinessPerson(String businessPerson) {
		this.businessPerson = businessPerson;
	}

	@Column(name="pzhzkmdy")
	public String getPzhzkmdy() {
		return pzhzkmdy;
	}

	public void setPzhzkmdy(String pzhzkmdy) {
		this.pzhzkmdy = pzhzkmdy;
	}

	@Column(name="pzhzbz")
	public String getPzhzbz() {
		return pzhzbz;
	}

	public void setPzhzbz(String pzhzbz) {
		this.pzhzbz = pzhzbz;
	}

	@Column(name="abstract")
	public String getAbstractStr() {
		return abstractStr;
	}

	public void setAbstractStr(String abstractStr) {
		this.abstractStr = abstractStr;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="kjManager")
	public String getKjManager() {
		return kjManager;
	}

	public void setKjManager(String kjManager) {
		this.kjManager = kjManager;
	}

	@Column(name="status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="money")
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	@JSON(serialize=false)
	@Transient
	public Map<String, VoucherDetail> getVoucherDetailData() {
		voucherDetailData = new HashMap<String, VoucherDetail>();
		if(voucherDetails!=null&&voucherDetails.size()!=0){
			for(VoucherDetail voucherDetail : voucherDetails){
				voucherDetailData.put(""+voucherDetail.getDetailNo(), voucherDetail);
			}
		}
		return voucherDetailData;
	}

	
	public void setVoucherDetailData(Map<String, VoucherDetail> voucherDetailData) {
		this.voucherDetailData = voucherDetailData;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="voucher",orphanRemoval=true,cascade=CascadeType.ALL)
	public Set<VoucherDetail> getVoucherDetails() {
		return voucherDetails;
	}

	private void setVoucherDetails(Set<VoucherDetail> voucherDetails) {
		this.voucherDetails = voucherDetails;
	}
	
	public void addVoucherDetails(Set<VoucherDetail> voucherDetails){
		if(this.voucherDetails!=null){
			this.voucherDetails.clear();
		}else{
			this.voucherDetails = new HashSet<VoucherDetail>();
		}
		
	  if (null != voucherDetails&& !voucherDetails.isEmpty())
	     {
	       this.voucherDetails.addAll(voucherDetails);
	     }
	}
	
	@Transient
	public String getVoucherNum() {
		return voucherNum = voucherType+"  "+voucherNo;
	}

	public void setVoucherNum(String voucherNum) {
		this.voucherNum = voucherNum;
	}
	
	@Column(name="ts")
	@Temporal(TemporalType.TIMESTAMP) 
	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	@Transient
	public String getTsStr() {
		tsStr = DateUtil.convertDateToString("yyyy-MM-dd hh:mm:ss", ts);
		return tsStr;
	}

	public void setTsStr(String tsStr) {
		this.tsStr = tsStr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abstractStr == null) ? 0 : abstractStr.hashCode());
		result = prime * result
				+ ((attachNum == null) ? 0 : attachNum.hashCode());
		result = prime * result
				+ ((auditDate == null) ? 0 : auditDate.hashCode());
		result = prime * result + ((auditId == null) ? 0 : auditId.hashCode());
		result = prime * result
				+ ((auditName == null) ? 0 : auditName.hashCode());
		result = prime * result
				+ ((billDate == null) ? 0 : billDate.hashCode());
		result = prime * result + ((billId == null) ? 0 : billId.hashCode());
		result = prime * result
				+ ((billName == null) ? 0 : billName.hashCode());
		result = prime * result
				+ ((businessPerson == null) ? 0 : businessPerson.hashCode());
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result + ((checkId == null) ? 0 : checkId.hashCode());
		result = prime * result
				+ ((checkName == null) ? 0 : checkName.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((kjManager == null) ? 0 : kjManager.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((periodMonth == null) ? 0 : periodMonth.hashCode());
		result = prime * result
				+ ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result + ((pzhzbz == null) ? 0 : pzhzbz.hashCode());
		result = prime * result
				+ ((pzhzkmdy == null) ? 0 : pzhzkmdy.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((ts == null) ? 0 : ts.hashCode());
		result = prime * result + ((tsStr == null) ? 0 : tsStr.hashCode());
		result = prime * result
				+ ((voucherDate == null) ? 0 : voucherDate.hashCode());
		result = prime * result
				+ ((voucherFromCode == null) ? 0 : voucherFromCode.hashCode());
		result = prime * result
				+ ((voucherId == null) ? 0 : voucherId.hashCode());
		result = prime * result
				+ ((voucherNo == null) ? 0 : voucherNo.hashCode());
		result = prime * result
				+ ((voucherNum == null) ? 0 : voucherNum.hashCode());
		result = prime * result
				+ ((voucherType == null) ? 0 : voucherType.hashCode());
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
		Voucher other = (Voucher) obj;
		if (abstractStr == null) {
			if (other.abstractStr != null)
				return false;
		} else if (!abstractStr.equals(other.abstractStr))
			return false;
		if (attachNum == null) {
			if (other.attachNum != null)
				return false;
		} else if (!attachNum.equals(other.attachNum))
			return false;
		if (auditDate == null) {
			if (other.auditDate != null)
				return false;
		} else if (!auditDate.equals(other.auditDate))
			return false;
		if (auditId == null) {
			if (other.auditId != null)
				return false;
		} else if (!auditId.equals(other.auditId))
			return false;
		if (auditName == null) {
			if (other.auditName != null)
				return false;
		} else if (!auditName.equals(other.auditName))
			return false;
		if (billDate == null) {
			if (other.billDate != null)
				return false;
		} else if (!billDate.equals(other.billDate))
			return false;
		if (billId == null) {
			if (other.billId != null)
				return false;
		} else if (!billId.equals(other.billId))
			return false;
		if (billName == null) {
			if (other.billName != null)
				return false;
		} else if (!billName.equals(other.billName))
			return false;
		if (businessPerson == null) {
			if (other.businessPerson != null)
				return false;
		} else if (!businessPerson.equals(other.businessPerson))
			return false;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (checkId == null) {
			if (other.checkId != null)
				return false;
		} else if (!checkId.equals(other.checkId))
			return false;
		if (checkName == null) {
			if (other.checkName != null)
				return false;
		} else if (!checkName.equals(other.checkName))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (kjManager == null) {
			if (other.kjManager != null)
				return false;
		} else if (!kjManager.equals(other.kjManager))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (periodMonth == null) {
			if (other.periodMonth != null)
				return false;
		} else if (!periodMonth.equals(other.periodMonth))
			return false;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (pzhzbz == null) {
			if (other.pzhzbz != null)
				return false;
		} else if (!pzhzbz.equals(other.pzhzbz))
			return false;
		if (pzhzkmdy == null) {
			if (other.pzhzkmdy != null)
				return false;
		} else if (!pzhzkmdy.equals(other.pzhzkmdy))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (ts == null) {
			if (other.ts != null)
				return false;
		} else if (!ts.equals(other.ts))
			return false;
		if (tsStr == null) {
			if (other.tsStr != null)
				return false;
		} else if (!tsStr.equals(other.tsStr))
			return false;
		if (voucherDate == null) {
			if (other.voucherDate != null)
				return false;
		} else if (!voucherDate.equals(other.voucherDate))
			return false;
		if (voucherFromCode == null) {
			if (other.voucherFromCode != null)
				return false;
		} else if (!voucherFromCode.equals(other.voucherFromCode))
			return false;
		if (voucherId == null) {
			if (other.voucherId != null)
				return false;
		} else if (!voucherId.equals(other.voucherId))
			return false;
		if (voucherNo == null) {
			if (other.voucherNo != null)
				return false;
		} else if (!voucherNo.equals(other.voucherNo))
			return false;
		if (voucherNum == null) {
			if (other.voucherNum != null)
				return false;
		} else if (!voucherNum.equals(other.voucherNum))
			return false;
		if (voucherType == null) {
			if (other.voucherType != null)
				return false;
		} else if (!voucherType.equals(other.voucherType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Voucher [voucherId=" + voucherId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", periodYear=" + periodYear
				+ ", periodMonth=" + periodMonth + ", voucherFromCode="
				+ voucherFromCode + ", voucherType=" + voucherType
				+ ", voucherNo=" + voucherNo + ", voucherDate=" + voucherDate
				+ ", attachNum=" + attachNum + ", billId=" + billId
				+ ", billName=" + billName + ", billDate=" + billDate
				+ ", checkId=" + checkId + ", checkName=" + checkName
				+ ", checkDate=" + checkDate + ", auditId=" + auditId
				+ ", auditName=" + auditName + ", auditDate=" + auditDate
				+ ", businessPerson=" + businessPerson + ", pzhzkmdy="
				+ pzhzkmdy + ", pzhzbz=" + pzhzbz + ", abstractStr="
				+ abstractStr + ", remark=" + remark + ", kjManager="
				+ kjManager + ", status=" + status + ", money=" + money
				+ ", voucherNum=" + voucherNum + ", ts=" + ts + ", tsStr="
				+ tsStr + "]";
	}


}
