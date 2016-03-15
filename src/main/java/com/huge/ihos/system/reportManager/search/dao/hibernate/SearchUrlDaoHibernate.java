package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.reportManager.search.dao.SearchUrlDao;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivDetailDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivUserDetailDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUserDetail;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;

@Repository( "searchUrlDao" )
public class SearchUrlDaoHibernate
    extends GenericDaoHibernate<SearchUrl, String>
    implements SearchUrlDao {

	private ButtonPrivDetailDao buttonPrivDetailDao;
	private ButtonPrivUserDetailDao buttonPrivUserDetailDao;
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    @Autowired
	public void setButtonPrivDetailDao(
			ButtonPrivDetailDao buttonPrivDetailDao) {
		this.buttonPrivDetailDao = buttonPrivDetailDao;
	}

	@Autowired
	public void setButtonPrivUserDetailDao(
			ButtonPrivUserDetailDao buttonPrivUserDetailDao) {
		this.buttonPrivUserDetailDao = buttonPrivUserDetailDao;
	}

	public SearchUrlDaoHibernate() {
        super( SearchUrl.class );
    }

    public JQueryPager getSearchUrlCriteria( JQueryPager paginatedList, String parentId ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("searchUrlId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:
            HibernateCallback callback = new SearchUrlByParentCallBack( paginatedList, SearchUrl.class, parentId );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, SearchUrl.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getSearchUrlCriteria", e );
            return paginatedList;
        }
    }

    class SearchUrlByParentCallBack
        extends JqueryPagerHibernateCallBack {
        String parentId;

        SearchUrlByParentCallBack( final JQueryPager paginatedList, final Class object, String parentId ) {
            super( paginatedList, object );
            this.parentId = parentId;
        }

        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.createAlias( "search", "search" );
            criteria.add( Restrictions.eq( "search.searchId", parentId ) );
            return criteria;
        }
    }

    public SearchUrl[] getSearchUrlsBySearchIdOrdered( String searchId ) {
        String hql = "from SearchUrl s where s.search.searchId=? order by s.oorder";
        List list = this.getHibernateTemplate().find( hql, searchId );
        SearchUrl[] sis = new SearchUrl[0];
        if ( list.size() > 0 ) {
            sis = new SearchUrl[list.size()];
            list.toArray( sis );
        }
        return sis;
    }

	@Override
	public SearchUrl[] getSearchUrlByRight(String userId,String searchId) {
		try {
			User user = userDao.get(Long.parseLong(userId));
			Set<Role> userRoles = user.getRoles();
			int roleSize = userRoles.size();
			Iterator<Role> roleIt = userRoles.iterator();
			Map<String,Integer> map = new HashMap<String,Integer>();
			while (roleIt.hasNext()) {
				Role role = roleIt.next();
				List<ButtonPrivDetail> buttoPrivDetails = buttonPrivDetailDao.findNoRightPrivDetail(""+role.getId(), searchId);
				for(ButtonPrivDetail searchButtoPrivDetail : buttoPrivDetails){
					String suid = searchButtoPrivDetail.getButtonId();
					if(map.get(suid)==null){
						map.put(suid, 1);
					}else{
						map.put(suid, map.get(suid)+1);
					}
//					norightUrl.add(searchButtoPrivDetail.getSearchURLId());
				}
			}
			Iterator<Entry<String,Integer>> ite = map.entrySet().iterator();
			while(ite.hasNext()){
				Entry<String,Integer> entry = ite.next();
				int value = entry.getValue();
				if(value!=roleSize){
					ite.remove();
				}
			}
			Set<String> norightUrl = new HashSet<String>(map.keySet());
			List<ButtonPrivUserDetail> buttoPrivDetails = buttonPrivUserDetailDao.findNoRightPrivDetail(""+userId, searchId);
			for(ButtonPrivUserDetail searchButtonPrivUserDetail : buttoPrivDetails){
				norightUrl.add(searchButtonPrivUserDetail.getButtonId());
			}
			Criteria criteria =this.getCriteria();
			criteria.add(Restrictions.eq("search.searchId", searchId));
			if(norightUrl!=null&&norightUrl.size()>0){
				criteria.add(Restrictions.not(Restrictions.in("searchUrlId", norightUrl)));
			}
			String herpType = ContextUtil.herpType;
			criteria.add(Restrictions.or(Restrictions.eq("herpType", herpType), Restrictions.or(Restrictions.eq("herpType", ""), Restrictions.isNull("herpType"))));
			criteria.addOrder(Order.asc("oorder"));
			List list = criteria.list();
			SearchUrl[] sis = new SearchUrl[0];
	        if ( list.size() > 0 ) {
	            sis = new SearchUrl[list.size()];
	            list.toArray( sis );
	        }
	        return sis;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
