package com.huge.ihos.material.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.model.BaseObject;

@Entity
@Table(name = "mm_inv_dict")
public class InventoryDict extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3954995609105411581L;
	private String invId;

	// InvId varchar 30
	@Id
	@Column(name = "invId", length = 30, nullable = false)
	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	// orgCode varchar 10 单位编码
	private String orgCode;

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	// Copycode varchar 10 账套编码
	private String copyCode;

	@Column(name = "copyCode", length = 10, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	// InvCode varchar 20 材料编码：根据规则，可手工修改
	private String invCode;

	@Column(name = "invCode", length = 20, nullable = false)
	public String getInvCode() {
		return invCode;
	}

	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}

	// Mlevel Int 管理级别 1,2,3
	private Integer mLevel;

	@Column(name = "mLevel", nullable = false, columnDefinition = "int default 1")
	public Integer getMLevel() {
		return mLevel;
	}

	public void setMLevel(Integer mLevel) {
		this.mLevel = mLevel;
	}

	// InvName varchar 100 材料名称
	private String invName;

	@Column(name = "invName", length = 100, nullable = false)
	public String getInvName() {
		return invName;
	}

	public void setInvName(String invName) {
		this.invName = invName;
	}

	// Invalias varchar 100 √ 别名
	private String ivAlias;

	@Column(name = "ivAlias", length = 100, nullable = true)
	public String getIvAlias() {
		return ivAlias;
	}

	public void setIvAlias(String ivAlias) {
		this.ivAlias = ivAlias;
	}

	// InvModel Varchar 100 √ 型号规格
	private String invModel;

	@Column(name = "invModel", length = 100, nullable = true)
	public String getInvModel() {
		return invModel;
	}

	public void setInvModel(String invModel) {
		this.invModel = invModel;
	}

	// unitcode Varchar 计量单位
	private String firstUnit;

	@Column(name = "firstUnit", length = 100, nullable = true)
	public String getFirstUnit() {
		return firstUnit;
	}

	public void setFirstUnit(String unitCodeFirst) {
		this.firstUnit = unitCodeFirst;
	}

	// Unitcode_f Varchar √ 辅助计量单位
	private String secondUnit;

	@Column(name = "secondUnit", length = 100, nullable = true)
	public String getSecondUnit() {
		return secondUnit;
	}

	public void setSecondUnit(String unitCodeSecond) {
		this.secondUnit = unitCodeSecond;
	}

	// unitrate Int √ 换算率：辅助计量单位=计量单位*换算率
	private Integer unitRate;

	@Column(name = "unitRate", nullable = true)
	public Integer getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(Integer unitRate) {
		this.unitRate = unitRate;
	}

	// InvtypeId varchar 30 物资类别id，取物资类别字典
	private InventoryType inventoryType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invType_id", nullable = false)
	public InventoryType getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(InventoryType materialCategory) {
		this.inventoryType = materialCategory;
	}

	// VendorId Varchar 30 √ 主要供货单位id，取 往来单位字典
	private Vendor vendor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id", nullable = true)
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	// factory Varchar 30 √ 生产厂商
	private String factory;

	@Column(name = "factory", length = 100, nullable = true)
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	// cncode varchar 100 √ 拼音码，自动生成
	private String cnCode;

	@Column(name = "cnCode", length = 30, nullable = true)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	// attrcode Int 0 物资属性： 0常规，1植入，2 接触
	private Integer invAttrCode;

	@Column(name = "invAttrCode", nullable = false, columnDefinition = "int default 0")
	public Integer getInvAttrCode() {
		return invAttrCode;
	}

	public void setInvAttrCode(Integer invAttrCode) {
		this.invAttrCode = invAttrCode;
	}

	// is_batch bit 1 1 是否批次管理
	private Boolean isBatch = true;

	@Column(name = "isBatch", nullable = false, columnDefinition = "bit default 1")
	public Boolean getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(Boolean isBatch) {
		this.isBatch = isBatch;
	}

	// Is_bar bit 1 0 是否条码管理
	private Boolean isBar = false;

	@Column(name = "isBar", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsBar() {
		return isBar;
	}

	public void setIsBar(Boolean isBar) {
		this.isBar = isBar;
	}

	// is_durable bit 1 0 是否耐用品
	private Boolean isDurable = false;

	@Column(name = "isDurable", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsDurable() {
		return isDurable;
	}

	public void setIsDurable(Boolean isDurable) {
		this.isDurable = isDurable;
	}

	// is_highvalue bit 1 0 是否高值耗材
	private Boolean isHighValue = false;

	@Column(name = "isHighValue", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsHighValue() {
		return isHighValue;
	}

	public void setIsHighValue(Boolean isHighValue) {
		this.isHighValue = isHighValue;
	}

	// is_selfmake bit 1 0 是否自制品
	private Boolean isSelfMake = false;

	@Column(name = "isSelfMake", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsSelfMake() {
		return isSelfMake;
	}

	public void setIsSelfMake(Boolean isSelfMake) {
		this.isSelfMake = isSelfMake;
	}

	// Is_add_sale bit 1 0 是否加价销售
	private Boolean isAddSale = false;

	@Column(name = "isAddSale", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsAddSale() {
		return isAddSale;
	}

	public void setIsAddSale(Boolean isAddSale) {
		this.isAddSale = isAddSale;
	}

	// Is_ Prepare bit 1 0 是否备库
	private Boolean isPrepare = false;

	@Column(name = "isPrepare", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsPrepare() {
		return isPrepare;
	}

	public void setIsPrepare(Boolean isPrepare) {
		this.isPrepare = isPrepare;
	}

	// Is_apply Bit 1 1 可否申领
	private Boolean isApply = true;

	@Column(name = "isApply", nullable = false, columnDefinition = "bit default 1")
	public Boolean getIsApply() {
		return isApply;
	}

	public void setIsApply(Boolean isApply) {
		this.isApply = isApply;
	}

	// DeptwhType bit 1 科室库管理：0 常规 1 盘点 3 台帐
	private Integer deptWhType;

	@Column(name = "deptWhType", nullable = true)
	public Integer getDeptWhType() {
		return deptWhType;
	}

	public void setDeptWhType(Integer deptWhType) {
		this.deptWhType = deptWhType;
	}

	// pricetype Int 计价方法： (0 1 先进先出等)
	private Integer priceType;

	@Column(name = "priceType", nullable = true)
	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	// Barcode Varchar 30 条形码/特征码
	private String barCode;

	@Column(name = "barCode", nullable = true, length = 30)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	// Barcode2 Varchar 30 条形码2
	private String barCode2;

	@Column(name = "barCode2", nullable = true, length = 30)
	public String getBarCode2() {
		return barCode2;
	}

	public void setBarCode2(String barCode2) {
		this.barCode2 = barCode2;
	}

	// Plan_price numeric (18, 4) 计划价
	private Double planPrice;

	@Column(name = "planPrice", nullable = true)
	public Double getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}

	// ref_cost numeric (18, 4) 参考进价
	private Double refCost;

	@Column(name = "refCost", nullable = true)
	public Double getRefCost() {
		return refCost;
	}

	public void setRefCost(Double refCost) {
		this.refCost = refCost;
	}

	// is_charge Bit 1 0 是否收费材料
	private Boolean isCharge = false;

	@Column(name = "isCharge", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(Boolean isCharge) {
		this.isCharge = isCharge;
	}

	// Addrate numeric (9, 4) 加价率
	private Double addRate;

	@Column(name = "addRate", nullable = true, columnDefinition = "numeric default 0.0")
	public Double getAddRate() {
		return addRate;
	}

	public void setAddRate(Double addRate) {
		this.addRate = addRate;
	}

	// ref_price numeric (18, 4) 参考零售价
	private Double refPrice;

	@Column(name = "refPrice", nullable = true, columnDefinition = "numeric default 0.0")
	public Double getRefPrice() {
		return refPrice;
	}

	public void setRefPrice(Double refPrice) {
		this.refPrice = refPrice;
	}

	// sdate datetime 启用日期
	private Date startDate;

	@Column(name = "startDate", nullable = true)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	// edate datetime 停用日期
	private Date endDate;

	@Column(name = "endDate", nullable = true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	// disabled bit 1 ((0)) 停用标志（必填）
	private Boolean disabled = false;

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	// file_name Varchar 60 文件路径：/upload/文件名
	private String fileName;

	@Column(name = "fileName", nullable = true, length = 60)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// 2）辅助信息
	// Origin Varchar 30 √ 产地
	private String origin;

	@Column(name = "origin", nullable = true, length = 30)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	// Agent Varchar 30 √ 代理商
	private String agent;

	@Column(name = "agent", nullable = true, length = 30)
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	// ServeOrg Varchar 30 √ 售后服务机构
	private String serveOrg;

	@Column(name = "serveOrg", nullable = true, length = 30)
	public String getServeOrg() {
		return serveOrg;
	}

	public void setServeOrg(String serveOrg) {
		this.serveOrg = serveOrg;
	}

	// brand_name Varchar 20 √ 品牌
	private String brandName;

	@Column(name = "brandName", nullable = true, length = 20)
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	// is_tender Bit 1 0 是否招标
	private Boolean isTender = false;

	@Column(name = "isTender", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsTender() {
		return isTender;
	}

	public void setIsTender(Boolean isTender) {
		this.isTender = isTender;
	}

	// Is_import bit 1 0 是否进口
	private Boolean isImport = false;

	@Column(name = "isImport", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsImport() {
		return isImport;
	}

	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
	}

	// refNo Varchar 20 REF号
	private String refNO;

	@Column(name = "refNO", nullable = true, length = 20)
	public String getRefNO() {
		return refNO;
	}

	public void setRefNO(String refNO) {
		this.refNO = refNO;
	}

	// is_cert Bit 1 0 是否证件管理
	private Boolean isCert = false;

	@Column(name = "isCert", nullable = false, columnDefinition = "bit default 1")
	public Boolean getIsCert() {
		return isCert;
	}

	public void setIsCert(Boolean isCert) {
		this.isCert = isCert;
	}

	// cert_code Varchar 30 √ 证书号（用户自己定是什么号）
	private String certCode;

	@Column(name = "certCode", nullable = true, length = 30)
	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	// Is_ Sterile Bit 1 0 是否灭菌材料
	private Boolean isSterile = false;

	@Column(name = "isSterile", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsSterile() {
		return isSterile;
	}

	public void setIsSterile(Boolean isSterile) {
		this.isSterile = isSterile;
	}

	// inv_structure Varchar 20 √ 材料结构：文本框
	private String invStructure;

	@Column(name = "invStructure", nullable = true, length = 20)
	public String getInvStructure() {
		return invStructure;
	}

	public void setInvStructure(String invStructure) {
		this.invStructure = invStructure;
	}

	// Usage Varchar 20 √ 材料用途：取用途字典
	private InvUse invUse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invUse_id", nullable = true)
	public InvUse getInvUse() {
		return invUse;
	}

	public void setInvUse(InvUse invUse) {
		this.invUse = invUse;
	}

	// PerWeight numeric (16, 2) 0 单位重量
	private Double perWeight;

	@Column(name = "perWeight", nullable = true, columnDefinition = "numeric default 0.0")
	public Double getPerWeight() {
		return perWeight;
	}

	public void setPerWeight(Double perWeight) {
		this.perWeight = perWeight;
	}

	// PerVolume numeric (16, 2) 0 单位体积
	private Double perValume;

	@Column(name = "perValume", nullable = true, columnDefinition = "numeric default 0.0")
	public Double getPerValume() {
		return perValume;
	}

	public void setPerValume(Double perValume) {
		this.perValume = perValume;
	}

	// Stockcondition Varchar 50 √ 存储条件
	private String stockCondition;

	@Column(name = "stockCondition", nullable = true, length = 50)
	public String getStockCondition() {
		return stockCondition;
	}

	public void setStockCondition(String stockCondition) {
		this.stockCondition = stockCondition;
	}

	// Plan_attr Bit 1 计划制定依据： 0库存基数1 科室需求
	private Integer planAttr;

	@Column(name = "planAttr", nullable = true, columnDefinition = "int default 0")
	public Integer getPlanAttr() {
		return planAttr;
	}

	public void setPlanAttr(Integer planAttr) {
		this.planAttr = planAttr;
	}

	// Remark Varchar 100 备注
	private String remark;

	@Column(name = "remark", nullable = true, length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	//
	// 3）经济信息
	// Eco_bat numeric (16, 2) 经济批量
	private Double ecoBat;

	@Column(name = "ecoBat", nullable = true, columnDefinition = "numeric default 0.0")
	public Double getEcoBat() {
		return ecoBat;
	}

	public void setEcoBat(Double ecoBat) {
		this.ecoBat = ecoBat;
	}

	// ABC Char 1 特别重要的库存（A类）、一般重要的库存（B类）和不重要的库存（C类）三个等级
	private String abc;

	@Column(name = "abc", nullable = true, length = 1)
	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	// amortize_type Int 0 摊销类型：0 一次性 1
	// 五五摊销（五五摊销法就是在周转材料领用时摊销其一半价值，在报废时再摊销其另一半价值的方法）
	private Integer amortizeType;

	@Column(name = "amortizeType", nullable = true, columnDefinition = "int default 0")
	public Integer getAmortizeType() {
		return amortizeType;
	}

	public void setAmortizeType(Integer amortizeType) {
		this.amortizeType = amortizeType;
	}

	// Curr_price numeric (18, 4) 当前售价 （后台）
	private Double currentPrice;

	@Column(name = "currentPrice", nullable = true, columnDefinition = "numeric default 0.0")
	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	// move_average_price numeric (18, 4) 移动平均价 （后台）
	private Double moveAveragePrice;

	@Column(name = "moveAveragePrice", nullable = true, columnDefinition = "numeric default 0.0")
	public Double getMoveAveragePrice() {
		return moveAveragePrice;
	}

	public void setMoveAveragePrice(Double moveAveragePrice) {
		this.moveAveragePrice = moveAveragePrice;
	}

	// Chargeitemid Varchar 30 √ 收费代码
	private String chargeItemId;

	@Column(name = "chargeItemId", nullable = true, length = 30)
	public String getChargeItemId() {
		return chargeItemId;
	}

	public void setChargeItemId(String chargeItemId) {
		this.chargeItemId = chargeItemId;
	}

	// Chargetype Varchar 30 √ 收费类别
	private String chargeType;

	@Column(name = "chargeType", nullable = true, length = 30)
	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	// YBcode Varchar 30 √ 医保编码
	private String ybCode;

	@Column(name = "ybCode", nullable = true, length = 30)
	public String getYbCode() {
		return ybCode;
	}

	public void setYbCode(String ybCode) {
		this.ybCode = ybCode;
	}

	//
	// 4）预警信息
	// Is_quality Bit 1 是否保质期管理
	private Boolean isQuality = false;

	@Column(name = "isQuality", nullable = true, columnDefinition = "bit default 1")
	public Boolean getIsQuality() {
		return isQuality;
	}

	public void setIsQuality(Boolean isQuality) {
		this.isQuality = isQuality;
	}

	// guarantee int 保质期（天）
	private Integer guarantee;

	@Column(name = "guarantee", nullable = true, columnDefinition = "int default 0")
	public Integer getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Integer guarantee) {
		this.guarantee = guarantee;
	}

	// safestock numeric (16, 2) 安全库存
	private Integer safeStock;

	@Column(name = "safeStock", nullable = true, columnDefinition = "int default 0")
	public Integer getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(Integer safeStock) {
		this.safeStock = safeStock;
	}

	// low_limit numeric (16, 2) 最低限
	private Integer lowLimit;

	@Column(name = "lowLimit", nullable = true, columnDefinition = "int default 0")
	public Integer getLowLimit() {
		return lowLimit;
	}

	public void setLowLimit(Integer lowLimit) {
		this.lowLimit = lowLimit;
	}

	// high_limit numeric (16, 2) 最高限
	private Integer highLimit;

	@Column(name = "highLimit", nullable = true, columnDefinition = "int default 0")
	public Integer getHighLimit() {
		return highLimit;
	}

	public void setHighLimit(Integer highLimit) {
		this.highLimit = highLimit;
	}

	// cur_stock numeric (16, 2) 当前库存（后台字段）
	private Integer currentStock;

	@Column(name = "currentStock", nullable = true, columnDefinition = "int default 0")
	public Integer getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}

	// buy_ahead Int 采购提前期（天）
	private Integer buyAhead;

	@Column(name = "buyAhead", nullable = true, columnDefinition = "int default 0")
	public Integer getBuyAhead() {
		return buyAhead;
	}

	public void setBuyAhead(Integer buyAhead) {
		this.buyAhead = buyAhead;
	}

	// is_overstock Bit 1 是否呆滞积压管理
	private Boolean isOverStock = false;

	@Column(name = "isOverStock", nullable = true, columnDefinition = "bit default 0")
	public Boolean getIsOverStock() {
		return isOverStock;
	}

	public void setIsOverStock(Boolean isOverStock) {
		this.isOverStock = isOverStock;
	}

	// Staytime numeric (9, 2) 呆滞标准：库存周转率极低的物料就是呆滞物料
	private Integer stayTime;

	@Column(name = "stayTime", nullable = true, columnDefinition = "int default 0")
	public Integer getStayTime() {
		return stayTime;
	}

	public void setStayTime(Integer stayTime) {
		this.stayTime = stayTime;
	}

	// isPublic Bit 0 是否公共材料
	private Boolean isPublic = false;

	@Column(name = "isPublic", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abc == null) ? 0 : abc.hashCode());
		result = prime * result + ((addRate == null) ? 0 : addRate.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result
				+ ((amortizeType == null) ? 0 : amortizeType.hashCode());
		result = prime * result + ((barCode == null) ? 0 : barCode.hashCode());
		result = prime * result
				+ ((barCode2 == null) ? 0 : barCode2.hashCode());
		result = prime * result
				+ ((brandName == null) ? 0 : brandName.hashCode());
		result = prime * result
				+ ((buyAhead == null) ? 0 : buyAhead.hashCode());
		result = prime * result
				+ ((certCode == null) ? 0 : certCode.hashCode());
		result = prime * result
				+ ((chargeItemId == null) ? 0 : chargeItemId.hashCode());
		result = prime * result
				+ ((chargeType == null) ? 0 : chargeType.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((currentPrice == null) ? 0 : currentPrice.hashCode());
		result = prime * result
				+ ((currentStock == null) ? 0 : currentStock.hashCode());
		result = prime * result
				+ ((deptWhType == null) ? 0 : deptWhType.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((ecoBat == null) ? 0 : ecoBat.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((factory == null) ? 0 : factory.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((firstUnit == null) ? 0 : firstUnit.hashCode());
		result = prime * result
				+ ((guarantee == null) ? 0 : guarantee.hashCode());
		result = prime * result
				+ ((highLimit == null) ? 0 : highLimit.hashCode());
		result = prime * result
				+ ((invAttrCode == null) ? 0 : invAttrCode.hashCode());
		result = prime * result + ((invCode == null) ? 0 : invCode.hashCode());
		result = prime * result + ((invId == null) ? 0 : invId.hashCode());
		result = prime * result
				+ ((invModel == null) ? 0 : invModel.hashCode());
		result = prime * result + ((invName == null) ? 0 : invName.hashCode());
		result = prime * result
				+ ((invStructure == null) ? 0 : invStructure.hashCode());
		result = prime * result + ((invUse == null) ? 0 : invUse.hashCode());
		result = prime * result
				+ ((isAddSale == null) ? 0 : isAddSale.hashCode());
		result = prime * result + ((isApply == null) ? 0 : isApply.hashCode());
		result = prime * result + ((isBar == null) ? 0 : isBar.hashCode());
		result = prime * result + ((isBatch == null) ? 0 : isBatch.hashCode());
		result = prime * result + ((isCert == null) ? 0 : isCert.hashCode());
		result = prime * result
				+ ((isCharge == null) ? 0 : isCharge.hashCode());
		result = prime * result
				+ ((isDurable == null) ? 0 : isDurable.hashCode());
		result = prime * result
				+ ((isHighValue == null) ? 0 : isHighValue.hashCode());
		result = prime * result
				+ ((isImport == null) ? 0 : isImport.hashCode());
		result = prime * result
				+ ((isOverStock == null) ? 0 : isOverStock.hashCode());
		result = prime * result
				+ ((isPrepare == null) ? 0 : isPrepare.hashCode());
		result = prime * result
				+ ((isQuality == null) ? 0 : isQuality.hashCode());
		result = prime * result
				+ ((isSelfMake == null) ? 0 : isSelfMake.hashCode());
		result = prime * result
				+ ((isPublic == null) ? 0 : isPublic.hashCode());
		result = prime * result
				+ ((isSterile == null) ? 0 : isSterile.hashCode());
		result = prime * result
				+ ((isTender == null) ? 0 : isTender.hashCode());
		result = prime * result + ((ivAlias == null) ? 0 : ivAlias.hashCode());
		result = prime * result
				+ ((lowLimit == null) ? 0 : lowLimit.hashCode());
		result = prime * result + ((mLevel == null) ? 0 : mLevel.hashCode());
		result = prime
				* result
				+ ((moveAveragePrice == null) ? 0 : moveAveragePrice.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result
				+ ((perValume == null) ? 0 : perValume.hashCode());
		result = prime * result
				+ ((perWeight == null) ? 0 : perWeight.hashCode());
		result = prime * result
				+ ((planAttr == null) ? 0 : planAttr.hashCode());
		result = prime * result
				+ ((planPrice == null) ? 0 : planPrice.hashCode());
		result = prime * result
				+ ((priceType == null) ? 0 : priceType.hashCode());
		result = prime * result + ((refCost == null) ? 0 : refCost.hashCode());
		result = prime * result + ((refNO == null) ? 0 : refNO.hashCode());
		result = prime * result
				+ ((refPrice == null) ? 0 : refPrice.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((safeStock == null) ? 0 : safeStock.hashCode());
		result = prime * result
				+ ((secondUnit == null) ? 0 : secondUnit.hashCode());
		result = prime * result
				+ ((serveOrg == null) ? 0 : serveOrg.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result
				+ ((stayTime == null) ? 0 : stayTime.hashCode());
		result = prime * result
				+ ((stockCondition == null) ? 0 : stockCondition.hashCode());
		result = prime * result
				+ ((unitRate == null) ? 0 : unitRate.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
		result = prime * result + ((ybCode == null) ? 0 : ybCode.hashCode());
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
		InventoryDict other = (InventoryDict) obj;
		if (abc == null) {
			if (other.abc != null)
				return false;
		} else if (!abc.equals(other.abc))
			return false;
		if (addRate == null) {
			if (other.addRate != null)
				return false;
		} else if (!addRate.equals(other.addRate))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (amortizeType == null) {
			if (other.amortizeType != null)
				return false;
		} else if (!amortizeType.equals(other.amortizeType))
			return false;
		if (barCode == null) {
			if (other.barCode != null)
				return false;
		} else if (!barCode.equals(other.barCode))
			return false;
		if (barCode2 == null) {
			if (other.barCode2 != null)
				return false;
		} else if (!barCode2.equals(other.barCode2))
			return false;
		if (brandName == null) {
			if (other.brandName != null)
				return false;
		} else if (!brandName.equals(other.brandName))
			return false;
		if (buyAhead == null) {
			if (other.buyAhead != null)
				return false;
		} else if (!buyAhead.equals(other.buyAhead))
			return false;
		if (certCode == null) {
			if (other.certCode != null)
				return false;
		} else if (!certCode.equals(other.certCode))
			return false;
		if (chargeItemId == null) {
			if (other.chargeItemId != null)
				return false;
		} else if (!chargeItemId.equals(other.chargeItemId))
			return false;
		if (chargeType == null) {
			if (other.chargeType != null)
				return false;
		} else if (!chargeType.equals(other.chargeType))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (currentPrice == null) {
			if (other.currentPrice != null)
				return false;
		} else if (!currentPrice.equals(other.currentPrice))
			return false;
		if (currentStock == null) {
			if (other.currentStock != null)
				return false;
		} else if (!currentStock.equals(other.currentStock))
			return false;
		if (deptWhType == null) {
			if (other.deptWhType != null)
				return false;
		} else if (!deptWhType.equals(other.deptWhType))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (ecoBat == null) {
			if (other.ecoBat != null)
				return false;
		} else if (!ecoBat.equals(other.ecoBat))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (factory == null) {
			if (other.factory != null)
				return false;
		} else if (!factory.equals(other.factory))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (firstUnit == null) {
			if (other.firstUnit != null)
				return false;
		} else if (!firstUnit.equals(other.firstUnit))
			return false;
		if (guarantee == null) {
			if (other.guarantee != null)
				return false;
		} else if (!guarantee.equals(other.guarantee))
			return false;
		if (highLimit == null) {
			if (other.highLimit != null)
				return false;
		} else if (!highLimit.equals(other.highLimit))
			return false;
		if (invAttrCode == null) {
			if (other.invAttrCode != null)
				return false;
		} else if (!invAttrCode.equals(other.invAttrCode))
			return false;
		if (invCode == null) {
			if (other.invCode != null)
				return false;
		} else if (!invCode.equals(other.invCode))
			return false;
		if (invId == null) {
			if (other.invId != null)
				return false;
		} else if (!invId.equals(other.invId))
			return false;
		if (invModel == null) {
			if (other.invModel != null)
				return false;
		} else if (!invModel.equals(other.invModel))
			return false;
		if (invName == null) {
			if (other.invName != null)
				return false;
		} else if (!invName.equals(other.invName))
			return false;
		if (invStructure == null) {
			if (other.invStructure != null)
				return false;
		} else if (!invStructure.equals(other.invStructure))
			return false;
		if (invUse == null) {
			if (other.invUse != null)
				return false;
		} else if (!invUse.equals(other.invUse))
			return false;
		if (isAddSale == null) {
			if (other.isAddSale != null)
				return false;
		} else if (!isAddSale.equals(other.isAddSale))
			return false;
		if (isPublic == null) {
			if (other.isPublic != null)
				return false;
		} else if (!isPublic.equals(other.isPublic))
			return false;
		if (isApply == null) {
			if (other.isApply != null)
				return false;
		} else if (!isApply.equals(other.isApply))
			return false;
		if (isBar == null) {
			if (other.isBar != null)
				return false;
		} else if (!isBar.equals(other.isBar))
			return false;
		if (isBatch == null) {
			if (other.isBatch != null)
				return false;
		} else if (!isBatch.equals(other.isBatch))
			return false;
		if (isCert == null) {
			if (other.isCert != null)
				return false;
		} else if (!isCert.equals(other.isCert))
			return false;
		if (isCharge == null) {
			if (other.isCharge != null)
				return false;
		} else if (!isCharge.equals(other.isCharge))
			return false;
		if (isDurable == null) {
			if (other.isDurable != null)
				return false;
		} else if (!isDurable.equals(other.isDurable))
			return false;
		if (isHighValue == null) {
			if (other.isHighValue != null)
				return false;
		} else if (!isHighValue.equals(other.isHighValue))
			return false;
		if (isImport == null) {
			if (other.isImport != null)
				return false;
		} else if (!isImport.equals(other.isImport))
			return false;
		if (isOverStock == null) {
			if (other.isOverStock != null)
				return false;
		} else if (!isOverStock.equals(other.isOverStock))
			return false;
		if (isPrepare == null) {
			if (other.isPrepare != null)
				return false;
		} else if (!isPrepare.equals(other.isPrepare))
			return false;
		if (isQuality == null) {
			if (other.isQuality != null)
				return false;
		} else if (!isQuality.equals(other.isQuality))
			return false;
		if (isSelfMake == null) {
			if (other.isSelfMake != null)
				return false;
		} else if (!isSelfMake.equals(other.isSelfMake))
			return false;
		if (isSterile == null) {
			if (other.isSterile != null)
				return false;
		} else if (!isSterile.equals(other.isSterile))
			return false;
		if (isTender == null) {
			if (other.isTender != null)
				return false;
		} else if (!isTender.equals(other.isTender))
			return false;
		if (ivAlias == null) {
			if (other.ivAlias != null)
				return false;
		} else if (!ivAlias.equals(other.ivAlias))
			return false;
		if (lowLimit == null) {
			if (other.lowLimit != null)
				return false;
		} else if (!lowLimit.equals(other.lowLimit))
			return false;
		if (mLevel == null) {
			if (other.mLevel != null)
				return false;
		} else if (!mLevel.equals(other.mLevel))
			return false;

		if (moveAveragePrice == null) {
			if (other.moveAveragePrice != null)
				return false;
		} else if (!moveAveragePrice.equals(other.moveAveragePrice))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (perValume == null) {
			if (other.perValume != null)
				return false;
		} else if (!perValume.equals(other.perValume))
			return false;
		if (perWeight == null) {
			if (other.perWeight != null)
				return false;
		} else if (!perWeight.equals(other.perWeight))
			return false;
		if (planAttr == null) {
			if (other.planAttr != null)
				return false;
		} else if (!planAttr.equals(other.planAttr))
			return false;
		if (planPrice == null) {
			if (other.planPrice != null)
				return false;
		} else if (!planPrice.equals(other.planPrice))
			return false;
		if (priceType == null) {
			if (other.priceType != null)
				return false;
		} else if (!priceType.equals(other.priceType))
			return false;
		if (refCost == null) {
			if (other.refCost != null)
				return false;
		} else if (!refCost.equals(other.refCost))
			return false;
		if (refNO == null) {
			if (other.refNO != null)
				return false;
		} else if (!refNO.equals(other.refNO))
			return false;
		if (refPrice == null) {
			if (other.refPrice != null)
				return false;
		} else if (!refPrice.equals(other.refPrice))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (safeStock == null) {
			if (other.safeStock != null)
				return false;
		} else if (!safeStock.equals(other.safeStock))
			return false;
		if (secondUnit == null) {
			if (other.secondUnit != null)
				return false;
		} else if (!secondUnit.equals(other.secondUnit))
			return false;
		if (serveOrg == null) {
			if (other.serveOrg != null)
				return false;
		} else if (!serveOrg.equals(other.serveOrg))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (stayTime == null) {
			if (other.stayTime != null)
				return false;
		} else if (!stayTime.equals(other.stayTime))
			return false;
		if (stockCondition == null) {
			if (other.stockCondition != null)
				return false;
		} else if (!stockCondition.equals(other.stockCondition))
			return false;
		if (unitRate == null) {
			if (other.unitRate != null)
				return false;
		} else if (!unitRate.equals(other.unitRate))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		if (ybCode == null) {
			if (other.ybCode != null)
				return false;
		} else if (!ybCode.equals(other.ybCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Material [invId=" + invId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", invCode=" + invCode
				+ ", mLevel=" + mLevel + ", invName=" + invName + ", ivAlias="
				+ ivAlias + ", invModel=" + invModel + ", firstUnit="
				+ firstUnit + ", secondUnit=" + secondUnit + ", unitRate="
				+ unitRate + ", vendor=" + vendor + ", factory=" + factory
				+ ", cnCode=" + cnCode + ", invAttrCode=" + invAttrCode
				+ ", isBatch=" + isBatch + ", isBar=" + isBar + ", isDurable="
				+ isDurable + ", isHighValue=" + isHighValue + ", isSelfMake="
				+ isSelfMake + ", isAddSale=" + isAddSale + ", isPrepare="
				+ isPrepare + ", isApply=" + isApply + ", deptWhType="
				+ deptWhType + ", priceType=" + priceType + ", barCode="
				+ barCode + ", barCode2=" + barCode2 + ", planPrice="
				+ planPrice + ", refCost=" + refCost + ", isCharge=" + isCharge
				+ ", addRate=" + addRate + ", refPrice=" + refPrice
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", disabled=" + disabled + ", fileName=" + fileName
				+ ", origin=" + origin + ", agent=" + agent + ", serveOrg="
				+ serveOrg + ", brandName=" + brandName + ", isTender="
				+ isTender + ", isImport=" + isImport + ", refNO=" + refNO
				+ ", isCert=" + isCert + ", certCode=" + certCode
				+ ", isSterile=" + isSterile + ", invStructure=" + invStructure
				+ ", invUse=" + invUse + ", perWeight=" + perWeight
				+ ", perValume=" + perValume + ", stockCondition="
				+ stockCondition + ", planAttr=" + planAttr + ", remark="
				+ remark + ", ecoBat=" + ecoBat + ", abc=" + abc
				+ ", amortizeType=" + amortizeType + ", currentPrice="
				+ currentPrice + ", moveAveragePrice=" + moveAveragePrice
				+ ", chargeItemId=" + chargeItemId + ", chargeType="
				+ chargeType + ", ybCode=" + ybCode + ", isQuality="
				+ isQuality + ", guarantee=" + guarantee + ", safeStock="
				+ safeStock + ", lowLimit=" + lowLimit + ", highLimit="
				+ highLimit + ", currentStock=" + currentStock + ", buyAhead="
				+ buyAhead + ", isOverStock=" + isOverStock + ", stayTime="
				+ stayTime + ",isPublic=" + isPublic + "]";
	}

}
