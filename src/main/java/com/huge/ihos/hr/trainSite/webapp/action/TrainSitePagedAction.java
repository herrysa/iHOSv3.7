package com.huge.ihos.hr.trainSite.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.trainSite.model.TrainSite;
import com.huge.ihos.hr.trainSite.service.TrainSiteManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class TrainSitePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4846124984998836661L;
	private TrainSiteManager trainSiteManager;
	private List<TrainSite> trainSites;
	private TrainSite trainSite;
	private String id;
	private List evaluationGradeList;

	public TrainSiteManager getTrainSiteManager() {
		return trainSiteManager;
	}

	public void setTrainSiteManager(TrainSiteManager trainSiteManager) {
		this.trainSiteManager = trainSiteManager;
	}

	public List getEvaluationGradeList() {
        return evaluationGradeList;
    }
	
	public List<TrainSite> gettrainSites() {
		return trainSites;
	}

	public void setTrainSites(List<TrainSite> trainSites) {
		this.trainSites = trainSites;
	}

	public TrainSite getTrainSite() {
		return trainSite;
	}

	public void setTrainSite(TrainSite trainSite) {
		this.trainSite = trainSite;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		evaluationGradeList= this.getDictionaryItemManager().getAllItemsByCode( "evaluationGrade" );
		this.clearSessionMessages();
	}
	public String trainSiteGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainSiteManager
					.getTrainSiteCriteria(pagedRequests,filters);
			this.trainSites = (List<TrainSite>) pagedRequests.getList();
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
			trainSiteManager.save(trainSite);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainSite.added" : "trainSite.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainSite = trainSiteManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainSite = new TrainSite();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainSiteGridEdit() {
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
				this.trainSiteManager.remove(ida);
				gridOperationMessage = this.getText("trainSite.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainSiteGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainSite == null) {
			return "Invalid trainSite Data";
		}

		return SUCCESS;

	}
}

