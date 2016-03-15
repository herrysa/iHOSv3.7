package com.huge.ihos.singletest.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.singletest.dao.SingleEntitySampleDao;
import com.huge.ihos.singletest.model.SingleEntitySample;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "singleEntitySampleDao" )
public class SingleEntitySampleDaoHibernate
    extends GenericDaoHibernate<SingleEntitySample, Long>
    implements SingleEntitySampleDao {

    public SingleEntitySampleDaoHibernate() {
        super( SingleEntitySample.class );
    }

    public JQueryPager getSingleEntitySampleCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("pkid");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new SingleEntitySampleByParentCallBack(paginatedList, SingleEntitySample.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, SingleEntitySample.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getSingleEntitySampleCriteria", e );
            return paginatedList;
        }
    }
    /*
    class SingleEntitySampleByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	SingleEntitySampleByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("singleEntitySample.pkid", parentId);
    		return criteria;
    	}
    }
     */
}
