package com.huge.ihos.hr.pact.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.model.PactRenew;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.pact.service.PactRenewManager;
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
public class PactRenewPagedAction extends JqGridBaseAction implements Preparable {

	private PactRenewManager pactRenewManager;
	private List<PactRenew> pactRenews;
	private PactRenew pactRenew;
	private String id;

	public void setPactRenewManager(PactRenewManager pactRenewManager) {
		this.pactRenewManager = pactRenewManager;
	}

	public List<PactRenew> getPactRenews() {
		return pactRenews;
	}

	public PactRenew getPactRenew() {
		return pactRenew;
	}

	public void setPactRenew(PactRenew pactRenew) {
		this.pactRenew = pactRenew;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }
	
	private String pactNeedCheck;

	public String getPactNeedCheck() {
		return pactNeedCheck;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	public String pactRenewList(){
		try {
			pactNeedCheck = this.globalParamManager.getGlobalParamByKey("pactNeedCheck");
			List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			List<String> checkIds = new ArrayList<String>();
			checkIds.add("1003020201");
			if("0".equals(pactNeedCheck)){
				checkIds.add("1003020205");
				checkIds.add("1003020206");
				checkIds.add("1003020207");
			}
			while(ite.hasNext()){
				MenuButton button = ite.next();
				if(checkIds.contains(button.getId())){
					ite.remove();
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter pactRenewList error:",e);
		}
		return SUCCESS;
	}
	private HrPersonCurrentManager hrPersonCurrentManager;
	private HrOrgManager hrOrgManager;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	
    public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}
	@SuppressWarnings("unchecked")
	public String pactRenewGridList() {
		log.debug("enter pactRenewGridList method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("deptId");
			String personId = request.getParameter("personId");
			if(orgCode!=null){
				String orgCodes = orgCode;
				List<HrOrg> orgList = hrOrgManager.getAllDescendants(orgCode);
				if(orgList!=null && !orgList.isEmpty()){
					for(HrOrg org:orgList){
						orgCodes += ","+org.getOrgCode();
					}
				}
				filters.add(new PropertyFilter("INS_pact.hrPerson.orgCode",orgCodes));
			}else if(deptId!=null){
				String deptIds = deptId;
				List<HrDepartmentCurrent> hrDeptList = hrDepartmentCurrentManager.getAllDescendants(deptId);
				if(hrDeptList!=null && !hrDeptList.isEmpty()){
					for(HrDepartmentCurrent hrDept:hrDeptList){
						deptIds += ","+hrDept.getDepartmentId();
					}
				}
				filters.add(new PropertyFilter("INS_pact.hrPerson.department.departmentId",deptIds));
			}else if(personId!=null){
				filters.add(new PropertyFilter("EQS_pact.hrPerson.personId",personId));
			}else{
				String orgCodes = hrOrgManager.getAllAvailableString();
				if(orgCodes==null){
					orgCodes = "";
				}
				filters.add(new PropertyFilter("INS_pact.hrPerson.orgCode",orgCodes));
			}
			String showDisabledDept = request.getParameter("showDisabledDept");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			String personName = request.getParameter("personName");
			if(showDisabledDept==null){
				filters.add(new PropertyFilter("EQB_pact.hrPerson.department.disabled","0"));
			}
			if(showDisabledPerson==null){
				filters.add(new PropertyFilter("EQB_pact.hrPerson.disable","0"));
			}
			if(personName!=null){
				filters.add(new PropertyFilter("LIKES_pact.hrPerson.name",personName));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = pactRenewManager
					.getPactRenewCriteria(pagedRequests,filters);
			this.pactRenews = (List<PactRenew>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("pactRenewGridList Error", e);
		}
		return SUCCESS;
	}
	private String addFrom;
	
	public String getAddFrom() {
		return addFrom;
	}

	public void setAddFrom(String addFrom) {
		this.addFrom = addFrom;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(OtherUtil.measureNull(pactRenew.getCheckPerson().getPersonId())){
				pactRenew.setCheckPerson(null);
			}
			if(OtherUtil.measureNull(pactRenew.getConfirmPerson().getPersonId())){
				pactRenew.setConfirmPerson(null);
			}
			//检查锁
			if(this.isEntityIsNew()){
			if(pactIds!=null&&!pactIds.equals("")){
				String[] pacts = pactIds.split(",");
				Pact pact=null;
				String[] checkPactLockState={"HT","XQ","ZZ","JC"};
				String[] checkPersonLockStates={"LZ"};
				for(String pactId:pacts){
					pact=pactManager.get(pactId);
					String personId=pact.getHrPerson().getPersonId();
					String mesStr=hrPersonCurrentManager.checkLockHrPersonCurrent(personId, checkPersonLockStates);
					if(mesStr!=null&&!mesStr.equals("")){
						mesStr=HrUtil.parseLockState(mesStr);
						String personName = hrPersonCurrentManager.get(personId).getName();
						return ajaxForwardError("人员["+personName+"]正在"+mesStr);
					}
					mesStr=pactManager.checkLockPact(pactId, checkPactLockState);
					if(mesStr!=null&&!mesStr.equals("")){
						mesStr=HrUtil.parseLockState(mesStr);
						String pactNo = pactManager.get(pactId).getCode();
						return ajaxForwardError("合同["+pactNo+"]正在"+mesStr);
					}
					
				 }
				}
			}
			if(pactRenew.getState()==3){
				/*
				 * 在合同信息处直接续签
				 */
				//pactRenewManager.confirmRenew(pactIds,pactRenew,this.getOperTime(),this.getSessionUser().getPerson());
				pactRenewManager.confirmRenew(pactIds,pactRenew,new Date(),this.getSessionUser().getPerson());
			}else{
				if(this.isEntityIsNew()){
					pactRenewManager.addPactRenew(pactIds,pactRenew);
				}else{
					pactRenewManager.save(pactRenew);
				}
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "pactRenew.added" : "pactRenew.updated";
		if(addFrom!=null && addFrom.equals("noCheck")){
			key = "pact.renew";
		}
		return ajaxForward(this.getText(key));
	}
	
	private PactManager pactManager;
	private String renewNames;
	private String pactIds;
	
    public String getPactIds() {
		return pactIds;
	}

	public void setPactIds(String pactIds) {
		this.pactIds = pactIds;
	}

	public String getRenewNames() {
		return renewNames;
	}

	public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}

	public String edit() {
		pactNeedCheck = this.globalParamManager.getGlobalParamByKey("pactNeedCheck");
        if (id != null) {
        	pactRenew = pactRenewManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        	filters.add(new PropertyFilter("INS_id",pactIds));
        	List<Pact> pacts = pactManager.getByFilters(filters);
        	renewNames = "";
        	for(Pact pact:pacts){
        		renewNames += pact.getHrPerson().getName()+",";
        	}
        	renewNames = OtherUtil.subStrEnd(renewNames, ",");
        	pactRenew = new PactRenew();
        	pactRenew.setState(1);
        	//Date date = this.getOperTime();
        	Date date = new Date();
        	Person person = this.getSessionUser().getPerson();
        	pactRenew.setMakeDate(date);
        	pactRenew.setMakePerson(person);
        	pactRenew.setYearMonth(this.getLoginPeriod());
        	/**
        	 * addFrom不为空表示在合同信息处续签
        	 * pactNeedCheck=0 表示无需走审核流程直接续签
        	 */
        	if(OtherUtil.measureNotNull(addFrom) || "0".equals(pactNeedCheck)){
        		pactRenew.setCheckDate(date);
        		pactRenew.setCheckPerson(person);
        		pactRenew.setState(3);
        	}
        	this.setEntityIsNew(true);
        }
        this.setRandom(this.getRequest().getParameter("random"));
        return SUCCESS;
    }
	public String pactRenewGridEdit() {
		try {
			//Date date = this.getOperTime();
			Date date = new Date();
			Person person = this.getSessionUser().getPerson();
			List<String> idl = new ArrayList<String>();
			StringTokenizer ids = new StringTokenizer(id,",");
			if (oper.equals("del")) {
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.pactRenewManager.remove(ida);
				gridOperationMessage = this.getText("pactRenew.deleted");
			}else if(oper.equals("check")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pactRenew = pactRenewManager.get(removeId);
					pactRenew.setState(2);
					pactRenew.setCheckDate(date);
					pactRenew.setCheckPerson(person);
					pactRenewManager.save(pactRenew);
				}
				gridOperationMessage = this.getText("审核成功。");
			}else if(oper.equals("cancelCheck")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pactRenew = pactRenewManager.get(removeId);
					pactRenew.setState(1);
					pactRenew.setCheckDate(null);
					pactRenew.setCheckPerson(null);
					pactRenewManager.save(pactRenew);
				}
				gridOperationMessage = this.getText("销审成功。");
			}else if(oper.equals("confirm")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pactRenew = pactRenewManager.get(removeId);
					pactRenew.setState(3);
					pactRenewManager.confirmRenew(null,pactRenew,date,person);
//					pactRenewManager.save(pactRenew);
				}
				gridOperationMessage = this.getText("续签成功。");
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkPactRenewGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (pactRenew == null) {
			return "Invalid pactRenew Data";
		}
		return SUCCESS;
	}
}

