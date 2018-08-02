package com.swf.mybatis.generator.api.dom.java;

import com.swf.mybatis.generator.api.dom.OutputUtilities;

import java.util.*;

import static com.swf.mybatis.generator.api.dom.OutputUtilities.calculateImports;
import static com.swf.mybatis.generator.api.dom.OutputUtilities.javaIndent;
import static com.swf.mybatis.generator.api.dom.OutputUtilities.newLine;

public class InnerInterface extends JavaElement{

    private List<Field> fields;

    private FullyQualifiedJavaType type;

    private List<InnerInterface> innerInterfaces;

    private Set<FullyQualifiedJavaType> superInterfaceTypes;

    private List<Method> methods;

    public InnerInterface(FullyQualifiedJavaType type){
        super();
        this.type = type;
        innerInterfaces = new ArrayList<>();
        superInterfaceTypes = new LinkedHashSet<>();
        methods = new ArrayList<>();
        fields = new ArrayList<>();
    }

    public InnerInterface(String type){
        this(new FullyQualifiedJavaType(type));
    }

    public List<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public String getFormattedContent(int indevtLevel,CompilationUnit compilationUnit){
        StringBuilder sb = new StringBuilder();

        addFormattedJavadoc(sb,indevtLevel);
        addFormattedAnnotations(sb,indevtLevel);
        javaIndent(sb,indevtLevel);
        sb.append(getVisibility().getValue());

        if(isStatic()){
            sb.append("static ");
        }

        if(isFinal()){
            sb.append("final ");
        }

        sb.append("interface ");
        sb.append(getType().getShortName());

        if(getSuperInterfaceTypes().size() > 0){
            sb.append(" extends ");
            boolean comma = false;
            for(FullyQualifiedJavaType fqjt : getSuperInterfaceTypes()){
                if(comma){
                    sb.append(", ");
                }else{
                    comma = true;
                }
                sb.append(JavaDomUtils.calculateTypeName(compilationUnit,fqjt));
            }
        }

        sb.append(" {");
        indevtLevel++;

        Iterator<Field> fldIter = fields.iterator();
        while (fldIter.hasNext()){
            OutputUtilities.newLine(sb);
            Field field = fldIter.next();
            sb.append(field.getFormattedContent(indevtLevel,compilationUnit));
        }

        if(fields.size() > 0 && methods.size() > 0){
            OutputUtilities.newLine(sb);
        }

        Iterator<Method> mtdIter = getMethods().iterator();
        while (mtdIter.hasNext()){
            newLine(sb);
            Method method = mtdIter.next();
            sb.append(method.getFormattedContent(indevtLevel,true,compilationUnit));
            if(mtdIter.hasNext()){
                newLine(sb);
            }
        }

        if(innerInterfaces.size() > 0){
            newLine(sb);
        }

        Iterator<InnerInterface> iiIter = innerInterfaces.iterator();
        while (iiIter.hasNext()){
            newLine(sb);
            InnerInterface innerInterface = iiIter.next();
            sb.append(innerInterface.getFormattedContent(indevtLevel,compilationUnit));
            if(iiIter.hasNext()){
                newLine(sb);
            }
        }

        indevtLevel--;
        newLine(sb);
        javaIndent(sb,indevtLevel);
        sb.append('}');
        return sb.toString();
    }

    private void addSuperInterface(FullyQualifiedJavaType superInterface){
        superInterfaceTypes.add(superInterface);
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public FullyQualifiedJavaType getType(){
        return type;
    }

    public FullyQualifiedJavaType getSuperClass(){
        return null;
    }

    public Set<FullyQualifiedJavaType> getSuperInterfaceTypes() {
        return superInterfaceTypes;
    }

    public List<InnerInterface> getInnerInterfaces() {
        return innerInterfaces;
    }

    public void addInnerInterfaces(InnerInterface innerInterface){
        innerInterfaces.add(innerInterface);
    }

    public boolean isJavaInterface(){return true;}

    public boolean isJavaEnumeration(){return false;}
}
