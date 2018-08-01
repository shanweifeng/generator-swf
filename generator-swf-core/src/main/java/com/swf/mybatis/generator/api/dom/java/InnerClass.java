package com.swf.mybatis.generator.api.dom.java;

import com.swf.mybatis.generator.api.dom.OutputUtilities;

import java.util.*;

public class InnerClass extends JavaElement {
    private List<Field> fields;

    private List<InnerClass> innerClasses;

    private List<InnerEnum> innerEnums;

    private List<TypeParameter> typeParameters;

    private  FullyQualifiedJavaType superClass;

    private FullyQualifiedJavaType type;

    private Set<FullyQualifiedJavaType> superInterfaceTypes;

    private List<Method> methods;

    private boolean isAbstract;

    private List<InitializationBlock> initializationBlocks;

    public InnerClass(FullyQualifiedJavaType type){
        super();
        this.type = type;
        fields = new ArrayList<>();
        innerClasses = new ArrayList<>();
        innerEnums = new ArrayList<>();
        this.typeParameters = new ArrayList<>();
        superInterfaceTypes = new HashSet<>();
        methods = new ArrayList<>();
        initializationBlocks = new ArrayList<>();
    }

    public InnerClass(String typeName){this(new FullyQualifiedJavaType(typeName));}

    public List<Field> getFields() {
        return fields;
    }

    public void addField(Field field){fields.add(field);}

    public FullyQualifiedJavaType getSuperClass() {
        return superClass;
    }

    public void setSuperClass(FullyQualifiedJavaType superClass) {
        this.superClass = superClass;
    }

    public void setSuperClass(String superClassType) {
        this.superClass = new FullyQualifiedJavaType(superClassType);
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

    public List<TypeParameter> getTypeParameters() {
        return typeParameters;
    }

    public void addTypeParameter(TypeParameter typeParameter){
        typeParameters.add(typeParameter);
    }

    public List<InitializationBlock> getInitializationBlocks() {
        return initializationBlocks;
    }

    public void addInitializationBlock(InitializationBlock initializationBlock){
        initializationBlocks.add(initializationBlock);
    }

    public String getFormattedContent(int indentLevel,CompilationUnit compilationUnit){
        StringBuilder sb = new StringBuilder();

        addFormattedJavadoc(sb,indentLevel);
        addFormattedAnnotations(sb,indentLevel);
        OutputUtilities.javaIndent(sb,indentLevel);
        sb.append(getVisibility().getValue());

        if(isAbstract()){
            sb.append("abstract ");
        }

        if(isStatic()){
            sb.append("static ");
        }
        if(isFinal()){
            sb.append("final ");
        }
        sb.append("class ");
        sb.append(getType().getShortName());

        if(!this.getTypeParameters().isEmpty()){
            boolean comma = false;
            sb.append("<");
            for (TypeParameter typeParameter:typeParameters){
                if(comma){
                    sb.append(", ");
                }
                sb.append(typeParameter.getFormattedContent(compilationUnit));
                comma = true;
            }
            sb.append("> ");
        }

        if(superClass != null){
            sb.append(" extends ");
            sb.append(JavaDomUtils.calculateTypeName(compilationUnit,superClass));
        }

        if(superInterfaceTypes.size() > 0){
            sb.append(" implements ");
            boolean comma = false;
            for (FullyQualifiedJavaType fqjt : superInterfaceTypes){
                if(comma){
                    sb.append(", ");
                }else {
                    comma = true;
                }
                sb.append(JavaDomUtils.calculateTypeName(compilationUnit,fqjt));
            }
        }

        sb.append(" {");
        indentLevel++;

        Iterator<Field> fldIter = fields.iterator();
        while(fldIter.hasNext()){
            OutputUtilities.newLine(sb);
            Field field = fldIter.next();
            sb.append(field.getFormattedContent(indentLevel,compilationUnit));
            if(fldIter.hasNext()){
                OutputUtilities.newLine(sb);
            }
        }

        if(initializationBlocks.size() > 0){
            OutputUtilities.newLine(sb);
        }
        Iterator<InitializationBlock> blkIter = initializationBlocks.iterator();
        while (blkIter.hasNext()){
            OutputUtilities.newLine(sb);
            InitializationBlock initializationBlock = blkIter.next();
            sb.append(initializationBlock.getFormattedContent(indentLevel));
            if(blkIter.hasNext()){
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
            if(blkIter.hasNext()){
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

        return sb.toString();
    }

    public Set<FullyQualifiedJavaType> getSuperInterfaceTypes() {
        return superInterfaceTypes;
    }

    public void addSuperInterface(FullyQualifiedJavaType superInterface){
        superInterfaceTypes.add(superInterface);
    }

    public FullyQualifiedJavaType getType() {
        return type;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method){
        methods.add(method);
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
