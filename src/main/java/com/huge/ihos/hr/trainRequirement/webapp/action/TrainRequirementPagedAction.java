package com.huge.ihos.hr.trainRequirement.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.trainRequirement.model.TrainRequirement;
import com.huge.ihos.hr.trainRequirement.model.TrainRequirementAnalysis;
import com.huge.ihos.hr.trainRequirement.service.TrainRequirementManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




@SuppressWarnings("serial")
public class TrainRequirementPagedAction extends JqGridBaseAction implements Preparable {
	private TrainRequirementManager trainRequirementManager;
	private List<TrainRequirement> trainRequirements;
	private TrainRequirement trainRequirement;
	private String id;
	private String deptId;
	private List<TrainRequirementAnalysis>  trainRequirementAnalysiss;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	
	public void setTrainRequirementManager(TrainRequirementManager trainRequirementManager) {
		this.trainRequirementManager = trainRequirementManager;
	}

	public List<TrainRequirement> getTrainRequirements() {
		return trainRequirements;
	}

	public TrainRequirement getTrainRequirement() {
		return trainRequirement;
	}

	public void setTrainRequirement(TrainRequirement trainRequirement) {
		this.trainRequirement = trainRequirement;
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
	public String trainRequirementGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			String orgCodes = hrOrgManager.getAllAvailableString();
			if(orgCodes==null){
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_hrDept.orgCode",orgCodes));
			filters.add(new PropertyFilter("EQB_hrDept.disabled","0"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = trainRequirementManager
					.getTrainRequirementCriteria(pagedRequests,filters);
			this.trainRequirements = (List<TrainRequirement>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String trainRequirementAnalysisGridList(){
		log.debug("enter list method!");
		try {
			String checkDateFrom = this.getRequest().getParameter("checkDateFrom");
			String checkDateTo = this.getRequest().getParameter("checkDateTo");
			trainRequirementAnalysiss=new ArrayList<TrainRequirementAnalysis>();
			if(checkDateFrom!=null&&checkDateTo!=null){
				trainRequirementAnalysiss=this.trainRequirementManager.requirementAnalysis(checkDateFrom, checkDateTo);
			}
			records=trainRequirementAnalysiss.size();
			total = 1;
			page = 1;
		} catch (Exception e) {
			log.error("trainRequirementAnalysisGridList Error", e);
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
			if(OtherUtil.measureNull(trainRequirement.getChecker().getPersonId()))
				 trainRequirement.setChecker(null);
			 /*if(OtherUtil.measureNull(trainRequirement.getMaker().getPersonId()))
				 trainRequirement.setMaker(null);
			 if(OtherUtil.measureNull(trainRequirement.getTrainCategory().getId()))
				 trainRequirement.setTrainCategory(null);*/
			trainRequirementManager.saveTrainRequirement(trainRequirement, this.isEntityIsNew());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "trainRequirement.added" : "trainRequirement.updated";
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

	public String edit() {
        if (id != null) {
        	trainRequirement = trainRequirementManager.get(id);
        	orgName = trainRequirement.getHrDept().getHrOrg().getOrgname();
        	orgCode = trainRequirement.getHrDept().getOrgCode();
        	this.setEntityIsNew(false);
        } else {
        	trainRequirement = new TrainRequirement();
        	if(deptId!=null){
        		HrDepartmentCurrent hrDepartmentCurrent =new HrDepartmentCurrent();
        		hrDepartmentCurrent=hrDepartmentCurrentManager.get(deptId);
        		trainRequirement.setHrDept(hrDepartmentCurrent);
        		trainRequirement.setDeptSnapCode(hrDepartmentCurrent.getSnapCode());
        		orgName = hrDepartmentCurrent.getHrOrg().getOrgname();
        		orgCode = hrDepartmentCurrent.getOrgCode();
        	}
        	trainRequirement.setYearMonth(this.getLoginPeriod());
        	trainRequirement.setState("1");
        	trainRequirement.setMaker(this.getSessionUser().getPerson());
        	//trainRequirement.setMakeDate(this.getOperTime());
        	trainRequirement.setMakeDate(new Date());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    
	public String trainRequirementGridEdit() {
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
				this.trainRequirementManager.remove(ida);
				gridOperationMessage = this.getText("trainRequirement.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				//trainRequirementManager.auditTrainRequirement(checkIds,this.getSessionUser().getPerson(),this.getOperTime(),this.getLoginPeriod());
				trainRequirementManager.auditTrainRequirement(checkIds,this.getSessionUser().getPerson(),new Date(),this.getLoginPeriod());
				gridOperationMessage = this.getText("审核成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				trainRequirementManager.antiTrainRequirement(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkTrainRequirementGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (trainRequirement == null) {
			return "Invalid trainRequirement Data";
		}
		return SUCCESS;

	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public List<TrainRequirementAnalysis> getTrainRequirementAnalysiss() {
		return trainRequirementAnalysiss;
	}


	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
}

