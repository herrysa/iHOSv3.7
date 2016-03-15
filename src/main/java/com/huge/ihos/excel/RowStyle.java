package com.huge.ihos.excel;

import java.util.List;

public class RowStyle implements Cloneable{

	public int rowHeight;
	public List<ZhCell> cellList;
	private SheetStyle sheetStyle;
	
	 public SheetStyle getSheetStyle() {
		return sheetStyle;
	}

	public void setSheetStyle(SheetStyle sheetStyle) {
		this.sheetStyle = sheetStyle;
	}

	@Override  
	    public RowStyle clone() throws CloneNotSupportedException {  
	        return (RowStyle)super.clone();  
	    }  
}
