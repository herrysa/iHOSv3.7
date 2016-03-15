package com.huge.ihos.system.datacollection.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_interdataSourceType" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class DataSourceType
    extends BaseObject
    implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5580714149406319295L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "dataSourceTypeId" )
    public Long getDataSourceTypeId() {
        return dataSourceTypeId;
    }

    public void setDataSourceTypeId( Long dataSourceTypeId ) {
        this.dataSourceTypeId = dataSourceTypeId;
    }

    @Column( name = "datasource_type_name", unique = true, nullable = false, length = 50 )
    public String getDataSourceTypeName() {
        return dataSourceTypeName;
    }

    public void setDataSourceTypeName( String dataSourceTypeName ) {
        this.dataSourceTypeName = dataSourceTypeName;
    }

    @Column( name = "is_needfile", nullable = false )
    public Boolean getIsNeedFile() {
        return isNeedFile;
    }

    public void setIsNeedFile( Boolean isNeedFile ) {
        this.isNeedFile = isNeedFile;
    }

    @Column( name = "helperclass_name", length = 100 )
    public String getHelperClassName() {
        return helperClassName;
    }

    public void setHelperClassName( String helperClassName ) {
        this.helperClassName = helperClassName;
    }

    @Column( name = "fileType", length = 10 )
    public String getFileType() {
        return fileType;
    }

    public void setFileType( String fileType ) {
        this.fileType = fileType;
    }

    @Column( name = "url_template", length = 100 )
    public String getUrlTemplate() {
        return urlTemplate;
    }

    public void setUrlTemplate( String urlTemplate ) {
        this.urlTemplate = urlTemplate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( dataSourceTypeId == null ) ? 0 : dataSourceTypeId.hashCode() );
        result = prime * result + ( ( dataSourceTypeName == null ) ? 0 : dataSourceTypeName.hashCode() );
        result = prime * result + ( ( fileType == null ) ? 0 : fileType.hashCode() );
        result = prime * result + ( ( helperClassName == null ) ? 0 : helperClassName.hashCode() );
        result = prime * result + ( ( isNeedFile == null ) ? 0 : isNeedFile.hashCode() );
        result = prime * result + ( ( urlTemplate == null ) ? 0 : urlTemplate.hashCode() );
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
        DataSourceType other = (DataSourceType) obj;
        if ( dataSourceTypeId == null ) {
            if ( other.dataSourceTypeId != null )
                return false;
        }
        else if ( !dataSourceTypeId.equals( other.dataSourceTypeId ) )
            return false;
        if ( dataSourceTypeName == null ) {
            if ( other.dataSourceTypeName != null )
                return false;
        }
        else if ( !dataSourceTypeName.equals( other.dataSourceTypeName ) )
            return false;
        if ( fileType == null ) {
            if ( other.fileType != null )
                return false;
        }
        else if ( !fileType.equals( other.fileType ) )
            return false;
        if ( helperClassName == null ) {
            if ( other.helperClassName != null )
                return false;
        }
        else if ( !helperClassName.equals( other.helperClassName ) )
            return false;
        if ( isNeedFile == null ) {
            if ( other.isNeedFile != null )
                return false;
        }
        else if ( !isNeedFile.equals( other.isNeedFile ) )
            return false;
        if ( urlTemplate == null ) {
            if ( other.urlTemplate != null )
                return false;
        }
        else if ( !urlTemplate.equals( other.urlTemplate ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DataSourceType [dataSourceTypeId=" + dataSourceTypeId + ", dataSourceTypeName=" + dataSourceTypeName + ", isNeedFile=" + isNeedFile
            + ", fileType=" + fileType + ", helperClassName=" + helperClassName + ", urlTemplate=" + urlTemplate + "]";
    }

    private Long dataSourceTypeId;

    private String dataSourceTypeName;

    private Boolean isNeedFile = false;

    private String fileType;

    private String helperClassName;

    private String urlTemplate;
}
