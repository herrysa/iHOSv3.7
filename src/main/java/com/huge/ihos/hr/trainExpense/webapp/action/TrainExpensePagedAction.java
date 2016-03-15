package com.huge.ihos.hr.trainExpense.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.trainExpense.model.TrainExpense;
import com.huge.ihos.hr.trainExpense.service.TrainExpenseManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




@SuppressWarnings("serial")
public class TrainExpensePagedAction extends JqGridBaseAction implements Preparable {
	
	private TrainExpenseManager trainExpenseManager;
	private List<TrainExpense> trainExpenses;
	private TrainExpense trainExpense;
	private String id;

	public void setTrainExpenseManager(TrainExpenseManager trainExpenseManager) {
		this.trainExpenseManager = trainExpenseManager;
	}

	public List<TrainExpense> getTrainExpenses() {
		return trainExpenses;
	}

	public TrainExpense getTrainExpense() {
		return trainExpense;
	}

	public void setTrainExpense(TrainExpense trainExpense) {
		this.trainExpense = trainExpense;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		try {
			  List<MenuButton> menuButtons = this.getMenuButtons();
			   //menuButtons.get(0).setEnable(false);
			  setMenuButtonsToJson(menuButtons);
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String trainExpenseGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainExpenseManager
					.getTrainExpenseCriteria(pagedRequests,filters);
			this.trainExpenses = (List<TrainExpense>) pagedRequests.getList();
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
//			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			if(OtherUtil.measureNull(trainExpense.getMaker().getPersonId()))
				trainExpense.setMaker(null);
			if(OtherUtil.measureNull(trainExpense.getTrainNeed().getId())){
				trainExpense.setTrainNeed(null);
			}
			trainExpenseManager.saveTrainExpense(trainExpense, this.isEntityIsNew());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainExpense.added" : "trainExpense.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainExpense = trainExpenseManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainExpense = new TrainExpense();
        	trainExpense.setMaker(this.getSessionUser().getPerson());
        	trainExpense.setMakeDate(new Date());
        	trainExpense.setYearMonth(this.getLoginPeriod());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainExpenseGridEdit() {
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
				this.trainExpenseManager.remove(ida);
				gridOperationMessage = this.getText("trainExpense.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainExpenseGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainExpense == null) {
			return "Invalid trainExpense Data";
		}
		return SUCCESS;

	}
}

