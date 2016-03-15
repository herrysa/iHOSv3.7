package com.huge.ihos.system.systemManager.globalparam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.asyncHrData.service.AsyncHrDataManager;
import com.huge.ihos.system.systemManager.globalparam.dao.GlobalParamDao;
import com.huge.ihos.system.systemManager.globalparam.model.GlobalParam;
import com.huge.ihos.system.systemManager.globalparam.service.GlobalParamManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "globalParamManager" )
public class GlobalParamManagerImpl
    extends GenericManagerImpl<GlobalParam, Long>
    implements GlobalParamManager {
    GlobalParamDao globalParamDao;
    private AsyncHrDataManager asyncHrDataManager;

    @Autowired
    public GlobalParamManagerImpl( GlobalParamDao globalParamDao ) {
        super( globalParamDao );
        this.globalParamDao = globalParamDao;
    }
    @Autowired
    public void setAsyncHrDataManager(AsyncHrDataManager asyncHrDataManager) {
		this.asyncHrDataManager = asyncHrDataManager;
	}
    public JQueryPager getGlobalParamCriteria( JQueryPager paginatedList, List<PropertyFilter> filters ) {
        return globalParamDao.getGlobalParamCriteria( paginatedList, filters );
    }

    public String getGlobalParamByKey( String key ) {
        return globalParamDao.getGlobalParamByKey( key );
    }
    @Override
    public GlobalParam save(GlobalParam globalParam){
    	if(globalParam.getParamKey().equals("ansyOrgDeptPerson")){
    		Long paramId=globalParam.getParamId();
    		String paramValue=globalParam.getParamValue();
    		String oldParamValue= globalParamDao.get(paramId).getParamValue();
    		if(!paramValue.equals(oldParamValue)&&"1".equals(paramValue)){
    			String snapCode = DateUtil.getSnapCode();
    			asyncHrDataManager.asyncHrData(snapCode);
    		}
    	}
    	return super.save(globalParam);
    }
}