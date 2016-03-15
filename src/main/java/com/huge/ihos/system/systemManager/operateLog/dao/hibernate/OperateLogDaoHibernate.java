package com.huge.ihos.system.systemManager.operateLog.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.operateLog.dao.OperateLogDao;
import com.huge.ihos.system.systemManager.operateLog.model.OperateLog;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository( "operateLogDao" )
public class OperateLogDaoHibernate
    extends GenericDaoHibernate<OperateLog, Long>
    implements OperateLogDao {

    public OperateLogDaoHibernate() {
        super( OperateLog.class );
    }

    public JQueryPager getOperateLogCriteria( JQueryPager paginatedList, List<PropertyFilter> filters ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( "operateLogId" );
            Map<String, Object> resultMap = getAppManagerCriteriaWithSearch( paginatedList, OperateLog.class, filters );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getOperateLogCriteria", e );
            return paginatedList;
        }

    }
}
