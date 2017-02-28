package com.huge.ihos.system.repository.vendor.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.ihos.system.repository.paymode.model.PayMode;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;

/**
 * 往来单位资料(供应商)
 * 
 * @author gaozhengyang
 * @date 2013-12-3
 */
@Entity
@Table(name = "sy_vendor")
public class Vendor extends BaseObject implements Serializable {
	private static final long serialVersionUID = -1935430451968363373L;
	private String vendorId;
	private String orgCode;
	private String vendorCode;
	private VendorType vendorType;
	private String vendorName;
	private String shortName;
	private String cnCode;
	private String province;// 省
	private String city;// 市
	private String venAddress;// 地址
	private String venPostCode;// 邮编

	private String distCode;// 地区码

	private String trafCode;// 运输方式
	private String venTrade;// 所属行业
	private String venRegCode;// 纳税人登记号
	private String venBank;// 开户银行
	private String venBankAccount;// 银行账号
	private String venPperson;// 业务联系人
	private String venLperson;// 法人
	private Date venDevDate;// 发展日期
	private String venPhone;// 电话
	private String venFax;// 传真
	private String venEmail;// 邮箱
	private String venPerson;// 供应商联系人
	private String venMobile;// 手机
	private Double venDisRate;// 扣率

	private String venCreGrade;// 信用等级
	private Double venCreLine;// 信用额度
	private Integer venCreDate;// 信用期限:天数

	private PayMode payMode;// 支付方式
	private PayCondition payCon;// 付款条件
	private String venDirAddress;// 到货地址
	// TODO
	private String venDirCode;// 到货方式
	private Department manageDept;// 分管部门

	private BigDecimal accPayMoney;
	private Date lastDate;
	private BigDecimal lastMoney;
	private Date lastPayDate;
	private BigDecimal lastPayMoney;

	private Date endDate;// 结束日期
	private String products;// 主要产品
	//
	private String frequency;
	private Boolean bvenTax = true;// 单价是否含税
	private String businessCharter;

	private Boolean disabled = false;
	// 是否物资供应商
	private Boolean isMate = false;
	// 是否设备供应商
	private Boolean isEquip = false;
	// 是否药品供应商
	private Boolean isDrug = false;
	// 是否无形资产供应商
	private Boolean isImma = false;

	@Id
	@Column(name = "vendorId",length = 32,nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "vendorCode", length = 20, nullable = false)
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendorTypeId", nullable = false)
	public VendorType getVendorType() {
		return vendorType;
	}

	public void setVendorType(VendorType vendorType) {
		this.vendorType = vendorType;
	}

