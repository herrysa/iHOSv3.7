package com.huge.ihos.pz.linkinfo.service.impl;

import java.util.List;

import com.huge.ihos.pz.linkinfo.dao.LinkInfoDao;
import com.huge.ihos.pz.linkinfo.model.LinkInfo;
import com.huge.ihos.pz.linkinfo.service.LinkInfoManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("linkInfoManager")
public class LinkInfoManagerImpl extends GenericManagerImpl<LinkInfo, String> implements LinkInfoManager {
    private LinkInfoDao linkInfoDao;

    @Autowired
    public LinkInfoManagerImpl(LinkInfoDao linkInfoDao) {
        super(linkInfoDao);
        this.linkInfoDao = linkInfoDao;
    }
    
    public JQueryPager getLinkInfoCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return linkInfoDao.getLinkInfoCriteria(paginatedList,filters);
	}
    
    @Override
    public void changeLinkInfo(String type) {
    	this.linkInfoDao.changeLinkInfo(type);
    }
    
    @Override
    public String getDataSourceId(String type) {
    	return this.linkInfoDao.getDateSourceId(type);
    }
}