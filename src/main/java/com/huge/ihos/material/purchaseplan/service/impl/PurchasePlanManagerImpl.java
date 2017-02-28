package com.huge.ihos.material.purchaseplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.dao.InventoryDictDao;
import com.huge.ihos.material.deptplan.dao.DeptNeedPlanDao;
import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.material.model.InvOutStore;
import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.purchaseplan.dao.PurchasePlanDao;
import com.huge.ihos.material.purchaseplan.model.PurchasePlan;
import com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail;
import com.huge.ihos.material.purchaseplan.service.PurchasePlanManager;
import com.huge.ihos.material.service.InvOutStoreManager;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.store.dao.StoreDao;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.dao.PersonDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateConverter;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("purchasePlanManager")
public class PurchasePlanManagerImpl extends GenericManagerImpl<PurchasePlan, String> implements PurchasePlanManager {
    private PurchasePlanDao purchasePlanDao;
    private DeptNeedPlanDao deptNeedPlanDao;
    private StoreDao storeDao;
    private PersonDao personDao;
    private BillNumberManager billNumberManager;
	private DocumentTemplateManager documentTemplateManager;
    private InvOutStoreManager invOutStoreManager;
    private DocumentTemplate docTemp;
    private DepartmentDao departmentDao;
    private InventoryDictDao inventoryDictDao;
	
	public DocumentTemplate getDocTemp() {
		return docTemp;
	}
    
   
    @Autowired
    public PurchasePlanManagerImpl(PurchasePlanDao purchasePlanDao) {
        super(purchasePlanDao);
        this.purchasePlanDao = purchasePlanDao;
    }
    
