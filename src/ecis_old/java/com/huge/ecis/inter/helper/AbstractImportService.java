package com.huge.ecis.inter.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.huge.common.util.ColumnDef;
import com.huge.foundation.util.StringUtil;

public abstract class AbstractImportService implements ImportService {
	private String		finalUrl;
	private String		driver;
	private List		sourceData;
	private ColumnDef[]	columnDefs;
	
	public String getFinalUrl() {
		return finalUrl;
	}
	
	public void setFinalUrl(String finalUrl) {
		this.finalUrl = finalUrl;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public List getSourceData() {
		return sourceData;
	}
	
	public void setSourceData(List sourceData) {
		this.sourceData = sourceData;
	}
	
	public ColumnDef[] getColumnDefs() {
		return columnDefs;
	}
	
	public void setColumnDefs(ColumnDef[] columnDefs) {
		this.columnDefs = columnDefs;
	}
	
	
	public void importData(JdbcTemplate simpleJdbcTemplate, String url,
			String username, String password, String execsql, String tempTable) {
		this.prepareData(url, username, password, execsql);
		
		this.importDataToTarget(simpleJdbcTemplate, sourceData, columnDefs, tempTable);
	}
	
	protected abstract void prepareData(String url, String username, String password, String execsql);
	
	protected void importDataToTarget(JdbcTemplate simpleJdbcTemplate, List dataList, ColumnDef[] columnDefs, String temptable) {
		String str1 = "insert into " + temptable + " (";
		if (dataList.size() > 0) {
			String[] fieldName = (String[]) null;
			for (int i = 0; i < dataList.size(); i++) {
				Map localResultMap = (Map) dataList.get(i);
				if (fieldName == null) {
					fieldName = new String[columnDefs.length];
					for (int j = 0; j < columnDefs.length; j++)
						fieldName[j] = columnDefs[j].getFieldName();
					for (int j = 0; j < fieldName.length; j++)
						str1 = str1 + fieldName[j] + ",";
					str1 = str1.substring(0, str1.length() - 1) + ") values(";
				}
				String sql = str1;
				for (int j = 0; j < fieldName.length; j++)
					sql = sql + getFieldSQLStr(columnDefs[j], fieldName[j], localResultMap) + ",";
				sql = sql.substring(0, sql.length() - 1) + ")";
				simpleJdbcTemplate.update(sql);
				// paramSQLTransaction.executeUpdate(str1);
			}
		}
	}
	
	protected String getFieldSQLStr(ColumnDef columnDef, String fieldName, Map rsm) {
		
		/*
		 * ResultMap rsm = new ResultMap(); rsm.putAll(map);
		 */

		String str1 = "";
		if (rsm.get(fieldName) == null) {
			str1 = str1 + "null";
		} else {
			int i = columnDef.getType();
			String str2;
			if ((i == -100) || (i == 93) || (i == 91)) {
				str2 = rsm.get(fieldName).toString();
				if ((str2 == null) || ("".equals(str2.trim())))
					str1 = str1 + "null";
				else
					str1 = str1 + "'" + str2 + "'";
			} else if ((i == 3) || (i == 4) || (i == 5) || (i == 6) || (i == 7) || (i == 8) || (i == -5) || (i == -7) || (i == 2) || (i == -6)) {
				str2 = rsm.get(fieldName).toString();
				
				if ((str2 == null) || ("".equals(str2.trim())))
					str1 = str1 + "null";
				else
					str1 = str1 + str2.trim();
			} else {
				str1 = str1 + "'" + StringUtil.replaceString((String) rsm.get(fieldName), "'", "''") + "'";
			}
		}
		return str1;
	}
	
	public boolean dataSourceTest(String url, String username, String password) {
		boolean result = false;
		try {
			DriverManagerDataSource outerDs = new DriverManagerDataSource(url, username, password);
			outerDs.setDriverClassName(this.getDriver());
			Connection con = outerDs.getConnection();
			if (con != null)
				result = true;
			else
				result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}
