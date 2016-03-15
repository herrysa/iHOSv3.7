package com.huge.ihos.excel;

import java.util.Map;

import javassist.expr.NewArray;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class ZhCellStyle {

	public static final short	ALIGN_CENTER	= 2;
	public static final short	ALIGN_CENTER_SELECTION	=6;
	public static final short	ALIGN_FILL	=4;
	public static final short	ALIGN_GENERAL	=0;
	public static final short	ALIGN_JUSTIFY	=5;
	public static final short	ALIGN_LEFT	=1;
	public static final short	ALIGN_RIGHT	=3;
	public static final short	ALT_BARS	=3;
	public static final short	BIG_SPOTS	=9;
	public static final short	BORDER_DASH_DOT	=9;
	public static final short	BORDER_DASH_DOT_DOT	=11;
	public static final short	BORDER_DASHED	=3;
	public static final short	BORDER_DOTTED	=4;
	public static final short	BORDER_DOUBLE	=6;
	public static final short	BORDER_HAIR		=7;
	public static final short	BORDER_MEDIUM	=2;
	public static final short	BORDER_MEDIUM_DASH_DOT	=10;
	public static final short	BORDER_MEDIUM_DASH_DOT_DOT	=12;
	public static final short	BORDER_MEDIUM_DASHED	=8;
	public static final short	BORDER_NONE				=0;
	public static final short	BORDER_SLANTED_DASH_DOT	=13;
	public static final short	BORDER_THICK		=5;
	public static final short	BORDER_THIN			=1;
	public static final short	BRICKS				=10;
	public static final short	DIAMONDS			=16;
	public static final short	FINE_DOTS			=2;
	public static final short	LEAST_DOTS			=18;
	public static final short	LESS_DOTS			=17;
	public static final short	NO_FILL				=0;
	public static final short	SOLID_FOREGROUND	=1;
	public static final short	SPARSE_DOTS			=4;
	public static final short	SQUARES				=15;
	public static final short	THICK_BACKWARD_DIAG	=7;
	public static final short	THICK_FORWARD_DIAG	=8;
	public static final short	THICK_HORZ_BANDS	=5;
	public static final short	THICK_VERT_BANDS	=6;
	public static final short	THIN_BACKWARD_DIAG	=13;
	public static final short	THIN_FORWARD_DIAG	=14;
	public static final short	THIN_HORZ_BANDS		=11;
	public static final short	THIN_VERT_BANDS		=12;
	public static final short	VERTICAL_BOTTOM		=2;
	public static final short	VERTICAL_CENTER		=1;
	public static final short	VERTICAL_JUSTIFY	=3;
	public static final short	VERTICAL_TOP		=0;
	
	private boolean wrapText = false;
	private short verticalAlignment = 1;
	private short topBorderColor = 0;
	private short rotation = 0;
	private short rightBorderColor = 0;
	private short leftBorderColor = 0;
	private boolean locked = false;
	private boolean hidden = false;
	private short indention = 0;
	private Font font = null;
	private ZhFont zhFont = null;
	
	public ZhFont getZhFont() {
		return zhFont;
	}
	public void setZhFont(ZhFont zhFont) {
		this.zhFont = zhFont;
	}

	private short fontIndex = 0;
	private short fillPattern = 0;
	private short fillForegroundColor = 0;
	private short fillBackgroundColor = 0;
	private short dataFormat = 0;
	private short bottomBorderColor = 0;
	private short borderTop = 0;
	private short borderRight = 0;
	private short borderLeft = 0;
	private short borderBottom = 0;
	private short alignment = 2;
	private ZhCell zhCell;
	private short cellStyleIndex = 0;
	
	public short getCellStyleIndex() {
		return cellStyleIndex;
	}
	public void setCellStyleIndex(short cellStyleIndex) {
		this.cellStyleIndex = cellStyleIndex;
	}
	public ZhCell getZhCell() {
		return zhCell;
	}
	public void setZhCell(ZhCell zhCell) {
		this.zhCell = zhCell;
	}
	public boolean getWrapText() {
		return wrapText;
	}
	public void setWrapText(boolean wrapText) {
		this.wrapText = wrapText;
	}
	public short getVerticalAlignment() {
		return verticalAlignment;
	}
	public void setVerticalAlignment(short verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}
	public short getTopBorderColor() {
		return topBorderColor;
	}
	public void setTopBorderColor(short topBorderColor) {
		this.topBorderColor = topBorderColor;
	}
	public short getRotation() {
		return rotation;
	}
	public void setRotation(short rotation) {
		this.rotation = rotation;
	}
	public short getRightBorderColor() {
		return rightBorderColor;
	}
	public void setRightBorderColor(short rightBorderColor) {
		this.rightBorderColor = rightBorderColor;
	}
	public short getLeftBorderColor() {
		return leftBorderColor;
	}
	public void setLeftBorderColor(short leftBorderColor) {
		this.leftBorderColor = leftBorderColor;
	}
	public boolean getLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean getHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public short getIndention() {
		return indention;
	}
	public void setIndention(short indention) {
		this.indention = indention;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public short getFillPattern() {
		return fillPattern;
	}
	public void setFillPattern(short fillPattern) {
		this.fillPattern = fillPattern;
	}
	public short getFillForegroundColor() {
		return fillForegroundColor;
	}
	public void setFillForegroundColor(short fillForegroundColor) {
		this.fillForegroundColor = fillForegroundColor;
	}
	public short getFillBackgroundColor() {
		return fillBackgroundColor;
	}
	public void setFillBackgroundColor(short fillBackgroundColor) {
		this.fillBackgroundColor = fillBackgroundColor;
	}
	public short getDataFormat() {
		return dataFormat;
	}
	public void setDataFormat(short dataFormat) {
		this.dataFormat = dataFormat;
	}
	public short getBottomBorderColor() {
		return bottomBorderColor;
	}
	public void setBottomBorderColor(short bottomBorderColor) {
		this.bottomBorderColor = bottomBorderColor;
	}
	public short getBorderTop() {
		return borderTop;
	}
	public void setBorderTop(short borderTop) {
		this.borderTop = borderTop;
	}
	public short getBorderRight() {
		return borderRight;
	}
	public void setBorderRight(short borderRight) {
		this.borderRight = borderRight;
	}
	public short getBorderLeft() {
		return borderLeft;
	}
	public void setBorderLeft(short borderLeft) {
		this.borderLeft = borderLeft;
	}
	public short getBorderBottom() {
		return borderBottom;
	}
	public void setBorderBottom(short borderBottom) {
		this.borderBottom = borderBottom;
	}
	public short getAlignment() {
		return alignment;
	}
	public void setAlignment(short alignment) {
		this.alignment = alignment;
	}
	public short getFontIndex() {
		return fontIndex;
	}
	public void setFontIndex(short fontIndex) {
		this.fontIndex = fontIndex;
	}
	
	public void cloneStyleTo(org.apache.poi.ss.usermodel.CellStyle cellstyle,Workbook workbook) {
		cellstyle.setAlignment(this.getAlignment());
		//边框和边框颜色
		cellstyle.setBorderBottom(this.getBorderBottom());
		cellstyle.setBorderLeft(this.getBorderLeft());
		cellstyle.setBorderRight(this.getBorderRight());
		cellstyle.setBorderTop(this.getBorderTop());
		cellstyle.setTopBorderColor(this.getTopBorderColor());
		cellstyle.setBottomBorderColor(this.getBottomBorderColor());
		cellstyle.setRightBorderColor(this.getRightBorderColor());
		cellstyle.setLeftBorderColor(this.getLeftBorderColor());
		
		//背景和前景
		cellstyle.setFillBackgroundColor(this.getFillBackgroundColor());
		cellstyle.setFillForegroundColor(this.getFillForegroundColor());
		
		cellstyle.setDataFormat(this.getDataFormat());
		cellstyle.setFillPattern(this.getFillPattern());
//		toStyle.setFont(fromStyle.getFont(null));
		cellstyle.setHidden(this.getHidden());
		cellstyle.setIndention(this.getIndention());//首行缩进
		cellstyle.setLocked(this.getLocked());
		cellstyle.setRotation(this.getRotation());//旋转
		cellstyle.setVerticalAlignment(this.getVerticalAlignment());
		cellstyle.setWrapText(this.getWrapText());
		Map<Integer, Integer> fontIndexMap = this.getZhCell().getRowStyle().getSheetStyle().fontIndexMap;
		Integer fontIndex = fontIndexMap.get(new Integer(this.getFontIndex()));
		Font fontTemp = this.getFont();
		Font font = null;
		if(fontIndex==null){
			font = workbook.createFont();
			if(fontTemp!=null){
				copyFont(this.getFont(),font);
			}else{
				if(zhFont!=null){
					font.setBoldweight(zhFont.getBoldWeight());
					font.setFontHeightInPoints(zhFont.getFontHeightInPoints());
					font.setFontName(zhFont.getFontName());
				}
			}
			cellstyle.setFont(font);
			fontIndexMap.put(new Integer(this.getFontIndex()), new Integer(cellstyle.getFontIndex()));
		}else{
			font = workbook.getFontAt(fontIndex.shortValue());
			cellstyle.setFont(font);
		}
	}
	
	public void cloneStyleFrom(org.apache.poi.ss.usermodel.CellStyle cellstyle,Workbook workbook) {
		this.setCellStyleIndex(cellstyle.getIndex());
		this.setAlignment(cellstyle.getAlignment());
		//边框和边框颜色
		this.setBorderBottom(cellstyle.getBorderBottom());
		this.setBorderLeft(cellstyle.getBorderLeft());
		this.setBorderRight(cellstyle.getBorderRight());
		this.setBorderTop(cellstyle.getBorderTop());
		this.setTopBorderColor(cellstyle.getTopBorderColor());
		this.setBottomBorderColor(cellstyle.getBottomBorderColor());
		this.setRightBorderColor(cellstyle.getRightBorderColor());
		this.setLeftBorderColor(cellstyle.getLeftBorderColor());
		
		//背景和前景
		this.setFillBackgroundColor(cellstyle.getFillBackgroundColor());
		this.setFillForegroundColor(cellstyle.getFillForegroundColor());
		
		this.setDataFormat(cellstyle.getDataFormat());
		this.setFillPattern(cellstyle.getFillPattern());
//		toStyle.setFont(fromStyle.getFont(null));
		this.setHidden(cellstyle.getHidden());
		this.setIndention(cellstyle.getIndention());//首行缩进
		this.setLocked(cellstyle.getLocked());
		this.setRotation(cellstyle.getRotation());//旋转
		this.setVerticalAlignment(cellstyle.getVerticalAlignment());
		this.setWrapText(cellstyle.getWrapText());
		short fontI = cellstyle.getFontIndex();
		this.setFontIndex(fontI);
		Font font = workbook.getFontAt(fontI);
		this.setFont(font);
	}
	
	public void copyFont(Font fontFrom ,Font fontTo){
		fontTo.setBoldweight(fontFrom.getBoldweight());
		fontTo.setCharSet(fontFrom.getCharSet());
		fontTo.setColor(fontFrom.getColor());
		fontTo.setFontHeight(fontFrom.getFontHeight());
		fontTo.setFontName(fontFrom.getFontName());
		fontTo.setItalic(fontFrom.getItalic());
		fontTo.setStrikeout(fontFrom.getStrikeout());
		fontTo.setTypeOffset(fontFrom.getTypeOffset());
		fontTo.setUnderline(fontFrom.getUnderline());
	}
}
