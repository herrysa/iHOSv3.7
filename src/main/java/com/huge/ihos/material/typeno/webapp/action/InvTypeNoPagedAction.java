package com.huge.ihos.material.typeno.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.service.InvCheckManager;
import com.huge.ihos.material.service.InvMainManager;
import com.huge.ihos.material.typeno.model.InvTypeNo;
import com.huge.ihos.material.typeno.service.InvTypeNoManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class InvTypeNoPagedAction extends JqGridBaseAction implements Preparable {

	private InvTypeNoManager invTypeNoManager;
	private List<InvTypeNo> invTypeNoes;
	private InvTypeNo invTypeNo;
	private String id;
	private String zhuanquData;
	
	private InvMainManager invMainManager;
	private InvCheckManager invCheckManager;
	
	public void setInvCheckManager(InvCheckManager invCheckManager) {
		this.invCheckManager = invCheckManager;
	}

	public void setInvMainManager(InvMainManager invMainManager) {
		this.invMainManager = invMainManager;
	}

	public InvTypeNoManager getInvTypeNoManager() {
		return invTypeNoManager;
	}

	public void setInvTypeNoManager(InvTypeNoManager invTypeNoManager) {
		this.invTypeNoManager = invTypeNoManager;
	}

	public List<InvTypeNo> getinvTypeNoes() {
		return invTypeNoes;
	}

	public void setInvTypeNos(List<InvTypeNo> invTypeNoes) {
		this.invTypeNoes = invTypeNoes;
	}

	public InvTypeNo getInvTypeNo() {
		return invTypeNo;
	}

	public void setInvTypeNo(InvTypeNo invTypeNo) {
		this.invTypeNo = invTypeNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }
	
	public void setZhuanquData(String zhuanquData) {
		this.zhuanquData = zhuanquData;
	}
	
	private String docId;

	public String getDocId() {
		return docId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String checkMaterialDocument(){
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			String orgCode = userContext.getOrgCode();
			String copyCode = userContext.getCopyCode();
			String type = invTypeNoManager.getTypeByNo(zhuanquData,orgCode,copyCode);
			if("4".equals(type)){
				InvCheck invCheck = invCheckManager.getInvCheckByCheckNo(zhuanquData,orgCode, copyCode);
				if(invCheck==null){
					return ajaxForward( false, "单据号异常！", false);
				}else{
					docId = invCheck.getCheckId();
				}
			}else{
				InvMain invMain = invMainManager.getInvMainByNo(zhuanquData,orgCode,copyCode);
				if(invMain == null){
					return ajaxForward( false, "单据号异常！", false);
				}else{
					docId = invMain.getIoId();
				}
			}
			if("1".equals(type)){
//				this.setForwardUrl("editInvMainInit?ioId="+docId);
//				this.setDialogId("showInvMainInit");
//				this.setResultName("期初入库单明细");
				return "invMainInit";
			}else if("2".equals(type)){
//				this.setForwardUrl("editInvMainIn?ioId="+docId);
//				this.setDialogId("showInvMainIn");
//				this.setResultName("入库单明细");
				return "invMainIn";
			}else if("3".equals(type)){
//				this.setForwardUrl("editInvMainOut?ioId="+docId);
//				this.setDialogId("showInvMainOut");
//				this.setResultName("出库单明细");
				return "invMainOut";
			}else if("4".equals(type)){
//				this.setForwardUrl("editInvCheck?checkId="+docId);
//				this.setDialogId("showInvCheck");
//				this.setResultName("盘点单明细");
				return "invCheck";
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("getActionByNo Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward( false, "单据号异常！", false);
		}
	}
	public String invTypeNoGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext
					.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext
					.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = invTypeNoManager
					.getInvTypeNoCriteria(pagedRequests,filters);
			this.invTypeNoes = (List<InvTypeNo>) pagedRequests.getList();
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
			invTypeNoManager.save(invTypeNo);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "invTypeNo.added" : "invTypeNo.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	invTypeNo = invTypeNoManager.get(Long.parseLong(id));
        	this.setEntityIsNew(false);
        } else {
        	invTypeNo = new InvTypeNo();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String invTypeNoGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					//String removeId = ids.nextToken();
					Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				Long[] ida=new Long[idl.size()];
				idl.toArray(ida);
				this.invTypeNoManager.remove(ida);
				gridOperationMessage = this.getText("invTypeNo.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInvTypeNoGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (invTypeNo == null) {
			return "Invalid invTypeNo Data";
		}

		return SUCCESS;

	}
}

