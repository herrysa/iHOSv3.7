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
import com.huge.ihos.system.datacollection.maptables.dao.AcctcostmapDao;
import com.huge.ihos.system.datacollection.maptables.model.Acctcostmap;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "acctcostmapDao" )
public class AcctcostmapDaoHibernate
    extends GenericDaoHibernate<Acctcostmap, Long>
    implements AcctcostmapDao {

    public AcctcostmapDaoHibernate() {
        super( Acctcostmap.class );
    }

    public JQueryPager getAcctcostmapCriteria( JQueryPager paginatedList, String acctcode, String acctname, String costitemid, String costitem ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("acctcode");
                paginatedList.setSortCriterion( null );

            HibernateCallback callback = new AcctcostmapByParentCallBack( paginatedList, Acctcostmap.class, acctcode, acctname, costitemid, costitem );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Acctcostmap.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getAcctcostmapCriteria", e );
            return paginatedList;
        }
    }

    class AcctcostmapByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String acctcode;

        String acctname;

        String costitemid;

        String costitem;

        AcctcostmapByParentCallBack( final JQueryPager paginatedList, final Class object, String acctcode, String acctname, String costitemid,
                                     String costitem ) {
            super( paginatedList, object );
            this.acctcode = acctcode;
            this.acctname = acctname;
            this.costitemid = costitemid;
            this.costitem = costitem;
        }

        @Override
        public Criteria getCustomCriterion( Criteria criteria ) {
            if ( acctcode != null && !acctcode.equals( "" ) )
                criteria.add( Restrictions.like( "acctcode", acctcode, MatchMode.ANYWHERE ) );
            if ( acctname != null && !acctname.equals( "" ) )
                criteria.add( Restrictions.like( "acctname", acctname, MatchMode.ANYWHERE ) );
            if ( costitemid != null && !costitemid.equals( "" ) )
                criteria.add( Restrictions.like( "costitemid", costitemid, MatchMode.ANYWHERE ) );
            if ( costitem != null && !costitem.equals( "" ) )
                criteria.add( Restrictions.like( "costitem", costitem, MatchMode.ANYWHERE ) );
            return criteria;
        }
    }

}
