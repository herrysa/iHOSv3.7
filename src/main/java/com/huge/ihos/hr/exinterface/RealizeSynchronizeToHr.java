package com.huge.ihos.hr.exinterface;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huge.ihos.hr.asyncHrData.model.syncHrData;
import com.huge.ihos.hr.asyncHrData.service.AsyncHrDataManager;
import com.huge.ihos.hr.asyncHrData.service.syncHrDataManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.exinterface.SynchronizeToHr;
import com.huge.ihos.system.systemManager.globalparam.service.GlobalParamManager;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.DateUtil;

@Component
public class RealizeSynchronizeToHr extends SynchronizeToHr{

	private AsyncHrDataManager asyncHrDataManager;
	private syncHrDataManager syncHrDataManager;
	private GlobalParamManager globalParamManager;
	//TODO 此处已经修改从periodMonth里取期间
	//private PeriodMonthManager periodMonthManager;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private HrDepartmentSnapManager hrDepartmentSnapManager;
	private HrPersonSnapManager hrPersonSnapManager;
	private PeriodManager periodManager;
	
	@Autowired
	public void setAsyncHrDataManager(AsyncHrDataManager asyncHrDataManager) {
		this.asyncHrDataManager = asyncHrDataManager;
	}
	@Autowired
	public void setSyncHrDataManager(syncHrDataManager syncHrDataManager) {
		this.syncHrDataManager = syncHrDataManager;
	}
	@Autowired
	public void setGlobalParamManager(GlobalParamManager globalParamManager) {
		this.globalParamManager = globalParamManager;
	}
	@Autowired
	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}
	/*@Autowired
	public void setPeriodMonthManager(PeriodMonthManager periodMonthManager) {
		this.periodMonthManager = periodMonthManager;
	}*/
	@Autowired
	public void setHrDepartmentCurrentManager(
			HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
	@Autowired
	public void setHrDepartmentSnapManager(
			HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}
	@Autowired
	public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}

	@Override
	public void synchronizeOrgToHr() {
		
	}

	@Override
	public void jzSynchronizeOrgToHr(String period) {
		String curPeriod = period;
		//Date endDate = periodMonthManager.getPeriodByCode(period).getEndDate();
		Date endDate = periodManager.getPeriodByCode(period).getEndDate();
		String ansyOrgDeptPerson = globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
		if("2".equals(ansyOrgDeptPerson)){
			Calendar cal=Calendar.getInstance();//使用日历类 
			cal.setTime(endDate);
			cal.set(Calendar.HOUR_OF_DAY,23);           
			cal.set( Calendar.MINUTE,59);           
			cal.set(Calendar.SECOND,59);
			String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN, cal.getTime());
			syncHrData syncHrData = new syncHrData();
			asyncHrDataManager.asyncHrData(snapCode);
			//在此处插入同步日志
			syncHrData.setSyncHrName("人力资源系统" + curPeriod + "结账");
			Date today = new Date();
			syncHrData.setSyncTime(today);
			syncHrData.setSyncToHrType("HR系统结账时同步");
			syncHrData.setSyncType("1");
			syncHrData.setHr_snap_time(cal.getTime());
			syncHrData.setSyncOperator(UserContextUtil.getUserContext().getLoginPersonName());
			syncHrDataManager.save(syncHrData);
		}
	}

	@Override
	public boolean deptInHr(String deptId) {
		return hrDepartmentCurrentManager.exists(deptId);
	}

	@Override
	public void syncHrDepartment(Department dept, Person person, Date date) {
		hrDepartmentSnapManager.syncHrDepartment(dept, person, date);
	}
	@Override
	public void snycHrPerson(Person person, Person operperson, Date operdate) {
		hrPersonSnapManager.snycHrPerson(person,operperson,operdate);
	}
}
