package com.swf.mybatis.generator.api.dom.java;

import com.swf.mybatis.generator.api.dom.OutputUtilities;
import org.ietf.jgss.Oid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class Method extends JavaElement {

    private List<String> bodyLines;

    private boolean constructor;

    private FullyQualifiedJavaType returnType;

    private String name;

    private List<TypeParameter> typeParameters;

    private List<Parameter> parameters;

    private List<FullyQualifiedJavaType> exceptions;

    private boolean isSynchronized;

    private boolean isNative;

    private boolean isDefault;

    public Method(){this("bar");}

    public Method(String name){
        super();
        bodyLines = new ArrayList<>();
        typeParameters = new ArrayList<>();
        parameters = new ArrayList<>();
        exceptions = new ArrayList<>();
        this.name = name;
    }

    public Method(Method original){
        super(original);
        bodyLines = new ArrayList<>();
        typeParameters = new ArrayList<>();
        parameters = new ArrayList<>();
        exceptions = new ArrayList<>();
        this.bodyLines.addAll(original.bodyLines);
        this.constructor = original.constructor;
        this.exceptions.addAll(original.exceptions);
        this.name = original.name;
        this.typeParameters.addAll(original.typeParameters);
        this.parameters.addAll(original.parameters);
        this.returnType = original.returnType;
        this.isNative = original.isNative;
        this.isSynchronized = original.isSynchronized;
        this.isDefault = original.isDefault;
    }

    public List<String> getBodyLines() {
        return bodyLines;
    }

    public void addBodyLine(String line){
        bodyLines.add(line);
    }

    public void addBodyLine(int index,String line){
        bodyLines.add(index,line);
    }

    public void addBodyLines(Collection<String> lines){bodyLines.addAll(lines);}

    public void addBodyLines(int index, Collection<String> lines){bodyLines.addAll(index,lines);}

    public String getFormattedContent(int indentlevel,boolean interfaceMethod,CompilationUnit compilationUnit){
        StringBuilder sb = new StringBuilder();

        addFormattedJavadoc(sb,indentlevel);
        addFormattedAnnotations(sb,indentlevel);
        OutputUtilities.javaIndent(sb,indentlevel);

        if(interfaceMethod){
            if(isStatic()){
                sb.append("static ");
            }else if (isDefault){
                sb.append("default ");
            }
        }else{
            sb.append(getVisibility().getValue());

            if(isStatic()){
                sb.append("static ");
            }

            if(isFinal()){
                sb.append("final ");
            }

            if(isSynchronized){
                sb.append("synchronized ");
            }

            if(isNative){
                sb.append("native ");
            }else if (bodyLines.size() == 0){
                sb.append("abstract ");
            }
        }

        if(!getTypeParameters().isEmpty()){
            sb.append("<");
            boolean comma = false;
            for(TypeParameter typeParameter : getTypeParameters()){
                if(comma){
                    sb.append(", ");
                }else {
                    comma = true;
                }
                sb.append(typeParameter.getFormattedContent(compilationUnit));
            }
            sb.append("> ");
        }

        if(!constructor){
            if(getReturnType() == null){
                sb.append("void");
            }else{
                sb.append(JavaDomUtils.calculateTypeName(compilationUnit,getReturnType()));
            }
            sb.append(' ');
        }

        sb.append(getName());
        sb.append('(');
        boolean comma = false;
        for (Parameter parameter : getParameters()){
            if(comma){
                sb.append(", ");
            }else{
                comma = true;
            }
            sb.append(parameter.getFormattedContent(compilationUnit));
        }

        sb.append(')');

        if(getExceptions().size() > 0){
            sb.append(" throws ");
            comma = false;
            for(FullyQualifiedJavaType fqjt : getExceptions()){
                if(comma){
                    sb.append(", ");
                }else {
                    comma = true;
                }
                sb.append(JavaDomUtils.calculateTypeName(compilationUnit,fqjt));
            }
        }

        if(bodyLines.size() == 0 || isNative()){
            sb.append(';');
        }else{
            sb.append(" {");
            indentlevel++;

            ListIterator<String> listIter = bodyLines.listIterator();
            while(listIter.hasNext()){
                String line = listIter.next();
                if(line.startsWith("}")){
                    indentlevel--;
                }

                OutputUtilities.newLine(sb);
                OutputUtilities.javaIndent(sb,indentlevel);
                sb.append(line);

                if((line.endsWith("{") && !line.startsWith("switch")) || line.endsWith(":")){
                    indentlevel++;
                }

                if(line.startsWith("break")){
                    if(listIter.hasNext()){
                        String nextLine = listIter.next();
                        if(nextLine.startsWith("}")){
                            indentlevel++;
                        }
                        //set back to the previous element
                        listIter.previous();
                    }
                    indentlevel--;
                }
            }
            indentlevel--;
            OutputUtilities.newLine(sb);
            OutputUtilities.javaIndent(sb,indentlevel);
            sb.append('}');
        }
        return sb.toString();
    }

    public boolean isConstructor() {
        return constructor;
    }

    public void setConstructor(boolean constructor) {
        this.constructor = constructor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FullyQualifiedJavaType getReturnType() {
        return returnType;
    }

    public void setReturnType(FullyQualifiedJavaType returnType) {
        this.returnType = returnType;
    }

    public List<TypeParameter> getTypeParameters() {
        return typeParameters;
    }

    public void addTypeParameter(TypeParameter typeParameter){typeParameters.add(typeParameter);}

    public void addTypeParameter(int index, TypeParameter typeParameter){typeParameters.add(index,typeParameter);}

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void addParameter(Parameter parameter){parameters.add(parameter);}

    public void addParameter(int index, Parameter parameter){parameters.add(index,parameter);}

    public List<FullyQualifiedJavaType> getExceptions() {
        return exceptions;
    }

    public void addException(FullyQualifiedJavaType exception){
        exceptions.add(exception);
    }

    public String getName() {
        return name;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public boolean isNative() {
        return isNative;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
