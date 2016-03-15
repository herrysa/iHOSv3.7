package com.huge.ihos.hr.trainNeed.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.ihos.hr.trainNeed.service.TrainNeedManager;
import com.huge.ihos.hr.trainPlan.model.TrainPlan;
import com.huge.ihos.hr.trainPlan.service.TrainPlanManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;


@SuppressWarnings("serial")
public class TrainNeedPagedAction extends JqGridBaseAction implements Preparable {

	private TrainNeedManager trainNeedManager;
	private List<TrainNeed> trainNeeds;
	private TrainNeed trainNeed;
	private String id;
	private List<String> quarterList;
	private List<String> monthList;
	private List<String> trainMethodList;
	private List<String> trainTypeList;
	private String trainPlanId;
	private TrainPlanManager trainPlanManager;
	private List<String> typeList;
	private List<String> trainTargetList;

	public void setTrainNeedManager(TrainNeedManager trainNeedManager) {
		this.trainNeedManager = trainNeedManager;
	}
	
	public List<String> getTrainMethodList() {
        return trainMethodList;
    }
	
	public List<String> getTrainTypeList() {
        return trainTypeList;
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

	public List<TrainNeed> getTrainNeeds() {
		return trainNeeds;
	}

	public TrainNeed getTrainNeed() {
		return trainNeed;
	}

	public void setTrainNeed(TrainNeed trainNeed) {
		this.trainNeed = trainNeed;
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
		trainMethodList=this.getDictionaryItemManager().getAllItemsByCode( "trainMethod" );
		trainTypeList=this.getDictionaryItemManager().getAllItemsByCode( "trainType" );
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
	public String trainNeedGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainNeedManager
					.getTrainNeedCriteria(pagedRequests,filters);
			this.trainNeeds = (List<TrainNeed>) pagedRequests.getList();
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
			 if(OtherUtil.measureNull(trainNeed.getChecker().getPersonId()))
				 trainNeed.setChecker(null);
			/* if(OtherUtil.measureNull(trainNeed.getMaker().getPersonId()))
				 trainNeed.setMaker(null);*/
			 if(OtherUtil.measureNull(trainNeed.getTrainInstitution().getId()))
				 trainNeed.setTrainInstitution(null);
			 if(OtherUtil.measureNull(trainNeed.getTrainPlan().getId()))
				 trainNeed.setTrainPlan(null);
			 if(OtherUtil.measureNull(trainNeed.getTrainSite().getId()))
				 trainNeed.setTrainSite(null);
			 trainNeedManager.saveTrainNeed(trainNeed, this.isEntityIsNew());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainNeed.added" : "trainNeed.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	trainNeed = trainNeedManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	trainNeed = new TrainNeed();
        	trainNeed.setState("1");
        	trainNeed.setMaker(this.getSessionUser().getPerson());
        	//trainNeed.setMakeDate(this.getOperTime());
        	trainNeed.setMakeDate(new Date());
        	if(trainPlanId!=null){
        		TrainPlan trainPlan=new TrainPlan();
        		trainPlan=trainPlanManager.get(trainPlanId);
        		trainNeed.setTrainPlan(trainPlan);
        		trainNeed.setPeopleNumber(trainPlan.getPeopleNumber());
        		trainNeed.setContent(trainPlan.getContent());
        		trainNeed.setGoal(trainPlan.getGoal());
        		trainNeed.setPersonNames(trainPlan.getPersonNames());
        		trainNeed.setPersonIds(trainPlan.getPersonIds());
        		trainNeed.setType(trainPlan.getType());
        		trainNeed.setYear(trainPlan.getYear());
        		trainNeed.setQuarter(trainPlan.getQuarter());
        		trainNeed.setMonth(trainPlan.getMonth());
        		trainNeed.setExpense(trainPlan.getBudgetAmount());
        		trainNeed.setTarget(trainPlan.getTarget());
        		trainNeed.setYearMonth(this.getLoginPeriod());
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String trainNeedGridEdit() {
		try {
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
//				String[] ida=new String[idl.size()];
//				idl.toArray(ida);
				this.trainNeedManager.deleteTrainNeed(idl);
				gridOperationMessage = this.getText("trainNeed.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				//trainNeedManager.auditTrainNeed(checkIds,this.getSessionUser().getPerson(),this.getOperTime());
				trainNeedManager.auditTrainNeed(checkIds,this.getSessionUser().getPerson(),new Date());
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				trainNeedManager.antiTrainNeed(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainNeedGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainNeed == null) {
			return "Invalid trainNeed Data";
		}
		return SUCCESS;
	}

	public String getTrainPlanId() {
		return trainPlanId;
	}

	public void setTrainPlanId(String trainPlanId) {
		this.trainPlanId = trainPlanId;
	}
	
	public void setTrainPlanManager(TrainPlanManager trainPlanManager) {
		this.trainPlanManager = trainPlanManager;
	}
}

