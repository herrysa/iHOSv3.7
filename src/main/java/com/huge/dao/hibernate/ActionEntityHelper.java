package com.huge.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.huge.ihos.system.reportManager.search.dao.SearchEntityDao;
import com.huge.ihos.system.reportManager.search.model.SearchEntity;
import com.huge.ihos.system.reportManager.search.model.SearchEntityCluster;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;
import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.util.SpringContextHelper;

public class ActionEntityHelper {
    
    public static Session getSession( Session session ) {
        
        String entityName = ActionEntityThreadLocalHolder.getActionEntityName();
        String an = ActionEntityThreadLocalHolder.getExecActionName();
        
        if(entityName!=null && !entityName.trim().equalsIgnoreCase( "" ) && an!=null && !an.trim().equalsIgnoreCase( "" )){
            ActionEntityCacheMap.addAEMap( an, entityName );
        }
        
        entityName = ActionEntityCacheMap.getEntityNameByActionName( an );
        
        
        if(entityName!=null && !entityName.trim().equalsIgnoreCase( "" ))
            
        {
            SearchEntityDao dao = (SearchEntityDao) SpringContextHelper.getBean( "searchEntityDao" );
            RoleDao roleDao = (RoleDao) SpringContextHelper.getBean( "roleDao" );
            UserDao userDao = (UserDao) SpringContextHelper.getBean( "userDao" );
            
            
            
            
            SecurityContext securityContext = SecurityContextHolder.getContext();
            String username = securityContext.getAuthentication().getName();
            User user = (User) userDao.loadUserByUsername( username );
            Set<Role> roles = user.getRoles();
            Iterator<Role> roleIt = roles.iterator();
            List rid = new ArrayList();
            while ( roleIt.hasNext() ) {
                Role r = (Role) roleIt.next();
                rid.add( r.getId() );
            }
            Long[] rida = new Long[rid.size()];
            rid.toArray( rida );// 用户的roles
            
            SearchEntity ent = dao.getSearchEntityByName( entityName );
            
           // SearchEntity entity = searchEntityDao.getSearchEntityByName( entityName );// 当前查询的实体名进而查到尸体id

            List entityClusters = roleDao.getDataPrivilegeOfRolesAndEntityid( rida, ent.getEntityId() );// 查出适用的所有尸体cluster

            String exaStr = null;// 生成额为的sql条件语句
            for ( Iterator iterator = entityClusters.iterator(); iterator.hasNext(); ) {
                SearchEntityCluster object = (SearchEntityCluster) iterator.next();
                if ( exaStr == null )
                    exaStr = "(" + object.getExpression() + ")";
                else
                    exaStr = exaStr + " or (" + object.getExpression() + ")";
            }
            if ( exaStr == null )
                exaStr = " 1=1 ";

            // 更新实际查询所需要的sql
            //String mysql = cri.getSourceSql();

            //mysql = mysql + " and ( "+exaStr+" )";
            SearchUtils su = new SearchUtils();

            exaStr = su.realSQL( exaStr );
            
            
            
            
            
          //  String sql = ent.get
            
            session = session.getSessionFactory().openSession( new EntityExecInterceptor( exaStr ) );
        }
       ActionEntityThreadLocalHolder.setActionEntityName( null );//getActionEntityName();
       ActionEntityThreadLocalHolder.setExecActionName( null );
        return session;
    }

}
