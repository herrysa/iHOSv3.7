package com.huge.ihos.system.systemManager.user.dao.hibernate;

import java.util.List;
import java.util.Map;

import javax.persistence.Table;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.SpringContextHelper;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler</a> Extended to
 *         implement Acegi UserDetailsService interface by David Carter
 *         david@carter.net Modified by <a href="mailto:bwnoll@gmail.com">Bryan
 *         Noll</a> to work with the new BaseDaoHibernate implementation that
 *         uses generics.
 */
@Repository( "userDao" )
public class UserDaoHibernate
    extends GenericDaoHibernate<User, Long>
    implements UserDao, UserDetailsService {

    /**
     * Constructor that sets the entity to User.class.
     */
    public UserDaoHibernate() {
        super( User.class );
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "unchecked" )
    public List<User> getUsers() {
        return getHibernateTemplate().find( "from User u order by upper(u.username)" );
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser( User user ) {
        if ( log.isDebugEnabled() ) {
            log.debug( "user's id: " + user.getId() );
        }
        getHibernateTemplate().clear();
        //getHibernateTemplate().merge(user);
        getHibernateTemplate().saveOrUpdate( user );
        // necessary to throw a DataIntegrityViolation and catch it in
        // UserManager
        getHibernateTemplate().flush();
        return user;
    }

    public User modifyPwd( User user ) {
        if ( log.isDebugEnabled() ) {
            log.debug( "user's id: " + user.getId() );
        }
        getHibernateTemplate().bulkUpdate( "update User u set u.password='" + user.getPassword() + "' where u.id = " + user.getId() );

        return user;
    }

    /**
     * Overridden simply to call the saveUser method. This is happenening
     * because saveUser flushes the session and saveObject of BaseDaoHibernate
     * does not.
     * 
     * @param user
     *            the user to save
     * @return the modified user (with a primary key set if they're new)
     */
    @Override
    public User save( User user ) {
        return this.saveUser( user );
    }

    /**
     * {@inheritDoc}
     */
    public UserDetails loadUserByUsername( String username )
        throws UsernameNotFoundException {
        List users = getHibernateTemplate().find( "from User where username=?", username );
        if ( users == null || users.isEmpty() ) {
            throw new UsernameNotFoundException( "user '" + username + "' not found..." );
        }
        else {
            return (UserDetails) users.get( 0 );
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getUserPassword( String username ) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate( SessionFactoryUtils.getDataSource( getSessionFactory() ) );
        JdbcTemplate jdbcTemplate = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" )  );
        Table table = AnnotationUtils.findAnnotation( User.class, Table.class );
        return jdbcTemplate.queryForObject( "select password from " + table.name() + " where username=?", String.class, username );

    }

    public JQueryPager getUserCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                // paginatedList.setSortCriterion("id");
                paginatedList.setSortCriterion( null );

            // TODO create a callback like this:HibernateCallback callback = new
            // UserByParentCallBack(paginatedList, User.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, User.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getUserCriteria", e );
            return paginatedList;
        }
    }

    public List searchDictionary( String entityName, String str ) {
        return super.searchDictionary( entityName, str );
    }

    public List getUsersWithNewSession( Session session ) {

        Transaction tx = session.beginTransaction();
        tx.begin();
        Query query = session.createQuery( "from User u" );
        List<User> userList = query.list();
        tx.commit();
        //session.close();
        return userList;

    }

    public Session openSession() {
        Session session = super.getHibernateTemplate().getSessionFactory().openSession();
        return session;
    }

    public void closeSession( Session session ) {
        session.flush();
        session.clear();
        session.close();
    }

	@Override
	public int getUserCountByRooId(String rootId) {
		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery( "SELECT dbo.func_getUserCount(?)" );
		query.setString( 0, rootId );
        List list = query.list();
        if(list!=null){
        	return Integer.parseInt(list.get(0).toString());
        }else{
        	return 0;
        }
	}

	@Override
	public List getUserByRoleName(String roleName) {
		Criteria criteria = this.getCriteria();
		criteria.createAlias("roles", "roles");
		criteria.add(Restrictions.eq("roles.name", roleName));
		criteria.setProjection(Projections.groupProperty("id"));
		return criteria.list();
	}
	
	@Override
	public String getDateBaseName() {
		String sql = "select db_name() as databasename";
		List rs = getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
		return rs.get(0).toString();
	}

	@Override
	public List<User> getUsersByDept(Department department) {
		Criteria criteria = this.getCriteria();
		criteria.createAlias("person", "person");
		criteria.add(Restrictions.eq("person.department", department));
		List<User> users = criteria.list();
		return users;
	}

	@Override
	public List<User> getUsersByRole(Role role) {
		Criteria criteria = this.getCriteria();
		criteria.createAlias("roles", "roles");
		criteria.add(Restrictions.eq("roles.id", role.getId()));
		List<User> users = criteria.list();
		return users;
	}

}
