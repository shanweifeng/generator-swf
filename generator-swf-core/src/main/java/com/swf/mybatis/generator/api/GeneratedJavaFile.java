package com.swf.mybatis.generator.api;

import com.swf.mybatis.generator.api.dom.java.CompilationUnit;

public class GeneratedJavaFile extends GeneratedFile {

    private CompilationUnit compilationUnit;

    private String fileEncoding;

    private JavaFormatter javaFormatter;

    public GeneratedJavaFile(CompilationUnit compilationUnit,String targetProject,String fileEncoding,JavaFormatter javaFormatter){
        super(targetProject);
        this.compilationUnit = compilationUnit;
        this.fileEncoding = fileEncoding;
        this.javaFormatter = javaFormatter;
    }

    public GeneratedJavaFile(CompilationUnit compilationUnit,String targetProject,JavaFormatter javaFormatter){this(compilationUnit,targetProject,null,javaFormatter);}



    @Override
    public String getFormattedContent() {
        return javaFormatter.getFormattedContent(compilationUnit);
    }

    @Override
    public String getFileName() {
        return compilationUnit.getType().getShortNameWithoutTypeArguments()+".java";
    }

    @Override
    public String getTargetPackage() {
        return compilationUnit.getType().getPackageName();
    }

    public CompilationUnit getCompilationUnit(){return compilationUnit;}

    @Override
    public boolean isMMergeable() {
        return true;
    }

    public String getFileEncoding(){return fileEncoding;}
}
