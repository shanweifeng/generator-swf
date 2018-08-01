package com.swf.mybatis.generator.api.dom.java;

import com.swf.mybatis.generator.api.dom.OutputUtilities;

public class Field extends JavaElement {
    private FullyQualifiedJavaType type;
    private String name;
    private String initializationString;
    private boolean isTransient;
    private boolean isVolatile;

    public Field(){
        this("foo",FullyQualifiedJavaType.getIntInstance());
    }

    public Field(String name,FullyQualifiedJavaType type){
        super();
        this.name = name;
        this.type = type;
    }

    public Field(Field field){
        super();
        this.type = field.type;
        this.name = field.name;
        this.initializationString = field.initializationString;
    }

    public FullyQualifiedJavaType getType() {
        return type;
    }

    public void setType(FullyQualifiedJavaType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitializationString() {
        return initializationString;
    }

    public void setInitializationString(String initializationString) {
        this.initializationString = initializationString;
    }

    public String getFormattedContent(int indentLevel,CompilationUnit compilationUnit){
        StringBuilder sb = new StringBuilder();

        addFormattedJavadoc(sb,indentLevel);
        addFormattedAnnotations(sb,indentLevel);

        OutputUtilities.javaIndent(sb,indentLevel);
        sb.append(getVisibility().getValue());

        if(isStatic()){
            sb.append("static ");
        }
        if(isFinal()){
            sb.append("final ");
        }
        if(isTransient()){
            sb.append("transient");
        }
        if(isVolatile()){
            sb.append("volatile ");
        }

        sb.append(JavaDomUtils.calculateTypeName(compilationUnit,type));
        sb.append(' ');
        sb.append(name);
        if(initializationString != null & initializationString.length() > 0){
            sb.append("=");
            sb.append(initializationString);
        }
        sb.append(';');
        return sb.toString();
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean aTransient) {
        isTransient = aTransient;
    }

    public boolean isVolatile() {
        return isVolatile;
    }

    public void setVolatile(boolean aVolatile) {
        isVolatile = aVolatile;
    }
}
