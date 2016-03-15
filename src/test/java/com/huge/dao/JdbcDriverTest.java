package com.huge.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import junit.framework.Assert;

import org.junit.Test;

public class JdbcDriverTest {
    //@Test
    public void testSqlserverDriver()
        throws SQLException {
        try {
            String zipFile = "d:\\sqlserver_backup\\ihos.zip";
            String sourceDir = "d:\\sqlserver_backup\\ihos3.bak";

            Class.forName( "net.sourceforge.jtds.jdbc.Driver" ).newInstance();
            String url = "jdbc:jtds:sqlserver://localhost:1433/ihos";
            String user = "sa";
            String password = "1234";
            Connection conn = null;
            conn = DriverManager.getConnection( url, user, password );
            Statement stmt = null;
            stmt = conn.createStatement();
            String sql = "backup database iHOS to disk='" + sourceDir + "'";
            boolean rs = stmt.execute( sql );

            OutputStream os;

            os = new FileOutputStream( zipFile );

            BufferedOutputStream bos = new BufferedOutputStream( os );

            ZipOutputStream zos = new ZipOutputStream( bos );

            File file = new File( sourceDir );

            String basePath = null;

            if ( file.isDirectory() ) {

                basePath = file.getPath();

            }
            else {//直接压缩单个文件时，取父目录  

                basePath = file.getParent();

            }

            zipFile( file, basePath, zos );

            zos.closeEntry();

            zos.close();
            // ResultSetMetaData md = rs.getMetaData();
            Assert.assertTrue( rs );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    @Test
    public void testOracleDriver()
        throws SQLException {
        try {

            // Class.forName("oracle.jdbc.OracleDriver").newInstance();
            // String url = "jdbc:oracle:thin:@192.168.1.106:1521:ZXL";
            // String user = "SYSTEM";
            // String password = "SYSTEM";
            // Connection conn = null;
            // conn = DriverManager.getConnection(url, user, password);
            // Statement stmt = null;
            // stmt = conn.createStatement();
            // String sql = "select * from SYSTEM.temp_oracle_9i";
            // ResultSet rs = stmt.executeQuery(sql);
            // ResultSetMetaData md = rs.getMetaData();
            // Assert.assertTrue(rs != null && md != null);
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /*
     * @Test public void testReadExcel2003() { String excel2007Name =
     * "C:\\Users\\fdc\\Desktop\\t_period.xls"; try {
     * 
     * Workbook book = WorkbookFactory.create(new
     * FileInputStream(excel2007Name)); Sheet sheetByName =
     * book.getSheet("t_period_copy"); Sheet sheetByIndex = book.getSheetAt(0);
     * 
     * Row row = sheetByName.getRow(0); Cell cell = row.getCell(0); int type =
     * cell.getCellType(); String value = cell.getStringCellValue(); } catch
     * (FileNotFoundException e) { 
     * e.printStackTrace(); } catch (IOException e) {  e.printStackTrace(); } catch (InvalidFormatException e) {  e.printStackTrace(); } }
     * 
     * @Test public void testReadExcel2007() { String excel2007Name =
     * "C:\\Users\\fdc\\Desktop\\t_period.xlsx"; try {
     * 
     * Workbook book = WorkbookFactory.create(new
     * FileInputStream(excel2007Name)); Sheet sheetByName =
     * book.getSheet("t_period_copy"); // Sheet sheetByIndex=book.getSheetAt(0);
     * Row row = sheetByName.getRow(0); for (int i = 0;; i++) { Cell cell =
     * row.getCell(i); cell.getCellType(); if (cell != null) { String
     * excelCellValue = cell.getStringCellValue(); if ((excelCellValue != null)
     * && (excelCellValue.trim().equals(""))) break;
     * System.out.print(excelCellValue); } else break; }
     * 
     * // Cell cell = row.getCell(0); // int type = cell.getCellType(); //
     * String value = cell.getStringCellValue(); } catch (FileNotFoundException
     * e) {  e.printStackTrace(); } catch
     * (IOException e) {  e.printStackTrace();
     * } catch (InvalidFormatException e) { 
     * e.printStackTrace(); } }
     */
    private static void zipFile( File source, String basePath,

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

    //@Test
    public void testExecSql() {
        try {
            /*			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
             String url = "jdbc:jtds:sqlserver://localhost:1433/ihos_zxl";*/

            /*			<jdbc.driverClassName>com.microsoft.sqlserver.jdbc.SQLServerDriver</jdbc.driverClassName>
             <jdbc.url>jdbc:sqlserver://localhost:1433;DatabaseName=iHOS_ZXL</jdbc.url>
             */
            Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver" ).newInstance();
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=iHOS_ZXL";
            String user = "sa";
            String password = "1234";
            Connection conn = null;
            conn = DriverManager.getConnection( url, user, password );
            Statement stmt = null;
            stmt = conn.createStatement();
            String sql = "select sum([金额])     from [temp_mzcharge] where [年月]='201206'";
            //String sql = "select * from [temp_mzcharge]";
            ResultSet rs = stmt.executeQuery( sql );
            while ( rs.next() )

                System.out.println( rs.getObject( 1 ) );
        }
        catch ( InstantiationException e ) {
            e.printStackTrace();
        }
        catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }
        catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        catch ( SQLException e ) {
            e.printStackTrace();
        }

    }
}
