package com.huge.ihos.system.configuration.dictionary.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.webapp.pagers.JQueryPager;

public interface DictionaryItemDao
    extends GenericDao<DictionaryItem, Long> {
    public JQueryPager getDictionaryItemCriteria( final JQueryPager paginatedList, Long parentId );

    public List getAllItemsByCode( String code );
}
