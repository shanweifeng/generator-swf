package com.swf.mybatis.generator.exception;

import java.util.List;

public class InvalidConfigurationException extends Exception {
    private static final long serialVersionUID = -7984412122181908843L;

    private List<String> errors;

    private InvalidConfigurationException(List<String> errors){
        super();
        this.errors = errors;
    }

    public List<String> getErrors(){return errors;}

    public String getMessage(){
        if(errors != null && errors.size() > 0){
            return errors.get(0);
        }
        return super.getMessage();
    }
}
