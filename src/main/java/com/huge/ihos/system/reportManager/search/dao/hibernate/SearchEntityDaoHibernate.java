package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.search.dao.SearchEntityDao;
import com.huge.ihos.system.reportManager.search.model.SearchEntity;

@Repository( "searchEntityDao" )
public class SearchEntityDaoHibernate
    extends GenericDaoHibernate<SearchEntity, Long>
    implements SearchEntityDao {

    public SearchEntityDaoHibernate() {
        super( SearchEntity.class );
    }

    @Override
    public SearchEntity getSearchEntityByName( String name ) {
        List entities = getHibernateTemplate().find( "from SearchEntity where entityName=?", name );
        if ( entities == null || entities.isEmpty() ) {
            throw new UsernameNotFoundException( "SearchEntity '" + name + "' not found..." );
        }
        else {
            return (SearchEntity) entities.get( 0 );
        }
    }

}
