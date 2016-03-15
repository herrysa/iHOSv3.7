package com.huge.ihos.system.systemManager.role.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.search.model.SearchEntityCluster;
import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.webapp.pagers.JQueryPager;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 * 
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 */
@Repository( "roleDao" )
public class RoleDaoHibernate
    extends GenericDaoHibernate<Role, Long>
    implements RoleDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDaoHibernate() {
        super( Role.class );
    }

    /**
     * {@inheritDoc}
     */
    public Role getRoleByName( String rolename ) {
        List roles = getHibernateTemplate().find( "from Role where name=?", rolename );
        if ( roles.isEmpty() ) {
            return null;
        }
        else {
            return (Role) roles.get( 0 );
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole( String rolename ) {
        Object role = getRoleByName( rolename );
        getHibernateTemplate().delete( role );
    }

    public List getRolesWithNewSession( Session session ) {
        Transaction tx = session.beginTransaction();
        tx.begin();
        Query query = session.createQuery( "from Role r" );
        List<Role> userList = query.list();
        tx.commit();
        // session.close();
        return userList;
    }

    // @Override
    public List testHql() {
        List list = getHibernateTemplate().find( "from DataCollectionTaskDefine order by dataSourceDefine.dataSourceType.dataSourceTypeName" );
        System.out.println( list.size() );
        return list;
    }

    @Override
    public List getRoleDataPrivilege( Long roleId ) {
        String sql =
            "  select a.*  from t_searchDataPrivilege b  inner join   t_entitycluster a   on b.dataPrivilege_id=a.entityClusterId  where  b.role_id=?";
        Session ses = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query q = ses.createSQLQuery( sql ).addEntity( SearchEntityCluster.class );
        q.setLong( 0, roleId );
        List list = q.list();

        /*
         * Role role = this.get(roleId); Set sdp =
         * role.getSearchDataPrivileges(); ArrayList list = new ArrayList(sdp);
         */

        return list;
    }

    public List getDataPrivilegeOfRolesAndEntityid( Long[] roleIds, Long entityId ) {
        if ( roleIds.length < 1 ) {
            return new ArrayList( 0 );
        }
        String sql =
            "select distinct a.entityClusterId ,a.clusterLevel,a.entityId,a.expression,a.priority  from t_searchDataPrivilege b  inner join   t_entitycluster a   on b.dataPrivilege_id=a.entityClusterId  where a.entityId=? and b.role_id in ";

        String sin = "(";
        for ( int i = 0; i < roleIds.length; i++ ) {
            sin = sin + roleIds[i];
            if ( i != ( roleIds.length - 1 ) )
                sin = sin + ",";
        }
        sin = sin + ")";

        sql = sql + sin;
        Session ses = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query q = ses.createSQLQuery( sql ).addEntity( SearchEntityCluster.class );
        q.setLong( 0, entityId );
        List list = q.list();
        return list;
    }

    public JQueryPager getPagedRoleSearchDp( JQueryPager paginatedList, Long roleId ) {
        String qSql =
            "  select a.*  from t_searchDataPrivilege b  inner join   t_entitycluster a   on b.dataPrivilege_id=a.entityClusterId  where  b.role_id=?";
        String cSql =
            "  select count(*)  from t_searchDataPrivilege b  inner join   t_entitycluster a   on b.dataPrivilege_id=a.entityClusterId  where  b.role_id=?";
        Session ses = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query qq = ses.createSQLQuery( qSql ).addEntity( SearchEntityCluster.class );
        qq.setLong( 0, roleId );

        List l = qq.setFirstResult( paginatedList.getStart() ).setMaxResults( paginatedList.getPageSize() ).list();

        Query cq = ses.createSQLQuery( cSql );
        cq.setLong( 0, roleId );
        int c = ( (Integer) cq.list().get( 0 ) ).intValue();
        paginatedList.setList( l );
        paginatedList.setTotalNumberOfRows( c );
        return paginatedList;
    }

    public JQueryPager getPagedUserSearchDp( JQueryPager paginatedList, String roleIds ) {
        String qSql =
            "  select a.*  from t_searchDataPrivilege b  inner join   t_entitycluster a   on b.dataPrivilege_id=a.entityClusterId  where  b.role_id in ("
                + roleIds + ")";
        String cSql =
            "  select count(*)  from t_searchDataPrivilege b  inner join   t_entitycluster a   on b.dataPrivilege_id=a.entityClusterId  where  b.role_id in ("
                + roleIds + ")";
        Session ses = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query qq = ses.createSQLQuery( qSql ).addEntity( SearchEntityCluster.class );
        //qq.setLong(0, roleId);

        List l = qq.setFirstResult( paginatedList.getStart() ).setMaxResults( paginatedList.getPageSize() ).list();

        Query cq = ses.createSQLQuery( cSql );
        //cq.setLong(0, roleId);
        int c = ( (Integer) cq.list().get( 0 ) ).intValue();
        paginatedList.setList( l );
        paginatedList.setTotalNumberOfRows( c );
        return paginatedList;
    }

    public void saveRoleDataPrivilege( Long roleId, Long clusterId ) {
        if ( !exitsRoleDataPrivilege( roleId, clusterId ) ) {
            String sql = "INSERT INTO t_searchDataPrivilege (role_id,dataPrivilege_id ) values(?,?)";
            Session ses = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
            Query qq = ses.createSQLQuery( sql );
            qq.setLong( 0, roleId );
            qq.setLong( 1, clusterId );
            qq.executeUpdate();
        }
    }

    public void removeRoleDataPrivilege( Long roleId, Long clusterId ) {
        String sql = "delete   t_searchDataPrivilege where role_id=? and dataPrivilege_id=?";
        Session ses = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query qq = ses.createSQLQuery( sql );
        qq.setLong( 0, roleId );
        qq.setLong( 1, clusterId );
        qq.executeUpdate();
    }

    private boolean exitsRoleDataPrivilege( Long roleId, Long clusterId ) {
        String sql = "select count(*) from  t_searchDataPrivilege where role_id=? and dataPrivilege_id=?";
        Session ses = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query qq = ses.createSQLQuery( sql );
        qq.setLong( 0, roleId );
        qq.setLong( 1, clusterId );

        int c = ( (Integer) qq.list().get( 0 ) ).intValue();
        if ( c > 0 )
            return true;
        else
            return false;

    }
}
