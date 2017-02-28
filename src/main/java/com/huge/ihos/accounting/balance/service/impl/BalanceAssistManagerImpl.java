package com.huge.ihos.accounting.balance.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.account.service.AccountManager;
import com.huge.ihos.accounting.balance.dao.BalanceAssistDao;
import com.huge.ihos.accounting.balance.dao.BalanceDao;
import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.ihos.accounting.balance.model.BalanceAssist;
import com.huge.ihos.accounting.balance.service.BalanceAssistManager;
import com.huge.ihos.accounting.balance.service.BalanceManager;
import com.huge.ihos.system.configuration.AssistType.dao.AssistTypeDao;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.ihos.system.configuration.AssistType.service.AssistTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("balanceAssistManager")
public class BalanceAssistManagerImpl extends GenericManagerImpl<BalanceAssist, String> implements BalanceAssistManager {

    private BalanceAssistDao balanceAssistDao;
	@Autowired
    private BalanceDao balanceDao;
	@Autowired
    private BalanceManager balanceManager;
	//@Autowired
	//private AssistTypeManager assistTypeManager;
	@Autowired
	private AssistTypeDao assistTypeDao;
	@Autowired
	private AccountManager accountManager;
    
	@Autowired
    public BalanceAssistManagerImpl(BalanceAssistDao balanceAssistDao) {
        super(balanceAssistDao);
        this.balanceAssistDao = balanceAssistDao;
    }
	

	@Override
	public List<HashMap<String,Object>> getAssistByBalance(String balanceId) {
		List<HashMap<String,Object>> balanceAssistList = balanceAssistDao.getAssistGroup(balanceId);
		List<HashMap<String,Object>> assistList = balanceAssistDao.getAssistMapByBalanceId(balanceId);
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		for(HashMap<String,Object> map:balanceAssistList){
			Integer num = (Integer)map.get("num");
			String beginJie = map.get("beginJie").toString();
			String beginDai = map.get("beginDai").toString();
			String yearBalance = map.get("yearBalance").toString();
			String initBalance = map.get("initBalance").toString();
			
			HashMap<String,Object> assist = new HashMap<String,Object>();
			for(HashMap<String,Object> mapp: assistList){
				Integer rownum = (Integer)mapp.get("num");
				if(rownum==num){
					mapp.put("assist_typeCode_"+mapp.get("assistCode"), mapp.get("assistCode"));
					mapp.put("assist_typeName_"+mapp.get("assistCode"), mapp.get("assistName"));
					mapp.put("assist_typeValue_"+mapp.get("assistCode"), mapp.get("assistValue"));
					mapp.put("assist_assId_"+mapp.get("assistCode"), mapp.get("balanceAssistId"));
					assist.putAll(mapp);
				}
			}
			map.put("assist", assist);
			resultList.add(map);
		}
		return resultList;
	}
	
