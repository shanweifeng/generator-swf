package com.swf.mybatis.generator.config;

public class TypePropertyHolder extends PropertyHolder {

    private String configurationType;

    public TypePropertyHolder(){super();}

    public String getConfigurationType() {
        return configurationType;
    }

    public void setConfigurationType(String configurationType) {
        if(!"DEFAULT".equalsIgnoreCase(configurationType)){
            this.configurationType = configurationType;
        }
    }
}
