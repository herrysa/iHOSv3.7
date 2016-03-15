package com.huge.ihos.hr.trainRecord.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.trainRecord.model.TrainRecordDetail;
import com.huge.ihos.hr.trainRecord.service.TrainRecordDetailManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class TrainRecordDetailPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5923998564865473353L;
	private TrainRecordDetailManager trainRecordDetailManager;
	private List<TrainRecordDetail> trainRecordDetails;
	private TrainRecordDetail trainRecordDetail;
	private String id;

	public TrainRecordDetailManager getTrainRecordDetailManager() {
		return trainRecordDetailManager;
	}

	public void setTrainRecordDetailManager(TrainRecordDetailManager trainRecordDetailManager) {
		this.trainRecordDetailManager = trainRecordDetailManager;
	}

	public List<TrainRecordDetail> gettrainRecordDetails() {
		return trainRecordDetails;
	}

	public void setTrainRecordDetails(List<TrainRecordDetail> trainRecordDetails) {
		this.trainRecordDetails = trainRecordDetails;
	}

	public TrainRecordDetail getTrainRecordDetail() {
		return trainRecordDetail;
	}

	public void setTrainRecordDetail(TrainRecordDetail trainRecordDetail) {
		this.trainRecordDetail = trainRecordDetail;
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
	public String trainRecordDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainRecordDetailManager
					.getTrainRecordDetailCriteria(pagedRequests,filters);
			this.trainRecordDetails = (List<TrainRecordDetail>) pagedRequests.getList();
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
			trainRecordDetailManager.save(trainRecordDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainRecordDetail.added" : "trainRecordDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainRecordDetail = trainRecordDetailManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainRecordDetail = new TrainRecordDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainRecordDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.trainRecordDetailManager.remove(ida);
				gridOperationMessage = this.getText("trainRecordDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainRecordDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainRecordDetail == null) {
			return "Invalid trainRecordDetail Data";
		}

		return SUCCESS;

	}
}

