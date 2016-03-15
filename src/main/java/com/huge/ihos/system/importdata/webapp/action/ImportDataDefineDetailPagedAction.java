package com.huge.ihos.system.importdata.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.system.importdata.model.ImportDataDefine;
import com.huge.ihos.system.importdata.model.ImportDataDefineDetail;
import com.huge.ihos.system.importdata.service.ImportDataDefineDetailManager;
import com.huge.ihos.system.importdata.service.ImportDataDefineManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class ImportDataDefineDetailPagedAction extends JqGridBaseAction implements Preparable {

	private ImportDataDefineDetailManager importDataDefineDetailManager;
	private ImportDataDefineManager importDataDefineManager;
	private List<ImportDataDefineDetail> importDataDefineDetails;
	private ImportDataDefineDetail importDataDefineDetail;
	private String interfaceName;
	private String detailId;
	private String interfaceId;
	
	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public List<ImportDataDefineDetail> getImportDataDefineDetails() {
		return importDataDefineDetails;
	}

	public ImportDataDefineDetailManager getImportDataDefineDetailManager() {
		return importDataDefineDetailManager;
	}

	public ImportDataDefineManager getImportDataDefineManager() {
		return importDataDefineManager;
	}

	public void setImportDataDefineManager(ImportDataDefineManager importDataDefineManager) {
		this.importDataDefineManager = importDataDefineManager;
	}

	public void setImportDataDefineDetailManager(ImportDataDefineDetailManager importDataDefineDetailManager) {
		this.importDataDefineDetailManager = importDataDefineDetailManager;
	}

	public List<ImportDataDefineDetail> getimportDataDefineDetails() {
		return importDataDefineDetails;
	}

	public void setImportDataDefineDetails(List<ImportDataDefineDetail> importDataDefineDetails) {
		this.importDataDefineDetails = importDataDefineDetails;
	}

	public ImportDataDefineDetail getImportDataDefineDetail() {
		return importDataDefineDetail;
	}

	public void setImportDataDefineDetail(ImportDataDefineDetail importDataDefineDetail) {
		this.importDataDefineDetail = importDataDefineDetail;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String importDataDefineDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = importDataDefineDetailManager.getImportDataDefineDetailCriteria(pagedRequests, filters);
			this.importDataDefineDetails = (List<ImportDataDefineDetail>) pagedRequests.getList();
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
			importDataDefineDetailManager.save(importDataDefineDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "importDataDefineDetail.added" : "importDataDefineDetail.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (detailId != null) {
			importDataDefineDetail = importDataDefineDetailManager.get(detailId);
			this.setEntityIsNew(false);
		} else {
			importDataDefineDetail = new ImportDataDefineDetail();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String importDataDefineDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					//Long removeId = Long.parseLong(ids.nextToken());
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//ImportDataDefineDetail importDataDefineDetail = importDataDefineDetailManager.get(new String(removeId));
					importDataDefineDetailManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("importDataDefineDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkImportDataDefineDetailGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String saveAll() {
		HttpServletRequest request = getRequest();
		String importData = request.getParameter("importData");
		try {
			JSONArray array = JSONArray.fromObject(importData);
			ImportDataDefine define = null;
			if (OtherUtil.measureNotNull(interfaceName)) {
				PropertyFilter filter = new PropertyFilter("EQS_interfaceName",interfaceName);
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(filter);
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests = importDataDefineManager.getImportDataDefineCriteria(pagedRequests, filters);
				List<ImportDataDefine> defines = (List<ImportDataDefine>) pagedRequests.getList();
				if(defines.size() > 0) {
					for(int i = 0;i < defines.size(); i++ ) {
						importDataDefineManager.remove(defines.get(i).getInterfaceId());
					}
				}
				define = new ImportDataDefine();
				define.setInterfaceName(interfaceName);
				
			}
			if (array != null && array.size() > 0) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject object = (JSONObject) array.get(i);
					String detailName = object.getString("detailName");
					Integer detailType = object.getInt("detailType");
					String entityName = object.getString("entityName");
					Boolean isConstraint = object.getBoolean("isConstraint");
					Boolean isUpdate = object.getBoolean("isUpdate");
					Integer sn = object.getInt("sn");
					ImportDataDefineDetail detail = new ImportDataDefineDetail();
					detail.setDetailName(detailName);
					detail.setDetailType(detailType);
					detail.setEntityName(entityName);
					detail.setIsConstraint(isConstraint);
					detail.setImportDataDefine(define);
					detail.setIsUpdate(isUpdate);
					detail.setSn(sn);
					define.getImportDataDefineDetails().add(detail);
				}
				importDataDefineManager.save(define);
				List<ImportDataDefine> list = importDataDefineManager.getByExample(define);
				if(OtherUtil.measureNotNull(list)) {
					ImportDataDefine temp = list.get(0);
					interfaceId = temp.getInterfaceId();
				}
			}
			return ajaxForwardSuccess("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForwardError("添加失败！");
		}
	}

	private String isValid() {
		if (importDataDefineDetail == null) {
			return "Invalid importDataDefineDetail Data";
		}

		return SUCCESS;

	}
}
