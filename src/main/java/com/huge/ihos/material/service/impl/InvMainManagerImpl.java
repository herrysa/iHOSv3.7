package com.huge.ihos.material.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BillNumCreateException;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.ihos.material.dao.InvBalanceBatchDao;
import com.huge.ihos.material.dao.InvBalanceDao;
import com.huge.ihos.material.dao.InvBatchDao;
import com.huge.ihos.material.dao.InvDetailDao;
import com.huge.ihos.material.dao.InvMainDao;
import com.huge.ihos.material.dao.InventoryDictDao;
import com.huge.ihos.material.deptapp.service.DeptAppDLDetailManager;
import com.huge.ihos.material.model.InvBalanceBatch;
import com.huge.ihos.material.model.InvBatch;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.order.service.ImportOrderLogManager;
import com.huge.ihos.material.service.InvBatchManager;
import com.huge.ihos.material.service.InvMainManager;
import com.huge.ihos.material.typeno.model.InvTypeNo;
import com.huge.ihos.material.typeno.service.InvTypeNoManager;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.repository.store.dao.StoreDao;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.systemManager.busiprocess.service.BusiProcessManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("invMainManager")
public class InvMainManagerImpl extends GenericManagerImpl<InvMain, String> implements InvMainManager {

	private InvMainDao invMainDao;
	
	private InvDetailDao invDetailDao;
	private InvBalanceDao invBalanceDao;
	private InvBalanceBatchDao invBalanceBatchDao;
	private StoreDao storeDao;
	private InvBatchManager invBatchManager;
	//private InvBillNumberManager invBillNumberManager;
	private BillNumberManager billNumberManager;
	private InvBatchDao invBatchDao;
	private InventoryDictDao InventoryDictDao;
	private InvTypeNoManager invTypeNoManager;
	private BusiProcessManager businessProcessManager;
	private DeptAppDLDetailManager deptAppDLDetailManager;
	private ImportOrderLogManager importOrderLogManager;
	private String confirmBillNum = "";
	
	@Autowired
	public void setBusinessProcessManager(BusiProcessManager businessProcessManager) {
		this.businessProcessManager = businessProcessManager;
	}
	@Autowired
	public void setInvBatchDao(InvBatchDao invBatchDao) {
		this.invBatchDao = invBatchDao;
	}
	
	@Autowired
	public void setInventoryDictDao(InventoryDictDao inventoryDictDao) {
		InventoryDictDao = inventoryDictDao;
	}

	@Autowired
	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
	@Autowired
	public void setDeptAppDLDetailManager(DeptAppDLDetailManager deptAppDLDetailManager) {
		this.deptAppDLDetailManager = deptAppDLDetailManager;
	}
	@Autowired
	public void setInvTypeNoManager(InvTypeNoManager invTypeNoManager) {
		this.invTypeNoManager = invTypeNoManager;
	}
	@Autowired
	public void setImportOrderLogManager(ImportOrderLogManager importOrderLogManager) {
		this.importOrderLogManager = importOrderLogManager;
	}
	@Autowired
	public void setInvDetailDao(InvDetailDao invDetailDao) {
		this.invDetailDao = invDetailDao;
	}

	@Autowired
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	@Autowired
	public void setInvBatchManager(InvBatchManager invBatchDao) {
		this.invBatchManager = invBatchDao;
	}

	@Autowired
	public InvMainManagerImpl(InvMainDao invMainDao) {
		super(invMainDao);
		this.invMainDao = invMainDao;
	}

	public JQueryPager getInvMainCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {
		return invMainDao.getInvMainCriteria(paginatedList, filters);
	}

	@Autowired
	public void setInvBalanceDao(InvBalanceDao invBalanceDao) {
		this.invBalanceDao = invBalanceDao;
	}

	@Autowired
	public void setInvBalanceBatchDao(InvBalanceBatchDao invBalanceBatchDao) {
		this.invBalanceBatchDao = invBalanceBatchDao;
	}

