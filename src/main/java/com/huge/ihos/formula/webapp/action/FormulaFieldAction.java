package com.huge.ihos.formula.webapp.action;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.ihos.formula.model.FormulaField;
import com.huge.ihos.formula.service.FormulaEntityManager;
import com.huge.ihos.formula.service.FormulaFieldManager;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class FormulaFieldAction
    extends BaseAction
     {
    private FormulaFieldManager formulaFieldManager;

    private FormulaEntityManager formulaEntityManager;

    private List formulaEntities;

    private List formulaFields;

    private FormulaField formulaField;

    private String formulaFieldId;

    private PagerFactory pagerFactory;

    // entity paging
    protected Integer page = 0;

    protected Integer total = 0;

    protected Integer records = 0;

    private String id;

    protected String oper;

    private String gridOperationMessage;

    // search
    private List distinctFiledList;

    /**
     * Grab the entity from the database before populating with request
     * parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /*
         * if (getRequest().getMethod().equalsIgnoreCase("post")) { // prevent
         * failures on new String formulaFieldId =
         * getRequest().getParameter("formulaField.formulaFieldId"); if
         * (formulaFieldId != null && !formulaFieldId.equals("")) { formulaField
         * = formulaFieldManager.get(new String(formulaFieldId)); } }
         */
        this.clearSessionMessages();
        distinctFiledList = formulaFieldManager.getAllDistinctKeyclass();
    }

    /*
     * public String list() { formulaFields = formulaFieldManager.search(query,
     * FormulaField.class); return SUCCESS; }
     */

    public void setFormulaFieldId( String formulaFieldId ) {
        this.formulaFieldId = formulaFieldId;
    }

    public FormulaField getFormulaField() {
        return formulaField;
    }

    public void setFormulaField( FormulaField formulaField ) {
        this.formulaField = formulaField;
    }

    public String delete() {
        formulaFieldManager.remove( formulaField.getFormulaFieldId() );
        saveMessage( getText( "formulaField.deleted" ) );

        return "edit_success";
    }
    
    private List<String> indicatorClassList;

    public List<String> getIndicatorClassList() {
		return indicatorClassList;
	}

	public String edit() {
		indicatorClassList = this.getDictionaryItemManager().getAllItemsByCode("indicatorClass");
        if ( formulaFieldId != null ) {
            formulaField = formulaFieldManager.get( formulaFieldId );
            this.setEntityIsNew( false );
        }
        else {
            formulaField = new FormulaField();
            this.setEntityIsNew( true );
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
        boolean isNew = ( this.isEntityIsNew() == true );

        formulaFieldManager.save( formulaField );

        String key = ( isNew ) ? "formulaField.added" : "formulaField.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String saveAndPreview() {
        boolean isNew = ( formulaField.getFormulaFieldId() == null );

        formulaFieldManager.save( formulaField );

        String key = ( isNew ) ? "formulaField.added" : "formulaField.updated";
        saveMessage( getText( key ) );
        this.formulaFieldId = formulaField.getFormulaFieldId();
        return "edit_success_preview";
    }

    public String formulaFieldGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = formulaFieldManager.getAppManagerCriteriaWithSearch( pagedRequests, FormulaField.class, filters );
            this.formulaFields = (List<FormulaField>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "formulaFieldGridList Error", e );
        }
        return SUCCESS;
    }

    public String formulaFieldGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    // Long removeId = Long.parseLong(ids.nextToken());
                    String removeId = ids.nextToken();
                    formulaFieldManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "formulaField.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                formulaFieldManager.save( formulaField );
                String key = ( oper.equals( "add" ) ) ? "formulaField.added" : "formulaField.updated";
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

    public String disabledFormulaField() {
        try {
            StringTokenizer ids = new StringTokenizer( id, "," );
            while ( ids.hasMoreTokens() ) {
                String removeId = ids.nextToken();
                formulaFieldManager.updateDisabled( removeId, true );
            }
        }
        catch ( Exception e ) {
            this.jsonStatus = "error";
            this.jsonMessages = e.getMessage();
            return SUCCESS;
        }
        this.jsonStatus = "success";
        this.jsonMessages = "成功。";
        return SUCCESS;
    }

    public String enabledFormulaField() {
        try {
            StringTokenizer ids = new StringTokenizer( id, "," );
            while ( ids.hasMoreTokens() ) {
                String removeId = ids.nextToken();
                formulaFieldManager.updateDisabled( removeId, false );
            }
        }
        catch ( Exception e ) {
            this.jsonStatus = "error";
            this.jsonMessages = e.getMessage();
            return SUCCESS;
        }
        this.jsonStatus = "success";
        this.jsonMessages = "成功。";
        return SUCCESS;
    }

    public String formulaFieldGridPrepare() {
        this.formulaEntities = this.formulaEntityManager.getAll();
        return SUCCESS;
    }

    private String jsonStatus = "success";

    private String jsonMessages;

    public String getJsonStatus() {
        return jsonStatus;
    }

    public void setJsonStatus( String jsonStatus ) {
        this.jsonStatus = jsonStatus;
    }

    public String getJsonMessages() {
        return jsonMessages;
    }

    public void setJsonMessages( String jsonMessages ) {
        this.jsonMessages = jsonMessages;
    }

    public String initFormulaField() {
        List entities = this.formulaEntityManager.getAll();
        Iterator itr = entities.iterator();

        while ( itr.hasNext() ) {
            FormulaEntity ent = (FormulaEntity) itr.next();
            this.formulaFieldManager.initFormulaFieldByTargetTable( ent.getTableName() );

        }
        this.jsonStatus = "success";
        this.jsonMessages = "初始化成功。";
        return SUCCESS;
    }

    /**
     * @TODO you should add some validation rules those are difficult on client
     *       side.
     * @return
     */
    private String isValid() {
        if ( formulaField == null ) {
            return "Invalid formulaField Data";
        }

        return SUCCESS;

    }

    public FormulaFieldManager getFormulaFieldManager() {
        return formulaFieldManager;
    }

    public String getFormulaFieldId() {
        return formulaFieldId;
    }

    public void setFormulaFields( List formulaFields ) {
        this.formulaFields = formulaFields;
    }

    public List getFormulaEntities() {
        return formulaEntities;
    }

    public void setFormulaEntities( List formulaEntities ) {
        this.formulaEntities = formulaEntities;
    }

    public FormulaEntityManager getFormulaEntityManager() {
        return formulaEntityManager;
    }

    public void setFormulaEntityManager( FormulaEntityManager formulaEntityManager ) {
        this.formulaEntityManager = formulaEntityManager;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal( Integer total ) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords( Integer records ) {
        this.records = records;
    }

    public String getOper() {
        return oper;
    }

    public void setOper( String oper ) {
        this.oper = oper;
    }

    public String getGridOperationMessage() {
        return gridOperationMessage;
    }

    public void setGridOperationMessage( String gridOperationMessage ) {
        this.gridOperationMessage = gridOperationMessage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage( Integer page ) {
        this.page = page;
    }

    public PagerFactory getPagerFactory() {
        return pagerFactory;
    }

    public void setPagerFactory( PagerFactory pagerFactory ) {
        this.pagerFactory = pagerFactory;
    }

    public void setFormulaFieldManager( FormulaFieldManager formulaFieldManager ) {
        this.formulaFieldManager = formulaFieldManager;
    }

    public List getFormulaFields() {
        return formulaFields;
    }

    public List getDistinctFiledList() {
        return distinctFiledList;
    }
}
