package com.huge.ihos.system.systemManager.menu.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.menu.dao.MenuDao;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "menuDao" )
public class MenuDaoHibernate
    extends GenericDaoHibernate<Menu, String>
    implements MenuDao {

    public MenuDaoHibernate() {
        super( Menu.class );
    }

    public JQueryPager getMenuCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("menuId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new MenuByParentCallBack(paginatedList, Menu.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Menu.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getMenuCriteria", e );
            return paginatedList;
        }
    }

    /*
    class MenuByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	MenuByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("menu.menuId", parentId);
    		return criteria;
    	}
    }
     */

    public List getAllSubSystem() {
        return getAllRootMenu();
    }

    public List getAllRootMenu() {

        String hql = "from Menu m where ( m.parent.menuId is null or m.parent.menuId='' ) and disabled = 0";
        return this.getHibernateTemplate().find( hql );
    }

    public List getAllByParent( String pid ) {
        String hql = "from Menu m where m.parent.menuId = ? and m.disabled = ?";
        Object[] values = { pid, false };
        return this.getHibernateTemplate().find( hql, values );
    }

    public List getAllByRoot( String rid ) {
        String hql = "from Menu m where m.subSystem.menuId = ? order by m.parent.menuId";
        return this.getHibernateTemplate().find( hql, rid );
    }

    public List getAllNotLeafByRoot( String rid ) {
        String hql = "from Menu m where m.subSystem.menuId = ? and m.leaf=? order by m.parent.menuId";
        return this.getHibernateTemplate().find( hql, rid, false );
    }

    public List getAllEnabled() {
        String hql = "from Menu m where m.disabled = ? order by m.menuId";
        return this.getHibernateTemplate().find( hql, false );
    }

    public List getMenuChain( String id ) {
        List list = new ArrayList();
        Menu menu = null ;
        if(("").equals(id)){
        	menu = this.get( id );
        }
        while ( menu != null ) {
            list.add( menu );
            if ( menu.getParent() != null )
                menu = menu.getParent();
            else
                menu = null;
        }

        return list;
    }

	@Override
	public List getByParent(String pid) {
		String hql = "from Menu m where m.parent.menuId = '"+pid+"' ";
        return this.getHibernateTemplate().find( hql);
	}
}
