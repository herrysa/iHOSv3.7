package com.huge.ihos.formula.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.ihos.formula.service.FormulaEntityManager;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class FormulaEntityAction
    extends JqGridBaseAction
     {
    private FormulaEntityManager formulaEntityManager;

    private List formulaEntities;

    private FormulaEntity formulaEntity;

    private String formulaEntityId;

    public void setFormulaEntityManager( FormulaEntityManager formulaEntityManager ) {
        this.formulaEntityManager = formulaEntityManager;
    }

    public List getFormulaEntities() {
        return formulaEntities;
    }

 

    public void setFormulaEntityId( String formulaEntityId ) {
        this.formulaEntityId = formulaEntityId;
    }

    public FormulaEntity getFormulaEntity() {
        return formulaEntity;
    }

    public void setFormulaEntity( FormulaEntity formulaEntity ) {
        this.formulaEntity = formulaEntity;
    }

    public String delete() {
        formulaEntityManager.remove( formulaEntity.getFormulaEntityId() );
        saveMessage( getText( "formulaEntity.deleted" ) );

        return "edit_success";
    }

    private int editType = 0; // 0:new,1:edit

    public void setEditType( int editType ) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }

    public String edit() {
        if ( formulaEntityId != null ) {
            formulaEntity = formulaEntityManager.get( formulaEntityId );
            this.editType = 1;
            this.setEntityIsNew( false );
        }
        else {
            formulaEntity = new FormulaEntity();
            this.editType = 0;
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

        boolean isNew = ( formulaEntity.getFormulaEntityId() == null );

        formulaEntityManager.save( formulaEntity );

        String key = ( this.isEntityIsNew() ) ? "formulaEntity.added" : "formulaEntity.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String formulaEntityGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = formulaEntityManager.getFormulaEntityCriteria( pagedRequests );
            this.formulaEntities = (List<FormulaEntity>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "formulaEntityGridList Error", e );
        }
        return SUCCESS;
    }

    public String formulaEntityGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    // Long removeId = Long.parseLong(ids.nextToken());
                    String removeId = ids.nextToken();
                    formulaEntityManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "formulaEntity.deleted" );
                return ajaxForward( true, gridOperationMessage, true );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                formulaEntityManager.save( formulaEntity );
                String key = ( oper.equals( "add" ) ) ? "formulaEntity.added" : "formulaEntity.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();

            return ajaxForward( false, gridOperationMessage, true );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client
     *       side.
     * @return
     */
    private String isValid() {
        if ( formulaEntity == null ) {
            return "Invalid formulaEntity Data";
        }

        return SUCCESS;

    }
}
