package com.huge.ihos.kaohe.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kaohe.dao.DeptInspectDao;
import com.huge.ihos.kaohe.model.DeptInspect;
import com.huge.ihos.kaohe.service.DeptInspectManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("deptInspectManager")
public class DeptInspectManagerImpl extends GenericManagerImpl<DeptInspect, Long> implements DeptInspectManager {
    private DeptInspectDao deptInspectDao;

    @Autowired
    public DeptInspectManagerImpl(DeptInspectDao deptInspectDao) {
        super(deptInspectDao);
        this.deptInspectDao = deptInspectDao;
    }
    
    public JQueryPager getDeptInspectCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptInspectDao.getDeptInspectCriteria(paginatedList,filters);
	}

	@Override
	public List<DeptInspect> templIsExis(String period, String inspectTemplId) {
		return deptInspectDao.templIsExis(period, inspectTemplId);
	}

    @Override
    public int getShouldConfirmInspectCount( String deptId, String period ) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getConfirmedInstectCount( String deptId, String period ) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getShouldConfirmItemCount( String deptId, String period ) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getConfirmedItemCount( String deptId, String period ) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void checkInspects( Long[] inspectIds ) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void saveCheckRemark( Long inspectId, String remark ) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void rejectFromCheck(String deptId,Long[] inspectId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public JQueryPager getDeptInspectCheckCriteria( JQueryPager paginatedList, List<PropertyFilter> filters, String deptId, String period ) {
        // TODO Auto-generated method stub
        return null;
    }

  /*  @Override
    public JQueryPager getDeptInspectAuditCriteria( JQueryPager paginatedList, List<PropertyFilter> filters ) {
        // TODO Auto-generated method stub
        return null;
    }*/

    @Override
	public void delByTemplAndPeriod(String period, String inspectTemplId) {
		deptInspectDao.delByTemplAndPeriod(period, inspectTemplId);
	}

	@Override
	public List<DeptInspect> findByInspectBSC(String InspectBSCId,String period) {
		return deptInspectDao.findByInspectBSC(InspectBSCId,period);
	}

	@Override
	public JQueryPager findByQuery(JQueryPager pagedRequests,String checkPeriodFrom,String checkPeriod,
			String inspectTemplId, String InspectBSCId,String inspectDept,String targetDept,Integer stateLe,Integer stateEq,Integer stateGe, String groupBy,String inspectTemplName,Long kpiId,String periodType,String jjdepttype,String type,String orderBy) {
		return deptInspectDao.findByQuery(pagedRequests,checkPeriodFrom,checkPeriod, inspectTemplId, InspectBSCId,inspectDept,targetDept,stateLe,stateEq,stateGe, groupBy,inspectTemplName,kpiId,periodType,jjdepttype,type,orderBy);
	}

	@Override
	 public JQueryPager findTargetDeptState(HashMap<String,Object> conditionMap,String groupBy,JQueryPager pagedRequests){
		return deptInspectDao.findTargetDeptState(conditionMap,groupBy,pagedRequests);
	}


	@Override
	public void updateIspectState(String departmentId, String checkPeriod,
			Person person, Date currentTime, String scoreType,int state) {
		List<DeptInspect> list = this.deptInspectDao.getInspectByDeptAndPeriod(departmentId, checkPeriod, person.getDepartment().getJjDept().getDepartmentId());
		for(DeptInspect deptInspect: list){
			if(scoreType.equals("new")){
				deptInspect.setOperator(person);
				deptInspect.setOperateDate(currentTime);
				deptInspect.setState(state);
			}else if(scoreType.equals("check")){
				deptInspect.setOperator1(person);
				deptInspect.setOperateDate1(currentTime);
				deptInspect.setState(state);
			}else if(scoreType.equals("audit")){
				deptInspect.setOperator2(person);
				deptInspect.setOperateDate2(currentTime);
				deptInspect.setState(state);
			}else if(scoreType.equals("denytouse")){
				deptInspect.setOperator1(person);
				deptInspect.setOperateDate1(currentTime);
				deptInspect.setState(state);
			}
			deptInspectDao.save(deptInspect);
		}
	}
}