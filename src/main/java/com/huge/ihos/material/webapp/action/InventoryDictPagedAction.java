package com.huge.ihos.material.webapp.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.material.model.InvUse;
import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.model.InventoryType;
import com.huge.ihos.material.service.InvUseManager;
import com.huge.ihos.material.service.InventoryDictManager;
import com.huge.ihos.material.service.InventoryTypeManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.util.DateConverter;
import com.huge.util.OptFile;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;
import com.opensymphony.xwork2.Preparable;

public class InventoryDictPagedAction extends JqGridBaseAction implements Preparable {

	private static final long serialVersionUID = 2183411302093633562L;
	private InventoryTypeManager inventoryTypeManager;

	public void setInventoryTypeManager(InventoryTypeManager inventoryTypeManager) {
		this.inventoryTypeManager = inventoryTypeManager;
	}

	private InventoryDictManager inventoryDictManager;
	private List<InventoryDict> inventoryDicts;
	private InventoryDict inventoryDict;
	private String invId;
	private List<InvUse> invUsedList;
	private InvUseManager invUseManager;

	public void setInvUseManager(InvUseManager invUseManager) {
		this.invUseManager = invUseManager;
	}

	public List<InvUse> getInvUsedList() {
		return invUsedList;
	}

	public void setInventoryDictManager(InventoryDictManager inventoryDictManager) {
		this.inventoryDictManager = inventoryDictManager;
	}

	public List<InventoryDict> getInventoryDicts() {
		return inventoryDicts;
	}

	public void setInventoryDicts(List<InventoryDict> inventoryDicts) {
		this.inventoryDicts = inventoryDicts;
	}

	public InventoryDict getInventoryDict() {
		return inventoryDict;
	}

	public void setInventoryDict(InventoryDict inventoryDict) {
		this.inventoryDict = inventoryDict;
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
		UserContext userContext = UserContextUtil.getUserContext();
		this.invUsedList = this.invUseManager.getAllByCO(userContext.getCopyCode(), userContext.getOrgCode());
	}

