package com.huge.ihos.hr.trainCategory.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.trainCategory.model.TrainCategory;
import com.huge.ihos.hr.trainCategory.service.TrainCategoryManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




@SuppressWarnings("serial")
public class TrainCategoryPagedAction extends JqGridBaseAction implements Preparable {

	private TrainCategoryManager trainCategoryManager;
	private List<TrainCategory> trainCategories;
	private TrainCategory trainCategory;
	private String id;
	private List<String> trainTargetList;

	public void setTrainCategoryManager(TrainCategoryManager trainCategoryManager) {
		this.trainCategoryManager = trainCategoryManager;
	}

	public List<String> getTrainTargetList() {
        return trainTargetList;
    }
	
	public List<TrainCategory> getTrainCategories() {
		return trainCategories;
	}

	public TrainCategory getTrainCategory() {
		return trainCategory;
	}

	public void setTrainCategory(TrainCategory trainCategory) {
		this.trainCategory = trainCategory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	@SuppressWarnings("unchecked")
	public void prepare() throws Exception {
		trainTargetList= this.getDictionaryItemManager().getAllItemsByCode( "trainTarget" );
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String trainCategoryGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainCategoryManager
					.getTrainCategoryCriteria(pagedRequests,filters);
			this.trainCategories = (List<TrainCategory>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("trainCategoryGridList Error", e);
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
			trainCategoryManager.save(trainCategory);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainCategory.added" : "trainCategory.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainCategory = trainCategoryManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainCategory = new TrainCategory();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainCategoryGridEdit() {
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
				this.trainCategoryManager.remove(ida);
				gridOperationMessage = this.getText("trainCategory.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainCategoryGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainCategory == null) {
			return "Invalid trainCategory Data";
		}
		return SUCCESS;
	}
}

