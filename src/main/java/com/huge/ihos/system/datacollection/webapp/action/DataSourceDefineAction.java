package com.huge.ihos.system.datacollection.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.datacollection.model.DataSourceDefine;
import com.huge.ihos.system.datacollection.service.DataCollectionTaskDefineManager;
import com.huge.ihos.system.datacollection.service.DataSourceDefineManager;
import com.huge.ihos.system.datacollection.service.DataSourceTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class DataSourceDefineAction
    extends JqGridBaseAction
   {
    private DataSourceDefineManager dataSourceDefineManager;

    private DataSourceTypeManager dataSourceTypeManager;

    private List dataSourceDefines;

    private List dataSourceDefineSelectList;

    private DataSourceDefine dataSourceDefine;

    private Long dataSourceDefineId;
    
    private DataCollectionTaskDefineManager dataCollectionTaskDefineManager;

    public DataCollectionTaskDefineManager getDataCollectionTaskDefineManager() {
		return dataCollectionTaskDefineManager;
	}

	public void setDataCollectionTaskDefineManager(
			DataCollectionTaskDefineManager dataCollectionTaskDefineManager) {
		this.dataCollectionTaskDefineManager = dataCollectionTaskDefineManager;
	}

	public DataSourceTypeManager getDataSourceTypeManager() {
        return dataSourceTypeManager;
    }

    public void setDataSourceTypeManager( DataSourceTypeManager dataSourceTypeManager ) {
        this.dataSourceTypeManager = dataSourceTypeManager;
    }

    public void setDataSourceDefineManager( DataSourceDefineManager dataSourceDefineManager ) {
        this.dataSourceDefineManager = dataSourceDefineManager;
    }

    public List getDataSourceDefines() {
        return dataSourceDefines;
    }

    public List getDataSourceDefineSelectList() {
        return dataSourceDefineSelectList;
    }

    public void setDataSourceDefineSelectList( List dataSourceDefineSelectList ) {
        this.dataSourceDefineSelectList = dataSourceDefineSelectList;
    }



    public void setDataSourceDefineId( Long dataSourceDefineId ) {
        this.dataSourceDefineId = dataSourceDefineId;
    }

    public DataSourceDefine getDataSourceDefine() {
        return dataSourceDefine;
    }

    public void setDataSourceDefine( DataSourceDefine dataSourceDefine ) {
        this.dataSourceDefine = dataSourceDefine;
    }

    public String allDataSourceDefine() {
        this.dataSourceDefineSelectList = this.dataSourceDefineManager.getAll();
        return SUCCESS;
    }

    public String delete() {
        dataSourceDefineManager.remove( dataSourceDefine.getDataSourceDefineId() );
        saveMessage( getText( "dataSourceDefine.deleted" ) );

        return ajaxForward( getText( "dataSourceDefine.deleted" ) );
    }

    public String connectionTest() {
        boolean ct = this.dataSourceDefineManager.connectionTest( dataSourceDefine );
        String key = ( ct ) ? "connectionTest.success" : "connectionTest.failure";
        gridOperationMessage = this.getText( key );
        if(ct){
        	return ajaxForward( true, gridOperationMessage, false );
        }else{
        	return ajaxForward( false, gridOperationMessage, false );
        }
    }

    public String urlGlance() {
        String dstId = this.getRequest().getParameter( "dstId" );

        if ( dstId != null && !dstId.equalsIgnoreCase( "" ) ) {

            Long dstIdl = new Long( dstId );

            String urlTemplate = this.dataSourceTypeManager.get( dstIdl ).getUrlTemplate();

            gridOperationMessage = urlTemplate;
        }

        //Long dsId = dataSourceDefine.getDataSourceType().getDataSourceTypeId();

        return INPUT;
    }

    public String edit() {
        if ( dataSourceDefineId != null ) {
            dataSourceDefine = dataSourceDefineManager.get( dataSourceDefineId );
        }
        else {
            dataSourceDefine = new DataSourceDefine();
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

        boolean isNew = ( dataSourceDefine.getDataSourceDefineId() == null );

        dataSourceDefineManager.save( dataSourceDefine );

        String key = ( isNew ) ? "dataSourceDefine.added" : "dataSourceDefine.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String dataSourceDefineGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = dataSourceDefineManager.getDataSourceDefineCriteria( pagedRequests );
            this.dataSourceDefines = (List<DataSourceDefine>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "dataSourceDefineGridList Error", e );
        }
        return SUCCESS;
    }

    public String dataSourceDefineGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    boolean isUsed = dataCollectionTaskDefineManager.isUesedDataSource(removeId);
                    if(isUsed){
                    	return ajaxForward( false, "该数据源被引用，无法删除！", false );
                    }
                    dataSourceDefineManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "dataSourceDefine.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                dataSourceDefineManager.save( dataSourceDefine );
                String key = ( oper.equals( "add" ) ) ? "dataSourceDefine.added" : "dataSourceDefine.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            gridOperationMessage = e.getMessage();
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( dataSourceDefine == null ) {
            return "Invalid dataSourceDefine Data";
        }

        return SUCCESS;

    }
}