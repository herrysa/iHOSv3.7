package com.huge.ihos.system.systemManager.dbback.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.dbback.model.DbBackup;

public interface DbBackupDao
    extends GenericDao<DbBackup, Long> {
    public void insertDbBackup( DbBackup dbv );

    public void updateDbBackup( DbBackup dbv );

    public DbBackup getDbBackupById( Long id );

    public void delDbBackupById( Long id );

    public void delDbBackups( String[] ids );

    public String backUpDataBase( String baseDir, String bakFileName, String dbName );

    public void deleteBackUpFile( String baseDir, String bakFileName );

    public String getCurrentDbName();
}
