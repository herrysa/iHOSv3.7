package com.huge.ihos.hr.hrDeptment.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.dao.HrDepartmentSnapDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptMerge;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.HrDeptMergeManager;
import com.huge.ihos.hr.hrOperLog.model.HrLogColumnInfo;
import com.huge.ihos.hr.hrOperLog.model.HrLogEntityInfo;
import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.hr.hrOperLog.service.HrOperLogManager;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.sysTables.dao.SysTableContentDao;
import com.huge.ihos.hr.sysTables.dao.SysTableStructureDao;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrDepartmentSnapManager")
public class HrDepartmentSnapManagerImpl extends GenericManagerImpl<HrDepartmentSnap, String> implements HrDepartmentSnapManager {
    private HrDepartmentSnapDao hrDepartmentSnapDao;
    private HrOperLogManager hrOperLogManager;
    private SysTableStructureDao sysTableStructureDao;
    private SysTableContentDao sysTableContentDao;
    private DepartmentDao departmentDao;
    private HrDeptMergeManager hrDeptMergeManager;
    private HrDepartmentCurrentManager hrDepartmentCurrentManager;
    private HrOrgManager hrOrgManager;
    
    @Autowired
    public void setHrDeptMergeManager(HrDeptMergeManager hrDeptMergeManager) {
		this.hrDeptMergeManager = hrDeptMergeManager;
	}

	@Autowired
    public void setSysTableContentDao(SysTableContentDao sysTableContentDao) {
		this.sysTableContentDao = sysTableContentDao;
	}

	@Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Autowired
    public void setSysTableStructureDao(SysTableStructureDao sysTableStructureDao) {
		this.sysTableStructureDao = sysTableStructureDao;
	}

	@Autowired
    public void setHrOperLogManager(HrOperLogManager hrOperLogManager) {
		this.hrOperLogManager = hrOperLogManager;
	}
	
