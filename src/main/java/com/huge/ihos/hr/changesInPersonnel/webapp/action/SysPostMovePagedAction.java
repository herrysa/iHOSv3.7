package com.huge.ihos.hr.changesInPersonnel.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.changesInPersonnel.model.SysPostMove;
import com.huge.ihos.hr.changesInPersonnel.service.SysPostMoveManager;
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
public class SysPostMovePagedAction extends JqGridBaseAction implements Preparable {

	private SysPostMoveManager sysPostMoveManager;
	private List<SysPostMove> sysPostMoves;
	private SysPostMove sysPostMove;
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

	public void setSysPostMoveManager(SysPostMoveManager sysPostMoveManager) {
		this.sysPostMoveManager = sysPostMoveManager;
	}

	public List<SysPostMove> getSysPostMoves() {
		return sysPostMoves;
	}

	public SysPostMove getSysPostMove() {
		return sysPostMove;
	}

	public void setSysPostMove(SysPostMove sysPostMove) {
		this.sysPostMove = sysPostMove;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	private List<String> postTypeList;

	public List<String> getPostTypeList() {
		return postTypeList;
	}
	public void prepare() throws Exception {
		this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode("postType");
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
	public String sysPostMoveGridList() {
		log.debug("enter sysPostMoveGridList method!");
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
			if(showDisabledDept==null){// show disabled DEPT
				filters.add(new PropertyFilter("EQB_hrPerson.department.disabled","0"));
			}
			if(showDisabledPerson==null){// show disabled PERSON
				filters.add(new PropertyFilter("EQB_hrPerson.disable","0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = sysPostMoveManager.getSysPostMoveCriteria(pagedRequests,filters);
			this.sysPostMoves = (List<SysPostMove>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("sysPostMoveGridList Error", e);
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
			 if(OtherUtil.measureNull(sysPostMove.getChecker().getPersonId())){
				 sysPostMove.setChecker(null);
			 }
			 if(OtherUtil.measureNull(sysPostMove.getDoner().getPersonId())){
				 sysPostMove.setDoner(null);
			 }
			 if(this.isEntityIsNew()){
				 String[] checkPersonLockStates={"DD","DG","LZ"};
				 String checkPersonId=sysPostMove.getHrPerson().getPersonId();
				 String  mesStr= hrPersonCurrentManager.checkLockHrPersonCurrent(checkPersonId,checkPersonLockStates );
				 if(mesStr!=null){
					 mesStr=HrUtil.parseLockState(mesStr);
					 return ajaxForwardError("该人员正在"+mesStr);
				 }
				 String checkDeptId=hrPersonCurrentManager.get(checkPersonId).getDepartment().getDepartmentId();
				 String[] checkDeptLockStates={"HDR","HDM","HDT"};
				 mesStr=hrDepartmentCurrentManager.checkLockHrDepartmentCurrent(checkDeptId, checkDeptLockStates);
				 if(mesStr!=null){
					 mesStr=HrUtil.parseLockState(mesStr);
					 return ajaxForwardError("该人员所属部门正在"+mesStr);
				 }
			 }
			 if(sysPostMove.getState().equals("2")){
					String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
					boolean ansycData = "1".equals(ansyOrgDeptPerson);
					sysPostMove=sysPostMoveManager.saveAndDoneSysPostMove(sysPostMove,this.getSessionUser().getPerson(),new Date(),this.isEntityIsNew(),ansycData);
					List<String> checkIds = new ArrayList<String>();
					checkIds.add(sysPostMove.getId());
					this.pactExist = getPactExists(checkIds);
					//sysPostMove=sysPostMoveManager.saveAndDoneSysPostMove(sysPostMove,this.getSessionUser().getPerson(),this.getOperTime(),this.isEntityIsNew(),ansycData);
				}else{
					sysPostMove=sysPostMoveManager.saveSysPostMove(sysPostMove, this.isEntityIsNew());
				}
			id=sysPostMove.getId();
		} catch (Exception dre) {
			log.error("save sysPostMove Error", dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "sysPostMove.added" : "sysPostMove.updated";
		 if(sysPostMove.getState().equals("2")){
			 key = "人员调岗成功。";
		 }
		return ajaxForward(this.getText(key));
	}
	private HrPersonHisManager hrPersonHisManager;
	
    public void setHrPersonHisManager(HrPersonHisManager hrPersonHisManager) {
		this.hrPersonHisManager = hrPersonHisManager;
	}
    public String edit() {
    	personNeedCheck = this.globalParamManager.getGlobalParamByKey("personNeedCheck");
        if (id != null) {
        	sysPostMove = sysPostMoveManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	//Date operDate = this.getOperTime();
        	Date operDate = new Date();
    		Person operPerson = this.getSessionUser().getPerson();
        	sysPostMove = new SysPostMove();
        	sysPostMove.setYearMonth(this.getLoginPeriod());
        	sysPostMove.setState("0");
        	sysPostMove.setMakeDate(operDate);
        	sysPostMove.setMaker(operPerson);
        	if(personId!=null){
        		HrPersonCurrent hrPersonCurrent = hrPersonCurrentManager.get(personId);
        		HrPersonPK hrPeronPk = new HrPersonPK(hrPersonCurrent.getPersonId(),hrPersonCurrent.getSnapCode());
        		HrPersonHis hrPersonHis = hrPersonHisManager.get(hrPeronPk);
        		sysPostMove.setHrPerson(hrPersonCurrent);
        		sysPostMove.setHrPersonHis(hrPersonHis);
        		sysPostMove.setPersonSnapCode(hrPersonCurrent.getSnapCode());
        	}
        	if("done".equals(oper) || "0".equals(personNeedCheck)){
        		sysPostMove.setState("2");
        		sysPostMove.setChecker(operPerson);
        		sysPostMove.setCheckDate(operDate);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String sysPostMoveList(){
       try {
	    	 List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
	        //menuButtons.get(0).setEnable(false);
	    	 personNeedCheck = this.globalParamManager.getGlobalParamByKey("personNeedCheck");
	    	   Iterator<MenuButton> ite = menuButtons.iterator();
				if("0".equals(personNeedCheck)){
					List<String> checkIds = new ArrayList<String>();
					checkIds.add("1002020304");
					checkIds.add("1002020305");
					checkIds.add("1002020306");
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
	public String sysPostMoveGridEdit() {
		try {
			//Date operDate = this.getOperTime();
			Date operDate = new Date();
			Person operPerson = this.getSessionUser().getPerson();
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				sysPostMoveManager.deleteSysPostMove(idl);
//				String[] ida=new String[idl.size()];
//				idl.toArray(ida);
//				this.sysPostMoveManager.remove(ida);
				gridOperationMessage = this.getText("sysPostMove.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				sysPostMoveManager.auditSysPostMove(checkIds,operPerson,operDate);
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				sysPostMoveManager.antiSysPostMove(cancelCheckIds);
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
				sysPostMoveManager.doneSysPostMove(doneIds, operPerson,operDate,ansycData);
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
				sysPostMoveManager.doneChangePact(doneIds);
				gridOperationMessage = this.getText("合同修改成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSysPostMoveGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (sysPostMove == null) {
			return "Invalid sysPostMove Data";
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
	private Boolean getPactExists(List<String> sysPostMoveIds){
		Boolean pactExistTemp = false;
		if(OtherUtil.measureNotNull(sysPostMoveIds)&&!sysPostMoveIds.isEmpty()){
			String personIds="";
			for(String idTemp:sysPostMoveIds){
				sysPostMove = this.sysPostMoveManager.get(idTemp);
				String personId = sysPostMove.getHrPerson().getPersonId();
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

