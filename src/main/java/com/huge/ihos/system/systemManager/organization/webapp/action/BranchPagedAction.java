package com.huge.ihos.system.systemManager.organization.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.cxf.common.util.StringUtils;

import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.DeptTreeNode;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class BranchPagedAction extends JqGridBaseAction implements Preparable {

	private BranchManager branchManager;
	private List<Branch> branches;
	private Branch branch;
	private String branchCode;
	private List<DeptTreeNode> ztreeList = new ArrayList<DeptTreeNode>();
	private OrgManager orgManager;
	private Boolean canBeAdded;
	

	public Boolean getCanBeAdded() {
		return canBeAdded;
	}

	public void setCanBeAdded(Boolean canBeAdded) {
		this.canBeAdded = canBeAdded;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public BranchManager getBranchManager() {
		return branchManager;
	}

	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}

	public List<Branch> getbranches() {
		return branches;
	}

	public void setBranchs(List<Branch> branches) {
		this.branches = branches;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public List<DeptTreeNode> getZtreeList() {
		return ztreeList;
	}

	public void setZtreeList(List<DeptTreeNode> ztreeList) {
		this.ztreeList = ztreeList;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String branchGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = branchManager.getBranchCriteria(pagedRequests, filters);
			this.branches = (List<Branch>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
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
			branchManager.save(branch);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "branch.added" : "branch.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (branchCode != null) {
			branch = branchManager.get(branchCode);
			this.setEntityIsNew(false);
		} else {
			branch = new Branch();
			String orgCode = getRequest().getParameter("orgCode");
			branch.setOrgCode(orgCode);
			;
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String branchGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//Branch branch = branchManager.get(removeId);
					branchManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("branch.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBranchGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String makeBranchTree() {
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/1_close.png";
		try {
			DeptTreeNode ztree = new DeptTreeNode();
			ztree.setId("-1");
			ztree.setOpen(true);
			ztree.setName("组织结构");
			ztree.setIcon(iconPath);
			ztree.setActionUrl("0");
			ztree.setSubSysTem("ALL");
			ztree.setParent(true);
			ztreeList.add(ztree);
			List<Org> orgs = this.orgManager.getAllAvailable();
			if (orgs != null && !orgs.isEmpty()) {
				for (Org org : orgs) {
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("NES_branchCode","XT"));
					filters.add(new PropertyFilter("EQB_disabled","0"));
					filters.add(new PropertyFilter("EQS_orgCode",org.getOrgCode()));
					List<Branch> branchList = branchManager.getByFilters(filters);
					if(branchList != null && !branchList.isEmpty()) {
						for(Branch branch : branchList) {
							DeptTreeNode znode = new DeptTreeNode();
							znode.setId(branch.getBranchCode());
							znode.setName(branch.getBranchName());
							znode.setIcon(iconPath);
							znode.setpId(org.getOrgCode());
							znode.setActionUrl(branch.getDisabled() ? "1" : "0");
							znode.setSubSysTem("BRANCH");
							znode.setNameWithoutPerson(branch.getBranchName());
							znode.setCode(branch.getBranchCode());
							ztreeList.add(znode);
						}
					}
					
					DeptTreeNode zNode = new DeptTreeNode();
					zNode.setId(org.getOrgCode());
					zNode.setParent(true);
					zNode.setOpen(true);
					if (org.getParentOrgCode() == null || StringUtils.isEmpty(org.getParentOrgCode().getOrgCode())) {
						zNode.setpId("-1");
					} else {
						zNode.setpId(org.getParentOrgCode().getOrgCode());
					}
					zNode.setActionUrl(org.getDisabled() ? "1" : "0");
					zNode.setSubSysTem("ORG");
					zNode.setIcon(iconPath);
					zNode.setNameWithoutPerson(org.getOrgname());
					zNode.setCode(org.getOrgCode());
					ztreeList.add(zNode);
				}
			}
			

		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}

	public String checkBranchCanBeAdded() {
		this.canBeAdded = true;
		String herpType = ContextUtil.herpType;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("NES_branchCode","XT"));
		List<Branch> branches = this.branchManager.getByFilters(filters);
		int num = 0;
		if(branches != null) {
			 num = branches.size();
		}
		if("S1".equals(herpType) && num >= 1) {
			this.canBeAdded = false;
		}
		return SUCCESS;
	}
	
	private String isValid() {
		if (branch == null) {
			return "Invalid branch Data";
		}

		return SUCCESS;

	}
	
}
