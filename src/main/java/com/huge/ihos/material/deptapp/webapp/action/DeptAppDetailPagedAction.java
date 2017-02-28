package com.huge.ihos.material.deptapp.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.ihos.material.deptapp.service.DeptAppDetailManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class DeptAppDetailPagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -2823814428494549954L;
	
	private DeptAppDetailManager deptAppDetailManager;
	private List<DeptAppDetail> deptAppDetails;
	private DeptAppDetail deptAppDetail;
	private String deptAppDetailId;

	public void setDeptAppDetailManager(DeptAppDetailManager deptAppDetailManager) {
		this.deptAppDetailManager = deptAppDetailManager;
	}

	public List<DeptAppDetail> getDeptAppDetails() {
		return deptAppDetails;
	}

	public void setDeptAppDetails(List<DeptAppDetail> deptAppDetails) {
		this.deptAppDetails = deptAppDetails;
	}

	public DeptAppDetail getDeptAppDetail() {
		return deptAppDetail;
	}

	public void setDeptAppDetail(DeptAppDetail deptAppDetail) {
		this.deptAppDetail = deptAppDetail;
	}

	public String getDeptAppDetailId() {
		return deptAppDetailId;
	}

	public void setDeptAppDetailId(String deptAppDetailId) {
        this.deptAppDetailId = deptAppDetailId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	private String id;
	public void setId(String id){
		this.id = id;
	}
	@SuppressWarnings("unchecked")
	public String deptAppDetailGridList() {
		log.debug("enter deptAppDetailGridList method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(id!=null){
				filters.add(new PropertyFilter("EQS_deptApp.deptAppId",id));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = deptAppDetailManager.getDeptAppDetailCriteria(pagedRequests,filters);
			this.deptAppDetails = (List<DeptAppDetail>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("deptAppDetailGridList Error", e);
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
			deptAppDetailManager.save(deptAppDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "deptAppDetail.added" : "deptAppDetail.updated";
		return ajaxForward(this.getText(key));
	}
	
    public String edit() {
        if (deptAppDetailId != null) {
        	deptAppDetail = deptAppDetailManager.get(deptAppDetailId);
        	this.setEntityIsNew(false);
        } else {
        	deptAppDetail = new DeptAppDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    
	public String deptAppDetailGridEdit() {
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
				this.deptAppDetailManager.remove(ida);
				gridOperationMessage = this.getText("deptAppDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDeptAppDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (deptAppDetail == null) {
			return "Invalid deptAppDetail Data";
		}
		return SUCCESS;
	}
}

