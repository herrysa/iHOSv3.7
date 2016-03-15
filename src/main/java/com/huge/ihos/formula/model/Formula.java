package com.huge.ihos.formula.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.huge.model.BaseObject;

@Embeddable
public class Formula
    extends BaseObject
    implements Serializable {
    private String formula = "";

    private String sqlExpress = "";

    private String condition = "";

    private String sqlCondition = "";

    public String toString() {
        return "Formula [formula=" + formula + ", sqlExpress=" + sqlExpress + ", condition=" + condition + ", sqlCondition=" + sqlCondition + "]";
    }

    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Formula other = (Formula) obj;
        if ( condition == null ) {
            if ( other.condition != null )
                return false;
        }
        else if ( !condition.equals( other.condition ) )
            return false;
        if ( formula == null ) {
            if ( other.formula != null )
                return false;
        }
        else if ( !formula.equals( other.formula ) )
            return false;
        if ( sqlCondition == null ) {
            if ( other.sqlCondition != null )
                return false;
        }
        else if ( !sqlCondition.equals( other.sqlCondition ) )
            return false;
        if ( sqlExpress == null ) {
            if ( other.sqlExpress != null )
                return false;
        }
        else if ( !sqlExpress.equals( other.sqlExpress ) )
            return false;
        return true;
    }

    @Column( name = "formulaExpress", nullable = true, length = 4000, unique = false )
    public String getFormula() {
        return formula;
    }

    public void setFormula( String formula ) {
        this.formula = formula;
    }

    @Column( name = "sqlExpress", nullable = true, length = 4000, unique = false )
    public String getSqlExpress() {
        return sqlExpress;
    }

    public void setSqlExpress( String sqlExpress ) {
        this.sqlExpress = sqlExpress;
    }

    @Column( name = "conditionExpress", nullable = true, length = 4000, unique = false )
    public String getCondition() {
        return condition;
    }

    public void setCondition( String condition ) {
        this.condition = condition;
    }

    @Column( name = "sqlCondition", nullable = false, length = 4000, unique = false )
    public String getSqlCondition() {
        return sqlCondition;
    }

    public void setSqlCondition( String sqlCondition ) {
        this.sqlCondition = sqlCondition;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( condition == null ) ? 0 : condition.hashCode() );
        result = prime * result + ( ( formula == null ) ? 0 : formula.hashCode() );
        result = prime * result + ( ( sqlCondition == null ) ? 0 : sqlCondition.hashCode() );
        result = prime * result + ( ( sqlExpress == null ) ? 0 : sqlExpress.hashCode() );
        return result;
    }
}
