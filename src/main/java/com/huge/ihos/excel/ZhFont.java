package com.huge.ihos.excel;

public class ZhFont {

	/*fontTo.setBoldweight(fontFrom.getBoldweight());
	fontTo.setCharSet(fontFrom.getCharSet());
	fontTo.setColor(fontFrom.getColor());
	fontTo.setFontHeight(fontFrom.getFontHeight());
	fontTo.setFontName(fontFrom.getFontName());
	fontTo.setItalic(fontFrom.getItalic());
	fontTo.setStrikeout(fontFrom.getStrikeout());
	fontTo.setTypeOffset(fontFrom.getTypeOffset());
	fontTo.setUnderline(fontFrom.getUnderline());*/
	
	private short fontHeight = ( short ) 0xc8;
	private short fontHeightInPoints = ( short ) 0xc8;
	private short boldWeight = ( short ) 0x190;
	private String fontName = "Arial";
	
	public short getFontHeight() {
		return fontHeight;
	}
	public void setFontHeight(short fontHeight) {
		this.fontHeight = fontHeight;
	}
	public short getFontHeightInPoints() {
		return fontHeightInPoints;
	}
	public void setFontHeightInPoints(short fontHeightInPoints) {
		this.fontHeightInPoints = fontHeightInPoints;
	}
	public short getBoldWeight() {
		return boldWeight;
	}
	public void setBoldWeight(short boldWeight) {
		this.boldWeight = boldWeight;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	
	
	 /*retval.setFontHeight(( short ) 0xc8);
     retval.setAttributes(( short ) 0x0);
     retval.setColorPaletteIndex(( short ) 0x7fff);
     retval.setBoldWeight(( short ) 0x190);
     retval.setFontName("Arial");*/
}
