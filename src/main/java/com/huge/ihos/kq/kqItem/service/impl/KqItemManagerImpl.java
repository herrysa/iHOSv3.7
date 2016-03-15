package com.huge.ihos.kq.kqItem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kq.kqItem.dao.KqItemDao;
import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.ihos.kq.kqItem.service.KqItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqItemManager")
public class KqItemManagerImpl extends GenericManagerImpl<KqItem, String> implements KqItemManager {
    private KqItemDao kqItemDao;

    @Autowired
    public KqItemManagerImpl(KqItemDao kqItemDao) {
        super(kqItemDao);
        this.kqItemDao = kqItemDao;
    }
    
    public JQueryPager getKqItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqItemDao.getKqItemCriteria(paginatedList,filters);
	}
    @Override
    public KqItem getDefaultKqItem(){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQB_isDefault","1"));
		List<KqItem> kqItems = kqItemDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(kqItems)&&!kqItems.isEmpty()){
			return kqItems.get(0);
		}else{
			return null;
		}
    }
    public List<KqItem> getLeafKqItems(){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("NES_parentId","-1"));
		return kqItemDao.getByFilters(filters);
    }
}