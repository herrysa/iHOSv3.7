package com.huge.ihos.hr.statistics.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.huge.ihos.excel.ColumnDefine;
import com.huge.ihos.excel.ColumnStyle;
import com.huge.ihos.hr.statistics.model.StatisticsDetail;
import com.huge.ihos.hr.statistics.model.StatisticsItem;
import com.huge.ihos.hr.statistics.model.StatisticsTreeNode;
import com.huge.ihos.hr.statistics.model.StatisticsType;
import com.huge.ihos.hr.statistics.service.StatisticsItemManager;
import com.huge.ihos.hr.statistics.service.StatisticsTypeManager;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.hr.sysTables.service.SysTableContentManager;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.util.DateUtil;
import com.huge.util.ExcelUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class StatisticsItemPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -461582980473423083L;
	private StatisticsItemManager statisticsItemManager;
	private StatisticsTypeManager statisticsTypeManager;
	private SysTableContentManager sysTableContentManager;
	private BdInfoManager bdInfoManager;
	private List<StatisticsItem> statisticsItems;
	private List<StatisticsType> statisticsTypes;
	private StatisticsItem statisticsItem;
	private StatisticsItem statisticsItemSecond;
	private String id;
	private String treeType;
	private String parentTypeId;
	private String statisticsCode;
	private String filterFieldName;
	private List itemList;
	private String itemIdSecond;
	private String mccKeyId;
	private List<BdInfo> bdInfos;
	private String subset;
	private StatisticsDetail statisticsDetail;
	private String shijianFK;

	public StatisticsItemManager getStatisticsItemManager() {
		return statisticsItemManager;
	}

	public void setStatisticsItemManager(StatisticsItemManager statisticsItemManager) {
		this.statisticsItemManager = statisticsItemManager;
	}
	
	public List<BdInfo> getBdInfos() {
		return bdInfos;
	}
	public void BdInfos(List<BdInfo> bdInfos) {
		this.bdInfos = bdInfos;
	}
	
	public List<StatisticsType> getstatisticsTypes() {
		return statisticsTypes;
	}

	public List<StatisticsItem> getstatisticsItems() {
		return statisticsItems;
	}
	public List getItemList() {
        return itemList;
    }
	public void setStatisticsItems(List<StatisticsItem> statisticsItems) {
		this.statisticsItems = statisticsItems;
	}

	public StatisticsItem getStatisticsItem() {
		return statisticsItem;
	}

	public void setStatisticsItem(StatisticsItem statisticsItem) {
		this.statisticsItem = statisticsItem;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String statisticsItemGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(statisticsCode!=null){
				filters.add(new PropertyFilter("EQS_statisticsCode",statisticsCode));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = statisticsItemManager
					.getStatisticsItemCriteria(pagedRequests,filters);
			this.statisticsItems = (List<StatisticsItem>) pagedRequests.getList();
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
			statisticsItem.setChangeDate(new Date());
			statisticsItem.setChangeUser(this.getSessionUser().getPerson());
			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			statisticsItemManager.save(statisticsItem);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "statisticsItem.added" : "statisticsItem.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	statisticsItem = statisticsItemManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	statisticsItem = new StatisticsItem();
        	if(parentTypeId!=null){
        		statisticsItem.setParentType(this.statisticsTypeManager.get(parentTypeId));
        	}
        	if(statisticsCode!=null){
        		statisticsItem.setStatisticsCode(statisticsCode);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String statisticsSingleChart(){
    	if(statisticsCode!=null){
    		statisticsDetail=new StatisticsDetail();
    		statisticsDetail=statisticsItemManager.getStatisticsDetail(statisticsCode);
    	}
    	return SUCCESS;
    }
    public String previewStatisticsItem(){
    	if(statisticsCode!=null){
    		statisticsDetail=new StatisticsDetail();
    		statisticsDetail=statisticsItemManager.getStatisticsDetail(statisticsCode);
    	}
    	return SUCCESS;
    }
    public String editSingleChartFilter(){
    	if(id!=null){
    		statisticsItem = statisticsItemManager.get(id);
    		filterFieldName=statisticsItem.getStatisticsBdInfo().getDeptFK();
    	}
    	return SUCCESS;
    }
    public String editStatisticsSingleField(){
    	if(id!=null){
    		if(subset!=null&&subset.equals("1")){
    			SysTableContent sysTableContent=new SysTableContent();
    			sysTableContent=sysTableContentManager.get(id);
    			id=sysTableContent.getTableKind().getId();
    		}
    	}
    	return SUCCESS;
    }
    public String editMultiChartFilter(){
    	if(id!=null){
    		statisticsItem = statisticsItemManager.get(id);
    		if(statisticsItem.getDynamicStatistics()){
        		if(statisticsItem.getDynamicTable().equals("v_hr_department_current")){
        			filterFieldName=statisticsItem.getDynamicTableForeignKey();
        		}
        	}
    	}
    	if(itemIdSecond!=null){
    		statisticsItemSecond=statisticsItemManager.get(itemIdSecond);
    		if(statisticsItemSecond.getDynamicStatistics()){
        		if(statisticsItemSecond.getDynamicTable().equals("v_hr_department_current")){
        			filterFieldName=statisticsItemSecond.getDynamicTableForeignKey();
        		}
        	}
    	}
    	
    	return SUCCESS;
    }
    public String singleFieldList(){
    	if(statisticsCode!=null){
    		statisticsDetail=new StatisticsDetail();
    		statisticsDetail=statisticsItemManager.getStatisticsDetail(statisticsCode);
    	}
    	return SUCCESS;
    }
    public String getSingleFieldTableList(){
    	bdInfos=new ArrayList<BdInfo>();
    	if(statisticsCode!=null){
    		bdInfos = sysTableContentManager.getSingleFieldTableList(statisticsCode);
    	}
    	return SUCCESS;
    }
    public String statisticsMultiChart(){
    	itemList=new ArrayList<DictionaryItem>();
    	if(statisticsCode!=null){
    		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_statisticsCode", statisticsCode));
//    		filters.add(new PropertyFilter("OAI_sn","sn"));
    		statisticsItems=this.statisticsItemManager.getByFilters(filters);
    		if(statisticsItems!=null&&statisticsItems.size()>0){
    			try{
    			int i=1;
    			for(StatisticsItem statisticsItemTemp:statisticsItems){
    				DictionaryItem dictionaryItem=new DictionaryItem();
    				dictionaryItem.setDictionary(null);
    				dictionaryItem.setContent(statisticsItemTemp.getName());
    				dictionaryItem.setValue(statisticsItemTemp.getId());
    				dictionaryItem.setOrderNo((long)i);
    				dictionaryItem.setDictionaryItemId((long)i);
    				itemList.add(dictionaryItem);
    				i++;
    			}
    			}catch(Exception e){
    				log.error("MultiChart Convert Int to Long Error", e);
    			}
    		}
    		statisticsDetail=new StatisticsDetail();
    		statisticsDetail=statisticsItemManager.getStatisticsDetail(statisticsCode);
    	}
    	return SUCCESS;
    }
	public String statisticsItemGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.statisticsItemManager.deleteStatisticsItem(ida);
				gridOperationMessage = this.getText("statisticsItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkStatisticsItemGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (statisticsItem == null) {
			return "Invalid statisticsItem Data";
		}

		return SUCCESS;

	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public StatisticsTypeManager getStatisticsTypeManager() {
		return statisticsTypeManager;
	}

	public void setStatisticsTypeManager(StatisticsTypeManager statisticsTypeManager) {
		this.statisticsTypeManager = statisticsTypeManager;
	}
	private List<StatisticsTreeNode> statisticsItemTreeNodes;
	 
	public List<StatisticsTreeNode> getStatisticsItemTreeNodes() {
		return statisticsItemTreeNodes;
	}
	//tree load
	public String makeStatisticsItemTree() {
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		statisticsItemTreeNodes=new ArrayList<StatisticsTreeNode>();
		try{
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			this.statisticsTypes=this.statisticsTypeManager.getByFilters(filters);
			StatisticsTreeNode rootNode=new StatisticsTreeNode();
			rootNode.setId("-1");
			rootNode.setName("统计项");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setIcon(iconPath+"category.png");
			statisticsItemTreeNodes.add(rootNode);
			if(this.statisticsTypes!=null&&this.statisticsTypes.size()>0){
				for(StatisticsType statisticsTypeTemp:statisticsTypes){
					StatisticsTreeNode typeNode=new StatisticsTreeNode();
					typeNode.setId(statisticsTypeTemp.getId());
					typeNode.setName(statisticsTypeTemp.getName());
					if(statisticsTypeTemp.getParentType()==null){
						typeNode.setpId("-1");
					}else{
						typeNode.setpId(statisticsTypeTemp.getParentType().getId());
					}
						typeNode.setIsParent(false);
						if(statisticsTypeTemp.getLeaf()){
							typeNode.setSubSysTem("leaf");
						}else{
							typeNode.setSubSysTem("statisticsType");
						}
						typeNode.setIcon(iconPath+"category.png");
					statisticsItemTreeNodes.add(typeNode);
				}
			}
			if(statisticsCode!=null){
				filters.add(new PropertyFilter("EQS_statisticsCode",statisticsCode));
			}
			this.statisticsItems=this.statisticsItemManager.getByFilters(filters);
			if(this.statisticsItems!=null&&this.statisticsItems.size()>0){
				for(StatisticsItem statisticsItemTemp:statisticsItems){
					StatisticsTreeNode itemNode=new StatisticsTreeNode();
					itemNode.setId(statisticsItemTemp.getId());
					itemNode.setName(statisticsItemTemp.getName());
					itemNode.setpId(statisticsItemTemp.getParentType().getId());
					itemNode.setParent(false);
					itemNode.setSubSysTem("item");
					itemNode.setIcon(iconPath+"statistics.png");
					itemNode.setDeptRequired(statisticsItemTemp.getDeptRequired());
					itemNode.setStatisticsAuto(statisticsItemTemp.getStatisticsAuto());
					statisticsItemTreeNodes.add(itemNode);
				}
			}
			
		}catch(Exception e){
			log.error("makeStatisticsItemTree error",e);
		}
		return SUCCESS;
	}

	public String getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(String parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getStatisticsCode() {
		return statisticsCode;
	}

	public void setStatisticsCode(String statisticsCode) {
		this.statisticsCode = statisticsCode;
	}


	public String getFilterFieldName() {
		return filterFieldName;
	}

	public void setFilterFieldName(String filterFieldName) {
		this.filterFieldName = filterFieldName;
	}

	public String getItemIdSecond() {
		return itemIdSecond;
	}

	public void setItemIdSecond(String itemIdSecond) {
		this.itemIdSecond = itemIdSecond;
	}

	public String getMccKeyId() {
		return mccKeyId;
	}

	public void setMccKeyId(String mccKeyId) {
		this.mccKeyId = mccKeyId;
	}

	public StatisticsItem getStatisticsItemSecond() {
		return statisticsItemSecond;
	}

	public void setStatisticsItemSecond(StatisticsItem statisticsItemSecond) {
		this.statisticsItemSecond = statisticsItemSecond;
	}

	public SysTableContentManager getSysTableContentManager() {
		return sysTableContentManager;
	}

	public void setSysTableContentManager(SysTableContentManager sysTableContentManager) {
		this.sysTableContentManager = sysTableContentManager;
	}

	public String getSubset() {
		return subset;
	}

	public void setSubset(String subset) {
		this.subset = subset;
	}

	public BdInfoManager getBdInfoManager() {
		return bdInfoManager;
	}

	public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}
	
	public StatisticsDetail getStatisticsDetail() {
		return statisticsDetail;
	}

	public void setStatisticsDetail(StatisticsDetail statisticsDetail) {
		this.statisticsDetail = statisticsDetail;
	}

	public String getShijianFK() {
		return shijianFK;
	}

	public void setShijianFK(String shijianFK) {
		this.shijianFK = shijianFK;
	}
	 /**导出excel**/
	@SuppressWarnings("deprecation")
	public String exportExcelForSatatiticsItem(){
		HttpServletRequest request = this.getRequest();
		Map<String,ColumnDefine> columnDefineMap = new HashMap<String, ColumnDefine>();
		List<ColumnStyle> columnStyles = new ArrayList<ColumnStyle>();
		List<Map<String, String>> rowList=new ArrayList<Map<String,String>>();
		String gridAllDatas=request.getParameter("gridAllDatas");
		JSONObject json=JSONObject.fromObject(gridAllDatas);
	    JSONArray allDatas=json.getJSONArray("edit");
	    List<String> columns=new ArrayList<String>();
	    Map<String,String> tempMap=new HashMap<String, String>();
	    columns.add(" ");
	    for(int i=0;i<allDatas.size();i++){
	    	//获取每一个JsonObject对象
			JSONObject myjObject = allDatas.getJSONObject(i);
			if(myjObject.size()>0){
				String cloumnName=myjObject.getString("columnName");
				String rowName=myjObject.getString("rowName");
				String value=myjObject.getString("value");
				if(!columns.contains(cloumnName)){
					columns.add(cloumnName);
				}
				if(!tempMap.containsValue(rowName)){
					if(tempMap.size()>0){
						rowList.add(tempMap);
						tempMap=new HashMap<String, String>();
					}
					tempMap.put(" ", rowName);
				}
				tempMap.put(cloumnName, value);
				
			}
	    }
	    rowList.add(tempMap);
		for(int i=0;i<columns.size();i++){
			String name=columns.get(i);
			ColumnStyle columnStyle = new ColumnStyle();
			columnStyle.name = name;
    		columnStyle.columnNum = i;
    		columnStyle.colnumWidth = 3000;
    		columnStyle.columnHidden =false;
    		columnStyle.align = "center";
    		columnStyle.label = name;
    		columnStyles.add(columnStyle);
    		ColumnDefine columnDefine = new ColumnDefine();
    		columnDefine.name = name;
    		int dataType = 1;
    		columnDefine.type = dataType;
    		columnDefineMap.put(name,columnDefine);
		}
		String title = request.getParameter("title");
		//String title ="TEST";
	    String excelFullPath = request.getRealPath( "//home//temporary//");
	    excelFullPath += "//"+title+"_"+DateUtil.convertDateToString("yyyyMMddhhmmss",Calendar.getInstance().getTime())+".xls";
	    ExcelUtil.createExcelStyle(rowList,columnStyles,columnDefineMap,title,excelFullPath);
	    this.setMessage(excelFullPath+"@@"+title+"_"+DateUtil.convertDateToString("yyyyMMddhhmmss",Calendar.getInstance().getTime())+".xls");
		return SUCCESS;
	}
}

