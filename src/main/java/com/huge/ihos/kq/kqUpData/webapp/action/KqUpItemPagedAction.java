package com.huge.ihos.kq.kqUpData.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.kq.kqType.service.KqTypeManager;
import com.huge.ihos.kq.kqUpData.model.KqDayData;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormula;
import com.huge.ihos.kq.kqUpData.service.KqDayDataManager;
import com.huge.ihos.kq.kqUpData.service.KqMonthDataManager;
import com.huge.ihos.kq.kqUpData.service.KqUpItemFormulaManager;
import com.huge.ihos.kq.kqUpData.service.KqUpItemManager;
import com.huge.ihos.system.configuration.colsetting.model.ColShow;
import com.huge.ihos.system.configuration.colsetting.service.ColShowManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;




public class KqUpItemPagedAction extends JqGridBaseAction implements Preparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4108802817055305489L;
	private KqUpItemManager kqUpItemManager;
	private List<KqUpItem> kqUpItems;
	private KqUpItem kqUpItem;
	private String itemId;
	private KqTypeManager kqTypeManager;
	private	String kqTypeId;

	public KqUpItemManager getKqUpItemManager() {
		return kqUpItemManager;
	}

	public void setKqUpItemManager(KqUpItemManager kqUpItemManager) {
		this.kqUpItemManager = kqUpItemManager;
	}

	public List<KqUpItem> getkqUpItems() {
		return kqUpItems;
	}

	public void setKqUpItems(List<KqUpItem> kqUpItems) {
		this.kqUpItems = kqUpItems;
	}

	public KqUpItem getKqUpItem() {
		return kqUpItem;
	}

	public void setKqUpItem(KqUpItem kqUpItem) {
		this.kqUpItem = kqUpItem;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
        this.itemId = itemId;
    }

	public void prepare() throws Exception {
		this.getRequest().setAttribute("kqTypes", kqTypeManager.allKqTypes(false));
		this.clearSessionMessages();
	}
	public String kqUpItemGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(OtherUtil.measureNull(this.getRequest().getParameter("filter_EQS_kqType.kqTypeId"))){
				if(OtherUtil.measureNotNull(kqTypeId)){
					filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
				}else{
					filters.add(new PropertyFilter("EQS_kqType.kqTypeId",""));
				}
			}
			if("all".equals(oper)){
				this.kqUpItems = kqUpItemManager.getByFilters(filters);
			}else{
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(
						PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests = kqUpItemManager
						.getKqUpItemCriteria(pagedRequests,filters);
				this.kqUpItems = (List<KqUpItem>) pagedRequests.getList();
				records = pagedRequests.getTotalNumberOfRows();
				total = pagedRequests.getTotalNumberOfPages();
				page = pagedRequests.getPageNumber();
			}

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String kqUpDataColumnInfo(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
		filters.add(new PropertyFilter("OAI_sn",""));
		this.kqUpItems = kqUpItemManager.getByFilters(filters);
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
			kqUpItem = kqUpItemManager.saveKqUpItem(kqUpItem, this.isEntityIsNew(), oldName,oper);
			itemId = kqUpItem.getItemId();
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "kqUpItem.added" : "kqUpItem.updated";
		return ajaxForward(this.getText(key));
	}
	private String initKqUpItem;//初始化系统考勤项目
    public String edit() {
    	initKqUpItem = "initKqUpItem";
        if (itemId != null) {
        	kqUpItem = kqUpItemManager.get(itemId);
        	this.setEntityIsNew(false);
        } else {
        	kqUpItem = new KqUpItem();
        	if(OtherUtil.measureNotNull(kqTypeId)){
        		kqUpItem.setKqType(kqTypeManager.get(kqTypeId));
        		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        		filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
        		filters.add(new PropertyFilter("EQB_sysField","1"));
        		kqUpItems = kqUpItemManager.getByFilters(filters);
        		if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
        			initKqUpItem = "";
        		}
        	}
        	String nextItemCode = kqUpItemManager.getNextItemCode(kqTypeId);
        	kqUpItem.setItemCode(nextItemCode);
        	kqUpItem.setItemType("0");
        	kqUpItem.setItemLength(18);
        	kqUpItem.setScale(2);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String kqUpItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					KqUpItem kqUpItem = kqUpItemManager.get(removeId);
					String columnName =  kqUpItem.getItemCode();
					//删除工资项后要在数据库里去掉相应的列
					String sql = "";
					sql = "ALTER TABLE kq_dayData DROP COLUMN  "+columnName;
					kqUpItemManager.executeSql(sql);
					kqUpItemManager.remove(removeId);
				}
				gridOperationMessage = this.getText("kqUpItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKqUpItemGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private ColShowManager colShowManager;
	public String kqUpItemSort(){
		try {
			String itemIdStr = this.getRequest().getParameter("itemIdStr");
			if(OtherUtil.measureNotNull(itemIdStr)){
				String[] itemIds = itemIdStr.split(",");
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("INS_itemId",itemIdStr));
				kqUpItems = kqUpItemManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
					Map<String, KqUpItem> kqUpItemMap = new HashMap<String, KqUpItem>();
					for(KqUpItem itemTemp:kqUpItems){
						kqUpItemMap.put(itemTemp.getItemId(), itemTemp);
					}
					kqUpItems = new ArrayList<KqUpItem>();
					int index = 1;
					for(String itemId:itemIds){
						KqUpItem itemTemp = kqUpItemMap.get(itemId);
						itemTemp.setSn(index++);
						kqUpItems.add(itemTemp);
					}
					kqUpItemManager.saveAll(kqUpItems);
				}
				filters.clear();
				filters.add(new PropertyFilter("EQS_entityName","com.huge.ihos.kq.kqUpData.model.KqDayData"));
				List<ColShow> colShows = colShowManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(colShows)&&!colShows.isEmpty()){
					for(ColShow colShow:colShows){
						colShowManager.remove(colShow.getId());
					}
				}
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward("排序成功。");
	}
	
	public String resortKqUpItemSn() {
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
    	filters.add(new PropertyFilter("OAI_sn",""));
    	List<KqUpItem> lst = kqUpItemManager.getByFilters(filters);
    	if(lst.size()>0){
    		for(int i=0;i<lst.size();i++){
    			lst.get(i).setSn(i+1);
    		}
    		kqUpItemManager.saveAll(lst);
    	}
    	return ajaxForward("1");
	}
	
	private String isValid() {
		if (kqUpItem == null) {
			return "Invalid kqUpItem Data";
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
	public String makeKqUpItemTree(){
		String itemCode = this.getRequest().getParameter("itemCode");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("INS_itemType","0,3"));
		filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
		if(OtherUtil.measureNotNull(itemCode)){
			filters.add(new PropertyFilter("NES_itemCode",itemCode));
		}
		filters.add(new PropertyFilter("OAI_sn",""));
		kqUpItems = kqUpItemManager.getByFilters(filters);
		itemNodes = new  ArrayList<ZTreeSimpleNode>();
		if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
			for(KqUpItem kqUpItemTemp:kqUpItems){
				ZTreeSimpleNode itemNode = new ZTreeSimpleNode();
				itemNode.setId(kqUpItemTemp.getItemId());
				itemNode.setName(kqUpItemTemp.getItemName());
				itemNode.setParent(false);
				itemNode.setSubSysTem(kqUpItemTemp.getItemCode());
				itemNode.setActionUrl(kqUpItemTemp.getItemType());
				itemNodes.add(itemNode);
			}
		}
		return SUCCESS;
	}

	private KqUpItemFormulaManager kqUpItemFormulaManager;
	private KqDayDataManager kqDayDataManager;
	private KqMonthDataManager kqMonthDataManager;
	public String checkDelKqUpItem(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_kqUpItem.itemId",id));
		List<KqUpItemFormula> kqUpItemFormulas = kqUpItemFormulaManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(kqUpItemFormulas)&&!kqUpItemFormulas.isEmpty()){
			return ajaxForwardError("包含公式的考勤项不能删除！");
		}
		String[] itemIds = id.split(",");
		String result = "";
		for(String itemId:itemIds){
			KqUpItem itemTemp = kqUpItemManager.get(itemId);
			result += kqUpItemFormulaManager.checkKqUpItemDel(itemTemp);
		}
		if(OtherUtil.measureNotNull(result)){
			return ajaxForwardError(result);
		}
		filters.clear();
		filters.add(new PropertyFilter("EQS_kqType", kqTypeId));
		List<KqDayData> kqDayDatas = kqDayDataManager.getByFilters(filters);
		if (OtherUtil.measureNotNull(kqDayDatas) && !kqDayDatas.isEmpty()) {
			return ajaxForward("该考勤类别已进行考勤上报，不能删除该项目。");
		}
		return null;
	}
	
	public void setKqTypeManager(KqTypeManager kqTypeManager) {
		this.kqTypeManager = kqTypeManager;
	}

	public String getKqTypeId() {
		return kqTypeId;
	}

	public void setKqTypeId(String kqTypeId) {
		this.kqTypeId = kqTypeId;
	}

	public KqUpItemFormulaManager getKqUpItemFormulaManager() {
		return kqUpItemFormulaManager;
	}

	public void setKqUpItemFormulaManager(KqUpItemFormulaManager kqUpItemFormulaManager) {
		this.kqUpItemFormulaManager = kqUpItemFormulaManager;
	}


	public void setKqDayDataManager(KqDayDataManager kqDayDataManager) {
		this.kqDayDataManager = kqDayDataManager;
	}

	public void setKqMonthDataManager(KqMonthDataManager kqMonthDataManager) {
		this.kqMonthDataManager = kqMonthDataManager;
	}

	public String getInitKqUpItem() {
		return initKqUpItem;
	}

	public void setInitKqUpItem(String initKqUpItem) {
		this.initKqUpItem = initKqUpItem;
	}

	public ColShowManager getColShowManager() {
		return colShowManager;
	}

	public void setColShowManager(ColShowManager colShowManager) {
		this.colShowManager = colShowManager;
	}
}

