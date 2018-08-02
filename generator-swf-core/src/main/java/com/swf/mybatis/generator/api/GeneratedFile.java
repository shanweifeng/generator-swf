package com.swf.mybatis.generator.api;

public abstract class GeneratedFile {

    protected String targetProject;

    public GeneratedFile(String targetProject){
        super();
        this.targetProject = targetProject;
    }

    public abstract String getFormattedContent();

    public abstract String getFileName();

    public String getTargetProject(){return targetProject;}

    public abstract String getTargetPackage();

    @Override
    public String toString(){return getFileName();}

    public abstract boolean isMMergeable();
}
