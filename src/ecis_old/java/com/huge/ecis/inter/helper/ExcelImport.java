package com.huge.ecis.inter.helper;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.common.query.NullSQLType;
import com.huge.common.util.ColumnDef;
import com.huge.common.util.ExportHelper;
import com.huge.common.util.ImportException;
import com.huge.foundation.common.GeneralAppException;
import com.huge.util.DateUtil;

public class ExcelImport implements ImportService {
	String	insert	= "";
	
	private static void importData(JdbcTemplate jdbcTemplate, Sheet excelSheet, String tableName) throws Exception {
		String insertSql = "insert into " + tableName + " (";
		String selectSql = "select ";
		String valueSql = "";
		ArrayList columnNameList = new ArrayList();
		Row firstRow = excelSheet.getRow(0);
		for (int i = 0;; i++)
			try {
				Cell cell = firstRow.getCell(i);
				if (cell != null) {
					String excelCellValue = cell.getStringCellValue();
					if ((excelCellValue != null) && (excelCellValue.trim().equals("")))
						break;
					columnNameList.add(replaceCellValue(excelCellValue));
				} else
					break;
			} catch (Exception localException1) {
				break;
			}
		String[] cellValueArray = (String[]) (String[]) columnNameList.toArray(new String[columnNameList.size()]);
		for (int j = 0; j < cellValueArray.length; j++) {
			insertSql = insertSql + cellValueArray[j] + ",";
			selectSql = selectSql + cellValueArray[j] + ",";
			valueSql = valueSql + "?,";
		}
		insertSql = insertSql.substring(0, insertSql.length() - 1);
		selectSql = selectSql.substring(0, selectSql.length() - 1) + " from " + tableName + " where 1=2";
		valueSql = valueSql.substring(0, valueSql.length() - 1);
		insertSql = insertSql + ") values(" + valueSql + ")";
		
		ColumnDef[] columnDefs = ExportHelper.getColumnDefs(jdbcTemplate, selectSql);
		int count = 0;
		for (int j = 1;; j++) {
			ArrayList sqlParaList = new ArrayList();
//			if (isEnd(excelSheet, j, cellValueArray.length))
//				break;
			Row row = excelSheet.getRow(j);
		if(row==null)
			break;
			for (int i = 0; i < cellValueArray.length; i++)
				try {
					Cell cell = row.getCell(i);
					String cellValue=null;
					if (cell != null) {
						 cellValue = getValue(cell);
						//cellValue = cell.getStringCellValue();
						
						if (cellValue.equals("null")) {
							sqlParaList.add(new NullSQLType(columnDefs[i].getType()));
						} else {
							int k = columnDefs[i].getType();
							if ((k == -100) || (k == 91) || (k == 93)) {
							if ((cellValue.length() <= 10) && (cellValue.length()>= 7))
						 try {
						 sqlParaList.add(java.sql.Date.valueOf(cellValue));
						 } catch (Exception localException3) {
						 sqlParaList.add(new
						 NullSQLType(columnDefs[i].getType()));
						 }
						 else if (cellValue.length() >= 17)
						 try {
						 sqlParaList.add(Timestamp.valueOf(cellValue));
						 } catch (Exception localException4) {
						 sqlParaList.add(new
						 NullSQLType(columnDefs[i].getType()));
						 }
						 else
						 sqlParaList.add(new
						 NullSQLType(columnDefs[i].getType()));
						 } else if ((k == 3) || (k == 4) || (k == 5) || (k ==
						 6) || (k == 7) || (k == 8)) {
						 if ((cellValue == null) ||
						 ("".equals(cellValue.trim())))
						 sqlParaList.add(new BigDecimal("0"));
						 else
						 sqlParaList.add(cellValue.trim());
						 } else if (k == -7) {// add by Mr.fdc 20121216
						 sqlParaList.add(Boolean.parseBoolean(cellValue));
						
						 }
						
						 else
						 sqlParaList.add(cellValue);
						 }
						/*int k = columnDefs[i].getType();
						int cellK = cell.getCellType();
						//k = cellK;
						if ((k == -100) || (k == 91) || (k == 93)) {
							sqlParaList.add(cell.getDateCellValue());
						}else if ((k == 3) || (k == 4) || (k == 5) || (k ==6) || (k == 7) || (k == 8)){
							sqlParaList.add(cell.getNumericCellValue());
						}else if (k == -7){
							
							try{
							sqlParaList.add(cell.getBooleanCellValue());
							}catch(Exception e){
								try{
								BooleanConverter bc =	new BooleanConverter();
								//bc.convert(Boolean.class, cell.getNumericCellValue());
								sqlParaList.add(bc.convert(Boolean.class, ((double)cell.getNumericCellValue())>0?1:0));
								}catch(Exception e1){
									BooleanConverter bc =	new BooleanConverter();
									//bc.convert(Boolean.class, cell.getNumericCellValue());
									sqlParaList.add(bc.convert(Boolean.class, cell.getStringCellValue()));
								}
							}
							
						}else{
							sqlParaList.add(cell.getStringCellValue());
						}*/
					}else{
						//sqlParaList.add(new NullSQLType(columnDefs[i].getType()));
						sqlParaList.add(null);
					}
				} catch (Exception localException2) {
					break;
				}
			/*
			 * if (i < arrayOfString.length) break;
			 */
			Object[] sqlParamArray = sqlParaList.toArray(new Object[sqlParaList.size()]);
			// paramSQLTransaction.executeUpdate(str1, arrayOfObject);
			
			if(sqlParamArray.length<27){
				System.out.println();
			}
			count ++;
			jdbcTemplate.update(insertSql, sqlParamArray);
		}
	}
	
