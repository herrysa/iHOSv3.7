package com.huge.webapp.taglib.select;

import com.huge.foundation.util.StringUtil;

public class StringSelect
    extends SingleSelect {
    public StringSelect( String paraString, String name, String initValue, boolean required ) {
        super( to2Array( paraString ), name, initValue, required );
    }

    public StringSelect( String paraString, String name, String initValue, boolean required, String paramString4 ) {
        super( to2Array( paraString ), name, initValue, required, paramString4 );
    }

    public static String[][] to2Array( String paramString ) {
        String[] arrayOfString = StringUtil.strToArray( paramString, ";" );
        String[][] arrayOfString1 = new String[arrayOfString.length][2];
        for ( int i = 0; i < arrayOfString.length; i++ ) {
        	if(arrayOfString[i].contains(",")){
        		String[] stringArr = arrayOfString[i].split(",");
        		arrayOfString1[i][0] = stringArr[0];
        		arrayOfString1[i][1] = stringArr[1];
        	}else{
        		arrayOfString1[i][0] = arrayOfString[i];
        		arrayOfString1[i][1] = arrayOfString[i];
        	}
        }
        return arrayOfString1;
    }

    public static void main( String[] paramArrayOfString ) {

        StringSelect ss = new StringSelect( "1;2;3", "testname", "", false );
        System.out.println( ss.toString() );
    }
}

/*
 * Location:
 * D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\ecis-web
 * -basic.jar Qualified Name: com.huge.waf.view.StringSelect JD-Core Version:
 * 0.6.0
 */
