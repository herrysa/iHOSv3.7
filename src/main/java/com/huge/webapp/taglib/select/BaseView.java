package com.huge.webapp.taglib.select;

public abstract class BaseView {
    protected String indent = "";

    protected static final String NEWLINE = "\n";

    protected void setIndent( int paramInt ) {
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < paramInt; i++ )
            sb.append( "  " );
        this.indent = sb.toString();
    }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\ecis-web-basic.jar
 * Qualified Name:     com.huge.waf.view.BaseView
 * JD-Core Version:    0.6.0
 */