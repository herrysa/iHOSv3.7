package com.huge.ihos.system.systemManager.dbback.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "t_dbbackup" )
public class DbBackup {
    private Long bkid;

    private String backupDataTime;

    private String operatorName;

    private String operatorId;

    private String dbBackupFileName;

    private String dbBackupFileSize;

    private String remark;

    @Column( name = "dbBackupFileSize", nullable = true, length = 100 )
    public String getDbBackupFileSize() {
        return dbBackupFileSize;
    }

    public void setDbBackupFileSize( String dbBackupSize ) {
        this.dbBackupFileSize = dbBackupSize;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getBkid() {
        return bkid;
    }

    public void setBkid( Long bkid ) {
        this.bkid = bkid;
    }

    @Column( name = "backupDataTime", nullable = false, length = 100 )
    public String getBackupDataTime() {
        return backupDataTime;
    }

    public void setBackupDataTime( String backupDataTime ) {
        this.backupDataTime = backupDataTime;
    }

    @Column( name = "operatorName", nullable = true, length = 50 )
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName( String operatorName ) {
        this.operatorName = operatorName;
    }

    @Column( name = "operatorId", nullable = true, length = 50 )
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId( String operatorId ) {
        this.operatorId = operatorId;
    }

    @Column( name = "dbBackupFileName", nullable = false, length = 200 )
    public String getDbBackupFileName() {
        return dbBackupFileName;
    }

    public void setDbBackupFileName( String dbBackupFileName ) {
        this.dbBackupFileName = dbBackupFileName;
    }

    @Column( name = "remark", nullable = true, length = 500 )
    public String getRemark() {
        return remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( backupDataTime == null ) ? 0 : backupDataTime.hashCode() );
        result = prime * result + ( ( bkid == null ) ? 0 : bkid.hashCode() );
        result = prime * result + ( ( dbBackupFileName == null ) ? 0 : dbBackupFileName.hashCode() );
        result = prime * result + ( ( operatorId == null ) ? 0 : operatorId.hashCode() );
        result = prime * result + ( ( operatorName == null ) ? 0 : operatorName.hashCode() );
        result = prime * result + ( ( remark == null ) ? 0 : remark.hashCode() );
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
        DbBackup other = (DbBackup) obj;
        if ( backupDataTime == null ) {
            if ( other.backupDataTime != null )
                return false;
        }
        else if ( !backupDataTime.equals( other.backupDataTime ) )
            return false;
        if ( bkid == null ) {
            if ( other.bkid != null )
                return false;
        }
        else if ( !bkid.equals( other.bkid ) )
            return false;
        if ( dbBackupFileName == null ) {
            if ( other.dbBackupFileName != null )
                return false;
        }
        else if ( !dbBackupFileName.equals( other.dbBackupFileName ) )
            return false;
        if ( operatorId == null ) {
            if ( other.operatorId != null )
                return false;
        }
        else if ( !operatorId.equals( other.operatorId ) )
            return false;
        if ( operatorName == null ) {
            if ( other.operatorName != null )
                return false;
        }
        else if ( !operatorName.equals( other.operatorName ) )
            return false;
        if ( remark == null ) {
            if ( other.remark != null )
                return false;
        }
        else if ( !remark.equals( other.remark ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DbBackup [bkid=" + bkid + ", backupDataTime=" + backupDataTime + ", operatorName=" + operatorName + ", operatorId=" + operatorId
            + ", dbBackupFileName=" + dbBackupFileName + ", remark=" + remark + "]";
    }

}
