package com.huge.ihos.templateDemo.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.globalparam.model.GlobalParam;
import com.huge.ihos.templateDemo.dao.TemplateDemoDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository( "templateDemoDao" )
public class TemplateDemoDaoHibernate
    extends GenericDaoHibernate<GlobalParam, Long>
    implements TemplateDemoDao {

    public TemplateDemoDaoHibernate() {
        super( GlobalParam.class );
    }

    public JQueryPager getGlobalParamCriteria( JQueryPager paginatedList, List<PropertyFilter> filters ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( "subSystemId" );
            //			Map<String, Object> resultMap = getAppManagerCriteria(
            //					paginatedList, GlobalParam.class,null);
            Map<String, Object> resultMap = getAppManagerCriteriaWithSearch( paginatedList, GlobalParam.class, filters );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getGlobalParamCriteria", e );
            return paginatedList;
        }

    }

}
