package com.huge.ihos.system.systemManager.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.dao.LookupDao;
import com.huge.ihos.system.systemManager.security.service.LookupManager;
import com.huge.model.LabelValue;

/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service( "lookupManager" )
public class LookupManagerImpl
    implements LookupManager {
    @Autowired
    LookupDao dao;

    public LookupDao getDao() {
        return dao;
    }

    public void setDao( LookupDao dao ) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for ( Role role1 : roles ) {
        	if(!role1.getName().equals("ROLE_ADMIN")){
        		list.add( new LabelValue( role1.getChName(), role1.getName() ) );
        	}
        }

        return list;
    }
}
