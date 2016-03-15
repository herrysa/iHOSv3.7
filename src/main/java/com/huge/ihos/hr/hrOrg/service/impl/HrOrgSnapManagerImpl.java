package com.huge.ihos.hr.hrOrg.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrOperLog.model.HrLogColumnInfo;
import com.huge.ihos.hr.hrOperLog.model.HrLogEntityInfo;
import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.hr.hrOperLog.service.HrOperLogManager;
import com.huge.ihos.hr.hrOrg.dao.HrOrgSnapDao;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgSnapManager;
import com.huge.ihos.system.systemManager.organization.dao.OrgDao;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrOrgSnapManager")
public class HrOrgSnapManagerImpl extends GenericManagerImpl<HrOrgSnap, String> implements HrOrgSnapManager {
    private HrOrgSnapDao hrOrgSnapDao;
    private HrOperLogManager hrOperLogManager;
    private OrgDao orgDao;
    private HrOrgManager hrOrgManager;
    
    @Autowired
    public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	@Autowired
    public void setHrOperLogManager(HrOperLogManager hrOperLogManager) {
		this.hrOperLogManager = hrOperLogManager;
	}

	@Autowired
    public HrOrgSnapManagerImpl(HrOrgSnapDao hrOrgSnapDao) {
        super(hrOrgSnapDao);
        this.hrOrgSnapDao = hrOrgSnapDao;
    }
	@Autowired
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
    
