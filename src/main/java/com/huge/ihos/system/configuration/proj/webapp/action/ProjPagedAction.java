package com.huge.ihos.system.configuration.proj.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.huge.ihos.system.configuration.proj.model.Proj;
import com.huge.ihos.system.configuration.proj.model.ProjNature;
import com.huge.ihos.system.configuration.proj.model.ProjType;
import com.huge.ihos.system.configuration.proj.model.ProjUse;
import com.huge.ihos.system.configuration.proj.service.ProjManager;
import com.huge.ihos.system.configuration.proj.service.ProjNatureManager;
import com.huge.ihos.system.configuration.proj.service.ProjTypeManager;
import com.huge.ihos.system.configuration.proj.service.ProjUseManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ProjPagedAction extends JqGridBaseAction implements Preparable {

	private ProjManager projManager;
	private List<Proj> projs;
	private Proj proj;
	private String projId;
	private DepartmentManager departmentManager;
	private ProjTypeManager projTypeManager;
	private ProjUseManager projUseManager;
	private ProjNatureManager projNatureManager;
	private List<Department> departmentList;
	private List<ProjType> projTypeList;
	private List<ProjUse> projUseList;
	private List<ProjNature> projNatureList;

	
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String projGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//filters.add(new PropertyFilter("EQS_orgCode", UserContextUtil.getUserContext().getOrgCode()));
			//filters.add(new PropertyFilter("EQS_copyCode", UserContextUtil.getUserContext().getCopyCode()));
			filters.add(new PropertyFilter("EQS_periodYear",UserContextUtil.getUserContext().getPeriodYear()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = projManager
					.getProjCriteria(pagedRequests,filters);
			this.projs = (List<Proj>) pagedRequests.getList();
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
			String orgCode = UserContextUtil.getUserContext().getOrgCode();
			String copyCode = UserContextUtil.getUserContext().getCopyCode();
			String periodYear = UserContextUtil.getUserContext().getPeriodYear();
			HashMap<String,String> condition = new HashMap<String,String>();
			condition.put("copyCode",copyCode);
			condition.put("orgCode", orgCode);
			condition.put("periodYear", periodYear);
			condition.put("projCode", proj.getProjCode());
			if(isEntityIsNew()){
				Proj projFind = new Proj();
				projFind.setCopyCode(UserContextUtil.getUserContext().getCopyCode());
				projFind.setOrgCode(UserContextUtil.getUserContext().getOrgCode());
				projFind.setProjCode(proj.getProjCode());
				projFind.setPeriodYear(periodYear);
				if(projManager.existByExample(projFind)){
					return ajaxForwardError("对不起, 您输入的项目编码已经存在, 请重新输入");
				}
				String proId = "";
				if(StringUtils.isNotEmpty(orgCode)){
					proId += orgCode+"_";
				}
				if(StringUtils.isNotEmpty(copyCode)){
					proId += copyCode+"_";
				}
				if(StringUtils.isNotEmpty(periodYear)){
					proId += periodYear+"_";
				}
				proId += proj.getProjCode();
				proj.setProjId(proId);
			}
			proj.setCnCode(projManager.pyCode(proj.getProjName()));
			proj.setCopyCode(copyCode);
			proj.setOrgCode(orgCode);
			proj.setPeriodYear(periodYear);
			projManager.save(proj);
			
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "proj.added" : "proj.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (projId != null) {
        	proj = projManager.get(projId);
        	this.setEntityIsNew(false);
        } else {
        	proj = new Proj();
        	this.setEntityIsNew(true);
        }
        projTypeList = projTypeManager.getAllEnabled();
        projUseList = projUseManager.getAllEnabled();
        projNatureList = projNatureManager.getAllEnabled();
        
        return SUCCESS;
    }
	public String projGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					projManager.remove(removeId);
				}
				gridOperationMessage = this.getText("proj.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkProjGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (proj == null) {
			return "Invalid proj Data";
		}

		return SUCCESS;

	}
	
	/**********************************get/set**************************************/
	
	public ProjManager getProjManager() {
		return projManager;
	}

	public void setProjManager(ProjManager projManager) {
		this.projManager = projManager;
	}

	public List<Proj> getprojs() {
		return projs;
	}

	public void setProjs(List<Proj> projs) {
		this.projs = projs;
	}

	public Proj getProj() {
		return proj;
	}

	public void setProj(Proj proj) {
		this.proj = proj;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
        this.projId = projId;
    }
	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	public ProjTypeManager getProjTypeManager() {
		return projTypeManager;
	}
	public void setProjTypeManager(ProjTypeManager projTypeManager) {
		this.projTypeManager = projTypeManager;
	}
	public ProjUseManager getProjUseManager() {
		return projUseManager;
	}
	public void setProjUseManager(ProjUseManager projUseManager) {
		this.projUseManager = projUseManager;
	}
	public ProjNatureManager getProjNatureManager() {
		return projNatureManager;
	}
	public void setProjNatureManager(ProjNatureManager projNatureManager) {
		this.projNatureManager = projNatureManager;
	}
	public List<Department> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}
	public List<ProjType> getProjTypeList() {
		return projTypeList;
	}
	public void setProjTypeList(List<ProjType> projTypeList) {
		this.projTypeList = projTypeList;
	}
	public List<ProjUse> getProjUseList() {
		return projUseList;
	}
	public void setProjUseList(List<ProjUse> projUseList) {
		this.projUseList = projUseList;
	}
	public List<ProjNature> getProjNatureList() {
		return projNatureList;
	}
	public void setProjNatureList(List<ProjNature> projNatureList) {
		this.projNatureList = projNatureList;
	}
	public List<Proj> getProjs() {
		return projs;
	}
	

}

