package com.huge.ihos.hr.hrPerson.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.ihos.hr.hrOperLog.model.HrLogColumnInfo;
import com.huge.ihos.hr.hrOperLog.model.HrLogEntityInfo;
import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.hr.hrOperLog.service.HrOperLogManager;
import com.huge.ihos.hr.hrPerson.dao.HrPersonSnapDao;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.sysTables.dao.SysTableStructureDao;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.dao.PersonDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrPersonSnapManager")
public class HrPersonSnapManagerImpl extends GenericManagerImpl<HrPersonSnap, String> implements HrPersonSnapManager {
    private HrPersonSnapDao hrPersonSnapDao;
    private PersonDao personDao;
    private DepartmentDao departmentDao;
    private HrOperLogManager hrOperLogManager;
    private SysTableStructureDao sysTableStructureDao;
    private PostManager postManager;
    private HrPersonCurrentManager hrPersonCurrentManager;
    private PersonTypeManager personTypeManager;
    private HrDepartmentCurrentManager hrDepartmentCurrentManager;
    
    @Autowired
    public void setSysTableStructureDao(SysTableStructureDao sysTableStructureDao) {
		this.sysTableStructureDao = sysTableStructureDao;
	}
    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	@Autowired
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
	@Autowired
	public void setPostManager(PostManager postManager) {
		this.postManager = postManager;
	}
	@Autowired
    public void setHrOperLogManager(HrOperLogManager hrOperLogManager) {
		this.hrOperLogManager = hrOperLogManager;
	}

