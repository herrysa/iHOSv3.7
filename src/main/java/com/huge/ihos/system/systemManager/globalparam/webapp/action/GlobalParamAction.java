package com.huge.ihos.system.systemManager.globalparam.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.globalparam.model.GlobalParam;
import com.huge.ihos.system.systemManager.globalparam.model.GlobalParamCard;
import com.huge.ihos.system.systemManager.globalparam.service.GlobalParamManager;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.util.HaspDogHandler;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class GlobalParamAction extends JqGridBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4032298776344009079L;

	private GlobalParamManager globalParamManager;

	private MenuManager menuManager;

	private List<GlobalParam> globalParams;

	private GlobalParam globalParam;

	private Long paramId;

	private List<Menu> subSystems;

	private String subSystem;

	private List<GlobalParamCard> globalParamCards;

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	private Map<String, Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();

	public Map<String, Boolean> getDogMenus() {
		return dogMenus;
	}

	public void setDogMenus(Map<String, Boolean> dogMenus) {
		this.dogMenus = dogMenus;
	}

	@Override
	public void prepare() throws Exception {
		super.prepare();
		List<Menu> subsystems = this.menuManager.getAllSubSystem();
		this.subSystems = new ArrayList();
		for (Menu menu : subsystems) {
			if ((dogMenus.get(menu.getMenuId()) == null) ? false : true) {
				this.subSystems.add(menu);
			}
		}
		this.clearSessionMessages();
	}

	public String globalparamGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if (OtherUtil.measureNotNull(subSystem)) {
				if ("HR".equalsIgnoreCase(subSystem)) {
					filters.add(new PropertyFilter("EQS_subSystemId", "人力资源管理系统"));
				} else if ("GZ".equalsIgnoreCase(subSystem)) {
					filters.add(new PropertyFilter("EQS_subSystemId", "薪资管理系统"));
				}else if ("BM".equalsIgnoreCase(subSystem)) {
					filters.add(new PropertyFilter("EQS_subSystemId", "全面预算管理系统"));
				}
			}
			String paramType = getRequest().getParameter("paramType");
			if (OtherUtil.measureNotNull(paramType)) {
				filters.add(new PropertyFilter("EQS_paramType", paramType));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = globalParamManager.getGlobalParamCriteria(pagedRequests, filters);
			this.globalParams = (List<GlobalParam>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

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
			globalParamManager.save(globalParam);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "globalParam.added" : "globalParam.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (paramId != null) {
			globalParam = globalParamManager.get(paramId);
			this.setEntityIsNew(false);
		} else {
			globalParam = new GlobalParam();
			if (OtherUtil.measureNotNull(subSystem)) {
				globalParam.setSubSystemId(subSystem);
				if ("HR".equalsIgnoreCase(subSystem)) {
					globalParam.setSubSystemId("人力资源管理系统");
				} else if ("GZ".equalsIgnoreCase(subSystem)) {
					globalParam.setSubSystemId("薪资管理系统");
				}else if ("BM".equalsIgnoreCase(subSystem)) {
					globalParam.setSubSystemId("全面预算管理系统");
				}
			}
			this.setEntityIsNew(true);
		}
		List<Menu> subsystems = this.menuManager.getAllSubSystem();
		this.subSystems = new ArrayList();
		for (Menu menu : subsystems) {
			if ((dogMenus.get(menu.getMenuId()) == null) ? false : true) {
				this.subSystems.add(menu);
			}
		}
		this.setRandom(this.getRequest().getParameter("random"));
		return SUCCESS;
	}

	public String globalparamGridEdit() {
		boolean flag = true;
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					int removeId = Integer.parseInt(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					GlobalParam globalParam = globalParamManager.get(new Long(removeId));
					globalParamManager.remove(new Long(removeId));

				}
				if (flag) {
					gridOperationMessage = this.getText("globalParam.deleted");
				}
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGlobalParamGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, "删除错误，请检查数据！", false);
		}
	}

	public String globalParamCard() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if (OtherUtil.measureNotNull(subSystem)) {
			if ("HR".equalsIgnoreCase(subSystem)) {
				filters.add(new PropertyFilter("EQS_subSystemId", "人力资源管理系统"));
			} else if ("GZ".equalsIgnoreCase(subSystem)) {
				filters.add(new PropertyFilter("EQS_subSystemId", "薪资管理系统"));
			}
		}
		globalParams = this.globalParamManager.getByFilters(filters);
		globalParamCards = new ArrayList<GlobalParamCard>();
		if (globalParams != null && !globalParams.isEmpty()) {
			for (int i = 0; i < globalParams.size(); i++) {
				GlobalParam globalParam = globalParams.get(i);
				GlobalParamCard card = new GlobalParamCard();
				card.setParamName(globalParam.getParamName());
				card.setParamType(globalParam.getParamType());
				if (!"".equals(globalParam.getSelectOptions()) && globalParam.getSelectOptions() != null) {
					String[] options = globalParam.getSelectOptions().split(";");
					for (int j = 0; j < options.length; j++) {
						String[] option = options[j].split(":");
						String key = option[0];
						String value = option[1];
						if (key.equals(globalParam.getParamValue())) {
							card.setParamValue(value);
							break;
						} else {
							continue;
						}
					}
				} else {
					card.setParamValue(globalParam.getParamValue());
				}
				globalParamCards.add(card);
			}
		}
		return SUCCESS;
	}

	private String isValid() {
		if (globalParam == null) {
			return "Invalid globalParam Data";
		}

		return SUCCESS;

	}

	public void setGlobalParamManager(GlobalParamManager globalParamManager) {
		this.globalParamManager = globalParamManager;
	}

	public List<GlobalParam> getGlobalParams() {
		return globalParams;
	}

	public void setGlobalParams(List<GlobalParam> globalParams) {
		this.globalParams = globalParams;
	}

	public GlobalParam getGlobalParam() {
		return globalParam;
	}

	public void setGlobalParam(GlobalParam globalParam) {
		this.globalParam = globalParam;
	}

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public List<Menu> getSubSystems() {
		return subSystems;
	}

	public void setSubSystems(List<Menu> subSystems) {
		this.subSystems = subSystems;
	}

	public List<GlobalParamCard> getGlobalParamCards() {
		return globalParamCards;
	}

	public void setGlobalParamCards(List<GlobalParamCard> globalParamCards) {
		this.globalParamCards = globalParamCards;
	}

}
