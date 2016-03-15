package com.huge.ihos.system.configuration.colsetting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.colsetting.dao.ColShowDao;
import com.huge.ihos.system.configuration.colsetting.model.ColShow;
import com.huge.ihos.system.configuration.colsetting.service.ColShowManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("colShowManager")
public class ColShowManagerImpl extends GenericManagerImpl<ColShow, String> implements ColShowManager {
    private ColShowDao colShowDao;

	@Autowired
    public ColShowManagerImpl(ColShowDao colShowDao) {
        super(colShowDao);
        this.colShowDao = colShowDao;
    }
	
    public JQueryPager getColShowCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return colShowDao.getColShowCriteria(paginatedList,filters);
	}
    
    public List<ColShow> getByEntityName(String entityName){
    	return colShowDao.getByEntityName(entityName);
    }
    
    @Override
	public List<ColShow> getByTemplName(String templName,String entityName,String userId,String colshowType) {
		return colShowDao.getByTemplName(templName,entityName,userId,colshowType);
	}

	@Override
	public void delByTemplName(String templName,String entityName,String userId,String colshowType) {
		List<ColShow> colShows = colShowDao.getByTemplName(templName,entityName,userId,colshowType);
		if(colShows!=null&&colShows.size()!=0){
			this.getHibernateTemplate().deleteAll(colShows);
		}
	}

	@Override
	public List<HashMap<String,String>> getAllTempl(String entityName,String userId,String colshowType) {
		return colShowDao.getAllTempl(entityName,userId,colshowType);
	}

	@Override
	public List<ColShow> getByFirstTempl(String entityName,String userId,String colshowType) {
		List<HashMap<String, String>> templList = colShowDao.getAllTempl(entityName,userId,colshowType);
		if(templList!=null&&templList.size()!=0){
			Map<String, String> tempMap = templList.get(0);
			String templName = tempMap.get("templetName");
			return colShowDao.getByTemplName(templName,entityName,userId,colshowType);
		}else{
			return null;
			
		}
	}

	@Override
	public List<HashMap<String, String>> getDeptTempl(String entityName,
			String userIds,String colshowType) {
		return colShowDao.getDeptTempl(entityName, userIds,colshowType);
	}

	@Override
	public List<HashMap<String, String>> getRoleTempl(String entityName,
			String userIds) {
		return colShowDao.getRoleTempl(entityName, userIds);
	}

	@Override
	public List<HashMap<String, String>> getPublicTempl(String entityName,String colshowType) {
		return colShowDao.getPublicTempl(entityName,colshowType);
	}

	@Override
	public List<ColShow> getDeptTempletByName(String templName,
			String entityName, String[] userIds,String colshowType) {
		return colShowDao.getDeptTempletByName(templName, entityName, userIds,colshowType);
	}

	@Override
	public List<ColShow> getRoleTempletByName(String templName,
			String entityName, String[] userIds,String colshowType) {
		return colShowDao.getRoleTempletByName(templName, entityName, userIds,colshowType);
	}

	@Override
	public List<ColShow> getPublicTempletByName(String templName,
			String entityName, String userId,String colshowType) {
		return colShowDao.getPublicTempletByName(templName, entityName,userId,colshowType);
	}

	@Override
	public List<HashMap<String, String>> getRoleTempl(String entityName,
			String userIds, String dept_userIds,String colshowType) {
		return colShowDao.getRoleTempl( entityName,userIds,dept_userIds,colshowType);
	}
	@Override
	public ColShow getLastByTemplName(String entityName,String userId,String colshowType){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_userId",userId));
		filters.add(new PropertyFilter("EQS_entityName",entityName));
		if(OtherUtil.measureNotNull(colshowType)){
			filters.add(new PropertyFilter("EQS_colshowType",colshowType));
		}
		filters.add(new PropertyFilter("ODL_editTime",""));
		List<ColShow> colShows = this.getByFilters(filters);
		if(OtherUtil.measureNotNull(colShows)&&!colShows.isEmpty()){
			return colShows.get(0);
		}
		return null;
	}
}