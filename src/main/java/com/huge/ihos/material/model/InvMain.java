package com.huge.ihos.material.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 材料字典
 * 
 * @author gaozhengyang
 * @date 2013-11-2
 */
@Entity
@Table(name = "mm_main")
public class InvMain extends BaseObject implements Serializable,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4023412235512428492L;

	private String ioId;						//出入库单据主键
	
	private String orgCode;						// OrgCode 单位编码
	
	private String copyCode;					// copyCode 账套编码
	
	private String yearMonth;					// yearMonth char 6 会计期间，以填制单据日期所在期间自动赋值
	
	private String ioBillNumber;				// Io_no Varchar 20 单据号，按规则生成，不能修改，不能断号
	
	private String ioType;						// InOut Char 1 入库 1 出库 2
	
	private MmBusinessType busType;				// BusTypeCode Varchar 10 业务类型编码，取自mm_businessType
	//private String busTypeCode;					// BusTypeCode Varchar 10 业务类型编码，取自mm_businessType
	
	//private String busTypeName;					//	busTypeName 业务类型名称
	
	private ProcType proctypeCode;				// ProctypeCode 采购类型，取自mm_proctype
	
	private Department buyDept;					// BuyDeptid Varchar 20 √ 采购科室id
	
	private Person buyPerson;					// BuyId Varchar 20 √ 采购员personid

	private String remark;						// Remark Varchar 100 √ 摘要
	
	private Boolean isInit = false;				// IsInit Bit 1 √ 是否初始账
	
	private Store store;						// StoreId Varchar 30 仓库id
	
	private StorePosition inStorePosition;		// InPosid Varchar 30 √ 入库货位id
	
	private StorePosition outStorePosition;		// OutPosid Varchar 30 √ 出库货位id
	
	private Department applyDept;				// Deptid Varchar 30 √ 领用科室id ，针对出库
	
	private Person applyPersion;				// Personid Varchar 30 √ 领用人personid

	private String vendorId;					// VendorId Varchar 30 供应商id
	
	private String vendorName;					// Vendor Varchar 30 供应商名称，考虑到名称有可能后来修改
	
	private String vendorPersion;				// VenorPerson Varchar 30 √ 供货商经办人
	
	private String factory;						// FactoryId Varchar 30 √ 生产厂商id
	
	private String status = "0";				// Char Char 1 状态：0 新建 1 已审核 2 已确认 3 已记帐（生成凭证）
	
	private Person makePerson;					// MakeId Varchar 30 √ 填制人 PERSONID
	
	private Date makeDate;						// MakeDate DATETIME √ 填制时间
	
	private Person checkPerson;					// CheckId Varchar 30 √ 审核人
	
	private Date checkDate;						// CheckDate DATETIME √ 审核时间

	private Person confirmPerson;				// ConfirmId Varchar 30 √ 确认人
	
	private Date confirmDate;					// ConfirmDate DATETIME √ 确认时间

	private Person accountPerson;				// accountId Varchar 30 √ 记帐人（生成凭证）
	
	private Date accountDate;					// AccountDate DATETIME √ 记帐时间（生成凭证）

	private Person qaPerson;					// QaId Varchar 30 √ 质检人

	private Date qaDate;						// QaDate DATETIME √ 质检时间

	private String patientSex;					// PatientSex Char 1 √ 病人性别 0 男 1 女

	private String hospitalNo;					// hospital_no nvarchar 10 √ 病人住院号

	private String bedNo;						// BedNo nvarchar 10 √ 床位号

	private String doctorId;					// doctorid Varchar 20 √ 医生id

	private String doctoraName;					// doctorname Varchar 20 √ 医生姓名
	
	private String stockTypeCode;				// stock_type_code nvarchar 10 √

	private String exchIo;						// exch_Io_no Varchar 20 √

	private Store exchStoreId;					// Exch_storeid Varchar 30 √

	private String docId;						// Doc_id Varchar 20 √ 文档id
	
	private String patientName;					// PatientName Varchar 20 √ 病人姓名

	private Date operationDate;					// operationDate Datetime √ 手术时间

	private String appDeptId;					// AppDeptid Varchar 20 √ 业务科室id

	private String appId;						// AppId Varchar 20 √ 业务id

	private Boolean isHis = false;				// IsHIS bit 1 √ 0 是否HIS接口数据

	private Boolean discountFlag = false;		// Discount_flag bit 1 √ 1

	private InvUse invUse;						// InvUseId varchar 10 物资用途id，取自 mm_InvUse

	private String allotCompany;				// company Varchar 30 √ 调拨单位

	private Department usedDept;				// UsedeptId Varchar 30 √ 使用科室id（出库）

	private Boolean isProj = false;				// isProj Bit 1 0 是否项目，不显示

	private String projId;						// ProjId Varchar 20 √ 项目id，取GL_PROJ

	private String invoiceNo;					// InvoiceNo Varchar 20 √ 发票号
	
	private Date invoiceDate;					// InvoiceDate datetime √ 发票日期

	private Boolean isPay = false;				// IsPay Bit 1 √ 付款标志

	private Double payMoney;					// PayMoney Numeric 16,4 √ 付款金额

	private Boolean isPayAll = false;			// IsPayall Bit 1 √ 是否付完款

	private String payModeCode;					// PayModeCode Varchar 10 √ 付款方式，取GL_payMode

	private String chequeTypeCode;				// ChequeTypeCode Varchar 10 √ 支票类型编码

	private String orderNo;						// OrderNo Varchar 30 √ 订单号
	
	private Set<InvDetail> invDetails;			//出入库详情
	private String docTemId;					//关联模板id

	@Column(name = "docTemId", length = 32, nullable = true)
	public String getDocTemId() {
		return docTemId;
	}

	public void setDocTemId(String docTemId) {
		this.docTemId = docTemId;
	}

	private Double totalMoney = 0.0;
	
	@Column(name = "totalMoney", nullable = false)
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	/*@Transient
	public Double getTotleMoney() {
		Double totle = 0.0d;
		for(InvDetail inv: invDetails){
			totle += inv.getInMoney()==null?0.0d:inv.getInMoney();
		}
		return totle;
	}

	public void setTotleMoney(Double totleMoney) {
		this.totleMoney = totleMoney;
	}*/

	@Id
	@Column(name = "io_id", length = 32, nullable = false)
	/*
	 * @GeneratedValue(generator = "hibernate-uuid")
	 * 
	 * @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	 */
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getIoId() {
		return ioId;
	}

	public void setIoId(String ioId) {
		this.ioId = ioId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "typeCode", nullable = true)
	public MmBusinessType getBusType() {
		return busType;
	}

	public void setBusType(MmBusinessType busType) {
		this.busType = busType;
	}

	@Column(name = "orgCode", length = 20, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	@Column(name = "copyCode", length = 20, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}



	@Column(name = "yearMonth", length = 6, nullable = false)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	

	@Column(name = "id_bill_no", length = 30, nullable = false)
	public String getIoBillNumber() {
		return ioBillNumber;
	}

	public void setIoBillNumber(String ioBillNumber) {
		this.ioBillNumber = ioBillNumber;
	}

	

	@Column(name = "ioType", length = 1, nullable = false)
	public String getIoType() {
		return ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	

	/*@Column(name = "busTypeCode", length = 10, nullable = false)
	public String getBusTypeCode() {
		return busTypeCode;
	}

	public void setBusTypeCode(String busTypeCode) {
		this.busTypeCode = busTypeCode;
	}
*/
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "proctypeCode", nullable = true)
	public ProcType getProctypeCode() {
		return proctypeCode;
	}

	public void setProctypeCode(ProcType proctypeCode) {
		this.proctypeCode = proctypeCode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buyDeptId", nullable = true)
	public Department getBuyDept() {
		return buyDept;
	}

	public void setBuyDept(Department buyDept) {
		this.buyDept = buyDept;
	}

	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buyPersonId", nullable = true)
	public Person getBuyPerson() {
		return buyPerson;
	}

	public void setBuyPerson(Person buyPerson) {
		this.buyPerson = buyPerson;
	}



	@Column(name = "remark", length = 100, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	@Column(name = "isInit", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsInit() {
		return isInit;
	}

	public void setIsInit(Boolean isInit) {
		this.isInit = isInit;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "store_id", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}



	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "inPos_id", nullable = true)
	public StorePosition getInStorePosition() {
		return inStorePosition;
	}

	public void setInStorePosition(StorePosition inStorePosition) {
		this.inStorePosition = inStorePosition;
	}



	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "outPos_id", nullable = true)
	public StorePosition getOutStorePosition() {
		return outStorePosition;
	}

	public void setOutStorePosition(StorePosition outStorePosition) {
		this.outStorePosition = outStorePosition;
	}



	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "apply_dept_id", nullable = true)
	public Department getApplyDept() {
		return applyDept;
	}

	public void setApplyDept(Department applyDept) {
		this.applyDept = applyDept;
	}

	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "apply_person_id", nullable = true)
	public Person getApplyPersion() {
		return applyPersion;
	}

	public void setApplyPersion(Person applyPersion) {
		this.applyPersion = applyPersion;
	}

	

	@Column(name = "vendorId", length = 32, nullable = true)
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	

	@Column(name = "vendorName", length = 30, nullable = true)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	@Column(name = "vendorPersion", length = 30, nullable = true)
	public String getVendorPersion() {
		return vendorPersion;
	}

	public void setVendorPersion(String vendorPersion) {
		this.vendorPersion = vendorPersion;
	}

	@Column(name = "factory", length = 30, nullable = true)
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}


	@Column(name = "status", length = 1, nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "make_person_id", nullable = true)
	public Person getMakePerson() {
		return makePerson;
	}

	public void setMakePerson(Person makePerson) {
		this.makePerson = makePerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "check_person_id", nullable = true)
	public Person getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Person checkPerson) {
		this.checkPerson = checkPerson;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "confirm_person_id", nullable = true)
	public Person getConfirmPerson() {
		return confirmPerson;
	}

	public void setConfirmPerson(Person confirmPerson) {
		this.confirmPerson = confirmPerson;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirmDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_person_id", nullable = true)
	public Person getAccountPerson() {
		return accountPerson;
	}

	public void setAccountPerson(Person accountPerson) {
		this.accountPerson = accountPerson;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "accountDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "qa_person_id", nullable = true)
	public Person getQaPerson() {
		return qaPerson;
	}

	public void setQaPerson(Person qaPerson) {
		this.qaPerson = qaPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "qaDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getQaDate() {
		return qaDate;
	}

	public void setQaDate(Date qaDate) {
		this.qaDate = qaDate;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "used_dept_id", nullable = true)
	public Department getUsedDept() {
		return usedDept;
	}

	public void setUsedDept(Department usedDept) {
		this.usedDept = usedDept;
	}


	@Column(name = "isProj", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsProj() {
		return isProj;
	}

	public void setIsProj(Boolean isProj) {
		this.isProj = isProj;
	}


	@Column(name = "projId", length = 20, nullable = true)
	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}


	@Column(name = "invoiceNo", length = 20, nullable = true)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invoiceDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	@Column(name = "isPay", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsPay() {
		return isPay;
	}

	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}


	@Column(name = "payMoney", nullable = true)
	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	@Column(name = "isPayAll", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsPayAll() {
		return isPayAll;
	}

	public void setIsPayAll(Boolean isPayAll) {
		this.isPayAll = isPayAll;
	}


	@Column(name = "payModeCode", length = 10, nullable = true)
	public String getPayModeCode() {
		return payModeCode;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	@Column(name = "chequeTypeCode", length = 10, nullable = true)
	public String getChequeTypeCode() {
		return chequeTypeCode;
	}

	public void setChequeTypeCode(String chequeTypeCode) {
		this.chequeTypeCode = chequeTypeCode;
	}


	@Column(name = "orderNo", length = 30, nullable = true)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	@Column(name = "allotCompany", length = 30, nullable = true)
	public String getAllotCompany() {
		return allotCompany;
	}

	public void setAllotCompany(String allotCompany) {
		this.allotCompany = allotCompany;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "invUse_id", nullable = true)
	public InvUse getInvUse() {
		return invUse;
	}

	public void setInvUse(InvUse invUse) {
		this.invUse = invUse;
	}


	@Column(name = "discountFlag", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDiscountFlag() {
		return discountFlag;
	}

	public void setDiscountFlag(Boolean discountFlag) {
		this.discountFlag = discountFlag;
	}


	@Column(name = "isHis", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsHis() {
		return isHis;
	}

	public void setIsHis(Boolean isHis) {
		this.isHis = isHis;
	}

	@Column(name = "appId", length = 20, nullable = true)
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}


	@Column(name = "appDeptId", length = 20, nullable = true)
	public String getAppDeptId() {
		return appDeptId;
	}

	public void setAppDeptId(String appDeptId) {
		this.appDeptId = appDeptId;
	}


	@Temporal(TemporalType.DATE)
	@Column(name = "operationDate", length = 19, nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	
	@Column(name = "patientName", length = 20, nullable = true)
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	@Column(name = "patientSex", length = 1, nullable = true)
	public String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}

	@Column(name = "hospitalNo", length = 10, nullable = true)
	public String getHospitalNo() {
		return hospitalNo;
	}

	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}

	@Column(name = "bedNo", length = 10, nullable = true)
	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	@Column(name = "doctorId", length = 20, nullable = true)
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "doctoraName", length = 20, nullable = true)
	public String getDoctoraName() {
		return doctoraName;
	}

	public void setDoctoraName(String doctoraName) {
		this.doctoraName = doctoraName;
	}

	@Column(name = "stockTypeCode", length = 10, nullable = true)
	public String getStockTypeCode() {
		return stockTypeCode;
	}

	public void setStockTypeCode(String stockTypeCode) {
		this.stockTypeCode = stockTypeCode;
	}

	@Column(name = "exchIo", length = 20, nullable = true)
	public String getExchIo() {
		return exchIo;
	}

	public void setExchIo(String exchIo) {
		this.exchIo = exchIo;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "exchStoreId")
	public Store getExchStoreId() {
		return exchStoreId;
	}

	public void setExchStoreId(Store exchStoreId) {
		this.exchStoreId = exchStoreId;
	}

	@Column(name = "docId", length = 20, nullable = true)
	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	/*@Column(name = "busTypeName", length = 50, nullable = true)
	public String getBusTypeName() {
		return busTypeName;
	}

	public void setBusTypeName(String busTypeName) {
		this.busTypeName = busTypeName;
	}*/

	@OneToMany( cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "invMain" )
	public Set<InvDetail> getInvDetails() {
		return invDetails;
	}

	public void setInvDetails(Set<InvDetail> invDetails) {
		this.invDetails = invDetails;
	}

	public Object clone(){
        InvMain o = null;
        try{
            o = (InvMain)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountDate == null) ? 0 : accountDate.hashCode());
		result = prime * result
				+ ((accountPerson == null) ? 0 : accountPerson.hashCode());
		result = prime * result
				+ ((allotCompany == null) ? 0 : allotCompany.hashCode());
		result = prime * result
				+ ((appDeptId == null) ? 0 : appDeptId.hashCode());
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result
				+ ((applyDept == null) ? 0 : applyDept.hashCode());
		result = prime * result
				+ ((applyPersion == null) ? 0 : applyPersion.hashCode());
		result = prime * result + ((bedNo == null) ? 0 : bedNo.hashCode());
		result = prime * result + ((busType == null) ? 0 : busType.hashCode());
		result = prime * result + ((buyDept == null) ? 0 : buyDept.hashCode());
		result = prime * result
				+ ((buyPerson == null) ? 0 : buyPerson.hashCode());
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result
				+ ((checkPerson == null) ? 0 : checkPerson.hashCode());
		result = prime * result
				+ ((chequeTypeCode == null) ? 0 : chequeTypeCode.hashCode());
		result = prime * result
				+ ((confirmDate == null) ? 0 : confirmDate.hashCode());
		result = prime * result
				+ ((confirmPerson == null) ? 0 : confirmPerson.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((discountFlag == null) ? 0 : discountFlag.hashCode());
		result = prime * result + ((docId == null) ? 0 : docId.hashCode());
		result = prime * result
				+ ((docTemId == null) ? 0 : docTemId.hashCode());
		result = prime * result
				+ ((doctorId == null) ? 0 : doctorId.hashCode());
		result = prime * result
				+ ((doctoraName == null) ? 0 : doctoraName.hashCode());
		result = prime * result + ((exchIo == null) ? 0 : exchIo.hashCode());
		result = prime * result
				+ ((exchStoreId == null) ? 0 : exchStoreId.hashCode());
		result = prime * result + ((factory == null) ? 0 : factory.hashCode());
		result = prime * result
				+ ((hospitalNo == null) ? 0 : hospitalNo.hashCode());
		result = prime * result
				+ ((inStorePosition == null) ? 0 : inStorePosition.hashCode());
		result = prime * result + ((invUse == null) ? 0 : invUse.hashCode());
		result = prime * result
				+ ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result
				+ ((invoiceNo == null) ? 0 : invoiceNo.hashCode());
		result = prime * result
				+ ((ioBillNumber == null) ? 0 : ioBillNumber.hashCode());
		result = prime * result + ((ioId == null) ? 0 : ioId.hashCode());
		result = prime * result + ((ioType == null) ? 0 : ioType.hashCode());
		result = prime * result + ((isHis == null) ? 0 : isHis.hashCode());
		result = prime * result + ((isInit == null) ? 0 : isInit.hashCode());
		result = prime * result + ((isPay == null) ? 0 : isPay.hashCode());
		result = prime * result
				+ ((isPayAll == null) ? 0 : isPayAll.hashCode());
		result = prime * result + ((isProj == null) ? 0 : isProj.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result
				+ ((makePerson == null) ? 0 : makePerson.hashCode());
		result = prime * result
				+ ((operationDate == null) ? 0 : operationDate.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime
				* result
				+ ((outStorePosition == null) ? 0 : outStorePosition.hashCode());
		result = prime * result
				+ ((patientName == null) ? 0 : patientName.hashCode());
		result = prime * result
				+ ((patientSex == null) ? 0 : patientSex.hashCode());
		result = prime * result
				+ ((payModeCode == null) ? 0 : payModeCode.hashCode());
		result = prime * result
				+ ((payMoney == null) ? 0 : payMoney.hashCode());
		result = prime * result
				+ ((proctypeCode == null) ? 0 : proctypeCode.hashCode());
		result = prime * result + ((projId == null) ? 0 : projId.hashCode());
		result = prime * result + ((qaDate == null) ? 0 : qaDate.hashCode());
		result = prime * result
				+ ((qaPerson == null) ? 0 : qaPerson.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((stockTypeCode == null) ? 0 : stockTypeCode.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result
				+ ((totalMoney == null) ? 0 : totalMoney.hashCode());
		result = prime * result
				+ ((usedDept == null) ? 0 : usedDept.hashCode());
		result = prime * result
				+ ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result
				+ ((vendorName == null) ? 0 : vendorName.hashCode());
		result = prime * result
				+ ((vendorPersion == null) ? 0 : vendorPersion.hashCode());
		result = prime * result
				+ ((yearMonth == null) ? 0 : yearMonth.hashCode());
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
		InvMain other = (InvMain) obj;
		if (accountDate == null) {
			if (other.accountDate != null)
				return false;
		} else if (!accountDate.equals(other.accountDate))
			return false;
		if (accountPerson == null) {
			if (other.accountPerson != null)
				return false;
		} else if (!accountPerson.equals(other.accountPerson))
			return false;
		if (allotCompany == null) {
			if (other.allotCompany != null)
				return false;
		} else if (!allotCompany.equals(other.allotCompany))
			return false;
		if (appDeptId == null) {
			if (other.appDeptId != null)
				return false;
		} else if (!appDeptId.equals(other.appDeptId))
			return false;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (applyDept == null) {
			if (other.applyDept != null)
				return false;
		} else if (!applyDept.equals(other.applyDept))
			return false;
		if (applyPersion == null) {
			if (other.applyPersion != null)
				return false;
		} else if (!applyPersion.equals(other.applyPersion))
			return false;
		if (bedNo == null) {
			if (other.bedNo != null)
				return false;
		} else if (!bedNo.equals(other.bedNo))
			return false;
		if (busType == null) {
			if (other.busType != null)
				return false;
		} else if (!busType.equals(other.busType))
			return false;
		if (buyDept == null) {
			if (other.buyDept != null)
				return false;
		} else if (!buyDept.equals(other.buyDept))
			return false;
		if (buyPerson == null) {
			if (other.buyPerson != null)
				return false;
		} else if (!buyPerson.equals(other.buyPerson))
			return false;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (checkPerson == null) {
			if (other.checkPerson != null)
				return false;
		} else if (!checkPerson.equals(other.checkPerson))
			return false;
		if (chequeTypeCode == null) {
			if (other.chequeTypeCode != null)
				return false;
		} else if (!chequeTypeCode.equals(other.chequeTypeCode))
			return false;
		if (confirmDate == null) {
			if (other.confirmDate != null)
				return false;
		} else if (!confirmDate.equals(other.confirmDate))
			return false;
		if (confirmPerson == null) {
			if (other.confirmPerson != null)
				return false;
		} else if (!confirmPerson.equals(other.confirmPerson))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (discountFlag == null) {
			if (other.discountFlag != null)
				return false;
		} else if (!discountFlag.equals(other.discountFlag))
			return false;
		if (docId == null) {
			if (other.docId != null)
				return false;
		} else if (!docId.equals(other.docId))
			return false;
		if (docTemId == null) {
			if (other.docTemId != null)
				return false;
		} else if (!docTemId.equals(other.docTemId))
			return false;
		if (doctorId == null) {
			if (other.doctorId != null)
				return false;
		} else if (!doctorId.equals(other.doctorId))
			return false;
		if (doctoraName == null) {
			if (other.doctoraName != null)
				return false;
		} else if (!doctoraName.equals(other.doctoraName))
			return false;
		if (exchIo == null) {
			if (other.exchIo != null)
				return false;
		} else if (!exchIo.equals(other.exchIo))
			return false;
		if (exchStoreId == null) {
			if (other.exchStoreId != null)
				return false;
		} else if (!exchStoreId.equals(other.exchStoreId))
			return false;
		if (factory == null) {
			if (other.factory != null)
				return false;
		} else if (!factory.equals(other.factory))
			return false;
		if (hospitalNo == null) {
			if (other.hospitalNo != null)
				return false;
		} else if (!hospitalNo.equals(other.hospitalNo))
			return false;
		if (inStorePosition == null) {
			if (other.inStorePosition != null)
				return false;
		} else if (!inStorePosition.equals(other.inStorePosition))
			return false;
		if (invUse == null) {
			if (other.invUse != null)
				return false;
		} else if (!invUse.equals(other.invUse))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceNo == null) {
			if (other.invoiceNo != null)
				return false;
		} else if (!invoiceNo.equals(other.invoiceNo))
			return false;
		if (ioBillNumber == null) {
			if (other.ioBillNumber != null)
				return false;
		} else if (!ioBillNumber.equals(other.ioBillNumber))
			return false;
		if (ioId == null) {
			if (other.ioId != null)
				return false;
		} else if (!ioId.equals(other.ioId))
			return false;
		if (ioType == null) {
			if (other.ioType != null)
				return false;
		} else if (!ioType.equals(other.ioType))
			return false;
		if (isHis == null) {
			if (other.isHis != null)
				return false;
		} else if (!isHis.equals(other.isHis))
			return false;
		if (isInit == null) {
			if (other.isInit != null)
				return false;
		} else if (!isInit.equals(other.isInit))
			return false;
		if (isPay == null) {
			if (other.isPay != null)
				return false;
		} else if (!isPay.equals(other.isPay))
			return false;
		if (isPayAll == null) {
			if (other.isPayAll != null)
				return false;
		} else if (!isPayAll.equals(other.isPayAll))
			return false;
		if (isProj == null) {
			if (other.isProj != null)
				return false;
		} else if (!isProj.equals(other.isProj))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (makePerson == null) {
			if (other.makePerson != null)
				return false;
		} else if (!makePerson.equals(other.makePerson))
			return false;
		if (operationDate == null) {
			if (other.operationDate != null)
				return false;
		} else if (!operationDate.equals(other.operationDate))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (outStorePosition == null) {
			if (other.outStorePosition != null)
				return false;
		} else if (!outStorePosition.equals(other.outStorePosition))
			return false;
		if (patientName == null) {
			if (other.patientName != null)
				return false;
		} else if (!patientName.equals(other.patientName))
			return false;
		if (patientSex == null) {
			if (other.patientSex != null)
				return false;
		} else if (!patientSex.equals(other.patientSex))
			return false;
		if (payModeCode == null) {
			if (other.payModeCode != null)
				return false;
		} else if (!payModeCode.equals(other.payModeCode))
			return false;
		if (payMoney == null) {
			if (other.payMoney != null)
				return false;
		} else if (!payMoney.equals(other.payMoney))
			return false;
		if (proctypeCode == null) {
			if (other.proctypeCode != null)
				return false;
		} else if (!proctypeCode.equals(other.proctypeCode))
			return false;
		if (projId == null) {
			if (other.projId != null)
				return false;
		} else if (!projId.equals(other.projId))
			return false;
		if (qaDate == null) {
			if (other.qaDate != null)
				return false;
		} else if (!qaDate.equals(other.qaDate))
			return false;
		if (qaPerson == null) {
			if (other.qaPerson != null)
				return false;
		} else if (!qaPerson.equals(other.qaPerson))
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
		if (stockTypeCode == null) {
			if (other.stockTypeCode != null)
				return false;
		} else if (!stockTypeCode.equals(other.stockTypeCode))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		if (totalMoney == null) {
			if (other.totalMoney != null)
				return false;
		} else if (!totalMoney.equals(other.totalMoney))
			return false;
		if (usedDept == null) {
			if (other.usedDept != null)
				return false;
		} else if (!usedDept.equals(other.usedDept))
			return false;
		if (vendorId == null) {
			if (other.vendorId != null)
				return false;
		} else if (!vendorId.equals(other.vendorId))
			return false;
		if (vendorName == null) {
			if (other.vendorName != null)
				return false;
		} else if (!vendorName.equals(other.vendorName))
			return false;
		if (vendorPersion == null) {
			if (other.vendorPersion != null)
				return false;
		} else if (!vendorPersion.equals(other.vendorPersion))
			return false;
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvMain [ioId=" + ioId + ", orgCode=" + orgCode + ", copyCode="
				+ copyCode + ", yearMonth=" + yearMonth + ", ioBillNumber="
				+ ioBillNumber + ", ioType=" + ioType + ", busType=" + busType
				+ ", proctypeCode=" + proctypeCode + ", buyDept=" + buyDept
				+ ", buyPerson=" + buyPerson + ", remark=" + remark
				+ ", isInit=" + isInit + ", store=" + store
				+ ", inStorePosition=" + inStorePosition
				+ ", outStorePosition=" + outStorePosition + ", applyDept="
				+ applyDept + ", applyPersion=" + applyPersion + ", vendorId="
				+ vendorId + ", vendorName=" + vendorName + ", vendorPersion="
				+ vendorPersion + ", factory=" + factory + ", status=" + status
				+ ", makePerson=" + makePerson + ", makeDate=" + makeDate
				+ ", checkPerson=" + checkPerson + ", checkDate=" + checkDate
				+ ", confirmPerson=" + confirmPerson + ", confirmDate="
				+ confirmDate + ", accountPerson=" + accountPerson
				+ ", accountDate=" + accountDate + ", qaPerson=" + qaPerson
				+ ", qaDate=" + qaDate + ", patientSex=" + patientSex
				+ ", hospitalNo=" + hospitalNo + ", bedNo=" + bedNo
				+ ", doctorId=" + doctorId + ", doctoraName=" + doctoraName
				+ ", stockTypeCode=" + stockTypeCode + ", exchIo=" + exchIo
				+ ", exchStoreId=" + exchStoreId + ", docId=" + docId
				+ ", patientName=" + patientName + ", operationDate="
				+ operationDate + ", appDeptId=" + appDeptId + ", appId="
				+ appId + ", isHis=" + isHis + ", discountFlag=" + discountFlag
				+ ", invUse=" + invUse + ", allotCompany=" + allotCompany
				+ ", usedDept=" + usedDept + ", isProj=" + isProj + ", projId="
				+ projId + ", invoiceNo=" + invoiceNo + ", invoiceDate="
				+ invoiceDate + ", isPay=" + isPay + ", payMoney=" + payMoney
				+ ", isPayAll=" + isPayAll + ", payModeCode=" + payModeCode
				+ ", chequeTypeCode=" + chequeTypeCode + ", orderNo=" + orderNo
				+ ", docTemId=" + docTemId + ", totalMoney=" + totalMoney + "]";
	}
	
	
}
