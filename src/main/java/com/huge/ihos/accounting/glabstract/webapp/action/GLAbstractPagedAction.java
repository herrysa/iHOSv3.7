package com.huge.ihos.accounting.glabstract.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.accounting.glabstract.model.GLAbstract;
import com.huge.ihos.accounting.glabstract.service.GLAbstractManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class GLAbstractPagedAction extends JqGridBaseAction implements Preparable {

	@Autowired
	private GLAbstractManager gLAbstractManager;
	private List<GLAbstract> gLAbstracts;
	private GLAbstract gLAbstract;
	private String abstractId;
	private String voucher_abstract;
	private String cnCode;
	private String disabled;


	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String gLAbstractGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//SystemVariable systemVariable = this.getCurrentSystemVariable();
			filters.add(new PropertyFilter("EQS_orgCode", UserContextUtil.getUserContext().getOrgCode()));
			filters.add(new PropertyFilter("EQS_copycode", UserContextUtil.getUserContext().getCopyCode()));
			filters.add(new PropertyFilter("EQS_kjYear", UserContextUtil.getUserContext().getPeriodYear()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = gLAbstractManager
					.getGLAbstractCriteria(pagedRequests,filters);
			this.gLAbstracts = (List<GLAbstract>) pagedRequests.getList();
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
			gLAbstractManager.save(gLAbstract);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "gLAbstract.added" : "gLAbstract.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
    	//SystemVariable systemVariable = this.getCurrentSystemVariable();
        if (abstractId != null) {
        	gLAbstract = gLAbstractManager.get(abstractId);
        	this.setEntityIsNew(false);
        } else {
        	gLAbstract = new GLAbstract();
        	gLAbstract.setOrgCode(UserContextUtil.getUserContext().getOrgCode());
        	gLAbstract.setCopycode(UserContextUtil.getUserContext().getCopyCode());
        	gLAbstract.setKjYear(UserContextUtil.getUserContext().getPeriodYear());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String gLAbstractGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					GLAbstract gLAbstract = gLAbstractManager.get(new String(removeId));
					gLAbstractManager.remove(new String(removeId));
				}
				gridOperationMessage = this.getText("gLAbstract.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("add")|| oper.equals("edit")){
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGLAbstractGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gLAbstract == null) {
			return "Invalid gLAbstract Data";
		} else if(StringUtils.isEmpty(gLAbstract.getVoucher_abstract())){
			return "凭证摘要不能为空";
		}

		return SUCCESS;

	}
	

	public GLAbstractManager getGLAbstractManager() {
		return gLAbstractManager;
	}

	public void setGLAbstractManager(GLAbstractManager gLAbstractManager) {
		this.gLAbstractManager = gLAbstractManager;
	}

	public List<GLAbstract> getgLAbstracts() {
		return gLAbstracts;
	}

	public void setGLAbstracts(List<GLAbstract> gLAbstracts) {
		this.gLAbstracts = gLAbstracts;
	}

	public GLAbstract getGLAbstract() {
		return gLAbstract;
	}

	public void setGLAbstract(GLAbstract gLAbstract) {
		this.gLAbstract = gLAbstract;
	}

	public String getAbstractId() {
		return abstractId;
	}

	public void setAbstractId(String abstractId) {
        this.abstractId = abstractId;
    }
	public GLAbstractManager getgLAbstractManager() {
		return gLAbstractManager;
	}
	public void setgLAbstractManager(GLAbstractManager gLAbstractManager) {
		this.gLAbstractManager = gLAbstractManager;
	}
	public GLAbstract getgLAbstract() {
		return gLAbstract;
	}
	public void setgLAbstract(GLAbstract gLAbstract) {
		this.gLAbstract = gLAbstract;
	}
	public void setgLAbstracts(List<GLAbstract> gLAbstracts) {
		this.gLAbstracts = gLAbstracts;
	}
	public String getVoucher_abstract() {
		return voucher_abstract;
	}
	public void setVoucher_abstract(String voucher_abstract) {
		this.voucher_abstract = voucher_abstract;
	}
	public String getCnCode() {
		return cnCode;
	}
	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
}

