package com.huge.ihos.system.configuration.dictionary.service;

import com.huge.exceptions.DuplicateRecordException;
import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DictionaryManager
    extends GenericManager<Dictionary, Long> {
    public JQueryPager getDictionaryCriteria( final JQueryPager paginatedList );

    public Object saveDictionary( Dictionary o )
        throws DuplicateRecordException;
}