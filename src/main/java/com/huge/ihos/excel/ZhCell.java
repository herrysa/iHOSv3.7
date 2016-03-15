package com.huge.ihos.excel;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;

public class ZhCell implements Cloneable{

	//public String name;
	//public String dataSetName;
	public ZhCellStyle cellStyle;
	public CellData cellData;
	public Map<String, DataSet> cellDataSetMap;
	public String comment;
	public int cellType;
	public String cellStyleValue;
	private RowStyle rowStyle;
	
	public void setValue(Cell cell){
		int type = 1;
		String cellValue = null;
		/*int replaceState = fullReplace();
		if(replaceState==0){
			type = this.cellType;
			cellValue = this.cellStyleValue;
		}else if(replaceState==1){
			type = this.cellType;
			cellValue = replaceData();
		}else if(replaceState==2){
			type = getCellType();
			cellValue = replaceData();
		}*/

		type = getCellType();
		cellValue = replaceData();
		
		if("".equals(cellValue)){
			cellValue = null;
		}
		if ( ( type == 1 ) || ( type == 8 ) ) {
            cell.setCellType( Cell.CELL_TYPE_STRING );
            cell.setCellValue( cellValue );
        }
        else if ( ( type == 3 ) || ( type == 4 ) || ( type == 6 ) ) {
            cell.setCellType( Cell.CELL_TYPE_NUMERIC );
            if(cellValue==null){
            	cell.setCellValue( Double.parseDouble( "0.00" ) );
            }else{
            	cellValue = cellValue.replace(",", "");
            	DecimalFormat df=new DecimalFormat("0.00");
            	cell.setCellValue( Double.parseDouble( df.format( Double.parseDouble( cellValue ) ) ) );
            }
        }
        else if ( type == 2 ) {
            cell.setCellType( Cell.CELL_TYPE_NUMERIC );
            if ( cellValue != null ){
            	cell.setCellValue( cellValue );
            } else {
            	cell.setCellValue(Integer.parseInt( "0" ));
            }
        }
        else if ( type == 5 ) {
            cell.setCellType( Cell.CELL_TYPE_BOOLEAN );
            if ( cellValue != null ){
                cell.setCellValue( Boolean.parseBoolean( cellValue ) );
            } else {
            	cell.setCellValue(0);
            }
        }else{
        	cell.setCellType( Cell.CELL_TYPE_STRING );
            cell.setCellValue( cellValue );
        }
	}
	public int fullReplace(){
		String cellStyleValue = this.cellStyleValue;
		if(cellStyleValue==null){
			return 1;
		}
		int replaceState = 0;        //0:不替换 1：有替换的部分 2：全替换
		Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(cellStyleValue);
        String replaceStr = "";
        int numGroup = matcher.groupCount();
        if(matcher.find()){
        	replaceState = 1;
        	for(int groupIndex=1;groupIndex<=numGroup;groupIndex++){
        		replaceStr = matcher.group(groupIndex);
        		cellStyleValue = cellStyleValue.replace("{"+replaceStr+"}", "");
        	}
        }
        if(replaceState==1&&"".equals(cellStyleValue)){
        	replaceState = 2;
        }
		return replaceState;
	}
	
	public int getCellType(){
		int cType = 1;
		if(this.cellDataSetMap!=null){
			Iterator<Entry<String, DataSet>> dataSetMapIt = this.cellDataSetMap.entrySet().iterator();
			int typeTemp = 0;
			while (dataSetMapIt.hasNext()) {
				boolean isBreak = false;
				Entry<String, DataSet> dataSetEntry = dataSetMapIt.next();
				DataSet dataSet = dataSetEntry.getValue();
				Map<String, CellData> cellDataMap = dataSet.cellDataMap;
				if(cellDataMap!=null){
					Iterator<Entry<String, CellData>> cellDataIt = cellDataMap.entrySet().iterator();
					while (cellDataIt.hasNext()) {
						Entry<String, CellData> cellDataEntry = cellDataIt.next();
						CellData cellData = cellDataEntry.getValue();
						if(cellData==null){
							continue;
						}
						if(typeTemp!=0){
							if(cType!=cellData.type){
								typeTemp = 1;
								break;
							}
						}else{
							typeTemp = cellData.type;
						}
					}
				}
				if(isBreak){
					break;
				}
			}
			if(typeTemp!=0){
				cType = typeTemp;
			}
		}
		return cType;
	}
	
/*	public String replaceData(){
		String cellStyleValue = this.cellStyleValue;
		if(cellStyleValue==null){
			return "";
		}
		if(this.cellDataSetMap!=null){
			Pattern pattern = Pattern.compile("\\{(.*?)\\}");
			Matcher matcher = pattern.matcher(cellStyleValue);
			String replaceStr = "";
			int numGroup = matcher.groupCount();
			if(matcher.find()){
				for(int groupIndex=1;groupIndex<=numGroup;groupIndex++){
					replaceStr = matcher.group(groupIndex);
					if(replaceStr!=null&&replaceStr.contains(".")){
						String[] dataFiledArr = replaceStr.split("\\.");
						String dataSetName = dataFiledArr[0];
						String filedName = dataFiledArr[1];
						DataSet dataSet = this.cellDataSetMap.get(dataSetName);
						if(dataSet!=null){
							Map<String, CellData> cellDataMap = dataSet.cellDataMap;
							if(cellDataMap!=null){
								filedName = filedName.toUpperCase();
								CellData cellData = cellDataMap.get(filedName);
								if(cellData!=null&&cellData.value!=null){
									cellStyleValue = cellStyleValue.replace("{"+replaceStr+"}", cellData.value.toString());
								}
							}
						}
					}
				}
			}
		}
		return cellStyleValue;
	}*/
	
	public String replaceData(){
		String cellStyleValue = this.cellStyleValue;
		if(cellStyleValue==null){
			return "";
		}
		if(this.cellDataSetMap!=null){
			String replaceStr = cellStyleValue;
			if(replaceStr!=null&&replaceStr.contains(".")){
				String[] dataFiledArr = replaceStr.split("\\.");
				String dataSetName = dataFiledArr[0];
				String filedName = dataFiledArr[1];
				DataSet dataSet = this.cellDataSetMap.get(dataSetName);
				if(dataSet!=null){
					Map<String, CellData> cellDataMap = dataSet.cellDataMap;
					if(cellDataMap!=null){
						filedName = filedName.toUpperCase();
						CellData cellData = cellDataMap.get(filedName);
						if(cellData!=null&&cellData.value!=null){
							cellStyleValue = cellStyleValue.replace( replaceStr , cellData.value.toString());
						}else{
							cellStyleValue = "";
						}
					}
				}
			}
		}
		return cellStyleValue;
	}
	
	@Override  
	public ZhCell clone() throws CloneNotSupportedException {  
		return (ZhCell)super.clone();  
	}
	
	public RowStyle getRowStyle() {
		return rowStyle;
	}
	public void setRowStyle(RowStyle rowStyle) {
		this.rowStyle = rowStyle;
	}
	public static void main(String[] args) {
		String aa = "-213.900000";
		DecimalFormat df=new DecimalFormat("0.00");
    	String ddString = df.format( Double.parseDouble(aa) );
    	Double.parseDouble(ddString);
	}
}
