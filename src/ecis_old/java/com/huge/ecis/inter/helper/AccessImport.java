package com.huge.ecis.inter.helper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.huge.common.util.ExportHelper;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;

public class AccessImport extends AbstractImportService {
	public AccessImport(){
		super();
		this.setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
	}
	
	@Override
	protected void prepareData(String url, String username, String password,
			String execsql) {
	SearchUtils su=new SearchUtils();
		
		execsql = su.realSQL(execsql);
		
		this.setFinalUrl("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="
				+ url);

		//this.setDriver("sun.jdbc.odbc.JdbcOdbcDriver");

		DriverManagerDataSource outerDs = new DriverManagerDataSource(
				this.getFinalUrl(), username, password);
		outerDs.setDriverClassName(this.getDriver());
		JdbcTemplate outSJT = new JdbcTemplate(outerDs);

		this.setSourceData(outSJT.queryForList(execsql));
		this.setColumnDefs(ExportHelper.getColumnDefs(outSJT,
				HelperUtil.noRecSQL(execsql)));

	}

}
