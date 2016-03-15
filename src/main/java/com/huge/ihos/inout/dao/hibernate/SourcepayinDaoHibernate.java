package com.huge.ihos.inout.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.dao.SourcepayinDao;
import com.huge.ihos.inout.model.Sourcepayin;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "sourcepayinDao" )
public class SourcepayinDaoHibernate
    extends GenericDaoHibernate<Sourcepayin, Long>
    implements SourcepayinDao {

    public SourcepayinDaoHibernate() {
        super( Sourcepayin.class );
    }

    public JQueryPager getSourcepayinCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("sourcePayinId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new SourcepayinByParentCallBack(paginatedList, Sourcepayin.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Sourcepayin.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getSourcepayinCriteria", e );
            return paginatedList;
        }
    }

    /*
    class SourcepayinByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	SourcepayinByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("sourcepayin.sourcePayinId", parentId);
    		return criteria;
    	}
    }
     */
    @Override
    public String getAmountSum( String currentPeriod ) {
        String sum = "0";
        try {
            List amount = getHibernateTemplate().find( "select sum(sp.amount) from Sourcepayin sp where sp.checkPeriod='" + currentPeriod + "'" );
            if ( amount != null && amount.size() != 0 ) {
                sum = amount.get( 0 )==null?"0":amount.get( 0 ).toString();
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        return sum;
    }
}
