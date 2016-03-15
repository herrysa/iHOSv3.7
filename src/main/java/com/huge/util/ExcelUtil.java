package com.huge.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.huge.ihos.excel.CellData;
import com.huge.ihos.excel.CellRange;
import com.huge.ihos.excel.ColumnDefine;
import com.huge.ihos.excel.ColumnStyle;
import com.huge.ihos.excel.DataSet;
import com.huge.ihos.excel.RowStyle;
import com.huge.ihos.excel.SheetStyle;
import com.huge.ihos.excel.ZhCell;
import com.huge.ihos.excel.ZhCellStyle;
import com.huge.ihos.excel.ZhFont;

public class ExcelUtil {

	enum CellType { String()};
	public static Workbook getWorkBook(String fileName)
			throws FileNotFoundException, IOException {
		File excel = new File(fileName);
		Workbook wb = getWorkBook(excel);
		return wb;
	}

	private static Workbook getWorkBook(File excel)
			throws FileNotFoundException, IOException {
		return (Workbook) (excel.getName().endsWith("xls") ? new HSSFWorkbook(
				new BufferedInputStream(new FileInputStream(excel))) : excel
				.getName().endsWith("xlsx") ? new XSSFWorkbook(
				new BufferedInputStream(new FileInputStream(excel))) : null);
	}
	
	/**通过自定义style来生成Excel，也就是没有真实模板的时候。
	 * @param excelStyle 
	 * @param excelData
	 * @param excelFullPath
	 */
	public static void writeExcelByCustom(Map<String, SheetStyle> excelStyle,Map<String , Map<String, DataSet>> excelData,String excelFullPath){
		Map<String, SheetStyle> excelStyleFilled = writeExcelByCustomPrepare(excelStyle,excelData);
		writeExcelByStyle(excelStyleFilled,excelFullPath);
	}
	/**通过模板来生成Excel
	 * @param templateName
	 * @param excelData
	 * @param excelFullPath
	 */
	public static void writeExcelByTemplate(String templateName,Map<String , Map<String, DataSet>> excelData,String excelFullPath){
		Map<String, SheetStyle> excelStyle = writeExcelByTemplatePrepare(templateName,excelData);
		writeExcelByStyle(excelStyle,excelFullPath);
	}
	
