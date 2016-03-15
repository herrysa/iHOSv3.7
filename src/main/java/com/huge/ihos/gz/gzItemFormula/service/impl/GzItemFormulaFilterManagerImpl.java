package com.huge.ihos.gz.gzItemFormula.service.impl;

import java.util.List;
import com.huge.ihos.gz.gzItemFormula.dao.GzItemFormulaFilterDao;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormulaFilter;
import com.huge.ihos.gz.gzItemFormula.service.GzItemFormulaFilterManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzItemFormulaFilterManager")
public class GzItemFormulaFilterManagerImpl extends GenericManagerImpl<GzItemFormulaFilter, String> implements GzItemFormulaFilterManager {
    private GzItemFormulaFilterDao gzItemFormulaFilterDao;

    @Autowired
    public GzItemFormulaFilterManagerImpl(GzItemFormulaFilterDao gzItemFormulaFilterDao) {
        super(gzItemFormulaFilterDao);
        this.gzItemFormulaFilterDao = gzItemFormulaFilterDao;
    }
    
    public JQueryPager getGzItemFormulaFilterCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzItemFormulaFilterDao.getGzItemFormulaFilterCriteria(paginatedList,filters);
	}
}