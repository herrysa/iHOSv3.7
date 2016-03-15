package com.huge.webapp.taglib.select;

import com.huge.exceptions.BusinessException;
import com.huge.foundation.util.StringUtil;

public class SingleSelect
    extends BaseComponent {
    protected String[][] initialInfo;

    protected String name;

    protected String defaultValue;

    protected String attachment = "class=\"input-small\"";

    protected String cssClass = "";

    public SingleSelect( Object paramObject, String paramString1, String paramString2, boolean paramBoolean ) {
        this( paramObject != null ? (String[][]) (String[][]) paramObject : (String[][]) null, paramString1, paramString2, paramBoolean );
    }

    public SingleSelect( String[][] paramArrayOfString, String paramString1, String paramString2, boolean paramBoolean, String paramString3 ) {
        this( paramArrayOfString, paramString1, paramString2, paramBoolean );
        this.attachment = paramString3;
    }

    public SingleSelect( String[][] paramArrayOfString, String paramString1, String paramString2, boolean paramBoolean ) {
        if ( paramString1 == null )
            throw new BusinessException( "参数错" );
        if ( ( paramArrayOfString != null ) && ( paramArrayOfString.length > 0 ) ) {
            if ( paramArrayOfString[0].length < 2 )
                throw new BusinessException( "参数错" );
            this.initialInfo = paramArrayOfString;
            if ( !paramBoolean ) {
                String[][] arrayOfString = new String[paramArrayOfString.length + 1][2];
                arrayOfString[0][0] = "";
                arrayOfString[0][1] = "";
                System.arraycopy( paramArrayOfString, 0, arrayOfString, 1, paramArrayOfString.length );
                this.initialInfo = arrayOfString;
            }
        }
        else {
            paramArrayOfString = new String[1][2];
            paramArrayOfString[0][0] = "";
            paramArrayOfString[0][1] = "";
            this.initialInfo = paramArrayOfString;
        }
        this.name = paramString1;
        this.defaultValue = paramString2;
        setIsRequired( paramBoolean );
        setIndent( 6 );
    }

    public String toString() {
        setAttribute( "name", this.name );
        setAttribute( "id", StringUtil.replaceString( this.name, ".", "_" ) );
        StringBuffer localStringBuffer = new StringBuffer( "" );
        localStringBuffer.append( "\n" ).append( this.indent ).append( "<select " + appendAttr() + "  " + this.attachment + " >" );
        if ( this.initialInfo != null )
            for ( int i = 0; i < this.initialInfo.length; i++ ) {
                localStringBuffer.append( "\n" ).append( this.indent ).append( "  <option value=\"" ).append( this.initialInfo[i][0] ).append( "\"" );
                if ( this.initialInfo[i][0].trim().equals( this.defaultValue.trim() ) )
                    localStringBuffer.append( " selected " );
                localStringBuffer.append( ">" ).append( this.initialInfo[i][1] ).append( "</option>" );
            }
        localStringBuffer.append( "\n" ).append( this.indent ).append( "</select>" );
        localStringBuffer.append( appendIsRequired() );
        return localStringBuffer.toString();
    }

    public static void main( String[] paramArrayOfString ) {
        String[][] arrayOfString = { { "23", "asd" }, { "231", "asd" }, { "23", "asd" } };
        System.out.println( new SingleSelect( arrayOfString, "tt", "231", false, "attachment" ) );
    }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\ecis-web-basic.jar
 * Qualified Name:     com.huge.waf.view.SingleSelect
 * JD-Core Version:    0.6.0
 */