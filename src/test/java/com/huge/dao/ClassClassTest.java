package com.huge.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

public class ClassClassTest
    extends TestCase {

    // @Test
    // public void testGetField() {
    // Class obj = Period.class;
    // try {
    // Field field = obj.getDeclaredField("currentFlag");
    //
    // // field.getType().isPrimitive();
    // System.out.print(field.getType().isPrimitive());
    // System.out.print(field.getType().getName());
    // System.out.print(field.getType().getSimpleName());
    // } catch (SecurityException e) {
    // e.printStackTrace();
    // } catch (NoSuchFieldException e) {
    // e.printStackTrace();
    // }
    //
    // }

    @Test
    public void testSplit() {
        String f = "dataSourceDefine.111";
        //		String[] sf = f.split("\\.");
        //		String path = null;
        //		if (sf.length == 1) {
        //			path = sf[0];
        //		} else
        //			for (int i = 0; i < sf.length - 1; i++) {
        //				for (int j = 0; j <= i; j++) {
        //					if (j == 0)
        //						path = sf[j];
        //					else
        //						path = path + "." + sf[j];
        //
        //				}
        //				
        //			}
        //		String[] sf=getAliasPath(f);
        //		for (int i = 0; i < sf.length; i++)
        //		System.out.println("path: " + sf[i]);

        String t = getSearchAliasFieldname( f );
        System.out.println( "alias: " + t );
    }

    private static String[] getAliasPath( String field ) {
        String[] sf = field.split( "\\." );
        List list = new ArrayList();
        String path = null;
        for ( int i = 0; i < sf.length - 1; i++ ) {
            for ( int j = 0; j <= i; j++ ) {
                if ( j == 0 )
                    path = sf[j];
                else
                    path = path + "." + sf[j];

            }
            list.add( path );
        }

        String[] al = new String[list.size()];

        list.toArray( al );
        return al;
    }

    private static String getSearchAliasFieldname( String field ) {
        String[] sf = field.split( "\\." );
        if ( sf.length <= 1 )
            return field;
        else
            return sf[sf.length - 2] + "." + sf[sf.length - 1];
    }
}
