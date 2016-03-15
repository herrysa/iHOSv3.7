package com.huge.webapp.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.huge.exceptions.BusinessException;

public class YearMonthTag
    extends TagSupport {
    private int defaultBeginYear = 2005;

    private int defaultEndYear = 2025;

    private String tabSpace = "            ";

    private int initYear;

    private int initMonth;

    /**
     * 
     */
    private static final long serialVersionUID = 2783813243838702691L;

    private String htmlField;

    private String initValue;

    private boolean required;

    private String cssClass;

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass( String cssClass ) {
        this.cssClass = cssClass;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired( boolean required ) {
        this.required = required;
    }

    public String getHtmlField() {
        return htmlField;
    }

    public void setHtmlField( String htmlField ) {
        this.htmlField = htmlField;
    }

    public String getInitValue() {
        return initValue;
    }

    // 校验初始化数据
    public void setInitValue( String initValue ) {
        this.initValue = initValue;
        if ( this.initValue != null && !this.initValue.trim().equalsIgnoreCase( "" ) && this.initValue.trim().length() == 6 ) {
            this.initYear = Integer.parseInt( initValue.substring( 0, 4 ) );
            this.initMonth = Integer.parseInt( initValue.substring( 4 ) );
        }

    }

    public int doStartTag()
        throws JspException {
        String randomString = String.valueOf( Math.random() ).substring( 2 );
        randomString = randomString.replace( '-', '0' );
        // yearID, monthID, year_monthID
        String yearID = "s_" + randomString + "1";
        String monthID = "s_" + randomString + "2";
        String year_monthID = "s_" + randomString + "4";
        StringBuffer sb =
            new StringBuffer( "\n" ).append(
                                             "      <select  onchange='return selectMonthToInput(" + yearID + "," + monthID + "," + this.htmlField
                                                 + ")' id='" + yearID + "' " + "class='" + cssClass + "'" + " >" ).append( "\n" ).append(
                                                                                                                                          this.tabSpace ).append(
                                                                                                                                                                  "        <option  selected='selected' value='' >&nbsp;&nbsp;&nbsp;&nbsp;</option>" );
        String yearOpt;
        for ( int i = this.defaultBeginYear; i <= this.defaultEndYear; i++ ) {
            yearOpt = intToString( i, 4 );
            if ( i == this.initYear )
                sb.append( "\n" ).append( this.tabSpace ).append( "        <option  value='" + yearOpt + "' selected >" + i + "</option>" );
            else
                sb.append( "\n" ).append( this.tabSpace ).append( "        <option  value='" + yearOpt + "' >" + i + "</option>" );
        }
        sb.append( "\n" ).append( this.tabSpace ).append( "      </select>" ).append( "\n" ).append(
                                                                                                     "      <select  onchange='return selectMonthToInput("
                                                                                                         + yearID + "," + monthID + ","
                                                                                                         + this.htmlField + ")' id='" + monthID
                                                                                                         + "' " + "class='" + cssClass + "'" + " >" ).append(
                                                                                                                                                              "\n" ).append(
                                                                                                                                                                             this.tabSpace ).append(
                                                                                                                                                                                                     "        <option  selected='selected' value='' >&nbsp;&nbsp;</option>" );
        String monthOpt;
        for ( int i = 1; i <= 12; i++ ) {
            monthOpt = intToString( i, 2 );
            if ( i == this.initMonth )
                sb.append( "\n" ).append( this.tabSpace ).append( "        <option  value='" + monthOpt + "' selected >" + monthOpt + "</option>" );
            else
                sb.append( "\n" ).append( this.tabSpace ).append( "        <option  value='" + monthOpt + "' >" + monthOpt + "</option>" );
        }

        String yearMonthInitValue = intToString( this.initYear, 4 ) + intToString( this.initMonth, 2 );
        if ( ( this.initYear == 0 ) || ( this.initMonth == 0 ) )
            yearMonthInitValue = "";

        sb.append( "\n" ).append( this.tabSpace ).append( "      </select>" ).append( "\n" ).append( this.tabSpace ).append(
                                                                                                                             " <input type=hidden  name='" ).append(
                                                                                                                                                                     this.htmlField ).append(
                                                                                                                                                                                              "' value='" ).append(
                                                                                                                                                                                                                    yearMonthInitValue ).append(
                                                                                                                                                                                                                                                 "' id='" ).append(
                                                                                                                                                                                                                                                                    this.htmlField ).append(
                                                                                                                                                                                                                                                                                             "' />" ).append(
                                                                                                                                                                                                                                                                                                              "\n" );

        System.out.println( sb.toString() );
        try {
            pageContext.getOut().write( sb.toString() );
        }
        catch ( IOException io ) {
            throw new JspException( io );
        }
        return super.doStartTag();
    }

    private String intToString( int intData, int stringlength ) {
        String str = String.valueOf( intData );
        if ( ( intData < 0 ) || ( str.length() > stringlength ) )
            throw new BusinessException( "参数不合法" );
        StringBuffer sb = new StringBuffer( str );
        for ( int i = str.length(); i < stringlength; i++ )
            sb.insert( 0, "0" );
        return sb.toString();
    }

    public static void main( String[] paramArrayOfString ) {
        YearMonthTag ymt = new YearMonthTag();
        ymt.setHtmlField( "htmlField" );
        ymt.setInitValue( "201202" );
        try {
            ymt.doStartTag();
        }
        catch ( JspException e ) {
            e.printStackTrace();
        }

    }
}
