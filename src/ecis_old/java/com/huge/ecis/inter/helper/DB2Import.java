package com.huge.ecis.inter.helper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.huge.common.util.ExportHelper;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;

public class DB2Import extends AbstractImportService {
	public DB2Import() {
		super();
		this.setDriver("COM.ibm.db2.jdbc.app.DB2Driver");
	}
	@Override
	protected void prepareData(String url, String username, String password,
			String execsql) {
		
		SearchUtils su=new SearchUtils();
		
		execsql = su.realSQL(execsql);
		this.setFinalUrl(url);
		//this.setDriver("COM.ibm.db2.jdbc.app.DB2Driver");

		DriverManagerDataSource outerDs = new DriverManagerDataSource(
				this.getFinalUrl(), username, password);
		outerDs.setDriverClassName(this.getDriver());
		JdbcTemplate outSJT = new JdbcTemplate(outerDs);

		this.setSourceData(outSJT.queryForList(execsql));
		this.setColumnDefs(ExportHelper.getColumnDefs(outSJT,
				HelperUtil.noRecSQL(execsql)));

	}
}

/*
 * Location:
 * D:\EclipseWorkspaces\OldWorkspace\ecis2.5_20110809\WebRoot\WEB-INF\lib
 * \inter-model.jar Qualified Name: com.huge.ecis.inter.helper.DB2Import JD-Core
 * Version: 0.6.0
 */