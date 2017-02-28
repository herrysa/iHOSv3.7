package com.huge.ihos.system.formDesigner.service.impl;

import java.util.List;
import com.huge.ihos.system.formDesigner.dao.FormDesignerDao;
import com.huge.ihos.system.formDesigner.model.FormDesigner;
import com.huge.ihos.system.formDesigner.service.FormDesignerManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("formDesignerManager")
public class FormDesignerManagerImpl extends GenericManagerImpl<FormDesigner, String> implements FormDesignerManager {
    private FormDesignerDao formDesignerDao;

    @Autowired
    public FormDesignerManagerImpl(FormDesignerDao formDesignerDao) {
        super(formDesignerDao);
        this.formDesignerDao = formDesignerDao;
    }
    
    public JQueryPager getFormDesignerCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return formDesignerDao.getFormDesignerCriteria(paginatedList,filters);
	}
}