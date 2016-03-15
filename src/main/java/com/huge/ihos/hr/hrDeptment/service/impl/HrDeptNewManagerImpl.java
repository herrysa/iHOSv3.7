package com.huge.ihos.hr.hrDeptment.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.dao.HrDeptNewDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptNew;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.HrDeptNewManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("hrDeptNewManager")
public class HrDeptNewManagerImpl extends GenericManagerImpl<HrDeptNew, String> implements HrDeptNewManager {
    private HrDeptNewDao hrDeptNewDao;
    private BillNumberManager billNumberManager;
    private HrDepartmentCurrentManager hrDepartmentCurrentManager;
    private HrDepartmentSnapManager hrDepartmentSnapManager;
    private SysTableStructureManager sysTableStructureManager;
    private HrOrgManager hrOrgManager;

    @Autowired
    public HrDeptNewManagerImpl(HrDeptNewDao hrDeptNewDao) {
        super(hrDeptNewDao);
        this.hrDeptNewDao = hrDeptNewDao;
    }
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    @Autowired
    public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
    @Autowired
   	public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
   		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
   	}
    @Autowired
    public void setSysTableStructureManager(SysTableStructureManager sysTableStructureManager) {
 		this.sysTableStructureManager = sysTableStructureManager;
 	}
    @Autowired
    public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
    public JQueryPager getHrDeptNewCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrDeptNewDao.getHrDeptNewCriteria(paginatedList,filters);
	}
    @Override
    public HrDeptNew save(HrDeptNew hrDeptNew){
    	if(OtherUtil.measureNull(hrDeptNew.getId())){
    		hrDeptNew.setNewNo(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_DEPT_NEW,hrDeptNew.getYearMonth()));
			// 给新增的的部门加部门撤销锁
		}
    	hrDeptNew.setCnCode(hrDeptNewDao.getPyCodes(hrDeptNew.getName()));
    	List<String> fDeptIds = getLockDeptIds(hrDeptNew);
		for(String deptId:fDeptIds){
			hrDepartmentSnapManager.lockHrDepartmentSnap(deptId, HrBusinessCode.DEPT_NEW);
		}
		return super.save(hrDeptNew);
    }
    
    public HrDeptNew saveAndConfirm(HrDeptNew hrDeptNew,Person person,Date operDate,boolean asyncData){
		/**
		 * 执行新增
		 * 新增部门，记录operLog
		 */
    	boolean isNew = false;
    	if(OtherUtil.measureNull(hrDeptNew.getId())){
    		isNew = true;
    		hrDeptNew.setNewNo(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_DEPT_NEW,hrDeptNew.getYearMonth()));
    	}
    	hrDeptNew.setCnCode(hrDeptNewDao.getPyCodes(hrDeptNew.getName()));
    	hrDeptNew.setConfirmDate(operDate);
    	hrDeptNew.setConfirmPerson(person);
    	hrDeptNew = super.save(hrDeptNew);
    	HrDepartmentSnap hrDepartmentSnap=new HrDepartmentSnap();
    	String deptId = hrDeptNew.getOrgCode()+"_" + hrDeptNew.getDeptCode();
		String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,operDate);
		//String snapId = deptId +"_"+snapCode;
		//hrDepartmentSnap.setSnapId(snapId);
		hrDepartmentSnap.setSnapCode(snapCode);
		hrDepartmentSnap.setDeptId(deptId);
		hrDepartmentSnap.setName(hrDeptNew.getName());
		hrDepartmentSnap.setDeptCode(hrDeptNew.getDeptCode());
		hrDepartmentSnap.setShortName(hrDeptNew.getShortnName());
		hrDepartmentSnap.setInternalCode(hrDeptNew.getInternalCode());
		hrDepartmentSnap.setOutin(hrDeptNew.getOutin());
		hrDepartmentSnap.setDeptType(hrDeptNew.getDeptClass());
		hrDepartmentSnap.setSubClass(hrDeptNew.getSubClass());
		hrDepartmentSnap.setPhone(hrDeptNew.getPhone());
		hrDepartmentSnap.setContact(hrDeptNew.getContact());
		hrDepartmentSnap.setContactPhone(hrDeptNew.getContactPhone());
		hrDepartmentSnap.setManager(hrDeptNew.getManager());
		hrDepartmentSnap.setRemark(hrDeptNew.getRemark());
		hrDepartmentSnap.setLeaf(hrDeptNew.getLeaf());
		hrDepartmentSnap.setClevel(hrDeptNew.getClevel());
		hrDepartmentSnap.setAttrCode(hrDeptNew.getAttrCode());
		hrDepartmentSnap.setKindCode(hrDeptNew.getKindCode());
		hrDepartmentSnap.setPlanCount(hrDeptNew.getPlanCount());
		hrDepartmentSnap.setRealCount(hrDeptNew.getRealCount());
		hrDepartmentSnap.setDiffCount(hrDeptNew.getDiffCount());
		String orgCode = hrDeptNew.getOrgCode();
		HrDepartmentCurrent hdc=null;
		if(OtherUtil.measureNotNull(hrDeptNew.getParentDept())&&OtherUtil.measureNotNull(hrDeptNew.getParentDept().getDepartmentId())){
			hdc=hrDepartmentCurrentManager.get(hrDeptNew.getParentDept().getDepartmentId());
			HrDepartmentSnap hds=hrDepartmentSnapManager.get(hdc.getDepartmentId()+"_"+hdc.getSnapCode());
			hds.setLeaf(false);
			hds=hrDepartmentSnapManager.saveHrDeptSnap(hds, null, person, operDate, asyncData);
			hrDepartmentSnap.setParentDeptId(hdc.getDepartmentId());
			hrDepartmentSnap.setHisPDSnapCode(hds.getSnapCode());
			orgCode = hdc.getOrgCode();
		}else{
			hrDepartmentSnap.setParentDept(null);
			hrDepartmentSnap.setHisPDSnapCode(null);
		}
		hrDepartmentSnap.setInvalidDate(hrDeptNew.getInvalidDate());
		hrDepartmentSnap.setCnCode(hrDeptNew.getCnCode());
		hrDepartmentSnap.setJjLeaf(hrDeptNew.getJjLeaf());
		if(OtherUtil.measureNull(hrDeptNew.getJjDept())){
			hrDepartmentSnap.setJjDeptId(deptId);
			hrDepartmentSnap.setHisJjDSnapCode(snapCode);
		}else{
			hdc=hrDepartmentCurrentManager.get(hrDeptNew.getJjDept().getDepartmentId());
			hrDepartmentSnap.setJjDeptId(hdc.getDepartmentId());
			hrDepartmentSnap.setHisJjDSnapCode(hdc.getSnapCode());
		}
		hrDepartmentSnap.setYsLeaf(hrDeptNew.getYsLeaf());
		if(OtherUtil.measureNull(hrDeptNew.getYsDept())){
			hrDepartmentSnap.setYsDeptId(deptId);
			hrDepartmentSnap.setHisYsDSnapCode(snapCode);
		}else{
			hdc=hrDepartmentCurrentManager.get(hrDeptNew.getYsDept().getDepartmentId());
			hrDepartmentSnap.setYsDeptId(hdc.getDepartmentId());
			hrDepartmentSnap.setHisYsDSnapCode(hdc.getSnapCode());
		}
		hrDepartmentSnap.setJjDeptType(hrDeptNew.getJjDeptType());
		hrDepartmentSnap.setPurchaseDept(hrDeptNew.getYsPurchasingDepartment());
		hrDepartmentSnap.setOrgCode(orgCode);
		hrDepartmentSnap.setOrgSnapCode(hrOrgManager.get(orgCode).getSnapCode());
		hrDepartmentSnap.setState(3);
		hrDepartmentSnap=hrDepartmentSnapManager.saveHrDeptSnap(hrDepartmentSnap, null, person, operDate, asyncData);
		Map<String,String> hrSubDataMap=new HashMap<String, String>();
		SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String str = sdf.format(operDate);
		hrSubDataMap.put("changePerson", person.getName());
		hrSubDataMap.put("changeDate", str);
		hrSubDataMap.put("changeType", "部门新增");
		hrSubDataMap.put("changeContent", "");
		hrSubDataMap.put("remark", "");
		sysTableStructureManager.insertHrSubData("hr_dept_deptChange",deptId,snapCode, hrSubDataMap);
		if(!isNew){
			// 给相关的部门解掉部门 撤销锁
			HrDepartmentCurrent parentDept=hrDeptNew.getParentDept();
			HrDepartmentCurrent jjDept=hrDeptNew.getJjDept();
			HrDepartmentCurrent ysDept=hrDeptNew.getYsDept();
			String parentDeptId=null;
			List<String> fDeptIds=null;
			String jjDeptId=null;
			String ysDeptId=null;
			if(OtherUtil.measureNotNull(parentDept)){
				parentDeptId=parentDept.getDepartmentId();
				fDeptIds=getUnLockparentDeptIds(parentDeptId,parentDept.getLeaf());
			}
			if(OtherUtil.measureNotNull(jjDept)){
				jjDeptId=jjDept.getDepartmentId();
			}
			if(OtherUtil.measureNotNull(ysDept)){
				ysDeptId=ysDept.getDepartmentId();
			}
			unlockHrDeptNewLock(parentDeptId,fDeptIds,jjDeptId,ysDeptId);
		}
		hrDeptNew = hrDeptNew.clone();
		hrDeptNew.setRemark(hrDepartmentSnap.getSnapId());
		return hrDeptNew;
	}
    
    
    private List<String> getLockDeptIds(HrDeptNew hrDeptNew){
		List<String> fDeptIds = new ArrayList<String>();
		HrDepartmentCurrent parentDept=hrDeptNew.getParentDept();
		HrDepartmentCurrent jjDept=hrDeptNew.getJjDept();
		HrDepartmentCurrent ysDept=hrDeptNew.getYsDept();
		if(!OtherUtil.measureNull(parentDept)){
			String parentDeptId=parentDept.getDepartmentId();
			fDeptIds.add(parentDeptId);
//			if(!parentDept.getLeaf()){
//				List<HrDepartmentCurrent> deptList = hrDepartmentCurrentManager.getAllDescendants(parentDeptId);
//				for(HrDepartmentCurrent dept:deptList){
//					fDeptIds.add(dept.getDepartmentId());
//				}
//			}
		}
		if(!OtherUtil.measureNull(jjDept)){
			String jjDeptId=jjDept.getDepartmentId();
			fDeptIds.add(jjDeptId);
//			if(!parentDept.getLeaf()){
//				List<HrDepartmentCurrent> deptList = hrDepartmentCurrentManager.getAllDescendants(jjDeptId);
//				for(HrDepartmentCurrent dept:deptList){
//					fDeptIds.add(dept.getDepartmentId());
//				}
//			}
		}
		if(!OtherUtil.measureNull(ysDept)){
			String ysDeptId=ysDept.getDepartmentId();
			fDeptIds.add(ysDeptId);
//			if(!parentDept.getLeaf()){
//				List<HrDepartmentCurrent> deptList = hrDepartmentCurrentManager.getAllDescendants(ysDeptId);
//				for(HrDepartmentCurrent dept:deptList){
//					fDeptIds.add(dept.getDepartmentId());
//				}
//			}
		}
		return fDeptIds;
	}
