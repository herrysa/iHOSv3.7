package com.huge.ihos.system.datacollection.maptables.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.datacollection.maptables.model.Deptmap;
import com.huge.ihos.system.datacollection.maptables.service.DeptmapManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class DeptmapAction
    extends JqGridBaseAction
     {
    private DeptmapManager deptmapManager;

    private List deptmaps;

    private Deptmap deptmap;

    private Long deptMapId;

    private String targetname;

    private String targetcode;

    private String sourcename;

    private String sourcecode;

    private String marktype;

    public void setDeptmapManager( DeptmapManager deptmapManager ) {
        this.deptmapManager = deptmapManager;
    }

    public List getDeptmaps() {
        return deptmaps;
    }



    /*   public String list() {
           deptmaps = deptmapManager.search(query, Deptmap.class);
           return SUCCESS;
       }*/

    public Deptmap getDeptmap() {
        return deptmap;
    }

    public void setDeptMapId( Long deptMapId ) {
        this.deptMapId = deptMapId;
    }

    public void setDeptmap( Deptmap deptmap ) {
        this.deptmap = deptmap;
    }

    public String delete() {
        deptmapManager.remove( deptmap.getDeptMapId() );
        saveMessage( getText( "deptmap.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( deptMapId != null ) {
            deptmap = deptmapManager.get( deptMapId );
        }
        else {
            deptmap = new Deptmap();
        }

        return SUCCESS;
    }

    public String save()
        throws Exception {
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }

        boolean isNew = ( deptmap.getDeptMapId() == null );

        deptmapManager.save( deptmap );

        String key = ( isNew ) ? "deptmap.added" : "deptmap.updated";
        saveMessage( getText( key ) );

        return "edit_success";
    }

    public String deptmapGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );

            pagedRequests = deptmapManager.getAppManagerCriteriaWithSearch( pagedRequests, Deptmap.class, filters );
            this.deptmaps = (List<Deptmap>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "deptmapGridList Error", e );
        }
        return SUCCESS;
    }

    public String deptmapGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer idst = new StringTokenizer( id, "," );
                while ( idst.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( idst.nextToken() );
                    deptmapManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "deptmap.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
            	if ((deptmapManager.existCode( "t_deptmap", "marktype","sourcecode", marktype,sourcecode )&&oper.equals( "add" ))||(OtherUtil.measureNotNull(deptMapId)&&deptmapManager.existCodeEdit(deptMapId, "t_deptmap", "deptMapId", "marktype", marktype,"sourcecode",sourcecode)) ) {
            		return ajaxForward( false, "您输入的编号已存在,请换一个!", false );
                }
                if ( deptmap == null ) {
                    if ( deptMapId == null )
                        deptMapId = 0L;
                    deptmap = new Deptmap();
                    deptmap.setDeptMapId( deptMapId == 0L ? null : deptMapId );
                    deptmap.setMarktype( marktype == null ? "" : marktype );
                    deptmap.setSourcecode( sourcecode == null ? "" : sourcecode );
                    deptmap.setSourcename( sourcename == null ? "" : sourcename );
                    deptmap.setTargetcode( targetcode == null ? "" : targetcode );
                    deptmap.setTargetname( targetname == null ? "" : targetname );
                }
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) && deptmap == null ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                deptmapManager.save( deptmap );
                String key = ( oper.equals( "add" ) ) ? "deptmap.added" : "deptmap.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return SUCCESS;
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( deptmap == null ) {
            return "Invalid deptmap Data";
        }

        return SUCCESS;

    }

    public Long getDeptMapId() {
        return deptMapId;
    }

    public void setTargetname( String targetname ) {
        this.targetname = targetname;
    }

    public void setTargetcode( String targetcode ) {
        this.targetcode = targetcode;
    }

    public void setSourcename( String sourcename ) {
        this.sourcename = sourcename;
    }

    public void setSourcecode( String sourcecode ) {
        this.sourcecode = sourcecode;
    }

    public void setMarktype( String marktype ) {
        this.marktype = marktype;
    }
}