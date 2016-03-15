package com.huge.ihos.formula.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.UpCostDao;
import com.huge.ihos.formula.model.UpCost;
import com.huge.ihos.formula.service.UpCostManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("upCostManager")
public class UpCostManagerImpl extends GenericManagerImpl<UpCost, Long> implements UpCostManager {
    private UpCostDao upCostDao;

    @Autowired
    public UpCostManagerImpl(UpCostDao upCostDao) {
        super(upCostDao);
        this.upCostDao = upCostDao;
    }
    

	public JQueryPager getUpCostCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return upCostDao.getUpCostCriteria(paginatedList,filters);
	}

	@Override
	public List<UpCost> getBycheckPeriodAndDept(String checkPeriod, String dept,Long upItemId,Integer state) {
		return upCostDao.getBycheckPeriodAndDept(checkPeriod, dept,upItemId,state);
	}

	@Override
	public String inheritUpCost(Long upItemId,String checkPeriod,String currentPeriod, User user,boolean containAmount,String upItemType) {
		
		String deptIdIn = null;
		if("1".equals(upItemType)){
			deptIdIn = "";
			//String[] deptIds = jjDeptMapDao.getByDeptIds( user.getPerson().getPersonId() );
			//String deptString = (String) UserContextUtil.findSysVariable("%JJFGKS%");
			String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "1");
			/*String[] deptIds = null;
			if(deptString != null && !"".equals(deptString)) {
				deptIds = deptString.split(",");
			}
	        if (deptIds != null && deptIds.length > 0 ) {

	            for ( String deptId : deptIds ) {
	                deptIdIn += deptId + ",";
	            }
	            deptIdIn = OtherUtil.subStrEnd( deptIdIn, "," );
	        }*/
			if(!deptIds.startsWith("SELECT") && !deptIds.startsWith("select")) {
				deptIdIn = deptIds;
			}
		}
        List<UpCost> upCosts = upCostDao.getBycheckPeriodAndDept(currentPeriod, deptIdIn, upItemId, null);
        if(!upCosts.isEmpty()){
        	return "notEmpty";
        }
		upCosts = upCostDao.getBycheckPeriodAndDept(checkPeriod, deptIdIn, upItemId, null);
		if(!upCosts.isEmpty()){
			UpCost uc = null,upCost = null;
			for(int i=0;i<upCosts.size();i++){
				upCost = upCosts.get(i);
				if("1".equals(upItemType)){
					if(upCost.getPersonId().getDisable()){
						continue;
					}
				}else{
					if(upCost.getDeptId().getDisabled()){
						continue;
					}
				}
				uc = new UpCost();
				uc.setCheckperiod(currentPeriod);
				uc.setUpitemid(upCost.getUpitemid());
				uc.setCostitemid(upCost.getCostitemid());
				uc.setDeptId(upCost.getDeptId());
				uc.setPersonId(upCost.getPersonId());
				uc.setOwnerdept(upCost.getOwnerdept());
				uc.setAmount(containAmount?upCost.getAmount():new BigDecimal(0));
				uc.setOperatorId(user.getPerson());
				uc.setOperatordeptid(user.getPerson().getDepartment());
				uc.setOperatorName(user.getPerson().getName());
				uc.setCostOrg(upCost.getCostOrg());
				uc.setBranch(upCost.getBranch());
				uc.setState(0);
				uc = upCostDao.save(uc);
			}
			return (uc != null)?"success":"failed";
		}else{
			return "empty";
		}
	}
	
	
}