package com.huge.ihos.system.systemManager.organization.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.jgeppert.struts2.jquery.tree.result.TreeNode;

public interface DepartmentManager
    extends GenericManager<Department, String> {
    public JQueryPager getDepartmentCriteria( final JQueryPager paginatedList );

    public TreeNode getFullDepartmentList();

    /**
     * 得到所有部门
     * @return
     */
    public List getAllDept();

    /**
     * 快速搜索
     * @param quickSelect
     * @return
     */
    public List getAllDeptByQuickSelect( String quickSelect );
    
    public List getAllDeptTypeName( String typeName ) ;
    
    public List<Department> getByJjDept(Department dept);

	public Department getDeptByName(String deptName);
	/**
	 * 
	 * @param dept
	 * @param hrStarted 人力资源是否启用
	 * @param person 操作人
	 * @param date 操作时间
	 * @return
	 */
	public Department saveDepartment(Department dept,boolean hrStarted,Person person,Date date);
	/**
	 * 判断department是否正在使用
	 * @param departmentId
	 * @return
	 */
	public boolean isInUse(String departmentId);
	/**
	 * 检查当前科室下是否有子级
	 * @param departmentId
	 * @return true:有子级;false:无子级
	 */
	public boolean hasChildren(String departmentId);
	/**
     * 同步单位后停用未同步到的单位
     * @param snapCode
     */
    public void disableOrgAfterSync(String snapCode);
    /**
     * 获取所有下级部门
     * @param deptId
     * @return
     */
	 public List<Department> getAllDescendants(String deptId);
	 /**
	  * 获取某单位下的所有部门
	  */
	 public List<Department> getAllDeptByOrgCode(String orgCodes,String branchCodes);
	 /**
	  * 根据id获取所有部门
	  * @param deptIds
	  * @return
	  */
	 public List<Department> getAllDeptByDeptIds(String deptIds);
	 
	 public Map<String, List<Department>> getDeptMapByDeptIds(String deptIds);
}