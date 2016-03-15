package com.huge.ihos.gz.gzAccount.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.gz.gzAccount.model.GzAccountPlan;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanItem;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanItemManager;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanManager;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class GzAccountPlanItemPagedAction extends JqGridBaseAction implements
		Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1159973721985823028L;
	private GzAccountPlanItemManager gzAccountPlanItemManager;
	private List<GzAccountPlanItem> gzAccountPlanItems;
	private GzAccountPlanItem gzAccountPlanItem;
	private String colId;
	private GzItemManager gzItemManager;
	private GzTypeManager gzTypeManager;
	private GzAccountPlanManager gzAccountPlanManager;

	public GzTypeManager getGzTypeManager() {
		return gzTypeManager;
	}

	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}

	public GzItemManager getGzItemManager() {
		return gzItemManager;
	}

	public void setGzItemManager(GzItemManager gzItemManager) {
		this.gzItemManager = gzItemManager;
	}

	public void setGzAccountPlanManager(GzAccountPlanManager gzAccountPlanManager) {
		this.gzAccountPlanManager = gzAccountPlanManager;
	}
	
	public GzAccountPlanItemManager getGzAccountPlanItemManager() {
		return gzAccountPlanItemManager;
	}

	public void setGzAccountPlanItemManager(
			GzAccountPlanItemManager gzAccountPlanItemManager) {
		this.gzAccountPlanItemManager = gzAccountPlanItemManager;
	}

	public List<GzAccountPlanItem> getgzAccountPlanItems() {
		return gzAccountPlanItems;
	}

	public void setGzAccountPlanItems(List<GzAccountPlanItem> gzAccountPlanItems) {
		this.gzAccountPlanItems = gzAccountPlanItems;
	}

	public GzAccountPlanItem getGzAccountPlanItem() {
		return gzAccountPlanItem;
	}

	public void setGzAccountPlanItem(GzAccountPlanItem gzAccountPlanItem) {
		this.gzAccountPlanItem = gzAccountPlanItem;
	}

	public String getColId() {
		return colId;
	}

	public void setColId(String colId) {
		this.colId = colId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String gzAccountPlanItemGridList() {
		log.debug("enter list method!");
		try {
			String planId = this.getRequest().getParameter("planId");
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("EQS_planId", planId));
			//JQueryPager pagedRequests = null;
			//pagedRequests = (JQueryPager) pagerFactory.getPager(
			//		PagerFactory.JQUERYTYPE, getRequest());
			//pagedRequests = gzAccountPlanItemManager
			//		.getGzAccountPlanItemCriteria(pagedRequests, filters);
			this.gzAccountPlanItems = gzAccountPlanItemManager.getByFilters(filters);
//			if(OtherUtil.measureNotNull(gzAccountPlanItems)&&!gzAccountPlanItems.isEmpty()){
//				GzAccountPlan gzPlan = gzAccountPlanManager.get(planId);
//				String gzTypeId = gzPlan.getGzTypeId();
//				filters.clear();
//				filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
//				List<GzItem> items = gzItemManager.getByFilters(filters);
//				Map<String, String> itemCodeNameMap = new HashMap<String, String>();
//				if(OtherUtil.measureNotNull(items)&&!items.isEmpty()){
//					for(GzItem gzItemTemp:items){
//						itemCodeNameMap.put(gzItemTemp.getItemCode(), gzItemTemp.getItemName());
//					}
//				}
//				for(GzAccountPlanItem itemTemp:gzAccountPlanItems){
//					String itemCode = itemTemp.getItemCode();
//					if(itemCodeNameMap.containsKey(itemCode)){
//						itemTemp.setItemName(itemCodeNameMap.get(itemCode));
//					}
//				}
//			}
			//records = pagedRequests.getTotalNumberOfRows();
			//total = pagedRequests.getTotalNumberOfPages();
			//page = pagedRequests.getPageNumber();

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
			 List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			 filters.add(new PropertyFilter("EQS_itemCode",gzAccountPlanItem.getItemCode()));
		     List<GzItem> gzItems = gzItemManager.getByFilters(filters);
		     GzItem gzItem = gzItems.get(0);
		     gzAccountPlanItem.setItemCode(gzItem.getItemCode());
		     gzAccountPlanItem.setColSn(gzItem.getSn());
			gzAccountPlanItemManager.save(gzAccountPlanItem);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "gzAccountPlanItem.added"
				: "gzAccountPlanItem.updated";
		return ajaxForward(this.getText(key));
	}

	private String gridAllDatas;
	public String saveGzAccountPlanItemForm(){
		try {
			String planId = this.getRequest().getParameter("planId");
			gzAccountPlanItemManager.saveGzAccountPlanItem(gridAllDatas,planId);
		} catch (Exception e) {
			gridOperationMessage = e.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward("保存成功");
	}
	
	public String edit() {
		String planId = this.getRequest().getParameter("planId");
		List<PropertyFilter> filters2 = PropertyFilter
				.buildFromHttpRequest(getRequest());
		filters2.add(new PropertyFilter("EQS_planId", planId));
		List<GzAccountPlanItem> lst = gzAccountPlanItemManager.getByFilters(filters2);
		String str = "";
		for(GzAccountPlanItem e:lst){
			str  = str + e.getItemCode()+ ",";
		}
		str = str.substring(0,str.length()-1);
		
		if (colId != null) {
			gzAccountPlanItem = gzAccountPlanItemManager.get(colId);
			this.setEntityIsNew(false);
			this.getRequest().setAttribute("planId", planId);		
		} else {
			String gzType = "";
			GzType curGzType = gzTypeManager.getCurrentGzType(getSessionUser().getId().toString());
			if(OtherUtil.measureNotNull(curGzType)){
				gzType = curGzType.getGzTypeId();
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_gzType.gzTypeId", gzType));
			filters.add(new PropertyFilter("NIS_itemCode", str));
			filters.add(new PropertyFilter("EQB_disabled", "0"));
			List<GzItem> items = gzItemManager.getByFilters(filters);
			this.getRequest().setAttribute("itemList", items);
			this.getRequest().setAttribute("planId", planId);			
			gzAccountPlanItem = new GzAccountPlanItem();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String gzAccountPlanItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					GzAccountPlanItem gzAccountPlanItem = gzAccountPlanItemManager
							.get(new String(removeId));
					gzAccountPlanItemManager.remove(new String(removeId));

				}
				gridOperationMessage = this
						.getText("gzAccountPlanItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGzAccountPlanItemGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gzAccountPlanItem == null) {
			return "Invalid gzAccountPlanItem Data";
		}

		return SUCCESS;

	}

	public String getGridAllDatas() {
		return gridAllDatas;
	}

	public void setGridAllDatas(String gridAllDatas) {
		this.gridAllDatas = gridAllDatas;
	}
}