    public JQueryPager getHrOrgSnapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrOrgSnapDao.getHrOrgSnapCriteria(paginatedList,filters);
	}
    
	@Override
	public HrOrgSnap saveHrOrg(HrOrgSnap hrOrgSnap, Date operDate,Person operPerson,boolean syncData) {
		String snapId = hrOrgSnap.getSnapId();
		if(OtherUtil.measureNull(snapId)){
			hrOrgSnap = this.addHrOrg(hrOrgSnap,operDate,operPerson,syncData);
		}else{
			hrOrgSnap = this.editHrOrg(hrOrgSnap,operDate,operPerson,syncData);
		}
		return hrOrgSnap;
	}

	private HrOrgSnap editHrOrg(HrOrgSnap hrOrgSnap, Date operDate,Person operPerson,boolean syncData) {
		List<HrLogColumnInfo> hrLogColumnInfoList = getChangedColumns(hrOrgSnap);
		if(hrLogColumnInfoList!=null){
    		//String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,operDate);
			String snapCode = DateUtil.getSnapCode();
    		hrOrgSnap.setSnapCode(snapCode);
    		hrOrgSnap.setSnapId(hrOrgSnap.getOrgCode()+"_"+hrOrgSnap.getSnapCode());
    		hrOrgSnap = super.save(hrOrgSnap);
			
			HrLogEntityInfo<HrOrgSnap> entityInfo = new HrLogEntityInfo<HrOrgSnap>(HrOrgSnap.class);
			entityInfo.setMainCode(hrOrgSnap.getSnapId());
			entityInfo.setOperType("修改");
			entityInfo.setOrgCode(hrOrgSnap.getOrgCode());
			entityInfo.setColumnInfoList(hrLogColumnInfoList);
			hrOperLogManager.addHrOperLog(entityInfo, operPerson, operDate);
			if(syncData){
				syncUpdateOrg(hrOrgSnap,HrOperLog.OPER_EDIT);
			}
    	}
		return hrOrgSnap;
	}

	private List<HrLogColumnInfo> getChangedColumns(HrOrgSnap hrOrgSnap) {
		List<HrLogColumnInfo> hrLogColumnInfoList = new ArrayList<HrLogColumnInfo>();
		String sql = "select * from hr_org_snap where snapId = '"+hrOrgSnap.getSnapId()+"'";
		List<Map<String, Object>> oldOrgList = this.hrOrgSnapDao.getBySqlToMap(sql);
		if(OtherUtil.measureNull(oldOrgList)||oldOrgList.isEmpty()){
			return null;
		}
		Map<String,Object> oldDeptMap = oldOrgList.get(0);
		Map<String,Object> newDeptMap = hrOrgSnap.getMapEntity();
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
				oldValue = "";
			}else{
				if(oldValue instanceof Date){
					Date date = (Date) oldValue;
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					oldValue = formatter.format(date);
				}
			}
			if(newValue==null){
				newValue = "";
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

	private HrOrgSnap addHrOrg(HrOrgSnap hrOrgSnap, Date operDate,Person operPerson,boolean syncData) {
		hrOrgSnap.setSnapId(hrOrgSnap.getOrgCode()+"_"+hrOrgSnap.getSnapCode());
		hrOrgSnap = super.save(hrOrgSnap);
		HrLogEntityInfo<HrOrgSnap> entityInfo = new HrLogEntityInfo<HrOrgSnap>(HrOrgSnap.class);
		entityInfo.setMainCode(hrOrgSnap.getSnapId());
		entityInfo.setOperType("添加");
		entityInfo.setOrgCode(hrOrgSnap.getOrgCode());
		entityInfo.setColumnInfoList(null);
		hrOperLogManager.addHrOperLog(entityInfo, operPerson, operDate);
		if(syncData){
			syncUpdateOrg(hrOrgSnap,HrOperLog.OPER_EDIT);
		}
		return hrOrgSnap;
	}

	@Override
	public void deleteHrOrg(String orgCode,Date operDate,Person operPerson,boolean syncData) {
		HrOrgSnap hrOrgSnap = this.get(orgCode);
		hrOrgSnap.setDeleted(true);
		super.save(hrOrgSnap);
		HrLogEntityInfo<HrOrgSnap> entityInfo = new HrLogEntityInfo<HrOrgSnap>(HrOrgSnap.class);
		entityInfo.setMainCode(hrOrgSnap.getSnapId());
		entityInfo.setOperType("删除");
		entityInfo.setOrgCode(hrOrgSnap.getOrgCode());
		entityInfo.setColumnInfoList(null);
		hrOperLogManager.addHrOperLog(entityInfo, operPerson, operDate);
		if(syncData){
			syncUpdateOrg(hrOrgSnap,HrOperLog.OPER_DELETE);
		}
	}
	
	public synchronized void syncUpdateOrg(HrOrgSnap hrOrgSnap,String operType){
		String orgCode=hrOrgSnap.getOrgCode();
		Org org = null;
		if(HrOperLog.OPER_EDIT.equals(operType)){
			if(orgDao.exists(orgCode)){
				org = orgDao.get(orgCode);
			}else{
				org = new Org();
				org.setOrgCode(orgCode);
			}
		}else if(HrOperLog.OPER_DELETE.equals(operType)){
			try {
				org = orgDao.get(orgCode);
				org.setDisabled(true);
				orgDao.save(org);
				//orgDao.remove(hrOrgSnap.getOrgCode());
			} catch (Exception e) {
				return;
			}
			return;
		}
		org.setOrgname(hrOrgSnap.getOrgname());
		org.setAddress(hrOrgSnap.getAddress());
		org.setContact(hrOrgSnap.getContact());
		org.setContactPhone(hrOrgSnap.getContactPhone());
		org.setDisabled(hrOrgSnap.getDisabled());
		org.setEmail(hrOrgSnap.getEmail());
		org.setFax(hrOrgSnap.getFax());
		org.setHomePage(hrOrgSnap.getHomePage());
		org.setInternal(hrOrgSnap.getInternal());
		org.setInvalidDate(hrOrgSnap.getInvalidDate());
		org.setNote(hrOrgSnap.getNote());
		org.setOwnerOrg(hrOrgSnap.getOwnerOrg());
		Org parentOrg = null;
		if(hrOrgSnap.getParentOrg()!=null){
			String parentOrgCode=hrOrgSnap.getParentOrgCode();
			if(orgDao.exists(parentOrgCode)){
				parentOrg=orgDao.get(parentOrgCode);
			}else{
				parentOrg = new Org();
				parentOrg.setOrgCode(parentOrgCode);
				parentOrg.setOrgname(hrOrgSnap.getOrgname());
				orgDao.save(parentOrg);
			}
		}
		org.setParentOrgCode(parentOrg);
		org.setPhone(hrOrgSnap.getPhone());
		org.setType(hrOrgSnap.getType());
		org.setShortName(hrOrgSnap.getShortName());
		org.setPostCode(hrOrgSnap.getPostCode());
		org.setSnapCode(hrOrgSnap.getSnapCode());
		orgDao.save(org);
	}

	 public synchronized void syncUpdateHrOrg(String orgCode,String orgName,String snapCode,Person person,Date date){
		 Org org=null;
		 if(orgDao.exists(orgCode)){
			 org=orgDao.get(orgCode);
		 }else{
			 org=new Org();
			 org.setOrgCode(orgCode);
			 org.setOrgname(orgName);
		 }
		 HrOrgSnap hrOrgSnap=null;
		 String snapId=orgCode+"_"+snapCode;
		 if(hrOrgManager.exists(orgCode)){
			 snapCode=hrOrgManager.get(orgCode).getSnapCode();	
			 snapId=orgCode+"_"+snapCode;
			 hrOrgSnap=hrOrgSnapDao.get(snapId).clone();
		 }else{
			 hrOrgSnap=new HrOrgSnap();
			 hrOrgSnap.setSnapCode(snapCode);
			 hrOrgSnap.setOrgCode(orgCode);
			// hrOrgSnap.setSnapId(snapId);
		 }
		 hrOrgSnap.setOrgname(org.getOrgname());
		 hrOrgSnap.setAddress(org.getAddress());
		 hrOrgSnap.setContact(org.getContact());
		 hrOrgSnap.setContactPhone(org.getContactPhone());
		 hrOrgSnap.setDisabled(org.getDisabled());
		 hrOrgSnap.setEmail(org.getEmail());
		 hrOrgSnap.setFax(org.getFax());
		 hrOrgSnap.setHomePage(org.getHomePage());
		 hrOrgSnap.setInternal(org.getInternal());
		 hrOrgSnap.setNote(org.getNote());
		 hrOrgSnap.setOwnerOrg(org.getOwnerOrg());
		 Org parentOrg=org.getParentOrgCode();
		 HrOrg parentHrOrg=null;
		 if(parentOrg==null){
			 hrOrgSnap.setParentOrgCode(null);
			 hrOrgSnap.setParentSnapCode(null);
		 }else{
			 String parentOrgCode=parentOrg.getOrgCode();
			 if(hrOrgManager.exists(parentOrgCode)){
				 parentHrOrg=hrOrgManager.get(parentOrgCode);
				 hrOrgSnap.setParentOrgCode(parentHrOrg.getOrgCode());
				 hrOrgSnap.setParentSnapCode(parentHrOrg.getSnapCode());
			 }else{
				 HrOrgSnap parentHrOrgSnap=new HrOrgSnap();
				 //parentHrOrgSnap.setSnapId(parentOrgCode+"_"+snapCode);
				 parentHrOrgSnap.setSnapCode(snapCode);
				 parentHrOrgSnap.setOrgCode(parentOrgCode);
				 parentHrOrgSnap.setOrgname(parentOrg.getOrgname());
				 parentHrOrgSnap.setDeleted(false);
				 parentHrOrgSnap.setDisabled(parentOrg.getDisabled());
				 this.saveHrOrg(parentHrOrgSnap, date, person, false);
				// hrOrgSnapDao.save(parentHrOrgSnap);
				 hrOrgSnap.setParentOrgCode(parentOrgCode);
				 hrOrgSnap.setParentSnapCode(snapCode);
			 }
		 }
		 hrOrgSnap.setPhone(org.getPhone());
		 hrOrgSnap.setType(org.getType());
		 hrOrgSnap.setShortName(org.getShortName());
		 hrOrgSnap.setPostCode(org.getPostCode());
		 hrOrgSnap.setDeleted(false);
		 hrOrgSnap = this.saveHrOrg(hrOrgSnap, date, person, false);
		// hrOrgSnapDao.save(hrOrgSnap);
		 org.setSnapCode(snapCode);
		 orgDao.save(org);
		 hrOrgSnapDao.flusSession();
	 }
	
	@Override
	public String getHisSnapIdString(String snapCode) {
		List<String> snapIdList = this.getHisSnapIdList(snapCode);
		if(snapIdList!=null && !snapIdList.isEmpty()){
			String snapIds = "";
			for(String snapId:snapIdList){
				snapIds += snapId +",";
			}
			snapIds = OtherUtil.subStrEnd(snapIds, ",");
			return snapIds;
		}
		return null;
	}

	@Override
	public List<String> getHisSnapIdList(String snapCode) {
		return this.hrOrgSnapDao.getHisSnapIdList(snapCode);
	}

	@Override
	public List<HrOrgSnap> getOrgListByHisTime(String snapCode) {
		String hisSnapIds = this.getHisSnapIdString(snapCode);
		if(OtherUtil.measureNull(hisSnapIds)){
			return null;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_snapId",hisSnapIds));
		filters.add(new PropertyFilter("NES_orgCode","XT"));
		filters.add(new PropertyFilter("EQB_deleted","0"));
		List<HrOrgSnap> hrOrgSnaps = this.getByFilters(filters);
		if(hrOrgSnaps!=null && !hrOrgSnaps.isEmpty()){
			return hrOrgSnaps;
		}
		return null;
	}

	@Override
	public List<HrOrgSnap> getAllHisAvailable(String snapCode) {
		String hisSnapIds = this.getHisSnapIdString(snapCode);
		if(OtherUtil.measureNull(hisSnapIds)){
			return null;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_snapId",hisSnapIds));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("NES_orgCode","XT"));
		filters.add(new PropertyFilter("EQB_deleted","0"));
		List<HrOrgSnap> hrOrgSnaps = this.getByFilters(filters);
		if(hrOrgSnaps!=null && !hrOrgSnaps.isEmpty()){
			return hrOrgSnaps;
		}
		return null;
	}

	@Override
	public List<HrOrgSnap> getAllDescendants(String orgCode, String snapCode) {
		String snapIds = this.getHisSnapIdString(snapCode);
		if(snapIds==null){
			return null;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_parentOrgCode",orgCode));
		filters.add(new PropertyFilter("INS_snapId",snapIds));
		filters.add(new PropertyFilter("EQB_deleted","0"));
		List<HrOrgSnap> hos = this.getByFilters(filters);
		List<HrOrgSnap> resultList = new ArrayList<HrOrgSnap>();
		if(hos!=null && !hos.isEmpty()){
			resultList.addAll(hos);
			for(HrOrgSnap orgSnap:hos){
				List<HrOrgSnap> childOrgs = this.getAllDescendants(orgSnap.getOrgCode(), snapCode);
				resultList.addAll(childOrgs);
			}
		}
		return resultList;
	}
}