	@Override
	public void removeInvMain(InvMain invMain) {
		try {
			List<InvDetail> invDetails = this.invDetailDao.getInvDetailsInSameInvMain(invMain);
			int size = invDetails.size();
			for(int i=0;i<size;i++){
				invDetailDao.remove(invDetails.get(i).getInvDetialId());
			}
			invMainDao.remove(invMain.getIoId());
		} catch (Exception e) {
			log.debug("delete InvMain error :"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public InvMain saveInvMain(InvMain main, InvDetail[] details) throws BillNumCreateException {
		if(OtherUtil.measureNull(main.getIoId())){
			main.setIoId(null);
		}
		if(main.getIoBillNumber()==null || main.getIoBillNumber().trim().length()==0){
			String bcode =null;
			String iotype=main.getIoType();
			if(iotype.equals("1")||iotype.equals("3")){
				bcode = "MMRK";
			}else if(iotype.equals("2")||iotype.equals("4")){
				bcode = "MMCK";
			}
			String tempMainNo = "";
			//tempMainNo = this.invBillNumberManager.createNextBillNumber(bcode, main.getOrgCode(), main.getCopyCode(), main.getYearMonth(),false);
			tempMainNo = billNumberManager.createNextBillNumber("MM",bcode, false, main.getOrgCode(), main.getCopyCode(),main.getYearMonth());
			/*			
			 * try{
			} catch (Exception e) {
				throw new BusinessException("没有对应的序列号设置，请在参数设置里配置！");
			}
*/			main.setIoBillNumber("临-"+tempMainNo);
		}
		main = this.invMainDao.save(main);
		//修改单据时，先清空以前的明细数据
		List<InvDetail> invDetails = this.invDetailDao.getInvDetailsInSameInvMain(main);
		for (int i = 0; i < invDetails.size(); i++) {
			this.invDetailDao.remove(invDetails.get(i).getInvDetialId());
		}
		Set<InvDetail> invDets = new HashSet<InvDetail>();
		double totalMoney = 0.0;
		for (int i = 0; i < details.length; i++) {
			details[i].setInvMain(main);
			if (details[i].getInvDetialId() == null || details[i].getInvDetialId().trim().length() == 0) {
				details[i].setInvDetialId(null);
			}

			InventoryDict ivndict = this.InventoryDictDao.get(details[i].getInvDict().getInvId());
			
			
			// 处理下批次问题 begin
			if("1".equals(main.getIoType())){
				if(details[i].getInvBatch().getId()==null || details[i].getInvBatch().getId().trim().equals("")){
					InvBatch ib = this.invBatchDao.getInvBatchByDictAndBatchNo(ivndict.getInvId(), details[i].getInvBatch().getBatchNo(),ivndict.getOrgCode(),ivndict.getCopyCode(),main.getStore());
						if (ib == null) {
							InvBatch tempBatch =  details[i].getInvBatch();
							ib = this.invBatchDao.addBatch(ivndict.getInvId(), ivndict.getOrgCode(), ivndict.getCopyCode(), tempBatch.getBatchNo(),tempBatch.getValidityDate(),main.getStore());//DefaultBatch(invDictId, org, copy, vdc)
						}
					details[i].setInvBatch(ib);
				}
			}
			// 处理下批次问题 end
			if (details[i].getIsPerBar() == true) {
				for (int j = 0; j < details[i].getInAmount(); j++) {
					try {
						InvDetail clone = (InvDetail) details[i].clone();
						clone.setInvDetialId(null);
						clone.setInAmount(1d);
						clone.setInMoney(clone.getInPrice());
						
						//TODO 在批上加上个体码生成机制
						
						String unitPerBar = this.invBatchManager.getNextPerBar(clone.getInvBatch().getId());
						clone.setUnitBarCode(unitPerBar);
						clone = this.invDetailDao.save(clone);
						invDets.add(clone);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (details[i].getInvDetialId() != null && details[i].getInvDetialId().trim().length() > 0)
					this.invDetailDao.remove(details[i].getInvDetialId());
			} else{
				details[i].setInvMain(main);
				details[i] = this.invDetailDao.save(details[i]);
				invDets.add(details[i]);
			}
			totalMoney += details[i].getInMoney();
		}
		main.setTotalMoney(totalMoney);
		main.setInvDetails(invDets);
		main = invMainDao.save(main);
		return main;
	}

	public void initConfirm(String[] invMainIds, Date cfd, Person cfp) {
		for (int i = 0; i < invMainIds.length; i++) {
			InvMain im = this.invMainDao.get(invMainIds[i]);
			String tempNo = im.getIoBillNumber();
			im.setStatus("2");//
			im.setConfirmDate(cfd);
			im.setConfirmPerson(cfp);
			String iotype=im.getIoType();
			String bcode = "";
			if(iotype.equals("1")||iotype.equals("3")){
				bcode = "MMRK";
			}else if(iotype.equals("2")||iotype.equals("4")){
				bcode = "MMCK";
			}
			//String realMainNo = this.invBillNumberManager.createNextBillNumber(bcode, im.getOrgCode(), im.getCopyCode(), im.getYearMonth(),true);
			String realMainNo = billNumberManager.createNextBillNumber("MM",bcode, true, im.getOrgCode(), im.getCopyCode(),im.getYearMonth());
			confirmBillNum = realMainNo;
			im.setIoBillNumber(realMainNo);
			im = this.invMainDao.save(im);
			/**
			 * 入库确认时将根据订单入库的log更新
			 */
			importOrderLogManager.setImportLog(im,tempNo,"confirm",null);
			Set<InvDetail> details = im.getInvDetails();
			for (Iterator<InvDetail> iterator = details.iterator(); iterator.hasNext();) {
				InvDetail idl = (InvDetail) iterator.next();
				this.invBalanceDao.updateBalance(im, idl,null);
				this.invBalanceBatchDao.updateBatchBalance(im, idl,null);

			}

			//入库记账时，向单据号和单据类型关联表插入数据
			InvTypeNo invTypeNo = new InvTypeNo();
			invTypeNo.setOrgCode(im.getOrgCode());
			invTypeNo.setCopyCode(im.getCopyCode());
			invTypeNo.setNo(im.getIoBillNumber());
			String type = "";
			if(iotype.equals("1")||iotype.equals("3")){
				if(im.getBusType().getTypeCode().equals("39")){
					type = "1";//期初入库单
				}else{
					type = "2";//日常入库单
				}
			}else if(iotype.equals("2")||iotype.equals("4")){
				type = "3";//出库单
			}
			invTypeNo.setType(type);
			invTypeNoManager.save(invTypeNo);
			
			//入库记账时 向材料明细账表更新数据
			for(InvDetail invDetail:details){
				Object[] args = {im.getOrgCode(),im.getCopyCode(),im.getYearMonth(),cfp.getPersonId(),im.getStore().getId(),invDetail.getInvDict().getInvId(),invDetail.getInvBatch().getId(),im.getIoBillNumber()};
				businessProcessManager.execBusinessProcess("MMRKJZ", args);
			}
		}

	}

	public void initUnConfirm(String[] invMainIds) {
		//TODO 还没有做
	}

	@Override
	public void bookStore(String orgCode, String copyCode,String kjYear,String[] storeId, Date startDate) {
		
		for (int i = 0; i < storeId.length; i++) {
			
			boolean allConfirmed = this.invMainDao.checkAllInitInvMainConfirm(orgCode, copyCode,kjYear,storeId[i]);
			if (allConfirmed) {
//				Store st = this.storeDao.get(storeId[i]);
				List<Store> stores = storeDao.getPathToNode(storeId[i], true);
				for(Store store:stores){
					store.setIsBook(true);
					store.setStartDate(startDate);
					this.storeDao.save(store);
				}
			} else {
				throw new BusinessException("本仓库的期初入库单尚未全部确认入库，请检查。");
			}
		}
	}
	
	@Override
	public void unBookStore(String[] storeIds,String orgCode,String copyCode,String kjYear) {
		Store store = null;
		String storeId = null;
		List<InvMain> invMains = null;
		List<PropertyFilter> filters = null;
		for(int i=0;i<storeIds.length;i++){
			storeId = storeIds[i];
			store = storeDao.get(storeId);
			boolean onlyInitRecord = this.invMainDao.checkAllDocsInStore(storeId,orgCode,copyCode,kjYear);
			if(onlyInitRecord){
				store.setIsBook(false);
				store.setStartDate(null);
				this.storeDao.save(store);
				this.invBalanceDao.delete(storeId,orgCode,copyCode,kjYear);
				this.invBalanceBatchDao.delete(storeId,orgCode,copyCode,kjYear);
				this.invMainDao.deleteInvDictAccount(storeId,orgCode,copyCode,kjYear);
				filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_orgCode",orgCode));
				filters.add(new PropertyFilter("EQS_copyCode",copyCode));
				filters.add(new PropertyFilter("LIKES_yearMonth",kjYear+"*"));
				filters.add(new PropertyFilter("EQS_store.id",storeId));
				invMains = this.getByFilters(filters);
				for(InvMain invMain:invMains){
					invMain.setStatus("0");
					//String tempMainNo = this.invBillNumberManager.createNextBillNumber("MMRK", invMain.getOrgCode(), invMain.getCopyCode(), invMain.getYearMonth(),false);
					String tempMainNo = billNumberManager.createNextBillNumber("MM","MMRK", false, invMain.getOrgCode(), invMain.getCopyCode(),invMain.getYearMonth());
					
					invMain.setIoBillNumber("临-"+tempMainNo);
					this.invMainDao.save(invMain);
				}
			}else {
				throw new BusinessException("本仓库含有期初入库单之外的其他单据，不能反记账。");
			}
		}
		
	}
	@Override
	public void invInAudit(String[] invMainIds, Date aud, Person aup) {
		// TODO Auto-generated method stub
		for (int i = 0; i < invMainIds.length; i++) {
			InvMain im = this.invMainDao.get(invMainIds[i]);
			im.setStatus("1");//
			im.setCheckDate(aud);
			im.setCheckPerson(aup);
			this.invMainDao.save(im);
		}
		
	}

	@Override
	public void invInUnAudit(String[] invMainIds) {
		for (int i = 0; i < invMainIds.length; i++) {
			InvMain im = this.invMainDao.get(invMainIds[i]);
			im.setStatus("0");//
			im.setCheckDate(null);
			im.setCheckPerson(null);
			this.invMainDao.save(im);
		}
		
	}

	@Override
	public void confirmOut(String[] invMainIds, Date cfd, Person cfp) {
		for (int i = 0; i < invMainIds.length; i++) {
			InvMain im = this.invMainDao.get(invMainIds[i]);
			im.setStatus("2");//
			im.setConfirmDate(cfd);
			im.setConfirmPerson(cfp);
			//String realMainNo = this.invBillNumberManager.createNextBillNumber("MMCK", im.getOrgCode(), im.getCopyCode(), im.getYearMonth(),true);
			String realMainNo = billNumberManager.createNextBillNumber("MM","MMCK", true, im.getOrgCode(), im.getCopyCode(),im.getYearMonth());
			im.setIoBillNumber(realMainNo);
			im = this.invMainDao.save(im);
			/**
			 * 出库确认时将发放记录的明细表的关联出库单号一并更新
			 */
			deptAppDLDetailManager.setOutNoByInvMainOut(im.getIoBillNumber(), "comfirm", null, null);
			Set<InvDetail> details = im.getInvDetails();
			//出库记账时 向材料明细账表更新数据
			for(InvDetail invDetail:details){
				Object[] args = {im.getOrgCode(),im.getCopyCode(),im.getYearMonth(),cfp.getPersonId(),im.getStore().getId(),invDetail.getInvDict().getInvId(),invDetail.getInvBatch().getId(),im.getIoBillNumber()};
				businessProcessManager.execBusinessProcess("MMCKJZ", args);
			}
			//出库记账时，向单据号和单据类型关联表插入数据
			InvTypeNo invTypeNo = new InvTypeNo();
			invTypeNo.setOrgCode(im.getOrgCode());
			invTypeNo.setCopyCode(im.getCopyCode());
			invTypeNo.setNo(im.getIoBillNumber());
			invTypeNo.setType("3");
			invTypeNoManager.save(invTypeNo);
			/*List<InvDetail> details = im.getInvDetails();
			for (InvDetail detail: details) {
				this.invBalanceDao.updateBalance(im, detail);
				this.invBalanceBatchDao.updateBatchBalance(im, detail);
			}*/
			//移库
			if(im.getBusType().getTypeCode().equals("14")){
				InvMain mainIn = (InvMain)im.clone();
				mainIn.setIoId(null);
				Set<InvDetail> detailsIn = im.getInvDetails();
				Set<InvDetail> detailsInTemp = new HashSet<InvDetail>();
				detailsInTemp.addAll(detailsIn);
				mainIn.getInvDetails().clear();
				MmBusinessType bst = new MmBusinessType();
				bst.setTypeCode("13");
				mainIn.setBusType(bst);
				mainIn.setIoType("3");
				mainIn.setExchIo(im.getIoBillNumber());
				Store inStore = mainIn.getExchStoreId();
				Store OutStore = mainIn.getStore();
				mainIn.setStore(inStore);
				mainIn.setExchStoreId(OutStore);
				mainIn.setMakeDate(im.getConfirmDate());
				mainIn.setCheckDate(im.getConfirmDate());
				mainIn.setCheckPerson(im.getConfirmPerson());
				mainIn.setMakePerson(im.getConfirmPerson());
				mainIn = this.invMainDao.save(mainIn);
				for(InvDetail idt:detailsInTemp){
					InvDetail idtTemp = (InvDetail)idt.clone();
					idtTemp.setInvDetialId(null);
					idtTemp.setInvMain(mainIn);
					this.invDetailDao.save(idtTemp);
				}
				mainIn.setInvDetails(detailsInTemp);
				mainIn = this.invMainDao.save(mainIn);
				String[] ids = {mainIn.getIoId()};
				initConfirm(ids,cfd,cfp);
				im.setExchIo(confirmBillNum);
				this.invMainDao.save(im);
			}
		}
	}

	@Override
	public void auditOut(String[] invMainIds, Date cfd, Person cfp) {
		for (int i = 0; i < invMainIds.length; i++) {
			InvMain im = this.invMainDao.get(invMainIds[i]);
			im.setStatus("1");//
			im.setCheckPerson(cfp);
			im.setCheckDate(cfd);
			this.invMainDao.save(im);
			Set<InvDetail> details = im.getInvDetails();
			for (InvDetail detail: details) {//出库审核的时候更新balance
				this.invBalanceDao.updateBalance(im, detail,null);
				this.invBalanceBatchDao.updateBatchBalance(im, detail,null);
			}
		}
	}
	
	@Override
	public void auditOutNot(String[] invMainIds) {
		for (int i = 0; i < invMainIds.length; i++) {
			InvMain im = this.invMainDao.get(invMainIds[i]);
			im.setStatus("0");//
			im.setCheckPerson(null);
			im.setCheckDate(null);
			
			this.invMainDao.save(im);//出库销审的时候还原balance
			Set<InvDetail> details = im.getInvDetails();
			for (InvDetail detail: details) {
				this.invBalanceDao.updateBalance(im, detail,"auditOutNot");
				this.invBalanceBatchDao.updateBatchBalance(im, detail,"auditOutNot");
			}
		}
	}
	
	@Override
	public Iterator<InvDetail> beforeOutAudit(String[] invMinIds) {
		List<InvMain> invMains = new ArrayList<InvMain>();
		for(int i=0;i<invMinIds.length;i++){
			invMains.add(invMainDao.get(invMinIds[i]));
		}
		Map<String,InvDetail> map = new HashMap<String,InvDetail>();
		InvDetail invDetail = null;
		if(invMains.size()>0){
			InvMain invMain = null;
			Set<InvDetail> invDetails = null;
			for(int i=0;i<invMains.size();i++){//遍历出库单
				invMain = invMains.get(i);
				invDetails = invMain.getInvDetails();
				Iterator<InvDetail> it = invDetails.iterator();
				while(it.hasNext()){//遍历出库单的条目，每遇到一个材料，判断其是否已经被记录(同一批次、同一材料[肯定是同一单位帐套下的])，如果没有则添加，如果有则更新记录
					invDetail = it.next();
					String key = invDetail.getInvDict().getInvId()+invDetail.getInvBatch().getBatchNo();
					if(map.containsKey(key)){
						InvDetail invDetail2= map.get(key);
						invDetail2.setCurrentStock(invDetail2.getCurrentStock()-invDetail.getInAmount());//更新map中对应材料的数量
						map.put(key, invDetail2);
					}else{
						//查找当年度的剩余值
						InvBalanceBatch ibb = invBalanceBatchDao.getBatchBalance(invMain.getOrgCode(), invMain.getCopyCode(), invMain.getYearMonth(), invMain.getStore().getId(), invDetail.getInvDict().getInvId(), invDetail.getInvBatch().getId());
						invDetail.setCurrentStock(ibb.getCurAmount()-invDetail.getInAmount());
						map.put(key, invDetail);
					}
				}
			}
		}
		//最后遍历map，如果某一种材料的库存小于0，则不能通过审核
		Collection<InvDetail> invDetailOuts = map.values();
		return invDetailOuts.iterator();
	}
	@Override
	public InvMain getInvMainByNo(String no, String orgCode, String copyCode) {
		return invMainDao.getInvMainByNo(no,orgCode,copyCode);
	}
	@Override
	public InvMain saveInvOutFromDeptApp(InvMain invMain, InvDetail[] ids,String deptAppId,String deptAppDetailIds) {
		invMain = saveInvMain(invMain,ids);
		deptAppDLDetailManager.setOutNoByInvMainOut(invMain.getIoBillNumber(), "save", deptAppId, deptAppDetailIds);
		return invMain;
	}
	@Override
	public void removeInvMainOut(String outId) {
		InvMain invMainOut = this.invMainDao.get(outId);
		deptAppDLDetailManager.setOutNoByInvMainOut(invMainOut.getIoBillNumber(), "delete", null, null);
		super.remove(outId);
	}
	@Override
	public InvMain saveInvMainFromOrder(InvMain invMain, InvDetail[] ids,String fromOrderData,Person person) {
		invMain = this.saveInvMain(invMain, ids);
		importOrderLogManager.setImportLog(invMain,fromOrderData,"save",person);
		return invMain;
	}
	@Override
	public void removeInvMainIn(String inId) {
		InvMain invMainIn = this.invMainDao.get(inId);
		importOrderLogManager.setImportLog(invMainIn,null,"delete",null);
		super.remove(inId);
	}
	
}