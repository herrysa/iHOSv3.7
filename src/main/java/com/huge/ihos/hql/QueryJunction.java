package com.huge.ihos.hql;


public class QueryJunction {

	private int value = 0;

    public static QueryJunction AND = new QueryJunction( 1 );

    public static QueryJunction OR = new QueryJunction( 2 );

    private QueryJunction( int paramInt ) {
        this.value = paramInt;
    }

    public QueryJunction() {
    }

    public boolean equals( Object obj ) {
        if ( ( obj instanceof QueryJunction ) ) {
        	QueryJunction lsb = (QueryJunction) obj;
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
