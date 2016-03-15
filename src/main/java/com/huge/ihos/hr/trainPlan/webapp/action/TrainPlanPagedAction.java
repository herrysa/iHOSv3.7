package com.huge.ihos.hr.trainPlan.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.trainCategory.model.TrainCategory;
import com.huge.ihos.hr.trainCategory.service.TrainCategoryManager;
import com.huge.ihos.hr.trainPlan.model.TrainPlan;
import com.huge.ihos.hr.trainPlan.service.TrainPlanManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;


@SuppressWarnings("serial")
public class TrainPlanPagedAction extends JqGridBaseAction implements Preparable {

	private TrainPlanManager trainPlanManager;
	private List<TrainPlan> trainPlans;
	private TrainPlan trainPlan;
	private String id;
	private List<String> quarterList;
	private List<String> monthList;
	private TrainCategoryManager trainCategoryManager;
	private List<String> typeList;
	private List<String> trainTargetList;

	public void setTrainPlanManager(TrainPlanManager trainPlanManager) {
		this.trainPlanManager = trainPlanManager;
	}
	
	public List<String> getQuarterList() {
        return quarterList;
    }
	
	public List<String> getMonthList() {
        return monthList;
    }

	public List<String> getTypeList() {
		return typeList;
	}
	
	public List<String> getTrainTargetList() {
        return trainTargetList;
    }

	public List<TrainPlan> getTrainPlans() {
		return trainPlans;
	}

	public TrainPlan getTrainPlan() {
		return trainPlan;
	}

	public void setTrainPlan(TrainPlan trainPlan) {
		this.trainPlan = trainPlan;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	@SuppressWarnings("unchecked")
	public void prepare() throws Exception {
		quarterList=this.getDictionaryItemManager().getAllItemsByCode( "quarter" );
		monthList=this.getDictionaryItemManager().getAllItemsByCode( "month" );
		typeList=this.getDictionaryItemManager().getAllItemsByCode( "planType" );
		trainTargetList= this.getDictionaryItemManager().getAllItemsByCode( "trainTarget" );
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
	public String trainPlanGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainPlanManager
					.getTrainPlanCriteria(pagedRequests,filters);
			this.trainPlans = (List<TrainPlan>) pagedRequests.getList();
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
			if(OtherUtil.measureNull(trainPlan.getChecker().getPersonId()))
				trainPlan.setChecker(null);
			/*if(OtherUtil.measureNull(trainPlan.getMaker().getPersonId()))
				trainPlan.setMaker(null);
			if(OtherUtil.measureNull(trainPlan.getTrainCategory().getId()))
				trainPlan.setTrainCategory(null);*/
			trainPlanManager.saveTrainPlan(trainPlan, this.isEntityIsNew());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainPlan.added" : "trainPlan.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainPlan = trainPlanManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainPlan = new TrainPlan();
        	trainPlan.setState("1");
        	trainPlan.setMaker(this.getSessionUser().getPerson());
        	//trainPlan.setMakeDate(this.getOperTime());
        	trainPlan.setMakeDate(new Date());
        	HttpServletRequest request = this.getRequest();
        	String trainCategoryId = request.getParameter("trainCategoryId");
        	if(trainCategoryId!=null){
        		TrainCategory trainCategory=new TrainCategory();
        		trainCategory=trainCategoryManager.get(trainCategoryId);
        		trainPlan.setTrainCategory(trainCategory);
        		String peopleNumber = request.getParameter("peopleNumber");
        		String personIds = request.getParameter("personIds");
        		String personNames = request.getParameter("personNames");
        		trainPlan.setPeopleNumber(Integer.parseInt(peopleNumber));
        		trainPlan.setPersonIds(personIds);
        		trainPlan.setPersonNames(personNames);
        		trainPlan.setGoal(trainCategory.getGoal());
        		trainPlan.setContent(trainCategory.getContent());
        		trainPlan.setTarget(trainCategory.getTarget());
        		trainPlan.setYearMonth(this.getLoginPeriod());
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainPlanGridEdit() {
		try {
			//Date operDate = this.getOperTime();
			Date operDate = new Date();
			Person operPerson = this.getSessionUser().getPerson();
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
				this.trainPlanManager.remove(ida);
				gridOperationMessage = this.getText("trainPlan.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				trainPlanManager.auditTrainPlan(checkIds,operPerson,operDate);
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				trainPlanManager.antiTrainPlan(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
			}
		catch (Exception e) {
			log.error("checkTrainPlanGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainPlan == null) {
			return "Invalid trainPlan Data";
		}

		return SUCCESS;

	}
	
	public void setTrainCategoryManager(TrainCategoryManager trainCategoryManager) {
		this.trainCategoryManager = trainCategoryManager;
	}
}

