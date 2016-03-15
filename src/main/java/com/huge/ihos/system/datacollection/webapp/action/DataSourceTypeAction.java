package com.huge.ihos.system.datacollection.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.datacollection.model.DataSourceType;
import com.huge.ihos.system.datacollection.service.DataSourceTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class DataSourceTypeAction
    extends JqGridBaseAction
{
    private DataSourceTypeManager dataSourceTypeManager;

    private List dataSourceTypes;

    private List dataSourceTypeSelectList;

    public List getDataSourceTypeSelectList() {
        return dataSourceTypeSelectList;
    }

    public void setDataSourceTypeSelectList( List dataSourceTypeSelectList ) {
        this.dataSourceTypeSelectList = dataSourceTypeSelectList;
    }

    private DataSourceType dataSourceType;

    private Long dataSourceTypeId;

    public void setDataSourceTypeManager( DataSourceTypeManager dataSourceTypeManager ) {
        this.dataSourceTypeManager = dataSourceTypeManager;
    }

    public List getDataSourceTypes() {
        return dataSourceTypes;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        if ( getRequest().getMethod().equalsIgnoreCase( "post" ) ) {
            // prevent failures on new
            String dataSourceTypeId = getRequest().getParameter( "dataSourceType.dataSourceTypeId" );
            if ( dataSourceTypeId != null && !dataSourceTypeId.equals( "" ) ) {
                dataSourceType = dataSourceTypeManager.get( new Long( dataSourceTypeId ) );
            }
        }
        this.clearSessionMessages();
    }

    /*   public String list() {
           dataSourceTypes = dataSourceTypeManager.search(query, DataSourceType.class);
           return SUCCESS;
       }*/

    public void setDataSourceTypeId( Long dataSourceTypeId ) {
        this.dataSourceTypeId = dataSourceTypeId;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType( DataSourceType dataSourceType ) {
        this.dataSourceType = dataSourceType;
    }

    public String delete() {
        dataSourceTypeManager.remove( dataSourceType.getDataSourceTypeId() );
        saveMessage( getText( "dataSourceType.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( dataSourceTypeId != null ) {
            dataSourceType = dataSourceTypeManager.get( dataSourceTypeId );
        }
        else {
            dataSourceType = new DataSourceType();
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

        boolean isNew = ( dataSourceType.getDataSourceTypeId() == null );

        dataSourceTypeManager.save( dataSourceType );

        String key = ( isNew ) ? "dataSourceType.added" : "dataSourceType.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String dataSourceTypeGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = dataSourceTypeManager.getDataSourceTypeCriteria( pagedRequests );
            this.dataSourceTypes = (List<DataSourceType>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "dataSourceTypeGridList Error", e );
        }
        return SUCCESS;
    }

    public String dataSourceTypeSelectList() {

        this.dataSourceTypeSelectList = this.dataSourceTypeManager.getAll();
        return SUCCESS;
    }

    public String dataSourceTypeGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    dataSourceTypeManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "dataSourceType.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                dataSourceTypeManager.save( dataSourceType );
                String key = ( oper.equals( "add" ) ) ? "dataSourceType.added" : "dataSourceType.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( dataSourceType == null ) {
            return "Invalid dataSourceType Data";
        }

        return SUCCESS;

    }
}