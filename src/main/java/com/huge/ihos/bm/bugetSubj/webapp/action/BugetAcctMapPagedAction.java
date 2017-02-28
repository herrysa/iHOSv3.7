package com.huge.ihos.bm.bugetSubj.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.bm.bugetSubj.model.BugetAcctMap;
import com.huge.ihos.bm.bugetSubj.model.BugetSubj;
import com.huge.ihos.bm.bugetSubj.service.BugetAcctMapManager;
import com.huge.ihos.bm.bugetSubj.service.BugetSubjManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BugetAcctMapPagedAction extends JqGridBaseAction implements Preparable {

	private BugetAcctMapManager bugetAcctMapManager;
	private List<BugetAcctMap> bugetAcctMaps;
	private BugetAcctMap bugetAcctMap;
	private String mapId;
	private BugetSubjManager bugetSubjManager;
	private String gridData;

	public String getGridData() {
		return gridData;
	}

	public void setGridData(String gridData) {
		this.gridData = gridData;
	}

	public void setBugetSubjManager(BugetSubjManager bugetSubjManager) {
		this.bugetSubjManager = bugetSubjManager;
	}

	public BugetAcctMapManager getBugetAcctMapManager() {
		return bugetAcctMapManager;
	}

	public void setBugetAcctMapManager(BugetAcctMapManager bugetAcctMapManager) {
		this.bugetAcctMapManager = bugetAcctMapManager;
	}

	public List<BugetAcctMap> getbugetAcctMaps() {
		return bugetAcctMaps;
	}

	public void setBugetAcctMaps(List<BugetAcctMap> bugetAcctMaps) {
		this.bugetAcctMaps = bugetAcctMaps;
	}

	public BugetAcctMap getBugetAcctMap() {
		return bugetAcctMap;
	}

	public void setBugetAcctMap(BugetAcctMap bugetAcctMap) {
		this.bugetAcctMap = bugetAcctMap;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
        this.mapId = mapId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String bugetAcctMapGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bugetAcctMapManager
					.getBugetAcctMapCriteria(pagedRequests,filters);
			this.bugetAcctMaps = (List<BugetAcctMap>) pagedRequests.getList();
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
			bugetAcctMapManager.save(bugetAcctMap);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bugetAcctMap.added" : "bugetAcctMap.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (mapId != null) {
        	bugetAcctMap = bugetAcctMapManager.get(mapId);
        	this.setEntityIsNew(false);
        } else {
        	bugetAcctMap = new BugetAcctMap();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bugetAcctMapGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BugetAcctMap bugetAcctMap = bugetAcctMapManager.get(new String(removeId));
					bugetAcctMapManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("bugetAcctMap.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBugetAcctMapGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bugetAcctMap == null) {
			return "Invalid bugetAcctMap Data";
		}

		return SUCCESS;

	}
	
	public String initAcctMap(){
		try {
			List<PropertyFilter> budgetSubjFilters = new ArrayList<PropertyFilter>();
			budgetSubjFilters.add(new PropertyFilter("EQB_disabled","false"));
			budgetSubjFilters.add(new PropertyFilter("EQB_leaf","true"));
			List<BugetSubj> bugetSubjs = bugetSubjManager.getByFilters(budgetSubjFilters);
			
			List<PropertyFilter> budgetAcctMapFilters = new ArrayList<PropertyFilter>();
			budgetAcctMapFilters.add(new PropertyFilter("EQB_disabled","false"));
			bugetAcctMaps = bugetAcctMapManager.getAll();
			Map<String, Boolean> acctMapMap = new HashMap<String, Boolean>();
			for(BugetAcctMap acctMap : bugetAcctMaps){
				acctMapMap.put(acctMap.getBmSubjId().getBugetSubjId(), true);
			}
			int i = 0;
			for(BugetSubj bugetSubj : bugetSubjs){
				Boolean exist = acctMapMap.get(bugetSubj.getBugetSubjId());
				if(exist!=null){
					continue;
				}
				BugetAcctMap bugetAcctMap = new BugetAcctMap();
				bugetAcctMap.setBmSubjId(bugetSubj);
				bugetAcctMapManager.save(bugetAcctMap);
				i++;
			}
			return ajaxForward(true, "刷新成功！", false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "刷新失败！", false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String saveAcctMap(){
		try {
			JSONArray dataArray = JSONArray.fromObject(gridData);
			Iterator<JSONObject> dataIt =  dataArray.iterator();
			List<String> sqlList = new ArrayList<String>();
			while(dataIt.hasNext()){
				String sql = "";
				JSONObject data = dataIt.next();
				sql += "update bm_bugetAcctMap set acctId='"+data.getString("acctId")+"',"
				+" acctCode='"+data.getString("acctCode")+"',"
				+" acctName='"+data.getString("acctName")+"',"
				+" addDirect='"+data.getString("addDirect")+"',"
				+" subDirect='"+data.getString("subDirect")+"',"
				+" remark='"+data.getString("remark")+"'"
				+" where mapId ='"+data.getString("mapId")+"'";
				sqlList.add(sql);
			}
			bugetAcctMapManager.executeSqlList(sqlList);
			return ajaxForward(true, "保存成功！", false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！", false);
		}
	}
}

