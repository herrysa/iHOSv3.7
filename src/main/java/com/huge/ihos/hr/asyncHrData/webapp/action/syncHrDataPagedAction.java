package com.huge.ihos.hr.asyncHrData.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.asyncHrData.model.syncHrData;
import com.huge.ihos.hr.asyncHrData.service.syncHrDataManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;





public class syncHrDataPagedAction extends JqGridBaseAction implements Preparable {

	private syncHrDataManager syncHrDataManager;
	private List<syncHrData> syncHrDatas;
	private syncHrData syncHrData;
	private String syncHrId;

	public syncHrDataManager getsyncHrDataManager() {
		return syncHrDataManager;
	}

	public void setsyncHrDataManager(syncHrDataManager syncHrDataManager) {
		this.syncHrDataManager = syncHrDataManager;
	}

	public List<syncHrData> getSyncHrDatas() {
		return syncHrDatas;
	}

	public void setSyncHrDatas(List<syncHrData> syncHrDatas) {
		this.syncHrDatas = syncHrDatas;
	}

	public String getSyncHrId() {
		return syncHrId;
	}

	public void setSyncHrId(String syncHrId) {
        this.syncHrId = syncHrId;
    }
	

	public syncHrData getSyncHrData() {
		return syncHrData;
	}

	public void setSyncHrData(syncHrData syncHrData) {
		this.syncHrData = syncHrData;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String syncHrDataGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = (HttpServletRequest) this.getRequest();
			String syncType = request.getParameter("syncType");
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("INS_syncType", syncType));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = syncHrDataManager
					.getsyncHrDataCriteria(pagedRequests,filters);
			this.syncHrDatas = (List<syncHrData>) pagedRequests.getList();
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
			Date today = new Date();
			syncHrData.setSyncOperator(getSessionUser().getPersonName());
			syncHrData.setSyncTime(today);
			if(syncHrData.getTemparyTime()!=null){
			      syncHrData.setSyncType("1");
			      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			      Date hr_sync_time = format.parse(syncHrData.getTemparyTime());
			      syncHrData.setHr_snap_time(hr_sync_time);
			}else{
				if("1".equals(syncHrData.getIsUseHR())){
					syncHrData.setIsUseHR("系统平台单位");
				}else{
					syncHrData.setIsUseHR("人力资源单位");
				}
				syncHrData.setSyncType("2");
			}
			syncHrData.setSyncToHrType("通过菜单手动同步");
			syncHrDataManager.save(syncHrData);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "syncHrData.added" : "syncHrData.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
    	HttpServletRequest request = (HttpServletRequest) this.getRequest();
		String syncType = request.getParameter("syncType");
		request.setAttribute("syncType", syncType);
		String hr_time = request.getParameter("hr_time");
		request.setAttribute("hr_time", hr_time);
        if (syncHrId != null) {
        	syncHrData = syncHrDataManager.get(syncHrId);
        	this.setEntityIsNew(false);
        } else {
        	syncHrData = new syncHrData();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String syncHrDataGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
//					syncHrData syncHrData = syncHrDataManager.get(new String(removeId));
					syncHrDataManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("syncHrData.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checksyncHrDataGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
     private String isValid() {
		if (syncHrData == null) {
			return "Invalid syncHrData Data";
		}
        return SUCCESS;

	}
	public String syncHrDataList(){
		//获取request对象
		HttpServletRequest request = (HttpServletRequest) this.getRequest();
		String syncType = request.getParameter("syncType");
		request.setAttribute("syncType", syncType);
		return "success";
	}
}

