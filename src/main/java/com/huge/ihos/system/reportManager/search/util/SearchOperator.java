package com.huge.ihos.system.reportManager.search.util;

import java.io.Serializable;

public class SearchOperator
    implements Serializable {
    private int value = 0;

    public static SearchOperator EQUAL = new SearchOperator( 1 );

    public static SearchOperator LIKE = new SearchOperator( 2 );

    public static SearchOperator NOT_EQUAL = new SearchOperator( 3 );

    public static SearchOperator LESS_THAN = new SearchOperator( 4 );

    public static SearchOperator LESS_EQUAL = new SearchOperator( 5 );

    public static SearchOperator GREATER_THAN = new SearchOperator( 6 );

    public static SearchOperator GREATER_EQUAL = new SearchOperator( 7 );

    public static SearchOperator IN = new SearchOperator( 8 );

    private SearchOperator( int value ) {
        this.value = value;
    }

    public SearchOperator() {
    }

    public boolean equals( Object obj ) {
        if ( ( obj instanceof SearchOperator ) ) {
            SearchOperator lso = (SearchOperator) obj;
            return lso.value == this.value;
        }
        return false;
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        switch ( this.value ) {
            case 1:
                return "=";
            case 2:
                return "LIKE";
            case 3:
                return "<>";
            case 4:
                return "<";
            case 5:
                return "<=";
            case 6:
                return ">";
            case 7:
                return ">=";
        }
        return "UNKNOWN";
    }
}
