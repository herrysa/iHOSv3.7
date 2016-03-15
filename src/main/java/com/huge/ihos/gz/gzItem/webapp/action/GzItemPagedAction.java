package com.huge.ihos.gz.gzItem.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.gz.gzContent.model.GzContent;
import com.huge.ihos.gz.gzContent.service.GzContentManager;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormula;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormulaFilter;
import com.huge.ihos.gz.gzItemFormula.service.GzItemFormulaFilterManager;
import com.huge.ihos.gz.gzItemFormula.service.GzItemFormulaManager;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;




public class GzItemPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4853437791840665529L;
	private GzItemManager gzItemManager;
	private GzTypeManager gzTypeManager;
	private List<GzItem> gzItems;
	private GzItem gzItem;
	private String itemId;
	private String maxid;
	private String gzTypeId;
	

	public GzTypeManager getGzTypeManager() {
		return gzTypeManager;
	}

	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}

	public String getMaxid() {
		return maxid;
	}

	public void setMaxid(String maxid) {
		this.maxid = maxid;
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

	public GzItem getGzItem() {
		return gzItem;
	}

	public void setGzItem(GzItem gzItem) {
		this.gzItem = gzItem;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
        this.itemId = itemId;
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
	public String gzItemGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//加入为切换类别的当前筛选条件
			if(OtherUtil.measureNull(this.getRequest().getParameter("filter_EQS_gzType.gzTypeId"))){
				//当第一次进入时默认加入条件
			    filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
			}
			if("all".equals(oper)){
				this.gzItems = gzItemManager.getByFilters(filters);
			}else{
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(
						PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests = gzItemManager
						.getGzItemCriteria(pagedRequests,filters);
				this.gzItems = (List<GzItem>) pagedRequests.getList();
				records = pagedRequests.getTotalNumberOfRows();
				total = pagedRequests.getTotalNumberOfPages();
				page = pagedRequests.getPageNumber();
			}
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
			String oldName = this.getRequest().getParameter("oldName");
			gzItem = gzItemManager.saveGzItem(gzItem,this.isEntityIsNew(),oldName);
			itemId = gzItem.getItemId();
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "gzItem.added" : "gzItem.updated";
		return ajaxForward(this.getText(key));
	}
	
	private GzItemFormulaFilterManager gzItemFormulaFilterManager;
	//添加工资项
	public String addGzItem(){
		try {
			String itemIds = "";
			if(OtherUtil.measureNotNull(id)){
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					itemIds += ids.nextToken()+",";
				}
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_gzType.gzTypeId","XT"));
			filters.add(new PropertyFilter("EQB_sysField","1"));
			filters.add(new PropertyFilter("SQS_itemCode","itemCode NOT IN (SELECT itemCode FROM gz_gzItem  WHERE gzTypeId = '"+gzTypeId+"')"));
			gzItems = gzItemManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
				for(GzItem itemTemp:gzItems){
					itemIds += itemTemp.getItemId()+",";
				}
			}
			if(OtherUtil.measureNotNull(itemIds)){
				itemIds = OtherUtil.subStrEnd(itemIds, ",");
			}
			filters.clear();
			filters.add(new PropertyFilter("INS_itemId",itemIds));
			gzItems = gzItemManager.getByFilters(filters);
			filters.clear();
			filters.add(new PropertyFilter("INS_gzItem.itemId",itemIds));
			List<GzItemFormula> gzItemFormulas = gzItemFormulaManager.getByFilters(filters);
			filters.clear();
			filters.add(new PropertyFilter("INS_gzItemFormula.gzItem.itemId",itemIds));
			List<GzItemFormulaFilter> gzItemFormulaFilters = gzItemFormulaFilterManager.getByFilters(filters);
			List<String> sqlList = new ArrayList<String>();
			if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
				GzType gzType = gzTypeManager.get(gzTypeId);
				for(GzItem itemTemp:gzItems){
					itemTemp.setIsUsed(true);
					String itemIdTemp = itemTemp.getItemId();
					GzItem itemNew = itemTemp.clone();
					String itemIdNew = OtherUtil.getRandomUUID();
					itemId = itemIdNew;
					itemNew.setItemId(itemIdNew);
					itemNew.setGzType(gzType);
					sqlList.add(itemNew.toInsertSql());
					if(OtherUtil.measureNotNull(gzItemFormulas)&&!gzItemFormulas.isEmpty()){
						for(GzItemFormula formulaTemp:gzItemFormulas){
							if(itemIdTemp.equals(formulaTemp.getGzItem().getItemId())){
								String formulaIdTemp = formulaTemp.getFormulaId();
								GzItemFormula formulaNew = formulaTemp.clone();
								String formulaIdNew = OtherUtil.getRandomUUID();
								formulaNew.setFormulaId(formulaIdNew);
								formulaNew.setGzItem(itemNew);
								sqlList.add(formulaNew.toInsertSql());
								if(OtherUtil.measureNotNull(gzItemFormulaFilters)&&!gzItemFormulaFilters.isEmpty()){
									for(GzItemFormulaFilter filterTemp:gzItemFormulaFilters){
										if(formulaIdTemp.equals(filterTemp.getGzItemFormula().getFormulaId())){
											GzItemFormulaFilter filterNew = filterTemp.clone();
											filterNew.setFilterId(OtherUtil.getRandomUUID());
											filterNew.setGzItemFormula(formulaNew);
											sqlList.add(filterNew.toInsertSql());
										}
									}
								}
							}
						}
					}
				}
				gzItemManager.saveAll(gzItems);
			}
			gzItemManager.executeSqlList(sqlList);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward("添加成功。");
	}

	public String gzItemSort() {
		try {
			String itemIdStr = this.getRequest().getParameter("itemIdStr");
			if(OtherUtil.measureNotNull(itemIdStr)){
				String[] itemIds = itemIdStr.split(",");
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("INS_itemId",itemIdStr));
				gzItems = gzItemManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
					Map<String, GzItem> gzItemMap = new HashMap<String, GzItem>();
					for(GzItem itemTemp:gzItems){
						gzItemMap.put(itemTemp.getItemId(), itemTemp);
					}
					gzItems = new ArrayList<GzItem>();
					int index = 1;
					for(String itemId:itemIds){
						GzItem itemTemp = gzItemMap.get(itemId);
						itemTemp.setSn(index++);
						gzItems.add(itemTemp);
					}
					gzItemManager.saveAll(gzItems);
				}
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward("排序成功。");
	}
	private String gzItemsJsonStr;
	public String edit() {
        if (itemId != null) {
        	gzItem = gzItemManager.get(itemId);
        	this.setEntityIsNew(false);
        } else {
        	gzItem = new GzItem();
        	if(gzTypeManager.exists(gzTypeId)){
        		gzItem.setGzType(gzTypeManager.get(gzTypeId));
        	}
        	String nextItemCode = gzItemManager.getNextItemCode();
        	gzItem.setItemCode(nextItemCode);
        	gzItem.setItemType("0");
        	gzItem.setItemLength(18);
        	gzItem.setScale(2);
        	this.setEntityIsNew(true);
        /*	String gzTypeId = inUseGzType;
        	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_gzType.gzTypeId","XT"));
    		filters.add(new PropertyFilter("EQB_disabled","0"));
    		filters.add(new PropertyFilter("EQB_sysField","0"));
    		filters.add(new PropertyFilter("SQS_gzTypeId","itemCode not in (select itemCode from gz_gzItem where gzTypeId = '"+gzTypeId+"')"));
    		filters.add(new PropertyFilter("OAI_sn",""));
    		gzItems = gzItemManager.getByFilters(filters);
    		JSONArray jsonArray = new JSONArray();
    		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
    			for(GzItem itemTemp:gzItems){
    				JSONObject jsonObject = new JSONObject();
    				jsonObject.put("calculateType", itemTemp.getCalculateType());
    				jsonObject.put("itemCode", itemTemp.getItemCode());
    				jsonObject.put("itemName", itemTemp.getItemName());
    				jsonObject.put("itemType", itemTemp.getItemType());
    				jsonObject.put("itemLength", itemTemp.getItemLength());
    				jsonObject.put("scale", itemTemp.getScale());
    				jsonArray.add(jsonObject);
    			}
    		}
    		gzItemsJsonStr = jsonArray.toString();*/
        }
//		request.setAttribute("gztypes", gztypes);
        return SUCCESS;
    }
	public String gzItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					GzItem itemTemp = gzItemManager.get(removeId);
					String itemCode = itemTemp.getItemCode();
					gzItemManager.remove(removeId);
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_itemCode",itemCode));
					gzItems = gzItemManager.getByFilters(filters);
					if(OtherUtil.measureNotNull(gzItems)&&gzItems.size()==1){
						itemTemp = gzItems.get(0);
						itemTemp.setIsUsed(false);
						gzItemManager.save(itemTemp);
					}
				}
				gridOperationMessage = this.getText("gzItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if (oper.equals("delItemSet")) {//工资项指标删除时删除列
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					GzItem gzItem = gzItemManager.get(removeId);
					String columnName =  gzItem.getItemCode();
					gzItemManager.remove(new String(removeId));
					//删除工资项后要在数据库里去掉相应的列
					removeGzContentColumn(columnName);
				}
				gridOperationMessage = this.getText("gzItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGzItemGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	 private void removeGzContentColumn(String columnName) {
		String sql = "ALTER TABLE gz_gzContent DROP COLUMN  "+columnName;
		gzItemManager.executeSql(sql);
		
	}

	public String gzItemChangeSn() {
		String items = this.getRequest().getParameter("item");
		String [] sns = items.split(",");
		gzItems = new ArrayList<GzItem>();
		for(String e:sns){
			String[] strArr = e.split(":");
			GzItem gzItem = gzItemManager.get(strArr[0]);
			gzItem.setSn(Integer.parseInt(strArr[1]));
			gzItems.add(gzItem);
		}
		gzItemManager.saveAll(gzItems);
		return SUCCESS;
	}

	private String isValid() {
		if (gzItem == null) {
			return "Invalid gzItem Data";
		}

		return SUCCESS;

	}	
	private List<ZTreeSimpleNode> itemNodes;
	public List<ZTreeSimpleNode> getItemNodes() {
		return itemNodes;
	}

	public void setItemNodes(List<ZTreeSimpleNode> itemNodes) {
		this.itemNodes = itemNodes;
	}
	
	public String makeGzItemTree(){	
		String gzTypeId = this.getRequest().getParameter("gzTypeId");
		String itemCode = this.getRequest().getParameter("itemCode");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("INS_itemType","0,3"));
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
		if(OtherUtil.measureNotNull(itemCode)){
			filters.add(new PropertyFilter("NES_itemCode",itemCode));
		}
		filters.add(new PropertyFilter("OAI_sn",""));
		gzItems = gzItemManager.getByFilters(filters);
		itemNodes = new  ArrayList<ZTreeSimpleNode>();
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			for(GzItem gzItemTemp:gzItems){
				ZTreeSimpleNode itemNode = new ZTreeSimpleNode();
				itemNode.setId(gzItemTemp.getItemId());
				itemNode.setName(gzItemTemp.getItemName());
				itemNode.setParent(false);
				itemNode.setSubSysTem(gzItemTemp.getItemCode());
				itemNode.setActionUrl(gzItemTemp.getItemType());
				itemNodes.add(itemNode);
			}
		}
		return SUCCESS;
	}
	private GzItemFormulaManager gzItemFormulaManager;
	public GzItemFormulaManager getGzItemFormulaManager() {
		return gzItemFormulaManager;
	}

	public void setGzItemFormulaManager(GzItemFormulaManager gzItemFormulaManager) {
		this.gzItemFormulaManager = gzItemFormulaManager;
	}
	private GzContentManager gzContentManager;
	public String checkDelGzItem(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzType",gzTypeId));
		List<GzContent> gzContentList = gzContentManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzContentList)&&!gzContentList.isEmpty()){
			return ajaxForwardError("已编辑数据的工资类别的工资项不能删除！");
		}
		filters.clear();
		filters.add(new PropertyFilter("INS_gzItem.itemId",id));
		List<GzItemFormula> gzItemFormulas = gzItemFormulaManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzItemFormulas)&&!gzItemFormulas.isEmpty()){
			return ajaxForwardError("包含公式的工资项不能删除！");
		}
		String[] itemIds = id.split(",");
		String result = "";
		for(String itemId:itemIds){
			GzItem itemTemp = gzItemManager.get(itemId);
			result += gzItemFormulaManager.checkGzItemDel(itemTemp);
		}
		if(OtherUtil.measureNotNull(result)){
			return ajaxForwardError(result);
		}
		return null;
	}
	//添加工资项时检查
	public String checkAddGzItem(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<String> itemCodeList = new ArrayList<String>();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));//已经添加的工资项
		gzItems = gzItemManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			for(GzItem itemTemp:gzItems){
				itemCodeList.add(itemTemp.getItemCode());
			}
		}
		List<String> checkItemIdList = new ArrayList<String>();
		if(OtherUtil.measureNotNull(id)){
			StringTokenizer ids = new StringTokenizer(id,",");
			while (ids.hasMoreTokens()) {
				String checkId = ids.nextToken();
				checkItemIdList.add(checkId);
			}
		}else{
			id = "";
		}
		filters.clear();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId","XT"));//系统工资项
		gzItems = gzItemManager.getByFilters(filters);
		Map<String, GzItem> notExistMap = new HashMap<String, GzItem>();
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			for(GzItem itemTemp:gzItems){
				String itemCode = itemTemp.getItemCode();
				String itemId = itemTemp.getItemId();
				if(OtherUtil.measureNotNull(itemTemp.getSysField())&&itemTemp.getSysField()){
					if(!itemCodeList.contains(itemCode)){
						id += itemId + ",";
					}
					continue;
				}
				if(itemCodeList.contains(itemCode)||checkItemIdList.contains(itemId)){
					continue;
				}
				notExistMap.put(itemCode, itemTemp);
			}
		}
		if(notExistMap.size()>0){
			filters.clear();
			if(OtherUtil.measureNotNull(id)&&id.substring(id.length()-1)==","){
				id = OtherUtil.subStrEnd(id, ",");
			}
			filters.add(new PropertyFilter("INS_gzItem.itemId",id));
			List<GzItemFormula> itemFormulas = gzItemFormulaManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(itemFormulas)&&!itemFormulas.isEmpty()){
				for(GzItemFormula formulaTemp:itemFormulas){
					String resultParameter = formulaTemp.getResultParameter();
					String conditionParameter = formulaTemp.getConditionParameter();
					if(OtherUtil.measureNotNull(resultParameter)){
						String[] checkCodeArr = resultParameter.split(",");
						for(String checkCode:checkCodeArr){
							if(notExistMap.containsKey(checkCode)){
								String itemName = formulaTemp.getGzItem().getItemName();
								String formulaName = formulaTemp.getName();
								String notExistItemName = notExistMap.get(checkCode).getItemName();
								gridOperationMessage = "工资项["+itemName+"]中公式["+formulaName+"]结果表达式中存在未添加的工资项["+notExistItemName+"]";
								return ajaxForwardError(gridOperationMessage);
							}
						}
					}
					if(OtherUtil.measureNotNull(conditionParameter)){
						String[] checkCodeArr = conditionParameter.split(",");
						for(String checkCode:checkCodeArr){
							if(notExistMap.containsKey(checkCode)){
								String itemName = formulaTemp.getGzItem().getItemName();
								String formulaName = formulaTemp.getName();
								String notExistItemName = notExistMap.get(checkCode).getItemName();
								gridOperationMessage = "工资项["+itemName+"]中公式["+formulaName+"]条件表达式中存在未添加的工资项["+notExistItemName+"]";
								return ajaxForwardError(gridOperationMessage);
							}
						}
					}
				}
			}
		}
		return ajaxForward("");
	}
	public String checkGzItemName(){
		HttpServletRequest request = this.getRequest();
		String gzItemName = request.getParameter("gzItemName");
		String gzTypeId = request.getParameter("gzTypeId");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
		filters.add(new PropertyFilter("EQS_itemName",gzItemName));
		gzItems = gzItemManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			return ajaxForwardError("该工资类别下已存在该名称！");
		}
		return null;
	}
	/** 
	 * 检查用户填写的code是否已经存在
	 * **/
    public String getCodeIsRepeat(){
    	String code = this.getRequest().getParameter("code");
         //根据code去查询是否存在
    	String sql = "select name from syscolumns where id=(select max(id) "
    			+ "from sysobjects where xtype='u' and name='gz_gzcontent') and name='"+code+"'";
    	List lst = gzItemManager.getBySql(sql);
    	if(lst.size()==0){
    		return ajaxForward("0");
    	}
    	return ajaxForward("1");
    }
	/**
	 * 重新生成顺序号
	 * */
    public String resortSn(){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
    	filters.add(new PropertyFilter("OAI_sn",""));
    	List<GzItem> lst = gzItemManager.getByFilters(filters);
    	if(lst.size()>0){
    		for(int i=0;i<lst.size();i++){
    			lst.get(i).setSn(i+1);
    		}
    		gzItemManager.saveAll(lst);
    	}
    	return ajaxForward("1");
    }
    /*扣税项唯一性判断*/
    public String gzItemIsTaxExist(){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
    	filters.add(new PropertyFilter("EQB_isTax","1"));
    	gzItems = gzItemManager.getByFilters(filters);
    	if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
    		for(GzItem itemTemp:gzItems){
    			if(!itemTemp.getItemId().equals(itemId)){
    				String itemName = itemTemp.getItemName();
    				return ajaxForward("该工资类别下已存在扣税项："+itemName+"！");
    			}
    		}
    	}
    	return ajaxForward("");
    }

	public String getGzItemsJsonStr() {
		return gzItemsJsonStr;
	}

	public void setGzItemsJsonStr(String gzItemsJsonStr) {
		this.gzItemsJsonStr = gzItemsJsonStr;
	}

	public String getGzTypeId() {
		return gzTypeId;
	}

	public void setGzTypeId(String gzTypeId) {
		this.gzTypeId = gzTypeId;
	}

	public GzItemFormulaFilterManager getGzItemFormulaFilterManager() {
		return gzItemFormulaFilterManager;
	}

	public void setGzItemFormulaFilterManager(GzItemFormulaFilterManager gzItemFormulaFilterManager) {
		this.gzItemFormulaFilterManager = gzItemFormulaFilterManager;
	}

	public GzContentManager getGzContentManager() {
		return gzContentManager;
	}

	public void setGzContentManager(GzContentManager gzContentManager) {
		this.gzContentManager = gzContentManager;
	}
	//工资项添加
	public String gzItemCheckList(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
		filters.add(new PropertyFilter("EQB_sysField","1"));
		gzItems = gzItemManager.getByFilters(filters);
		this.setEntityIsNew(true);
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			this.setEntityIsNew(false);
		}
		return SUCCESS;
	}
}

