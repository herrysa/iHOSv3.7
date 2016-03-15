package com.huge.ihos.hr.hrOperLog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrOperLog.dao.HrOperLogDao;
import com.huge.ihos.hr.hrOperLog.model.HrLogColumnInfo;
import com.huge.ihos.hr.hrOperLog.model.HrLogEntityInfo;
import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.hr.hrOperLog.service.HrOperLogManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.HibernateConfigurationUtil;
import com.huge.webapp.util.PropertyFilter;

@Service("hrOperLogManager")
public class HrOperLogManagerImpl extends GenericManagerImpl<HrOperLog, String> implements HrOperLogManager {
    private HrOperLogDao hrOperLogDao;

    @Autowired
    public HrOperLogManagerImpl(HrOperLogDao hrOperLogDao) {
        super(hrOperLogDao);
        this.hrOperLogDao = hrOperLogDao;
    }
    
    public JQueryPager getHrOperLogCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrOperLogDao.getHrOperLogCriteria(paginatedList,filters);
	}

	@Override
	public <T> void addHrOperLog(HrLogEntityInfo<T> entityInfo,Person operPerson, Date operTime) {
		operTime=new Date();
		String operTable = HibernateConfigurationUtil.getTableName(entityInfo.getEntityClass());
		HrOperLog hrOperLog = null;
		List<HrLogColumnInfo> columnInfoList = entityInfo.getColumnInfoList();
		if(columnInfoList!=null && !columnInfoList.isEmpty()){
			for(HrLogColumnInfo column:columnInfoList){
				hrOperLog = new HrOperLog();
				hrOperLog.setOperTable(operTable);
				hrOperLog.setRecordCode(entityInfo.getMainCode());
				hrOperLog.setOrgCode(entityInfo.getOrgCode());
				hrOperLog.setOperType(entityInfo.getOperType());
				hrOperLog.setOperPerson(operPerson);
				hrOperLog.setOperTime(operTime);
				
				hrOperLog.setColumnName(column.getColumnName());
				hrOperLog.setOldValue(""+column.getOldValue());
				hrOperLog.setNewValue(""+column.getNewValue());
				this.save(hrOperLog);
			}
		}else{
			hrOperLog = new HrOperLog();
			hrOperLog.setOperTable(operTable);
			hrOperLog.setRecordCode(entityInfo.getMainCode());
			hrOperLog.setOrgCode(entityInfo.getOrgCode());
			hrOperLog.setOperType(entityInfo.getOperType());
			hrOperLog.setOperPerson(operPerson);
			hrOperLog.setOperTime(operTime);
			this.save(hrOperLog);
		}
	}
	@Override
	public <T> void addHrOperLog(List<HrLogEntityInfo<T>> entityInfoList,Person operPerson, Date operTime) {
		if(entityInfoList!=null && !entityInfoList.isEmpty()){
			for(HrLogEntityInfo<T> entityInfo:entityInfoList){
				this.addHrOperLog(entityInfo, operPerson, operTime);
			}
		}
	}
}