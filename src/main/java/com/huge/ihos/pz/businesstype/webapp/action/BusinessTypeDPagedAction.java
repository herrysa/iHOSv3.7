package com.huge.ihos.pz.businesstype.webapp.action;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.ihos.pz.businesstype.service.BusinessTypeDManager;
import com.huge.ihos.pz.businesstype.service.BusinessTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class BusinessTypeDPagedAction extends JqGridBaseAction implements Preparable {

	private BusinessTypeDManager businessTypeDManager;
	private List<BusinessTypeD> businessTypeDs;
	private BusinessTypeD businessTypeD;
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

	public BusinessTypeDManager getBusinessTypeDManager() {
		return businessTypeDManager;
	}

	public void setBusinessTypeDManager(BusinessTypeDManager businessTypeDManager) {
		this.businessTypeDManager = businessTypeDManager;
	}

	public List<BusinessTypeD> getbusinessTypeDs() {
		return businessTypeDs;
	}

	public void setBusinessTypeDs(List<BusinessTypeD> businessTypeDs) {
		this.businessTypeDs = businessTypeDs;
	}

	public BusinessTypeD getBusinessTypeD() {
		return businessTypeD;
	}

	public void setBusinessTypeD(BusinessTypeD businessTypeD) {
		this.businessTypeD = businessTypeD;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String businessTypeDGridList() {
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
			pagedRequests = businessTypeDManager.getBusinessTypeDCriteria(pagedRequests, filters);
			this.businessTypeDs = (List<BusinessTypeD>) pagedRequests.getList();
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
				businessTypeDManager.saveBusinessTypeD(businessTypeD);
				String businessId = businessTypeD.getBusinessType().getBusinessId();
				BusinessType businessType = this.businessTypeManager.get(businessId);
				if(businessType != null) {
					String collectTempTable = businessType.getCollectTempTable();
					if(collectTempTable != null && !"".equals(collectTempTable)) {
						//select name from syscolumns where id=object_id('tb_menu')		查询表中的列
						String sqlTemp = "select name from syscolumns where id=object_id('"+collectTempTable+"') and name = '" +businessTypeD.getFieldName()+ "'";
						List<Object[]> listTemp = this.businessTypeDManager.getBySql(sqlTemp);
						if (listTemp == null || listTemp.isEmpty() || listTemp.get(0).length <=0) {
							String alterSql = "ALTER TABLE " + collectTempTable +" ADD "+ businessTypeD.getFieldName() +" varchar(50) NULL";
							this.businessTypeDManager.executeSql(alterSql);
						}
					}
				}
			} else {
				businessTypeDManager.save(businessTypeD);
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "businessTypeD.added" : "businessTypeD.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		String sqlTemp = "select acctTable sql from pz_cwLink where isUse = '1'";
		List<Map<String, Object>> listTemp = this.businessTypeDManager.getBySqlToMap(sqlTemp);
		if (listTemp != null && !listTemp.isEmpty()) {
			acctTableSql = (String) listTemp.get(0).get("sql");
		}
		if (businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
			BusinessType businessType = businessTypeManager.get(businessId);
			this.dataSourceType = businessType.getDataSourceType();
		}
		accColIsHave = false;
		List<BusinessTypeD> list = this.businessTypeDManager.getAllByBusinessId(businessId);
		if(list != null && !list.isEmpty()) {
			for(BusinessTypeD businessTypeD : list) {
				if(businessTypeD.getAccCol() != null && businessTypeD.getAccCol()) {
					accColIsHave = true;
				}
			}
		}
		if (id != null) {
			businessTypeD = businessTypeDManager.get(id);
			this.setEntityIsNew(false);
		} else {
			businessTypeD = new BusinessTypeD();
			List<BusinessTypeD> businessTypeDs = businessTypeDManager.getAllByBusinessId(businessId);
			int sn = 0;
			if (businessTypeDs != null && !businessTypeDs.isEmpty()) {
				sn = businessTypeDs.size();
			}
			businessTypeD.setSn(sn + 1);
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String businessTypeDGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					//Long removeId = Long.parseLong(ids.nextToken());
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BusinessTypeD businessTypeD = businessTypeDManager.get(new String(removeId));
					//businessTypeDManager.remove(removeId);
					String sqlTemp = "Select name from syscolumns Where ID=OBJECT_ID('" + businessTypeD.getBusinessType().getBusinessId() + "_D') and name like '" + businessTypeD.getFieldName() + "%'";
					List<Map<String, Object>> list = this.businessTypeDManager.getBySqlToMap(sqlTemp);
					if (list == null || list.isEmpty()) {
						businessTypeDManager.remove(removeId);
					} else {
						businessTypeDManager.removeBusinessTypeD(businessTypeD);
					}

				}
				gridOperationMessage = this.getText("businessTypeD.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBusinessTypeDGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String checkBusinessTypeDDel() {
		if (id == null || "".equals(id)) {
			return ajaxForward("");
		}
		StringTokenizer ids = new StringTokenizer(id, ",");
		while (ids.hasMoreTokens()) {
			String removeId = ids.nextToken();
			BusinessTypeD businessTypeD = businessTypeDManager.get(new String(removeId));
			String sqlTemp = "Select name from syscolumns Where ID=OBJECT_ID('" + businessTypeD.getBusinessType().getBusinessId() + "_D') and name like '" + businessTypeD.getFieldName() + "%'";
			List<Map<String, Object>> list = this.businessTypeDManager.getBySqlToMap(sqlTemp);
			if (list != null && !list.isEmpty()) {
				return ajaxForwardSuccess("该条目已经生成对照表，是否确定删除？");
			}
		}

		//this.businessTypeJManager.get
		return ajaxForward("");
	}

	private String isValid() {
		if (businessTypeD == null) {
			return "Invalid businessTypeD Data";
		}

		return SUCCESS;

	}
}
