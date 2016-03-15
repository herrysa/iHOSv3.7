package com.huge.ihos.system.datacollection.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_interdataSource" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class DataSourceDefine
    extends BaseObject
    implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2236621154035245061L;

    @Override
    public String toString() {
        return "DataSourceDefine [dataSourceDefineId=" + dataSourceDefineId + ", dataSourceName=" + dataSourceName + ", dataSourceType="
            + dataSourceType + ", url=" + url
            //+ ", dbName=" + dbName
            + ", userName=" + userName + ", password=" + password + ", note=" + note + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( dataSourceDefineId == null ) ? 0 : dataSourceDefineId.hashCode() );
        result = prime * result + ( ( dataSourceName == null ) ? 0 : dataSourceName.hashCode() );
        result = prime * result + ( ( dataSourceType == null ) ? 0 : dataSourceType.hashCode() );
        //result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
        result = prime * result + ( ( note == null ) ? 0 : note.hashCode() );
        result = prime * result + ( ( password == null ) ? 0 : password.hashCode() );
        result = prime * result + ( ( url == null ) ? 0 : url.hashCode() );
        result = prime * result + ( ( userName == null ) ? 0 : userName.hashCode() );
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
        DataSourceDefine other = (DataSourceDefine) obj;
        if ( dataSourceDefineId == null ) {
            if ( other.dataSourceDefineId != null )
                return false;
        }
        else if ( !dataSourceDefineId.equals( other.dataSourceDefineId ) )
            return false;
        if ( dataSourceName == null ) {
            if ( other.dataSourceName != null )
                return false;
        }
        else if ( !dataSourceName.equals( other.dataSourceName ) )
            return false;
        if ( dataSourceType == null ) {
            if ( other.dataSourceType != null )
                return false;
        }
        else if ( !dataSourceType.equals( other.dataSourceType ) )
            return false;
        //		if (dbName == null) {
        //			if (other.dbName != null)
        //				return false;
        //		} else if (!dbName.equals(other.dbName))
        //			return false;
        if ( note == null ) {
            if ( other.note != null )
                return false;
        }
        else if ( !note.equals( other.note ) )
            return false;
        if ( password == null ) {
            if ( other.password != null )
                return false;
        }
        else if ( !password.equals( other.password ) )
            return false;
        if ( url == null ) {
            if ( other.url != null )
                return false;
        }
        else if ( !url.equals( other.url ) )
            return false;
        if ( userName == null ) {
            if ( other.userName != null )
                return false;
        }
        else if ( !userName.equals( other.userName ) )
            return false;
        return true;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "dataSourceId" )
    public Long getDataSourceDefineId() {
        return dataSourceDefineId;
    }

    public void setDataSourceDefineId( Long dataSourceDefineId ) {
        this.dataSourceDefineId = dataSourceDefineId;
    }

    @Column( name = "dsName", unique = true, nullable = false, length = 50 )
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName( String dataSourceName ) {
        this.dataSourceName = dataSourceName;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "dataSourceTypeId", nullable = false )
    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType( DataSourceType dataSourceType ) {
        this.dataSourceType = dataSourceType;
    }

    @Column( name = "note", length = 100 )
    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    @Column( name = "url", nullable = true, length = 200 )
    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    //	@Column(name = "dbName", nullable = true, length = 20)
    //	public String getDbName() {
    //		return dbName;
    //	}
    //
    //	public void setDbName(String dbName) {
    //		this.dbName = dbName;
    //	}
    @Column( name = "userName", nullable = true, length = 20 )
    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    @Column( name = "password", nullable = true, length = 20 )
    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    @Column( name = "deptId", length = 20 )
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId( String deptId ) {
        this.deptId = deptId;
    }

    private Long dataSourceDefineId;

    private String dataSourceName;

    private DataSourceType dataSourceType;

    private String url;

    //private String dbName;
    private String userName;

    private String password;

    private String note;

    private String deptId;
}
