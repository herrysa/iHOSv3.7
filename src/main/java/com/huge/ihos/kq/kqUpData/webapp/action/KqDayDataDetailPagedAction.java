package com.huge.ihos.kq.kqUpData.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.kq.kqUpData.model.KqDayDataDetail;
import com.huge.ihos.kq.kqUpData.service.KqDayDataDetailManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class KqDayDataDetailPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 590966992463733436L;
	private KqDayDataDetailManager kqDayDataDetailManager;
	private List<KqDayDataDetail> kqDayDataDetails;
	private KqDayDataDetail kqDayDataDetail;
	private String detailId;

	public KqDayDataDetailManager getKqDayDataDetailManager() {
		return kqDayDataDetailManager;
	}

	public void setKqDayDataDetailManager(KqDayDataDetailManager kqDayDataDetailManager) {
		this.kqDayDataDetailManager = kqDayDataDetailManager;
	}

	public List<KqDayDataDetail> getkqDayDataDetails() {
		return kqDayDataDetails;
	}

	public void setKqDayDataDetails(List<KqDayDataDetail> kqDayDataDetails) {
		this.kqDayDataDetails = kqDayDataDetails;
	}

	public KqDayDataDetail getKqDayDataDetail() {
		return kqDayDataDetail;
	}

	public void setKqDayDataDetail(KqDayDataDetail kqDayDataDetail) {
		this.kqDayDataDetail = kqDayDataDetail;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String kqDayDataDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = kqDayDataDetailManager
					.getKqDayDataDetailCriteria(pagedRequests,filters);
			this.kqDayDataDetails = (List<KqDayDataDetail>) pagedRequests.getList();
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
			kqDayDataDetailManager.save(kqDayDataDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "kqDayDataDetail.added" : "kqDayDataDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (detailId != null) {
        	kqDayDataDetail = kqDayDataDetailManager.get(detailId);
        	this.setEntityIsNew(false);
        } else {
        	kqDayDataDetail = new KqDayDataDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String kqDayDataDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					kqDayDataDetailManager.remove(removeId);
				}
				gridOperationMessage = this.getText("kqDayDataDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKqDayDataDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (kqDayDataDetail == null) {
			return "Invalid kqDayDataDetail Data";
		}

		return SUCCESS;

	}
}

