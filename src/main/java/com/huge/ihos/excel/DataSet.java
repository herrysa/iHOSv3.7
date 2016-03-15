package com.huge.ihos.excel;

import java.util.List;
import java.util.Map;

public class DataSet {

	public String name;
	public boolean multiRow = false;
	public int maxDataRowLength;
	public Map<String , ColumnDefine> columnDefineMap ;
	public List<Map<String, String>> rowList;
	public Map<String, CellData> cellDataMap;
}
