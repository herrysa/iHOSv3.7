package com.huge.ihos.kq.kqUpData.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormula;
import com.huge.ihos.kq.kqUpData.model.KqUpItemNode;
import com.huge.ihos.kq.kqUpData.service.KqUpItemFormulaManager;
import com.huge.ihos.kq.kqUpData.service.KqUpItemManager;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class KqUpItemFormulaPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6926731446595202376L;
	private KqUpItemFormulaManager kqUpItemFormulaManager;
	private List<KqUpItemFormula> kqUpItemFormulas;
	private KqUpItemFormula kqUpItemFormula;
	private String formulaId;
	private String itemId;
	private String filterItemCode;

	public KqUpItemFormulaManager getKqUpItemFormulaManager() {
		return kqUpItemFormulaManager;
	}

	public void setKqUpItemFormulaManager(KqUpItemFormulaManager kqUpItemFormulaManager) {
		this.kqUpItemFormulaManager = kqUpItemFormulaManager;
	}

	public List<KqUpItemFormula> getkqUpItemFormulas() {
		return kqUpItemFormulas;
	}

	public void setKqUpItemFormulas(List<KqUpItemFormula> kqUpItemFormulas) {
		this.kqUpItemFormulas = kqUpItemFormulas;
	}

	public KqUpItemFormula getKqUpItemFormula() {
		return kqUpItemFormula;
	}

	public void setKqUpItemFormula(KqUpItemFormula kqUpItemFormula) {
		this.kqUpItemFormula = kqUpItemFormula;
	}

	public String getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String kqUpItemFormulaGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(OtherUtil.measureNotNull(itemId)){
				filters.add(new PropertyFilter("EQS_kqUpItem.itemId",itemId));
			}else{
				filters.add(new PropertyFilter("EQS_kqUpItem.itemId",""));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = kqUpItemFormulaManager
					.getKqUpItemFormulaCriteria(pagedRequests,filters);
			this.kqUpItemFormulas = (List<KqUpItemFormula>) pagedRequests.getList();
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
			String kqUpItemFormulaJsonStr = this.getRequest().getParameter("kqUpItemFormulaJsonStr");
			kqUpItemFormulaManager.saveKqUpItemFormula(kqUpItemFormula, kqUpItemFormulaJsonStr);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "kqUpItemFormula.added" : "kqUpItemFormula.updated";
		return ajaxForward(this.getText(key));
	}
	private String kqTypeId;
	private String kqUpItemFormulaCheckStr;
	private KqUpItemManager kqUpItemManager;
    public String edit() {
    	List<PropertyFilter> jsonFilters = new ArrayList<PropertyFilter>();
        if (formulaId != null) {
        	kqUpItemFormula = kqUpItemFormulaManager.get(formulaId);
        	jsonFilters.add(new PropertyFilter("NES_formulaId",formulaId));
        	this.setEntityIsNew(false);
        } else {
        	kqUpItemFormula = new KqUpItemFormula();
        	if(OtherUtil.measureNotNull(itemId)){
        		KqUpItem kqUpItem = kqUpItemManager.get(itemId);
        		kqUpItemFormula.setKqUpItem(kqUpItem);
        		String name = kqUpItem.getItemName()+"公式";
            	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
            	filters.add(new PropertyFilter("EQS_kqUpItem.itemId",itemId));
            	filters.add(new PropertyFilter("ODI_sn",""));
            	this.kqUpItemFormulas = kqUpItemFormulaManager.getByFilters(filters);
            	if(OtherUtil.measureNotNull(kqUpItemFormulas)&&!kqUpItemFormulas.isEmpty()){
            		name = name + (kqUpItemFormulas.size()+1);
            		kqUpItemFormula.setSn(kqUpItemFormulas.get(0).getSn()+1);
            	}else{
            		name = name + 1;
            		kqUpItemFormula.setSn(0);
            	}
            	kqUpItemFormula.setName(name);
        	}
        	this.setEntityIsNew(true);
        }
        jsonFilters.add(new PropertyFilter("EQS_kqUpItem.kqType.kqTypeId",kqTypeId));
        this.kqUpItemFormulas = kqUpItemFormulaManager.getByFilters(jsonFilters);
        JSONArray jsonArray = new JSONArray();
        if(OtherUtil.measureNotNull(kqUpItemFormulas)&&!kqUpItemFormulas.isEmpty()){
        	for(KqUpItemFormula fTemp:kqUpItemFormulas){
        		JSONObject jsonObject = new JSONObject();
        		jsonObject.put("itemName", fTemp.getKqUpItem().getItemName());
        		jsonObject.put("itemCode", fTemp.getKqUpItem().getItemCode());
        		jsonObject.put("formulaName", fTemp.getName());
        		String cpStr = fTemp.getConditionParameter();
        		JSONObject cpObject = new JSONObject();
        		if(OtherUtil.measureNotNull(cpStr)){
        			String[] cps = cpStr.split(",");
        			for(String cpTemp:cps){
        				cpObject.put(cpTemp, "cp");
        			}
        		}
        		jsonObject.put("cps", cpObject);
        		String rpStr = fTemp.getResultParameter();
        		JSONObject rpObject = new JSONObject();
        		if(OtherUtil.measureNotNull(rpStr)){
        			String[] rps = rpStr.split(",");
        			for(String rpTemp:rps){
        				rpObject.put(rpTemp, "rp");
        			}
        		}
        		jsonObject.put("cps", cpObject);
        		jsonObject.put("rps", rpObject);
        		jsonArray.add(jsonObject);
        	}
        }
        kqUpItemFormulaCheckStr = jsonArray.toString();
        return SUCCESS;
    }
	public String kqUpItemFormulaGridEdit() {
		try {
			if (oper.equals("del")) {
				kqUpItemFormulaManager.deleteKqUpItemFormula(id);
				gridOperationMessage = this.getText("kqUpItemFormula.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKqUpItemFormulaGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (kqUpItemFormula == null) {
			return "Invalid kqUpItemFormula Data";
		}

		return SUCCESS;

	}
	private List<KqUpItemNode> kqUpItemNodes;
	private PersonTypeManager personTypeManager;
	public String getKqUpItemNodesForFormula(){
		 kqUpItemNodes = new ArrayList<KqUpItemNode>();
    	 String nodeNameStr = "";
    	 /*部门*/
    	 KqUpItemNode node = null;
         node = new KqUpItemNode();
         node.setId("deptName");
         node.setName("部门名称");
         node.setType(1);
         node.setIsParent(false);
         nodeNameStr += "deptName,";
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("deptCode");
         node.setName("部门编码");
         node.setType(1);
         node.setIsParent(false);
         nodeNameStr += "deptCode,";
         kqUpItemNodes.add(node);
         /*人员*/
         node = new KqUpItemNode();
         node.setId("personName");
         node.setName("人员姓名");
         node.setType(1);
         node.setIsParent(false);
         nodeNameStr += "personName,";
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("personCode");
         node.setName("人员编码");
         node.setType(1);
         node.setIsParent(false);
         nodeNameStr += "personCode,";
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("sex");
         node.setName("性别");
         node.setType(5);
         node.setValues(this.getDictionaryItemManager().getAllItemValuesByCode( "sex" ));
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("birthday");
         node.setName("出生日期");
         node.setType(4);
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("duty");
         node.setName("职务");
         node.setType(1);
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("educationalLevel");
         node.setName("学历");
         node.setType(5);
         node.setValues(this.getDictionaryItemManager().getAllItemValuesByCode( "education" ));
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("salaryNumber");
         node.setName("工资号");
         node.setType(1);
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("idNumber");
         node.setName("身份证号");
         node.setType(1);
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("jobTitle");
         node.setName("职称");
         node.setType(5);
         node.setValues(this.getDictionaryItemManager().getAllItemValuesByCode( "jobTitle" ));
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("postType");
         node.setName("岗位类别");
         node.setType(5);
         node.setValues(this.getDictionaryItemManager().getAllItemValuesByCode( "postType" ));
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("ratio");
         node.setName("系数");
         node.setType(2);
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("workBegin");
         node.setName("参加工作时间");
         node.setType(4);
         node.setIsParent(false);
         kqUpItemNodes.add(node);
         node = new KqUpItemNode();
         node.setId("empType");
         node.setName("人员类别");
         node.setType(5);
         node.setValues(personTypeManager.personTypeNameList());
         node.setIsParent(false);
         nodeNameStr += "empType,";
         kqUpItemNodes.add(node);
         nodeNameStr += "personId,deptId,orgCode,orgName";
         String itemCode = this.getRequest().getParameter("itemCode");
         List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
 		 filters.add(new PropertyFilter("EQB_disabled","0"));
 		 filters.add(new PropertyFilter("NIS_itemCode",nodeNameStr));
 		 if(OtherUtil.measureNotNull(itemCode)){
 			filters.add(new PropertyFilter("NES_itemCode",itemCode));
 		 }
 		 filters.add(new PropertyFilter("INS_itemType","0,3"));
 		 filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
 		 filters.add(new PropertyFilter("OAI_sn",""));
 		 List<KqUpItem> kqUpItems = kqUpItemManager.getByFilters(filters);
 		 if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
 			for(KqUpItem kqUpItemTemp:kqUpItems){
 				KqUpItemNode itemNode = new KqUpItemNode();
 				itemNode.setId(kqUpItemTemp.getItemCode());
 				itemNode.setName(kqUpItemTemp.getItemName());
 				String itemType = kqUpItemTemp.getItemType();
 				itemNode.setParent(false);
 				if("0".equals(itemType)){
 					itemNode.setType(2);
 				}else if("1".equals(itemType)){
 					itemNode.setType(1);
 				}else if("2".equals(itemType)){
 					itemNode.setType(4);
 				}else if("3".equals(itemType)){
 					itemNode.setType(3);
 				}
 				kqUpItemNodes.add(itemNode);
 			}
 		}
         return SUCCESS;
	}
	
	private List<JSONObject> jsonList;
	public List<JSONObject> getJsonList() {
		return jsonList;
	}

	public void setJsonList(List<JSONObject> jsonList) {
		this.jsonList = jsonList;
	}
	/*公式整理*/
	public String getCurrentKqUpDataFormula(){
		String kqUpDataType = this.getRequest().getParameter("kqUpDataType");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if(!"1".equals(kqUpDataType)){//日上报的时候考勤项的公式不执行
			filters.add(new PropertyFilter("NES_kqUpItem.showType","kqItem"));
		}
    	filters.add(new PropertyFilter("EQS_kqUpItem.kqType.kqTypeId",kqTypeId));
    	filters.add(new PropertyFilter("OAI_kqUpItem.sn",""));
    	kqUpItemFormulas = kqUpItemFormulaManager.getByFilters(filters);
    	jsonList = new ArrayList<JSONObject>();
    	if(OtherUtil.measureNotNull(kqUpItemFormulas)&&!kqUpItemFormulas.isEmpty()){
    		for(KqUpItemFormula formulaTemp:kqUpItemFormulas){
				JSONObject jsonTemp = new JSONObject();
				jsonTemp.put("kqItem", formulaTemp.getKqUpItem().getItemCode());
				jsonTemp.put("name", formulaTemp.getKqUpItem().getItemCode()+"_"+formulaTemp.getName());
				jsonTemp.put("sn", formulaTemp.getSn()==null?0:formulaTemp.getSn());
				if(OtherUtil.measureNull(formulaTemp.getConditionFormula())){
					jsonTemp.put("conditionFormula", "true");
				}else{
					jsonTemp.put("conditionFormula", formulaTemp.getConditionFormula());
				}
				jsonTemp.put("conditionParameter", formulaTemp.getConditionParameter());
				jsonTemp.put("conditionParameterDataType", formulaTemp.getConditionParameterDataType());
				String resultFormulaExp = formulaTemp.getResultFormulaExp();
				if(formulaTemp.getOtherExped()){
					int otherExpType = formulaTemp.getOtherExpType();
					double otherExpValue = formulaTemp.getOtherExpValue();
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
				jsonTemp.put("resultParameter", formulaTemp.getResultParameter());
				jsonTemp.put("resultParameterDataType", formulaTemp.getResultParameterDataType());
				jsonList.add(jsonTemp);
    		}
    	}
    	 //根据Collections.sort重载方法来实现  
        Collections.sort(jsonList,new Comparator<JSONObject>(){  
            @Override  
            public int compare(JSONObject b1, JSONObject b2) { 
            	int bsn1 = Integer.parseInt(b1.get("sn").toString());
            	int bsn2 = Integer.parseInt(b2.get("sn").toString());
                return bsn1 - bsn2;  
            }  
        });
        return SUCCESS;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getKqTypeId() {
		return kqTypeId;
	}

	public void setKqTypeId(String kqTypeId) {
		this.kqTypeId = kqTypeId;
	}

	public String getKqUpItemFormulaCheckStr() {
		return kqUpItemFormulaCheckStr;
	}

	public void setKqUpItemFormulaCheckStr(String kqUpItemFormulaCheckStr) {
		this.kqUpItemFormulaCheckStr = kqUpItemFormulaCheckStr;
	}

	public KqUpItemManager getKqUpItemManager() {
		return kqUpItemManager;
	}

	public void setKqUpItemManager(KqUpItemManager kqUpItemManager) {
		this.kqUpItemManager = kqUpItemManager;
	}

	public List<KqUpItemNode> getKqUpItemNodes() {
		return kqUpItemNodes;
	}

	public void setKqUpItemNodes(List<KqUpItemNode> kqUpItemNodes) {
		this.kqUpItemNodes = kqUpItemNodes;
	}

	public PersonTypeManager getPersonTypeManager() {
		return personTypeManager;
	}

	public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}

	public String getFilterItemCode() {
		return filterItemCode;
	}

	public void setFilterItemCode(String filterItemCode) {
		this.filterItemCode = filterItemCode;
	}
}

