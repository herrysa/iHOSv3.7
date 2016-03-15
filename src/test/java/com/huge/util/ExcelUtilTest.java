package com.huge.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.huge.ihos.excel.CellRange;
import com.huge.ihos.excel.DataSet;
import com.huge.ihos.excel.RowStyle;
import com.huge.ihos.excel.SheetStyle;
import com.huge.ihos.excel.ZhCell;

public class ExcelUtilTest extends TestCase{

	@Test
	public void testWriteExcelByCustom(){
		Map<String, SheetStyle> excelStyle = new HashMap<String, SheetStyle>();
		Map<String , Map<String, DataSet>> excelData = new HashMap<String, Map<String,DataSet>>();
		String excelFullPath = "D://writeExcelByCustom.xls";
		SheetStyle sheetStyle = new SheetStyle();
		excelStyle.put("sheet1", sheetStyle);
		CellRange cellRange = new CellRange();
		cellRange.firstRow = 0;
		cellRange.lastRow = 0;
		cellRange.firstColumn = 0;
		cellRange.lastColumn = 10;
		List<CellRange> cellRanges = new ArrayList<CellRange>();
		cellRanges.add(cellRange);
		sheetStyle.cellRangeList = cellRanges;
		sheetStyle.name = "sheet1";
		List<RowStyle> rowStyles = new ArrayList<RowStyle>();
		sheetStyle.rowStyleList = rowStyles;
		RowStyle rowStyleHead = new RowStyle();
		rowStyles.add(rowStyleHead);
		rowStyleHead.rowHeight = 30*20;
		List<ZhCell> zhCells = new ArrayList<ZhCell>();
		rowStyleHead.cellList = zhCells;
		ZhCell zhCell = new ZhCell();
		zhCells.add(zhCell);
		zhCell.cellStyleValue = "收入数据";
		zhCell.cellType = 1;
		
		RowStyle rowStyleBody = new RowStyle();
		rowStyles.add(rowStyleBody);
		List<ZhCell> bodyZhCells = new ArrayList<ZhCell>();
		rowStyleBody.rowHeight = 20*20;
		rowStyleBody.cellList = bodyZhCells;
		ZhCell zhCell_checkP = new ZhCell();
		bodyZhCells.add(zhCell_checkP);
		zhCell_checkP.cellStyleValue = "data.checkPeriod";
		zhCell_checkP.cellType = 1;

		ZhCell kdDept_name = new ZhCell();
		bodyZhCells.add(kdDept_name);
		kdDept_name.cellStyleValue = "data.kdDept_name";
		kdDept_name.cellType = 1;
		
		ZhCell zxDept_name = new ZhCell();
		bodyZhCells.add(zxDept_name);
		zxDept_name.cellStyleValue = "data.zxDept_name";
		zxDept_name.cellType = 1;
		
		Map<String, DataSet> dataSetMap = new HashMap<String, DataSet>();
		excelData.put("sheet1", dataSetMap);
		DataSet dataSet = new DataSet();
		dataSetMap.put("data", dataSet);
		
		dataSet.name = "data";
		List<Map<String, String>> rowList = new ArrayList<Map<String,String>>();
		dataSet.rowList = rowList;
		Map<String, String> data1 = new HashMap<String, String>();
		rowList.add(data1);
		data1.put("checkPeriod", "201408");
		data1.put("kdDept_name", "呼吸科");
		data1.put("zxDept_name", "财务科");
		
		ExcelUtil.writeExcelByCustom(excelStyle, excelData, excelFullPath);
	}
}
