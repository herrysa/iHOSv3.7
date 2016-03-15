package com.huge.ihos.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetStyle {

	public String name;
	public List<CellRange> cellRangeList;
	public List<ColumnStyle> columnStyleList;
	public List<RowStyle> rowStyleList;
	public Map<Integer, Integer> fontIndexMap = new HashMap<Integer, Integer>();
	public Map<Integer, Integer> cellStyleIndexMap = new HashMap<Integer, Integer>();
}
