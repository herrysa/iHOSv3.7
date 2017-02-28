package com.huge.ihos.material.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.businessType.dao.MmBusinessTypeDao;
import com.huge.ihos.material.dao.InvCheckDao;
import com.huge.ihos.material.dao.InvCheckDetailDao;
import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvCheckDetail;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.service.InvCheckManager;
import com.huge.ihos.material.service.InvMainManager;
import com.huge.ihos.material.typeno.model.InvTypeNo;
import com.huge.ihos.material.typeno.service.InvTypeNoManager;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.busiprocess.service.BusiProcessManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateConverter;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("invCheckManager")
public class InvCheckManagerImpl extends GenericManagerImpl<InvCheck, String> implements InvCheckManager {
    private InvCheckDao invCheckDao;
    private InvCheckDetailDao invCheckDetailDao;
    private InvMainManager invMainManager;
    private MmBusinessTypeDao businessTypeDao;
    //private InvBillNumberManager invBillNumberManager;
    private BillNumberManager billNumberManager;
	private InvTypeNoManager invTypeNoManager;
    private BusiProcessManager businessProcessManager;
    
    @Autowired
    public void setBusinessProcessManager(BusiProcessManager businessProcessManager) {
		this.businessProcessManager = businessProcessManager;
	}

	@Autowired
    public void setBusinessTypeDao(MmBusinessTypeDao businessTypeDao) {
		this.businessTypeDao = businessTypeDao;
	}

	@Autowired
	public void setInvMainManager(InvMainManager invMainManager) {
		this.invMainManager = invMainManager;
	}
	
	@Autowired
	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
	
	@Autowired
	public void setInvTypeNoManager(InvTypeNoManager invTypeNoManager) {
		this.invTypeNoManager = invTypeNoManager;
	}

	@Autowired
    public void setInvCheckDetailDao(InvCheckDetailDao invCheckDetailDao) {
		this.invCheckDetailDao = invCheckDetailDao;
	}

	@Autowired
    public InvCheckManagerImpl(InvCheckDao invCheckDao) {
        super(invCheckDao);
        this.invCheckDao = invCheckDao;
    }
    
