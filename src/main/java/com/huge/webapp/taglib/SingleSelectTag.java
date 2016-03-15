package com.huge.webapp.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.huge.foundation.util.StringUtil;
import com.huge.webapp.taglib.select.SingleSelect;

public class SingleSelectTag
    extends TagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = 6284238962979065119L;

    private String paraValueString = "";

    private String paraDisString = "";

    private String htmlFieldName = "";

    private String initValue = "";

    private String cssClass;

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass( String cssClass ) {
        this.cssClass = cssClass;
    }

    public String getParaValueString() {
        return paraValueString;
    }

    public void setParaValueString( String paraValueString ) {
        this.paraValueString = paraValueString;
    }

    public String getParaDisString() {
        return paraDisString;
    }

    public void setParaDisString( String paraDisString ) {
        this.paraDisString = paraDisString;
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

        String[] arrayValues = StringUtil.strToArray( paraValueString, ";" );
        String[] arrayDiss = StringUtil.strToArray( paraDisString, ";" );

        String[][] arrayOfString1 = new String[arrayValues.length][2];
        for ( int i = 0; i < arrayOfString1.length; i++ ) {
            arrayOfString1[i][0] = arrayValues[i];
            arrayOfString1[i][1] = arrayDiss[i];
        }

        SingleSelect ss = new SingleSelect( arrayOfString1, htmlFieldName, initValue, false, "class=\"" + this.cssClass + "\"" );
        try {
            pageContext.getOut().write( ss.toString() );
        }
        catch ( IOException io ) {
            throw new JspException( io );
        }
        return super.doStartTag();
    }
}
