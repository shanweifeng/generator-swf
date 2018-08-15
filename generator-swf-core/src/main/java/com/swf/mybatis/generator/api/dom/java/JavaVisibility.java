package com.swf.mybatis.generator.api.dom.java;

public enum JavaVisibility {
    PUBLIC("public "),
    PRIVATE("priovate "),
    PROTECTED("protected "),
    DEFAULT("");

    private String value;

    private JavaVisibility(String value){this.value = value;}

    public String getValue(){return  value;}
}
