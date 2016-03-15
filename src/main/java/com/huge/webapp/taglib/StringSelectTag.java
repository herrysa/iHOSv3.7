package com.huge.webapp.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.huge.webapp.taglib.select.StringSelect;

public class StringSelectTag
    extends TagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = 5322130507517183458L;

    private String paraString = "";

    private String htmlFieldName = "";

    private String initValue = "";

    private boolean required;

    public void setRequired( boolean prompt ) {
        this.required = prompt;
    }

    public String getParaString() {
        return paraString;
    }

    public void setParaString( String paraString ) {
        this.paraString = paraString;
    }

    public String getHtmlFieldName() {
        return htmlFieldName;
    }

    public void setHtmlFieldName( String htmlFieldName ) {
        this.htmlFieldName = htmlFieldName;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue( String initValue ) {
        this.initValue = initValue;
    }

    public int doStartTag()
        throws JspException {
        StringSelect ss = null;
        if ( this.required )
            ss = new StringSelect( paraString, htmlFieldName, initValue, true );
        else
            ss = new StringSelect( paraString, htmlFieldName, initValue, false );

        try {
            pageContext.getOut().write( ss.toString() );
        }
        catch ( IOException io ) {
            throw new JspException( io );
        }
        return super.doStartTag();
    }
}
