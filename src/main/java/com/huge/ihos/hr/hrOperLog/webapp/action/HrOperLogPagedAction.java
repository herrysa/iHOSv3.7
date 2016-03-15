package com.huge.ihos.hr.hrOperLog.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.hr.hrOperLog.service.HrOperLogManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class HrOperLogPagedAction extends JqGridBaseAction implements Preparable {

	private HrOperLogManager hrOperLogManager;
	private List<HrOperLog> hrOperLogs;

	public void setHrOperLogManager(HrOperLogManager hrOperLogManager) {
		this.hrOperLogManager = hrOperLogManager;
	}

	public List<HrOperLog> getHrOperLogs() {
		return hrOperLogs;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String hrOperLogGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String operTime = request.getParameter("operTime");
			if(OtherUtil.measureNotNull(operTime)){
				filters.add(new PropertyFilter("GED_operTime",operTime));
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateUtil.convertStringToDate("yyyy-MM-dd",operTime));
				cal.add(Calendar.DAY_OF_YEAR, 1);
				String endTime = DateUtil.getDateNow(cal.getTime());
				filters.add(new PropertyFilter("LTD_operTime",endTime));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, request);
			pagedRequests = hrOperLogManager.getHrOperLogCriteria(pagedRequests,filters);
			this.hrOperLogs = (List<HrOperLog>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("hrOperLogGridList Error", e);
		}
		return SUCCESS;
	}
	
	public String hrOperLogGridEdit() {
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
				this.hrOperLogManager.remove(ida);
				gridOperationMessage = this.getText("hrOperLog.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkHrOperLogGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
}