    @Autowired
    public HrPersonSnapManagerImpl(HrPersonSnapDao hrPersonSnapDao) {
        super(hrPersonSnapDao);
        this.hrPersonSnapDao = hrPersonSnapDao;
    }
    @Autowired
   	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
   		this.hrPersonCurrentManager = hrPersonCurrentManager;
   	}
    @Autowired
    public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}
    @Autowired
    public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
    public JQueryPager getHrPersonSnapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrPersonSnapDao.getHrPersonSnapCriteria(paginatedList,filters);
	}
    @Override
    public HrPersonSnap saveHrPersonWithImage(HrPersonSnap hrPersonSnap,String hrSubSets,Person operPerson,Date operDate,boolean asyncData,String imagePath,HttpServletRequest req) throws Exception{
    	String imagePathOld = hrPersonSnap.getImagePath();
    	if(OtherUtil.measureNotNull(imagePathOld)&&!imagePathOld.equals(imagePath)){
    		String imagePathNew=hrPersonSnap.getPersonId()+"_"+imagePathOld;
    		hrPersonSnap.setImagePath(imagePathNew);
    		String serverTempPath = "//home//temporary//";
    		serverTempPath = req.getRealPath(serverTempPath);
    		String filePath = serverTempPath + "\\" + imagePathOld;
    		File img = new File(filePath);
    		String serverNewPath = "//home//hrPerson//";
    		serverNewPath+=hrPersonSnap.getOrgCode()+"//";
    		serverNewPath=req.getRealPath(serverNewPath);
    		OptFile.mkParent(serverNewPath + "\\" + imagePathNew);
    		File targetFile = new File(serverNewPath + "\\" + imagePathNew);
    		try {
    		OptFile.copyFile(img, targetFile);
    		img.delete();
//    		if(OtherUtil.measureNotNull(imagePath)){
//    			filePath= serverNewPath + "\\" + imagePath;
//    			img = new File(filePath);
//    			img.delete();
//    		}
    		}catch(Exception e){
    			throw e;
    		}
    	}
    	return this.saveHrPerson(hrPersonSnap, hrSubSets, operPerson, operDate, asyncData);
    }
	@Override
	public HrPersonSnap saveHrPerson(HrPersonSnap hrPersonSnap,String hrSubSets,Person operPerson, Date operDate,boolean asyncData) {
		hrPersonSnap.setCnCode(this.pyCode(hrPersonSnap.getName()));//设置拼音码,该步骤不会影响后面的结果
		String personSnapId = hrPersonSnap.getSnapId();
		if(OtherUtil.measureNull(personSnapId)){// add person
			hrPersonSnap = this.addHrPerson(hrPersonSnap,operPerson,operDate,asyncData);
		}else{// edit person
			String hql = "from HrPersonSnap where snapId='"+personSnapId+"'";
			List<HrPersonSnap> oldPersons = this.getByHql(hql);
			if(oldPersons==null || oldPersons.isEmpty()){// add person
				hrPersonSnap = this.addHrPerson(hrPersonSnap,operPerson,operDate,asyncData);
			}else{
				hrPersonSnap = this.editHrPerson(hrPersonSnap,operPerson,operDate,asyncData);
			}
		}
		//岗位上锁
		if(OtherUtil.measureNotNull(hrPersonSnap.getDuty())&&OtherUtil.measureNotNull(hrPersonSnap.getDuty().getId())){
			String postId=hrPersonSnap.getDuty().getId();
			String lockState="RY";
			postManager.lockPost(postId, lockState);
		}
		if(hrSubSets!=null){
			sysTableStructureDao.saveHrSubData(hrPersonSnap.getPersonId(), hrPersonSnap.getSnapCode(), hrSubSets);
		}
		return hrPersonSnap;
	}

	private HrPersonSnap addHrPerson(HrPersonSnap hrPersonSnap,Person operPerson, Date operDate,boolean asyncData) {
		/**
		 * 1、设置人员snapId，保存人员到数据库
		 * 2、记录add人员的log
		 * 3、向t_person表插入数据
		 */
		hrPersonSnap.setSnapId(hrPersonSnap.getPersonId()+"_"+hrPersonSnap.getSnapCode());
		hrPersonSnap = hrPersonSnapDao.save(hrPersonSnap); // 1 end
		
		HrLogEntityInfo<HrPersonSnap> entityInfo = new HrLogEntityInfo<HrPersonSnap>(HrPersonSnap.class);
		entityInfo.setMainCode(hrPersonSnap.getSnapId());
		entityInfo.setOperType("添加");
		entityInfo.setOrgCode(hrPersonSnap.getOrgCode());
		entityInfo.setColumnInfoList(null);
		hrOperLogManager.addHrOperLog(entityInfo, operPerson, operDate); // 2 end
		if(asyncData){
			snycUpdatePerson(hrPersonSnap,HrOperLog.OPER_EDIT);// 3 end
		}
		return hrPersonSnap;
	}

	private HrPersonSnap editHrPerson(HrPersonSnap hrPersonSnap,Person operPerson, Date operDate,boolean asyncData) {
		/**
		 * 1、根据人员snapId获取数据库的原始值oldPersonSnap[Map<String,Object>]
		 * 2、对比oldPersonSnap和newPersonSnap，获取变动的属性集合 changedColumns
		 * 3、如果changedColumn是为空，则返回，否则执行4
		 * 4、设置人员snapCode和snapId,保存newPersonSnap
		 * 5、记录edit人员的log
		 * 6、向t_person更新数据
		 */
		List<HrLogColumnInfo> columnInfoList = getChangedColumns(hrPersonSnap);// 1、2 end
		if(columnInfoList!=null){// 3
			HrPersonSnap newPersonSnap = hrPersonSnap.clone();
			String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,operDate);
			newPersonSnap.setSnapCode(snapCode);
			newPersonSnap.setSnapId(newPersonSnap.getPersonId()+"_"+newPersonSnap.getSnapCode());
			hrPersonSnap = super.save(newPersonSnap); // 4 end
			
			HrLogEntityInfo<HrPersonSnap> entityInfo = new HrLogEntityInfo<HrPersonSnap>(HrPersonSnap.class);
			entityInfo.setMainCode(hrPersonSnap.getSnapId());
			entityInfo.setOperType("修改");
			entityInfo.setOrgCode(hrPersonSnap.getOrgCode());
			entityInfo.setColumnInfoList(columnInfoList);
			hrOperLogManager.addHrOperLog(entityInfo, operPerson, operDate);//5 end
			if(asyncData){
				snycUpdatePerson(hrPersonSnap,HrOperLog.OPER_EDIT);// 6 end
			}
		}
		return hrPersonSnap;
	}
	
	private List<HrLogColumnInfo> getChangedColumns(HrPersonSnap hrPersonSnap){
		List<HrLogColumnInfo> changedColumns = new ArrayList<HrLogColumnInfo>();
		String sql = "select * from hr_person_snap where snapId = '"+hrPersonSnap.getSnapId()+"'";
		List<Map<String, Object>>  hrPersonSnapList = this.hrPersonSnapDao.getBySqlToMap(sql);
		if(hrPersonSnapList==null||hrPersonSnapList.size()==0){
			return null;
		}
		Map<String,Object> oldMap = hrPersonSnapList.get(0);
		Map<String,Object> newMap = hrPersonSnap.getMapEntity();// 转换Entity为Map
		Iterator<Entry<String,Object>> ite = oldMap.entrySet().iterator();
		Entry<String,Object> item = null;
		String key = null;
		Object oldValue = null,newValue = null;
		while(ite.hasNext()){
			item = ite.next();
			key = item.getKey();
			oldValue = item.getValue();
			newValue = newMap.get(key);
			if(oldValue==null){
				oldValue="";
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
				changedColumns.add(new HrLogColumnInfo(key,oldValue,newValue));
			}
		}
		if(changedColumns.isEmpty()){
			return null;
		}
		return changedColumns;
	}

	@Override
	public HrPersonSnap save(HrPersonSnap hrPersonSnap, Person person,String operType, String changedAttrs) {
		hrPersonSnap.setCnCode(this.hrPersonSnapDao.getPyCodes(hrPersonSnap.getName()));
		Date snapTime = new Date();
		if("edit".equals(operType) && changedAttrs!=null){
			String timeStamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,snapTime);
			String snapId = hrPersonSnap.getOrgCode()+"_" + hrPersonSnap.getPersonCode()+"_"+timeStamp;
			hrPersonSnap.setSnapCode(timeStamp);
			hrPersonSnap.setSnapId(snapId);
		}
		hrPersonSnap = super.save(hrPersonSnap);
