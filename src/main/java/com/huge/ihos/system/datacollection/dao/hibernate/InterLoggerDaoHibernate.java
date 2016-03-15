package com.huge.ihos.system.datacollection.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.datacollection.dao.InterLoggerDao;
import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "interLoggerDao" )
public class InterLoggerDaoHibernate
    extends GenericDaoHibernate<InterLogger, Long>
    implements InterLoggerDao {

    public InterLoggerDaoHibernate() {
        super( InterLogger.class );
    }

    public void deleteByTaskInterId( String tid ) {
        String hql = "delete from InterLogger where taskInterId = ?";
        this.hibernateTemplate.bulkUpdate( hql, tid );
    }

    public void deleteByPeriodCode( String periodCode ) {
        String hql = "delete from InterLogger where periodCode = ?";
        this.hibernateTemplate.bulkUpdate( hql, periodCode );
    }

    public JQueryPager getInterLoggerCriteria( JQueryPager paginatedList, String interLogId ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("logId");
                paginatedList.setSortCriterion( null );

            HibernateCallback callback = new InterLoggerByParentCallBack( paginatedList, InterLogger.class, interLogId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, InterLogger.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getInterLoggerCriteria", e );
            return paginatedList;
        }
    }

    public JQueryPager getInterLoggerCriteria( JQueryPager paginatedList, String interLogId, String stepName ) {
        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("logId");
                paginatedList.setSortCriterion( null );

            HibernateCallback callback = new InterLoggerByParentStepNameCallBack( paginatedList, InterLogger.class, interLogId, stepName );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, InterLogger.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getInterLoggerCriteria", e );
            return paginatedList;
        }
    }

    class InterLoggerByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String interLogId;

        InterLoggerByParentCallBack( final JQueryPager paginatedList, final Class object, String interLogId ) {
            super( paginatedList, object );
            this.interLogId = interLogId;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.add( Restrictions.eq( "taskInterId", interLogId ) );
            return criteria;
        }
    }

    class InterLoggerByParentStepNameCallBack
        extends JqueryPagerHibernateCallBack {
        String interLogId;

        String stepName;

        InterLoggerByParentStepNameCallBack( final JQueryPager paginatedList, final Class object, String interLogId, String stepName ) {
            super( paginatedList, object );
            this.interLogId = interLogId;
            this.stepName = stepName;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.add( Restrictions.eq( "taskInterId", interLogId ) );
            criteria.add( Restrictions.eq( "logFrom", this.stepName ) );
            return criteria;
        }
    }
}
