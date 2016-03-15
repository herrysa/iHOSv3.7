package com.huge.ihos.accounting.voucher.model;

import java.math.BigDecimal;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.model.BaseObject;

@Entity
@Table(name="GL_voucherDetail")
public class VoucherDetail extends BaseObject {

	private String voucherDetailId;
	/*private String orgCode;
	private String copyCode;
	private String kjPeriod;
	private String voucherFromCode;*/
	private Voucher voucher;
	/*private String voucherType;
	private Integer voucherNo;*/
	private Integer detailNo;
	private String acctcode;
	private Account account;
	private String accountId;
	private String accountFullName;
	private String abstractStr;
	private String wbcode;
	private String hl;
	private String direction;
	private BigDecimal wbje;
	private BigDecimal money;
	
	private Set<VoucherDetailAssist> voucherDetailAssists;
	
	private Map<String,Map> assistData;
	/*private BigDecimal lend = new BigDecimal(0);
	private BigDecimal loan = new BigDecimal(0);
	private BigDecimal showLend = new BigDecimal(0);
	private BigDecimal showLoan = new BigDecimal(0);*/
	/*private String lendFlag;
	private String loanFlag;
	private String loanOver;*/
	
	/*private BigDecimal lendF;
	private BigDecimal lendJ;
	private BigDecimal lendY;
	private BigDecimal lendS;
	private BigDecimal lendB;
	private BigDecimal lendQ;
	private BigDecimal lendW;
	private BigDecimal lendSW;
	private BigDecimal lendBW;
	private BigDecimal lendQW;
	private BigDecimal lendYI;
	
	private BigDecimal loanF;
	private BigDecimal loanJ;
	private BigDecimal loanY;
	private BigDecimal loanS;
	private BigDecimal loanB;
	private BigDecimal loanQ;
	private BigDecimal loanW;
	private BigDecimal loanSW;
	private BigDecimal loanBW;
	private BigDecimal loanQW;
	private BigDecimal loanYI;*/
	
