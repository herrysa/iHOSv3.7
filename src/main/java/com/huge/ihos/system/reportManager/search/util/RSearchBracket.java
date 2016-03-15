package com.huge.ihos.system.reportManager.search.util;

public class RSearchBracket {
    private int value = 0;

    public static RSearchBracket DEFAULT = new RSearchBracket( 0 );

    public static RSearchBracket RIGHT = new RSearchBracket( 1 );

    public static RSearchBracket RIGHT2 = new RSearchBracket( 2 );

    public static RSearchBracket RIGHT3 = new RSearchBracket( 3 );

    public static RSearchBracket RIGHT4 = new RSearchBracket( 4 );

    public static RSearchBracket RIGHT5 = new RSearchBracket( 5 );

    public RSearchBracket( int paramInt ) {
        this.value = paramInt;
    }

    public boolean equals( Object paramObject ) {
        if ( ( paramObject instanceof RSearchBracket ) ) {
            RSearchBracket localRSearchBracket = (RSearchBracket) paramObject;
            return localRSearchBracket.value == this.value;
        }
        return false;
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        String str = "";
        for ( int i = 0; i < this.value; i++ )
            str = str + ")";
        return str;
    }
}
