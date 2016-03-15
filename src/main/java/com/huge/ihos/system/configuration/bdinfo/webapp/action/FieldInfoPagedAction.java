package com.huge.ihos.system.configuration.bdinfo.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.configuration.bdinfo.service.FieldInfoManager;
import com.huge.ihos.system.exinterface.ProxySynchronizeToHr;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class FieldInfoPagedAction extends JqGridBaseAction implements
		Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6850997746964236885L;
	private FieldInfoManager fieldInfoManager;
	private List<FieldInfo> fieldInfoes;
	private FieldInfo fieldInfo;
	private String id;
	private String bdInfoId;

	public FieldInfoManager getFieldInfoManager() {
		return fieldInfoManager;
	}

	public void setFieldInfoManager(FieldInfoManager fieldInfoManager) {
		this.fieldInfoManager = fieldInfoManager;
	}

	public List<FieldInfo> getFieldInfoes() {
		return fieldInfoes;
	}

	public void setFieldInfos(List<FieldInfo> fieldInfoes) {
		this.fieldInfoes = fieldInfoes;
	}

	public FieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(FieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
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

	public String fieldInfoGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			if ("all".equals(oper)) {
				this.fieldInfoes = fieldInfoManager.getByFilters(filters);
			} else {
				if (OtherUtil.measureNotNull(bdInfoId)) {
					filters.add(new PropertyFilter("EQS_bdInfo.bdInfoId",
							bdInfoId));
				} else {
					filters.add(new PropertyFilter("EQS_bdInfo.bdInfoId", ""));
				}
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(
						PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests = fieldInfoManager.getFieldInfoCriteria(
						pagedRequests, filters);
				this.fieldInfoes = (List<FieldInfo>) pagedRequests.getList();
				records = pagedRequests.getTotalNumberOfRows();
				total = pagedRequests.getTotalNumberOfPages();
				page = pagedRequests.getPageNumber();
			}
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String batchEditFieldInfoGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("EQB_disabled", "0"));
			filters.add(new PropertyFilter("EQB_batchEdit", "1"));
			filters.add(new PropertyFilter("OAI_sn", ""));
			this.fieldInfoes = fieldInfoManager.getByFilters(filters);
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			Person operPerson = this.getSessionUser().getPerson();
			fieldInfo.setChanger(operPerson);
			fieldInfo.setChangeDate(new Date());
			fieldInfoManager.save(fieldInfo);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "fieldInfo.added"
				: "fieldInfo.updated";
		return ajaxForward(this.getText(key));
	}

	private BdInfoManager bdInfoManager;

	public String edit() {
		if (id != null) {
			fieldInfo = fieldInfoManager.get(id);
			this.setEntityIsNew(false);
		} else {
			fieldInfo = new FieldInfo();
			int sn = 0;
			if (OtherUtil.measureNotNull(bdInfoId)) {
				fieldInfo.setBdInfo(bdInfoManager.get(bdInfoId));
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_bdInfo.bdInfoId", bdInfoId));
				fieldInfoes = fieldInfoManager.getByFilters(filters);
				if (OtherUtil.measureNotNull(fieldInfoes)
						&& !fieldInfoes.isEmpty()) {
					sn = fieldInfoes.size();
				}
			}
			sn = sn + 1;
			fieldInfo.setSn(sn);
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String fieldInfoGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					// Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);

				}
				String[] ida = new String[idl.size()];
				idl.toArray(ida);
				this.fieldInfoManager.remove(ida);
				gridOperationMessage = this.getText("fieldInfo.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkFieldInfoGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private PersonManager personManager;

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}
//批量修改保存
	private String tableCode;
	private String filterStr;
	private String tableKey;
	public String batchEditSave() {
		try {
			HttpServletRequest request = this.getRequest();
			String filedKeys = request.getParameter("filedKeys");
			String gridAllDatas = request.getParameter("gridAllDatas");
			if (OtherUtil.measureNotNull(filedKeys)
					&& OtherUtil.measureNotNull(tableCode)
					&& OtherUtil.measureNotNull(gridAllDatas)
					&& OtherUtil.measureNotNull(tableKey)) {
				JSONArray jsonArray = JSONArray.fromObject(gridAllDatas);
				String sqlTemp = "";
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String code = jsonObject.getString("code");
					String value = jsonObject.getString("value");
					sqlTemp += code + "='" + value + "',";
					if("t_person".equals(tableCode)&&"dept_id".equals(code)){
						sqlTemp += "orgCode=(SELECT orgCode FROM t_department WHERE deptId = '"+value+"'),";
					}
				}
				sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
				String keyString = OtherUtil.splitStrAddQuotes(filedKeys, ",");
				sqlTemp = "UPDATE " + tableCode + " SET " + sqlTemp + " WHERE "
						+ tableKey + " IN (" + keyString + ")";
				fieldInfoManager.executeSql(sqlTemp);
				if (this.getYearStarted()) {
					if ("t_person".equals(tableCode)) {
						String[] personIds = filedKeys.split(",");
						for (String personId : personIds) {
							ProxySynchronizeToHr proxySynchronizeToHr = new ProxySynchronizeToHr();
							proxySynchronizeToHr.snycHrPerson(personManager.get(personId), this.getSessionUser().getPerson(), new Date());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("batchEditSave Error", e);
			gridOperationMessage = e.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward("批量修改成功");
	}

	private String isValid() {
		if (fieldInfo == null) {
			return "Invalid fieldInfo Data";
		}

		return SUCCESS;

	}

	public String getBdInfoId() {
		return bdInfoId;
	}

	public void setBdInfoId(String bdInfoId) {
		this.bdInfoId = bdInfoId;
	}

	public BdInfoManager getBdInfoManager() {
		return bdInfoManager;
	}

	public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getFilterStr() {
		return filterStr;
	}

	public void setFilterStr(String filterStr) {
		this.filterStr = filterStr;
	}

	public String getTableKey() {
		return tableKey;
	}

	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
	}
}
