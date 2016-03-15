package com.huge.ihos.hr.changesInPersonnel.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.changesInPersonnel.model.SysPersonLeave;
import com.huge.ihos.hr.changesInPersonnel.service.SysPersonLeaveManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.model.HrPersonPK;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonHisManager;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.service.PactManager;
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
public class SysPersonLeavePagedAction extends JqGridBaseAction implements Preparable {

	private SysPersonLeaveManager sysPersonLeaveManager;
	private List<SysPersonLeave> sysPersonLeaves;
	private SysPersonLeave sysPersonLeave;
	private String id;
	private String personId;
	private HrPersonCurrentManager hrPersonCurrentManager;
	private String personNeedCheck;
	private Boolean pactExist = false;
	private PactManager pactManager;

	public Boolean getPactExist() {
		return pactExist;
	}

	public void setPactExist(Boolean pactExist) {
		this.pactExist = pactExist;
	}

	public PactManager getPactManager() {
		return pactManager;
	}

	public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}

	public void setSysPersonLeaveManager(SysPersonLeaveManager sysPersonLeaveManager) {
		this.sysPersonLeaveManager = sysPersonLeaveManager;
	}

	public List<SysPersonLeave> getSysPersonLeaves() {
		return sysPersonLeaves;
	}

	public SysPersonLeave getSysPersonLeave() {
		return sysPersonLeave;
	}

	public void setSysPersonLeave(SysPersonLeave sysPersonLeave) {
		this.sysPersonLeave = sysPersonLeave;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }
	
	private List<String> leaveTypeList;
    
    public List<String> getLeaveTypeList() {
        return leaveTypeList;
    }
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	private HrOrgManager hrOrgManager;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	
	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
	@SuppressWarnings("unchecked")
	public String sysPersonLeaveGridList() {
		log.debug("enter sysPersonLeaveGridList method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("deptId");
			String personId = request.getParameter("personId");
			String orgCodes = hrOrgManager.getAllAvailableString();
			if(OtherUtil.measureNull(orgCodes)){
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_hrPerson.orgCode",orgCodes));
			String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode, deptId);
			if(deptIds!=null){
				filters.add(new PropertyFilter("INS_hrPerson.department.departmentId",deptIds));
			}
			if(OtherUtil.measureNotNull(personId)){// click PERSON node
				filters.add(new PropertyFilter("EQS_hrPerson.personId",personId));
			}
			String showDisabledDept = request.getParameter("showDisabledDept");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			if(OtherUtil.measureNull(showDisabledDept)){// show disabled DEPT
				filters.add(new PropertyFilter("EQB_hrPerson.department.disabled","0"));
			}
			if(OtherUtil.measureNull(showDisabledPerson)){// show disabled PERSON
				filters.add(new PropertyFilter("EQB_hrPerson.disable","0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = sysPersonLeaveManager.getSysPersonLeaveCriteria(pagedRequests,filters);
			this.sysPersonLeaves = (List<SysPersonLeave>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("sysPersonLeaveGridList Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(OtherUtil.measureNull(sysPersonLeave.getChecker().getPersonId())){
				sysPersonLeave.setChecker(null);
			}
			if(OtherUtil.measureNull(sysPersonLeave.getDoner().getPersonId())){
				sysPersonLeave.setDoner(null);
			}
			personId = sysPersonLeave.getHrPerson().getPersonId();
			 if(this.isEntityIsNew()){
				 String[] checkLockStates={"DD","DG","LZ","HT","XQ"};
				 String checkPersonId=sysPersonLeave.getHrPerson().getPersonId();
				 String  mesStr= hrPersonCurrentManager.checkLockHrPersonCurrent(checkPersonId,checkLockStates );
				 if(mesStr!=null){
					 mesStr=HrUtil.parseLockState(mesStr);
					 return ajaxForwardError("该人员正在"+mesStr);
				 }
			 }
			 if(sysPersonLeave.getState().equals("2")){
					String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
					boolean ansycData = "1".equals(ansyOrgDeptPerson);
					//sysPersonLeave=sysPersonLeaveManager.saveAndDoneSysPersonLeave(sysPersonLeave, this.getSessionUser().getPerson(),this.getOperTime(),this.isEntityIsNew(),ansycData);
					sysPersonLeave=sysPersonLeaveManager.saveAndDoneSysPersonLeave(sysPersonLeave, this.getSessionUser().getPerson(),new Date(),this.isEntityIsNew(),ansycData);
					List<String> checkIds = new ArrayList<String>();
					checkIds.add(sysPersonLeave.getId());
					this.pactExist = getPactExists(checkIds);
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,sysPersonLeave.getHrPerson().getDepartment().getDepartmentId());
			 }else{
					sysPersonLeave=sysPersonLeaveManager.saveSysPersonLeave(sysPersonLeave, this.isEntityIsNew());
				}
			id=sysPersonLeave.getId();
		} catch (Exception dre) {
			log.error("save sysPersonLeave error:",dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "sysPersonLeave.added" : "sysPersonLeave.updated";
		 if(sysPersonLeave.getState().equals("2")){
			 key = "人员离职成功。";
		 }
		return ajaxForward(this.getText(key));
	}
	private HrPersonHisManager hrPersonHisManager;
	
    public void setHrPersonHisManager(HrPersonHisManager hrPersonHisManager) {
		this.hrPersonHisManager = hrPersonHisManager;
	}
	
    public String edit() {
    	personNeedCheck = this.globalParamManager.getGlobalParamByKey("personNeedCheck");
    	this.leaveTypeList= this.getDictionaryItemManager().getAllItemsByCode( "leaveType" );
        if (id != null) {
        	sysPersonLeave = sysPersonLeaveManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	//Date operDate = this.getOperTime();
        	Date operDate=new Date();
    		Person operPerson = this.getSessionUser().getPerson();
        	sysPersonLeave = new SysPersonLeave();
        	sysPersonLeave.setYearMonth(this.getLoginPeriod());
        	sysPersonLeave.setState("0");
        	sysPersonLeave.setMakeDate(operDate);
        	sysPersonLeave.setMaker(operPerson);
        	if(personId!=null){
        		HrPersonCurrent hrPersonCurrent = hrPersonCurrentManager.get(personId);
        		HrPersonPK hrPeronPk = new HrPersonPK(hrPersonCurrent.getPersonId(),hrPersonCurrent.getSnapCode());
        		HrPersonHis hrPersonHis = hrPersonHisManager.get(hrPeronPk);
        		sysPersonLeave.setHrPerson(hrPersonCurrent);
        		sysPersonLeave.setHrPersonHis(hrPersonHis);
        		sysPersonLeave.setPersonSnapCode(hrPersonCurrent.getSnapCode());
        	}
        	if("done".equals(oper) || "0".equals(personNeedCheck)){
        		sysPersonLeave.setState("2");
        		sysPersonLeave.setChecker(operPerson);
        		sysPersonLeave.setCheckDate(operDate);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String sysPersonLeaveList(){
    	try {
    		this.leaveTypeList= this.getDictionaryItemManager().getAllItemsByCode( "leaveType" );
	    	   List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
	    	   //menuButtons.get(0).setEnable(false);
	    	   personNeedCheck = this.globalParamManager.getGlobalParamByKey("personNeedCheck");
	    	   Iterator<MenuButton> ite = menuButtons.iterator();
				if("0".equals(personNeedCheck)){
					List<String> checkIds = new ArrayList<String>();
					checkIds.add("1002020404");
					checkIds.add("1002020405");
					checkIds.add("1002020406");
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
	public String sysPersonLeaveGridEdit() {
		try {
			//Date operDate = this.getOperTime();
			Date operDate=new Date();
			Person person = this.getSessionUser().getPerson();
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				sysPersonLeaveManager.deleteSysPersonLeave(idl);
//				String[] ida=new String[idl.size()];
//				idl.toArray(ida);
//				this.sysPersonLeaveManager.remove(ida);
				gridOperationMessage = this.getText("sysPersonLeave.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				sysPersonLeaveManager.auditSysPersonLeave(checkIds,person,operDate);
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				sysPersonLeaveManager.antiSysPersonLeave(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("done")){
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> doneIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String doneId = ids.nextToken();
					doneIds.add(doneId);
					
				}
				sysPersonLeaveManager.doneSysPersonLeave(doneIds, person,operDate,ansycData);
				for(String LeaveId:doneIds){
					SysPersonLeave sysPersonLeave = sysPersonLeaveManager.get(LeaveId);
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,sysPersonLeave.getHrPerson().getDepartment().getDepartmentId());
				}
				this.pactExist = getPactExists(doneIds);
				gridOperationMessage = this.getText("执行成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("donePact")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> doneIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String doneId = ids.nextToken();
					doneIds.add(doneId);
				}
				sysPersonLeaveManager.doneChangePact(doneIds, person,operDate,this.getLoginPeriod());
				gridOperationMessage = this.getText("合同解除成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSysPersonLeaveGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (sysPersonLeave == null) {
			return "Invalid sysPersonLeave Data";
		}
		return SUCCESS;

	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
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
	private Boolean getPactExists(List<String> sysPersonLeaveIds){
		Boolean pactExistTemp = false;
		if(OtherUtil.measureNotNull(sysPersonLeaveIds)&&!sysPersonLeaveIds.isEmpty()){
			String personIds="";
			for(String idTemp:sysPersonLeaveIds){
				sysPersonLeave = this.sysPersonLeaveManager.get(idTemp);
				String personId = sysPersonLeave.getHrPerson().getPersonId();
				personIds += personId+",";
			}
			if(!personIds.equals("")){
				personIds = personIds.substring(0, personIds.length()-1);
			}
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("INS_hrPerson.personId", personIds));
    		List<Pact> pacts=pactManager.getByFilters(filters);
    		if(OtherUtil.measureNotNull(pacts)&&!pacts.isEmpty()){
    			pactExistTemp = true;
    		}
		}
		return pactExistTemp;
	}
}

