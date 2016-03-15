package com.huge.ihos.system.reportManager.search.util;

public class ColumnDef {
    private int type;

    private String fieldName;

    private String title;

    private ColumnDef[] titleZi;
    
    private Boolean isMerge = false;


	public ColumnDef( String paramString1, int paramInt, String paramString2, ColumnDef[] titleZi ) {
        this.fieldName = paramString1;
        this.type = paramInt;
        this.title = paramString2;
        this.titleZi = titleZi;
    }

    public ColumnDef() {
    }

    public int getType() {
        return this.type;
    }

    public void setType( int paramInt ) {
        this.type = paramInt;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName( String paramString ) {
        this.fieldName = paramString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public ColumnDef[] getTitleZi() {
        return titleZi;
    }

    public void setTitleZi( ColumnDef[] titleZi ) {
        this.titleZi = titleZi;
    }
    
    public Boolean getIsMerge() {
		return isMerge;
	}

	public void setIsMerge(Boolean isMerge) {
		this.isMerge = isMerge;
	}
}

/*
 * Location:
 * D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2
 * .0.0\ Qualified Name: com.huge.common.util.ColumnDef JD-Core Version: 0.6.0
 */