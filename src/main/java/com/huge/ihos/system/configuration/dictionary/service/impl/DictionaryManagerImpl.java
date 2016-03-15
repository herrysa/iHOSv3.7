package com.huge.ihos.system.configuration.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.huge.exceptions.DuplicateRecordException;
import com.huge.ihos.system.configuration.dictionary.dao.DictionaryDao;
import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "dictionaryManager" )
public class DictionaryManagerImpl
    extends GenericManagerImpl<Dictionary, Long>
    implements DictionaryManager {
    DictionaryDao dictionaryDao;

    @Autowired
    public DictionaryManagerImpl( DictionaryDao dictionaryDao ) {
        super( dictionaryDao );
        this.dictionaryDao = dictionaryDao;
    }

    public JQueryPager getDictionaryCriteria( JQueryPager paginatedList ) {
        return dictionaryDao.getDictionaryCriteria( paginatedList );
    }

    public Object saveDictionary( Dictionary o )
        throws DuplicateRecordException {
        try {
            dictionaryDao.saveDictionary( o );
            return o;
        }
        catch ( DataIntegrityViolationException e ) {
            log.warn( e.getMessage() );
            throw new DuplicateRecordException( "Person '" + o.getCode() + " or " + o.getName() + "' already exists!" );
        }
        catch ( JpaSystemException e ) { // needed for JPA
            log.warn( e.getMessage() );
            throw new DuplicateRecordException( "Person '" + o.getCode() + " or " + o.getName() + "' already exists!" );
        }
    }

}