//		hrOperLogManager.addHrOperLog(operType, hrPersonSnap.getOrgCode(), hrPersonSnap.getPersonCode(), person, snapTime, changedAttrs, HrPersonSnap.class);
		return hrPersonSnap;
	}

	@Override
	public void delete(String[] ids, Person person,Date operDate,boolean asyncData) {
		HrPersonSnap hrPersonSnap = null;
		for(String id:ids){
			hrPersonSnap = this.get(id);
			hrPersonSnap.setDeleted(true);
			hrPersonSnap = super.save(hrPersonSnap);
			
			HrLogEntityInfo<HrPersonSnap> entityInfo = new HrLogEntityInfo<HrPersonSnap>(HrPersonSnap.class);
			entityInfo.setMainCode(hrPersonSnap.getSnapId());
			entityInfo.setOperType("删除");
			entityInfo.setOrgCode(hrPersonSnap.getOrgCode());
			entityInfo.setColumnInfoList(null);
			hrOperLogManager.addHrOperLog(entityInfo, person, operDate);
			if(asyncData){
				snycUpdatePerson(hrPersonSnap,HrOperLog.OPER_DELETE);
			}
		}
	}

	@Override
	public List<String> getHisSnapIds(String timestamp) {
		return this.hrPersonSnapDao.getHisSnapIds(timestamp);
	}
	
	public String getHisSnapIdString(String timestamp){
		List<String> snapIdList = this.getHisSnapIds(timestamp);
		if(snapIdList!=null && !snapIdList.isEmpty()){
			String snapIds = "";
			for(String snapId:snapIdList){
				snapIds += snapId +",";
			}
			snapIds = OtherUtil.subStrEnd(snapIds, ",");
			return snapIds;
		}else{
			return null;
		}
	}
	
	@Override
	public List<HrPersonSnap> getPersonListByHisTime(String timestamp) {
		//String snapIds = this.getHisSnapIdString(timestamp);
		//if(OtherUtil.measureNull(snapIds)){
		//	return null;
		//}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		String sql = "select max(snapId) from hr_person_snap where snapCode <='" + timestamp + "' and personId <> 'XT' group by personId";
		sql = "snapId in (" + sql +")";
		filters.add(new PropertyFilter("SQS_snapId", sql));
		filters.add(new PropertyFilter("EQB_deleted","0"));
		List<HrPersonSnap> personList = this.getByFilters(filters);
		if(personList!=null && !personList.isEmpty()){
			return personList;
		}
		return null;
	}

	/**
	 * 同步人员
	 * @param hrPersonSnap
	 * @param operType 操作类型 add edit del
	 */
	public synchronized void snycUpdatePerson(HrPersonSnap hrPersonSnap,String operType){
		String personId = hrPersonSnap.getPersonId();
		Person person = null;
		if(HrOperLog.OPER_EDIT.equals(operType)){
			if(personDao.exists(personId)){
				person=personDao.get(personId);
			}else{
				person = new Person();
				person.setPersonId(personId);
			}	
		}else if(HrOperLog.OPER_DELETE.equals(operType)){
			try {
				person=personDao.get(personId);
				person.setDisable(true);
				personDao.save(person);
			} catch (Exception e) {
				return;// TODO
			}
			return;
		}else{
			return;
		}
		person.setName(hrPersonSnap.getName());
		person.setPersonCode(hrPersonSnap.getPersonCode());
		person.setSex(hrPersonSnap.getSex());
		person.setStatus(hrPersonSnap.getEmpType().getName());
		if(OtherUtil.measureNotNull(hrPersonSnap.getDuty())&&OtherUtil.measureNotNull(hrPersonSnap.getDuty().getName())){
			person.setDuty(hrPersonSnap.getDuty().getName());
		}
		person.setBirthday(hrPersonSnap.getBirthday());
		person.setPostType(hrPersonSnap.getPostType());
		person.setEducationalLevel(hrPersonSnap.getEducationalLevel());
		person.setDisable(hrPersonSnap.getDisabled());
		person.setIdNumber(hrPersonSnap.getIdNumber());
		person.setJobTitle(hrPersonSnap.getJobTitle());
		person.setNote(hrPersonSnap.getRemark());
		person.setRatio(hrPersonSnap.getRatio());
		person.setSalaryNumber(hrPersonSnap.getSalaryNumber());
		person.setWorkBegin(hrPersonSnap.getWorkBegin());
		Department dept = departmentDao.get(hrPersonSnap.getHrDept().getDepartmentId());
		//Department dept = new Department();
		//dept.setDepartmentId(hrPersonSnap.getHrDept().getDepartmentId());
		person.setDepartment(dept);
		person.setJjmark(hrPersonSnap.getJjmark());
		person.setOrgCode(hrPersonSnap.getOrgCode());
		person.setSnapCode(hrPersonSnap.getSnapCode());
		/*工资*/
		person.setStopSalary(hrPersonSnap.getStopSalary());
		person.setStopReason(hrPersonSnap.getStopReason());
		person.setTaxType(hrPersonSnap.getTaxType());
		person.setBank1(hrPersonSnap.getBank1());
		person.setBank2(hrPersonSnap.getBank2());
		person.setSalaryNumber2(hrPersonSnap.getSalaryNumber2());
		person.setGzType(hrPersonSnap.getGzType());
		person.setGzType2(hrPersonSnap.getGzType2());
		personDao.save(person);
	}
	public synchronized void syncUpdateHrPerson(Person person,String snapCode,Person operPerson,Date date){
		String personId=person.getPersonId();
		String deptId=person.getDepartment().getDepartmentId();
		HrDepartmentCurrent hdc = hrDepartmentCurrentManager.get(deptId);		
		HrPersonSnap hrPersonSnap=null;
		String snapId=personId+"_"+snapCode;
		if(hrPersonCurrentManager.exists(personId)){
			HrPersonCurrent hrPersonCurrent=hrPersonCurrentManager.get(personId);
			snapCode=hrPersonCurrent.getSnapCode();
			snapId=personId+"_"+snapCode;
			hrPersonSnap=hrPersonSnapDao.get(snapId).clone();
		}else{
			hrPersonSnap=new HrPersonSnap();
			//hrPersonSnap.setSnapId(snapId);
			hrPersonSnap.setSnapCode(snapCode);
			hrPersonSnap.setPersonId(personId);
		}
		hrPersonSnap.setHrDept(new HrDepartmentCurrent());
		hrPersonSnap.getHrDept().setDepartmentId(deptId);
		hrPersonSnap.setDeptSnapCode(hdc.getSnapCode());
		hrPersonSnap.setOrgCode(hdc.getOrgCode());
		//hrPersonSnap.setHrOrg(hdc.getHrOrg());
		//hrPersonSnap.setOrgCode(hds.getOrgCode());
		hrPersonSnap.setOrgSnapCode(hdc.getOrgSnapCode());
		hrPersonSnap.setName(person.getName());
		hrPersonSnap.setPersonCode(person.getPersonCode());
		hrPersonSnap.setSex(person.getSex());
		String emptyName=person.getStatus();
		PersonType personType=personTypeManager.getPersonTypeByName(emptyName);
		if(personType==null){
			emptyName="未分类";
			personType=personTypeManager.getPersonTypeByName(emptyName);
			if(personType==null){
				personType=new PersonType();
				personType.setCode("PT04");
				personType.setLeaf(true);
				personType.setName(emptyName);
				personType.setRemark("由人员同步产生");
				personType = personTypeManager.save(personType);
			}
		}
		hrPersonSnap.setEmpType(personType);
		String postName=person.getDuty();
		Post post=postManager.getPostByDeptIdAndPostName(deptId, postName);
		hrPersonSnap.setPostType(person.getPostType());
		hrPersonSnap.setBirthday(person.getBirthday());
		hrPersonSnap.setDuty(post);
		hrPersonSnap.setEducationalLevel(person.getEducationalLevel());
		hrPersonSnap.setDisabled(person.getDisable());
		hrPersonSnap.setIdNumber(person.getIdNumber());
		hrPersonSnap.setJobTitle(person.getJobTitle());
		hrPersonSnap.setRemark(person.getNote());
		hrPersonSnap.setRatio(person.getRatio());
		hrPersonSnap.setSalaryNumber(person.getSalaryNumber());
		hrPersonSnap.setWorkBegin(person.getWorkBegin());
		hrPersonSnap.setJjmark(person.getJjmark());
		/*工资*/
		hrPersonSnap.setStopSalary(person.getStopSalary());
		hrPersonSnap.setStopReason(person.getStopReason());
		hrPersonSnap.setTaxType(person.getTaxType());
		hrPersonSnap.setBank1(person.getBank1());
		hrPersonSnap.setBank2(person.getBank2());
		hrPersonSnap.setSalaryNumber2(person.getSalaryNumber2());
		hrPersonSnap.setGzType(person.getGzType());
		hrPersonSnap.setGzType2(person.getGzType2());
		this.saveHrPerson(hrPersonSnap, null, operPerson, date, false);
		//hrPersonSnapDao.save(hrPersonSnap);
		person.setSnapCode(snapCode);
		personDao.save(person);
	}
	public synchronized void snycHrPerson(Person person,Person operPerson,Date date){
		String personId = person.getPersonId();
		if(!hrPersonCurrentManager.exists(personId)){
			return;
		}
		HrPersonCurrent hpc = hrPersonCurrentManager.get(personId);
		HrPersonSnap hps = hrPersonSnapDao.get(personId+"_"+hpc.getSnapCode()).clone();
		hps.setJjmark(person.getJjmark());
		/*工资*/
		hps.setGzType(person.getGzType());
		hps.setStopSalary(person.getStopSalary());
		hps.setStopReason(person.getStopReason());
		hps.setTaxType(person.getTaxType());
		hps.setBank1(person.getBank1());
		hps.setBank2(person.getBank2());
		hps.setSalaryNumber(person.getSalaryNumber());
		hps.setSalaryNumber2(person.getSalaryNumber2());
		/*考勤*/
		hps.setKqType(person.getKqType());
		hps.setStopKq(person.getStopKq());
		hps.setStopKqReason(person.getStopKqReason());
		this.saveHrPerson(hps, null, operPerson, date, false);
	}
	@Override
	public void batchUpdateHrPerson(String[] snapIds,Map<String, Object> columnMap, Person operPerson,Date operDate,boolean asyncData) {
		/**
		 * 1、组织更新人员的SQL
		 * 2、组织LogEntityInfo
		 * 3、组织同步t_person的SQL
		 * 4、执行SQL，写Log
		 */
		String snapIdS = "";
		for(String snapId:snapIds){
			snapIdS += "'"+snapId +"',";
		}
		snapIdS = OtherUtil.subStrEnd(snapIdS, ",");
		String sql = "select * from hr_person_snap where snapId in ("+snapIdS+")";
		List<Map<String,Object>> oldPersonMapList = this.hrPersonSnapDao.getBySqlToMap(sql);
		String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,operDate);
		if(oldPersonMapList!=null && !oldPersonMapList.isEmpty()){
			String updateHrPersonSql = "",updateTPersonSql = "";
			List<HrLogEntityInfo<HrPersonSnap>> entityInfoList = new ArrayList<HrLogEntityInfo<HrPersonSnap>>();
			for(Map<String,Object> oldPersonMap:oldPersonMapList){
				oldPersonMap.put("snapCode", snapCode);
				oldPersonMap.put("snapId", oldPersonMap.get("personId")+"_"+snapCode);
				updateHrPersonSql += organizeUpdateHrPersonSql(oldPersonMap,columnMap);
				updateTPersonSql += organizeUpdateTPersonSql((String)oldPersonMap.get("personId"),columnMap);
				entityInfoList.add(organizeOperLogInfo(oldPersonMap,columnMap));
			}
			if(OtherUtil.measureNotNull(updateHrPersonSql)){
				this.hrPersonSnapDao.excuteSql(updateHrPersonSql);
			}
			if(asyncData && OtherUtil.measureNotNull(updateTPersonSql)){
				this.personDao.excuteSql(updateTPersonSql);
			}
			this.hrOperLogManager.addHrOperLog(entityInfoList, operPerson, operDate);
		}
	} 
	/**
	 * 组织变动的log信息
	 * @param beanMap
	 * @param changeMap
	 * @return
	 */
	private HrLogEntityInfo<HrPersonSnap> organizeOperLogInfo(Map<String, Object> beanMap,Map<String, Object> changeMap) {
		Set<Entry<String,Object>> changeEntry = changeMap.entrySet();
		Iterator<Entry<String,Object>> changeIte = changeEntry.iterator();
		Entry<String,Object> cEntry = null;
		String name = null;
		Object oldValue = null,newValue=null;
		HrLogEntityInfo<HrPersonSnap> entityInfo = new HrLogEntityInfo<HrPersonSnap>(HrPersonSnap.class);
		entityInfo.setMainCode((String)beanMap.get("snapId"));
		entityInfo.setOperType("修改");
		entityInfo.setOrgCode((String)beanMap.get("orgCode"));
		List<HrLogColumnInfo> columnInfoList = new ArrayList<HrLogColumnInfo>();
		while(changeIte.hasNext()){
			cEntry = changeIte.next();
			name = cEntry.getKey();
			oldValue = beanMap.get(name);
			if(OtherUtil.measureNull(oldValue)){
				oldValue = "";
			}
			newValue = cEntry.getValue();
			if(OtherUtil.measureNull(newValue)){
				newValue = "";
			}
			columnInfoList.add(new HrLogColumnInfo(name,oldValue,newValue));
		}
		entityInfo.setColumnInfoList(columnInfoList);
		return entityInfo;
	}
	/**
	 * 组织插入HrPersonSnap的SQL
	 * @param beanMap
	 * @param changeMap
	 * @return
	 */
	private String organizeUpdateHrPersonSql(Map<String, Object> beanMap,Map<String, Object> changeMap) {
		Set<Entry<String,Object>> beanEntry = beanMap.entrySet();
		Iterator<Entry<String,Object>> beanIte = beanEntry.iterator();
		Entry<String,Object> bEntry = null;
		String name = null;
		Object value = null;
		String columnSql = "",valueSql = "";
		while(beanIte.hasNext()){
			bEntry = beanIte.next();
			name = bEntry.getKey();
			value = changeMap.get(name);
			if(value==null){
				value = bEntry.getValue();
			}
			columnSql += name +",";
			if(value==null){
				valueSql += "null,";
			}else{
				valueSql += "'"+value+"',";
			}
		}
		columnSql = OtherUtil.subStrEnd(columnSql, ",");
		valueSql = OtherUtil.subStrEnd(valueSql, ",");
		return "insert into hr_person_snap ("+columnSql+") values("+valueSql+");";
	}
	/**
	 * 组织更新t_person的SQL
	 * @param beanMap
	 * @param changeMap
	 * @return
	 */
	private String organizeUpdateTPersonSql(String personId,Map<String, Object> changeMap) {
		Set<Entry<String,Object>> changeEntry = changeMap.entrySet();
		Iterator<Entry<String,Object>> changeIte = changeEntry.iterator();
		Entry<String,Object> cEntry = null;
		String name = null;
		String value = null;
		String setSql = "";
		Map<String,Boolean> tPersonMap = new HashMap<String,Boolean>();
		String tPersonSql = "SELECT name FROM SysColumns WHERE id=Object_Id('t_person')";
		List<Object[]> tPersonColumns = this.personDao.getBySql(tPersonSql);
		for(int i=0;i<tPersonColumns.size();i++){
			Object obj = tPersonColumns.get(i);
			tPersonMap.put((String)obj, true);
		}
		while(changeIte.hasNext()){
			cEntry = changeIte.next();
			name = cEntry.getKey();
			if(tPersonMap.get(name)==null){
				continue;// TODO
			}
			value = (String)changeMap.get(name);
			if(value==null){
				value = "null";
			}else{
				value = "'"+value+"'";
			}
			setSql += name + "=" +value+",";
		}
		if(OtherUtil.measureNull(setSql)){
			return "";
		}
		setSql = OtherUtil.subStrEnd(setSql, ",");
		return "update t_person set "+setSql+" where personId = '"+personId+"';";
	}
	@Override
	public void enableOrDsiable(String[] ids, Person person,Date operDate,String oper,boolean asyncData){
		Boolean state=false;
		if(oper.equals("enable")){
			state=false;
		}else if(oper.equals("disable")){
			state=true;
		}
		HrPersonSnap hrPersonSnap = null;
		for(String id:ids){
			hrPersonSnap = hrPersonSnapDao.get(id).clone();
			hrPersonSnap.setDisabled(state);
			this.saveHrPerson(hrPersonSnap, null,person, operDate,asyncData);
		}
	}
	
	@Override
	public void lockHrPerson(String personId,String lockState){
			HrPersonSnap hrPersonSnap=hrPersonSnapDao.getMaxHrPersonSnap(personId);
			if(hrPersonSnap!=null){
				String originalLockState=hrPersonSnap.getLockState();
				String nowLockState=null;
				if(originalLockState==null||originalLockState.equals("")){
					nowLockState=lockState;
				}else{
					String[] states=originalLockState.split(",");
					List statelist = new ArrayList();
					statelist = Arrays.asList(states);   
					if(statelist.contains(lockState)){
						nowLockState=originalLockState;
					}else{
						nowLockState=originalLockState+","+lockState;
					}
				}
				hrPersonSnap.setLockState(nowLockState);
				hrPersonSnapDao.save(hrPersonSnap);
			}
	}
	 public void unlockHrPersonSnap(String personId,String lockState){
				HrPersonSnap hrPersonSnap=hrPersonSnapDao.getMaxHrPersonSnap(personId);
				if(hrPersonSnap!=null){
					String originalLockState=hrPersonSnap.getLockState();
					String nowLockState=null;
					if(originalLockState==null||originalLockState.equals("")){
					}else{
						String[] states=originalLockState.split(",");
						for(int stateIndx=0;stateIndx<states.length;stateIndx++){
							String stateTem=states[stateIndx];
							if(!stateTem.equals(lockState)){
								if(nowLockState==null){
									nowLockState=stateTem;
								}else{
									nowLockState+=","+stateTem;
								}
							}
						}
					}
					hrPersonSnap.setLockState(nowLockState);
					hrPersonSnapDao.save(hrPersonSnap);
				}
	 }
	 @Override
	 public String checkLockHrPersonSnap(String personId,String[] checkLockStates){
			HrPersonSnap hrPersonSnap=hrPersonSnapDao.getMaxHrPersonSnap(personId);
			String messtr=null;
			if(hrPersonSnap!=null){
				String lockState=hrPersonSnap.getLockState();
				if(lockState==null||lockState.equals("")){
				}else{
					String[] states=lockState.split(",");
					List checkStatelist = new ArrayList();
					checkStatelist = Arrays.asList(checkLockStates);
					for(int stateIndex=0;stateIndex<states.length;stateIndex++){
						String stateTemp=states[stateIndex];
						if(checkStatelist.contains(stateTemp)){
							if(messtr==null){
								messtr=stateTemp;
							}else{
								messtr+=","+stateTemp;
							}
						}
					}
				}
			}
			return messtr;
	 }
	 @Override
	 public Map<String, HrPersonSnap> getPersonIdPersonMap(String timestamp){
		 return hrPersonSnapDao.getPersonIdPersonMap(timestamp);
	 }
}