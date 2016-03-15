package com.huge.ihos.system.datacollection.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.datacollection.dao.DataSourceTypeDao;
import com.huge.ihos.system.datacollection.model.DataSourceType;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "dataSourceTypeDao" )
public class DataSourceTypeDaoHibernate
    extends GenericDaoHibernate<DataSourceType, Long>
    implements DataSourceTypeDao {

    public DataSourceTypeDaoHibernate() {
        super( DataSourceType.class );
    }

    public JQueryPager getDataSourceTypeCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("dataSourceTypeId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new DataSourceTypeByParentCallBack(paginatedList, DataSourceType.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataSourceType.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataSourceTypeCriteria", e );
            return paginatedList;
        }
    }
    /*
    class DataSourceTypeByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	DataSourceTypeByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criterion getCustomCriterion() {
    		return Restrictions.eq("dataSourceType.dataSourceTypeId", parentId);
    	}
    }
     */
}
