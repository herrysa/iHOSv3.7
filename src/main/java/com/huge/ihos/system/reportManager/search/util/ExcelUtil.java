package com.huge.ihos.system.reportManager.search.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.common.util.ImportException;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.reportManager.search.dao.QueryDao;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.util.OtherUtil;

public class ExcelUtil {

    @Autowired
    QueryDao queryDao;

    @Autowired
    private QueryManager queryManager;

    public void setQueryManager( QueryManager queryManager ) {
        this.queryManager = queryManager;
    }

    public void setQueryDao( QueryDao queryDao ) {
        this.queryDao = queryDao;
    }

    public static void importExcel( JdbcTemplate jdbcTemplate, String filePath )
        throws ImportException {
        try {
            Workbook book = WorkbookFactory.create( new FileInputStream( filePath ) );
            int sheetNum = book.getNumberOfSheets();
            for ( int i = 0; i < sheetNum; i++ ) {
                Sheet sheet = book.getSheetAt( i );
                importExcelSheet( jdbcTemplate, sheet );
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
            throw new ImportException( e.getMessage() );
        }

    }

    private static void importExcelSheet( JdbcTemplate jdbcTemplate, Sheet sheet )
        throws Exception {
        String tableName;
        try {
            tableName = sheet.getSheetName();
        }
        catch ( Exception localException1 ) {
            return;
        }
        String insertSql = "insert into " + tableName + " (";
        String selectSql = "select ";
        String valueSql = "";
        ArrayList columnNameList = new ArrayList();
        Row firstRow = sheet.getRow( 0 );
        for ( int i = 0;; i++ )
            try {
                Cell cell = firstRow.getCell( i );
                if ( cell != null ) {
                    String excelCellValue = cell.getStringCellValue();
                    if ( ( excelCellValue != null ) && ( excelCellValue.trim().equals( "" ) ) )
                        break;
                    columnNameList.add( excelCellValue );
                }
                else
                    break;
            }
            catch ( Exception localException1 ) {
                break;
            }

        String[] cellValueArray = new String[columnNameList.size()];
        columnNameList.toArray( cellValueArray );

        if ( cellValueArray.length == 0 )
            return;
        for ( int j = 0; j < cellValueArray.length; j++ ) {
            insertSql = insertSql + cellValueArray[j] + ",";
            selectSql = selectSql + cellValueArray[j] + ",";
            valueSql = valueSql + "?,";
        }
        insertSql = insertSql.substring( 0, insertSql.length() - 1 );
        selectSql = selectSql.substring( 0, selectSql.length() - 1 ) + " from " + tableName + " where 1=2";
        valueSql = valueSql.substring( 0, valueSql.length() - 1 );
        insertSql = insertSql + ") values(" + valueSql + ")";

        ColumnDef[] columnDefs = ExportHelper.getColumnDefs( jdbcTemplate, selectSql );

        for ( int j = 1;; j++ ) {
            ArrayList sqlParaList = new ArrayList();

            Row row = sheet.getRow( j );
            if ( row == null )
                break;
            for ( int i = 0; i < cellValueArray.length; i++ )
                try {
                    Cell cell = row.getCell( i );
                    //System.out.println("current process colum is: " + columnDefs[i].getFieldName() + " type is: " + columnDefs[i].getType());
                    if ( cell != null && !cell.toString().equals( "" ) ) {
                        int k = columnDefs[i].getType();
                        if ( ( k == -100 ) || ( k == 91 ) || ( k == 93 ) ) {
                            sqlParaList.add( cell.getDateCellValue() );
                        }
                        else if ( ( k == 3 ) || ( k == 4 ) || ( k == 5 ) || ( k == 6 ) || ( k == 7 ) || ( k == 8 ) ) {
                            sqlParaList.add( cell.getNumericCellValue() );
                        }
                        else if ( k == -7 ) {

                            try {
                                sqlParaList.add( cell.getBooleanCellValue() );
                            }
                            catch ( Exception e ) {
                                try {
                                    BooleanConverter bc = new BooleanConverter();
                                    sqlParaList.add( bc.convert( Boolean.class, ( (double) cell.getNumericCellValue() ) > 0 ? 1 : 0 ) );
                                }
                                catch ( Exception e1 ) {
                                    BooleanConverter bc = new BooleanConverter();
                                    sqlParaList.add( bc.convert( Boolean.class, cell.getStringCellValue() ) );
                                }
                            }

                        }
                        else {
                            sqlParaList.add( cell.getStringCellValue() );
                        }
                    }
                    else {
                        sqlParaList.add( null );
                    }
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                    throw new BusinessException( e.getMessage() );
                }
            Object[] sqlParamArray = sqlParaList.toArray( new Object[sqlParaList.size()] );
            jdbcTemplate.update( insertSql, sqlParamArray );
        }
    }

    public static void exportExcelBySQL( JdbcTemplate jdbcTemplate, String[] sqlStrs, OutputStream outS ) {

        try {
            Workbook workbook = new HSSFWorkbook();
            for ( int i = 0; i < sqlStrs.length; i++ ) {
                String tableName = getTableName( i, getTableName( sqlStrs[i] ), sqlStrs );
                Sheet sheet = workbook.createSheet( tableName );
                ColumnDef[] columnDefs = ExportHelper.getColumnDefs( jdbcTemplate, sqlStrs[i] );
                List list = jdbcTemplate.queryForList( sqlStrs[i] ); // $1.executeQuery(paramConnection,
                // paramArrayOfString[i]);

                // 首行

                // 数据行
                Iterator itr = list.iterator();
                int rowIndex = 0;
                while ( itr.hasNext() ) {

                    Map rowMap = (Map) itr.next();
                    if ( rowIndex == 0 ) {
                        Row row = sheet.createRow( rowIndex );
                        int cellIndex = 0;
                        Set set = rowMap.entrySet();
                        Iterator itr0 = set.iterator();

                        while ( itr0.hasNext() ) {
                            Cell cell = row.createCell( cellIndex );
                            cellIndex++;
                            Entry key = (Entry) itr0.next();
                            if ( key.getKey() != null ) {
                                cell.setCellType( Cell.CELL_TYPE_STRING );
                                cell.setCellValue( key.getKey().toString() );

                            }
                            else {
                                cell.setCellType( Cell.CELL_TYPE_STRING );
                                cell.setCellValue( "" );
                            }
                        }
                        rowIndex++;
                    }
                    Row row = sheet.createRow( rowIndex );
                    rowIndex++;

                    // Map rowMap = (Map) itr.next();
                    int cellIndex = 0;
                    Set set = rowMap.entrySet();
                    Iterator itr0 = set.iterator();

                    while ( itr0.hasNext() ) {
                        Cell cell = row.createCell( cellIndex );

                        Entry key = (Entry) itr0.next();
                        if ( key.getValue() != null ) {
                            int k = columnDefs[cellIndex].getType();
                            if ( ( k == 3 ) || ( k == 4 ) || ( k == 5 ) || ( k == 6 ) || ( k == 7 ) || ( k == 8 ) ) {
                                cell.setCellType( Cell.CELL_TYPE_NUMERIC );
                                cell.setCellValue( Double.parseDouble( key.getValue().toString() ) );
                            }
                            else {

                                cell.setCellType( Cell.CELL_TYPE_STRING );
                                cell.setCellValue( key.getValue().toString() );
                            }
                        }
                        else {
                            cell.setCellType( Cell.CELL_TYPE_BLANK );
                            cell.setCellValue( "" );
                        }
                        cellIndex++;
                    }
                }

            }

            workbook.write( outS );

        }
        catch ( Exception e ) {
            e.printStackTrace();
            throw new BusinessException( e.getMessage() );
        }

    }
    public static void exportExcelBySQL( JdbcTemplate jdbcTemplate, String sql, OutputStream outS ) {
    	
    	try {
    		Workbook workbook = new HSSFWorkbook();
    			String tableName = getTableName(sql);
    			Sheet sheet = workbook.createSheet( tableName );
    			ColumnDef[] columnDefs = ExportHelper.getColumnDefs( jdbcTemplate, sql );
    			List list = jdbcTemplate.queryForList( sql ); // $1.executeQuery(paramConnection,
    			// paramArrayOfString[i]);
    			
    			// 首行
    			
    			// 数据行
    			Iterator itr = list.iterator();
    			int rowIndex = 0;
    			while ( itr.hasNext() ) {
    				
    				Map rowMap = (Map) itr.next();
    				if ( rowIndex == 0 ) {
    					Row row = sheet.createRow( rowIndex );
    					int cellIndex = 0;
    					Set set = rowMap.entrySet();
    					Iterator itr0 = set.iterator();
    					
    					while ( itr0.hasNext() ) {
    						Cell cell = row.createCell( cellIndex );
    						cellIndex++;
    						Entry key = (Entry) itr0.next();
    						if ( key.getKey() != null ) {
    							cell.setCellType( Cell.CELL_TYPE_STRING );
    							cell.setCellValue( key.getKey().toString() );
    							
    						}
    						else {
    							cell.setCellType( Cell.CELL_TYPE_STRING );
    							cell.setCellValue( "" );
    						}
    					}
    					rowIndex++;
    				}
    				Row row = sheet.createRow( rowIndex );
    				rowIndex++;
    				
    				// Map rowMap = (Map) itr.next();
    				int cellIndex = 0;
    				Set set = rowMap.entrySet();
    				Iterator itr0 = set.iterator();
    				
    				while ( itr0.hasNext() ) {
    					Cell cell = row.createCell( cellIndex );
    					
    					Entry key = (Entry) itr0.next();
    					if ( key.getValue() != null ) {
    						int k = columnDefs[cellIndex].getType();
    						if ( ( k == 3 ) || ( k == 4 ) || ( k == 5 ) || ( k == 6 ) || ( k == 7 ) || ( k == 8 ) ) {
    							cell.setCellType( Cell.CELL_TYPE_NUMERIC );
    							cell.setCellValue( Double.parseDouble( key.getValue().toString() ) );
    						}
    						else {
    							
    							cell.setCellType( Cell.CELL_TYPE_STRING );
    							cell.setCellValue( key.getValue().toString() );
    						}
    					}
    					else {
    						cell.setCellType( Cell.CELL_TYPE_BLANK );
    						cell.setCellValue( "" );
    					}
    					cellIndex++;
    				}
    			}
    			
    		
    		workbook.write( outS );
    		
    	}
    	catch ( Exception e ) {
    		e.printStackTrace();
    		throw new BusinessException( e.getMessage() );
    	}
    	
    }
    
    public static void exportExcelByObject( List list,HttpServletResponse resp,String fileName ) {
    	
    	try {
    		resp.setContentType( "application/vnd.ms-excel" );
    		Workbook workbook = new HSSFWorkbook();
    			Sheet sheet = workbook.createSheet( fileName );
    			resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes( "GBK" ), "ISO8859-1" ) );//"ISO8859-1"
    	        OutputStream outS = resp.getOutputStream();
    			// 首行
    			
    			// 数据行
    			Iterator itr = list.iterator();
    			int rowIndex = 0;
    			while ( itr.hasNext() ) {
    				
    				Map rowMap = (Map) itr.next();
    				if ( rowIndex == 0 ) {
    					Row row = sheet.createRow( rowIndex );
    					int cellIndex = 0;
    					Set set = rowMap.entrySet();
    					Iterator itr0 = set.iterator();
    					
    					while ( itr0.hasNext() ) {
    						Cell cell = row.createCell( cellIndex );
    						cellIndex++;
    						Entry key = (Entry) itr0.next();
    						if ( key.getKey() != null ) {
    							cell.setCellType( Cell.CELL_TYPE_STRING );
    							cell.setCellValue( key.getKey().toString() );
    							
    						}
    						else {
    							cell.setCellType( Cell.CELL_TYPE_STRING );
    							cell.setCellValue( "" );
    						}
    					}
    					rowIndex++;
    				}
    				Row row = sheet.createRow( rowIndex );
    				rowIndex++;
    				
    				// Map rowMap = (Map) itr.next();
    				int cellIndex = 0;
    				Set set = rowMap.entrySet();
    				Iterator itr0 = set.iterator();
    				
    				while ( itr0.hasNext() ) {
    					Cell cell = row.createCell( cellIndex );
    					
    					Entry key = (Entry) itr0.next();
    					if ( key.getValue() != null ) {
    							
    							cell.setCellType( Cell.CELL_TYPE_STRING );
    							cell.setCellValue( key.getValue().toString() );
    					}
    					else {
    						cell.setCellType( Cell.CELL_TYPE_BLANK );
    						cell.setCellValue( "" );
    					}
    					cellIndex++;
    				}
    			}
    			
    		
    		workbook.write( outS );
    		
    	}
    	catch ( Exception e ) {
    		e.printStackTrace();
    		throw new BusinessException( e.getMessage() );
    	}
    	
    }

    public static void exportExcelByList( List list, HttpServletResponse resp, SearchOption[] ops, String searchName ) {
        resp.setContentType( "application/vnd.ms-excel" );
        try {
            String title = "导出数据.xls";
            if ( OtherUtil.measureNotNull( searchName ) ) {
                title = searchName + ".xls";
                resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( title.getBytes( "GBK" ), "ISO8859-1" ) );//"ISO8859-1"
            }
            else {
                resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( title.getBytes( "GBK" ), "ISO8859-1" ) );//"ISO8859-1"
            }
            OutputStream outs = resp.getOutputStream();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            String[] titles = ExportHelper.getTitleData( ops );

            int lengthCell = titles.length - 1;
            sheet.addMergedRegion( new Region( 0, (short) 0, 1, (short) lengthCell ) );
            HSSFRow rowT = sheet.createRow( 0 );
            HSSFCell cellT = rowT.createCell( 0 );
            if ( OtherUtil.measureNotNull( searchName ) ) {
                cellT.setCellValue( searchName );
                cellT.setCellStyle( cellStyle( workbook, 12 ) );
            }
            else {
                cellT.setCellValue( "导出数据" );
                cellT.setCellStyle( cellStyle( workbook, 12 ) );
            }

            HSSFCellStyle style = cellStyle( workbook, 10 );

            int lengthRow = ExportHelper.maxLengthSearchOption( ops );
            int rowIndex = 2;
            for ( int i = 0; i <= lengthRow; i++ ) {
                HSSFRow row = sheet.createRow( rowIndex );
                for ( int j = 0; j < titles.length; j++ ) {
                    String[] columns = titles[j].split( "\\|" );
                    String cloumnPath = "";
                    for ( int k = 0; k <= i; k++ ) {
                        if ( k != i ) {
                            cloumnPath += columns[k] + "|";
                        }
                        else {
                            cloumnPath += columns[k];
                        }
                    }
                    HSSFCell cell = row.createCell( j );
                    cell.setCellValue( cloumnPath );
                    cell.setCellStyle( style );
                }
                int cellNum = row.getLastCellNum();
                for ( int ii = 0; ii < cellNum; ii++ ) {
                    HSSFCell cell = row.getCell( ii );
                    String cellValue = cell.getStringCellValue();
                    HSSFCell xinCell = row.createCell( ii );
                    if ( ( ii != cellNum - 1 ) && cellValue.equals( row.getCell( ii + 1 ).getStringCellValue() ) ) {
                        sheet.addMergedRegion( new Region( rowIndex, (short) ii, rowIndex, (short) ( ii + 1 ) ) );
                        String[] thisTitleArr = cellValue.split( "\\|" );
                        xinCell.setCellValue( thisTitleArr[thisTitleArr.length - 1] );
                        xinCell.setCellStyle( style );
                    }
                    else {
                        String[] thisTitleArr = cellValue.split( "\\|" );
                        xinCell.setCellValue( thisTitleArr[thisTitleArr.length - 1] );
                        xinCell.setCellStyle( style );
                    }
                }
                rowIndex++;
            }
            for ( int i = 2; i < lengthRow + 2; i++ ) {
                HSSFRow row = sheet.getRow( i );
                HSSFRow rowNext = sheet.getRow( i + 1 );
                for ( int ii = 0; ii < row.getLastCellNum(); ii++ ) {
                    System.out.println( row.getLastCellNum() + ":" + rowNext.getCell( ii ) );
                    if ( rowNext.getCell( ii ).getStringCellValue().equals( "@" ) ) {
                        sheet.addMergedRegion( new Region( i, (short) ii, i + 1, (short) ( ii ) ) );
                        row.getCell( ii ).setCellStyle( style );
                    }
                }

            }
            sheet.createFreezePane( 0, lengthRow + 3 );//冻结标题行
            // 数据行
            Iterator itr = list.iterator();
            //int rowIndex = lengthRow+1;
            ColumnDef[] columnDefData = ExportHelper.getColumnDefs( ops );
            HSSFCellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderLeft( HSSFCellStyle.BORDER_THIN );
            dataStyle.setBorderTop( HSSFCellStyle.BORDER_THIN );
            dataStyle.setBorderRight( HSSFCellStyle.BORDER_THIN );
            dataStyle.setBorderBottom( HSSFCellStyle.BORDER_THIN );

            while ( itr.hasNext() ) {
                Map rowMap = (Map) itr.next();
                HSSFRow row = sheet.createRow( rowIndex );
                rowIndex++;
                for ( int i = 0; i < columnDefData.length; i++ )
                    fillData( row, i, columnDefData, rowMap, dataStyle );
            }
            workbook.write( outs );
            outs.flush();
            outs.close();

        }
        catch ( Exception e ) {
            e.printStackTrace();
            throw new BusinessException( e.getMessage() );
        }

    }

    private static HSSFCellStyle cellStyle( HSSFWorkbook workbook, int size ) {
        HSSFFont font1 = workbook.createFont();
        font1.setFontHeightInPoints( (short) size );
        font1.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );

        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );

        style.setFont( font1 );
        return style;
    }

    /*	public static void exportExcelByList(List list, HttpServletResponse resp,SearchOption[] ops,String searchName) {
     resp.setContentType("application/vnd.ms-excel");
     try {
     String title="导出数据.xls";
     if(OtherUtil.measureNotNull(searchName)){
     title=searchName+".xls";
     resp.setHeader("Content-Disposition", "attachment;filename=" + new String(title.getBytes("GBK"),"ISO8859-1"));//"ISO8859-1"
     }else{
     resp.setHeader("Content-Disposition", "attachment;filename=" + new String(title.getBytes("GBK"),"ISO8859-1"));//"ISO8859-1"
     }
     OutputStream outs = resp.getOutputStream();
     HSSFWorkbook  workbook = new HSSFWorkbook();
     ColumnDef[] columnDefs = ExportHelper.getColumnDefChieseName(ops);
    
     int colSize=columnDefs.length;//取columnDefs数组长度
     boolean measure=false;
     for (int i = 0; i < columnDefs.length; i++) {
     if(OtherUtil.measureNotNull(columnDefs[i])){
     if(OtherUtil.measureNotNull(columnDefs[i].getTitleZi())){
     measure=true;
     }
     }
     }
     HSSFSheet  sheet = workbook.createSheet();
    
    
     // 数据行
     Iterator itr = list.iterator();
     int rowIndex = 0;
     ColumnDef[] columnNew=new ColumnDef[colSize];
     while (itr.hasNext()) {
     Map rowMap = (Map) itr.next();
     if ((measure&&rowIndex<2)||(!measure&&rowIndex==0)) {
    
     Row row = sheet.createRow(rowIndex);
    
     HSSFFont font = workbook.createFont();
     font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
     CellStyle style = workbook.createCellStyle();
     style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
     style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
     style.setFont(font);
     rowIndex++;
     Row ziRow = sheet.createRow(rowIndex);
     for (int i = 0; i < colSize; i++) {
     if(OtherUtil.measureNotNull(columnDefs[i])){
    
     Cell cell = row.createCell(i);
    
     if(OtherUtil.measureNotNull(columnDefs[i].getTitleZi())){
     ColumnDef[] ziColumnDefs=columnDefs[i].getTitleZi();//得到所有子项
     int ziSize=ziColumnDefs.length;
    
     for (int j = 0; j < ziSize; j++) {
     Cell cellZi = ziRow.createCell(i+j);
     columnNew[i+j]=ziColumnDefs[j];
     fillCellData(columnDefs[i],cellZi,style);
     }
     sheet.addMergedRegion(new Region(0,(short)i,0,(short)((ziSize-1)+i)));//合并父项的列
    
     Cell cellXin = row.createCell((short) i);
     cellXin.setCellValue(OtherUtil.measureNull(columnDefs[i].getTitle())?"":(columnDefs[i].getTitle()));
     cellXin.setCellStyle(style);
     }else if(measure){
     columnNew[i]=columnDefs[i];
     sheet.addMergedRegion(new Region(0,(short)i,1,(short)i));
     fillCellData(columnDefs[i],cell,style);
     }else{
     columnNew[i]=columnDefs[i];
     fillCellData(columnDefs[i],cell,style);
     }
     }else {
     Cell cell = row.createCell(i);
     cell.setCellType(Cell.CELL_TYPE_STRING);
     cell.setCellValue("");
     }
     }
     rowIndex++;
     }
     Row row = sheet.createRow(rowIndex);
     rowIndex++;
     for (int i = 0; i < columnNew.length; i++) 
     fillData(row,i,columnNew,rowMap);
     }
    
     for (int i = 1; i < colSize; i++) {
     sheet.autoSizeColumn((short)i); 
     }
     workbook.write(outs);
     outs.flush();
     outs.close();
    
     } catch (Exception e) {
     e.printStackTrace();
     throw new BusinessException(e.getMessage());
     }
    
     }
     *//**
       * 创建行的单元格并且给数据单元格填充数据
       * @param row
       * @param i
       * @param columnDefs
       * @param rowMap
       */
    private static void fillData( HSSFRow row, int i, ColumnDef[] columnDefs, Map rowMap, HSSFCellStyle style ) {

        HSSFCell cell = row.createCell( i );
        String key = columnDefs[i].getFieldName().toUpperCase();
        if ( OtherUtil.measureNotNull( key ) && OtherUtil.measureNotNull( rowMap.get( key ) ) ) {
            for ( int j = 0; j < columnDefs.length; j++ ) {
                int k = columnDefs[i].getType();
                if ( ( k == 1 ) || ( k == 8 ) ) {
                    cell.setCellType( Cell.CELL_TYPE_STRING );
                    cell.setCellValue( rowMap.get( key ).toString() );
                    cell.setCellStyle( style );
                }
                else if ( ( k == 3 ) || ( k == 4 ) || ( k == 6 ) ) {
                    cell.setCellType( Cell.CELL_TYPE_NUMERIC );
                    cell.setCellValue( new BigDecimal( rowMap.get( key ).toString() ).doubleValue() );
                    cell.setCellStyle( style );
                }
                else if ( k == 2 ) {
                    cell.setCellType( Cell.CELL_TYPE_NUMERIC );
                    cell.setCellValue( new BigDecimal( rowMap.get( key ).toString() ).intValue() );
                    cell.setCellStyle( style );
                }
                else if ( k == 5 ) {
                    cell.setCellType( Cell.CELL_TYPE_BOOLEAN );
                    cell.setCellValue( Boolean.parseBoolean( rowMap.get( key ).toString() ) );
                    cell.setCellStyle( style );
                }
            }
        }
        else {
            cell.setCellType( Cell.CELL_TYPE_BLANK );
            cell.setCellValue( "" );
            cell.setCellStyle( style );
        }
    }

    /**
     * 给表头数据填值
     * @param sqlString
     * @return
     */
    private static void fillCellData( ColumnDef columnDefs, Cell cell, CellStyle style ) {
        String ziValue = columnDefs.getTitle();
        cell.setCellType( Cell.CELL_TYPE_STRING );
        cell.setCellValue( OtherUtil.measureNull( ziValue ) ? "" : ziValue );
        cell.setCellStyle( style );
    }

    private static void fillCellData( ColumnDef columnDefs, Cell cell ) {
        String ziValue = columnDefs.getTitle();
        cell.setCellType( Cell.CELL_TYPE_STRING );
        cell.setCellValue( OtherUtil.measureNull( ziValue ) ? "" : ziValue );
    }

    private static String getTableName( String sqlString ) {
        String sql = sqlString.toUpperCase();
        int i = sql.indexOf( "FROM" );
        /*
         * if (sql.length() > i + 100) return "导出数据";
         */
        sql = sql.substring( i + 5 );
        i = sql.indexOf( "WHERE" );
        if ( i >= 0 )
            sql = sql.substring( 0, i - 1 );
        i = sql.indexOf( "ORDER BY" );
        if ( i < 0 )
            return sql.trim();
        sql = sql.substring( 0, i - 1 );
        return sql.trim();
    }

    private static String getTableName( int paramInt, String paramString, String[] paramArrayOfString ) {
        for ( int i = 0; i < paramInt; i++ )
            if ( paramString.equalsIgnoreCase( getTableName( paramArrayOfString[i] ) ) )
                return "" + paramInt + "." + paramString;
        return paramString;
    }

    /**
     * 
     * @param list  需要导出的集合
     * @param outS  输出流
     * @param ops   要导出的列
     * @param fileName  模版文件路径
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void read( List list, HttpServletResponse resp, SearchOption[] ops, String fileName, HttpServletRequest req, String searchName ,String title)
        throws FileNotFoundException, IOException {
    	OutputStream outs = null;
    	try {
        SearchUtils su = new SearchUtils( req, searchName );
        Map systemProperties = su.getSystemPropertiesMap();
        Map userProperties = su.getUserPropertiesMap();
        Map searchItemProperties = su.getSearchProperitesMap();
        resp.setContentType( "application/vnd.ms-excel" );
        File excel = new File( fileName );
        ColumnDef[] methods = ExportHelper.getColumnDefs( ops );
        Workbook wb = getWorkBook( excel );
        if ( wb == null )
            return;
        Sheet sheet = wb.getSheetAt( 0 ); //取第一张表
        String sheetName = sheet.getSheetName() + ".xls";//取第一张表的名字
        if(title==null||"".equals(title)){
        	title = "导出数据";
        }
        title += ".xls";
        resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( title.getBytes( "GBK" ), "ISO8859-1" ) );//"ISO8859-1"
        outs = resp.getOutputStream();
        int columnIndex = 0;
        List<ColumnDef> columnDefsList = new LinkedList<ColumnDef>();
        List<CellStyle> cellStyleList = new LinkedList<CellStyle>();
        int dataRowHeight = 0;
//        ColumnDef[] columnDefs = new ColumnDef[methods.length]; //存储字段的顺序
//        CellStyle[] cellStyle = new CellStyle[methods.length];//存储单元格样式
        Map<Integer, Integer> rowNums = new HashMap<Integer, Integer>();//存储数据行所在行
        
        for ( short i = 0; i < sheet.getLastRowNum()+1; i++ ) {
            Row row = sheet.getRow( i );//获取所有行
            if(row==null){
            	continue;
            }
            for ( short j = 0; j < row.getLastCellNum(); j++ ) {
                Cell tip = row.getCell( j );//获取所有表格
                if ( null != tip && tip.getCellType() == Cell.CELL_TYPE_STRING ) {
                    String curTipName = tip.getStringCellValue().trim();//得到表格内容
                    for ( int k = 0; k < methods.length; k++ ) {
                        if ( curTipName.length() >= 5 && curTipName.substring( 0, 4 ).equals( "data" ) ) {
                            String curCell0 = curTipName.substring( 5 ).toUpperCase();
                            if ( curCell0.equals( methods[k].getFieldName() ) ) {
                            	columnDefsList.add(methods[k]);
                            	cellStyleList.add(tip.getCellStyle());
                            	dataRowHeight = row.getHeight();
                                rowNums.put( (int) j, (int) i );
                                columnIndex++;
                                break;
                            }
                        }
                        else if (curTipName.length() >= 5 && curTipName.substring( 0, 5 ).equals( "merge" )){
                        	 String curCell0 = curTipName.substring( 6 ).toUpperCase();
                             if ( curCell0.equals( methods[k].getFieldName() ) ) {
                            	 methods[k].setIsMerge(true);
                            	 columnDefsList.add(methods[k]);
//                                 columnDefs[columnIndex].setIsMerge(true);
                            	 cellStyleList.add(tip.getCellStyle());
                                 rowNums.put( (int) j, (int) i );
                                 columnIndex++;
                                 break;
                             }
                        }
                        else if ( curTipName.contains( "user.ORG" ) ) {
                            curTipName =
                                curTipName.replace( "user.ORG", userProperties.get( "orgName" ) == null ? ""
                                                : userProperties.get( "orgName" ).toString() );
                            tip.setCellValue( curTipName );
                        }
                        else if ( curTipName.contains( "start.CYEAR" ) ) {
                            curTipName =
                                curTipName.replace( "start.CYEAR", searchItemProperties.get( "STARTCYEAR" ) == null ? ""
                                                : searchItemProperties.get( "STARTCYEAR" ).toString() );
                            tip.setCellValue( curTipName );
                        }
                        else if ( curTipName.contains( "start.CMONTH" ) ) {
                            curTipName =
                                curTipName.replace( "start.CMONTH", searchItemProperties.get( "STARTCMONTH" ) == null ? ""
                                                : searchItemProperties.get( "STARTCMONTH" ).toString() );
                            tip.setCellValue( curTipName );
                        }
                        else if ( curTipName.contains( "end.CYEAR" ) ) {
                            curTipName =
                                curTipName.replace( "end.CYEAR", searchItemProperties.get( "ENDCYEAR" ) == null ? ""
                                                : searchItemProperties.get( "ENDCYEAR" ).toString() );
                            tip.setCellValue( curTipName );
                        }
                        else if ( curTipName.contains( "end.CMONTH" ) ) {
                            curTipName =
                                curTipName.replace( "end.CMONTH", searchItemProperties.get( "ENDCMONTH" ) == null ? ""
                                                : searchItemProperties.get( "ENDCMONTH" ).toString() );
                            tip.setCellValue( curTipName );
                        }
                        else if ( curTipName.contains( "query." ) ) {
                        	
                            Pattern pattern = Pattern.compile("\\{(.*?)\\}");
                            Matcher matcher = pattern.matcher(curTipName);
                            String replaceStr = "";
                            if(matcher.find()){
                            	replaceStr = matcher.group();
                            	//replaceStr.replace("{", "");
                            	//replaceStr.replace("}", "");
                            	String queryName = replaceStr.split("\\.")[1];
                            	queryName = queryName.replace("}", "");
                            	queryName = queryName.toUpperCase();
                            	curTipName = 
                                    curTipName.replace( replaceStr, searchItemProperties.get( queryName ) == null ? ""
                                                    : searchItemProperties.get( queryName ).toString() );
                                tip.setCellValue( curTipName );
                            }
                            
                        }
                    }
                }
            }
        }

        Set<Integer> key = rowNums.keySet();
        int rowNum = 0;//得到数据行所在行数
        for ( Iterator it = key.iterator(); it.hasNext(); )
            rowNum = rowNums.get( (Integer) it.next() );

        int lastStartRowNum = rowNum + 1;//获取数据列以下的行(开始位置)
        int lastEndRowNum = sheet.getLastRowNum();//获取数据列以下的行(结束位置)

        int lastRowNum = lastEndRowNum - lastStartRowNum;

        Row[] lastRows = new Row[lastRowNum + 1];
        int y = 0;
        for ( int i = lastStartRowNum; i <= lastEndRowNum; i++ ) {
            Row row = sheet.getRow( i );
            lastRows[y] = row;
            sheet.removeRow(row);
            y++;
        }

        Iterator itr = list.iterator();
        int cellIndex = 0;
        while ( itr.hasNext() ) {
            Row rowC = sheet.createRow( rowNum );
            rowC.setHeight((short)dataRowHeight);
            Map rowMap = (Map) itr.next();
            for ( int i = 0; i < columnDefsList.size(); i++ ) {
                Cell cell = rowC.createCell( i );
                String keys = null;
                if ( columnDefsList.get(i) != null )
                    keys = columnDefsList.get(i).getFieldName().toUpperCase();
                if ( keys != null ) {
                    for ( int j = 0; j < columnDefsList.size(); j++ ) {
                        int k = columnDefsList.get(i).getType();
                        if ( ( k == 1 ) || ( k == 8 ) ) {
                            cell.setCellStyle( cellStyleList.get(i) );
                            cell.setCellType( Cell.CELL_TYPE_STRING );
                            if ( rowMap != null && rowMap.get( keys ) != null )
                            	if(columnDefsList.get(i).getIsMerge()){
                            		cell.setCellValue( rowMap.get( keys ).toString()+"$merge$" );
                            	}else{
                            		cell.setCellValue( rowMap.get( keys ).toString() );
                            	}
                        }
                        else if ( ( k == 3 ) || ( k == 4 ) || ( k == 6 ) ) {
                            cell.setCellType( Cell.CELL_TYPE_NUMERIC );
                            cell.setCellStyle(cellStyleList.get(i));
                            if(rowMap.get(keys)==null){
                            	cell.setCellValue( Double.parseDouble( "0.00" ) );//如果从DB中查询出的是NULL 则直接添加0.00
                            }else{
                            	cell.setCellValue( Double.parseDouble( rowMap.get( keys ).toString() ) );
                            }
                        }
                        else if ( k == 2 ) {
                            cell.setCellStyle( cellStyleList.get(i) );
                            cell.setCellType( Cell.CELL_TYPE_NUMERIC );
                            if ( rowMap != null && rowMap.get( keys ) != null ){
                            	cell.setCellValue( Integer.parseInt( rowMap.get( keys ).toString() ) );
                            } else {
                            	cell.setCellValue(Integer.parseInt( "0" ));
                            }
                        }
                        else if ( k == 5 ) {
                            cell.setCellStyle( cellStyleList.get(i) );
                            cell.setCellType( Cell.CELL_TYPE_BOOLEAN );
                            if ( rowMap != null && rowMap.get( keys ) != null ){
                                cell.setCellValue( Boolean.parseBoolean( rowMap.get( keys ).toString() ) );
                            } else {
                            	cell.setCellValue(0);
                            }
                        }
                    }
                }
                else {
                    cell.setCellType( Cell.CELL_TYPE_BLANK );
                    cell.setCellValue( "" );
                }
                cellIndex++;
            }
            rowNum++;
        }

        int pPosition = sheet.getLastRowNum() + 1;
        for ( short i = 0; i < lastRows.length; i++ ) {
            Row endRow = sheet.createRow( pPosition );
            //endRow=lastRows[i];
            endRow.setHeight(lastRows[i].getHeight());
            for ( int j = 0; j < lastRows[i].getLastCellNum(); j++ ) {
                Cell tc = endRow.createCell( j );
                Cell sc = lastRows[i].getCell( j );
                if ( sc != null ) {
                    String value = sc.getStringCellValue();
                    if ( value.contains( "user.NAME" ) ) {
                        value = value.replace( "user.NAME", userProperties.get( "personName" ) == null ? ""
                                            : userProperties.get( "personName" ).toString() );
                    }
                    else if ( value.contains( "system.DATE" ) ) {
                        value = value.replace( "system.DATE", com.huge.util.DateUtil.getALLNow( new Date() ) );
                    }
                    tc.setCellValue( value );
                    tc.setCellStyle( sc.getCellStyle() );
                    tc.setCellType( sc.getCellType() );
                }
            }
            pPosition++;
        }
        
        String tempValueString = "";
        for(int col=0;col<columnDefsList.size();col++){
			int mergeRow = lastStartRowNum-1;
			for(int rowIndex=mergeRow;rowIndex<sheet.getLastRowNum();rowIndex++){
				//HSSFCell firtCell = sheet.getRow(mergeRow).getCell(col);
				Cell cellTemp = sheet.getRow(rowIndex).getCell(col);
				String tempValue = "";
				if(cellTemp!=null && cellTemp.getCellType() == Cell.CELL_TYPE_STRING){
					tempValue = cellTemp.getStringCellValue();
				}
				//System.out.println(tempValue);
				if(tempValue.contains("$merge$")){
					if(tempValue.equals(tempValueString)){
						sheet.addMergedRegion(new CellRangeAddress(mergeRow,rowIndex,col,col));
//						System.out.println("行==>>>>"+mergeRow+"列====>>>>>"+rowIndex+"合并数据=========>>>>>>>>>>>>"+tempValueString);
					} else {
						tempValueString = tempValue;
						mergeRow = rowIndex;
					}
//					cellTemp.setCellValue(tempValue.replace("$merge$", ""));
				}else{
					tempValueString = tempValue;
					mergeRow = rowIndex;
				}
			}
		}
        
        
        for ( int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++ ) {
            Row row = sheet.getRow( rowIndex );//获取所有行
            if(row==null){
            	continue;
            }
            int colNum = 0;
            for ( int col = 0; col < row.getLastCellNum(); col++ ) {
            	Cell cellTemp = row.getCell(col);
				String tempValue = "";
				if(cellTemp!=null && cellTemp.getCellType() == Cell.CELL_TYPE_STRING){
					tempValue = cellTemp.getStringCellValue().trim();
				}
				//System.out.println(tempValue);
				if(tempValue.contains("$merge$")){
					if(tempValue.equals(tempValueString)){
						sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,colNum,col));
					} else {
						tempValueString = tempValue;
						colNum = col;
					}
					cellTemp.setCellValue(tempValue.replace("$merge$", ""));
				}else{
					tempValueString = tempValue;
					colNum = col;
				}
			}
		}
        
        Row endRow = sheet.createRow( pPosition );
        /*for ( int i = 0; i < columnDefs.length; i++ ) {
            sheet.autoSizeColumn( (short) i );
        }*/
        wb.write( outs );
        outs.flush();
        resp.flushBuffer();
    	} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(outs!=null){
				outs.close();
			}
			if(resp!=null){
				//resp.
			}
		}
    }

    /**
     * 复制行
     * 
     * @param startRowIndex
     *            起始行
     * @param endRowIndex
     *            结束行
     * @param pPosition
     *           目标起始行位置
     */
    public static void copyRows( int startRow, int endRow, int pPosition, Sheet currentSheet ) {
        int pStartRow = startRow - 1;
        int pEndRow = endRow;
        int targetRowFrom;
        int targetRowTo;
        int columnCount;
        CellRangeAddress region = null;
        int i;
        int j;
        if ( pStartRow == -1 || pEndRow == -1 ) {
            return;
        }
        for ( i = 0; i < currentSheet.getNumMergedRegions(); i++ ) {
            region = currentSheet.getMergedRegion( i );
            if ( ( region.getFirstRow() >= pStartRow ) && ( region.getLastRow() <= pEndRow ) ) {
                targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
                targetRowTo = region.getLastRow() - pStartRow + pPosition;
                CellRangeAddress newRegion = region.copy();
                newRegion.setFirstRow( targetRowFrom );
                newRegion.setFirstColumn( region.getFirstColumn() );
                newRegion.setLastRow( targetRowTo );
                newRegion.setLastColumn( region.getLastColumn() );
                currentSheet.addMergedRegion( newRegion );
            }
        }
        for ( i = pStartRow; i <= pEndRow; i++ ) {
            Row sourceRow = currentSheet.getRow( i );
            columnCount = sourceRow.getLastCellNum();
            if ( sourceRow != null ) {
                Row newRow = currentSheet.createRow( pPosition - pStartRow + i );
                newRow.setHeight( sourceRow.getHeight() );
                for ( j = 0; j < columnCount; j++ ) {
                    Cell templateCell = sourceRow.getCell( j );
                    if ( templateCell != null ) {
                        Cell newCell = newRow.createCell( j );
                        copyCell( templateCell, newCell );
                    }
                }
            }
        }
    }

    private static void copyCell( Cell srcCell, Cell distCell ) {
        distCell.setCellStyle( srcCell.getCellStyle() );
        if ( srcCell.getCellComment() != null ) {
            distCell.setCellComment( srcCell.getCellComment() );
        }
        int srcCellType = srcCell.getCellType();
        distCell.setCellType( srcCellType );
        if ( srcCellType == HSSFCell.CELL_TYPE_NUMERIC ) {
            if ( DateUtil.isCellDateFormatted( srcCell ) ) {
                distCell.setCellValue( srcCell.getDateCellValue() );
            }
            else {
                distCell.setCellValue( srcCell.getNumericCellValue() );
            }
        }
        else if ( srcCellType == HSSFCell.CELL_TYPE_STRING ) {
            distCell.setCellValue( srcCell.getRichStringCellValue() );
        }
        else if ( srcCellType == HSSFCell.CELL_TYPE_BLANK ) {
            // nothing21
        }
        else if ( srcCellType == HSSFCell.CELL_TYPE_BOOLEAN ) {
            distCell.setCellValue( srcCell.getBooleanCellValue() );
        }
        else if ( srcCellType == HSSFCell.CELL_TYPE_ERROR ) {
            distCell.setCellErrorValue( srcCell.getErrorCellValue() );
        }
        else if ( srcCellType == HSSFCell.CELL_TYPE_FORMULA ) {
            distCell.setCellFormula( srcCell.getCellFormula() );
        }
        else { // nothing29

        }
    }

    private static Workbook getWorkBook( File excel )
        throws FileNotFoundException, IOException {
        return (Workbook) ( excel.getName().endsWith( "xls" ) ? new HSSFWorkbook( new BufferedInputStream( new FileInputStream( excel ) ) )
                        : excel.getName().endsWith( "xlsx" ) ? new XSSFWorkbook( new BufferedInputStream( new FileInputStream( excel ) ) ) : null );
    }
    public static void main(String[] args) {
    	String curTipName = "{112}asdf";
    	Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(curTipName);
        if(matcher.find()){
        	curTipName = matcher.group();
            System.out.println(curTipName);
        }
        
	}
}
