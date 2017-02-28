package com.huge.ihos.material.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InvUse;
import com.huge.ihos.material.service.InvUseManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvUsePagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -4160593975188408445L;
	private InvUseManager invUseManager;
	private List<InvUse> invUses;
	private InvUse invUse;
	private String invUseId;
	private String invUseCode;
	private String invUseName;

	public void setInvUseCode(String invUseCode) {
		this.invUseCode = invUseCode;
	}

	public void setInvUseName(String invUseName) {
		this.invUseName = invUseName;
	}

	public void setInvUseManager(InvUseManager invUseManager) {
		this.invUseManager = invUseManager;
	}

	public List<InvUse> getInvUses() {
		return invUses;
	}

	public void setInvUses(List<InvUse> invUses) {
		this.invUses = invUses;
	}

	public InvUse getInvUse() {
		return invUse;
	}

	public void setInvUse(InvUse invUse) {
		this.invUse = invUse;
	}

	public String getInvUseId() {
		return invUseId;
	}

	public void setInvUseId(String invUseId) {
		this.invUseId = invUseId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	@SuppressWarnings("unchecked")
	public String invUseGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invUseManager.getInvUseCriteria(pagedRequests,
					filters);
			this.invUses = (List<InvUse>) pagedRequests.getList();
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
			invUseManager.save(invUse);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invUse.added"
				: "invUse.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (invUseId != null) {
			invUse = invUseManager.get(invUseId);
			this.setEntityIsNew(false);
		} else {
			invUse = new InvUse();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String invUseGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
//					InvUse invUse = invUseManager.get(new String(removeId));
					invUseManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("invUse.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("edit")) {
				UserContext userContext = UserContextUtil.getUserContext();
				String orgCode = userContext.getOrgCode();
				String copyCode = userContext.getCopyCode();
				if (invUse == null) {
					if (invUseId == null || "".equals(invUseId)) {
						invUseId = orgCode + "_" + copyCode + "_" + invUseCode;
						invUse = new InvUse();
					} else {
						invUse = invUseManager.get(invUseId);
						invUseCode = invUse.getInvUseCode();
					}
					invUse.setInvUseId(invUseId);
					invUse.setOrgCode(orgCode);
					invUse.setCopyCode(copyCode);
					invUse.setInvUseCode(invUseCode);
					invUse.setInvUseName(invUseName);
				}
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return ajaxForwardError(gridOperationMessage);
				}
				invUseManager.save(invUse);
				String key = ((this.isEntityIsNew())) ? "invUse.added"
						: "invUse.updated";
				gridOperationMessage = this.getText(key);
				return ajaxForward(true, gridOperationMessage, false);
			}

			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvUseGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (invUse == null) {
			return "Invalid invUse Data";
		}

		return SUCCESS;

	}
}
