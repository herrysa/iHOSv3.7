package com.huge.ihos.system.configuration.code.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.bdinfo.dao.BdInfoDao;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.code.dao.CodeDao;
import com.huge.ihos.system.configuration.code.model.Code;
import com.huge.ihos.system.configuration.code.service.CodeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("codeManager")
public class CodeManagerImpl extends GenericManagerImpl<Code, String> implements CodeManager {
    private CodeDao codeDao;
    @Autowired
    private BdInfoDao bdInfoDao;

    @Autowired
    public CodeManagerImpl(CodeDao codeDao) {
        super(codeDao);
        this.codeDao = codeDao;
    }
    public JQueryPager getCodeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return codeDao.getCodeCriteria(paginatedList,filters);
	}
	@Override
	public List isHaveData(Code code) {
		return codeDao.isHaveData(code);
	}

	@Override
	public String getCodeRule(Class clazz, HashMap<String, String> environment) {
		BdInfo bdInfo = bdInfoDao.getBdInfoByClazz(clazz);
		if(bdInfo == null){
			return "";
		}
		Code code = codeDao.getCodeByBdInfo(bdInfo,environment);
		if(code == null){
			return "";
		}
		return code.getCodeRule();
	}
	public CodeDao getCodeDao() {
		return codeDao;
	}
	public void setCodeDao(CodeDao codeDao) {
		this.codeDao = codeDao;
	}
	public BdInfoDao getBdInfoDao() {
		return bdInfoDao;
	}
	public void setBdInfoDao(BdInfoDao bdInfoDao) {
		this.bdInfoDao = bdInfoDao;
	}
	
}