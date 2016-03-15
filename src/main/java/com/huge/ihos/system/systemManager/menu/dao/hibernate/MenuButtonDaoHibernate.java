package com.huge.ihos.system.systemManager.menu.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.menu.dao.MenuButtonDao;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("menuButtonDao")
public class MenuButtonDaoHibernate extends GenericDaoHibernate<MenuButton, String> implements MenuButtonDao {

    public MenuButtonDaoHibernate() {
        super(MenuButton.class);
    }
    
    public JQueryPager getMenuButtonCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, MenuButton.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getMenuButtonCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public String[] getDistinctMenuIds(Object obj) {
		Session session = this.getSessionFactory().getCurrentSession();
		String sql = "select distinct menuId from t_menuButton";
		if(obj instanceof List){
			List<Menu> menus = (List<Menu>)obj;
			if(menus!=null && menus.size()>0){
				String menuIds = "";
				for(Menu menu:menus){
					menuIds += "'"+menu.getMenuId().trim()+"',";
				}
				menuIds = OtherUtil.subStrEnd(menuIds, ",");
				sql += " where menuId in ( "+menuIds+" )";
			}else{
				return null;
			}
		}else{
			
		}
		SQLQuery query = session.createSQLQuery(sql);
		List<String> list = query.list();
		if(list==null || list.size()==0){
			return null;
		}
		return list.toArray( new String[]{});
	}

	@Override
	public MenuButton getByMenuIdAndButtonId(String menuId, String buttonId) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("menuId", menuId));
		criteria.add(Restrictions.eq("id", buttonId));
		List<MenuButton> l = (List<MenuButton>)criteria.list();
		if(l==null || l.size()!=1){
			return null;
		}else{
			return l.get(0);
		}
	}

	@Override
	public List<MenuButton> getMenuButtonsInRight(String menuId, String userId) {
		String sql = "SELECT * FROM t_menuButton WHERE id NOT in (" +
					 "SELECT DISTINCT(j.mbId) FROM (SELECT bpd.buttonId mbId FROM t_buttonPriv bp LEFT JOIN t_buttonPriv_detail bpd ON bp.privId=bpd.buttonPrivId WHERE bp.buttonType=2 AND bp.searchOrMenuId='"+menuId+"' AND bp.roleId in (SELECT role_id FROM app_user u LEFT JOIN user_role ur ON u.id=ur.user_id left JOIN role r ON ur.role_id=r.id WHERE u.id="+userId+" ) " +
					 "UNION SELECT bpud.buttonId mbId FROM t_buttonPrivUser bpu LEFT JOIN t_buttonPrivUser_detail bpud ON bpu.privId=bpud.buttonPrivUserId WHERE bpu.buttonType=2 AND bpu.searchOrMenuId='"+menuId+"' AND bpu.userId = "+userId+") j ) " +
					 "AND menuId='"+menuId+"'";
		SQLQuery  query =  this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.addEntity(MenuButton.class).list();
	}
	
	
    
}