	private static boolean isEnd(Sheet paramSheet, int paramInt1, int paramInt2) {
		try {
			for (int i = 0; i < paramInt2; i++) {
				Row row = paramSheet.getRow(paramInt1);
				Cell localCell = row.getCell(i);
				String str = null;
				
				str = localCell.getStringCellValue();
				if ("<END>".equals(str))
					return true;
				if ((str != null) && (str.length() > 0))
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}
		return true;
	}
	
	private static String replaceCellValue(String paramString) {
//		paramString = StringUtil.replaceString(paramString, "(", "");
//		paramString = StringUtil.replaceString(paramString, ")", "");
//		paramString = StringUtil.replaceString(paramString, "（", "");
//		paramString = StringUtil.replaceString(paramString, "）", "");
//		paramString = StringUtil.replaceString(paramString, "-", "");
//		paramString = StringUtil.replaceString(paramString, "_", "");
//		paramString = StringUtil.replaceString(paramString, "/", "");
//		paramString = StringUtil.replaceString(paramString, " ", "");
		return paramString;
	}
	
	private static void importData(JdbcTemplate paramSQLTransaction, String paramString1, String paramString2, String paramString3) throws ImportException {
		try {
			// Workbook localWorkbook = Workbook.getWorkbook(new
			// File(paramString1));
			Workbook book = WorkbookFactory.create(new FileInputStream(paramString1));
			Sheet localSheet = book.getSheet(paramString2);
			importData(paramSQLTransaction, localSheet, paramString3);
			// book.close();
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new ImportException(localException.getMessage());
		}
	}
	
	public static void importExcel(JdbcTemplate simpleJdbcTemplate, String paramString1, int paramInt, String paramString2) throws ImportException {
		try {
			Workbook book = WorkbookFactory.create(new FileInputStream(paramString1));
			Sheet localSheet = book.getSheetAt(paramInt);
			if (localSheet != null)
				importData(simpleJdbcTemplate, localSheet, paramString2);
			// book.close();
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new ImportException(localException.getMessage());
		}
	}
	
	public void importData(JdbcTemplate jdbcTemplate, String url, String username, String password, String execsql, String tempTable) {
		try {
			if ((execsql == null) || (execsql.trim().equals("")))
				importExcel(jdbcTemplate, url, 0, tempTable);
			else
				importData(jdbcTemplate, url, execsql, tempTable);
		} catch (Exception localException) {
			
			throw new GeneralAppException("数据采集失败," + localException.getMessage());
		}
	}
	
	public static String getValue(Cell cell) {
		int cellType = cell.getCellType();
		String value = "";
		switch (cellType) {   
		  
        case Cell.CELL_TYPE_FORMULA:  
        	try {
        		value = ""+cell.getNumericCellValue();
			} catch (Exception e) {
				value = ""+cell.getStringCellValue();
			}
            break;   

        case Cell.CELL_TYPE_NUMERIC:   
            if(HSSFDateUtil.isCellDateFormatted(cell)){   
            	Date dateValue = cell.getDateCellValue();
                value = ""  
                    + DateUtil.convertDateToString(dateValue);   
            }else{   
                value = ""  
                        + cell.getNumericCellValue();   
            }   
               
            break;   

        case Cell.CELL_TYPE_STRING:   
            value = ""  
                    + cell.getStringCellValue();   
            break;   
               
        case Cell.CELL_TYPE_BOOLEAN:   
            value = ""  
                    + cell.getBooleanCellValue();   
               
            break;   

        default:   
        }   
		// String str = paramCell.getContents();
		// Object localObject;
		// if ((paramCell.getType().equals(CellType.NUMBER)) ||
		// (paramCell.getType().equals(CellType.NUMBER_FORMULA))) {
		// localObject = (NumberCell) paramCell;
		// str = ((NumberCell) localObject).getContents();
		// if (str.endsWith(".0"))
		// str = str.substring(0, str.length() - 2);
		// } else if (paramCell.getType().equals(CellType.DATE)) {
		// localObject = (DateCell) paramCell;
		// java.util.Date localDate = ((DateCell) localObject).getDate();
		// SimpleDateFormat localSimpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// str = localSimpleDateFormat.format(localDate);
		// }
		// return (String) str;
		return value;
	}
	
	// @Override
	// public void importData(SimpleJdbcTemplate simpleJdbcTemplate, String url,
	// String username, String password, String execsql, String tempTable,
	// Hashtable paraTable) {
	// }
}

/*
 * Location:
 * D:\EclipseWorkspaces\OldWorkspace\ecis2.5_20110809\WebRoot\WEB-INF\lib
 * \inter-model.jar Qualified Name: com.huge.ecis.inter.helper.ExcelImport
 * JD-Core Version: 0.6.0
 */
