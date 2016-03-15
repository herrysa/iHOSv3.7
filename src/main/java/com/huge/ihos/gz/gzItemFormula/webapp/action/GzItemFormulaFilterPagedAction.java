package com.huge.ihos.gz.gzItemFormula.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.gz.gzItemFormula.model.GzItemFormulaFilter;
import com.huge.ihos.gz.gzItemFormula.service.GzItemFormulaFilterManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class GzItemFormulaFilterPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5942865053465882086L;
	private GzItemFormulaFilterManager gzItemFormulaFilterManager;
	private List<GzItemFormulaFilter> gzItemFormulaFilters;
	private GzItemFormulaFilter gzItemFormulaFilter;
	private String filterId;
	private String formulaId;

	public GzItemFormulaFilterManager getGzItemFormulaFilterManager() {
		return gzItemFormulaFilterManager;
	}

	public void setGzItemFormulaFilterManager(GzItemFormulaFilterManager gzItemFormulaFilterManager) {
		this.gzItemFormulaFilterManager = gzItemFormulaFilterManager;
	}

	public List<GzItemFormulaFilter> getgzItemFormulaFilters() {
		return gzItemFormulaFilters;
	}

	public void setGzItemFormulaFilters(List<GzItemFormulaFilter> gzItemFormulaFilters) {
		this.gzItemFormulaFilters = gzItemFormulaFilters;
	}

	public GzItemFormulaFilter getGzItemFormulaFilter() {
		return gzItemFormulaFilter;
	}

	public void setGzItemFormulaFilter(GzItemFormulaFilter gzItemFormulaFilter) {
		this.gzItemFormulaFilter = gzItemFormulaFilter;
	}

	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String gZItemFormulaFilterGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(OtherUtil.measureNotNull(formulaId)){
				filters.add(new PropertyFilter("EQS_gzItemFormula.formulaId",formulaId));
			}else{
				filters.add(new PropertyFilter("EQS_gzItemFormula.formulaId","-1"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = gzItemFormulaFilterManager
					.getGzItemFormulaFilterCriteria(pagedRequests,filters);
			this.gzItemFormulaFilters = (List<GzItemFormulaFilter>) pagedRequests.getList();
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
			gzItemFormulaFilterManager.save(gzItemFormulaFilter);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "gzItemFormulaFilter.added" : "gzItemFormulaFilter.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (filterId != null) {
        	gzItemFormulaFilter = gzItemFormulaFilterManager.get(filterId);
        	this.setEntityIsNew(false);
        } else {
        	gzItemFormulaFilter = new GzItemFormulaFilter();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String gZItemFormulaFilterGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					gzItemFormulaFilterManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("gzItemFormulaFilter.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGzItemFormulaFilterGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gzItemFormulaFilter == null) {
			return "Invalid gzItemFormulaFilter Data";
		}

		return SUCCESS;

	}

	public String getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(String formulaId) {
		this.formulaId = formulaId;
	}
}

