package com.swf.mybatis.generator.exception;

public class ShellException extends Exception {

    private static final long serialVersionUID = 4293148724730581615L;

    public ShellException(){super();}

    public ShellException(String arg0){
        super(arg0);
    }

    public ShellException(String arg0,Throwable arg1){
        super(arg0,arg1);
    }

    public ShellException(Throwable arg0){
        super(arg0);
    }
}
