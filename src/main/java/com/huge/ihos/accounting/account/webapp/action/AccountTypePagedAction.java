package com.huge.ihos.accounting.account.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.account.model.AccountType;
import com.huge.ihos.accounting.account.service.AccountTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class AccountTypePagedAction extends JqGridBaseAction implements Preparable {

	private AccountTypeManager accountTypeManager;
	private List<AccountType> accountTypes;
	private AccountType accountType;
	private String accountTypeId;
	
	private String orgCode;
	private String copyCode;
	private String accounttype;
	private String accouttypecode;

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public void setAccouttypecode(String accouttypecode) {
		this.accouttypecode = accouttypecode;
	}

	public String getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public AccountTypeManager getAccountTypeManager() {
		return accountTypeManager;
	}

	public void setAccountTypeManager(AccountTypeManager accountTypeManager) {
		this.accountTypeManager = accountTypeManager;
	}

	public List<AccountType> getaccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(List<AccountType> accountTypes) {
		this.accountTypes = accountTypes;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}


	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String accountTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = accountTypeManager
					.getAccountTypeCriteria(pagedRequests,filters);
			this.accountTypes = (List<AccountType>) pagedRequests.getList();
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
			accountTypeManager.save(accountType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "accountType.added" : "accountType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (accountTypeId != null) {
    		//orgCopyAtCodePk = new OrgCopyAtCodePk();
    		//orgCopyAtCodePk.buildPk(accountTypeId);
        	accountType = accountTypeManager.get(accountTypeId);
        	this.setEntityIsNew(false);
        } else {
        	accountType = new AccountType();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String accountTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//orgCopyAtCodePk = new OrgCopyAtCodePk(removeId.split("PKFENGE")[0],removeId.split("PKFENGE")[1],removeId.split("PKFENGE")[2]);
					//AccountType accountType = accountTypeManager.get(orgCopyAtCodePk);
					accountTypeManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("accountType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
				
			if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
//            	if ( (accountTypeManager.existCode( "t_accountType", "acctcode", acctcode, oper )&&oper.equals( "add" ))||(OtherUtil.measureNotNull(acctMapId)&&acctcostmapManager.existCodeEdit(acctMapId, "t_acctcostmap", "acctMapId", "acctcode", acctcode)) ) {
//                    return ajaxForward( false, "您输入的编号已存在,请换一个!", false );
//                }
                if ( accountType == null ) {
                    if ( accountTypeId == null )
                    	accountTypeId = orgCode+"_"+copyCode+"_"+accouttypecode;
                    accountType = new AccountType();
                    accountType.setAccttypeId( accountTypeId == "" ? null : accountTypeId );
                    accountType.setOrgCode( orgCode == null ? "" : orgCode );
                    accountType.setCopyCode( copyCode == null ? "" : copyCode );
                    accountType.setAccouttypecode( accouttypecode == null ? "" : accouttypecode );
                    accountType.setAccounttype( accounttype == null ? "" : accounttype );
                }
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) && accountType == null ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                accountTypeManager.save( accountType );
                String key = ( oper.equals( "add" ) ) ? "accountType.added" : "accountType.updated";
                gridOperationMessage = this.getText( key );
            }
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkAccountTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (accountType == null) {
			return "Invalid accountType Data";
		}

		return SUCCESS;

	}
}

