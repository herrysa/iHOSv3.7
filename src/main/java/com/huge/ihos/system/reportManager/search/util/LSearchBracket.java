package com.huge.ihos.system.reportManager.search.util;

import java.io.Serializable;

public class LSearchBracket
    implements Serializable {
    private int value = 0;

    public static LSearchBracket DEFAULT = new LSearchBracket( 0 );

    public static LSearchBracket LEFT = new LSearchBracket( 1 );

    public static LSearchBracket LEFT2 = new LSearchBracket( 2 );

    public static LSearchBracket LEFT3 = new LSearchBracket( 3 );

    public static LSearchBracket LEFT4 = new LSearchBracket( 4 );

    public static LSearchBracket LEFT5 = new LSearchBracket( 5 );

    public LSearchBracket( int value ) {
        this.value = value;
    }

    public boolean equals( Object obj ) {
        if ( ( obj instanceof LSearchBracket ) ) {
            LSearchBracket lsb = (LSearchBracket) obj;
            return lsb.value == this.value;
        }
        return false;
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        String str = "";
        for ( int i = 0; i < this.value; i++ )
            str = str + "(";
        return str;
    }
}