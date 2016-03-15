package com.huge.ihos.system.reportManager.search.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_report" )
public class Report
    extends BaseObject
    implements Serializable {
    private Long reportId;

    private String groupName;

    private String reportName;

    private String url;

    private Short reqNo;

    private String reportType;

    private String remark;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getReportId() {
        return this.reportId;
    }

    public void setReportId( Long reportId ) {
        this.reportId = reportId;
    }

    @Column( name = "groupName", nullable = false, length = 30 )
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName( String groupName ) {
        this.groupName = groupName;
    }

    @Column( name = "reportName", nullable = false, length = 50 )
    public String getReportName() {
        return this.reportName;
    }

    public void setReportName( String reportName ) {
        this.reportName = reportName;
    }

    @Column( name = "url" )
    public String getUrl() {
        return this.url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    @Column( name = "reqNo" )
    public Short getReqNo() {
        return this.reqNo;
    }

    public void setReqNo( Short reqNo ) {
        this.reqNo = reqNo;
    }

    @Column( name = "reportType", length = 20 )
    public String getReportType() {
        return this.reportType;
    }

    public void setReportType( String reportType ) {
        this.reportType = reportType;
    }

    @Column( name = "remark", length = 100 )
    public String getRemark() {
        return this.remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Report pojo = (Report) o;

        if ( groupName != null ? !groupName.equals( pojo.groupName ) : pojo.groupName != null )
            return false;
        if ( reportName != null ? !reportName.equals( pojo.reportName ) : pojo.reportName != null )
            return false;
        if ( url != null ? !url.equals( pojo.url ) : pojo.url != null )
            return false;
        if ( reqNo != null ? !reqNo.equals( pojo.reqNo ) : pojo.reqNo != null )
            return false;
        if ( reportType != null ? !reportType.equals( pojo.reportType ) : pojo.reportType != null )
            return false;
        if ( remark != null ? !remark.equals( pojo.remark ) : pojo.remark != null )
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = ( groupName != null ? groupName.hashCode() : 0 );
        result = 31 * result + ( reportName != null ? reportName.hashCode() : 0 );
        result = 31 * result + ( url != null ? url.hashCode() : 0 );
        result = 31 * result + ( reqNo != null ? reqNo.hashCode() : 0 );
        result = 31 * result + ( reportType != null ? reportType.hashCode() : 0 );
        result = 31 * result + ( remark != null ? remark.hashCode() : 0 );

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "reportId" ).append( "='" ).append( getReportId() ).append( "', " );
        sb.append( "groupName" ).append( "='" ).append( getGroupName() ).append( "', " );
        sb.append( "reportName" ).append( "='" ).append( getReportName() ).append( "', " );
        sb.append( "url" ).append( "='" ).append( getUrl() ).append( "', " );
        sb.append( "reqNo" ).append( "='" ).append( getReqNo() ).append( "', " );
        sb.append( "reportType" ).append( "='" ).append( getReportType() ).append( "', " );
        sb.append( "remark" ).append( "='" ).append( getRemark() ).append( "'" );
        sb.append( "]" );

        return sb.toString();
    }

}
