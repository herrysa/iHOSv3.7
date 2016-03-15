package com.huge.ihos.inout.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.dao.PayinItemDao;
import com.huge.ihos.inout.model.PayinItem;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "payinItemDao" )
public class PayinItemDaoHibernate
    extends GenericDaoHibernate<PayinItem, String>
    implements PayinItemDao {

    public PayinItemDaoHibernate() {
        super( PayinItem.class );
    }

    public JQueryPager getPayinItemCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("payinItemId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new PayinItemByParentCallBack(paginatedList, PayinItem.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, PayinItem.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getPayinItemCriteria", e );
            return paginatedList;
        }
    }
    /*
    class PayinItemByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	PayinItemByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("payinItem.payinItemId", parentId);
    		return criteria;
    	}
    }
     */

    /*	public void updateCnCodeById(String id) {
     String hql = "update PayinItem set cnCode = func_getpycodes(payinItemName) where payinItemId = ?";
     Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
     query.setString(0, id);
     query.executeUpdate();
     }*/

}
