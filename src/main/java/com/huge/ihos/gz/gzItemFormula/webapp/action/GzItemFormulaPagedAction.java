package com.huge.ihos.gz.gzItemFormula.webapp.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormula;
import com.huge.ihos.gz.gzItemFormula.model.GzItemNode;
import com.huge.ihos.gz.gzItemFormula.service.GzItemFormulaManager;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class GzItemFormulaPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6710874219513588011L;
	private GzItemFormulaManager gzItemFormulaManager;
	private List<GzItemFormula> gzItemFormulas;
	private GzItemFormula gzItemFormula;
	private String formulaId;
	private GzItemManager gzItemManager;
	private List<GzItem> gzItems;
	private String itemId;
	private List<GzItemNode> gzItemNodes;
	private String gzItemFormulaJsonStr; 
	private String name;
	private String gzTypeId;
	private String filterItemCode;

	public GzItemFormulaManager getGzItemFormulaManager() {
		return gzItemFormulaManager;
	}

	public void setGzItemFormulaManager(GzItemFormulaManager gzItemFormulaManager) {
		this.gzItemFormulaManager = gzItemFormulaManager;
	}

	public List<GzItemFormula> getgzItemFormulas() {
		return gzItemFormulas;
	}

	public void setGzItemFormulas(List<GzItemFormula> gzItemFormulas) {
		this.gzItemFormulas = gzItemFormulas;
	}

	public GzItemFormula getGzItemFormula() {
		return gzItemFormula;
	}

	public void setGzItemFormula(GzItemFormula gzItemFormula) {
		this.gzItemFormula = gzItemFormula;
	}

	public String getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

	public GzItemManager getGzItemManager() {
		return gzItemManager;
	}

	public void setGzItemManager(GzItemManager gzItemManager) {
		this.gzItemManager = gzItemManager;
	}

	public List<GzItem> getgzItems() {
		return gzItems;
	}

	public void setGzItems(List<GzItem> gzItems) {
		this.gzItems = gzItems;
	}
	
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String gzItemFormulaGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(OtherUtil.measureNotNull(itemId)){
				filters.add(new PropertyFilter("EQS_gzItem.itemId",itemId));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = gzItemFormulaManager
					.getGzItemFormulaCriteria(pagedRequests,filters);
			this.gzItemFormulas = (List<GzItemFormula>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String gzItemGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String gzTypeId = request.getParameter("filter_EQS_gzTypeId");
			if(OtherUtil.measureNull(gzTypeId)){
				filters.add(new PropertyFilter("EQS_gzTypeId","0"));
			}
			filters.add(new PropertyFilter("EQS_calculateType","1"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = gzItemManager
					.getGzItemCriteria(pagedRequests,filters);
			this.gzItems = (List<GzItem>) pagedRequests.getList();
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
//			gzItemFormulaManager.save(gzItemFormula);
			gzItemFormulaManager.saveGzItemFormula(gzItemFormula, gzItemFormulaJsonStr);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "gzItemFormula.added" : "gzItemFormula.updated";
		return ajaxForward(this.getText(key));
	}
	private String gzItemFormulaCheckStr;
    public String edit() {
    	List<PropertyFilter> jsonFilters = new ArrayList<PropertyFilter>();
    	String gzTypeId = "";
        if (formulaId != null) {
        	gzItemFormula = gzItemFormulaManager.get(formulaId);
        	gzTypeId = gzItemFormula.getGzItem().getGzType().getGzTypeId();
        	jsonFilters.add(new PropertyFilter("NES_formulaId",formulaId));
        	this.setEntityIsNew(false);
        } else {
        	gzItemFormula = new GzItemFormula();
        	if(OtherUtil.measureNotNull(itemId)){
        		GzItem gzItem = gzItemManager.get(itemId);
        		gzTypeId = gzItem.getGzType().getGzTypeId();
        		gzItemFormula.setGzItem(gzItem);
        		String name = gzItem.getItemName()+"公式";
            	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
            	filters.add(new PropertyFilter("EQS_gzItem.itemId",itemId));
            	filters.add(new PropertyFilter("ODI_sn",""));
            	this.gzItemFormulas = gzItemFormulaManager.getByFilters(filters);
            	if(OtherUtil.measureNotNull(gzItemFormulas)&&!gzItemFormulas.isEmpty()){
            		name = name + (gzItemFormulas.size()+1);
            		gzItemFormula.setSn(gzItemFormulas.get(0).getSn()+1);
            	}else{
            		name = name + 1;
            		gzItemFormula.setSn(0);
            	}
            	gzItemFormula.setName(name);
        	}
        	this.setEntityIsNew(true);
        }
        jsonFilters.add(new PropertyFilter("EQS_gzItem.gzType.gzTypeId",gzTypeId));
        this.gzItemFormulas = gzItemFormulaManager.getByFilters(jsonFilters);
        JSONArray jsonArray = new JSONArray();
        if(OtherUtil.measureNotNull(gzItemFormulas)&&!gzItemFormulas.isEmpty()){
        	for(GzItemFormula fTemp:gzItemFormulas){
        		JSONObject jsonObject = new JSONObject();
        		jsonObject.put("itemName", fTemp.getGzItem().getItemName());
        		jsonObject.put("itemCode", fTemp.getGzItem().getItemCode());
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
        gzItemFormulaCheckStr = jsonArray.toString();
        return SUCCESS;
    }
    
    private PersonTypeManager personTypeManager;
    public String getGzItemNodesForFormula(){
    	 gzItemNodes = new ArrayList<GzItemNode>();
    	 String nodeNameStr = "";
    	 /*部门*/
         GzItemNode node = null;
         node = new GzItemNode();
         node.setId("deptName");
         node.setName("部门名称");
         node.setType(1);
         node.setIsParent(false);
         nodeNameStr += "deptName,";
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("deptCode");
         node.setName("部门编码");
         node.setType(1);
         node.setIsParent(false);
         nodeNameStr += "deptCode,";
         gzItemNodes.add(node);
         /*人员*/
         node = new GzItemNode();
         node.setId("personName");
         node.setName("人员姓名");
         node.setType(1);
         node.setIsParent(false);
         nodeNameStr += "personName,";
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("personCode");
         node.setName("人员编码");
         node.setType(1);
         node.setIsParent(false);
         nodeNameStr += "personCode,";
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("sex");
         node.setName("性别");
         node.setType(5);
         node.setValues(this.getDictionaryItemManager().getAllItemValuesByCode( "sex" ));
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("birthday");
         node.setName("出生日期");
         node.setType(4);
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("duty");
         node.setName("职务");
         node.setType(1);
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("educationalLevel");
         node.setName("学历");
         node.setType(5);
         node.setValues(this.getDictionaryItemManager().getAllItemValuesByCode( "education" ));
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("salaryNumber");
         node.setName("工资号");
         node.setType(1);
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("idNumber");
         node.setName("身份证号");
         node.setType(1);
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("jobTitle");
         node.setName("职称");
         node.setType(5);
         node.setValues(this.getDictionaryItemManager().getAllItemValuesByCode( "jobTitle" ));
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("postType");
         node.setName("岗位类别");
         node.setType(5);
         node.setValues(this.getDictionaryItemManager().getAllItemValuesByCode( "postType" ));
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("ratio");
         node.setName("系数");
         node.setType(2);
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("workBegin");
         node.setName("参加工作时间");
         node.setType(4);
         node.setIsParent(false);
         gzItemNodes.add(node);
         node = new GzItemNode();
         node.setId("empType");
         node.setName("人员类别");
         node.setType(5);
         node.setValues(personTypeManager.personTypeNameList());
         node.setIsParent(false);
         nodeNameStr += "empType,";
         gzItemNodes.add(node);
         nodeNameStr += "personId,deptId,orgCode,orgName";
         String itemCode = this.getRequest().getParameter("itemCode");
         List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
 		 filters.add(new PropertyFilter("EQB_disabled","0"));
 		 filters.add(new PropertyFilter("NIS_itemCode",nodeNameStr));
 		 if(OtherUtil.measureNotNull(itemCode)){
 			filters.add(new PropertyFilter("NES_itemCode",itemCode));
 		 }
 		 filters.add(new PropertyFilter("INS_itemType","0,3"));
 		 filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
 		 filters.add(new PropertyFilter("OAI_sn",""));
 		 gzItems = gzItemManager.getByFilters(filters);
 		 if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
 			for(GzItem gzItemTemp:gzItems){
 				GzItemNode itemNode = new GzItemNode();
 				itemNode.setId(gzItemTemp.getItemCode());
 				itemNode.setName(gzItemTemp.getItemName());
 				String itemType = gzItemTemp.getItemType();
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
 				gzItemNodes.add(itemNode);
 			}
 		}
         return SUCCESS;
    }
    
	public String gzItemFormulaGridEdit() {
		try {
			if (oper.equals("del")) {
				/*StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					gzItemFormulaManager.remove(new String(removeId));
				}*/
				gzItemFormulaManager.deleteGzItemFormula(id);
				gridOperationMessage = this.getText("gzItemFormula.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGzItemFormulaGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gzItemFormula == null) {
			return "Invalid gzItemFormula Data";
		}

		return SUCCESS;

	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public List<GzItemNode> getGzItemNodes() {
		return gzItemNodes;
	}

	public void setGzItemNodes(List<GzItemNode> gzItemNodes) {
		this.gzItemNodes = gzItemNodes;
	}

	public String getGzItemFormulaJsonStr() {
		return gzItemFormulaJsonStr;
	}

	public void setGzItemFormulaJsonStr(String gzItemFormulaJsonStr) {
		this.gzItemFormulaJsonStr = gzItemFormulaJsonStr;
	}
	
	public String gzItemFormulaNameCheck(){
		List<PropertyFilter> filters = new  ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzItem.itemId",itemId));
		filters.add(new PropertyFilter("EQS_name",name));
		this.gzItemFormulas = gzItemFormulaManager.getByFilters(filters);
		String returnMessage = "该名称已存在！";
		if(gzItemFormulas!=null && gzItemFormulas.size()>0){
			return ajaxForward( false, returnMessage, false );
		}else{
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGzTypeId() {
		return gzTypeId;
	}

	public void setGzTypeId(String gzTypeId) {
		this.gzTypeId = gzTypeId;
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

	public String getGzItemFormulaCheckStr() {
		return gzItemFormulaCheckStr;
	}

	public void setGzItemFormulaCheckStr(String gzItemFormulaCheckStr) {
		this.gzItemFormulaCheckStr = gzItemFormulaCheckStr;
	}
}

