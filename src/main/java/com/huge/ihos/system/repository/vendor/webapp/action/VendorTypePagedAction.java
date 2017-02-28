package com.huge.ihos.system.repository.vendor.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.repository.vendor.model.VendorType;
import com.huge.ihos.system.repository.vendor.service.VendorManager;
import com.huge.ihos.system.repository.vendor.service.VendorTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class VendorTypePagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = 9133881339244901296L;
	private VendorTypeManager vendorTypeManager;
	private List<VendorType> vendorTypes;
	private VendorType vendorType;
	private String id;

	private List<VendorType> vendorTypeTreeList;
	private String parentId;
	private VendorManager vendorManager;

	public void setVendorManager(VendorManager vendorManager) {
		this.vendorManager = vendorManager;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<VendorType> getVendorTypeTreeList() {
		return vendorTypeTreeList;
	}

	public void setVendorTypeManager(VendorTypeManager vendorTypeManager) {
		this.vendorTypeManager = vendorTypeManager;
	}

	public List<VendorType> getVendorTypes() {
		return vendorTypes;
	}

	public void setVendorTypes(List<VendorType> vendorTypes) {
		this.vendorTypes = vendorTypes;
	}

	public VendorType getVendorType() {
		return vendorType;
	}

	public void setVendorType(VendorType vendorType) {
		this.vendorType = vendorType;
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

	@SuppressWarnings("unchecked")
	public String vendorTypeGridList() {
		log.debug("enter list method!");
		try {
//			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			this.vendorTypes = (List<VendorType>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	private VendorType addVendorType;

	public VendorType getAddVendorType() {
		return addVendorType;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if (this.isEntityIsNew()) {
				String orgCode = UserContextUtil.getOrgCodeOrLoginOrgCode();
				vendorType.setOrgCode(orgCode);
				vendorType.setId(orgCode + "_" + vendorType.getVendorTypeCode());
			} else {
				VendorType vendorTypeTemp = vendorType;
				vendorType = vendorTypeManager.get(id);
				vendorType.setVendorTypeName(vendorTypeTemp.getVendorTypeName());
				vendorType.setDisabled(vendorTypeTemp.getDisabled());
				vendorType.setLeaf(vendorTypeTemp.getLeaf());
			}
			vendorType.setParentNode(vendorTypeManager.get(parentId));
			addVendorType = vendorTypeManager.save(vendorType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "vendorType.added"
				: "vendorType.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (id != null) {
			vendorType = vendorTypeManager.get(id);
			this.setEntityIsNew(false);
		} else {
			//super.setCodeRule(VendorType.class);
			vendorType = new VendorType();
			vendorType.setParentNode(vendorTypeManager.get(parentId));
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String delete() {
		try {
			VendorType vendorType = vendorTypeManager.get(id);
			// 判断是否被供应商字典引用
			List<Vendor> vendors = vendorManager
					.getVendorsByVendorType(vendorType);
			if (vendors != null && vendors.size() > 0) {
				return ajaxForwardError("该类别正在被使用，不能删除！");
			}
			VendorType vendorTypeParent = vendorType.getParentNode();
			vendorTypeManager.delete(id);
			int childCount = vendorTypeManager
					.getChildrenCount(vendorTypeParent.getId());
			if (childCount == 0) {
				vendorTypeParent.setLeaf(true);
				vendorTypeManager.save(vendorTypeParent);
			}
			return ajaxForward(this.getText("vendorType.deleted"));
		} catch (Exception e) {
			log.error("deleteVendorType Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String vendorTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					vendorTypeManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("vendorType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkVendorTypeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String makeVendorTypeTree() {
		String disabled = this.getRequest().getParameter("disabled");
		String orgCode = UserContextUtil.getOrgCodeOrLoginOrgCode();
		vendorTypeTreeList = vendorTypeManager.getFullVendorTypeTree(orgCode,disabled);
		try {
			vendorType = vendorTypeManager.get("-1");
		} catch (ObjectRetrievalFailureException e) {
			vendorType = new VendorType();
			vendorType.setId("-1");
			vendorType.setLevel(0);
			vendorType.setLft(1);
			vendorType.setRgt(2);
			vendorType.setLeaf(false);
			vendorType.setOrgCode("-1");
			vendorType.setVendorTypeCode("-1");
			vendorType.setVendorTypeName("供应商类别");
			vendorType = vendorTypeManager.save(vendorType);
		}
		vendorTypeTreeList.add(vendorType);
		return SUCCESS;
	}

	private List<VendorType> parentAndChildList;

	public List<VendorType> getParentAndChildList() {
		return parentAndChildList;
	}

	public String refreshVendorTypeNode() {
		String orgCode = UserContextUtil.getUserContext().getOrgCode();
		parentAndChildList = this.vendorTypeManager.getAllDescendant(
				orgCode, this.getId());
		return SUCCESS;
	}

	private Boolean hasVendorTypeChildren;

	public Boolean getHasVendorTypeChildren() {
		return hasVendorTypeChildren;
	}

	public String hasVendorTypeChildren() {
		int childCount = vendorTypeManager.getChildrenCount(id);
		hasVendorTypeChildren = (childCount != 0);
		return SUCCESS;
	}

	private List<VendorType> searchVendorTypeList;

	public List<VendorType> getSearchVendorTypeList() {
		return searchVendorTypeList;
	}

	public String getSearchVendorTypes() {
		String orgCode = UserContextUtil.getUserContext().getOrgCode();
		HttpServletRequest request = this.getRequest();
		String vendorTypeCode = request.getParameter("vendorTypeCode");
		String vendorTypeName = request.getParameter("vendorTypeName");
		String disabled = request.getParameter("disabled");
		searchVendorTypeList = vendorTypeManager.getVendorTypesBySearch(
				orgCode, vendorTypeCode, vendorTypeName,
				disabled);
		return SUCCESS;
	}

	private String isValid() {
		if (vendorType == null) {
			return "Invalid vendorType Data";
		}

		return SUCCESS;

	}
}
