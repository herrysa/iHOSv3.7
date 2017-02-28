package com.huge.ihos.system.repository.store.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.ihos.system.repository.store.service.StoreManager;
import com.huge.ihos.system.repository.store.service.StorePositionManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;
import com.opensymphony.xwork2.Preparable;

public class StorePositionPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 4491669902126375327L;
	private StorePositionManager storePositionManager;
	private StoreManager storeManager;
	private List<StorePosition> storePositions;
	private StorePosition storePosition;
	private String posId;
	private String posCode;
	private String posName;
	private String remark;
	private String disabled;
	private Store store;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public void setStorePositionManager(StorePositionManager storePositionManager) {
		this.storePositionManager = storePositionManager;
	}

	public List<StorePosition> getStorePositions() {
		return storePositions;
	}

	public void setStorePositions(List<StorePosition> storePositions) {
		this.storePositions = storePositions;
	}

	public StorePosition getStorePosition() {
		return storePosition;
	}

	public void setStorePosition(StorePosition storePosition) {
		this.storePosition = storePosition;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
        this.posId = posId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String storePositionGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if (filters.size() == 1) {
				PropertyFilter pf = filters.get(0);
				if (pf.getPropertyName().equals("store.id") && pf.getMatchType().equals(MatchType.IN)) {

					List<Store> list = this.storeManager.getAllDescendant(null,((String[]) pf.getMatchValue())[0],true);
					List<String> sl = new ArrayList<String>();

					for (Iterator<Store> iterator = list.iterator(); iterator.hasNext();) {
						Store o = (Store) iterator.next();
						sl.add(o.getId());
					}
					sl.add(((String[]) pf.getMatchValue())[0]);
					Object[] objs = sl.toArray();
					pf.setMatchValue(objs);
				}
			}
			//SystemVariable systemVariable = this.getCurrentSystemVariable();
			String orgCode = UserContextUtil.getUserContext().getOrgCode();
			filters.add(new PropertyFilter("EQS_orgCode", orgCode));
			//filters.add(new PropertyFilter("EQS_store.id",storeId));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = storePositionManager
					.getStorePositionCriteria(pagedRequests,filters);
			this.storePositions = (List<StorePosition>) pagedRequests.getList();
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
			storePositionManager.save(storePosition);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "storePosition.added" : "storePosition.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (posId != null) {
        	storePosition = storePositionManager.get(posId);
        	this.setEntityIsNew(false);
        } else {
        	storePosition = new StorePosition();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String storePositionGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
//					StorePosition storePosition = storePositionManager.get(new String(removeId));
					//货位删除之前，需要判断是否有数据引用	
					storePositionManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("storePosition.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if (oper.equals("edit")) {
//				SystemVariable systemVariable = this.getCurrentSystemVariable();
//				String orgCode = systemVariable.getOrgCode();
				if (storePosition == null) {
					if (posId == null || "".equals(posId)) {
						posId = store.getId() + "_" + posCode;
						storePosition = new StorePosition();
					} else {
						storePosition = storePositionManager.get(posId);
						posCode = storePosition.getPosCode();
					}
					String orgCode = UserContextUtil.getUserContext().getOrgCode();
					storePosition.setPosId(posId);
					storePosition.setOrgCode(orgCode);
					storePosition.setPosCode(posCode);
					storePosition.setPosName(posName);
					storePosition.setRemark(remark);
					storePosition.setDisabled("Yes".equalsIgnoreCase(disabled) ? true
							: false);
					storePosition.setStore(store);
				}
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return ajaxForwardError(gridOperationMessage);
				}
				storePositionManager.save(storePosition);
				String key = ((this.isEntityIsNew())) ? "storePosition.added"
						: "storePosition.updated";
				gridOperationMessage = this.getText(key);
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkStorePositionGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private String isValid() {
		if (storePosition == null) {
			return "Invalid storePosition Data";
		}

		return SUCCESS;

	}
}

