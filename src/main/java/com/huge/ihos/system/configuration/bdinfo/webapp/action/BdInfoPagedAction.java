package com.huge.ihos.system.configuration.bdinfo.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.configuration.bdinfo.service.FieldInfoManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BdInfoPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7790648456550769452L;
	private BdInfoManager bdInfoManager;
	private List<BdInfo> bdInfoes;
	private BdInfo bdInfo;
	private String bdInfoId;

	public BdInfoManager getBdInfoManager() {
		return bdInfoManager;
	}

	public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}

	public List<BdInfo> getbdInfoes() {
		return bdInfoes;
	}

	public void setBdInfos(List<BdInfo> bdInfoes) {
		this.bdInfoes = bdInfoes;
	}

	public BdInfo getBdInfo() {
		return bdInfo;
	}

	public void setBdInfo(BdInfo bdInfo) {
		this.bdInfo = bdInfo;
	}

	public String getBdInfoId() {
		return bdInfoId;
	}

	public void setBdInfoId(String bdInfoId) {
        this.bdInfoId = bdInfoId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String bdInfoGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bdInfoManager
					.getBdInfoCriteria(pagedRequests,filters);
			this.bdInfoes = (List<BdInfo>) pagedRequests.getList();
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
			Person operPerson = this.getSessionUser().getPerson();
			bdInfo.setChanger(operPerson);
			bdInfo.setChangeDate(new Date());
			bdInfoManager.saveBdInfo(bdInfo, this.isEntityIsNew(), oper);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bdInfo.added" : "bdInfo.updated";
		return ajaxForward(this.getText(key));
	}
	public String saveBdInfoSn() {
		try {
			String dataStr = getRequest().getParameter("dataStr");
			if(OtherUtil.measureNotNull(dataStr)) {
				String[] datas = dataStr.split(",");
				for(int i=0;i<datas.length;i++) {
					String[] data = datas[i].split(":");
					String bdInfoId = data[0];
					String sn = data[1];
					BdInfo bdInfo = this.bdInfoManager.get(bdInfoId);
					bdInfo.setSn(Integer.parseInt(sn));
					this.bdInfoManager.save(bdInfo);
				}
			}
		} catch (Exception e) {
			log.error("saveBdInfoSn error!",e);
		}
		return SUCCESS;
	}
    public String edit() {
        if (bdInfoId != null) {
        	bdInfo = bdInfoManager.get(bdInfoId);
        	this.setEntityIsNew(false);
        } else {
        	bdInfo = new BdInfo();
        	bdInfoes = bdInfoManager.getAll();
        	int sn = 0;
        	if(OtherUtil.measureNotNull(bdInfoes)&&!bdInfoes.isEmpty()){
        		sn = bdInfoes.size();
        	}
        	sn = sn + 1;
        	bdInfo.setSn(sn);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    private FieldInfoManager fieldInfoManager;
	public String bdInfoGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_bdInfo.bdInfoId",removeId));
					List<FieldInfo> fieldInfos = fieldInfoManager.getByFilters(filters);
					if(OtherUtil.measureNotNull(fieldInfos)&&!fieldInfos.isEmpty()){
						for(FieldInfo fieldInfo:fieldInfos){
							fieldInfoManager.remove(fieldInfo.getFieldId());
						}
					}
					bdInfoManager.remove(removeId);
				}
				gridOperationMessage = this.getText("bdInfo.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBdInfoGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bdInfo == null) {
			return "Invalid bdInfo Data";
		}

		return SUCCESS;

	}

	public FieldInfoManager getFieldInfoManager() {
		return fieldInfoManager;
	}

	public void setFieldInfoManager(FieldInfoManager fieldInfoManager) {
		this.fieldInfoManager = fieldInfoManager;
	}
}

