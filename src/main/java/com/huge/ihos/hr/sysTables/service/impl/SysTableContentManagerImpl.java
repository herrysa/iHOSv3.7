package com.huge.ihos.hr.sysTables.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.sysTables.dao.SysTableContentDao;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.hr.sysTables.service.SysTableContentManager;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("sysTableContentManager")
public class SysTableContentManagerImpl extends GenericManagerImpl<SysTableContent, String> implements SysTableContentManager {
    private SysTableContentDao sysTableContentDao;
    private BdInfoManager bdInfoManager;

    @Autowired
    public SysTableContentManagerImpl(SysTableContentDao sysTableContentDao) {
        super(sysTableContentDao);
        this.sysTableContentDao = sysTableContentDao;
    }
    @Autowired
    public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}
    public JQueryPager getSysTableContentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return sysTableContentDao.getSysTableContentCriteria(paginatedList,filters);
	}
    
    @Override
    public void createTable(String code){
    	this.sysTableContentDao.createTable(code);
    }
    
    @Override
    public void deleteTable(String[] ids){
    	for (int i = 0; i < ids.length; i++) {
    		String id = this.get(ids[i]).getId();
    		this.sysTableContentDao.deleteTable(id);		
		}
    }
    @Override
    public List<SysTableContent> getFullSysTableContent(String orgCode,String tableKindCode){
    	return this.sysTableContentDao.getFullSysTableContent(orgCode, tableKindCode);
    }
    
    @Override
    public List<SysTableContent> getTableContentByMainTable(String mainTable){
    	return this.sysTableContentDao.getTableContentByMainTable(mainTable);
    }
    @Override
    public List<BdInfo> getSingleFieldTableList(String statisticsCode){
    	return this.sysTableContentDao.getSingleFieldTableList(statisticsCode);
    }
    @Override
    public void deleteAllSubSetDataByFK(String mainTable,String foreignId){
    	this.sysTableContentDao.deleteAllSubSetDataByFK(mainTable, foreignId);
    }
    @Override
    public void saveTableContent(SysTableContent sysTableContent,Person person,Boolean isEntityIsNew){
		Date date = new Date();
		BdInfo bdInfo = null;
		String bdInfoId = sysTableContent.getBdinfo().getBdInfoId();
		if(isEntityIsNew){
			bdInfo = new BdInfo();
			bdInfo.setBdInfoId(sysTableContent.getBdinfo().getTableName());
			bdInfo.setTableName(sysTableContent.getBdinfo().getTableName());
		}else{
			bdInfo = bdInfoManager.get(bdInfoId);
		}
		bdInfo.setBdInfoName(sysTableContent.getName());
		bdInfo.setChangeDate(date);
		sysTableContent.setChangeDate(date);
    	bdInfo.setChanger(person);
    	sysTableContent.setChangeUser(person);
    	//bdInfo.setTablePkName("code");
    	bdInfo.setDisabled(sysTableContent.getDisabled());
    	bdInfo.setSn(sysTableContent.getSn());
    	bdInfo.setRemark(sysTableContent.getRemark());
    	bdInfo.setMainTable(sysTableContent.getTableKind().getMainTable());
    	bdInfo = bdInfoManager.save(bdInfo);
    	sysTableContent.setBdinfo(bdInfo);
		sysTableContent = this.save(sysTableContent);
		if(sysTableContent.getIsView()){
			return;
		}
		if(isEntityIsNew){
			this.sysTableContentDao.createTable(sysTableContent.getId());
		}
    }
    @Override
    public void deleteTableContent(String[] ida,String[] bdInfoIda){
    	for (int i = 0; i < ida.length; i++) {
    		String id = this.get(ida[i]).getId();
    		this.sysTableContentDao.deleteTable(id);		
		}
		this.remove(ida);
		this.bdInfoManager.remove(bdInfoIda);
    }
}