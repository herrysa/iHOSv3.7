package com.huge.ihos.system.reportManager.search.util;

import java.io.Serializable;

public class SearchBoolean
    implements Serializable {
    private int value = 0;

    public static SearchBoolean AND = new SearchBoolean( 1 );

    public static SearchBoolean OR = new SearchBoolean( 2 );

    private SearchBoolean( int paramInt ) {
        this.value = paramInt;
    }

    public SearchBoolean() {
    }

    public boolean equals( Object obj ) {
        if ( ( obj instanceof SearchBoolean ) ) {
            SearchBoolean lsb = (SearchBoolean) obj;
            return lsb.value == this.value;
        }
        return false;
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        switch ( this.value ) {
            case 1:
                return "AND";
            case 2:
                return "OR";
        }
        return "UNKNOWN";
    }
}
