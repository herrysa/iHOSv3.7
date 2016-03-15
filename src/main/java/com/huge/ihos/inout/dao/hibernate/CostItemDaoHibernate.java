package com.huge.ihos.inout.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.dao.CostItemDao;
import com.huge.ihos.inout.model.CostItem;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "costItemDao" )
public class CostItemDaoHibernate
    extends GenericDaoHibernate<CostItem, String>
    implements CostItemDao {

    public CostItemDaoHibernate() {
        super( CostItem.class );
    }

    public JQueryPager getCostItemCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("costItemId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new CostItemByParentCallBack(paginatedList, CostItem.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, CostItem.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getCostItemCriteria", e );
            return paginatedList;
        }
    }

    /*
    class CostItemByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	CostItemByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("costItem.costItemId", parentId);
    		return criteria;
    	}
    }
     */

    /* (non-Javadoc)
     * @see com.huge.dao.CostItemDao#countItemByCostuseId(java.lang.String)
     */
    public int countItemByCostuseId( String id ) {
        int count =
            ( (Long) ( this.getHibernateTemplate().find( "select count(*) from CostItem c where c.costUse.costUseId=?", id ).get( 0 ) ) ).intValue();

        return count;
    }

    public int countItemByParentId( String id ) {
        int count =
            ( (Long) ( this.getHibernateTemplate().find( "select count(*) from CostItem c where c.parent.costItemId=?", id ).get( 0 ) ) ).intValue();
        return count;
    }
    /*	public void updateCnCodeById(String id) {
     String hql = "update CostItem set cnCode = func_getpycodes(costItemName) where id = ?";
     Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
     query.setString(0, id);
     query.executeUpdate();
     }*/

	@Override
	public boolean isInUse(String costItemId) {
		/*
		 * 检查表:t_sourcecost,T_UpItem,T_UpCost,t_allotItem
		 */
		String hql_sourcecost = "from Sourcecost where costItemId.costItemId = ?";
		List list = this.getHibernateTemplate().find(hql_sourcecost,costItemId);
		if(list==null || list.size()==0){
			String hql_upitem = "from UpItem where costItemId.costItemId = ?";
			List list2 = this.getHibernateTemplate().find(hql_upitem,costItemId);
			if(list2==null || list2.size()==0){
				String hql_upcost = "from UpCost where costitemid.costItemId = ?";
				List list3 = this.getHibernateTemplate().find(hql_upcost,costItemId);
				if(list3==null || list3.size()==0){
					String hql_allotitem = "from AllotItem where costItem.costItemId = ?";
					List list4 = this.getHibernateTemplate().find(hql_allotitem,costItemId);
					if(list4==null || list4.size()==0){
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean hasChildren(String costItemId) {
		String hql = "from CostItem where costItemId like '"+costItemId+"%'";
		List list = this.getHibernateTemplate().find(hql);
		if(list==null || list.size()<2){
			return false;
		}
		return true;
	}
	
	
    
}
