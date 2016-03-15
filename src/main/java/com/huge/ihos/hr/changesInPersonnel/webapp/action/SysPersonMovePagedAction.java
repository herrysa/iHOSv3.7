package com.huge.ihos.hr.changesInPersonnel.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.changesInPersonnel.model.SysPersonMove;
import com.huge.ihos.hr.changesInPersonnel.service.SysPersonMoveManager;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
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
public class SysPersonMovePagedAction extends JqGridBaseAction implements Preparable {

	private SysPersonMoveManager sysPersonMoveManager;
	private HrPersonCurrentManager hrPersonCurrentManager;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private List<SysPersonMove> sysPersonMoves;
	private SysPersonMove sysPersonMove;
	private String id;
	private String personId;
	private String personNeedCheck;
	private Boolean pactExist = false;
	private PactManager pactManager;
	

	public void setSysPersonMoveManager(SysPersonMoveManager sysPersonMoveManager) {
		this.sysPersonMoveManager = sysPersonMoveManager;
	}

	public List<SysPersonMove> getSysPersonMoves() {
		return sysPersonMoves;
	}

	public SysPersonMove getSysPersonMove() {
		return sysPersonMove;
	}

	public void setSysPersonMove(SysPersonMove sysPersonMove) {
		this.sysPersonMove = sysPersonMove;
	}
	public String getPersonNeedCheck() {
		return personNeedCheck;
	}

