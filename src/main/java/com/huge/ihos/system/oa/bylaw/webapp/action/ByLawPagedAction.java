package com.huge.ihos.system.oa.bylaw.webapp.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.huge.ihos.system.oa.bylaw.model.ByLaw;
import com.huge.ihos.system.oa.bylaw.service.ByLawManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.OptFile;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class ByLawPagedAction
    extends JqGridBaseAction
 {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3365213445936164726L;

	private ByLawManager byLawManager;

    private List<ByLaw> byLaws;

    private ByLaw byLaw;

    private Long byLawId;

    private List departList;

    private DepartmentManager departmentManager;

    private File bylaw_appendixfile;

    private String fileName;

    public ByLawManager getByLawManager() {
        return byLawManager;
    }

    public void setByLawManager( ByLawManager byLawManager ) {
        this.byLawManager = byLawManager;
    }

    public List<ByLaw> getbyLaws() {
        return byLaws;
    }

    public void setByLaws( List<ByLaw> byLaws ) {
        this.byLaws = byLaws;
    }

    public ByLaw getByLaw() {
        return byLaw;
    }

    public void setByLaw( ByLaw byLaw ) {
        this.byLaw = byLaw;
    }

    public Long getByLawId() {
        return byLawId;
    }

    public void setByLawId( Long byLawId ) {
        this.byLawId = byLawId;
    }

    @Override
    public void prepare() throws Exception {
        super.prepare();
        departList = departmentManager.getAllDept();
        this.clearSessionMessages();
    }

    public String byLawGridList() {
        log.debug( "enter list method!" );
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = byLawManager.getByLawCriteria( pagedRequests, filters, group_on );
            this.byLaws = (List<ByLaw>) pagedRequests.getList();
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
            ByLaw oldByLaw = null;
            if ( this.isEntityIsNew() ) {
                byLaw.setCreateTime( Calendar.getInstance().getTime() );
            }
            else {
                oldByLaw = byLawManager.get( byLaw.getByLawId() );
                byLaw.setCreateTime( oldByLaw.getCreateTime() );
            }
            if ( fileName != null && !fileName.equals( "" ) ) {
                fileName = fileName.substring( fileName.lastIndexOf( "\\" ) + 1 );
                String serverPath = "//home//bylaw//";
                serverPath = this.getRequest().getRealPath( serverPath );
                OptFile.mkParent( serverPath + "\\" + fileName );
                File targetFile = new File( serverPath + "\\" + fileName );
                OptFile.copyFile( bylaw_appendixfile, targetFile );
                byLaw.setUrl( serverPath );
                byLaw.setFileName( fileName );
            }
            else if ( !this.isEntityIsNew() ) {
                byLaw.setFileName( oldByLaw.getFileName() );
                byLaw.setUrl( oldByLaw.getUrl() );
            }

            User user = this.userManager.getCurrentLoginUser();
            byLaw.setCreator( user.getPerson().getName() );
            if ( user.getPerson() != null ) {
                byLaw.setDepartment( user.getPerson().getDepartment().getName() );
            }

            byLawManager.save( byLaw );
        }
        catch ( Exception dre ) {
            gridOperationMessage = dre.getMessage();
            return ajaxForwardError( gridOperationMessage );
        }
        String key = ( ( this.isEntityIsNew() ) ) ? "byLaw.added" : "byLaw.updated";
        return ajaxForward( this.getText( key ) );
    }

    public String edit() {
        if ( byLawId != null ) {
            byLaw = byLawManager.get( byLawId );
            this.setEntityIsNew( false );
        }
        else {
            byLaw = new ByLaw();
            this.setEntityIsNew( true );
        }
        return SUCCESS;
    }

    public String byLawGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    log.debug( "Delete Customer " + removeId );
                    ByLaw byLaw = byLawManager.get( new Long( removeId ) );
                    byLawManager.remove( new Long( removeId ) );

                }
                gridOperationMessage = this.getText( "byLaw.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkByLawGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, e.getMessage(), false );
        }
    }

    public String showByLaw() {
        byLaw = byLawManager.get( byLawId );
        return SUCCESS;
    }

    public String dowloadAppendix() {
        byLaw = byLawManager.get( byLawId );
        try {
            File file = new File( byLaw.getUrl() + "/" + byLaw.getFileName() );
            InputStream fis = new BufferedInputStream( new FileInputStream( file ) );
            byte[] buffer = new byte[fis.available()];
            fis.read( buffer );
            fis.close();
            HttpServletResponse resp = ServletActionContext.getResponse();
            resp.reset();
            resp.setContentType( "application/octet-stream" );
            resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( byLaw.getFileName().getBytes( "GBK" ), "ISO8859-1" ) );
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

    private String isValid() {
        if ( byLaw == null ) {
            return "Invalid byLaw Data";
        }

        return SUCCESS;

    }

    public File getBylaw_appendixfile() {
        return bylaw_appendixfile;
    }

    public void setBylaw_appendixfile( File bylaw_appendixfile ) {
        this.bylaw_appendixfile = bylaw_appendixfile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    public List<ByLaw> getByLaws() {
        return byLaws;
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