	@Column(name = "vendorName", length = 100, nullable = false)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name = "shortName", length = 10, nullable = true)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "cncode", length = 100, nullable = false)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@Column(name = "prov", length = 20, nullable = true)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length = 20, nullable = true)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ven_address", length = 30, nullable = true)
	public String getVenAddress() {
		return venAddress;
	}

	public void setVenAddress(String venAddress) {
		this.venAddress = venAddress;
	}

	@Column(name = "ven_postcode", length = 6, nullable = true)
	public String getVenPostCode() {
		return venPostCode;
	}

	public void setVenPostCode(String venPostCode) {
		this.venPostCode = venPostCode;
	}

	@Column(name = "dist_code", length = 6, nullable = true)
	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pay_code", nullable = true)
	public PayMode getPayMode() {
		return payMode;
	}

	public void setPayMode(PayMode payMode) {
		this.payMode = payMode;
	}

	@Column(name = "traf_code", length = 10, nullable = true)
	public String getTrafCode() {
		return trafCode;
	}

	public void setTrafCode(String trafCode) {
		this.trafCode = trafCode;
	}

	@Column(name = "ven_trade", length = 12, nullable = true)
	public String getVenTrade() {
		return venTrade;
	}

	public void setVenTrade(String venTrade) {
		this.venTrade = venTrade;
	}

	@Column(name = "ven_reg_code", length = 18, nullable = true)
	public String getVenRegCode() {
		return venRegCode;
	}

	public void setVenRegCode(String venRegCode) {
		this.venRegCode = venRegCode;
	}

	@Column(name = "ven_bank", length = 40, nullable = true)
	public String getVenBank() {
		return venBank;
	}

	public void setVenBank(String venBank) {
		this.venBank = venBank;
	}

	@Column(name = "ven_bank_account", length = 50, nullable = true)
	public String getVenBankAccount() {
		return venBankAccount;
	}

	public void setVenBankAccount(String venBankAccount) {
		this.venBankAccount = venBankAccount;
	}

	@Column(name = "ven_lperson", length = 20, nullable = true)
	public String getVenLperson() {
		return venLperson;
	}

	public void setVenLperson(String venLperson) {
		this.venLperson = venLperson;
	}

	@Column(name = "ven_dev_date", nullable = true)
	public Date getVenDevDate() {
		return venDevDate;
	}

	public void setVenDevDate(Date venDevDate) {
		this.venDevDate = venDevDate;
	}

	@Column(name = "ven_phone", length = 20, nullable = true)
	public String getVenPhone() {
		return venPhone;
	}

	public void setVenPhone(String venPhone) {
		this.venPhone = venPhone;
	}

	@Column(name = "ven_fax", length = 20, nullable = true)
	public String getVenFax() {
		return venFax;
	}

	public void setVenFax(String venFax) {
		this.venFax = venFax;
	}

	@Column(name = "ven_email", length = 30, nullable = true)
	public String getVenEmail() {
		return venEmail;
	}

	public void setVenEmail(String venEmail) {
		this.venEmail = venEmail;
	}

	@Column(name = "ven_person", length = 20, nullable = true)
	public String getVenPerson() {
		return venPerson;
	}

	public void setVenPerson(String venPerson) {
		this.venPerson = venPerson;
	}

	@Column(name = "ven_mobile", length = 15, nullable = true)
	public String getVenMobile() {
		return venMobile;
	}

	public void setVenMobile(String venMobile) {
		this.venMobile = venMobile;
	}

	@Column(name = "ven_pperson", length = 20, nullable = true)
	public String getVenPperson() {
		return venPperson;
	}

	public void setVenPperson(String venPperson) {
		this.venPperson = venPperson;
	}

	@Column(name = "ven_dis_rate", nullable = true, columnDefinition = "numeric default 0.0 ")
	public Double getVenDisRate() {
		return venDisRate;
	}

	public void setVenDisRate(Double venDisRate) {
		this.venDisRate = venDisRate;
	}

	@Column(name = "ven_cre_grade", length = 4, nullable = true)
	public String getVenCreGrade() {
		return venCreGrade;
	}

	public void setVenCreGrade(String venCreGrade) {
		this.venCreGrade = venCreGrade;
	}

	@Column(name = "ven_cre_line", nullable = true, columnDefinition = "numeric default 0.0 ")
	public Double getVenCreLine() {
		return venCreLine;
	}

	public void setVenCreLine(Double venCreLine) {
		this.venCreLine = venCreLine;
	}

	@Column(name = "ven_cre_date", nullable = true)
	public Integer getVenCreDate() {
		return venCreDate;
	}

	public void setVenCreDate(Integer venCreDate) {
		this.venCreDate = venCreDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ven_pay_con", nullable = true)
	public PayCondition getPayCon() {
		return payCon;
	}

	public void setPayCon(PayCondition payCon) {
		this.payCon = payCon;
	}

	@Column(name = "ven_dir_address", length = 30, nullable = true)
	public String getVenDirAddress() {
		return venDirAddress;
	}

	public void setVenDirAddress(String venDirAddress) {
		this.venDirAddress = venDirAddress;
	}

	@Column(name = "ven_dir_code", length = 2, nullable = true)
	public String getVenDirCode() {
		return venDirCode;
	}

	public void setVenDirCode(String venDirCode) {
		this.venDirCode = venDirCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manageDeptId", nullable = true)
	public Department getManageDeptId() {
		return manageDept;
	}

	public void setManageDeptId(Department manageDept) {
		this.manageDept = manageDept;
	}

	@Column(name = "acc_pay_money", nullable = true)
	public BigDecimal getAccPayMoney() {
		return accPayMoney;
	}

	public void setAccPayMoney(BigDecimal accPayMoney) {
		this.accPayMoney = accPayMoney;
	}

	@Column(name = "last_date", nullable = true)
	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	@Column(name = "last_money", nullable = true)
	public BigDecimal getLastMoney() {
		return lastMoney;
	}

	public void setLastMoney(BigDecimal lastMoney) {
		this.lastMoney = lastMoney;
	}

	@Column(name = "last_pay_date", nullable = true)
	public Date getLastPayDate() {
		return lastPayDate;
	}

	public void setLastPayDate(Date lastPayDate) {
		this.lastPayDate = lastPayDate;
	}

	@Column(name = "last_pay_money", nullable = true)
	public BigDecimal getLastPayMoney() {
		return lastPayMoney;
	}

	public void setLastPayMoney(BigDecimal lastPayMoney) {
		this.lastPayMoney = lastPayMoney;
	}

	@Column(name = "end_date", nullable = true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "frequency", length = 2, nullable = true)
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	@Column(name = "bven_tax", nullable = true, columnDefinition = "bit default 0")
	public Boolean getBvenTax() {
		return bvenTax;
	}

	public void setBvenTax(Boolean bvenTax) {
		this.bvenTax = bvenTax;
	}

	@Column(name = "products", length = 100, nullable = true)
	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "isMate", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsMate() {
		return isMate;
	}

	public void setIsMate(Boolean isMate) {
		this.isMate = isMate;
	}

	@Column(name = "isEquip", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsEquip() {
		return isEquip;
	}

	public void setIsEquip(Boolean isEquip) {
		this.isEquip = isEquip;
	}

	@Column(name = "isDrug", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsDrug() {
		return isDrug;
	}

	public void setIsDrug(Boolean isDrug) {
		this.isDrug = isDrug;
	}

	@Column(name = "isImma", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsImma() {
		return isImma;
	}

	public void setIsImma(Boolean isImma) {
		this.isImma = isImma;
	}

	@Column(name = "business_charter", length = 20, nullable = true)
	public String getBusinessCharter() {
		return businessCharter;
	}

	public void setBusinessCharter(String businessCharter) {
		this.businessCharter = businessCharter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accPayMoney == null) ? 0 : accPayMoney.hashCode());
		result = prime * result
				+ ((businessCharter == null) ? 0 : businessCharter.hashCode());
		result = prime * result + ((bvenTax == null) ? 0 : bvenTax.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((distCode == null) ? 0 : distCode.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((isDrug == null) ? 0 : isDrug.hashCode());
		result = prime * result + ((isEquip == null) ? 0 : isEquip.hashCode());
		result = prime * result + ((isImma == null) ? 0 : isImma.hashCode());
		result = prime * result + ((isMate == null) ? 0 : isMate.hashCode());
		result = prime * result
				+ ((lastDate == null) ? 0 : lastDate.hashCode());
		result = prime * result
				+ ((lastMoney == null) ? 0 : lastMoney.hashCode());
		result = prime * result
				+ ((lastPayDate == null) ? 0 : lastPayDate.hashCode());
		result = prime * result
				+ ((lastPayMoney == null) ? 0 : lastPayMoney.hashCode());
		result = prime * result
				+ ((manageDept == null) ? 0 : manageDept.hashCode());
		result = prime * result + ((payCon == null) ? 0 : payCon.hashCode());
		result = prime * result + ((payMode == null) ? 0 : payMode.hashCode());
		result = prime * result
				+ ((products == null) ? 0 : products.hashCode());
		result = prime * result
				+ ((province == null) ? 0 : province.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result
				+ ((trafCode == null) ? 0 : trafCode.hashCode());
		result = prime * result
				+ ((venAddress == null) ? 0 : venAddress.hashCode());
		result = prime * result + ((venBank == null) ? 0 : venBank.hashCode());
		result = prime * result
				+ ((venBankAccount == null) ? 0 : venBankAccount.hashCode());
		result = prime * result
				+ ((venCreDate == null) ? 0 : venCreDate.hashCode());
		result = prime * result
				+ ((venCreGrade == null) ? 0 : venCreGrade.hashCode());
		result = prime * result
				+ ((venCreLine == null) ? 0 : venCreLine.hashCode());
		result = prime * result
				+ ((venDevDate == null) ? 0 : venDevDate.hashCode());
		result = prime * result
				+ ((venDirAddress == null) ? 0 : venDirAddress.hashCode());
		result = prime * result
				+ ((venDirCode == null) ? 0 : venDirCode.hashCode());
		result = prime * result
				+ ((venDisRate == null) ? 0 : venDisRate.hashCode());
		result = prime * result
				+ ((venEmail == null) ? 0 : venEmail.hashCode());
		result = prime * result + ((venFax == null) ? 0 : venFax.hashCode());
		result = prime * result
				+ ((venLperson == null) ? 0 : venLperson.hashCode());
		result = prime * result
				+ ((venMobile == null) ? 0 : venMobile.hashCode());
		result = prime * result
				+ ((venPerson == null) ? 0 : venPerson.hashCode());
		result = prime * result
				+ ((venPhone == null) ? 0 : venPhone.hashCode());
		result = prime * result
				+ ((venPostCode == null) ? 0 : venPostCode.hashCode());
		result = prime * result
				+ ((venPperson == null) ? 0 : venPperson.hashCode());
		result = prime * result
				+ ((venRegCode == null) ? 0 : venRegCode.hashCode());
		result = prime * result
				+ ((venTrade == null) ? 0 : venTrade.hashCode());
		result = prime * result
				+ ((vendorCode == null) ? 0 : vendorCode.hashCode());
		result = prime * result
				+ ((vendorName == null) ? 0 : vendorName.hashCode());
		result = prime * result
				+ ((vendorType == null) ? 0 : vendorType.hashCode());
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
		Vendor other = (Vendor) obj;
		if (accPayMoney == null) {
			if (other.accPayMoney != null)
				return false;
		} else if (!accPayMoney.equals(other.accPayMoney))
			return false;
		if (businessCharter == null) {
			if (other.businessCharter != null)
				return false;
		} else if (!businessCharter.equals(other.businessCharter))
			return false;
		if (bvenTax == null) {
			if (other.bvenTax != null)
				return false;
		} else if (!bvenTax.equals(other.bvenTax))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (distCode == null) {
			if (other.distCode != null)
				return false;
		} else if (!distCode.equals(other.distCode))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (isDrug == null) {
			if (other.isDrug != null)
				return false;
		} else if (!isDrug.equals(other.isDrug))
			return false;
		if (isEquip == null) {
			if (other.isEquip != null)
				return false;
		} else if (!isEquip.equals(other.isEquip))
			return false;
		if (isImma == null) {
			if (other.isImma != null)
				return false;
		} else if (!isImma.equals(other.isImma))
			return false;
		if (isMate == null) {
			if (other.isMate != null)
				return false;
		} else if (!isMate.equals(other.isMate))
			return false;
		if (lastDate == null) {
			if (other.lastDate != null)
				return false;
		} else if (!lastDate.equals(other.lastDate))
			return false;
		if (lastMoney == null) {
			if (other.lastMoney != null)
				return false;
		} else if (!lastMoney.equals(other.lastMoney))
			return false;
		if (lastPayDate == null) {
			if (other.lastPayDate != null)
				return false;
		} else if (!lastPayDate.equals(other.lastPayDate))
			return false;
		if (lastPayMoney == null) {
			if (other.lastPayMoney != null)
				return false;
		} else if (!lastPayMoney.equals(other.lastPayMoney))
			return false;
		if (manageDept == null) {
			if (other.manageDept != null)
				return false;
		} else if (!manageDept.equals(other.manageDept))
			return false;
		if (payCon == null) {
			if (other.payCon != null)
				return false;
		} else if (!payCon.equals(other.payCon))
			return false;
		if (payMode == null) {
			if (other.payMode != null)
				return false;
		} else if (!payMode.equals(other.payMode))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (trafCode == null) {
			if (other.trafCode != null)
				return false;
		} else if (!trafCode.equals(other.trafCode))
			return false;
		if (venAddress == null) {
			if (other.venAddress != null)
				return false;
		} else if (!venAddress.equals(other.venAddress))
			return false;
		if (venBank == null) {
			if (other.venBank != null)
				return false;
		} else if (!venBank.equals(other.venBank))
			return false;
		if (venBankAccount == null) {
			if (other.venBankAccount != null)
				return false;
		} else if (!venBankAccount.equals(other.venBankAccount))
			return false;
		if (venCreDate == null) {
			if (other.venCreDate != null)
				return false;
		} else if (!venCreDate.equals(other.venCreDate))
			return false;
		if (venCreGrade == null) {
			if (other.venCreGrade != null)
				return false;
		} else if (!venCreGrade.equals(other.venCreGrade))
			return false;
		if (venCreLine == null) {
			if (other.venCreLine != null)
				return false;
		} else if (!venCreLine.equals(other.venCreLine))
			return false;
		if (venDevDate == null) {
			if (other.venDevDate != null)
				return false;
		} else if (!venDevDate.equals(other.venDevDate))
			return false;
		if (venDirAddress == null) {
			if (other.venDirAddress != null)
				return false;
		} else if (!venDirAddress.equals(other.venDirAddress))
			return false;
		if (venDirCode == null) {
			if (other.venDirCode != null)
				return false;
		} else if (!venDirCode.equals(other.venDirCode))
			return false;
		if (venDisRate == null) {
			if (other.venDisRate != null)
				return false;
		} else if (!venDisRate.equals(other.venDisRate))
			return false;
		if (venEmail == null) {
			if (other.venEmail != null)
				return false;
		} else if (!venEmail.equals(other.venEmail))
			return false;
		if (venFax == null) {
			if (other.venFax != null)
				return false;
		} else if (!venFax.equals(other.venFax))
			return false;
		if (venLperson == null) {
			if (other.venLperson != null)
				return false;
		} else if (!venLperson.equals(other.venLperson))
			return false;
		if (venMobile == null) {
			if (other.venMobile != null)
				return false;
		} else if (!venMobile.equals(other.venMobile))
			return false;
		if (venPerson == null) {
			if (other.venPerson != null)
				return false;
		} else if (!venPerson.equals(other.venPerson))
			return false;
		if (venPhone == null) {
			if (other.venPhone != null)
				return false;
		} else if (!venPhone.equals(other.venPhone))
			return false;
		if (venPostCode == null) {
			if (other.venPostCode != null)
				return false;
		} else if (!venPostCode.equals(other.venPostCode))
			return false;
		if (venPperson == null) {
			if (other.venPperson != null)
				return false;
		} else if (!venPperson.equals(other.venPperson))
			return false;
		if (venRegCode == null) {
			if (other.venRegCode != null)
				return false;
		} else if (!venRegCode.equals(other.venRegCode))
			return false;
		if (venTrade == null) {
			if (other.venTrade != null)
				return false;
		} else if (!venTrade.equals(other.venTrade))
			return false;
		if (vendorCode == null) {
			if (other.vendorCode != null)
				return false;
		} else if (!vendorCode.equals(other.vendorCode))
			return false;
		if (vendorName == null) {
			if (other.vendorName != null)
				return false;
		} else if (!vendorName.equals(other.vendorName))
			return false;
		if (vendorType == null) {
			if (other.vendorType != null)
				return false;
		} else if (!vendorType.equals(other.vendorType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CopyOfVendor [vendorId=" + vendorId + ", vendorCode="
				+ vendorCode + ", vendorType=" + vendorType + ", vendorName="
				+ vendorName + ", shortName=" + shortName + ", cnCode="
				+ cnCode + ", province=" + province + ", city=" + city
				+ ", venAddress=" + venAddress + ", venPostCode=" + venPostCode
				+ ", distCode=" + distCode + ", trafCode=" + trafCode
				+ ", venTrade=" + venTrade + ", venRegCode=" + venRegCode
				+ ", venBank=" + venBank + ", venBankAccount=" + venBankAccount
				+ ", venPperson=" + venPperson + ", venLperson=" + venLperson
				+ ", venDevDate=" + venDevDate + ", venPhone=" + venPhone
				+ ", venFax=" + venFax + ", venEmail=" + venEmail
				+ ", venPerson=" + venPerson + ", venMobile=" + venMobile
				+ ", venDisRate=" + venDisRate + ", venCreGrade=" + venCreGrade
				+ ", venCreLine=" + venCreLine + ", venCreDate=" + venCreDate
				+ ", payMode=" + payMode + ", payCon=" + payCon
				+ ", venDirAddress=" + venDirAddress + ", venDirCode="
				+ venDirCode + ", manageDept=" + manageDept + ", accPayMoney="
				+ accPayMoney + ", lastDate=" + lastDate + ", lastMoney="
				+ lastMoney + ", lastPayDate=" + lastPayDate
				+ ", lastPayMoney=" + lastPayMoney + ", endDate=" + endDate
				+ ", products=" + products + ", frequency=" + frequency
				+ ", bvenTax=" + bvenTax + ", businessCharter="
				+ businessCharter + ", disabled=" + disabled + ", isMate="
				+ isMate + ", isEquip=" + isEquip + ", isDrug=" + isDrug
				+ ", isImma=" + isImma + "]";
	}

}