	@Override
	public Boolean saveBalance(String balanceId,String status) {
		try{
			Double oldBeginJie = 0.0d;
			Double oldInitBalance = 0.0d;
			Double oldYearBalance = 0.0d;
			Double oldBeginDai = 0.0d;
			Balance balance = balanceDao.get(balanceId);
			if(!"del".equals(status)){
				oldBeginJie = balance.getBeginJie();
				oldInitBalance = balance.getInitBalance();
				oldYearBalance = balance.getYearBalance();
				oldBeginDai = balance.getBeginDai();
			}
			List<HashMap<String,Object>> saveList = balanceAssistDao.getAssistSumByBalance(balanceId);
			Map<String, Double> diffMap = new HashMap<String,Double>();
			for(HashMap<String,Object> map:saveList){
				String beginJie = map.get("beginJie")==null?"0":map.get("beginJie").toString();
				String beginDai = map.get("beginDai")==null?"0":map.get("beginDai").toString();
				String yearBalance = map.get("yearBalance")==null?"0":map.get("yearBalance").toString();
				String initBalance = map.get("initBalance")==null?"0":map.get("initBalance").toString();
				diffMap.put("diffJie", Double.parseDouble(beginJie)-oldBeginJie);
				diffMap.put("diffInitBalance", Double.parseDouble(initBalance)-oldInitBalance);
				diffMap.put("diffYearBalance",Double.parseDouble(yearBalance)-oldYearBalance);
				diffMap.put("diffDai", Double.parseDouble(beginDai)-oldBeginDai);
			}
			balanceManager.editBalance(balance, diffMap);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean removeAssist(String balanceId) {
		try{
			//saveBalance(balanceId,"del");
			Map<String, Double> diffMap = new HashMap<String,Double>();
			Double diffDai =  0.0d;
			Double diffJie =  0.0d;
			Double diffYearBalance = 0.0d;
			Double diffInitBalance = 0.0d;
			Balance delBalance = balanceManager.get(balanceId);
			diffYearBalance = 0 - delBalance.getYearBalance();
			diffInitBalance = 0 - delBalance.getInitBalance();
			diffDai = 0 - delBalance.getBeginDai();
			diffJie = 0 - delBalance.getBeginJie();
			diffMap.put("diffInitBalance", diffInitBalance);
			diffMap.put("diffYearBalance", diffYearBalance);
			diffMap.put("diffJie", diffJie);
			diffMap.put("diffDai", diffDai);
			balanceManager.editBalance(delBalance,diffMap);
			balanceAssistDao.removeAssist(balanceId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean saveBalanceAssist(String assistBalanceJson,String balanceId) {
		try {
			Balance oldBalance = balanceManager.get(balanceId);
			balanceAssistDao.removeAssist(balanceId);
			
			JSONObject jsonObject = JSONObject.fromObject(assistBalanceJson);
			Iterator<String> it = jsonObject.keySet().iterator();
			List<AssistType> assistTypeList = accountManager.getAssitTypesByAcct(oldBalance.getAccount().getAcctId());
			while(it.hasNext()){
				String assistStr = (String) it.next();
				JSONObject json = jsonObject.getJSONObject(assistStr);
				String beginJie = json.getString("beginJie");
				String beginDai = json.getString("beginDai");
				String initBalance = json.getString("initBalance");
				String yearBalance = json.getString("yearBalance");
				String num = json.getString("num");
				for(AssistType assit: assistTypeList){
					String typeCode = assit.getTypeCode();
					String typeValue = json.getString("assist_typeValue_"+typeCode);
					String typeName = json.getString("assist_typeName_"+typeCode);
					BalanceAssist balAss = new BalanceAssist();
					balAss.setAssistType(assit);
					balAss.setAssistValue(typeValue);
					balAss.setBeginDai(Double.parseDouble(beginDai));
					balAss.setBeginJie(Double.parseDouble(beginJie));
					balAss.setInitBalance(Double.parseDouble(initBalance));
					balAss.setYearBalance(Double.parseDouble(yearBalance));
					balAss.setNum(Integer.parseInt(num));
					balAss.setAssistName(typeName);
					balAss.setBalance(oldBalance);
					this.save(balAss);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public List<HashMap<String, String>> getAssistTypes(Balance balance) {
		Account account = balance.getAccount();
		String assistTypes = account.getAssistTypes();
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		if(assistTypes != null &&  !"".equals(assistTypes)){
			String[] assistArrays = assistTypes.split(",");
			for(String asstId : assistArrays){
				asstId = asstId.trim();
				if(StringUtils.isNotBlank(asstId)){
					AssistType assist = assistTypeDao.get(asstId);
					List<HashMap<String,String>> listTemp = balanceAssistDao.getAssistTypes(balance.getBalanceId(), assist.getTypeCode());
					StringBuffer assistName = new StringBuffer("");
					StringBuffer assistValue = new StringBuffer("");
					for(HashMap<String,String> tempMap : listTemp){
						assistName.append(tempMap.get("assistName"));
						assistName.append(",");
						assistValue.append(tempMap.get("assistValue"));
						assistValue.append(",");
					}
					if(!"".equals(assistName.toString())){
						assistName.deleteCharAt(assistName.length()-1);
						assistValue.deleteCharAt(assistValue.length()-1);
					}
					HashMap<String, String> resultMap = new HashMap<String, String>();
					resultMap.put("typeCode",assist.getTypeCode());
					resultMap.put("typeName",assist.getTypeName());
					resultMap.put("assistName",assistName.toString());
					resultMap.put("assistValue",assistValue.toString());
					resultList.add(resultMap);
				}
			}
		}
		return resultList;
	}
	
	
	/*************************************get//set***************************************************/

	public BalanceManager getBalanceManager() {
		return balanceManager;
	}

	public void setBalanceManager(BalanceManager balanceManager) {
		this.balanceManager = balanceManager;
	}

	public BalanceDao getBalanceDao() {
		return balanceDao;
	}

	public void setBalanceDao(BalanceDao balanceDao) {
		this.balanceDao = balanceDao;
	}
    
    public JQueryPager getBalanceAssistCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return balanceAssistDao.getBalanceAssistCriteria(paginatedList,filters);
	}
    

	public BalanceAssistDao getBalanceAssistDao() {
		return balanceAssistDao;
	}

	public void setBalanceAssistDao(BalanceAssistDao balanceAssistDao) {
		this.balanceAssistDao = balanceAssistDao;
	}

	public AssistTypeDao getAssistTypeDao() {
		return assistTypeDao;
	}
	public void setAssistTypeDao(AssistTypeDao assistTypeDao) {
		this.assistTypeDao = assistTypeDao;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}


}