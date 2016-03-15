package com.huge.ihos.formula.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_formulaentity" )
public class FormulaEntity
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4982065304179949505L;

    private String formulaEntityId;

    private String tableName;

    private String entityName;

    private String entityDesc;

    private Set<FormulaField> formulaFields;

    @Id
    @Column( length = 30 )
    public String getFormulaEntityId() {
        return formulaEntityId;
    }

    public void setFormulaEntityId( String formulaEntityId ) {
        this.formulaEntityId = formulaEntityId;
    }

    @Column( name = "tableName", nullable = false, length = 50, unique = true )
    public String getTableName() {
        return tableName;
    }

    public void setTableName( String tableName ) {
        this.tableName = tableName;
    }

    @Column( name = "entityName", nullable = false, length = 50, unique = true )
    public String getEntityName() {
        return entityName;
    }

    public void setEntityName( String entityName ) {
        this.entityName = entityName;
    }

    @Column( name = "entityDesc", nullable = true, length = 500, unique = false )
    public String getEntityDesc() {
        return entityDesc;
    }

    public void setEntityDesc( String entityDesc ) {
        this.entityDesc = entityDesc;
    }

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "formulaEntity" )
    public Set<FormulaField> getFormulaFields() {
        return formulaFields;
    }

    public void setFormulaFields( Set<FormulaField> formulaFields ) {
        this.formulaFields = formulaFields;
    }

    @Override
    public String toString() {
        return "FormulaEntity [formulaEntityId=" + formulaEntityId + ", tableName=" + tableName + ", entityName=" + entityName + ", entityDesc="
            + entityDesc + ", formulaFields=" + formulaFields + "]";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        FormulaEntity other = (FormulaEntity) obj;
        if ( entityDesc == null ) {
            if ( other.entityDesc != null )
                return false;
        }
        else if ( !entityDesc.equals( other.entityDesc ) )
            return false;
        if ( entityName == null ) {
            if ( other.entityName != null )
                return false;
        }
        else if ( !entityName.equals( other.entityName ) )
            return false;
        if ( formulaEntityId == null ) {
            if ( other.formulaEntityId != null )
                return false;
        }
        else if ( !formulaEntityId.equals( other.formulaEntityId ) )
            return false;
        if ( formulaFields == null ) {
            if ( other.formulaFields != null )
                return false;
        }
        else if ( !formulaFields.equals( other.formulaFields ) )
            return false;
        if ( tableName == null ) {
            if ( other.tableName != null )
                return false;
        }
        else if ( !tableName.equals( other.tableName ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( entityDesc == null ) ? 0 : entityDesc.hashCode() );
        result = prime * result + ( ( entityName == null ) ? 0 : entityName.hashCode() );
        result = prime * result + ( ( formulaEntityId == null ) ? 0 : formulaEntityId.hashCode() );
        //		result = prime * result + ((formulaFields == null) ? 0 : formulaFields.hashCode());
        result = prime * result + ( ( tableName == null ) ? 0 : tableName.hashCode() );
        return result;
    }
}
