package com.huge.ihos.system.oa.bulletin.webapp.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.huge.ihos.system.oa.bulletin.model.Bulletin;
import com.huge.ihos.system.oa.bulletin.service.BulletinManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.OptFile;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class BulletinPagedAction
    extends JqGridBaseAction
 {

    /**
	 * 
	 */
	private static final long serialVersionUID = 714052836856159575L;

	private final String DIC_SECRETLEVE_KEY = "secretLeve";

    private BulletinManager bulletinManager;

    private List<Bulletin> bulletins;

    private Bulletin bulletin;

    private Long bulletinId;

    private File appendixfile;

    private String fileName;

    private List departList;

    private DepartmentManager departmentManager;

    private List secretLeveList;

    public BulletinManager getBulletinManager() {
        return bulletinManager;
    }

    public void setBulletinManager( BulletinManager bulletinManager ) {
        this.bulletinManager = bulletinManager;
    }

    public List<Bulletin> getbulletins() {
        return bulletins;
    }

    public void setBulletins( List<Bulletin> bulletins ) {
        this.bulletins = bulletins;
    }

    public Bulletin getBulletin() {
        return bulletin;
    }

    public void setBulletin( Bulletin bulletin ) {
        this.bulletin = bulletin;
    }

    public Long getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId( Long bulletinId ) {
        this.bulletinId = bulletinId;
    }

    public List getSecretLeveList() {
        return secretLeveList;
    }

    public void setSecretLeveList( List secretLeveList ) {
        this.secretLeveList = secretLeveList;
    }

    public File getAppendixfile() {
        return appendixfile;
    }

    public void setAppendixfile( File appendixfile ) {
        this.appendixfile = appendixfile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    @Override
    public void prepare() throws Exception {
        super.prepare();
        this.secretLeveList = this.getDictionaryItemManager().getAllItemsByCode( DIC_SECRETLEVE_KEY );
        this.currentUser = this.getSessionUser();
        departList = departmentManager.getAllDept();
        this.clearSessionMessages();
    }

    public String bulletinGridList() {
        log.debug( "enter list method!" );
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = bulletinManager.getBulletinCriteria( pagedRequests, filters, group_on );
            this.bulletins = (List<Bulletin>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "List Error", e );
        }
        return SUCCESS;
    }

    public String save() {
        String error = isValid();
        if ( !error.equalsIgnoreCase( SUCCESS ) ) {
            gridOperationMessage = error;
            return ajaxForwardError( gridOperationMessage );
        }
        try {
            Bulletin oldButtBulletin = null;
            if ( this.isEntityIsNew() ) {
                bulletin.setCreateTime( new Timestamp( Calendar.getInstance().getTimeInMillis() ) );
                bulletin.setOpenNum( 0 );
            }
            else {
                oldButtBulletin = bulletinManager.get( bulletin.getBulletinId() );
                bulletin.setCreateTime( oldButtBulletin.getCreateTime() );
                //bulletin.setFileName(oldButtBulletin.getUrl());
            }
            if ( fileName != null && !fileName.equals( "" ) ) {
                fileName = fileName.substring( fileName.lastIndexOf( "\\" ) + 1 );
                String serverPath = "//home//bulletin//";
                serverPath = this.getRequest().getRealPath( serverPath );
                OptFile.mkParent( serverPath + "\\" + fileName );
                File targetFile = new File( serverPath + "\\" + fileName );
                OptFile.copyFile( appendixfile, targetFile );
                bulletin.setUrl( serverPath );
                bulletin.setFileName( fileName );
            }
            else if ( !this.isEntityIsNew() ) {
                bulletin.setFileName( oldButtBulletin.getFileName() );
                bulletin.setUrl( oldButtBulletin.getUrl() );
            }

            User user = this.userManager.getCurrentLoginUser();
            bulletin.setCreator( user.getPerson().getName() );
            if ( user.getPerson() != null ) {
                bulletin.setDepartment( user.getPerson().getDepartment().getName() );
            }

            bulletinManager.save( bulletin );
        }
        catch ( Exception dre ) {
            gridOperationMessage = dre.getMessage();
            return ajaxForwardError( gridOperationMessage );
        }
        String key = ( ( this.isEntityIsNew() ) ) ? "bulletin.added" : "bulletin.updated";
        return ajaxForward( this.getText( key ) );
    }

    public String edit() {
        if ( bulletinId != null ) {
            bulletin = bulletinManager.get( bulletinId );
            this.setEntityIsNew( false );
        }
        else {
            bulletin = new Bulletin();
            this.setEntityIsNew( true );
        }
        return SUCCESS;
    }

    public String bulletinGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    log.debug( "Delete Customer " + removeId );
                    Bulletin bulletin = bulletinManager.get( new Long( removeId ) );
                    bulletinManager.remove( new Long( removeId ) );

                }
                gridOperationMessage = this.getText( "bulletin.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkBulletinGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, e.getMessage(), false );
        }
    }

    private String isValid() {
        if ( bulletin == null ) {
            return "Invalid bulletin Data";
        }

        return SUCCESS;

    }

    public String showBulletin() {
        bulletin = bulletinManager.get( bulletinId );
        bulletin.setOpenNum( bulletin.getOpenNum() + 1 );
        bulletinManager.save( bulletin );
        return SUCCESS;
    }

    public String checkAppendix() {
        try {
        	bulletin = bulletinManager.get( bulletinId );
            File file = new File( bulletin.getUrl() + "/" + bulletin.getFileName() );
            if(file.exists()){
            	return ajaxForward(true, "", false);
            }else{
            	return ajaxForward(false, "文件不存在!", false);
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
            return ajaxForward(false, "", false);
        }

    }
    public String dowloadAppendix() {
        bulletin = bulletinManager.get( bulletinId );
        try {
            File file = new File( bulletin.getUrl() + "/" + bulletin.getFileName() );
            InputStream fis = new BufferedInputStream( new FileInputStream( file ) );
            byte[] buffer = new byte[fis.available()];
            fis.read( buffer );
            fis.close();
            HttpServletResponse resp = ServletActionContext.getResponse();
            resp.reset();
            resp.setContentType( "application/octet-stream" );
            resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( bulletin.getFileName().getBytes( "GBK" ), "ISO8859-1" ) );
            OutputStream outs = resp.getOutputStream();
            outs.write( buffer );
            outs.flush();
            outs.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        return null;
    }

    public List getDepartList() {
        return departList;
    }

    public void setDepartList( List departList ) {
        this.departList = departList;
    }

    public DepartmentManager getDepartmentManager() {
        return departmentManager;
    }

    public void setDepartmentManager( DepartmentManager departmentManager ) {
        this.departmentManager = departmentManager;
    }
}