	@Autowired
    public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
	@Autowired
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
    @Autowired
    public HrDepartmentSnapManagerImpl(HrDepartmentSnapDao hrDepartmentSnapDao) {
        super(hrDepartmentSnapDao);
        this.hrDepartmentSnapDao = hrDepartmentSnapDao;
    }
    public JQueryPager getHrDepartmentSnapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrDepartmentSnapDao.getHrDepartmentSnapCriteria(paginatedList,filters);
	}
    
    @Override
	public void confirmSave(HrDepartmentSnap hrDepartmentSnap,Person operPerson, Date operDate,boolean asyncData) {
    	hrDepartmentSnap = super.save(hrDepartmentSnap);
    	/*
    	 * 给该部门的上级部门的新增部门锁解锁
    	 * 1.检查该上级部门下是否有未审核的新增部门
    	 * 2.检查该上级部门下是否有即将合并到该部门下的部门
    	 * 3.检查该上级部门下是否有即将划转到该部门下的部门
    	 */
    	unlockDeptNewLock(hrDepartmentSnap.getParentDeptId());
    	addHrDeptLog(hrDepartmentSnap,operPerson,operDate,asyncData);
	}

	public HrDepartmentSnap saveHrDeptSnap(HrDepartmentSnap hrDepartmentSnap,String hrSubSets,Person operPerson,Date operDate,boolean asyncData){
    	hrDepartmentSnap.setCnCode(this.hrDepartmentSnapDao.getPyCodes(hrDepartmentSnap.getName()));
    	String deptSnapId = hrDepartmentSnap.getSnapId();
    	if(OtherUtil.measureNull(deptSnapId)){
    		hrDepartmentSnap = this.addHrDept(hrDepartmentSnap,operPerson, operDate,asyncData);
    	}else{
    		String hql = "from HrDepartmentSnap where snapId='"+deptSnapId+"'";
    		List<HrDepartmentSnap> oldDepts = this.getByHql(hql);
    		if(oldDepts==null || oldDepts.isEmpty()){
    			hrDepartmentSnap = this.addHrDept(hrDepartmentSnap, operPerson, operDate,asyncData);
    		}else{
    			hrDepartmentSnap = this.editHrDept(hrDepartmentSnap.clone(), operPerson, operDate,asyncData);
    		}
    	}
    	if(hrSubSets!=null){
    		sysTableStructureDao.saveHrSubData(hrDepartmentSnap.getDeptId(), hrDepartmentSnap.getSnapCode(), hrSubSets);
    	}
    	return hrDepartmentSnap;
    }
    
    public HrDepartmentSnap addHrDept(HrDepartmentSnap hrDepartmentSnap,Person operPerson,Date operDate,boolean asyncData) {
    	hrDepartmentSnap.setSnapId(hrDepartmentSnap.getDeptId()+"_"+hrDepartmentSnap.getSnapCode());
    	hrDepartmentSnap = super.save(hrDepartmentSnap);
    	if(hrDepartmentSnap.getState()<3){
    		/*
        	 * 给上级部门加新增部门锁 
        	 */
        	String pDeptId = hrDepartmentSnap.getParentDeptId();
        	if(OtherUtil.measureNotNull(pDeptId)){
        		this.lockHrDepartmentSnap(pDeptId, HrBusinessCode.DEPT_NEW);
        	}
    	}
    	if(hrDepartmentSnap.getState()==3){
    		// 保存实体、同步数据、写Log
    		addHrDeptLog(hrDepartmentSnap,operPerson,operDate,asyncData);
    	}
    	return hrDepartmentSnap;
    }
    
    private void addHrDeptLog(HrDepartmentSnap hrDepartmentSnap,Person operPerson,Date operDate,boolean asyncData){
    	HrLogEntityInfo<HrDepartmentSnap> entityInfo = new HrLogEntityInfo<HrDepartmentSnap>(HrDepartmentSnap.class);
		entityInfo.setMainCode(hrDepartmentSnap.getSnapId());
		entityInfo.setOperType("添加");
		entityInfo.setOrgCode(hrDepartmentSnap.getOrgCode());
		entityInfo.setColumnInfoList(null);
		hrOperLogManager.addHrOperLog(entityInfo, operPerson, operDate);
		if(asyncData){
			syncUpdateDepartment(hrDepartmentSnap,HrOperLog.OPER_EDIT);
		}
		
		// 处理上下级关系
		String parentDeptId = hrDepartmentSnap.getParentDeptId();
		if(OtherUtil.measureNotNull(parentDeptId)){
			HrDepartmentSnap parentDept = this.hrDepartmentSnapDao.get(parentDeptId+"_"+hrDepartmentSnap.getHisPDSnapCode()).clone();
			if(parentDept.getLeaf()){
				parentDept.setLeaf(false);
				this.editHrDept(parentDept,operPerson, operDate,asyncData);
			}
		}
    }
    
    public HrDepartmentSnap editHrDept(HrDepartmentSnap hrDepartmentSnap,Person operPerson,Date operDate,boolean asyncData){
    	List<HrLogColumnInfo> hrLogColumnInfoList = getChangedColumns(hrDepartmentSnap);
    	if(hrLogColumnInfoList!=null){//存在改变的字段
    		String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,operDate);
    		hrDepartmentSnap.setSnapCode(snapCode);
			hrDepartmentSnap.setSnapId(hrDepartmentSnap.getDeptId()+"_"+hrDepartmentSnap.getSnapCode());
			hrDepartmentSnap = super.save(hrDepartmentSnap);
			
			if(hrDepartmentSnap.getState()>=3){
				HrLogEntityInfo<HrDepartmentSnap> entityInfo = new HrLogEntityInfo<HrDepartmentSnap>(HrDepartmentSnap.class);
				entityInfo.setMainCode(hrDepartmentSnap.getSnapId());
				entityInfo.setOperType("修改");
				entityInfo.setOrgCode(hrDepartmentSnap.getOrgCode());
				entityInfo.setColumnInfoList(hrLogColumnInfoList);
				hrOperLogManager.addHrOperLog(entityInfo, operPerson, operDate);
				if(asyncData){
					syncUpdateDepartment(hrDepartmentSnap,HrOperLog.OPER_EDIT);
				}
			}
    	}
    	return hrDepartmentSnap;
    }
    
    /**
     * 分析改变的属性
     * @param hrDepartmentSnap
     * @return
     */
	private List<HrLogColumnInfo> getChangedColumns(HrDepartmentSnap hrDepartmentSnap) {
		/*
		 * 获取原来的记录
		 * 转换为可比较的map
		 * 比较并记录比较结果 
		 */
		List<HrLogColumnInfo> hrLogColumnInfoList = new ArrayList<HrLogColumnInfo>();
		String sql = "select * from hr_department_snap where snapId = '"+hrDepartmentSnap.getSnapId()+"'";
		List<Map<String, Object>> oldDeptList = this.hrDepartmentSnapDao.getBySqlToMap(sql);
		if(OtherUtil.measureNull(oldDeptList)||oldDeptList.isEmpty()){
			return null;
		}
		Map<String,Object> oldDeptMap = oldDeptList.get(0);
		Map<String,Object> newDeptMap = hrDepartmentSnap.getMapEntity();
		Iterator<Entry<String,Object>> ite = oldDeptMap.entrySet().iterator();
		Entry<String,Object> item = null;
		String key = null;
		Object oldValue = null,newValue = null;
		while(ite.hasNext()){
			item = ite.next();
			key = item.getKey();
			oldValue = item.getValue();
			newValue = newDeptMap.get(key);
			if(oldValue==null){
				if(oldValue instanceof Integer||oldValue instanceof Double) {
					oldValue = 0;
				}else{
					oldValue = "";
				}
			}else{
				if(oldValue instanceof Date){
					Date date = (Date) oldValue;
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					oldValue = formatter.format(date);
				}
			}
			if(newValue==null){
				if(newValue instanceof Integer||newValue instanceof Double) {
					newValue = 0;
				}else{
					newValue = "";
				}
			}else{
				if(newValue instanceof Date){
					Date date = (Date) newValue;
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					newValue = formatter.format(date);
				}
			}
			if(OtherUtil.measureNull(oldValue) && OtherUtil.measureNull(newValue)){
				continue;
			}
			if(!newValue.equals(oldValue)){
				hrLogColumnInfoList.add(new HrLogColumnInfo(key,oldValue,newValue));
			}
		}
		if(hrLogColumnInfoList.isEmpty()){
			return null;
		}
		return hrLogColumnInfoList;
	}

	@Override
	public void delete(String[] ids, Person person,Date operDate,boolean asyncData) {
		HrDepartmentSnap hrDepartmentSnap = null;
		for(String id:ids){
			hrDepartmentSnap = this.get(id);
			if(hrDepartmentSnap.getState()<=2){
				this.remove(id);
				/*
				 * 此时为真正的删除，一并删除部门附属子集 
				 * 并对父级部门的新增部门锁解锁
				 */
				unlockDeptNewLock(hrDepartmentSnap.getParentDeptId());
				sysTableContentDao.deleteAllSubSetDataByFK("v_hr_department_current", hrDepartmentSnap.getDeptId());
				continue;
			}
			hrDepartmentSnap.setDeleted(true);
			hrDepartmentSnap = this.save(hrDepartmentSnap);
			HrLogEntityInfo<HrDepartmentSnap> entityInfo = new HrLogEntityInfo<HrDepartmentSnap>(HrDepartmentSnap.class);
			entityInfo.setMainCode(hrDepartmentSnap.getSnapId());
			entityInfo.setOperType("删除");
			entityInfo.setOrgCode(hrDepartmentSnap.getOrgCode());
			entityInfo.setColumnInfoList(null);
			hrOperLogManager.addHrOperLog(entityInfo, person, operDate);
			if(asyncData){
				syncUpdateDepartment(hrDepartmentSnap,HrOperLog.OPER_DELETE);
			}
		}
	}
	
	private void unlockDeptNewLock(String pDeptId){
    	if(OtherUtil.measureNotNull(pDeptId)){
	    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
	    	filters.add(new PropertyFilter("EQS_parentDeptId",pDeptId));
	    	filters.add(new PropertyFilter("LTI_state","2"));
	    	List<HrDepartmentSnap> hrDeptSnaps = this.getByFilters(filters);
	    	filters = new ArrayList<PropertyFilter>();
	    	filters.add(new PropertyFilter("LTI_state","2"));
	    	filters.add(new PropertyFilter("EQS_parentId",pDeptId));
	    	List<HrDeptMerge> hrDeptMerges = hrDeptMergeManager.getByFilters(filters);
	    	if((hrDeptSnaps==null || hrDeptSnaps.isEmpty()) && (hrDeptMerges==null || hrDeptMerges.isEmpty())){
	    		this.unlockHrDepartmentSnap(pDeptId, HrBusinessCode.DEPT_NEW);
	    	}
    	}
	}

	@Override
	public List<String> getHisSnapIdList(String timestamp) {
		return this.hrDepartmentSnapDao.getHisSnapIdList(timestamp);
	}
	
	public String getHisSnapIdString(String timestamp){
		List<String> hisSnapIdList = this.getHisSnapIdList(timestamp);
		if(hisSnapIdList!=null && !hisSnapIdList.isEmpty()){
			String snapIds = "";
			for(String snapId:hisSnapIdList){
				snapIds += snapId+",";
			}
			snapIds = OtherUtil.subStrEnd(snapIds, ",");
			return snapIds;
		}else{
			return null;
		}
	}

	@Override
	public List<HrDepartmentSnap> getDeptListByHisTime(String timestamp) {
		String hisSnapIds = getHisSnapIdString(timestamp);
		if(OtherUtil.measureNull(hisSnapIds)){
			return null;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_snapId",hisSnapIds));
		filters.add(new PropertyFilter("GTI_state","2"));
		filters.add(new PropertyFilter("EQB_deleted","0"));
		List<HrDepartmentSnap> deptSnapList = this.getByFilters(filters);
		if(deptSnapList!=null && !deptSnapList.isEmpty()){
			return deptSnapList;
		}
		return null;
	}

	@Override
	public List<HrDepartmentSnap> getAllDescendants(String snapId,String hisTime) {
		int segm = snapId.lastIndexOf("_");
		String deptId = snapId.substring(0, segm);
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_parentDeptId",deptId));
		List<String> snapIdList = this.getHisSnapIdList(hisTime);
		String snapIds = OtherUtil.transferListToString(snapIdList);
		filters.add(new PropertyFilter("INS_snapId",snapIds));
		List<HrDepartmentSnap> hdss = this.getByFilters(filters);
		List<HrDepartmentSnap> hrDSList = new ArrayList<HrDepartmentSnap>();
		if(hdss!=null && !hdss.isEmpty()){
			hrDSList.addAll(hdss);
			for(HrDepartmentSnap hds:hdss){
				List<HrDepartmentSnap> hrds = getAllDescendants(hds.getSnapId(),hisTime);
				hrDSList.addAll(hrds);
			}
		}
		return hrDSList;
	}
	
	public synchronized void syncUpdateDepartment(HrDepartmentSnap deptSnap,String operType){
		String deptId=deptSnap.getDeptId();
		Department dept = null;
		if(HrOperLog.OPER_EDIT.equals(operType)){
			if(departmentDao.exists(deptId)){
				dept = departmentDao.get(deptId);
			}else{
				dept = new Department();
				dept.setDepartmentId(deptId);
			}
		}else if(HrOperLog.OPER_DELETE.equals(operType)){
			try {
				dept = departmentDao.get(deptId);
				dept.setDisabled(true);
				departmentDao.save(dept);
			} catch (Exception e) {
				return;// TODO
			}
			return;
		}
		dept.setName(deptSnap.getName());
		dept.setDeptCode(deptSnap.getDeptCode());
		dept.setShortnName(deptSnap.getShortName());
		dept.setInternalCode(deptSnap.getInternalCode());
		dept.setOutin(deptSnap.getAttrCode());
		dept.setDeptClass(deptSnap.getDeptType());
		dept.setSubClass(deptSnap.getSubClass());
		dept.setPhone(deptSnap.getPhone());
		dept.setContact(deptSnap.getContact());
		dept.setContactPhone(deptSnap.getContactPhone());
		dept.setManager(deptSnap.getManager());
		dept.setNote(deptSnap.getRemark());
		dept.setLeaf(deptSnap.getLeaf());
		dept.setClevel(deptSnap.getClevel());
		HrDepartmentCurrent tempDept = null;
		Department parentDept = null;
		if(OtherUtil.measureNotNull(deptSnap.getParentDeptId())){
			String parentDeptId=deptSnap.getParentDeptId();
			if(departmentDao.exists(parentDeptId)){
				parentDept=departmentDao.get(parentDeptId);
			}else{
				parentDept = new Department();
				tempDept=hrDepartmentCurrentManager.get(parentDeptId);					
				parentDept.setDepartmentId(parentDeptId);
				parentDept.setName(tempDept.getName());
				parentDept.setDeptCode(tempDept.getDeptCode());
				parentDept.setDeptClass(tempDept.getDeptClass());
				parentDept.setJjDept(parentDept);
				parentDept.setYsDept(parentDept);
				//departmentDao.save(parentDept);
			}
		}
		dept.setParentDept(parentDept);
		dept.setDisabled(deptSnap.getDisabled());
		dept.setInvalidDate(deptSnap.getInvalidDate());
		dept.setCnCode(deptSnap.getCnCode());
		Department jjDept = null;
		if(OtherUtil.measureNotNull(deptSnap.getJjDeptId())){
			String jjDeptId=deptSnap.getJjDeptId();
			if(departmentDao.exists(jjDeptId)){
				jjDept=departmentDao.get(jjDeptId);
			}else{
				if(hrDepartmentCurrentManager.exists(jjDeptId)&&!jjDeptId.equals(deptId)){
					jjDept = new Department();
					tempDept=hrDepartmentCurrentManager.get(jjDeptId);
					jjDept.setName(tempDept.getName());
					jjDept.setDeptCode(tempDept.getDeptCode());
					jjDept.setDeptClass(tempDept.getDeptClass());
					jjDept.setDepartmentId(jjDeptId);
					jjDept.setJjDept(jjDept);
					jjDept.setYsDept(jjDept);
					//departmentDao.save(jjDept);
				}else{
					jjDept=dept;
				}
			}
		}else{
			jjDept=dept;
		}
		dept.setJjLeaf(deptSnap.getJjLeaf());
		dept.setJjDept(jjDept);
		Department ysDept = null;
		if(OtherUtil.measureNotNull(deptSnap.getYsDeptId())){
			String ysDeptId=deptSnap.getYsDeptId();
			if(departmentDao.exists(ysDeptId)){
				ysDept=departmentDao.get(ysDeptId);
			}else{
				if(hrDepartmentCurrentManager.exists(ysDeptId)&&!ysDeptId.equals(deptId)){
					ysDept = new Department();
					tempDept=hrDepartmentCurrentManager.get(ysDeptId);
					ysDept.setDepartmentId(ysDeptId);
					ysDept.setName(tempDept.getName());
					ysDept.setDeptCode(tempDept.getDeptCode());
					ysDept.setDeptClass(tempDept.getDeptClass());
					ysDept.setJjDept(ysDept);
					ysDept.setYsDept(ysDept);
					//departmentDao.save(ysDept);
				}else{
					ysDept=dept;
				}
			}
		}else{
			ysDept=dept;
		}
		dept.setYsLeaf(deptSnap.getYsLeaf());
		dept.setYsDept(ysDept);
		dept.setJjDeptType(deptSnap.getJjDeptType());
		dept.setOrgCode(deptSnap.getOrgCode());
		//dept.setYsPurchasingDepartment(deptSnap.getPurchaseDept());
		dept.setSnapCode(deptSnap.getSnapCode());
		departmentDao.save(dept);
	}
	
	public synchronized void syncUpdateHrDepartment(String orgCode,String deptId,String snapCode,Person person,Date date){	
		Department dept=departmentDao.get(deptId);
		HrDepartmentSnap hrDeptSnap=null;
		HrDepartmentCurrent hrDepartmentCurrentTemp=null;
		HrDepartmentSnap hrDeptSnapTemp=null;
		String snapId=deptId+"_"+snapCode;
		HrOrg hrOrg = hrOrgManager.get(orgCode);
		if(hrDepartmentCurrentManager.exists(deptId)){
			hrDepartmentCurrentTemp=hrDepartmentCurrentManager.get(deptId);
			snapCode=hrDepartmentCurrentTemp.getSnapCode();
			snapId=deptId+"_"+snapCode;
			hrDeptSnap=hrDepartmentSnapDao.get(snapId).clone();			
		}else{
			hrDeptSnap=new HrDepartmentSnap();
			//hrDeptSnap.setSnapId(snapId);
			hrDeptSnap.setSnapCode(snapCode);
			hrDeptSnap.setDeptId(deptId);
		}
		hrDeptSnap.setOrgCode(hrOrg.getOrgCode());
		hrDeptSnap.setOrgSnapCode(hrOrg.getSnapCode());
		hrDeptSnap.setName(dept.getName());
		hrDeptSnap.setDeptCode(dept.getDeptCode());
		hrDeptSnap.setShortName(dept.getShortnName());
		hrDeptSnap.setInternalCode(dept.getInternalCode());
		hrDeptSnap.setAttrCode(dept.getOutin());
		hrDeptSnap.setDeptType(dept.getDeptClass());
		hrDeptSnap.setSubClass(dept.getSubClass());
		hrDeptSnap.setPhone(dept.getPhone());
		hrDeptSnap.setContact(dept.getContact());
		hrDeptSnap.setContactPhone(dept.getContactPhone());
		hrDeptSnap.setManager(dept.getManager());
		hrDeptSnap.setRemark(dept.getNote());
		hrDeptSnap.setLeaf(dept.getLeaf());
		hrDeptSnap.setClevel(dept.getClevel());
		hrDeptSnap.setState(3);
		Department parentDept=dept.getParentDept();
		if(OtherUtil.measureNull(parentDept)||OtherUtil.measureNull(parentDept.getDepartmentId())){
			hrDeptSnap.setParentDeptId(null);
			hrDeptSnap.setHisPDSnapCode(null);
		}else{
			String parentDeptId=parentDept.getDepartmentId();
			if(hrDepartmentCurrentManager.exists(parentDeptId)){
				hrDepartmentCurrentTemp=hrDepartmentCurrentManager.get(parentDeptId);
				hrDeptSnap.setParentDeptId(parentDeptId);
				hrDeptSnap.setHisPDSnapCode(hrDepartmentCurrentTemp.getSnapCode());
			}else if(parentDeptId.equals(deptId)){
				hrDeptSnap.setParentDeptId(parentDeptId);
				hrDeptSnap.setHisPDSnapCode(snapCode);
			}else{
				hrDeptSnapTemp=new HrDepartmentSnap();
				//hrDeptSnapTemp.setSnapId(parentDeptId+"_"+snapCode);
				hrDeptSnapTemp.setSnapCode(snapCode);
				hrDeptSnapTemp.setDeptId(parentDeptId);
				hrDeptSnapTemp.setName(parentDept.getName());
				hrDeptSnapTemp.setDeptCode(parentDept.getDeptCode());
				hrDeptSnapTemp.setDeptType(parentDept.getDeptClass());
				hrDeptSnapTemp.setJjDeptId(parentDeptId);
				hrDeptSnapTemp.setHisJjDSnapCode(snapCode);
				hrDeptSnapTemp.setYsDeptId(parentDeptId);
				hrDeptSnapTemp.setHisYsDSnapCode(snapCode);
				hrDeptSnapTemp.setState(3);
				hrDeptSnapTemp.setOrgCode(hrOrg.getOrgCode());
				hrDeptSnapTemp.setOrgSnapCode(hrOrg.getSnapCode());
				hrDeptSnapTemp.setLeaf(false);
				this.saveHrDeptSnap(hrDeptSnapTemp, null, person, date, false);
				//hrDepartmentSnapDao.save(hrDeptSnapTemp);
				hrDeptSnap.setParentDeptId(parentDeptId);
				hrDeptSnap.setHisPDSnapCode(snapCode);
			}
		}
		hrDeptSnap.setDisabled(dept.getDisabled());
		hrDeptSnap.setInvalidDate(dept.getInvalidDate());
		hrDeptSnap.setCnCode(dept.getCnCode());
		Department jjDept=dept.getJjDept();
		if(OtherUtil.measureNull(jjDept)||OtherUtil.measureNull(jjDept.getDepartmentId())){
			hrDeptSnap.setJjDeptId(deptId);
			hrDeptSnap.setHisJjDSnapCode(snapCode);
		}else{
			String jjDeptId=jjDept.getDepartmentId();
			if(hrDepartmentCurrentManager.exists(jjDeptId)){
				hrDepartmentCurrentTemp=hrDepartmentCurrentManager.get(jjDeptId);
				hrDeptSnap.setJjDeptId(jjDeptId);
				hrDeptSnap.setHisJjDSnapCode(hrDepartmentCurrentTemp.getSnapCode());
			}else if(jjDeptId.equals(deptId)){
				hrDeptSnap.setJjDeptId(jjDeptId);
				hrDeptSnap.setHisJjDSnapCode(snapCode);
			}else{
				hrDeptSnapTemp=new HrDepartmentSnap();
				//hrDeptSnapTemp.setSnapId(jjDeptId+"_"+snapCode);
				hrDeptSnapTemp.setSnapCode(snapCode);
				hrDeptSnapTemp.setDeptId(jjDeptId);
				hrDeptSnapTemp.setName(jjDept.getName());
				hrDeptSnapTemp.setDeptCode(jjDept.getDeptCode());
				hrDeptSnapTemp.setDeptType(jjDept.getDeptClass());
				hrDeptSnapTemp.setJjDeptId(jjDeptId);
				hrDeptSnapTemp.setHisJjDSnapCode(snapCode);
				hrDeptSnapTemp.setYsDeptId(jjDeptId);
				hrDeptSnapTemp.setHisYsDSnapCode(snapCode);
				hrDeptSnapTemp.setState(3);
				hrDeptSnapTemp.setOrgCode(hrOrg.getOrgCode());
				hrDeptSnapTemp.setOrgSnapCode(hrOrg.getSnapCode());
				hrDeptSnapTemp.setLeaf(jjDept.getLeaf());
				this.saveHrDeptSnap(hrDeptSnapTemp, null, person, date, false);
				//hrDepartmentSnapDao.save(hrDeptSnapTemp);
				hrDeptSnap.setJjDeptId(jjDeptId);
				hrDeptSnap.setHisJjDSnapCode(snapCode);
			}
		}
		hrDeptSnap.setJjLeaf(dept.getJjLeaf());
		Department ysDept=dept.getYsDept();
		if(OtherUtil.measureNull(ysDept)||OtherUtil.measureNull(ysDept.getDepartmentId())){
			hrDeptSnap.setYsDeptId(deptId);
			hrDeptSnap.setHisYsDSnapCode(snapCode);
		}else{
			String ysDeptId=ysDept.getDepartmentId();
			if(hrDepartmentCurrentManager.exists(ysDeptId)){
				hrDepartmentCurrentTemp=hrDepartmentCurrentManager.get(ysDeptId);
				hrDeptSnap.setYsDeptId(ysDeptId);
				hrDeptSnap.setHisYsDSnapCode(hrDepartmentCurrentTemp.getSnapCode());
			}else if(ysDeptId.equals(deptId)){
				hrDeptSnap.setYsDeptId(ysDeptId);
				hrDeptSnap.setHisYsDSnapCode(snapCode);
			}else{
				hrDeptSnapTemp=new HrDepartmentSnap();
				//hrDeptSnapTemp.setSnapId(ysDeptId+"_"+snapCode);
				hrDeptSnapTemp.setSnapCode(snapCode);
				hrDeptSnapTemp.setDeptId(ysDeptId);
				hrDeptSnapTemp.setName(ysDept.getName());
				hrDeptSnapTemp.setDeptCode(ysDept.getDeptCode());
				hrDeptSnapTemp.setDeptType(ysDept.getDeptClass());
				hrDeptSnapTemp.setJjDeptId(ysDeptId);
				hrDeptSnapTemp.setHisJjDSnapCode(snapCode);
				hrDeptSnapTemp.setYsDeptId(ysDeptId);
				hrDeptSnapTemp.setHisYsDSnapCode(snapCode);
				hrDeptSnapTemp.setState(3);
				hrDeptSnapTemp.setOrgCode(hrOrg.getOrgCode());
				hrDeptSnapTemp.setOrgSnapCode(hrOrg.getSnapCode());
				hrDeptSnapTemp.setLeaf(ysDept.getLeaf());
				this.saveHrDeptSnap(hrDeptSnapTemp, null, person, date, false);
				//hrDepartmentSnapDao.save(hrDeptSnapTemp);
				hrDeptSnap.setYsDeptId(ysDeptId);
				hrDeptSnap.setHisYsDSnapCode(snapCode);
			}
		}
		hrDeptSnap.setYsLeaf(dept.getYsLeaf());
		hrDeptSnap.setJjDeptType(dept.getJjDeptType());
		hrDeptSnap = this.saveHrDeptSnap(hrDeptSnap, null, person, date, false);
		//hrDepartmentSnapDao.save(hrDeptSnap);
		dept.setSnapCode(snapCode);
		departmentDao.save(dept);
		hrDepartmentSnapDao.flusSession();
	}
	public synchronized void syncHrDepartment(Department dept,Person person,Date date){
		String deptId = dept.getDepartmentId();
		if(!hrDepartmentCurrentManager.exists(deptId)){
			return;
		}
		HrDepartmentCurrent hdc = hrDepartmentCurrentManager.get(deptId);
		HrDepartmentSnap hds = hrDepartmentSnapDao.get(deptId+"_"+hdc.getSnapCode()).clone();
		hds.setAttrCode(dept.getOutin());
		hds.setYsLeaf(dept.getYsLeaf());
		hds.setJjLeaf(dept.getJjLeaf());
		hds.setJjDeptType(dept.getJjDeptType());
		String jjDeptId = dept.getJjDept().getDepartmentId();
		String ysDeptId = dept.getYsDept().getDepartmentId();
		if(hrDepartmentCurrentManager.exists(jjDeptId)){
			hdc = hrDepartmentCurrentManager.get(jjDeptId);
			hds.setJjDeptId(jjDeptId);
			hds.setHisJjDSnapCode(hdc.getSnapCode());
		}else{
			return;
		}
		if(hrDepartmentCurrentManager.exists(ysDeptId)){
			hdc = hrDepartmentCurrentManager.get(ysDeptId);
			hds.setYsDeptId(ysDeptId);
			hds.setHisYsDSnapCode(hdc.getSnapCode());
		}else{
			return;
		}
		this.saveHrDeptSnap(hds, null, person, date, false);
	}
	 @Override
	 public void lockHrDepartmentSnap(String deptId,String lockState){
		 HrDepartmentSnap hrDepartmentSnap=hrDepartmentSnapDao.getMaxHrDepartmentSnap(deptId);
		 if(hrDepartmentSnap!=null){
				String originalLockState=hrDepartmentSnap.getLockState();
				String nowLockState=null;
				if(OtherUtil.measureNull(originalLockState)){
					nowLockState=lockState;
				}else{
					String[] states=originalLockState.split(",");
					List<String> statelist = new ArrayList<String>();
					statelist = Arrays.asList(states);   
					if(statelist.contains(lockState)){
						nowLockState=originalLockState;
					}else{
						nowLockState=originalLockState+","+lockState;
					}
				}
				hrDepartmentSnap.setLockState(nowLockState);
				hrDepartmentSnapDao.save(hrDepartmentSnap);
			}
	 }
	 @Override
	 public void unlockHrDepartmentSnap(String deptId,String lockState){
		 HrDepartmentSnap hrDepartmentSnap=hrDepartmentSnapDao.getMaxHrDepartmentSnap(deptId);
		 if(hrDepartmentSnap!=null){
			 String originalLockState=hrDepartmentSnap.getLockState();
			 String nowLockState="";
			 if(OtherUtil.measureNotNull(originalLockState)){
				 String[] states=originalLockState.split(",");
				 String stateTem = null;
				 for(int stateIndx=0; stateIndx < states.length; stateIndx++){
					 stateTem=states[stateIndx];
					 if(stateTem.equals(lockState)){
						 continue;
					 }else{
						 nowLockState += stateTem +",";
					 }
				 }
				 if(!nowLockState.equals("")){
					 nowLockState = OtherUtil.subStrEnd(nowLockState, ",");
				 }
			 }
			 hrDepartmentSnap.setLockState(nowLockState);
			 hrDepartmentSnapDao.save(hrDepartmentSnap);
		 }
	 }
	 @Override
	 public HrDepartmentSnap getMaxHrDepartmentSnap(String deptId){
		 return hrDepartmentSnapDao.getMaxHrDepartmentSnap(deptId);
	 }
	 
	 @Override
	 public Map<String,HrDepartmentSnap> getDeptIdDeptMap(String hisTime){
		 return hrDepartmentSnapDao.getDeptIdDeptMap(hisTime);
	 }
	 @Override
	 public List<HrDepartmentSnap> getHrDepartmentSnapListBysql(String sql){
		 return hrDepartmentSnapDao.getHrDepartmentSnapListBysql(sql);
	 }
	 public static void main(String[] args) {
		Object a = new Double(0.5);
		Object b = new Double(0.50);
		System.out.println(a.equals(b));
		
	}
}