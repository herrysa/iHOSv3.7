package com.huge.ihos.system.configuration.serialNumber.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberSet;
import com.huge.ihos.system.configuration.serialNumber.service.SerialNumberSetManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class SerialNumberSetPagedAction extends JqGridBaseAction implements Preparable {

	private SerialNumberSetManager serialNumberSetManager;
	private List<SerialNumberSet> serialNumberSets;
	private SerialNumberSet serialNumberSet;
	private String id;
	private String subSystem;
	private Map<String,String> businessCodeMap;

	public Map<String, String> getBusinessCodeMap() {
		return businessCodeMap;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public String getSubSystem() {
		return subSystem;
	}

	public void setSerialNumberSetManager(SerialNumberSetManager serialNumberSetManager) {
		this.serialNumberSetManager = serialNumberSetManager;
	}

	public List<SerialNumberSet> getSerialNumberSets() {
		return serialNumberSets;
	}

	public SerialNumberSet getSerialNumberSet() {
		return serialNumberSet;
	}

	public void setSerialNumberSet(SerialNumberSet serialNumberSet) {
		this.serialNumberSet = serialNumberSet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		subSystem = this.getRequest().getParameter("subSystem");
		businessCodeMap = this.getBusinessCodeMap(subSystem);
		this.clearSessionMessages();
	}
	
	@SuppressWarnings("unchecked")
	public String serialNumberSetGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			String orgCode = userContext.getOrgCode();
			String copyCode = userContext.getCopyCode();
			if("HR".equalsIgnoreCase(subSystem)){
				orgCode = BillNumberConstants.SUBSYSTEM_HR;
				copyCode = BillNumberConstants.SUBSYSTEM_HR;
			}
			if("BM".equalsIgnoreCase(subSystem)){
				orgCode = BillNumberConstants.SUBSYSTEM_BM;
				copyCode = BillNumberConstants.SUBSYSTEM_BM;
			}
			filters.add(new PropertyFilter("EQS_subSystem",subSystem));
			filters.add(new PropertyFilter("EQS_orgCode",orgCode));
			filters.add(new PropertyFilter("EQS_copyCode",copyCode));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = serialNumberSetManager.getSerialNumberSetCriteria(pagedRequests,filters);
			this.serialNumberSets = (List<SerialNumberSet>) pagedRequests.getList();
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
				if(exists(serialNumberSet)){
					return ajaxForwardError("已存在正在使用的该类型的序列号设置，请停用后再添加！");
				}
			}
			serialNumberSetManager.save(serialNumberSet);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "serialNumberSet.added" : "serialNumberSet.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	serialNumberSet = serialNumberSetManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	serialNumberSet = new SerialNumberSet();
        	serialNumberSet.setSubSystem(subSystem);
        	//TODO 更换UserContextUtil
        	UserContext userContext = UserContextUtil.getUserContext();
        	String orgCode = userContext.getOrgCode();
			String copyCode = userContext.getCopyCode();
			if("HR".equalsIgnoreCase(subSystem)){
				orgCode = BillNumberConstants.SUBSYSTEM_HR;
				copyCode = BillNumberConstants.SUBSYSTEM_HR;
				serialNumberSet.setNeedYearMonth(false);
			}
			if("BM".equalsIgnoreCase(subSystem)){
				orgCode = BillNumberConstants.SUBSYSTEM_BM;
				copyCode = BillNumberConstants.SUBSYSTEM_BM;
				serialNumberSet.setNeedYearMonth(false);
			}
        	serialNumberSet.setOrgCode(orgCode);
        	serialNumberSet.setCopyCode(copyCode);
        	this.setEntityIsNew(true);
        }
        this.setRandom(this.getRequest().getParameter("random"));
        return SUCCESS;
    }
	public String serialNumberSetGridEdit() {
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
				this.serialNumberSetManager.remove(ida);
				gridOperationMessage = this.getText("serialNumberSet.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if (oper.equals("enable") || oper.equals("disable")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String ableId = ids.nextToken();
					serialNumberSet = this.serialNumberSetManager.get(ableId);
					if(serialNumberSet==null){
						/**
						 * TODO 检测该序列号已经被引用
						 */
						return ajaxForward(false,"对应数据表己存在数据不能修改启用状态！",false);
					}else{
						if (oper.equals("enable")){
							if(exists(serialNumberSet)){
								return ajaxForwardError("同类型的序列号设置，只能有一个处于使用状态！");
							}
							serialNumberSet.setDisabled(false);
						}else{
							serialNumberSet.setDisabled(true);
						}
						this.serialNumberSetManager.save(serialNumberSet);
					}
				}
				if(oper.equals("enable")){
					gridOperationMessage = "启用成功！";
				}else{
					gridOperationMessage = "停用成功！";
				}
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSerialNumberSetGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private boolean exists(SerialNumberSet serialNumberSet){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_businessCode",serialNumberSet.getBusinessCode()));
		filters.add(new PropertyFilter("EQS_subSystem",serialNumberSet.getSubSystem()));
		filters.add(new PropertyFilter("EQS_orgCode",serialNumberSet.getOrgCode()));
		filters.add(new PropertyFilter("EQS_copyCode",serialNumberSet.getCopyCode()));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		this.serialNumberSets = this.serialNumberSetManager.getByFilters(filters);
		if(serialNumberSets!=null && serialNumberSets.size()>0){
			return true;
		}
		return false;
	}
	private String isValid() {
		if (serialNumberSet == null) {
			return "Invalid serialNumberSet Data";
		}

		return SUCCESS;
	}
	
	private Map<String,String> getBusinessCodeMap(String subSystem){
		Map<String,String> map = new HashMap<String,String>();
		if(subSystem==null){
			return map;
		}
		String bsCodeString = BillNumberConstants.BUSINESSCODE_MAP;//HR=HDM,部门合并#HDT,部门划转;
		String[] bsCodeArr = bsCodeString.split(";");//HR=HDM,部门合并#HDT,部门划转;
		String mapStr = null;
		for(String bsCode:bsCodeArr){
			String prefix = bsCode.substring(0, 2);
			if(subSystem.equalsIgnoreCase(prefix)){
				mapStr = bsCode;
				break;
			}
		}
		if(mapStr!=null){
			mapStr = mapStr.substring(3, mapStr.length());//HDM,部门合并#HDT,部门划转;
			String[] mapArr = mapStr.split("#");
			String[] kv = null;
			for(String ma:mapArr){
				kv = ma.split(",");//HDM,部门合并
				map.put(kv[0], kv[1]);
			}
		}
		return map;
	}
}

