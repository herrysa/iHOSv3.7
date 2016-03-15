package com.huge.ihos.system.systemManager.dbback.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.huge.ihos.system.systemManager.dbback.model.DbBackup;
import com.huge.ihos.system.systemManager.dbback.service.DbBackupManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class DbBackupAction
    extends JqGridBaseAction
 {
    DbBackupManager dbBackupManager;

    private List dbBackups;

    private DbBackup dbBackup;

    private Long dbBackupId;

    public List getDbBackups() {
        return dbBackups;
    }

    public void setDbBackups( List dbBackups ) {
        this.dbBackups = dbBackups;
    }

    public DbBackup getDbBackup() {
        return dbBackup;
    }

    public void setDbBackup( DbBackup dbBackup ) {
        this.dbBackup = dbBackup;
    }

    public Long getDbBackupId() {
        return dbBackupId;
    }

    public void setDbBackupId( Long dbBackupId ) {
        this.dbBackupId = dbBackupId;
    }

    public DbBackupManager getDbBackupManager() {
        return dbBackupManager;
    }

    public void setDbBackupManager( DbBackupManager dbBackupManager ) {
        this.dbBackupManager = dbBackupManager;
    }

    public String edit() {
        if ( dbBackupId != null ) {
            this.dbBackup = this.dbBackupManager.getDbBackupById( dbBackupId );
        }
        else {
            this.dbBackup = initBackUpInfo();
        }

        return SUCCESS;
    }

    private DbBackup initBackUpInfo() {
        DbBackup dbv = new DbBackup();

        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        User user = userManager.getUserByUsername( username );
        /*
         * HttpServletRequest request = ServletActionContext.getRequest();
         * 
         * 
         * UserProp userProp = (UserProp) SessionMgr.getUserProp(request);
         */

        Person cuPer = user.getPerson();
        if ( cuPer != null ) {
            dbv.setOperatorId( cuPer.getPersonId() );
            dbv.setOperatorName( cuPer.getName() );
        }
        else {
            dbv.setOperatorId( "" );
            dbv.setOperatorName( "" );
        }
        String dbname = dbBackupManager.getCurrentDbName();

        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd-HH-mm-ss" );
        String sData = localSimpleDateFormat.format( new Date() );
        // String sData = DataFormat.getStandardDataFormat(new Date(),
        // "yyyy-MM-dd-HH-mm-ss");

        // System.out.print(dbname+sData);

        dbv.setDbBackupFileName( dbname + "_" + sData + ".zip" );
        dbv.setBackupDataTime( sData );
        return dbv;
    }

    public String save() {
        boolean isNew = ( dbBackup.getBkid() == null );
        String baseDir = getBackupDirPath();
        String dbName = this.dbBackupManager.getCurrentDbName();
        this.dbBackupManager.insertDataBackup( dbBackup, baseDir, dbName );
        String key = ( isNew ) ? "dbBackup.added" : "dbBackup.updated";
        saveMessage( getText( key ) );
        return ajaxForward( getText( key ) );
    }

    public String dbBackupGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );

            pagedRequests = dbBackupManager.getAppManagerCriteriaWithSearch( pagedRequests, DbBackup.class, filters );
            this.dbBackups = (List<DbBackup>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "deptmapGridList Error", e );
        }
        return SUCCESS;
    }

    public String dbBackupGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                List idl = new ArrayList();
                while ( ids.hasMoreTokens() ) {
                    idl.add( ids.nextToken() );
                }
                String[] idsa = new String[idl.size()];
                idl.toArray( idsa );
                dbBackupManager.delDbBackups( idsa, getBackupDirPath() );
                /*
                 * while (ids.hasMoreTokens()) { Long removeId =
                 * Long.parseLong(ids.nextToken());
                 * dbBackupManager.delDbBackups(id,
                 * getBackupDirPath());//remove(removeId);
                 */
                gridOperationMessage = this.getText( "dbBackup.added" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "dbbackup edit Error", e );
            gridOperationMessage = e.getMessage();
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    public String downLoadBackUp() {
        try {
            DbBackup dbv = dbBackupManager.get( dbBackupId );

            HttpServletResponse resp = ServletActionContext.getResponse();
            // resp.setContentType("APPLICATION/OCTET-STREAM");
            resp.setHeader( "Content-Disposition", "attachment; filename=" + dbv.getDbBackupFileName() );

            java.io.OutputStream outp = null;
            java.io.FileInputStream in = null;
            outp = resp.getOutputStream();
            in = new FileInputStream( this.getBackupDirPath() + "\\" + dbv.getDbBackupFileName() );
            byte[] b = new byte[1024];
            int i = 0;
            while ( ( i = in.read( b ) ) > 0 ) {
                outp.write( b, 0, i );
            }
            outp.flush();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    private String getBackupDirPath() {
        String str = "//home//DataBase_Backup//";
        str = this.getRequest().getRealPath( str );
        if ( !( new File( str ) ).isDirectory() ) {
            new File( str ).mkdir();
        }
        return str;
    }

}
