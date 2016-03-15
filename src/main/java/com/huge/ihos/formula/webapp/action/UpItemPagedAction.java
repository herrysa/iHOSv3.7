package com.huge.ihos.formula.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import com.huge.ihos.formula.model.UpItem;
import com.huge.ihos.formula.service.UpItemManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class UpItemPagedAction extends JqGridBaseAction {

	private UpItemManager upItemManager;
	private List<UpItem> upItems;
	private UpItem upItem;
	private String herpType;
	/*private Map<String, String> sbDeptMap;
	private Map<String, String> auditDeptMap;*/
	private Set<Department> sbDeptMap;
	private Set<Department> auditDeptMap;

	@Override
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String upItemList() {
		List<UpItem> upItems = this.upItemManager.getAll();
		/*sbDeptMap = new HashMap<String, String>();
		auditDeptMap = new HashMap<String, String>();*/
		sbDeptMap = new TreeSet<Department>();
		auditDeptMap = new TreeSet<Department>();
		for(UpItem upItem : upItems) {
			Department sbDept = upItem.getSbdeptId();
			/*if(sbDept != null) {
				sbDeptMap.put(sbDept.getDepartmentId(),sbDept.getName());
			}*/
			if(sbDept != null) {
				sbDept.getDeptCode();
				sbDeptMap.add(sbDept);
			}
			Department auditDept = upItem.getAuditDeptId();
			if(auditDept != null) {
				auditDept.getDeptCode();
				auditDeptMap.add(auditDept);
				//auditDeptMap.put(auditDept.getDepartmentId(), auditDept.getName());
			}
		}
		
		return SUCCESS;
	}

	public String upItemGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = upItemManager.getUpItemCriteria(pagedRequests, filters);
			this.upItems = (List<UpItem>) pagedRequests.getList();
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
			if (OtherUtil.measureNull(upItem.getSbdeptId().getDepartmentId())) {
				upItem.setSbdeptId(null);
			}
			if (OtherUtil.measureNull(upItem.getAuditDeptId().getDepartmentId())) {
				upItem.setAuditDeptId(null);
			}
			if (OtherUtil.measureNull(upItem.getCostItemId().getCostItemId())) {
				upItem.setCostItemId(null);
			}
			if (OtherUtil.measureNull(upItem.getSbPersonId().getPersonId())) {
				upItem.setSbPersonId(null);
			}
			if (upItem.getItemClass().equals("本科室") || upItem.getItemClass().equals("")) {
				upItem.setSbdeptId(null);
			}
			upItemManager.save(upItem);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "upItem.added" : "upItem.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		this.herpType = ContextUtil.herpType;
		if (id != null) {
			upItem = upItemManager.get(Long.parseLong(id));
			this.setEntityIsNew(false);
		} else {
			upItem = new UpItem();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String upItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong((ids.nextToken()));
					if (upItemManager.validataUpCost(removeId)) {
						return ajaxForward(false, "删除失败，该字典项已被引用！", false);
					}
					log.debug("Delete Customer " + removeId);
					UpItem upItem = upItemManager.get(removeId);
					upItemManager.remove(new Long(removeId));

				}
				gridOperationMessage = this.getText("upItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkUpItemGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, gridOperationMessage, false);
		}
	}

	private String isValid() {
		if (upItem == null) {
			return "Invalid upItem Data";
		}

		return SUCCESS;

	}

	public UpItemManager getUpItemManager() {
		return upItemManager;
	}

	public void setUpItemManager(UpItemManager upItemManager) {
		this.upItemManager = upItemManager;
	}

	public List<UpItem> getupItems() {
		return upItems;
	}

	public void setUpItems(List<UpItem> upItems) {
		this.upItems = upItems;
	}

	public UpItem getUpItem() {
		return upItem;
	}

	public void setUpItem(UpItem upItem) {
		this.upItem = upItem;
	}

	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

	public Set<Department> getSbDeptMap() {
		return sbDeptMap;
	}

	public void setSbDeptMap(Set<Department> sbDeptMap) {
		this.sbDeptMap = sbDeptMap;
	}

	public Set<Department> getAuditDeptMap() {
		return auditDeptMap;
	}

	public void setAuditDeptMap(Set<Department> auditDeptMap) {
		this.auditDeptMap = auditDeptMap;
	}


	
}
