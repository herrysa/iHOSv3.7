package com.huge.ihos.hr.changesInPersonnel.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.changesInPersonnel.model.PersonEntry;
import com.huge.ihos.hr.changesInPersonnel.service.PersonEntryManager;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;


@SuppressWarnings("serial")
public class PersonEntryPagedAction extends JqGridBaseAction implements Preparable {

	private PersonEntryManager personEntryManager;
	private List<PersonEntry> personEntries;
	private PersonEntry personEntry;
	private String id;
	private String deptId;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private HrPersonSnapManager hrPersonSnapManager;
	private HrPersonCurrentManager hrPersonCurrentManager;
	private String personNeedCheck;
	private Boolean pactCreate = false;

	public void setPersonEntryManager(PersonEntryManager personEntryManager) {
		this.personEntryManager = personEntryManager;
	}

	public List<PersonEntry> getPersonEntries() {
		return personEntries;
	}

	public PersonEntry getPersonEntry() {
		return personEntry;
	}

	public void setPersonEntry(PersonEntry personEntry) {
		this.personEntry = personEntry;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	private List<String> sexList;
    private List<String> educationalLevelList;
    private List<String> professionalList;
    private List<String> degreeList;
    private List<String> personPolList;
    private List<String> peopleList;
    private List<String> maritalStatusList;
    private List<String> salaryLevelResumeList;
    private List<String> postTypeList;
    private List<String> jobTitleList;
    public List<String> getJobTitleList() {
    	return jobTitleList;
    }

	public List<String> getPostTypeList() {
		return postTypeList;
	}

    
    public List<String> getMaritalStatusList() {
        return maritalStatusList;
    }
	
    public List<String> getSalaryLevelResumeList() {
        return salaryLevelResumeList;
    }
    
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	private HrOrgManager hrOrgManager;
	
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	@SuppressWarnings("unchecked")
	public String personEntryGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String orgCodes = hrOrgManager.getAllAvailableString();
			if(OtherUtil.measureNull(orgCodes)){
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_hrDept.orgCode",orgCodes));
			
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode, deptId);
			if(deptIds!=null){
				filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
			}
			
			String showDisabled = request.getParameter("showDisabled");
			if(OtherUtil.measureNull(showDisabled)){
				filters.add(new PropertyFilter("EQB_hrDept.disabled","0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, request);
			pagedRequests = personEntryManager.getPersonEntryCriteria(pagedRequests,filters);
			this.personEntries = (List<PersonEntry>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("personEntryGridList Error", e);
		}
		return SUCCESS;
	}
	private HrDeptTreeNode personNode;
	
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(this.isEntityIsNew()){
				String checkDeptId= personEntry.getHrDept().getDepartmentId();
				String[] checkLockStates={"HDR","HDM","HDT"};
				String mesStr=hrDepartmentCurrentManager.checkLockHrDepartmentCurrent(checkDeptId, checkLockStates);
				if(mesStr!=null){
					mesStr=HrUtil.parseLockState(mesStr);
					return ajaxForwardError("该部门正在"+mesStr);
				}
			}
			if(OtherUtil.measureNull(personEntry.getDuty())||OtherUtil.measureNull(personEntry.getDuty().getId())){
				personEntry.setDuty(null);
			}
			if(OtherUtil.measureNull(personEntry.getChecker().getPersonId())){
				personEntry.setChecker(null);
			}
			if(OtherUtil.measureNull(personEntry.getDoner().getPersonId())){
				personEntry.setDoner(null);
			}
			HttpServletRequest req = this.getRequest();
			String imagePath=req.getParameter("imagePath");
			if(personEntry.getState().equals("2")){
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				personEntry = personEntryManager.saveAndDonePersonEntry(personEntry, this.getSessionUser().getPerson(),this.isEntityIsNew(),ansycData,"",req);
				HrPersonSnap hps = hrPersonSnapManager.get(personEntry.getRemark());
				  String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
				   personNode = new HrDeptTreeNode();
				   personNode.setId(hps.getPersonId());
				   personNode.setCode(hps.getPersonCode());
				   personNode.setSnapCode(hps.getSnapCode());
				   personNode.setName(hps.getName());
				   personNode.setpId(hps.getHrDept().getDepartmentId());
				   personNode.setIsParent(false);
				   personNode.setNameWithoutPerson(hps.getName());
				   personNode.setSubSysTem("PERSON");
				   personNode.setActionUrl(hps.getDisabled()?"1":"0");
				   personNode.setIcon(iconPath+"person.png");
				   personNode.setDisplayOrder(4);
				   HrUtil.computePersonCountTask(hrDepartmentCurrentManager,hps.getHrDept().getDepartmentId());
				   //hrDepartmentCurrentManager.computePersonCount(hps.getHrDept().getDepartmentId());
				if(OtherUtil.measureNotNull(personEntry.getEmpType())&&"PT0102".equals(personEntry.getEmpType().getCode())){
					this.pactCreate = true;
				}
			}else{
				personEntry=personEntryManager.savePersonEntry(personEntry, this.isEntityIsNew(),imagePath,req);
			}
			id=personEntry.getId();
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "personEntry.added" : "personEntry.updated";
		if(personEntry.getState().equals("2")){
			key = "人员入职成功。";
		}
		return ajaxForward(this.getText(key));
	}
	private String orgName;
	private String orgCode;
	
    public String getOrgName() {
		return orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public String edit() {
		this.sexList = this.getDictionaryItemManager().getAllItemsByCode( "sex" );
		this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode( "education" );	
	    this.professionalList = this.getDictionaryItemManager().getAllItemsByCode("professional");
	    this.degreeList = this.getDictionaryItemManager().getAllItemsByCode("degree");
	    this.personPolList = this.getDictionaryItemManager().getAllItemsByCode("personPol");
	    this.peopleList = this.getDictionaryItemManager().getAllItemsByCode("nation");
	    this.maritalStatusList= this.getDictionaryItemManager().getAllItemsByCode( "maritalStatus" );
	    this.salaryLevelResumeList= this.getDictionaryItemManager().getAllItemsByCode( "salaryLevelResume" );
	    this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode("postType");
	    this.jobTitleList = this.getDictionaryItemManager().getAllItemsByCode("jobTitle");
	    personNeedCheck = this.globalParamManager.getGlobalParamByKey("personNeedCheck");
        if (id != null) {
        	personEntry = personEntryManager.get(id);
        	orgName = personEntry.getHrDept().getHrOrg().getOrgname();
        	orgCode = personEntry.getHrDept().getOrgCode();
        	this.setEntityIsNew(false);
        } else {
        	//Date operDate = this.getOperTime();
        	Date operDate=new Date();
    		Person operPerson = this.getSessionUser().getPerson();
        	personEntry =new PersonEntry();
        	personEntry.setYearMonth(this.getLoginPeriod());
        	personEntry.setState("0");
        	personEntry.setMaker(operPerson);
        	personEntry.setMakeDate(operDate);
        	if(deptId!=null){
        		HrDepartmentCurrent hrDepartmentCurrent =new HrDepartmentCurrent();
        		hrDepartmentCurrent=hrDepartmentCurrentManager.get(deptId);
        		personEntry.setHrDept(hrDepartmentCurrent);
        		personEntry.setDeptSnapCode(hrDepartmentCurrent.getSnapCode());
        		orgName = hrDepartmentCurrent.getHrOrg().getOrgname();
        		orgCode = hrDepartmentCurrent.getOrgCode();
        	}
        	if("done".equals(oper) || "0".equals(personNeedCheck)){
        		personEntry.setState("2");
        		personEntry.setChecker(operPerson);
        		personEntry.setCheckDate(operDate);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String personEntryList(){
    	try {
    		this.sexList = this.getDictionaryItemManager().getAllItemsByCode( "sex" );
    		this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode( "education" );	
    	    this.professionalList = this.getDictionaryItemManager().getAllItemsByCode("professional");
    	    this.degreeList = this.getDictionaryItemManager().getAllItemsByCode("degree");
    	    this.personPolList = this.getDictionaryItemManager().getAllItemsByCode("personPol");
    	    this.peopleList = this.getDictionaryItemManager().getAllItemsByCode("nation");
    	    this.maritalStatusList= this.getDictionaryItemManager().getAllItemsByCode( "maritalStatus" );
    	    this.salaryLevelResumeList= this.getDictionaryItemManager().getAllItemsByCode( "salaryLevelResume" );
    	    this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode("postType");
    	    this.jobTitleList = this.getDictionaryItemManager().getAllItemsByCode("jobTitle");
	    	   List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
	    	   //menuButtons.get(0).setEnable(false);
	    	   personNeedCheck = this.globalParamManager.getGlobalParamByKey("personNeedCheck");
	    	   Iterator<MenuButton> ite = menuButtons.iterator();
				if("0".equals(personNeedCheck)){
					List<String> checkIds = new ArrayList<String>();
					checkIds.add("1002020104");
					checkIds.add("1002020105");
					checkIds.add("1002020106");
					while(ite.hasNext()){
						MenuButton button = ite.next();
						if(checkIds.contains(button.getId())){
							ite.remove();
						}
					}
				}
	    	   setMenuButtonsToJson(menuButtons);
	    } catch (Exception e) {
	    	   e.printStackTrace();
	    }
    	return SUCCESS;
    }
    
    public String personEntryDPList(){
    	this.sexList = this.getDictionaryItemManager().getAllItemsByCode( "sex" );
		this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode( "education" );	
	    this.professionalList = this.getDictionaryItemManager().getAllItemsByCode("professional");
	    this.degreeList = this.getDictionaryItemManager().getAllItemsByCode("degree");
	    this.personPolList = this.getDictionaryItemManager().getAllItemsByCode("personPol");
	    this.peopleList = this.getDictionaryItemManager().getAllItemsByCode("nation");
	    this.maritalStatusList= this.getDictionaryItemManager().getAllItemsByCode( "maritalStatus" );
	    this.salaryLevelResumeList= this.getDictionaryItemManager().getAllItemsByCode( "salaryLevelResume" );
	    this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode("postType");
	    this.jobTitleList = this.getDictionaryItemManager().getAllItemsByCode("jobTitle");
		return SUCCESS;
	}
    
    private List<HrDeptTreeNode> treeNodes;
	public String personEntryGridEdit() {
		try {
			//Date operDate = this.getOperTime();
			Date operDate=new Date();
			Person operPerson = this.getSessionUser().getPerson();
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				personEntryManager.deletePersonEntry(idl);
//				String[] ida=new String[idl.size()];
//				idl.toArray(ida);
//				this.personEntryManager.remove(ida);
				gridOperationMessage = this.getText("personEntry.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				personEntryManager.auditPersonEntry(checkIds,operPerson,operDate);
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				personEntryManager.antiPersonEntry(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("done")){
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> doneIds = new ArrayList<String>();
				List<String> personIdS =new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String doneId = ids.nextToken();
					personEntry = personEntryManager.get(doneId);
					String personId = null;
					if(OtherUtil.measureNull(personEntry.getPersonId())){
						personId = personEntry.getHrDept().getOrgCode()+"_"+personEntry.getPersonCode();
					}else{
						personId = personEntry.getPersonId();
					}
					if(OtherUtil.measureNull(personEntry.getPersonFrom())){
						List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
						filters.add(new PropertyFilter("EQS_personId", personId));
						filters.add(new PropertyFilter("EQS_orgCode", personEntry.getHrDept().getOrgCode()));
						if(hrPersonCurrentManager.getByFilters(filters).size()>0){
							gridOperationMessage = this.getText("人员编码已经存在！");
							return ajaxForward(false, gridOperationMessage, false);
						}
					}
					personIdS.add(personId);
					doneIds.add(doneId);
				}
				personEntryManager.donePersonEntry(doneIds, operPerson,ansycData,this.getRequest());
				treeNodes = new ArrayList<HrDeptTreeNode>();
				for(String personId:personIdS){
					   HrPersonCurrent hpc = hrPersonCurrentManager.get(personId);
					   String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
					   personNode = new HrDeptTreeNode();
					   personNode.setId(hpc.getPersonId());
					   personNode.setCode(hpc.getPersonCode());
					   personNode.setSnapCode(hpc.getSnapCode());
					   personNode.setName(hpc.getName());
					   personNode.setpId(hpc.getDepartment().getDepartmentId());
					   personNode.setIsParent(false);
					   personNode.setNameWithoutPerson(hpc.getName());
					   personNode.setSubSysTem("PERSON");
					   personNode.setActionUrl(hpc.getDisable()?"1":"0");
					   personNode.setIcon(iconPath+"person.png");
					   personNode.setDisplayOrder(4);
					   treeNodes.add(personNode);
					   HrUtil.computePersonCountTask(hrDepartmentCurrentManager,hpc.getDepartment().getDepartmentId());
				}
				gridOperationMessage = this.getText("执行成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("donePact")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> donePactIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String donePactId = ids.nextToken();
					donePactIds.add(donePactId);
				}
				personEntryManager.donePactPersonEntry(donePactIds, operPerson,this.getLoginPeriod());
				gridOperationMessage = this.getText("合同生成成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPersonEntryGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (personEntry == null) {
			return "Invalid personEntry Data";
		}
		return SUCCESS;
	}

	public List<String> getSexList() {
        return sexList;
    }
    public List<String> getEducationalLevelList() {
        return educationalLevelList;
    }
    public List<String> getProfessionalList() {
        return professionalList;
    }
    public List<String> getDegreeList() {
        return degreeList;
    }
    public List<String> getPersonPolList(){
    	return personPolList;
    }
    public List<String> getPeopleList(){
    	return peopleList;
    }

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}

	public String getPersonNeedCheck() {
		return personNeedCheck;
	}

	public void setPersonNeedCheck(String personNeedCheck) {
		this.personNeedCheck = personNeedCheck;
	}

	public Boolean getPactCreate() {
		return pactCreate;
	}

	public void setPactCreate(Boolean pactCreate) {
		this.pactCreate = pactCreate;
	}

	public List<HrDeptTreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<HrDeptTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public HrDeptTreeNode getPersonNode() {
		return personNode;
	}

	public void setPersonNode(HrDeptTreeNode personNode) {
		this.personNode = personNode;
	}

	public HrPersonSnapManager getHrPersonSnapManager() {
		return hrPersonSnapManager;
	}

	public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
}

