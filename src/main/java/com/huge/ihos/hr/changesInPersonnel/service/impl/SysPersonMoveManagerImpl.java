package com.huge.ihos.hr.changesInPersonnel.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.changesInPersonnel.dao.SysPersonMoveDao;
import com.huge.ihos.hr.changesInPersonnel.model.SysPersonMove;
import com.huge.ihos.hr.changesInPersonnel.service.SysPersonMoveManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
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

@Service("sysPersonMoveManager")
public class SysPersonMoveManagerImpl extends GenericManagerImpl<SysPersonMove, String> implements SysPersonMoveManager {
    private SysPersonMoveDao sysPersonMoveDao;
    private HrPersonSnapManager hrPersonSnapManager;
    private PactManager pactManager;
    private SysTableStructureManager sysTableStructureManager;
    private BillNumberManager billNumberManager;
    private HrPersonCurrentManager hrPersonCurrentManager;
    private HrDepartmentSnapManager hrDepartmentSnapManager;
    private PostManager postManager;
    private HrDepartmentCurrentManager hrDepartmentCurrentManager;
    
    @Autowired
	public void setHrDepartmentCurrentManager(
			HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
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
    public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}
    @Autowired
    public SysPersonMoveManagerImpl(SysPersonMoveDao sysPersonMoveDao) {
        super(sysPersonMoveDao);
        this.sysPersonMoveDao = sysPersonMoveDao;
    }
    @Autowired
    public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}
    @Autowired
    public void setPactManager(PactManager pactManager) {
  		this.pactManager = pactManager;
  	}
    @Autowired
    public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
    
    public JQueryPager getSysPersonMoveCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return sysPersonMoveDao.getSysPersonMoveCriteria(paginatedList,filters);
	}
    @Override
    public void auditSysPersonMove(List<String> checkIds,Person person,Date date){
    	SysPersonMove sysPersonMove = null;
		for(String checkId:checkIds){
			sysPersonMove = sysPersonMoveDao.get(checkId);
			sysPersonMove.setState("1");
			sysPersonMove.setChecker(person);
			sysPersonMove.setCheckDate(date);
			sysPersonMoveDao.save(sysPersonMove);
		}
    }
    
    @Override
    public void antiSysPersonMove(List<String> cancelCheckIds){
    	SysPersonMove sysPersonMove = null;
		for(String cancelCheckId:cancelCheckIds){
			sysPersonMove = sysPersonMoveDao.get(cancelCheckId);
			sysPersonMove.setState("0");
			sysPersonMove.setChecker(null);
			sysPersonMove.setCheckDate(null);
			sysPersonMoveDao.save(sysPersonMove);
		}
					
    }
    @Override
    public void doneSysPersonMove(List<String> doneIds,Person person,Date date,boolean ansycData){
    	/**
    	 * 人员调动的执行逻辑
    	 * 1.更改调动单状态
    	 * 2.移动人员(保存人员)
    	 * 	2.1 修改人员个别属性值
    	 * 	2.2 记录人员变动的log
    	 * 	2.3 同步t_person表
    	 */
    	SysPersonMove sysPersonMove = null;
    	HrPersonHis hrPersonHis = null;
    	HrPersonSnap movePerson = null;
    	for(String doneId:doneIds){
    		sysPersonMove = sysPersonMoveDao.get(doneId);
    		
    		hrPersonHis = sysPersonMove.getHrPersonHis();
    		movePerson = hrPersonSnapManager.get(hrPersonHis.getHisSnapId()).clone();
    		String oldDupy = "空岗位职务";
    		if(OtherUtil.measureNotNull(movePerson.getDuty())&&OtherUtil.measureNotNull(movePerson.getDuty().getName())){
    			oldDupy = movePerson.getDuty().getName();
    		}
    		String oldPostType="空岗位类别";
    		if(OtherUtil.measureNotNull(movePerson.getPostType())){
    			oldPostType = movePerson.getPostType();
    		}
    		String content = movePerson.getName()+"由["+movePerson.getHrOrg().getOrgname()+"]单位下["+movePerson.getHrDept().getName()+"]部门";
    		String deptId = movePerson.getHrDept().getDepartmentId();
    		movePerson.setOrgCode(sysPersonMove.getHrDeptNew().getOrgCode());
    		movePerson.setOrgSnapCode(sysPersonMove.getHrDeptNew().getOrgSnapCode());
    		movePerson.setHrDept(sysPersonMove.getHrDeptNew());
    		movePerson.setDeptSnapCode(sysPersonMove.getDeptSnapCodeNew());
    		movePerson.setDuty(sysPersonMove.getDuty());
    		movePerson.setPostType(sysPersonMove.getPostType());
    		content += "调到["+sysPersonMove.getHrDeptNew().getHrOrg().getOrgname()+"]单位下["+sysPersonMove.getHrDeptNew().getName()+"]部门";
    		HrPersonSnap hrPersonSnap = hrPersonSnapManager.saveHrPerson(movePerson, null,person, date,ansycData);
    		
    		sysPersonMove.setState("2");
    		sysPersonMove.setDoner(person);
    		sysPersonMove.setDoneDate(date);
    		sysPersonMove.setChangedSnapCode(hrPersonSnap.getSnapCode());
    		sysPersonMove = sysPersonMoveDao.save(sysPersonMove);
    		
    		//岗位上锁(人员信息)
    		String postId = null;
    		if(!oldPostType.equals(sysPersonMove.getPostType())){
    			content += ";岗位类别由["+oldPostType+"]变动为["+sysPersonMove.getPostType()+"]";
    		}
    		if(OtherUtil.measureNotNull(sysPersonMove.getDuty())&&OtherUtil.measureNotNull(sysPersonMove.getDuty().getId())&&!sysPersonMove.getDuty().getName().equals(oldDupy)){
    			content += ";岗位职务由["+oldDupy+"]变动为["+sysPersonMove.getDuty().getName()+"]";
    			String lockState = "RY";
    			postId = sysPersonMove.getDuty().getId();
        		postManager.lockPost(postId, lockState);
    		}else if(!oldDupy.equals("空岗位职务")){
    			content += ";岗位职务由["+oldDupy+"]变动为[空岗位职务]";
    		}
    		
    		/**子集写记录**/
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			hrSubDataMap.put("changeType", "人员调动");
			SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String str = sdf.format(date);
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("changeDate", str);
			hrSubDataMap.put("changeContent", content);
			sysTableStructureManager.insertHrSubData("hr_person_changeInfo",hrPersonSnap.getPersonId(), hrPersonSnap.getSnapCode(), hrSubDataMap);
			
			//解锁
			 String personId = sysPersonMove.getHrPerson().getPersonId();
			 String newDeptId = sysPersonMove.getHrDeptNew().getDepartmentId();
			 unlockSysPersonMove(personId,deptId,newDeptId,postId,false);
    	}
    }
	@Override
	public SysPersonMove saveAndDoneSysPersonMove(SysPersonMove sysPersonMove,Person person,Date date,Boolean isEntityIsNew,boolean ansycData){
		 if(isEntityIsNew){
			 sysPersonMove.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_MOVE, sysPersonMove.getYearMonth()));
