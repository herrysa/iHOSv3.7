package com.huge.ihos.accounting.voucherType.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.voucherType.model.VoucherType;
import com.huge.ihos.accounting.voucherType.service.VoucherTypeManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class VoucherTypePagedAction extends JqGridBaseAction implements Preparable {

	private VoucherTypeManager voucherTypeManager;
	private List<VoucherType> voucherTypes;
	private VoucherType voucherType;
	private String voucherTypeId;
	
	private String vouchertype;
	private String voucherTypeShort;
	private String voucherTypeCode;
	private String isUsed;
	
	public void setVouchertype(String vouchertype) {
		this.vouchertype = vouchertype;
	}

	public void setVoucherTypeShort(String voucherTypeShort) {
		this.voucherTypeShort = voucherTypeShort;
	}

	public void setVoucherTypeCode(String voucherTypeCode) {
		this.voucherTypeCode = voucherTypeCode;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public VoucherTypeManager getVoucherTypeManager() {
		return voucherTypeManager;
	}

	public void setVoucherTypeManager(VoucherTypeManager voucherTypeManager) {
		this.voucherTypeManager = voucherTypeManager;
	}

	public List<VoucherType> getvoucherTypes() {
		return voucherTypes;
	}

	public void setVoucherTypes(List<VoucherType> voucherTypes) {
		this.voucherTypes = voucherTypes;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}

	public String getVoucherTypeId() {
		return voucherTypeId;
	}

	public void setVoucherTypeId(String voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String voucherTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//SystemVariable systemVariable = this.getCurrentSystemVariable();
			filters.add(new PropertyFilter("EQS_orgCode", UserContextUtil.getUserContext().getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", UserContextUtil.getUserContext().getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = voucherTypeManager
					.getVoucherTypeCriteria(pagedRequests,filters);
			this.voucherTypes = (List<VoucherType>) pagedRequests.getList();
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
			voucherTypeManager.save(voucherType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "voucherType.added" : "voucherType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (voucherTypeId != null) {
        	voucherType = voucherTypeManager.get(voucherTypeId);
        	this.setEntityIsNew(false);
        } else {
        	voucherType = new VoucherType();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String voucherTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					VoucherType voucherType = voucherTypeManager.get(new String(removeId));
					voucherTypeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("voucherType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
				//SystemVariable systemVariable = this.getCurrentSystemVariable();
                if ( voucherType == null ) {
                    if ( voucherTypeId == null || "".equals(voucherTypeId)){
                    	voucherTypeId = UserContextUtil.getUserContext().getOrgCode()+"_"+UserContextUtil.getUserContext().getCopyCode()+"_"+voucherTypeCode;
                    	voucherType = new VoucherType();
                    	voucherType.setOrgCode(UserContextUtil.getUserContext().getOrgCode());
                        voucherType.setCopyCode(UserContextUtil.getUserContext().getCopyCode());
                    }else{
                    	voucherType = voucherTypeManager.get(voucherTypeId);
                    	voucherTypeCode = voucherType.getVoucherTypeCode();
                    }
                    voucherType.setVoucherTypeId(voucherTypeId == "" ? null : voucherTypeId );
                    voucherType.setVouchertype(vouchertype== null ? "" : vouchertype );
                    voucherType.setVoucherTypeCode(voucherTypeCode == null ? "" : voucherTypeCode );
                    voucherType.setVoucherTypeShort(voucherTypeShort==null?"":voucherTypeShort);
                    voucherType.setIsUsed("Yes".equalsIgnoreCase(isUsed) ? true:false);
                }
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) && voucherType == null ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                voucherTypeManager.save( voucherType );
                String key = ( oper.equals( "add" ) ) ? "voucherType.added" : "voucherType.updated";
                gridOperationMessage = this.getText( key );
            }
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkVoucherTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (voucherType == null) {
			return "Invalid voucherType Data";
		}

		return SUCCESS;

	}
}