    public JQueryPager getInvCheckCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invCheckDao.getInvCheckCriteria(paginatedList,filters);
	}

	@Override
	public InvCheck getInvCheckByCheckNo(String checkNo, String orgCode,
			String copyCode) {
		return invCheckDao.getInvCheckByCheckNo(checkNo, orgCode, copyCode);
	}

	@Override
	public InvCheck saveInvCheck(InvCheck invCheck, InvCheckDetail[] invCheckDetails) {
		if(OtherUtil.measureNull(invCheck.getCheckId())){
			invCheck.setCheckId(null);
		}
		if(invCheck.getCheckNo()==null || invCheck.getCheckNo().trim().length()==0){
			//String tempCheckNo = invBillNumberManager.createNextBillNumber("MMPD", invCheck.getOrgCode(), invCheck.getCopyCode(), invCheck.getYearMonth(),false);
			String tempCheckNo = billNumberManager.createNextBillNumber("MM","MMPD", false, invCheck.getOrgCode(), invCheck.getCopyCode(),invCheck.getYearMonth());
			invCheck.setCheckNo("临-"+tempCheckNo);
		}
		invCheck = invCheckDao.save(invCheck);
		InvCheckDetail invCheckDetail = null;
		Double totalDiffMoney = 0.0;
		for(int i=0;i<invCheckDetails.length;i++){
			invCheckDetail = invCheckDetails[i];
			invCheckDetail.setInvCheck(invCheck);
			invCheckDetail.setDiffAmount(invCheckDetail.getChkAmount()-invCheckDetail.getAcctAmount());
			invCheckDetailDao.save(invCheckDetail);
			totalDiffMoney += invCheckDetail.getDiffAmount()*invCheckDetail.getPrice();
		}
		invCheck.setTotalDiffMoney(totalDiffMoney);
		invCheckDao.save(invCheck);
		return invCheck;
	}

	@Override
	public void removeInvCheck(InvCheck invCheck,
			List<InvCheckDetail> invCheckDetails) {
		try {
			int size = invCheckDetails.size();
			for(int i=0;i<size;i++){
				invCheckDetailDao.remove(invCheckDetails.get(i).getInvCheckDetailId());
			}
			invCheckDao.remove(invCheck.getCheckId());
		} catch (Exception e) {
			log.debug("delete invCheck and invCheckDetails error:"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean exportCheckInOut(String checkId,Person person) {
		InvCheck invCheck = this.get(new String(checkId));
		List<InvCheckDetail> invCheckDetails = invCheckDetailDao.getInvCheckDetailsInSameCheck(invCheck);
		if(invCheckDetails!=null && invCheckDetails.size()>0){
			InvMain invMainIn = new InvMain(), invMainOut = new InvMain();
			
			invMainIn = initInvMain(invMainIn,"1","7",invCheck,person);
			invMainOut = initInvMain(invMainOut,"2","8",invCheck,person);
			
			InvDetail invDetailIn = null, invDetailOut = null;
			List<InvDetail> invDetailIns = new ArrayList<InvDetail>(),invDetailOuts = new ArrayList<InvDetail>();
			InvCheckDetail invCheckDetail = null;
			for (int i = 0; i < invCheckDetails.size(); i++) {
				invCheckDetail = invCheckDetails.get(i);
				if (invCheckDetail.getDiffAmount()== 0) {// 持平
					continue;
				}
				if (invCheckDetail.getDiffAmount() > 0) {// 盘盈入库 7
					invDetailIn = new InvDetail();
					invDetailIn = initInvDetail(invDetailIn,invCheckDetail,invCheckDetail.getDiffAmount());
					invDetailIns.add(invDetailIn);
				}
				if (invCheckDetail.getDiffAmount() < 0) {// 盘亏出库 8
					invDetailOut = new InvDetail();
					invDetailOut = initInvDetail(invDetailOut,invCheckDetail,-invCheckDetail.getDiffAmount());
					invDetailOuts.add(invDetailOut);
				}
			}
			if(invDetailIns.size()>0){
				invMainIn = invMainManager.saveInvMain(invMainIn, (InvDetail[])invDetailIns.toArray(new InvDetail[]{}));
				//做入库记账操作
				Set<InvDetail> invDetails = invMainIn.getInvDetails();
				for(InvDetail invDetail:invDetails){
					Object[] args = {invMainIn.getOrgCode(),invMainIn.getCopyCode(),invMainIn.getYearMonth(),person.getPersonId(),invMainIn.getStore().getId(),invDetail.getInvDict().getInvId(),invDetail.getInvBatch().getId(),invMainIn.getIoBillNumber()};
					businessProcessManager.execBusinessProcess("MMRKJZ", args);
				}
				invCheck.setInvMainIn(invMainIn);
			}
			if(invDetailOuts.size()>0){
				invMainOut = invMainManager.saveInvMain(invMainOut, (InvDetail[])invDetailOuts.toArray(new InvDetail[]{}));
				//做出库记账操作
				Set<InvDetail> invDetails = invMainOut.getInvDetails();
				for(InvDetail invDetail:invDetails){
					Object[] args = {invMainOut.getOrgCode(),invMainOut.getCopyCode(),invMainOut.getYearMonth(),person.getPersonId(),invMainOut.getStore().getId(),invDetail.getInvDict().getInvId(),invDetail.getInvBatch().getId(),invMainOut.getIoBillNumber()};
					businessProcessManager.execBusinessProcess("MMCKJZ", args);
				}
				invCheck.setInvMainOut(invMainOut);
			}
			
			invCheck.setState("2");
			invCheck.setConfirmPerson(person);
			UserContext userContext = UserContextUtil.getUserContext();
			invCheck.setConfirmDate(userContext.getBusinessDate());
			//String realCheckNo = invBillNumberManager.createNextBillNumber("MMPD", invCheck.getOrgCode(), invCheck.getCopyCode(), invCheck.getYearMonth(),true);
			String realCheckNo = billNumberManager.createNextBillNumber("MM","MMPD", true, invCheck.getOrgCode(), invCheck.getCopyCode(),invCheck.getYearMonth());
			
			invCheck.setCheckNo(realCheckNo);
			invCheck = this.save(invCheck);
			
			InvTypeNo invTypeNo = new InvTypeNo();
			invTypeNo.setOrgCode(invCheck.getOrgCode());
			invTypeNo.setCopyCode(invCheck.getCopyCode());
			invTypeNo.setNo(invCheck.getCheckNo());
			invTypeNo.setType("4");
			invTypeNoManager.save(invTypeNo);
			if(invDetailIns.size()==0 && invDetailOuts.size()==0){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	
	private InvMain initInvMain(InvMain invMain,String ioType,String busType,InvCheck invCheck,Person person){
		UserContext userContext = UserContextUtil.getUserContext();
		invMain.setOrgCode(userContext.getOrgCode());
		invMain.setCopyCode(userContext.getCopyCode());
		DateConverter dc = new DateConverter();
		invMain.setMakeDate((Date) (dc.convert(Date.class,
				userContext.getBusinessDate())));
		invMain.setMakePerson(person);
		invMain.setYearMonth(DateUtil.convertDateToString("yyyyMM",
				invMain.getMakeDate()));
		invMain.setConfirmPerson(person);
		invMain.setConfirmDate(
				userContext.getBusinessDate());
		invMain.setIoType(ioType);
		invMain.setBusType(businessTypeDao.get(busType));
		invMain.setStatus("2");
		invMain.setProctypeCode(null);
		invMain.setStore(invCheck.getStore());
		String bcode = "";
		if(ioType.equals("1")){
			bcode = "MMRK";
		}else if(ioType.equals("2")){
			bcode = "MMCK";
		}
		//String realMainNo = this.invBillNumberManager.createNextBillNumber(bcode, invMain.getOrgCode(), invMain.getCopyCode(), invMain.getYearMonth(),false);
		String realMainNo = billNumberManager.createNextBillNumber("MM",bcode, false, invMain.getOrgCode(), invMain.getCopyCode(),invMain.getYearMonth());
		invMain.setIoBillNumber(realMainNo);
		return invMain;
	}
	
	private InvDetail initInvDetail(InvDetail invDetail,InvCheckDetail invCheckDetail,Double amount){
		invDetail.setInvDict(invCheckDetail.getInvDict());
		invDetail.setInvBatch(invCheckDetail.getInvBatch());
		invDetail.setInAmount(amount);
		invDetail.setInPrice(invCheckDetail.getPrice());
		invDetail.setInMoney(invDetail.getInAmount()*invDetail.getInPrice());
		invDetail.setSnCode(invCheckDetail.getBarCode());
		return invDetail;
	}

}