//			 sysPersonMove.setCode(billNumberManager.createNextBillNumberForHR(BillNumberConstants.HR_PERSON_MOVE));
		 }
		//岗位上锁
		if(OtherUtil.measureNotNull(sysPersonMove.getDuty())&&OtherUtil.measureNotNull(sysPersonMove.getDuty().getId())){
			String lockState="DD";
			String postId=sysPersonMove.getDuty().getId();
			postManager.lockPost(postId, lockState);
		}
		sysPersonMove=this.save(sysPersonMove);
		String doneId=sysPersonMove.getId();
		this.doneSysPersonMoveOnlyOne(doneId, person,date,ansycData);
		return sysPersonMove;
	}
	@Override
	 public void doneChangePact(List<String> doneIds){
		SysPersonMove sysPersonMove = null;
		for(String doneId:doneIds){
			sysPersonMove = sysPersonMoveDao.get(doneId);
			String personId=sysPersonMove.getHrPerson().getPersonId();
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_hrPerson.personId", personId));
    		List<Pact> pacts=new ArrayList<Pact>();
    		pacts=pactManager.getByFilters(filters);
    		if(pacts!=null&&pacts.size()>0){
    			for(Pact pact:pacts){
    				pact.setPost(sysPersonMove.getDuty());
    				pact.setDept(sysPersonMove.getHrDeptNew());
    				pact.setDeptSnapCode(sysPersonMove.getDeptSnapCodeNew());
    				pact.setDeptHis(sysPersonMove.getHrDeptHisNew());
    				pactManager.save(pact);
    			}
    		}
		}
	 }
	 private void doneSysPersonMoveOnlyOne(String doneId,Person person,Date date,boolean ansycData){
	    	/**
	    	 * 人员调动的执行逻辑
	    	 * 1.更改调动单状态
	    	 * 2.移动人员(保存人员)
	    	 * 	2.1 修改人员个别属性值
	    	 * 	2.2 记录人员变动的log
	    	 * 	2.3 同步t_person表
	    	 */
	    	SysPersonMove sysPersonMove = null;
	    	HrPersonHis hrPersonHis = null;
	    	HrPersonSnap movePerson = null;
	    		sysPersonMove = sysPersonMoveDao.get(doneId);
	    		
	    		hrPersonHis = sysPersonMove.getHrPersonHis();
	    		movePerson = hrPersonSnapManager.get(hrPersonHis.getHisSnapId()).clone();
	    		String oldDuty = "空岗位职务";
	    		if(OtherUtil.measureNotNull(movePerson.getDuty())&&OtherUtil.measureNotNull(movePerson.getDuty().getName())){
	    			oldDuty = movePerson.getDuty().getName();
	    		}
	    		String oldPostType = "空岗位类别";
	    		if(OtherUtil.measureNotNull(movePerson.getPostType())){
	    			oldPostType = movePerson.getPostType();
	    		}
	    		String content = movePerson.getName()+"由["+movePerson.getHrOrg().getOrgname()+"]单位下["+movePerson.getHrDept().getName()+"]部门";
	    		movePerson.setOrgCode(sysPersonMove.getHrDeptNew().getOrgCode());
	    		movePerson.setHrDept(sysPersonMove.getHrDeptNew());
	    		movePerson.setDeptSnapCode(sysPersonMove.getDeptSnapCodeNew());
	    		movePerson.setPostType(sysPersonMove.getPostType());
	    		movePerson.setDuty(sysPersonMove.getDuty());
	    		content += "调到["+sysPersonMove.getHrDeptNew().getHrOrg().getOrgname()+"]单位下["+sysPersonMove.getHrDeptNew().getName()+"]部门";
	    		HrPersonSnap hrPersonSnap = hrPersonSnapManager.saveHrPerson(movePerson, null,person, date,ansycData);
	    		
	    		sysPersonMove.setCheckDate(date);
	    		sysPersonMove.setChecker(person);
	    		sysPersonMove.setState("2");
	    		sysPersonMove.setDoner(person);
	    		sysPersonMove.setDoneDate(date);
	    		sysPersonMove.setChangedSnapCode(hrPersonSnap.getSnapCode());
	    		sysPersonMove = sysPersonMoveDao.save(sysPersonMove);
	    		
	    		if(!oldPostType.equals(sysPersonMove.getPostType())){
	    			content += ";岗位类别由["+oldPostType+"]变动为["+sysPersonMove.getPostType()+"]";
	    		}
	    		//岗位上锁(人员信息)
	    		if(OtherUtil.measureNotNull(sysPersonMove.getDuty())&&OtherUtil.measureNotNull(sysPersonMove.getDuty().getId())&&!sysPersonMove.getDuty().getName().equals(oldDuty)){
	    			content += ";岗位职务由["+oldDuty+"]变动为["+sysPersonMove.getDuty().getName()+"]";
	    			String lockState="RY";
		    		String postId=sysPersonMove.getDuty().getId();
		    		postManager.lockPost(postId, lockState);
	    		}else if(!oldDuty.equals("空岗位职务")){
	    			content += ";岗位职务由["+oldDuty+"]变动为[空岗位职务]";
	    		}
	    		/**子集写记录**/
				Map<String,String> hrSubDataMap=new HashMap<String, String>();
				hrSubDataMap.put("changeType", "人员调动");
				SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				String str = sdf.format(date);
				hrSubDataMap.put("changePerson", person.getName());
				hrSubDataMap.put("changeDate", str);
				hrSubDataMap.put("changeContent", content);
				sysTableStructureManager.insertHrSubData("hr_person_changeInfo",hrPersonSnap.getPersonId(), hrPersonSnap.getSnapCode(), hrSubDataMap);
	    }
	 @Override
	 public SysPersonMove saveSysPersonMove(SysPersonMove sysPersonMove,Boolean isEntityIsNew){
		 //String personId=sysPersonMove.getHrPerson().getPersonId();
		 if(isEntityIsNew){
			 sysPersonMove.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_MOVE, sysPersonMove.getYearMonth()));
			 lockSysPersonMove(sysPersonMove);
		 }
		 if(OtherUtil.measureNotNull(sysPersonMove.getDuty())&&OtherUtil.measureNotNull(sysPersonMove.getDuty().getId())){
			 String lockState="DD";
			 String postId=sysPersonMove.getDuty().getId();
			 postManager.lockPost(postId, lockState);		
		 }
		 sysPersonMove=sysPersonMoveDao.save(sysPersonMove);
		 return sysPersonMove;
	 }
	 private void lockSysPersonMove(SysPersonMove sysPersonMove){
		 String lockState="DD";
		 String personId=sysPersonMove.getHrPerson().getPersonId();
		 String deptId=hrPersonCurrentManager.get(personId).getDepartment().getDepartmentId();
		 hrDepartmentSnapManager.lockHrDepartmentSnap(deptId, lockState); 
		 hrPersonSnapManager.lockHrPerson(personId, lockState);
		 deptId=sysPersonMove.getHrDeptNew().getDepartmentId();
		 hrDepartmentSnapManager.lockHrDepartmentSnap(deptId, lockState); 
	 }
	 private void unlockSysPersonMove(String personId,String deptId,String newDeptId,String postId,Boolean isDel){
		 String lockState="DD"; 
		 List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		 filters.add(new PropertyFilter("EQS_hrPerson.personId", personId));
		 filters.add(new PropertyFilter("NES_state", "2"));
		 List<SysPersonMove> sysPersonMovesTemp=sysPersonMoveDao.getByFilters(filters);
		 if(sysPersonMovesTemp==null||sysPersonMovesTemp.isEmpty()){
			 hrPersonSnapManager.unlockHrPersonSnap(personId, lockState);
		 }
		 filters.clear();
		 filters.add(new PropertyFilter("EQS_hrPerson.department.departmentId", deptId));
		 filters.add(new PropertyFilter("NES_state", "2"));
		 sysPersonMovesTemp=sysPersonMoveDao.getByFilters(filters);
		 if(sysPersonMovesTemp==null||sysPersonMovesTemp.isEmpty()){
			hrDepartmentSnapManager.unlockHrDepartmentSnap(deptId, lockState);
		 }
		 filters.clear();
		 filters.add(new PropertyFilter("EQS_hrDeptNew.departmentId", newDeptId));
		 filters.add(new PropertyFilter("NES_state", "2"));
		 sysPersonMovesTemp=sysPersonMoveDao.getByFilters(filters);
		 if(sysPersonMovesTemp==null||sysPersonMovesTemp.isEmpty()){
			hrDepartmentSnapManager.unlockHrDepartmentSnap(newDeptId, lockState);
		 }
		 if(OtherUtil.measureNotNull(postId)&&isDel){
			 filters.clear();
			 filters.add(new PropertyFilter("EQS_duty.id", postId));
			 sysPersonMovesTemp=sysPersonMoveDao.getByFilters(filters);
			 if(sysPersonMovesTemp==null||sysPersonMovesTemp.isEmpty()){
				postManager.unlockPost(postId, lockState);
			 }
		 }
	 }
	 @Override
	 public void deleteSysPersonMove(List<String> delIds){
		
		 SysPersonMove sysPersonMove=null;
		 for(String delId:delIds){
			 sysPersonMove=sysPersonMoveDao.get(delId);
			 String personId=sysPersonMove.getHrPerson().getPersonId();
			 String deptId=hrPersonCurrentManager.get(personId).getDepartment().getDepartmentId();
			 String postId=null;
			 if(OtherUtil.measureNotNull(sysPersonMove.getDuty())){
				 postId=sysPersonMove.getDuty().getId();
			 }
			 String newDeptId=sysPersonMove.getHrDeptNew().getDepartmentId();
			 sysPersonMoveDao.remove(delId);
			 unlockSysPersonMove(personId,deptId,newDeptId,postId,true);
		 }
	 }
}