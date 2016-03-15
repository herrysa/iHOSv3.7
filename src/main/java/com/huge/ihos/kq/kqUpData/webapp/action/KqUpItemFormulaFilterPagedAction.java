package com.huge.ihos.kq.kqUpData.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.kq.kqUpData.model.KqUpItemFormulaFilter;
import com.huge.ihos.kq.kqUpData.service.KqUpItemFormulaFilterManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class KqUpItemFormulaFilterPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6889049755536620927L;
	private KqUpItemFormulaFilterManager kqUpItemFormulaFilterManager;
	private List<KqUpItemFormulaFilter> kqUpItemFormulaFilters;
	private KqUpItemFormulaFilter kqUpItemFormulaFilter;
	private String filterId;

	public KqUpItemFormulaFilterManager getKqUpItemFormulaFilterManager() {
		return kqUpItemFormulaFilterManager;
	}

	public void setKqUpItemFormulaFilterManager(KqUpItemFormulaFilterManager kqUpItemFormulaFilterManager) {
		this.kqUpItemFormulaFilterManager = kqUpItemFormulaFilterManager;
	}

	public List<KqUpItemFormulaFilter> getkqUpItemFormulaFilters() {
		return kqUpItemFormulaFilters;
	}

	public void setKqUpItemFormulaFilters(List<KqUpItemFormulaFilter> kqUpItemFormulaFilters) {
		this.kqUpItemFormulaFilters = kqUpItemFormulaFilters;
	}

	public KqUpItemFormulaFilter getKqUpItemFormulaFilter() {
		return kqUpItemFormulaFilter;
	}

	public void setKqUpItemFormulaFilter(KqUpItemFormulaFilter kqUpItemFormulaFilter) {
		this.kqUpItemFormulaFilter = kqUpItemFormulaFilter;
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
	public String kqUpItemFormulaFilterGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			String formulaId = this.getRequest().getParameter("formulaId");
			if(OtherUtil.measureNotNull(formulaId)){
				filters.add(new PropertyFilter("EQS_kqUpItemFormula.formulaId",formulaId));
			}else{
				filters.add(new PropertyFilter("EQS_kqUpItemFormula.formulaId",""));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = kqUpItemFormulaFilterManager
					.getKqUpItemFormulaFilterCriteria(pagedRequests,filters);
			this.kqUpItemFormulaFilters = (List<KqUpItemFormulaFilter>) pagedRequests.getList();
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
			kqUpItemFormulaFilterManager.save(kqUpItemFormulaFilter);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "kqUpItemFormulaFilter.added" : "kqUpItemFormulaFilter.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (filterId != null) {
        	kqUpItemFormulaFilter = kqUpItemFormulaFilterManager.get(filterId);
        	this.setEntityIsNew(false);
        } else {
        	kqUpItemFormulaFilter = new KqUpItemFormulaFilter();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String kqUpItemFormulaFilterGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					kqUpItemFormulaFilterManager.remove(new String(removeId));
				}
				gridOperationMessage = this.getText("kqUpItemFormulaFilter.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKqUpItemFormulaFilterGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (kqUpItemFormulaFilter == null) {
			return "Invalid kqUpItemFormulaFilter Data";
		}

		return SUCCESS;

	}
}

