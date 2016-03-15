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
import com.huge.ihos.hr.pact.model.PactRelieve;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.pact.service.PactRelieveManager;
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
public class PactRelievePagedAction extends JqGridBaseAction implements Preparable {

	private PactRelieveManager pactRelieveManager;
	private List<PactRelieve> pactRelieves;
	private PactRelieve pactRelieve;

	public void setPactRelieveManager(PactRelieveManager pactRelieveManager) {
		this.pactRelieveManager = pactRelieveManager;
	}

	public List<PactRelieve> getPactRelieves() {
		return pactRelieves;
	}

	public PactRelieve getPactRelieve() {
		return pactRelieve;
	}

	public void setPactRelieve(PactRelieve pactRelieve) {
		this.pactRelieve = pactRelieve;
	}
	
	private String pactNeedCheck;

	public String getPactNeedCheck() {
		return pactNeedCheck;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String pactRelieveList(){
		try {
			pactNeedCheck = this.globalParamManager.getGlobalParamByKey("pactNeedCheck");
			List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			if("0".equals(pactNeedCheck)){
				List<String> checkIds = new ArrayList<String>();
				checkIds.add("1003020404");
				checkIds.add("1003020405");
				checkIds.add("1003020406");
				while(ite.hasNext()){
					MenuButton button = ite.next();
					if(checkIds.contains(button.getId())){
						ite.remove();
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter pactRelieveList error:",e);
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
	public String pactRelieveGridList() {
		log.debug("enter list method!");
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
			pagedRequests = pactRelieveManager
					.getPactRelieveCriteria(pagedRequests,filters);
			this.pactRelieves = (List<PactRelieve>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
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
			if(OtherUtil.measureNull(pactRelieve.getCheckPerson().getPersonId())){
				pactRelieve.setCheckPerson(null);
			}
			if(OtherUtil.measureNull(pactRelieve.getConfirmPerson().getPersonId())){
				pactRelieve.setConfirmPerson(null);
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
			if(pactRelieve.getState()==3){
				/*
				 * 从合同信息处直接解除
				 */
				//pactRelieveManager.confirmRelieve(pactIds,pactRelieve, this.getOperTime(), this.getSessionUser().getPerson());
				pactRelieveManager.confirmRelieve(pactIds,pactRelieve, new Date(), this.getSessionUser().getPerson());
			}else{
				if(this.isEntityIsNew()){
					pactRelieveManager.addPactRelieve(pactIds,pactRelieve);
				}else{
					pactRelieveManager.save(pactRelieve);
				}
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "pactRelieve.added" : "pactRelieve.updated";
		if(addFrom!=null && addFrom.equals("noCheck")){
			key = "pact.relieve";
		}
		return ajaxForward(this.getText(key));
	}
	private PactManager pactManager;
	private String relieveNames;
	private String pactIds;
	
    public String getPactIds() {
		return pactIds;
	}

	public void setPactIds(String pactIds) {
		this.pactIds = pactIds;
	}

	public String getRelieveNames() {
		return relieveNames;
	}

	public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}
    public String edit() {
    	pactNeedCheck = this.globalParamManager.getGlobalParamByKey("pactNeedCheck");
        if (id != null) {
        	pactRelieve = pactRelieveManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        	filters.add(new PropertyFilter("INS_id",pactIds));
        	List<Pact> pacts = pactManager.getByFilters(filters);
        	relieveNames = "";
        	for(Pact pact:pacts){
        		relieveNames += pact.getHrPerson().getName()+",";
        	}
        	relieveNames = OtherUtil.subStrEnd(relieveNames, ",");
        	pactRelieve = new PactRelieve();
        	pactRelieve.setState(1);
        	//Date date = this.getOperTime();
        	Date date = new Date();
        	Person person = this.getSessionUser().getPerson();
        	pactRelieve.setRelieveDate(date);
        	pactRelieve.setMakeDate(date);
        	pactRelieve.setMakePerson(person);
        	pactRelieve.setYearMonth(this.getLoginPeriod());
        	/*
        	 * addFrom不为空表示从合同信息处解除
        	 * pactNeedCheck=0 表示无需走审核流程
        	 */
        	if(OtherUtil.measureNotNull(addFrom) || "0".equals(pactNeedCheck)){
        		pactRelieve.setState(3);
        		pactRelieve.setCheckDate(date);
        		pactRelieve.setCheckPerson(person);
        	}
        	this.setEntityIsNew(true);
        }
        this.setRandom(this.getRequest().getParameter("random"));
        return SUCCESS;
    }
	public String pactRelieveGridEdit() {
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
				this.pactRelieveManager.remove(ida);
				gridOperationMessage = this.getText("pactRelieve.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pactRelieve = pactRelieveManager.get(removeId);
					pactRelieve.setState(2);
					pactRelieve.setCheckDate(date);
					pactRelieve.setCheckPerson(person);
					pactRelieveManager.save(pactRelieve);
				}
				gridOperationMessage = this.getText("审核成功。");
			}else if(oper.equals("cancelCheck")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pactRelieve = pactRelieveManager.get(removeId);
					pactRelieve.setState(1);
					pactRelieve.setCheckDate(null);
					pactRelieve.setCheckPerson(null);
					pactRelieveManager.save(pactRelieve);
				}
				gridOperationMessage = this.getText("销审成功。");
			}else if(oper.equals("confirm")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pactRelieve = pactRelieveManager.get(removeId);
					pactRelieve.setState(3);
					//pactRelieveManager.confirmRelieve(null,pactRelieve, this.getOperTime(), this.getSessionUser().getPerson());
					pactRelieveManager.confirmRelieve(null,pactRelieve, new Date(), this.getSessionUser().getPerson());
				}
				gridOperationMessage = this.getText("解除成功。");
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkPactRelieveGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String cascadeUpdatePersonByRelievePact(){
		try {
			StringTokenizer ids = new StringTokenizer(id,",");
			List<String> idList = new ArrayList<String>();
			String idType = this.getRequest().getParameter("idType");
			String removeId = null;
			if(idType!=null && idType.equals("relieve")){
				while (ids.hasMoreTokens()) {
					removeId = ids.nextToken();
					pactRelieve = pactRelieveManager.get(removeId);
					idList.add(pactRelieve.getPact().getId());
				}
			}else{
				while (ids.hasMoreTokens()) {
					removeId = ids.nextToken();
					idList.add(removeId);
				}
			}
			String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
			boolean ansycData = "1".equals(ansyOrgDeptPerson);
			//this.pactManager.updatePersonByPactChange(idList, this.getOperTime(), this.getSessionUser().getPerson(), "解除合同",ansycData);
			this.pactManager.updatePersonByPactChange(idList, new Date(), this.getSessionUser().getPerson(), "解除合同",ansycData);
			for(String pactId:idList){
				Pact pact = pactManager.get(pactId);
				HrUtil.computePersonCountTask(hrDepartmentCurrentManager,pact.getHrPerson().getDepartment().getDepartmentId());
			}
			return ajaxForward(true, "修改人员状态为离职成功。", false);
		} catch (Exception e) {
			log.error("cascadeUpdatePersonByRelievePact error:", e);
			return ajaxForward(false, "修改人员状态为离职失败。", false);
		}
	}

	private String isValid() {
		if (pactRelieve == null) {
			return "Invalid pactRelieve Data";
		}
		return SUCCESS;
	}
}

