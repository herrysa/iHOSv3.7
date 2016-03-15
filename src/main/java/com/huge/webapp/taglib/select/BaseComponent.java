package com.huge.webapp.taglib.select;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.huge.exceptions.BusinessException;

public abstract class BaseComponent
    extends BaseView {
    private HashMap attributeMap = new HashMap();

    private boolean isRequired = false;

    public void setIsRequired( boolean required ) {
        this.isRequired = required;
    }

    protected StringBuffer appendIsRequired() {
        if ( !this.isRequired )
            return new StringBuffer();
        return new StringBuffer( "<font color=\"#FF0000\">*</font>" );
    }

    public void setAttribute( String key, String value ) {
        if ( ( key == null ) || ( value == null ) || ( key.trim().equals( "" ) ) || ( value.trim().equals( "" ) ) )
            throw new BusinessException( "参数错" );
        this.attributeMap.put( key, value );
    }

    protected StringBuffer appendAttr() {
        if ( this.attributeMap.isEmpty() )
            return new StringBuffer();
        StringBuffer sb = new StringBuffer();
        String attrKey = null;
        String attrValue = null;
        Set set = this.attributeMap.keySet();
        Iterator itr = set.iterator();
        while ( itr.hasNext() ) {
            attrKey = (String) itr.next();
            attrValue = (String) this.attributeMap.get( attrKey );
            sb.append( " " ).append( attrKey ).append( "=\"" ).append( attrValue ).append( "\"" );
        }
        return sb;
    }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\ecis-web-basic.jar
 * Qualified Name:     com.huge.waf.view.BaseComponent
 * JD-Core Version:    0.6.0
 */