package com.huge.ihos.system.reportManager.chart.dao.hibernate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.reportManager.chart.dao.MccKeyDetailDao;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.ihos.system.reportManager.chart.model.MccKeyDetail;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "mccKeyDetailDao" )
public class MccKeyDetailDaoHibernate
    extends GenericDaoHibernate<MccKeyDetail, String>
    implements MccKeyDetailDao {

    public MccKeyDetailDaoHibernate() {
        super( MccKeyDetail.class );
    }

    public JQueryPager getMccKeyDetailCriteria( String parentId, JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( "mccKeyDetailId" );
            HibernateCallback callback = new MccKeyDetailByParentCallBack( paginatedList, MccKeyDetail.class, parentId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, MccKey.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;

        }
        catch ( Exception e ) {
            log.error( "getMccKeyDetailCriteria", e );
            return paginatedList;
        }
    }

    class MccKeyDetailByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String parentId;

        MccKeyDetailByParentCallBack( final JQueryPager paginatedList, final Class object, String parentId ) {
            super( paginatedList, object );
            this.parentId = parentId;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            //			cr.add(Restrictions.eq("dictionary.dictionaryId", parentId));
            criteria.add( Restrictions.eq( "mccKey.mccKeyId", parentId ) );
            return criteria;
        }
    }

    @Transactional( readOnly = true )
    public Object[] clockDialMethod( String[] data )
        throws SQLException {
        Object[] myData = new Object[2];
        List<MccKeyDetail> list = this.getCriteria().add(Restrictions.eq("mccKeyId",data[0])).list();
        myData[0] = list;
        String sql = data[1];
        /*SearchUtils su = new SearchUtils();
        Map systemProperties = su.getSystemPropertiesMap();
*/
        if ( OtherUtil.measureNotNull( sql ) ) {
            Connection conn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
            ResultSet rs = null;
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery( sql );
            if ( rs.next() ) {
                BigDecimal value = (BigDecimal) rs.getObject( 1 );
                myData[1] = value;
            }
        }

        return myData;
    }

}