	public static Map<String, SheetStyle> writeExcelByCustomPrepare(Map<String, SheetStyle> excelStyle,Map<String , Map<String, DataSet>> excelData){
		try {
			Set<Entry<String , Map<String, DataSet>>> sheetData = excelData.entrySet();
			for(Entry<String , Map<String, DataSet>> sheetEntry : sheetData){
				String sheetName = sheetEntry.getKey();
				Map<String, DataSet> dataSetMap = sheetEntry.getValue();
				trimCellData(dataSetMap);
				SheetStyle sheetStyle = excelStyle.get(sheetName);
				fillSheetData(sheetStyle,dataSetMap);
				excelStyle.put(sheetName, sheetStyle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return excelStyle;
	}
	
	public static Map<String, SheetStyle> writeExcelByTemplatePrepare(String templateName,Map<String , Map<String, DataSet>> excelData){
		HSSFWorkbook workbook;
		Map<String, SheetStyle> excelStyle = null;
		try {
			workbook = (HSSFWorkbook)ExcelUtil.getWorkBook(templateName);
			Set<Entry<String , Map<String, DataSet>>> sheetData = excelData.entrySet();
			excelStyle = new LinkedHashMap<String, SheetStyle>();
			int sheetIndex = 0;
			int numSheet = workbook.getNumberOfSheets();
			for(Entry<String , Map<String, DataSet>> sheetEntry : sheetData){
				String sheetName = sheetEntry.getKey();
				Map<String, DataSet> dataSetMap = sheetEntry.getValue();
				trimCellData(dataSetMap);
				HSSFSheet sheet = null;
				if(sheetIndex<=numSheet-1){
					sheet = workbook.getSheetAt( sheetIndex ); 
				}else{
					sheet = workbook.getSheetAt( 0 ); 
				}
				SheetStyle sheetStyle = getSheetStyle(workbook,sheet);
				fillSheetData(sheetStyle,dataSetMap);
				excelStyle.put(sheetName, sheetStyle);
				sheetIndex++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return excelStyle;
	}
	/**通过excelStyle生成Excel，包括cellRange（单元格合并）、columnStyle（列样式）、rowStyle（行样式）
	 * @param excelStyle
	 * @param excelFullPath
	 * @return
	 */
	public static String writeExcelByStyle(Map<String, SheetStyle> excelStyle,String excelFullPath) {
		FileOutputStream fileOut = null;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			Iterator<Entry<String, SheetStyle>> sheetIt = excelStyle.entrySet().iterator();
			while (sheetIt.hasNext()) {
				Entry<String,SheetStyle> sheetEntry = sheetIt.next();
				String sheetName = sheetEntry.getKey();
				Sheet sheet = workbook.createSheet(sheetName);
				SheetStyle sheetStyle = excelStyle.get(sheetName);
				
				mergeCell(sheet,sheetStyle);
				addColumnStyle(sheet,sheetStyle);
				addRowStyleAndSetValue(workbook,sheet,sheetStyle);
				
			}
			OptFile.mkParent(excelFullPath);
			fileOut = new FileOutputStream(excelFullPath);
			workbook.write(fileOut);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fileOut != null)
						fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return null; 
	}
	
	private static void mergeCell(Sheet sheet , SheetStyle sheetStyle){
		List<CellRange> cellRangeList = sheetStyle.cellRangeList;
		if(cellRangeList!=null){
			for(int cellRangeIndex=0;cellRangeIndex<cellRangeList.size();cellRangeIndex++){
				CellRange zhCellRange = cellRangeList.get(cellRangeIndex);
				CellRangeAddress cellRange = new CellRangeAddress(zhCellRange.firstRow, zhCellRange.lastRow, zhCellRange.firstColumn, zhCellRange.lastColumn);
				sheet.addMergedRegion(cellRange);
			}
		}
	}
	
	private static void addColumnStyle(Sheet sheet , SheetStyle sheetStyle){
		List<ColumnStyle> columnStyleList = sheetStyle.columnStyleList;
		if(columnStyleList!=null){
			for(int columnIndex=0;columnIndex<columnStyleList.size();columnIndex++){
				ColumnStyle columnStyle = columnStyleList.get(columnIndex);
				int columnNum = columnStyle.columnNum;
				int columnWidth = columnStyle.colnumWidth;
				boolean columnHidden = columnStyle.columnHidden;
				sheet.setColumnHidden((short) columnNum, columnHidden);
				sheet.setColumnWidth((short) columnNum, (short)columnWidth);
			}
		}
	}
	
	private static void addRowStyleAndSetValue(Workbook workbook,Sheet sheet , SheetStyle sheetStyle){
		List<RowStyle> rowStyleList = sheetStyle.rowStyleList;
		if(rowStyleList!=null){
			for(int rowIndex=0;rowIndex<rowStyleList.size();rowIndex++){
				RowStyle rowStyle = rowStyleList.get(rowIndex);
				if(rowStyle==null){
					continue;
				}
				Row row = sheet.createRow(rowIndex);
				int rowHeight = rowStyle.rowHeight;
				row.setHeight((short)rowHeight);
				addCellStyleAndSetValue(workbook , row ,rowStyle);
			}
		}
	}
	
	private static void addCellStyleAndSetValue(Workbook workbook ,Row row ,RowStyle rowStyle){
		List<ZhCell> cellList = rowStyle.cellList;
		for(int cellIndex=0;cellIndex<cellList.size();cellIndex++){
			Cell cell = row.createCell(cellIndex);
			ZhCell zhCell = cellList.get(cellIndex);
			ZhCellStyle zhCellStyle = zhCell.cellStyle;
			Integer cellStyleIndex = new Integer(zhCellStyle.getCellStyleIndex());
			Map<Integer, Integer> cellStyleIndexMap = rowStyle.getSheetStyle().cellStyleIndexMap;
			Integer newCellStyleIndex = cellStyleIndexMap.get(cellStyleIndex);
			CellStyle cellStyle = null;
			if(newCellStyleIndex==null){
				cellStyle = workbook.createCellStyle();
				cellStyleIndexMap.put(cellStyleIndex, new Integer(cellStyle.getIndex()));
				if(zhCellStyle!=null){
					zhCellStyle.cloneStyleTo(cellStyle,workbook);
					cell.setCellStyle(cellStyle);
				}
			}else{
				cellStyle = workbook.getCellStyleAt(newCellStyleIndex.shortValue());
				cell.setCellStyle(cellStyle);
			}
			
			/*if(cellStyle!=null){
				workbook.getCellStyleAt(arg0);
				CellStyle newCellStyle = workbook.createCellStyle();
				newCellStyle.cloneStyleFrom((CellStyle)cellStyle);
				cell.setCellStyle(newCellStyle);
			}*/
			zhCell.setValue(cell);
		}
	}
	
	/**把dataSet里面按行存储的数据拆分，形成一个数据单元的map，map的key为：列名+行号
	 * @param dataseMap
	 */
	public static void trimCellData(Map<String, DataSet> dataseMap){
		Iterator<Entry<String, DataSet>> dataSetIt = dataseMap.entrySet().iterator();
		while (dataSetIt.hasNext()) {
			Entry<String, DataSet> dataSetMap = dataSetIt.next();
			//String dataSetName = dataSetMap.getKey();
			DataSet dataSet = dataSetMap.getValue();
			dataSet.cellDataMap = new HashMap<String, CellData>();
			List<Map<String, String>> rowList = dataSet.rowList;
			Map<String , ColumnDefine> columnDefineMap = dataSet.columnDefineMap;
			if(rowList!=null&&rowList.size()>0){
				dataSet.multiRow = true;
				dataSet.maxDataRowLength = rowList.size();
			}
			for(int rowIndex=0;rowIndex<rowList.size();rowIndex++){
				Map<String, String> rowData = rowList.get(rowIndex);
				Set<Entry<String, String>> rowEntry = rowData.entrySet();
				Iterator<Entry<String, String>> rowIt = rowEntry.iterator();
				while(rowIt.hasNext()){
					Entry<String, String> colData = rowIt.next();
					String colName = colData.getKey();
					Object colValue = colData.getValue();
					CellData cellData = new CellData();
					cellData.name = colName;
					cellData.value = colValue;
					ColumnDefine columnDefine = null;
					if(columnDefineMap!=null){
						columnDefine = columnDefineMap.get(colName);
						if(columnDefine!=null){
							cellData.type = columnDefine.type;
						}
					}
					colName = colName.toUpperCase();
					dataSet.cellDataMap.put(colName+rowIndex, cellData);
				}
			}
			//dataSet.rowList = null;
		}
	}
	
	/**获取模板的sheet样式
	 * @param sheet
	 * @return
	 */
	public static SheetStyle getSheetStyle(Workbook workbook , Sheet sheet){
		SheetStyle sheetStyle = new SheetStyle();
		List<CellRange> cellRangeList = new ArrayList<CellRange>();
		List<ColumnStyle> columnStyleList = new ArrayList<ColumnStyle>();
		List<RowStyle> rowStyleList = new ArrayList<RowStyle>();
		
		getMergeRange(sheet,cellRangeList);
		getColumnStyle(sheet,columnStyleList);
		getRowStyle(workbook , sheet,rowStyleList , sheetStyle);
		
		sheetStyle.cellRangeList = cellRangeList;
		sheetStyle.columnStyleList = columnStyleList;
		sheetStyle.rowStyleList = rowStyleList;
		return sheetStyle;
	}
	
	private static void getMergeRange(Sheet sheet,List<CellRange> cellRangeList){
		int sheetMergerCount = sheet.getNumMergedRegions();
		for(int i = 0; i < sheetMergerCount; i++) {
			CellRange cellRange = new CellRange();
			CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
			cellRange.firstColumn = cellRangeAddress.getFirstColumn();
			cellRange.firstRow = cellRangeAddress.getFirstRow();
			cellRange.lastColumn = cellRangeAddress.getLastColumn();
			cellRange.lastRow = cellRangeAddress.getLastRow();
			cellRangeList.add(cellRange);
		}
	}
	
	private static void getColumnStyle(Sheet sheet ,List<ColumnStyle> columnStyleList){
		Row firstRow = sheet.getRow(0);
		for (int j = firstRow.getLastCellNum(); j >= firstRow.getFirstCellNum(); j--) {
			int colnum = sheet.getColumnWidth((short) j);
			ColumnStyle columnStyle = new ColumnStyle();
			columnStyle.columnNum = j;
			if (colnum > 100) {
				columnStyle.colnumWidth = colnum;
			}
			if (colnum == 0) {
				columnStyle.columnHidden = true;
			} else {
				columnStyle.columnHidden = false;
			}
			columnStyleList.add(columnStyle);
		}
	}
	
	private static void getRowStyle(Workbook workbook , Sheet sheet ,List<RowStyle> rowStyleList , SheetStyle sheetStyle){
		for (Iterator<Row> rowIt = sheet.rowIterator(); rowIt.hasNext();) {
			Row tmpRow = rowIt.next();
			//Map<String, Object> rowMap = new HashMap<String, Object>();
			RowStyle rowStyle = new RowStyle();
			rowStyle.setSheetStyle(sheetStyle);
			rowStyle.rowHeight = tmpRow.getHeight();
			List<ZhCell> cellList = new ArrayList<ZhCell>();
			getCellStyle(tmpRow, rowStyle, sheet, workbook , cellList);
			rowStyle.cellList = cellList;
			rowStyleList.add(rowStyle);
		}
	}
	
	private static void getCellStyle(Row tmpRow , RowStyle rowStyle ,Sheet sheet , Workbook workbook ,List<ZhCell> cellList){
		for (Iterator<Cell> cellIt = tmpRow.cellIterator(); cellIt.hasNext();) {
			Cell tmpCell = cellIt.next();
			ZhCell zhCell = new ZhCell();
			zhCell.setRowStyle(rowStyle);
			CellStyle cellStyle = tmpCell.getCellStyle();
			ZhCellStyle zhCellStyle = new ZhCellStyle();
			zhCellStyle.setZhCell(zhCell);
			zhCellStyle.cloneStyleFrom(cellStyle, workbook);
			zhCell.cellStyle = zhCellStyle;
			Comment comment = tmpCell.getCellComment();
			if(comment!=null){
				zhCell.comment = comment.getString().getString();
			}
			//String cellFormula = tmpCell.getCellFormula();
			//cellMap.put("cellFormula", cellFormula);
			int cellType = tmpCell.getCellType();
			zhCell.cellType = cellType;
			String cellValue = "";
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(tmpCell)) {
					zhCell.cellStyleValue = ""+tmpCell.getDateCellValue();
				} else {
					zhCell.cellStyleValue  = ""+tmpCell.getNumericCellValue();
				}
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				zhCell.cellStyleValue  = ""+tmpCell.getRichStringCellValue();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				// nothing21
			} else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
				zhCell.cellStyleValue  = ""+tmpCell.getBooleanCellValue();
			} else if (cellType == HSSFCell.CELL_TYPE_ERROR) {
				zhCell.cellStyleValue  = ""+tmpCell.getErrorCellValue();
			} else if (cellType == HSSFCell.CELL_TYPE_FORMULA) {
				zhCell.cellStyleValue  = ""+tmpCell.getCellFormula();
			} else { // nothing29
			}
			cellList.add(zhCell);
		}
	}
	
	/**根据sheetStyle里的rowStyle，把dataset里的数据填充进去
	 * @param sheetStyle
	 * @param dataSetMap
	 */
	public static void fillSheetData(SheetStyle sheetStyle , Map<String, DataSet> dataSetMap ){
		List<RowStyle> rowFilledList = new ArrayList<RowStyle>();
		List<RowStyle> rowList = sheetStyle.rowStyleList;
		for(int rowIndex=0;rowIndex<rowList.size();rowIndex++){
			RowStyle rowStyle  = rowList.get(rowIndex);
			List<ZhCell> cellList = rowStyle.cellList;
			boolean multiDataRow = false;
			int maxDataRowLength = 0;
			for(int cellIndex=0;cellIndex<cellList.size();cellIndex++){
				ZhCell zhGell = cellList.get(cellIndex);
				String cellValue = zhGell.cellStyleValue;
				if(cellValue==null){
					continue;
				}
				//String replaceStr = "";
				String replaceStr = cellValue;
				/*Matcher matcher = matchBrace(cellValue);
				if(!matcher.find()){
					continue;
				}
				int numGroup = matcher.groupCount();*/
				int numGroup = 1;
				for(int groupIndex=1;groupIndex<=numGroup;groupIndex++){
					//replaceStr = matcher.group(groupIndex);
	        		if(replaceStr!=null&&replaceStr.contains(".")){
	        			String[] dataFiledArr = null;
						dataFiledArr = replaceStr.split("\\.");
						String dataSetName = dataFiledArr[0];
						String filedName = dataFiledArr[1];
						DataSet dataSet = dataSetMap.get(dataSetName);
						if(dataSet!=null){
							if(!multiDataRow){
								multiDataRow = dataSet.multiRow;
							}
							maxDataRowLength = maxDataRowLength<dataSet.maxDataRowLength-1?dataSet.maxDataRowLength-1:maxDataRowLength;
							fillCellData(dataSetName, filedName, zhGell, dataSet);
						}
				}
				}
			}
			rowFilledList.add(rowStyle);
			if(multiDataRow){
				copyRowStyleAndSetData(rowStyle, dataSetMap, rowFilledList, maxDataRowLength);
			}
		}
		sheetStyle.rowStyleList = rowFilledList;
	}
	
	private static void fillCellData(String dataSetName ,String filedName,ZhCell zhGell , DataSet dataSet){
		Map<String, CellData> cellDataMap = dataSet.cellDataMap;
		if(cellDataMap!=null){
			Map<String, DataSet> cellDataSetMap = zhGell.cellDataSetMap;
			if(cellDataSetMap==null){
				cellDataSetMap = new HashMap<String, DataSet>();
				zhGell.cellDataSetMap = cellDataSetMap;
			}
			DataSet cellDataSet = cellDataSetMap.get(dataSetName);
			if(cellDataSet==null){
				cellDataSet = new DataSet();
				cellDataSet.name = dataSetName;
				cellDataSetMap.put(dataSetName, cellDataSet);
			}
			Map<String, CellData> zhCellDataMap = cellDataSet.cellDataMap;
			if(zhCellDataMap==null){
				zhCellDataMap = new HashMap<String, CellData>();
				cellDataSet.cellDataMap = zhCellDataMap;
			}
			filedName = filedName.toUpperCase();
			CellData cellData = cellDataMap.get(filedName+0);
			zhCellDataMap.put(filedName, cellData);
			//zhGell.cellData = cellData;
		}
	}
	
	private static void copyRowStyleAndSetData(RowStyle rowStyle , Map<String, DataSet> dataSetMap ,List<RowStyle> rowFilledList, int maxDataRowLength){
		for(int dataRowIndex=0;dataRowIndex<maxDataRowLength;dataRowIndex++){
			try {
				RowStyle rowStyleAdded = rowStyle.clone();
				List<ZhCell> firstCellList = rowStyle.cellList;
				List<ZhCell> cellListAdded = new ArrayList<ZhCell>();
				rowStyleAdded.cellList = cellListAdded;
				for(int cellIndex=0;cellIndex<firstCellList.size();cellIndex++){
					ZhCell cell = firstCellList.get(cellIndex);
					ZhCell cellNew = cell.clone();
					//String cellName = cell.name;
					//String dataSetName = cell.dataSetName;
					Map<String,DataSet> cellDataSetMap = cell.cellDataSetMap;
					Map<String,DataSet> cellDataSetMapNew = new HashMap<String, DataSet>();
					cellNew.cellDataSetMap = cellDataSetMapNew;
					copyCellAndSetData(cellDataSetMapNew, cellDataSetMap, dataSetMap, dataRowIndex);
					cellListAdded.add(cellNew);
				}
				rowFilledList.add(rowStyleAdded);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void copyCellAndSetData(Map<String,DataSet> cellDataSetMapNew ,Map<String,DataSet> cellDataSetMap , Map<String, DataSet> dataSetMap,int dataRowIndex){
		Iterator<Entry<String, DataSet>> cellDataIt = cellDataSetMap.entrySet().iterator();
		while (cellDataIt.hasNext()) {
			Entry<String, DataSet> dataSetEntry = cellDataIt.next();
			DataSet cellDataSet = dataSetEntry.getValue();
			String dataSetName = cellDataSet.name;
			DataSet cellDataSetNew = new DataSet();
			cellDataSetNew.name = dataSetName;
			cellDataSetMapNew.put(dataSetName, cellDataSetNew);
			DataSet dataSet = dataSetMap.get(dataSetName);
			if(dataSet!=null){
				Map<String, CellData> zhCellDatMap = cellDataSet.cellDataMap;
				Map<String, CellData> zhCellDatMapNew = new HashMap<String, CellData>();
				Map<String, CellData> cellDataMap = dataSet.cellDataMap;
				if(cellDataMap!=null){
					Iterator<Entry<String, CellData>> zhCellDataIt = zhCellDatMap.entrySet().iterator();
					while (zhCellDataIt.hasNext()) {
						Entry<String, CellData> cellDataEntry = zhCellDataIt.next();
						String cellName = cellDataEntry.getKey();
						cellName = cellName.toUpperCase();
						CellData cellData = cellDataMap.get(cellName+(dataRowIndex+1));
						if(cellData!=null){
							//cellNew.cellData = cellData;
							zhCellDatMapNew.put(cellName, cellData);
						}else {
							CellData cellDataEmpty = new CellData();
							cellDataEmpty.type = 1;
							cellDataEmpty.value = "";
							//cellNew.cellData = cellDataEmpty;
							zhCellDatMapNew.put(cellName, cellDataEmpty);
						}
					}
				}
				cellDataSetNew.cellDataMap = zhCellDatMapNew;
			}
		}
	}
	
    public static void createExcelStyle(List<Map<String, String>> rowList ,List<ColumnStyle> columnStyles ,Map<String,ColumnDefine> columnDefineMap,String title,String excelFullPath){
 		Map<String, SheetStyle> excelStyle = new HashMap<String, SheetStyle>();
		Map<String , Map<String, DataSet>> excelData = new HashMap<String, Map<String,DataSet>>();
		SheetStyle sheetStyle = new SheetStyle();
		sheetStyle.columnStyleList = columnStyles;
		excelStyle.put("sheet1", sheetStyle);
		CellRange cellRange = new CellRange();
		cellRange.firstRow = 0;
		cellRange.lastRow = 0;
		cellRange.firstColumn = 0;
		cellRange.lastColumn = columnStyles.size()-1;
		List<CellRange> cellRanges = new ArrayList<CellRange>();
		cellRanges.add(cellRange);
		sheetStyle.cellRangeList = cellRanges;
		sheetStyle.name = "sheet1";
		
		List<RowStyle> rowStyles = new ArrayList<RowStyle>();
		sheetStyle.rowStyleList = rowStyles;
		RowStyle rowStyleHead = new RowStyle();
		rowStyleHead.setSheetStyle(sheetStyle);
		rowStyles.add(rowStyleHead);
		rowStyleHead.rowHeight = 30*20;
		List<ZhCell> zhCells = new ArrayList<ZhCell>();
		rowStyleHead.cellList = zhCells;
		ZhCell zhCell = new ZhCell();
		zhCell.setRowStyle(rowStyleHead);
		zhCells.add(zhCell);
		zhCell.cellStyleValue = title;
		zhCell.cellType = 1;
		ZhCellStyle zhCellStyle = new ZhCellStyle();
		zhCellStyle.setZhCell(zhCell);
		zhCell.cellStyle = zhCellStyle;
		ZhFont font = new ZhFont();
		font.setBoldWeight((short)700);
		font.setFontHeightInPoints((short)12);
		zhCellStyle.setZhFont(font);
		zhCellStyle.setFontIndex((short)1);
		zhCellStyle.setCellStyleIndex((short)1);
		
		RowStyle dataHeadRowStyle = new RowStyle();
		dataHeadRowStyle.setSheetStyle(sheetStyle);
		rowStyles.add(dataHeadRowStyle);
		dataHeadRowStyle.rowHeight = 20*20;
		List<ZhCell> dataHeadZhCells = new ArrayList<ZhCell>();
		dataHeadRowStyle.cellList = dataHeadZhCells;
		
		RowStyle firstDataRowStyle = new RowStyle();
		firstDataRowStyle.setSheetStyle(sheetStyle);
		rowStyles.add(firstDataRowStyle);
		firstDataRowStyle.rowHeight = 20*20;
    	List<ZhCell> firstDataZhCells = new ArrayList<ZhCell>();
    	firstDataRowStyle.cellList = firstDataZhCells;
    	int columnIndex =0;
    	int numColumn = columnStyles.size();
		for(ColumnStyle columnStyle : columnStyles){
			
			String label = columnStyle.label;
			if(label.contains(".")){
				label = label.replace(".", "_");
    		}
			ZhCell dataHeadZhCell = new ZhCell();
			dataHeadZhCell.setRowStyle(dataHeadRowStyle);
    		dataHeadZhCells.add(dataHeadZhCell);
    		dataHeadZhCell.cellStyleValue = label;
    		dataHeadZhCell.cellType = 1;
    		ZhCellStyle headzhCellStyle = new ZhCellStyle();
    		headzhCellStyle.setZhCell(dataHeadZhCell);
    		dataHeadZhCell.cellStyle = headzhCellStyle;
    		ZhFont headFont = new ZhFont();
    		headFont.setBoldWeight((short)700);
    		headFont.setFontHeightInPoints((short)10);
    		headzhCellStyle.setZhFont(headFont);
    		headzhCellStyle.setFontIndex((short)2);
    		headzhCellStyle.setVerticalAlignment( ZhCellStyle.VERTICAL_CENTER );

    		headzhCellStyle.setBorderLeft( ZhCellStyle.BORDER_THIN );
    		headzhCellStyle.setBorderTop( ZhCellStyle.BORDER_THIN );
    		headzhCellStyle.setBorderRight( ZhCellStyle.BORDER_THIN );
    		headzhCellStyle.setBorderBottom( ZhCellStyle.BORDER_THIN );
    		headzhCellStyle.setCellStyleIndex((short)(columnIndex+2));
    		String name = columnStyle.name;
    		String alias = name;
    		if(alias.contains(".")){
    			alias = alias.replace(".", "_");
    		}
    		ZhCell firstDataZhCell = new ZhCell();
    		firstDataZhCell.setRowStyle(firstDataRowStyle);
    		firstDataZhCells.add(firstDataZhCell);
    		firstDataZhCell.cellStyleValue = "data."+alias+"";
    		firstDataZhCell.cellType = 1;
    		
    		ZhCellStyle firstDatazhCellStyle = new ZhCellStyle();
    		firstDatazhCellStyle.setZhCell(firstDataZhCell);
    		firstDataZhCell.cellStyle = firstDatazhCellStyle;
    		String align = columnStyle.align;
    		if("left".equals(align)){
    			firstDatazhCellStyle.setAlignment(ZhCellStyle.ALIGN_LEFT);
    		}else if("right".equals(align)){
    			firstDatazhCellStyle.setAlignment(ZhCellStyle.ALIGN_RIGHT);
    		}else{
    			firstDatazhCellStyle.setAlignment(ZhCellStyle.ALIGN_CENTER);
    		}
    		firstDatazhCellStyle.setBorderLeft( ZhCellStyle.BORDER_THIN );
    		firstDatazhCellStyle.setBorderTop( ZhCellStyle.BORDER_THIN );
    		firstDatazhCellStyle.setBorderRight( ZhCellStyle.BORDER_THIN );
    		firstDatazhCellStyle.setBorderBottom( ZhCellStyle.BORDER_THIN );
    		firstDatazhCellStyle.setCellStyleIndex((short)(columnIndex+2+numColumn));
    		columnIndex++;
		}
		
		Map<String, DataSet> dataSetMap = new HashMap<String, DataSet>();
		excelData.put("sheet1", dataSetMap);
		DataSet dataSet = new DataSet();
		dataSetMap.put("data", dataSet);
		
		dataSet.name = "data";
		dataSet.rowList = rowList;
		dataSet.columnDefineMap = columnDefineMap;
		
		writeExcelByCustom(excelStyle, excelData, excelFullPath);
	}
    public static void createExcelStylePlanCount(List<Map<String, String>> rowList ,List<ColumnStyle> columnStyles ,Map<String,ColumnDefine> columnDefineMap,String title,String excelFullPath,List<Integer> cellRanges2){
 		Map<String, SheetStyle> excelStyle = new HashMap<String, SheetStyle>();
		Map<String , Map<String, DataSet>> excelData = new HashMap<String, Map<String,DataSet>>();
		SheetStyle sheetStyle = new SheetStyle();
		sheetStyle.columnStyleList = columnStyles;
		excelStyle.put("sheet1", sheetStyle);
		CellRange cellRange = new CellRange();
		cellRange.firstRow = 0;
		cellRange.lastRow = 0;
		cellRange.firstColumn = 0;
		cellRange.lastColumn = columnStyles.size()-1;
		List<CellRange> cellRanges = new ArrayList<CellRange>();
		cellRanges.add(cellRange);
		for(Integer in:cellRanges2){
			CellRange cellRanged = new CellRange();
			cellRange.firstRow= in;
			cellRange.lastRow = in;
			cellRange.firstColumn = 0;
			cellRange.lastColumn = columnStyles.size()-1;
			cellRanges.add(cellRanged);
		}
		sheetStyle.cellRangeList = cellRanges;
		sheetStyle.name = "sheet1";
		
		List<RowStyle> rowStyles = new ArrayList<RowStyle>();
		sheetStyle.rowStyleList = rowStyles;
		RowStyle rowStyleHead = new RowStyle();
		rowStyleHead.setSheetStyle(sheetStyle);
		rowStyles.add(rowStyleHead);
		rowStyleHead.rowHeight = 30*20;
		List<ZhCell> zhCells = new ArrayList<ZhCell>();
		rowStyleHead.cellList = zhCells;
		ZhCell zhCell = new ZhCell();
		zhCell.setRowStyle(rowStyleHead);
		zhCells.add(zhCell);
		zhCell.cellStyleValue = title;
		zhCell.cellType = 1;
		ZhCellStyle zhCellStyle = new ZhCellStyle();
		zhCellStyle.setZhCell(zhCell);
		zhCell.cellStyle = zhCellStyle;
		ZhFont font = new ZhFont();
		font.setBoldWeight((short)700);
		font.setFontHeightInPoints((short)12);
		zhCellStyle.setZhFont(font);
		zhCellStyle.setFontIndex((short)1);
		zhCellStyle.setCellStyleIndex((short)1);
		
		RowStyle dataHeadRowStyle = new RowStyle();
		dataHeadRowStyle.setSheetStyle(sheetStyle);
		rowStyles.add(dataHeadRowStyle);
		dataHeadRowStyle.rowHeight = 20*20;
		List<ZhCell> dataHeadZhCells = new ArrayList<ZhCell>();
		dataHeadRowStyle.cellList = dataHeadZhCells;
		
		RowStyle firstDataRowStyle = new RowStyle();
		firstDataRowStyle.setSheetStyle(sheetStyle);
		rowStyles.add(firstDataRowStyle);
		firstDataRowStyle.rowHeight = 20*20;
    	List<ZhCell> firstDataZhCells = new ArrayList<ZhCell>();
    	firstDataRowStyle.cellList = firstDataZhCells;
    	int columnIndex =0;
    	int numColumn = columnStyles.size();
		for(ColumnStyle columnStyle : columnStyles){
			
			String label = columnStyle.label;
			if(label.contains(".")){
				label = label.replace(".", "_");
    		}
			ZhCell dataHeadZhCell = new ZhCell();
			dataHeadZhCell.setRowStyle(dataHeadRowStyle);
    		dataHeadZhCells.add(dataHeadZhCell);
    		dataHeadZhCell.cellStyleValue = label;
    		dataHeadZhCell.cellType = 1;
    		ZhCellStyle headzhCellStyle = new ZhCellStyle();
    		headzhCellStyle.setZhCell(dataHeadZhCell);
    		dataHeadZhCell.cellStyle = headzhCellStyle;
    		ZhFont headFont = new ZhFont();
    		headFont.setBoldWeight((short)700);
    		headFont.setFontHeightInPoints((short)10);
    		headzhCellStyle.setZhFont(headFont);
    		headzhCellStyle.setFontIndex((short)2);
    		headzhCellStyle.setVerticalAlignment( ZhCellStyle.VERTICAL_CENTER );

    		headzhCellStyle.setBorderLeft( ZhCellStyle.BORDER_THIN );
    		headzhCellStyle.setBorderTop( ZhCellStyle.BORDER_THIN );
    		headzhCellStyle.setBorderRight( ZhCellStyle.BORDER_THIN );
    		headzhCellStyle.setBorderBottom( ZhCellStyle.BORDER_THIN );
    		headzhCellStyle.setCellStyleIndex((short)(columnIndex+2));
    		String name = columnStyle.name;
    		String alias = name;
    		if(alias.contains(".")){
    			alias = alias.replace(".", "_");
    		}
    		ZhCell firstDataZhCell = new ZhCell();
    		firstDataZhCell.setRowStyle(firstDataRowStyle);
    		firstDataZhCells.add(firstDataZhCell);
    		firstDataZhCell.cellStyleValue = "data."+alias+"";
    		firstDataZhCell.cellType = 1;
    		
    		ZhCellStyle firstDatazhCellStyle = new ZhCellStyle();
    		firstDatazhCellStyle.setZhCell(firstDataZhCell);
    		firstDataZhCell.cellStyle = firstDatazhCellStyle;
    		String align = columnStyle.align;
    		if("left".equals(align)){
    			firstDatazhCellStyle.setAlignment(ZhCellStyle.ALIGN_LEFT);
    		}else if("right".equals(align)){
    			firstDatazhCellStyle.setAlignment(ZhCellStyle.ALIGN_RIGHT);
    		}else{
    			firstDatazhCellStyle.setAlignment(ZhCellStyle.ALIGN_CENTER);
    		}
    		firstDatazhCellStyle.setBorderLeft( ZhCellStyle.BORDER_THIN );
    		firstDatazhCellStyle.setBorderTop( ZhCellStyle.BORDER_THIN );
    		firstDatazhCellStyle.setBorderRight( ZhCellStyle.BORDER_THIN );
    		firstDatazhCellStyle.setBorderBottom( ZhCellStyle.BORDER_THIN );
    		firstDatazhCellStyle.setCellStyleIndex((short)(columnIndex+2+numColumn));
    		columnIndex++;
		}
		
		Map<String, DataSet> dataSetMap = new HashMap<String, DataSet>();
		excelData.put("sheet1", dataSetMap);
		DataSet dataSet = new DataSet();
		dataSetMap.put("data", dataSet);
		
		dataSet.name = "data";
		dataSet.rowList = rowList;
		dataSet.columnDefineMap = columnDefineMap;
		
		writeExcelByCustom(excelStyle, excelData, excelFullPath);
	}
    
    public static String outPutActionToExcel(String colDefineStr,String title,String excelFullPath,List<Map<String, String>> dataList){
    	JSONObject colArr = JSONObject.fromObject(colDefineStr);
    	String[] colNameArr = new String[colArr.size()];
    	int colIndex = 0;
    	Iterator colIt = colArr.keys();
    	List<ColumnStyle> columnStyles = new ArrayList<ColumnStyle>();
    	Map<String,ColumnDefine> columnDefineMap = new HashMap<String, ColumnDefine>();
    	while (colIt.hasNext()) {
			String	key = colIt.next().toString();
			JSONObject col = JSONObject.fromObject(colArr.get(key));
    		String label = col.getString("label");
    		String name = col.getString("name");
    		String width = col.getString("width");
    		String align = col.getString("align");
    		String alias = name;
    		if(alias.contains(".")){
    			alias = alias.replace(".", "_");
    		}
    		colNameArr[colIndex] = name;
    		
    		ColumnStyle columnStyle = new ColumnStyle();
    		columnStyles.add(columnStyle);
    		columnStyle.name = name;
    		columnStyle.columnNum = colIndex;
    		columnStyle.colnumWidth = Integer.parseInt(width);
    		columnStyle.columnHidden =false;
    		columnStyle.align = align;
    		columnStyle.label = label;
    		
    		ColumnDefine columnDefine = new ColumnDefine();
    		columnDefine.name = name;
    		String type = col.getString("type");
    		int dataType = 1;
    		if("integer".equals(type)){
    			dataType = 2;
    		}else if("number".equals(type)||"currency".equals(type)){
    			dataType = 3;
    		}else if("checkbox".equals(type)){
    			dataType = 5;
    		}
    		columnDefine.type = dataType;
    		columnDefineMap.put(name,columnDefine);
    		colIndex++;
		}
    	//title = request.getParameter("title");
    	//excelFullPath = request.getRealPath( "//home//temporary//");
    	if(!excelFullPath.contains(".xls")){
    		excelFullPath += "//"+DateUtil.convertDateToString("yyyyMMddhhmmss",Calendar.getInstance().getTime())+".xls";
    	}
    	createExcelStyle(dataList,columnStyles,columnDefineMap,title,excelFullPath);
    	return excelFullPath+"@@"+title+".xls";
    }
    
    public static Matcher matchBrace(String cellValue){
    	Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(cellValue);
        return matcher;
    }
    
	public static void main(String[] args) {
		/*XSSFWorkbook workbook = new XSSFWorkbook();
		for(int sheetIndex=0;sheetIndex<10;sheetIndex++){
			XSSFSheet sheet = workbook.createSheet();
			for(int rowIndex=0;rowIndex<20;rowIndex++){
				XSSFRow row = sheet.createRow(rowIndex);
				for(int cellIndex=0;cellIndex<20;cellIndex++){
					XSSFCell cell = row.createCell(cellIndex);
					CellStyle cellStyle = workbook.createCellStyle();
					XSSFFont font = workbook.createFont();
					font.setFontName("黑体");
					font.setFontHeightInPoints((short) 20);//设置字体大小
					cellStyle.setFont(font);
					cell.setCellStyle(cellStyle);
					cell.setCellValue("小白");
				}
			}
		}
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("D://aaaaa3.xls");
			workbook.write(fileOut);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		/*List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("key", "v1");
		dataList.add(dataMap);
		Map<String, String> dataMapT = dataList.get(0);
		dataMapT.put("key", "v2");*/
		//System.out.println(dataList.get(0).get("key"));
		String cellValue = "{data.qa}asdfas{data.qa}";
		Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(cellValue);
        String replaceStr = "";
        int numGroup = matcher.groupCount();
        if(matcher.find()){
        	for(int groupIndex=1;groupIndex<=numGroup;groupIndex++){
        		replaceStr = matcher.group();
        		replaceStr = matcher.group(groupIndex);
        		cellValue = cellValue.replace("{"+replaceStr+"}", "1111");
        	}
        }
        System.out.println(cellValue);
	}
}
