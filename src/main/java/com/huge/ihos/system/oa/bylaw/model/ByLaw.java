package com.huge.ihos.system.oa.bylaw.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "sy_ByLaw" )
public class ByLaw
    extends BaseObject
    implements Serializable {
    private Long byLawId;

    private String title;

    private String body;

    private String department;

    private Date createTime;

    private String creator;

    private String url;

    private String fileName;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getByLawId() {
        return this.byLawId;
    }

    public void setByLawId( Long byLawId ) {
        this.byLawId = byLawId;
    }

    @Column( name = "title", nullable = false, length = 100 )
    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    @Column( name = "body" )
    public String getBody() {
        return body;
    }

    public void setBody( String body ) {
        this.body = body;
    }

    @Column( name = "department" )
    public String getDepartment() {
        return department;
    }

    public void setDepartment( String department ) {
        this.department = department;
    }

    @Column( name = "createTime", nullable = false )
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime( Date createTime ) {
        this.createTime = createTime;
    }

    @Column( name = "creator", nullable = false )
    public String getCreator() {
        return creator;
    }

    public void setCreator( String creator ) {
        this.creator = creator;
    }

    @Column( name = "url" )
    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    @Column( name = "fileName" )
    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( body == null ) ? 0 : body.hashCode() );
        result = prime * result + ( ( byLawId == null ) ? 0 : byLawId.hashCode() );
        result = prime * result + ( ( createTime == null ) ? 0 : createTime.hashCode() );
        result = prime * result + ( ( creator == null ) ? 0 : creator.hashCode() );
        result = prime * result + ( ( department == null ) ? 0 : department.hashCode() );
        result = prime * result + ( ( fileName == null ) ? 0 : fileName.hashCode() );
        result = prime * result + ( ( title == null ) ? 0 : title.hashCode() );
        result = prime * result + ( ( url == null ) ? 0 : url.hashCode() );
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
        ByLaw other = (ByLaw) obj;
        if ( body == null ) {
            if ( other.body != null )
                return false;
        }
        else if ( !body.equals( other.body ) )
            return false;
        if ( byLawId == null ) {
            if ( other.byLawId != null )
                return false;
        }
        else if ( !byLawId.equals( other.byLawId ) )
            return false;
        if ( createTime == null ) {
            if ( other.createTime != null )
                return false;
        }
        else if ( !createTime.equals( other.createTime ) )
            return false;
        if ( creator == null ) {
            if ( other.creator != null )
                return false;
        }
        else if ( !creator.equals( other.creator ) )
            return false;
        if ( department == null ) {
            if ( other.department != null )
                return false;
        }
        else if ( !department.equals( other.department ) )
            return false;
        if ( fileName == null ) {
            if ( other.fileName != null )
                return false;
        }
        else if ( !fileName.equals( other.fileName ) )
            return false;
        if ( title == null ) {
            if ( other.title != null )
                return false;
        }
        else if ( !title.equals( other.title ) )
            return false;
        if ( url == null ) {
            if ( other.url != null )
                return false;
        }
        else if ( !url.equals( other.url ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ByLaw [byLawId=" + byLawId + ", title=" + title + ", body=" + body + ", department=" + department + ", createTime=" + createTime
            + ", creator=" + creator + ", url=" + url + ", fileName=" + fileName + "]";
    }

}