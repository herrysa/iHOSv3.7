package com.huge.ihos.hr.recruitNeed.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.recruitNeed.model.RecruitNeed;
import com.huge.ihos.hr.recruitNeed.service.RecruitNeedManager;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class RecruitNeedPagedAction extends JqGridBaseAction implements Preparable {

	private RecruitNeedManager recruitNeedManager;
	private List<RecruitNeed> recruitNeeds;
	private RecruitNeed recruitNeed;
	private String id;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private List<Object> sexList;
	private List<Object> educationList;
	private List<Object> professionalList;
	private List<Object> maritalStatusList;
	private List<Object> personPolList;
	private String deptId;

	public void setRecruitNeedManager(RecruitNeedManager recruitNeedManager) {
		this.recruitNeedManager = recruitNeedManager;
	}
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
        this.deptId = deptId;
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

	public List<RecruitNeed> getRecruitNeeds() {
		return recruitNeeds;
	}

	public RecruitNeed getRecruitNeed() {
		return recruitNeed;
	}

	public void setRecruitNeed(RecruitNeed recruitNeed) {
		this.recruitNeed = recruitNeed;
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
	private HrOrgManager hrOrgManager;
	
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
	@SuppressWarnings("unchecked")
	public String recruitNeedGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String orgCodes = hrOrgManager.getAllAvailableString();
			if(orgCodes==null){
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_hrDept.orgCode",orgCodes));
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode, deptId);
			if(deptIds!=null){
				filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
			}
			String showDisabled = request.getParameter("showDisabled");
			if(OtherUtil.measureNull(showDisabled)){
				filters.add(new PropertyFilter("EQB_hrDept.disabled","0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = recruitNeedManager
					.getRecruitNeedCriteria(pagedRequests,filters);
			this.recruitNeeds = (List<RecruitNeed>) pagedRequests.getList();
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
			 if(OtherUtil.measureNull(recruitNeed.getChecker().getPersonId())){
				 recruitNeed.setChecker(null);
			 }
			 if(OtherUtil.measureNull(recruitNeed.getDoner().getPersonId())){
				 recruitNeed.setDoner(null);
			 }
			 String deptId=recruitNeed.getHrDept().getDepartmentId();
			 if(deptId!=null&&!deptId.equals("")){
	        		HrDepartmentCurrent hrDepartmentCurrent =new HrDepartmentCurrent();
	        	    hrDepartmentCurrent=hrDepartmentCurrentManager.get(deptId);
	        	    recruitNeed.setHrDept(hrDepartmentCurrent);
	        	    recruitNeed.setDeptSnapCode(hrDepartmentCurrent.getSnapCode());
	        	}
//			 if(OtherUtil.measureNull(recruitNeed.getMaker().getPersonId()))
//				 recruitNeed.setMaker(null);
//			 if(OtherUtil.measureNull(recruitNeed.getPost().getId()))
//				 recruitNeed.setPost(null);
			 recruitNeedManager.saveRecruitNeed(recruitNeed, this.isEntityIsNew());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "recruitNeed.added" : "recruitNeed.updated";
		return ajaxForward(this.getText(key));
	}
	
	private String orgName;
	private String orgCode;
	
    public String getOrgName() {
		return orgName;
	}
	public String getOrgCode() {
		return orgCode;
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
        if (id != null) {
        	recruitNeed = recruitNeedManager.get(id);
        	orgName = recruitNeed.getHrDept().getHrOrg().getOrgname();
        	orgCode = recruitNeed.getHrDept().getOrgCode();
        	this.setEntityIsNew(false);
        } else {
        	recruitNeed = new RecruitNeed();
        	recruitNeed.setState("1");
        	recruitNeed.setMaker(this.getSessionUser().getPerson());
        	//recruitNeed.setMakeDate(this.getOperTime());
        	recruitNeed.setMakeDate(new Date());
        	recruitNeed.setSex("不限");
        	recruitNeed.setPoliticsStatus("不限");
        	recruitNeed.setMaritalStatus("不限");
        	recruitNeed.setEducationLevel("不限");
        	recruitNeed.setProfession("不限");
        	recruitNeed.setYearMonth(this.getLoginPeriod());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    
	public String recruitNeedGridEdit() {
		try {
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.recruitNeedManager.remove(ida);
				gridOperationMessage = this.getText("recruitNeed.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				//recruitNeedManager.auditRecruitNeed(checkIds,this.getSessionUser().getPerson(),this.getOperTime());
				recruitNeedManager.auditRecruitNeed(checkIds,this.getSessionUser().getPerson(),new Date());
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				recruitNeedManager.antiRecruitNeed(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("addPlan")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> addPlanCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String addPlanCheckId = ids.nextToken();
					addPlanCheckIds.add(addPlanCheckId);
				}
				//recruitNeedManager.addPlanRecruitNeed(addPlanCheckIds,this.getSessionUser().getPerson(),this.getOperTime(),this.getLoginPeriod());
				recruitNeedManager.addPlanRecruitNeed(addPlanCheckIds,this.getSessionUser().getPerson(),new Date(),this.getLoginPeriod());
				gridOperationMessage = this.getText("招聘计划添加成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkRecruitNeedGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (recruitNeed == null) {
			return "Invalid recruitNeed Data";
		}
		return SUCCESS;
	}

	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

}

