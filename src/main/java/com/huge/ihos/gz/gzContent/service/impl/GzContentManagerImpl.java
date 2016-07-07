package com.huge.ihos.gz.gzContent.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.gz.gzContent.dao.GzContentDao;
import com.huge.ihos.gz.gzContent.model.GzContent;
import com.huge.ihos.gz.gzContent.service.GzContentManager;
import com.huge.ihos.gz.gzItem.dao.GzItemDao;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.MonthPerson;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzContentManager")
public class GzContentManagerImpl extends GenericManagerImpl<GzContent, String> implements GzContentManager {
    private GzContentDao gzContentDao;
    private GzItemDao gzItemDao;

    @Autowired
    public GzContentManagerImpl(GzContentDao gzContentDao) {
        super(gzContentDao);
        this.gzContentDao = gzContentDao;
    }
    @Autowired
    public void setGzItemDao(GzItemDao gzItemDao) {
		this.gzItemDao = gzItemDao;
	}
    public JQueryPager getGzContentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzContentDao.getGzContentCriteria(paginatedList,filters);
	}
    @Override
    public List<Map<String,Object>> getGzContentGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList){
    	return gzContentDao.getGzContentGridData(columns,lastPeriod,sqlFilterList,sqlOrderList);
    }
    @Override
    public List<String> gzContentInherit(List<MonthPerson> monthPersons,String curPeriod,String curIssueNumber,String filterPeriod,String filterIssueNumber,Boolean allInheritType,List<GzType> gzTypes,Boolean coverInherit,Boolean refreshInherit,String deptIds,Date operDate,String operPerson,String gzContentNeedCheck){
    	if(OtherUtil.measureNull(gzTypes)||gzTypes.isEmpty()){
    		return null;
    	}
    	List<String> gzTypeIdList = new ArrayList<String>();
    	String gzTypeIdStr = "";
    	for(GzType gzType:gzTypes){
    		String gzTypeId = gzType.getGzTypeId();
    		gzTypeIdList.add(gzTypeId);
    		gzTypeIdStr += gzTypeId + ",";
    	}
    	gzTypeIdStr = OtherUtil.subStrEnd(gzTypeIdStr, ",");
    	
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_period",curPeriod));
		filters.add(new PropertyFilter("EQI_issueNumber",curIssueNumber));
		List<GzContent> gzContents = gzContentDao.getByFilters(filters);
		Map<String,GzContent> gzContentMap = new HashMap<String, GzContent>();
		if(OtherUtil.measureNotNull(gzContents)&&!gzContents.isEmpty()){
			for(GzContent gzContent:gzContents){
				gzContentMap.put(gzContent.getPersonId(), gzContent);
			}
		}
		gzContents = new ArrayList<GzContent>();
		String itemCode = "personId,deptId,empType,orgCode,personName,deptCode,personCode,"
				+ "deptName,orgName,branchCode,branchName";
		List<String> sqlList = new ArrayList<String>();
		for(GzType gzType:gzTypes){
    		String gzTypeId = gzType.getGzTypeId();
    		filters.clear();
    		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
    		if(!allInheritType){
    			filters.add(new PropertyFilter("EQB_isInherit","1"));
    		}
    		filters.add(new PropertyFilter("EQB_disabled","0"));
    		filters.add(new PropertyFilter("NIS_itemCode",itemCode));
    		List<GzItem> gzItems = gzItemDao.getByFilters(filters);
    		String sqlTemp = "";
    		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
    			for(GzItem gzItem:gzItems){
    				String itemCodeTemp = gzItem.getItemCode();
    				if(coverInherit){
    					sqlTemp += "gz_gzContent." + itemCodeTemp +"=" +"b."+itemCodeTemp+",";
    				}else{
    					sqlTemp += "gz_gzContent." + itemCodeTemp +"=" +"(CASE WHEN gz_gzContent."+itemCodeTemp+" is NOT NULL THEN gz_gzContent."+itemCodeTemp+" ELSE b."+itemCodeTemp+" END),";
    				}
    			}
    			sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
    			sqlTemp = " UPDATE gz_gzContent SET " + sqlTemp;
    			sqlTemp += " FROM gz_gzContent,gz_gzContent b ";
    			sqlTemp += " WHERE gz_gzContent.personId = b.personId ";
    			sqlTemp += " AND gz_gzContent.gzTypeId = b.gzTypeId ";
    			sqlTemp += " AND gz_gzContent.period = '"+curPeriod+"' ";
    			sqlTemp += " AND gz_gzContent.issueNumber = '"+curIssueNumber+"' ";
    			sqlTemp += " AND b.period = '"+filterPeriod+"' ";
    			sqlTemp += " AND b.issueNumber = '"+filterIssueNumber+"' ";
    			sqlTemp += " AND gz_gzContent.gzTypeId = '"+gzTypeId+"' ";
    			System.out.println(sqlTemp);
    			sqlList.add(sqlTemp);
    		}
    		for(MonthPerson person:monthPersons){
    			String personId = person.getPersonId();
    			String mapKey = personId;
    			String gzId = null;
    			if(refreshInherit){
    				if(gzContentMap.containsKey(mapKey)){
    					gzId = gzContentMap.get(mapKey).getGzId();
    				}
    			}else if(gzContentMap.containsKey(mapKey)){
    				continue;
    			}
    			GzContent gzContentTemp = new GzContent();
    			gzContentTemp.setGzId(gzId);
    			gzContentTemp.setGzType(gzTypeId);
    			gzContentTemp.setPeriod(curPeriod);
    			gzContentTemp.setIssueNumber(Integer.parseInt(curIssueNumber));
    			gzContentTemp.setPersonId(person.getPersonId());
    			gzContentTemp.setPersonCode(person.getPersonCode());
    			gzContentTemp.setPersonName(person.getName());
//    			gzContentTemp.setOrgCode(person.getOrgCode());
    			Department department = person.getDepartment();
				if(department!=null){
					gzContentTemp.setDeptId(department.getDepartmentId());
					gzContentTemp.setDeptCode(department.getDeptCode());
					gzContentTemp.setDeptName(department.getName());
					Org org = department.getOrg();
					if(org!=null){
						gzContentTemp.setOrgName(org.getOrgname());
	    				gzContentTemp.setOrgCode(org.getOrgCode());
					}
					Branch branch = person.getBranch();
					if(branch!=null){
						gzContentTemp.setBranchCode(branch.getBranchCode());
						gzContentTemp.setBranchName(branch.getBranchName());
					}
				}
    			/*if(OtherUtil.measureNotNull(person.getDepartment().getOrg())&&OtherUtil.measureNotNull(person.getDepartment().getOrg().getOrgCode())){
    				gzContentTemp.setOrgName(person.getDepartment().getOrg().getOrgname());
    				gzContentTemp.setOrgCode(person.getDepartment().getOrg().getOrgCode());
    			}else{
    				gzContentTemp.setOrgName("");
    			}*/
    			if("0".equals(gzContentNeedCheck)){
    				gzContentTemp.setStatus("1");
    			}else{
    				gzContentTemp.setStatus("0");
    			}
				gzContentTemp.setMakeDate(operDate);
				gzContentTemp.setMaker(operPerson);
				gzContentTemp.setEmpType(person.getStatus());
    			gzContents.add(gzContentTemp);
    		}
    	}
		this.saveAll(gzContents);
		return sqlList;
    }
    public Boolean getGzContentChecked(String period,String issueNumber){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_period",period));
		filters.add(new PropertyFilter("EQI_issueNumber",issueNumber));
		filters.add(new PropertyFilter("EQS_status","0"));
		List<GzContent> gzContents = gzContentDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzContents)&&!gzContents.isEmpty()){
			return false;
		}else{
			return true;
		}
    }
    public Boolean getGzContentChecked(String period,String issueNumber,String gzType,String branchCode,String status){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_period",period));
		filters.add(new PropertyFilter("EQI_issueNumber",issueNumber));
		filters.add(new PropertyFilter("EQS_gzType",gzType));
		filters.add(new PropertyFilter("EQS_branchCode",branchCode));
		if(status != null && !"".equals(status)) {
			filters.add(new PropertyFilter("EQS_status",status));
		}
		List<GzContent> gzContents = gzContentDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzContents)&&!gzContents.isEmpty()){
			return false;
		}else{
			return true;
		}
    }
    @Override
    public Map<String, Integer> getPersonCountMap(String id,String currPeriod,String gzTypeId) {
    	return this.gzContentDao.getPersonCountMap(id,currPeriod,gzTypeId);
    }
}