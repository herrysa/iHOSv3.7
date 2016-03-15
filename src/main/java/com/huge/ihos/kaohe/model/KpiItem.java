package com.huge.ihos.kaohe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.formula.model.FormulaField;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseTreeNode;

@Entity
@Table( name = "KH_KEYDEPOT" )
public class KpiItem
    extends BaseTreeNode<KpiItem, Long> {

    /**
     * 
     */
    private static final long serialVersionUID = 2704435064180542025L;

    private String keyCode;;

    private String keyName;;

    private Integer displayOrder;

    private FormulaField targetField;

    private FormulaField actualField;

    private FormulaField scoreField;

    private Boolean disabled = false;

    private Boolean used = true;

    private Boolean leaf = false;

    private Department executeDept;

    private String pattern;

    private String inputType;

    private String periodType=KpiConstants.KPI_PERIODTYPE_MONTH;

    @Column( name = "periodType", length = 10, nullable = false )
    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType( String periodType ) {
        this.periodType = periodType;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "execDept_id", nullable = true )
    public Department getExecuteDept() {
        return executeDept;
    }

    public void setExecuteDept( Department executeDept ) {
        this.executeDept = executeDept;
    }

    @Column( name = "pattern", length = 50, nullable = true )
    public String getPattern() {
        return pattern;
    }

    public void setPattern( String pattern ) {
        this.pattern = pattern;
    }

    @Column( name = "inputType", length = 50, nullable = true )
    public String getInputType() {
        return inputType;
    }

    public void setInputType( String inputType ) {
        this.inputType = inputType;
    }

    @Column( name = "keyCode", length = 50, nullable = false )
    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode( String keyCode ) {
        this.keyCode = keyCode;
    }

    @Column( name = "keyName", length = 50, nullable = false )
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName( String keyName ) {
        this.keyName = keyName;
    }

    @Column( name = "dOrder", columnDefinition = "int default 0" )
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder( Integer displayOrder ) {
        this.displayOrder = displayOrder;
    }

    // @Column(name = "targetField",columnDefinition = "varchar(50) NULL DEFAULT   '' ")
   // @Column( name = "targetField", length = 50, nullable = true )
    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "targetFormula_id", nullable = true )
    public FormulaField getTargetField() {
        return targetField;
    }

    public void setTargetField( FormulaField targetField ) {
        this.targetField = targetField;
    }

  //  @Column( name = "actualField", length = 50, nullable = true )
    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "actualFormula_id", nullable = true )
    public FormulaField getActualField() {
        return actualField;
    }

    public void setActualField( FormulaField actualField ) {
        this.actualField = actualField;
    }

    //@Column( name = "scoreField", length = 50, nullable = true )
    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "scoreFormula_id", nullable = true )
    public FormulaField getScoreField() {
        return scoreField;
    }

    public void setScoreField( FormulaField scoreField ) {
        this.scoreField = scoreField;
    }

    @Column( name = "disabled", nullable = false )
    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

    @Column( name = "isUsed", nullable = false )
    public Boolean getUsed() {
        return used;
    }

    public void setUsed( Boolean used ) {
        this.used = used;
    }

    @Column( name = "leaf", nullable = false )
    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf( Boolean leaf ) {
        this.leaf = leaf;
    }

    @Transient
    public String getKeyNameWithCode() {
        return this.getKeyName() + "(" + this.getKeyCode() + ")";
    }

   /* @Transient
    public String getIconSkin() {
        switch ( this.getLevel() ) {
            case KpiConstants.KPI_ROOT_LEVEL:
                return "pIcon00";
            case KpiConstants.KPI_DIMENSION_LEVEL:
                return "pIcon01";
            case KpiConstants.KPI_CATEGORY_LEVEL:
                return "pIcon02";
                
                 * case KpiConstants.KPI_ITEM_LEVEL: return "pIcon03";
                 
            default:
                return "";
        }

    }*/

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( actualField == null ) ? 0 : actualField.hashCode() );
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
        result = prime * result + ( ( displayOrder == null ) ? 0 : displayOrder.hashCode() );
        result = prime * result + ( ( executeDept == null ) ? 0 : executeDept.hashCode() );
        result = prime * result + ( ( inputType == null ) ? 0 : inputType.hashCode() );
        result = prime * result + ( ( keyCode == null ) ? 0 : keyCode.hashCode() );
        result = prime * result + ( ( keyName == null ) ? 0 : keyName.hashCode() );
        result = prime * result + ( ( leaf == null ) ? 0 : leaf.hashCode() );
        result = prime * result + ( ( pattern == null ) ? 0 : pattern.hashCode() );
        result = prime * result + ( ( scoreField == null ) ? 0 : scoreField.hashCode() );
        result = prime * result + ( ( targetField == null ) ? 0 : targetField.hashCode() );
        result = prime * result + ( ( used == null ) ? 0 : used.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( !super.equals( obj ) )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        KpiItem other = (KpiItem) obj;
        if ( actualField == null ) {
            if ( other.actualField != null )
                return false;
        }
        else if ( !actualField.equals( other.actualField ) )
            return false;
        if ( disabled == null ) {
            if ( other.disabled != null )
                return false;
        }
        else if ( !disabled.equals( other.disabled ) )
            return false;
        if ( displayOrder == null ) {
            if ( other.displayOrder != null )
                return false;
        }
        else if ( !displayOrder.equals( other.displayOrder ) )
            return false;
        if ( executeDept == null ) {
            if ( other.executeDept != null )
                return false;
        }
        else if ( !executeDept.equals( other.executeDept ) )
            return false;
        if ( inputType == null ) {
            if ( other.inputType != null )
                return false;
        }
        else if ( !inputType.equals( other.inputType ) )
            return false;
        if ( keyCode == null ) {
            if ( other.keyCode != null )
                return false;
        }
        else if ( !keyCode.equals( other.keyCode ) )
            return false;
        if ( keyName == null ) {
            if ( other.keyName != null )
                return false;
        }
        else if ( !keyName.equals( other.keyName ) )
            return false;
        if ( leaf == null ) {
            if ( other.leaf != null )
                return false;
        }
        else if ( !leaf.equals( other.leaf ) )
            return false;
        if ( pattern == null ) {
            if ( other.pattern != null )
                return false;
        }
        else if ( !pattern.equals( other.pattern ) )
            return false;
        if ( scoreField == null ) {
            if ( other.scoreField != null )
                return false;
        }
        else if ( !scoreField.equals( other.scoreField ) )
            return false;
        if ( targetField == null ) {
            if ( other.targetField != null )
                return false;
        }
        else if ( !targetField.equals( other.targetField ) )
            return false;
        if ( used == null ) {
            if ( other.used != null )
                return false;
        }
        else if ( !used.equals( other.used ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "KpiItem [keyCode=" + keyCode + ", keyName=" + keyName + ", displayOrder=" + displayOrder + ", targetField=" + targetField
            + ", actualField=" + actualField + ", scoreField=" + scoreField + ", disabled=" + disabled + ", used=" + used + ", leaf=" + leaf
            + ", executeDept=" + executeDept + ", pattern=" + pattern + ", inputType=" + inputType + "]";
    }

}
