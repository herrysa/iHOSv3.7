package com.huge.ihos.period.dao.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.period.dao.PeriodDao;
import com.huge.ihos.period.model.Period;
import com.huge.ihos.system.reportManager.search.dao.hibernate.QueryJqueryPagerHibernateNativeSqlCallBack;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "periodDao" )
public class PeriodDaoHibernate
    extends GenericDaoHibernate<Period, Long>
    implements PeriodDao {

    public PeriodDaoHibernate() {
        super( Period.class );
    }

    public JQueryPager getPeriodCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("periodId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new PeriodByParentCallBack(paginatedList, Period.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Period.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getPeriodCriteria", e );
            return paginatedList;
        }
    }

    /*
    class PeriodByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	PeriodByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	
    	public Criterion getCustomCriterion() {
    		return Restrictions.eq("period.periodId", parentId);
    	}
    }
     */

    public Period getCurrentPeriod() {
        Period period = null;
        String hql = "from Period where cpFlag=?";
        List list = this.getHibernateTemplate().find( hql, true );
        if ( list.size() > 1 ) {
            throw new RuntimeException( "current period more than one,please check it." );
        }
        else if ( list.size() == 0 ) {
            throw new RuntimeException( "current period had't been set,please check it." );
        }
        else {
            period = (Period) list.get( 0 );
        }
        return period;
    }

    public void closeCheckPeriod( String periodCode ) {
        Period period = this.getPeriodByCode( periodCode );
        period.setCpFlag( false );
        this.save( period );
    }

    public void closeDataCollectPeriod( String periodCode ) {
        Period period = this.getPeriodByCode( periodCode );
        period.setCdpFlag( false );
        this.save( period );

    }

    public boolean isCheckPeriodClosed( String periodCode ) {
        Period period = this.getPeriodByCode( periodCode );
        return !period.getCpFlag();
    }

    public boolean isDataCollectPeriodClosed( String periodCode ) {
        Period period = this.getPeriodByCode( periodCode );
        return !period.getCdpFlag();
    }

    public void setCurrentPeriod( String periodCode ) {
        Period period = this.getPeriodByCode( periodCode );
        period.setCpFlag( true );
        this.save( period );
    }

    public void deletePeriod( String periodCode ) {
        Period period = this.getPeriodByCode( periodCode );
        this.remove( period.getPeriodId() );

    }

    public Period getPeriodByCode( String periodCode ) {
        Period period = null;
        String hql = "from Period where periodCode=?";
        List list = this.getHibernateTemplate().find( hql, periodCode );
        if ( list.size() == 0 ) {
            period = null;
            throw new RuntimeException( "can not find the period define whose period code is: " + periodCode );
        }
        else {
            period = (Period) list.get( 0 );
        }
        return period;
    }

    public List getAllListMapOfPeriodBySql() {
        String sql = "select * from t_period";
        Query q = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( sql );
        AliasToEntityMapResultTransformer tran1 = AliasToEntityMapResultTransformer.INSTANCE;
        q.setResultTransformer( tran1 );
        /*		List l1 = q.list();
        
         ToListResultTransformer tran2 = ToListResultTransformer.INSTANCE;
         q.setResultTransformer(tran2);
         List l2 = q.list();*/
        System.err.println( q.getQueryString() );
        return q.list();
    }

    public Object getPageOfPeriodBySql() {
        String sql = "select * from t_period ";
        //		String sql = "select * from t_period where cyear = ? and cmonth =?";
        JQueryPager pager = new JQueryPager();
        pager.setPageSize( 10 );
        //		Object[] args = {"2011","09"};
        Object[] args = {};
        QueryJqueryPagerHibernateNativeSqlCallBack back = new QueryJqueryPagerHibernateNativeSqlCallBack( pager, sql, args, null );
        Object obj = this.getHibernateTemplate().execute( back );
        return obj;

    }

   /* @Override
    public Period getCurrentDCPeriod() {
        Period period = null;
        String hql = "from Period where cdpFlag=?";
        List list = this.getHibernateTemplate().find( hql, true );
        if ( list.size() > 1 ) {
            throw new RuntimeException( "current dc period more than one,please check it." );
        }
        else if ( list.size() == 0 ) {
            //throw new RuntimeException( "current dc period had't been set,please check it." );
            return null;
        }
        else {
            period = (Period) list.get( 0 );
        }
        return period;
    }*/

	@Override
	public String getPeriodCodeByDate(Date date) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.ge("endDate", date));
		criteria.add(Restrictions.le("startDate", date));
		List<Period> periods = criteria.list();
		if(periods!=null&&periods.size()!=0){
			return periods.get(0).getPeriodCode();
		}else{
			return null;
		}
	}

    @Override
    public List<Period> getPeriodsBetween( String beginPeriod, String endPeriod ) {
        String hql = "from Period where periodCode>=? and periodCode<=? order by periodCode asc";
        List list = this.getHibernateTemplate().find( hql, beginPeriod, endPeriod);
        return list;
    }

	@Override
	public List<Period> getLessCurrentPeriod(String currentPeriod) {
		String hql = "from Period where periodCode<? order by periodCode asc";
        List list = this.getHibernateTemplate().find( hql, currentPeriod);
        return list;
	}
	
	@Override
	public List<Period> getPeriodsByYear(String year) {
		String hql = "from Period where cyear=? order by periodCode asc";
        List list = this.getHibernateTemplate().find( hql, year);
        return list;
	}

	@Override
	public List<String> getYearList() {
		Criteria criteria = this.getCriteria();
		ProjectionList projectionList = Projections.projectionList();  
		projectionList.add(Projections.property("cyear")); 
		criteria.setProjection(Projections.distinct(projectionList));
		List<String> yearList = criteria.list();
		return yearList;
	}
    

}
