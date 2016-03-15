package com.huge.ihos.system.datacollection.dao.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepExecuteDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStepExecute;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "dataCollectionTaskStepExecuteDao" )
public class DataCollectionTaskStepExecuteDaoHibernate
    extends GenericDaoHibernate<DataCollectionTaskStepExecute, Long>
    implements DataCollectionTaskStepExecuteDao {

    public DataCollectionTaskStepExecuteDaoHibernate() {
        super( DataCollectionTaskStepExecute.class );
    }

    public JQueryPager getDataCollectionTaskStepExecuteCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("stepExecuteId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new DataCollectionTaskStepExecuteByParentCallBack(paginatedList, DataCollectionTaskStepExecute.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataCollectionTaskStepExecute.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataCollectionTaskStepExecuteCriteria", e );
            return paginatedList;
        }
    }

    /*
    class DataCollectionTaskStepExecuteByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	DataCollectionTaskStepExecuteByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("dataCollectionTaskStepExecute.stepExecuteId", parentId);
    		return criteria;
    	}
    }
     */

    public JQueryPager getDataCollectionTaskStepExecuteCriteria( JQueryPager paginatedList, Long taskId ) {
        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("stepId");
                paginatedList.setSortCriterion( null );

            HibernateCallback callback = new DataCollectionTaskStepExecuteByTaskCallBack( paginatedList, DataCollectionTaskStepExecute.class, taskId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataCollectionTaskStepExecute.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataCollectionTaskStepExecuteCriteria", e );
            return paginatedList;
        }
    }

    class DataCollectionTaskStepExecuteByTaskCallBack
        extends JqueryPagerHibernateCallBack {
        Long taskId;

        DataCollectionTaskStepExecuteByTaskCallBack( final JQueryPager paginatedList, final Class object, Long taskId ) {
            super( paginatedList, object );
            this.taskId = taskId;
        }

        public Criteria getCustomCriterion( Criteria cr ) {
            //			
            //			Criterion[] cs = new Criterion[1];
            //			cs[0] = Restrictions.eq("dataCollectionTaskDefine.dataCollectionTaskDefineId", parentId);
            cr.add( Restrictions.eq( "dataCollectionTask.dataCollectionTaskId", taskId ) );

            return cr;
        }
    }

    public void clearStepExecuteByTask( DataCollectionTask task ) {
        String hql = "delete from DataCollectionTaskStepExecute where dataCollectionTask=?";
        Object[] args = { task };
        int c = this.getHibernateTemplate().bulkUpdate( hql, args );
        if ( log.isDebugEnabled() )
            this.log.debug( "clear period stepExecutes by task , count is :" + c );
    }

    public List getByTaskExecId( Long stepId ) {
        String hql = "from DataCollectionTaskStepExecute where dataCollectionTask.dataCollectionTaskId=? order by dataCollectionTaskStep.execOrder";
        Object[] args = { stepId };
        return this.getHibernateTemplate().find( hql, args );
        //		return (DataCollectionTaskStepExecute)searchDictionary("DataCollectionTaskStepExecute","dataCollectionTaskStep_id="+stepId).get(0);
    }

    public DataCollectionTaskStepExecute getByTaskExecIdAndStepDefineId( Long taskExecId, Long stepDefineId ) {
        String hql = "from DataCollectionTaskStepExecute where dataCollectionTask.dataCollectionTaskId=? and dataCollectionTaskStep.stepId=?";
        Object[] args = { taskExecId, stepDefineId };
        List l = this.getHibernateTemplate().find( hql, args );
        if ( l.size() > 0 )
            return (DataCollectionTaskStepExecute) l.get( 0 );
        else
            return null;
    }

    public String updateDisabled( String id, boolean value ) {
        //String hql = "update DataCollectionTaskStepExecute dctse set dctse.isUsed=? where dctse.stepExecuteId=?";

        DataCollectionTaskStepExecute dcTaskStepExecute =
            (DataCollectionTaskStepExecute) searchDictionary( "DataCollectionTaskStepExecute", "stepExecuteId=" + id ).get( 0 );
        dcTaskStepExecute.setIsUsed( value );
        if ( value ) {
            dcTaskStepExecute.setStatus( dcTaskStepExecute.STEP_STATUS_PREPARED );
        }
        else {
            dcTaskStepExecute.setStatus( dcTaskStepExecute.STEP_STATUS_PREPARED );

        }
        this.getHibernateTemplate().update( dcTaskStepExecute );
        //int ret = this.getHibernateTemplate().bulkUpdate(hql, value, id);
        //return ret + "";
        return "";
    }

    public String getStepExecuteIsUsed() {
        String isUsedString = "";
        List stepExecuteList = getAll();
        Iterator<DataCollectionTaskStepExecute> dctseIterator = stepExecuteList.iterator();
        while ( dctseIterator.hasNext() ) {
            DataCollectionTaskStepExecute dcts = dctseIterator.next();
            if ( dcts.getIsUsed() ) {
                isUsedString += dcts.getStepExecuteId() + ",";
            }
        }
        return isUsedString;
    }

    public int getStepDefineUsedCount( Long stepDefineId ) {
        String hql = "select count(*) from DataCollectionTaskStepExecute where dataCollectionTaskStep.stepId=?";
        List list = this.hibernateTemplate.find( hql, stepDefineId );
        int count = ( (Long) list.get( 0 ) ).intValue();
        return count;
    }
}
