package com.swf.mybatis.generator.api.dom.java;

import com.swf.mybatis.generator.api.dom.OutputUtilities;

import java.util.*;

public class InnerEnum extends JavaElement {

    private List<Field> fields;

    private List<InnerClass> innerClasses;

    private List<InnerEnum> innerEnums;

    private FullyQualifiedJavaType type;

    private Set<FullyQualifiedJavaType> superInterfaceTypes;

    private List<Method> methods;

    private List<String> enumConstants;

    public InnerEnum(FullyQualifiedJavaType type){
        super();
        this.type = type;
        fields = new ArrayList<>();
        innerClasses = new ArrayList<>();
        innerEnums = new ArrayList<>();
        superInterfaceTypes = new HashSet<>();
        methods = new ArrayList<>();
        enumConstants = new ArrayList<>();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void addField(Field field){
        fields.add(field);
    }

    public List<InnerClass> getInnerClasses() {
        return innerClasses;
    }

    public void addInnerClass(InnerClass innerClass){
        innerClasses.add(innerClass);
    }

    public List<InnerEnum> getInnerEnums() {
        return innerEnums;
    }

    public void addInnerEnum(InnerEnum innerEnum){
        innerEnums.add(innerEnum);
    }

    public List<String> getEnumConstants() {
        return enumConstants;
    }

    public void addEnumConstant(String enumConstant){
        enumConstants.add(enumConstant);
    }

    public String getFormattedContent(int indentLevel,CompilationUnit compilationUnit){
        StringBuilder sb = new StringBuilder();

        addFormattedJavadoc(sb,indentLevel);
        addFormattedAnnotations(sb,indentLevel);
        OutputUtilities.javaIndent(sb,indentLevel);

        if(getVisibility() == JavaVisibility.PRIVATE){
            sb.append(getVisibility().getValue());
        }

        sb.append("enum ");
        sb.append(getType().getShortName());

        if(superInterfaceTypes.size() > 0){
            sb.append(" implements ");
            boolean comma = false;
            for (FullyQualifiedJavaType fqjt : superInterfaceTypes){
                if(comma){
                    sb.append(", ");
                }else{
                    comma = true;
                }
                sb.append(JavaDomUtils.calculateTypeName(compilationUnit,fqjt));
            }
        }

        sb.append(" {");
        indentLevel++;

        Iterator<String> strIter = enumConstants.iterator();
        while (strIter.hasNext()){
            OutputUtilities.newLine(sb);
            OutputUtilities.javaIndent(sb,indentLevel);
            String enumConstant = strIter.next();
            sb.append(enumConstant);

            if(strIter.hasNext()){
                sb.append(',');
            }else{
                sb.append(';');
            }
        }

        if(fields.size() > 0){
            OutputUtilities.newLine(sb);
        }
        Iterator<Field> fldIter = fields.iterator();
        while (fldIter.hasNext()){
            OutputUtilities.newLine(sb);
            Field field = fldIter.next();
            sb.append(field.getFormattedContent(indentLevel,compilationUnit));
            if(fldIter.hasNext()){
                OutputUtilities.newLine(sb);
            }
        }
        if(methods.size() > 0){
            OutputUtilities.newLine(sb);
        }
        Iterator<Method> mtdIter = methods.iterator();
        while (mtdIter.hasNext()){
            OutputUtilities.newLine(sb);
            Method method = mtdIter.next();
            sb.append(method.getFormattedContent(indentLevel,false,compilationUnit));
            if(mtdIter.hasNext()){
                OutputUtilities.newLine(sb);
            }
        }

        if(innerClasses.size() > 0){
            OutputUtilities.newLine(sb);
        }
        Iterator<InnerClass> icIter = innerClasses.iterator();
        while (icIter.hasNext()){
            OutputUtilities.newLine(sb);
            InnerClass innerClass = icIter.next();
            sb.append(innerClass.getFormattedContent(indentLevel,compilationUnit));
            if(icIter.hasNext()){
                OutputUtilities.newLine(sb);
            }
        }

        if(innerEnums.size() > 0){
            OutputUtilities.newLine(sb);
        }
        Iterator<InnerEnum> ieIter = innerEnums.iterator();
        while (ieIter.hasNext()){
            OutputUtilities.newLine(sb);
            InnerEnum innerEnum = ieIter.next();
            sb.append(innerEnum.getFormattedContent(indentLevel,compilationUnit));
            if(ieIter.hasNext()){
                OutputUtilities.newLine(sb);
            }
        }
        indentLevel--;
        OutputUtilities.newLine(sb);
        OutputUtilities.javaIndent(sb,indentLevel);
        sb.append('}');
        return sb.toString();
    }

    public FullyQualifiedJavaType getType() {
        return type;
    }

    public Set<FullyQualifiedJavaType> getSuperInterfaceTypes() {
        return superInterfaceTypes;
    }

    public void addSuperInterface(FullyQualifiedJavaType superInterface){superInterfaceTypes.add(superInterface);}

    public List<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method){methods.add(method);}
}
