package com.huge.ihos.accounting.account.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.accounting.account.dao.AccountDao;
import com.huge.ihos.accounting.account.dao.AccountTypeDao;
import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.account.model.AccountType;
import com.huge.ihos.accounting.account.service.AccountManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("accountManager")
public class AccountManagerImpl extends GenericManagerImpl<Account, String> implements AccountManager {
    private AccountDao accountDao;
    @Autowired
    private AccountTypeDao accountTypeDao;

    @Autowired
    public AccountManagerImpl(AccountDao accountDao) {
        super(accountDao);
        this.accountDao = accountDao;
    }
    
    public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public AccountTypeDao getAccountTypeDao() {
		return accountTypeDao;
	}

	public void setAccountTypeDao(AccountTypeDao accountTypeDao) {
		this.accountTypeDao = accountTypeDao;
	}

	public JQueryPager getAccountCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return accountDao.getAccountCriteria(paginatedList,filters);
	}

	@Override
	public Account getAccountByNumber(int number,String selectId) {
		return accountDao.getAccountByNumber(number,selectId);
	}

	@Override
	public void updateAccountDisabled(String acctId,String lossDirection) {
		this.accountDao.updateAccountDisabled(acctId,lossDirection);
	}

	@Override
	public int hasChildren(String parentId) {
		return this.accountDao.hasChildren(parentId);
	}

	@Override
	public Account save(Account account, String codeRule) {
		account.setCnCode(accountDao.getPyCodes( account.getAcctname()));
		String acctFullName = account.getAcctCode()+"[";
		String[] strs = codeRule.split("-");
		int len = account.getAcctId().length()-account.getAcctCode().length();
		for(int i=Integer.parseInt(strs[0])+len,j=1;i<account.getAcctCode().length()+len;i+=Integer.parseInt(strs[j]),j++){
			acctFullName += (String)accountDao.get(account.getAcctId().substring(0,i)).getAcctname()+"-";
		}
		acctFullName += account.getAcctname()+"]";
		account.setAcctFullname(acctFullName);
		super.save( account );
		return account;
	}

	@Override
	public List<Account> getAll(HashMap<String, String> environment) {
		return accountDao.getAll(environment);
	}

	@Override
	public List<Account> getAllAccountByOCK(String orgCode, String copyCode,
			String kjYear) {
		return accountDao.getAllAccountByOCK(orgCode,copyCode,kjYear);
	}

	@Override
	public void initAccount(HashMap<String, String> environment) {
		HashMap<String,String> systemEnvironment = new HashMap<String,String>();
		systemEnvironment.put("orgCode", "001001");
		systemEnvironment.put("kjYear", "2012");
		systemEnvironment.put("copyCode", "001");
		List<AccountType> acctTypeList = accountTypeDao.getAll(environment);
		if(acctTypeList.size() == 0 ){
			List<AccountType> acctTypeTempList = accountTypeDao.getAll(systemEnvironment);
			for(AccountType acctTypeTemp:acctTypeTempList ){
				AccountType acctType = new AccountType();
				acctType.setAccounttype(acctTypeTemp.getAccounttype());
				acctType.setAccouttypecode(acctTypeTemp.getAccouttypecode());
				acctType.setCopyCode(environment.get("copyCode"));
				acctType.setOrgCode(environment.get("orgCode"));
				acctType.setAccttypeId(environment.get("copyCode")+"_"+environment.get("orgCode")+"_"+acctTypeTemp.getAccouttypecode());
				acctTypeList.add(acctType);
				accountTypeDao.save(acctType);
			}
		}
		List<Account> acctTempList = accountDao.getAll(systemEnvironment);
		for(Account acctTemp: acctTempList){
			Account acct = new Account();
			acct.setAcctFullname(acctTemp.getAcctFullname());
			acct.setAcctCode(acctTemp.getAcctCode());
			acct.setAcctname(acctTemp.getAcctname());
			acct.setCnCode(acctTemp.getCnCode());
			acct.setAcctNature(acctTemp.getAcctNature());
			acct.setAssistTypes(acctTemp.getAssistTypes());
			acct.setLeaf(acctTemp.getLeaf());
			acct.setMxtypecode(acctTemp.getMxtypecode());
			acct.setDirection(acctTemp.getDirection());
			acct.setBuild_id(acctTemp.getBuild_id());
			acct.setBuild_date(acctTemp.getBuild_date());
			acct.setModi_id(acctTemp.getModi_id());
			acct.setModi_date(acctTemp.getModi_date());
			acct.setCash(acctTemp.isCash());
			acct.setLossDirection(acctTemp.getLossDirection());
			acct.setIsProfitLoss(acctTemp.isIsProfitLoss());
			acct.setIsUsed(acctTemp.getIsUsed());
			acct.setDisabled(acctTemp.getDisabled());
			acct.setCopyCode(environment.get("copyCode"));
			acct.setOrgCode(environment.get("orgCode"));
			acct.setKjYear(environment.get("kjYear"));
			acct.setAcctId(environment.get("copyCode")+"_"+environment.get("orgCode")+"_"+environment.get("kjYear")+"_"+acctTemp.getAcctCode());
				
			String acctTempTypeCode = acctTemp.getAccttype().getAccouttypecode();
			for(AccountType acctType: acctTypeList){
				String typeCode = acctType.getAccouttypecode();
				if(typeCode.equals(acctTempTypeCode)){
					acct.setAccttype(acctType);
					break;
				}
			}
			accountDao.save(acct);
		}
	}
}