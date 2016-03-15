package com.huge.ihos.system.datacollection.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "dataCollectionTaskStepDao" )
public class DataCollectionTaskStepDaoHibernate
    extends GenericDaoHibernate<DataCollectionTaskStep, Long>
    implements DataCollectionTaskStepDao {

    public DataCollectionTaskStepDaoHibernate() {
        super( DataCollectionTaskStep.class );
    }

    public JQueryPager getDataCollectionTaskStepCriteria( JQueryPager paginatedList, Long parentId ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("stepId");
                paginatedList.setSortCriterion( null );

            HibernateCallback callback = new DataCollectionTaskStepByParentCallBack( paginatedList, DataCollectionTaskStep.class, parentId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataCollectionTaskStep.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataCollectionTaskStepCriteria", e );
            return paginatedList;
        }
    }

    class DataCollectionTaskStepByParentCallBack
        extends JqueryPagerHibernateCallBack {
        Long parentId;

        DataCollectionTaskStepByParentCallBack( final JQueryPager paginatedList, final Class object, Long parentId ) {
            super( paginatedList, object );
            this.parentId = parentId;
        }

        public Criteria getCustomCriterion( Criteria cr ) {
            //			
            //			Criterion[] cs = new Criterion[1];
            //			cs[0] = Restrictions.eq("dataCollectionTaskDefine.dataCollectionTaskDefineId", parentId);
            cr.add( Restrictions.eq( "dataCollectionTaskDefine.dataCollectionTaskDefineId", parentId ) );

            return cr;
        }
    }

    public List getAllByDefineId( Long defineId ) {
        String hql = "from DataCollectionTaskStep where dataCollectionTaskDefine.dataCollectionTaskDefineId=? order by execOrder";
        return this.getHibernateTemplate().find( hql, defineId );
    }

}
