package com.huge.ihos.system.systemManager.organization.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.organization.dao.KhDeptTypeDao;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "khDeptTypeManager" )
public class KhDeptTypeManagerImpl
    extends GenericManagerImpl<KhDeptType, String>
    implements KhDeptTypeManager {
	KhDeptTypeDao khDeptTypeDao;

    @Autowired
    public KhDeptTypeManagerImpl( KhDeptTypeDao khDeptTypeDao ) {
        super( khDeptTypeDao );
        this.khDeptTypeDao = khDeptTypeDao;
    }

    public JQueryPager getKhDeptTypeCriteria( JQueryPager paginatedList ) {
        return khDeptTypeDao.getKhDeptTypeCriteria( paginatedList );
    }
    @Override
    public KhDeptType getKhDeptTypeByName(String khDeptTypeName){
    	KhDeptType khDeptType=null;
    	List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_khDeptTypeName", khDeptTypeName));
    	filters.add(new PropertyFilter("EQB_disabled", "0"));
    	List<KhDeptType> khDeptTypes=khDeptTypeDao.getByFilters(filters);
    	if(khDeptTypes!=null&&khDeptTypes.size()>0){
    		khDeptType=khDeptTypes.get(0);
    	}
    	return khDeptType;
    }
}