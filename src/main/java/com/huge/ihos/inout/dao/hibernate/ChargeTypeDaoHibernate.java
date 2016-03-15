package com.huge.ihos.inout.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.dao.ChargeTypeDao;
import com.huge.ihos.inout.model.ChargeType;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "chargeTypeDao" )
public class ChargeTypeDaoHibernate
    extends GenericDaoHibernate<ChargeType, String>
    implements ChargeTypeDao {

    public ChargeTypeDaoHibernate() {
        super( ChargeType.class );
    }

    public JQueryPager getChargeTypeCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("chargeTypeId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new ChargeTypeByParentCallBack(paginatedList, ChargeType.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, ChargeType.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getChargeTypeCriteria", e );
            return paginatedList;
        }
    }

    /*
    class ChargeTypeByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	ChargeTypeByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("chargeType.chargeTypeId", parentId);
    		return criteria;
    	}
    }
     */

    public int countItemByPayinItemId( String id ) {
        int count =
            ( (Long) ( this.getHibernateTemplate().find( "select count(*) from ChargeType c where c.payinItem.payinItemId=?", id ).get( 0 ) ) ).intValue();

        return count;
    }

    /*	public void updateCnCodeById(String id) {
     String hql = "update ChargeType set cnCode = func_getpycodes(chargeTypeName) where id = ?";
     Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
     query.setString(0, id);
     query.executeUpdate();
     }*/
}
