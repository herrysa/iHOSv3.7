package com.huge.ihos.inout.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.dao.ChargeItemDao;
import com.huge.ihos.inout.model.ChargeItem;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "chargeItemDao" )
public class ChargeItemDaoHibernate
    extends GenericDaoHibernate<ChargeItem, String>
    implements ChargeItemDao {

    public ChargeItemDaoHibernate() {
        super( ChargeItem.class );
    }

    public JQueryPager getChargeItemCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("chargeItemNo");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new ChargeItemByParentCallBack(paginatedList, ChargeItem.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, ChargeItem.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getChargeItemCriteria", e );
            return paginatedList;
        }
    }

    /*
    class ChargeItemByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	ChargeItemByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("chargeItem.chargeItemNo", parentId);
    		return criteria;
    	}
    }
     */
    public int countItemByChargeTypeId( String id ) {
        int count =
            ( (Long) ( this.getHibernateTemplate().find( "select count(*) from ChargeItem c where c.chargeType.chargeTypeId=?", id ).get( 0 ) ) ).intValue();

        return count;
    }

    /*public void updateCnCodeById(String id) {
    	String hql = "update ChargeItem set pyCode = func_getpycodes(chargeItemName) where chargeItemId = ?";
    	  Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
    	  query.setString(0, id);
    	  query.executeUpdate();
    }*/
}
