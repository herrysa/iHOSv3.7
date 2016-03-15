package com.huge.ihos.hr.statistics.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import com.huge.ihos.hr.statistics.model.StatisticsType;
import com.huge.ihos.hr.statistics.service.StatisticsTypeManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;




public class StatisticsTypePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5589156597426215976L;
	private StatisticsTypeManager statisticsTypeManager;
	private List<StatisticsType> statisticsTypes;
	private StatisticsType statisticsType;
	private String id;
	private String treeType;
	private String statisticsCode;
	private String parentId;

	public void setStatisticsTypeManager(StatisticsTypeManager statisticsTypeManager) {
		this.statisticsTypeManager = statisticsTypeManager;
	}

	public List<StatisticsType> getstatisticsTypes() {
		return statisticsTypes;
	}

	public void setStatisticsTypes(List<StatisticsType> statisticsTypes) {
		this.statisticsTypes = statisticsTypes;
	}

	public StatisticsType getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(StatisticsType statisticsType) {
		this.statisticsType = statisticsType;
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
	public String statisticsTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(statisticsCode!=null){
				filters.add(new PropertyFilter("EQS_statisticsCode", statisticsCode));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = statisticsTypeManager
					.getStatisticsTypeCriteria(pagedRequests,filters);
			this.statisticsTypes = (List<StatisticsType>) pagedRequests.getList();
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
			statisticsType.setChangeDate(new Date());
			statisticsType.setChangeUser(this.getSessionUser().getPerson());
			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			 if(OtherUtil.measureNull(statisticsType.getParentType().getId()))
				 statisticsType.setParentType(null);
			 statisticsType=statisticsTypeManager.saveStatisticsType(statisticsType);
			 parentId=OtherUtil.measureNull(statisticsType.getParentType())?"-1":statisticsType.getParentType().getId();
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "statisticsType.added" : "statisticsType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	statisticsType = statisticsTypeManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	statisticsType = new StatisticsType();
        	if(statisticsCode!=null){
        		statisticsType.setStatisticsCode(statisticsCode);
        	}
        	if(parentId!=null){
        		if(parentId.equals("-1")){
        			statisticsType.setParentType(null);
        		}else{
        			statisticsType.setParentType(statisticsTypeManager.get(parentId));
        		}
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String statisticsTypeList(){
    	try {
			   List<MenuButton> menuButtons = this.getMenuButtons();
			   //menuButtons.get(0).setEnable(false);
			   setMenuButtonsToJson(menuButtons);
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
    	return SUCCESS;
    }
	public String statisticsTypeGridEdit() {
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
				this.statisticsTypeManager.deleteStatisticsType(ida);
				gridOperationMessage = this.getText("statisticsType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkStatisticsTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (statisticsType == null) {
			return "Invalid statisticsType Data";
		}

		return SUCCESS;

	}

	private List<ZTreeSimpleNode> statisticsTypeTreeNodes;
	 
	public List<ZTreeSimpleNode> getStatisticsTypeTreeNodes() {
		return statisticsTypeTreeNodes;
	}
	
	//tree load
	public String makeStatisticsTypeTree() {
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		statisticsTypeTreeNodes= new ArrayList<ZTreeSimpleNode>();
		try{
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			if(statisticsCode!=null){
				filters.add(new PropertyFilter("EQS_statisticsCode",statisticsCode));
			}
			this.statisticsTypes=this.statisticsTypeManager.getByFilters(filters);
			ZTreeSimpleNode rootNode = new ZTreeSimpleNode();
			rootNode.setId("-1");
			rootNode.setName("统计项");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
//			rootNode.setIcon(iconPath+"category.png");
			statisticsTypeTreeNodes.add(rootNode);
			if(this.statisticsTypes!=null&&this.statisticsTypes.size()>0){
				for(StatisticsType statisticsTypeTemp:statisticsTypes){
					ZTreeSimpleNode typeNode = new ZTreeSimpleNode();
					typeNode.setId(statisticsTypeTemp.getId());
					typeNode.setName(statisticsTypeTemp.getName());
					if(statisticsTypeTemp.getParentType()==null){
						typeNode.setpId("-1");
					}else{
						typeNode.setpId(statisticsTypeTemp.getParentType().getId());
					}
					typeNode.setIsParent(false);
					typeNode.setSubSysTem("statisticsType");
//					typeNode.setIcon(iconPath+"category.png");
					statisticsTypeTreeNodes.add(typeNode);
				}
			}
		}catch(Exception e){
			 log.error("makeStatisticsTypeTree Error", e);
		}
		return SUCCESS;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getStatisticsCode() {
		return statisticsCode;
	}

	public void setStatisticsCode(String statisticsCode) {
		this.statisticsCode = statisticsCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}

