package com.huge.ihos.hr.statistics.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import com.huge.ihos.hr.statistics.model.StatisticsCondition;
import com.huge.ihos.hr.statistics.service.StatisticsConditionManager;
import com.huge.ihos.hr.statistics.service.StatisticsItemManager;
import com.huge.ihos.hr.statistics.service.StatisticsTargetManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class StatisticsConditionPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4587929456445104433L;
	private StatisticsConditionManager statisticsConditionManager;
	private StatisticsTargetManager statisticsTargetManager;
	private StatisticsItemManager statisticsItemManager;
	private List<StatisticsCondition> statisticsConditions;
	private StatisticsCondition statisticsCondition;
	private String id;
	private String itemId;
	private String menuCode;
	private String gridAllDatas;

	public StatisticsConditionManager getStatisticsConditionManager() {
		return statisticsConditionManager;
	}

	public void setStatisticsConditionManager(StatisticsConditionManager statisticsConditionManager) {
		this.statisticsConditionManager = statisticsConditionManager;
	}

	public List<StatisticsCondition> getstatisticsConditions() {
		return statisticsConditions;
	}

	public void setStatisticsConditions(List<StatisticsCondition> statisticsConditions) {
		this.statisticsConditions = statisticsConditions;
	}

	public StatisticsCondition getStatisticsCondition() {
		return statisticsCondition;
	}

	public void setStatisticsCondition(StatisticsCondition statisticsCondition) {
		this.statisticsCondition = statisticsCondition;
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
	public String statisticsConditionGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = statisticsConditionManager
					.getStatisticsConditionCriteria(pagedRequests,filters);
			this.statisticsConditions = (List<StatisticsCondition>) pagedRequests.getList();
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
			statisticsCondition.setChangeDate(new Date());
			statisticsCondition.setChangeUser(this.getSessionUser().getPerson());
			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			statisticsCondition=statisticsConditionManager.save(statisticsCondition);
			statisticsTargetManager.saveStatisticsTargetGridData(gridAllDatas, statisticsCondition.getId(),this.getSessionUser().getPerson());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "statisticsCondition.added" : "statisticsCondition.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	statisticsCondition = statisticsConditionManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	statisticsCondition = new StatisticsCondition();
        	if(itemId!=null){
        		statisticsCondition.setParentItem(statisticsItemManager.get(itemId));
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String statisticsConditionGridEdit() {
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
				this.statisticsConditionManager.deleteStatisticsConditionAndTarget(ida);
				gridOperationMessage = this.getText("statisticsCondition.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkStatisticsConditionGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String checkStatisticsConditionName(){
		 String conditionName = this.getRequest().getParameter("conditionName");
		 List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		 filters.add(new PropertyFilter("EQS_parentItem.id",itemId));
		 filters.add(new PropertyFilter("EQS_name",conditionName));
		 statisticsConditions=statisticsConditionManager.getByFilters(filters);
		 boolean exist = false;
		  if(statisticsConditions!=null && !statisticsConditions.isEmpty()){
		     exist = true;
		  }
		  if(exist){
		   return ajaxForward(true, "此名称已存在", false);
		  }else{
		   return null;
		  }
	}
	
	private String isValid() {
		if (statisticsCondition == null) {
			return "Invalid statisticsCondition Data";
		}

		return SUCCESS;

	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public StatisticsItemManager getStatisticsItemManager() {
		return statisticsItemManager;
	}

	public void setStatisticsItemManager(StatisticsItemManager statisticsItemManager) {
		this.statisticsItemManager = statisticsItemManager;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getGridAllDatas() {
		return gridAllDatas;
	}

	public void setGridAllDatas(String gridAllDatas) {
		this.gridAllDatas = gridAllDatas;
	}

	public StatisticsTargetManager getStatisticsTargetManager() {
		return statisticsTargetManager;
	}

	public void setStatisticsTargetManager(StatisticsTargetManager statisticsTargetManager) {
		this.statisticsTargetManager = statisticsTargetManager;
	}
}

