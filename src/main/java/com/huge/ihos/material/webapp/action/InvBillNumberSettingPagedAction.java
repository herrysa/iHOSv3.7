package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.material.service.InvBillNumberSettingManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvBillNumberSettingPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 7725292840624932111L;
	private InvBillNumberSettingManager invBillNumberSettingManager;
	private List<InvBillNumberSetting> invBillNumberSettings;
	private InvBillNumberSetting invBillNumberSetting;
	private String id;

	public void setInvBillNumberSettingManager(InvBillNumberSettingManager invBillNumberSettingManager) {
		this.invBillNumberSettingManager = invBillNumberSettingManager;
	}

	public List<InvBillNumberSetting> getInvBillNumberSettings() {
		return invBillNumberSettings;
	}

	public void setInvBillNumberSettings(List<InvBillNumberSetting> invBillNumberSettings) {
		this.invBillNumberSettings = invBillNumberSettings;
	}

	public InvBillNumberSetting getInvBillNumberSetting() {
		return invBillNumberSetting;
	}

	public void setInvBillNumberSetting(InvBillNumberSetting invBillNumberSetting) {
		this.invBillNumberSetting = invBillNumberSetting;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String invBillNumberSettingGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invBillNumberSettingManager
					.getInvBillNumberSettingCriteria(pagedRequests,filters);
			this.invBillNumberSettings = (List<InvBillNumberSetting>) pagedRequests.getList();
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
			if(this.isEntityIsNew()){
				//检查是否已经存在该类型的设置
				if(isUsing(invBillNumberSetting)){
					return ajaxForwardError("已存在正在使用的该类型的序列号设置，请停用后再添加！");
				}
			}
			invBillNumberSettingManager.save(invBillNumberSetting);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invBillNumberSetting.added" : "invBillNumberSetting.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	invBillNumberSetting = invBillNumberSettingManager.get(Long.parseLong(id));
        	this.setEntityIsNew(false);
        } else {
        	invBillNumberSetting = new InvBillNumberSetting();
        	this.setEntityIsNew(true);
        	UserContext userContext = UserContextUtil.getUserContext();
        	invBillNumberSetting.setOrgCode(userContext.getOrgCode());
        	invBillNumberSetting.setCopyCode(userContext.getCopyCode());
        	//invBillNumberSetting.setInArow(true);
        }
        return SUCCESS;
    }
	public String invBillNumberSettingGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					//String removeId = ids.nextToken();
					Long removeId = Long.parseLong(ids.nextToken());
					this.invBillNumberSettingManager.remove(removeId);
				}
				gridOperationMessage = this.getText("invBillNumberSetting.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("enable") || oper.equals("disable")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					Long enabledId = Long.parseLong(ids.nextToken());
					invBillNumberSetting = invBillNumberSettingManager.get(enabledId);
//					if(invBillNumberSettingManager.isHaveData(invBillNumberSetting)){
					if(invBillNumberSetting==null){//need to be fixed
						return ajaxForward(false,"对应数据表己存在数据不能修改启用状态！",false);
					}else{
						if (oper.equals("enable")){
							if(isUsing(invBillNumberSetting)){
								return ajaxForwardError("同类型的序列号设置，只能有一个处于使用状态！");
							}
							invBillNumberSetting.setDisabled(false);
						}else{
							invBillNumberSetting.setDisabled(true);
						}
						invBillNumberSettingManager.save(invBillNumberSetting);
					}
				}
				if(oper.equals("enable")){
					gridOperationMessage = this.getText("启用成功！");
				}else{
					gridOperationMessage = this.getText("停用成功！");
				}
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvBillNumberSettingGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private boolean isUsing(InvBillNumberSetting invBillNumberSetting){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_orgCode",invBillNumberSetting.getOrgCode()));
		filters.add(new PropertyFilter("EQS_copyCode",invBillNumberSetting.getCopyCode()));
		filters.add(new PropertyFilter("EQS_serialCode",invBillNumberSetting.getBusinessCode()));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		this.invBillNumberSettings = this.invBillNumberSettingManager.getByFilters(filters);
		if(invBillNumberSettings!=null && invBillNumberSettings.size()>0){
			return true;
		}
		return false;
	}

	private boolean exists;
	public boolean getExists(){
		return exists;
	}
	public String checkSerialCode(){
		try {
			String serialCode = this.getRequest().getParameter("serialCode");
			/*HashMap<String,String> condition = new HashMap<String,String>();
			condition.put("serialCode", serialCode);
			condition.put("orgCode", this.getCurrentSystemVariable().getOrgCode());
			condition.put("copyCode", this.getCurrentSystemVariable().getCopyCode());*/
			InvBillNumberSetting invBillNumberSettingFind = new InvBillNumberSetting();
			invBillNumberSettingFind.setBusinessCode(serialCode);
			UserContext userContext = UserContextUtil.getUserContext();
			invBillNumberSettingFind.setOrgCode(userContext.getOrgCode());
			invBillNumberSettingFind.setCopyCode(userContext.getCopyCode());
			exists = invBillNumberSettingManager.existByExample(invBillNumberSettingFind);
			//exists = invBillNumberSettingManager.existCode("mm_billnumber_setting", condition);
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSerialCode Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private String isValid() {
		if (invBillNumberSetting == null) {
			return "Invalid invBillNumberSetting Data";
		}

		return SUCCESS;

	}
}

