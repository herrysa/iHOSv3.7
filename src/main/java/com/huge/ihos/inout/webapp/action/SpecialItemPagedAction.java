package com.huge.ihos.inout.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.inout.model.SpecialItem;
import com.huge.ihos.inout.service.SpecialItemManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class SpecialItemPagedAction extends JqGridBaseAction  {

	private SpecialItemManager specialItemManager;
	private List<SpecialItem> specialItems;
	private SpecialItem specialItem;
	private String itemId;
	
	private List dicSpecialItemList;

	  @Override
	    public void prepare() throws Exception {
	        super.prepare();
		this.dicSpecialItemList = this.getDictionaryItemManager().getAllItemsByCode( "specialitemtype" );
		this.clearSessionMessages();
	}
	public String specialItemGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = specialItemManager
					.getSpecialItemCriteria(pagedRequests,filters);
			this.specialItems = (List<SpecialItem>) pagedRequests.getList();
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
			specialItemManager.save(specialItem);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "specialItem.added" : "specialItem.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (itemId != null) {
        	specialItem = specialItemManager.get(itemId);
        	this.setEntityIsNew(false);
        } else {
        	specialItem = new SpecialItem();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String specialItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					SpecialItem specialItem = specialItemManager.get(removeId);
					specialItemManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("specialItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSpecialItemGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, "该字典项已被引用，请检查后再试！", false);
		}
	}

	private String isValid() {
		if (specialItem == null) {
			return "Invalid specialItem Data";
		}

		return SUCCESS;

	}
	public SpecialItemManager getSpecialItemManager() {
		return specialItemManager;
	}

	public void setSpecialItemManager(SpecialItemManager specialItemManager) {
		this.specialItemManager = specialItemManager;
	}

	public List<SpecialItem> getspecialItems() {
		return specialItems;
	}

	public void setSpecialItems(List<SpecialItem> specialItems) {
		this.specialItems = specialItems;
	}

	public SpecialItem getSpecialItem() {
		return specialItem;
	}

	public void setSpecialItem(SpecialItem specialItem) {
		this.specialItem = specialItem;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
        this.itemId = itemId;
    }
	public List getDicSpecialItemList() {
		return dicSpecialItemList;
	}
	public void setDicSpecialItemList(List dicSpecialItemList) {
		this.dicSpecialItemList = dicSpecialItemList;
	}
}

