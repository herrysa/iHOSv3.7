package com.huge.ihos.kaohe.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.huge.ihos.kaohe.model.DeptInspect;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptInspectManager extends GenericManager<DeptInspect, Long> {
     public JQueryPager getDeptInspectCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<DeptInspect> templIsExis(String period,String inspectTemplId); 
     
     
     /**************************初审begin**************************************/
     public int getShouldConfirmInspectCount(String deptId,String period);
     public int getConfirmedInstectCount(String deptId,String period);
     public int getShouldConfirmItemCount(String deptId,String period);
     public int getConfirmedItemCount(String deptId,String period);
     public void checkInspects(Long[] inspectIds);
     public void saveCheckRemark(Long inspectId,String remark);
     public void rejectFromCheck(String deptId,Long[] inspectId) ;
     
     /**
      * 考评主任对本部门负责的打分情况进行初审.
      * 按照指标进行列表显示
      * 
      * 当前期间,本部门负责考评
      * 
      * @param paginatedList
      * @param filters
      * @return
      */
     public JQueryPager getDeptInspectCheckCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters,String deptId,String period);
     /**************************初审end**************************************/
     
     
    /* public JQueryPager getDeptInspectAuditCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);*/
     public void delByTemplAndPeriod(String period,String inspectTemplId);
     
     public List<DeptInspect> findByInspectBSC(String InspectBSCId,String period);
     
     public JQueryPager findByQuery(JQueryPager pagedRequests, String checkPeriodFrom,String checkPeriod,
 			String inspectTemplId, String InspectBSCId,String inspectDept,String targetDept,Integer stateLe,Integer stateEq,Integer stateGe,String groupBy,String inspectTemplName,Long kpiId,String periodType,String jjdepttype,String type,String orderBy);
     public JQueryPager findTargetDeptState(HashMap<String,Object> conditionMap,String groupBy,JQueryPager pagedRequests);

	 public void updateIspectState(String departmentId, String checkPeriod,
			Person person, Date currentTime, String scoreType,int state);
}