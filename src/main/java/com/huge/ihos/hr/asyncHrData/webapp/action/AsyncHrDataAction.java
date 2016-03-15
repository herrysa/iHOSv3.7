package com.huge.ihos.hr.asyncHrData.webapp.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.asyncHrData.service.AsyncHrDataManager;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.ihos.hr.hrOperLog.model.HrLogColumnInfo;
import com.huge.ihos.hr.hrOperLog.model.HrLogEntityInfo;
import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgSnapManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class AsyncHrDataAction extends BaseAction implements Preparable{
	private AsyncHrDataManager asyncHrDataManager;
	private DepartmentManager departmentManager;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private PersonManager personManager;
	private PersonTypeManager personTypeManager;
	private PostManager postManager;
	private HrOrgManager hrOrgManager;
	private OrgManager orgManager;
	private HrOrgSnapManager hrOrgSnapManager;
	private String deptIds;
	private HrPersonCurrentManager hrPersonCurrentManager;
	private HrDepartmentSnapManager hrDepartmentSnapManager;
	private HrPersonSnapManager hrPersonSnapManager;
	
	public void setAsyncHrDataManager(AsyncHrDataManager asyncHrDataManager) {
		this.asyncHrDataManager = asyncHrDataManager;
	}
	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	public String confirmAsyncHrData(){
		try {
			String asyncData = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
			if(!"0".equals(asyncData)){
				return ajaxForwardError("当前同步数据的方式非手工同步，请在系统参数里修改配置。");
			}
			String hisTime = this.getRequest().getParameter("hisTime");
			Timestamp hisTimestamp = Timestamp.valueOf(hisTime);
			String timestamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,hisTimestamp);
			asyncHrDataManager.asyncHrData(timestamp);
			return ajaxForwardSuccess("数据同步成功。");
		} catch (Exception e) {
			log.error("confirmAsyncHrData error:", e);
			return ajaxForwardError("数据同步失败。");
		}
	}
	public String confirmAsyncToHrData(){
		try {
//			TestTimer dataPrepare = new TestTimer("处理单位");
//			dataPrepare.begin();
			HttpServletRequest request = this.getRequest();
			String orgCode = request.getParameter("orgCode");
			StringTokenizer ids = new StringTokenizer(deptIds,",");
			String[] deptIdArr = deptIds.split(",");
			//String deptIdStr = "";
			List<String> idl = new ArrayList<String>();
			while (ids.hasMoreTokens()) {
				String deptId = ids.nextToken();
				idl.add(deptId);
			}
			//Department deptTemp = null;
			String deptName = null;
			String jjDeptId = null;
			String ysDeptId = null;
			String pDeptId = null;
			String errorMessage = "";
			String jjDeptMessage = "";
			String ysDeptMessage = "";
			String pDeptMessage = "";
			String snapCode = DateUtil.getSnapCode();
			/* 获取hrDepartmentCurrent中deptId和snapCode的Map */
			Map<String, HrDepartmentSnap> hrDeptIdAndDeptMap = hrDepartmentSnapManager.getDeptIdDeptMap(snapCode);
			//Map<String, String> hrDeptIdAndSnapCodeMap = hrDepartmentCurrentManager.getDeptIdAndSnapCodeMap();
			/*  HrDepartmentSnap的List集合（新增这些部门到HR） */
			List<HrDepartmentSnap> hdss = new ArrayList<HrDepartmentSnap>();
			/*  Department的List集合（修改这些部门的snapCode） */
			List<Department> depts = new ArrayList<Department>();
			HrLogEntityInfo<HrDepartmentSnap> entityDeptInfo = null;
			HrLogEntityInfo<HrPersonSnap> entityPersonInfo = null;
			/* HrDepartmentSnap日志的集合（部门新增日志） */
			List<HrLogEntityInfo<HrDepartmentSnap>> entityDeptInfos = new ArrayList<HrLogEntityInfo<HrDepartmentSnap>>();
			/* HrPersonSnap日志的集合（人员新增日志） */
			List<HrLogEntityInfo<HrPersonSnap>> entityPersonInfos = new ArrayList<HrLogEntityInfo<HrPersonSnap>>();
			HrDepartmentSnap hrDeptSnap = null;
			String orgSnapCode = snapCode;
			HrOrgSnap hrOrgSnap = null;
			Person operPerson = this.getSessionUser().getPerson();
			Date date=new Date();
			/* 处理单位  */
			if(hrOrgManager.exists(orgCode)){
				orgSnapCode = hrOrgManager.get(orgCode).getSnapCode();
			}else{
				hrOrgSnap = new HrOrgSnap();
				Org org = orgManager.get(orgCode);
				hrOrgSnap.setSnapCode(snapCode);
				hrOrgSnap.setOrgCode(orgCode);
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
				hrOrgSnap.setParentOrgCode(null);
				hrOrgSnap.setParentSnapCode(null);
				hrOrgSnap.setPhone(org.getPhone());
				hrOrgSnap.setType(org.getType());
				hrOrgSnap.setShortName(org.getShortName());
				hrOrgSnap.setPostCode(org.getPostCode());
				hrOrgSnap.setDeleted(false);
				hrOrgSnapManager.saveHrOrg(hrOrgSnap, date, operPerson, false);
				org.setSnapCode(snapCode);
				orgManager.save(org);
			}
//			dataPrepare.done();
//			dataPrepare = new TestTimer("部门数据准备");
//			dataPrepare.begin();
			/* 部门数据准备  */
			List<Department> deptlist = departmentManager.get(deptIdArr);
			for(Department deptTemp:deptlist){
				String checkId = deptTemp.getDepartmentId();
				if(hrDeptIdAndDeptMap.containsKey(checkId)){
					hrDeptSnap = hrDeptIdAndDeptMap.get(checkId).clone();
				}else{
					hrDeptSnap = new HrDepartmentSnap();
				}
				deptName =  deptTemp.getName();
				jjDeptId = deptTemp.getJjDept().getDepartmentId();
				ysDeptId = deptTemp.getYsDept().getDepartmentId();
				String snapId = checkId + "_" + snapCode;
				hrDeptSnap.setDeptId(checkId);
				hrDeptSnap.setSnapId(snapId);
				hrDeptSnap.setSnapCode(snapCode);
				hrDeptSnap.setOrgCode(orgCode);
				hrDeptSnap.setOrgSnapCode(orgSnapCode);
				hrDeptSnap.setName(deptName==null?null:deptName.trim());
				hrDeptSnap.setDeptCode(deptTemp.getDeptCode()==null?null:deptTemp.getDeptCode().trim());
				hrDeptSnap.setShortName(deptTemp.getShortnName());
				hrDeptSnap.setInternalCode(deptTemp.getInternalCode()==null?null:deptTemp.getInternalCode().trim());
				hrDeptSnap.setAttrCode(deptTemp.getOutin()==null?null:deptTemp.getOutin().trim());
				hrDeptSnap.setOutin(deptTemp.getOutin()==null?null:deptTemp.getOutin().trim());
				hrDeptSnap.setDeptType(deptTemp.getDeptClass()==null?null:deptTemp.getDeptClass().trim());
				hrDeptSnap.setSubClass(deptTemp.getSubClass()==null?null:deptTemp.getSubClass().trim());
				hrDeptSnap.setPhone(deptTemp.getPhone());
				hrDeptSnap.setContact(deptTemp.getContact());
				hrDeptSnap.setContactPhone(deptTemp.getContactPhone());
				hrDeptSnap.setManager(deptTemp.getManager());
				hrDeptSnap.setRemark(deptTemp.getNote());
				hrDeptSnap.setLeaf(deptTemp.getLeaf());
				hrDeptSnap.setClevel(deptTemp.getClevel());
				hrDeptSnap.setState(3);
				
				/*修改院区后新增的字段同步维护 */
				hrDeptSnap.setBranchCode(deptTemp.getBranchCode());
				hrDeptSnap.setDgroup(deptTemp.getDgroup());
				hrDeptSnap.setCbLeaf(deptTemp.getCbLeaf());
				hrDeptSnap.setZcLeaf(deptTemp.getZcLeaf());
				hrDeptSnap.setCrLeaf(deptTemp.getCrLeaf());
				hrDeptSnap.setXmLeaf(deptTemp.getXmLeaf());
				
				/* 上级部门的处理，如果hrDeptCurrent有的话就使用HR中的snapcode  */
				if(deptTemp.getParentDept() == null){
					pDeptId = null;
				}else{
					pDeptId = deptTemp.getParentDept().getDepartmentId();
					hrDeptSnap.setParentDeptId(pDeptId);
					if(!hrDeptIdAndDeptMap.containsKey(pDeptId)){
						if(!idl.contains(pDeptId)){
							pDeptMessage += deptName + ",";
						}
						hrDeptSnap.setHisPDSnapCode(snapCode);
					}else{
						hrDeptSnap.setHisPDSnapCode(hrDeptIdAndDeptMap.get(pDeptId).getSnapCode());
					}
				}
				hrDeptSnap.setDisabled(deptTemp.getDisabled());
				hrDeptSnap.setInvalidDate(deptTemp.getInvalidDate());
				hrDeptSnap.setCnCode(deptTemp.getCnCode());
				hrDeptSnap.setJjDeptId(jjDeptId);
				/* jj部门的处理，如果hrDeptCurrent有的话就使用HR中的snapcode  */
				if(!hrDeptIdAndDeptMap.containsKey(jjDeptId)){
					if(!idl.contains(jjDeptId)){
						jjDeptMessage += deptName + ",";
					}
					hrDeptSnap.setHisJjDSnapCode(snapCode);
				}else{
					hrDeptSnap.setHisJjDSnapCode(hrDeptIdAndDeptMap.get(jjDeptId).getSnapCode());
				}
				hrDeptSnap.setJjLeaf(deptTemp.getJjLeaf());
				hrDeptSnap.setYsDeptId(ysDeptId);
				/* ys部门的处理，如果hrDeptCurrent有的话就使用HR中的snapcode  */
				if(!hrDeptIdAndDeptMap.containsKey(ysDeptId)){
					if(!idl.contains(ysDeptId)){
						ysDeptMessage += deptName + ",";
					}
					hrDeptSnap.setHisYsDSnapCode(snapCode);
				}else{
					hrDeptSnap.setHisYsDSnapCode(hrDeptIdAndDeptMap.get(ysDeptId).getSnapCode());
				}
				hrDeptSnap.setYsLeaf(deptTemp.getYsLeaf());
				hrDeptSnap.setJjDeptType(deptTemp.getJjDeptType());
				/* 部门日志处理  */
				if(hrDeptIdAndDeptMap.containsKey(checkId)){
					HrDepartmentSnap hdsTemp = hrDeptIdAndDeptMap.get(deptTemp.getDepartmentId());
					List<HrLogColumnInfo> hrLogColumnInfoList = getColumnInfos(hdsTemp.getMapEntity(), hrDeptSnap.getMapEntity());					
					if(!hrLogColumnInfoList.isEmpty()){
						entityDeptInfo = new HrLogEntityInfo<HrDepartmentSnap>(HrDepartmentSnap.class);
						entityDeptInfo.setMainCode(snapId);
						entityDeptInfo.setOperType("修改");
						entityDeptInfo.setOrgCode(orgCode);
						entityDeptInfo.setColumnInfoList(hrLogColumnInfoList);
						entityDeptInfos.add(entityDeptInfo);
					}
				}else{
					entityDeptInfo = new HrLogEntityInfo<HrDepartmentSnap>(HrDepartmentSnap.class);
					entityDeptInfo.setMainCode(snapId);
					entityDeptInfo.setOperType("添加");
					entityDeptInfo.setOrgCode(orgCode);
					entityDeptInfo.setColumnInfoList(null);
					entityDeptInfos.add(entityDeptInfo);
				}
				deptTemp.setSnapCode(snapCode);
				hdss.add(hrDeptSnap);
//				deptIdStr += "'"+deptTemp.getDepartmentId()+"',";
				depts.add(deptTemp);
			}
//			if(!"".equals(deptIdStr)){
//				deptIdStr = "("+OtherUtil.subStrEnd(deptIdStr, ",")+")";
//			}
			/* 部门选择错误处理  */
			if(OtherUtil.measureNotNull(jjDeptMessage)){
				jjDeptMessage = OtherUtil.subStrEnd(jjDeptMessage, ",");
				errorMessage +="以下部门的奖金部门未在所选部门中：" + jjDeptMessage + "。";
			}
			if(OtherUtil.measureNotNull(ysDeptMessage)){
				ysDeptMessage = OtherUtil.subStrEnd(ysDeptMessage, ",");
				errorMessage +="以下部门的预算部门未在所选部门中：" + ysDeptMessage + "。";
			}
			if(OtherUtil.measureNotNull(pDeptMessage)){
				pDeptMessage = OtherUtil.subStrEnd(pDeptMessage, ",");
				errorMessage +="以下部门的父级部门未在所选部门中：" + pDeptMessage + "。";
			}
			if(OtherUtil.measureNotNull(errorMessage)){
				return ajaxForwardError(errorMessage);
			}
//			dataPrepare.done();
//			dataPrepare = new TestTimer("人员数据准备");
//			dataPrepare.begin();
			/* 查询需要插入到HR的人员集合  */
			List<Person> personList=new ArrayList<Person>();
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
			filters.add(new PropertyFilter("EQS_snapCode","0"));
			personList=personManager.getByFilters(filters);
			/* HrPersonSnap的List集合（新增这些人员到HR中）  */
			List<HrPersonSnap> hpss = new ArrayList<HrPersonSnap>();
			HrPersonSnap hrPersonSnap = null;
			/* 处理职工类别中是否有未分类  */
			String emptyName = "未分类";
			PersonType personTypeDefault = null;
			personTypeDefault = personTypeManager.getPersonTypeByName(emptyName);
			if(personTypeDefault==null){
				personTypeDefault=new PersonType();
				personTypeDefault.setCode("PT04");
				personTypeDefault.setLeaf(true);
				personTypeDefault.setName(emptyName);
				personTypeDefault.setRemark("由人员同步产生");
				personTypeDefault = personTypeManager.save(personTypeDefault);
			}
//			dataPrepare.done();
//			dataPrepare = new TestTimer("人员数据while:"+personList.size());
//			dataPrepare.begin();
			//List<String> personIdList = hrPersonCurrentManager.getPersonIdList();
			Map<String,HrPersonSnap> hrPersonIdAndPersonMap = hrPersonSnapManager.getPersonIdPersonMap(snapCode);
			List<PersonType> personTypes = personTypeManager.getAll();
			List<Post> posts = postManager.getAll();
			Map<String, PersonType> personTypeMap = new HashMap<String, PersonType>();
			Map<String, Post> postMap = new HashMap<String, Post>();
			for(int i=0;i<personTypes.size();i++){
				PersonType personTypeTemp = personTypes.get(i);
				personTypeMap.put(personTypeTemp.getName(), personTypeTemp);
			}
			for(int i=0;i<posts.size();i++){
				Post post = posts.get(i);
				postMap.put(post.getHrDept().getDepartmentId()+post.getName(), post);
			}
			/* 人员信息准备  */
//			String personIdStr = "";
			if(OtherUtil.measureNotNull(personList)&&!personList.isEmpty()){
				Iterator iter = personList.iterator();
				while(iter.hasNext()){
					Person person = (Person)iter.next();
					String personId = person.getPersonId();
					if(hrPersonIdAndPersonMap.containsKey(personId)){
						hrPersonSnap = hrPersonIdAndPersonMap.get(personId).clone();
					}else{
						hrPersonSnap = new HrPersonSnap();
					}
					String deptId = person.getDepartment().getDepartmentId();
					String snapId=personId+"_"+snapCode;
					hrPersonSnap.setPersonId(personId);
					hrPersonSnap.setSnapId(snapId);
					hrPersonSnap.setSnapCode(snapCode);
					hrPersonSnap.setHrDept(new HrDepartmentCurrent());
					hrPersonSnap.getHrDept().setDepartmentId(deptId);
					hrPersonSnap.setDeptSnapCode(snapCode);
					hrPersonSnap.setOrgCode(orgCode);
					hrPersonSnap.setOrgSnapCode(orgSnapCode);
					hrPersonSnap.setName(person.getName()==null?null:person.getName().trim());
					hrPersonSnap.setPersonCode(person.getPersonCode()==null?null:person.getPersonCode().trim());
					hrPersonSnap.setSex(person.getSex()==null?null:person.getSex().trim());
					emptyName = person.getStatus();
					//personType=personTypeManager.getPersonTypeByName(emptyName);
					PersonType personType=personTypeMap.get(emptyName);
					if(personType==null){
							emptyName = "未分类";
							personType = personTypeDefault;
					}
					hrPersonSnap.setEmpType(personType);
					String postName=person.getDuty();
					Post post = postMap.get(deptId+postName);
					hrPersonSnap.setPostType(person.getPostType()==null?null:person.getPostType().trim());
					hrPersonSnap.setBirthday(person.getBirthday());
					hrPersonSnap.setDuty(post);
					hrPersonSnap.setEducationalLevel(person.getEducationalLevel()==null?null:person.getEducationalLevel().trim());
					hrPersonSnap.setDisabled(person.getDisable());
					hrPersonSnap.setIdNumber(person.getIdNumber()==null?null:person.getIdNumber().trim());
					hrPersonSnap.setJobTitle(person.getJobTitle()==null?null:person.getJobTitle().trim());
					hrPersonSnap.setRemark(person.getNote());
					hrPersonSnap.setRatio(person.getRatio());
					hrPersonSnap.setSalaryNumber(person.getSalaryNumber()==null?null:person.getSalaryNumber().trim());
					hrPersonSnap.setWorkBegin(person.getWorkBegin());
					hrPersonSnap.setJjmark(person.getJjmark());
					/*工资*/
					hrPersonSnap.setGzType(person.getGzType());
					hrPersonSnap.setStopSalary(person.getStopSalary());
					hrPersonSnap.setStopReason(person.getStopReason());
					hrPersonSnap.setTaxType(person.getTaxType());
					hrPersonSnap.setBank1(person.getBank1());
					hrPersonSnap.setBank2(person.getBank2());
					hrPersonSnap.setSalaryNumber2(person.getSalaryNumber2());
					/*考勤*/
					hrPersonSnap.setKqType(person.getKqType());
					hrPersonSnap.setStopKq(person.getStopKq());
					hrPersonSnap.setStopKqReason(person.getStopKqReason());
					person.setSnapCode(snapCode);
					hpss.add(hrPersonSnap);
//					personIdStr += "'"+person.getPersonId()+"',";
					/* 人员日志处理  */
					if(hrPersonIdAndPersonMap.containsKey(person.getPersonId())){
						HrPersonSnap hpsTemp = hrPersonIdAndPersonMap.get(person.getPersonId());
						List<HrLogColumnInfo> hrLogColumnInfoList = getColumnInfos(hpsTemp.getMapEntity(), hrPersonSnap.getMapEntity());
						if(!hrLogColumnInfoList.isEmpty()){
							entityPersonInfo = new HrLogEntityInfo<HrPersonSnap>(HrPersonSnap.class);
							entityPersonInfo.setMainCode(snapId);
							entityPersonInfo.setOperType("修改");
							entityPersonInfo.setOrgCode(orgCode);
							entityPersonInfo.setColumnInfoList(hrLogColumnInfoList);
							entityPersonInfos.add(entityPersonInfo);
						}
					}else{
						entityPersonInfo = new HrLogEntityInfo<HrPersonSnap>(HrPersonSnap.class);
						entityPersonInfo.setMainCode(snapId);
						entityPersonInfo.setOperType("添加");
						entityPersonInfo.setOrgCode(orgCode);
						entityPersonInfo.setColumnInfoList(null);
						entityPersonInfos.add(entityPersonInfo);
					}
				 }
			}
//			if(!"".equals(personIdStr)){
//				personIdStr = "("+OtherUtil.subStrEnd(personIdStr, ",")+")";
//			}
//			dataPrepare.done();
			asyncHrDataManager.asyncToHr(hdss, deptlist, entityDeptInfos, hpss, personList, entityPersonInfos, operPerson, date);
			HrUtil.computeAllPersonCountTask(hrDepartmentCurrentManager);
			//Thread.sleep(5000);
//			asyncHrDataManager.asyncToHrData(idl, orgCode, orgName, isHrOrg,deptIds,person,date);
			return ajaxForwardSuccess("数据同步成功。");
		} catch (Exception e) {
			log.error("confirmAsyncToHrData error:", e);
			return ajaxForwardError("数据同步失败。");
		}
	}
	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	public HrDepartmentCurrentManager getHrDepartmentCurrentManager() {
		return hrDepartmentCurrentManager;
	}
	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
	public PersonManager getPersonManager() {
		return personManager;
	}
	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}
	public PersonTypeManager getPersonTypeManager() {
		return personTypeManager;
	}
	public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}
	public PostManager getPostManager() {
		return postManager;
	}
	public void setPostManager(PostManager postManager) {
		this.postManager = postManager;
	}
	public HrOrgManager getHrOrgManager() {
		return hrOrgManager;
	}
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
	public OrgManager getOrgManager() {
		return orgManager;
	}
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	public HrOrgSnapManager getHrOrgSnapManager() {
		return hrOrgSnapManager;
	}
	public void setHrOrgSnapManager(HrOrgSnapManager hrOrgSnapManager) {
		this.hrOrgSnapManager = hrOrgSnapManager;
	}
	public HrPersonCurrentManager getHrPersonCurrentManager() {
		return hrPersonCurrentManager;
	}
	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}
	public HrDepartmentSnapManager getHrDepartmentSnapManager() {
		return hrDepartmentSnapManager;
	}
	public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}
	public HrPersonSnapManager getHrPersonSnapManager() {
		return hrPersonSnapManager;
	}
	public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
	
	private List<HrLogColumnInfo> getColumnInfos(Map<String,Object> oldMap,Map<String,Object> newMap){
		List<HrLogColumnInfo> hrLogColumnInfoList = new ArrayList<HrLogColumnInfo>();
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
		return hrLogColumnInfoList;
	}
}