	public void setPersonNeedCheck(String personNeedCheck) {
		this.personNeedCheck = personNeedCheck;
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

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	@SuppressWarnings("unchecked")
	public String sysPersonMoveGridList() {
		log.debug("enter sysPersonMoveGridList method!");
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
			String showDisabledPerson = request
					.getParameter("showDisabledPerson");
			if (showDisabledDept == null) {// show disabled DEPT
				filters.add(new PropertyFilter(
						"EQB_hrPerson.department.disabled", "0"));
			}
			if (showDisabledPerson == null) {// show disabled PERSON
				filters.add(new PropertyFilter("EQB_hrPerson.disable", "0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = sysPersonMoveManager.getSysPersonMoveCriteria(
					pagedRequests, filters);
			this.sysPersonMoves = (List<SysPersonMove>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("sysPersonMoveGridList Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if (OtherUtil.measureNull(sysPersonMove.getChecker().getPersonId())) {
				sysPersonMove.setChecker(null);
			}
			if (OtherUtil.measureNull(sysPersonMove.getDoner().getPersonId())) {
				sysPersonMove.setDoner(null);
			}
			if (OtherUtil.measureNull(sysPersonMove.getDuty())||OtherUtil.measureNull(sysPersonMove.getDuty().getId())) {
				sysPersonMove.setDuty(null);
			}
			String newDeptId = sysPersonMove.getHrDeptNew().getDepartmentId();
			if (this.isEntityIsNew()) {
				 String[] checkPersonLockStates={"DD","DG","LZ"};
				 String checkPersonId=sysPersonMove.getHrPerson().getPersonId();
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
				sysPersonMove.setDeptSnapCodeNew(hrDepartmentCurrentManager.get(newDeptId).getSnapCode());
			} else {
				if (!newDeptId.equals(sysPersonMoveManager.get(sysPersonMove.getId()).getHrDeptNew().getDepartmentId())) {
					sysPersonMove.setDeptSnapCodeNew((hrDepartmentCurrentManager.get(newDeptId).getSnapCode()));
				}
			}
			String checkNewDeptId=sysPersonMove.getHrDeptNew().getDepartmentId();
			String[] checkNewDeptLockStates={"HDR","HDM","HDT"};
			String deptMesStr=hrDepartmentCurrentManager.checkLockHrDepartmentCurrent(checkNewDeptId, checkNewDeptLockStates);
			if(deptMesStr!=null){
				deptMesStr=HrUtil.parseLockState(deptMesStr);
				 return ajaxForwardError("新部门正在"+deptMesStr);
			}
			if(sysPersonMove.getState().equals("2")){
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				delTreeNodes = new ArrayList<HrDeptTreeNode>();
				addTreeNodes = new ArrayList<HrDeptTreeNode>();
				String personId = sysPersonMove.getHrPerson().getPersonId();
				HrPersonCurrent hpc = hrPersonCurrentManager.get(personId);
				String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
				HrDeptTreeNode personNode = new HrDeptTreeNode();
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
				delTreeNodes.add(personNode);
				HrDeptTreeNode personAddNode = personNode.clone();
				personAddNode.setpId(sysPersonMove.getHrDeptNew().getDepartmentId());
				addTreeNodes.add(personAddNode);
				sysPersonMove = sysPersonMoveManager.saveAndDoneSysPersonMove(sysPersonMove, this.getSessionUser().getPerson(),new Date(), this.isEntityIsNew(), ansycData);
				List<String> checkIds = new ArrayList<String>();
				checkIds.add(sysPersonMove.getId());
				this.pactExist = getPactExists(checkIds);
				HrUtil.computePersonCountTask(hrDepartmentCurrentManager,sysPersonMove.getHrPersonHis().getDepartment().getDepartmentId());
				HrUtil.computePersonCountTask(hrDepartmentCurrentManager,sysPersonMove.getHrDeptNew().getDepartmentId());
				//sysPersonMove = sysPersonMoveManager.saveAndDoneSysPersonMove(sysPersonMove, this.getSessionUser().getPerson(),this.getOperTime(), this.isEntityIsNew(), ansycData);
			}else{
				sysPersonMove = sysPersonMoveManager.saveSysPersonMove(sysPersonMove, this.isEntityIsNew());
			}
			id = sysPersonMove.getId();
		} catch (Exception dre) {
			log.error("save sysPersonMove error:", dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "sysPersonMove.added"
				: "sysPersonMove.updated";
		if(sysPersonMove.getState().equals("2")){
			key = "人员调动成功。";
		}
		return ajaxForward(this.getText(key));
	}

	private HrPersonHisManager hrPersonHisManager;

	public void setHrPersonHisManager(HrPersonHisManager hrPersonHisManager) {
		this.hrPersonHisManager = hrPersonHisManager;
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
		personNeedCheck = this.globalParamManager.getGlobalParamByKey("personNeedCheck");
        if (id != null) {
        	sysPersonMove = sysPersonMoveManager.get(id);
        	orgName = sysPersonMove.getHrDeptNew().getHrOrg().getOrgname();
        	orgCode = sysPersonMove.getHrDeptNew().getOrgCode();
        	this.setEntityIsNew(false);
        } else {
        	//Date operDate = this.getOperTime();
        	Date operDate = new Date();
    		Person operPerson = this.getSessionUser().getPerson();
        	sysPersonMove = new SysPersonMove();
        	sysPersonMove.setYearMonth(this.getLoginPeriod());
        	sysPersonMove.setState("0");
        	sysPersonMove.setMaker(operPerson);
        	sysPersonMove.setMakeDate(operDate);
        	if(personId!=null){
        		HrPersonCurrent hrPersonCurrent = hrPersonCurrentManager.get(personId);
        		HrPersonPK hrPeronPk = new HrPersonPK(hrPersonCurrent.getPersonId(),hrPersonCurrent.getSnapCode());
        		HrPersonHis hrPersonHis = hrPersonHisManager.get(hrPeronPk);
        		sysPersonMove.setHrPerson(hrPersonCurrent);
        		sysPersonMove.setHrPersonHis(hrPersonHis);
        		sysPersonMove.setPersonSnapCode(hrPersonCurrent.getSnapCode());
        	}
        	if("done".equals(oper) || "0".equals(personNeedCheck)){
        		sysPersonMove.setState("2");
        		sysPersonMove.setChecker(operPerson);
        		sysPersonMove.setCheckDate(operDate);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	
    public String sysPersonMoveList(){
    	try {
	    	   List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
	    	   //menuButtons.get(0).setEnable(false);
	    	   personNeedCheck = this.globalParamManager.getGlobalParamByKey("personNeedCheck");
	    	   Iterator<MenuButton> ite = menuButtons.iterator();
				if("0".equals(personNeedCheck)){
					List<String> checkIds = new ArrayList<String>();
					checkIds.add("1002020204");
					checkIds.add("1002020205");
					checkIds.add("1002020206");
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
    private List<HrDeptTreeNode> delTreeNodes;
    private List<HrDeptTreeNode> addTreeNodes;
	public String sysPersonMoveGridEdit() {
		try {
			//Date operDate = this.getOperTime();
			Date operDate =new Date();
			Person operPerson = this.getSessionUser().getPerson();
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					// Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
				}
				sysPersonMoveManager.deleteSysPersonMove(idl);
//				String[] ida = new String[idl.size()];
//				idl.toArray(ida);
//				this.sysPersonMoveManager.remove(ida);
				gridOperationMessage = this.getText("sysPersonMove.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("check")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				sysPersonMoveManager.auditSysPersonMove(checkIds, operPerson, operDate);
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("cancelCheck")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				sysPersonMoveManager.antiSysPersonMove(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("done")) {
				String ansyOrgDeptPerson = this.globalParamManager
						.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);

				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> doneIds = new ArrayList<String>();
				delTreeNodes = new ArrayList<HrDeptTreeNode>();
				addTreeNodes = new ArrayList<HrDeptTreeNode>();
				while (ids.hasMoreTokens()) {
					String doneId = ids.nextToken();
					sysPersonMove = sysPersonMoveManager.get(doneId);
					String personId = sysPersonMove.getHrPerson().getPersonId();
					HrPersonCurrent hpc = hrPersonCurrentManager.get(personId);
					String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
					HrDeptTreeNode personNode = new HrDeptTreeNode();
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
					delTreeNodes.add(personNode);
					HrDeptTreeNode personAddNode = personNode.clone();
					personAddNode.setpId(sysPersonMove.getHrDeptNew().getDepartmentId());
					addTreeNodes.add(personAddNode);
					doneIds.add(doneId);
				}
				sysPersonMoveManager.doneSysPersonMove(doneIds, operPerson, operDate, ansycData);
				for(String moveId:doneIds){
					sysPersonMove = sysPersonMoveManager.get(moveId);
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,sysPersonMove.getHrPersonHis().getDepartment().getDepartmentId());
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,sysPersonMove.getHrDeptNew().getDepartmentId());
				}
				this.pactExist = getPactExists(doneIds);
				gridOperationMessage = this.getText("执行成功。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("donePact")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> doneIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String doneId = ids.nextToken();
					doneIds.add(doneId);
				}
				sysPersonMoveManager.doneChangePact(doneIds);
				gridOperationMessage = this.getText("执行成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checksysPersonMoveGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private Boolean getPactExists(List<String> sysPersonMoveIds){
		Boolean pactExistTemp = false;
		if(OtherUtil.measureNotNull(sysPersonMoveIds)&&!sysPersonMoveIds.isEmpty()){
			String personIds="";
			for(String idTemp:sysPersonMoveIds){
				sysPersonMove = this.sysPersonMoveManager.get(idTemp);
				String personId = sysPersonMove.getHrPerson().getPersonId();
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
	
	private String isValid() {
		if (sysPersonMove == null) {
			return "Invalid sysPersonMove Data";
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

	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

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

	public List<HrDeptTreeNode> getDelTreeNodes() {
		return delTreeNodes;
	}

	public void setDelTreeNodes(List<HrDeptTreeNode> delTreeNodes) {
		this.delTreeNodes = delTreeNodes;
	}

	public List<HrDeptTreeNode> getAddTreeNodes() {
		return addTreeNodes;
	}

	public void setAddTreeNodes(List<HrDeptTreeNode> addTreeNodes) {
		this.addTreeNodes = addTreeNodes;
	}
}
