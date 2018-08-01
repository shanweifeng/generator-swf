package com.swf.mybatis.generator.logging;

public class LogException extends RuntimeException {

    private static final long serialVersionUID = -1415634051402986006L;

    public LogException(){super();}

    public LogException(String message){super(message);}

    public LogException(String message,Throwable cause){super(message,cause);}

    public LogException(Throwable cause){super(cause);}
}
