package com.huge.ihos.hr.hrOrg.webapp.action;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgSnapManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class HrOrgSnapPagedAction extends JqGridBaseAction implements Preparable {

	private HrOrgSnapManager hrOrgSnapManager;
	private List<HrOrgSnap> hrOrgSnaps;
	private HrOrgSnap hrOrgSnap;
	private String snapId;

	public void setHrOrgSnapManager(HrOrgSnapManager hrOrgSnapManager) {
		this.hrOrgSnapManager = hrOrgSnapManager;
	}

	public List<HrOrgSnap> getHrOrgSnaps() {
		return hrOrgSnaps;
	}

	public HrOrgSnap getHrOrgSnap() {
		return hrOrgSnap;
	}

	public void setHrOrgSnap(HrOrgSnap hrOrgSnap) {
		this.hrOrgSnap = hrOrgSnap;
	}

	public String getSnapId() {
		return snapId;
	}

	public void setSnapId(String snapId) {
        this.snapId = snapId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	@SuppressWarnings("unchecked")
	public String hrOrgSnapGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = hrOrgSnapManager.getHrOrgSnapCriteria(pagedRequests,filters);
			this.hrOrgSnaps = (List<HrOrgSnap>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("hrOrgSnapGridList Error", e);
		}
		return SUCCESS;
	}
	
	private String parentOrgCode;
	
	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(this.isEntityIsNew()){
				//String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN, this.getOperTime());
				String snapCode = DateUtil.getSnapCode();
				hrOrgSnap.setSnapCode(snapCode);
			}
			if(OtherUtil.measureNull(hrOrgSnap.getParentOrgCode())){
				hrOrgSnap.setParentOrg(null);
				hrOrgSnap.setParentSnapCode(null);
				hrOrgSnap.setParentOrgCode(null);
			}
			String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
			boolean ancyData = "1".equals(ansyOrgDeptPerson);
			//hrOrgSnap = hrOrgSnapManager.saveHrOrg(hrOrgSnap, this.getOperTime(), this.getSessionUser().getPerson(), ancyData);
			hrOrgSnap = hrOrgSnapManager.saveHrOrg(hrOrgSnap, new Date(), this.getSessionUser().getPerson(), ancyData);
			if(hrOrgSnap.getParentOrgCode()==null){
				parentOrgCode = null;
			}else{
				parentOrgCode = hrOrgSnap.getParentOrgCode();
			}
		} catch (Exception dre) {
			log.error("save hrOrgSnap Error", dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "hrOrgSnap.added" : "hrOrgSnap.updated";
		return ajaxForward(this.getText(key));
	}
	
	private HrOrgManager hrOrgManager;
	
    public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	public String edit() {
        if (snapId != null) {
        	hrOrgSnap = hrOrgSnapManager.get(snapId);
        	this.setEntityIsNew(false);
        } else {
        	hrOrgSnap = new HrOrgSnap();
        	hrOrgSnap.setType("组织");
        	if(OtherUtil.measureNotNull(parentOrgCode)){
        		HrOrg pOrg = hrOrgManager.get(parentOrgCode);
        		hrOrgSnap.setOwnerOrg(pOrg.getOrgname());
        		hrOrgSnap.setParentSnapCode(pOrg.getSnapCode());
        		hrOrgSnap.setParentOrgCode(parentOrgCode);
//        		hrOrgSnap.setParentOrg(pOrg);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    
	public String hrOrgSnapGridEdit() {
		try {
			String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
			boolean ancyData = "1".equals(ansyOrgDeptPerson);
			StringTokenizer ids = new StringTokenizer(id,",");
			String operId = null;
			//Date operDate = this.getOperTime();
			Date operDate =new Date();
			Person operPerson = this.getSessionUser().getPerson();
			if (oper.equals("del")) {
				while (ids.hasMoreTokens()) {
					operId = ids.nextToken();
					hrOrgSnapManager.deleteHrOrg(operId,operDate,operPerson,ancyData);
				}
				gridOperationMessage = this.getText("hrOrgSnap.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("enable")){
				while (ids.hasMoreTokens()) {
					operId = ids.nextToken();
					this.hrOrgSnap = this.hrOrgSnapManager.get(operId).clone();
					hrOrgSnap.setDisabled(false);
					hrOrgSnap.setInvalidDate(null);
					hrOrgSnapManager.saveHrOrg(hrOrgSnap, operDate, operPerson, ancyData);
				}
				gridOperationMessage = this.getText("hrOrgSnap.enabled");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("disable")){
				while (ids.hasMoreTokens()) {
					operId = ids.nextToken();
					this.hrOrgSnap = this.hrOrgSnapManager.get(operId).clone();
					hrOrgSnap.setDisabled(true);
					hrOrgSnap.setInvalidDate(operDate);
					hrOrgSnapManager.saveHrOrg(hrOrgSnap, operDate, operPerson, ancyData);
				}
				gridOperationMessage = this.getText("hrOrgSnap.disablesuccess");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkHrOrgSnapGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (hrOrgSnap == null) {
			return "Invalid hrOrgSnap Data";
		}
		return SUCCESS;
	}
}

