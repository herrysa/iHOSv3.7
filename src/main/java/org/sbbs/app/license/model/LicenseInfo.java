package org.sbbs.app.license.model;

import java.util.Date;

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
@Table( name = "t_license_info" )
public class LicenseInfo
    extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = -6712792161785982617L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long licenseInfoId;

    @Column( name = "genDate", nullable = false )
    private Date genDate;

    @Column( name = "licensedfdata", nullable = false, length = 4000 )
    private String licenseDefineData;

    @Column( name = "licenseData", nullable = false, length = 4000 )
    private String licenseData;

    @Column( name = "initC2VData", nullable = false, length = 4000 )
    private String initC2VData;

    @Column( name = "currentUsed" )
    private Boolean currentUsed;
    
    @Column( name = "note", nullable = true, length = 1000 )
    private String note;
    
    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    public String getInitC2VData() {
        return initC2VData;
    }

    public void setInitC2VData( String initC2VData ) {
        this.initC2VData = initC2VData;
    }

    public Boolean getCurrentUsed() {
        return currentUsed;
    }

    public void setCurrentUsed( Boolean currentUsed ) {
        this.currentUsed = currentUsed;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "customerInfo_id", nullable = false )
    private CustomerInfo customerInfo;

    public Long getLicenseInfoId() {
        return licenseInfoId;
    }

    public void setLicenseInfoId( Long licenseInfoId ) {
        this.licenseInfoId = licenseInfoId;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate( Date genDate ) {
        this.genDate = genDate;
    }

    public String getLicenseData() {
        return licenseData;
    }

    public void setLicenseData( String licenseData ) {
        this.licenseData = licenseData;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo( CustomerInfo customerInfo ) {
        this.customerInfo = customerInfo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( currentUsed == null ) ? 0 : currentUsed.hashCode() );
        result = prime * result + ( ( customerInfo == null ) ? 0 : customerInfo.hashCode() );
        result = prime * result + ( ( genDate == null ) ? 0 : genDate.hashCode() );
        result = prime * result + ( ( initC2VData == null ) ? 0 : initC2VData.hashCode() );
        result = prime * result + ( ( licenseData == null ) ? 0 : licenseData.hashCode() );
        result = prime * result + ( ( licenseDefineData == null ) ? 0 : licenseDefineData.hashCode() );
        result = prime * result + ( ( licenseInfoId == null ) ? 0 : licenseInfoId.hashCode() );
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
        LicenseInfo other = (LicenseInfo) obj;
        if ( currentUsed == null ) {
            if ( other.currentUsed != null )
                return false;
        }
        else if ( !currentUsed.equals( other.currentUsed ) )
            return false;
        if ( customerInfo == null ) {
            if ( other.customerInfo != null )
                return false;
        }
        else if ( !customerInfo.equals( other.customerInfo ) )
            return false;
        if ( genDate == null ) {
            if ( other.genDate != null )
                return false;
        }
        else if ( !genDate.equals( other.genDate ) )
            return false;
        if ( initC2VData == null ) {
            if ( other.initC2VData != null )
                return false;
        }
        else if ( !initC2VData.equals( other.initC2VData ) )
            return false;
        if ( licenseData == null ) {
            if ( other.licenseData != null )
                return false;
        }
        else if ( !licenseData.equals( other.licenseData ) )
            return false;
        if ( licenseDefineData == null ) {
            if ( other.licenseDefineData != null )
                return false;
        }
        else if ( !licenseDefineData.equals( other.licenseDefineData ) )
            return false;
        if ( licenseInfoId == null ) {
            if ( other.licenseInfoId != null )
                return false;
        }
        else if ( !licenseInfoId.equals( other.licenseInfoId ) )
            return false;
        return true;
    }

    public String getLicenseDefineData() {
        return licenseDefineData;
    }

    public void setLicenseDefineData( String licenseDefineData ) {
        this.licenseDefineData = licenseDefineData;
    }

    @Override
    public String toString() {
        return "LicenseInfo [licenseInfoId=" + licenseInfoId + ", genDate=" + genDate + ", licenseDefineData=" + licenseDefineData + ", licenseData="
            + licenseData + ", initC2VData=" + initC2VData + ", currentUsed=" + currentUsed + ", customerInfo=" + customerInfo + "]";
    }
}
