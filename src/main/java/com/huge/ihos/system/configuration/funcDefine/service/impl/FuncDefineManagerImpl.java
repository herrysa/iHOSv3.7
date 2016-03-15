package com.huge.ihos.system.configuration.funcDefine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.funcDefine.dao.FuncDefineDao;
import com.huge.ihos.system.configuration.funcDefine.model.FuncDefine;
import com.huge.ihos.system.configuration.funcDefine.service.FuncDefineManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("funcDefineManager")
public class FuncDefineManagerImpl extends GenericManagerImpl<FuncDefine, String> implements FuncDefineManager {
    private FuncDefineDao funcDefineDao;

    @Autowired
    public FuncDefineManagerImpl(FuncDefineDao funcDefineDao) {
        super(funcDefineDao);
        this.funcDefineDao = funcDefineDao;
    }
    
    public JQueryPager getFuncDefineCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return funcDefineDao.getFuncDefineCriteria(paginatedList,filters);
	}
}