	@SuppressWarnings("unchecked")
	public String inventoryDictGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if (filters.size() == 1) {
				PropertyFilter pf = filters.get(0);
				if (pf.getPropertyName().equals("inventoryType.id") && pf.getMatchType().equals(MatchType.IN)) {

					List<InventoryType> list = this.inventoryTypeManager.getAllDescendant(((String[]) pf.getMatchValue())[0]);

					// String[] ids = new String[list.size()+1];
					// list.toArray(ids);
					List<String> sl = new ArrayList<String>();

					for (Iterator<InventoryType> iterator = list.iterator(); iterator.hasNext();) {
						InventoryType o = (InventoryType) iterator.next();
						sl.add(o.getId());
					}
					sl.add(((String[]) pf.getMatchValue())[0]);
					Object[] objs = sl.toArray();
					// ids[ids.length-1] = (String)pf.getMatchValue();

					pf.setMatchValue(objs);

				}
			}
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = inventoryDictManager.getInventoryDictCriteria(pagedRequests, filters);
			this.inventoryDicts = (List<InventoryDict>) pagedRequests.getList();
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
			/*
			 * inventoryDict.setInvId("test1");
			 * inventoryDict.setOrgCode("org1");
			 * inventoryDict.setCopyCode("copyCode1");
			 */
			/*if (this.isEntityIsNew()) {
				boolean rst = this.inventoryDictManager.exists(this.inventoryDict.getInvId());
				if (rst) {
					return ajaxForwardError("编码已存在，请重新输入。");
				}
			}*/
			inventoryDictManager.save(inventoryDict);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "inventoryDict.added" : "inventoryDict.updated";
		return ajaxForward(this.getText(key));
	}

	public String copy() {
		inventoryDict = inventoryDictManager.get(invId);
		inventoryDict.setInvId(null);
		this.setEntityIsNew(true);
		return SUCCESS;
	}

	private String invTypeId;

	public void setInvTypeId(String invTypeId) {
		this.invTypeId = invTypeId;
	}

	public String edit() {

		if (invId != null) {
			inventoryDict = inventoryDictManager.get(invId);

			this.setEntityIsNew(false);
		} else {
			UserContext userContext = UserContextUtil.getUserContext();
			inventoryDict = new InventoryDict();
			inventoryDict.setCopyCode(userContext.getCopyCode());
			inventoryDict.setOrgCode(userContext.getOrgCode());
			inventoryDict.setStartDate(userContext.getBusinessDate());
			if (invTypeId != null && !invTypeId.trim().equals("")) {
				InventoryType it = this.inventoryTypeManager.get(invTypeId);
				inventoryDict.setInventoryType(it);
			}

			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	/*
	 * public String delete() { StringTokenizer ids = new StringTokenizer(id,
	 * ",");
	 * 
	 * return this.ajaxForward(SUCCESS); }
	 */

	public String inventoryDictGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = (ids.nextToken());
					log.debug("Delete inventoryDict " + removeId);
					inventoryDictManager.remove(removeId);

				}
				gridOperationMessage = this.getText("inventoryDict.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInventoryDictGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String batchIds;

	public String getBatchIds() {
		return batchIds;
	}

	public void setBatchIds(String batchIds) {
		this.batchIds = batchIds;
	}

	public String inventoryDictBatchEdit() {
		this.batchIds = this.getId();
		return SUCCESS;
	}

	public String inventoryDictBatchSave() {
		StringTokenizer ids = new StringTokenizer(batchIds, ",");
		List<String> l = new ArrayList<String>();
		while (ids.hasMoreTokens()) {
			l.add(ids.nextToken());
		}
		String[] idss = new String[l.size()];
		l.toArray(idss);
		this.inventoryDictManager.batchEditUpdate(idss, this.inventoryDict);

		return ajaxForward("批量修改成功。");
	}

	private String isValid() {
		if (inventoryDict == null) {
			return "Invalid inventoryDict Data";
		}

		if (inventoryDict.getInvUse() != null && inventoryDict.getInvUse().getInvUseId() != null && inventoryDict.getInvUse().getInvUseId().equalsIgnoreCase(""))
			inventoryDict.setInvUse(null);
		if (inventoryDict.getInventoryType() != null && inventoryDict.getInventoryType().getId() != null && inventoryDict.getInventoryType().getId().equalsIgnoreCase(""))
			inventoryDict.setInventoryType(null);
		if (inventoryDict.getVendor() != null && inventoryDict.getVendor().getVendorId() != null && inventoryDict.getVendor().getVendorId().equalsIgnoreCase(""))
			inventoryDict.setVendor(null);
		else {

		}
		if (this.isEntityIsNew()) {
			inventoryDict.setInvId(inventoryDict.getOrgCode() + "_"+inventoryDict.getCopyCode() +"_"+ inventoryDict.getInvCode());
		}
		return SUCCESS;

	}

	private String Filename;

	public void setFilename(String filename) {
		Filename = filename;
	}

	private File imageFile;

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	@SuppressWarnings("deprecation")
	public String uploadImageFile() {

		HttpServletRequest req = this.getRequest();
		// String fileName =(new Date()).getTime()+"";
		String fileName = this.Filename;
		if (fileName != null && !fileName.equals("")) {
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		} else {
			fileName = (new Date()).getTime() + ".jpg";
		}
		String serverPath = "//home//inventory//";
		serverPath = req.getRealPath(serverPath);
		OptFile.mkParent(serverPath + "\\" + fileName);
		File targetFile = new File(serverPath + "\\" + fileName);
		try {
			OptFile.copyFile(imageFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.ajaxForwardSuccess(fileName);
	}

	@SuppressWarnings("deprecation")
	public String deleteImageFile() {
		String serverPath = "//home//inventory//";
		serverPath = this.getRequest().getRealPath(serverPath);
		String filePath = serverPath + "\\" + this.Filename;
		File img = new File(filePath);
		img.delete();
		return this.ajaxForwardSuccess("图片删除成功");
	}
}
