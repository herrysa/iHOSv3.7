package com.huge.ihos.accounting.glabstract.service.impl;

import java.util.List;
import com.huge.ihos.accounting.glabstract.dao.GLAbstractDao;
import com.huge.ihos.accounting.glabstract.model.GLAbstract;
import com.huge.ihos.accounting.glabstract.service.GLAbstractManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gLAbstractManager")
public class GLAbstractManagerImpl extends GenericManagerImpl<GLAbstract, String> implements GLAbstractManager {
    private GLAbstractDao gLAbstractDao;

    @Autowired
    public GLAbstractManagerImpl(GLAbstractDao gLAbstractDao) {
        super(gLAbstractDao);
        this.gLAbstractDao = gLAbstractDao;
    }
    
    public JQueryPager getGLAbstractCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gLAbstractDao.getGLAbstractCriteria(paginatedList,filters);
	}
    
    public GLAbstract save(GLAbstract glabstract){
    	String cnCode =  gLAbstractDao.getPyCodes(glabstract.getVoucher_abstract());
    	glabstract.setCnCode(cnCode);
    	super.save(glabstract);
    	return glabstract;
    }
}