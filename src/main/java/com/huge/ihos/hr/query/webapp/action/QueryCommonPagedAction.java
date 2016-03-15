package com.huge.ihos.hr.query.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.query.model.QueryCommon;
import com.huge.ihos.hr.query.service.QueryCommonDetailManager;
import com.huge.ihos.hr.query.service.QueryCommonManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class QueryCommonPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5667154234882562706L;
	private QueryCommonManager queryCommonManager;
	private QueryCommonDetailManager queryCommonDetailManager;
	private List<QueryCommon> queryCommons;
	private QueryCommon queryCommon;
	private String id;
	private String gridAllDatas;

	public QueryCommonManager getQueryCommonManager() {
		return queryCommonManager;
	}

	public void setQueryCommonManager(QueryCommonManager queryCommonManager) {
		this.queryCommonManager = queryCommonManager;
	}

	public List<QueryCommon> getqueryCommons() {
		return queryCommons;
	}

	public void setQueryCommons(List<QueryCommon> queryCommons) {
		this.queryCommons = queryCommons;
	}

	public QueryCommon getQueryCommon() {
		return queryCommon;
	}

	public void setQueryCommon(QueryCommon queryCommon) {
		this.queryCommon = queryCommon;
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
	public String queryCommonGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = queryCommonManager
					.getQueryCommonCriteria(pagedRequests,filters);
			this.queryCommons = (List<QueryCommon>) pagedRequests.getList();
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
			queryCommon.setChangeDate(new Date());
			queryCommon.setChangeUser(this.getSessionUser().getPerson());
			queryCommon=queryCommonManager.save(queryCommon);
			queryCommonDetailManager.saveQueryCommonDetailGridData(gridAllDatas, queryCommon.getId(), this.getSessionUser().getPerson());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "queryCommon.added" : "queryCommon.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	queryCommon = queryCommonManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	queryCommon = new QueryCommon();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String queryCommonList(){
    	try {
    		List<MenuButton> menuButtons = this.getMenuButtons();
		    //menuButtons.get(0).setEnable(false);
			setMenuButtonsToJson(menuButtons);
			} catch (Exception e) {
			 e.printStackTrace();
		}
    	return SUCCESS;
    }
    public String previewQueryCommon(){
    	return SUCCESS;
    }
	public String queryCommonGridEdit() {
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
				this.queryCommonManager.deleteQueryCommonAndDetail(ida);
				gridOperationMessage = this.getText("queryCommon.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkQueryCommonGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (queryCommon == null) {
			return "Invalid queryCommon Data";
		}

		return SUCCESS;

	}

	public String getGridAllDatas() {
		return gridAllDatas;
	}

	public void setGridAllDatas(String gridAllDatas) {
		this.gridAllDatas = gridAllDatas;
	}

	public QueryCommonDetailManager getQueryCommonDetailManager() {
		return queryCommonDetailManager;
	}

	public void setQueryCommonDetailManager(QueryCommonDetailManager queryCommonDetailManager) {
		this.queryCommonDetailManager = queryCommonDetailManager;
	}
}

