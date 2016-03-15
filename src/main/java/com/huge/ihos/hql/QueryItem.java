package com.huge.ihos.hql;


public class QueryItem {
	private static final long serialVersionUID = 1L;

    private String field = null;

    private Object value = null;

    private QueryJunction queryJunction = QueryJunction.AND;

    //private LSearchBracket lBracket = LSearchBracket.DEFAULT;

	private QueryOperator operator = QueryOperator.EQUAL;

    //private RSearchBracket rBracket = RSearchBracket.DEFAULT;

    //private String htmlField;


    public QueryItem( String field,String op,Object searchValue) {
        this.field = field;
        if ( ( op.equals( "*" ) ) || ( op.equals( "*" ) ) ) {
            this.operator = QueryOperator.LIKE;
        }else {
            int i;
            if ( op.equals( ">=" ) ) {
                this.operator = QueryOperator.GREATER_EQUAL;
            }
            else if ( op.equals( ">" ) ) {
                this.operator = QueryOperator.GREATER_THAN;
            }
            else if ( op.equals( "<=" ) ) {
                this.operator = QueryOperator.LESS_EQUAL;
            }
            else if ( op.equals( "<>" ) ) {
                this.operator = QueryOperator.NOT_EQUAL;
            }
            else if ( op.equals( "<" ) ) {
                this.operator = QueryOperator.LESS_THAN;
            }
            else if ( op.equals( "in" ) ) {
                this.operator = QueryOperator.IN;
            }
        }
        this.value = searchValue;
    }


    public String getField() {
        return this.field;
    }

    public QueryOperator getOperator() {
        return this.operator;
    }

    public Object getValue() {
        return this.value;
    }
    
    public QueryJunction getQueryJunction() {
		return queryJunction;
	}

	public void setQueryJunction(QueryJunction queryJunction) {
		this.queryJunction = queryJunction;
	}
}
