package com.huge.ihos.inout.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.inout.service.CostItemManager;
import com.huge.ihos.inout.service.CostUseManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class CostItemAction extends JqGridBaseAction {
	private final String DIC_MEDORLEE_KEY = "medorlee";

	private CostUseManager costUseManager;

	private CostItemManager costItemManager;

	private List costItems;

	private CostItem costItem;

	private String costItemId;

	private List allCostUseList;

	private int numNameSpace;

	public int getNumNameSpace() {
		return numNameSpace;
	}

	public void setNumNameSpace(int numNameSpace) {
		this.numNameSpace = numNameSpace;
	}

	// private DictionaryItemManager dictionaryItemManager;
	private List dictionaryItemsCost;

	private List allCostItemList;

	public void setCostUseManager(CostUseManager costUseManager) {
		this.costUseManager = costUseManager;
	}

	public void setCostItemManager(CostItemManager costItemManager) {
		this.costItemManager = costItemManager;
	}

	public List getCostItems() {
		return costItems;
	}

	private List medorleeList;

	public List getMedorleeList() {
		return medorleeList;
	}

	public void setMedorleeList(List medorleeList) {
		this.medorleeList = medorleeList;
	}

	/**
	 * Grab the entity from the database before populating with request
	 * parameters
	 */
	@Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String costItemId = getRequest().getParameter("costItem.costItemId");
             if (costItemId != null && !costItemId.equals("")) {
                 costItem = costItemManager.get(new String(costItemId));
             }
         }*/
        this.medorleeList = this.getDictionaryItemManager().getAllItemsByCode( DIC_MEDORLEE_KEY );
        this.dictionaryItemsCost = this.getDictionaryItemManager().getAllItemsByCode( "costItem_behaviour" );
        this.allCostUseList = this.costUseManager.getAll();
        this.allCostItemList = this.costItemManager.getAll();
        String nameSpace = this.getGlobalParamByKey("costItemName_space");
        if(OtherUtil.measureNotNull(nameSpace)){
        	this.numNameSpace = Integer.parseInt(nameSpace);
        }else{
        	this.numNameSpace = 0;
        }
        
        this.clearSessionMessages();
    }

	/*
	 * public String list() { costItems = costItemManager.search(query,
	 * CostItem.class); return SUCCESS; }
	 */

	public void setCostItemId(String costItemId) {
		this.costItemId = costItemId;
	}

	public CostItem getCostItem() {
		return costItem;
	}

	public void setCostItem(CostItem costItem) {
		this.costItem = costItem;
	}

	public String delete() {
		costItemManager.remove(costItem.getCostItemId());
		saveMessage(getText("costItem.deleted"));

		return "edit_success";
	}

	private boolean inUse;

	public boolean getInUse() {
		return inUse;
	}

	public String edit() {
		if (costItemId != null) {
			costItem = costItemManager.get(costItemId);
			inUse = costItemManager.isInUse(costItemId);
			this.setEntityIsNew(false);
		} else {
			costItem = new CostItem();
			this.setEntityIsNew(true);
		}

		return SUCCESS;
	}

	public String save() throws Exception {
		if (cancel != null) {
			return "cancel";
		}

		if (delete != null) {
			return delete();
		}

		boolean isNew = (costItem.getCostItemId() == null);
		if (costItem.getParent().getCostItemId() == null
				|| costItem.getParent().getCostItemId().equals("")) {
			costItem.setParent(null);
		}
		if (!isNew) {
			boolean leaf = costItem.getLeaf();
			if (leaf && costItemManager.hasChildren(costItem.getCostItemId())) {
				return ajaxForward(false, "当前项目下有子级，不能修改末级标志！", false);
			}
		}
		costItemManager.save(costItem);

		String key = (this.isEntityIsNew()) ? "costItem.added"
				: "costItem.updated";
		saveMessage(getText(key));

		return ajaxForward(getText(key));
	}

	public String costItemGridList() {
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());

			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = costItemManager.getAppManagerCriteriaWithSearch(
					pagedRequests, CostItem.class, filters);
			this.costItems = (List<CostItem>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("costItemGridList Error", e);
		}
		return SUCCESS;
	}

	public String costItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					costItem = costItemManager.get(removeId);
					if (costItemManager.hasChildren(costItem.getCostItemId())) {
						return ajaxForward(false,
								"当前项目" + costItem.getCostItemName()
										+ "下有子级，不能修改末级标志！", false);
					}
					costItemManager.remove(removeId);
				}
				gridOperationMessage = this.getText("costItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}

			if (oper.equals("add") || oper.equals("edit")) {
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return SUCCESS;
				}
				costItemManager.save(costItem);
				String key = (oper.equals("add")) ? "costItem.added"
						: "costItem.updated";
				gridOperationMessage = this.getText(key);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPeriodGridEdit Error", e);
			gridOperationMessage = e.getMessage();
			return ajaxForward(false, "删除失败，请检查数据！", false);
		}
	}

	/**
	 * @TODO you should add some validation rules those are difficult on client
	 *       side.
	 * @return
	 */
	private String isValid() {
		if (costItem == null) {
			return "Invalid costItem Data";
		}

		return SUCCESS;

	}

	public List getAllCostUseList() {
		return allCostUseList;
	}

	/*
	 * public void setDictionaryItemManager(DictionaryItemManager
	 * dictionaryItemManager) { this.dictionaryItemManager =
	 * dictionaryItemManager; }
	 */
	public List getDictionaryItemsCost() {
		return dictionaryItemsCost;
	}

	public List getAllCostItemList() {
		return allCostItemList;
	}
}