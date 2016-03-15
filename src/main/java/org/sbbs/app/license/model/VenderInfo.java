package org.sbbs.app.license.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.sbbs.base.model.BaseObject;

@Entity
@Table( name = "t_vender_info" )
public class VenderInfo
    extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = -5621844779524370343L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long venderId;

    @Column( name = "venderName", length = 100, nullable = false )
    private String venderName;

    @Column( name = "copyRightInfo", length = 200, nullable = false )
    private String copyRightInfo;

    @Column( name = "productVersion", length = 20, nullable = false )
    private String productVersion;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "venderInfo" )
    private Set<ProductInfo> productInfoes = new HashSet<ProductInfo>( 0 );

    @Override
    public String toString() {
        return "VenderInfo [venderId=" + venderId + ", venderName=" + venderName + ", copyRightInfo=" + copyRightInfo + ", productVersion="
            + productVersion + ", productInfoes=" + productInfoes + "]";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        VenderInfo other = (VenderInfo) obj;
        if ( copyRightInfo == null ) {
            if ( other.copyRightInfo != null )
                return false;
        }
        else if ( !copyRightInfo.equals( other.copyRightInfo ) )
            return false;
        if ( productInfoes == null ) {
            if ( other.productInfoes != null )
                return false;
        }
        else if ( !productInfoes.equals( other.productInfoes ) )
            return false;
        if ( productVersion == null ) {
            if ( other.productVersion != null )
                return false;
        }
        else if ( !productVersion.equals( other.productVersion ) )
            return false;
        if ( venderId == null ) {
            if ( other.venderId != null )
                return false;
        }
        else if ( !venderId.equals( other.venderId ) )
            return false;
        if ( venderName == null ) {
            if ( other.venderName != null )
                return false;
        }
        else if ( !venderName.equals( other.venderName ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( copyRightInfo == null ) ? 0 : copyRightInfo.hashCode() );
        result = prime * result + ( ( productInfoes == null ) ? 0 : productInfoes.hashCode() );
        result = prime * result + ( ( productVersion == null ) ? 0 : productVersion.hashCode() );
        result = prime * result + ( ( venderId == null ) ? 0 : venderId.hashCode() );
        result = prime * result + ( ( venderName == null ) ? 0 : venderName.hashCode() );
        return result;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId( Long venderId ) {
        this.venderId = venderId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName( String venderName ) {
        this.venderName = venderName;
    }

    public String getCopyRightInfo() {
        return copyRightInfo;
    }

    public void setCopyRightInfo( String copyRightInfo ) {
        this.copyRightInfo = copyRightInfo;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion( String productVersion ) {
        this.productVersion = productVersion;
    }

    public Set<ProductInfo> getProductInfoes() {
        return productInfoes;
    }

    public void setProductInfoes( Set<ProductInfo> productInfoes ) {
        this.productInfoes = productInfoes;
    }

}
