package com.huge.ihos.system.oa.bulletin.model;

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
@Table( name = "sy_Bulletin" )
public class Bulletin
    extends BaseObject
    implements Serializable {
    private Long bulletinId;

    private String subject;

    private String content;

    private String secretLevel;

    private String department;

    private Date createTime;

    private String creator;

    private String url;

    private String fileName;

    private int openNum;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getBulletinId() {
        return this.bulletinId;
    }

    public void setBulletinId( Long bulletinId ) {
        this.bulletinId = bulletinId;
    }

    @Column( name = "subject", nullable = false, length = 100 )
    public String getSubject() {
        return subject;
    }

    public void setSubject( String subject ) {
        this.subject = subject;
    }

    @Column( name = "content" )
    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    @Column( name = "secretLevel" )
    public String getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel( String secretLevel ) {
        this.secretLevel = secretLevel;
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

    @Column( name = "openNum" )
    public int getOpenNum() {
        return openNum;
    }

    public void setOpenNum( int openNum ) {
        this.openNum = openNum;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( bulletinId == null ) ? 0 : bulletinId.hashCode() );
        result = prime * result + ( ( content == null ) ? 0 : content.hashCode() );
        result = prime * result + ( ( createTime == null ) ? 0 : createTime.hashCode() );
        result = prime * result + ( ( creator == null ) ? 0 : creator.hashCode() );
        result = prime * result + ( ( department == null ) ? 0 : department.hashCode() );
        result = prime * result + ( ( fileName == null ) ? 0 : fileName.hashCode() );
        result = prime * result + openNum;
        result = prime * result + ( ( subject == null ) ? 0 : subject.hashCode() );
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
        Bulletin other = (Bulletin) obj;
        if ( bulletinId == null ) {
            if ( other.bulletinId != null )
                return false;
        }
        else if ( !bulletinId.equals( other.bulletinId ) )
            return false;
        if ( content == null ) {
            if ( other.content != null )
                return false;
        }
        else if ( !content.equals( other.content ) )
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
        if ( openNum != other.openNum )
            return false;
        if ( subject == null ) {
            if ( other.subject != null )
                return false;
        }
        else if ( !subject.equals( other.subject ) )
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
        return "Bulletin [bulletinId=" + bulletinId + ", subject=" + subject + ", content=" + content + ", department=" + department
            + ", createTime=" + createTime + ", creator=" + creator + ", url=" + url + ", fileName=" + fileName + ", openNum=" + openNum + "]";
    }

}
