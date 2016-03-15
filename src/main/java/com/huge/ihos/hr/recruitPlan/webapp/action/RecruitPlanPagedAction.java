package com.huge.ihos.hr.recruitPlan.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.recruitPlan.model.RecruitPlan;
import com.huge.ihos.hr.recruitPlan.service.RecruitPlanManager;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class RecruitPlanPagedAction extends JqGridBaseAction implements Preparable {

	private RecruitPlanManager recruitPlanManager;
	private List<RecruitPlan> recruitPlans;
	private RecruitPlan recruitPlan;
	private String id;
	private List<Object> sexList;
	private List<Object> educationList;
	private List<Object> professionalList;
	private List<Object> maritalStatusList;
	private List<Object> personPolList;
	private List<Object> recruitChannelList;
	private List<Object> recruitTargetList;
	private List<Object> salaryLevelList;
	private String checkResult;

	public void setRecruitPlanManager(RecruitPlanManager recruitPlanManager) {
		this.recruitPlanManager = recruitPlanManager;
	}

	public List<Object> getSexList() {
        return sexList;
    }
	public List<Object> getEducationList() {
        return educationList;
    }
	public List<Object> getProfessionalList() {
        return professionalList;
    }
	public List<Object> getMaritalStatusList() {
        return maritalStatusList;
    }
	public List<Object> getPersonPolList() {
        return personPolList;
    }
	public List<Object> getRecruitChannelList() {
        return recruitChannelList;
    }
	public List<Object> getRecruitTargetList() {
        return recruitTargetList;
    }
	
    public List<Object> getSalaryLevelList() {
        return salaryLevelList;
    }
    
	public List<RecruitPlan> getRecruitPlans() {
		return recruitPlans;
	}

	public RecruitPlan getRecruitPlan() {
		return recruitPlan;
	}

	public void setRecruitPlan(RecruitPlan recruitPlan) {
		this.recruitPlan = recruitPlan;
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
	public String recruitPlanGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = recruitPlanManager
					.getRecruitPlanCriteria(pagedRequests,filters);
			this.recruitPlans = (List<RecruitPlan>) pagedRequests.getList();
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
			 if(OtherUtil.measureNull(recruitPlan.getChecker().getPersonId()))
				 recruitPlan.setChecker(null);
			 if(OtherUtil.measureNull(recruitPlan.getBreaker().getPersonId()))
				 recruitPlan.setBreaker(null);
			 if(OtherUtil.measureNull(recruitPlan.getReleaseder().getPersonId())){
				 recruitPlan.setReleaseder(null);
			 }
			 recruitPlanManager.saveRecruitPlan(recruitPlan, this.isEntityIsNew(),this.getSessionUser().getPerson());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "recruitPlan.added" : "recruitPlan.updated";
		return ajaxForward(this.getText(key));
	}
    @SuppressWarnings("unchecked")
	public String edit() {
    	DictionaryItem dictionaryItem=new DictionaryItem();
		dictionaryItem.setDictionary(null);
		dictionaryItem.setContent("不限");
		dictionaryItem.setValue("不限");
		dictionaryItem.setOrderNo(0L);
		dictionaryItem.setDictionaryItemId(0L);
		sexList= this.getDictionaryItemManager().getAllItemsByCode( "sex" );
		sexList.add(dictionaryItem);
		educationList= this.getDictionaryItemManager().getAllItemsByCode( "education" );
		educationList.add(dictionaryItem);
		professionalList= this.getDictionaryItemManager().getAllItemsByCode( "professional" );
		professionalList.add(dictionaryItem);
		maritalStatusList= this.getDictionaryItemManager().getAllItemsByCode( "maritalStatus" );
		maritalStatusList.add(dictionaryItem);
		personPolList= this.getDictionaryItemManager().getAllItemsByCode( "personPol" );
		personPolList.add(dictionaryItem);
		recruitChannelList= this.getDictionaryItemManager().getAllItemsByCode( "recruitChannel" );
		recruitTargetList= this.getDictionaryItemManager().getAllItemsByCode( "recruitTarget" );
		salaryLevelList= this.getDictionaryItemManager().getAllItemsByCode( "salaryLevel" );
        if (id != null) {
        	recruitPlan = recruitPlanManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	recruitPlan = new RecruitPlan();
        	recruitPlan.setState("1");
        	recruitPlan.setSex("不限");
        	recruitPlan.setPoliticsStatus("不限");
        	recruitPlan.setMaritalStatus("不限");
        	recruitPlan.setEducationLevel("不限");
        	recruitPlan.setProfession("不限");
        	recruitPlan.setMaker(this.getSessionUser().getPerson());
        	//recruitPlan.setMakeDate(this.getOperTime());
        	recruitPlan.setMakeDate(new Date());
        	recruitPlan.setYearMonth(this.getLoginPeriod());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    
	public String recruitPlanGridEdit() {
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
				this.recruitPlanManager.deleteRecruitPlan(idl);
//				String[] ida=new String[idl.size()];
//				idl.toArray(ida);
//				this.recruitPlanManager.remove(ida);
				gridOperationMessage = this.getText("recruitPlan.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				recruitPlanManager.auditRecruitPlan(checkIds,operPerson,operDate);
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				recruitPlanManager.antiRecruitPlan(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("confirm")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> confirmCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String confirmCheckId = ids.nextToken();
					confirmCheckIds.add(confirmCheckId);
				}
				recruitPlanManager.confirmRecruitPlan(confirmCheckIds,operPerson,operDate);
				gridOperationMessage = this.getText("发布成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("confirmAgain")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> confirmCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String confirmCheckId = ids.nextToken();
					confirmCheckIds.add(confirmCheckId);
				}
				recruitPlanManager.confirmRecruitPlan(confirmCheckIds,operPerson,operDate);
				gridOperationMessage = this.getText("发布成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("break")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> breakIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String breakId = ids.nextToken();
					breakIds.add(breakId);
				}
				recruitPlanManager.breakRecruitPlan(breakIds,operPerson,operDate);
				gridOperationMessage = this.getText("终止成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkRecruitPlanGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String recruitPlanPublishCheck(){
		checkResult="";
		StringTokenizer ids = new StringTokenizer(id,",");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		while (ids.hasMoreTokens()) {
			String checkId = ids.nextToken();
			recruitPlan=recruitPlanManager.get(checkId);
			String orgCode=recruitPlan.getOrgCode();
			String post=recruitPlan.getPost();
			filters.clear();
			filters.add(new PropertyFilter("EQS_orgCode", orgCode));
			filters.add(new PropertyFilter("EQS_post", post));
			filters.add(new PropertyFilter("EQS_state", "3"));
			recruitPlans=recruitPlanManager.getByFilters(filters);
			if(recruitPlans!=null&&recruitPlans.size()>0){
				checkResult="该单位下的该岗位已经发布，请勿重复发布!";
			}else{
				checkResult="";
			}
		}
		return SUCCESS;
	}
	private String isValid() {
		if (recruitPlan == null) {
			return "Invalid recruitPlan Data";
		}
		return SUCCESS;

	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
}

