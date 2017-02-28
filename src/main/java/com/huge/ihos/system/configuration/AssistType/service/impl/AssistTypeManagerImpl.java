package com.huge.ihos.system.configuration.AssistType.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.AssistType.dao.AssistTypeDao;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.ihos.system.configuration.AssistType.service.AssistTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("assistTypeManager")
public class AssistTypeManagerImpl extends GenericManagerImpl<AssistType, String> implements AssistTypeManager {
    private AssistTypeDao assistTypeDao;
    @Autowired
    public AssistTypeManagerImpl(AssistTypeDao assistTypeDao) {
        super(assistTypeDao);
        this.assistTypeDao = assistTypeDao;
    }
    
    public JQueryPager getAssistTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return assistTypeDao.getAssistTypeCriteria(paginatedList,filters);
	}
    
    
    public List<HashMap<String ,String >> getAssits(String table, String assitMark, String id_name, String id_value, List<AssistType> assitTypes){
    	return assistTypeDao.getAssits(table, assitMark, id_name, id_value, assitTypes);
    }
    
    
}