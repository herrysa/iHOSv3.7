package com.huge.ihos.material.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.BaseTreeDaoHibernate;
import com.huge.ihos.material.dao.InventoryTypeDao;
import com.huge.ihos.material.model.InventoryType;

@Repository("inventoryTypeDao")
public class InventoryTypeDaoHibernate extends BaseTreeDaoHibernate<InventoryType, String> implements InventoryTypeDao {

    public InventoryTypeDaoHibernate() {
        super(InventoryType.class);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryType> getFullInvTypeTree(String orgCode,
			String copyCode,String type) {
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where orgCode=? and copyCode=? and parent_id is not null";
		if("disabled".equals(type)){
			hql += " and disabled = 0 ";
		}
		hql += " ORDER BY InvtypeCode ASC";
		List<InventoryType> inventoryTypes = this.getHibernateTemplate().find( hql,orgCode, copyCode);
		return inventoryTypes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryType> getAllDescendant(String orgCode,String copyCode,String nodeId) {
	     String hql = "from " + this.getPersistentClass().getSimpleName() + " where orgCode=? and copyCode=? and parent_id=? ORDER BY InvtypeCode ASC";
	     List<InventoryType> l = this.getHibernateTemplate().find( hql, orgCode,copyCode,nodeId );
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
    
}
