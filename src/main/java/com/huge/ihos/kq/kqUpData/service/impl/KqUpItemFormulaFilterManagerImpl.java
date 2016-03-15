package com.huge.ihos.kq.kqUpData.service.impl;

import java.util.List;
import com.huge.ihos.kq.kqUpData.dao.KqUpItemFormulaFilterDao;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormulaFilter;
import com.huge.ihos.kq.kqUpData.service.KqUpItemFormulaFilterManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqUpItemFormulaFilterManager")
public class KqUpItemFormulaFilterManagerImpl extends GenericManagerImpl<KqUpItemFormulaFilter, String> implements KqUpItemFormulaFilterManager {
    private KqUpItemFormulaFilterDao kqUpItemFormulaFilterDao;

    @Autowired
    public KqUpItemFormulaFilterManagerImpl(KqUpItemFormulaFilterDao kqUpItemFormulaFilterDao) {
        super(kqUpItemFormulaFilterDao);
        this.kqUpItemFormulaFilterDao = kqUpItemFormulaFilterDao;
    }
    
    public JQueryPager getKqUpItemFormulaFilterCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqUpItemFormulaFilterDao.getKqUpItemFormulaFilterCriteria(paginatedList,filters);
	}
}