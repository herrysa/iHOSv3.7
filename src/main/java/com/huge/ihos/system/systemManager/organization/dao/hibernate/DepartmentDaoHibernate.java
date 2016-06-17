package com.huge.ihos.system.systemManager.organization.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository( "departmentDao" )
public class DepartmentDaoHibernate
    extends GenericDaoHibernate<Department, String>
    implements DepartmentDao {

    public DepartmentDaoHibernate() {
        super( Department.class );
    }

    public JQueryPager getDepartmentCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                // paginatedList.setSortCriterion("departmentId");
                paginatedList.setSortCriterion( null );

            // TODO create a callback like this:HibernateCallback callback = new
            // DepartmentByParentCallBack(paginatedList, Department.class,
            // parentId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, Department.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDepartmentCriteria", e );
            return paginatedList;
        }
    }

    /*
     * class DepartmentByParentCallBack extends JqueryPagerHibernateCallBack {
     * Long parentId;
     * 
     * DepartmentByParentCallBack(final JQueryPager paginatedList, final Class
     * object, Long parentId) { super(paginatedList, object); this.parentId =
     * parentId; }
     * 
     * @Override public Criteria getCustomCriterion(Criteria criteria) {
     * Restrictions.eq("department.departmentId", parentId); return criteria; }
     * }
     */

    public List getAllRoot() {
        String hql = "from Department d where d.parentDept=null";
        List roots = this.getHibernateTemplate().find( hql );

        return roots;
    }

    public List getAllSubByParent( String parentId ) {
        String hql = "from Department d where d.parentDept.departmentId=?";
        List subs = this.getHibernateTemplate().find( hql, parentId );
        return subs;

    }

    public List getAllDept() {
        String hql = "from Department d where d.disabled=0 and d.leaf=1  and d.deptCode<>'XT' order by d.internalCode asc";
        List list = this.getHibernateTemplate().find( hql );
        return list;
    }

    public List getAllDeptByQuickSelect( String quickSelect ) {
        String hql =
            " from  Department d where d.disabled=0 and leaf=1 and( d.name like '%" + quickSelect + "%' or d.deptCode like '%" + quickSelect
                + "%') order by deptCode";
        List list = this.getHibernateTemplate().find( hql );
        return list;
    }
    public List getAllDeptTypeName( String typeName ) {
    	String hql =
    		" from  Department d where  d.jjDeptType = '" + typeName + "'";
    	List list = this.getHibernateTemplate().find( hql );
    	return list;
    }

    /*	public void updateCnCodeById(String id,String cnCode) {
     String hql = "update Department set cnCode = ? where id = ?";
     Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
     query.setString(0, cnCode);
     query.setString(1, id);
     query.executeUpdate();
     }*/
    public Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters ) {
        this.getHibernateTemplate().enableFilter( "exceptDepartmentFilter" ).setParameter( "exceptDepartment", "xt" );
        return super.getAppManagerCriteriaWithSearch( paginatedList, object, filters );
    }

	@Override
	public List<Department> getByJjDept(Department dept) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("jjDept", dept));
		return criteria.list();
	}

	@Override
	public Department getDeptByName(String deptName) {
		String hql = "from Department where name = '"+deptName+"'";
		List list = this.getHibernateTemplate().find(hql);
		return (Department) list.get(0);
	}

	@Override
	public boolean isInUse(String departmentId) {
		/*
		 * 检查表：t_sourcecost，t_sourcepayin，t_deptkey
		 */
		Session session = this.getSessionFactory().getCurrentSession();
		String sql_deptKey = "select checkPeriod from t_DeptKey where deptId = '"+departmentId+"'";
		SQLQuery query = session.createSQLQuery(sql_deptKey);
		List result_deptKey = query.list();
		if(result_deptKey==null || result_deptKey.size()==0){
			String hql_sourcecost = "from Sourcecost where deptartment.departmentId = ?";
			List list = this.getHibernateTemplate().find(hql_sourcecost,departmentId);
			if(list==null || list.size()==0){
				String hql_sourcepayin = "from Sourcepayin where kdDept.departmentId = ? or zxDept.departmentId=? or hlDept.departmentId =? or jzDept.departmentId =? ";
				List list2 = this.getHibernateTemplate().find(hql_sourcepayin,departmentId,departmentId,departmentId,departmentId);
				if(list2==null || list2.size()==0){
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean hasChildren(String deptCode) {
		String hql = "from Department where parentDept.departmentId= '"+deptCode+"'";
		List list = this.getHibernateTemplate().find(hql);
		if(list==null || list.size()==0){
			return false;
		}
		return true;
	}
	@Override
	public void disableOrgAfterSync(String snapCode){
		String sql="update t_department set disabled = 1 where snapCode is not NULL and snapCode > '"+snapCode+"'";
    	excuteSql(sql);
	}
	
	@Override
	public List<Department> getAllDeptByOrgCode(String orgCodes,String branchCodes) {
		if(orgCodes != null && !"".equals(orgCodes)) {
			Criteria criteria = this.getCriteria();
			criteria.add(Restrictions.eq("leaf", true));
			criteria.add(Restrictions.eq("disabled", false));
			if(orgCodes.contains(",")) {
				String[] orgCodeArr = orgCodes.split(",");
				criteria.add(Restrictions.in("orgCode", orgCodeArr));
			} else {
				if(!orgCodes.startsWith("select")&&!orgCodes.startsWith("SELECT")) {
					criteria.add(Restrictions.eq("orgCode", orgCodes));
				}
			}
			if(branchCodes != null && !"".equals(branchCodes)) {
				if(branchCodes.contains(",")) {
					String[] branchCodeArr = branchCodes.split(",");
					criteria.add(Restrictions.in("branchCode", branchCodeArr));
				} else {
					if(!branchCodes.startsWith("select")&&!branchCodes.startsWith("SELECT")) {
						criteria.add(Restrictions.eq("branchCode", branchCodes));
					}
				}
			}/* else {
				return null;
			}*/
			return criteria.list();
		} else {
			return null;
		}
	}
	
}
