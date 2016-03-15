package com.huge.ihos.hql;


public class QueryOperator {

	private int value = 0;

    public static QueryOperator EQUAL = new QueryOperator( 1 );

    public static QueryOperator LIKE = new QueryOperator( 2 );

    public static QueryOperator NOT_EQUAL = new QueryOperator( 3 );

    public static QueryOperator LESS_THAN = new QueryOperator( 4 );

    public static QueryOperator LESS_EQUAL = new QueryOperator( 5 );

    public static QueryOperator GREATER_THAN = new QueryOperator( 6 );

    public static QueryOperator GREATER_EQUAL = new QueryOperator( 7 );

    public static QueryOperator IN = new QueryOperator( 8 );

    private QueryOperator( int value ) {
        this.value = value;
    }


    public boolean equals( Object obj ) {
        if ( ( obj instanceof QueryOperator ) ) {
        	QueryOperator lso = (QueryOperator) obj;
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
            case 8:
                return "in";
        }
        return "UNKNOWN";
    }
}
