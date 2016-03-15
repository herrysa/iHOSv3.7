package com.huge.ihos.system.datacollection.maptables.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.datacollection.maptables.dao.DeptmapDao;
import com.huge.ihos.system.datacollection.maptables.model.Deptmap;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "deptmapDao" )
public class DeptmapDaoHibernate
    extends GenericDaoHibernate<Deptmap, Long>
    implements DeptmapDao {

    public DeptmapDaoHibernate() {
        super( Deptmap.class );
    }

    public JQueryPager getDeptmapCriteria( JQueryPager paginatedList, String targetname, String targetcode, String sourcename, String sourcecode,
                                           String marktype ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("id");
                paginatedList.setSortCriterion( null );

            HibernateCallback callback =
                new DeptmapByParentCallBack( paginatedList, Deptmap.class, targetname, targetcode, sourcename, sourcecode, marktype );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Deptmap.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDeptmapCriteria", e );
            return paginatedList;
        }
    }

    class DeptmapByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String targetname;

        String targetcode;

        String sourcename;

        String sourcecode;

        String marktype;

        DeptmapByParentCallBack( final JQueryPager paginatedList, final Class object, String targetname, String targetcode, String sourcename,
                                 String sourcecode, String marktype ) {
            super( paginatedList, object );
            this.targetname = targetname;
            this.targetcode = targetcode;
            this.sourcename = sourcename;
            this.sourcecode = sourcecode;
            this.marktype = marktype;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            if ( targetname != null && !targetname.equals( "" ) )
                criteria.add( Restrictions.like( "targetname", targetname, MatchMode.ANYWHERE ) );
            if ( targetcode != null && !targetcode.equals( "" ) )
                criteria.add( Restrictions.like( "targetcode", targetcode, MatchMode.ANYWHERE ) );
            if ( sourcename != null && !sourcename.equals( "" ) )
                criteria.add( Restrictions.like( "sourcename", sourcename, MatchMode.ANYWHERE ) );
            if ( sourcecode != null && !sourcecode.equals( "" ) )
                criteria.add( Restrictions.like( "sourcecode", sourcecode, MatchMode.ANYWHERE ) );
            if ( marktype != null && !marktype.equals( "" ) )
                criteria.add( Restrictions.like( "marktype", marktype, MatchMode.ANYWHERE ) );
            return criteria;
        }
    }

}
