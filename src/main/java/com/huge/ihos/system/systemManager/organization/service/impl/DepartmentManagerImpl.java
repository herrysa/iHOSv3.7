package com.huge.ihos.system.systemManager.organization.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.exinterface.ProxySynchronizeToHr;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.jgeppert.struts2.jquery.tree.result.TreeNode;

@Service( "departmentManager" )
public class DepartmentManagerImpl extends GenericManagerImpl<Department, String> implements DepartmentManager {
    DepartmentDao departmentDao;
    private PersonManager personManager;

    @Autowired
    public DepartmentManagerImpl( DepartmentDao departmentDao ) {
        super( departmentDao );
        this.departmentDao = departmentDao;
    }
    @Autowired
    public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}
    public JQueryPager getDepartmentCriteria( JQueryPager paginatedList ) {
        return departmentDao.getDepartmentCriteria( paginatedList );
    }

    @Override
    public Department save( Department object ) {
        String pid = object.getParentDept().getDepartmentId();
        if ( pid == null || pid.trim().equalsIgnoreCase( "" ) )
            object.setParentDept( null );
        else {
            Department parent = this.departmentDao.get( pid );
            parent.setLeaf( false );
            super.save( parent );
            object.setParentDept( parent );
        }

        object.setCnCode( departmentDao.getPyCodes( object.getName() ) );
        
        object = super.save( object );
        
        return object;
    }
    @Override
    public Department saveDepartment(Department dept,boolean hrStarted,Person person,Date date){
    	 if(OtherUtil.measureNotNull(dept.getParentDept()) ){
    		 String pid = dept.getParentDept().getDepartmentId();
             if ( pid == null || pid.trim().equalsIgnoreCase( "" ) )
            	 dept.setParentDept( null );
             else {
                 Department parent = this.departmentDao.get( pid );
                 parent.setLeaf( false );
                 super.save( parent );
                 dept.setParentDept( parent );
             }
    	 }
    	 String deptId = dept.getDepartmentId();
    	 if(OtherUtil.measureNotNull(deptId)){//部门保存时变更人员的orgcode,和branchcode
    		 String orgCode = dept.getOrgCode();
    		 String branchCode = dept.getBranchCode();
    		 List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    		 filters.add(new PropertyFilter("EQS_department.departmentId",deptId));
    		 List<Person> persons = personManager.getByFilters(filters);
    		 if(OtherUtil.measureNotNull(persons)&&!persons.isEmpty()){
    			 for(Person pTemp:persons){
    				 pTemp.setOrgCode(orgCode);
    				 pTemp.setBranchCode(branchCode);
    			 }
    			 personManager.saveAll(persons);
    		 }
    	 }
         dept.setCnCode( departmentDao.getPyCodes( dept.getName() ) );
         dept = super.save( dept );
         if(hrStarted){
        	ProxySynchronizeToHr proxySynchronizeToHr = new ProxySynchronizeToHr();
			proxySynchronizeToHr.syncHrDepartment(dept, person, date);
         }
         return dept;
    }
    public TreeNode getFullDepartmentList() {
        List treeNodes = new ArrayList();
        TreeNode dummyRoot = initRoot();
        List rootNodes = this.departmentDao.getAllRoot();

        for ( Iterator iterator = rootNodes.iterator(); iterator.hasNext(); ) {
            Department root = (Department) iterator.next();

            TreeNode tn = new TreeNode();
            tn.setId( root.getDepartmentId().toString() );
            tn.setTitle( root.getName() );
            tn.setState( TreeNode.NODE_STATE_CLOSED );
            tn = initSubs( tn, root );

            treeNodes.add( tn );
        }
        dummyRoot.setChildren( treeNodes );
        return dummyRoot;
    }

    private TreeNode initRoot() {
        TreeNode root = new TreeNode();
        root.setId( "Root" );
        root.setTitle( "Root" );
        root.setState( TreeNode.NODE_STATE_CLOSED );
        return root;
    }

    private TreeNode initSubs( TreeNode tn, Department dept ) {
        List subs = this.departmentDao.getAllSubByParent( dept.getDepartmentId() );
        if ( subs.size() > 0 ) {
            List subNodes = new ArrayList();
            for ( Iterator iterator = subs.iterator(); iterator.hasNext(); ) {
                Department sub = (Department) iterator.next();
                TreeNode trnode = new TreeNode();
                trnode.setId( sub.getDepartmentId().toString() );
                trnode.setTitle( sub.getName() );
                trnode.setState( TreeNode.NODE_STATE_CLOSED );
                trnode = initSubs( trnode, sub );
                subNodes.add( trnode );

            }
            tn.setChildren( subNodes );
            return tn;

        }
        else {
            return tn;

        }

    }

    public List getAllDept() {
        return departmentDao.getAllDept();
    }

    public List getAllDeptByQuickSelect( String quickSelect ) {

        return departmentDao.getAllDeptByQuickSelect( quickSelect );
    }

	@Override
	public List getAllDeptTypeName(String typeName) {
		return departmentDao.getAllDeptTypeName(typeName);
	}

	@Override
	public List<Department> getByJjDept(Department dept) {
		return departmentDao.getByJjDept(dept);
	}

	@Override
	public Department getDeptByName(String deptName) {
		return this.departmentDao.getDeptByName(deptName);
	}

	@Override
	public boolean isInUse(String departmentId) {
		return departmentDao.isInUse(departmentId);
	}

	@Override
	public boolean hasChildren(String deptCode) {
		return departmentDao.hasChildren(deptCode);
	}
	@Override
	public void disableOrgAfterSync(String snapCode){
		departmentDao.disableOrgAfterSync(snapCode);
	}
	@Override
	public List<Department> getAllDescendants(String deptId){
		List<Department> deptList=new ArrayList<Department>();
		List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
		pfs.add(new PropertyFilter("EQS_parentDept.departmentId", deptId));
		List<Department> depts = departmentDao.getByFilters(pfs);
		if (depts != null && !depts.isEmpty()) {
			deptList.addAll(depts);
			for (Department deptTemp:depts){
				List<Department> deptsTemp = getAllDescendants(deptTemp.getDepartmentId());
				deptList.addAll(deptsTemp);
			}
		}
		return deptList;
	}
	@Override
	public List<Department> getAllDeptByOrgCode(String orgCodes,String branchCodes) {
		return departmentDao.getAllDeptByOrgCode(orgCodes,branchCodes);
	}
	/*@Override
	public List<Department> getAllDeptByDeptIds(String deptIds) {
		Criteria criteria = this.departmentDao.getCriteria();
		criteria.add(Restrictions.eq("leaf", true));
		criteria.add(Restrictions.eq("disabled", false));
		criteria.add(Restrictions.ne("departmentId", "XT"));
		if(!deptIds.startsWith("SELECT") && !deptIds.startsWith("select")) {
			String[] deptIdArr = deptIds.split(",");
			criteria.add(Restrictions.in("departmentId", deptIdArr));
		}
		return criteria.list();
	}
	*/
	@Override
	public List<Department> getAllDeptByDeptIds(String deptIds) {
		List<Department> departments = new ArrayList<Department>();
		Criteria criteria = this.departmentDao.getCriteria();
		criteria.add(Restrictions.eq("disabled", false));
		criteria.add(Restrictions.ne("departmentId", "XT"));
		if(!deptIds.startsWith("SELECT") && !deptIds.startsWith("select")) {
			String[] deptIdArr = deptIds.split(",");
			criteria.add(Restrictions.in("departmentId", deptIdArr));
		}
		List<Department> deptTemps = criteria.list();
		if(deptTemps != null && !deptTemps.isEmpty()) {
			for(Department deptTemp : deptTemps) {
				if(deptTemp.getLeaf()) {
					departments.add(deptTemp);
				} else {
					List<Department> childDepts = getAllDescendants(deptTemp.getDepartmentId());
					for(Department childDept : childDepts) {
						if(childDept.getLeaf()) {
							departments.add(childDept);
						}
					}
				}
			}
		}
		return departments;
	}
	
	@Override
	public Map<String, List<Department>> getDeptMapByDeptIds(String deptIds) {
		Map<String, List<Department>> deptMap = new HashMap<String, List<Department>>();
		Criteria criteria = this.departmentDao.getCriteria();
		criteria.add(Restrictions.eq("disabled", false));
		criteria.add(Restrictions.ne("departmentId", "XT"));
		if(!deptIds.startsWith("SELECT") && !deptIds.startsWith("select")) {
			String[] deptIdArr = deptIds.split(",");
			criteria.add(Restrictions.in("departmentId", deptIdArr));
		}
		List<Department> deptTemps = criteria.list();
		if(deptTemps != null && !deptTemps.isEmpty()) {
			for(Department deptTemp : deptTemps) {
				if(deptTemp.getLeaf()) {
					List<Department> departments = new ArrayList<Department>();
					departments.add(deptTemp);
					deptMap.put(deptTemp.getDepartmentId(), departments);
				} else {
					List<Department> departments = new ArrayList<Department>();
					List<Department> childDepts = getAllDescendants(deptTemp.getDepartmentId());
					for(Department childDept : childDepts) {
						if(childDept.getLeaf()) {
							departments.add(childDept);
						}
					}
					deptMap.put(deptTemp.getDepartmentId(), departments);
				}
			}
		}
		return deptMap;
	}
}
