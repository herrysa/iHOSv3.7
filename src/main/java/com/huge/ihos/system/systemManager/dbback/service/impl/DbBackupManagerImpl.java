package com.huge.ihos.system.systemManager.dbback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.dbback.dao.DbBackupDao;
import com.huge.ihos.system.systemManager.dbback.model.DbBackup;
import com.huge.ihos.system.systemManager.dbback.service.DbBackupManager;
import com.huge.service.impl.GenericManagerImpl;

@Service( "dbBackupManager" )
public class DbBackupManagerImpl
    extends GenericManagerImpl<DbBackup, Long>
    implements DbBackupManager {

    private DbBackupDao dbBackupDao;

    public DbBackupDao getDbBackupDao() {
        return dbBackupDao;
    }

    @Autowired
    public void setDbBackupDao( DbBackupDao dbBackupDao ) {
        this.dbBackupDao = dbBackupDao;
    }

    @Autowired
    public DbBackupManagerImpl( DbBackupDao dbBackupDao ) {
        super( dbBackupDao );
        this.dbBackupDao = dbBackupDao;
    }

    public void insertDataBackup( DbBackup dbv, String baseDir, String dbName ) {
        String zfSize = this.getDbBackupDao().backUpDataBase( baseDir, dbv.getDbBackupFileName(), dbName );
        dbv.setDbBackupFileSize( zfSize );
        this.getDbBackupDao().insertDbBackup( dbv );
    }

    public DbBackup getDbBackupById( Long id ) {
        return this.getDbBackupDao().getDbBackupById( id );
    }

    public void delDbBackups( String[] index, String baseDir ) {
        Long[] ids = new Long[index.length];
        for ( int i = 0; i < index.length; i++ ) {
            ids[i] = new Long( index[i] );
        }
        for ( int i = 0; i < ids.length; i++ ) {
            this.deleteBackupFile( ids[i], baseDir );
        }

        this.getDbBackupDao().delDbBackups( index );
    }

    private void deleteBackupFile( Long id, String baseDir ) {
        DbBackup dbv = this.getDbBackupDao().getDbBackupById( id );

        this.getDbBackupDao().deleteBackUpFile( baseDir, dbv.getDbBackupFileName() );
    }

    public String getCurrentDbName() {
        return getDbBackupDao().getCurrentDbName();
    }

    /*	@Override
     public boolean checkBackupedFile(int Long, String baseDir) {
     return false;
     }
    
     @Override
     public void downloadBackupFile(int Long, String baseDir, OutputStream os) {
    
     }*/

}