//    private void unlockHrDeptNewLock(HrDeptNew hrDeptNew){
//		List<String> fDeptIds = getLockDeptIds(hrDeptNew);
//		for(String deptId:fDeptIds){
//			hrDepartmentSnapManager.unlockHrDepartmentSnap(deptId, HrBusinessCode.DEPT_NEW);
//		}
//	}
    @Override
    public void auditHrDeptNew(List<String> checkIds,Person person,Date date){
    	for(String checkId:checkIds){
    		HrDeptNew hrDeptNew = hrDeptNewDao.get(checkId);
    		hrDeptNew.setState(2);
    		hrDeptNew.setCheckDate(date);
    		hrDeptNew.setCheckPerson(person);
    		hrDeptNewDao.save(hrDeptNew);
    	}
    }
    @Override
    public void doneHrDeptNew(List<String> doneIds,Person person,Date date,boolean ansycData){
    	for(String doneId:doneIds){
    		HrDeptNew hrDeptNew = hrDeptNewDao.get(doneId);
			hrDeptNew.setState(3);
			this.saveAndConfirm(hrDeptNew,person,date,ansycData);
    	}
    }
    @Override
    public void antiHrDeptNew(List<String> cancelCheckIds){
    	for(String cancelCheckId:cancelCheckIds){
    		HrDeptNew hrDeptNew = hrDeptNewDao.get(cancelCheckId);
    		hrDeptNew.setState(1);
    		hrDeptNew.setCheckDate(null);
    		hrDeptNew.setCheckPerson(null);
    		hrDeptNewDao.save(hrDeptNew);
    	}
    }
    @Override
    public void deleteHrDeptNew(List<String> delIds){
    	for(String delId:delIds){
    		HrDeptNew hrDeptNew = hrDeptNewDao.get(delId);
    		// 给相关的部门解掉部门 撤销锁
    		HrDepartmentCurrent parentDept=hrDeptNew.getParentDept();
    		HrDepartmentCurrent jjDept=hrDeptNew.getJjDept();
    		HrDepartmentCurrent ysDept=hrDeptNew.getYsDept();
    		String parentDeptId=null;
    		List<String> fDeptIds=null;
    		String jjDeptId=null;
    		String ysDeptId=null;
    		if(OtherUtil.measureNotNull(parentDept)){
    			parentDeptId=parentDept.getDepartmentId();
    			fDeptIds=getUnLockparentDeptIds(parentDeptId,parentDept.getLeaf());
    		}
    		if(OtherUtil.measureNotNull(jjDept)){
    			jjDeptId=jjDept.getDepartmentId();
    		}
    		if(OtherUtil.measureNotNull(ysDept)){
    			ysDeptId=ysDept.getDepartmentId();
    		}
    		hrDeptNewDao.remove(delId);
    		unlockHrDeptNewLock(parentDeptId,fDeptIds,jjDeptId,ysDeptId);
    	}
    }
    private List<String> getUnLockparentDeptIds(String parentDeptId,Boolean leaf){
    	List<String> fDeptIds = new ArrayList<String>();
		if(!OtherUtil.measureNull(parentDeptId)){
			fDeptIds.add(parentDeptId);
			if(!leaf){
				List<HrDepartmentCurrent> deptList = hrDepartmentCurrentManager.getAllDescendants(parentDeptId);
				for(HrDepartmentCurrent dept:deptList){
					fDeptIds.add(dept.getDepartmentId());
				}
			}
		}
		return fDeptIds;
    }
    private void unlockHrDeptNewLock(String parentDeptId,List<String> fDeptIds,String jjDeptId,String ysDeptId){
    	List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    	List<HrDeptNew> hrDeptNewsTemp=null;
    	if(OtherUtil.measureNotNull(parentDeptId)){
    		filters.add(new PropertyFilter("EQS_parentDept.departmentId",parentDeptId));
    		filters.add(new PropertyFilter("NEI_state","3"));
    		hrDeptNewsTemp=hrDeptNewDao.getByFilters(filters);
    		if(hrDeptNewsTemp==null||hrDeptNewsTemp.isEmpty()){
    			for(String deptId:fDeptIds){
    				hrDepartmentSnapManager.unlockHrDepartmentSnap(deptId, HrBusinessCode.DEPT_NEW);
    			}
    		}
    	}
    	if(OtherUtil.measureNotNull(jjDeptId)){
    		filters.clear();
    		filters.add(new PropertyFilter("EQS_jjDept.departmentId",parentDeptId));
        	filters.add(new PropertyFilter("NEI_state","3"));
        	hrDeptNewsTemp=hrDeptNewDao.getByFilters(filters);
        	if(hrDeptNewsTemp==null||hrDeptNewsTemp.isEmpty()){
        		hrDepartmentSnapManager.unlockHrDepartmentSnap(jjDeptId, HrBusinessCode.DEPT_NEW);
        	}
    	}
    	if(OtherUtil.measureNotNull(ysDeptId)){
    		filters.clear();
    		filters.add(new PropertyFilter("EQS_ysDept.departmentId",parentDeptId));
        	filters.add(new PropertyFilter("NEI_state","3"));
        	hrDeptNewsTemp=hrDeptNewDao.getByFilters(filters);
        	if(hrDeptNewsTemp==null||hrDeptNewsTemp.isEmpty()){
        		hrDepartmentSnapManager.unlockHrDepartmentSnap(ysDeptId, HrBusinessCode.DEPT_NEW);
        	}
    	}
	}
}