package com.huge.ihos.hr.trainRepository.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import com.huge.ihos.hr.trainRepository.model.TrainRepository;
import com.huge.ihos.hr.trainRepository.service.TrainRepositoryManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class TrainRepositoryPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8626405758517399501L;
	private TrainRepositoryManager trainRepositoryManager;
	private List<TrainRepository> trainRepositories;
	private TrainRepository trainRepository;
	private String id;

	public TrainRepositoryManager getTrainRepositoryManager() {
		return trainRepositoryManager;
	}

	public void setTrainRepositoryManager(TrainRepositoryManager trainRepositoryManager) {
		this.trainRepositoryManager = trainRepositoryManager;
	}

	public List<TrainRepository> gettrainRepositories() {
		return trainRepositories;
	}

	public void setTrainRepositorys(List<TrainRepository> trainRepositories) {
		this.trainRepositories = trainRepositories;
	}

	public TrainRepository getTrainRepository() {
		return trainRepository;
	}

	public void setTrainRepository(TrainRepository trainRepository) {
		this.trainRepository = trainRepository;
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
	public String trainRepositoryGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("EQS_orgCode", UserContextUtil.getUserContext().getOrgCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainRepositoryManager
					.getTrainRepositoryCriteria(pagedRequests,filters);
			this.trainRepositories = (List<TrainRepository>) pagedRequests.getList();
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
			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			trainRepositoryManager.save(trainRepository);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainRepository.added" : "trainRepository.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainRepository = trainRepositoryManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainRepository = new TrainRepository();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainRepositoryGridEdit() {
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
				this.trainRepositoryManager.remove(ida);
				gridOperationMessage = this.getText("trainRepository.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainRepositoryGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainRepository == null) {
			return "Invalid trainRepository Data";
		}

		return SUCCESS;

	}
}

