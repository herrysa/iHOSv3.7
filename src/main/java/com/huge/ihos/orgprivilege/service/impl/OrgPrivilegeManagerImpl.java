package com.huge.ihos.orgprivilege.service.impl;

import java.util.List;
import com.huge.ihos.orgprivilege.dao.OrgPrivilegeDao;
import com.huge.ihos.orgprivilege.model.OrgPrivilege;
import com.huge.ihos.orgprivilege.service.OrgPrivilegeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("orgPrivilegeManager")
public class OrgPrivilegeManagerImpl extends GenericManagerImpl<OrgPrivilege, String> implements OrgPrivilegeManager {
    private OrgPrivilegeDao orgPrivilegeDao;

    @Autowired
    public OrgPrivilegeManagerImpl(OrgPrivilegeDao orgPrivilegeDao) {
        super(orgPrivilegeDao);
        this.orgPrivilegeDao = orgPrivilegeDao;
    }
    
    public JQueryPager getOrgPrivilegeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return orgPrivilegeDao.getOrgPrivilegeCriteria(paginatedList,filters);
	}

	@Override
	public String getOrgPrivilegeToStr(String personId) {
		String[] orgIds = orgPrivilegeDao.getByOperatorId(personId);
        String sqlStr = "(";
        if ( orgIds.length > 0 ) {

            for ( String orgId : orgIds ) {
            	sqlStr += "'" + orgId + "',";
            }
            sqlStr = OtherUtil.subStrEnd( sqlStr, "," );
        }
        sqlStr += ")";

        return sqlStr;
	}
}