package com.huge.ihos.system.repository.store.webapp.action;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.huge.ihos.material.model.StoreInvSet;
import com.huge.ihos.material.service.StoreInvSetManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.service.StoreManager;
import com.huge.ihos.system.repository.store.service.StorePositionManager;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class StorePagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = 1824466378760432417L;
	private StoreManager storeManager;
	private StorePositionManager storePositionManager;
	private List<Store> stores;
	private Store store;
	private String id;
	private String parentId;
	private StoreInvSetManager storeInvSetManager;
	
	public void setStoreInvSetManager(StoreInvSetManager storeInvSetManager) {
		this.storeInvSetManager = storeInvSetManager;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	private List<Store> storeTree;

	public List<Store> getStoreTree() {
		return storeTree;
	}

	public void setStorePositionManager(StorePositionManager storePositionManager) {
		this.storePositionManager = storePositionManager;
	}

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
		codeRule = "2-2-2";
	}
	@SuppressWarnings("unchecked")
	public String storeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = storeManager
					.getStoreCriteria(pagedRequests,filters);
			this.stores = (List<Store>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	private Store addStore;
	
	public Store getAddStore() {
		return addStore;
	}

	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(this.isEntityIsNew()){
				//SystemVariable systemVariable = this.getCurrentSystemVariable();
				String orgCode = UserContextUtil.getUserContext().getOrgCode();
				store.setOrgCode(orgCode);
				store.setId(orgCode+"_"+store.getStoreCode());
				store.setIsBook(false);
				store.setIsLock(false);
				store.setDepartment("".equals(store.getDepartment().getDepartmentId())?null:store.getDepartment());
				store.setKeeper("".equals(store.getKeeper())?null:store.getKeeper());
				store.setAccountor("".equals(store.getAccountor())?null:store.getAccountor());
				store.setPurchaser("".equals(store.getPurchaser())?null:store.getPurchaser());
			}else{
				Store storeTemp = store;
				store = storeManager.get(id);
				store.setStoreName(storeTemp.getStoreName());
				store.setStoreType(storeTemp.getStoreType());
				store.setAddress(storeTemp.getAddress());
				store.setTelephone(storeTemp.getTelephone());
				store.setRemark(storeTemp.getRemark());
				store.setIsPos(storeTemp.getIsPos());
				store.setLeaf(storeTemp.getLeaf());
				store.setDisabled(storeTemp.getDisabled());
//				store.setIsBook(storeTemp.getIsBook());
//				store.setIsLock(storeTemp.getIsLock());
				store.setDepartment("".equals(storeTemp.getDepartment().getDepartmentId())?null:storeTemp.getDepartment());
				store.setKeeper("".equals(storeTemp.getKeeper())?null:storeTemp.getKeeper());
				store.setAccountor("".equals(storeTemp.getAccountor())?null:storeTemp.getAccountor());
				store.setPurchaser("".equals(storeTemp.getPurchaser())?null:storeTemp.getPurchaser());
			}
			store.setParentNode(storeManager.get(parentId));
			addStore = storeManager.save(store);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "store.added" : "store.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
			store = storeManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	//super.setCodeRule(Store.class);
        	store = new Store();
        	store.setLeaf(true);
        	store.setParentNode(storeManager.get(parentId));
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    public String delete(){
    	try {
    		Store store = storeManager.get(id);
    		//TODO 有数据引用的不能删除
    		//下面有货位的不能删除√
    		List<Store> stores = storePositionManager.getStoresByStorePosition(store);
    		if(stores!=null && stores.size()>0){
    			return ajaxForwardError("该仓库下有货位，不能删除！");
    		}
    		//有子节点的不能删除√
    		int childCount = storeManager.getChildrenCount(id);
    		if(childCount>0){
    			return ajaxForwardError("该仓库下有子节点，不能删除！");
    		}
    		//在仓库材料设置里有对应关系的不能删
    		List<StoreInvSet> storeInvSets = storeInvSetManager.getStoreInvSetsByColumn("storeId", id);
    		if(storeInvSets != null && storeInvSets.size() > 0){
    			return ajaxForwardError("该仓库下有材料，不能删除！");
    		}
    		storeManager.delete(id);
    		//删除后，是否需要更新父级leaf标志√
    		Store storeParent = store.getParentNode();
    		childCount = storeManager.getChildrenCount(storeParent.getId());
    		if(childCount==0){
    			storeParent.setLeaf(true);
    			storeManager.save(storeParent);
    		}
    		return ajaxForward(this.getText("store.deleted"));
		} catch (Exception e) {
			log.error("deleteStore Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
    }
	public String storeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					storeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("store.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkStoreGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String makeStoreTree(){
		String orgCode = UserContextUtil.getUserContext().getOrgCode();
		String disabled = this.getRequest().getParameter("disabled");
		storeTree = storeManager.getStoreTreeByColumn("orgCode",orgCode,disabled);
		try {
			store = storeManager.get("-1");
		} catch (ObjectRetrievalFailureException e) {
			store = new Store();
			store.setId("-1");
			store.setOrgCode("-1");
			store.setStoreName("所有仓库");
			store.setStoreCode("-1");
			store.setLevel(0);
			store.setLft(1);
			store.setRgt(2);
			store.setDisabled(false);
			store.setIsPos(false);
			store.setLeaf(false);
			store.setStoreType("0");
			store.setIsBook(false);
			store.setIsLock(false);
			store = storeManager.save(store);
		}
		storeTree.add(store);
		return SUCCESS;
	}
	
	public String makeStoreTreeIsPos(){
		String orgCode = UserContextUtil.getUserContext().getOrgCode();
		storeTree = storeManager.getStoreTreeByColumn("orgCode",orgCode,"isPos");
		storeTree.add(storeManager.get("-1"));
		return SUCCESS;
	}
	
	public String reBuildTree(){
		storeManager.reBuildAllTree();
		return SUCCESS;
	}
	private List<Store> parentAndChildList;
	private Boolean hasStoreChildren;
	private Boolean hasStorePositionChild;
	private Boolean hasStorePos;
	
	public Boolean getHasStorePos() {
		return hasStorePos;
	}

	public Boolean getHasStorePositionChild() {
		return hasStorePositionChild;
	}

	public Boolean getHasStoreChildren() {
		return hasStoreChildren;
	}

	public List<Store> getParentAndChildList() {
		return parentAndChildList;
	}
	
	public String hasStorePos(){
		stores = storeManager.getAllDescendant(id);
		if(stores!=null && stores.size()>0){
			for(int i=0;i<stores.size();i++){
				if(stores.get(i).getIsBook()){
					hasStorePos = true;
					break;
				}
			}
		}
		return SUCCESS;
	}
	
	public String hasStoreChildren(){
		int childCount = storeManager.getChildrenCount(id);
		hasStoreChildren = (childCount!=0);
		return SUCCESS;
	}
	
	public String hasStorePositionChild(){
		Store store = storeManager.get(id);
		List<Store> stores = storePositionManager.getStoresByStorePosition(store);
		hasStorePositionChild = (stores!=null && stores.size()>0);
		return SUCCESS;
	}
	
	public String refreshStoreNode(){
		String orgCode = UserContextUtil.getUserContext().getOrgCode();
		parentAndChildList = storeManager.getAllDescendant(orgCode,id,false);
		return SUCCESS;
	}
	
	private List<Store> searchStoreList;
	
	public List<Store> getSearchStoreList() {
		return searchStoreList;
	}

	public String getSearchStores(){
		String orgCode = UserContextUtil.getUserContext().getOrgCode();
		HttpServletRequest request = this.getRequest();
		String storeCode = request.getParameter("storeCode");
		String storeName = request.getParameter("storeName");
		String storeType = request.getParameter("storeType");
		String isPos = request.getParameter("isPos");
		String disabled = request.getParameter("disabled");
		searchStoreList = storeManager.getStoresBySearch(orgCode,storeCode,storeName,storeType,isPos,disabled);
		return SUCCESS;
	}
	private Boolean isLock;
	
	public Boolean getIsLock() {
		return isLock;
	}

	public String getStoreLockState(){
		store = storeManager.get(id);
		isLock = store.getIsLock();
		return SUCCESS;
	}
	
	/*
	 * 封帐操作
	 */
	public String doLockStore() {
		try {
			Store store = storeManager.get(id);
			store.setIsLock(true);
			storeManager.save(store);
			return ajaxForward(true, store.getStoreName() + "封帐成功!", false);
		} catch (Exception e) {
			log.error("doLockStore Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	/*
	 * 解封操作
	 */
	public String unLockStore() {
		try {
			Store store = storeManager.get(id);
			store.setIsLock(false);
			storeManager.save(store);
			return ajaxForward(true, store.getStoreName() + "解封成功!", false);
		} catch (Exception e) {
			log.error("unLockStore Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private Department department;
	
	public Department getDepartment() {
		return department;
	}
	private Date storeStartDate;
	
	public Date getStoreStartDate() {
		return storeStartDate;
	}

	public String getDeptByStore(){
		try {
			store = storeManager.get(id);
			storeStartDate = store.getStartDate();
			Date periodBeginDate = UserContextUtil.getUserContext().getPeriodBeginDate();
			//Date periodBeginDate = this.getCurrentSystemVariable().getPeriodBeginDate();
			if(storeStartDate.before(periodBeginDate)){
				storeStartDate = periodBeginDate;
			}
			department = store.getDepartment();
			return SUCCESS;
		} catch (Exception e) {
			log.error("getDeptByStore Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private String isValid() {
		if (store == null) {
			return "Invalid store Data";
		}
		return SUCCESS;

	}
}

