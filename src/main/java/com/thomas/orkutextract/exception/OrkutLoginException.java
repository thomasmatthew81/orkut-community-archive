package com.thomas.orkutextract.exception;

/**
 * Created by Thomas on 9/20/2014.
 */
public class OrkutLoginException extends RuntimeException {

    public OrkutLoginException(){super();}
    public OrkutLoginException(String message){super(message);}
    public OrkutLoginException(Throwable cause){super(cause);}
    public OrkutLoginException(String message, Throwable cause){super(message,cause);}

}
