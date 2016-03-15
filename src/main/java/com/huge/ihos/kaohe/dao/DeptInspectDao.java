package com.huge.ihos.kaohe.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.kaohe.model.DeptInspect;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptInspect table.
 */
public interface DeptInspectDao extends GenericDao<DeptInspect, Long> {
	public JQueryPager getDeptInspectCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	
	public List<DeptInspect> templIsExis(String period,String inspectTemplId); 

    /************************** 初审begin **************************************/
    public int getShouldConfirmedInspectCount( String deptId, String period );

    public int getConfirmedInstectCount( String deptId, String period );

    public int getShouldConfirmdItemCount( String deptId, String period );

    public int getConfirmedItemCount( String deptId, String period );

    public void checkInspects( Long[] inspectIds );

    public void saveCheckRemark( Long inspectId, String remark );

    public void rejectFromCheck( String deptId, String period, Long[] kpiid );

    List getShouldCheckItemList( String deptId, String period );
    public JQueryPager getDeptInspectCheckCriteria( final JQueryPager paginatedList, List<PropertyFilter> filters, String deptId, String period ,Long kpiId);
    /************************** 初审end **************************************/

    int getInspectCountByPeriodAndDeptAndState( String period, String deptId, int state );

    public void delByTemplAndPeriod(String period,String inspectTemplId); 
    
    public List<DeptInspect> findByInspectBSC(String InspectBSCId,String period);
    
    public JQueryPager findByQuery(JQueryPager pagedRequests,String checkPeriodFrom,String checkPeriod,String inspectTemplId,String InspectBSCId,String inspectDept,String targetDept,Integer stateLe,Integer stateEq,Integer stateGe,String groupBy,String inspectTemplName,Long kpiId,String periodType,String jjdepttype,String type,String orderBy);
    
    public JQueryPager findTargetDeptState(HashMap<String,Object> conditionMap,String groupBy,JQueryPager pagedRequests);

	public List<DeptInspect> getInspectByDeptAndPeriod(String departmentId,
			String checkPeriod,String currentDeptId);
}