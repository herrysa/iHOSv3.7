package com.huge.ihos.hr.changesInPersonnel.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.changesInPersonnel.dao.SysPostMoveDao;
import com.huge.ihos.hr.changesInPersonnel.model.SysPostMove;
import com.huge.ihos.hr.changesInPersonnel.service.SysPostMoveManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Service("sysPostMoveManager")
public class SysPostMoveManagerImpl extends GenericManagerImpl<SysPostMove, String> implements SysPostMoveManager {
    private SysPostMoveDao sysPostMoveDao;
    private HrPersonSnapManager hrPersonSnapManager;
    private PactManager pactManager;
    private SysTableStructureManager sysTableStructureManager;
    private BillNumberManager billNumberManager;
    private HrDepartmentSnapManager hrDepartmentSnapManager;
    private HrPersonCurrentManager hrPersonCurrentManager;
    private PostManager postManager;
    
    @Autowired
     public void setSysTableStructureManager(SysTableStructureManager sysTableStructureManager) {
 		this.sysTableStructureManager = sysTableStructureManager;
 	}
    @Autowired
    public void setPostManager(PostManager postManager) {
		this.postManager = postManager;
	}
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    @Autowired
    public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}
    @Autowired
    public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}
    @Autowired
    public SysPostMoveManagerImpl(SysPostMoveDao sysPostMoveDao) {
        super(sysPostMoveDao);
        this.sysPostMoveDao = sysPostMoveDao;
    }
    @Autowired
    public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
    @Autowired
    public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}
    public JQueryPager getSysPostMoveCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return sysPostMoveDao.getSysPostMoveCriteria(paginatedList,filters);
	}
    
    @Override
    public void auditSysPostMove(List<String> checkIds,Person person,Date date){
    	SysPostMove sysPostMove = null;
		for(String checkId:checkIds){
			sysPostMove = sysPostMoveDao.get(checkId);
			sysPostMove.setState("1");
			sysPostMove.setChecker(person);
			sysPostMove.setCheckDate(date);
			sysPostMoveDao.save(sysPostMove);
		}
    }
    
    @Override
    public void antiSysPostMove(List<String> cancelCheckIds){
    	SysPostMove sysPostMove = null;
		for(String cancelCheckId:cancelCheckIds){
			sysPostMove = sysPostMoveDao.get(cancelCheckId);
			sysPostMove.setState("0");
			sysPostMove.setChecker(null);
			sysPostMove.setCheckDate(null);
			sysPostMoveDao.save(sysPostMove);	
		}
    }
    
    @Override
    public void doneSysPostMove(List<String> doneIds,Person person,Date date,boolean ansycData){
    	SysPostMove sysPostMove = null;
    	HrPersonHis hrPersonHis = null;
    	HrPersonSnap movePerson = null;
    	for(String doneId:doneIds){
    		sysPostMove = sysPostMoveDao.get(doneId);
 	
    		hrPersonHis = sysPostMove.getHrPersonHis();
    		movePerson = hrPersonSnapManager.get(hrPersonHis.getHisSnapId()).clone();
    		String oldPostType = "空岗位类别";
    		String oldDuty = "空岗位职务";
    		if(OtherUtil.measureNotNull(movePerson.getPostType())){
    			oldPostType = movePerson.getPostType();
    		}
    		if(OtherUtil.measureNotNull(movePerson.getDuty())&&OtherUtil.measureNotNull(movePerson.getDuty().getName())){
    			oldDuty = movePerson.getDuty().getName();
    		}
    		String content = movePerson.getName();
    		if(!sysPostMove.getPostType().equals(oldPostType)){
    			content += "岗位类别由[" + oldPostType + "]变动为[" + sysPostMove.getPostType() + "];";
    		}
    		
    		String postId = null;
    		if(OtherUtil.measureNotNull(sysPostMove.getDuty())&&OtherUtil.measureNotNull(sysPostMove.getDuty().getId())&&!sysPostMove.getDuty().getName().equals(oldDuty)){
    			content += "岗位职务由[" + oldDuty + "]变动为["+sysPostMove.getDuty().getName()+"]";
    			postId=sysPostMove.getDuty().getId();
    		}else if(!oldDuty.equals("空岗位职务")){
    			content += "岗位职务由[" + oldDuty + "]变动为[空岗位职务]";
    		}
    		movePerson.setPostType(sysPostMove.getPostType());
    		movePerson.setDuty(sysPostMove.getDuty());
    		HrPersonSnap hrPersonSnap = hrPersonSnapManager.saveHrPerson(movePerson, null,person, date,ansycData);
    		
    		sysPostMove.setState("2");
    		sysPostMove.setDoner(person);
    		sysPostMove.setDoneDate(date);
    		sysPostMove.setChangedSnapCode(hrPersonSnap.getSnapCode());
    		sysPostMoveDao.save(sysPostMove);	
    		
    		//岗位上锁(人员信息)
    		String lockState = "RY";
    		postManager.lockPost(postId, lockState);
    		/**子集写记录**/
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			hrSubDataMap.put("changeType", "人员调岗");
			SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String str = sdf.format(date);
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("changeDate", str);
			hrSubDataMap.put("changeContent", content);
			sysTableStructureManager.insertHrSubData("hr_person_changeInfo",hrPersonSnap.getPersonId(), hrPersonSnap.getSnapCode(), hrSubDataMap);
			
			//解锁
			String personId = sysPostMove.getHrPerson().getPersonId();
        	String deptId = hrPersonCurrentManager.get(personId).getDepartment().getDepartmentId();
        	unlockSysPostMove(personId,deptId,postId,false);
    	}
    }
    @Override
    public SysPostMove saveAndDoneSysPostMove(SysPostMove sysPostMove,Person person,Date date,Boolean isEntityIsNew,boolean ansycData){
    	if(isEntityIsNew){
    		sysPostMove.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_POSTMOVE, sysPostMove.getYearMonth()));
    		//			 sysPostMove.setCode(billNumberManager.createNextBillNumberForHR(BillNumberConstants.HR_PERSON_POSTMOVE));
		 }
    	//岗位上锁
		 String lockState = "DG";
		 String postId = null;
		 if(OtherUtil.measureNotNull(sysPostMove.getDuty())){
			 postId = sysPostMove.getDuty().getId();
		 }
		 postManager.lockPost(postId, lockState);
    	sysPostMove = this.save(sysPostMove);
    	List<String> doneIds=new ArrayList<String>();
		doneIds.add(sysPostMove.getId());
		this.doneSysPostMove(doneIds, person,date,ansycData);
		return sysPostMove;
    }
    @Override
    public void doneChangePact(List<String> doneIds){
    	SysPostMove sysPostMove = null;
    	for(String doneId:doneIds){
    		sysPostMove = sysPostMoveDao.get(doneId);
    		String personId=sysPostMove.getHrPerson().getPersonId();
    		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_hrPerson.personId", personId));
    		List<Pact> pacts=new ArrayList<Pact>();
    		pacts=pactManager.getByFilters(filters);
    		if(pacts!=null&&pacts.size()>0){
    			for(Pact pact:pacts){
    				pact.setPost(sysPostMove.getDuty());
    				pactManager.save(pact);
    			}
    		}
    		
    	}
    }
    @Override
    public SysPostMove saveSysPostMove(SysPostMove sysPostMove,Boolean isEntityIsNew){
    	String lockState = "DG";
    	if(isEntityIsNew){
    		sysPostMove.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_POSTMOVE, sysPostMove.getYearMonth()));
    		 lockSysPostMove(sysPostMove);
    	}
    	String postId = null;
    	if(OtherUtil.measureNotNull(sysPostMove.getDuty())){
    		postId = sysPostMove.getDuty().getId();
    	}
    	postManager.lockPost(postId, lockState);
    	sysPostMove = sysPostMoveDao.save(sysPostMove);
    	return sysPostMove;
    }
    private void lockSysPostMove(SysPostMove sysPostMove){
    	String lockState = "DG";
    	String personId=sysPostMove.getHrPerson().getPersonId();
    	hrPersonSnapManager.lockHrPerson(personId, lockState);
    	String deptId=hrPersonCurrentManager.get(personId).getDepartment().getDepartmentId();
    	hrDepartmentSnapManager.lockHrDepartmentSnap(deptId, lockState);
    }
    private void unlockSysPostMove(String personId,String deptId,String postId,Boolean isDel){
    	String lockState="DG";
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_hrPerson.personId", personId));
    	filters.add(new PropertyFilter("NES_state", "2"));
    	List<SysPostMove> sysPostMovesTemp=sysPostMoveDao.getByFilters(filters);
    	if(sysPostMovesTemp==null||sysPostMovesTemp.isEmpty()){
    		hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
    	}
    	filters.clear();
    	filters.add(new PropertyFilter("EQS_hrPerson.department.departmentId", deptId));
    	filters.add(new PropertyFilter("NES_state", "2"));
    	sysPostMovesTemp=sysPostMoveDao.getByFilters(filters);
    	if(sysPostMovesTemp==null||sysPostMovesTemp.isEmpty()){
    		hrDepartmentSnapManager.unlockHrDepartmentSnap(deptId, lockState);
    	}
    	if(isDel){
    		filters.clear();
        	filters.add(new PropertyFilter("EQS_duty.id", postId));
        	sysPostMovesTemp=sysPostMoveDao.getByFilters(filters);
        	if(sysPostMovesTemp==null||sysPostMovesTemp.isEmpty()){
        		postManager.unlockPost(postId, lockState);
        	}
    	}
    }
    @Override
    public void deleteSysPostMove(List<String> delIds){
    	SysPostMove sysPostMove=null;
    	for(String delId:delIds){
    		sysPostMove=sysPostMoveDao.get(delId);
    		String personId=sysPostMove.getHrPerson().getPersonId();
    		String deptId=hrPersonCurrentManager.get(personId).getDepartment().getDepartmentId();
    		String postId = null;
    		if(OtherUtil.measureNotNull(sysPostMove.getDuty())){
    			postId = sysPostMove.getDuty().getId();
    		}
        	sysPostMoveDao.remove(delId);
        	unlockSysPostMove(personId,deptId,postId,true);
    	}
    }
}