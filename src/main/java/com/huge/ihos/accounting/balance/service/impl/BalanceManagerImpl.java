package com.huge.ihos.accounting.balance.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.accounting.account.dao.AccountDao;
import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.balance.dao.BalanceAssistDao;
import com.huge.ihos.accounting.balance.dao.BalanceDao;
import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.ihos.accounting.balance.model.BalanceAssist;
import com.huge.ihos.accounting.balance.model.BalancePeriod;
import com.huge.ihos.accounting.balance.service.BalanceManager;
import com.huge.ihos.system.configuration.AssistType.dao.AssistTypeDao;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.ihos.system.systemManager.copy.dao.CopyDao;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.ihos.system.systemManager.period.dao.PeriodMonthDao;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("balanceManager")
public class BalanceManagerImpl extends GenericManagerImpl<Balance, String> implements BalanceManager {
    private BalanceDao balanceDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AssistTypeDao assistTypeDao;
    @Autowired
    private BalanceAssistDao balanceAssistDao;
    @Autowired
    private CopyDao copyDao;
    @Autowired
    private PeriodMonthDao periodMonthDao;

    @Autowired
    public BalanceManagerImpl(BalanceDao balanceDao) {
        super(balanceDao);
        this.balanceDao = balanceDao;
    }

	public JQueryPager getBalanceCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return balanceDao.getBalanceCriteria(paginatedList,filters);
	}
    
    public JQueryPager checkBalance(JQueryPager paginatedList, List<PropertyFilter> filters){
    	filters.add(new PropertyFilter("EQB_account.leaf", "true"));
    	filters.add(new PropertyFilter("ISNOTNULLS_account.assistTypes",""));
    	filters.add(new PropertyFilter("NES_account.assistTypes",","));
    	List<Balance> balanceList = balanceDao.getBalanceCriteria(paginatedList, filters).getList();
    	paginatedList.setList(getAssitListUtil(balanceList,"3"));
    	return paginatedList;
    }
    
    
    @Override
	public JQueryPager checkBalance(JQueryPager paginatedList, List<PropertyFilter> filters, String assitBalance) {
    	filters.add(new PropertyFilter("EQB_account.leaf", "true"));
    	filters.add(new PropertyFilter("ISNOTNULLS_account.assistTypes",""));
    	filters.add(new PropertyFilter("NES_account.assistTypes",","));
    	List<Balance> disAssitList = balanceDao.getBalanceCriteriaWithCount(paginatedList, filters);
    	List<Balance> balanceList = getAssitListUtil(disAssitList,assitBalance);
		paginatedList.setList(balanceList);
		List<Balance> pageList = new ArrayList<Balance>();
		if(balanceList !=null&&balanceList.size()>0){
			int fromNum = (paginatedList.getPageNumber()-1)*paginatedList.getPageSize();
			int toNum = paginatedList.getPageNumber()*paginatedList.getPageSize();
			if(toNum >= balanceList.size()){
				toNum = balanceList.size();
			}
			pageList = balanceList.subList(fromNum, toNum);
		}
		paginatedList.setTotalNumberOfRows(balanceList.size());
		paginatedList.setList(pageList);
		return paginatedList;
	}
    
    private List<Balance> getAssitListUtil(List<Balance> balanceList, String flag){
    	
    	Iterator<Balance> balIt = balanceList.iterator();
    	while(balIt.hasNext()){
    		Balance balance = balIt.next();
    	
//    		List<HashMap<String,Object>> balanceAssistList = balanceAssistDao.getAssistGroup(balance.getBalanceId());
//    		HashMap<String,Object> assist = new HashMap<String,Object>();
//    		Double initAssistBalance = 0.0d;
//    		for(HashMap<String,Object> assistMap:balanceAssistList){
//    			String initBalanceTemp = assistMap.get("initBalance").toString();
//    			initAssistBalance+=Double.parseDouble(initBalanceTemp);
//    		}
//    		assist.put("initAssistBalance", initAssistBalance);
//    		if(initAssistBalance == balance.getInitBalance()){
//    			assist.put("isBalance", false);
//    		} else {
//    			assist.put("isBalance", true);
//    		}
    		Iterator<BalanceAssist> balAssIt = balance.getBalanceAssistSet().iterator();
    		HashMap<Integer, Double> balInit = new HashMap<Integer,Double>();
    		
    		while(balAssIt.hasNext()){
    			BalanceAssist balAss = balAssIt.next();
    			balInit.put(balAss.getNum(),balAss.getInitBalance());
    		}
    		Iterator<Integer> it = balInit.keySet().iterator();
    		Double initAssistBalance = 0.0d;
    		HashMap<String,Object> assist = new HashMap<String,Object>();
    		while(it.hasNext()){
    			Double assitInitBal = balInit.get(it.next());
    			initAssistBalance+=assitInitBal;
    		}
    		if(initAssistBalance.equals(balance.getInitBalance())){
    			if("0".equals(flag)){
    				balIt.remove();
    				continue;
    			}
    			assist.put("isBalance", true);
    		} else {
    			if("1".equals(flag)){
    				balIt.remove();
    				continue;
    			}
    			assist.put("isBalance", false);
    		}
    		assist.put("initAssistBalance", initAssistBalance);
    		List<AssistType> assitTypeList = this.getAssistTypeByAcct(balance.getAccount().getAcctId());
    		StringBuffer assitType = new StringBuffer();
    		for(AssistType assit:assitTypeList){
    			String assitTypes =  assit.getTypeName().trim()+",";
    			assitType.append(assitTypes);
    		}
    		String typeStr  = assitType.substring(0, assitType.length()-1);
    		assist.put("assitType", typeStr);
    		balance.setAssistMap(assist);
    	}
    	return balanceList;
    	
    }

	@Override
	public List<Balance> getBalWithAssistByAcct(String acctId) {
		return balanceDao.getBalWithAssistByAcct(acctId);
	}
	
	@Override
	public List<Balance> getBalancesByAcct(String acctId) {
		return balanceDao.getBalancesByAcct(acctId);
	}

	@Override
	public List<AssistType> getAssistTypeByAcct(String acctId) {
		Account account = accountDao.get(acctId);
		String assistTypes = account.getAssistTypes();
		String orgCode = account.getOrgCode().trim();
		String kjYear = account.getKjYear().trim();
		List<AssistType> resultList = new ArrayList<AssistType>();
		if(assistTypes != null &&  !"".equals(assistTypes)){
			String[] assistArrays = assistTypes.split(",");
			for(String str : assistArrays){
				if(""!=null){
					str = str.trim();
				}else{
					continue;
				}
				if(StringUtils.isNotEmpty(str.trim())){
					String asstId = str;
					AssistType assist = assistTypeDao.get(asstId);
					if(assist != null && !assist.getDisabled()){
						resultList.add(assist);
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public Account getAcctById(String acctId) {
		Account account = accountDao.get(acctId);
		return account;
	}

	@Override
	public Boolean editBalance(Balance balance, Map<String, Double> diffMap) {
		try{
			String acctCode = balance.getAcctCode();
			String orgCode = balance.getOrgCode();
			String kjYear = balance.getKjYear();
			String copyCode = balance.getCopyCode();
			
			Double diffYearBalance = diffMap.get("diffYearBalance");
			Double diffInitBalance = diffMap.get("diffInitBalance");
			Double diffBeginJie = diffMap.get("diffJie");
			Double diffBeginDai = diffMap.get("diffDai");
			
			List<Balance> balanceList = balanceDao.getBalancesByAcctCode(acctCode,orgCode, kjYear, copyCode);
 			for(Balance balanceEntity : balanceList){
				balanceEntity.setYearBalance(balanceEntity.getYearBalance()+diffYearBalance);
				balanceEntity.setInitBalance(balanceEntity.getInitBalance()+diffInitBalance);
				balanceEntity.setBeginDai(balanceEntity.getBeginDai()+diffBeginDai);
				balanceEntity.setBeginJie(balanceEntity.getBeginJie()+diffBeginJie);
				balanceDao.save(balanceEntity);
			}
			return true;
		}catch(Exception e){
			log.error(e);
			return false;
		}
	}

	@Override
	public List<HashMap<String, String>> isBalance(HashMap<String, String> environment) {
		return balanceDao.isBalance(environment);
	}

	@Override
	public Boolean init(HashMap<String, String> environment) {
		try{
			List<Account> acctList = accountDao.getAll(environment);
			Copy copy  = copyDao.getCopyByCode(environment.get("copyCode"));
			PeriodPlan plan = copy.getPeriodPlan();
			List<PeriodMonth> periodMonthList = periodMonthDao.getMonthByPlanAndYear(plan.getPlanId(), environment.get("kjYear"));
			for(Account acct : acctList){
				Balance balance = new Balance();
				balance.setAccount(acct);
				balance.setAcctCode(acct.getAcctCode());
				balance.setBalanceType("B");
				balance.setOrgCode(environment.get("orgCode"));
				balance.setCopyCode(environment.get("copyCode"));
				balance.setKjYear(environment.get("kjYear"));
				Set<BalancePeriod> periodSet = new HashSet<BalancePeriod>();
				for(PeriodMonth month : periodMonthList){
					String monthStr = month.getPeriodMonthCode();
					BalancePeriod bp = new BalancePeriod();
					bp.setPeriodMonth(monthStr);
					bp.setBalance(balance);
					bp.setLend(0.0d);
					bp.setLoan(0.0d);
					bp.setMonthBalance(0.0d);
					periodSet.add(bp);
				}
				balance.setBalancePeriodSet(periodSet);
				balanceDao.save(balance);
			}
			return true;
		}catch(Exception e){
			log.error(e);
			return false;
		}
	}

	@Override
	public List<Balance> getAll(HashMap<String, String> environment) {
		return balanceDao.getAll(environment);
	}

	
	/*****************************get/set**********************************/
	 public BalanceDao getBalanceDao() {
			return balanceDao;
		}
		public void setBalanceDao(BalanceDao balanceDao) {
			this.balanceDao = balanceDao;
		}
		public AccountDao getAccountDao() {
			return accountDao;
		}
		public void setAccountDao(AccountDao accountDao) {
			this.accountDao = accountDao;
		}
		public AssistTypeDao getAssistTypeDao() {
			return assistTypeDao;
		}
		public void setAssistTypeDao(AssistTypeDao assistTypeDao) {
			this.assistTypeDao = assistTypeDao;
		}
		public BalanceAssistDao getBalanceAssistDao() {
			return balanceAssistDao;
		}
		public void setBalanceAssistDao(BalanceAssistDao balanceAssistDao) {
			this.balanceAssistDao = balanceAssistDao;
		}

	
}