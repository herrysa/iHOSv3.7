package com.huge.ihos.hr.trainEquipment.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import com.huge.ihos.hr.trainEquipment.model.TrainEquipment;
import com.huge.ihos.hr.trainEquipment.service.TrainEquipmentManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class TrainEquipmentPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 998390007148707641L;
	private TrainEquipmentManager trainEquipmentManager;
	private List<TrainEquipment> trainEquipments;
	private TrainEquipment trainEquipment;
	private String id;
	private List categoryList;
	private List stateList;

	public TrainEquipmentManager getTrainEquipmentManager() {
		return trainEquipmentManager;
	}

	public void setTrainEquipmentManager(TrainEquipmentManager trainEquipmentManager) {
		this.trainEquipmentManager = trainEquipmentManager;
	}

	public List getCategoryList() {
        return categoryList;
    }
	public List getStateList() {
        return stateList;
    }

	
	public List<TrainEquipment> gettrainEquipments() {
		return trainEquipments;
	}

	public void setTrainEquipments(List<TrainEquipment> trainEquipments) {
		this.trainEquipments = trainEquipments;
	}

	public TrainEquipment getTrainEquipment() {
		return trainEquipment;
	}

	public void setTrainEquipment(TrainEquipment trainEquipment) {
		this.trainEquipment = trainEquipment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		categoryList=this.getDictionaryItemManager().getAllItemsByCode( "equipmentCategory" );
		stateList=this.getDictionaryItemManager().getAllItemsByCode( "equipmentState" );
		this.clearSessionMessages();
	}
	public String trainEquipmentGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainEquipmentManager
					.getTrainEquipmentCriteria(pagedRequests,filters);
			this.trainEquipments = (List<TrainEquipment>) pagedRequests.getList();
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
			trainEquipmentManager.save(trainEquipment);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainEquipment.added" : "trainEquipment.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainEquipment = trainEquipmentManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainEquipment = new TrainEquipment();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainEquipmentGridEdit() {
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
				this.trainEquipmentManager.remove(ida);
				gridOperationMessage = this.getText("trainEquipment.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainEquipmentGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainEquipment == null) {
			return "Invalid trainEquipment Data";
		}

		return SUCCESS;

	}
}

