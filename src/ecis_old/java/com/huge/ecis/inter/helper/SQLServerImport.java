package com.huge.ecis.inter.helper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.huge.common.util.ExportHelper;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;

public class SQLServerImport extends AbstractImportService {
	public SQLServerImport() {
		super();
		this.setDriver("net.sourceforge.jtds.jdbc.Driver");
	}
  
	protected void prepareData(String url, String username, String password,
			String execsql) {
	SearchUtils su=new SearchUtils();
		
		execsql = su.realSQL(execsql);
		
		this.setFinalUrl(url);
	//	this.setDriver("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		this.setDriver("net.sourceforge.jtds.jdbc.Driver");
		
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
 * \inter-model.jar Qualified Name: com.huge.ecis.inter.helper.SQLServerImport
 * JD-Core Version: 0.6.0
 */