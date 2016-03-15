package com.huge.ihos.system.systemManager.copy.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.cxf.common.util.StringUtils;

import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.ihos.system.systemManager.copy.service.CopyManager;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.ihos.system.systemManager.period.service.PeriodPlanManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class CopyPagedAction extends JqGridBaseAction implements Preparable {

	private CopyManager copyManager;
	private List<Copy> copies;
	private Copy copy;
	private String copycode;
	private OrgManager orgManager;
	private PeriodPlanManager periodPlanManager;
	private List<PeriodPlan> planList;

	
	public String copyGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = copyManager
					.getCopyCriteria(pagedRequests,filters);
			this.copies = (List<Copy>) pagedRequests.getList();
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
			if(isEntityIsNew()){
				String copyCode = copy.getCopycode();
				if(!copyManager.isNewCopy(copyCode)){
					return ajaxForwardError("您添加的帐套编号已经存在。");
				}
			}
			if(StringUtils.isEmpty(copy.getOrg().getOrgCode())){
				copy.setOrg(null);
			}
			copyManager.save(copy);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "copy.added" : "copy.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
    	//this.setCodeRule(Copy.class);
    	planList = periodPlanManager.getAll();
        if (copycode != null) {
        	copy = copyManager.get(copycode);
        	this.setEntityIsNew(false);
        } else {
        	copy = new Copy();
        	String orgCode = this.getRequest().getParameter("orgCode");
        	Org org = orgManager.get(orgCode);
        	copy.setOrg(org);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String copyGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					Copy copy = copyManager.get(new String(removeId));
					copyManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("copy.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkCopyGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public boolean exists;
	
	public boolean getExists() {
		return exists;
	}
	public String checkCopyCode(){
		String orgCode = this.getRequest().getParameter("orgCode");
		Copy copyFind = new Copy();
		copyFind.getOrg().setOrgCode(orgCode);
		copyFind.setCopycode(copycode);
		copyFind.setBalanceFlag(null);
		// TODO 现有DAO缺少existByExample方法
		//exists =copyManager.existByExample(copyFind);
		return SUCCESS;
	}
	private String isValid() {
		if (copy == null) {
			return "Invalid copy Data";
		} else if(StringUtils.isEmpty(copy.getCopyname())){
			return "帐套名称不能为空";
		}
		return SUCCESS;
	}
	
	
	/***************************get/set**********************/
	
	public CopyManager getCopyManager() {
		return copyManager;
	}

	public void setCopyManager(CopyManager copyManager) {
		this.copyManager = copyManager;
	}

	public List<Copy> getcopies() {
		return copies;
	}

	public void setCopys(List<Copy> copies) {
		this.copies = copies;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	public String getCopycode() {
		return copycode;
	}

	public void setCopycode(String copycode) {
        this.copycode = copycode;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public List<Copy> getCopies() {
		return copies;
	}
	public void setCopies(List<Copy> copies) {
		this.copies = copies;
	}
	public OrgManager getOrgManager() {
		return orgManager;
	}
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	public PeriodPlanManager getPeriodPlanManager() {
		return periodPlanManager;
	}
	public void setPeriodPlanManager(PeriodPlanManager periodPlanManager) {
		this.periodPlanManager = periodPlanManager;
	}
	public List<PeriodPlan> getPlanList() {
		return planList;
	}
	public void setPlanList(List<PeriodPlan> planList) {
		this.planList = planList;
	}
	
	
	
}

