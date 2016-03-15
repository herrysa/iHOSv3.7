package com.huge.ihos.gz.gzAccount.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.ihos.gz.gzAccount.service.GzAccountDefineManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class GzAccountDefinePagedAction extends JqGridBaseAction implements Preparable {

	private GzAccountDefineManager gzAccountDefineManager;
	private List<GzAccountDefine> gzAccountDefines;
	private GzAccountDefine gzAccountDefine;
	private String defineId;

	public GzAccountDefineManager getGzAccountDefineManager() {
		return gzAccountDefineManager;
	}

	public void setGzAccountDefineManager(GzAccountDefineManager gzAccountDefineManager) {
		this.gzAccountDefineManager = gzAccountDefineManager;
	}

	public List<GzAccountDefine> getgzAccountDefines() {
		return gzAccountDefines;
	}

	public void setGzAccountDefines(List<GzAccountDefine> gzAccountDefines) {
		this.gzAccountDefines = gzAccountDefines;
	}

	public GzAccountDefine getGzAccountDefine() {
		return gzAccountDefine;
	}

	public void setGzAccountDefine(GzAccountDefine gzAccountDefine) {
		this.gzAccountDefine = gzAccountDefine;
	}

	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String gzAccountDefineGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = gzAccountDefineManager
					.getGzAccountDefineCriteria(pagedRequests,filters);
			this.gzAccountDefines = (List<GzAccountDefine>) pagedRequests.getList();
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
			gzAccountDefineManager.save(gzAccountDefine);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "gzAccountDefine.added" : "gzAccountDefine.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (defineId != null) {
        	gzAccountDefine = gzAccountDefineManager.get(defineId);
        	this.setEntityIsNew(false);
        } else {
        	gzAccountDefine = new GzAccountDefine();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String gzAccountDefineGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					GzAccountDefine gzAccountDefine = gzAccountDefineManager.get(new String(removeId));
					gzAccountDefineManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("gzAccountDefine.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGzAccountDefineGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gzAccountDefine == null) {
			return "Invalid gzAccountDefine Data";
		}

		return SUCCESS;

	}
}

