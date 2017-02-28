package com.huge.ihos.material.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.model.StoreInvSet;
import com.huge.ihos.material.service.InventoryDictManager;
import com.huge.ihos.material.service.StoreInvSetManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.service.StoreManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;
import com.opensymphony.xwork2.Preparable;

public class StoreInvSetPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 1024261838168490466L;
	private StoreInvSetManager storeInvSetManager;
	private List<StoreInvSet> storeInvSets;
	private StoreInvSet storeInvSet;
	private String storeInvSetId;

	private InventoryDictManager inventoryDictManager;
	private StoreManager storeManager;

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public void setInventoryDictManager(
			InventoryDictManager inventoryDictManager) {
		this.inventoryDictManager = inventoryDictManager;
	}

	public void setStoreInvSetManager(StoreInvSetManager storeInvSetManager) {
		this.storeInvSetManager = storeInvSetManager;
	}

	public List<StoreInvSet> getStoreInvSets() {
		return storeInvSets;
	}

	public void setStoreInvSets(List<StoreInvSet> storeInvSets) {
		this.storeInvSets = storeInvSets;
	}

	public StoreInvSet getStoreInvSet() {
		return storeInvSet;
	}

	public void setStoreInvSet(StoreInvSet storeInvSet) {
		this.storeInvSet = storeInvSet;
	}

	public String getStoreInvSetId() {
		return storeInvSetId;
	}

	public void setStoreInvSetId(String storeInvSetId) {
		this.storeInvSetId = storeInvSetId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	@SuppressWarnings("unchecked")
	public String storeInvSetGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			if (filters.size() == 1) {
				PropertyFilter pf = filters.get(0);
				if (pf.getPropertyName().equals("store.id") && pf.getMatchType().equals(MatchType.IN)) {
					List<Store> list = this.storeManager.getAllDescendant(((String[]) pf.getMatchValue())[0]);
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
//			filters.add(new PropertyFilter("EQS_orgCode", this.getCurrentSystemVariable().getOrgCode()));
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_inventoryDict.orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_inventoryDict.copyCode", userContext.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = storeInvSetManager.getStoreInvSetCriteria(
					pagedRequests, filters);
			this.storeInvSets = (List<StoreInvSet>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String storeInvSetSearchGridList() {
		log.debug("storeInvSetSearchGridList method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			// 过滤条件
			String currentStoreId = this.getRequest().getParameter("storeId");
			UserContext userContext = UserContextUtil.getUserContext();
			List<StoreInvSet> oneStoreList = storeInvSetManager
					.getStoreInvSetsByColumn("storeId",currentStoreId);
			if (oneStoreList != null && oneStoreList.size() > 0) {
				String alreadyIvId = "";
				for (StoreInvSet storeInvSet : oneStoreList) {
					alreadyIvId += storeInvSet.getInventoryDict().getInvId()
							+ ",";
				}
				alreadyIvId = alreadyIvId
						.substring(0, alreadyIvId.length() - 1);
				filters.add(new PropertyFilter("NIS_invId", alreadyIvId));
			}
			filters.add(new PropertyFilter("NEB_isPublic", "1"));
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			filters.add(new PropertyFilter("EQB_disabled","0"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = inventoryDictManager.getInventoryDictCriteria(
					pagedRequests, filters);
			// 封装为storeInvSet
			List<InventoryDict> invDicts = new ArrayList<InventoryDict>();
			invDicts = (List<InventoryDict>) pagedRequests.getList();
			StoreInvSet storeInvSet = null;
			List<StoreInvSet> storeInvSett = null;
			this.storeInvSets = new ArrayList<StoreInvSet>();
			for (InventoryDict invDict : invDicts) {
				storeInvSet = new StoreInvSet();
				/**
				 * 设置storeNames：反查storeInvSet，如果invDict已经存在于其他仓库，则将其storeName做处理
				 */
				storeInvSet.setOrgCode(userContext.getOrgCode());
				storeInvSet.setInventoryDict(invDict);
				storeInvSet.setStore(storeManager.get(currentStoreId));
				storeInvSett = storeInvSetManager.getStoreInvSetsByColumn("invId", invDict.getInvId());
				if (storeInvSett != null && storeInvSett.size() > 0) {
					String storeNames = "";
					for(int i=0;i<storeInvSett.size();i++){
						storeNames += storeInvSett.get(i).getStore().getStoreName()+",";
					}
					storeNames = storeNames.substring(0, storeNames.length()-1);
					storeInvSet.setStoreNames(storeNames);
				}
				this.storeInvSets.add(storeInvSet);
			}

			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("storeInvSetSearchGridList Error", e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String saveStoreInvSetAll() {
		log.debug("saveStoreInvSetAll method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			// 过滤条件
			String currentStoreId = this.getRequest().getParameter("storeId");
			UserContext userContext = UserContextUtil.getUserContext();
			List<StoreInvSet> oneStoreList = storeInvSetManager
					.getStoreInvSetsByColumn("storeId",currentStoreId);
			if (oneStoreList != null && oneStoreList.size() > 0) {
				String alreadyIvId = "";
				for (StoreInvSet storeInvSet : oneStoreList) {
					alreadyIvId += storeInvSet.getInventoryDict().getInvId()
							+ ",";
				}
				alreadyIvId = alreadyIvId
						.substring(0, alreadyIvId.length() - 1);
				filters.add(new PropertyFilter("NIS_invId", alreadyIvId));
			}
			filters.add(new PropertyFilter("NEB_isPublic", "1"));
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			filters.add(new PropertyFilter("EQB_disabled","0"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = inventoryDictManager.getInventoryDictCriteria(
					pagedRequests, filters);
			// 封装为storeInvSet
			List<InventoryDict> invDicts = new ArrayList<InventoryDict>();
			invDicts = (List<InventoryDict>) pagedRequests.getList();
			StoreInvSet storeInvSet = null;
			Store currentStore = storeManager.get(currentStoreId);
			// 保存storeInvSets数据到表mm_storeInvSet中
			for (InventoryDict invDict : invDicts) {
				storeInvSet = new StoreInvSet();
				storeInvSet.setStore(currentStore);
				storeInvSet.setInventoryDict(invDict);
				storeInvSet.setOrgCode(userContext.getOrgCode());
				storeInvSetManager.save(storeInvSet);
			}
		} catch (Exception e) {
			log.error("saveStoreInvSetAll Error", e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String deleteStoreInvSetAll() {
		log.debug("deleteStoreInvSetAll method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = storeInvSetManager.getStoreInvSetCriteria(
					pagedRequests, filters);
			this.storeInvSets = (List<StoreInvSet>) pagedRequests.getList();
			// 删除结果集对应的记录
			for (StoreInvSet storeInvSet : this.storeInvSets) {
				storeInvSetManager.remove(storeInvSet.getStoreInvSetId());
			}
		} catch (Exception e) {
			log.error("deleteStoreInvSetAll Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			String currentStoreId = this.getRequest().getParameter("storeId");
			String invIds = this.getRequest().getParameter("invIds");
			StringTokenizer ids = new StringTokenizer(invIds, ",");
			String invId = "";
			while (ids.hasMoreTokens()) {
				invId = ids.nextToken();
				InventoryDict invDict = inventoryDictManager.get(invId);
				storeInvSet = new StoreInvSet();
				Store store = storeManager.get(currentStoreId);
				storeInvSet.setInventoryDict(invDict);
				storeInvSet.setStore(store);
				storeInvSet.setOrgCode(userContext.getOrgCode());
				storeInvSetManager.save(storeInvSet);
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = "storeInvSet.added";
		return ajaxForward(true, this.getText(key), false);
	}

	public String edit() {
		return SUCCESS;
	}

	public String storeInvSetGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					storeInvSetManager.remove(new String(removeId));
				}
				gridOperationMessage = this.getText("storeInvSet.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkStoreInvSetGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	/*private String isValid() {
		if (storeInvSet == null) {
			return "Invalid storeInvSet Data";
		}

		return SUCCESS;

	}*/
}
