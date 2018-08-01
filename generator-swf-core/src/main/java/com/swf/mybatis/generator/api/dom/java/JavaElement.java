package com.swf.mybatis.generator.api.dom.java;

import com.swf.mybatis.generator.api.dom.OutputUtilities;

import java.util.ArrayList;
import java.util.List;

public abstract class JavaElement {
    private List<String> javaDocLines;

    private JavaVisibility visibility = JavaVisibility.DEFAULT;

    private boolean isStatic;

    private boolean isFinal;

    private List<String> annotations;

    public JavaElement(){
        super();
        javaDocLines = new ArrayList<>();
        annotations = new ArrayList<>();
    }

    public JavaElement(JavaElement original){
        this();
        this.annotations.addAll(original.annotations);
        this.isFinal = original.isFinal;
        this.isStatic = original.isStatic;
        this.javaDocLines.addAll(original.javaDocLines);
        this.visibility = original.visibility;
    }

    public List<String> getJavaDocLines(){return javaDocLines;}

    public void addJavaDocLime(String javaDocLine){
        javaDocLines.add(javaDocLine);
    }

    public List<String> getAnnotations(){return annotations;}

    public void addAnnotation(String annotation){annotations.add(annotation);}

    public JavaVisibility getVisibility(){return visibility;}

    public void setVisibility(JavaVisibility visibility){this.visibility = visibility;}

    public void addSuppressTypeWarningsAnnotation(){
        addAnnotation("@suppressWarnings(\"unchecked\")");
    }

    public void addFormattedJavadoc(StringBuilder sb,int indentLevel){
        for(String javaDocLine : javaDocLines){
            OutputUtilities.javaIndent(sb,indentLevel);
            sb.append(javaDocLine);
            OutputUtilities.newLine(sb);
        }
    }

    public void addFormattedAnnotations(StringBuilder sb,int indentLevel){
        for(String annotation : annotations){
            OutputUtilities.javaIndent(sb,indentLevel);
            sb.append(annotation);
            OutputUtilities.newLine(sb);
        }
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }
}
