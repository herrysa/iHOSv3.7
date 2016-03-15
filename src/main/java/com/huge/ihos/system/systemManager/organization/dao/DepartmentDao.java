package com.huge.ihos.system.systemManager.organization.dao;

import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the Department
 * table.
 */
public interface DepartmentDao
    extends GenericDao<Department, String> {

    public JQueryPager getDepartmentCriteria( final JQueryPager paginatedList );

    public List getAllRoot();

    public List getAllSubByParent( String parentId );

    /**
     * 得到所有部门
     * @return
     */
    public List getAllDept();
    
    public List getAllDeptTypeName( String typeName ) ;

    public List getAllDeptByQuickSelect( String quickSelect );

    /*public void updateCnCodeById(String id,String cnCode);*/

    public Map<String, Object> getAppManagerCriteriaWithSearch( final JQueryPager paginatedList, final Class object, List<PropertyFilter> filters );

    public List<Department> getByJjDept(Department dept);

	public Department getDeptByName(String deptName);
	public boolean isInUse(String departmentId);

	public boolean hasChildren(String deptCode);
	/**
     * 同步单位后停用未同步到的单位
     * @param snapCode
     */
    public void disableOrgAfterSync(String snapCode);
    /**
     * 得到某单位下的所有部门
     * 
     */
    public List<Department> getAllDeptByOrgCode(String orgCodes,String branchCodes);
}
