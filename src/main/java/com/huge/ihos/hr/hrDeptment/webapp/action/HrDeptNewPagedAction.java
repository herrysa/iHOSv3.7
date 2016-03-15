package com.huge.ihos.hr.hrDeptment.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptNew;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.HrDeptNewManager;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class HrDeptNewPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 110000840027577269L;
	private HrDeptNewManager hrDeptNewManager;
	private List<HrDeptNew> hrDeptNews;
	private HrDeptNew hrDeptNew;
	private String id;
	public String deptNeedCheck;
	private String parentDeptId;
	private List<DeptType> deptTypeList;
	private List<String> deptKindList;
	private List<String> deptAttrList;
	private List<String> deptSubClassList;
	private List<String> deptOutinList;
	private List<KhDeptType> jjDeptTypeList;
	private DeptTypeManager deptTypeManager;
	private KhDeptTypeManager khDeptTypeManager;
	private String orgCode;
	private HrOrgManager hrOrgManager;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private HrDepartmentSnapManager hrDepartmentSnapManager;
	private List<HrDeptTreeNode> deptTreeNodes ;
	private HrDeptTreeNode deptNode;
	public HrDeptTreeNode getDeptNode() {
		return deptNode;
	}

	public void setDeptNode(HrDeptTreeNode deptNode) {
		this.deptNode = deptNode;
	}

	public List<HrDeptTreeNode> getDeptTreeNodes() {
		return deptTreeNodes;
	}

	public void setDeptTreeNodes(List<HrDeptTreeNode> deptTreeNodes) {
		this.deptTreeNodes = deptTreeNodes;
	}

	public HrDeptNewManager getHrDeptNewManager() {
		return hrDeptNewManager;
	}

	public void setHrDeptNewManager(HrDeptNewManager hrDeptNewManager) {
		this.hrDeptNewManager = hrDeptNewManager;
	}

	public List<HrDeptNew> gethrDeptNews() {
		return hrDeptNews;
	}

	public void setHrDeptNews(List<HrDeptNew> hrDeptNews) {
		this.hrDeptNews = hrDeptNews;
	}

	public HrDeptNew getHrDeptNew() {
		return hrDeptNew;
	}

	public void setHrDeptNew(HrDeptNew hrDeptNew) {
		this.hrDeptNew = hrDeptNew;
	}

	public String getId() {
		return id;
	}

	public String getDeptNeedCheck() {
		return deptNeedCheck;
	}

	public void setId(String id) {
        this.id = id;
    }
	public String getParentDeptId() {
		return parentDeptId;
	}
	
	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}
    public List<KhDeptType> getJjDeptTypeList() {
		return jjDeptTypeList;
	}

	public List<String> getDeptOutinList() {
		return deptOutinList;
	}

	public List<String> getDeptSubClassList() {
		return deptSubClassList;
	}

	public List<String> getDeptAttrList() {
		return deptAttrList;
	}

	public List<String> getDeptKindList() {
		return deptKindList;
	}

	public List<DeptType> getDeptTypeList() {
		return deptTypeList;
	}
	
	public void setKhDeptTypeManager(KhDeptTypeManager khDeptTypeManager) {
		this.khDeptTypeManager = khDeptTypeManager;
	}

	public void setDeptTypeManager(DeptTypeManager deptTypeManager) {
		this.deptTypeManager = deptTypeManager;
	}
	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
	
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	@SuppressWarnings("unchecked")
	public String hrDeptNewList(){
		try {
			deptTypeList = deptTypeManager.getAllExceptDisable();
			deptKindList = this.getDictionaryItemManager().getAllItemsByCode("deptKind");
			deptAttrList = this.getDictionaryItemManager().getAllItemsByCode("deptAttr");
			deptSubClassList = this.getDictionaryItemManager().getAllItemsByCode("subClass");
			deptOutinList = this.getDictionaryItemManager().getAllItemsByCode("outin");
			jjDeptTypeList = khDeptTypeManager.getAllExceptDisable();
			deptNeedCheck = this.globalParamManager.getGlobalParamByKey("deptNeedCheck");
			List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			if("0".equals(deptNeedCheck)){
				List<String> checkIds = new ArrayList<String>();
				checkIds.add("100102030104");
				checkIds.add("100102030105");
				checkIds.add("100102030106");
				while(ite.hasNext()){
					MenuButton button = ite.next();
					if(checkIds.contains(button.getId())){
						ite.remove();
					}
				}
			}
			//this.getLoginPeriod()
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrDeptNewList error:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String hrDeptNewGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
//			String orgCodes = hrOrgManager.getAllAvailableString();
//			if(orgCodes==null){
//				orgCodes = "";
//			}
			String showDisabled = request.getParameter("showDisabled");
			boolean showDisableDept = false;
			if(OtherUtil.measureNotNull(showDisabled)){
				showDisableDept = true;
			}
//			filters.add(new PropertyFilter("INS_orgCode",orgCodes));
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String[] results = hrDepartmentCurrentManager.getAllDeptCodes(orgCode, deptId,showDisableDept);
			String deptCodes = results[1];
			String orgCodes = results[0];
			filters.add(new PropertyFilter("INS_orgCode",orgCodes));
			filters.add(new PropertyFilter("INS_deptCode",deptCodes));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = hrDeptNewManager
					.getHrDeptNewCriteria(pagedRequests,filters);
			this.hrDeptNews = (List<HrDeptNew>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
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
			HrDepartmentCurrent hdc=null;
			/*
			 * 检查锁
			 */
			if(this.isEntityIsNew()){
				String pDeptId= hrDeptNew.getParentDept().getDepartmentId();
				if(OtherUtil.measureNotNull(pDeptId)){
					String[] checkLockStates={HrBusinessCode.DEPT_RESCIND,HrBusinessCode.DEPT_MERGE,HrBusinessCode.DEPT_TRANSFER};
					String mesStr=hrDepartmentCurrentManager.checkLockHrDepartmentCurrent(pDeptId, checkLockStates);
					if(mesStr!=null){
						mesStr=HrUtil.parseLockState(mesStr);
						String deptName = hdc.getName();
						return ajaxForwardError("部门["+deptName+"]正在"+mesStr);
					}
				}
			}
			if(this.isEntityIsNew()){
				/*
				 * 部门简称不填时默认为全称，部门内部编码不填时默认为部门编码
				 */
				if(OtherUtil.measureNull(hrDeptNew.getShortnName())){
					hrDeptNew.setShortnName(hrDeptNew.getName());
				}
				if(OtherUtil.measureNull(hrDeptNew.getInternalCode())){
					hrDeptNew.setInternalCode(hrDeptNew.getDeptCode());
				}
			}
			String jjdeptId=hrDeptNew.getJjDept().getDepartmentId();
			if(OtherUtil.measureNull(jjdeptId)){// 如果没有设置奖金部门，则设置为空
				hrDeptNew.setJjDept(null);
				hrDeptNew.setJjDeptSnapCode(null);
			}else{// 如果设置了，则给其奖金部门的snapCode赋值
				hrDeptNew.setJjDeptSnapCode(hrDepartmentCurrentManager.get(jjdeptId).getSnapCode());
			}
			
			String ysDeptId=hrDeptNew.getYsDept().getDepartmentId();
			if(OtherUtil.measureNull(ysDeptId)){// 如果没有设置奖金部门，则设置为空
				hrDeptNew.setYsDept(null);
				hrDeptNew.setYsDeptSnapCode(null);
			}else{// 如果设置了，则给其奖金部门的snapCode赋值
				hrDeptNew.setYsDeptSnapCode(hrDepartmentCurrentManager.get(ysDeptId).getSnapCode());
			}
			parentDeptId=hrDeptNew.getParentDept().getDepartmentId();
			if(OtherUtil.measureNull(parentDeptId)){
				hrDeptNew.setParentDept(null);
				hrDeptNew.setParentDeptSnapCode(null);
			}else{
				hrDeptNew.setParentDeptSnapCode(hrDepartmentCurrentManager.get(parentDeptId).getSnapCode());
			}
			if(OtherUtil.measureNull(hrDeptNew.getCheckPerson().getPersonId())){
				hrDeptNew.setCheckPerson(null);
			}
			if(OtherUtil.measureNull(hrDeptNew.getConfirmPerson().getPersonId())){
				hrDeptNew.setConfirmPerson(null);
			}
			if(hrDeptNew.getState()==3){
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				//hrDeptNew = hrDeptNewManager.saveAndConfirm(hrDeptNew,this.getSessionUser().getPerson(),this.getOperTime(),ansycData);
				hrDeptNew = hrDeptNewManager.saveAndConfirm(hrDeptNew,this.getSessionUser().getPerson(),new Date(),ansycData);
				HrDepartmentSnap hrDepartmentSnap = hrDepartmentSnapManager.get(hrDeptNew.getRemark());
				String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
				deptNode = new HrDeptTreeNode();
				deptNode.setCode(hrDepartmentSnap.getDeptCode());
				deptNode.setId(hrDepartmentSnap.getDeptId());
				deptNode.setSnapCode(hrDepartmentSnap.getSnapCode());
				deptNode.setState(hrDepartmentSnap.getState()>=3?"real":"temp");
				deptNode.setName(hrDepartmentSnap.getName());
				deptNode.setNameWithoutPerson(hrDepartmentSnap.getName());
				deptNode.setpId(hrDepartmentSnap.getParentDeptId()==null?hrDepartmentSnap.getOrgCode():hrDepartmentSnap.getParentDeptId());
				deptNode.setIsParent(!hrDepartmentSnap.getLeaf());
				deptNode.setSubSysTem("DEPT");
				deptNode.setActionUrl(hrDepartmentSnap.getDisabled()?"1":"0");
				deptNode.setIcon(iconPath+"dept.gif");
				deptNode.setPersonCount("0");
				deptNode.setPersonCountD("0");
				deptNode.setPersonCountP("0");
				deptNode.setPersonCountDP("0");
				deptNode.setDisplayOrder(3);
				HrUtil.computePersonCountTask(hrDepartmentCurrentManager,hrDepartmentSnap.getParentDeptId());
			}else{
				hrDeptNew = hrDeptNewManager.save(hrDeptNew);
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "hrDeptNew.added" : "hrDeptNew.updated";
		if(hrDeptNew.getState()==3){
			return ajaxForward("部门新增成功。");
		}
		return ajaxForward(this.getText(key));
	}
	
	@SuppressWarnings("unchecked")
    public String edit() {
		deptNeedCheck = this.globalParamManager.getGlobalParamByKey("deptNeedCheck");
    	deptTypeList = deptTypeManager.getAllExceptDisable();
		deptKindList = this.getDictionaryItemManager().getAllItemsByCode("deptKind");
		deptAttrList = this.getDictionaryItemManager().getAllItemsByCode("deptAttr");
		deptSubClassList = this.getDictionaryItemManager().getAllItemsByCode("subClass");
		deptOutinList = this.getDictionaryItemManager().getAllItemsByCode("outin");
		jjDeptTypeList = khDeptTypeManager.getAllExceptDisable();
        if (id != null) {
        	hrDeptNew = hrDeptNewManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	hrDeptNew = new HrDeptNew();
        	hrDeptNew.setState(1);
        	if(parentDeptId!=null){
        		HrDepartmentCurrent hrDeptCur = hrDepartmentCurrentManager.get(parentDeptId);
        		orgCode = hrDeptCur.getOrgCode();
        		hrDeptNew.setParentDept(hrDeptCur);
        		hrDeptNew.setParentDeptSnapCode(hrDeptCur.getSnapCode());
        		HrDepartmentHis deptHis = new HrDepartmentHis();
        		deptHis.setName(hrDeptCur.getName());
        		hrDeptNew.setParentDeptHis(deptHis);
        		hrDeptNew.setClevel(hrDeptCur.getClevel()+1);
        	}
        	if(orgCode!=null){
        		hrDeptNew.setOrgCode(orgCode);
        		HrOrg hrOrg = hrOrgManager.get(orgCode);
        		hrDeptNew.setOrgSnapCode(hrOrg.getSnapCode());
        		hrDeptNew.setHrOrg(hrOrg);
        	}
        	String deptNeedCheck = this.globalParamManager.getGlobalParamByKey("deptNeedCheck");
        	String addFrom = this.getRequest().getParameter("addFrom");
        	//Date operDate = this.getOperTime();
        	Date operDate = new Date();
        	Person operPerson = this.getSessionUser().getPerson();
        	
        	hrDeptNew.setMakeDate(operDate);
        	hrDeptNew.setMakePerson(operPerson);
        	hrDeptNew.setYearMonth(this.getLoginPeriod());
        	if(OtherUtil.measureNotNull(addFrom) || "0".equals(deptNeedCheck)){
        		hrDeptNew.setState(3);
        		hrDeptNew.setCheckDate(operDate);
        		hrDeptNew.setCheckPerson(operPerson);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String hrDeptNewGridEdit() {
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
				this.hrDeptNewManager.deleteHrDeptNew(idl);
				gridOperationMessage = this.getText("删除成功。");
				
			}else if(oper.equals("check")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				this.hrDeptNewManager.auditHrDeptNew(idl, person, date);
				gridOperationMessage = this.getText("审核成功。");
			}else if(oper.equals("cancelCheck")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				this.hrDeptNewManager.antiHrDeptNew(idl);
				gridOperationMessage = this.getText("销审成功。");
			}else if(oper.equals("confirm")){
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				String errMessage = "";
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					hrDeptNew = hrDeptNewManager.get(removeId);
					if(OtherUtil.measureNotNull(hrDeptNew.getParentDept())&&hrDeptNew.getParentDept().getDisabled()){
						errMessage += hrDeptNew.getName()+"的父级部门"+hrDeptNew.getParentDept().getName()+"已停用。";
					}
					idl.add(removeId);
				}
				if(OtherUtil.measureNotNull(errMessage)){
					return ajaxForwardError(errMessage);
				}
				this.hrDeptNewManager.doneHrDeptNew(idl, person, date, ansycData);
				deptTreeNodes = new ArrayList<HrDeptTreeNode>();
				for(String doneId:idl){
		    		HrDeptNew hrDeptNew = hrDeptNewManager.get(doneId);
					String deptId = hrDeptNew.getOrgCode()+"_" + hrDeptNew.getDeptCode();
					HrDepartmentCurrent hrDepartment = hrDepartmentCurrentManager.get(deptId);
					String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
					HrDeptTreeNode deptNode = new HrDeptTreeNode();
					deptNode.setCode(hrDepartment.getDeptCode());
					deptNode.setId(hrDepartment.getDepartmentId());
					deptNode.setSnapCode(hrDepartment.getSnapCode());
					deptNode.setState(hrDepartment.getState()>=3?"real":"temp");
					deptNode.setName(hrDepartment.getName());
					deptNode.setNameWithoutPerson(hrDepartment.getName());
					deptNode.setpId(hrDepartment.getParentDept()==null?hrDepartment.getOrgCode():hrDepartment.getParentDept().getDepartmentId());
					deptNode.setIsParent(!hrDepartment.getLeaf());
					deptNode.setSubSysTem("DEPT");
					deptNode.setActionUrl(hrDepartment.getDisabled()?"1":"0");
					deptNode.setIcon(iconPath+"dept.gif");
					deptNode.setPersonCount("0");
					deptNode.setPersonCountD("0");
					deptNode.setPersonCountDP("0");
					deptNode.setPersonCountP("0");
					deptNode.setDisplayOrder(3);
					deptTreeNodes.add(deptNode);
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,deptId);
				}
				gridOperationMessage = this.getText("执行成功。");
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkHrDeptNewGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private String isValid() {
		if (hrDeptNew == null) {
			return "Invalid hrDeptNew Data";
		}
		return SUCCESS;
	}

	public HrDepartmentSnapManager getHrDepartmentSnapManager() {
		return hrDepartmentSnapManager;
	}

	public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}
}

