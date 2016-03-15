package com.huge.ihos.pz.businesstype.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.tools.ant.taskdefs.Length;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.ihos.pz.businesstype.model.BusinessTypeJ;
import com.huge.ihos.pz.businesstype.model.BusinessTypeParam;
import com.huge.ihos.pz.businesstype.service.BusinessTypeManager;
import com.huge.ihos.pz.businesstype.service.BusinessTypeParamManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class BusinessTypePagedAction extends JqGridBaseAction implements Preparable {

	private BusinessTypeManager businessTypeManager;
	private BusinessTypeParamManager businessTypeParamManager;
	private List<BusinessType> businessTypes;
	private BusinessType businessType;
	private String businessId;
	private List<Map<String, Object>> businessTypeTreeNodes = new ArrayList<Map<String, Object>>();
	private Map<String, Object> treeNode;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(Map<String, Object> treeNode) {
		this.treeNode = treeNode;
	}

	public void setBusinessTypeParamManager(BusinessTypeParamManager businessTypeParamManager) {
		this.businessTypeParamManager = businessTypeParamManager;
	}

	public BusinessTypeManager getBusinessTypeManager() {
		return businessTypeManager;
	}

	public void setBusinessTypeManager(BusinessTypeManager businessTypeManager) {
		this.businessTypeManager = businessTypeManager;
	}

	public List<BusinessType> getbusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(List<BusinessType> businessTypes) {
		this.businessTypes = businessTypes;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public List<Map<String, Object>> getBusinessTypeTreeNodes() {
		return businessTypeTreeNodes;
	}

	public void setBusinessTypeTreeNodes(List<Map<String, Object>> businessTypeTreeNodes) {
		this.businessTypeTreeNodes = businessTypeTreeNodes;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String businessTypeList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("businessTypeList error!", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String businessTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			String businessId = getRequest().getParameter("businessId");
			if (businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
				List<BusinessType> businessTypes = this.businessTypeManager.getAllDescendants(businessId);
				for (BusinessType businessType : businessTypes) {
					businessId += "," + businessType.getBusinessId();
				}
				filters.add(new PropertyFilter("INS_businessId", businessId));
			}
			//filters.add(new PropertyFilter("EQB_leaf", "1"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = businessTypeManager.getBusinessTypeCriteria(pagedRequests, filters);
			this.businessTypes = (List<BusinessType>) pagedRequests.getList();
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
			String collectTempTable = businessType.getCollectTempTable();
			if(collectTempTable != null && !"".equals(collectTempTable)) {
				boolean exist = this.businessTypeManager.checkDBTableExist(collectTempTable);
				if(!exist) {
					String businessId = null;
					if(!this.isEntityIsNew()) {
						businessId = businessType.getBusinessId();
					}
					this.businessTypeManager.createCollectTempTable(businessId,collectTempTable);
				}
			}
			if (businessType.getDisabled() == null || "".equals(businessType.getDisabled()) || "undefined".equals(businessType.getDisabled())) {
				businessType.setDisabled(false);
			}
			if (businessType.getParentType() == null || businessType.getParentType().getBusinessId() == null || "".equals(businessType.getParentType().getBusinessId()) || "undefined".equals(businessType.getParentType().getBusinessId())) {
				businessType.setParentType(null);
			}
			if (this.isEntityIsNew() && this.businessType.getLeaf()) {
				businessType = businessTypeManager.saveBusinessType(businessType);
			} else {
				businessType = businessTypeManager.save(businessType);
			}

			
			
			//businessType = businessTypeManager.save(businessType);
			treeNode = new HashMap<String, Object>();
			treeNode.put("id", businessType.getBusinessId());
			treeNode.put("name", businessType.getBusinessName());
			treeNode.put("pId", (businessType.getParentType() != null) ? businessType.getParentType().getBusinessId() : "-1");
			treeNode.put("idParent", !businessType.getLeaf());
			treeNode.put("subSysTem", "TYPE");
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "businessType.added" : "businessType.updated";
		return ajaxForward(this.getText(key));
	}

	
	public String edit() {
		if (businessId != null) {
			businessType = businessTypeManager.get(businessId);
			this.setEntityIsNew(false);
		} else {
			businessType = new BusinessType();
			String parentId = this.getRequest().getParameter("parentId");
			BusinessType parentType = null;
			if (parentId != null && !"".equals(parentId) && !"undefined".equals(parentId)) {
				parentType = this.businessTypeManager.get(parentId);
			}
			businessType.setParentType(parentType);

			List<BusinessType> businessTypes = businessTypeManager.getAll();
			int sn = 0;
			if (businessTypes != null && !businessTypes.isEmpty()) {
				sn = businessTypes.size();
			}
			businessType.setSn(sn + 1);
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String businessTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					//Long removeId = Long.parseLong(ids.nextToken());
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BusinessType businessType = businessTypeManager.get(new String(removeId));
					//businessTypeManager.remove(removeId);
					businessTypeManager.delBusinessType(removeId);

				}
				gridOperationMessage = this.getText("businessType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBusinessTypeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (businessType == null) {
			return "Invalid businessType Data";
		}

		return SUCCESS;

	}

	public String makeBusinessTypeTree() {
		businessTypeTreeNodes = new ArrayList<Map<String, Object>>();
		Map<String, Object> rootNode = new HashMap<String, Object>();
		rootNode.put("id", "-1");
		rootNode.put("name", "业务类别");
		rootNode.put("open", true);
		rootNode.put("isParent", true);
		//rootNode.put("url", "0");
		rootNode.put("subSysTem", "ALL");
		businessTypeTreeNodes.add(rootNode);
		try {
			List<BusinessType> businessTypes = this.businessTypeManager.getAllAvailable();
			if (businessTypes != null && !businessTypes.isEmpty()) {
				for (BusinessType businessType : businessTypes) {
					Map<String, Object> treeNode = new HashMap<String, Object>();
					treeNode.put("id", businessType.getBusinessId());
					treeNode.put("name", businessType.getBusinessName());
					treeNode.put("pId", (businessType.getParentType() == null) ? "-1" : businessType.getParentType().getBusinessId());
					treeNode.put("isParent", !businessType.getLeaf());
					treeNode.put("subSysTem", "TYPE");
					treeNode.put("searchName", businessType.getSearchName());
					businessTypeTreeNodes.add(treeNode);
				}
			}

		} catch (Exception e) {
			log.error("makeBusinessTypeTree error!", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	String colInfos;
	String rowInfos;

	public String getColInfos() {
		return colInfos;
	}

	public void setColInfos(String colInfos) {
		this.colInfos = colInfos;
	}

	public String getRowInfos() {
		return rowInfos;
	}

	public void setRowInfos(String rowInfos) {
		this.rowInfos = rowInfos;
	}

	List<Map<String, Object>> colInfo;
	List<Map<String, Object>> rowInfo;
	Map<String, String> colSql;
	Map<String, String> colParam;

	public Map<String, String> getColParam() {
		return colParam;
	}

	public void setColParam(Map<String, String> colParam) {
		this.colParam = colParam;
	}

	public Map<String, String> getColSql() {
		return colSql;
	}

	public void setColSql(Map<String, String> colSql) {
		this.colSql = colSql;
	}

	public List<Map<String, Object>> getColInfo() {
		return colInfo;
	}

	public void setColInfo(List<Map<String, Object>> colInfo) {
		this.colInfo = colInfo;
	}

	public List<Map<String, Object>> getRowInfo() {
		return rowInfo;
	}

	public void setRowInfo(List<Map<String, Object>> rowInfo) {
		this.rowInfo = rowInfo;
	}

	private String getSqlParam(String sqlStr) {
		String result = "";
		String sqlTemp1 = sqlStr.trim().toUpperCase();
		String sqlTemp2 = sqlTemp1.substring(6, sqlTemp1.indexOf("FROM"));
		String[] params = sqlTemp2.split(",");
		for (int i = 0; i < params.length; i++) {
			String param = params[i].trim();
			if (param.contains(" ")) {
				String[] paramArr = param.split(" ");
				String replaceStr = " ";
				replaceStr += paramArr[paramArr.length - 1];
				String colName = param.replace(replaceStr, "");
				if (colName.contains("+")) {
					String[] colNameArr = colName.split("\\+");
					for (String col : colNameArr) {
						if (col.contains("'")) {
							continue;
						} else {
							result += col + ",";
						}
					}
				} else {
					result += colName + ",";
				}

				//result += paramArr[0] + ",";
			} else {
				result += param + ",";
			}

		}
		return result.substring(0, result.length() - 1);
	}

	//{name:'gzId',index:'gzId',align:'center',text : '<s:text name="gzContent.gzId" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
	public String businessAccountMapGrid() {
		if (businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
			businessType = businessTypeManager.get(businessId);
			colSql = new HashMap<String, String>();
			colInfo = new ArrayList<Map<String, Object>>();
			colParam = new HashMap<String, String>();
			String sqlTemp = "select acctTable sql from pz_cwLink where isUse = '1'";
			List<Map<String, Object>> listTemp = this.businessTypeManager.getBySqlToMap(sqlTemp);
			String acctTableSql = "";
			if (listTemp != null && !listTemp.isEmpty()) {
				acctTableSql = (String) listTemp.get(0).get("sql");
			}
			if ("J".equals(type)) {
				Set<BusinessTypeJ> businessTypeJset = businessType.getBusinessTypeJs();
				Map<String, Object> keyCol = new HashMap<String, Object>();
				keyCol.put("name", "jId");
				keyCol.put("index", "jId");
				keyCol.put("align", "center");
				keyCol.put("label", "jId");
				keyCol.put("editable", false);
				keyCol.put("key", true);
				keyCol.put("hidden", true);
				colInfo.add(keyCol);
				for (BusinessTypeJ businessTypeJ : businessTypeJset) {
					if (businessTypeJ.getRowToCol() != null && businessTypeJ.getRowToCol()) {
						String sourceId = businessTypeJ.getSourceId();
						String sourceName = businessTypeJ.getSourceName();
						if (sourceId != null && !"".equals(sourceId)) {
							String[] sourceIds = sourceId.split(",");
							String[] sourceNames = sourceName.split(",");
							for (int i = 0; i < sourceIds.length; i++) {
								String id = sourceIds[i];
								String name = sourceNames[i];
								String fileld = businessTypeJ.getFieldName() + "_" + id;
								Map<String, Object> col = new HashMap<String, Object>();
								col.put("name", fileld + "_name");
								col.put("index", fileld + "_name");
								col.put("align", "left");
								col.put("label", name);
								col.put("width", "250");
								col.put("editable", true);
								col.put("edittype", "text");
								col.put("sortable", true);
								col.put("dataType", "string");
								col.put("highsearch", true);
								/*Map<String, Object> editoptions = new HashMap<String, Object>();
								col.put("highsearch", true);
								Map<String, Object> editoptions = new HashMap<String, Object>();
								editoptions.put("dataInit", "addComboGrid");
								col.put("editoptions", editoptions);*/
								colInfo.add(col);

								Map<String, Object> colId = new HashMap<String, Object>();
								colId.put("name", fileld);
								colId.put("index", fileld);
								colId.put("align", "left");
								colId.put("label", name);
								colId.put("width", "150");
								colId.put("editable", false);
								colId.put("dataType", "string");
								colId.put("hidden", true);
								colInfo.add(colId);

								colSql.put(fileld + "_name", acctTableSql);
								colParam.put(fileld + "_name", getSqlParam(acctTableSql));
							}
						}

					} else {
						Map<String, Object> col = new HashMap<String, Object>();
						col.put("name", businessTypeJ.getFieldName() + "_name");
						col.put("index", businessTypeJ.getFieldName() + "_name");
						col.put("align", "left");
						col.put("label", businessTypeJ.getColName());
						if (businessTypeJ.getAccCol() != null && businessTypeJ.getAccCol()) {
							col.put("editable", true);
							col.put("width", "250");
						} else {
							col.put("editable", false);
							col.put("width", "150");
						}
						col.put("sortable", true);
						col.put("edittype", "text");
						col.put("dataType", "string");
						col.put("highsearch", true);
						/*Map<String, Object> editoptions = new HashMap<String, Object>();
						col.put("highsearch", true);
						Map<String, Object> editoptions = new HashMap<String, Object>();
						editoptions.put("dataInit", "addComboGrid");
						col.put("editoptions", editoptions);*/
						colInfo.add(col);

						Map<String, Object> colId = new HashMap<String, Object>();
						colId.put("name", businessTypeJ.getFieldName());
						colId.put("index", businessTypeJ.getFieldName());
						colId.put("align", "left");
						colId.put("label", businessTypeJ.getColName());
						colId.put("width", "150");
						colId.put("editable", false);
						colId.put("dataType", "string");
						colId.put("hidden", true);
						colInfo.add(colId);

						colSql.put(businessTypeJ.getFieldName() + "_name", businessTypeJ.getSourceTable());
						colParam.put(businessTypeJ.getFieldName() + "_name", getSqlParam(businessTypeJ.getSourceTable()));
					}
				}

				/*String rowSql = "Select * from " + businessType.getBusinessId() + "_J";
				rowInfo = businessTypeManager.getBySqlToMap(rowSql);*/
			} else if ("D".equals(type)) {
				Set<BusinessTypeD> businessTypeDset = businessType.getBusinessTypeDs();
				Map<String, Object> keyCol = new HashMap<String, Object>();
				keyCol.put("name", "dId");
				keyCol.put("index", "dId");
				keyCol.put("align", "center");
				keyCol.put("label", "dId");
				keyCol.put("editable", false);
				keyCol.put("key", true);
				keyCol.put("hidden", true);
				colInfo.add(keyCol);
				for (BusinessTypeD businessTypeD : businessTypeDset) {
					if (businessTypeD.getRowToCol() != null && businessTypeD.getRowToCol()) {
						String sourceId = businessTypeD.getSourceId();
						String sourceName = businessTypeD.getSourceName();
						if (sourceId != null && !"".equals(sourceId)) {
							String[] sourceIds = sourceId.split(",");
							String[] sourceNames = sourceName.split(",");
							for (int i = 0; i < sourceIds.length; i++) {
								String id = sourceIds[i];
								String name = sourceNames[i];
								String fileld = businessTypeD.getFieldName() + "_" + id;
								Map<String, Object> col = new HashMap<String, Object>();
								col.put("name", fileld + "_name");
								col.put("index", fileld + "_name");
								col.put("align", "left");
								col.put("label", name);
								col.put("width", "250");
								col.put("editable", true);
								col.put("edittype", "text");
								col.put("sortable", true);
								col.put("dataType", "string");
								col.put("highsearch", true);
								/*Map<String, Object> editoptions = new HashMap<String, Object>();
								editoptions.put("dataInit", "addComboGrid");
								col.put("editoptions", editoptions);*/
								colInfo.add(col);

								Map<String, Object> colId = new HashMap<String, Object>();
								colId.put("name", fileld);
								colId.put("index", fileld);
								colId.put("align", "left");
								colId.put("label", name);
								colId.put("width", "150");
								colId.put("editable", false);
								colId.put("dataType", "string");
								colId.put("hidden", true);
								colInfo.add(colId);

								colSql.put(fileld + "_name", acctTableSql);
								colParam.put(fileld + "_name", getSqlParam(acctTableSql));
							}
						}
					} else {
						Map<String, Object> col = new HashMap<String, Object>();
						col.put("name", businessTypeD.getFieldName() + "_name");
						col.put("index", businessTypeD.getFieldName() + "_name");
						col.put("align", "left");
						col.put("label", businessTypeD.getColName());
						if (businessTypeD.getAccCol() != null && businessTypeD.getAccCol()) {
							col.put("editable", true);
							col.put("width", "250");
						} else {
							col.put("editable", false);
							col.put("width", "150");
						}
						col.put("sortable", true);
						col.put("edittype", "text");
						col.put("dataType", "string");
						col.put("highsearch", true);
						/*Map<String, Object> editoptions = new HashMap<String, Object>();
						editoptions.put("dataInit", "addComboGrid");
						col.put("editoptions", editoptions);*/
						colInfo.add(col);

						Map<String, Object> colId = new HashMap<String, Object>();
						colId.put("name", businessTypeD.getFieldName());
						colId.put("index", businessTypeD.getFieldName());
						colId.put("align", "left");
						colId.put("label", businessTypeD.getColName());
						colId.put("width", "150");
						colId.put("editable", false);
						colId.put("dataType", "string");
						colId.put("hidden", true);
						colInfo.add(colId);

						colSql.put(businessTypeD.getFieldName() + "_name", businessTypeD.getSourceTable());
						colParam.put(businessTypeD.getFieldName() + "_name", getSqlParam(businessTypeD.getSourceTable()));
					}
				}

				/*String rowSql = "Select * from " + businessType.getBusinessId() + "_D";
				rowInfo = businessTypeManager.getBySqlToMap(rowSql);*/
			}

		}

		//colInfos = JSONArray.fromObject(colInfo).toString();
		return SUCCESS;
	}

	private List<String> createInsertSql(String tableName, JSONArray jsonArray) {
		List<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Set<String> keys = jsonObject.keySet();
			/*if(keys.contains("jId") || keys.contains("dId")) {
				continue;
			}*/
			String keyStr = "";
			String valueStr = "";
			for (String string : keys) {
				/*if(string.endsWith("_acctId")) {
					keyStr += string + ",";
					valueStr += "'" + jsonObject.getString(string) + "',";
				} else {
					if(!string.endsWith("_id")) {
						keyStr += string + ",";
						if(jsonObject.containsKey(string + "_id")) {
							valueStr += "'" + jsonObject.getString(string + "_id") + "',";
						} else {
							valueStr += "null,";
						}
					}
				}*/
				if (!"jId".equals(string) && !"dId".equals(string)) {
					keyStr += string + ",";
					valueStr += "'" + jsonObject.getString(string) + "',";
				}
			}
			keyStr = keyStr.substring(0, keyStr.length() - 1);
			valueStr = valueStr.substring(0, valueStr.length() - 1);
			String insertSql = "insert into " + tableName + "(";
			insertSql += keyStr;
			insertSql += ")";
			insertSql += " values (";
			insertSql += valueStr;
			insertSql += ")";
			sqlList.add(insertSql);
		}
		return sqlList;
	}

	private List<String> createUpdateSql(String tableName, JSONObject mapData) {
		List<String> sqlList = new ArrayList<String>();
		if (mapData != null) {
			Set<String> keySet = mapData.keySet();
			for (String key : keySet) {
				String sqlStr = "update " + tableName + " set "; //update SF0101_J SET paycode = '01' where jId = '17'
				JSONObject valueObject = mapData.getJSONObject(key);
				Set<String> valueKeySet = valueObject.keySet();
				String setSqlStr = "";
				for (String valueKey : valueKeySet) {
					String value = valueObject.getString(valueKey);
					if(value == null ||"".equals(value) || "undefined".equals(value)) {
						setSqlStr += valueKey + "=null,";
					} else {
						setSqlStr += valueKey + "='" + value + "',";
					}
				}
				if (!"".equals(setSqlStr)) {
					setSqlStr = setSqlStr.substring(0, setSqlStr.length() - 1);
					sqlStr += setSqlStr;
					String idStr = "";
					if (tableName.lastIndexOf("_J") != -1) {
						idStr = "jId";
					} else if (tableName.lastIndexOf("_D") != -1) {
						idStr = "dId";
					}
					sqlStr += " where " + idStr + "='" + key + "'";
					sqlList.add(sqlStr);
				}
			}
		}
		return sqlList;
	}

	public String saveBusinessAccountMap() {
		String businessAccountMapData = this.getRequest().getParameter("businessAccountMapData");
		JSONObject mapData = null;
		if (businessAccountMapData != null) {
			mapData = JSONObject.fromObject(businessAccountMapData);
		}
		try {
			businessType = businessTypeManager.get(businessId);
			List<String> sqlList = new ArrayList<String>();
			if (businessType != null) {
				//TODO 存之前全部删除
				if ("J".equals(type)) {
					String tableName = businessType.getBusinessId() + "_J";
					/*String deleteSql = "delete from " + tableName;
					businessTypeManager.executeSql(deleteSql);*/
					sqlList = createUpdateSql(tableName, mapData);
				} else if ("D".equals(type)) {
					String tableName = businessType.getBusinessId() + "_D";
					/*String deleteSql = "delete from " + tableName;
					businessTypeManager.executeSql(deleteSql);*/
					sqlList = createUpdateSql(tableName, mapData);
				}
				/*Set<BusinessTypeJ> businessTypeJset = null; // 通过 businessType 获取 businessTypeJset
				for (BusinessTypeJ businessTypeJ : businessTypeJset) {
					if (businessTypeJ.getRowToCol() != null && businessTypeJ.getRowToCol()) {
						String fieldColSql = "Select name from syscolumns Where ID=OBJECT_ID('" + businessType.getBusinessId() + "') and name like '" + businessTypeJ.getFieldName() + "%' and name NOT like '%_acctId'";
						List<Map<String, Object>> fieldCols = businessTypeManager.getBySqlToMap(fieldColSql);
						for (Map<String, Object> fieldMap : fieldCols) {
							String fileld = fieldMap.get("name").toString();
							//TODO 拼inser sql
						}
					} else {
						String fileld = businessTypeJ.getFieldName();
						//TODO 拼inser sql
					}
				}*/
			} else {
				return ajaxForwardError("保存错误！");
			}
			businessTypeManager.executeSqlList(sqlList);
			return ajaxForwardSuccess("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("saveBusinessAccountMap error!", e);
			return ajaxForwardError("保存错误");
		}

	}

	public String saveBusinessTypeSn() {
		try {
			String dataStr = getRequest().getParameter("dataStr");
			if (dataStr != null && !"".equals(dataStr) && !"undefined".equals(dataStr)) {
				String[] datas = dataStr.split(",");
				for (int i = 0; i < datas.length; i++) {
					String[] data = datas[i].split(":");
					String businessId = data[0];
					String sn = data[1];
					BusinessType businessType = businessTypeManager.get(businessId);
					businessType.setSn(Integer.parseInt(sn));
					this.businessTypeManager.save(businessType);
				}
			}
		} catch (Exception e) {
			log.error("saveBusinessTypeSn error!", e);
		}
		return SUCCESS;
	}

	public String checkBusinessTypeDel() {
		try {
			if (id == null || "".equals(id) || "undefined".equals(id)) {
				return ajaxForward("");
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("INS_businessType.businessId", id));
			List<BusinessTypeJ> typeJList = businessTypeManager.getByFilters(filters, BusinessTypeJ.class);
			List<BusinessTypeD> typeDList = businessTypeManager.getByFilters(filters, BusinessTypeD.class);
			List<BusinessTypeParam> paramList = businessTypeManager.getByFilters(filters, BusinessTypeParam.class);
			if (typeJList != null && !typeJList.isEmpty()) {
				return ajaxForward("业务类别下有借方科目，不能删除。");
			}
			if (typeDList != null && !typeDList.isEmpty()) {
				return ajaxForward("业务类别下有贷方科目，不能删除。");
			}
			if (paramList != null && !paramList.isEmpty()) {
				return ajaxForward("业务类别下有其他科目，不能删除。");
			}
		} catch (Exception e) {
			log.error("checkBusinssTypeDel error!", e);
		}
		return ajaxForward("");
	}

	public String checkBusinessTypeAdd() {
		try {
			if (businessId == null || "".equals(businessId) || "undefined".equals(businessId)) {
				return ajaxForward("");
			}
			boolean flag = this.businessTypeManager.checkBusinessTypeAdd(businessId);
			if (!flag) {
				return ajaxForward("数据库中已存在" + businessId + "表，请检查。");
			}
		} catch (Exception e) {
			log.error("checkBusinessTypeAdd error!", e);
		}
		return ajaxForward("");
	}

	private String searchContents;

	public String getSearchContents() {
		return searchContents;
	}

	public void setSearchContents(String searchContents) {
		this.searchContents = searchContents;
	}

	public String getPzMapSearchContent() {
		try {
			if (businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
				BusinessType businessType = this.businessTypeManager.get(businessId);
				if (businessType != null) {
					List<Map<String, Object>> searchContentList = new ArrayList<Map<String, Object>>();
					if ("J".equals(type)) {
						Set<BusinessTypeJ> businessTypeJs = businessType.getBusinessTypeJs();
						for (BusinessTypeJ businessTypeJ : businessTypeJs) {
							if (businessTypeJ.getAccCol() != null && !businessTypeJ.getAccCol()) {
								Map<String, Object> searchContentMap = new HashMap<String, Object>();
								searchContentMap.put("key", businessTypeJ.getFieldName());
								searchContentMap.put("label", businessTypeJ.getColName());
								/*String sqlString = businessTypeJ.getSourceTable();
								String newString = sqlString.toUpperCase();
								String[] arrStrings = newString.split("FROM");
								sqlString = arrStrings[0] + " ,-1 pId from " + arrStrings[1];
								searchContentMap.put("sql", sqlString);*/
								searchContentList.add(searchContentMap);
							}
						}

					} else if ("D".equals(type)) {
						Set<BusinessTypeD> businessTypeDs = businessType.getBusinessTypeDs();
						for (BusinessTypeD businessTypeD : businessTypeDs) {
							if (businessTypeD.getAccCol() != null && !businessTypeD.getAccCol()) {
								Map<String, Object> searchContentMap = new HashMap<String, Object>();
								searchContentMap.put("key", businessTypeD.getFieldName());
								searchContentMap.put("label", businessTypeD.getColName());
								/*String sqlString = businessTypeD.getSourceTable();
								String newString = sqlString.toUpperCase();
								String[] arrStrings = newString.split("FROM");
								sqlString = arrStrings[0] + " ,-1 pId from " + arrStrings[1];
								searchContentMap.put("sql", sqlString);*/
								searchContentList.add(searchContentMap);
							}
						}
					}
					if (!searchContentList.isEmpty()) {
						searchContents = JSONArray.fromObject(searchContentList).toString();
					}
				}
			}
		} catch (Exception e) {
			log.error("getPzMapSearchContent error", e);
		}
		return SUCCESS;
	}

	public String reloadPzMapData() {
		try {
			String datas = this.getRequest().getParameter("datas");
			JSONArray datasArray = new JSONArray();
			if (datas != null) {
				datasArray = JSONArray.fromObject(datas);
			}
			String whereSql = "";
			for (int i = 0; i < datasArray.size(); i++) {
				JSONObject jsonObject = datasArray.getJSONObject(i);
				String name = jsonObject.getString("name");
				String value = jsonObject.getString("value");
				/*String valueStr = "(";
				if (value != null) {
					String[] values = value.split(",");
					for (int j = 0; j < values.length; j++) {
						valueStr += "'" + values[j] + "',";
					}
				}
				valueStr = valueStr.substring(0, valueStr.length() - 1) + ")";*/
				whereSql += " and " + name + " like '" + value + "'";
			}
			Map<String, String> sqlMap = new HashMap<String, String>();
			if (businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
				String rowSql = "";
				String countSql = "";
				if ("J".equals(type)) {
					rowSql = "Select * from " + businessId + "_J";
					countSql = "select count(*) from " + businessId + "_J";

				} else if ("D".equals(type)) {
					rowSql = "Select * from " + businessId + "_D";
					countSql = "select count(*) c from " + businessId + "_D";
				}
				rowSql += " where 1=1 " + whereSql;
				countSql += " where 1=1 " + whereSql;
				/*rowInfo = businessTypeManager.getBySqlToMap(rowSql);
				List<Map<String, Object>> countList = businessTypeManager.getBySqlToMap(countSql);
				if(countList != null && !countList.isEmpty()) {
					Map<String, Object> resultMap = countList.get(0);
					count = (Integer) resultMap.get("");
				}*/
				sqlMap.put("rowSql", rowSql);
				sqlMap.put("countSql", countSql);
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = businessTypeManager.getBusinessTypeQuery(pagedRequests, sqlMap);
			rowInfo = pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("reloadPzMapData error", e);
		}
		return SUCCESS;
	}

	public String initPzMapData() {
		int count = 0;
		try {
			if (businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
				BusinessType businessType = businessTypeManager.get(businessId);
				if (businessType != null) {
					if ("J".equals(type)) {
						String testSql = "select * from " + businessId + "_J";
						List<Map<String, Object>> testList = this.businessTypeManager.getBySqlToMap(testSql);
						/*if(testList != null && !testList.isEmpty() && !testList.get(0).isEmpty()) {
							return ajaxForwardError("表中已有数据，不能初始化！");
						}*/
						Set<BusinessTypeJ> businessTypeJs = businessType.getBusinessTypeJs();
						Map<String, List<Map<String, Object>>> mapTemp = new HashMap<String, List<Map<String, Object>>>();
						for (BusinessTypeJ businessTypeJ : businessTypeJs) {
							if (businessTypeJ.getAccCol() != null && !businessTypeJ.getAccCol()) {
								String sqlString = businessTypeJ.getSourceTable();
								String fieldName = businessTypeJ.getFieldName();
								List<Map<String, Object>> sourceMaps = this.businessTypeManager.getBySqlToMap(sqlString);
								mapTemp.put(fieldName, sourceMaps);
							}
						}
						Set<String> keySet = mapTemp.keySet();
						List<String> keyList = new ArrayList<String>(keySet);
						List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
						getSqlString(keyList, resultList, 0, mapTemp);
						List<String> sqlList = new ArrayList<String>();
						for (int i = 0; i < resultList.size(); i++) {
							Map<String, String> sqlMap = resultList.get(i);
							String keyString = sqlMap.get("key");
							String valueString = sqlMap.get("value");
							String sqlString = "insert into " + businessId + "_J (";
							sqlString += keyString;
							sqlString += ") values (";
							sqlString += valueString;
							sqlString += ")";
							boolean isHaveDate = isHaveDate(sqlMap, testList);
							if (!isHaveDate) {
								sqlList.add(sqlString);
							}
						}
						count = sqlList.size();
						this.businessTypeManager.executeSqlList(sqlList);
					} else if ("D".equals(type)) {
						String testSql = "select * from " + businessId + "_D";
						List<Map<String, Object>> testList = this.businessTypeManager.getBySqlToMap(testSql);
						/*if(testList != null && !testList.isEmpty() && !testList.get(0).isEmpty()) {
							return ajaxForwardError("表中已有数据，不能初始化！");
						}*/
						Set<BusinessTypeD> businessTypeDs = businessType.getBusinessTypeDs();
						Map<String, List<Map<String, Object>>> mapTemp = new HashMap<String, List<Map<String, Object>>>();
						for (BusinessTypeD businessTypeD : businessTypeDs) {
							if (businessTypeD.getAccCol() != null && !businessTypeD.getAccCol()) {
								String sqlString = businessTypeD.getSourceTable();
								String fieldName = businessTypeD.getFieldName();
								List<Map<String, Object>> sourceMaps = this.businessTypeManager.getBySqlToMap(sqlString);
								mapTemp.put(fieldName, sourceMaps);
							}
						}
						Set<String> keySet = mapTemp.keySet();
						List<String> keyList = new ArrayList<String>(keySet);
						List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
						getSqlString(keyList, resultList, 0, mapTemp);
						List<String> sqlList = new ArrayList<String>();
						for (int i = 0; i < resultList.size(); i++) {
							Map<String, String> sqlMap = resultList.get(i);
							String keyString = sqlMap.get("key");
							String valueString = sqlMap.get("value");
							String sqlString = "insert into " + businessId + "_D (";
							sqlString += keyString;
							sqlString += ") values (";
							sqlString += valueString;
							sqlString += ")";
							boolean isHaveDate = isHaveDate(sqlMap, testList);
							if (!isHaveDate) {
								sqlList.add(sqlString);
							}
						}
						count = sqlList.size();
						this.businessTypeManager.executeSqlList(sqlList);
					}
				}
			}
		} catch (Exception e) {
			log.error("initPzMapData error", e);
		}
		return ajaxForwardSuccess("刷新成功，新增" + count + "条记录。");
	}

	private boolean isHaveDate(Map<String, String> sqlMap, List<Map<String, Object>> testList) {
		String keyString = sqlMap.get("key");
		String valueString = sqlMap.get("value");
		String[] keyArr = keyString.split(",");
		String[] valueArr = valueString.split(",");
		for (Map<String, Object> testMap : testList) {
			int flag = 0;
			for (int i = 0; i < keyArr.length; i++) {
				String testKey = keyArr[i];
				String testValue = testMap.get(testKey) + "";
				if (valueArr[i].contains(testValue)) {
					flag++;
				}
			}
			if (flag == keyArr.length) {
				return true;
			}
		}
		return false;
	}

	private void getSqlString(List<String> keyList, List<Map<String, String>> resultList, int layer, Map<String, List<Map<String, Object>>> mapTemp) {
		if (layer < keyList.size() - 1) {
			String keyString = keyList.get(layer);
			List<Map<String, Object>> listTemp = mapTemp.get(keyString);
			if (resultList != null) {
				if (resultList.isEmpty()) {
					for (int i = 0; i < listTemp.size(); i++) {
						Map<String, Object> sqlMap = listTemp.get(i);
						String idString = sqlMap.get("id") + "";
						String nameString = sqlMap.get("name") + "";
						String sqlKey = keyString + "," + keyString + "_name";
						String sqlValue = "'" + idString + "','" + nameString + "'";
						Map<String, String> realSqlMap = new HashMap<String, String>();
						realSqlMap.put("key", sqlKey);
						realSqlMap.put("value", sqlValue);
						resultList.add(realSqlMap);
					}
					getSqlString(keyList, resultList, layer + 1, mapTemp);
				} else {
					List<Map<String, String>> realResultList = new ArrayList<Map<String, String>>();
					for (int i = 0; i < listTemp.size(); i++) {
						Map<String, Object> sqlMap = listTemp.get(i);
						String idString = sqlMap.get("id") + "";
						String nameString = sqlMap.get("name") + "";
						String sqlKey = keyString + "," + keyString + "_name";
						String sqlValue = "'" + idString + "','" + nameString + "'";
						for (int j = 0; j < resultList.size(); j++) {
							Map<String, String> realSqlMap = resultList.get(j);
							String realKeyString = realSqlMap.get("key");
							String realValueString = realSqlMap.get("value");
							realKeyString += "," + sqlKey;
							realValueString += "," + sqlValue;
							Map<String, String> newSqlMap = new HashMap<String, String>();
							newSqlMap.put("key", realKeyString);
							newSqlMap.put("value", realValueString);
							realResultList.add(newSqlMap);
						}
					}
					getSqlString(keyList, realResultList, layer + 1, mapTemp);
				}
			}
		} else if (layer == keyList.size() - 1) {
			String keyString = keyList.get(layer);
			List<Map<String, Object>> listTemp = mapTemp.get(keyString);
			if (resultList != null) {
				if (resultList.isEmpty()) {
					for (int i = 0; i < listTemp.size(); i++) {
						Map<String, Object> sqlMap = listTemp.get(i);
						String idString = sqlMap.get("id") + "";
						String nameString = sqlMap.get("name") + "";
						String sqlKey = keyString + "," + keyString + "_name";
						String sqlValue = "'" + idString + "','" + nameString + "'";
						Map<String, String> realSqlMap = new HashMap<String, String>();
						realSqlMap.put("key", sqlKey);
						realSqlMap.put("value", sqlValue);
						resultList.add(realSqlMap);
					}
				} else {
					List<Map<String, String>> realResultList = new ArrayList<Map<String, String>>();
					for (int i = 0; i < listTemp.size(); i++) {
						Map<String, Object> sqlMap = listTemp.get(i);
						String idString = sqlMap.get("id") + "";
						String nameString = sqlMap.get("name") + "";
						String sqlKey = keyString + "," + keyString + "_name";
						String sqlValue = "'" + idString + "','" + nameString + "'";
						for (int j = 0; j < resultList.size(); j++) {
							Map<String, String> realSqlMap = resultList.get(j);
							String realKeyString = realSqlMap.get("key");
							String realValueString = realSqlMap.get("value");
							realKeyString += "," + sqlKey;
							realValueString += "," + sqlValue;
							Map<String, String> newSqlMap = new HashMap<String, String>();
							newSqlMap.put("key", realKeyString);
							newSqlMap.put("value", realValueString);
							realResultList.add(newSqlMap);
						}
					}
					resultList.clear();
					for (Map<String, String> realMapTemp : realResultList) {
						resultList.add(realMapTemp);
					}
				}
			}
		}
	}

	private Map<String, String> voucherMap;
	private String voucherType;

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public Map<String, String> getVoucherMap() {
		return voucherMap;
	}

	public void setVoucherMap(Map<String, String> voucherMap) {
		this.voucherMap = voucherMap;
	}

	public String businessAccountParam() {
		String sqlTemp = "select voucherType sql from pz_cwLink where isUse = '1'";
		List<Map<String, Object>> listTemp = this.businessTypeManager.getBySqlToMap(sqlTemp);
		voucherMap = new HashMap<String, String>();
		if (listTemp != null && !listTemp.isEmpty()) {
			String voucherTypeSql = (String) listTemp.get(0).get("sql");
			if (voucherTypeSql != null) {
				List<Map<String, Object>> voucherTypeList = this.businessTypeManager.getBySqlToMap(voucherTypeSql);
				if (voucherTypeList != null) {
					for (Map<String, Object> mapTemp : voucherTypeList) {
						String id = mapTemp.get("id") + "";
						String name = mapTemp.get("name") + "";
						voucherMap.put(id, name);
					}
				}
			}
		}
		if (businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
			BusinessType businessType = this.businessTypeManager.get(businessId);
			Set<BusinessTypeParam> businessTypeParams = businessType.getBusinessTypeParams();
			for (BusinessTypeParam businessTypeParam : businessTypeParams) {
				if ("voucherType".equals(businessTypeParam.getParamCode())) {
					voucherType = businessTypeParam.getParamValue();
				}
			}
		}
		return SUCCESS;
	}

	public String saveAccountParam() {
		if(businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
			String voucherType = getRequest().getParameter("voucherType");
			BusinessType businessType = businessTypeManager.get(businessId);
			Set<BusinessTypeParam> businessTypeParams = businessType.getBusinessTypeParams();
			BusinessTypeParam paramTemp = null;
			for(BusinessTypeParam businessTypeParam : businessTypeParams) {
				if("voucherType".equals(businessTypeParam.getParamCode())) {
					paramTemp = businessTypeParam;
				}
			}
			if(paramTemp == null) {
				paramTemp = new BusinessTypeParam();
				paramTemp.setBusinessType(businessType);
				paramTemp.setDisabled(false);
				paramTemp.setParamCode("voucherType");
				paramTemp.setParamValue(voucherType);
				paramTemp.setRemark(null);
				paramTemp.setSn(0);
			} else {
				paramTemp.setParamValue(voucherType);
			}
			businessTypeParamManager.save(paramTemp);
		}
		return ajaxForwardSuccess("保存成功！");
	}

	private List<Map<String, Object>> pzMapBatchEditCols;
	
	public List<Map<String, Object>> getPzMapBatchEditCols() {
		return pzMapBatchEditCols;
	}

	public void setPzMapBatchEditCols(List<Map<String, Object>> pzMapBatchEditCols) {
		this.pzMapBatchEditCols = pzMapBatchEditCols;
	}

	public String getPzMapBatchEditList() {
		try {
			if(businessId != null && !"".equals(businessId) && !"undefined".equals(businessId)) {
				pzMapBatchEditCols = new ArrayList<Map<String,Object>>();
				BusinessType businessType = businessTypeManager.get(businessId);
				if("J".equals(type)) {
					Set<BusinessTypeJ> businessTypeJs = businessType.getBusinessTypeJs();
					for(BusinessTypeJ businessTypeJ : businessTypeJs) {
						if(businessTypeJ.getAccCol() != null && businessTypeJ.getAccCol()) {
							if(businessTypeJ.getRowToCol() != null && businessTypeJ.getRowToCol()) {
								String sqlTemp = "select acctTable sql from pz_cwLink where isUse = '1'";
								List<Map<String, Object>> listTemp = this.businessTypeManager.getBySqlToMap(sqlTemp);
								String acctTableSql = "";
								if (listTemp != null && !listTemp.isEmpty()) {
									acctTableSql = (String) listTemp.get(0).get("sql");
								}
								String sourceId = businessTypeJ.getSourceId();
								String sourceName = businessTypeJ.getSourceName();
								if(sourceId != null && !"".equals(sourceId)) {
									String[] sourceIds = sourceId.split(",");
									String[] sourceNames = sourceName.split(",");
									for(int i = 0 ; i < sourceIds.length ; i ++) {
										Map<String, Object> colMap = new HashMap<String, Object>();
										colMap.put("fieldCode", businessTypeJ.getFieldName()+"_"+sourceIds[i]);
										colMap.put("dataType", "1");
										colMap.put("dataLength", 50);
										colMap.put("userTag", "combogrid");
										colMap.put("parameter1",acctTableSql);
										colMap.put("parameter2",getSqlParam(acctTableSql));
										colMap.put("sn", pzMapBatchEditCols.size()+(i+1));
										colMap.put("name", sourceNames[i]);
										pzMapBatchEditCols.add(colMap);
									}
								}
							} else {
								Map<String, Object> colMap = new HashMap<String, Object>();
								colMap.put("fieldCode", businessTypeJ.getFieldName());
								colMap.put("dataType", "1");
								colMap.put("dataLength", 50);
								colMap.put("userTag", "combogrid");
								String sourceTable = businessTypeJ.getSourceTable();
								colMap.put("parameter1",sourceTable);
								colMap.put("parameter2",getSqlParam(sourceTable));
								colMap.put("sn", pzMapBatchEditCols.size()+1);
								colMap.put("name", businessTypeJ.getColName());
								pzMapBatchEditCols.add(colMap);
							}
						}
					}
					
				} else if("D".equals(type)){
					Set<BusinessTypeD> businessTypeDs = businessType.getBusinessTypeDs();
					for(BusinessTypeD businessTypeD : businessTypeDs) {
						if(businessTypeD.getAccCol() != null && businessTypeD.getAccCol()) {
							if(businessTypeD.getRowToCol() != null && businessTypeD.getRowToCol()) {
								String sqlTemp = "select acctTable sql from pz_cwLink where isUse = '1'";
								List<Map<String, Object>> listTemp = this.businessTypeManager.getBySqlToMap(sqlTemp);
								String acctTableSql = "";
								if (listTemp != null && !listTemp.isEmpty()) {
									acctTableSql = (String) listTemp.get(0).get("sql");
								}
								String sourceId = businessTypeD.getSourceId();
								String sourceName = businessTypeD.getSourceName();
								if(sourceId != null && !"".equals(sourceId)) {
									String[] sourceIds = sourceId.split(",");
									String[] sourceNames = sourceName.split(",");
									for(int i = 0 ; i < sourceIds.length ; i ++) {
										Map<String, Object> colMap = new HashMap<String, Object>();
										colMap.put("fieldCode", businessTypeD.getFieldName()+"_"+sourceIds[i]);
										colMap.put("dataType", "1");
										colMap.put("dataLength", 50);
										colMap.put("userTag", "combogrid");
										colMap.put("parameter1",acctTableSql);
										colMap.put("parameter2",getSqlParam(acctTableSql));
										colMap.put("sn", pzMapBatchEditCols.size()+(i+1));
										colMap.put("name", sourceNames[i]);
										pzMapBatchEditCols.add(colMap);
									}
								}
							} else {
								Map<String, Object> colMap = new HashMap<String, Object>();
								colMap.put("fieldCode", businessTypeD.getFieldName());
								colMap.put("dataType", "1");
								colMap.put("dataLength", 50);
								colMap.put("userTag", "combogrid");
								String sourceTable = businessTypeD.getSourceTable();
								colMap.put("parameter1",sourceTable);
								colMap.put("parameter2",getSqlParam(sourceTable));
								colMap.put("sn", pzMapBatchEditCols.size()+1);
								colMap.put("name", businessTypeD.getColName());
								pzMapBatchEditCols.add(colMap);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("getPzMapBatchEditList error!",e);
		}
		return SUCCESS;
	}
	private List<Map<String, Object>> acctTreeDatas;
	
	public List<Map<String, Object>> getAcctTreeDatas() {
		return acctTreeDatas;
	}

	public void setAcctTreeDatas(List<Map<String, Object>> acctTreeDatas) {
		this.acctTreeDatas = acctTreeDatas;
	}

	public String makeAcctTree() {
		try {
			String sqlTemp = "select acctTable sql from pz_cwLink where isUse = '1'";
			List<Map<String, Object>> listTemp = this.businessTypeManager.getBySqlToMap(sqlTemp);
			String acctTableSql = "";
			if (listTemp != null && !listTemp.isEmpty()) {
				acctTableSql = (String) listTemp.get(0).get("sql");
			}
			acctTreeDatas = new ArrayList<Map<String,Object>>();
			Map<String, Object> rootNode = new HashMap<String, Object>();
			rootNode.put("id", "-1");
			rootNode.put("name", "会计科目");
			rootNode.put("isParent", true);
			acctTreeDatas.add(rootNode);
			if(!"".equals(acctTableSql)) {
				List<Map<String, Object>> acctList = this.businessTypeManager.getBySqlToMap(acctTableSql);
				for(Map<String, Object> acctMap : acctList) {
					String id = acctMap.get("id") + "";
					String name = acctMap.get("name") + "";
					String acctCode = id.substring(id.lastIndexOf("_")+1);
					String prefix = id.substring(0,id.lastIndexOf("_"));
					String pId = "";
					int len = acctCode.length();
					if(len == 4) {
						pId = "-1";
					} else if(len > 4) {
						pId = prefix + "_" + acctCode.substring(0,len-2);
					}
					Map<String, Object> treeNode = new HashMap<String, Object>();
					treeNode.put("id", id);
					treeNode.put("name", acctCode + " " + name);
					treeNode.put("pId", pId);
					acctTreeDatas.add(treeNode);
				}
			}
		} catch (Exception e) {
			log.error("makeAcctTree error",e);
		}
		return SUCCESS;
	}
	/*BusinessTypeResultMain*/
	public String businessAccountMap() {

		return SUCCESS;
	}

	/*BusinessTypeResultList*/
	public String businessAccountList() {
		return SUCCESS;
	}
}
