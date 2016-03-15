package com.huge.webapp.taglib;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.displaytag.tags.el.ExpressionEvaluator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryItemManager;

public class DictionaryTag
    extends TagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = -3709227763190307660L;

    private String code;

    private String name;

    private boolean required;

    private String scope;

    private String value;

    private String cssClass;

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass( String cssClass ) {
        this.cssClass = cssClass;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    /**
     * 页面显示名字与ID
     * @param name
     */
    public void setName( String name ) {
        this.name = name;
    }

    public void setRequired( boolean prompt ) {
        this.required = prompt;
    }

    public void setScope( String scope ) {
        this.scope = scope;
    }

    public int doStartTag()
        throws JspException {
        ExpressionEvaluator eval = new ExpressionEvaluator( this, pageContext );

        //		if (value != null) {
        //			value = eval.evalString("default", value);
        //		}

        List items = getDictionaryItemsManager().getAllItemsByCode( code );
        if ( scope != null ) {
            if ( scope.equals( "page" ) ) {
                pageContext.setAttribute( code, items );
            }
            else if ( scope.equals( "request" ) ) {
                pageContext.getRequest().setAttribute( code, items );
            }
            else if ( scope.equals( "session" ) ) {
                pageContext.getSession().setAttribute( code, items );
            }
            else if ( scope.equals( "application" ) ) {
                pageContext.getServletContext().setAttribute( code, items );
            }
            else {
                throw new JspException( "Attribute 'scope' must be: page, request, session or application" );
            }
        }
        else {
            StringBuffer sb = new StringBuffer();
            sb.append( "<select name=\"" + name + "\" id=\"" + name + "\" class=\"" + this.cssClass + "\">\n" );

            if ( !required ) {
                sb.append( "    <option value=\"\" selected=\"selected\">" );
                //sb.append(eval.evalString("prompt", prompt) + "</option>\n");
                sb.append( "--" + "</option>\n" );
            }

            for ( Iterator i = items.iterator(); i.hasNext(); ) {
                DictionaryItem dicItem = (DictionaryItem) i.next();
                sb.append( "    <option value=\"" + dicItem.getValue() + "\"" );

                if ( ( value != null ) && value.equals( dicItem.getValue() ) ) {
                    sb.append( " selected=\"selected\"" );
                }

                sb.append( ">" + dicItem.getContent() + "</option>\n" );
            }

            sb.append( "</select>" );

            try {
                pageContext.getOut().write( sb.toString() );
            }
            catch ( IOException io ) {
                throw new JspException( io );
            }
        }

        return super.doStartTag();
    }

    private DictionaryItemManager getDictionaryItemsManager() {
        return (DictionaryItemManager) WebApplicationContextUtils.getWebApplicationContext( this.pageContext.getServletContext() ).getBean(
                                                                                                                                            "dictionaryItemManager" );
    }

}
