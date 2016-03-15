package com.huge.ihos.system.configuration.dictionary.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.DuplicateRecordException;
import com.huge.ihos.system.configuration.dictionary.dao.DictionaryDao;
import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "dictionaryDao" )
public class DictionaryDaoHibernate
    extends GenericDaoHibernate<Dictionary, Long>
    implements DictionaryDao {

    public DictionaryDaoHibernate() {
        super( Dictionary.class );
    }

    public JQueryPager getDictionaryCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( "code" );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Dictionary.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDictionaryCriteria", e );
            return paginatedList;
        }

    }

    public Object saveDictionary( Dictionary o )
        throws DuplicateRecordException {
        getHibernateTemplate().saveOrUpdate( o );
        // necessary to throw a DataIntegrityViolation and catch it in Manager
        getHibernateTemplate().flush();
        return o;
    }

}
