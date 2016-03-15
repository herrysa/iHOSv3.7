package com.huge.ihos.hr.query.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.query.model.QueryCommonDetail;
import com.huge.ihos.hr.query.service.QueryCommonDetailManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class QueryCommonDetailPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3554533185160339541L;
	private QueryCommonDetailManager queryCommonDetailManager;
	private List<QueryCommonDetail> queryCommonDetails;
	private QueryCommonDetail queryCommonDetail;
	private String id;

	public QueryCommonDetailManager getQueryCommonDetailManager() {
		return queryCommonDetailManager;
	}

	public void setQueryCommonDetailManager(QueryCommonDetailManager queryCommonDetailManager) {
		this.queryCommonDetailManager = queryCommonDetailManager;
	}

	public List<QueryCommonDetail> getqueryCommonDetails() {
		return queryCommonDetails;
	}

	public void setQueryCommonDetails(List<QueryCommonDetail> queryCommonDetails) {
		this.queryCommonDetails = queryCommonDetails;
	}

	public QueryCommonDetail getQueryCommonDetail() {
		return queryCommonDetail;
	}

	public void setQueryCommonDetail(QueryCommonDetail queryCommonDetail) {
		this.queryCommonDetail = queryCommonDetail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String queryCommonDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = queryCommonDetailManager
					.getQueryCommonDetailCriteria(pagedRequests,filters);
			this.queryCommonDetails = (List<QueryCommonDetail>) pagedRequests.getList();
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
			queryCommonDetailManager.save(queryCommonDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "queryCommonDetail.added" : "queryCommonDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	queryCommonDetail = queryCommonDetailManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	queryCommonDetail = new QueryCommonDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String queryCommonDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.queryCommonDetailManager.remove(ida);
				gridOperationMessage = this.getText("queryCommonDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkQueryCommonDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (queryCommonDetail == null) {
			return "Invalid queryCommonDetail Data";
		}

		return SUCCESS;

	}
}

