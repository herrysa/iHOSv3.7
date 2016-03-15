package com.huge.ihos.system.reportManager.chart.dao.hibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.chart.dao.MccKeyDao;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "mccKeyDao" )
public class MccKeyDaoHibernate
    extends GenericDaoHibernate<MccKey, String>
    implements MccKeyDao {

    public MccKeyDaoHibernate() {
        super( MccKey.class );
    }

    public JQueryPager getMccKeyCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("mccKeyId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new MccKeyByParentCallBack(paginatedList, MccKey.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, MccKey.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getMccKeyCriteria", e );
            return paginatedList;
        }
    }

    /*
    class MccKeyByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	MccKeyByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("mccKey.mccKeyId", parentId);
    		return criteria;
    	}
    }
     */

    public MccKey getCkey( String cKey ) {
    	List<MccKey> list =this.getCriteria().add(Restrictions.eq("ckey",cKey)).list();
        return list.size()==1?list.get( 0 ):null;
    }

    @Transactional( readOnly = true )
    @SuppressWarnings({ "unused", "rawtypes" })
    public Map<String, Object[]> obtainDataMethod( String mySql )
        throws SQLException {
    	List list=this.sessionFactory.getCurrentSession().createSQLQuery(mySql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		int size=list.size();
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		Object[] listKey=((Map<String, Object>) list.get(0)).keySet().toArray();
		for (int i = 0; i < listKey.length; i++) {
			String columnName=(String) listKey[i];
			
			Object[] values = new Object[size];
			
			for (int j = 0; j < size; j++) {
				values[j]=((Map<String, Object>) list.get(j)).get(columnName);
			}
			map.put(columnName, values);
		}
        return map;
    }
/*    public Object obtainDataMethod( String mySql )
    throws SQLException {
    	Connection conn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
    	ResultSet rs = null;
    	Statement stmt = conn.createStatement( rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY );
    	
    	rs = stmt.executeQuery( mySql );
    	//封装resultSet数据 ,利用ResultSetMetaData对象可获得表的结构
    	ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
    	Map<String, Object[]> map = new HashMap<String, Object[]>();
    	for ( int j = 1; j <= rsmd.getColumnCount(); j++ ) {
    		String columnName = rsmd.getColumnName( j );
    		rs.last();
    		Object[] values = new Object[rs.getRow()];
    		rs.beforeFirst();
    		//rs = stmt.executeQuery( mySql );
    		int k = 0;
    		while ( rs.next() ) {
    			//每行记录放到一个map里！
    			Object value = rs.getObject( columnName );
    			values[k] = value;
    			k++;
    		}
    		map.put( columnName, values );
    	}
    	
    	Object myData = map;
    	
    	conn.close();
    	stmt.close();
    	rs.close();
    	return myData;
    }
*/
    public Float[] obtainDatagradisPlay( String sql, String[] columnName )
        throws SQLException {
    	List list=this.sessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    	Float[] obj = new Float[3];
    	for (Object object : list) {
    		obj[0] = ((BigDecimal)((Map<String, Object>)object).get(columnName[0])).floatValue();
    		obj[1] = ((BigDecimal)((Map<String, Object>)object).get(columnName[1])).floatValue();
    		obj[2] = ((BigDecimal)((Map<String, Object>)object).get(columnName[2])).floatValue();
		}
        return obj;
    }
/*    public Float[] obtainDatagradisPlay( String sql, String[] columnName )
    throws SQLException {
    	Connection conn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
    	ResultSet rs = null;
    	Statement stmt = conn.createStatement( rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY );
    	rs = stmt.executeQuery( sql );
    	Float[] obj = new Float[3];
    	while ( rs.next() ) {
    		obj[0] = ( (BigDecimal) rs.getObject( columnName[0] ) ).floatValue();
    		obj[1] = ( (BigDecimal) rs.getObject( columnName[1] ) ).floatValue();
    		obj[2] = ( (BigDecimal) rs.getObject( columnName[2] ) ).floatValue();
    	}
    	return obj;
    }
*/}
