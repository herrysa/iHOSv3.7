package com.huge.ihos.accounting.AssistType.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.accounting.AssistType.dao.AssistTypeDao;
import com.huge.ihos.accounting.AssistType.model.AssistType;
import com.huge.ihos.accounting.AssistType.service.AssistTypeManager;
import com.huge.ihos.accounting.account.dao.AccountDao;
import com.huge.ihos.accounting.account.model.Account;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("assistTypeManager")
public class AssistTypeManagerImpl extends GenericManagerImpl<AssistType, String> implements AssistTypeManager {
    private AssistTypeDao assistTypeDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    public AssistTypeManagerImpl(AssistTypeDao assistTypeDao) {
        super(assistTypeDao);
        this.assistTypeDao = assistTypeDao;
    }
    
    public JQueryPager getAssistTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return assistTypeDao.getAssistTypeCriteria(paginatedList,filters);
	}
    
    public List<AssistType> getAssitTypesByAcct(String acctId){
    	Account account = accountDao.get(acctId);
		String assistTypes = account.getAssistTypes();
		List<AssistType> resultList = new ArrayList<AssistType>();
		if(assistTypes != null &&  !"".equals(assistTypes)){
			String[] assistArrays = assistTypes.split(",");
			for(String asstId : assistArrays){
				asstId = asstId.trim();
				if(StringUtils.isNotBlank(asstId)){
					AssistType assist = assistTypeDao.get(asstId);
					if(assist != null && !assist.getDisabled()){
						resultList.add(assist);
					}
				}
			}
		}
    	return resultList;
    }
    
    public List<HashMap<String ,String >> getAssits(String table, String assitMark, String id_name, String id_value, List<AssistType> assitTypes){
    	return assistTypeDao.getAssits(table, assitMark, id_name, id_value, assitTypes);
    }
    
    
}