package com.huge.ihos.system.configuration.dictionary.webapp.action;

import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryItemManager;
import com.huge.webapp.action.BaseAction;

public class DictionaryItemEditinlineAction
    extends BaseAction
     {

    public Long getDictionaryItemId() {
        return dictionaryItemId;
    }

    public void setDictionaryItemId( Long dictionaryItemId ) {
        this.dictionaryItemId = dictionaryItemId;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo( Long orderNo ) {
        this.orderNo = orderNo;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    private Long dictionaryItemId;

    private String value;

    private String content;

    private Long orderNo;


    private DictionaryItemManager dictionaryItemManager;

    public DictionaryItemManager getDictionaryItemManager() {
        return dictionaryItemManager;
    }

    public void setDictionaryItemManager( DictionaryItemManager dictionaryItemManager ) {
        this.dictionaryItemManager = dictionaryItemManager;
    }

    public String cellEdit()
        throws Exception {

        DictionaryItem dictionaryItem = dictionaryItemManager.get( id );
        if ( dictionaryItem != null ) {
            if ( content != null )
                dictionaryItem.setContent( content );
            if ( orderNo != null )
                dictionaryItem.setOrderNo( orderNo );
            if ( value != null )
                dictionaryItem.setValue( value );
        }
        dictionaryItemManager.saveItem( dictionaryItem );

        return SUCCESS;
    }

}
