package com.huge.ihos.system.reportManager.report.dao.hibernate;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.system.reportManager.report.model.ReportFunc;
import com.huge.webapp.util.SpringContextHelper;

public class BathFuncThread implements Runnable{

	int start=0,end=0;
	List<ReportFunc> funcList;
	
	public BathFuncThread(int start,int end,List<ReportFunc> funcList){
		this.start = start;
		this.end = end;
		this.funcList = funcList;
	}
	
	@Override
	public void run() {
		if(funcList.size()<end){
			end = funcList.size()-1;
		}
		for(int i=start;i<=end;i++){
			//System.out.println(i);
			ReportFunc reportFunc = funcList.get(i);
			getFuncValue(reportFunc);
		}
		
	}
	
	public void getFuncValue(ReportFunc reportFunc) {
		String rs;
		try {
			DataSource dataSource = SpringContextHelper.getDataSource();
			JdbcTemplate jtl = new JdbcTemplate(dataSource);
			rs = jtl.queryForObject(reportFunc.getFunc(), reportFunc.getPara(),String.class);
			reportFunc.setValue(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
