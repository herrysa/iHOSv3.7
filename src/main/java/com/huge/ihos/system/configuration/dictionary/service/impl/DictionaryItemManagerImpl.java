package com.huge.ihos.system.configuration.dictionary.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.dictionary.dao.DictionaryItemDao;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "dictionaryItemManager" )
public class DictionaryItemManagerImpl
    extends GenericManagerImpl<DictionaryItem, Long>
    implements DictionaryItemManager {
    DictionaryItemDao dictionaryItemDao;

    @Autowired
    public DictionaryItemManagerImpl( DictionaryItemDao dictionaryItemDao ) {
        super( dictionaryItemDao );
        this.dictionaryItemDao = dictionaryItemDao;
    }

    public JQueryPager getDictionaryItemCriteria( JQueryPager paginatedList, Long parentId ) {

        return this.dictionaryItemDao.getDictionaryItemCriteria( paginatedList, parentId );
    }

    public List getAllItemsByCode( String code ) {
        return this.dictionaryItemDao.getAllItemsByCode( code );
    }

    public void saveItem( DictionaryItem Item ) {
        super.save( Item );

    }

    public DictionaryItem getItem( Long id ) {
        return super.get( id );
    }

    public void removeItem( Long id ) {
        super.remove( id );

    }
    @Override
    public List<String> getAllItemValuesByCode( String code ){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_dictionary.code",code));
    	filters.add(new PropertyFilter("OAS_orderNo",""));
    	List<DictionaryItem> items = dictionaryItemDao.getByFilters(filters);
    	List<String> valueList = new ArrayList<String>();
    	if(OtherUtil.measureNotNull(items)&&!items.isEmpty()){
    		for(DictionaryItem item:items){
    			valueList.add(item.getValue());
    		}
    	}
    	return valueList;
    }
}
