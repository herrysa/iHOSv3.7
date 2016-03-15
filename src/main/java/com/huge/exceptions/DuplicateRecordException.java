package com.huge.exceptions;

public class DuplicateRecordException
    extends Exception {

    private static final long serialVersionUID = -7831544606576686779L;

    public DuplicateRecordException( final String message ) {
        super( message );
    }

}