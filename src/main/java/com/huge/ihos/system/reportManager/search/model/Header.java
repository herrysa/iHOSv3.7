package com.huge.ihos.system.reportManager.search.model;

public class Header {
    private String startColumnName;
    
    private Long indexOfCol;

	private int numberOfColumns;

    private String headerTitleText;

    public String getStartColumnName() {
        return startColumnName;
    }

    public void setStartColumnName( String startColumnName ) {
        this.startColumnName = startColumnName;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns( int numberOfColumns ) {
        this.numberOfColumns = numberOfColumns;
    }

    public String getHeaderTitleText() {
        return headerTitleText;
    }

    public void setHeaderTitleText( String headerTitleText ) {
        this.headerTitleText = headerTitleText;
    }
    
    public Long getIndexOfCol() {
		return indexOfCol;
	}

	public void setIndexOfCol(Long indexOfCol) {
		this.indexOfCol = indexOfCol;
	}

}
