package com.huge.ihos.system.datacollection.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.datacollection.dao.DataSourceDefineDao;
import com.huge.ihos.system.datacollection.model.DataSourceDefine;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "dataSourceDefineDao" )
public class DataSourceDefineDaoHibernate
    extends GenericDaoHibernate<DataSourceDefine, Long>
    implements DataSourceDefineDao {

    public DataSourceDefineDaoHibernate() {
        super( DataSourceDefine.class );
    }

    public JQueryPager getDataSourceDefineCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("dataSourceDefineId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new DataSourceDefineByParentCallBack(paginatedList, DataSourceDefine.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataSourceDefine.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataSourceDefineCriteria", e );
            return paginatedList;
        }
    }
    /*
    class DataSourceDefineByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	DataSourceDefineByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criterion getCustomCriterion() {
    		return Restrictions.eq("dataSourceDefine.dataSourceDefineId", parentId);
    	}
    }
     */
}
