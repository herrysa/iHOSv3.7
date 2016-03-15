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
@Table( name = "t_customer_info" )
public class CustomerInfo
    extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = -6140281237668070275L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long customerInfoId;

    @Column( name = "customerName", length = 100, nullable = false )
    private String customerName;

    @Column( name = "note", length = 1000, nullable = true )
    private String note;
    
    @Column( name = "oemFlag",nullable = false )
    private boolean oemFlag;
    
    
    @Column( name = "oemVender", length = 100, nullable = true )
    private String  oemVender;
    
    @Column( name = "oemKeyMsg", length = 100, nullable = true )
    private String  oemKeyMsg;
    
    @Column( name = "machineInfo", length = 4000 )
    private String machineInfo;
    public String getMachineInfo() {
		return machineInfo;
	}

	public void setMachineInfo(String machineInfo) {
		this.machineInfo = machineInfo;
	}
	
	@Column( name = "haspType")
    private String haspType;
    
    
    public String getHaspType() {
		return haspType;
	}

	public void setHaspType(String haspType) {
		this.haspType = haspType;
	}
	
	@Column( name = "orgType" )
    private String orgType;
    
    
    public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	@Column( name = "orgNum" )
	private Integer orgNum;

	public Integer getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(Integer orgNum) {
		this.orgNum = orgNum;
	}

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customerInfo" )
    private Set<LicenseInfo> licenseInfoes = new HashSet<LicenseInfo>( 0 );

    public Long getCustomerInfoId() {
        return customerInfoId;
    }

    public void setCustomerInfoId( Long customerId ) {
        this.customerInfoId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName( String customerName ) {
        this.customerName = customerName;
    }

    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    public Set<LicenseInfo> getLicenseInfoes() {
        return licenseInfoes;
    }

    public void setLicenseInfoes( Set<LicenseInfo> licenseInfoes ) {
        this.licenseInfoes = licenseInfoes;
    }

    public boolean isOemFlag() {
        return oemFlag;
    }

    public void setOemFlag( boolean oemFlag ) {
        this.oemFlag = oemFlag;
    }

    public String getOemVender() {
        return oemVender;
    }

    public void setOemVender( String oemVender ) {
        this.oemVender = oemVender;
    }

    public String getOemKeyMsg() {
        return oemKeyMsg;
    }

    public void setOemKeyMsg( String oemKeyMsg ) {
        this.oemKeyMsg = oemKeyMsg;
    }

    @Override
    public String toString() {
        return "CustomerInfo [customerInfoId=" + customerInfoId + ", customerName=" + customerName + ", note=" + note + ", oemFlag=" + oemFlag
            + ", oemVender=" + oemVender + ", oemKeyMsg=" + oemKeyMsg 
           // + ", licenseInfoes=" + licenseInfoes 
            + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( customerInfoId == null ) ? 0 : customerInfoId.hashCode() );
        result = prime * result + ( ( customerName == null ) ? 0 : customerName.hashCode() );
       // result = prime * result + ( ( licenseInfoes == null ) ? 0 : licenseInfoes.hashCode() );
        result = prime * result + ( ( note == null ) ? 0 : note.hashCode() );
        result = prime * result + ( oemFlag ? 1231 : 1237 );
        result = prime * result + ( ( oemKeyMsg == null ) ? 0 : oemKeyMsg.hashCode() );
        result = prime * result + ( ( oemVender == null ) ? 0 : oemVender.hashCode() );
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
        CustomerInfo other = (CustomerInfo) obj;
        if ( customerInfoId == null ) {
            if ( other.customerInfoId != null )
                return false;
        }
        else if ( !customerInfoId.equals( other.customerInfoId ) )
            return false;
        if ( customerName == null ) {
            if ( other.customerName != null )
                return false;
        }
        else if ( !customerName.equals( other.customerName ) )
            return false;
        if ( licenseInfoes == null ) {
            if ( other.licenseInfoes != null )
                return false;
        }
     /*   else if ( !licenseInfoes.equals( other.licenseInfoes ) )
            return false;
        if ( note == null ) {
            if ( other.note != null )
                return false;
        }*/
        else if ( !note.equals( other.note ) )
            return false;
        if ( oemFlag != other.oemFlag )
            return false;
        if ( oemKeyMsg == null ) {
            if ( other.oemKeyMsg != null )
                return false;
        }
        else if ( !oemKeyMsg.equals( other.oemKeyMsg ) )
            return false;
        if ( oemVender == null ) {
            if ( other.oemVender != null )
                return false;
        }
        else if ( !oemVender.equals( other.oemVender ) )
            return false;
        return true;
    }
}
