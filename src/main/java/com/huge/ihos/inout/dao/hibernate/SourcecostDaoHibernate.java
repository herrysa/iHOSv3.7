package com.huge.ihos.inout.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.dao.SourcecostDao;
import com.huge.ihos.inout.model.Sourcecost;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "sourcecostDao" )
public class SourcecostDaoHibernate
    extends GenericDaoHibernate<Sourcecost, Long>
    implements SourcecostDao {

    public SourcecostDaoHibernate() {
        super( Sourcecost.class );
    }

    public JQueryPager getSourcecostCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("sourceCostId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new SourcecostByParentCallBack(paginatedList, Sourcecost.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Sourcecost.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getSourcecostCriteria", e );
            return paginatedList;
        }
    }

    /*
    class SourcecostByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	SourcecostByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("sourcecost.sourceCostId", parentId);
    		return criteria;
    	}
    }
     */
    @Override
    public String getAmountSum( String currentPeriod ) {
        String sum = "0";
        try {
            List amount = getHibernateTemplate().find( "select sum(sc.amount) from Sourcecost sc where sc.checkPeriod='" + currentPeriod + "'" );
            if ( amount != null && amount.size() != 0 ) {
            	if(amount.get(0) != null) {
            		sum = amount.get( 0 ).toString();
            	}
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        return sum;
    }
}
