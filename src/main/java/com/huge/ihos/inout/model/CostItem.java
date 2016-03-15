package com.huge.ihos.inout.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_costitem" )
public class CostItem
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1865212466457390158L;

    private String costItemId;

    private String costItemName;

    private String costDesc;

    private String behaviour;

    private Boolean controllable = true;

    private Boolean leaf = true;

    private Boolean disabled = false;

    private Integer clevel;

    private String cnCode;

    private String medOrLee;

    private String userDefine_1;

    private String userDefine_2;

    private String userDefine_3;

    private CostUse costUse;

    private CostItem parent;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "costUseId", nullable = true )
    public CostUse getCostUse() {
        return costUse;
    }

    public void setCostUse( CostUse costUse ) {
        this.costUse = costUse;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "parentId", nullable = true )
    public CostItem getParent() {
        return parent;
    }

    public void setParent( CostItem parent ) {
        this.parent = parent;
    }

    @Id
    public String getCostItemId() {
        return this.costItemId;
    }

    public void setCostItemId( String costItemId ) {
        this.costItemId = costItemId;
    }

    @Column( name = "costItem", nullable = false, length = 30 )
    public String getCostItemName() {
        return this.costItemName;
    }

    public void setCostItemName( String costItem ) {
        this.costItemName = costItem;
    }

    @Column( name = "costDesc", nullable = false, length = 100 )
    public String getCostDesc() {
        return this.costDesc;
    }

    public void setCostDesc( String costDesc ) {
        this.costDesc = costDesc;
    }

    @Column( name = "behaviour", nullable = false, length = 10 )
    public String getBehaviour() {
        return this.behaviour;
    }

    public void setBehaviour( String behaviour ) {
        this.behaviour = behaviour;
    }

    @Column( name = "controllable", nullable = false )
    public Boolean getControllable() {
        return this.controllable;
    }

    public void setControllable( Boolean controllable ) {
        this.controllable = controllable;
    }

    @Column( name = "leaf", nullable = false )
    public Boolean getLeaf() {
        return this.leaf;
    }

    public void setLeaf( Boolean leaf ) {
        this.leaf = leaf;
    }

    @Column( name = "disabled", nullable = false )
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

    @Column( name = "clevel", nullable = false )
    public Integer getClevel() {
        return this.clevel;
    }

    public void setClevel( Integer clevel ) {
        this.clevel = clevel;
    }

    @Column( name = "cnCode", length = 30 )
    public String getCnCode() {
        return this.cnCode;
    }

    public void setCnCode( String cnCode ) {
        this.cnCode = cnCode;
    }

    @Column( name = "userDefine1", length = 30 )
    public String getUserDefine1() {
        return this.userDefine_1;
    }

    public void setUserDefine1( String field1 ) {
        this.userDefine_1 = field1;
    }

    @Column( name = "userDefine2", length = 30 )
    public String getUserDefine2() {
        return this.userDefine_2;
    }

    public void setUserDefine2( String field2 ) {
        this.userDefine_2 = field2;
    }

    @Column( name = "userDefine3", length = 30 )
    public String getUserDefine3() {
        return this.userDefine_3;
    }

    public void setUserDefine3( String field3 ) {
        this.userDefine_3 = field3;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( behaviour == null ) ? 0 : behaviour.hashCode() );
        result = prime * result + ( ( clevel == null ) ? 0 : clevel.hashCode() );
        result = prime * result + ( ( cnCode == null ) ? 0 : cnCode.hashCode() );
        result = prime * result + ( ( controllable == null ) ? 0 : controllable.hashCode() );
        result = prime * result + ( ( costDesc == null ) ? 0 : costDesc.hashCode() );
        result = prime * result + ( ( costItemName == null ) ? 0 : costItemName.hashCode() );
        result = prime * result + ( ( costItemId == null ) ? 0 : costItemId.hashCode() );
        result = prime * result + ( ( costUse == null ) ? 0 : costUse.hashCode() );
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
        result = prime * result + ( ( leaf == null ) ? 0 : leaf.hashCode() );
        //result = prime * result + ( ( parent == null ) ? 0 : parent.hashCode() );
        result = prime * result + ( ( userDefine_1 == null ) ? 0 : userDefine_1.hashCode() );
        result = prime * result + ( ( userDefine_2 == null ) ? 0 : userDefine_2.hashCode() );
        result = prime * result + ( ( userDefine_3 == null ) ? 0 : userDefine_3.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        CostItem other = (CostItem) obj;
        if ( behaviour == null ) {
            if ( other.behaviour != null )
                return false;
        }
        else if ( !behaviour.equals( other.behaviour ) )
            return false;
        if ( clevel == null ) {
            if ( other.clevel != null )
                return false;
        }
        else if ( !clevel.equals( other.clevel ) )
            return false;
        if ( cnCode == null ) {
            if ( other.cnCode != null )
                return false;
        }
        else if ( !cnCode.equals( other.cnCode ) )
            return false;
        if ( controllable == null ) {
            if ( other.controllable != null )
                return false;
        }
        else if ( !controllable.equals( other.controllable ) )
            return false;
        if ( costDesc == null ) {
            if ( other.costDesc != null )
                return false;
        }
        else if ( !costDesc.equals( other.costDesc ) )
            return false;
        if ( costItemName == null ) {
            if ( other.costItemName != null )
                return false;
        }
        else if ( !costItemName.equals( other.costItemName ) )
            return false;
        if ( costItemId == null ) {
            if ( other.costItemId != null )
                return false;
        }
        else if ( !costItemId.equals( other.costItemId ) )
            return false;
        if ( costUse == null ) {
            if ( other.costUse != null )
                return false;
        }
        else if ( !costUse.equals( other.costUse ) )
            return false;
        if ( disabled == null ) {
            if ( other.disabled != null )
                return false;
        }
        else if ( !disabled.equals( other.disabled ) )
            return false;
        if ( leaf == null ) {
            if ( other.leaf != null )
                return false;
        }
        else if ( !leaf.equals( other.leaf ) )
            return false;
        if ( parent == null ) {
            if ( other.parent != null )
                return false;
        }
        else if ( !parent.equals( other.parent ) )
            return false;
        if ( userDefine_1 == null ) {
            if ( other.userDefine_1 != null )
                return false;
        }
        else if ( !userDefine_1.equals( other.userDefine_1 ) )
            return false;
        if ( userDefine_2 == null ) {
            if ( other.userDefine_2 != null )
                return false;
        }
        else if ( !userDefine_2.equals( other.userDefine_2 ) )
            return false;
        if ( userDefine_3 == null ) {
            if ( other.userDefine_3 != null )
                return false;
        }
        else if ( !userDefine_3.equals( other.userDefine_3 ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Costitem [costItemId=" + costItemId + ", costItem=" + costItemName + ", costDesc=" + costDesc + ", behaviour=" + behaviour
            + ", controllable=" + controllable + ", leaf=" + leaf + ", disabled=" + disabled + ", clevel=" + clevel + ", cnCode=" + cnCode
            + ", userDefine_1=" + userDefine_1 + ", userDefine_2=" + userDefine_2 + ", userDefine_3=" + userDefine_3 + ", costUse=" + costUse
            + ", parent=" + parent + "]";
    }

    @Column( name = "medorlee", length = 50 )
    public String getMedOrLee() {
        return medOrLee;
    }

    public void setMedOrLee( String medOrLee ) {
        this.medOrLee = medOrLee;
    }

    // @Column(name="costUseId", length=10)
    //
    // public String getCostUseId() {
    // return this.costUseId;
    // }
    //
    // public void setCostUseId(String costUseId) {
    // this.costUseId = costUseId;
    // }
    //
    // @Column(name="parentId", length=10)
    //
    // public String getParentId() {
    // return this.parentId;
    // }
    //
    // public void setParentId(String parentId) {
    // this.parentId = parentId;
    // }

}