    @Autowired
    public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}
    @Autowired
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
    @Autowired
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
    
    @Autowired
	public void setInventoryDictDao(InventoryDictDao inventoryDictDao) {
		this.inventoryDictDao = inventoryDictDao;
	}

    
	@Autowired
    public void setDeptNeedPlanDao(DeptNeedPlanDao deptNeedPlanDao) {
		this.deptNeedPlanDao = deptNeedPlanDao;
	}
	
	@Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

	@Autowired
    public void setDocumentTemplateManager(DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	public JQueryPager getPurchasePlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return purchasePlanDao.getPurchasePlanCriteria(paginatedList,filters);
	}
	
	@Autowired
	public void setInvOutStoreManager(InvOutStoreManager invOutStoreManager) {
		this.invOutStoreManager = invOutStoreManager;
	}
	@Override
	public void remove(String removeId) {
		super.remove(removeId);
		UserContext userContext = UserContextUtil.getUserContext();
		//invBillNumberManager.updateSerialNumber(InvBillNumberSetting.PURCHASE_PLAN, userContext.getOrgCode(), userContext.getCopyCode(), userContext.getPeriodMonth());
	}

	@Override
	public PurchasePlan savePurchasePlan(PurchasePlan purchasePlan,PurchasePlanDetail[] purchasePlanDetails) {
		if(OtherUtil.measureNull(purchasePlan.getPurchaseId())){
			purchasePlan.setPurchaseId(null);
		}
		if(purchasePlan.getPurchaseNo()==null || purchasePlan.getPurchaseNo().trim().length()==0){
			//String tempPurchaseNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.PURCHASE_PLAN, purchasePlan.getOrgCode(), purchasePlan.getCopyCode(), purchasePlan.getPeriodMonth(),true);
			String tempPurchaseNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.PURCHASE_PLAN, true, purchasePlan.getOrgCode(), purchasePlan.getCopyCode(),purchasePlan.getPeriodMonth());;
			purchasePlan.setPurchaseNo(tempPurchaseNo);
		}
		Set<PurchasePlanDetail> ppds = null;
		if(purchasePlanDetails.length>0){
			ppds = new HashSet<PurchasePlanDetail>();
		}
		for(int i=0;i<purchasePlanDetails.length;i++){
			purchasePlanDetails[i].setPurchasePlan(purchasePlan);
			ppds.add(purchasePlanDetails[i]);
		}
		if(purchasePlan.getPurchasePlanDetails()!=null){
			purchasePlan.getPurchasePlanDetails().clear();
		}
		purchasePlan.setPurchasePlanDetails(ppds);
		purchasePlan = this.purchasePlanDao.save(purchasePlan);
		return purchasePlan;
	}

	@Override
	public void auditPurchasePlan(List<String> checkIds, Person person,Date date) {
		PurchasePlan purchasePlan = null;
		for(String checkId:checkIds){
			purchasePlan = this.purchasePlanDao.get(checkId);
			purchasePlan.setState("1");
			purchasePlan.setCheckPerson(person);
			purchasePlan.setCheckDate(date);
			this.purchasePlanDao.save(purchasePlan);
		}
	}

	@Override
	public void antiAuditPurchasePlan(List<String> cancelCheckIds) {
		PurchasePlan purchasePlan = null;
		for(String cancelCheckId:cancelCheckIds){
			purchasePlan = this.purchasePlanDao.get(cancelCheckId);
			purchasePlan.setState("0");
			purchasePlan.setCheckPerson(null);
			purchasePlan.setCheckDate(null);
			this.purchasePlanDao.save(purchasePlan);
		}
	}

	@Override
	public PurchasePlan createPurchasePlanByNeed(List<String> needIds,String storeId,Person person) {
    	PurchasePlan purchasePlan = initPurchasePlan(person,storeId);
		PurchasePlanDetail purchasePlanDetail = null;
		Set<PurchasePlanDetail> purchasePlanDetails = new HashSet<PurchasePlanDetail>();
		DeptNeedPlan deptNeedPlan = null;
		Set<DeptNeedPlanDetail> deptNeedPlanDetails = null;
		InventoryDict invDict = null;
		Department dept = null;
		Map<String,PurchasePlanDetail> map = new HashMap<String,PurchasePlanDetail>();
		for(String needId:needIds){
			deptNeedPlan = deptNeedPlanDao.get(needId); //需求主单
			dept = deptNeedPlan.getDept();//需求科室
			deptNeedPlanDetails = deptNeedPlan.getDeptNeedPlanDetails();//需求明细
			for(DeptNeedPlanDetail deptNeedPlanDetail:deptNeedPlanDetails){
				invDict = deptNeedPlanDetail.getInvDict();
				if(map.get(invDict.getInvId())!=null){
					purchasePlanDetail = map.get(invDict.getInvId());
					purchasePlanDetail.setAmount(purchasePlanDetail.getAmount()+deptNeedPlanDetail.getAmount());
				}else{
					purchasePlanDetail = new PurchasePlanDetail();//采购明细
					purchasePlanDetail.setPurchasePlan(purchasePlan);
					purchasePlanDetail.setNeedDept(dept);
					purchasePlanDetail.setInvDict(invDict);
					purchasePlanDetail.setAmount(deptNeedPlanDetail.getAmount());
					purchasePlanDetail.setPrice(invDict.getRefCost());
//					purchasePlanDetail.setPurchaser(purchaser);
					map.put(invDict.getInvId(), purchasePlanDetail);
				}
				purchasePlanDetails.add(purchasePlanDetail);
			}
			deptNeedPlanDao.save(deptNeedPlan);
		}
		purchasePlan.setPurchasePlanDetails(purchasePlanDetails);
		purchasePlan = purchasePlanDao.save(purchasePlan);
		return purchasePlan;
	}
	private PurchasePlan initPurchasePlan(Person person,String storeId){
		PurchasePlan purchasePlan= new PurchasePlan();
		UserContext userContext = UserContextUtil.getUserContext();
		purchasePlan.setOrgCode(userContext.getOrgCode());
    	purchasePlan.setCopyCode(userContext.getCopyCode());
    	purchasePlan.setPeriodMonth(userContext.getPeriodMonth());
    	//String tempPurchaseNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.PURCHASE_PLAN, purchasePlan.getOrgCode(), purchasePlan.getCopyCode(), purchasePlan.getPeriodMonth(),true);
    	String tempPurchaseNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.PURCHASE_PLAN, true, purchasePlan.getOrgCode(), purchasePlan.getCopyCode(),purchasePlan.getPeriodMonth());
		purchasePlan.setPurchaseNo(tempPurchaseNo);
    	purchasePlan.setMakePerson(person);
    	purchasePlan.setMakeDate(userContext.getBusinessDate());
    	purchasePlan.setState("0");
    	purchasePlan.setStore(storeDao.get(storeId));
    	purchasePlan.setDept(person.getDepartment());//TODO
    	DocumentTemplate docTemp = documentTemplateManager.getDocumentTemplateInUse("采购计划单",userContext.getOrgCode(),userContext.getCopyCode());
    	purchasePlan.setDocTemId(docTemp.getId());
    	purchasePlan = purchasePlanDao.save(purchasePlan);
    	return purchasePlan;
	}
	@Override
	public PurchasePlan createPurchasePlanByBase(Map<String, String> conditions, Person person) {
		// TODO Auto-generated method stub
		UserContext userContext = UserContextUtil.getUserContext();
		PurchasePlanDetail purchasePlanDetail = null;
		String deptId = conditions.get("deptId");
		String storeId = conditions.get("storeId");
		String purchaserId = conditions.get("purchaserId");
		String invTypeId = conditions.get("invTypeId");
		String makeDate = conditions.get("makeDate");
		String arrivalDate = conditions.get("arrivalDate");
		PurchasePlan purchasePlan = null;
		purchasePlan= new PurchasePlan();
		List<PurchasePlanDetail> purchasePlanDetails = new ArrayList<PurchasePlanDetail>();
		List<PropertyFilter> invOutStoreDictfilters = new ArrayList<PropertyFilter>();//材料视图（取当前数量）
		PropertyFilter storeIdFilter = new PropertyFilter("EQS_storeId", storeId);//仓库号
		PropertyFilter invTypeIdFilter = new PropertyFilter("EQS_invTypeId",invTypeId);//材料类别
		invOutStoreDictfilters.add(storeIdFilter);
		invOutStoreDictfilters.add(invTypeIdFilter);
		invOutStoreDictfilters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
		invOutStoreDictfilters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
		invOutStoreDictfilters.add(new PropertyFilter("EQS_yearMonth", userContext.getPeriodMonth()));
		List<InvOutStore> invOutStores = invOutStoreManager.getByFilters(invOutStoreDictfilters);
		List<InventoryDict> inventoryDicts = new ArrayList<InventoryDict>();
		Map<String,Double> idcurAmountmap = new HashMap<String, Double>();
		if(invOutStores != null){//对应仓库和材料类别的材料取出，求出当前量
			for(InvOutStore invOutStore:invOutStores){
				if(idcurAmountmap.containsKey(invOutStore.getInvId())){
					idcurAmountmap.put(invOutStore.getInvId(), invOutStore.getCurAmount()+idcurAmountmap.get(invOutStore.getInvId()));
				}else{
					idcurAmountmap.put(invOutStore.getInvId(),invOutStore.getCurAmount());
					inventoryDicts.add(inventoryDictDao.get(invOutStore.getInvId()));
				}
			}
		if(inventoryDicts!=null){
			for(InventoryDict inventoryDict:inventoryDicts){//将安全库存大于当前量的材料放入details表里
				if(inventoryDict.getPlanAttr()!=null&&inventoryDict.getPlanAttr()==0&&inventoryDict.getInventoryType().getId().equals(invTypeId)){
					if(idcurAmountmap.containsKey(inventoryDict.getInvId())){
						if(idcurAmountmap.get(inventoryDict.getInvId())<inventoryDict.getSafeStock()){
							double amount = inventoryDict.getSafeStock() - idcurAmountmap.get(inventoryDict.getInvId());
							purchasePlanDetail = new PurchasePlanDetail();
							purchasePlanDetail.setAmount(amount);
							purchasePlanDetail.setInvDict(inventoryDict);
							purchasePlanDetail.setPrice(inventoryDict.getRefCost());
							purchasePlanDetail.setArrivalDate((Date) (new DateConverter().convert(Date.class,arrivalDate)));
							purchasePlanDetail.setPurchaser(personDao.get(purchaserId));
							purchasePlanDetail.setVendor(inventoryDict.getVendor());
							purchasePlanDetails.add(purchasePlanDetail);
						}
					}
				}
			}
		}
		if(purchasePlanDetails!=null&&purchasePlanDetails.size()>0){
			int length = purchasePlanDetails.size();
			PurchasePlanDetail[] purchasePlanDetailstemp = new PurchasePlanDetail[length];
			for(int index =0;index < length;index++){
				purchasePlanDetailstemp[index]=purchasePlanDetails.get(index);
			}
			docTemp = documentTemplateManager.getDocumentTemplateInUse("采购计划单",userContext.getOrgCode(),userContext.getCopyCode());
			String docTemId = docTemp.getId();
			purchasePlan.setStore(storeDao.get(storeId));
			purchasePlan.setDept(departmentDao.get(deptId));
			purchasePlan.setMakeDate((Date) (new DateConverter().convert(Date.class,makeDate)));
			purchasePlan.setMakePerson(person);
			purchasePlan.setPeriodMonth(userContext.getPeriodMonth());
			purchasePlan.setState("0");
	    	purchasePlan.setDocTemId(docTemId);
	    	purchasePlan.setOrgCode(userContext.getOrgCode());
	    	purchasePlan.setCopyCode(userContext.getCopyCode());
			purchasePlan =  this.savePurchasePlan(purchasePlan, purchasePlanDetailstemp);
		}	
		}
		return purchasePlan;
	}


	

	



	
	
}