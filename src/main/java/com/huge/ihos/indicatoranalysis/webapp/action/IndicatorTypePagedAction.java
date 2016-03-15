package com.huge.ihos.indicatoranalysis.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.indicatoranalysis.model.IndicatorType;
import com.huge.ihos.indicatoranalysis.service.IndicatorTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class IndicatorTypePagedAction extends JqGridBaseAction implements Preparable {

	private IndicatorTypeManager indicatorTypeManager;
	private List<IndicatorType> indicatorTypes;
	private IndicatorType indicatorType;
	private String indicatorTypeId;

	public void setIndicatorTypeManager(IndicatorTypeManager indicatorTypeManager) {
		this.indicatorTypeManager = indicatorTypeManager;
	}

	public List<IndicatorType> getIndicatorTypes() {
		return indicatorTypes;
	}

	public IndicatorType getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(IndicatorType indicatorType) {
		this.indicatorType = indicatorType;
	}

	public String getIndicatorTypeId() {
		return indicatorTypeId;
	}

	public void setIndicatorTypeId(String indicatorTypeId) {
		this.indicatorTypeId = indicatorTypeId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String indicatorTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = indicatorTypeManager
					.getIndicatorTypeCriteria(pagedRequests,filters);
			this.indicatorTypes = (List<IndicatorType>) pagedRequests.getList();
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
			indicatorTypeManager.save(indicatorType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "indicatorType.added" : "indicatorType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (indicatorTypeId != null) {
        	indicatorType = indicatorTypeManager.get(indicatorTypeId);
        	this.setEntityIsNew(false);
        } else {
        	indicatorType = new IndicatorType();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String indicatorTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
//					IndicatorType indicatorType = indicatorTypeManager.get(new String(removeId));
					indicatorTypeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("indicatorType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkIndicatorTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (indicatorType == null) {
			return "Invalid indicatorType Data";
		}
		return SUCCESS;
	}
}

