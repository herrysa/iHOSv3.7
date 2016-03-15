package com.huge.ihos.system.systemManager.dbback.dao.hibernate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.systemManager.dbback.dao.DbBackupDao;
import com.huge.ihos.system.systemManager.dbback.model.DbBackup;
import com.huge.webapp.util.SpringContextHelper;

@Repository( "dbBackupDao" )
public class DbBackupDaoHibernate
    extends GenericDaoHibernate<DbBackup, Long>
    implements DbBackupDao {

    public DbBackupDaoHibernate() {
        super( DbBackup.class );
    }

    public void insertDbBackup( DbBackup dbv ) {
        super.save( dbv );
    }

    public void updateDbBackup( DbBackup dbv ) {
        super.save( dbv );
    }

    public DbBackup getDbBackupById( Long id ) {
        return super.get( id );
    }

    public void delDbBackupById( Long id ) {
        super.remove( id );

    }

    public void delDbBackups( String[] index ) {
        Long[] ids = new Long[index.length];
        for ( int i = 0; i < index.length; i++ ) {
            ids[i] = new Long( index[i] );
        }

        for ( int i = 0; i < ids.length; i++ ) {
            this.delDbBackupById( ids[i] );
        }
    }

    /*
     * @Override public void backUpDataBase(String baseDir, String bakFileName,
     * 
     * }
     */

    public void deleteBackUpFile( String baseDir, String bakFileName ) {
        String zipFile = baseDir + "\\" + bakFileName + ".zip";
        File file = new File( zipFile );
        if ( file.exists() )
            file.delete();
    }

    public String backUpDataBase( String baseDir, String bakFileName, String dbName ) {
        String zfSize = "";
        String sql = "backup database " + dbName + " to disk='" + baseDir + "\\" + bakFileName.replace( ".zip", "" ) + ".bak" + "'";

        DataSource ds = (DataSource) SpringContextHelper.getBean( "dataSource" );
        JdbcTemplate jt = new JdbcTemplate( ds );
        int i = jt.update( sql );

        //int i = sqlPLrocessor.executeUpdate(sql);
        /*
         * stmt = conn.createStatement(); //String sql =
         * "backup database iHOS to disk='"+sourceDir+"'"; boolean rs =
         * stmt.execute(sql);
         */

        System.out.print( i );

        String zipFile = baseDir + "\\" + bakFileName;

        OutputStream os;

        try {
            os = new FileOutputStream( zipFile );

            BufferedOutputStream bos = new BufferedOutputStream( os );

            ZipOutputStream zos = new ZipOutputStream( bos );

            File file = new File( baseDir + "\\" + bakFileName.replace( ".zip", "" ) + ".bak" );

            String basePath = null;

            if ( file.isDirectory() ) {

                basePath = file.getPath();

            }
            else {// 直接压缩单个文件时，取父目录

                basePath = file.getParent();

            }

            zipFile( file, basePath, zos );

            zos.closeEntry();

            zos.close();

            file.delete();
            File zf = new File( zipFile );
            long zfSizel = zf.length();

            DecimalFormat df = new DecimalFormat( "#0.00" );
            zfSize = df.format( (double) zfSizel / 1048576 ) + "M";
            /*long zfSizel=zf.getTotalSpace();
            long zfSizef=zf.getFreeSpace();
            zfSize= ((zf.getTotalSpace() - zf.getFreeSpace()) / 1024.0 / 1024 / 1024)+"M";*/
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return zfSize;


    }

    public static void zipFile( File source, String basePath,

    ZipOutputStream zos ) {

        File[] files = new File[0];

        if ( source.isDirectory() ) {

            files = source.listFiles();

        }
        else {

            files = new File[1];

            files[0] = source;

        }

        String pathName;// 存相对路径(相对于待压缩的根目录)

        byte[] buf = new byte[1024];

        int length = 0;

        try {

            for ( File file : files ) {

                if ( file.isDirectory() ) {

                    pathName = file.getPath().substring( basePath.length() + 1 )

                    + "/";

                    zos.putNextEntry( new ZipEntry( pathName ) );

                    zipFile( file, basePath, zos );

                }
                else {

                    pathName = file.getPath().substring( basePath.length() + 1 );

                    InputStream is = new FileInputStream( file );

                    BufferedInputStream bis = new BufferedInputStream( is );

                    zos.putNextEntry( new ZipEntry( pathName ) );

                    while ( ( length = bis.read( buf ) ) > 0 ) {

                        zos.write( buf, 0, length );

                    }

                    is.close();

                }

            }

        }
        catch ( Exception e ) {

            e.printStackTrace();

        }

    }

    public String getCurrentDbName() {
        try {
            String dbn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection().getCatalog();
            return dbn;
        }
        catch ( Exception e ) {
            e.printStackTrace();

            throw new BusinessException( "获取当前数据库名称异常，请检查。具体异常信息如下：\n\r" + e.getMessage() );
        }
        //		return null;
    }
}
