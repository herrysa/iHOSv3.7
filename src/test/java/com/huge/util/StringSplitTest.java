package com.huge.util;

import static org.junit.Assert.assertTrue;

import java.util.StringTokenizer;

import org.junit.Test;

public class StringSplitTest {
    @Test
    public void testSplitString() {
        String str = "abcdefghijklmn";
        String[] stra = str.split( "", 2 );

        StringTokenizer st = new StringTokenizer( str, "" );
        while ( st.hasMoreElements() ) {
            System.out.println( "StringTokenizer Output: " + st.nextElement() );
        }

        for ( int i = 0; i < str.length(); i = i + 2 ) {
            System.out.println( str.substring( i, i + 2 ) );
        }

        assertTrue( stra.length > 0 );
    }

    @Test
    public void testStringFromByteArrayEq() {
        String a = "123456";
        byte[] ba;// = new byte[128];
        ba = a.getBytes();
        String b = new String( ba );
        assertTrue( a.equals( b ) );
        
        byte[] ba1 = new byte[128];
        System.arraycopy( ba, 0, ba1, 0, ba.length );
        String c = new String(ba1);
        String d = c.trim();
        
        assertTrue( a.equals( d ) );
       // assertTrue( a.equals( c ) );
        
        
    }
}
