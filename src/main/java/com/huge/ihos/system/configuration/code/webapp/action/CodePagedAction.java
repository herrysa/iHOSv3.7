package com.huge.ihos.system.configuration.code.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.code.model.Code;
import com.huge.ihos.system.configuration.code.service.CodeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class CodePagedAction extends JqGridBaseAction implements Preparable {

	private CodeManager codeManager;
	private List<Code> codes;
	private Code code;
	private String codeId;
	private String codeRule;
	private String remark;
	private String disabled;
	
	private String enableType;

	public String getEnableType() {
		return enableType;
	}

	public void setEnableType(String enableType) {
		this.enableType = enableType;
	}

	public void setCodeRule(String codeRule) {
		this.codeRule = codeRule;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public void setCodeManager(CodeManager codeManager) {
		this.codeManager = codeManager;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public void setCodes(List<Code> codes) {
		this.codes = codes;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String codeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = codeManager.getCodeCriteria(pagedRequests, filters);
			this.codes = (List<Code>) pagedRequests.getList();
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
			if(code.getOrg().getOrgCode().equals("")){
				code.setOrg(null);
			}
			if(code.getCopy().getCopycode().equals("")){
				code.setCopy(null);
			}
			codeManager.save(code);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "code.added" : "code.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (codeId != null) {
			code = codeManager.get(codeId);
			this.setEntityIsNew(false);
		} else {
			code = new Code();
			this.setEntityIsNew(true);
		}
		codes = codeManager.getAll();
		return SUCCESS;
	}

	public String codeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					Code code = codeManager.get(new String(removeId));
					if(!code.getDisabled()){
						codeManager.remove(new String(removeId));
					}else{
						return ajaxForward(false, "启用状态下不能删除，请停用后删除！", false);
					}
				}
				gridOperationMessage = this.getText("code.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("add") || oper.equals("edit")) {
				if (code == null) {
					code =  codeManager.get(codeId);
					code.setCodeRule(codeRule == null ? "" : codeRule);
					code.setRemark(remark == null ? "" : remark);
					code.setDisabled("Yes".equalsIgnoreCase(disabled) ? true
							: false);
				}
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS) && code == null) {
					gridOperationMessage = error;
					return SUCCESS;
				}
				codeManager.save(code);
				String key = (oper.equals("add")) ? "code.added"
						: "code.updated";
				gridOperationMessage = this.getText(key);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkCodeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (code == null) {
			return "Invalid code Data";
		}

		return SUCCESS;

	}
	
	public String enableOrDisableCode(){
		try {
			code = codeManager.get(codeId);
			List dataList= codeManager.isHaveData(code);
			if(dataList.size()>0){
				return ajaxForward(false,"对应数据表己存在数据不能修改启用状态！",false);
			}else{
				if("enable".equals(enableType)){
					code.setDisabled(false);
					codeManager.save(code);
					return ajaxForward(true,"启用成功！",false);
				}else{
					code.setDisabled(true);
					codeManager.save(code);
					return ajaxForward(true,"停用成功！",false);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"操作失败！",false);
		}
	}
}
