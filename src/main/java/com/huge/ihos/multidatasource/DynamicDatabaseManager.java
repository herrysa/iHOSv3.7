package com.huge.ihos.multidatasource;

import java.util.Map;

public class DynamicDatabaseManager {
	private Map<String,String> selectedDataSource;
	private String defaultSessionFactory;

	public Map<String,String> getSelectedDataSource() {
		return selectedDataSource;
	}

	public void setSelectedDataSource(Map<String,String> selectedDataSource) {
		this.selectedDataSource = selectedDataSource;
	}

	public String getDefaultSessionFactory() {
		return defaultSessionFactory;
	}

	public void setDefaultSessionFactory(String defaultSessionFactory) {
		this.defaultSessionFactory = defaultSessionFactory;
	}
	
	
	
}
