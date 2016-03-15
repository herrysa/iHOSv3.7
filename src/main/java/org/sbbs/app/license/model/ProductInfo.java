package org.sbbs.app.license.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.sbbs.base.model.BaseObject;

@Entity
@Table( name = "t_product_info" )
public class ProductInfo
    extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = 3887816262271865048L;

    @Id
    @Column( name = "productId", length = 20, nullable = false )
    private String productId;

    @Column( name = "productName", length = 100, nullable = false )
    private String productName;
    
    @Column( name = "note", length = 50, nullable = true )
    private String note;
    
    
    @Column( name = "disabled" )
    private Boolean disabled = Boolean.FALSE;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "venderInfo_id", nullable = false )
    private VenderInfo venderInfo;

    public String getProductId() {
        return productId;
    }

    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    public void setProductId( String productId ) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName( String productName ) {
        this.productName = productName;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled( Boolean disabled ) {
        this.disabled = disabled;
    }

    public VenderInfo getVenderInfo() {
        return venderInfo;
    }

    public void setVenderInfo( VenderInfo venderInfo ) {
        this.venderInfo = venderInfo;
    }

    @Override
    public String toString() {
        return "ProductInfo [productId=" + productId + ", productName=" + productName + ", disabled=" + disabled + ", venderInfo=" + venderInfo + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( disabled == null ) ? 0 : disabled.hashCode() );
        result = prime * result + ( ( productId == null ) ? 0 : productId.hashCode() );
        result = prime * result + ( ( productName == null ) ? 0 : productName.hashCode() );
        result = prime * result + ( ( venderInfo == null ) ? 0 : venderInfo.hashCode() );
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
        ProductInfo other = (ProductInfo) obj;
        if ( disabled == null ) {
            if ( other.disabled != null )
                return false;
        }
        else if ( !disabled.equals( other.disabled ) )
            return false;
        if ( productId == null ) {
            if ( other.productId != null )
                return false;
        }
        else if ( !productId.equals( other.productId ) )
            return false;
        if ( productName == null ) {
            if ( other.productName != null )
                return false;
        }
        else if ( !productName.equals( other.productName ) )
            return false;
        if ( venderInfo == null ) {
            if ( other.venderInfo != null )
                return false;
        }
        else if ( !venderInfo.equals( other.venderInfo ) )
            return false;
        return true;
    }

}
