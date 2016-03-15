package com.huge.ihos.system.configuration.dictionary.dao;

import com.huge.dao.GenericDao;
import com.huge.exceptions.DuplicateRecordException;
import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the Dictionary table.
 */
public interface DictionaryDao
    extends GenericDao<Dictionary, Long> {
    public JQueryPager getDictionaryCriteria( final JQueryPager paginatedList );

    public Object saveDictionary( Dictionary o )
        throws DuplicateRecordException;

}