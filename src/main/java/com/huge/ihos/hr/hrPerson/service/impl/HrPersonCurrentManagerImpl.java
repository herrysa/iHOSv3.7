package com.huge.ihos.hr.hrPerson.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.dao.HrPersonCurrentDao;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("hrPersonCurrentManager")
public class HrPersonCurrentManagerImpl extends GenericManagerImpl<HrPersonCurrent, String> implements HrPersonCurrentManager {
    private HrPersonCurrentDao hrPersonCurrentDao;
    private HrOrgManager hrOrgManager;

	@Autowired
    public HrPersonCurrentManagerImpl(HrPersonCurrentDao hrPersonCurrentDao) {
        super(hrPersonCurrentDao);
        this.hrPersonCurrentDao = hrPersonCurrentDao;
    }
	@Autowired
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

    public JQueryPager getHrPersonCurrentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrPersonCurrentDao.getHrPersonCurrentCriteria(paginatedList,filters);
	}
	@Override
    public List<Object[]> getHrPersonSomeFileds(String[] ida,String fileds){
    	String ids="(";
    	for(int i=0;i<ida.length;i++){
    		if(i>0){
    			ids+=","+"'"+ida[i]+"'";
    		}else{
    			ids+="'"+ida[i]+"'";
    		}
    	}
    	ids+=")";
    	return this.hrPersonCurrentDao.getHrPersonSomeFileds(ids,fileds);
    }
    @Override
	 public String checkLockHrPersonCurrent(String personId,String[] checkLockStates){
    	HrPersonCurrent hrPersonCurrent=hrPersonCurrentDao.get(personId);
			String messtr=null;
			if(hrPersonCurrent!=null){
				String lockState=hrPersonCurrent.getLockState();
				if(lockState==null||lockState.equals("")){
				}else{
					String[] states=lockState.split(",");
					List checkStatelist = new ArrayList();
					checkStatelist = Arrays.asList(checkLockStates);
					for(int stateIndex=0;stateIndex<states.length;stateIndex++){
						String stateTemp=states[stateIndex];
						if(checkStatelist.contains(stateTemp)){
							if(messtr==null){
								messtr=stateTemp;
							}else{
								messtr+=","+stateTemp;
							}
						}
					}
				}
			}
			return messtr;
	 }
    @Override
    public List<HrPersonCurrent> getBirthThisMonthHrPersonList(){
    	List<PropertyFilter> filters =new ArrayList<PropertyFilter>();
		String orgCodes = hrOrgManager.getAllAvailableString();
		if(OtherUtil.measureNull(orgCodes)){
			orgCodes = "";
		}
		filters.add(new PropertyFilter("INS_orgCode",orgCodes));
		filters.add(new PropertyFilter("EQB_department.disabled","0"));
		filters.add(new PropertyFilter("OAS_personCode",""));
		List<HrPersonCurrent> hrPersonCurrents=hrPersonCurrentDao.getByFilters(filters);
		List<HrPersonCurrent> hrPersonCurrentsTemp=new ArrayList<HrPersonCurrent>();
		if(hrPersonCurrents!=null&&hrPersonCurrents.size()>0){
			Calendar cal = Calendar.getInstance(); 
			int monthNow = cal.get(Calendar.MONTH);
			for(HrPersonCurrent hrPersonCurrentTemp:hrPersonCurrents){
				Date birthDay=hrPersonCurrentTemp.getBirthday();
				if(birthDay!=null&&!birthDay.equals("")){
					cal.setTime(birthDay); 
					int monthBirth = cal.get(Calendar.MONTH); 
					if(monthBirth==monthNow){
						hrPersonCurrentsTemp.add(hrPersonCurrentTemp);
					}
				}
			}
		}
		return hrPersonCurrentsTemp;
    }
    @Override
    public Boolean personRemovable(String personIds){
    	return hrPersonCurrentDao.personRemovable(personIds);
    }
    @Override
    public List<String> getPersonIdList(){
    	return hrPersonCurrentDao.getPersonIdList();
    }
}