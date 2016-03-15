package com.huge.ihos.pz.businesstype.webapp.action;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.ihos.pz.businesstype.model.BusinessTypeJ;
import com.huge.ihos.pz.businesstype.service.BusinessTypeJManager;
import com.huge.ihos.pz.businesstype.service.BusinessTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class BusinessTypeJPagedAction extends JqGridBaseAction implements Preparable {

	private BusinessTypeJManager businessTypeJManager;
	private List<BusinessTypeJ> businessTypeJs;
	private BusinessTypeJ businessTypeJ;
	private String businessId;
	private String acctTableSql;
	private BusinessTypeManager businessTypeManager;
	private String dataSourceType;
	private boolean accColIsHave;
	
	public boolean isAccColIsHave() {
		return accColIsHave;
	}

	public void setAccColIsHave(boolean accColIsHave) {
		this.accColIsHave = accColIsHave;
	}

	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public void setBusinessTypeManager(BusinessTypeManager businessTypeManager) {
		this.businessTypeManager = businessTypeManager;
	}

	public String getAcctTableSql() {
		return acctTableSql;
	}

	public void setAcctTableSql(String acctTableSql) {
		this.acctTableSql = acctTableSql;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public BusinessTypeJManager getBusinessTypeJManager() {
		return businessTypeJManager;
	}

	public void setBusinessTypeJManager(BusinessTypeJManager businessTypeJManager) {
		this.businessTypeJManager = businessTypeJManager;
	}

	public List<BusinessTypeJ> getbusinessTypeJs() {
		return businessTypeJs;
	}

	public void setBusinessTypeJs(List<BusinessTypeJ> businessTypeJs) {
		this.businessTypeJs = businessTypeJs;
	}

	public BusinessTypeJ getBusinessTypeJ() {
		return businessTypeJ;
	}

	public void setBusinessTypeJ(BusinessTypeJ businessTypeJ) {
		this.businessTypeJ = businessTypeJ;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	

	public String businessTypeJGridList() {
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
			pagedRequests = businessTypeJManager.getBusinessTypeJCriteria(pagedRequests, filters);
			this.businessTypeJs = (List<BusinessTypeJ>) pagedRequests.getList();
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
			if (this.isEntityIsNew()) {
				businessTypeJManager.saveBusinessTypeJ(businessTypeJ);
				String businessId = businessTypeJ.getBusinessType().getBusinessId();
				BusinessType businessType = this.businessTypeManager.get(businessId);
				if(businessType != null) {
					String collectTempTable = businessType.getCollectTempTable();
					if(collectTempTable != null && !"".equals(collectTempTable)) {
						String sqlTemp = "select name from syscolumns where id=object_id('"+collectTempTable+"') and name = '" +businessTypeJ.getFieldName()+ "'";
						List<Object[]> listTemp = this.businessTypeJManager.getBySql(sqlTemp);
						if (listTemp == null || listTemp.isEmpty() || listTemp.get(0).length <=0) {
							String alterSql = "ALTER TABLE " + collectTempTable +" ADD "+ businessTypeJ.getFieldName() +" varchar(50) NULL";
							this.businessTypeJManager.executeSql(alterSql);
						}
					}
				}
			} else {
				businessTypeJManager.save(businessTypeJ);
			}
			
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "businessTypeJ.added" : "businessTypeJ.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		String sqlTemp = "select acctTable sql from pz_cwLink where isUse = '1'";
		List<Map<String, Object>> listTemp = this.businessTypeJManager.getBySqlToMap(sqlTemp);
		if(listTemp != null && !listTemp.isEmpty()) {
			acctTableSql = (String) listTemp.get(0).get("sql");
		}
		if(businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
			BusinessType businessType = businessTypeManager.get(businessId);
			this.dataSourceType = businessType.getDataSourceType();
		}
		List<BusinessTypeJ> list = this.businessTypeJManager.getAllByBusinessId(businessId);
		accColIsHave = false;
		if(list != null && !list.isEmpty()) {
			for(BusinessTypeJ businessTypeJ : list) {
				if(businessTypeJ.getAccCol() != null && businessTypeJ.getAccCol()) {
					accColIsHave = true;
				}
			}
		}
		if (id != null) {
			businessTypeJ = businessTypeJManager.get(id);
			this.setEntityIsNew(false);
		} else {
			businessTypeJ = new BusinessTypeJ();
			List<BusinessTypeJ> businessTypeJs = businessTypeJManager.getAllByBusinessId(businessId);
			int sn = 0;
			if (businessTypeJs != null && !businessTypeJs.isEmpty()) {
				sn = businessTypeJs.size();
			}
			businessTypeJ.setSn(sn + 1);
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String businessTypeJGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					//Long removeId = Long.parseLong(ids.nextToken());
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BusinessTypeJ businessTypeJ = businessTypeJManager.get(new String(removeId));
					//businessTypeJManager.remove(removeId);
					String sqlTemp = "Select name from syscolumns Where ID=OBJECT_ID('" + businessTypeJ.getBusinessType().getBusinessId() + "_J') and name like '" + businessTypeJ.getFieldName() + "%'";
					List<Map<String, Object>> list = this.businessTypeJManager.getBySqlToMap(sqlTemp);
					if (list == null || list.isEmpty()) {
						businessTypeJManager.remove(removeId);
					} else {
						businessTypeJManager.removeBusinesstypeJ(businessTypeJ);
					}

				}
				gridOperationMessage = this.getText("businessTypeJ.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBusinessTypeJGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String checkBusinessTypeJDel() {
		if (id == null || "".equals(id)) {
			return ajaxForward("");
		}
		StringTokenizer ids = new StringTokenizer(id, ",");
		while (ids.hasMoreTokens()) {
			String removeId = ids.nextToken();
			BusinessTypeJ businessTypeJ = businessTypeJManager.get(new String(removeId));
			String sqlTemp = "Select name from syscolumns Where ID=OBJECT_ID('" + businessTypeJ.getBusinessType().getBusinessId() + "_J') and name like '" + businessTypeJ.getFieldName() + "%'";
			List<Map<String, Object>> list = this.businessTypeJManager.getBySqlToMap(sqlTemp);
			if (list != null && !list.isEmpty()) {
				return ajaxForwardSuccess("该条目已经生成对照表，是否确定删除？");
			}
		}

		//this.businessTypeJManager.get
		return ajaxForward("");
	}

	private String isValid() {
		if (businessTypeJ == null) {
			return "Invalid businessTypeJ Data";
		}

		return SUCCESS;

	}
}
