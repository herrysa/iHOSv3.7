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
import com.huge.ihos.hr.hrDeptment.dao.HrDeptRescindDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptRescind;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.HrDeptRescindManager;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrDeptRescindManager")
public class HrDeptRescindManagerImpl extends GenericManagerImpl<HrDeptRescind, String> implements HrDeptRescindManager {
    private HrDeptRescindDao hrDeptRescindDao;
    private HrDepartmentSnapManager hrDepartmentSnapManager;
    private BillNumberManager billNumberManager;
    private HrPersonCurrentManager hrPersonCurrentManager;
    private HrPersonSnapManager hrPersonSnapManager;
    private PostManager postManager;
    private SysTableStructureManager sysTableStructureManager;
    private HrDepartmentCurrentManager hrDepartmentCurrentManager;
    private HrOrgManager hrOrgManager;
    
    @Autowired
    public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
    
    @Autowired
    public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
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
    public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}

	@Autowired
	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
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
    public HrDeptRescindManagerImpl(HrDeptRescindDao hrDeptRescindDao) {
        super(hrDeptRescindDao);
        this.hrDeptRescindDao = hrDeptRescindDao;
    }
    
    public JQueryPager getHrDeptRescindCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrDeptRescindDao.getHrDeptRescindCriteria(paginatedList,filters);
	}

	@Override
	public HrDeptRescind save(HrDeptRescind hrDeptRescind) {
		if(OtherUtil.measureNull(hrDeptRescind.getId())){
			hrDeptRescind.setRescindNo(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_DEPT_RESCIND,hrDeptRescind.getYearMonth()));
			// 给被撤销的部门加部门撤销锁
			List<String> fDeptIds = getLockDeptIds(hrDeptRescind);
			for(String deptId:fDeptIds){
				hrDepartmentSnapManager.lockHrDepartmentSnap(deptId, HrBusinessCode.DEPT_RESCIND);
			}
		}
		return super.save(hrDeptRescind);
	}
	
	private List<String> getLockDeptIds(HrDeptRescind hrDeptRescind){
		List<String> fDeptIds = new ArrayList<String>();
		String fromDeptId = hrDeptRescind.getHrDept().getDepartmentId();
		fDeptIds.add(fromDeptId);
		HrDepartmentCurrent fromDept = hrDepartmentCurrentManager.get(fromDeptId);
		if(!fromDept.getLeaf()){
			List<HrDepartmentCurrent> deptList = hrDepartmentCurrentManager.getAllDescendants(fromDeptId);
			for(HrDepartmentCurrent dept:deptList){
				fDeptIds.add(dept.getDepartmentId());
			}
		}
		return fDeptIds;
	}
	
	public HrDeptRescind saveAndConfirm(HrDeptRescind hrDeptRescind,Person person,Date operDate,boolean asyncData){
		/**
		 * 执行撤销
		 * 1.赋值撤销单，保存撤销单
		 * 2.给部门解锁
		 * 3.修改被撤销部门状态为4并设为停用，记录operLog
		 * 4.复制岗位，移动人员，记录operLog
		 */
		boolean isNew = false;
		if(OtherUtil.measureNull(hrDeptRescind.getId())){
			isNew = true;
			hrDeptRescind.setRescindNo(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_DEPT_RESCIND,hrDeptRescind.getYearMonth()));
		}
		
		Map<String,String> hrSubDataMap=new HashMap<String, String>();
		SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String str = sdf.format(operDate);
		hrSubDataMap.put("changePerson", person.getName());
		hrSubDataMap.put("changeDate", str);
		hrSubDataMap.put("changeType", "部门撤销");
		hrSubDataMap.put("changeContent", "部门被撤销,人员归属到部门["+hrDeptRescind.getMoveToDeptHis().getName()+"]");
		hrSubDataMap.put("remark", "");
		sysTableStructureManager.insertHrSubData("hr_dept_deptChange",hrDeptRescind.getHrDeptHis().getHrDeptPk().getDeptId(), hrDeptRescind.getHrDeptHis().getHrDeptPk().getSnapCode(), hrSubDataMap);
	
		if(!isNew){
			// 给相关的部门解掉部门 撤销锁
			unlockDeptRescindLock(hrDeptRescind);
		}
		
		String deptSnapId = hrDeptRescind.getHrDept().getDepartmentId()+"_"+hrDeptRescind.getSnapCode();
		HrDepartmentSnap hps = hrDepartmentSnapManager.get(deptSnapId).clone();
		hps.setDisabled(true);
		hps.setState(4);
		hps = hrDepartmentSnapManager.saveHrDeptSnap(hps, null, person,operDate,asyncData);
		
		hrDeptRescind.setConfirmDate(operDate);
		hrDeptRescind.setConfirmPerson(person);
		hrDeptRescind.setSnapCode(hps.getSnapCode());
		hrDeptRescind = this.save(hrDeptRescind);
		
		movePostAndPerson(hrDeptRescind,person,operDate,asyncData);
		return hrDeptRescind;
	}
	
	private void movePostAndPerson(HrDeptRescind hrDeptRescind,Person operPerson,Date operDate,boolean asyncData){
		/**
		 * 1.将原岗位集合复制到归属部门集合下
		 * 2.如果原部门是末级部门，则移动人员
		 * 3.将人员的岗位替换为新的岗位
		 */
		HrDepartmentCurrent fromHrDept = hrDeptRescind.getHrDept();
		HrDepartmentCurrent toHrDept = hrDeptRescind.getMoveToDept();
		Map<String,Post> toPostMap = this.getPostMapByDeptId(toHrDept.getDepartmentId());
		if(!fromHrDept.getLeaf()){
			return;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_department.departmentId",fromHrDept.getDepartmentId()));
		List<HrPersonCurrent> hrPersonCurrentList = this.hrPersonCurrentManager.getByFilters(filters);
		if(hrPersonCurrentList!=null && !hrPersonCurrentList.isEmpty()){
			HrPersonSnap newPerson = null;
			Post post = null;
			for(HrPersonCurrent hrPerson:hrPersonCurrentList){
				newPerson = this.hrPersonSnapManager.get(hrPerson.getPersonId()+"_"+hrPerson.getSnapCode()).clone();
				newPerson.setOrgCode(toHrDept.getOrgCode());
				newPerson.setOrgSnapCode(this.hrOrgManager.get(toHrDept.getOrgCode()).getSnapCode());
				newPerson.setHrDept(toHrDept);
				newPerson.setDeptSnapCode(toHrDept.getSnapCode());
				Post oldPost = hrPerson.getDuty();
				if(oldPost!=null){
					post = toPostMap.get(oldPost.getName());
					if(post==null){
						post = postManager.addPostForDept(oldPost,toHrDept.getDepartmentId());
					}
					newPerson.setDuty(post);
				}
				newPerson = hrPersonSnapManager.saveHrPerson(newPerson, null,operPerson, operDate,asyncData);
				/**子集写记录**/
				Map<String,String> hrSubDataMap=new HashMap<String, String>();
				hrSubDataMap.put("changeType", "部门撤销");
				SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				String str = sdf.format(operDate);
				hrSubDataMap.put("changePerson", operPerson.getName());
				hrSubDataMap.put("changeDate", str);
				hrSubDataMap.put("changeContent", "当前人从["+hrPerson.getDepartment().getName()+"]部门转到["+toHrDept.getName()+"]部门");
				sysTableStructureManager.insertHrSubData("hr_person_changeInfo",newPerson.getPersonId(), newPerson.getSnapCode(), hrSubDataMap);
			}
		}
		postManager.disablePostByDeptId(fromHrDept.getDepartmentId());
	}
	
	private Map<String,Post> getPostMapByDeptId(String deptId){
		Map<String,Post> map = new HashMap<String,Post>();
		List<Post> postList = postManager.getPostByDeptId(deptId);
		if(postList!=null){
			for(Post post:postList){
				map.put(post.getName(), post);
			}
		}
		return map;
	}

	@Override
	public void remove(String[] ids) {
		HrDeptRescind hrDeptRescind = null;
		for(String id:ids){
			hrDeptRescind = this.get(id);
			// 给相关的部门解掉部门 撤销锁
			unlockDeptRescindLock(hrDeptRescind);
		}
		super.remove(ids);
	}
	
	private void unlockDeptRescindLock(HrDeptRescind hrDeptRescind){
		List<String> fDeptIds = getLockDeptIds(hrDeptRescind);
		for(String deptId:fDeptIds){
			hrDepartmentSnapManager.unlockHrDepartmentSnap(deptId, HrBusinessCode.DEPT_RESCIND);
		}
	}
	@Override
    public void auditHrDeptRescind(List<String> checkIds,Person person,Date date){
    	for(String checkId:checkIds){
    		HrDeptRescind hrDeptRescind = hrDeptRescindDao.get(checkId);
    		hrDeptRescind.setState(2);
    		hrDeptRescind.setCheckDate(date);
    		hrDeptRescind.setCheckPerson(person);
    		hrDeptRescindDao.save(hrDeptRescind);
    	}
    }
    @Override
    public void doneHrDeptRescind(List<String> doneIds,Person person,Date date,boolean ansycData){
    	for(String doneId:doneIds){
    		HrDeptRescind hrDeptRescind = hrDeptRescindDao.get(doneId);
    		hrDeptRescind.setState(3);
			this.saveAndConfirm(hrDeptRescind,person,date,ansycData);
    	}
    }
    @Override
    public void antiHrDeptRescind(List<String> cancelCheckIds){
    	for(String cancelCheckId:cancelCheckIds){
    		HrDeptRescind hrDeptRescind = hrDeptRescindDao.get(cancelCheckId);
    		hrDeptRescind.setState(1);
    		hrDeptRescind.setCheckDate(null);
    		hrDeptRescind.setCheckPerson(null);
    		hrDeptRescindDao.save(hrDeptRescind);
    	}
    }
}