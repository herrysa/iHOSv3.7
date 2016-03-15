package com.huge.ihos.system.configuration.dictionary.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.configuration.dictionary.dao.DictionaryItemDao;
import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "dictionaryItemDao" )
public class DictionaryItemDaoHibernate
    extends GenericDaoHibernate<DictionaryItem, Long>
    implements DictionaryItemDao {

    public DictionaryItemDaoHibernate() {
        super( DictionaryItem.class );
    }

    public JQueryPager getDictionaryItemCriteria( JQueryPager paginatedList, Long parentId ) {
        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( "orderNo" );
            HibernateCallback callback = new DictionaryItemByParentCallBack( paginatedList, DictionaryItem.class, parentId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Dictionary.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDictionaryItemCriteria", e );
            return paginatedList;
        }
    }

    class DictionaryItemByParentCallBack
        extends JqueryPagerHibernateCallBack {
        Long parentId;

        String isUsed = "1";

        DictionaryItemByParentCallBack( final JQueryPager paginatedList, final Class object, Long parentId ) {
            super( paginatedList, object );
            this.parentId = parentId;
        }

        public Criteria getCustomCriterion( Criteria cr ) {
            cr.add( Restrictions.eq( "dictionary.dictionaryId", parentId ) );
            return cr;
        }

    }

    public List getAllItemsByCode( String code ) {
        String hql = "from DictionaryItem dis where dis.dictionary.code = ? order by dis.orderNo";
        List list = this.getHibernateTemplate().find( hql, code );
        return list;
    }
}
