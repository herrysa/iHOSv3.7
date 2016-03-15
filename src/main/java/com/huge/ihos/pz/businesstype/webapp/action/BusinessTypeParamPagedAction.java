package com.huge.ihos.pz.businesstype.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.ihos.pz.businesstype.model.BusinessTypeParam;
import com.huge.ihos.pz.businesstype.service.BusinessTypeParamManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class BusinessTypeParamPagedAction extends JqGridBaseAction implements Preparable {

	private BusinessTypeParamManager businessTypeParamManager;
	private List<BusinessTypeParam> businessTypeParams;
	private BusinessTypeParam businessTypeParam;
	private String paramId;
	private String businessId;
	private Map<String, String> voucherMap;

	public BusinessTypeParamManager getBusinessTypeParamManager() {
		return businessTypeParamManager;
	}

	public void setBusinessTypeParamManager(BusinessTypeParamManager businessTypeParamManager) {
		this.businessTypeParamManager = businessTypeParamManager;
	}

	public List<BusinessTypeParam> getbusinessTypeParams() {
		return businessTypeParams;
	}

	public void setBusinessTypeParams(List<BusinessTypeParam> businessTypeParams) {
		this.businessTypeParams = businessTypeParams;
	}

	public BusinessTypeParam getBusinessTypeParam() {
		return businessTypeParam;
	}

	public void setBusinessTypeParam(BusinessTypeParam businessTypeParam) {
		this.businessTypeParam = businessTypeParam;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Map<String, String> getVoucherMap() {
		return voucherMap;
	}

	public void setVoucherMap(Map<String, String> voucherMap) {
		this.voucherMap = voucherMap;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String businessTypeParamGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if (businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
				filters.add(new PropertyFilter("EQS_businessType.businessId", businessId));
			} else {
				filters.add(new PropertyFilter("EQS_businessType.businessId", ""));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = businessTypeParamManager.getBusinessTypeParamCriteria(pagedRequests, filters);
			this.businessTypeParams = (List<BusinessTypeParam>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

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
			businessTypeParamManager.save(businessTypeParam);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "businessTypeParam.added" : "businessTypeParam.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		String sqlTemp = "select voucherType sql from pz_cwLink where isUse = '1'";
		List<Map<String, Object>> listTemp = this.businessTypeParamManager.getBySqlToMap(sqlTemp);
		voucherMap = new HashMap<String, String>();
		if (listTemp != null && !listTemp.isEmpty()) {
			String voucherTypeSql = (String) listTemp.get(0).get("sql");
			if(voucherTypeSql != null) {
				List<Map<String, Object>> voucherTypeList = this.businessTypeParamManager.getBySqlToMap(voucherTypeSql);
				if(voucherTypeList != null) {
					for(Map<String, Object> mapTemp : voucherTypeList) {
						String id = mapTemp.get("id") + "";
						String name = mapTemp.get("name") + "";
						voucherMap.put(id, name);
					}
				}
			}
		}
		if (paramId != null) {
			businessTypeParam = businessTypeParamManager.get(paramId);
			this.setEntityIsNew(false);
		} else {
			businessTypeParam = new BusinessTypeParam();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String businessTypeParamGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					//Long removeId = Long.parseLong(ids.nextToken());
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//BusinessTypeParam businessTypeParam = businessTypeParamManager.get(new String(removeId));
					businessTypeParamManager.remove(removeId);

				}
				gridOperationMessage = this.getText("businessTypeParam.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBusinessTypeParamGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (businessTypeParam == null) {
			return "Invalid businessTypeParam Data";
		}

		return SUCCESS;

	}
}