	@Id
	@GeneratedValue(generator = "uuid")     
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name="voucherDetailId")
	@JSON(serialize=false)
	public String getVoucherDetailId() {
		return voucherDetailId;
	}

	public void setVoucherDetailId(String voucherDetailId) {
		this.voucherDetailId = voucherDetailId;
	}

	/*@JSON(serialize=false)
	@Column(name="orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@JSON(serialize=false)
	@Column(name="copyCode")
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@JSON(serialize=false)
	@Column(name="kjPeriod")
	public String getKjPeriod() {
		return kjPeriod;
	}

	public void setKjPeriod(String kjPeriod) {
		this.kjPeriod = kjPeriod;
	}

	@JSON(serialize=false)
	@Column(name="voucherFromCode")
	public String getVoucherFromCode() {
		return voucherFromCode;
	}

	public void setVoucherFromCode(String voucherFromCode) {
		this.voucherFromCode = voucherFromCode;
	}*/

	@JSON(serialize=false)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="voucherId")
	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
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
	}*/
	
	@Column(name="detailNo")
	public Integer getDetailNo() {
		return detailNo;
	}

	public void setDetailNo(Integer detailNo) {
		this.detailNo = detailNo;
	}

	@JSON(serialize=false)
	@ManyToOne
	@JoinColumn(name="accountId")
	public Account getAccount() {
		return account;
	}

	@Transient
	public String getAccountId() {
		if(account!=null){
			accountId = account.getAcctId();
		}
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Transient
	public String getAccountFullName() {
		if(account!=null){
			accountFullName = account.getAcctFullname();
		}
		return accountFullName;
	}

	public void setAccountFullName(String accountFullName) {
		this.accountFullName = accountFullName;
	}
	
	@Column(name="abstract")
	public String getAbstractStr() {
		return abstractStr;
	}

	public void setAbstractStr(String abstractStr) {
		this.abstractStr = abstractStr;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	@Column(name="acctcode")
	public String getAcctcode() {
		return acctcode;
	}

	public void setAcctcode(String acctcode) {
		this.acctcode = acctcode;
	}
	
	@JSON(serialize=false)
	@Column(name="wbcode")
	public String getWbcode() {
		return wbcode;
	}

	public void setWbcode(String wbcode) {
		this.wbcode = wbcode;
	}

	@JSON(serialize=false)
	@Column(name="hl")
	public String getHl() {
		return hl;
	}

	public void setHl(String hl) {
		this.hl = hl;
	}

	@Column(name="direction")
	public String getDirection() {
		/*if(direction!=null&&direction.equals("借")){
			direction = "lend";
		}else{
			direction = "loan";
		}*/
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@JSON(serialize=false)
	@Column(name="wbje")
	public BigDecimal getWbje() {
		return wbje;
	}

	public void setWbje(BigDecimal wbje) {
		this.wbje = wbje;
	}

	@JSON(serialize=false)
	@Column(name="money")
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	@JSON(serialize=false)
	@OneToMany(fetch=FetchType.LAZY,mappedBy="voucherDetail",orphanRemoval=true,cascade=CascadeType.ALL)
	public Set<VoucherDetailAssist> getVoucherDetailAssists() {
		return voucherDetailAssists;
	}

	private void setVoucherDetailAssists(Set<VoucherDetailAssist> voucherDetailAssists) {
		this.voucherDetailAssists = voucherDetailAssists;
	}
	
	public void addVoucherDetailAssists(Set<VoucherDetailAssist> voucherDetailAssists){
		if(this.voucherDetailAssists!=null){
			this.voucherDetailAssists.clear();
		}else{
			this.voucherDetailAssists = new HashSet<VoucherDetailAssist>();
		}
		
	  if (null != voucherDetailAssists&& !voucherDetailAssists.isEmpty())
	     {
	       this.voucherDetailAssists.addAll(voucherDetailAssists);
	     }
	}
	
	@Transient
	public Map<String,Map> getAssistData() {
		if(voucherDetailAssists!=null){
			assistData = new HashMap<String, Map>();
			for(VoucherDetailAssist voucherDetailAssist : voucherDetailAssists){
				Map assistMap = assistData.get(""+voucherDetailAssist.getAssistNo());
				if(assistMap==null){
					assistMap = new HashMap<String, String>();
				}
				assistMap.put("assistsAbstract", voucherDetailAssist.getAbstractStr());
				assistMap.put(""+voucherDetailAssist.getAssistType().getTypeCode(), voucherDetailAssist.getAssistName()+","+voucherDetailAssist.getAssistValue());
				assistMap.put("assistsMoney", voucherDetailAssist.getMoney()+","+voucherDetailAssist.getMoney());
				assistData.put(""+voucherDetailAssist.getAssistNo(), assistMap);
			}
		}
		return assistData;
	}

	public void setAssistData(Map<String,Map> assistData) {
		this.assistData = assistData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abstractStr == null) ? 0 : abstractStr.hashCode());
		result = prime * result
				+ ((accountFullName == null) ? 0 : accountFullName.hashCode());
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result
				+ ((acctcode == null) ? 0 : acctcode.hashCode());
		result = prime * result
				+ ((assistData == null) ? 0 : assistData.hashCode());
		result = prime * result
				+ ((detailNo == null) ? 0 : detailNo.hashCode());
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((hl == null) ? 0 : hl.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result
				+ ((voucherDetailId == null) ? 0 : voucherDetailId.hashCode());
		result = prime * result + ((wbcode == null) ? 0 : wbcode.hashCode());
		result = prime * result + ((wbje == null) ? 0 : wbje.hashCode());
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
		VoucherDetail other = (VoucherDetail) obj;
		if (abstractStr == null) {
			if (other.abstractStr != null)
				return false;
		} else if (!abstractStr.equals(other.abstractStr))
			return false;
		if (accountFullName == null) {
			if (other.accountFullName != null)
				return false;
		} else if (!accountFullName.equals(other.accountFullName))
			return false;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (acctcode == null) {
			if (other.acctcode != null)
				return false;
		} else if (!acctcode.equals(other.acctcode))
			return false;
		if (assistData == null) {
			if (other.assistData != null)
				return false;
		} else if (!assistData.equals(other.assistData))
			return false;
		if (detailNo == null) {
			if (other.detailNo != null)
				return false;
		} else if (!detailNo.equals(other.detailNo))
			return false;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (hl == null) {
			if (other.hl != null)
				return false;
		} else if (!hl.equals(other.hl))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (voucherDetailId == null) {
			if (other.voucherDetailId != null)
				return false;
		} else if (!voucherDetailId.equals(other.voucherDetailId))
			return false;
		if (wbcode == null) {
			if (other.wbcode != null)
				return false;
		} else if (!wbcode.equals(other.wbcode))
			return false;
		if (wbje == null) {
			if (other.wbje != null)
				return false;
		} else if (!wbje.equals(other.wbje))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VoucherDetail [voucherDetailId=" + voucherDetailId
				+ ", voucher=" + voucher + ", detailNo=" + detailNo
				+ ", acctcode=" + acctcode + ", account=" + account
				+ ", accountId=" + accountId + ", accountFullName="
				+ accountFullName + ", abstractStr=" + abstractStr
				+ ", wbcode=" + wbcode + ", hl=" + hl + ", direction="
				+ direction + ", wbje=" + wbje + ", money=" + money
				+ ", voucherDetailAssists=" + voucherDetailAssists
				+ ", assistData=" + assistData + "]";
	}


	
}
