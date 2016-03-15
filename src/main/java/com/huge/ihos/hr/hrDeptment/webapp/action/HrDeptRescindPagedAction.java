package com.huge.ihos.hr.hrDeptment.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.HrDeptRescind;
import com.huge.ihos.hr.hrDeptment.model.HrDeptSnapPk;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentHisManager;
import com.huge.ihos.hr.hrDeptment.service.HrDeptRescindManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
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
public class HrDeptRescindPagedAction extends JqGridBaseAction implements Preparable {

	private HrDeptRescindManager hrDeptRescindManager;
	private List<HrDeptRescind> hrDeptRescinds;
	private HrDeptRescind hrDeptRescind;
	private String id;

	public void setHrDeptRescindManager(HrDeptRescindManager hrDeptRescindManager) {
		this.hrDeptRescindManager = hrDeptRescindManager;
	}

	public List<HrDeptRescind> getHrDeptRescinds() {
		return hrDeptRescinds;
	}

	public HrDeptRescind getHrDeptRescind() {
		return hrDeptRescind;
	}

	public void setHrDeptRescind(HrDeptRescind hrDeptRescind) {
		this.hrDeptRescind = hrDeptRescind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }
	public String deptNeedCheck;

	public String getDeptNeedCheck() {
		return deptNeedCheck;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	public String hrDeptRescindList(){
		try {
			deptNeedCheck = this.globalParamManager.getGlobalParamByKey("deptNeedCheck");
			List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			if("0".equals(deptNeedCheck)){
				List<String> checkIds = new ArrayList<String>();
				checkIds.add("100102030204");
				checkIds.add("100102030205");
				checkIds.add("100102030206");
				while(ite.hasNext()){
					MenuButton button = ite.next();
					if(checkIds.contains(button.getId())){
						ite.remove();
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrDeptRescindList error:", e);
		}
		return SUCCESS;
	}
	
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private HrOrgManager hrOrgManager;
	private HrDepartmentHisManager hrDepartmentHisManager;
	
	public void setHrDepartmentHisManager(HrDepartmentHisManager hrDepartmentHisManager) {
		this.hrDepartmentHisManager = hrDepartmentHisManager;
	}

	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	@SuppressWarnings("unchecked")
	public String hrDeptRescindGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			String orgCodes = hrOrgManager.getAllAvailableString();
			if(orgCodes==null){
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_hrDept.orgCode",orgCodes));
			
			String showDisabled = this.getRequest().getParameter("showDisabled");
			if(OtherUtil.measureNull(showDisabled)){
				filters.add(new PropertyFilter("EQB_hrDept.disabled","0"));
			}
			String orgCode = this.getRequest().getParameter("orgCode");
			String deptId = this.getRequest().getParameter("departmentId");
			String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode, deptId);
			if(deptIds!=null){
				filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = hrDeptRescindManager
					.getHrDeptRescindCriteria(pagedRequests,filters);
			this.hrDeptRescinds = (List<HrDeptRescind>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("hrDeptRescindGridList Error", e);
		}
		return SUCCESS;
	}
	
	private List<String> getLockDeptIds(HrDeptRescind hrDeptRescind){
		List<String> fDeptIds = new ArrayList<String>();
		String fromDeptId = hrDeptRescind.getHrDept().getDepartmentId();
		fDeptIds.add(fromDeptId);
		HrDepartmentCurrent fromDept = hrDepartmentCurrentManager.get(fromDeptId);
		if(!fromDept.getLeaf()){
			List<HrDepartmentCurrent> deptList = hrDepartmentCurrentManager.getAllDescendants(fromDeptId);
			for(HrDepartmentCurrent dept:deptList){
				fDeptIds.add(dept.getDepartmentId());
			}
		}
		return fDeptIds;
	}
	
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			/*
			 * 检查锁
			 */
			if(this.isEntityIsNew()){
				String[] checkLockStates={HrBusinessCode.DEPT_NEW,HrBusinessCode.DEPT_RESCIND,HrBusinessCode.DEPT_MERGE,
						HrBusinessCode.DEPT_MERGE_TO,HrBusinessCode.DEPT_TRANSFER,HrBusinessCode.DEPT_TRANSFER_TO,
						HrBusinessCode.PERSON_ENTRY,HrBusinessCode.PERSON_MOVE,HrBusinessCode.PERSON_MOVE_POST};
				List<String> fDeptIds = getLockDeptIds(hrDeptRescind);
				for(String fDeptId:fDeptIds){
					String mesStr=hrDepartmentCurrentManager.checkLockHrDepartmentCurrent(fDeptId, checkLockStates);
					if(mesStr!=null){
						mesStr=HrUtil.parseLockState(mesStr);
						String deptName = hrDepartmentCurrentManager.get(fDeptId).getName();
						return ajaxForwardError("部门["+deptName+"]正在"+mesStr);
					}
				}
			}
			if(OtherUtil.measureNull(hrDeptRescind.getCheckPerson().getPersonId())){
				hrDeptRescind.setCheckPerson(null);
			}
			if(OtherUtil.measureNull(hrDeptRescind.getConfirmPerson().getPersonId())){
				hrDeptRescind.setConfirmPerson(null);
			}
			String moveToDeptId = hrDeptRescind.getMoveToDept().getDepartmentId();
			HrDepartmentCurrent moveToDept = this.hrDepartmentCurrentManager.get(moveToDeptId);
			hrDeptRescind.setMoveToSnapCode(moveToDept.getSnapCode());
			if(hrDeptRescind.getState()==3){
				HrDepartmentHis deptHis = hrDepartmentHisManager.get(new HrDeptSnapPk(hrDeptRescind.getHrDept().getDepartmentId(),hrDeptRescind.getSnapCode()));
				hrDeptRescind.setHrDeptHis(deptHis);
				deptHis = hrDepartmentHisManager.get(new HrDeptSnapPk(moveToDept.getDepartmentId(),moveToDept.getSnapCode()));
				hrDeptRescind.setMoveToDeptHis(deptHis);
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				/*树节点变化*/
				deptTreeNodes = new ArrayList<HrDeptTreeNode>();
				deptEditTreeNodes = new ArrayList<HrDeptTreeNode>();
				String delDeptId = hrDeptRescind.getHrDept().getDepartmentId();
				String editDeptId = hrDeptRescind.getMoveToDept().getDepartmentId();
				HrDepartmentCurrent delHdc = hrDepartmentCurrentManager.get(delDeptId);
				HrDepartmentCurrent editHdc = hrDepartmentCurrentManager.get(editDeptId);
				HrDeptTreeNode deptTreeNode = new HrDeptTreeNode(delHdc, this.getContextPath());
				deptTreeNodes.add(deptTreeNode);
				HrDeptTreeNode deptEditTreeNode = new HrDeptTreeNode(editHdc, this.getContextPath());
				int pc = Integer.parseInt(deptTreeNode.getPersonCount()) + Integer.parseInt(deptEditTreeNode.getPersonCount());
				deptEditTreeNode.setPersonCount(""+pc);
				int pcD = Integer.parseInt(deptTreeNode.getPersonCountD()) + Integer.parseInt(deptEditTreeNode.getPersonCountD());
				deptEditTreeNode.setPersonCountD(""+pcD);
				int pcP = Integer.parseInt(deptTreeNode.getPersonCountP()) + Integer.parseInt(deptEditTreeNode.getPersonCountP());
				deptEditTreeNode.setPersonCountP(""+pcP);
				int pcDP = Integer.parseInt(deptTreeNode.getPersonCountDP()) + Integer.parseInt(deptEditTreeNode.getPersonCountDP());
				deptEditTreeNode.setPersonCountDP(""+pcDP);
				deptEditTreeNodes.add(deptEditTreeNode);
				hrDeptRescind = hrDeptRescindManager.saveAndConfirm(hrDeptRescind,this.getSessionUser().getPerson(),new Date(),ansycData);
				HrUtil.computePersonCountTask(hrDepartmentCurrentManager,delDeptId);
				//hrDeptRescind = hrDeptRescindManager.saveAndConfirm(hrDeptRescind,this.getSessionUser().getPerson(),this.getOperTime(),ansycData);
			}else{
				hrDeptRescind = hrDeptRescindManager.save(hrDeptRescind);
			}
		} catch (Exception dre) {
			log.error("save hrDeptRescind error:",dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "hrDeptRescind.added" : "hrDeptRescind.updated";
		if(hrDeptRescind.getState()==3){
			return ajaxForward("部门撤销成功。");
		}
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		deptNeedCheck = this.globalParamManager.getGlobalParamByKey("deptNeedCheck");
        if (id != null) {
        	hrDeptRescind = hrDeptRescindManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	hrDeptRescind = new HrDeptRescind();
        	String deptId = this.getRequest().getParameter("deptId");
    		HrDepartmentCurrent hdc = hrDepartmentCurrentManager.get(deptId);
    		hrDeptRescind.setHrDept(hdc);
    		hrDeptRescind.setSnapCode(hdc.getSnapCode());
    		//Date operDate = this.getOperTime();
    		Date operDate = new Date();
    		Person operPerson = this.getSessionUser().getPerson();
    		hrDeptRescind.setState(1);
    		hrDeptRescind.setMakeDate(operDate);
    		hrDeptRescind.setRescindPerson(operPerson);
    		hrDeptRescind.setYearMonth(this.getLoginPeriod());
    		String addFrom = this.getRequest().getParameter("addFrom");
    		if("0".equals(deptNeedCheck) || OtherUtil.measureNotNull(addFrom)){
    			hrDeptRescind.setState(3);
    			hrDeptRescind.setCheckDate(operDate);
    			hrDeptRescind.setCheckPerson(operPerson);
    		}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	private List<HrDeptTreeNode> deptTreeNodes ;
	private List<HrDeptTreeNode> deptEditTreeNodes ;

	public List<HrDeptTreeNode> getDeptTreeNodes() {
		return deptTreeNodes;
	}

	public void setDeptTreeNodes(List<HrDeptTreeNode> deptTreeNodes) {
		this.deptTreeNodes = deptTreeNodes;
	}
	public List<HrDeptTreeNode> getDeptEditTreeNodes() {
		return deptEditTreeNodes;
	}

	public void setDeptEditTreeNodes(List<HrDeptTreeNode> deptEditTreeNodes) {
		this.deptEditTreeNodes = deptEditTreeNodes;
	}
	public String hrDeptRescindGridEdit() {
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
				this.hrDeptRescindManager.remove(ida);
				gridOperationMessage = this.getText("hrDeptRescind.deleted");
				
			}else if(oper.equals("check")){
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					idl.add(checkId);
				}
				this.hrDeptRescindManager.auditHrDeptRescind(idl, person, date);
				gridOperationMessage = this.getText("审核成功。");
			}else if(oper.equals("cancelCheck")){
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					idl.add(cancelCheckId);
				}
				this.hrDeptRescindManager.antiHrDeptRescind(idl);
				gridOperationMessage = this.getText("销审成功。");
			}else if(oper.equals("confirm")){
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				deptTreeNodes = new ArrayList<HrDeptTreeNode>();
				deptEditTreeNodes = new ArrayList<HrDeptTreeNode>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					idl.add(cancelCheckId);
					hrDeptRescind = hrDeptRescindManager.get(cancelCheckId);
					String delDeptId = hrDeptRescind.getHrDept().getDepartmentId();
					String editDeptId = hrDeptRescind.getMoveToDept().getDepartmentId();
					HrDepartmentCurrent delHdc = hrDepartmentCurrentManager.get(delDeptId);
					HrDepartmentCurrent editHdc = hrDepartmentCurrentManager.get(editDeptId);
					HrDeptTreeNode deptTreeNode = new HrDeptTreeNode(delHdc, this.getContextPath());
					deptTreeNodes.add(deptTreeNode);
					HrDeptTreeNode deptEditTreeNode = new HrDeptTreeNode(editHdc, this.getContextPath());
					int pc = Integer.parseInt(deptTreeNode.getPersonCount()) + Integer.parseInt(deptEditTreeNode.getPersonCount());
					deptEditTreeNode.setPersonCount(""+pc);
					int pcD = Integer.parseInt(deptTreeNode.getPersonCountD()) + Integer.parseInt(deptEditTreeNode.getPersonCountD());
					deptEditTreeNode.setPersonCountD(""+pcD);
					int pcP = Integer.parseInt(deptTreeNode.getPersonCountP()) + Integer.parseInt(deptEditTreeNode.getPersonCountP());
					deptEditTreeNode.setPersonCountP(""+pcP);
					int pcDP = Integer.parseInt(deptTreeNode.getPersonCountDP()) + Integer.parseInt(deptEditTreeNode.getPersonCountDP());
					deptEditTreeNode.setPersonCountDP(""+pcDP);
					deptEditTreeNodes.add(deptEditTreeNode);
				}
				this.hrDeptRescindManager.doneHrDeptRescind(idl, person, date, ansycData);
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					hrDeptRescind = hrDeptRescindManager.get(cancelCheckId);
					String delDeptId = hrDeptRescind.getHrDept().getDepartmentId();
					String editDeptId = hrDeptRescind.getMoveToDept().getDepartmentId();
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,delDeptId);
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,editDeptId);
				}
				gridOperationMessage = this.getText("撤销成功。");
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkHrDeptRescindGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private String isValid() {
		if (hrDeptRescind == null) {
			return "Invalid hrDeptRescind Data";
		}
		return SUCCESS;
	}
}


