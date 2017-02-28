package com.huge.ihos.material.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.model.InventoryType;
import com.huge.ihos.material.service.InventoryDictManager;
import com.huge.ihos.material.service.InventoryTypeManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.opensymphony.xwork2.Preparable;

public class InventoryTypePagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = -2350281154726367827L;
	private InventoryTypeManager inventoryTypeManager;
	private List<InventoryType> inventoryTypes;
	private InventoryType inventoryType;
	private String id;
	private String parentId;

	private InventoryDictManager inventoryDictManager;
	private List<InventoryType> inventoryTypeTreeList;

	public void setInventoryDictManager(InventoryDictManager inventoryDictManager) {
		this.inventoryDictManager = inventoryDictManager;
	}

	public List<InventoryType> getInventoryTypeTreeList() {
		return inventoryTypeTreeList;
	}

	public void setInventoryTypeTreeList(
			List<InventoryType> inventoryTypeTreeList) {
		this.inventoryTypeTreeList = inventoryTypeTreeList;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setInventoryTypeManager(
			InventoryTypeManager inventoryTypeManager) {
		this.inventoryTypeManager = inventoryTypeManager;
	}

	public List<InventoryType> getInventoryTypes() {
		return inventoryTypes;
	}

	public void setInventoryTypes(List<InventoryType> inventoryTypes) {
		this.inventoryTypes = inventoryTypes;
	}

	public InventoryType getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(InventoryType inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
		codeRule = "2-2-2-2";
	}

	public String inventoryTypeGridList() {
		log.debug("enter list method!");
		/*
		 * try { List<PropertyFilter> filters =
		 * PropertyFilter.buildFromHttpRequest(getRequest()); JQueryPager
		 * pagedRequests = null; pagedRequests = (JQueryPager)
		 * pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest());
		 * pagedRequests = inventoryTypeManager
		 * .getInventoryTypeCriteria(pagedRequests,filters); this.inventoryTypes
		 * = (List<InventoryType>) pagedRequests.getList(); records =
		 * pagedRequests.getTotalNumberOfRows(); total =
		 * pagedRequests.getTotalNumberOfPages(); page =
		 * pagedRequests.getPageNumber();
		 * 
		 * } catch (Exception e) { log.error("List Error", e); }
		 */
		return SUCCESS;
	}

	private InventoryType addInvType;
	
	public InventoryType getAddInvType() {
		return addInvType;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(this.isEntityIsNew()){
				UserContext userContext = UserContextUtil.getUserContext();
				String orgCode = userContext.getOrgCode();
				String copyCode = userContext.getCopyCode();
				inventoryType.setOrgCode(orgCode);
				inventoryType.setCopyCode(copyCode);
				inventoryType.setId(orgCode+"_"+copyCode+"_"+inventoryType.getInvTypeCode());
				inventoryType.setCostItem("".equals(inventoryType.getCostItem().getCostItemId())?null:inventoryType.getCostItem());
			}else{
				InventoryType invTypeTemp = inventoryType;
				inventoryType = inventoryTypeManager.get(id);
				inventoryType.setInvTypeName(invTypeTemp.getInvTypeName());
				inventoryType.setRemark(invTypeTemp.getRemark());
				inventoryType.setDisabled(invTypeTemp.getDisabled());
				inventoryType.setIsBudg(invTypeTemp.getIsBudg());
				inventoryType.setLeaf(invTypeTemp.getLeaf());
				inventoryType.setCostItem("".equals(invTypeTemp.getCostItem().getCostItemId())?null:invTypeTemp.getCostItem());
			}
			inventoryType.setParentNode(inventoryTypeManager.get(parentId));
			addInvType= inventoryTypeManager.save(inventoryType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "inventoryType.added"
				: "inventoryType.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (id != null) {
			inventoryType = inventoryTypeManager.get(id);
			this.setEntityIsNew(false);
		} else {
			//super.setCodeRule(InventoryType.class);
			inventoryType = new InventoryType();
			inventoryType.setParentNode(inventoryTypeManager.get(parentId));
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}
	
	public String delete(){
		try {
			InventoryType inventoryType = inventoryTypeManager.get(id);
			//判断是否被物资字典引用
			List<InventoryDict> invDicts = inventoryDictManager.getInventoryDictByInventoryType(inventoryType);
			if(invDicts!=null && invDicts.size()>0) {
				return ajaxForwardError("该类别正在被使用，不能删除！");
			}
			InventoryType inventoryTypeParent = inventoryType.getParentNode();
			inventoryTypeManager.delete(id);
			int childCount = inventoryTypeManager.getChildrenCount(inventoryTypeParent.getId());
			if(childCount==0){
				inventoryTypeParent.setLeaf(true);
				inventoryTypeManager.save(inventoryTypeParent);
			}
			return ajaxForward(this.getText("inventoryType.deleted"));
		} catch (Exception e) {
			log.error("deleteInventoryType Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String reBuildTree(){
		inventoryTypeManager.reBuildAllTree();
		return SUCCESS;
	}

	public String inventoryTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					inventoryTypeManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("inventoryType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInventoryTypeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (inventoryType == null) {
			return "Invalid inventoryType Data";
		}

		return SUCCESS;

	}

	public String makeInventoryTypeTree() {
		UserContext userContext = UserContextUtil.getUserContext();
		String disabled = this.getRequest().getParameter("disabled");
		inventoryTypeTreeList = inventoryTypeManager.getFullInvTypeTree(
				userContext.getOrgCode(), userContext.getCopyCode(),disabled);
		try {
			inventoryType = inventoryTypeManager.get("-1");
		} catch (ObjectRetrievalFailureException e) {
			inventoryType = new InventoryType();
			inventoryType.setId("-1");
			inventoryType.setInvTypeCode("-1");
			inventoryType.setOrgCode("-1");
			inventoryType.setCopyCode("-1");
			inventoryType.setLeaf(false);
			inventoryType.setLevel(0);
			inventoryType.setLft(1);
			inventoryType.setRgt(2);
			inventoryType.setInvTypeName("物资类别");
			inventoryType = inventoryTypeManager.save(inventoryType);
		}
		inventoryTypeTreeList.add(inventoryType);
		return SUCCESS;
	}
	private List<InventoryType> parentAndChildList;
	
	public List<InventoryType> getParentAndChildList() {
		return parentAndChildList;
	}

	public void setParentAndChildList(List<InventoryType> parentAndChildList) {
		this.parentAndChildList = parentAndChildList;
	}

	public String refreshInvTypeNode() {
		UserContext userContext = UserContextUtil.getUserContext();
		String id = this.getId();
        parentAndChildList = this.inventoryTypeManager.getAllDescendant( userContext.getOrgCode(), userContext.getCopyCode(),id);
        return SUCCESS;
    }
	
	private Boolean hasInvTypeChildren;
	
	public Boolean getHasInvTypeChildren() {
		return hasInvTypeChildren;
	}

	public String hasInvTypeChildren(){
		int childCount = inventoryTypeManager.getChildrenCount(id);
		hasInvTypeChildren = (childCount!=0);
		return SUCCESS;
	}
}
