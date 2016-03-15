package com.huge.ihos.formula.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_formulaField" )
public class FormulaField
    extends BaseObject
    implements Serializable {

    private String formulaFieldId;

    private String fieldName;

    private String fieldTitle;

    private String calcType;

    private boolean disabled;

    private String direction;

    private boolean inherited;

    private String defaultValue;

    private String keyClass;

    private String description;

    private Integer execOrder;

    /*
     * private String helperClass; private String param1; private String param2;
     */
    @Column( name = "execOrder", nullable = false )
    public Integer getExecOrder() {
        return execOrder;
    }

    public void setExecOrder( Integer execOrder ) {
        this.execOrder = execOrder;
    }

    private Formula formula;

    private FormulaEntity formulaEntity;

    @Id
    @Column( length = 30 )
    public String getFormulaFieldId() {
        return formulaFieldId;
    }

    public void setFormulaFieldId( String formulaFieldId ) {
        this.formulaFieldId = formulaFieldId;
    }

    @Column( name = "fieldName", nullable = false, length = 50, unique = false )
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName( String fieldName ) {
        this.fieldName = fieldName;
    }

    @Column( name = "fieldTitle", nullable = false, length = 50, unique = false )
    public String getFieldTitle() {
        return fieldTitle;
    }

    public void setFieldTitle( String fieldTitle ) {
        this.fieldTitle = fieldTitle;
    }

    @Column( name = "calcType", nullable = false, length = 50, unique = false )
    public String getCalcType() {
        return calcType;
    }

    public void setCalcType( String calcType ) {
        this.calcType = calcType;
    }

    @Column( name = "disabled" )
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }

    @Column( name = "direction", nullable = false, length = 50, unique = false )
    public String getDirection() {
        return direction;
    }

    public void setDirection( String direction ) {
        this.direction = direction;
    }

    @Column( name = "inherited", nullable = false )
    public boolean isInherited() {
        return inherited;
    }

    public void setInherited( boolean inherited ) {
        this.inherited = inherited;
    }

    @Column( name = "defaultValue", nullable = false, length = 50, unique = false )
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue( String defaultValue ) {
        this.defaultValue = defaultValue;
    }

    @Column( name = "keyClass", nullable = false, length = 50, unique = false )
    public String getKeyClass() {
        return keyClass;
    }

    public void setKeyClass( String keyClass ) {
        this.keyClass = keyClass;
    }

    @Column( name = "description", nullable = true, length = 500, unique = false )
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    /*
     * @Column(name = "helperClass", nullable = true, length = 100, unique =
     * false) public String getHelperClass() { return helperClass; }
     * 
     * public void setHelperClass(String helperClass) { this.helperClass =
     * helperClass; }
     */
    /*
     * @Column(name = "param1", nullable = true, length = 100, unique = false)
     * public String getParam1() { return param1; }
     * 
     * public void setParam1(String param1) { this.param1 = param1; }
     * 
     * @Column(name = "param2", nullable = true, length = 100, unique = false)
     * public String getParam2() { return param2; }
     * 
     * public void setParam2(String param2) { this.param2 = param2; }
     */

    @Embedded
    public Formula getFormula() {
        return formula;
    }

    public void setFormula( Formula formula ) {
        this.formula = formula;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "formulaEntityId", nullable = true )
    public FormulaEntity getFormulaEntity() {
        return formulaEntity;
    }

    public void setFormulaEntity( FormulaEntity formulaEntity ) {
        this.formulaEntity = formulaEntity;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -5462961969711872786L;

    @Override
    public String toString() {
        return "FormulaField [formulaFieldId=" + formulaFieldId + ", fieldName=" + fieldName + ", fieldTitle=" + fieldTitle + ", calcType="
            + calcType + ", disabled=" + disabled + ", direction=" + direction + ", inherited=" + inherited + ", defaultValue=" + defaultValue
            + ", keyClass=" + keyClass + ", description=" + description + ", formula=" + formula + ", formulaEntity=" + formulaEntity + "]";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        FormulaField other = (FormulaField) obj;
        if ( calcType == null ) {
            if ( other.calcType != null )
                return false;
        }
        else if ( !calcType.equals( other.calcType ) )
            return false;
        if ( defaultValue == null ) {
            if ( other.defaultValue != null )
                return false;
        }
        else if ( !defaultValue.equals( other.defaultValue ) )
            return false;
        if ( description == null ) {
            if ( other.description != null )
                return false;
        }
        else if ( !description.equals( other.description ) )
            return false;
        if ( direction == null ) {
            if ( other.direction != null )
                return false;
        }
        else if ( !direction.equals( other.direction ) )
            return false;
        if ( disabled != other.disabled )
            return false;
        if ( fieldName == null ) {
            if ( other.fieldName != null )
                return false;
        }
        else if ( !fieldName.equals( other.fieldName ) )
            return false;
        if ( fieldTitle == null ) {
            if ( other.fieldTitle != null )
                return false;
        }
        else if ( !fieldTitle.equals( other.fieldTitle ) )
            return false;
        if ( formula == null ) {
            if ( other.formula != null )
                return false;
        }
        else if ( !formula.equals( other.formula ) )
            return false;
        if ( formulaEntity == null ) {
            if ( other.formulaEntity != null )
                return false;
        }
        else if ( !formulaEntity.equals( other.formulaEntity ) )
            return false;
        if ( formulaFieldId == null ) {
            if ( other.formulaFieldId != null )
                return false;
        }
        else if ( !formulaFieldId.equals( other.formulaFieldId ) )
            return false;
        if ( inherited != other.inherited )
            return false;
        if ( keyClass == null ) {
            if ( other.keyClass != null )
                return false;
        }
        else if ( !keyClass.equals( other.keyClass ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( calcType == null ) ? 0 : calcType.hashCode() );
        result = prime * result + ( ( defaultValue == null ) ? 0 : defaultValue.hashCode() );
        result = prime * result + ( ( description == null ) ? 0 : description.hashCode() );
        result = prime * result + ( ( direction == null ) ? 0 : direction.hashCode() );
        result = prime * result + ( disabled ? 1231 : 1237 );
        result = prime * result + ( ( fieldName == null ) ? 0 : fieldName.hashCode() );
        result = prime * result + ( ( fieldTitle == null ) ? 0 : fieldTitle.hashCode() );
        result = prime * result + ( ( formula == null ) ? 0 : formula.hashCode() );
        result = prime * result + ( ( formulaEntity == null ) ? 0 : formulaEntity.hashCode() );
        result = prime * result + ( ( formulaFieldId == null ) ? 0 : formulaFieldId.hashCode() );
        result = prime * result + ( inherited ? 1231 : 1237 );
        result = prime * result + ( ( keyClass == null ) ? 0 : keyClass.hashCode() );
        return result;
    }

}
