package com.huge.exceptions;

public class BusinessException
    extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -6029262068121573969L;

    public BusinessException( final String msg ) {
        super( msg );
    }

    public BusinessException() {

    }
}
