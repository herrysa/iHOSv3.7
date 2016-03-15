package com.huge.ihos.kaohe.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.BaseTreeDaoHibernate;
import com.huge.ihos.kaohe.dao.KpiItemDao;
import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.model.BaseTreeNode;

@Repository("kpiItemDao")
public class KpiItemDaoHibernate
		extends
			BaseTreeDaoHibernate<KpiItem, Long> implements KpiItemDao {

	public KpiItemDaoHibernate() {
		super(KpiItem.class);
	}

    @Override
    public List<KpiItem> getTreeLeafByRootIdAndPeriodType( Long rootId, String periodType ) {
        KpiItem node = this.get( rootId );
        int lft = node.getLft(),rgt=node.getRgt();
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where lft>=? AND lft<=? and rgt=lft+1 and level=3 and periodType=? ORDER BY keyCode ASC";
        List<KpiItem> l = this.getHibernateTemplate().find( hql, lft, rgt ,periodType);
        return l;
    }

    @Override
    public List<KpiItem> getTreeNoLeafByRootIdAndLeafLessThree( Long rootId ) {
        KpiItem node = this.get( rootId );
        int lft = node.getLft(),rgt=node.getRgt();
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where lft>=? AND lft<=? and level<3 ORDER BY keyCode ASC";
        List<KpiItem> l = this.getHibernateTemplate().find( hql, lft, rgt);
        return l;
    }

    @Override
    public List<KpiItem> getFullTreeByRootId(Long rootId){
        KpiItem node = this.get( rootId );
        int lft = node.getLft(),rgt=node.getRgt();
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where lft>=? AND lft<=?  ORDER BY keyCode ASC";
        List<KpiItem> l = this.getHibernateTemplate().find( hql, lft, rgt);
        return l;
    }

    @Override
    public void processNodeLeaf() {
        String sql = "UPDATE " + this.getPersistentClass().getSimpleName() + " set leaf=1  where level=3 and (rgt-lft)=1";
        
        this.getHibernateTemplate().bulkUpdate( sql );
        
    }
    

    public List<KpiItem> getAllDescendant( Long nodeId ) {
    	KpiItem node = this.get( nodeId );
        String hql = "from " + this.getPersistentClass().getSimpleName() + " where (lft>? AND lft<?) ORDER BY keyCode ASC";
        List<KpiItem> l = this.getHibernateTemplate().find( hql, node.getLft(), node.getRgt() );
        return l;
    }
}
