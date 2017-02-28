package com.huge.ihos.system.reportManager.report.dao.hibernate;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.system.reportManager.report.model.ReportFunc;
import com.huge.webapp.util.SpringContextHelper;

public class BathFuncThread implements Runnable{

	/*int start=0,end=0;
	List<ReportFunc> funcList;
	
	public BathFuncThread(int start,int end,List<ReportFunc> funcList){
		this.start = start;
		this.end = end;
		this.funcList = funcList;
	}*/
	ReportFunc reportFunc;
	public BathFuncThread(ReportFunc reportFunc){
		this.reportFunc = reportFunc;
	}
	
	@Override
	public void run() {
		String rs;
		try {
			DataSource dataSource = SpringContextHelper.getDataSource();
			JdbcTemplate jtl = new JdbcTemplate(dataSource);
			Object rsObj = jtl.queryForObject(reportFunc.getFunc(), reportFunc.getPara(),Object.class);
			if(rsObj==null){
				reportFunc.setValue("");
			}else{
				rs = rsObj.toString();
				reportFunc.setValue(rs);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
	}
}
