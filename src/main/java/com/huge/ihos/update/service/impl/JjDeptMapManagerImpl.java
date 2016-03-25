package com.huge.ihos.update.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.update.dao.JjDeptMapDao;
import com.huge.ihos.update.model.JjDeptMap;
import com.huge.ihos.update.service.JjDeptMapManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "jjDeptMapManager" )
public class JjDeptMapManagerImpl
    extends GenericManagerImpl<JjDeptMap, Integer>
    implements JjDeptMapManager {
    private JjDeptMapDao jjDeptMapDao;

    @Autowired
    public JjDeptMapManagerImpl( JjDeptMapDao jjDeptMapDao ) {
        super( jjDeptMapDao );
        this.jjDeptMapDao = jjDeptMapDao;
    }

    public JQueryPager getJjDeptMapCriteria( JQueryPager paginatedList, List<PropertyFilter> filters ) {
        return jjDeptMapDao.getJjDeptMapCriteria( paginatedList, filters );
    }

    @Override
    public List<Department> getByOperatorId( String personId ) {
        return jjDeptMapDao.getByOperatorId( personId );
    }

    @Override
    public String getDeptIdInS( String personId ) {
        String[] deptIds = jjDeptMapDao.getByDeptIds( personId );
        String deptIdIn = "(";
        if ( deptIds.length > 0 ) {

            for ( String deptId : deptIds ) {
                deptIdIn += "'" + deptId + "',";
            }
            deptIdIn = OtherUtil.subStrEnd( deptIdIn, "," );
        }
        deptIdIn += ")";

        return deptIdIn;
    }

    @Override
    public String[] getDeptIdsByPerson( String personId ) {
        String[] deptIds = jjDeptMapDao.getByDeptIds( personId );
        return deptIds;
    }

	@Override
	public List<Department> getAllDept() {
		List<JjDeptMap> jjDeptMaps = jjDeptMapDao.getAllExceptDisable();
		List<Department> depts = null;
		if(jjDeptMaps!=null && jjDeptMaps.size()>0){
			depts = new ArrayList<Department>();
			for(JjDeptMap jjDeptMap:jjDeptMaps){
				depts.add(jjDeptMap.getDeptId());
			}
		}
		return depts;
	}
    
    
}