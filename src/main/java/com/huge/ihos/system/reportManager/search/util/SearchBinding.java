package com.huge.ihos.system.reportManager.search.util;

import java.io.Serializable;

public class SearchBinding
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String field = null;

    private Object value = null;

    private SearchBoolean searchBoolean = SearchBoolean.AND;

    private LSearchBracket lBracket = LSearchBracket.DEFAULT;

    private SearchOperator operator = SearchOperator.EQUAL;

    private RSearchBracket rBracket = RSearchBracket.DEFAULT;

    private String htmlField;

    public SearchBinding( SearchBoolean paramSearchBoolean, String paramString1, SearchOperator paramSearchOperator, Object paramObject,
                          String paramString2 ) {
        this( paramString1, paramObject, paramString2 );
        this.searchBoolean = paramSearchBoolean;
        this.operator = paramSearchOperator;
    }

    public SearchBinding( SearchBoolean searchBoolean, String field, SearchOperator operator, Object searchValue, RSearchBracket rBracket,
                          String htmlField ) {
        this( field, searchValue, htmlField );
        this.searchBoolean = searchBoolean;
        this.operator = operator;
        this.rBracket = rBracket;
    }

    public SearchBinding( SearchBoolean searchBoolean, LSearchBracket rBracket, String field, SearchOperator operator, Object searchValue,
                          String htmlField ) {
        this( field, searchValue, htmlField );
        this.searchBoolean = searchBoolean;

        this.lBracket = rBracket;
        this.operator = operator;
    }

    public SearchBinding( SearchBoolean searchBoolean, LSearchBracket lBracket, String field, SearchOperator operator, Object searchValue,
                          RSearchBracket rBracket, String htmlField ) {
        this( searchBoolean, lBracket, field, operator, searchValue, htmlField );
        this.rBracket = rBracket;
    }

    public SearchBinding( String field, Object searchValue, String htmlField ) {
        this.field = field;
        this.htmlField = htmlField;
        if ( ( searchValue instanceof String ) ) {
            String str = (String) searchValue;
            if ( ( str.startsWith( "*" ) ) || ( str.endsWith( "*" ) ) ) {
                this.operator = SearchOperator.LIKE;
                this.value = str.replace( '*', '%' );
            }
            else {
                int i;
                if ( str.startsWith( ">=" ) ) {
                    i = str.length();
                    this.operator = SearchOperator.GREATER_EQUAL;
                    this.value = str.substring( 2, i );
                }
                else if ( str.startsWith( ">" ) ) {
                    i = str.length();
                    this.operator = SearchOperator.GREATER_THAN;
                    this.value = str.substring( 1, i );
                }
                else if ( str.startsWith( "<=" ) ) {
                    i = str.length();
                    this.operator = SearchOperator.LESS_EQUAL;
                    this.value = str.substring( 2, i );
                }
                else if ( str.startsWith( "<>" ) ) {
                    i = str.length();
                    this.operator = SearchOperator.NOT_EQUAL;
                    this.value = str.substring( 2, i );
                }
                else if ( str.startsWith( "<" ) ) {
                    i = str.length();
                    this.operator = SearchOperator.LESS_THAN;
                    this.value = str.substring( 1, i );
                }
                else {
                    this.value = searchValue;
                }
            }
        }
        else {
            this.value = searchValue;
        }
    }

    public SearchBinding( SearchCriteria criteria ) {
    }

    public SearchBoolean getBoolean() {
        return this.searchBoolean;
    }

    public String getField() {
        return this.field;
    }

    public SearchOperator getOperator() {
        return this.operator;
    }

    public Object getValue() {
        return this.value;
    }

    public LSearchBracket getLBracket() {
        return this.lBracket;
    }

    public RSearchBracket getRBracket() {
        return this.rBracket;
    }

    public String getHtmlField() {
        return this.htmlField;
    }

    public void setHtmlField( String paramString ) {
        this.htmlField = paramString;
    }
}
