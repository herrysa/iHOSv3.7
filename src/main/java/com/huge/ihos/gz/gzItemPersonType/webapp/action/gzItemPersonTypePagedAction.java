package com.huge.ihos.gz.gzItemPersonType.webapp.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormula;
import com.huge.ihos.gz.gzItemFormula.service.GzItemFormulaManager;
import com.huge.ihos.gz.gzItemPersonType.model.GzItemPersonType;
import com.huge.ihos.gz.gzItemPersonType.service.gzItemPersonTypeManager;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.gz.incomeTaxRate.model.IncomeTaxRate;
import com.huge.ihos.gz.incomeTaxRate.service.IncomeTaxRateManager;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;

public class gzItemPersonTypePagedAction extends JqGridBaseAction implements
		Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3389349546084923805L;
	private gzItemPersonTypeManager gzItemPersonTypeManager;
	private List<GzItemPersonType> gzItemPersonTypes;
	private GzItemPersonType gzItemPersonType;
	private String mappingId;
	private List<GzItemFormula> formulaNodes;
	private GzItemFormulaManager formulaManager;
	private GzItem gzItem;
	private List<GzItem> itemTreeNodes;
	private GzItemManager gzItemManager;
	private List<ZTreeSimpleNode> formulaTreeNodes;
	private GzTypeManager gzTypeManager;
	private IncomeTaxRateManager incomeTaxRateManager;
	private PersonTypeManager personTypeManager;

	public GzItemFormulaManager getFormulaManager() {
		return formulaManager;
	}

	@Autowired
	public void setFormulaManager(GzItemFormulaManager formulaManager) {
		this.formulaManager = formulaManager;
	}

	public List<GzItemFormula> getFormulaNodes() {
		return formulaNodes;
	}

	public void setFormulaNodes(List<GzItemFormula> formulaNodes) {
		this.formulaNodes = formulaNodes;
	}

	public List<ZTreeSimpleNode> getFormulaTreeNodes() {
		return formulaTreeNodes;
	}

	public void setFormulaTreeNodes(List<ZTreeSimpleNode> formulaTreeNodes) {
		this.formulaTreeNodes = formulaTreeNodes;
	}

	public GzItem getGzItem() {
		return gzItem;
	}

	public void setGzItem(GzItem gzItem) {
		this.gzItem = gzItem;
	}

	public List<GzItem> getItemTreeNodes() {
		return itemTreeNodes;
	}

	public void setItemTreeNodes(List<GzItem> itemTreeNodes) {
		this.itemTreeNodes = itemTreeNodes;
	}

	public GzItemManager getGzItemManager() {
		return gzItemManager;
	}

	@Autowired
	public void setGzItemManager(GzItemManager gzItemManager) {
		this.gzItemManager = gzItemManager;
	}

	public gzItemPersonTypeManager getgzItemPersonTypeManager() {
		return gzItemPersonTypeManager;
	}

	public void setgzItemPersonTypeManager(gzItemPersonTypeManager gzItemPersonTypeManager) {
		this.gzItemPersonTypeManager = gzItemPersonTypeManager;
	}

	public List<GzItemPersonType> getgzItemPersonTypes() {
		return gzItemPersonTypes;
	}

	public void setGzItemPersonTypes(List<GzItemPersonType> gzItemPersonTypes) {
		this.gzItemPersonTypes = gzItemPersonTypes;
	}

	public GzItemPersonType getGzItemPersonType() {
		return gzItemPersonType;
	}

	public void setGzItemPersonType(GzItemPersonType gzItemPersonType) {
		this.gzItemPersonType = gzItemPersonType;
	}

	public List<GzItemPersonType> getGzItemPersonTypes() {
		return gzItemPersonTypes;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	
	
  
	public GzTypeManager getGzTypeManager() {
		return gzTypeManager;
	}
	@Autowired
	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}

	public void prepare() throws Exception {
		HttpServletRequest request = this.getRequest();
		String NowgzType = "";
		GzType curGzType = gzTypeManager.getCurrentGzType(getSessionUser().getId().toString());
		if(OtherUtil.measureNotNull(curGzType)){
			NowgzType = curGzType.getGzTypeId();
		}
		request.setAttribute("gztypes", gzTypeManager.allGzTypes(false));
		request.setAttribute("now", NowgzType);
		this.clearSessionMessages();
	}

	public String gzItemPersonTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = gzItemPersonTypeManager
					.getgzItemPersonTypeCriteria(pagedRequests, filters);
			this.gzItemPersonTypes = (List<GzItemPersonType>) pagedRequests
					.getList();
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
			// 这里需要去重处理
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_gzTypeId", gzItemPersonType
					.getGzTypeId()));
			filters.add(new PropertyFilter("EQS_empType", gzItemPersonType
					.getEmpType()));
			List<GzItemPersonType> exist = gzItemPersonTypeManager
					.getByFilters(filters);
			if (exist.size() != 0) {
				return ajaxForwardError("此关联已经存在，请勿重复保存！");
			}
			gzItemPersonTypeManager.save(gzItemPersonType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward(this.getText("保存成功。"));
	}

	public String saveGzPersonType() {
		try {
			HttpServletRequest request = this.getRequest();
			String fomulaIds = request.getParameter("fomulaIds");
			String personTypeId = request.getParameter("personTypeId");
			String gzTypeId = request.getParameter("gzTypeId");
			List<String> sqlList = new ArrayList<String>();
			String sql = "";
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_gzTypeId",gzTypeId));
			filters.add(new PropertyFilter("EQS_empType",personTypeId));
			gzItemPersonTypes = gzItemPersonTypeManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(gzItemPersonTypes)&&!gzItemPersonTypes.isEmpty()){
				filters.clear();
				filters.add(new PropertyFilter("EQS_gzItem.gzType.gzTypeId",gzTypeId));
				filters.add(new PropertyFilter("EQS_gzItem.calculateType","0"));
				List<String> notDelFormulaIdList = new ArrayList<String>();
				List<String> needDelPTidList = new ArrayList<String>();
				List<GzItemFormula> gzItemFormulas = gzItemFormulaManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(gzItemFormulas)&&!gzItemFormulas.isEmpty()){
					for(GzItemFormula gifTemp:gzItemFormulas){
						notDelFormulaIdList.add(gifTemp.getFormulaId());
					}
				}
				for(GzItemPersonType gptTemp:gzItemPersonTypes){
					String formulaIdTemp = gptTemp.getFormulaId();
					String mappingIdTemp = gptTemp.getMappingId();
					if(notDelFormulaIdList.contains(formulaIdTemp)){
						sql += "'" + formulaIdTemp + "',";
					}else{
						needDelPTidList.add(mappingIdTemp);
					}
				}
				String[] removePTidArr = new String[needDelPTidList.size()];
				gzItemPersonTypeManager.remove(needDelPTidList.toArray(removePTidArr));
			}
			if(OtherUtil.measureNotNull(fomulaIds)){
				gzItemPersonTypes = new ArrayList<GzItemPersonType>();
				String[] fomulaIdArr = fomulaIds.split(",");
				for(String fomulaIdTemp:fomulaIdArr){
					GzItemPersonType gzItemPersonTypeTemp = new GzItemPersonType();
					gzItemPersonTypeTemp.setFormulaId(fomulaIdTemp);
					gzItemPersonTypeTemp.setGzTypeId(gzTypeId);
					gzItemPersonTypeTemp.setEmpType(personTypeId);
					gzItemPersonTypes.add(gzItemPersonTypeTemp);
					sql += "'" + fomulaIdTemp + "',";
				}
				gzItemPersonTypeManager.saveAll(gzItemPersonTypes);
			}
			/*公式修改为已使用状态*/
			if(OtherUtil.measureNotNull(sql)){
				sql = OtherUtil.subStrEnd(sql, ",");
				String sqlTemp = sql;
				sql = "UPDATE gz_gzItemFormula SET inUsed = '1' WHERE formulaId IN ("+sql+")";
				sqlList.add(sql);
				sql = "UPDATE gz_gzItemFormula SET inUsed = '0' WHERE formulaId NOT IN ("+sqlTemp+")";
				sqlList.add(sql);
				gzItemPersonTypeManager.executeSqlList(sqlList);
			}
			
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForwardSuccess("保存成功。");
	}

	public String edit() {
		if (mappingId != null) {
			gzItemPersonType = gzItemPersonTypeManager.get(mappingId);
			this.setEntityIsNew(false);
		} else {
			gzItemPersonType = new GzItemPersonType();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String gzItemPersonTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					GzItemPersonType gzItemPersonType = gzItemPersonTypeManager
							.get(new String(removeId));
					gzItemPersonTypeManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("gzItemPersonType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkgzItemPersonTypeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gzItemPersonType == null) {
			return "Invalid gzItemPersonType Data";
		}

		return SUCCESS;

	}

	public String makeformulaMiddleTree() {
		String gzType = ServletActionContext.getRequest().getParameter("gzType");
 		formulaTreeNodes = new ArrayList<ZTreeSimpleNode>();
 		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
 		filters.add(new PropertyFilter("EQS_gzType.gzTypeId", gzType));
 		filters.add(new PropertyFilter("EQS_calculateType", "1"));
 		filters.add(new PropertyFilter("EQB_disabled","false"));
 		filters.add(new PropertyFilter("OAI_sn",""));
 		//filters.add(new PropertyFilter("ISNULLS_necessary",""));
		String orgIcon = this.getContextPath()
				+ "/scripts/zTree/css/zTreeStyle/img/diy/1_close.png";
		try {
			itemTreeNodes = gzItemManager.getByFilters(filters);
			ZTreeSimpleNode rootNode = new ZTreeSimpleNode();
			rootNode.setId("-1");
			rootNode.setName("工资项");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setIcon(orgIcon);
			formulaTreeNodes.add(rootNode);
			String orgIcon2 = this.getContextPath()
					+ "/scripts/zTree/css/zTreeStyle/img/diy/leaf.png";
			if (itemTreeNodes != null && itemTreeNodes.size() > 0) {
				for (GzItem item : itemTreeNodes) {
					ZTreeSimpleNode typeNode = new ZTreeSimpleNode();
					typeNode.setId(item.getItemId());
					//typeNode.setName(item.getItemName());
					typeNode.setName(item.getItemShowName());
					typeNode.setIsParent(true);
					typeNode.setSubSysTem("TYPE");
					typeNode.setIcon(orgIcon2);
					if (typeNode != null) {
						typeNode.setpId("-1");
					}
					String gzItemId = item.getItemId();
					filters.clear();
					filters.add(new PropertyFilter("EQS_gzItem.itemId",gzItemId));
					filters.add(new PropertyFilter("OAS_sn",""));
					formulaNodes = gzItemFormulaManager.getByFilters(filters);
					if(OtherUtil.measureNull(formulaNodes)||formulaNodes.isEmpty()){
						continue;
					}else{
						formulaTreeNodes.add(typeNode);
						String orgIcon3 = this.getContextPath()
								+ "/scripts/zTree/css/zTreeStyle/img/diy/2.png";
						for (GzItemFormula formula : formulaNodes) {
							ZTreeSimpleNode typeChildNode = new ZTreeSimpleNode();
							typeChildNode.setId(formula.getFormulaId());
							typeChildNode.setName(formula.getName());
							typeChildNode.setIsParent(false);
							typeChildNode.setSubSysTem("TYPE");
							typeChildNode.setIcon(orgIcon3);

							if (typeChildNode != null) {
								typeChildNode.setpId(formula.getGzItem()
										.getItemId());
							}
							formulaTreeNodes.add(typeChildNode);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("makeHrPersonTypeTree Error", e);
		}
		return SUCCESS;
	}

	public void getShowNodes() {
		HttpServletRequest request = this.getRequest();
		String empType = request.getParameter("empType");
		String gzType = request.getParameter("gzType");
		String nodes = "";
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_empType", empType));
		filters.add(new PropertyFilter("EQS_gzTypeId", gzType));
		gzItemPersonTypes = gzItemPersonTypeManager.getByFilters(filters);
		String gzFormulaIds = "";
 		if (OtherUtil.measureNotNull(gzItemPersonTypes)&&!gzItemPersonTypes.isEmpty()) {
			for(GzItemPersonType gptTemp:gzItemPersonTypes){
				gzFormulaIds += gptTemp.getFormulaId()+",";
			}
			gzFormulaIds = OtherUtil.subStrEnd(gzFormulaIds, ",");
			filters.clear();
			filters.add(new PropertyFilter("EQS_gzItem.calculateType","1"));
			filters.add(new PropertyFilter("INS_formulaId",gzFormulaIds));
			List<GzItemFormula> gzItemFormulas = gzItemFormulaManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(gzItemFormulas)&&!gzItemFormulas.isEmpty()){
				for(GzItemFormula gifTemp:gzItemFormulas){
					nodes += gifTemp.getFormulaId()+",";
				}
			}
		}
 		if(OtherUtil.measureNotNull(nodes)){
 			nodes = OtherUtil.subStrEnd(nodes, ",");
 		}else{
			nodes = "null";
		}
		try {
			ServletActionContext.getResponse().setContentType("text/html"); 
			ServletActionContext.getResponse().getWriter().write("{'nodes':'"+nodes+"'}");  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private GzItemFormulaManager gzItemFormulaManager;
	public GzItemFormulaManager getGzItemFormulaManager() {
		return gzItemFormulaManager;
	}

	public void setGzItemFormulaManager(GzItemFormulaManager gzItemFormulaManager) {
		this.gzItemFormulaManager = gzItemFormulaManager;
	}
	private Map<String, List<JSONObject>> jsonMap;
	public Map<String, List<JSONObject>> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, List<JSONObject>> jsonMap) {
		this.jsonMap = jsonMap;
	}
	public String getCurrentGzContentFormula(){
		String gzTypeId = this.getRequest().getParameter("gzTypeId");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzTypeId",gzTypeId));		
		this.gzItemPersonTypes = this.gzItemPersonTypeManager.getByFilters(filters);
		jsonMap = new HashMap<String, List<JSONObject>>();
		String calTaxRateBySysFunc = this.getGlobalParamByKey("calTaxRateBySysFunc");//使用系统方法计算个人所得税：0--不使用，1--使用
		if(OtherUtil.measureNotNull(gzItemPersonTypes)&&!gzItemPersonTypes.isEmpty()){
			List<JSONObject> jsonObjectListTemp = null;
			for(GzItemPersonType gzItemPersonType:gzItemPersonTypes){
				String formulaId = gzItemPersonType.getFormulaId();
				String empType = gzItemPersonType.getEmpType();
				if(jsonMap.containsKey(empType)){
					jsonObjectListTemp = jsonMap.get(empType);
					if(OtherUtil.measureNull(jsonObjectListTemp)){
						jsonObjectListTemp = new ArrayList<JSONObject>();
					}
				}else{
					jsonObjectListTemp = new ArrayList<JSONObject>();
				}
				GzItemFormula gzItemFormula = gzItemFormulaManager.get(formulaId);
				String itemName = gzItemFormula.getGzItem().getItemName();
				String calculateType = gzItemFormula.getGzItem().getCalculateType();
				if("1".equals(calTaxRateBySysFunc)&&"个人所得税".equals(itemName)){
					continue;
				}
				if("0".equals(calculateType)||"存零".equals(itemName)||"补零".equals(itemName)){
					continue;
				}
				JSONObject jsonTemp = new JSONObject();
				jsonTemp.put("gzItem", gzItemFormula.getGzItem().getItemCode());
				jsonTemp.put("name", gzItemFormula.getGzItem().getItemCode()+"_"+gzItemFormula.getName());
				jsonTemp.put("sn", gzItemFormula.getSn()==null?0:gzItemFormula.getSn());
				if(OtherUtil.measureNull(gzItemFormula.getConditionFormula())){
					jsonTemp.put("conditionFormula", "true");
				}else{
					jsonTemp.put("conditionFormula", gzItemFormula.getConditionFormula());
				}
				jsonTemp.put("conditionParameter", gzItemFormula.getConditionParameter());
				jsonTemp.put("conditionParameterDataType", gzItemFormula.getConditionParameterDataType());
				String resultFormulaExp = gzItemFormula.getResultFormulaExp();
				if(gzItemFormula.getOtherExped()){
					int otherExpType = gzItemFormula.getOtherExpType();
					double otherExpValue = gzItemFormula.getOtherExpValue();
					switch (otherExpType) {
					case 1://1：增加;2:减少;3:乘系数
						resultFormulaExp = "(" + resultFormulaExp + ") + "+otherExpValue;
						break;
					case 2:
						resultFormulaExp = "(" + resultFormulaExp + ") - "+otherExpValue;
						break;
					case 3:
						resultFormulaExp = "(" + resultFormulaExp + ") * "+otherExpValue;
						break;
					default:
						break;
					}
				}
				jsonTemp.put("resultFormula", resultFormulaExp);
				jsonTemp.put("resultParameter", gzItemFormula.getResultParameter());
				jsonTemp.put("resultParameterDataType", gzItemFormula.getResultParameterDataType());
				jsonObjectListTemp.add(jsonTemp);
				jsonMap.put(empType, jsonObjectListTemp);
			}
		}
		/*计算个人所得税*/
		filters.clear();
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("OAI_incomeFloor",""));
		List<IncomeTaxRate> iTaxRates = incomeTaxRateManager.getByFilters(filters);
		filters.clear();
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("EQB_leaf","1"));
		List<PersonType> personTypes = personTypeManager.getByFilters(filters);
		GzItem isTaxItem = gzItemManager.getIsTaxGzItem(gzTypeId);//计税依据
		GzItem taxItem = gzItemManager.getGzItemByName(gzTypeId,"个人所得税");//个人所得税
		if("1".equals(calTaxRateBySysFunc)&&OtherUtil.measureNotNull(iTaxRates)&&!iTaxRates.isEmpty()&&OtherUtil.measureNotNull(isTaxItem)
				&&OtherUtil.measureNotNull(taxItem)&&OtherUtil.measureNotNull(personTypes)
				&&!personTypes.isEmpty()){
			for(PersonType personType:personTypes){
				List<JSONObject> jsonObjectListTemp = null;
				String empType = personType.getName();
				if(jsonMap.containsKey(empType)){
					jsonObjectListTemp = jsonMap.get(empType);
					if(OtherUtil.measureNull(jsonObjectListTemp)){
						jsonObjectListTemp = new ArrayList<JSONObject>();
					}
				}else{
					jsonObjectListTemp = new ArrayList<JSONObject>();
				}
				int taxSn = 0;
				for(IncomeTaxRate iTaxRate:iTaxRates){
					JSONObject jsonTemp = new JSONObject();
					jsonTemp.put("gzItem", taxItem.getItemCode());
					jsonTemp.put("name", taxItem.getItemCode()+"_公式"+taxSn);
					jsonTemp.put("sn", taxSn++);
					int incomeFloor = iTaxRate.getIncomeFloor();//下限
					int incomeTopLimit = iTaxRate.getIncomeTopLimit();//上限
					int baseNum = iTaxRate.getBaseNum();//基数
					double fullTaxCost= iTaxRate.getFullTaxCost();//速算扣除数
					double taxRate = iTaxRate.getTaxRate();//税率
					String conditionFormula = "(["+isTaxItem.getItemCode()+"]-"+baseNum+">"+incomeFloor+")";
					conditionFormula += "&&(["+isTaxItem.getItemCode()+"]-"+baseNum+"<="+incomeTopLimit+")";
					jsonTemp.put("conditionFormula", conditionFormula);
					jsonTemp.put("conditionParameter", isTaxItem.getItemCode());
					jsonTemp.put("conditionParameterDataType", "0");
					String resultFormula = "(["+isTaxItem.getItemCode()+"]-"+baseNum+")*"+taxRate+"/100-"+fullTaxCost;
					jsonTemp.put("resultFormula", resultFormula);
					jsonTemp.put("resultParameter", isTaxItem.getItemCode());
					jsonTemp.put("resultParameterDataType", "0");
					jsonObjectListTemp.add(jsonTemp);
					jsonMap.put(empType, jsonObjectListTemp);
				}
			}
		}
		/*存零补零*/
		String discountZero = this.getGlobalParamByKey("discountZero");
		GzItem saveZeroItem = gzItemManager.getGzItemByName(gzTypeId,"存零");//存零
		GzItem addZeroItem = gzItemManager.getGzItemByName(gzTypeId,"补零");//补零
		GzItem realGzItem = gzItemManager.getGzItemByName(gzTypeId,"实发合计");//实发合计
		if(OtherUtil.measureNotNull(saveZeroItem)&&OtherUtil.measureNotNull(addZeroItem)
				&&OtherUtil.measureNotNull(realGzItem)&&OtherUtil.measureNotNull(personTypes)
				&&!personTypes.isEmpty()&&OtherUtil.measureNotNull(discountZero)){
			String addZeroItemCode = addZeroItem.getItemCode();
			String realItemCode = realGzItem.getItemCode();
			String saveZeroItemCode = saveZeroItem.getItemCode();
			String resultFormula = "";
			if("1".equals(discountZero)){//舍50元以下
				resultFormula = "(["+realItemCode+"]+["+addZeroItemCode+"])%50";
			}else if("2".equals(discountZero)){//舍元
				resultFormula = "(["+realItemCode+"]+["+addZeroItemCode+"])%10";
			}else if("3".equals(discountZero)){//舍角
				resultFormula = "(["+realItemCode+"]+["+addZeroItemCode+"])%1";
			}else if("4".equals(discountZero)){//舍分
				resultFormula = "(["+realItemCode+"]+["+addZeroItemCode+"])%0.1";
			}
			if(OtherUtil.measureNotNull(resultFormula)){
				for(PersonType personType:personTypes){
					List<JSONObject> jsonObjectListTemp = null;
					String empType = personType.getName();
					if(jsonMap.containsKey(empType)){
						jsonObjectListTemp = jsonMap.get(empType);
						if(OtherUtil.measureNull(jsonObjectListTemp)){
							jsonObjectListTemp = new ArrayList<JSONObject>();
						}
					}else{
						jsonObjectListTemp = new ArrayList<JSONObject>();
					}
					JSONObject jsonTemp = new JSONObject();
					jsonTemp.put("gzItem", saveZeroItemCode);
					jsonTemp.put("name", "存零");
					jsonTemp.put("sn", 0);
					jsonTemp.put("conditionFormula", "true");
					jsonTemp.put("conditionParameter", "");
					jsonTemp.put("conditionParameterDataType", "0");
					jsonTemp.put("resultFormula", resultFormula);
					jsonTemp.put("resultParameter", realItemCode+","+addZeroItemCode);
					jsonTemp.put("resultParameterDataType", "0");
					jsonTemp.put("callBackItem", realItemCode);
					jsonTemp.put("callBackFormula", "["+realItemCode+"]+["+addZeroItemCode+"]-["+saveZeroItemCode+"]");
					jsonTemp.put("callBackParameter", realItemCode+","+addZeroItemCode+","+saveZeroItemCode);
					jsonTemp.put("callBackParameterDataType", "0,0,0");
					jsonObjectListTemp.add(jsonTemp);
					jsonMap.put(empType, jsonObjectListTemp);
				}
			}
		}
		Iterator it = jsonMap.keySet().iterator();    
		while(it.hasNext()){    
		     String key = it.next().toString();    
		     List<JSONObject> jsonObjectListTemp = jsonMap.get(key);
		     //根据Collections.sort重载方法来实现  
		        Collections.sort(jsonObjectListTemp,new Comparator<JSONObject>(){  
		            @Override  
		            public int compare(JSONObject b1, JSONObject b2) { 
		            	int bsn1 = Integer.parseInt(b1.get("sn").toString());
		            	int bsn2 = Integer.parseInt(b2.get("sn").toString());
		                return bsn1 - bsn2;  
		            }  
		        });
		        jsonMap.put(key, jsonObjectListTemp);
		}
		return SUCCESS;
	}

	public IncomeTaxRateManager getIncomeTaxRateManager() {
		return incomeTaxRateManager;
	}

	public void setIncomeTaxRateManager(IncomeTaxRateManager incomeTaxRateManager) {
		this.incomeTaxRateManager = incomeTaxRateManager;
	}

	public PersonTypeManager getPersonTypeManager() {
		return personTypeManager;
	}

	public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}
}
