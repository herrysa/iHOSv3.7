package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.search.dao.ReportDao;
import com.huge.ihos.system.reportManager.search.model.Report;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository( "reportDao" )
public class ReportDaoHibernate
    extends GenericDaoHibernate<Report, Long>
    implements ReportDao {

    public ReportDaoHibernate() {
        super( Report.class );
    }

    public JQueryPager getReportCriteria( JQueryPager paginatedList,List<PropertyFilter> filters ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("reportId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new ReportByParentCallBack(paginatedList, Report.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Report.class, filters);
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getReportCriteria", e );
            return paginatedList;
        }
    }

    /*
    class ReportByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	ReportByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("report.reportId", parentId);
    		return criteria;
    	}
    }
     */

    public List getReportByGroup( String groupName ) {

        String hql = " from Report r where r.groupName=? order by r.reqNo asc";
        List l = this.getHibernateTemplate().find( hql, groupName );
        return l;
    }
}
