package com.huge.ihos.system.systemManager.dbback.service;


import com.huge.ihos.system.systemManager.dbback.model.DbBackup;
import com.huge.service.GenericManager;

public interface DbBackupManager
    extends GenericManager<DbBackup, Long> {
    public void insertDataBackup( DbBackup dbv, String baseDir, String dbName );

    public DbBackup getDbBackupById( Long id );

    public void delDbBackups( String[] ids, String baseDir );

    public String getCurrentDbName();
    /*	public boolean checkBackupedFile(int Long, String baseDir);
    
     public void downloadBackupFile(int Long, String baseDir, OutputStream os);*/
}
