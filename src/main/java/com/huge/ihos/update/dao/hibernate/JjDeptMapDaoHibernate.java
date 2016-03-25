package com.huge.ihos.update.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.update.dao.JjDeptMapDao;
import com.huge.ihos.update.model.JjDeptMap;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("jjDeptMapDao")
public class JjDeptMapDaoHibernate extends GenericDaoHibernate<JjDeptMap, Integer> implements JjDeptMapDao {
	@Autowired
	private DepartmentDao departmentDao;

    public JjDeptMapDaoHibernate() {
        super(JjDeptMap.class);
    }
    
    public JQueryPager getJjDeptMapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, JjDeptMap.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getJjDeptMapCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<Department> getByOperatorId(String personId) {
		String[] deptIds=null;
		try {
			deptIds=getByDeptIds(personId);
		} catch (Exception e) {
			return null;
		}
		List<Department> listDepartment = departmentDao.get(deptIds);
		return listDepartment;
	}
	public String[] getByDeptIds(String personId) {
		String sql="select deptIds from jj_t_jjdeptmap where personId=?";
		Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
		query.setString(0, personId);
		List list = query.list();
		
		String[] deptIds=new String[0];//=list.get(0).toString().split(",");
		if(list.size()>0)
		 deptIds=list.get(0).toString().split(",");
		/*else
		    throw new BusinessException("奖金分管科室为空.");
		*/
		else{
		    deptIds = new String[1];
		    deptIds[0]=personId;
		    
		}
		return deptIds;
		}
}
