package com.huge.ihos.hr.trainInstitution.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.trainInstitution.model.TrainInstitution;
import com.huge.ihos.hr.trainInstitution.service.TrainInstitutionManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




@SuppressWarnings("serial")
public class TrainInstitutionPagedAction extends JqGridBaseAction implements Preparable {

	private TrainInstitutionManager trainInstitutionManager;
	private List<TrainInstitution> trainInstitutions;
	private TrainInstitution trainInstitution;
	private String id;
	private List<String> evaluationGradeList;

	public void setTrainInstitutionManager(TrainInstitutionManager trainInstitutionManager) {
		this.trainInstitutionManager = trainInstitutionManager;
	}

	public List<TrainInstitution> getTrainInstitutions() {
		return trainInstitutions;
	}

	public TrainInstitution getTrainInstitution() {
		return trainInstitution;
	}

	public void setTrainInstitution(TrainInstitution trainInstitution) {
		this.trainInstitution = trainInstitution;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	@SuppressWarnings("unchecked")
	public void prepare() throws Exception {
		evaluationGradeList= this.getDictionaryItemManager().getAllItemsByCode( "evaluationGrade" );
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String trainInstitutionGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainInstitutionManager
					.getTrainInstitutionCriteria(pagedRequests,filters);
			this.trainInstitutions = (List<TrainInstitution>) pagedRequests.getList();
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
			trainInstitutionManager.save(trainInstitution);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainInstitution.added" : "trainInstitution.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainInstitution = trainInstitutionManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainInstitution = new TrainInstitution();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainInstitutionGridEdit() {
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
				this.trainInstitutionManager.remove(ida);
				gridOperationMessage = this.getText("trainInstitution.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainInstitutionGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainInstitution == null) {
			return "Invalid trainInstitution Data";
		}

		return SUCCESS;

	}

	public List<String> getEvaluationGradeList() {
		return evaluationGradeList;
	}
}

