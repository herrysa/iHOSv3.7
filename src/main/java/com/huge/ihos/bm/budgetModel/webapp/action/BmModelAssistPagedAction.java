package com.huge.ihos.bm.budgetModel.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.huge.ihos.bm.budgetAssistType.model.BudgetAssistType;
import com.huge.ihos.bm.budgetAssistType.service.BudgetAssistTypeManager;
import com.huge.ihos.bm.budgetModel.model.BmModelAssist;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BmModelAssistManager;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.ihos.system.systemManager.dataprivilege.model.DataPrivilege;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.pagers.SortOrderEnum;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BmModelAssistPagedAction extends JqGridBaseAction implements Preparable {

	private BmModelAssistManager bmModelAssistManager;
	private List<BmModelAssist> bmModelAssists;
	private BmModelAssist bmModelAssist;
	private BudgetAssistTypeManager budgetAssistTypeManager;
	private BudgetModelManager budgetModelManager;
	public void setBudgetModelManager(BudgetModelManager budgetModelManager) {
		this.budgetModelManager = budgetModelManager;
	}

	public void setBudgetAssistTypeManager(
			BudgetAssistTypeManager budgetAssistTypeManager) {
		this.budgetAssistTypeManager = budgetAssistTypeManager;
	}
	private String id;

	public BmModelAssistManager getBmModelAssistManager() {
		return bmModelAssistManager;
	}

	public void setBmModelAssistManager(BmModelAssistManager bmModelAssistManager) {
		this.bmModelAssistManager = bmModelAssistManager;
	}

	public List<BmModelAssist> getbmModelAssists() {
		return bmModelAssists;
	}

	public void setBmModelAssists(List<BmModelAssist> bmModelAssists) {
		this.bmModelAssists = bmModelAssists;
	}

	public BmModelAssist getBmModelAssist() {
		return bmModelAssist;
	}

	public void setBmModelAssist(BmModelAssist bmModelAssist) {
		this.bmModelAssist = bmModelAssist;
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
	String layoutH;
	public String getLayoutH() {
		return layoutH;
	}

	public void setLayoutH(String layoutH) {
		this.layoutH = layoutH;
	}
	String parentRandom;
	public String getParentRandom() {
		return parentRandom;
	}

	public void setParentRandom(String parentRandom) {
		this.parentRandom = parentRandom;
	}
	String assistCode;
	String assistName;
	String modelId;
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getAssistName() {
		return assistName;
	}

	public void setAssistName(String assistName) {
		this.assistName = assistName;
	}

	public String getAssistCode() {
		return assistCode;
	}

	public void setAssistCode(String assistCode) {
		this.assistCode = assistCode;
	}
	String pageType;

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	BdInfoUtil bdInfoUtil;
	public BdInfoUtil getBdInfoUtil() {
		return bdInfoUtil;
	}

	public void setBdInfoUtil(BdInfoUtil bdInfoUtil) {
		this.bdInfoUtil = bdInfoUtil;
	}

	@SuppressWarnings("unused")
	public String bmModelAssistList(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_assistType.typeCode",assistCode));
		List<BudgetAssistType> budgetAssistTypes = budgetAssistTypeManager.getByFilters(filters);
		BdInfo bdInfo = null;
		if(budgetAssistTypes!=null&&budgetAssistTypes.size()>0){
			BudgetAssistType budgetAssistType = budgetAssistTypes.get(0);
			assistName = budgetAssistType.getColName();
			bdInfo = budgetAssistType.getAssistType().getBdInfo();
			if(bdInfo!=null){
				bdInfoUtil = new BdInfoUtil(bdInfo);
			}
		}
		return SUCCESS;
	}
	
	List bmAssistItems;
	
	public List getBmAssistItems() {
		return bmAssistItems;
	}

	public void setBmAssistItems(List bmAssistItems) {
		this.bmAssistItems = bmAssistItems;
	}

	public String bmModelAssistGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			BdInfo bdInfo = null;
			List<PropertyFilter> assistFilters = new ArrayList<PropertyFilter>();
			assistFilters.add(new PropertyFilter("EQS_assistType.typeCode",assistCode));
			List<BudgetAssistType> budgetAssistTypes = budgetAssistTypeManager.getByFilters(assistFilters);
			if(budgetAssistTypes!=null&&budgetAssistTypes.size()>0){
				bdInfo = budgetAssistTypes.get(0).getAssistType().getBdInfo();
			}
			BdInfoUtil bdInfoUtil = new BdInfoUtil();
			//bdInfoUtil.setOrgJoin(true);
			//bdInfoUtil.setDeptJoin(true);
			bdInfoUtil.setOnlyShowMain(true);
			bdInfoUtil.setFilterXT(true);
			bdInfoUtil.setFilterDisabled(true);
			//bdInfoUtil.setUnionInfilter(true);
			bdInfoUtil.addBdInfo(bdInfo);
			List<PropertyFilter> bmModelAssistFilters = new ArrayList<PropertyFilter>();
			bmModelAssistFilters.add(new PropertyFilter("EQS_assistCode.typeCode",assistCode));
			bmModelAssistFilters.add(new PropertyFilter("EQS_model.modelId",modelId));
			List<BmModelAssist> bmModelAssists = bmModelAssistManager.getByFilters(bmModelAssistFilters);
			String initStr = "";
			for(BmModelAssist bmModelAssist : bmModelAssists){
				initStr += bmModelAssist.getItem()+",";
			}
			if(StringUtils.isNotEmpty(initStr)){
				initStr = initStr.substring(0, initStr.length()-1);
			}
			if("add".equals(pageType)){
				filters.add(new PropertyFilter("NIS_"+bdInfoUtil.getPkCol().getFieldCode(),initStr));
			}else{
				filters.add(new PropertyFilter("INS_"+bdInfoUtil.getPkCol().getFieldCode(),initStr));
			}
			bdInfoUtil.addFilters(filters);
			String aliasSort = pagedRequests.getSortCriterion();
			String orderDirec = "ASC";
			if (pagedRequests.getSortDirection() == SortOrderEnum.DESCENDING) {
				orderDirec = "DESC";
			}
			pagedRequests.setSortCriterion(null);
			if (aliasSort.contains(",")) {
				String[] aliasArr = aliasSort.split(",");
				for (String aliasOrder : aliasArr) {
					aliasOrder = aliasOrder.trim();
					aliasOrder = aliasOrder.replaceAll(" +", " ");
					String[] alias = aliasOrder.split(" ");
					String direc = "ASC";
					if (alias.length > 1) {
						direc = alias[1].toUpperCase();
						bdInfoUtil.addSort(alias[0], direc, true);
					} else {
						bdInfoUtil.addSort(alias[0], orderDirec, true);
					}
				}
			} else {
				bdInfoUtil.addSort(aliasSort, orderDirec, true);
			}
			pagedRequests = bmModelAssistManager.getBdInfoCriteriaWithSearch(pagedRequests, bdInfoUtil, filters);
			bmAssistItems = pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	String saveIds;
	String names;
	public String getSaveIds() {
		return saveIds;
	}

	public void setSaveIds(String saveIds) {
		this.saveIds = saveIds;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}
	public String saveBmModelAssist(){
		try {
			//dataPrivilegeManager.deleteByRoleIdAndClass(ruId, privilegeClass);
			BudgetModel budgetModel = budgetModelManager.get(modelId);
			List<PropertyFilter> assistFilters = new ArrayList<PropertyFilter>();
			assistFilters.add(new PropertyFilter("EQS_assistType.typeCode",assistCode));
			List<BudgetAssistType> budgetAssistTypes = budgetAssistTypeManager.getByFilters(assistFilters);
			BudgetAssistType budgetAssistType = null;
			AssistType assistType = null;
			if(budgetAssistTypes!=null&&budgetAssistTypes.size()>0){
				budgetAssistType = budgetAssistTypes.get(0);
				assistType = budgetAssistType.getAssistType();
				assistName = budgetAssistType.getColName();
			}
			String[] sidArr = saveIds.split(",");
			String[] nameArr = names.split(",");
			for(int i=0;i<sidArr.length;i++){
				BmModelAssist bmModelAssistTemp = new BmModelAssist();
				bmModelAssistTemp.setAssistCode(assistType);
				bmModelAssistTemp.setItem(sidArr[i].trim());
				bmModelAssistTemp.setItemName(nameArr[i]);
				bmModelAssistTemp.setModel(budgetModel);
				bmModelAssistManager.save(bmModelAssistTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(assistName+"保存失败！");
		}
		return ajaxForward(true,assistName+"保存成功！",false);
	}
	
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			bmModelAssistManager.save(bmModelAssist);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bmModelAssist.added" : "bmModelAssist.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	bmModelAssist = bmModelAssistManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	bmModelAssist = new BmModelAssist();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bmModelAssistGridEdit() {
		try {
			if (oper.equals("del")) {
				if(StringUtils.isNotEmpty(saveIds)){
					saveIds = saveIds.replace(" ", "");
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_assistCode.typeCode",assistCode));
					filters.add(new PropertyFilter("EQS_model.modelId",modelId));
					filters.add(new PropertyFilter("INS_item",saveIds));
					bmModelAssists = bmModelAssistManager.getByFilters(filters);
					for(BmModelAssist assist : bmModelAssists){
						bmModelAssistManager.remove(assist.getId());
					}
				}
				return ajaxForward(true, "删除成功！", false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBmModelAssistGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bmModelAssist == null) {
			return "Invalid bmModelAssist Data";
		}

		return SUCCESS;

	}
	

	public String bmModelAssistItemList(){
		
		
		return SUCCESS;
	}
}

