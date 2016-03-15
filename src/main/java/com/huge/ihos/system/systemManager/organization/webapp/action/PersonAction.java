package com.huge.ihos.system.systemManager.organization.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Filter;
import org.hibernate.Session;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.FieldInfoManager;
import com.huge.ihos.system.exinterface.ProxyGetGZResource;
import com.huge.ihos.system.exinterface.ProxyGetKQResource;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.DeptTreeNode;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.util.OtherUtil;
import com.huge.util.ReturnUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;

public class PersonAction extends JqGridBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6891251347580010748L;

	private PersonManager personManager;

	private DepartmentManager departmentManager;

	private OrgManager orgManager;

	private QueryManager queryManager;

	private BranchManager branchManager;

	private PeriodManager periodManager;

	private List persons;
	private List<String> personAll;

	private Person person;

	private String personId;

	private List deptList;

	private List dutyList;

	private List educationalLevelList;

	private List jobTitleList;

	private List postTypeList;

	private List sexList;

	private List statusList;

	private String taskName;

	private String proArgsStr;

	private List nodes;
	private String params = "";
	private String autoPrefixId;
	
	private DeptTreeNode deptTreeNode;
	

	public DeptTreeNode getDeptTreeNode() {
		return deptTreeNode;
	}

	public void setDeptTreeNode(DeptTreeNode deptTreeNode) {
		this.deptTreeNode = deptTreeNode;
	}

	public String getAutoPrefixId() {
		return autoPrefixId;
	}

	public void setAutoPrefixId(String autoPrefixId) {
		this.autoPrefixId = autoPrefixId;
	}

	public OrgManager getOrgManager() {
		return orgManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public List getNodes() {
		return nodes;
	}

	public void setNodes(List nodes) {
		this.nodes = nodes;
	}

	/**
	 * Grab the entity from the database before populating with request parameters
	 */
	private String gzTypeJsonStr;
	private String kqTypeJsonStr;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		/* if (getRequest().getMethod().equalsIgnoreCase("post")) {
		     // prevent failures on new
		     String personId = getRequest().getParameter("person.personId");
		     if (personId != null && !personId.equals("")) {
		         person = personManager.get(new Long(personId));
		     }
		 }
		 */
		this.dutyList = this.getDictionaryItemManager().getAllItemsByCode("postDuty");
		this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode("education");
		this.jobTitleList = this.getDictionaryItemManager().getAllItemsByCode("jobTitle");
		this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode("postType");
		this.sexList = this.getDictionaryItemManager().getAllItemsByCode("sex");
		this.statusList = this.getDictionaryItemManager().getAllItemsByCode("empType");
		this.currentPeriod = this.getLoginPeriod();
		deptList = departmentManager.getAll();
		//TODO 外部接口，获取工资类别、考勤类别
		ProxyGetGZResource proxyGetGZResource = new ProxyGetGZResource();
		List<ReportType> gzTypes = proxyGetGZResource.allGzTypes(true);
		//List<GzType> gzTypes = gzTypeManager.allGzTypes(true);
		ProxyGetKQResource proxyGetKQResource = new ProxyGetKQResource();
		List<ReportType> kqTypes = proxyGetKQResource.allKqTypes(true);
		//List<KqType> kqTypes = kqTypeManager.allKqTypes(true);
		String jsonStr = "[";
		if (OtherUtil.measureNotNull(gzTypes) && !gzTypes.isEmpty()) {
			for (ReportType gzTypeTemp : gzTypes) {
				jsonStr += "{\"gzTypeId\":\"" + gzTypeTemp.getTypeId() + "\",\"gzTypeName\":\"" + gzTypeTemp.getTypeName() + "\"},";
			}
			jsonStr = OtherUtil.subStrEnd(jsonStr, ",");
		}
		jsonStr += "]";
		gzTypeJsonStr = jsonStr;
		jsonStr = "[";
		if (OtherUtil.measureNotNull(kqTypes) && !kqTypes.isEmpty()) {
			for (ReportType kqTypeTemp : kqTypes) {
				jsonStr += "{\"kqTypeId\":\"" + kqTypeTemp.getTypeId() + "\",\"kqTypeName\":\"" + kqTypeTemp.getTypeName() + "\"},";
			}
			jsonStr = OtherUtil.subStrEnd(jsonStr, ",");
		}
		jsonStr += "]";
		kqTypeJsonStr = jsonStr;
		this.clearSessionMessages();
	}

	/*   public String list() {
	       persons = personManager.search(query, Person.class);
	       return SUCCESS;
	   }*/
	private List<Branch> branches = new ArrayList<Branch>();

	public List<Branch> getBranches() {
		return branches;
	}

	public String personList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			//menuButtons.get(0).setEnable(false);
			setMenuButtonsToJson(menuButtons);
			this.branches = this.branchManager.getAllAvailable();
		} catch (Exception e) {
			log.error("enter personList error:", e);
		}
		return SUCCESS;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String delete() {
		personManager.remove(person.getPersonId());
		saveMessage(getText("person.deleted"));

		return "edit_success";
	}

	private int editType = 0; // 0:new,1:edit

	public void setEditType(int editType) {
		this.editType = editType;
	}

	public int getEditType() {
		return editType;
	}

	public String edit() {
		HttpServletRequest req = this.getRequest();
		this.autoPrefixId = this.getGlobalParamByKey("autoPrefixId");
		//TODO 外部接口，获取工资类别、考勤类别
		ProxyGetGZResource proxyGetGZResource = new ProxyGetGZResource();
		List<ReportType> gzTypes = proxyGetGZResource.allGzTypes(true);
		//List<GzType> gzTypes = gzTypeManager.allGzTypes(true);
		ProxyGetKQResource proxyGetKQResource = new ProxyGetKQResource();
		List<ReportType> kqTypes = proxyGetKQResource.allKqTypes(true);
		//List<GzType> gztypes = gzTypeManager.allGzTypes(true);
		req.setAttribute("gztypes", gzTypes);
		//List<KqType> kqtypes = kqTypeManager.allKqTypes(true);
		req.setAttribute("kqtypes", kqTypes);
		String orgCode = req.getParameter("orgCode");
		String deptId = req.getParameter("deptId");
		if (personId != null) {
			person = personManager.get(personId);
			this.editType = 1;
			this.setEntityIsNew(false);
		} else {
			person = new Person();
			if (OtherUtil.measureNotNull(orgCode)) {
				person.setOrgCode(orgCode);
			}
			if (OtherUtil.measureNotNull(deptId)) {
				Department department = departmentManager.get(deptId);
				person.setDepartment(department);
				person.setBranchCode(department.getBranchCode());
				person.setBranch(department.getBranch());
				person.setOrgCode(department.getOrgCode());
			}
			this.editType = 0;
			this.setEntityIsNew(true);
		}

		return SUCCESS;
	}

	public String save() throws Exception {
		if (cancel != null) {
			return "cancel";
		}

		if (delete != null) {
			return delete();
		}

		boolean isNew = (person.getPersonId() == null);
		String deptId = person.getDepartment().getDepartmentId();
		Department department = departmentManager.get(deptId);
		if (!department.getLeaf()) {
			return ajaxForward(false, "人员科室为非末级科室，不能添加人员!", false);
		}
		person.setOrgCode(department.getOrgCode());
		if (OtherUtil.measureNull(person.getBranchCode())) {
			person.setBranchCode(department.getBranchCode());
		}
		//personManager.save( person );
		Person personTemp = personManager.savePerson(person, this.getYearStarted(), this.getSessionUser().getPerson(), new Date());
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		deptTreeNode = new DeptTreeNode();
		deptTreeNode.setId(personTemp.getPersonId());
		deptTreeNode.setCode(personTemp.getPersonCode());
		deptTreeNode.setSubSysTem("PERSON");
		deptTreeNode.setIsParent(false);
		deptTreeNode.setpId(personTemp.getDepartment().getDepartmentId());
		deptTreeNode.setIcon(iconPath + "person.png");
		deptTreeNode.setActionUrl(personTemp.getDisable() ? "1" : "0");
		deptTreeNode.setName(personTemp.getName());
		deptTreeNode.setNameWithoutPerson(personTemp.getName());
		deptTreeNode.setDisplayOrder(4);
		String key = (this.isEntityIsNew()) ? "person.added" : "person.updated";
		saveMessage(getText(key));

		return ajaxForward(getText(key));
	}

	public String personGridList() {
		try {
			Session session = this.personManager.getHibernateTemplate().getSessionFactory().openSession();
			Filter filter = session.enableFilter("exceptPersonFilter");
			filter.setParameter("exceptPerson", "P00001");

			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			HttpServletRequest request = this.getRequest();
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String personId = request.getParameter("personId");
			String showDisabledDept = request.getParameter("showDisabledDept");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			if (OtherUtil.measureNotNull(orgCode)) {
				filters.add(new PropertyFilter("EQS_department.orgCode", orgCode));
			}
			if (OtherUtil.measureNotNull(deptId)) {
				List<Department> depts = departmentManager.getAllDescendants(deptId);
				if (depts != null && !depts.isEmpty()) {
					for (Department deptTemp : depts) {
						deptId += "," + deptTemp.getDepartmentId();
					}
				}
				filters.add(new PropertyFilter("INS_department.departmentId", deptId));
			}
			if (OtherUtil.measureNotNull(personId)) {
				filters.add(new PropertyFilter("EQS_personId", personId));
			}
			if (OtherUtil.measureNull(showDisabledDept)) {
				filters.add(new PropertyFilter("EQB_department.disabled", "0"));
			}
			if (OtherUtil.measureNull(showDisabledPerson)) {
				filters.add(new PropertyFilter("EQB_disable", "0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = personManager.getAppManagerCriteriaWithSearch(pagedRequests, Person.class, filters);
			this.persons = (List<Person>) pagedRequests.getList();
			List<Person> personAllTempList = personManager.getByFilters(filters);
			this.personAll = new ArrayList<String>();
			if (OtherUtil.measureNotNull(personAllTempList) && !personAllTempList.isEmpty()) {
				for (Person personTemp : personAllTempList) {
					personAll.add(personTemp.getPersonId());
				}
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("personGridList Error", e);
		}
		return SUCCESS;
	}

	public String gzPersonGridList() {
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			HttpServletRequest request = this.getRequest();
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String personId = request.getParameter("personId");
			String showDisabledDept = request.getParameter("showDisabledDept");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			String gzTypeId = request.getParameter("filter_EQS_gzType");
			List<PropertyFilter> countFilters = new ArrayList<PropertyFilter>();
			if (OtherUtil.measureNotNull(orgCode)) {
				filters.add(new PropertyFilter("EQS_department.orgCode", orgCode));
				countFilters.add(new PropertyFilter("EQS_department.orgCode", orgCode));
			}
			if (OtherUtil.measureNotNull(deptId)) {
				List<Department> depts = departmentManager.getAllDescendants(deptId);
				if (depts != null && !depts.isEmpty()) {
					for (Department deptTemp : depts) {
						deptId += "," + deptTemp.getDepartmentId();
					}
				}
				filters.add(new PropertyFilter("INS_department.departmentId", deptId));
				countFilters.add(new PropertyFilter("INS_department.departmentId", deptId));
			}
			if (OtherUtil.measureNotNull(personId)) {
				filters.add(new PropertyFilter("EQS_personId", personId));
			}
			if (OtherUtil.measureNull(showDisabledDept)) {
				filters.add(new PropertyFilter("EQB_department.disabled", "0"));
				countFilters.add(new PropertyFilter("EQB_department.disabled", "0"));
			}
			if (OtherUtil.measureNull(showDisabledPerson)) {
				filters.add(new PropertyFilter("EQB_disable", "0"));
				countFilters.add(new PropertyFilter("EQB_disable", "0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = personManager.getAppManagerCriteriaWithSearch(pagedRequests, Person.class, filters);
			this.persons = (List<Person>) pagedRequests.getList();
			List<Person> personAllTempList = personManager.getByFilters(filters);
			this.personAll = new ArrayList<String>();
			if (OtherUtil.measureNotNull(personAllTempList) && !personAllTempList.isEmpty()) {
				for (Person personTemp : personAllTempList) {
					personAll.add(personTemp.getPersonId());
				}
			}
			//List<Person> personCountList = personManager.getByFilters(countFilters);
			int curGzSum = 0;//当前工资类别总人数
			int curGzSalarySum = 0;//当前工资类别发放总人数 
			int curGzStopSalarySum = 0;//当前工资类别停发人数
			int deptSum = 0;//部门总人数
			int deptSalarySum = 0;//发放总人数
			int deptStopSalarySum = 0;//停发总人数

			if (OtherUtil.measureNotNull(personAllTempList) && !personAllTempList.isEmpty()) {
				for (Person personTemp : personAllTempList) {
					deptSum++;
					//            		 String gzTypeIdTemp = personTemp.getGzType();
					Boolean stopSalary = personTemp.getStopSalary();
					if (OtherUtil.measureNull(stopSalary) || !stopSalary) {
						deptSalarySum++;
						//            			 if(gzTypeId.equals(gzTypeIdTemp)){
						//            				 curGzSalarySum++;
						//            				 curGzSum++;
						//            			 }
					} else {
						deptStopSalarySum++;
						//        				 if(gzTypeId.equals(gzTypeIdTemp)){
						//        					 curGzStopSalarySum++;
						//        					 curGzSum++;
						//            			 }
					}
				}
			}
			Map<String, String> userData = new HashMap<String, String>();
			userData.put("curGzSum", curGzSum + "");
			userData.put("curGzSalarySum", curGzSalarySum + "");
			userData.put("curGzStopSalarySum", curGzStopSalarySum + "");
			userData.put("deptSum", deptSum + "");
			userData.put("deptSalarySum", deptSalarySum + "");
			userData.put("deptStopSalarySum", deptStopSalarySum + "");
			this.setUserdata(userData);
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("personGridList Error", e);
		}
		return SUCCESS;
	}

	public String kqPersonGridList() {
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			HttpServletRequest request = this.getRequest();
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String personId = request.getParameter("personId");
			String showDisabledDept = request.getParameter("showDisabledDept");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			String kqTypeId = request.getParameter("filter_EQS_kqType");
			List<PropertyFilter> countFilters = new ArrayList<PropertyFilter>();
			if (OtherUtil.measureNotNull(orgCode)) {
				filters.add(new PropertyFilter("EQS_department.orgCode", orgCode));
				countFilters.add(new PropertyFilter("EQS_department.orgCode", orgCode));
			}
			if (OtherUtil.measureNotNull(deptId)) {
				List<Department> depts = departmentManager.getAllDescendants(deptId);
				if (depts != null && !depts.isEmpty()) {
					for (Department deptTemp : depts) {
						deptId += "," + deptTemp.getDepartmentId();
					}
				}
				filters.add(new PropertyFilter("INS_department.departmentId", deptId));
				countFilters.add(new PropertyFilter("INS_department.departmentId", deptId));
			}
			if (OtherUtil.measureNotNull(personId)) {
				filters.add(new PropertyFilter("EQS_personId", personId));
			}
			if (OtherUtil.measureNull(showDisabledDept)) {
				filters.add(new PropertyFilter("EQB_department.disabled", "0"));
				countFilters.add(new PropertyFilter("EQB_department.disabled", "0"));
			}
			if (OtherUtil.measureNull(showDisabledPerson)) {
				filters.add(new PropertyFilter("EQB_disable", "0"));
				countFilters.add(new PropertyFilter("EQB_disable", "0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = personManager.getAppManagerCriteriaWithSearch(pagedRequests, Person.class, filters);
			this.persons = (List<Person>) pagedRequests.getList();
			List<Person> personAllTempList = personManager.getByFilters(filters);
			this.personAll = new ArrayList<String>();
			if (OtherUtil.measureNotNull(personAllTempList) && !personAllTempList.isEmpty()) {
				for (Person personTemp : personAllTempList) {
					personAll.add(personTemp.getPersonId());
				}
			}
			//List<Person> personCountList = personManager.getByFilters(countFilters);
			int curKqSum = 0;//当前考勤类别总人数
			int curKqSalarySum = 0;//当前考勤类别发放总人数 
			int curKqStopSalarySum = 0;//当前考勤类别停发人数
			int deptSum = 0;//部门总人数
			int deptSalarySum = 0;//发放总人数
			int deptStopSalarySum = 0;//停发总人数

			if (OtherUtil.measureNotNull(personAllTempList) && !personAllTempList.isEmpty()) {
				for (Person personTemp : personAllTempList) {
					deptSum++;
					//           		 String kqTypeIdTemp = personTemp.getKqType();
					Boolean stopSalary = personTemp.getStopKq();
					if (OtherUtil.measureNull(stopSalary) || !stopSalary) {
						deptSalarySum++;
						//           			 if(kqTypeId.equals(kqTypeIdTemp)){
						//           				 curKqSalarySum++;
						//           				 curKqSum++;
						//           			 }
					} else {
						deptStopSalarySum++;
						//       				 if(kqTypeId.equals(kqTypeIdTemp)){
						//       					 curKqStopSalarySum++;
						//       					 curKqSum++;
						//           			 }
					}
				}
			}
			Map<String, String> userData = new HashMap<String, String>();
			userData.put("curKqSum", curKqSum + "");
			userData.put("curKqSalarySum", curKqSalarySum + "");
			userData.put("curKqStopSalarySum", curKqStopSalarySum + "");
			userData.put("deptSum", deptSum + "");
			userData.put("deptSalarySum", deptSalarySum + "");
			userData.put("deptStopSalarySum", deptStopSalarySum + "");
			this.setUserdata(userData);
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("personGridList Error", e);
		}
		return SUCCESS;
	}

	public String personGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					personManager.remove(removeId);
				}
				gridOperationMessage = this.getText("person.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}

			if (oper.equals("add") || oper.equals("edit")) {
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return SUCCESS;
				}
				personManager.save(person);
				String key = (oper.equals("add")) ? "person.added" : "person.updated";
				gridOperationMessage = this.getText(key);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPeriodGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, gridOperationMessage, false);
		}
	}

	public String backUpPerson() {
		String m = null;
		try {
			Object[] proArgs = proArgsStr.split(",");
			ReturnUtil returnUtil = this.queryManager.publicPrecess(taskName, proArgs);
			if (returnUtil.getStatusCode() == 0) {
				return ajaxForward(true, returnUtil.getMessage(), false);
			} else if (returnUtil.getStatusCode() <= -1) {
				return ajaxForward(false, returnUtil.getMessage(), false);
			} else {
				this.setMessage(returnUtil.getMessage());
				this.setStatusCode(returnUtil.getStatusCode());
				return SUCCESS;
			}
		} catch (Exception e) {
			return ajaxForward(false, e.getMessage(), false);
		}

	}

	public String personAndDeptTree() {
		// TODO: make person and dept tree for treeselect
		try {
			Map<String, Boolean> notPersonMap = new HashMap<String, Boolean>();
			if (!("").equals(params)) {
				String[] paramsArr = params.split(",");
				for (String param : paramsArr) {
					notPersonMap.put(param.trim(), true);
				}
			}

			List<Department> departments = departmentManager.getAllDept();
			List<Person> persons = personManager.getAllPerson();
			nodes = new ArrayList();
			for (Department department : departments) {
				ZTreeSimpleNode zTreeSimpleNode = new ZTreeSimpleNode();
				zTreeSimpleNode.setId(department.getDepartmentId());
				zTreeSimpleNode.setName(department.getName());
				zTreeSimpleNode.setpId(department.getParentDept() == null ? null : department.getParentDept().getDepartmentId());
				zTreeSimpleNode.setIsParent(true);
				nodes.add(zTreeSimpleNode);
			}
			for (Person person : persons) {
				if (person.getPersonId().equals("P900708")) {
					System.out.println((notPersonMap.get(person.getPersonId()) == null));
					System.out.println(notPersonMap.get("P900708"));
					System.out.println((notPersonMap.get("P900708") == null));
				}
				if ((notPersonMap.get(person.getPersonId()) == null) ? false : true) {
					continue;
				}
				ZTreeSimpleNode zTreeSimpleNode = new ZTreeSimpleNode();
				zTreeSimpleNode.setId(person.getPersonId());
				zTreeSimpleNode.setName(person.getName());
				zTreeSimpleNode.setpId(person.getDepartment() == null ? null : person.getDepartment().getDepartmentId());
				nodes.add(zTreeSimpleNode);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @TODO you should add some validation rules those are difficult on client side.
	 * @return
	 */
	private String isValid() {
		if (person == null) {
			return "Invalid person Data";
		}

		return SUCCESS;

	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public QueryManager getQueryManager() {
		return queryManager;
	}

	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public List getPersons() {
		return persons;
	}

	public List getDeptList() {
		return deptList;
	}

	public List getDutyList() {
		return dutyList;
	}

	public List getEducationalLevelList() {
		return educationalLevelList;
	}

	public List getJobTitleList() {
		return jobTitleList;
	}

	public List getPostTypeList() {
		return postTypeList;
	}

	public List getSexList() {
		return sexList;
	}

	public List getStatusList() {
		return statusList;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProArgsStr() {
		return proArgsStr;
	}

	public void setProArgsStr(String proArgsStr) {
		this.proArgsStr = proArgsStr;
	}

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public List<String> getPersonAll() {
		return personAll;
	}

	public void setPersonAll(List<String> personAll) {
		this.personAll = personAll;
	}

	public List<DeptTreeNode> getPersonTreeNodes() {
		return personTreeNodes;
	}

	public void setPersonTreeNodes(List<DeptTreeNode> personTreeNodes) {
		this.personTreeNodes = personTreeNodes;
	}

	private List<DeptTreeNode> personTreeNodes;

	public String makeDepartmentPersonTree() {
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		try {
			personTreeNodes = new ArrayList<DeptTreeNode>();
			DeptTreeNode rootNode = new DeptTreeNode(), deptNode, orgNode, personNode;
			rootNode.setId("-1");
			rootNode.setName("组织机构");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setActionUrl("0");// 此处的url只用来标识是否停用
			rootNode.setIcon(iconPath + "1_close.png");
			rootNode.setDisplayOrder(1);
			personTreeNodes.add(rootNode);
			List<Org> orgs = orgManager.getAllAvailable();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			List<Department> depts = null;
			String deptInOrg = "";
			if (OtherUtil.measureNotNull(orgs) && !orgs.isEmpty()) {
				for (Org orgTemp : orgs) {
					int personCount = 0;//包含停用部门；包含停用人员
					int personCountD = 0;//不包含停用部门；包含停用人员
					int personCountP = 0;//包含停用部门；不包含停用人员
					int personCountDP = 0;//不包含停用部门；不包含停用人员
					if (orgTemp.getOrgCode().equals("XT")) {
						continue;
					}
					filters.clear();
					filters.add(new PropertyFilter("NES_departmentId", "XT"));
					filters.add(new PropertyFilter("EQS_orgCode", orgTemp.getOrgCode()));
					filters.add(new PropertyFilter("OAS_deptCode", ""));
					depts = departmentManager.getByFilters(filters);
					if (depts != null && !depts.isEmpty()) {
						for (Department deptTemp : depts) {
							if (deptTemp.getLeaf()) {
								personCount += deptTemp.getPersonCount();
								personCountP += deptTemp.getPersonCountWithOutDisabled();
								if (!deptTemp.getDisabled()) {
									personCountD += deptTemp.getPersonCount();
									personCountDP += deptTemp.getPersonCountWithOutDisabled();
								}
							}
						}
						orgTemp.setPersonCount(personCount);
						orgTemp.setPersonCountD(personCountD);
						orgTemp.setPersonCountP(personCountP);
						orgTemp.setPersonCountDP(personCountDP);
						for (Department deptTemp : depts) {
							if (deptTemp.getLeaf() == true && (deptTemp.getPersonCount() > 0)) {
								deptTemp.setPersonCountP(deptTemp.getPersonCountWithOutDisabled());
								if (deptTemp.getDisabled()) {
									deptTemp.setPersonCountD(0);
									deptTemp.setPersonCountDP(0);
								} else {
									deptTemp.setPersonCountD(deptTemp.getPersonCount());
									deptTemp.setPersonCountDP(deptTemp.getPersonCountWithOutDisabled());
								}
								setDeptPersonCount(deptTemp, deptTemp.getPersonCount(), deptTemp.getPersonCountD(), deptTemp.getPersonCountP(), deptTemp.getPersonCountDP());
							}
						}
						for (Department deptTemp : depts) {
							deptInOrg += deptTemp.getDepartmentId() + ",";
							deptNode = new DeptTreeNode();
							deptNode.setId(deptTemp.getDepartmentId());
							deptNode.setCode(deptTemp.getDeptCode());
							deptNode.setName(deptTemp.getName());
							deptNode.setNameWithoutPerson(deptTemp.getName());
							deptNode.setpId(deptTemp.getParentDept() == null ? orgTemp.getOrgCode() : deptTemp.getParentDept().getDepartmentId());
							deptNode.setIsParent(!deptTemp.getLeaf());
							deptNode.setSubSysTem("DEPT");
							deptNode.setState(deptTemp.getLeaf() == true ? "LEAF" : "PARENT");
							deptNode.setActionUrl(deptTemp.getDisabled() ? "1" : "0");
							deptNode.setIcon(iconPath + "dept.gif");
							deptNode.setDeptCode(deptTemp.getDeptCode());
							deptNode.setOrgCode(deptTemp.getOrgCode());
							deptNode.setPersonCount("" + deptTemp.getPersonCount());
							deptNode.setPersonCountD("" + deptTemp.getPersonCountD());
							deptNode.setPersonCountP("" + deptTemp.getPersonCountP());
							deptNode.setPersonCountDP("" + deptTemp.getPersonCountDP());
							deptNode.setDisplayOrder(3);
							personTreeNodes.add(deptNode);
						}
					}
				}
				for (Org orgTemp : orgs) {
					if (orgTemp.getPersonCount() > 0) {
						setPersonCountInOrg(orgTemp, orgTemp.getPersonCount(), orgTemp.getPersonCountD(), orgTemp.getPersonCountP(), orgTemp.getPersonCountDP());
					}
				}
				for (Org orgTemp : orgs) {
					orgNode = new DeptTreeNode();
					orgNode.setId(orgTemp.getOrgCode());
					orgNode.setCode(orgTemp.getOrgCode());
					orgNode.setName(orgTemp.getOrgname());
					orgNode.setNameWithoutPerson(orgTemp.getOrgname());
					orgNode.setpId(orgTemp.getParentOrgCode() == null ? "-1" : orgTemp.getParentOrgCode().getOrgCode());
					orgNode.setIsParent(orgTemp.getParentOrgCode() == null);
					orgNode.setSubSysTem("ORG");
					orgNode.setActionUrl(orgTemp.getDisabled() ? "1" : "0");
					orgNode.setIcon(iconPath + "1_close.png");
					orgNode.setOrgCode(orgTemp.getOrgCode());
					orgNode.setPersonCount("" + orgTemp.getPersonCount());
					orgNode.setPersonCountD("" + orgTemp.getPersonCountD());
					orgNode.setPersonCountP("" + orgTemp.getPersonCountP());
					orgNode.setPersonCountDP("" + orgTemp.getPersonCountDP());
					orgNode.setDisplayOrder(2);
					personTreeNodes.add(orgNode);
				}
			}
			String deptIdS = deptInOrg;
			deptInOrg += "XT";
			filters.clear();
			filters.add(new PropertyFilter("NIS_departmentId", deptInOrg));
			filters.add(new PropertyFilter("OAS_deptCode", ""));
			depts = departmentManager.getByFilters(filters);
			if (depts != null && !depts.isEmpty()) {
				for (Department deptTemp : depts) {
					if (deptTemp.getLeaf() == true && (deptTemp.getPersonCount() > 0)) {
						deptTemp.setPersonCountP(deptTemp.getPersonCountWithOutDisabled());
						if (deptTemp.getDisabled()) {
							deptTemp.setPersonCountD(0);
							deptTemp.setPersonCountDP(0);
						} else {
							deptTemp.setPersonCountD(deptTemp.getPersonCount());
							deptTemp.setPersonCountDP(deptTemp.getPersonCountWithOutDisabled());
						}
						setDeptPersonCount(deptTemp, deptTemp.getPersonCount(), deptTemp.getPersonCountD(), deptTemp.getPersonCountP(), deptTemp.getPersonCountDP());
					}
				}
				for (Department deptTemp : depts) {
					deptNode = new DeptTreeNode();
					deptIdS += deptTemp.getDepartmentId() + ",";
					deptNode.setId(deptTemp.getDepartmentId());
					deptNode.setCode(deptTemp.getDeptCode());
					deptNode.setName(deptTemp.getName());
					deptNode.setNameWithoutPerson(deptTemp.getName());
					deptNode.setpId(deptTemp.getParentDept() == null ? "-1" : deptTemp.getParentDept().getDepartmentId());
					deptNode.setIsParent(!deptTemp.getLeaf());
					deptNode.setSubSysTem("DEPT");
					deptNode.setActionUrl(deptTemp.getDisabled() ? "1" : "0");
					deptNode.setIcon(iconPath + "dept.gif");
					deptNode.setDeptCode(deptTemp.getDeptCode());
					deptNode.setOrgCode(deptTemp.getOrgCode());
					deptNode.setPersonCount("" + deptTemp.getPersonCount());
					deptNode.setPersonCountD("" + deptTemp.getPersonCountD());
					deptNode.setPersonCountP("" + deptTemp.getPersonCountP());
					deptNode.setPersonCountDP("" + deptTemp.getPersonCountDP());
					deptNode.setDisplayOrder(3);
					personTreeNodes.add(deptNode);
				}
			}
			if (OtherUtil.measureNotNull(deptIdS)) {
				deptIdS = OtherUtil.subStrEnd(deptIdS, ",");
			}
			filters.clear();
			filters.add(new PropertyFilter("INS_department.departmentId", deptIdS));
			filters.add(new PropertyFilter("OAS_personCode", ""));
			List<Person> personAllTempList = personManager.getByFilters(filters);
			if (OtherUtil.measureNotNull(personAllTempList) && !personAllTempList.isEmpty()) {
				for (Person personTemp : personAllTempList) {
					personNode = new DeptTreeNode();
					personNode.setId(personTemp.getPersonId());
					personNode.setCode(personTemp.getPersonCode());
					personNode.setSubSysTem("PERSON");
					personNode.setIsParent(false);
					personNode.setpId(personTemp.getDepartment().getDepartmentId());
					personNode.setIcon(iconPath + "person.png");
					personNode.setActionUrl(personTemp.getDisable() ? "1" : "0");
					personNode.setName(personTemp.getName());
					personNode.setNameWithoutPerson(personTemp.getName());
					personNode.setDisplayOrder(4);
					personTreeNodes.add(personNode);
				}
			}
			Collections.sort(personTreeNodes, new Comparator<DeptTreeNode>() {
				@Override
				public int compare(DeptTreeNode node1, DeptTreeNode node2) {
					return node1.getDisplayOrder() - node2.getDisplayOrder();
				}
			});

		} catch (Exception e) {
			log.error("makeDepartmentTree Error", e);
		}
		return SUCCESS;
	}

	private void setDeptPersonCount(Department deptTemp, int addPersonNum, int addPersonNumD, int addPersonNumP, int addPersonNumDP) {
		Department pDept = deptTemp.getParentDept();
		if (OtherUtil.measureNotNull(pDept)) {
			int personCount = pDept.getPersonCount();
			int personCountD = pDept.getPersonCountD();
			int personCountP = pDept.getPersonCountP();
			int personCountDP = pDept.getPersonCountDP();
			pDept.setPersonCount(personCount + addPersonNum);
			pDept.setPersonCountD(personCountD + addPersonNumD);
			pDept.setPersonCountP(personCountP + addPersonNumP);
			pDept.setPersonCountDP(personCountDP + addPersonNumDP);
			setDeptPersonCount(pDept, addPersonNum, addPersonNumD, addPersonNumP, addPersonNumDP);
		}
	}

	private void setPersonCountInOrg(Org orgTemp, int addPersonNum, int addPersonNumD, int addPersonNumP, int addPersonNumDP) {
		Org parentOrg = orgTemp.getParentOrgCode();
		if (OtherUtil.measureNotNull(parentOrg)) {
			int personCount = parentOrg.getPersonCount();
			int personCountD = parentOrg.getPersonCountD();
			int personCountP = parentOrg.getPersonCountP();
			int personCountDP = parentOrg.getPersonCountDP();
			parentOrg.setPersonCount(personCount + addPersonNum);
			parentOrg.setPersonCountD(personCountD + addPersonNumD);
			parentOrg.setPersonCount(personCountP + addPersonNumP);
			parentOrg.setPersonCountD(personCountDP + addPersonNumDP);
			setPersonCountInOrg(parentOrg, addPersonNum, addPersonNumD, addPersonNumP, addPersonNumDP);
		}
	}

	public String getGzTypeJsonStr() {
		return gzTypeJsonStr;
	}

	public void setGzTypeJsonStr(String gzTypeJsonStr) {
		this.gzTypeJsonStr = gzTypeJsonStr;
	}

	public String getKqTypeJsonStr() {
		return kqTypeJsonStr;
	}

	public void setKqTypeJsonStr(String kqTypeJsonStr) {
		this.kqTypeJsonStr = kqTypeJsonStr;
	}

	private FieldInfoManager fieldInfoManager;
	private String fieldJsonStr;

	public void setFieldInfoManager(FieldInfoManager fieldInfoManager) {
		this.fieldInfoManager = fieldInfoManager;
	}

	public String getFieldJsonStr() {
		return fieldJsonStr;
	}

	public void setFieldJsonStr(String fieldJsonStr) {
		this.fieldJsonStr = fieldJsonStr;
	}

	//更新月度职工表
	public String updateMonthPersonList() {
		params = "";
		String sql = "SELECT DISTINCT checkperiod FROM t_monthperson ORDER BY checkperiod DESC";
		List<Map<String, Object>> resultList = personManager.getBySqlToMap(sql);
		if (OtherUtil.measureNotNull(resultList) && !resultList.isEmpty()) {
			for (Map<String, Object> tempMap : resultList) {
				String checkperiod = tempMap.get("checkperiod").toString();
				params += checkperiod + ",";
			}
		}
		if (OtherUtil.measureNotNull(params)) {
			params = OtherUtil.subStrEnd(params, ",");
		}
		HttpServletRequest request = this.getRequest();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		String tableCode = request.getParameter("tableCode");
		filters.add(new PropertyFilter("EQS_bdInfo.tableName", tableCode));
		filters.add(new PropertyFilter("EQB_disabled", "0"));
		filters.add(new PropertyFilter("EQB_batchEdit", "1"));
		filters.add(new PropertyFilter("OAI_sn", ""));
		List<FieldInfo> fieldInfos = fieldInfoManager.getByFilters(filters);
		fieldJsonStr = "";
		if (OtherUtil.measureNotNull(fieldInfos) && !fieldInfos.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (FieldInfo fieldInfo : fieldInfos) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", fieldInfo.getFieldCode());
				jsonObject.put("name", fieldInfo.getFieldName());
				jsonArray.add(jsonObject);
			}
			fieldJsonStr = jsonArray.toString();
		}
		return SUCCESS;
	}

	//更新月职工表
	public String updateMonthPerson() {
		HttpServletRequest request = this.getRequest();
		String periodStr = request.getParameter("periodStr");
		String fieldStr = request.getParameter("fieldStr");
		String personIdStr = id;
		try {
			periodStr = OtherUtil.subStrEnd(periodStr, ",");
			fieldStr = OtherUtil.subStrEnd(fieldStr, ",");
			String[] fields = fieldStr.split(",");
			String sqlTemp = " UPDATE t_monthperson SET ";
			for (String fieldTemp : fields) {
				sqlTemp += fieldTemp + "=b." + fieldTemp + ",";
			}
			sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
			String personIdTemp = OtherUtil.splitStrAddQuotes(personIdStr, ",");
			sqlTemp += " FROM t_monthperson a,t_person b ";
			sqlTemp += " WHERE a.personId = b.personId AND a.personId IN (" + personIdTemp + ") ";
			sqlTemp += " AND a.checkperiod = ";
			String[] periods = periodStr.split(",");
			List<String> sqlList = new ArrayList<String>();
			for (String periodTemp : periods) {
				String sql = sqlTemp + "'" + periodTemp + "'";
				sqlList.add(sql);
			}
			personManager.executeSqlList(sqlList);
		} catch (Exception e) {
			log.error("updateMonthPerson:" + e.getMessage());
			return ajaxForwardError("系统错误。");
		}
		return ajaxForwardSuccess("更新成功。");
	}
}