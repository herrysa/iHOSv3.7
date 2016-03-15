package org.sbbs.app.license.model;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class Product implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6052991646263949079L;

    private String productId;

    private String productName;
    
    private String note;

    private String allowedUsers;

    public String getProductId() {
        return productId;
    }

    public void setProductId( String productId ) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName( String productName ) {
        this.productName = productName;
    }

    public String getAllowedUsers() {
        return allowedUsers;
    }

    public void setAllowedUsers( String allowedUsers ) {
        this.allowedUsers = allowedUsers;
    }

    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }
    
   
}
