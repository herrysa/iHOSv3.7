package com.huge.ihos.system.repository.store.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.BaseTreeDaoHibernate;
import com.huge.ihos.system.repository.store.dao.StoreDao;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("storeDao")
public class StoreDaoHibernate extends BaseTreeDaoHibernate<Store, String> implements StoreDao {

    public StoreDaoHibernate() {
        super(Store.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getStoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Store.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getStoreCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getStoreTreeByColumn(String column,String value,String type) {
		String hql = "";
		if(type==null){
			hql = "from " + this.getPersistentClass().getSimpleName() + " where "+column+"=? and parent_id is not null ORDER BY storeCode ASC";
		}else if("isPos".equals(type)){
			hql = "from " + this.getPersistentClass().getSimpleName() + " where "+column+"=? and is_pos = 1 and parent_id is not null ORDER BY storeCode ASC";
		}else if("disabled".equals(type)){
			hql = "from " + this.getPersistentClass().getSimpleName() + " where "+column+"=? and disabled = 0 and parent_id is not null ORDER BY storeCode ASC";
		}
		List<Store> storeTree = this.getHibernateTemplate().find(hql,value);
		return storeTree;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getAllDescendant(String orgCode,String nodeId,boolean isPos) {
		Store node = this.get( nodeId );
		String hql = "";
		List<Store> l = null;
		if(!isPos){
			hql = "from " + this.getPersistentClass().getSimpleName() + " where orgCode=? and parent_id=? ORDER BY storeCode ASC";
		    l = this.getHibernateTemplate().find( hql, orgCode,nodeId );
		}else{
			hql = "from " + this.getPersistentClass().getSimpleName() + " where is_pos = 1 and (lft>? AND lft<?) ORDER BY storeCode ASC";
			l = this.getHibernateTemplate().find( hql, node.getLft(), node.getRgt() );
		}
	    return l;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getChildrenCount(String nodeId) {
		 String hql = "select count(id) from " + this.getPersistentClass().getSimpleName() + " where parent_id= ?";
	     List l = this.getHibernateTemplate().find( hql,nodeId );
	     int count = ( (Long) l.get( 0 ) ).intValue();
	     return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getStoresBySearch(String orgCode,String storeCode, String storeName,
			String storeType, String isPos,String disabled) {
		 String hql = "from " + this.getPersistentClass().getSimpleName() + " where orgCode = '" + orgCode+"' ";
		 if(storeCode!=null){
			 hql += " and storeCode like '%"+storeCode+"%'";
		 }
		 if(storeName!=null){
			 hql += " and storeName like '%"+storeName+"%'";
		 }
		 if(storeType!=null){
			 hql += " and storeType = "+storeType;
		 }
		 if(isPos!=null){
			 hql += " and is_pos = "+isPos;
		 }
		 if(disabled!=null){
			 hql += " and disabled = "+disabled;
		 }
		 List<Store> list = this.getHibernateTemplate().find(hql);
		 return list;
	}
	
    
    
}
