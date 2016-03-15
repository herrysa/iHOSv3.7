package com.huge.ihos.system.systemManager.organization.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.organization.dao.PersonDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository( "personDao" )
public class PersonDaoHibernate
    extends GenericDaoHibernate<Person, String>
    implements PersonDao {

    public PersonDaoHibernate() {
        super( Person.class );
        /* Session session = SessionFactoryUtils.openSession(getSessionFactory());
         Filter filter = session.enableFilter("exceptPerson");
         filter.setParameter("personId", "xt");*/
    }

    public List getAllByFilter() {
        this.getHibernateTemplate().enableFilter( "exceptPersonFilter" ).setParameter( "exceptPerson", "P00001" );
        return getAll();
    }

    public JQueryPager getPersonCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                //paginatedList.setSortCriterion("personId");
                paginatedList.setSortCriterion( null );

            //TODO create a callback like this:HibernateCallback callback = new PersonByParentCallBack(paginatedList, Person.class, parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Person.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getPersonCriteria", e );
            return paginatedList;
        }
    }

    /*
    class PersonByParentCallBack extends JqueryPagerHibernateCallBack {
    	Long parentId;

    	PersonByParentCallBack(final JQueryPager paginatedList,
    			final Class object, Long parentId) {
    		super(paginatedList, object);
    		this.parentId = parentId;
    	}

    	@Override
    	public Criteria getCustomCriterion(Criteria criteria) {
    		Restrictions.eq("person.personId", parentId);
    		return criteria;
    	}
    }
     */

    public Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters ) {
        this.getHibernateTemplate().enableFilter( "exceptPersonFilter" ).setParameter( "exceptPerson", "xt" );
        return super.getAppManagerCriteriaWithSearch( paginatedList, object, filters );
    }
    
    public List getAllPerson() {
        String hql = "from Person p where p.disable=0  and p.personCode<>'XT' order by p.personCode asc";
        List list = this.getHibernateTemplate().find( hql );
        return list;
    }

	@Override
	public List<Person> getPersonsByDept(Department department) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("department", department));
		return criteria.list();
	}

	@Override
	public List<Person> getPersonsByView(String viewName, Department department) {
		String sql = "select * from "+viewName;
		if(department!=null){
			sql += " where dept_id='"+department.getDepartmentId()+"'";
		}
		SQLQuery sqlQuery = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		sqlQuery.addEntity(Person.class);
		return sqlQuery.list();
	}
	@Override
	public void disableOrgAfterSync(String snapCode){
		String sql="update t_person set disabled = 1 where snapCode is not NULL and snapCode > '"+snapCode+"'";
    	excuteSql(sql);
	}
}
